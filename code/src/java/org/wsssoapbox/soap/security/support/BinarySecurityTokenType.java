/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.soap.security.support;

import com.sun.xml.wss.XWSSecurityException;
import com.sun.xml.wss.core.SecurityTokenReference;
import com.sun.xml.wss.core.X509SecurityToken;
import com.sun.xml.wss.core.reference.DirectReference;
import com.sun.xml.wss.impl.MessageConstants;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

/**
 *
 * @author Peter
 */
public class BinarySecurityTokenType implements KeyIndentifierType {

   private static final Logger logger = LoggerFactory.getLogger(X509CertificateType.class);
   private X509SecurityToken x509SecurityToken;

   @Override
   public SecurityTokenReference getKeyIndentifierType(SecurityTokenReference securityTokenReference, Document owner, X509Certificate certificate) {
      try {
         //**********************************
         // Binary Security Token Case
         //  System.out.println("Vertion :" + certificate.getVersion());
         // Create the certificate (<wsse:BinarySecurityToken>)
         // ValueType="xxxxx/oasis-200401-wss-x509-token-profile-1.0#X509(x)         

         String x509Vesion = "#X509v" + certificate.getVersion();

         if (certificate.getVersion() == 0) {
            x509Vesion = "#X509";
         }
         SecureRandom secRadom = SecureRandom.getInstance("SHA1PRNG");
         int certId = secRadom.nextInt();

         this.setX509SecurityToken(new X509SecurityToken(owner,
                  certificate, "CertId-" + certId, MessageConstants.X509_TOKEN_NS + x509Vesion));

         // Insert Security Token Reference to Binary Security Tokent by using URI should mach with wsuId .
         // Create (<wsse:Reference>) tag 
         DirectReference directReference = new DirectReference();
         directReference.setURI("#CertId-" + certId);
         // set value type 
         directReference.setValueType(MessageConstants.X509_TOKEN_NS + x509Vesion);
         // Insert (<wsse:Reference>) tag  to (<wsse:SecurityTokenReference>) tag
         securityTokenReference.setReference(directReference);
         // Insert the certificate (<wsse:BinarySecurityToken>) to (<soapenv:Header>)

         return securityTokenReference;

      } catch (NoSuchAlgorithmException ex) {
         logger.error(ex.getMessage());
      } catch (XWSSecurityException ex) {
         logger.error(ex.getMessage());
      }
      return null;
   }

   /**
    * @return the x509SecurityToken
    */
   public X509SecurityToken getX509SecurityToken() {
      return x509SecurityToken;
   }

   /**
    * @param x509SecurityToken the x509SecurityToken to set
    */
   public void setX509SecurityToken(X509SecurityToken x509SecurityToken) {
      this.x509SecurityToken = x509SecurityToken;
   }

   @Override
   public boolean verifyCertificate(SecurityTokenReference securityTokenReference, Document owner, X509Certificate certificate) {
      throw new UnsupportedOperationException("Not supported yet.");
   }
}
