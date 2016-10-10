/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.bean.backing.soap.security.keystore;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.security.KeyStoreException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.slf4j.Logger;
import org.hibernate.PropertyValueException;
import org.primefaces.event.FileUploadEvent;

import org.primefaces.event.SelectEvent;
import org.slf4j.LoggerFactory;
import org.wsssoapbox.bean.model.soap.security.keystore.CertificateBean;
import org.wsssoapbox.bean.model.soap.security.keystore.KeyStoreBean;

import org.wsssoapbox.database.User;
import org.wsssoapbox.services.soap.security.keystore.KeyStoreServices;
import org.wsssoapbox.services.soap.security.keystore.impl.KeyStoreServicesImpl;
import org.wsssoapbox.soap.security.keystore.support.KeyStoreHelper;
import org.wsssoapbox.view.util.ScopeController;

/**
 *
 * @author Peter
 */
@ManagedBean(name = "keyStoreForm")
@SessionScoped
public class KeyStoreForm implements Serializable {

   private static final Logger logger = LoggerFactory.getLogger(KeyStoreForm.class);
   private static final long serialVersionUID = 4803006284237357197L;
   private KeyStoreBean keyStoreBean;
   private List<KeyStoreBean> keyStoreBeans = new ArrayList<KeyStoreBean>();
   private String keyStoreFile;
   private String ksFileName;
   private String password;
   private KeyStoreBean selectedKeyStoreBean;
   private CertificateBean selectedCertBean;
   private InputStream ksInputStream;
   private Map<String, String> keyStores = new HashMap<String, String>();
   private Map<String, String> aliasNames = new HashMap<String, String>();
   private Map<String, Map<String, String>> aliasNamesData = new HashMap<String, Map<String, String>>();
   private String keyStoreName;
   private String aliasName;
   private KeyStoreServices keyStoreService;

   public KeyStoreForm() {
      logger.debug("start public KeyStoreForm()");
      selectedKeyStoreBean = new KeyStoreBean();
      keyStoreBean = new KeyStoreBean();
      keyStoreService = new KeyStoreServicesImpl();
      //initKeyStore();
      logger.debug("end public KeyStoreForm()");
   }

   public void handleKeyStoreChange() {
      if (keyStoreName != null && !keyStoreName.equals("")) {
         aliasNames = aliasNamesData.get(keyStoreName);
         logger.debug("You select keystore : " + keyStoreName);
      } else {
         aliasNames = new HashMap<String, String>();
      }
   }

   public void deleteKeyStore() {
      logger.debug("end  public void deleteKeyStore() ");
      try {
         User user = (User) ScopeController.getSessionMap("user");
         logger.info("Deleting the key store .... ");
         logger.info("Alias Name : " + selectedKeyStoreBean.getAliasName());
         logger.info("Key Store Id : " + selectedKeyStoreBean.getId());
         logger.info("User : " + user.getUsername());
         keyStoreService.delete(selectedKeyStoreBean, user);
         keyStoreBeans.remove(selectedKeyStoreBean);

         ScopeController.setSessionMap("keyStoreBeans", keyStoreBeans);

         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Succesful", selectedKeyStoreBean.getAliasName() + " is deleted.");
         FacesContext.getCurrentInstance().addMessage(null, msg);

         selectedKeyStoreBean = new KeyStoreBean();
         keyStoreBean = new KeyStoreBean();
      } catch (Exception ex) {
         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error ", ex.getMessage());
         FacesContext.getCurrentInstance().addMessage(null, msg);
         logger.equals("Error with delete key store : " + ex.getMessage());
      }


      logger.debug("end  public void deleteKeyStore() ");
   }

   @PostConstruct
   public void initKeyStore() {
      keyStores = new HashMap<String, String>();
      aliasNamesData = new HashMap<String, Map<String, String>>();

      User user = (User) ScopeController.getSessionMap("user");
      keyStoreBeans = keyStoreService.query(user);
      ScopeController.setSessionMap("keyStoreBeans", keyStoreBeans);

      if (keyStoreBeans != null) {
         for (KeyStoreBean ksBean : keyStoreBeans) {
            String ksName = ksBean.getName();
            keyStores.put(ksName, ksName);
            logger.debug("KeyStore Name : " + ksName);
            logger.debug("Alias Name  : " + ksBean.getStrAliasNames());
            Iterator<String> itAlias = ksBean.getStrAliasNames().iterator();
            Map<String, String> alias = new HashMap<String, String>();
            while (itAlias.hasNext()) {
               String name = itAlias.next();
               logger.debug("Alias Name : " + name);
               alias.put(name, name);
            }
            aliasNamesData.put(ksName, alias);
            logger.debug("After Added Alias Name Count : " + aliasNamesData.size() + " Names");
         }
      } else {
         logger.debug("Not have any Key Store in database....");
      }
   }

