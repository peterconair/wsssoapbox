/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soap.security.saml;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPMessage;
import org.apache.ws.security.WSSConfig;
import org.apache.ws.security.WSSecurityEngine;
import org.apache.ws.security.WSSecurityEngineResult;
import org.apache.ws.security.WSSecurityException;
import org.apache.ws.security.components.crypto.Crypto;
import org.apache.ws.security.components.crypto.CryptoFactory;
import org.apache.ws.security.message.WSSecHeader;
import org.apache.ws.security.message.WSSecSAMLToken;
import org.apache.ws.security.saml.ext.AssertionWrapper;
import org.apache.ws.security.saml.ext.OpenSAMLUtil;
import org.apache.ws.security.saml.ext.SAMLCallback;
import org.apache.ws.security.saml.ext.SAMLParms;
import org.apache.ws.security.saml.ext.bean.ActionBean;
import org.apache.ws.security.saml.ext.bean.AttributeBean;
import org.apache.ws.security.saml.ext.bean.AttributeStatementBean;
import org.apache.ws.security.saml.ext.bean.AuthDecisionStatementBean;
import org.apache.ws.security.saml.ext.bean.AuthenticationStatementBean;
import org.apache.ws.security.saml.ext.bean.ConditionsBean;
import org.apache.ws.security.saml.ext.bean.KeyInfoBean;
import org.apache.ws.security.saml.ext.bean.SubjectBean;
import org.apache.ws.security.saml.ext.bean.SubjectLocalityBean;
import org.apache.ws.security.saml.ext.builder.SAML2Constants;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.opensaml.common.SAMLVersion;
import org.opensaml.saml1.core.Assertion;
import org.opensaml.xml.XMLObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.wsssoapbox.soap.support.SoapCreator;
import org.wsssoapbox.soap.security.support.SAML2CallbackHandler;
import org.wsssoapbox.xml.util.XMLUtils;

/**
 *
 * @author Peter
 */
public class SAMLWSS4JTest {
   
   private WSSecurityEngine secEngine = new WSSecurityEngine();
   private Crypto crypto = null;
   KeyStore keystore;
   Key privateKey;
   PublicKey publicKey;
   X509Certificate certificate;
   String password = "123456";
   String aliasName = "client_keystore";
   String keyStoreFile = "F:/Develope/sourecode/netbeans/Web_Service/Prodiver/ws-payment-btoken/src/main/webapp/WEB-INF/config/certificates/client/client.jks";
   SOAPMessage soapMessage;
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
   
   public SAMLWSS4JTest() {
      WSSConfig config = WSSConfig.getNewInstance();
      //    config.setValidator(WSSecurityEngine.SAML_TOKEN, new CustomSamlAssertionValidator());
      //    config.setValidator(WSSecurityEngine.SAML2_TOKEN, new CustomSamlAssertionValidator());
      secEngine.setWssConfig(config);
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
      
      
      WSSConfig.init();
      
      Properties merlinProp = new Properties();
      merlinProp.put("org.apache.ws.security.crypto.merlin.keystore.type", "JKS");
      merlinProp.put("org.apache.ws.security.crypto.merlin.file", keyStoreFile);
      merlinProp.put("org.apache.ws.security.crypto.merlin.keystore.password", password);
      crypto = CryptoFactory.getInstance(merlinProp);
      
      
   }

   // @Test
   public void samlCallBackTest() throws WSSecurityException, Exception {
      
      WSSConfig.init();
      
      Properties merlinProp = new Properties();
      merlinProp.put("org.apache.ws.security.crypto.merlin.keystore.type", "JKS");
      merlinProp.put("org.apache.ws.security.crypto.merlin.file", keyStoreFile);
      merlinProp.put("org.apache.ws.security.crypto.merlin.keystore.password", password);
      crypto = CryptoFactory.getInstance(merlinProp);
      
      
   }
   
