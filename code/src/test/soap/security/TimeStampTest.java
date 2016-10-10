/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soap.security;

import com.sun.xml.wss.XWSSecurityException;
import com.sun.xml.wss.core.SecurityHeader;
import com.sun.xml.wss.core.Timestamp;
import com.sun.xml.wss.core.UsernameToken;
import com.sun.xml.wss.impl.SecurableSoapMessage;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.xml.namespace.QName;
import org.wsssoapbox.xml.util.XMLUtils;
import javax.xml.soap.SOAPPart;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import org.junit.Before;
import org.junit.Test;
import org.wsssoapbox.bean.model.soap.security.SecurityHeaderBean;
import org.wsssoapbox.bean.model.soap.security.TimeStampBean;
import org.wsssoapbox.bean.model.soap.security.UsernameTokenBean;

/**
 *
 * @author Peter
 */
public class TimeStampTest {

   boolean mustUnderstand;

   public TimeStampTest() {
   }
   SOAPMessage soapMessage;
   SOAPEnvelope envelope;
   SecurableSoapMessage secureMessage;
   SOAPPart soapPart;
   SOAPHeader soapHeader;

   @Before
   public void setUp() throws Exception {

      soapMessage = MessageFactory.newInstance().createMessage();
      envelope = soapMessage.getSOAPPart().getEnvelope();
      secureMessage = new SecurableSoapMessage(soapMessage);
      soapPart = soapMessage.getSOAPPart();
      soapHeader = soapMessage.getSOAPHeader();
   }

   // @Test
   public void testCreateTimeStamp() throws XWSSecurityException, Exception {


      Timestamp timeStamp = new Timestamp();
      timeStamp.setId("Timestamp-" + 1);
      timeStamp.setTimeout(60);
      SOAPElement timeStampElement = timeStamp.getAsSoapElement();



      // All (Username , Password , Nounce , Created ,Digest ) 
      UsernameToken usernameToken = new UsernameToken(secureMessage.getSOAPPart(), "admin", "secret", true, true, true);
      //  usernameToken.setDigestOn();

      SOAPElement usernameElement = usernameToken.getAsSoapElement();


      System.out.println("Element QName : "
              + usernameElement.getElementQName());
      System.out.println("Element Name : "
              + usernameElement.getLocalName());
      System.out.println("Prefix Name : "
              + usernameElement.getPrefix());


      System.out.println("");

      //     SecurityHeader secureHeader = new SecurityHeader(soapHeader);

      SecurityHeader secureHeader = secureMessage.findWsseSecurityHeaderBlock(true, true);

      secureMessage.generateId();
      secureMessage.saveChanges();

      //    secureHeader.setMustUnderstand(mustUnderstand);

      secureHeader.addChildElement(usernameElement);
      secureHeader.addChildElement(timeStampElement);


      System.out.println("Securable Soap Message :");
      XMLUtils.print(secureMessage);


      //  soapHeader.addNotUnderstoodHeaderElement(new QName("1","2"));

      QName operationQName = new QName("http://example.com/wsdl", "sayHello", "ns");

      System.out.println("SOAP Message :");
      //  XMLUtils.print(soapMessage);

   }

   //  @Test
   public void testCreateUsernameToken() throws SOAPException, XWSSecurityException, Exception {

      soapMessage = MessageFactory.newInstance().createMessage();
      secureMessage = new SecurableSoapMessage(soapMessage);

      SecurityHeaderBean secureHB = new SecurityHeaderBean();
      secureHB.setDoCreate(true);
      secureHB.setMustUnderstand(true);

      SecurityHeader secureHeader = createSecurityHeader(secureMessage, secureHB);

      UsernameTokenBean uNameToken = new UsernameTokenBean();
      uNameToken.setUsernameValue("admin");
      uNameToken.setPasswordValue("secret");
      uNameToken.setCreated(false);
      uNameToken.setNonce(false);
      uNameToken.setPasswordType("PasswordText");



      TimeStampBean tsBean = new TimeStampBean();

      tsBean.setWsuId("Timestamp-1");
      tsBean.setTimeout(60);


      SOAPElement usernameElement = addUsernameToken(secureMessage, uNameToken);
      SOAPElement timeStampElement = addTimeStmap(tsBean);

      secureHeader.addChildElement(usernameElement);
      secureHeader.addChildElement(timeStampElement);

      secureMessage.generateId();
      secureMessage.saveChanges();
      System.out.println("Securable Soap Message :");
      XMLUtils.print(secureMessage);


   }

   private SecurityHeader createSecurityHeader(SecurableSoapMessage secMessage, SecurityHeaderBean secureHB) throws XWSSecurityException, Exception {
      boolean mustUndstand = secureHB.isMustUnderstand();
      boolean doCteate = secureHB.isDoCreate();
      SecurityHeader secureHeader = secMessage.findWsseSecurityHeaderBlock(doCteate, mustUndstand);

      return secureHeader;
   }

   private SOAPElement addUsernameToken(SecurableSoapMessage secMessage, UsernameTokenBean uNameToken) throws XWSSecurityException {

      UsernameToken usernameToken;

      String userName = uNameToken.getUsernameValue();
      String password = uNameToken.getPasswordValue();
      boolean passwordDigest = uNameToken.isPasswordDigest();
      boolean nonce = uNameToken.isNonce();
      boolean created = uNameToken.isCreated();

      usernameToken = new UsernameToken(secMessage.getSOAPPart(), userName, password, nonce, created, passwordDigest);
      SOAPElement usernameElement = usernameToken.getAsSoapElement();

      return usernameElement;

   }

   private SOAPElement addTimeStmap(TimeStampBean tsBean) throws XWSSecurityException {

      Timestamp timeStamp;
      SOAPElement timeStampElement;

      String id = tsBean.getWsuId();
      Long timeOut = tsBean.getTimeout();

      timeStamp = new Timestamp();
      timeStamp.setId(id);
      timeStamp.setTimeout(timeOut);
      timeStampElement = timeStamp.getAsSoapElement();

      return timeStampElement;
   }

   @Test
   public void testTimeStampFormat() {
      Timestamp timestamp = new Timestamp();
      timestamp.setTimeout(60);

      System.currentTimeMillis();



      Date date = new Date();
      System.out.println(date);

      System.out.println("Created : " + timestamp.getCreated());
      System.out.println("Created : " + timestamp.getExpires());

      
       Calendar c = new GregorianCalendar();
       System.out.println("Time Zone : "+c.getTimeZone().getDisplayName());
       System.out.println("Time  : "+c.getTime());
   }
}
