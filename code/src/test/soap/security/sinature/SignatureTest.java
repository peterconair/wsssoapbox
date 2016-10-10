/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soap.security.sinature;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.XMLCryptoContext;
import javax.xml.crypto.XMLStructure;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.parsers.ParserConfigurationException;
import com.sun.org.apache.xml.internal.security.transforms.Transforms;
import com.sun.org.apache.xml.internal.security.utils.Constants;
import javax.xml.soap.SOAPPart;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPEnvelope;

import com.sun.xml.wss.XWSSecurityException;
import com.sun.xml.wss.core.KeyInfoHeaderBlock;
import com.sun.xml.wss.core.X509SecurityToken;
import com.sun.xml.wss.core.SecurityHeader;

import com.sun.xml.wss.core.SecurityTokenReference;
import com.sun.xml.wss.core.SignatureHeaderBlock;
import com.sun.xml.wss.core.reference.DirectReference;
import com.sun.xml.wss.core.reference.X509IssuerSerial;
import com.sun.xml.wss.core.reference.X509ThumbPrintIdentifier;
import com.sun.xml.wss.impl.MessageConstants;
import com.sun.xml.wss.impl.SecurableSoapMessage;
import com.sun.xml.wss.impl.XMLUtil;

import java.security.NoSuchAlgorithmException;
import java.security.Key;
import java.security.KeyStore;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

import java.security.SecureRandom;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.Transform;
import javax.xml.crypto.dsig.XMLSignature;
import org.apache.xml.security.exceptions.XMLSecurityException;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import org.apache.xml.security.Init;
import org.junit.Test;
import org.junit.Before;
import org.w3c.dom.Element;
import org.wsssoapbox.soap.support.SoapCreator;
import org.wsssoapbox.soap.security.CertUtil;
import org.wsssoapbox.xml.util.XMLUtils;
import org.xml.sax.SAXException;

/**
 *
 * @author Peter
 */
public class SignatureTest {

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

