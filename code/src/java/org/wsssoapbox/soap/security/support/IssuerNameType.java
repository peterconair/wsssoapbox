/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.soap.security.support;

import org.wsssoapbox.soap.security.support.SubjectkeyIdentifierType;
import com.sun.xml.wss.XWSSecurityException;
import com.sun.xml.wss.core.SecurityTokenReference;
import com.sun.xml.wss.core.reference.X509IssuerSerial;
import java.security.cert.X509Certificate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

/**
 *
 * @author Peter
 */
public class IssuerNameType implements KeyIndentifierType {

   private static final Logger logger = LoggerFactory.getLogger(SubjectkeyIdentifierType.class);

   @Override
   public SecurityTokenReference getKeyIndentifierType(SecurityTokenReference securityTokenReference, Document owner, X509Certificate certificate) {
      try {
         /**********************************
         // Issuer Name and Serial Number Case :
         // Create (<ds:X509Data>) 
         //     <ds:X509Data>
         //             <ds:X509IssuerSerial>
         //                <ds:X509IssuerName>CN=Client Cert,OU=Client Department,O=Client Org.,L=Client City,ST=Client State,C=TH</ds:X509IssuerName>
         //               <ds:X509SerialNumber>1324563157</ds:X509SerialNumber>
         //            </ds:X509IssuerSerial>
         //         </ds:X509Data>
          */
         X509IssuerSerial x509IssuerSerial = new X509IssuerSerial(owner, certificate);
         // Insert (<ds:X509Data>) to (<wsse:SecurityTokenReference>) tag
         securityTokenReference.setReference(x509IssuerSerial);
         return securityTokenReference;
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
