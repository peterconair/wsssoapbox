/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.bean.backing.soap.security;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.security.KeyStore;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.apache.ws.security.WSSConfig;
import org.apache.ws.security.components.crypto.Crypto;
import org.apache.ws.security.components.crypto.CryptoFactory;
import org.apache.ws.security.components.crypto.CryptoType;
import org.apache.ws.security.components.crypto.Merlin;
import org.apache.ws.security.message.WSSecHeader;
import org.apache.ws.security.message.WSSecSAMLToken;
import org.apache.ws.security.saml.ext.AssertionWrapper;
import org.apache.ws.security.saml.ext.SAMLParms;
import org.apache.ws.security.saml.ext.bean.ActionBean;
import org.apache.ws.security.saml.ext.bean.AuthDecisionStatementBean;
import org.apache.ws.security.saml.ext.bean.AuthenticationStatementBean;
import org.apache.ws.security.saml.ext.bean.ConditionsBean;
import org.apache.ws.security.saml.ext.bean.SubjectBean;
import org.apache.ws.security.saml.ext.bean.SubjectLocalityBean;
import org.apache.ws.security.saml.ext.builder.SAML2Constants;
import org.joda.time.DateTime;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.wsssoapbox.bean.backing.soap.security.keystore.KeyStoreForm;
import org.wsssoapbox.bean.model.soap.SoapRequestBean;
import org.wsssoapbox.bean.model.soap.security.keystore.KeyStoreBean;
import org.wsssoapbox.bean.model.soap.security.SAMLBean;
import org.wsssoapbox.bean.model.soap.security.support.CertConstants;
import org.wsssoapbox.soap.support.SoapConstants;
import org.wsssoapbox.soap.security.support.SAML2CallbackHandler;
import org.wsssoapbox.view.util.ScopeController;
import org.wsssoapbox.xml.util.XMLUtils;

/**
 *
 * @author Peter
 */
@ManagedBean(name = "samlAuthForm")
@ViewScoped
public class SAMLAuthtenticationForm implements Serializable {

   private static final long serialVersionUID = -5271814614896983845L;
   private static final Logger logger = LoggerFactory.getLogger(SAMLAuthtenticationForm.class);
   private Map<String, String> sigAlgorithms;
   private Map<String, String> sigCononicalizations;
   private Map<String, String> keyIdTypes;
   private Map<String, String> subConfirmationMethods;
   private Map<String, String> nameIdFormats;
   private Map<String, String> authContexts;
   private Map<String, String> verstions;
   private String keyStoreName;
   private String signPassword;
   private String password;
   private String aliasName;
   private boolean signed;
   private boolean holderOfKey;
   private SAMLBean samlBean;
   private Crypto crypto = null;
   private Map<String, String> keyStores = new HashMap<String, String>();
   private Map<String, String> aliasNames = new HashMap<String, String>();
   private Map<String, Map<String, String>> aliasNamesData = new HashMap<String, Map<String, String>>();

   public SAMLAuthtenticationForm() throws Exception {
      samlBean = new SAMLBean();
      sigAlgorithms = SoapConstants.SIGNATURE_ALGORITHM;
      sigCononicalizations = SoapConstants.SIGNATURE_CANNONICALIZATION;
      keyIdTypes = SoapConstants.KEY_IDENTIFIER_TYPES;
      nameIdFormats = SoapConstants.SAML_NAMEID_FORMATS;
      subConfirmationMethods = SoapConstants.SAML_SUB_CONFIRMATION_METHODS;
      authContexts = SoapConstants.SAML_AUTH_CONTEXTS;
      verstions = SoapConstants.SAML_VERSIONS;
   }

