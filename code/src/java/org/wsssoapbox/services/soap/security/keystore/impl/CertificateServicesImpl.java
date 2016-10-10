/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.services.soap.security.keystore.impl;

import java.security.cert.X509Certificate;
import java.util.List;
import org.wsssoapbox.bean.model.soap.security.keystore.CertificateBean;
import org.wsssoapbox.soap.security.keystore.support.CertificateHelper;
import org.wsssoapbox.soap.security.keystore.support.CertificateHelperImpl;
import org.wsssoapbox.services.soap.security.keystore.CertificateSevices;

/**
 *
 * @author Peter
 */
public class CertificateServicesImpl implements CertificateSevices {

   private static final long serialVersionUID = 3699713218636501906L;

   @Override
   public void addCertificate(CertificateBean certBean) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public CertificateBean getCertificateById(int id) {

      CertificateBean certBean = new CertificateBean();
      CertificateHelperImpl certDAO = new CertificateHelperImpl();
      certBean = certDAO.getCertificateById(1);
      return certBean;
   }

   @Override
   public X509Certificate getCertificate(int id) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public List<CertificateBean> getCertificates() {
      throw new UnsupportedOperationException("Not supported yet.");
   }
}
