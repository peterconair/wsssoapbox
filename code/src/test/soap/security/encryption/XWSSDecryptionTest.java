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
import com.sun.org.apache.xml.internal.security.keys.keyresolver.KeyResolverException;
import com.sun.org.apache.xml.internal.serialize.DOMSerializer;
import com.sun.org.apache.xml.internal.serialize.Method;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import com.sun.xml.wss.XWSSecurityException;
import com.sun.xml.wss.core.EncryptedKeyHeaderBlock;
import com.sun.xml.wss.core.KeyInfoHeaderBlock;
import com.sun.xml.wss.core.ReferenceElement;
import com.sun.xml.wss.core.ReferenceListHeaderBlock;
import com.sun.xml.wss.core.SecurityHeader;
import com.sun.xml.wss.core.SecurityTokenReference;
import com.sun.xml.wss.core.X509SecurityToken;
import com.sun.xml.wss.core.reference.DirectReference;
import com.sun.xml.wss.core.reference.X509IssuerSerial;
import com.sun.xml.wss.impl.MessageConstants;
import com.sun.xml.wss.impl.SecurableSoapMessage;
import com.sun.xml.wss.impl.SecurityTokenException;
import com.sun.xml.wss.impl.misc.Base64;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.Key;
import java.security.KeyStore;
import java.security.PublicKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.soap.SOAPBody;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.wsssoapbox.bean.model.soap.SoapPartBean;
import org.wsssoapbox.soap.support.SoapConstants;
import org.wsssoapbox.soap.support.SoapCreator;
import org.wsssoapbox.soap.security.CertUtil;
import org.wsssoapbox.soap.security.crypto.SoapCrypto;
import org.wsssoapbox.soap.security.crypto.SoapDecryption;
import org.wsssoapbox.soap.security.crypto.SoapEncryption;
import org.wsssoapbox.soap.security.cypto.xpath.SoapDecryptionXPath;
import org.wsssoapbox.soap.security.support.BinarySecurityTokenType;
import org.wsssoapbox.soap.security.support.IssuerNameType;
import org.wsssoapbox.soap.security.support.KeyIndentifierType;
import org.wsssoapbox.soap.security.support.SubjectkeyIdentifierType;
import org.wsssoapbox.soap.security.support.X509CertificateType;
import org.wsssoapbox.xml.util.SimpleNamespaceContext;
import org.wsssoapbox.xml.util.XMLUtils;

/**
 *
 * @author Peter
 */
public class XWSSDecryptionTest {

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
   KeyStore severKeyStore;
   Key serverPrivateKey;
   PublicKey serverPublicKey;
   String serverFile;
   X509Certificate serverCertificate;
   String serverPassword = "123456";
   String serverAliasName = "client_cert";
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
   String encStrMessage;