   static {
      WSSConfig.init();
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

   public void handleMethodChange() {
      logger.debug("Method : " + samlBean.getSubConfirmationMethod());
      if (samlBean.getSubConfirmationMethod().equals("Holder-of-Key")) {
         holderOfKey = true;
      } else {
         holderOfKey = false;
      }
      logger.debug("Holder-of-Key : " + holderOfKey);
   }

   public void addAuth() throws Exception {
      String regMsg = "";
      SoapRequestBean requestBean = null;
      KeyStoreBean keyStoreBean = null;
      KeyStore keyStore = null;
      String keyStoreFile = "";
      byte[] content = new byte[0];

      try {
         requestBean = (SoapRequestBean) ScopeController.getSessionMap("soapRequestBean");

         if (requestBean != null) {
            regMsg = requestBean.getSoapXMLFormat();
            System.out.println("Originall Message : " + regMsg);
         }

         if (samlBean.getIssuer().equals("")) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Please input issuer.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return;
         }
         if (samlBean.getSubjectName().equals("")) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Please input subject name.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return;
         }
         if (samlBean.getExpireInMin() <= 0) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Please input expire time.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return;
         }



         logger.debug("**************************************************");
         logger.info("SAML Version : " + samlBean.getVersion());
         logger.info("Issuer Name : " + samlBean.getIssuer());
         logger.info("Subject Confirmatin Method : " + samlBean.getSubConfirmationMethod());
         logger.info("Name Id format : " + samlBean.getNameIdFormat());
         logger.info("Subject Name t : " + samlBean.getSubjectName());
         logger.info("Expire In : " + samlBean.getExpireInMin());
         logger.info("Audience : " + samlBean.getAudience());
         logger.info("Authentication Context : " + samlBean.getAuthContext());

