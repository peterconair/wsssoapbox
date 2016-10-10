/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soap.security.encryption;

import com.sun.org.apache.xml.internal.security.encryption.EncryptedData;
import com.sun.org.apache.xml.internal.security.encryption.EncryptedKey;
import com.sun.org.apache.xml.internal.security.encryption.XMLCipher;
import com.sun.org.apache.xml.internal.security.keys.KeyInfo;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPPart;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPEnvelope;

import com.sun.xml.wss.XWSSecurityException;
import com.sun.xml.wss.core.EncryptedDataImpl;
import com.sun.xml.wss.core.EncryptedKeyHeaderBlock;
import com.sun.xml.wss.core.EncryptedKeyToken;
import com.sun.xml.wss.core.EncryptedTypeHeaderBlock;
import com.sun.xml.wss.core.KeyInfoHeaderBlock;
import com.sun.xml.wss.core.ReferenceListHeaderBlock;
import com.sun.xml.wss.core.X509SecurityToken;
import com.sun.xml.wss.impl.SecurityTokenException;
import com.sun.xml.wss.core.SecurityHeader;
import com.sun.xml.wss.core.SecurityTokenReference;
import com.sun.xml.wss.core.reference.DirectReference;
import com.sun.xml.wss.core.reference.X509IssuerSerial;
import com.sun.xml.wss.core.reference.X509ThumbPrintIdentifier;
import com.sun.xml.wss.impl.MessageConstants;
import com.sun.xml.wss.impl.SecurableSoapMessage;
import com.sun.xml.wss.impl.XMLUtil;
import com.sun.xml.wss.impl.misc.Base64;

import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.Key;
import java.security.KeyStore;
import java.security.PublicKey;
import java.security.cert.X509Certificate;

import java.util.Enumeration;
import java.io.FileNotFoundException;
import java.io.BufferedInputStream;
import java.io.FileInputStream;

import java.security.SecureRandom;
import java.util.UUID;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import org.junit.Test;
import org.junit.Before;
import org.wsssoapbox.soap.support.SoapCreator;
import org.wsssoapbox.soap.security.CertUtil;
import org.wsssoapbox.xml.util.XMLUtils;

/**
 *
 * @author Peter
 */
public class EncryptionSecurityTest {

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