   public XWSSDecryptionTest() {
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
      serverFile = "F:/Develope/sourecode/netbeans/Web_Service/Prodiver/ws-payment-btoken/src/main/webapp/WEB-INF/config/certificates/server/server.jks";

      FileInputStream fis = new FileInputStream(file);
      BufferedInputStream bis = new BufferedInputStream(fis);

      keystore = CertUtil.loadKeyStore("JKS", file, password);
      certificate = (X509Certificate) keystore.getCertificate(aliasName);
      publicKey = certificate.getPublicKey();
      privateKey = keystore.getKey("client_keystore", password.toCharArray());

      severKeyStore = CertUtil.loadKeyStore("JKS", serverFile, serverPassword);
      serverCertificate = (X509Certificate) severKeyStore.getCertificate(serverAliasName);
      serverPublicKey = serverCertificate.getPublicKey();
      serverPrivateKey = severKeyStore.getKey("server_keystore", serverPassword.toCharArray());
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

  // @Test
   public void encryptXWSS() throws Exception {
      //   String keyIndentifierType = "Binary Security Token";
//      String keyIndentifierType = "Issuer Name and Serial Number";
      //    String keyIndentifierType = "X509 Certificate";
      String keyIndentifierType = "Subjectkey Identifier";

      String symmetricEncAlg = "http://www.w3.org/2001/04/xmlenc#tripledes-cbc";
      String keyEncAlg = "http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p";

      String encDataId = "EncDataId-2";
      // String encKeyId = "#EncKeyId-" + UUID.randomUUID().toString();
      String encKeyId = "#EncKeyId-1";

      SecurityHeader secureHeader = secureMessage.findWsseSecurityHeaderBlock(true, true);


      KeyGenerator keyGenerator = null;
      XMLCipher cipherData = null;
      // Select Symmetric encrypt algorithms for encrypt the data.
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

      // create session key for encrypt the data
      SecretKey sessionKey = keyGenerator.generateKey();
      cipherData.init(XMLCipher.ENCRYPT_MODE, sessionKey);
      // Do the encrypt soapBody Content (true) , Element (false)
      EncryptedData encryptedData = cipherData.encryptData(envelope.getOwnerDocument(), soapBody, true);
      encryptedData.setId(encDataId);
      Element encryptedDataElem = cipherData.martial(encryptedData);

      KeyInfoHeaderBlock dataKeyInfo = new KeyInfoHeaderBlock(envelope.getOwnerDocument());
      SecurityTokenReference dataTokenReference = new SecurityTokenReference(envelope.getOwnerDocument());
      ReferenceElement referenceElement = new DirectReference();
      referenceElement.setAttribute("URI", encKeyId);
      dataTokenReference.setReference(referenceElement);

      dataKeyInfo.addSecurityTokenReference(dataTokenReference);
      // Insert KeyInfo to EncryptedData
      encryptedDataElem.insertBefore(dataKeyInfo.getAsSoapElement(), encryptedDataElem.getFirstChild());


      // Remove SoapBody Content. 
      soapBody.removeChild(soapBody.getFirstChild());
      // insert  Encrypted Data to body element
      soapBody.appendChild(encryptedDataElem);


      // Create an XMLCipher 
      //The encryption is performed  The data encryption algorithm is specified when getting an XMLCipher instance. 
      //The  XMLCipher instance must be initialised with the key to encrypt the data, which should be the session key generated
      XMLCipher keyCipher = null;
      // Select algorithm for encrypt session key 
      if (keyEncAlg.equals(XMLCipher.RSA_v1dot5)) {
         keyCipher = XMLCipher.getInstance(XMLCipher.RSA_v1dot5);
      } else {
         keyCipher = XMLCipher.getInstance(XMLCipher.RSA_OAEP);
      }

      // creates an XMLCipher, initialised for key wrapping using the public key of the certificate, and encrypts the session key
      keyCipher.init(XMLCipher.WRAP_MODE, certificate.getPublicKey());
      EncryptedKey encryptedKey = keyCipher.encryptKey(envelope.getOwnerDocument(), sessionKey);
      encryptedKey.setId(encKeyId);

      //Create ReferenceList  and add to EncryptedKey Each  EncryptedKey must contain a ReferenceList  and have a single 
      //DataReference  with the URI attribute set to the  Id attribute of the encrypted data 
      ReferenceList referenceList = keyCipher.createReferenceList(ReferenceList.DATA_REFERENCE);
      encryptedKey.setReferenceList(referenceList);
      // Reference to target EncryptedData using Id. 
      Reference dataReference = referenceList.newDataReference("#" + encDataId);
      referenceList.add(dataReference);
      encryptedKey.setReferenceList(referenceList);


      // Create the keyinfo referring to the certificate (<ds:KeyInfo>)
      KeyInfoHeaderBlock keyInfoHeaderBlock = new KeyInfoHeaderBlock(envelope.getOwnerDocument());

      // Set SUN KeyInfoHeaderBlock to Apache KeyInfo
      encryptedKey.setKeyInfo(keyInfoHeaderBlock.getKeyInfo());
      // Mashall 
      Element encryptedKeyElem = keyCipher.martial(encryptedKey);

//      System.out.println("YYYYYYYYYYYYYYYYY : " + encryptedKey.getKeyInfo().getSecretKey().getEncoded());

      // Create block (<wsse:SecurityTokenReference>) 
      SecurityTokenReference securityTokenReference = new SecurityTokenReference(envelope.getOwnerDocument());

      // ReferenceListHeaderBlock refListHeaderBlock = new ReferenceListHeaderBlock(envelope.getOwnerDocument());

      if (SoapConstants.BINARY_SEC_TOKEN.equals(keyIndentifierType)) {
         BinarySecurityTokenType kit = new BinarySecurityTokenType();
         securityTokenReference = kit.getKeyIndentifierType(securityTokenReference, soapHeader.getOwnerDocument(), certificate);
         // insert <wsse:BinarySecurityTokent> into <wsse:Security> only Key Identifier Type are Binary Security Token
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

      // Insert  <wsse:Reference> or <> or <> or <>  <wsse:SecurityTokenReference> Depend on Key Indetifier Type 
      keyInfoHeaderBlock.addSecurityTokenReference(securityTokenReference);

      // Insert <xenc:ReferenceList>  to <xenc:EncryptedKey> 
      // encryptedKeyHeaderBlock.setReferenceList(refListHeaderBlock);
      // Insert EncryptedKey into Header (<wsse:Security>)
      secureHeader.appendChild(encryptedKeyElem);


      // System.out.println("Soap Message : " + XMLUtils.prettyPrint(soapMessage));
      //  String message = XMLUtils.prettyPrint(soapMessage);
      //  System.out.println("Response Message : " + message);
      //     Element encryptedPayloadElem = XMLUtil.toDOMDocument(message).getDocumentElement();

      //   String decMessage = decryptSoap(soapMessage, serverPrivateKey, true);


      SOAPMessage soap = MessageFactory.newInstance().createMessage();
      soap = SoapCreator.createSOAPMessageFromString(strMessage);

      // System.out.println(XMLUtils.prettyPrint(soap));

      SoapDecryptionXPath xpath = new SoapDecryptionXPath();
      List<SoapPartBean> parts = xpath.findAllSoapPartsInBody(soap);

      SoapPartBean soapPartBean = new SoapPartBean();
      SoapPartBean soapPartBean1 = new SoapPartBean();


      soapPartBean = parts.get(1);
      soapPartBean1 = parts.get(2);
      soapPartBean.setContentId("Id-1");
      soapPartBean1.setContentId("Id-2");
      soapPartBean.setContent(true);
      soapPartBean1.setContent(true);
      List<SoapPartBean> soapParts = new ArrayList<SoapPartBean>();
      soapParts.add(soapPartBean);
      soapParts.add(soapPartBean1);

      SoapCrypto se = new SoapEncryption(encDataId);

      encStrMessage = se.encryptSoap(soapMessage, keyEncAlg, keyIndentifierType, symmetricEncAlg, certificate);
      //encStrMessage = se.encryptSoapParts(strMessage, soapParts, keyEncAlg, keyIndentifierType, symmetricEncAlg, certificate);


      System.out.println(encStrMessage);

      SoapCrypto SoapCrypto = new SoapDecryption();

        String decMessage = SoapCrypto.decryptSoap(encStrMessage, serverPrivateKey, true);
      //System.out.println(decMessage);

   }

   public String decryptSoap(SOAPMessage soapMessage, Key privateKey, boolean content) throws Exception {

      System.out.println("**********************************************");
      System.out.println("************  Decryption Parts **************");
      System.out.println("**********************************************");

      List<EncryptedData> eds = findAllEncrypteDataByDataReferences(soapMessage);
      System.out.println("Founds EncryptedData : " + eds.size() + " : " + "elements ");

      String encAlg = "";
      String decMessage = "";
      for (EncryptedData ed : eds) {

         String Id = ed.getId();
         System.out.println("Encrypted Data Id : " + Id);

         EncryptedKey ek = findEncryptedKeyByEncryptedData(soapMessage, Id);
         encAlg = ed.getEncryptionMethod().getAlgorithm();

         XMLCipher xmlCipher = XMLCipher.getInstance();

         Element el = xmlCipher.martial(ek);
         Element d1 = xmlCipher.martial(ed);

         // XMLUtils.printElement(el);
         // XMLUtils.printElement(d1);

         xmlCipher.init(XMLCipher.UNWRAP_MODE, privateKey);
         Key sKey = xmlCipher.decryptKey(ek, encAlg);

         xmlCipher.init(XMLCipher.DECRYPT_MODE, sKey);

         DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
         DocumentBuilder db = dbf.newDocumentBuilder();
         Document doc = db.newDocument();

         Document d = xmlCipher.doFinal(soapMessage.getSOAPBody().getOwnerDocument(), soapMessage.getSOAPBody(), content);
         decMessage = XMLUtils.prettyPrintDoc(d);
         //  System.out.println(XMLUtils.prettyPrintDoc(d));
      }
      return decMessage;
   }

   public void testDecrypt() throws Exception {
      List<EncryptedData> eds = findEncryptedDatas(soapMessage);

      System.out.println("Size : " + eds.size());

      for (EncryptedData ed : eds) {
         System.out.println("Id :" + ed.getId());
      }

      EncryptedData ed = findEncryptedData(soapMessage, "EncDataId-1");
      if (ed != null) {
         System.out.println("Id :" + ed.getId());
      }


      List<EncryptedKey> eks = findEncryptedKeys(soapMessage);

      System.out.println("Size : " + eds.size());

      for (EncryptedKey ek : eks) {
         System.out.println("Id :" + ek.getId());
      }

      EncryptedKey ek = findEncryptedKey(soapMessage, "#EncKeyId-1");
      if (ek != null) {
         System.out.println("Id :" + ek.getId());
      }

      boolean certMatch = verifyCertificate(soapMessage, serverCertificate);
      System.out.println("Verify Cerfificates Result : " + certMatch);

   }
@Test
   public void testSoapCryptoDAO() throws Exception {
      String enc = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xenc=\"http://www.w3.org/2001/04/xmlenc#\"><SOAP-ENV:Header><wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" SOAP-ENV:mustUnderstand=\"1\"><xenc:EncryptedKey Id=\"EncKeyId-4BD43E393C90557C5F13256137493114\" xmlns:xenc=\"http://www.w3.org/2001/04/xmlenc#\"><xenc:EncryptionMethod Algorithm=\"http://www.w3.org/2001/04/xmlenc#rsa-1_5\"/><ds:KeyInfo xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\">"
+"<wsse:SecurityTokenReference xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\"><ds:X509Data xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\">"
+"<ds:X509IssuerSerial xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\">"
+"<ds:X509IssuerName xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\">CN=Java Tech,OU=Java Department,O=Java Org.,L=Java City,ST=Java State,C=TH</ds:X509IssuerName>"
+"<ds:X509SerialNumber xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\">1324562982</ds:X509SerialNumber>"
+"</ds:X509IssuerSerial>"
+"</ds:X509Data></wsse:SecurityTokenReference>"
+"</ds:KeyInfo><xenc:CipherData><xenc:CipherValue>AIsIlJuKOVxnMhDlTJI0P2P0TIvNelY3zuU5j1nlLq57JxUIcqjRfZISMrKja+LKov//7/re2aVYWPlfmJEq9vv72NLkVVBlS8S1au1GBjD2zJSxC3+scqZOi1FduRfQW07xi6QGCZRkWYOD9Q5R/y3oFHlsXMc4dLm6QYTmSVk=</xenc:CipherValue></xenc:CipherData><xenc:ReferenceList><xenc:DataReference URI=\"#EncDataId-2\"/></xenc:ReferenceList></xenc:EncryptedKey></wsse:Security></SOAP-ENV:Header><SOAP-ENV:Body><xenc:EncryptedData Id=\"EncDataId-2\" Type=\"http://www.w3.org/2001/04/xmlenc#Content\" xmlns:xenc=\"http://www.w3.org/2001/04/xmlenc#\"><xenc:EncryptionMethod Algorithm=\"http://www.w3.org/2001/04/xmlenc#aes128-cbc\" xmlns:xenc=\"http://www.w3.org/2001/04/xmlenc#\"/><ds:KeyInfo xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\">"
+"<wsse:SecurityTokenReference xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\"><wsse:Reference URI=\"#EncKeyId-4BD43E393C90557C5F13256137493114\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\"/></wsse:SecurityTokenReference>"
+"</ds:KeyInfo><xenc:CipherData xmlns:xenc=\"http://www.w3.org/2001/04/xmlenc#\"><xenc:CipherValue xmlns:xenc=\"http://www.w3.org/2001/04/xmlenc#\">m+BAtxPInjLLXEiWoT+GPkyBMT1u0X3npSOrlYsi6w2ZtMCumt8hxtZGESlinKHH2sbiJcKEzN9H"
+"PMWbcwBu+gPCw2TMdAeir2ticbVbibErDugGSRpOU9Em/5lzXGt03huGiZI38cLDWKaOBVg4snXG"
+"iD8ZrGRj/b5aCGBAV6dp/KylTVX/VkwAvAVwjM0cXU/IOiqxOMhfjsoZxQL2Q/C+QfHDukBB8Biz"
+"yUHVFlT+SXPPJXVpE6AjB9GEWOzZmXhMamYYKxk5U3y/2YuXJO7y5Znyn9d9XK4PEwlDWj9JP/Sr"
+"uEEtMaKOtZW6OC1b/2cV9OWzPeAyjtmes6KLdhD/zB1pANRS7K9nC8AJtrXCbTM5+FbeuQZ6+O2W"
+"vV3XbvGjwa4N5kTSJnXcc9r+gpQEQaQVf8swN0zVZ85hDG8Owsh5BsQUb6VuneFf8XCs</xenc:CipherValue></xenc:CipherData></xenc:EncryptedData></SOAP-ENV:Body></SOAP-ENV:Envelope>";
      
      SoapCrypto SoapCrypto = new SoapDecryption();
      String decMessage = SoapCrypto.decryptSoap(enc, serverPrivateKey, true);
      
      System.out.println("Result : "+decMessage);
   }

   public EncryptedKey findEncryptedKeyByEncryptedData(SOAPMessage soapMessage, String encDataId) throws Exception {
      System.out.println("**********************************************************");
      System.out.println("*********  Find EncryptedKey  By EncrypteData ************");
      System.out.println("Finding ... By EncryptedData Id : " + encDataId);
      System.out.println("**********************************************************");

      EncryptedKey ek = null;
      List<EncryptedKey> eks = findEncryptedKeys(soapMessage);

      System.out.println("Size : " + eks.size());
      for (EncryptedKey k : eks) {
         Iterator it = k.getReferenceList().getReferences();
         while (it.hasNext()) {
            Reference ref = (Reference) it.next();
            String id = ref.getURI();
            id = id.replaceFirst("#", "");
            System.out.println("Ref URI : " + id);
            System.out.println("encDataId :" + encDataId);
            if (id.equals(encDataId)) {
               ek = k;
            }
         }
      }
      return ek;
   }

   public EncryptedData findEncryptedDataByEncryptedKey(SOAPMessage soapMessage, String encKeyId) throws Exception {
      System.out.println("**********************************************************");
      System.out.println("**********  Find EncrypteData By EncryptedKey ************");
      System.out.println("Finding ... By  EncryptedKey Id : " + encKeyId);
      System.out.println("**********************************************************");
      EncryptedData ed = null;
      EncryptedKey ek = findEncryptedKey(soapMessage, encKeyId);
      if (ek != null) {
         System.out.println("Id :" + ek.getId());
         System.out.println("Cert : " + ek.getKeyInfo().getX509Certificate());
         Iterator it = ek.getReferenceList().getReferences();
         while (it.hasNext()) {
            Reference ref = (Reference) it.next();
            String id = ref.getURI();
            System.out.println("Ref URI : " + id);
            ed = findEncryptedData(soapMessage, id);
         }
      }
      return ed;
   }

   public List<EncryptedData> findAllEncrypteDataByDataReferences(SOAPMessage soapMessage) throws XPathExpressionException, SOAPException, XMLEncryptionException, KeyResolverException {
      List<EncryptedData> eds = new ArrayList<EncryptedData>();
      EncryptedData ed = null;
      System.out.println("**********************************************************");
      System.out.println("******  Find All EncrypteData By DataReferences URI ******");
      System.out.println("**********************************************************");

      XPath xpath = XPathFactory.newInstance().newXPath();
      NamespaceContext ns = new SimpleNamespaceContext();
      xpath.setNamespaceContext(ns);
      XPathExpression expr = null;
      //find all DataReference in envelope 
      String exprStr1 = "/soapenv:Envelope/soapenv:Header/wsse:Security/xenc:EncryptedKey/xenc:ReferenceList/xenc:DataReference/@URI";
      System.out.println("Using expresion : " + exprStr1);
      expr = xpath.compile(exprStr1);
      Object dataRef = expr.evaluate(soapMessage.getSOAPHeader().getOwnerDocument(), XPathConstants.NODESET);
      NodeList dataNodes = (NodeList) dataRef;
      //    System.out.println("Length : " + nodes.getLength());
      String dataRefURI = "";
      System.out.println("We founds : " + dataNodes.getLength() + " DataReference... ");
      // Operate all DataReference values
      for (int i = 0; i < dataNodes.getLength(); i++) {
         dataRefURI = dataNodes.item(i).getTextContent();
         // remove '#' charactor
         dataRefURI = dataRefURI.substring(1);
         ed = findEncryptedData(soapMessage, dataRefURI);
         eds.add(ed);
      }
      return eds;
   }

   public EncryptedKey findEncryptedKey(SOAPMessage soapMessage, String encKeyId) throws SOAPException, XPathExpressionException, XMLEncryptionException, KeyResolverException {
      EncryptedKey ek = null;

      System.out.println("******************************************");
      System.out.println("*******  Find the encrypted key  *********");
      System.out.println("******************************************");


      XPath xpath = XPathFactory.newInstance().newXPath();
      NamespaceContext ns = new SimpleNamespaceContext();
      xpath.setNamespaceContext(ns);
      XPathExpression expr = null;
      XMLCipher xmlCipher = null;
      Element decryptedKeyElem = null;

      if (soapMessage != null) {
         // find all EncryptedKey nodes
         String exprStr = "//soapenv:Envelope/soapenv:Header/wsse:Security/xenc:EncryptedKey[@Id='" + encKeyId + "']";
         System.out.println("Using expresion : " + exprStr);
         expr = xpath.compile(exprStr);
         Object encResult = expr.evaluate(soapMessage.getSOAPHeader().getOwnerDocument(), XPathConstants.NODESET);
         NodeList encNodes = (NodeList) encResult;

         System.out.println("Found " + encNodes.getLength() + " <xenc:EncryptedKey> elements.");
         for (int i = 0; i < encNodes.getLength(); i++) {
            System.out.println("Node Name : " + encNodes.item(i).getNodeName());
            // Assign xenc:EncryptedKey that found to element. 
            decryptedKeyElem = (Element) encNodes.item(i);
            //To unmarshal an  xenc:EncryptedKey  Element to an EncryptedKey 
            xmlCipher = XMLCipher.getInstance();
            xmlCipher.init(XMLCipher.DECRYPT_MODE, null);
            ek = xmlCipher.loadEncryptedKey(decryptedKeyElem);
            ek.getKeyInfo().getPublicKey();
         }
      }
      return ek;
   }

   public List<EncryptedKey> findEncryptedKeys(SOAPMessage soapMessage) throws SOAPException, XPathExpressionException, XMLEncryptionException, KeyResolverException {
      List<EncryptedKey> eks = new ArrayList<EncryptedKey>();
      EncryptedKey ek = null;

      System.out.println("******************************************");
      System.out.println("*******  Find the encrypted key  *********");
      System.out.println("******************************************");

      XPath xpath = XPathFactory.newInstance().newXPath();
      NamespaceContext ns = new SimpleNamespaceContext();
      xpath.setNamespaceContext(ns);
      XPathExpression expr = null;
      XMLCipher xmlCipher = null;
      Element decryptedKeyElem = null;

      if (soapMessage != null) {
         // find all EncryptedKey nodes
         String exprStr = "//soapenv:Envelope/soapenv:Header/wsse:Security/*";
         System.out.println("Using expresion : " + exprStr);
         expr = xpath.compile(exprStr);

         Object encResult = expr.evaluate(soapMessage.getSOAPHeader().getOwnerDocument(), XPathConstants.NODESET);
         NodeList encNodes = (NodeList) encResult;

         System.out.println("Found " + encNodes.getLength() + " <xenc:EncryptedKey> elements.");

         for (int i = 0; i < encNodes.getLength(); i++) {
            System.out.println("Node Name : " + encNodes.item(i).getNodeName());

            if (encNodes.item(i).getNodeName().equals("xenc:EncryptedKey")) {
               // Assign xenc:EncryptedKey that found to element. 
               decryptedKeyElem = (Element) encNodes.item(i);
               //To unmarshal an  xenc:EncryptedKey  Element to an EncryptedKey 
               xmlCipher = XMLCipher.getInstance();
               xmlCipher.init(XMLCipher.DECRYPT_MODE, null);
               ek = xmlCipher.loadEncryptedKey(decryptedKeyElem);
               ek.getKeyInfo().getPublicKey();
               eks.add(ek);
            }
         }
      }
      return eks;
   }

   public EncryptedData findEncryptedData(SOAPMessage soapMessage, String encDataId) throws SOAPException, XPathExpressionException, XMLEncryptionException {
      EncryptedData ed = null;

      System.out.println("*************************************************");
      System.out.println("************ Find encrypted data ****************");
      System.out.println("*************************************************");
      XPath xpath = XPathFactory.newInstance().newXPath();
      NamespaceContext ns = new SimpleNamespaceContext();
      xpath.setNamespaceContext(ns);
      XPathExpression expr = null;
      XMLCipher xmlCipher = null;
      Element encryptedDataElem = null;
      //find all DataReference in envelope 
      if (encDataId.startsWith("#")) {
         encDataId = encDataId.substring(1);
      }
      String exprStr = "//xenc:EncryptedData[@Id='" + encDataId + "']";
      System.out.println("Using expresion : " + exprStr);
      expr = xpath.compile(exprStr);
      Object dataRef = expr.evaluate(soapMessage.getSOAPPart().getEnvelope().getOwnerDocument(), XPathConstants.NODESET);

      NodeList dataNodes = (NodeList) dataRef;
      //    System.out.println("Length : " + nodes.getLength());

      System.out.println("We founds : " + dataNodes.getLength() + " DataReference... ");
      // Operate all DataReference values
      for (int i = 0; i < dataNodes.getLength(); i++) {
         // Assign xenc:EncryptedKey that found to element. 
         encryptedDataElem = (Element) dataNodes.item(i);
         //To unmarshal an  xenc:EncryptedKey  Element to an EncryptedKey 
         xmlCipher = XMLCipher.getInstance();
         xmlCipher.init(XMLCipher.DECRYPT_MODE, null);
         ed = xmlCipher.loadEncryptedData(envelope.getOwnerDocument(), encryptedDataElem);
      }
      return ed;
   }

   public List<EncryptedData> findEncryptedDatas(SOAPMessage soapMessage) throws SOAPException, XPathExpressionException, XMLEncryptionException {
      EncryptedData ed = null;
      List<EncryptedData> eds = new ArrayList<EncryptedData>();

      System.out.println("*************************************************");
      System.out.println("************ Find encrypted data ****************");
      System.out.println("*************************************************");
      XPath xpath = XPathFactory.newInstance().newXPath();
      NamespaceContext ns = new SimpleNamespaceContext();
      xpath.setNamespaceContext(ns);
      XPathExpression expr = null;
      XMLCipher xmlCipher = null;
      Element encryptedDataElem = null;
      //find all DataReference in envelope 
      String exprStr = "//xenc:EncryptedData";
      System.out.println("Using expresion : " + exprStr);
      expr = xpath.compile(exprStr);
      Object dataRef = expr.evaluate(soapMessage.getSOAPPart().getEnvelope().getOwnerDocument(), XPathConstants.NODESET);
      NodeList dataNodes = (NodeList) dataRef;
      //    System.out.println("Length : " + nodes.getLength());
      System.out.println("We founds : " + dataNodes.getLength() + " DataReference... ");
      // Operate all DataReference values
      for (int i = 0; i < dataNodes.getLength(); i++) {
         // Assign xenc:EncryptedKey that found to element. 
         encryptedDataElem = (Element) dataNodes.item(i);
         //To unmarshal an  xenc:EncryptedKey  Element to an EncryptedKey 
         xmlCipher = XMLCipher.getInstance();
         xmlCipher.init(XMLCipher.DECRYPT_MODE, null);
         ed = xmlCipher.loadEncryptedData(envelope.getOwnerDocument(), encryptedDataElem);
         eds.add(ed);
      }
      return eds;
   }

   public boolean verifyCertificate(SOAPMessage soapMessage, X509Certificate certificate) throws XPathExpressionException, SOAPException, SecurityTokenException, IOException, CertificateEncodingException, XWSSecurityException {
      System.out.println("*************************************************");
      System.out.println("************ Verify Certificate ****************");
      System.out.println("*************************************************");
      XPath xpath = XPathFactory.newInstance().newXPath();
      NamespaceContext ns = new SimpleNamespaceContext();
      xpath.setNamespaceContext(ns);
      XPathExpression expr = null;

      // find all Key Identifier Type 
      String exprStr1 = "/soapenv:Envelope/soapenv:Header/wsse:Security/xenc:EncryptedKey/ds:KeyInfo/wsse:SecurityTokenReference/*";
      System.out.println("Using expresion : " + exprStr1);
      expr = xpath.compile(exprStr1);
      Object result = expr.evaluate(soapMessage.getSOAPHeader().getOwnerDocument(), XPathConstants.NODESET);
      NodeList nodes = (NodeList) result;
      String nodeName = "";
      System.out.println("Length : " + nodes.getLength());

      for (int i = 0; i < nodes.getLength(); i++) {

         System.out.println("Node Name : " + nodes.item(i).getNodeName());
         nodeName = nodes.item(i).getNodeName();
         System.out.println("");

         // Subjectkey Identifier and // X509 Certificate 
         if (nodeName.equals("wsse:KeyIdentifier")) {
            System.out.println("KeyIdentifier...............");
            // expr = xpath.compile("/*/*/*/*/*/*/node()");
            String exprStr2 = "/soapenv:Envelope/soapenv:Header/wsse:Security/xenc:EncryptedKey/ds:KeyInfo/wsse:SecurityTokenReference/node()";
            System.out.println("Using expresion : " + exprStr2);
            expr = xpath.compile(exprStr2);
            String subKeyIndetifier = "";
            Object resultRef = expr.evaluate(soapMessage.getSOAPHeader().getOwnerDocument(), XPathConstants.NODESET);
            NodeList refNodes = (NodeList) resultRef;
            //   System.out.println("Length : " + refNodes.getLength());
            for (int j = 0; j < refNodes.getLength(); j++) {
               if (refNodes.item(j).getNodeName().equals("wsse:KeyIdentifier")) {
                  subKeyIndetifier = refNodes.item(j).getTextContent();
                  break;
               }
            }

            String idTypeValue = "";
            String toolSubKeyId = "";

            expr = xpath.compile("/soapenv:Envelope/soapenv:Header/wsse:Security/xenc:EncryptedKey/ds:KeyInfo/wsse:SecurityTokenReference/wsse:KeyIdentifier/@ValueType");
            Object idType = expr.evaluate(soapMessage.getSOAPHeader().getOwnerDocument(), XPathConstants.NODESET);
            NodeList idTypeNodes = (NodeList) idType;
            for (int j = 0; j < idTypeNodes.getLength(); j++) {
               idTypeValue = idTypeNodes.item(j).getTextContent();
            }

            System.out.println("Value Type : " + idTypeValue);

            // Subjectkey Identifier
            if (idTypeValue.equals(MessageConstants.X509SubjectKeyIdentifier_NS)) {
               System.out.println("Subjectkey Identifier............");
               toolSubKeyId = CertUtil.getSubjectKeyIdentity(certificate);
            } else { // X509 Certificate 
               System.out.println("X509 Certificate............");
               toolSubKeyId = Base64.encode(certificate.getEncoded());
            }

            boolean certMatch = Arrays.equals(toolSubKeyId.getBytes(), subKeyIndetifier.getBytes());
            System.out.println("*******************************");
            System.out.println("Subject Key Identifier : " + subKeyIndetifier);
            System.out.println("Tool Subject Key Indentifier : " + toolSubKeyId);
            System.out.println("Comparation result : " + certMatch);
            System.out.println("*******************************");

            return certMatch;
         }


         // Issuer Name and Serial Number
         if (nodeName.equals("ds:X509Data")) {
            System.out.println("Issuer Name and Serial Number.................");

            expr = xpath.compile("/*/*/*/*/*/*/*/*/node()");

            String issuerName = "";
            String serialNumber = "";
            Object resultRef = expr.evaluate(soapMessage.getSOAPHeader().getOwnerDocument(), XPathConstants.NODESET);
            NodeList refNodes = (NodeList) resultRef;
            //  System.out.println("Length : " + refNodes.getLength());
            for (int j = 0; j < refNodes.getLength(); j++) {
               if (refNodes.item(j).getNodeName().equals("ds:X509IssuerName")) {
                  System.out.println("X509IssuerName.............");
                  issuerName = refNodes.item(j).getTextContent();
               }
               if (refNodes.item(j).getNodeName().equals("ds:X509SerialNumber")) {
                  System.out.println("X509SerialNumber.............");
                  serialNumber = refNodes.item(j).getTextContent();
                  break;
               }
            }

            X509IssuerSerial x509IssuerSerial = new X509IssuerSerial(soapMessage.getSOAPHeader().getOwnerDocument(), certificate);

            String toolIssuerName = x509IssuerSerial.getIssuerName();
            String toolSerialNumber = x509IssuerSerial.getSerialNumber().toString();

            boolean certIssuerMatch = Arrays.equals(toolIssuerName.getBytes(), issuerName.getBytes());
            boolean certSerialMatch = Arrays.equals(toolSerialNumber.getBytes(), serialNumber.getBytes());
            boolean certMatch = certIssuerMatch && certSerialMatch ? true : false;


            System.out.println("*******************************");
            System.out.println("Issuer Name : " + issuerName);
            System.out.println("Serial Number : " + serialNumber);

            System.out.println("Tool Issuer Name : " + toolIssuerName);
            System.out.println("Tool Serial Number : " + toolSerialNumber);
            System.out.println("Comparation result : " + certMatch);
            System.out.println("*******************************");
            return certMatch;

         }
         if (nodeName.equals("wsse:Reference")) {
            System.out.println("Binary Security Token.................");
            String certId = "";
            expr = xpath.compile("/soapenv:Envelope/soapenv:Header/wsse:Security/xenc:EncryptedKey/ds:KeyInfo/wsse:SecurityTokenReference/wsse:Reference/@URI");
            System.out.println("Base URI : " + nodes.item(i).getLocalName());

            String binValueText = "";
            Object resultRef = expr.evaluate(soapMessage.getSOAPHeader().getOwnerDocument(), XPathConstants.NODESET);
            NodeList refNodes = (NodeList) resultRef;
            // System.out.println("Length : " + refNodes.getLength());
            for (int j = 0; j < refNodes.getLength(); j++) {

               certId = refNodes.item(j).getTextContent();

               if (!certId.equals("")) {
                  System.out.println("Getting Binary Token.............");
                  expr = xpath.compile("/soapenv:Envelope/soapenv:Header/wsse:Security/wsse:BinarySecurityToken/@wsu:Id");
                  Object binRef = expr.evaluate(soapMessage.getSOAPHeader().getOwnerDocument(), XPathConstants.NODESET);
                  NodeList binNodes = (NodeList) binRef;
                  // System.out.println("Length : " + binNodes.getLength());
                  for (int k = 0; k < binNodes.getLength(); k++) {
                     System.out.println("Binary Token :" + binNodes.item(k));

                     expr = xpath.compile("/soapenv:Envelope/soapenv:Header/wsse:Security/wsse:BinarySecurityToken/text()");
                     Object binValueRef = expr.evaluate(soapMessage.getSOAPHeader().getOwnerDocument(), XPathConstants.NODESET);
                     NodeList binValueNodes = (NodeList) binValueRef;
                     for (int l = 0; l < binValueNodes.getLength(); l++) {
                        binValueText = binValueNodes.item(l).getTextContent();
                     }
                  }
               }
            }


            X509SecurityToken x509SecurityToken = new X509SecurityToken(soapMessage.getSOAPHeader().getOwnerDocument(), certificate);
            String toolBinToken = Base64.encode(x509SecurityToken.getCertificate().getEncoded());
            boolean certMatch = Arrays.equals(toolBinToken.getBytes(), binValueText.getBytes());

            System.out.println("*******************************");
            System.out.println("Reference URI : " + certId);
            System.out.println("Binary Token : " + binValueText);
            System.out.println("Tool Binary Token : " + toolBinToken);
            System.out.println("Comparation result : " + certMatch);
            System.out.println("*******************************");

            return certMatch;
         }
      }


      return false;
   }

   private void dump(Element element) {
      OutputFormat of = new OutputFormat();
      of.setIndenting(true);
      of.setMethod(Method.XML);
      of.setOmitDocumentType(true);
      of.setOmitXMLDeclaration(true);
      DOMSerializer serializer = new XMLSerializer(System.out, of);
      try {
         serializer.serialize(element);
      } catch (IOException ioe) {
         ioe.printStackTrace();
      }
   }
}