   public void handleFileUpload(FileUploadEvent event) {
      try {
         logger.debug("start public void handleFileUpload(FileUploadEvent event)");
         logger.info("File : " + event.getFile().getFileName());
         ksInputStream = event.getFile().getInputstream();
         ksFileName = event.getFile().getFileName();

         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Succesful", ksFileName + " is uploaded.");
         FacesContext.getCurrentInstance().addMessage(null, msg);
         logger.info("Succesful", ksFileName + " is uploaded.");


      } catch (IOException ex) {
         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed", event.getFile() + " is not upload.");
         FacesContext.getCurrentInstance().addMessage(null, msg);
         logger.info("IOException : " + "Failed", event.getFile() + " is not upload.");
         logger.debug("IOException : " + ex.getMessage());
         ex.printStackTrace();
      }

      logger.debug("end public void handleFileUpload(FileUploadEvent event)");
   }

   public void onRowSelectKeyStore(SelectEvent event) {
      try {
         keyStoreBean = new KeyStoreBean();
         keyStoreBean = (KeyStoreBean) event.getObject();
         KeyStoreHelper keyStoreHelper = new KeyStoreHelper();
         List<CertificateBean> ksBeans = keyStoreHelper.getCertificates(keyStoreBean.getKeyStore());
         keyStoreBean.setCertBeans(ksBeans);
         logger.debug("Certificate Size  :" + keyStoreBean.getCertBeans().size());
         for (CertificateBean cb : keyStoreBean.getCertBeans()) {
            logger.debug("Alais name in CertBean : " + cb.getAliasName());
         }
      } catch (KeyStoreException ex) {
         java.util.logging.Logger.getLogger(KeyStoreForm.class.getName()).log(Level.SEVERE, null, ex);
      }
   }

   public void onRowSelectCert(SelectEvent event) {
      logger.debug("SelectedCertBean Bean cert aliat name :" + selectedCertBean.getAliasName());
      selectedCertBean = (CertificateBean) event.getObject();
   }

