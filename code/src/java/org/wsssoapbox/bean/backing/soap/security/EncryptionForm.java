/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.bean.backing.soap.security;

import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import org.apache.xml.security.Init;
import org.apache.xml.security.encryption.XMLEncryptionException;
import com.sun.xml.wss.impl.SecurableSoapMessage;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.KeyStore;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.xpath.XPathExpressionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.wsssoapbox.bean.model.soap.SoapPartBean;
import org.wsssoapbox.bean.model.soap.SoapRequestBean;
import org.wsssoapbox.bean.model.soap.SoapResponseBean;
import org.wsssoapbox.bean.model.soap.security.EncrytionBean;
import org.wsssoapbox.bean.model.soap.security.keystore.KeyStoreBean;
import org.wsssoapbox.soap.support.SoapConstants;
import org.wsssoapbox.soap.support.SoapCreator;
import org.wsssoapbox.soap.security.CertUtil;
import org.wsssoapbox.soap.security.crypto.SoapCrypto;
import org.wsssoapbox.soap.security.crypto.SoapDecryption;
import org.wsssoapbox.soap.security.crypto.SoapEncryption;
import org.wsssoapbox.soap.security.cypto.xpath.SoapCryptoDAO;
import org.wsssoapbox.soap.security.cypto.xpath.SoapDecryptionXPath;
import org.wsssoapbox.view.util.ScopeController;
import org.wsssoapbox.xml.util.XMLUtils;

/**
 *
 * @author Peter
 */
@ManagedBean(name = "encryptionForm")
@ViewScoped
public class EncryptionForm implements Serializable {

   private static final long serialVersionUID = -5271814614896983845L;
   private static final Logger logger = LoggerFactory.getLogger(EncryptionForm.class);
   private String rawSoapMessage;
   private Map<String, String> encCononicalizations;
   private Map<String, String> keyIdTypes;
   private Map<String, String> keyEncryptionAlgs;
   private Map<String, String> symmetricEncAlgs;
   private Map<String, String> encryptionParts;
   private String encryptionPart;
   private Map<String, String> tagNames;
   private String tagName;
   private EncrytionBean encBean;
   private int encDataId = 1;
   private SoapCryptoDAO soapCryptoDAO;
   private SOAPMessage soapMessage;
   private SoapRequestBean requestBean;
   private SoapResponseBean responseBean;
   private List<SoapPartBean> soapPartBeans;
   private boolean decrypted;
   private String keyStoreName;
   private String password;
   private String aliasName;
   private KeyStoreBean keyStoreBean;
   private Map<String, String> keyStores = new HashMap<String, String>();
   private Map<String, String> aliasNames = new HashMap<String, String>();
   private Map<String, Map<String, String>> aliasNamesData = new HashMap<String, Map<String, String>>();

   static {
      Init.init();
   }

   public EncryptionForm() throws Exception {

      logger.debug("Start  public EncryptionForm()");
      keyIdTypes = SoapConstants.KEY_IDENTIFIER_TYPES;
      symmetricEncAlgs = SoapConstants.SYMMETRIC_ENCODING_ALGORITHMS;
      keyEncryptionAlgs = SoapConstants.KEY_ENCRYPTION_ALGORITHMS;
      encCononicalizations = SoapConstants.ENCRYPTION_CANNONICALIZATION;
      encryptionParts = SoapConstants.ENCRYPTION_PARTS;
      encBean = new EncrytionBean();
      tagNames = new HashMap<String, String>();
      soapPartBeans = new ArrayList<SoapPartBean>();
      //  initKeyStore();
      //  loadTagNames();
      logger.debug("End  public EncryptionForm()");
   }

