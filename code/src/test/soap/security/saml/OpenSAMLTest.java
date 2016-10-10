/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soap.security.saml;

import java.io.FileNotFoundException;
import javax.security.cert.CertificateException;
import org.opensaml.xml.ConfigurationException;
import org.wsssoapbox.soap.support.SoapCreator;
import javax.xml.soap.MessageFactory;
import org.junit.Before;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPPart;
import com.sun.xml.wss.impl.SecurableSoapMessage;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.Principal;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPMessage;
import org.junit.Test;
import org.opensaml.Configuration;
import org.opensaml.DefaultBootstrap;
import org.opensaml.common.SAMLObjectBuilder;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.Issuer;
import org.wsssoapbox.soap.security.CertUtil;

/**
 *
 * @author Peter
 */
public class OpenSAMLTest {

   SOAPMessage soapMessage;
   SOAPMessage soapResponse;
   SOAPEnvelope envelope;
   SecurableSoapMessage secureMessage;
   SOAPPart soapPart;
   SOAPBody soapBody;
   SOAPHeader soapHeader;
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
   KeyStore keystore;
   Key privateKey;
   PublicKey publicKey;
   String file;
   X509Certificate certificate;
   String password = "123456";
   String aliasName = "server_cert";

   public OpenSAMLTest() {
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
      privateKey = keystore.getKey("client_keystore", password.toCharArray());
   }

   @Test
   public void testConstructSAML() throws FileNotFoundException, CertificateException, ConfigurationException {


      DefaultBootstrap.bootstrap(); // initialize the opensaml library 


      Principal subjectName = certificate.getSubjectDN();

      // Issuer
//      SAMLObjectBuilder<Issuer> issuerBuilder = (SAMLObjectBuilder<Issuer>) builderFactory.getBuilder(Issuer.DEFAULT_ELEMENT_NAME);
////      Issuer issuer = issuerBuilder.buildObject();
 //     issuer.setFormat("urn:oasis:names:tc:SAML:2.0:nameid-format:X509SubjectName");
//      issuer.setValue(subjectName.getName());
   }
}
