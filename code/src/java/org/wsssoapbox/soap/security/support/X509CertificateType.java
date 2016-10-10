/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.soap.security.support;

import org.wsssoapbox.soap.security.support.KeyIndentifierType;
import com.sun.xml.wss.XWSSecurityException;
import com.sun.xml.wss.core.SecurityTokenReference;
import com.sun.xml.wss.core.reference.X509ThumbPrintIdentifier;
import com.sun.xml.wss.impl.MessageConstants;
import com.sun.xml.wss.impl.misc.Base64;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import javax.xml.soap.SOAPException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

/**
 *
 * @author Peter
 */
public class X509CertificateType implements KeyIndentifierType {

   private static final Logger logger = LoggerFactory.getLogger(X509CertificateType.class);

   @Override
   public SecurityTokenReference getKeyIndentifierType(SecurityTokenReference securityTokenReference, Document owner, X509Certificate certificate) {
      try {
         //**********************************
         // X.509 Certificates:
         // Create (<wsse:KeyIdentifier>)  
         // ValueType="xxxxx/oasis-200401-wss-x509-token-profile-1.0#X509v(x)
         X509ThumbPrintIdentifier x509Cert = new X509ThumbPrintIdentifier(owner);
         String x509Vesion = "#X509v" + certificate.getVersion();

         if (certificate.getVersion() == 0) {
            x509Vesion = "#X509";
         }

         x509Cert.setValueType(MessageConstants.X509_TOKEN_NS + x509Vesion);
         x509Cert.setCertificate(certificate);

         String cert = Base64.encode(certificate.getEncoded());
         // Add the Certificate 
         x509Cert.addTextNode(cert);
         securityTokenReference.setReference(x509Cert);
         return securityTokenReference;
      } catch (CertificateEncodingException ex) {
         logger.error(ex.getMessage());
      } catch (SOAPException ex) {
         logger.error(ex.getMessage());
      } catch (XWSSecurityException ex) {
         logger.error(ex.getMessage());
      }
      return null;
   }

   @Override
   public boolean verifyCertificate(SecurityTokenReference securityTokenReference, Document owner, X509Certificate certificate) {
      throw new UnsupportedOperationException("Not supported yet.");
   }
}
