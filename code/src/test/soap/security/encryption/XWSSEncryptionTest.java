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

import org.apache.xml.security.keys.KeyInfo;
import com.sun.org.apache.xml.internal.security.keys.content.X509Data;
import com.sun.org.apache.xml.internal.security.keys.content.x509.XMLX509SKI;
import com.sun.xml.wss.core.EncryptedKeyHeaderBlock;
import com.sun.xml.wss.core.KeyInfoHeaderBlock;
import com.sun.xml.wss.core.ReferenceElement;
import com.sun.xml.wss.core.ReferenceListHeaderBlock;
import com.sun.xml.wss.core.SecurityHeader;
import com.sun.xml.wss.core.SecurityTokenReference;
import com.sun.xml.wss.core.reference.DirectReference;
import com.sun.xml.wss.core.reference.X509IssuerSerial;
import com.sun.xml.wss.core.reference.X509ThumbPrintIdentifier;
import com.sun.xml.wss.impl.SecurableSoapMessage;
import com.sun.xml.wss.impl.XMLUtil;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.Key;
import java.security.KeyStore;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.naming.Name;
import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.wsssoapbox.soap.support.SoapConstants;
import org.wsssoapbox.soap.support.SoapCreator;
import org.wsssoapbox.soap.security.CertUtil;
import org.wsssoapbox.soap.security.support.BinarySecurityTokenType;
import org.wsssoapbox.soap.security.support.IssuerNameType;
import org.wsssoapbox.soap.security.support.KeyIndentifierType;
import org.wsssoapbox.soap.security.support.SubjectkeyIdentifierType;
import org.wsssoapbox.soap.security.support.X509CertificateType;
import org.wsssoapbox.xml.util.XMLUtils;

/**
 *
 * @author Peter
 */
public class XWSSEncryptionTest {

   SOAPMessage soapMessage;
   SOAPMessage soapResponse;
   SOAPEnvelope envelope;
   SecurableSoapMessage secureMessage;
   SOAPPart soapPart;
   SOAPBody soapBody;
   SOAPHeader soapHeader;
   KeyStore keystore;
   Key privateKey;
   PublicKey publicKey;
   String file;
   X509Certificate certificate;
   String password = "123456";
   String aliasName = "server_cert";
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

   public XWSSEncryptionTest() {
   }

