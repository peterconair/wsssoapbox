/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soap.security.saml;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import java.util.UUID;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import org.joda.time.DateTime;
import org.opensaml.Configuration;
import org.opensaml.common.SAMLVersion;
import org.opensaml.saml2.core.AuthnContextClassRef;
import org.opensaml.saml2.core.AuthnContextComparisonTypeEnumeration;
import org.opensaml.saml2.core.AuthnRequest;
import org.opensaml.saml2.core.Issuer;
import org.opensaml.saml2.core.NameIDPolicy;
import org.opensaml.saml2.core.RequestedAuthnContext;
import org.opensaml.saml2.core.impl.AuthnContextClassRefBuilder;
import org.opensaml.saml2.core.impl.AuthnRequestBuilder;
import org.opensaml.saml2.core.impl.IssuerBuilder;
import org.opensaml.saml2.core.impl.NameIDPolicyBuilder;
import org.opensaml.saml2.core.impl.RequestedAuthnContextBuilder;
import org.opensaml.xml.XMLObjectBuilderFactory;
import org.opensaml.xml.io.Marshaller;
import org.opensaml.xml.io.MarshallingException;
import org.opensaml.xml.util.Base64;
import org.opensaml.xml.util.XMLHelper;
import org.w3c.dom.Element;

/**
 *
 * @author Peter
 */
public class SAMLAuthnRequest {

   private  String redirectionUrl;
   private  String relayState;
 
   public static void main(String[] args) throws IOException {
      SAMLAuthnRequest SAMLAuthnRequest  = new SAMLAuthnRequest();
      SAMLAuthnRequest.buildAuthnRequest();
   }