   @Test
   public void testSAML2AuthnAssertion() throws Exception {
      
      WSSConfig.init();
      
      Properties merlinProp = new Properties();
      merlinProp.put("org.apache.ws.security.crypto.merlin.keystore.type", "JKS");
      merlinProp.put("org.apache.ws.security.crypto.merlin.file", keyStoreFile);
      merlinProp.put("org.apache.ws.security.crypto.merlin.keystore.password", password);
      crypto = CryptoFactory.getInstance(merlinProp);
      
      SAML2CallbackHandler callbackHandler = new SAML2CallbackHandler();
      
      callbackHandler.setStatement(SAML2CallbackHandler.Statement.AUTHN);
      
      callbackHandler.setIssuer("www.example.com");
      //     callbackHandler.setConfirmationMethod(SAML2Constants.CONF_HOLDER_KEY);
      callbackHandler.setConfirmationMethod(SAML2Constants.CONF_BEARER);
      //    callbackHandler.setConfirmationMethod(SAML2Constants.CONF_SENDER_VOUCHES);

      
      ConditionsBean conditionsBean = new ConditionsBean();
      conditionsBean.setTokenPeriodMinutes(10);
      conditionsBean.setAudienceURI("http://ws.apache.org/wss4j");
      callbackHandler.setConditions(conditionsBean);
      
      
      SubjectBean subjectBean = new SubjectBean();
      subjectBean.setSubjectName("WSSOAPBox");
      subjectBean.setSubjectNameQualifier("urn:oasis:names:tc:SAML:1.1:nameid-format:X509SubjectName");
      subjectBean.setSubjectConfirmationMethod(SAML2Constants.CONF_SENDER_VOUCHES);
      callbackHandler.setSubjectBean(subjectBean);


      //Authentication Statements
      AuthenticationStatementBean authBean = new AuthenticationStatementBean();
      authBean.setAuthenticationInstant(new DateTime());
      authBean.setSubject(subjectBean);
      SubjectLocalityBean subjectLocality = new SubjectLocalityBean();
      subjectLocality.setIpAddress("ip address");
      subjectLocality.setDnsAddress("DNS address");
      authBean.setSubjectLocality(subjectLocality);
      
      authBean.setAuthenticationMethod("Password");
 
      
      callbackHandler.setAuthBean(authBean);
      
      
      SAMLParms samlParms = new SAMLParms();
      samlParms.setCallbackHandler(callbackHandler);
      AssertionWrapper assertion = new AssertionWrapper(samlParms);

      
      // assertion.signAssertion(aliasName, password, crypto, true);

      WSSecSAMLToken wsSign = new WSSecSAMLToken();
      
      Document doc = XMLUtils.createDocumentFromString(strMessage);
      WSSecHeader secHeader = new WSSecHeader();
      secHeader.insertSecurityHeader(doc);
      
      Document unsignedDoc = wsSign.build(doc, assertion, secHeader);
      
      
      System.out.println("SAML 2 Authn Assertion (holder-of-key):");
      String outputString = XMLUtils.PrettyDocumentToString(unsignedDoc);
      System.out.println(outputString);


      //   List<WSSecurityEngineResult> results = verify(unsignedDoc);
      //   WSSecurityEngineResult actionResult =
      //            WSSecurityUtil.fetchActionResult(results, WSConstants.ST_SIGNED);
      //     AssertionWrapper receivedAssertion =
      //             (AssertionWrapper) actionResult.get(WSSecurityEngineResult.TAG_SAML_ASSERTION);

      
   }

