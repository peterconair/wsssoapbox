/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.bean.backing.soap.security.keystore;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.slf4j.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.slf4j.LoggerFactory;
import org.wsssoapbox.bean.model.soap.security.TrustedStoreBean;
import org.wsssoapbox.bean.model.soap.security.keystore.CertificateBean;
import org.wsssoapbox.soap.security.keystore.support.CertificateHelper;
import org.wsssoapbox.soap.security.keystore.support.CertificateHelperImpl;

/**
 *
 * @author Peter
 */
@ManagedBean(name = "certForm")
@SessionScoped
public class CertificatesForm implements Serializable {

   private static final long serialVersionUID = -2305570885872038715L;
   private static final Logger logger = LoggerFactory.getLogger(CertificatesForm.class);
   private CertificateBean slectedCertBean;
   private CertificateBean certBean;
   private CertificateBean uploadedCert;
   private List<CertificateBean> certBeans;
   private TrustedStoreBean trustedStoreBean;
   private CertificateHelperImpl certHelper;

   public CertificatesForm() {
      logger.debug("start public CertificatesForm()");
      certHelper = new CertificateHelperImpl();
      uploadedCert = new CertificateBean();
      trustedStoreBean = new TrustedStoreBean();
      certBeans = certHelper.getCertificates();
      trustedStoreBean.setCertificateBeans(certBeans);
      logger.debug("end public CertificatesForm()");
   }

   public void intailCertififcate() {
      trustedStoreBean = new TrustedStoreBean();
      certBeans = certHelper.getCertificates();
      trustedStoreBean.setCertificateBeans(certBeans);
   }

   public void addCertificate(CertificateBean certBean) {
      logger.debug("start  public void addCertificate()");
      //  CertificateHelper certDAO = new CertificateHelperImpl();

      logger.debug("DN : " + certBean.getSubjectDN());

      certHelper.addCertificate(certBean);
      FacesMessage msg = new FacesMessage("Successful.");
      FacesContext.getCurrentInstance().addMessage(null, msg);

      intailCertififcate();
      // reset value in import form
      this.uploadedCert = new CertificateBean();
      logger.debug("end  public void addCertificate()");
   }

   public void handleFileUpload(FileUploadEvent event) {

      logger.debug("start public void handleFileUpload(FileUploadEvent event)");
      CertificateBean cert = new CertificateBean();
      //CertificateDAO certDAO = new CertificateHelperImpl();
      try {
         cert = this.certHelper.createCertificate(event.getFile().getInputstream());
      } catch (IOException ex) {
         FacesMessage msg = new FacesMessage("Failed", event.getFile().getFileName() + " is not upload.");
         FacesContext.getCurrentInstance().addMessage(null, msg);
         logger.debug(ex.getMessage());
      }
      FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
      FacesContext.getCurrentInstance().addMessage(null, msg);

      this.uploadedCert = cert;
      logger.debug("DN : " + uploadedCert.getSubjectDN());

      logger.debug("end public void handleFileUpload(FileUploadEvent event)");
   }

   public void onRowSelect(SelectEvent event) {
      certBean = new CertificateBean();
      certBean = (CertificateBean) event.getObject();
   }

   /**
    * @return the certBeans
    */
   public List<CertificateBean> getCertBeans() {
      return certBeans;
   }

   /**
    * @param certBeans the certBeans to set
    */
   public void setCertBeans(List<CertificateBean> certBeans) {
      this.certBeans = certBeans;
   }

   /**
    * @return the trustedStoreBean
    */
   public TrustedStoreBean getTrustedStoreBean() {
      return trustedStoreBean;
   }

   /**
    * @param trustedStoreBean the trustedStoreBean to set
    */
   public void setTrustedStoreBean(TrustedStoreBean trustedStoreBean) {
      this.trustedStoreBean = trustedStoreBean;
   }

   /**
    * @return the slectedCertBean
    */
   public CertificateBean getSlectedCertBean() {
      return slectedCertBean;
   }

   /**
    * @param slectedCertBean the slectedCertBean to set
    */
   public void setSlectedCertBean(CertificateBean slectedCertBean) {
      this.slectedCertBean = slectedCertBean;
   }

   /**
    * @return the certBean
    */
   public CertificateBean getCertBean() {
      return certBean;
   }

   /**
    * @param certBean the certBean to set
    */
   public void setCertBean(CertificateBean certBean) {
      this.certBean = certBean;
   }

   /**
    * @return the uloadedCert
    */
   public CertificateBean getUloadedCert() {
      return uploadedCert;
   }

   /**
    * @param uloadedCert the uloadedCert to set
    */
   public void setUloadedCert(CertificateBean uloadedCert) {
      this.uploadedCert = uloadedCert;
   }
}