   public void addKeyStore() throws Exception {
      logger.debug("start  public void addKeyStore()");

      if (ksInputStream != null || ksInputStream.available() >= 0) {

         try {
            /*    */
            KeyStoreHelper keyStoreHelper = new KeyStoreHelper();
            logger.info("Try to upload key store file.");
            logger.info("Key Store File Size : " + ksInputStream.available() + " bytes ");
            logger.info("Key Store name : " + ksFileName);
            logger.debug("Key Store password : " + password);
            KeyStoreBean ksBean = keyStoreHelper.getKeyStore(ksInputStream, ksFileName, password);
            User user = (User) ScopeController.getSessionMap("user");
            keyStoreService.insert(ksBean, user);
            initKeyStore();

            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Succesful", ksBean.getAliasName() + " is added.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            logger.info("Succesful" + ksBean.getAliasName() + " is added.");

         } catch (PropertyValueException ex) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error ", "Unknow key store file format!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            logger.info("Error Unknow key store file format!");
            logger.error("PropertyValueException : " + ex.getMessage());
            ex.printStackTrace();
         } catch (Exception ex) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Exception ", ex.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            logger.info("Exception ", ex.getMessage());
            logger.error("Exception : " + ex.getMessage());
            ex.printStackTrace();
         } finally {
            ksInputStream.close();
         }

      } else {
         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error ", "Select your key store file!");
         FacesContext.getCurrentInstance().addMessage(null, msg);
         logger.error("Select your key store file!");
      }

      logger.debug("end  public void addKeyStore()");
   }

   public void showCert() {

      logger.debug("Password : " + password);
      logger.debug("selectedCertBean Alias Name of KeyStore : " + selectedCertBean.getAliasName());
      try {
         if (selectedCertBean != null) {
            logger.debug("Certificate Alias Name : " + selectedCertBean.getAliasName());
            logger.debug("DN : " + selectedCertBean.getIssuerDN());
         } else {
            logger.debug("certBean is null  ");
         }
      } catch (Exception ex) {
         logger.debug("Excption : " + ex.getMessage());
         ex.printStackTrace();
      }
   }

   public void showKeyStore() {

      logger.debug("Password : " + password);
      logger.debug("Alias Name of KeyStore : " + selectedKeyStoreBean.getAliasName());
      try {
         if (selectedKeyStoreBean != null && selectedKeyStoreBean.getKeyStore() != null) {
            logger.debug("Content lenght : " + selectedKeyStoreBean.getContent().length);
         } else {
            logger.debug(" slectedKeyStoreBean is null  ");
         }
      } catch (Exception ex) {
         logger.debug("Excption : " + ex.getMessage());
         ex.printStackTrace();
      }
   }

   /*
   private void showAliasNames(KeyStoreBean ksBean) throws KeyStoreException {
   
   KeyStore keystore = ksBean.getKeyStore();
   Enumeration keyStoreEnum = keystore.aliases();
   while (keyStoreEnum.hasMoreElements()) {
   String alias = (String) keyStoreEnum.nextElement();
   boolean b = keystore.isKeyEntry(alias);
   // Does alias refer to a private key?
   if (b) {
   logger.debug("Key alias : " + alias);
   }
   // Does alias refer to a trusted certificate?
   b = keystore.isCertificateEntry(alias);
   if (b) {
   logger.debug("Certificate alias : " + alias);
   }
   }
   }
    */
   /**
    * @return the keyStoreBean
    */
   public KeyStoreBean getKeyStoreBean() {
      return keyStoreBean;
   }

   /**
    * @param keyStoreBean the keyStoreBean to set
    */
   public void setKeyStoreBean(KeyStoreBean keyStoreBean) {
      this.keyStoreBean = keyStoreBean;
   }

   /**
    * @return the password
    */
   public String getPassword() {
      return password;
   }

   /**
    * @param password the password to set
    */
   public void setPassword(String password) {
      this.password = password;
   }

   /**
    * @return the keyStoreFile
    */
   public String getKeyStoreFile() {
      return keyStoreFile;
   }

   /**
    * @param keyStoreFile the keyStoreFile to set
    */
   public void setKeyStoreFile(String keyStoreFile) {
      this.keyStoreFile = keyStoreFile;
   }

   /**
    * @return the keyStoreBeans
    */
   public List<KeyStoreBean> getKeyStoreBeans() {
      return keyStoreBeans;
   }

   /**
    * @param keyStoreBeans the keyStoreBeans to set
    */
   public void setKeyStoreBeans(List<KeyStoreBean> keyStoreBeans) {
      this.keyStoreBeans = keyStoreBeans;
   }

   /**
    * @return the keyStores
    */
   public Map<String, String> getKeyStores() {
      return keyStores;
   }

   /**
    * @param keyStores the keyStores to set
    */
   public void setKeyStores(Map<String, String> keyStores) {
      this.keyStores = keyStores;
   }

   /**
    * @return the aliasNames
    */
   public Map<String, String> getAliasNames() {
      return aliasNames;
   }

   /**
    * @param aliasNames the aliasNames to set
    */
   public void setAliasNames(Map<String, String> aliasNames) {
      this.aliasNames = aliasNames;
   }

   /**
    * @return the aliasName
    */
   public String getAliasName() {
      return aliasName;
   }

   /**
    * @param aliasName the aliasName to set
    */
   public void setAliasName(String aliasName) {
      this.aliasName = aliasName;
   }

   /**
    * @return the selectedCertBean
    */
   public CertificateBean getSelectedCertBean() {
      return selectedCertBean;
   }

   /**
    * @param selectedCertBean the selectedCertBean to set
    */
   public void setSelectedCertBean(CertificateBean selectedCertBean) {
      this.selectedCertBean = selectedCertBean;
   }

   /**
    * @return the selectedKeyStoreBean
    */
   public KeyStoreBean getSelectedKeyStoreBean() {
      return selectedKeyStoreBean;
   }

   /**
    * @param selectedKeyStoreBean the selectedKeyStoreBean to set
    */
   public void setSelectedKeyStoreBean(KeyStoreBean selectedKeyStoreBean) {
      this.selectedKeyStoreBean = selectedKeyStoreBean;
   }

   /**
    * @return the keyStoreName
    */
   public String getKeyStoreName() {
      return keyStoreName;
   }

   /**
    * @param keyStoreName the keyStoreName to set
    */
   public void setKeyStoreName(String keyStoreName) {
      this.keyStoreName = keyStoreName;
   }
}
