/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.soap.security.keystore.support;

import java.io.InputStream;
import java.util.List;
import org.wsssoapbox.bean.model.soap.security.keystore.CertificateBean;

/**
 *
 * @author Peter
 */
public interface CertificateHelper {

   public void addCertificate(CertificateBean certBean);

   public CertificateBean getCertificateById(int id);

   public CertificateBean getCertificate(CertificateBean certificateBean);

   public List<CertificateBean> getCertificates();

   public CertificateBean createCertificate();

   public CertificateBean createCertificate(String certFile);

   public CertificateBean createCertificate(InputStream fileInputStream);
}
