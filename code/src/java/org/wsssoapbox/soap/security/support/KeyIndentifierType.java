/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.soap.security.support;

import com.sun.xml.wss.core.SecurityTokenReference;
import java.security.cert.X509Certificate;
import org.w3c.dom.Document;

/**
 *
 * @author Peter
 */
public interface KeyIndentifierType {
   SecurityTokenReference getKeyIndentifierType(SecurityTokenReference securityTokenReference,Document owner,X509Certificate certificate);
   boolean verifyCertificate(SecurityTokenReference securityTokenReference,Document owner,X509Certificate certificate);
}