   @Before
   public void setUp() throws Exception {

      soapMessage = MessageFactory.newInstance().createMessage();
      soapMessage = SoapCreator.createSOAPMessageFromString(strMessage);

      envelope = soapMessage.getSOAPPart().getEnvelope();
      secureMessage = new SecurableSoapMessage(soapMessage);
      soapPart = soapMessage.getSOAPPart();
      soapHeader = soapMessage.getSOAPHeader();
      soapBody = soapMessage.getSOAPBody();

      file = "F:/Develope/sourecode/netbeans/Web_Service/Prodiver/ws-payment-btoken/src/main/webapp/WEB-INF/config/certificates/client/client.jks";
      FileInputStream fis = new FileInputStream(file);
      BufferedInputStream bis = new BufferedInputStream(fis);

      keystore = CertUtil.loadKeyStore("JKS", file, password);
      certificate = (X509Certificate) keystore.getCertificate(aliasName);
      publicKey = certificate.getPublicKey();
      privateKey = keystore.getKey(aliasName, password.toCharArray());


   }

//   @Test
   public void testEncryptXWSS() throws Exception {

      String encDataId = "EncDataId-1";
      String encKeyId = "#EncKeyId-" + UUID.randomUUID().toString();


      SecurityHeader secureHeader = secureMessage.findWsseSecurityHeaderBlock(true, true);

      EncryptedKeyHeaderBlock encryptedKeyHeaderBlock = new EncryptedKeyHeaderBlock(envelope.getOwnerDocument());

      KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
      keyGenerator.init(128);
      SecretKey sessionKey = keyGenerator.generateKey();
      XMLCipher cipherData = XMLCipher.getInstance(XMLCipher.AES_128);
      cipherData.init(XMLCipher.ENCRYPT_MODE, sessionKey);
      EncryptedData encryptedData = cipherData.encryptData(envelope.getOwnerDocument(), soapBody, true);
      encryptedData.setId(encDataId);
      Element encryptedDataElem = cipherData.martial(encryptedData);

      KeyInfoHeaderBlock dataKeyInfo = new KeyInfoHeaderBlock(envelope.getOwnerDocument());
      SecurityTokenReference dataTokenReference = new SecurityTokenReference(envelope.getOwnerDocument());
      ReferenceElement referenceElement = new DirectReference();
      referenceElement.setAttribute("URI", encKeyId);
      dataTokenReference.setReference(referenceElement);

      dataKeyInfo.addSecurityTokenReference(dataTokenReference);

      encryptedDataElem.appendChild(dataKeyInfo.getAsSoapElement());

      // insert  Encrypted Data to body element
      // 
      System.out.println("First Node : " + soapBody.getFirstChild().getLocalName());
      soapBody.removeChild(soapBody.getFirstChild());
      soapBody.appendChild(encryptedDataElem);

      XMLCipher keyCipher = XMLCipher.getInstance(XMLCipher.RSA_v1dot5);
      keyCipher.init(XMLCipher.WRAP_MODE, certificate.getPublicKey());
      EncryptedKey encryptedKey = keyCipher.encryptKey(envelope.getOwnerDocument(), sessionKey);
      encryptedKey.setId(encKeyId);


      ReferenceList referenceList = keyCipher.createReferenceList(ReferenceList.DATA_REFERENCE);
      encryptedKey.setReferenceList(referenceList);
      Reference dataReference = referenceList.newDataReference("#" + encDataId);
      referenceList.add(dataReference);
      encryptedKey.setReferenceList(referenceList);



      // Create the keyinfo referring to the certificate (<ds:KeyInfo>)
      KeyInfoHeaderBlock keyInfoHeaderBlock = new KeyInfoHeaderBlock(envelope.getOwnerDocument());

      encryptedKey.setKeyInfo(keyInfoHeaderBlock.getKeyInfo());
      Element encryptedKeyElem = keyCipher.martial(encryptedKey);



      // Create block (<wsse:SecurityTokenReference>) 
      SecurityTokenReference securityTokenReference = new SecurityTokenReference(envelope.getOwnerDocument());
      //  encryptedKeyHeaderBlock.setEncryptionMethod("http://www.w3.org/2001/04/xmlenc#rsa-1_5");
      ReferenceListHeaderBlock refListHeaderBlock = new ReferenceListHeaderBlock(envelope.getOwnerDocument());



      X509IssuerSerial x509IssuerSerial = new X509IssuerSerial(envelope.getOwnerDocument(), certificate);
      // Insert (<ds:X509Data>) to (<wsse:SecurityTokenReference>) tag
      securityTokenReference.setReference(x509IssuerSerial);

      keyInfoHeaderBlock.addSecurityTokenReference(securityTokenReference);
      encryptedKeyHeaderBlock.setReferenceList(refListHeaderBlock);
      secureHeader.appendChild(encryptedKeyElem);
      System.out.println("Soap Message : " + XMLUtils.prettyPrint(soapMessage));

   }