   public SignatureTest() {
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

   // @Test
   public void showSigned() throws XWSSecurityException, SOAPException, Exception {

      SecureRandom secRadom = SecureRandom.getInstance("SHA1PRNG");
      int id = secRadom.nextInt();
      int certId = secRadom.nextInt();

// Set the wsu:Id attribute to the Body
      XMLUtil.setWsuIdAttr(soapBody, "id-" + id);

// Create a WSSE context for the SOAP message
      secureMessage = new SecurableSoapMessage(soapMessage);

      // Create a security header for the message (<wsse:Security>)
      SecurityHeader secureHeader = secureMessage.findWsseSecurityHeaderBlock(true, true);

      // Insert the Signature block (<ds:Signature>)   //Algorithm selection point 
      SignatureHeaderBlock signatureHeaderBlock = new SignatureHeaderBlock(soapHeader.getOwnerDocument(),
              XMLSignature.XMLNS + "rsa-sha1");
      //  XMLSignature.ALGO_ID_SIGNATURE_RSA);
      signatureHeaderBlock.setId("Signature-" + secRadom.nextInt());

      Transforms transforms = new Transforms(soapHeader.getOwnerDocument());
      transforms.addTransform(CanonicalizationMethod.EXCLUSIVE);


      XMLSignatureFactory xmlSigFactory = XMLSignatureFactory.getInstance("DOM");

      CanonicalizationMethod canonicalisationMethod = xmlSigFactory.newCanonicalizationMethod(CanonicalizationMethod.EXCLUSIVE,
              (C14NMethodParameterSpec) null);

      //Algorithm selection point 
      signatureHeaderBlock.addSignedInfoReference("#id-" + id, transforms, Constants.ALGO_ID_DIGEST_SHA1);

      // Create the keyinfo referring to the certificate (<ds:KeyInfo>)
      KeyInfoHeaderBlock keyInfoHeaderBlock = new KeyInfoHeaderBlock(soapHeader.getOwnerDocument());
      keyInfoHeaderBlock.setId("KeyId-" + secRadom.nextInt());


      // Create block (<wsse:SecurityTokenReference>) 
      SecurityTokenReference securityTokenReference = new SecurityTokenReference(soapHeader.getOwnerDocument());
      securityTokenReference.setWsuId("STRId-" + secRadom.nextInt());


      // Insert the Signature block (<wsse:SecurityTokenReference>)  to (<ds:KeyInfo>)
      keyInfoHeaderBlock.addSecurityTokenReference(securityTokenReference);

      // Insert the Signature block (<ds:KeyInfo>) to (<ds:Signature>)
      signatureHeaderBlock.addChildElement(keyInfoHeaderBlock.getAsSoapElement());

      // Insert the Signature block (<ds:Signature>)  to (<soapenv:Header>)
      secureHeader.insertHeaderBlock(signatureHeaderBlock);

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
      //    securityTokenReference.setReference(x509IssuerSerial);


      //**********************************
      // Subjectkey and Identifer:
      // Create (<ds:X509Data>) 
      X509ThumbPrintIdentifier x509ThumbPrintIdentifier = new X509ThumbPrintIdentifier(soapHeader.getOwnerDocument());
      x509ThumbPrintIdentifier.setValueType(MessageConstants.X509SubjectKeyIdentifier_NS);
      x509ThumbPrintIdentifier.setCertificate(certificate);

      String subKeyId = CertUtil.getSubjectKeyIdentity(certificate);

      x509ThumbPrintIdentifier.addTextNode(subKeyId);
      securityTokenReference.setReference(x509ThumbPrintIdentifier);


      // Digest all References (#MyId) in the SignedInfo, calculate the signature value
      // and set it in the SignatureValue Element
      //   System.out.println("PublicKey : " + privateKey.toString());
      signatureHeaderBlock.sign(privateKey);

// Add the signature data to the header element
      soapHeader.addChildElement(secureHeader.getAsSoapElement());


// Save the signed SOAP message
      //     FileOutputStream fos = new FileOutputStream(new File(signatureFileName));
      //    message.writeTo(fos);

      System.out.println(XMLUtils.prettyPrint(soapMessage));

   }

   @Test
   public void testSinedPayload() throws ParserConfigurationException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, MarshalException, XMLSignatureException, XWSSecurityException, SAXException, IOException, TransformerFactoryConfigurationError, TransformerException, SOAPException, XMLSecurityException {

      Init.init();

      SecureRandom secRadom = SecureRandom.getInstance("SHA1PRNG");
      int id = secRadom.nextInt();
      int certId = secRadom.nextInt();

      String signId = UUID.randomUUID().toString();
      // Set the wsu:Id attribute to the Body
      XMLUtil.setWsuIdAttr(soapBody, "id-" + signId);

// Create a WSSE context for the SOAP message
      secureMessage = new SecurableSoapMessage(soapMessage);

      // Create a security header for the message (<wsse:Security>)
      SecurityHeader secureHeader = secureMessage.findWsseSecurityHeaderBlock(true, true);

      XMLSignatureFactory xmlSigFactory = XMLSignatureFactory.getInstance("DOM");

      String referenceUri = "#id-" + signId;

      DigestMethod digestMethod = xmlSigFactory.newDigestMethod(DigestMethod.SHA1, null);

      Transform transform = xmlSigFactory.newTransform(CanonicalizationMethod.EXCLUSIVE, (TransformParameterSpec) null);
      List<Transform> transformList = Collections.singletonList(transform);

      Reference reference = xmlSigFactory.newReference(referenceUri, digestMethod, transformList, null, null);

      CanonicalizationMethod canonicalisationMethod = xmlSigFactory.newCanonicalizationMethod(CanonicalizationMethod.EXCLUSIVE,
              (C14NMethodParameterSpec) null);

      SignatureMethod signatureMethod = xmlSigFactory.newSignatureMethod(SignatureMethod.RSA_SHA1, null);
      List<Reference> referenceList = Collections.singletonList(reference);
      SignedInfo signedInfo = xmlSigFactory.newSignedInfo(canonicalisationMethod, signatureMethod, referenceList);


      // Create block (<wsse:SecurityTokenReference>) 
      SecurityTokenReference securityTokenReference = new SecurityTokenReference(soapHeader.getOwnerDocument());
      securityTokenReference.setWsuId("STRId-" + UUID.randomUUID().toString());
      //**********************************
      // Subjectkey and Identifer:
      // Create (<ds:X509Data>) 
      X509ThumbPrintIdentifier x509ThumbPrintIdentifier = new X509ThumbPrintIdentifier(soapHeader.getOwnerDocument());
      x509ThumbPrintIdentifier.setValueType(MessageConstants.X509SubjectKeyIdentifier_NS);
      x509ThumbPrintIdentifier.setCertificate(certificate);

      String subKeyId = CertUtil.getSubjectKeyIdentity(certificate);
      x509ThumbPrintIdentifier.addTextNode(subKeyId);
      securityTokenReference.setReference(x509ThumbPrintIdentifier);


      //  KeyInfo keyInfo = keyinfoFactory.newKeyInfo(Collections.singletonList(securityTokenReference));

      Element keyInfoElement = XMLUtil.createElement(envelope.getOwnerDocument(), "KeyInfo", "http://www.w3.org/2000/09/xmldsig#", "ds");
      keyInfoElement.setAttribute("Id", "KeyId-" + secRadom.nextInt());
      keyInfoElement.appendChild(securityTokenReference.getAsSoapElement());

    //  XMLUtils.printElement(keyInfoElement);

      KeyInfoFactory keyinfoFactory = xmlSigFactory.getKeyInfoFactory();

      // add security reference to keyInfo 
      KeyInfo keyInfo = keyinfoFactory.newKeyInfo(Collections.singletonList(reference));

      XMLSignature signature = xmlSigFactory.newXMLSignature(signedInfo, keyInfo);

      Element sigElement = XMLUtil.createElement(envelope.getOwnerDocument(), "Signature", "http://www.w3.org/2000/09/xmldsig#", "ds");
      sigElement.setAttribute("Id", "Signature-" + secRadom.nextInt());
      sigElement.appendChild(keyInfoElement);
      
     // XMLUtils.printElement(securityTokenReference.getReference());
            
      //  add to header
      DOMSignContext signContext = new DOMSignContext(privateKey, secureHeader);

      // Marshal and sign the signature elements 
      signature.sign(signContext);


      XMLUtils.print(soapMessage);

   }
   
   
}