   public EncryptionSecurityTest() {
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

   //  @Test
   public void showKeyStore() throws KeyStoreException {
      System.out.println("***************************************");
      System.out.println("Keytore alias name : " + aliasName);
      System.out.println("Keytore alias password : " + password);
      System.out.println("Create Date  : " + keystore.getCreationDate(aliasName));

      Enumeration<String> e = keystore.aliases();
      while (e.hasMoreElements()) {
         System.out.println("Keytore all alias name " + e.nextElement());
      }
      System.out.println("***************************************");
   }

   //  @Test
   public void showPublicKey() {
      System.out.println("***************************************");
      System.out.println("Public Key Algorithm : " + publicKey.getAlgorithm());
      System.out.println("Public Key Format : " + publicKey.getFormat());
      System.out.println("Public Key Encoded Base64 : " + Base64.encode(publicKey.getEncoded()));
      System.out.println("***************************************");
   }

   //  @Test
   public void showCertificate() {
      System.out.println("***************************************");
      System.out.println("***************************************");
   }

   //   @Test
   public void showPrivateKey() throws KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException {


      System.out.println("***************************************");
      System.out.println("Private Key Algorithm : " + privateKey.getAlgorithm());
      System.out.println("Private Key Format : " + privateKey.getFormat());
      System.out.println("Private Key Encoded Base64 : " + Base64.encode(privateKey.getEncoded()));
      System.out.println("***************************************");
   }

   // @Test
   public void showBinarysecurityTokent() throws SecurityTokenException, CertificateException, FileNotFoundException, XWSSecurityException, SOAPException, Exception {

      X509SecurityToken X509SecurityToken = new X509SecurityToken(soapPart, certificate);

      /*
      System.out.println("Token Value : " + X509SecurityToken.getTokenValue());
      // System.out.println("getBaseURI Value : " + X509SecurityToken.getBaseURI());
      
      // System.out.println("getEncodingStyle Value : " + X509SecurityToken.getEncodingStyle());
      System.out.println("getEncodingType Value : " + X509SecurityToken.getEncodingType());
      System.out.println("getId Value : " + X509SecurityToken.getId());
      //      System.out.println("getLocalName Value : " + X509SecurityToken.getLocalName());
      //      System.out.println("getNamespaceURI Value : " + X509SecurityToken.getNamespaceURI());
      //      System.out.println("getNodeName Value : " + X509SecurityToken.getNodeName());
      //      System.out.println("getNodeValue Value : " + X509SecurityToken.getNodeValue());
      //      System.out.println("getPrefix Value : " + X509SecurityToken.getPrefix());
      //      System.out.println("getTagName Value : " + X509SecurityToken.getTagName());
      //      System.out.println("getTextContent Value : " + X509SecurityToken.getTextContent());
      System.out.println("getTextValue Value : " + X509SecurityToken.getTextValue());
      System.out.println("getType Value : " + X509SecurityToken.getType());
      //      System.out.println("getValue Value : " + X509SecurityToken.getValue());
      System.out.println("getValueType Value : " + X509SecurityToken.getValueType());
       * 
       */

      SOAPElement soapElement = X509SecurityToken.getAsSoapElement();

      SecurityHeader secureHeader = secureMessage.findWsseSecurityHeaderBlock(true, true);
      secureHeader.addChildElement(soapElement);
      secureMessage.generateId();
      secureMessage.saveChanges();
      System.out.println("Securable Soap Message :");
      //   XMLUtil.print(secureMessage);

   }

   @Test
   public void encryptTest() throws XWSSecurityException, SOAPException, Exception {

      SecureRandom secRadom = SecureRandom.getInstance("SHA1PRNG");
      int id = secRadom.nextInt();
      int certId = secRadom.nextInt();

// Set the wsu:Id attribute to the Body
      XMLUtil.setWsuIdAttr(soapBody, "id-" + id);

// Create a WSSE context for the SOAP message
      secureMessage = new SecurableSoapMessage(soapMessage);

      // Create a security header for the message (<wsse:Security>)
      SecurityHeader secureHeader = secureMessage.findWsseSecurityHeaderBlock(true, true);


      EncryptedKey encKey;
      EncryptedTypeHeaderBlock encTypeHeaderBlock;
      EncryptedKeyToken encKeyToken;

      //EncryptedData encData = new EncryptedData(null, privateKey, null, null);

      EncryptedDataImpl encDataImpl = new EncryptedDataImpl();
      encDataImpl.setKeyInfo(null);

      String referenceId = "_" + UUID.randomUUID().toString();


      KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
      keyGenerator.init(128);
      SecretKey sessionKey = keyGenerator.generateKey();

      XMLCipher dataCipher = XMLCipher.getInstance(XMLCipher.AES_256);
      dataCipher.init(XMLCipher.ENCRYPT_MODE, sessionKey);

      EncryptedData encryptedData = dataCipher.encryptData(soapHeader.getOwnerDocument(), soapBody.getOwnerDocument().getDocumentElement());
      encryptedData.setId(referenceId);

      
      

      EncryptedKeyHeaderBlock encryptedKeyHeaderBlock = new EncryptedKeyHeaderBlock(secureHeader.getOwnerDocument());
      encryptedKeyHeaderBlock.setEncryptionMethod("http://www.w3.org/2001/04/xmlenc#rsa-1_5");
      ReferenceListHeaderBlock refListHeaderBlock = new ReferenceListHeaderBlock(secureHeader.getOwnerDocument());


      refListHeaderBlock.addReference("id-" + id);
      // encryptedKeyHeaderBlock.setReferenceList(refListHeaderBlock);

      //  refListHeaderBlock.

      /**/
      // Create the keyinfo referring to the certificate (<ds:KeyInfo>)
      KeyInfoHeaderBlock keyInfoHeaderBlock = new KeyInfoHeaderBlock(soapHeader.getOwnerDocument());
      keyInfoHeaderBlock.setId("KeyId-" + secRadom.nextInt());



      // Create block (<wsse:SecurityTokenReference>) 
      SecurityTokenReference securityTokenReference = new SecurityTokenReference(soapHeader.getOwnerDocument());
      securityTokenReference.setWsuId("STRId-" + secRadom.nextInt());
      keyInfoHeaderBlock.addSecurityTokenReference(securityTokenReference);


      // encryptedKeyHeaderBlock.addChildElement(keyInfoHeaderBlock.getAsSoapElement());

      secureHeader.insertHeaderBlock(encryptedKeyHeaderBlock);


      //*****************************************************************************************
      //**********************************
      // Binary Security Token Case
      //  System.out.println("Vertion :" + certificate.getVersion());
      String namespace = MessageConstants.X509_TOKEN_NS;
      String x509Vesion = namespace + "#X509v" + certificate.getVersion();
      // Create the certificate (<wsse:BinarySecurityToken>)
      X509SecurityToken x509SecurityToken = new X509SecurityToken(soapHeader.getOwnerDocument(),
              certificate, "CertId-" + certId, x509Vesion);
      // Insert Security Token Reference to Binary Security Tokent by using URI should mach with wsuId .
      // Create (<wsse:Reference>) tag 
      DirectReference directReference = new DirectReference();
      directReference.setURI("#CertId-" + certId);
      // set value type 
      directReference.setValueType(x509Vesion);
      // Insert (<wsse:Reference>) tag  to (<wsse:SecurityTokenReference>) tag
      //  securityTokenReference.setReference(directReference);
      // Insert the certificate (<wsse:BinarySecurityToken>) to (<soapenv:Header>)
      // secureHeader.insertHeaderBlock(x509SecurityToken);


      //**********************************
      // Issuer Name and Serial Number Case :
      // Create (<ds:X509Data>) 
      X509IssuerSerial x509IssuerSerial = new X509IssuerSerial(soapHeader.getOwnerDocument(), certificate);
      // Insert (<ds:X509Data>) to (<wsse:SecurityTokenReference>) tag
      securityTokenReference.setReference(x509IssuerSerial);


      //**********************************
      // Subjectkey and Identifer:
      // Create (<ds:X509Data>) 
      X509ThumbPrintIdentifier x509ThumbPrintIdentifier = new X509ThumbPrintIdentifier(soapHeader.getOwnerDocument());
      x509ThumbPrintIdentifier.setValueType(MessageConstants.X509SubjectKeyIdentifier_NS);
      x509ThumbPrintIdentifier.setCertificate(certificate);

      String subKeyId = CertUtil.getSubjectKeyIdentity(certificate);

      x509ThumbPrintIdentifier.addTextNode(subKeyId);
      //  securityTokenReference.setReference(x509ThumbPrintIdentifier);    
      //*************************************************************************

      // Digest all References (#MyId) in the SignedInfo, calculate the signature value
      // and set it in the SignatureValue Element
      //   System.out.println("PublicKey : " + privateKey.toString());

      //  signatureHeaderBlock.sign(privateKey);

// Add the signature data to the header element
      soapHeader.addChildElement(secureHeader.getAsSoapElement());


// Save the signed SOAP message
      //     FileOutputStream fos = new FileOutputStream(new File(signatureFileName));
      //    message.writeTo(fos);
      System.out.println("Messasg : " + XMLUtils.prettyPrint(soapMessage));

      //    soapMessage.writeTo(System.out);

   }
}
