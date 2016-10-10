/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soap.security.encryption;

import com.sun.org.apache.xml.internal.security.encryption.EncryptedData;
import com.sun.org.apache.xml.internal.security.encryption.EncryptedKey;
import com.sun.org.apache.xml.internal.security.encryption.Reference;
import com.sun.org.apache.xml.internal.security.encryption.ReferenceList;
import com.sun.org.apache.xml.internal.security.encryption.XMLCipher;
import com.sun.org.apache.xml.internal.security.encryption.XMLEncryptionException;
import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
import com.sun.org.apache.xml.internal.security.keys.KeyInfo;
import com.sun.org.apache.xml.internal.security.keys.content.X509Data;
import com.sun.org.apache.xml.internal.security.keys.content.x509.XMLX509SKI;
import com.sun.xml.wss.core.KeyInfoHeaderBlock;
import com.sun.xml.wss.core.SecurityHeader;
import com.sun.xml.wss.core.SecurityTokenReference;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.SOAPPart;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPEnvelope;

import com.sun.xml.wss.impl.SecurableSoapMessage;

import com.sun.xml.wss.impl.XMLUtil;
import com.sun.xml.wss.impl.misc.SecurityUtil;
import java.security.NoSuchAlgorithmException;
import java.security.Key;
import java.security.KeyStore;
import java.security.PublicKey;
import java.security.cert.X509Certificate;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.wsssoapbox.soap.support.SoapCreator;
import org.wsssoapbox.xml.util.XMLUtils;

/**
 *
 * @author Peter
 */
public class EncryptionPayloadTest {

   SOAPMessage soapMessage;
   SOAPEnvelope envelope;
   SecurableSoapMessage secureMessage;
   SOAPPart soapPart;
   SOAPBody soapBody;
   SOAPHeader soapHeader;
   KeyStore keystore;
   Key privateKey;
   PublicKey publicKey;
   X509Certificate certificate;
   String password = "123456";
   String aliasName = "client_keystore";
   String strMessage =
           "<soapenv:Envelope xmlns:oss=\"http://payment.javatech.com/ws/schema/oss\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">"
           + "<soapenv:Header>"
           + "</soapenv:Header>"
           + "<soapenv:Body>"
           + "<oss:paymentRequest>"
           + "<oss:amount>239</oss:amount>"
           + "<oss:cardNumber>1232323454565</oss:cardNumber>"
           + "<oss:expirationDate>12/12</oss:expirationDate>"
           + "<oss:securityCode>567</oss:securityCode>"
           + "<oss:nameOnCard>Pongsak Gran</oss:nameOnCard>"
           + "</oss:paymentRequest>"
           + "</soapenv:Body>"
           + "</soapenv:Envelope>";

   public EncryptionPayloadTest() {
   }

   @Before
   public void setUp() throws Exception {



      soapMessage = MessageFactory.newInstance().createMessage();

      soapMessage = SoapCreator.createSOAPMessageFromString(strMessage);
      //  soapMessage.saveChanges();

      //  soapMessage.writeTo(System.out);

      envelope = soapMessage.getSOAPPart().getEnvelope();
      secureMessage = new SecurableSoapMessage(soapMessage);
      soapPart = soapMessage.getSOAPPart();
      soapHeader = soapMessage.getSOAPHeader();
      soapBody = soapMessage.getSOAPBody();


      FileInputStream fis = new FileInputStream("F:/Develope/sourecode/netbeans/Web_Service/Prodiver/ws-payment-btoken/src/main/webapp/WEB-INF/config/certificates/client/client.jks");
      BufferedInputStream bis = new BufferedInputStream(fis);

      keystore = KeyStore.getInstance("JKS", "SUN");
      keystore.load(bis, password.toCharArray());

      //   CertificateFactory certFact = CertificateFactory.getInstance("X.509");
      //  cert = (X509Certificate) certFact.generateCertificate(bis);
      certificate = (X509Certificate) keystore.getCertificate(aliasName);
      publicKey = certificate.getPublicKey();
      privateKey = keystore.getKey(aliasName, password.toCharArray());
   }