   public  String buildAuthnRequest() throws IOException {
      try {

         XMLObjectBuilderFactory builderFactory = Configuration.getBuilderFactory();

         //Generate ID
         String randId = UUID.randomUUID().toString();
         System.out.println("Random ID: " + randId);

         //SAMLObjectBuilder authnRequestBuilder = (SAMLObjectBuilder) builderFactory.getBuilder(AuthnRequest.DEFAULT_ELEMENT_NAME);
         //AuthnRequest authnRequest = (AuthnRequest) authnRequestBuilder.buildObject();

         //DocumentBuilder builder = factory.newDocumentBuilder();
         //Document authXmlDocument = builder.parse(new InputSource(new StringReader(this.authRequestString)));

         //Create an issuer Object
         IssuerBuilder issuerBuilder = new IssuerBuilder();
         Issuer issuer = issuerBuilder.buildObject("urn:oasis:names:tc:SAML:2.0:assertion", "Issuer", "samlp");
         issuer.setValue("http://saml20sp.abilityweb.us");

         //Create NameIDPolicy
         NameIDPolicyBuilder nameIdPolicyBuilder = new NameIDPolicyBuilder();
         NameIDPolicy nameIdPolicy = nameIdPolicyBuilder.buildObject();
         //nameIdPolicy.setSchemaLocation("urn:oasis:names:tc:SAML:2.0:protocol");
         nameIdPolicy.setFormat("urn:oasis:names:tc:SAML:2.0:nameid-format:persistent");
         nameIdPolicy.setSPNameQualifier("http://saml20sp.abilityweb.us");
         nameIdPolicy.setAllowCreate(true);

         //Create AuthnContextClassRef
         AuthnContextClassRefBuilder authnContextClassRefBuilder = new AuthnContextClassRefBuilder();
         AuthnContextClassRef authnContextClassRef =
                 authnContextClassRefBuilder.buildObject("urn:oasis:names:tc:SAML:2.0:assertion",
                 "AuthnContextClassRef", "saml");
         authnContextClassRef.setAuthnContextClassRef("urn:oasis:names:tc:SAML:2.0:ac:classes:PasswordProtectedTransport");
         //Marshaller accrMarshaller = org.opensaml.Configuration.getMarshallerFactory().getMarshaller(authnContextClassRef);
         //org.w3c.dom.Element authnContextClassRefDom = accrMarshaller.marshall(authnContextClassRef);


         //Create RequestedAuthnContext
         RequestedAuthnContextBuilder requestedAuthnContextBuilder = new RequestedAuthnContextBuilder();
         RequestedAuthnContext requestedAuthnContext =
                 requestedAuthnContextBuilder.buildObject();
         requestedAuthnContext.setComparison(AuthnContextComparisonTypeEnumeration.EXACT);
         requestedAuthnContext.getAuthnContextClassRefs().add(authnContextClassRef);
         //requestedAuthnContext.setDOM(authnContextClassRefDom);
         //authnContextClassRef.
         //.setParent((XMLObject) requestedAuthnContext);



         DateTime issueInstant = new DateTime();
         AuthnRequestBuilder authRequestBuilder = new AuthnRequestBuilder();
         AuthnRequest authRequest = authRequestBuilder.buildObject("urn:oasis:names:tc:SAML:2.0:protocol", "AuthnRequest", "samlp");
         //AuthnRequest request = (AuthnRequest) buildXMLObject(AuthnRequest.DEFAULT_ELEMENT_NAME);
         //authRequest.ASSERTION_CONSUMER_SERVICE_URL_ATTRIB_NAME = "AssertionConsumerServiceURL";
         //authRequest.FORCE_AUTHN_ATTRIB_NAME = "ForceAuthn";
         //authRequest.IS_PASSIVE_ATTRIB_NAME = "IsPassive";
         authRequest.setForceAuthn(false);
         authRequest.setIsPassive(false);
         authRequest.setIssueInstant(issueInstant);
         authRequest.setProtocolBinding("urn:oasis:names:tc:SAML:2.0:bindings:HTTP-POST");
         authRequest.setAssertionConsumerServiceURL("http://saml20sp.abilityweb.us/spdbg/sp.php");
         authRequest.setIssuer(issuer);
         authRequest.setNameIDPolicy(nameIdPolicy);
         authRequest.setRequestedAuthnContext(requestedAuthnContext); //TODO: How to connect the AuthnContextClassRef that I created for this object
         authRequest.setID(randId);
         authRequest.setVersion(SAMLVersion.VERSION_20);
         
         String stringRep = authRequest.toString();
      //   System.out.println("New AuthnRequestImpl: " + stringRep);
         System.out.println("Assertion Consumer Service URL: " + authRequest.getAssertionConsumerServiceURL());


         // Now we must build our representation to put into the html form to be submitted to the idp
         Marshaller marshaller = Configuration.getMarshallerFactory().getMarshaller(authRequest);
         Element authDOM = marshaller.marshall(authRequest);
         StringWriter rspWrt = new StringWriter();
         XMLHelper.writeNode(authDOM, rspWrt);
         String messageXML = rspWrt.toString();
         //String samlResponse = new String(Base64.encodeBytes(messageXML.getBytes(), Base64.DONT_BREAK_LINES));

         //delete this area
         //String temp = "<samlp:AuthnRequest  xmlns:samlp=\"urn:oasis:names:tc:SAML:2.0:protocol\"  ID=\"71069679271a7cf36e0e02e48084798ea844fce23f\" Version=\"2.0\" IssueInstant=\"2010-03-09T10:46:23Z\" ForceAuthn=\"false\" IsPassive=\"false\" ProtocolBinding=\"urn:oasis:names:tc:SAML:2.0:bindings:HTTP-POST\" AssertionConsumerServiceURL=\"http://saml20sp.abilityweb.us/spdbg/sp.php\"><saml:Issuer xmlns:saml=\"urn:oasis:names:tc:SAML:2.0:assertion\">http://saml20sp.abilityweb.us</saml:Issuer><samlp:NameIDPolicy  xmlns:samlp=\"urn:oasis:names:tc:SAML:2.0:protocol\" Format=\"urn:oasis:names:tc:SAML:2.0:nameid-format:persistent\" SPNameQualifier=\"http://saml20sp.abilityweb.us\" AllowCreate=\"true\"></samlp:NameIDPolicy><samlp:RequestedAuthnContext xmlns:samlp=\"urn:oasis:names:tc:SAML:2.0:protocol\" Comparison=\"exact\"><saml:AuthnContextClassRef xmlns:saml=\"urn:oasis:names:tc:SAML:2.0:assertion\">urn:oasis:names:tc:SAML:2.0:ac:classes:PasswordProtectedTransport</saml:AuthnContextClassRef></samlp:RequestedAuthnContext></samlp:AuthnRequest>";
         Deflater deflater = new Deflater(Deflater.DEFLATED, true);
         ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
         DeflaterOutputStream deflaterOutputStream = new DeflaterOutputStream(byteArrayOutputStream, deflater);
         deflaterOutputStream.write(messageXML.getBytes());
         deflaterOutputStream.close();
         String samlResponse = Base64.encodeBytes(byteArrayOutputStream.toByteArray(), Base64.DONT_BREAK_LINES);
         String outputString = new String(byteArrayOutputStream.toByteArray());
         //System.out.println("Compressed String: " + outputString);
         samlResponse = URLEncoder.encode(samlResponse);

         String actionURL = this.redirectionUrl;
         System.out.println("Converted AuthRequest: " + messageXML);
         System.out.println("samlResponse: " + samlResponse);
         //messageXML = messageXML.replace("<", "&lt;");
         //messageXML = messageXML.replace(">", "&gt;");

         String url = actionURL + "?SAMLRequest=" + samlResponse + "&RelayState=" + this.relayState;
         System.out.println(url);
         return url;



         //HTTPRedirectDeflateEncoder httpRedirectDeflateEncoder = new HTTPRedirectDeflateEncoder();
         //httpRedirectDeflateEncoder.encode((MessageContext) authDOM);


      } catch (MarshallingException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } catch (UnsupportedEncodingException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } finally {
         //Nothing yet
      }
      return "";
   }
}
