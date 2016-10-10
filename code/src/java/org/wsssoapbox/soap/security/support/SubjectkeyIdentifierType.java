/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.soap.security.support;

import com.sun.xml.wss.XWSSecurityException;
import com.sun.xml.wss.core.SecurityTokenReference;
import com.sun.xml.wss.core.reference.X509ThumbPrintIdentifier;
import com.sun.xml.wss.impl.MessageConstants;
import java.io.IOException;
import java.security.cert.X509Certificate;
import javax.xml.soap.SOAPException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.wsssoapbox.soap.security.CertUtil;

/**
 *
 * @author Peter
 */
public class SubjectkeyIdentifierType implements KeyIndentifierType {

   private static final Logger logger = LoggerFactory.getLogger(SubjectkeyIdentifierType.class);

   @Override
   public SecurityTokenReference getKeyIndentifierType(SecurityTokenReference securityTokenReference,Document owner, X509Certificate certificate) {
      try {
         /**********************************
         // Subjectkey and Identifer:
         // Create (<wsse:KeyIdentifier>) 
          * <wsse:KeyIdentifier EncodingType="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary" 
          * ValueType="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509SubjectKeyIdentifier">
          *             zy125LT9tgijX+9NOlhUAMvVMnA=
          * </wsse:KeyIdentifier>
          **/
         X509ThumbPrintIdentifier subjectKeyIdentifier = new X509ThumbPrintIdentifier(owner);
         subjectKeyIdentifier.setValueType(MessageConstants.X509SubjectKeyIdentifier_NS);
         subjectKeyIdentifier.setCertificate(certificate);

         String subKeyId = CertUtil.getSubjectKeyIdentity(certificate);

         subjectKeyIdentifier.addTextNode(subKeyId);
         securityTokenReference.setReference(subjectKeyIdentifier);

         return securityTokenReference;
      } catch (SOAPException ex) {
         logger.error(ex.getMessage());
      } catch (IOException ex) {
         logger.error(ex.getMessage());
      } catch (XWSSecurityException ex) {
         logger.error(ex.getMessage());
      }
      return null;
   }

   @Override
   public boolean verifyCertificate(SecurityTokenReference securityTokenReference, Document owner, X509Certificate certificate) {
      
      
      return false;
   }
}