   // @Test
   public void testEncryptXWSS() throws ParserConfigurationException, NoSuchAlgorithmException, Exception {


      // Create a WSSE context for the SOAP message
      secureMessage = new SecurableSoapMessage(soapMessage);

      // Create a security header for the message (<wsse:Security>)
      SecurityHeader secureHeader = secureMessage.findWsseSecurityHeaderBlock(true, true);


      KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
      keyGenerator.init(128);
      SecretKey sessionKey = keyGenerator.generateKey();

      String referenceId = "_" + UUID.randomUUID().toString();
      XMLCipher dataCipher = XMLCipher.getInstance(XMLCipher.AES_256);
      dataCipher.init(XMLCipher.ENCRYPT_MODE, sessionKey);

      EncryptedData encryptedData = dataCipher.encryptData(soapBody.getOwnerDocument(), soapBody.getOwnerDocument().getDocumentElement());
      encryptedData.setId(referenceId);

      SecurityTokenReference securityTokenReference1 = new SecurityTokenReference(soapBody.getOwnerDocument());

      KeyInfoHeaderBlock keyInfoHeaderBlock = new KeyInfoHeaderBlock(soapBody.getOwnerDocument());


      KeyInfo keyInfoEncData = new KeyInfo(soapBody.getOwnerDocument());

      Element encryptedDataElem = dataCipher.martial(encryptedData);

      //   keyInfoEncData.setElement(keyInfoHeaderBlock, "http://www.w3.org/2000/09/xmldsig#");
      encryptedData.setKeyInfo(keyInfoEncData);
//      keyInfoEncData.addUnknownElement(keyInfoHeaderBlock);
      soapBody.removeContents();
      soapBody.appendChild(encryptedDataElem);


      XMLCipher keyCipher = XMLCipher.getInstance(XMLCipher.RSA_v1dot5);
      keyCipher.init(XMLCipher.WRAP_MODE, certificate.getPublicKey());

      EncryptedKey encryptedKey = keyCipher.encryptKey(secureHeader.getOwnerDocument(), sessionKey);


      KeyInfo keyInfo = new KeyInfo(secureHeader.getOwnerDocument());
      encryptedKey.setKeyInfo(keyInfo);


      // X509Data x509Data = new X509Data(secureHeader.getOwnerDocument());
      //  keyInfo.add(x509Data);

      // String subKeyId = CertUtil.getSubjectKeyIdentity(certificate);
      // x509Data.addSKI(subKeyId.getBytes());

      // Create block (<wsse:SecurityTokenReference>) 
      SecurityTokenReference securityTokenReference = new SecurityTokenReference(secureHeader.getOwnerDocument());
      securityTokenReference.setWsuId(referenceId);

      // Insert the Signature block (<wsse:SecurityTokenReference>)  to (<ds:KeyInfo>)
      // keyInfo.addUnknownElement(securityTokenReference);



      ReferenceList referenceList =
              keyCipher.createReferenceList(ReferenceList.DATA_REFERENCE);
      encryptedKey.setReferenceList(referenceList);
      Reference dataReference = referenceList.newDataReference("#" + referenceId);
      referenceList.add(dataReference);


      Element encryptedKeyElem = keyCipher.martial(encryptedKey);
      secureHeader.appendChild(encryptedKeyElem);
      soapHeader.addChildElement(secureHeader.getAsSoapElement());

      System.out.println(
              XMLUtils.prettyPrint(soapMessage));
   }