         if (signed || samlBean.getSubConfirmationMethod().equals("Holder-of-Key")) {

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
               logger.debug("Amount of Key Store  : " + keyStoreBeans.size() + " Key Stores");
               for (KeyStoreBean ksBean : keyStoreBeans) {
                  if (ksBean.getName().equals(keyStoreName)) {
                     keyStoreBean = ksBean;
                     keyStore = keyStoreBean.getKeyStore();
                     content = keyStoreBean.getContent();
                  }
               }
            }
            String serverPath = "/WEB-INF/config/keystores/tmp/users/";
            keyStoreFile = getKeyStorePath(content, keyStoreName, serverPath);
            keyStoreFile = getKeyStorePath(content, keyStoreName, serverPath);
            logger.debug("Key Store file Path : " + keyStoreFile);
            Properties merlinProp = new Properties();
            merlinProp.put("org.apache.ws.security.crypto.merlin.keystore.type", CertConstants.KEY_STORE_TYPE);
            merlinProp.put("org.apache.ws.security.crypto.merlin.file", keyStoreFile);
            merlinProp.put("org.apache.ws.security.crypto.merlin.keystore.password", password);
            crypto = CryptoFactory.getInstance(merlinProp);
            Merlin merlin = new Merlin();
            CertificateFactory certificateFactory = merlin.getCertificateFactory();
            crypto.setCertificateFactory("#X509", certificateFactory);
         }

         logger.debug("**************************************************");

         SAML2CallbackHandler callbackHandler = new SAML2CallbackHandler();

         callbackHandler.setStatement(SAML2CallbackHandler.Statement.AUTHN);

         // set Isuuer
         callbackHandler.setIssuer(samlBean.getIssuer());

         SubjectBean subjectBean = new SubjectBean();
         if (!samlBean.getSubjectName().equals("")) {
            subjectBean.setSubjectName(samlBean.getSubjectName());
         } else {
            subjectBean.setSubjectName("WSSOAPBox");
         }

         if (!samlBean.getNameIdFormat().equals("")) {
            subjectBean.setSubjectNameIDFormat(samlBean.getNameIdFormat());
         } else {
            subjectBean.setSubjectNameIDFormat(SAML2Constants.NAMEID_FORMAT_UNSPECIFIED);
         }


         if (samlBean.getSubConfirmationMethod().equals("Sender-Vouches")) {
            subjectBean.setSubjectConfirmationMethod(SAML2Constants.CONF_SENDER_VOUCHES);
         } else if (samlBean.getSubConfirmationMethod().equals("Holder-of-Key")) {
            subjectBean.setSubjectConfirmationMethod(SAML2Constants.CONF_HOLDER_KEY);
            CryptoType cryptoType = new CryptoType(CryptoType.TYPE.ALIAS);
            cryptoType.setAlias(aliasName);
            X509Certificate[] certificates = crypto.getX509Certificates(cryptoType);
            callbackHandler.setCerts(certificates);
         } else {// default "urn:oasis:names:tc:SAML:2.0:cm:bearer"
            subjectBean.setSubjectConfirmationMethod(SAML2Constants.CONF_BEARER);
         }
         callbackHandler.setSubjectBean(subjectBean);

         ConditionsBean conditionsBean = new ConditionsBean();
         try {
            int min = samlBean.getExpireInMin();
            conditionsBean.setTokenPeriodMinutes(min);
         } catch (NumberFormatException ex) { // default 10 min. 
            conditionsBean.setTokenPeriodMinutes(10);
         }
         conditionsBean.setAudienceURI(samlBean.getAudience());
         callbackHandler.setConditions(conditionsBean);

         //Authentication Statements
         String authContext = samlBean.getAuthContext();
         String ipAddress = "";
         String dnsAddress = "";

         AuthenticationStatementBean authBean = addAuthenticationStatement(authContext, ipAddress, dnsAddress, subjectBean);
         callbackHandler.setAuthBean(authBean);

       //  callbackHandler.setAuthBean(authBean);

         SAMLParms samlParms = new SAMLParms();
         samlParms.setCallbackHandler(callbackHandler);
         AssertionWrapper assertion = new AssertionWrapper(samlParms);

         if (signed) {
            assertion.signAssertion(aliasName, password, crypto, true);
         }

         WSSecSAMLToken wsSecSAMLToken = new WSSecSAMLToken();
         Document doc = XMLUtils.createDocumentFromString(regMsg);
         WSSecHeader secHeader = new WSSecHeader();
         secHeader.insertSecurityHeader(doc);

         Document soapSecDoc = wsSecSAMLToken.build(doc, assertion, secHeader);

         logger.debug("SAML 2 Authn Assertion :");
         String rawSoapMessage = XMLUtils.PrettyDocumentToString(soapSecDoc);

         rawSoapMessage = XMLUtils.prettyPrint(rawSoapMessage);
         samlBean.setStrSecMessage(rawSoapMessage);
         requestBean.setSecureSoapMessage(true);
         requestBean.setSecureSoapMessageValue(rawSoapMessage);
         logger.debug("Soap Message : ");
         logger.debug(rawSoapMessage);
         logger.debug("\nxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");

         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Succesful", "The Soap request message was created.");
         FacesContext.getCurrentInstance().addMessage(null, msg);
         deleteKeyStoreFile(keyStoreFile);



      } catch (Exception ex) {
         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ex.getMessage());
         FacesContext.getCurrentInstance().addMessage(null, msg);
         ex.printStackTrace();
      }
   }

   private AuthenticationStatementBean addAuthenticationStatement(String authContext, String ipAddress, String dnsAddress, SubjectBean subjectBean) {
      AuthenticationStatementBean authBean = new AuthenticationStatementBean();
      authBean.setAuthenticationInstant(new DateTime());
      authBean.setSubject(subjectBean);
      if (!ipAddress.equals("") || !dnsAddress.equals("")) {
         SubjectLocalityBean subjectLocality = new SubjectLocalityBean();
         subjectLocality.setIpAddress(ipAddress);
         subjectLocality.setDnsAddress(dnsAddress);
         authBean.setSubjectLocality(subjectLocality);
      }
      if (!authContext.equals("")) {
         authBean.setAuthenticationMethod(authContext);
      } else {
         authBean.setAuthenticationMethod("unspecified");
      }
      return authBean;
   }

   private AuthDecisionStatementBean addAuthDecisionStatement(String resource, String action, String actionNamespace, AuthDecisionStatementBean.Decision decision, Object evidence, SubjectBean subjectBean) {


      AuthDecisionStatementBean authzBean = new AuthDecisionStatementBean();
      ActionBean actionBean = new ActionBean();
      actionBean.setActionNamespace(actionNamespace);
      actionBean.setContents(action);

      authzBean.setActions(Collections.singletonList(actionBean));
      authzBean.setDecision(decision);
      authzBean.setResource(resource);
      authzBean.setEvidence(evidence);

      authzBean.setSubject(subjectBean);

      return authzBean;

   }

   private String getKeyStorePath(byte data[], String fileName, String path) {
      String fileOut = null;

      if (data.length > 0) {

         ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

         HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
         String userSessionId = session.getId();
         fileName += userSessionId + ".jks";
         String serverPath = servletContext.getRealPath(path);
         fileOut = serverPath + File.separatorChar + fileName;
         logger.debug("Server path : " + serverPath);
         logger.debug("File will be create  path : " + fileOut);
         OutputStream out = null;
         try {
            out = new FileOutputStream(fileOut);
            IOUtils.write(data, out);
         } catch (FileNotFoundException ex) {
            ex.printStackTrace();
         } catch (IOException ex) {
            ex.printStackTrace();
         } catch (Exception ex) {
            ex.printStackTrace();
         } finally {
            if (out != null) {
               try {
                  out.flush();
                  out.close();
               } catch (IOException ex) {
                  ex.printStackTrace();
               }
            }
         }
      }
      return fileOut;
   }

   private void deleteKeyStoreFile(String fileName) {
      File file = new File(fileName);
      boolean success = file.delete();
      if (!success) {
         logger.debug(fileName + " Deletion failed.");
      } else {
         logger.debug(fileName + " File deleted.");
      }
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
    * @return the sigAlgorithms
    */
   public Map<String, String> getSigAlgorithms() {
      return sigAlgorithms;
   }

   /**
    * @param sigAlgorithms the sigAlgorithms to set
    */
   public void setSigAlgorithms(Map<String, String> sigAlgorithms) {
      this.sigAlgorithms = sigAlgorithms;
   }

   /**
    * @return the sigCononicalizations
    */
   public Map<String, String> getSigCononicalizations() {
      return sigCononicalizations;
   }

   /**
    * @param sigCononicalizations the sigCononicalizations to set
    */
   public void setSigCononicalizations(Map<String, String> sigCononicalizations) {
      this.sigCononicalizations = sigCononicalizations;
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
    * @return the samlBean
    */
   public SAMLBean getSamlBean() {
      return samlBean;
   }

   /**
    * @param samlBean the samlBean to set
    */
   public void setSamlBean(SAMLBean samlBean) {
      this.samlBean = samlBean;
   }

   /**
    * @return the subConfirmationMethods
    */
   public Map<String, String> getSubConfirmationMethods() {
      return subConfirmationMethods;
   }

   /**
    * @param subConfirmationMethods the subConfirmationMethods to set
    */
   public void setSubConfirmationMethods(Map<String, String> subConfirmationMethods) {
      this.subConfirmationMethods = subConfirmationMethods;
   }

   /**
    * @return the authContexts
    */
   public Map<String, String> getAuthContexts() {
      return authContexts;
   }

   /**
    * @param authContexts the authContexts to set
    */
   public void setAuthContexts(Map<String, String> authContexts) {
      this.authContexts = authContexts;
   }

   /**
    * @return the verstions
    */
   public Map<String, String> getVerstions() {
      return verstions;
   }

   /**
    * @param verstions the verstions to set
    */
   public void setVerstions(Map<String, String> verstions) {
      this.verstions = verstions;
   }

   /**
    * @return the nameIdFormats
    */
   public Map<String, String> getNameIdFormats() {
      return nameIdFormats;
   }

   /**
    * @param nameIdFormats the nameIdFormats to set
    */
   public void setNameIdFormats(Map<String, String> nameIdFormats) {
      this.nameIdFormats = nameIdFormats;
   }

   /**
    * @return the signPassword
    */
   public String getSignPassword() {
      return signPassword;
   }

   /**
    * @param signPassword the signPassword to set
    */
   public void setSignPassword(String signPassword) {
      this.signPassword = signPassword;
   }

   /**
    * @return the signed
    */
   public boolean isSigned() {
      return signed;
   }

   /**
    * @param signed the signed to set
    */
   public void setSigned(boolean signed) {
      this.signed = signed;
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
    * @return the holderOfKey
    */
   public boolean isHolderOfKey() {
      return holderOfKey;
   }

   /**
    * @param holderOfKey the holderOfKey to set
    */
   public void setHolderOfKey(boolean holderOfKey) {
      this.holderOfKey = holderOfKey;
   }
}
