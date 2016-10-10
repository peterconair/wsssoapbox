/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.soap.security.keystore.support;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wsssoapbox.bean.model.soap.security.keystore.CertificateBean;
import org.wsssoapbox.soap.security.CertUtil;
import sun.security.x509.X500Name;

/**
 *
 * @author Peter
 */
public class CertificateHelperImpl {

   private static final Logger logger = LoggerFactory.getLogger(CertificateHelperImpl.class);
   private X509Certificate cert;
   private String aliasName;
   List<CertificateBean> certBeans = new ArrayList<CertificateBean>();

   public CertificateHelperImpl() {
   }

   public CertificateHelperImpl(InputStream is) throws FileNotFoundException, CertificateException, IOException {
      BufferedInputStream bis;
      CertificateFactory cf;
      // Generate sample X509Certificate
      bis = new BufferedInputStream(is);
      cf = CertificateFactory.getInstance("X.509");
      while (bis.available() > 0) {
         cert = (X509Certificate) cf.generateCertificate(bis);
      }
   }

   public CertificateHelperImpl(X509Certificate cert) {
      this.cert = cert;
   }

   public CertificateHelperImpl(X509Certificate cert, String aliasName) {
      this.cert = cert;
      this.aliasName = aliasName;
   }

   public CertificateBean createCertificate() {
      CertificateBean certBean = null;
      int id = 1;
      if (cert != null) {
         try {
            certBean = new CertificateBean();
            certBean.setId(new BigInteger("111111111"));
            if (aliasName != null && !aliasName.equals("")) {
               certBean.setAliasName(this.aliasName);
            } else {
               certBean.setAliasName(cert.getSubjectDN().getName());
            }
            certBean.setName(cert.getSubjectDN().getName());
            certBean.setFilePath("");
            certBean.setFile(null);
            try {
               cert.checkValidity();
            } catch (CertificateExpiredException e) {
               certBean.setValidity(false);
            }
            certBean.setSigAlgName(cert.getSigAlgName());
            certBean.setSigAlgOID(cert.getSigAlgOID());
            certBean.setSignature(CertUtil.byteToHex(cert.getSignature()));


            certBean.setSerialNumber(cert.getSerialNumber());
            certBean.setVersion(cert.getVersion());
            certBean.setEncoded(cert.getEncoded());


            certBean.setFingerpintsSHA1(CertUtil.doFingerprint(cert.getEncoded(), "SHA1"));
            certBean.setFingerpintsMD5(CertUtil.doFingerprint(cert.getEncoded(), "MD5"));
            certBean.setSubKeyId(CertUtil.hashToBase64(cert.getEncoded(), "SHA1"));

            certBean.setIssuerDN(cert.getIssuerDN().getName());
            X500Name x500Issuer = new X500Name(cert.getIssuerX500Principal().getName());
            //  certBean.setX500Issuer(x500Issuer);
            certBean.setIssuerCN(x500Issuer.getCommonName());
            certBean.setIssuerO(x500Issuer.getOrganization());
            certBean.setIssuerOU(x500Issuer.getOrganizationalUnit());
            certBean.setIssuerC(x500Issuer.getCountry());

            certBean.setSubjectDN(cert.getSubjectDN().getName());
            X500Name x500Subject = new X500Name(cert.getIssuerX500Principal().getName());
            //  certBean.setX500Subject(x500Subject);
            certBean.setSubjectCN(x500Subject.getCommonName());
            certBean.setSubjectO(x500Subject.getOrganization());
            certBean.setSubjectOU(x500Subject.getOrganizationalUnit());
            certBean.setSubjectC(x500Subject.getCountry());

            certBean.setNotAfter(cert.getNotAfter());
            certBean.setNotBefore(cert.getNotBefore());

            certBean.setPublicKeyAlg(cert.getPublicKey().getAlgorithm());
            certBean.setPublicFormat(cert.getPublicKey().getFormat());
            certBean.setPublicKeyValue(CertUtil.byteToHex(cert.getPublicKey().getEncoded()));
            certBean.setPublicKeyAlgSize(00000);


         } catch (CertificateEncodingException ex) {
            logger.debug(ex.getMessage());
         } catch (Exception ex) {
            logger.debug(ex.getMessage());
         }
      }

      return certBean;
   }

   public CertificateBean createCertificate(InputStream fis) {
      try {
         BufferedInputStream bis;
         CertificateFactory cf;
         // Generate sample X509Certificate
         bis = new BufferedInputStream(fis);
         cf = CertificateFactory.getInstance("X.509");
         while (bis.available() > 0) {
            cert = (X509Certificate) cf.generateCertificate(bis);
         }
         return createCertificate();
      } catch (CertificateException ex) {
         logger.debug(ex.getMessage());
      } catch (IOException ex) {
         logger.debug(ex.getMessage());
      }
      return null;
   }

   public CertificateBean createCertificate(String certFile) {
      try {
         FileInputStream fis = new FileInputStream(certFile);
         BufferedInputStream bis;
         CertificateFactory cf;
         // Generate sample X509Certificate
         bis = new BufferedInputStream(fis);
         cf = CertificateFactory.getInstance("X.509");
         while (bis.available() > 0) {
            cert = (X509Certificate) cf.generateCertificate(bis);
         }
         return createCertificate();
      } catch (CertificateException ex) {
         logger.debug(ex.getMessage());
      } catch (IOException ex) {
         logger.debug(ex.getMessage());
      }
      return null;
   }

   public void addCertificate(CertificateBean certBean) {
      certBeans.add(certBean);
      logger.debug("CertificateBeans Size : " + certBeans.size());
   }

   public CertificateBean getCertificateById(int id) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   public List<CertificateBean> getCertificates() {

      logger.debug("start public List<CertificateBean> getCertificates()");
      /*
      
      CertificateBean certBean = new CertificateBean();
      CertificateHelper CertificateHelper = new CertificateHelperImpl();
      certBean = CertificateHelper.createCertificate(
      "F:/Develope/sourecode/netbeans/J2EE/WS-SSOAPBox/src/test/soap/usernametoken/SECOM.pem");
      CertificateBeans.add(certBean);
      certBean = CertificateHelper.createCertificate(
      "F:/Develope/sourecode/netbeans/J2EE/WS-SSOAPBox/src/test/soap/usernametoken/cert.pem");
      CertificateBeans.add(certBean);
      certBean = CertificateHelper.createCertificate(
      "F:/Develope/sourecode/netbeans/J2EE/WS-SSOAPBox/src/test/soap/usernametoken/cacert.cer");
      CertificateBeans.add(certBean);
       */

      logger.debug("CertificateBeans Size : " + certBeans.size());
      logger.debug("end public List<CertificateBean> getCertificates()");
      return certBeans;
   }

   public CertificateBean getCertificate(CertificateBean certificateBean) {
      Iterator it = certBeans.iterator();
      while (it.hasNext()) {
         CertificateBean certBean = (CertificateBean) it.next();
         if (certBean == certificateBean);
         return certBean;
      }
      return new CertificateBean();
   }
}
