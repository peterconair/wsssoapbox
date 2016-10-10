/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soap.security.sinature;

import com.sun.xml.wss.impl.SecurableSoapMessage;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Properties;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import org.apache.ws.security.WSSConfig;
import org.apache.ws.security.WSSecurityEngine;
import org.apache.ws.security.WSConstants;
import org.apache.ws.security.WSSecurityException;
import org.apache.ws.security.components.crypto.Crypto;
import org.apache.ws.security.components.crypto.CryptoFactory;
import org.apache.ws.security.components.crypto.Merlin;
import org.apache.ws.security.message.WSSecHeader;
import org.apache.ws.security.message.WSSecSignature;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.wsssoapbox.soap.support.SoapCreator;
import org.wsssoapbox.xml.util.XMLUtils;

/**
 *
 * @author Peter
 */
public class SignatureWSS4JTest {

   private WSSecurityEngine secEngine = new WSSecurityEngine();
   private Crypto crypto = null;
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

   public SignatureWSS4JTest() {
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


      WSSecSignature builder = new WSSecSignature();
      builder.setUserInfo(aliasName, password);

      //builder.setKeyIdentifierType(WSConstants.ISSUER_SERIAL);
      //builder.setKeyIdentifierType(WSConstants.X509_KEY_IDENTIFIER);
      //builder.setKeyIdentifierType(WSConstants.SKI_KEY_IDENTIFIER);
      builder.setKeyIdentifierType(WSConstants.BST_DIRECT_REFERENCE);
      
      builder.setSigCanonicalization(WSConstants.C14N_WITH_COMMENTS);
      builder.setSignatureAlgorithm(WSConstants.RSA_SHA1);
      

      System.out.println("Before Signing IS....");
      Document doc = XMLUtils.createDocumentFromString(strMessage);
      WSSecHeader secHeader = new WSSecHeader();
      secHeader.insertSecurityHeader(doc);

      Document signedDoc = builder.build(doc, crypto, secHeader);
      System.out.println("Signed message with IssuerSerial key identifier:");
      String outputString = XMLUtils.PrettyDocumentToString(signedDoc);

      System.out.println(XMLUtils.prettyPrint(outputString));

      System.out.println("After Signing IS....");
      verify(signedDoc);
   }

   private void verify(Document doc) throws Exception {
      secEngine.processSecurityHeader(doc, null, null, crypto);
   }
}