   @Test
   public void testPayload() throws ParserConfigurationException, NoSuchAlgorithmException, XMLEncryptionException, Exception {
      DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
      docBuilderFactory.setNamespaceAware(true);
      DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();

      Document containerDoc = docBuilder.newDocument();

      Element encryptedPayloadElem = containerDoc.createElementNS(
              "http://ns.electronichealth.net.au/xsp/xsd/EncryptedPayload/2010",
              "ep:payloadDoc");

      containerDoc.appendChild(encryptedPayloadElem);

      Element body = containerDoc.createElementNS(
              "http://ns.electronichealth.net.au/xsp/xsd/EncryptedPayload/2010",
              "ep:body");

      Element data = containerDoc.createElementNS(
              "http://ns.electronichealth.net.au/xsp/xsd/EncryptedPayload/2010",
              "ep:data");

      data.setNodeValue("11111111111");

      body.appendChild(data);

      Element keysElem = containerDoc.createElementNS(
              "http://ns.electronichealth.net.au/xsp/xsd/EncryptedPayload/2010",
              "ep:keys");
      encryptedPayloadElem.appendChild(keysElem);
      Element encryptedPayloadDataElem = containerDoc.createElementNS("http://ns.electronichealth.net.au/xsp/xsd/EncryptedPayload/2010",
              "ep:encryptedPayloadData");
      encryptedPayloadElem.appendChild(encryptedPayloadDataElem);

      encryptedPayloadElem.appendChild(body);

      System.out.println(
              XMLUtils.prettyPrintDoc(containerDoc));

      KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
      keyGenerator.init(256);

      SecretKey sessionKey = keyGenerator.generateKey();
      String referenceId = "_" + UUID.randomUUID().toString();
      XMLCipher dataCipher = XMLCipher.getInstance(XMLCipher.AES_256);
      dataCipher.init(XMLCipher.ENCRYPT_MODE, sessionKey);

      EncryptedData encryptedData =
              dataCipher.encryptData(containerDoc, body.getOwnerDocument().getDocumentElement());

      encryptedData.setId(referenceId);
      Element encryptedDataElem = dataCipher.martial(encryptedData);
      encryptedPayloadDataElem.appendChild(encryptedDataElem);

      XMLCipher keyCipher = XMLCipher.getInstance(XMLCipher.RSA_v1dot5);
      keyCipher.init(XMLCipher.WRAP_MODE, certificate.getPublicKey());
      EncryptedKey encryptedKey = keyCipher.encryptKey(containerDoc, sessionKey);

      KeyInfo keyInfo = new KeyInfo(containerDoc);
      encryptedKey.setKeyInfo(keyInfo);


      X509Data x509Data = new X509Data(containerDoc);
      keyInfo.add(x509Data);
      x509Data.addCertificate(certificate);
      ReferenceList referenceList =
              keyCipher.createReferenceList(ReferenceList.DATA_REFERENCE);
      encryptedKey.setReferenceList(referenceList);
      Reference dataReference = referenceList.newDataReference("#" + referenceId);
      referenceList.add(dataReference);

      Element encryptedKeyElem = keyCipher.martial(encryptedKey);

      keysElem.appendChild(encryptedKeyElem);

      //    System.out.println( XMLUtils.prettyPrintDoc(containerDoc));

      decryptPayload(containerDoc);


   }

   public void decryptPayload(Document containerDoc) throws XMLEncryptionException, XMLSecurityException, Exception {


      Element encryptedPayloadElem = containerDoc.getDocumentElement();


    //  System.out.println(XMLUtils.prettyPrintDoc(containerDoc));


      Element keysElem = XMLUtils.getChild(encryptedPayloadElem, "keys");
      List<Element> encryptedKeyElems = XMLUtil.getElementsByTagNameNS1(keysElem, " http://www.w3.org/2001/04/xmlenc#", "EncryptedKey");


      for (Element encryptedKeyElem : encryptedKeyElems) {

         XMLCipher xmlCipher = XMLCipher.getInstance();
         xmlCipher.init(XMLCipher.DECRYPT_MODE, null);

         EncryptedKey encryptedKey = xmlCipher.loadEncryptedKey(encryptedKeyElem);

         KeyInfo keyInfo = encryptedKey.getKeyInfo();
         X509Data x509Data = keyInfo.itemX509Data(0);
         byte[] x509DataSki = x509Data.itemSKI(0).getSKIBytes();

         byte[] decryptCertSki = XMLX509SKI.getSKIBytesFromCert(certificate);
         boolean skiMatches = Arrays.equals(x509DataSki, decryptCertSki);

         Element encryptedPayloadDataElem = XMLUtils.getChild(encryptedPayloadElem, "encryptedPayloadData");
         Element encryptedDataElem = XMLUtils.getChild(encryptedPayloadDataElem, "EncryptedData");


         EncryptedData encryptedData = xmlCipher.loadEncryptedData(
                 containerDoc, encryptedDataElem);
         String encryptionAlgorithm = encryptedData.getEncryptionMethod().getAlgorithm();

         xmlCipher.init(XMLCipher.DECRYPT_MODE, privateKey);
         Key sessionKey = xmlCipher.decryptKey(encryptedKey, encryptionAlgorithm);

         xmlCipher.init(XMLCipher.DECRYPT_MODE, sessionKey);
         Document payloadDoc = xmlCipher.doFinal(containerDoc, encryptedDataElem);

         System.out.println(XMLUtils.prettyPrintDoc(payloadDoc));

      }




   }
}
