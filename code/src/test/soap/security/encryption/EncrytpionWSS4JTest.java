/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soap.security.encryption;

import soap.security.sinature.*;
import com.sun.xml.wss.impl.SecurableSoapMessage;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.security.auth.callback.CallbackHandler;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import org.apache.ws.security.SOAPConstants;
import org.apache.ws.security.WSSConfig;
import org.apache.ws.security.WSSecurityEngine;
import org.apache.ws.security.WSConstants;
import org.apache.ws.security.WSEncryptionPart;
import org.apache.ws.security.WSSecurityException;
import org.apache.ws.security.components.crypto.Crypto;
import org.apache.ws.security.components.crypto.CryptoFactory;
import org.apache.ws.security.components.crypto.Merlin;
import org.apache.ws.security.message.WSSecEncrypt;
import org.apache.ws.security.message.WSSecHeader;
import org.apache.ws.security.message.WSSecSignature;
import org.apache.ws.security.util.WSSecurityUtil;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.wsssoapbox.soap.support.SoapCreator;
import org.wsssoapbox.xml.util.XMLUtils;

/**
 *
 * @author Peter
 */
public class EncrytpionWSS4JTest {

   private WSSecurityEngine secEngine = new WSSecurityEngine();
   private Crypto crypto = null;
   SOAPMessage soapMessage;
   SOAPHeader soapHeader;
   KeyStore keystore;
   Key privateKey;
   PublicKey publicKey;
   X509Certificate certificate;
   String password = "123456";
   String aliasName = "client_keystore";
   String keyStoreFile = "F:/Develope/sourecode/netbeans/Web_Service/Prodiver/ws-payment-btoken/src/main/webapp/WEB-INF/config/certificates/client/client.jks";
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

   public EncrytpionWSS4JTest() {
   }

   @Before
   public void setUp() throws Exception {
      soapMessage = MessageFactory.newInstance().createMessage();
      soapMessage = SoapCreator.createSOAPMessageFromString(strMessage);
      FileInputStream fis = new FileInputStream("F:/Develope/sourecode/netbeans/Web_Service/Prodiver/ws-payment-btoken/src/main/webapp/WEB-INF/config/certificates/client/client.jks");
      BufferedInputStream bis = new BufferedInputStream(fis);

      keystore = KeyStore.getInstance("JKS", "SUN");
      keystore.load(bis, password.toCharArray());
      certificate = (X509Certificate) keystore.getCertificate(aliasName);
      publicKey = certificate.getPublicKey();
      privateKey = keystore.getKey(aliasName, password.toCharArray());


   }

   @Test
   public void testX509SignatureIS() throws WSSecurityException, Exception {


      WSSConfig.init();

      Properties merlinProp = new Properties();
      merlinProp.put("org.apache.ws.security.crypto.merlin.keystore.type", "JKS");
      merlinProp.put("org.apache.ws.security.crypto.merlin.file", keyStoreFile);
      merlinProp.put("org.apache.ws.security.crypto.merlin.keystore.password", password);

      crypto = CryptoFactory.getInstance(merlinProp);
      Merlin merlin = new Merlin();
      //  merlin.setKeyStore(keystore);
      
      CertificateFactory certificateFactory = merlin.getCertificateFactory();

      System.out.println("Cer Name : " + certificateFactory.getProvider().getName());
      System.out.println("Cert Tyep : " + certificateFactory.getType());
      crypto.setCertificateFactory("#X509", certificateFactory);

      System.out.println("Before Encrypt ....");
      Document doc = XMLUtils.createDocumentFromString(strMessage);
      WSSecHeader secHeader = new WSSecHeader();
      secHeader.insertSecurityHeader(doc);

      WSSecEncrypt encrypt = new WSSecEncrypt();
      encrypt.setUserInfo(aliasName, password);
      encrypt.setKeyIdentifierType(WSConstants.SKI_KEY_IDENTIFIER);
      //builder.setKeyIdentifierType(WSConstants.ISSUER_SERIAL);
      //builder.setKeyIdentifierType(WSConstants.X509_KEY_IDENTIFIER);
      //builder.setKeyIdentifierType(WSConstants.SKI_KEY_IDENTIFIER);
      encrypt.setKeyIdentifierType(WSConstants.BST_DIRECT_REFERENCE);

      encrypt.setSymmetricEncAlgorithm(WSConstants.TRIPLE_DES);
      encrypt.setSymmetricEncAlgorithm(WSConstants.AES_128);
      encrypt.prepare(doc, crypto);
      encrypt.setEmbedEncryptedKey(true);

      SOAPConstants soapConstants = WSSecurityUtil.getSOAPConstants(doc.getDocumentElement());
      List<WSEncryptionPart> parts = new ArrayList<WSEncryptionPart>();
      WSEncryptionPart encP = new WSEncryptionPart(
              soapConstants.getBodyQName().getLocalPart(),
              soapConstants.getEnvelopeURI(),
              "Content");
      parts.add(encP);
      encrypt.encryptForRef(null, parts);

      String outputString = XMLUtils.PrettyDocumentToString(doc);
      System.out.println(XMLUtils.prettyPrint(outputString));


      //  verify(doc, crypto, keystoreCallbackHandler);
   }

   private void verify(
           Document doc, Crypto decCrypto, CallbackHandler handler) throws Exception {
      secEngine.processSecurityHeader(doc, null, handler, decCrypto);

      String outputString =
              XMLUtils.PrettyDocumentToString(doc);
      System.out.println(outputString);

   }
}
