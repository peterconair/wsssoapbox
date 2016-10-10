/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.soap.security.keystore.support;

import java.security.cert.X509Certificate;
import java.util.List;
import org.wsssoapbox.bean.model.soap.security.keystore.CertificateBean;

/**
 *
 * @author Peter
 */
public class TrustedStoreHelperImpl implements TrustedStoreHelper {

   X509Certificate cert;
   List<CertificateBean> CertificateBeans;

   public TrustedStoreHelperImpl() {
   }

 
}