   //  @Test
   public void samlTest() throws WSSecurityException, Exception {
      //   SAML2CallbackHandler callbackHandler = new SAML2CallbackHandler();
      //  callbackHandler.setStatement(SAML2CallbackHandler.Statement.AUTHN);
      //   callbackHandler.setIssuer("www.example.com");

      
      WSSConfig.init();
      
      Properties merlinProp = new Properties();
      merlinProp.put("org.apache.ws.security.crypto.merlin.keystore.type", "JKS");
      merlinProp.put("org.apache.ws.security.crypto.merlin.file", keyStoreFile);
      merlinProp.put("org.apache.ws.security.crypto.merlin.keystore.password", password);
      
      
      crypto = CryptoFactory.getInstance(merlinProp);
      
      
      SAMLCallback callback = new SAMLCallback();
      
      callback.setSamlVersion(SAMLVersion.VERSION_20);
      callback.setIssuer("www.example.com");
      
      SubjectBean subjectBean = new SubjectBean();
      subjectBean.setSubjectName("uid=joe");
      subjectBean.setSubjectNameQualifier("apache.org");
      subjectBean.setSubjectConfirmationMethod(SAML2Constants.CONF_SENDER_VOUCHES);
      
      KeyInfoBean keyInfo = new KeyInfoBean();
      keyInfo.setCertificate(certificate);
      subjectBean.setKeyInfo(keyInfo);
      callback.setSubject(subjectBean);



      // Attribute Statements
      AttributeStatementBean attrBean = new AttributeStatementBean();
      attrBean.setSubject(subjectBean);
      AttributeBean attributeBean = new AttributeBean();
      attributeBean.setSimpleName("role");
      attributeBean.setAttributeValues(Collections.singletonList("user"));
      attrBean.setSamlAttributes(Collections.singletonList(attributeBean));
      callback.setAttributeStatementData(Collections.singletonList(attrBean));


      //Authentication Statements
      AuthenticationStatementBean authBean = new AuthenticationStatementBean();
      authBean.setSubject(subjectBean);
      SubjectLocalityBean subjectLocality = new SubjectLocalityBean();
      subjectLocality.setIpAddress("ip address");
      subjectLocality.setDnsAddress("DNS address");
      authBean.setSubjectLocality(subjectLocality);
      authBean.setAuthenticationMethod("Password");
      callback.setAuthenticationStatementData(Collections.singletonList(authBean));


      // Authorization Decision Statements
      AuthDecisionStatementBean authzBean = new AuthDecisionStatementBean();
      authzBean.setSubject(subjectBean);
      ActionBean actionBean = new ActionBean();
      actionBean.setContents("Read");
      authzBean.setActions(Collections.singletonList(actionBean));
      authzBean.setResource("endpoint");
      authzBean.setDecision(AuthDecisionStatementBean.Decision.PERMIT);
      authzBean.setResource("resource");
      callback.setAuthDecisionStatementData(Collections.singletonList(authzBean));


      // Create a Conditions object

      ConditionsBean conditionsBean = new ConditionsBean();
      conditionsBean.setTokenPeriodMinutes(10);
      conditionsBean.setAudienceURI("http://ws.apache.org/wss4j");
      callback.setConditions(conditionsBean);
      
      System.out.println("Issuer : " + callback.getIssuer());
      System.out.println("SAML Version : " + callback.getSamlVersion().toString());
      
      Assertion assertion = null;
      
      AssertionWrapper AssertionWrapper = new AssertionWrapper(assertion);
      
      
      
      Element element = callback.getAssertionElement();
      
      XMLObject xml = OpenSAMLUtil.fromDom(callback.getAssertionElement());
      
      if (element != null) {
         System.out.println("Tag Name : " + element.getTagName());
         Node node = element.getFirstChild();
         
         
         Document doc = XMLUtils.createDocumentFromString(strMessage);
         WSSecHeader secHeader = new WSSecHeader();
         secHeader.insertSecurityHeader(doc);
         
         Element header = secHeader.getSecurityHeader();
         
         header.getFirstChild().appendChild(node);
         
         XMLUtils.printDocument(doc);
      }
      
      
      
      
      
   }
   
   private List<WSSecurityEngineResult> verify(Document doc) throws Exception {
      List<WSSecurityEngineResult> results = secEngine.processSecurityHeader(doc, null, null, null);
      String outputString = XMLUtils.PrettyDocumentToString(doc);
      return results;
   }
}