   @PostConstruct
   public void initKeyStore() {
      logger.debug("Start  public initKeyStore()");
      keyStores = new HashMap<String, String>();
      aliasNamesData = new HashMap<String, Map<String, String>>();
      List<KeyStoreBean> ksBeans = (List<KeyStoreBean>) ScopeController.getSessionMap("keyStoreBeans");
      if (ksBeans != null) {
         for (KeyStoreBean ksBean : ksBeans) {
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
      }
      logger.debug("End  public initKeyStore()");
   }

   public void handleKeyStoreChange() {
      logger.debug("Start  public void handleKeyStoreChange()");
      if (keyStoreName != null && !keyStoreName.equals("")) {
         aliasNames = aliasNamesData.get(keyStoreName);
         logger.debug("You select keystore : " + keyStoreName);
      } else {
         aliasNames = new HashMap<String, String>();
      }
      logger.debug("End  public void handleKeyStoreChange()");
   }

   @PostConstruct
   private void constructSoapMessage() {
      try {
         logger.debug("Start  private void createSoapMessage()");
         requestBean = (SoapRequestBean) ScopeController.getSessionMap("soapRequestBean");
         soapMessage = MessageFactory.newInstance().createMessage();
         if (requestBean != null) {
            String regMsg = requestBean.getSoapXMLFormat();
            logger.debug("Originall Message : " + regMsg);
            soapMessage = SoapCreator.createSOAPMessageFromString(regMsg, soapMessage);
            soapMessage.saveChanges();
         }
         logger.debug("End  private void createSoapMessage()");
      } catch (UnsupportedEncodingException ex) {
         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "UnsupportedEncodingException ", ex.getMessage());
         FacesContext.getCurrentInstance().addMessage(null, msg);
         logger.error("UnsupportedEncodingException " + ex.getMessage());
      } catch (SOAPException ex) {
         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "SOAPException ", ex.getMessage());
         FacesContext.getCurrentInstance().addMessage(null, msg);
         logger.error("SOAPException " + ex.getMessage());
      }
      logger.debug("End  private void createSoapMessage()");
   }

   private void loadTagNames() {
      try {
         logger.debug("Start private void loadTagNames()");
         soapCryptoDAO = new SoapDecryptionXPath();
         setTagNames(new HashMap<String, String>());
         constructSoapMessage();
         if (soapMessage != null) {
            logger.debug("SOAP NOT NULL");
            List<Element> elements = soapCryptoDAO.findAllElementInBody(soapMessage);
            if (elements.size() > 0 || elements != null) {
               for (Element e : elements) {
                  logger.debug("All Tag Name : " + e.getTagName());
                  getTagNames().put(e.getTagName(), e.getTagName());
               }
            }
         }
         logger.debug("End private void loadTagNames()");
      } catch (XPathExpressionException ex) {
         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "XPathExpressionException ", ex.getMessage());
         FacesContext.getCurrentInstance().addMessage(null, msg);
         logger.error("XPathExpressionException " + ex.getMessage());
      } catch (XMLEncryptionException ex) {
         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "XMLEncryptionException ", ex.getMessage());
         FacesContext.getCurrentInstance().addMessage(null, msg);
         logger.error("XMLEncryptionException " + ex.getMessage());
      } catch (SOAPException ex) {
         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "SOAPException ", ex.getMessage());
         FacesContext.getCurrentInstance().addMessage(null, msg);
         logger.error("SOAPException " + ex.getMessage());
      }
   }

   public void encryptMessage() {
      try {
         logger.debug("Start public void addEncryption()");
         //Init.init();

         SOAPPart soapPart = null;
         SOAPEnvelope soapEnvelope = null;
         SOAPBody soapBody = null;
         SOAPHeader soapHeader = null;
         SecurableSoapMessage secureMessage = null;
         String regMsg = "";

         KeyStore keyStore = null;
         Key privateKey = null;
         X509Certificate certificate = null;

         String keyIndentifierType = encBean.getKeyIdType();
         String symmetricEncAlg = encBean.getSymmetricEncAlg();
         String keyEncAlg = encBean.getKeyEncryptionAlg();
         String canonicalization = encBean.getEncCononicalization();


         if (keyStoreName.equals("")) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Please select key store.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return;
         }
         if (aliasName.equals("")) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Please select alias name.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return;
         }
         if (password.equals("")) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Please input password.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return;
         }
         List<KeyStoreBean> keyStoreBeans = (List<KeyStoreBean>) ScopeController.getSessionMap("keyStoreBeans");

         if (keyStoreBeans != null) {
            for (KeyStoreBean ksBean : keyStoreBeans) {
               if (ksBean.getName().equals(getKeyStoreName())) {
                  keyStoreBean = ksBean;
                  // logger.debug("KeyStreBean Key Store Name : " + ksBean.getName());
                  // logger.debug("KeyStreBean Alias Name : " + ksBean.getAliasName());
               }
            }
         } else { // Key Store will replace with KeyStoreDAO             
            String keyStoreFile = "F:/Develope/sourecode/netbeans/J2EE/WS-SSOAPBox/web/WEB-INF/config/keysotres/client/client.jks";
            keyStore = CertUtil.loadKeyStore("JKS", keyStoreFile, password);
            logger.debug("Key Store File : " + keyStoreFile);
            aliasName = "client_keyStore";
            keyStoreName = "client.jks";
         }

         ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
         String path = servletContext.getRealPath("/WEB-INF/config/keysotres/client/client.jks");

         logger.debug("Context Name : " + FacesContext.getCurrentInstance().getExternalContext().getContextName());
         logger.debug("Path : " + path);

         logger.debug("Key Store Password : " + getPassword());
         logger.info("Key Store Name : " + getAliasName());
         logger.info("Alias Name : " + getAliasName());
         logger.info("Key KeyIndertifier Type  : " + keyIndentifierType);
         logger.info("Symmetric Encrytpion Alg  : " + symmetricEncAlg);
         logger.info("Key Encryption Alg  : " + keyEncAlg);

         keyStore = keyStoreBean.getKeyStore();

         certificate = (X509Certificate) keyStore.getCertificate(aliasName);
         privateKey = keyStore.getKey(aliasName, password.toCharArray());
         // load  and create soap messages
         constructSoapMessage();

         soapPart = soapMessage.getSOAPPart();
         soapEnvelope = soapPart.getEnvelope();
         if (soapMessage.getSOAPHeader() == null) {
            soapHeader = soapEnvelope.addHeader();
         }
         soapBody = soapMessage.getSOAPBody();
         secureMessage = new SecurableSoapMessage(soapMessage);

         String encDataIdStr = "EncDataId-" + encDataId;
         String encKeyId = "#EncKeyId-" + UUID.randomUUID().toString();

         // System.out.println(XMLUtils.prettyPrint(soap));

         SoapCrypto se = new SoapEncryption(encDataIdStr);

         logger.debug("Encryption Parts : " + soapPartBeans.size());
         for (SoapPartBean sp : soapPartBeans) {
            logger.debug("Id :" + sp.getContentId());
            logger.debug("Tag Name :" + sp.getTagName());
            logger.debug("Content :" + sp.isContent());
         }

         if (soapPartBeans.size() > 0) {
            /*    */
            List<SoapPartBean> parts = soapCryptoDAO.findAllSoapPartsInBody(soapMessage);
            List<SoapPartBean> soapParts = new ArrayList<SoapPartBean>();
            for (SoapPartBean sp : parts) {
               for (SoapPartBean spBean : soapPartBeans) {
                  if (spBean.getTagName().equals(sp.getTagName())) {
                     sp.setContentId(spBean.getContentId());
                     sp.setContent(spBean.isContent());
                     soapParts.add(sp);
                  }
               }
            }
            rawSoapMessage = se.encryptSoapParts(soapMessage, soapParts, keyEncAlg, keyIndentifierType, symmetricEncAlg, certificate);
         } else {
            rawSoapMessage = se.encryptSoap(soapMessage, keyEncAlg, keyIndentifierType, symmetricEncAlg, certificate);
         }

         rawSoapMessage = XMLUtils.getSoapMessageXML(soapMessage);
         encBean.setStrSecMessage(rawSoapMessage);
         requestBean.setSecureSoapMessage(true);
         requestBean.setSoapTextFormat(XMLUtils.getSoapMessageString(soapMessage));
         requestBean.setSecureSoapMessageValue(rawSoapMessage);
         // hide decrypted message
         decrypted = false;

         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Succesful", "The Soap request message was encrypted.");
         FacesContext.getCurrentInstance().addMessage(null, msg);


         logger.info("Soap Message : " + XMLUtils.prettyPrint(rawSoapMessage));
         logger.debug("End public void addEncryption()");


      } catch (NoSuchAlgorithmException ex) {
         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "NoSuchAlgorithmException ", ex.getMessage());
         FacesContext.getCurrentInstance().addMessage(null, msg);
         logger.error("NoSuchAlgorithmException " + ex.getMessage());
      } catch (UnrecoverableKeyException ex) {
         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "UnrecoverableKeyException ", ex.getMessage());
         FacesContext.getCurrentInstance().addMessage(null, msg);
         logger.error("UnrecoverableKeyException " + ex.getMessage());
      } catch (KeyStoreException ex) {
         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "KeyStoreException ", ex.getMessage());
         FacesContext.getCurrentInstance().addMessage(null, msg);
         logger.error("KeyStoreException " + ex.getMessage());
      } catch (NullPointerException ex) {
         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "NullPointerException ", ex.getMessage());
         FacesContext.getCurrentInstance().addMessage(null, msg);
         logger.error("NullPointerException " + ex.getMessage());
         ex.printStackTrace();
      } catch (Exception ex) {
         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Exception ", ex.getMessage());
         FacesContext.getCurrentInstance().addMessage(null, msg);
         logger.error("Exception " + ex.getMessage());
         ex.printStackTrace();
      }

   }

   public void addEncryptParts() {
      logger.debug("Start public void addEncryptParts()");
      String encDataIdStr = "EncDataId-" + encDataId;
      SoapPartBean soapPartBean = new SoapPartBean();


      soapPartBean.setTagName(tagName);
      if (encryptionPart.equals("Element")) {
         soapPartBean.setContent(false);
      } else {
         soapPartBean.setContent(true);
      }
      if (!soapPartBeans.contains(soapPartBean)) {
         soapPartBean.setContentId(encDataIdStr);
         encDataId++;
         soapPartBeans.add(soapPartBean);
      }
      logger.debug("End public void addEncryptParts()");
   }

   public String deleteEncryptParts(SoapPartBean soapPartBean) {
      logger.debug("Start public String deleteEncryptParts(SoapPartBean soapPartBean) ");

      for (SoapPartBean sp : soapPartBeans) {
         logger.debug("Before TagName :" + sp.getTagName());
      }
      this.soapPartBeans.remove(soapPartBean);
      for (SoapPartBean sp : soapPartBeans) {
         logger.debug("TagName :" + sp.getTagName());
      }
      FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "you removed parts ", soapPartBean.getTagName());
      FacesContext.getCurrentInstance().addMessage(null, message);
      logger.debug("End public String deleteEncryptParts(SoapPartBean soapPartBean) ");

      return null;
   }

   public void decryptMesssage() throws Exception {
      logger.debug("Start public void decryption()");

      String serverPassword = "123456";
      String serverAliasName = "client_cert";
      String serverFile = "F:/Develope/sourecode/netbeans/Web_Service/Prodiver/ws-payment-btoken/src/main/webapp/WEB-INF/config/certificates/server/server.jks";
      KeyStore severKeyStore = CertUtil.loadKeyStore("JKS", serverFile, serverPassword);
      X509Certificate serverCertificate = (X509Certificate) severKeyStore.getCertificate(serverAliasName);
      PublicKey serverPublicKey = serverCertificate.getPublicKey();
      Key serverPrivateKey = severKeyStore.getKey("server_keystore", serverPassword.toCharArray());


      logger.debug("Key Store Password : " + serverPassword);
      logger.debug("Key Store Alias Name : " + serverAliasName);
      logger.debug("Key Store File : " + serverFile);

      // Key Store will replace with KeyStoreDAO 


      responseBean = (SoapResponseBean) ScopeController.getSessionMap("soapResponseBean");
      String responseMessage = responseBean.getSoapTextFormat();



      logger.debug("Encrypted Message :" + responseMessage);

      Init.init();
      SoapCrypto soapCrypto = new SoapDecryption();
      String decMessage = soapCrypto.decryptSoap(responseMessage, serverPrivateKey, true);
      responseBean.setSoapDecryptedMsg(decMessage);
      decrypted = true;
      logger.debug("Decrypted Message: " + responseBean.getSoapDecryptedXML());

      logger.debug("End public void decryption()");
   }

   /**
    * @return the rawSoapMessage
    */
   public String getRawSoapMessage() {
      return rawSoapMessage;
   }

   /**
    * @param rawSoapMessage the rawSoapMessage to set
    */
   public void setRawSoapMessage(String rawSoapMessage) {
      this.rawSoapMessage = rawSoapMessage;
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
    * @return the encCononicalizations
    */
   public Map<String, String> getEncCononicalizations() {
      return encCononicalizations;
   }

   /**
    * @param encCononicalizations the encCononicalizations to set
    */
   public void setEncCononicalizations(Map<String, String> encCononicalizations) {
      this.encCononicalizations = encCononicalizations;
   }

   /**
    * @return the keyIdTypes
    */
   public Map<String, String> getKeyIdTypes() {
      return keyIdTypes;
   }

   /**
    * @param keyIdTypes the keyIdTypes to set
    */
   public void setKeyIdTypes(Map<String, String> keyIdTypes) {
      this.keyIdTypes = keyIdTypes;
   }

   /**
    * @return the keyEncryptionAlgs
    */
   public Map<String, String> getKeyEncryptionAlgs() {
      return keyEncryptionAlgs;
   }

   /**
    * @param keyEncryptionAlgs the keyEncryptionAlgs to set
    */
   public void setKeyEncryptionAlgs(Map<String, String> keyEncryptionAlgs) {
      this.keyEncryptionAlgs = keyEncryptionAlgs;
   }

   /**
    * @return the symmetricEncAlgs
    */
   public Map<String, String> getSymmetricEncAlgs() {
      return symmetricEncAlgs;
   }

   /**
    * @param symmetricEncAlgs the symmetricEncAlgs to set
    */
   public void setSymmetricEncAlgs(Map<String, String> symmetricEncAlgs) {
      this.symmetricEncAlgs = symmetricEncAlgs;
   }

   /**
    * @return the encBean
    */
   public EncrytionBean getEncBean() {
      return encBean;
   }

   /**
    * @param encBean the encBean to set
    */
   public void setEncBean(EncrytionBean encBean) {
      this.encBean = encBean;
   }

   /**
    * @return the encDataId
    */
   public int getEncDataId() {
      return encDataId++;
   }

   /**
    * @param encDataId the encDataId to set
    */
   public void setEncDataId(int encDataId) {
      this.encDataId = encDataId;
   }

   /**
    * @return the encryptionParts
    */
   public Map<String, String> getEncryptionParts() {
      return encryptionParts;
   }

   /**
    * @param encryptionParts the encryptionParts to set
    */
   public void setEncryptionParts(Map<String, String> encryptionParts) {
      this.encryptionParts = encryptionParts;
   }

   /**
    * @return the encryptionPart
    */
   public String getEncryptionPart() {
      return encryptionPart;
   }

   /**
    * @param encryptionPart the encryptionPart to set
    */
   public void setEncryptionPart(String encryptionPart) {
      this.encryptionPart = encryptionPart;
   }

   /**
    * @return the tagName
    */
   public String getTagName() {
      return tagName;
   }

   /**
    * @param tagName the tagName to set
    */
   public void setTagName(String tagName) {
      this.tagName = tagName;
   }

   /**
    * @return the soapPartBeans
    */
   public List<SoapPartBean> getSoapPartBeans() {
      return soapPartBeans;
   }

   /**
    * @param soapPartBeans the soapPartBeans to set
    */
   public void setSoapPartBeans(List<SoapPartBean> soapPartBeans) {
      this.soapPartBeans = soapPartBeans;
   }

   /**
    * @return the tagNames
    */
   public Map<String, String> getTagNames() {
      return tagNames;
   }

   /**
    * @param tagNames the tagNames to set
    */
   public void setTagNames(Map<String, String> tagNames) {
      this.tagNames = tagNames;
   }

   /**
    * @return the responseBean
    */
   public SoapResponseBean getResponseBean() {
      return responseBean;
   }

   /**
    * @param responseBean the responseBean to set
    */
   public void setResponseBean(SoapResponseBean responseBean) {
      this.responseBean = responseBean;
   }

   /**
    * @return the decrypted
    */
   public boolean isDecrypted() {
      return decrypted;
   }

   /**
    * @param decrypted the decrypted to set
    */
   public void setDecrypted(boolean decrypted) {
      this.decrypted = decrypted;
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

   @Override
   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      }
      if (getClass() != obj.getClass()) {
         return false;
      }
      final EncryptionForm other = (EncryptionForm) obj;
      if (this.keyStores != other.keyStores && (this.keyStores == null || !this.keyStores.equals(other.keyStores))) {
         return false;
      }
      if (this.aliasNamesData != other.aliasNamesData && (this.aliasNamesData == null || !this.aliasNamesData.equals(other.aliasNamesData))) {
         return false;
      }
      return true;
   }

   @Override
   public int hashCode() {
      int hash = 3;
      hash = 61 * hash + (this.keyStores != null ? this.keyStores.hashCode() : 0);
      hash = 61 * hash + (this.aliasNames != null ? this.aliasNames.hashCode() : 0);
      return hash;
   }

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
}
