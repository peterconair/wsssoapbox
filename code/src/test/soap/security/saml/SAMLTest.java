/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soap.security.saml;

import com.sun.xml.wss.XWSSecurityException;
import com.sun.xml.wss.core.SecurityHeader;
import com.sun.xml.wss.impl.SecurableSoapMessage;
import com.sun.xml.wss.saml.Advice;
import com.sun.xml.wss.saml.Assertion;
import com.sun.xml.wss.saml.Attribute;
import com.sun.xml.wss.saml.Conditions;
import com.sun.xml.wss.saml.NameID;
import com.sun.xml.wss.saml.SAMLAssertionFactory;
import com.sun.xml.wss.saml.SAMLException;
import com.sun.xml.wss.saml.Subject;
import com.sun.xml.wss.saml.SubjectConfirmation;
import java.io.UnsupportedEncodingException;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import org.junit.Before;
import org.junit.Test;
import org.wsssoapbox.soap.support.SoapCreator;

/**
 *
 * @author Peter
 */
public class SAMLTest {

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

   public SAMLTest() throws SOAPException, UnsupportedEncodingException, XWSSecurityException {
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
   }

   @Test
   public void samlTest() throws XWSSecurityException, SAMLException, SOAPException {


      SAMLAssertionFactory samlFoctory = SAMLAssertionFactory.newInstance(SAMLAssertionFactory.SAML2_0);

      String assertionID = "_" + UUID.randomUUID().toString();
      String issuer = "www.opensaml.org";



      //  SubjectLocality subjectLocality = samlFoctory.createSubjectLocality(null, null);

      //   AuthnContext authnContext = samlFoctory.createAuthnContext(null, null);
      //   
      ///    SubjectConfirmationData subjectConfirmationData = samlFoctory.createSubjectConfirmationData(issuer, issuer, null, null, issuer, envelope);
      //   AuthorityBinding AuthorityBinding = samlFoctory.createAuthorityBinding(null, null, null);

      //    AuthenticationStatement autStatment = samlFoctory.createAuthenticationStatement(strMessage, null, null, null, null);


      GregorianCalendar issueInstant = new GregorianCalendar();
      GregorianCalendar notBefore = new GregorianCalendar();
      GregorianCalendar notOnOrAfter = new GregorianCalendar();

      Conditions conditions = samlFoctory.createConditions();
      NameID nameID = samlFoctory.createNameID("www.thaijavatech.com", "nameQualifield", "formmer");

      Advice advice = samlFoctory.createAdvice(null, null, null);

      SubjectConfirmation subjectConfirmation = samlFoctory.createSubjectConfirmation(nameID, issuer);
      Subject subject = samlFoctory.createSubject(nameID, subjectConfirmation);

      Attribute Attribute = samlFoctory.createAttribute(null, null);

      List statements = null;

      Assertion assertion = samlFoctory.createAssertion(assertionID, nameID, issueInstant, conditions, advice, subject, statements);

      SecurityHeader secureHeader = secureMessage.findWsseSecurityHeaderBlock(true, true);

      System.out.println("Assertion ID : " + assertion.getAssertionID());
      System.out.println("ID : " + assertion.getID());

      System.out.println("Major Version : " + assertion.getMajorVersion());
      System.out.println("Minor Version : " + assertion.getMinorVersion());
      System.out.println("Version : " + assertion.getVersion());
      System.out.println("Saml Issuer : " +assertion.getSamlIssuer());
      
      
      // secureHeader.addChildElement(samlHeader);

      //   XMLUtils.printElement(assertion.toElement(envelope.getOwnerDocument()));

   }
}
