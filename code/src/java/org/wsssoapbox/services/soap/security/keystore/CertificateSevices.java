/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.services.soap.security.keystore;

import java.security.cert.X509Certificate;
import java.util.List;
import org.wsssoapbox.bean.model.soap.security.keystore.CertificateBean;


/**
 *
 * @author Peter
 */
public interface CertificateSevices {

   public void addCertificate(CertificateBean certBean);

   public CertificateBean getCertificateById(int id);

   public X509Certificate getCertificate(int id);

   public List<CertificateBean> getCertificates();
}