   @Test
   public void encryptXWSS() throws Exception {
      //String keyIndentifierType = "Binary Security Token";
      String keyIndentifierType = "Issuer Name and Serial Number";
      String symmetricEncAlg = "http://www.w3.org/2001/04/xmlenc#tripledes-cbc";
      String keyEncAlg = "http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p";

      String encDataId = "EncDataId-1";
      String encKeyId = "#EncKeyId-" + UUID.randomUUID().toString();

      SecurityHeader secureHeader = secureMessage.findWsseSecurityHeaderBlock(true, true);
      EncryptedKeyHeaderBlock encryptedKeyHeaderBlock = new EncryptedKeyHeaderBlock(envelope.getOwnerDocument());

      KeyGenerator keyGenerator = null;
      XMLCipher cipherData = null;
      if (symmetricEncAlg.equals(XMLCipher.TRIPLEDES)) {
         keyGenerator = KeyGenerator.getInstance("DESede");
         cipherData = XMLCipher.getInstance(XMLCipher.TRIPLEDES);
      } else if (symmetricEncAlg.equals(XMLCipher.AES_192)) {
         keyGenerator = KeyGenerator.getInstance("AES");
         keyGenerator.init(192);
         cipherData = XMLCipher.getInstance(XMLCipher.AES_192);
      } else if (symmetricEncAlg.equals(XMLCipher.AES_256)) {
         keyGenerator = KeyGenerator.getInstance("AES");
         keyGenerator.init(256);
         cipherData = XMLCipher.getInstance(XMLCipher.AES_256);
      } else {
         keyGenerator = KeyGenerator.getInstance("AES");
         keyGenerator.init(128);
         cipherData = XMLCipher.getInstance(XMLCipher.AES_128);
      }

      SecretKey sessionKey = keyGenerator.generateKey();

      cipherData.init(XMLCipher.ENCRYPT_MODE, sessionKey);
      EncryptedData encryptedData = cipherData.encryptData(envelope.getOwnerDocument(), soapBody, true);
      encryptedData.setId(encDataId);
      Element encryptedDataElem = cipherData.martial(encryptedData);

      /*
      KeyInfoHeaderBlock dataKeyInfo = new KeyInfoHeaderBlock(envelope.getOwnerDocument());
      SecurityTokenReference dataSecTokenReference = new SecurityTokenReference(envelope.getOwnerDocument());
      ReferenceElement referenceElement = new DirectReference();
      referenceElement.setAttribute("URI", encKeyId);
      dataSecTokenReference.setReference(referenceElement);
      dataKeyInfo.addSecurityTokenReference(dataSecTokenReference);
      
      XMLUtils.printElement(dataKeyInfo.getAsSoapElement());
      encryptedDataElem.insertBefore(dataKeyInfo.getAsSoapElement(), encryptedDataElem.getFirstChild());
      
       */

      /*    */


      SecurityTokenReference dataSecTokenReference = new SecurityTokenReference(envelope.getOwnerDocument());
      ReferenceElement referenceElement = new DirectReference();
      referenceElement.setAttribute("URI", encKeyId);
      dataSecTokenReference.setReference(referenceElement);
      XMLUtils.printElement(dataSecTokenReference.getAsSoapElement());
      Element dataKeyInfo = XMLUtil.createElement(envelope.getOwnerDocument(), "KeyInfo", "http://www.w3.org/2000/09/xmldsig#", "ds");
      dataKeyInfo.appendChild(dataSecTokenReference.getAsSoapElement());
      XMLUtils.printElement(dataKeyInfo);
      encryptedDataElem.insertBefore(dataKeyInfo, encryptedDataElem.getFirstChild());


      // insert  Encrypted Data to body element
      // 
      soapBody.removeChild(soapBody.getFirstChild());
      soapBody.appendChild(encryptedDataElem);


      XMLCipher keyCipher = null;
      if (keyEncAlg.equals(XMLCipher.RSA_v1dot5)) {
         keyCipher = XMLCipher.getInstance(XMLCipher.RSA_v1dot5);
      } else {
         keyCipher = XMLCipher.getInstance(XMLCipher.RSA_OAEP);
      }

      keyCipher.init(XMLCipher.WRAP_MODE, certificate.getPublicKey());
      EncryptedKey encryptedKey = keyCipher.encryptKey(envelope.getOwnerDocument(), sessionKey);
      encryptedKey.setId(encKeyId);


      ReferenceList referenceList = keyCipher.createReferenceList(ReferenceList.DATA_REFERENCE);
      encryptedKey.setReferenceList(referenceList);
      Reference dataReference = referenceList.newDataReference("#" + encDataId);
      referenceList.add(dataReference);
      encryptedKey.setReferenceList(referenceList);


      // Create the keyinfo referring to the certificate (<ds:KeyInfo>)
      KeyInfoHeaderBlock keyInfoHeaderBlock = new KeyInfoHeaderBlock(envelope.getOwnerDocument());

      encryptedKey.setKeyInfo(keyInfoHeaderBlock.getKeyInfo());
      Element encryptedKeyElem = keyCipher.martial(encryptedKey);

      // Create block (<wsse:SecurityTokenReference>) 
      SecurityTokenReference securityTokenReference = new SecurityTokenReference(envelope.getOwnerDocument());

      ReferenceListHeaderBlock refListHeaderBlock = new ReferenceListHeaderBlock(envelope.getOwnerDocument());

      if (SoapConstants.BINARY_SEC_TOKEN.equals(keyIndentifierType)) {
         BinarySecurityTokenType kit = new BinarySecurityTokenType();
         securityTokenReference = kit.getKeyIndentifierType(securityTokenReference, soapHeader.getOwnerDocument(), certificate);
         secureHeader.insertHeaderBlock(kit.getX509SecurityToken());

      }
      if (SoapConstants.ISSUER_NAME.equals(keyIndentifierType)) {
         KeyIndentifierType kit = new IssuerNameType();
         securityTokenReference = kit.getKeyIndentifierType(securityTokenReference, envelope.getOwnerDocument(), certificate);
      }
      if (SoapConstants.SUBJECT_KEY_INDENTIFIER.equals(keyIndentifierType)) {
         KeyIndentifierType kit = new SubjectkeyIdentifierType();
         securityTokenReference = kit.getKeyIndentifierType(securityTokenReference, envelope.getOwnerDocument(), certificate);
      }
      if (SoapConstants.X509_CERTIFICATE.equals(keyIndentifierType)) {
         KeyIndentifierType kit = new X509CertificateType();
         securityTokenReference = kit.getKeyIndentifierType(securityTokenReference, envelope.getOwnerDocument(), certificate);

      }

      keyInfoHeaderBlock.addSecurityTokenReference(securityTokenReference);
      encryptedKeyHeaderBlock.setReferenceList(refListHeaderBlock);
      secureHeader.appendChild(encryptedKeyElem);

      soapResponse = soapMessage;
      // System.out.println("Soap Message : " + XMLUtils.prettyPrint(soapMessage));


      String message = XMLUtils.prettyPrint(soapMessage);
      System.out.println("Response Message : " + message);
      //     Element encryptedPayloadElem = XMLUtil.toDOMDocument(message).getDocumentElement();


      System.out.println("**********************************************");
      System.out.println("************  Decryption Parts **************");
      System.out.println("**********************************************");

      SOAPEnvelope resEnvelope = soapMessage.getSOAPPart().getEnvelope();
      SecurableSoapMessage secResMessage = new SecurableSoapMessage(soapMessage);
      SecurityHeader secResHeader = secResMessage.findSecurityHeader();

      QName encQName = new QName("http://www.w3.org/2001/04/xmlenc#", "EncryptedKey");

      List<Element> encryptedKeyElems = XMLUtil.getElementsByTagNameNS1(secResHeader,
              "http://www.w3.org/2001/04/xmlenc#", "EncryptedKey");


      XPath xpath = XPathFactory.newInstance().newXPath();
      XPathExpression expr = xpath.compile("/*/*/*/*/*/node()");
      Object result = expr.evaluate(secResMessage.getSOAPHeader().getOwnerDocument(), XPathConstants.NODESET);

      SecurityTokenReference secTokenRef = null;
      NodeList nodes = (NodeList) result;
      System.out.println("Length : " + nodes.getLength());
      for (int i = 0; i < nodes.getLength(); i++) {
         // System.out.println(nodes.item(i).getNodeType());
         // System.out.println("Prefix : " + nodes.item(i).getPrefix());
         // System.out.println("Namespace URI : " + nodes.item(i).getNamespaceURI());
         System.out.println("Node Name : " + nodes.item(i).getNodeName());
         // System.out.println("Local Name: " + nodes.item(i).getLocalName());
         // XMLUtils.printocument(nodes.item(i).getOwnerDocument(), System.out);
         System.out.println("");
         Element ex = null;
         if (nodes.item(i).getNodeName().equals("wsse:SecurityTokenReference")) {
            ex = (Element) nodes.item(i);
         }
         if (ex != null) {
            System.out.println("SecurityTokenReference Value : " + ex.getTagName());
            secTokenRef = new SecurityTokenReference(XMLUtil.convertToSoapElement(ex.getOwnerDocument(), ex));
         }
      }

      if (secTokenRef != null) {
         System.out.println("SecurityTokenReference............... : ");
         ReferenceElement refElement = secTokenRef.getReference();

         if (refElement != null) {
            System.out.println("ReferenceElement..............");
            if (refElement instanceof X509IssuerSerial) {
               System.out.println("X509IssuerSerial...............");

               X509IssuerSerial X509IssuerSerial = (X509IssuerSerial) refElement;
               //      X509IssuerSerial.get
               System.out.println("getSerialNumber :" + X509IssuerSerial.getSerialNumber());
               System.out.println("This Cert : " + certificate.getPublicKey().getEncoded());


            }
            if (refElement instanceof X509ThumbPrintIdentifier) {
               System.out.println("X509ThumbPrintIdentifier...............");
            }
            if (refElement instanceof X509ThumbPrintIdentifier) {
               System.out.println("X509ThumbPrintIdentifier...............");
            }
            if (refElement instanceof DirectReference) {
               System.out.println("X509SecurityToken...............");
            }
         }


      }


      /*
      for (Element encKeyElem : encryptedKeyElems) {
      XMLCipher xmlCipher = XMLCipher.getInstance();
      xmlCipher.init(XMLCipher.DECRYPT_MODE, null);
      EncryptedKey encKey = xmlCipher.loadEncryptedKey(encKeyElem);
      
      //System.out.println("Tag Name : " + encKeyElem.getTagName());
      
      KeyInfo keyInfo = encKey.getKeyInfo();
      
      // XMLUtils.printocument(keyInfo1.getDocument(), System.out);
      //    KeyInfoHeaderBlock resKeyInfo = new KeyInfoHeaderBlock(keyInfo);
      System.out.println("getBaseURI : " + encKey.getKeyInfo().getBaseURI());
      System.out.println("getId : " + encKey.getKeyInfo().getId());
      System.out.println("getFirstChild : " + encKey.getKeyInfo().getTextFromTextChild());
      System.out.println("getBaseLocalName : " + encKey.getKeyInfo().getBaseLocalName());
      System.out.println("getDocument : " + encKey.getKeyInfo().getDocument());
      
      //   if (resKeyInfo.getSecurityTokenReference(0) != null) {
      //        System.out.println("Id reference: " + resKeyInfo.getSecurityTokenReference(0).getId());
      //    }
      
      
      //  System.out.println(result);
      
      
      
      X509Data x509Data = keyInfo.itemX509Data(0);
      //   byte[] x509DataSki = x509Data.itemSKI(0).getSKIBytes();
      //   byte[] decryptCertSki = XMLX509SKI.getSKIBytesFromCert(certificate);
      //   boolean skiMatches = Arrays.equals(x509DataSki, decryptCertSki);
      
      }
      
       */








   }

   //  @Test
   public void decryptXWSS() throws Exception {
   }
}
