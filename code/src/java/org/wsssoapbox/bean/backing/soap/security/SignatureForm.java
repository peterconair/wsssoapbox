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
import java.security.Key;
import java.security.KeyStore;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
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
import org.apache.ws.security.WSConstants;
import org.apache.ws.security.WSSConfig;
import org.apache.ws.security.WSSecurityException;
import org.apache.ws.security.components.crypto.Crypto;
import org.apache.ws.security.components.crypto.CryptoFactory;
import org.apache.ws.security.components.crypto.Merlin;
import org.apache.ws.security.message.WSSecHeader;
import org.apache.ws.security.message.WSSecSignature;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.wsssoapbox.bean.backing.soap.security.keystore.KeyStoreForm;
import org.wsssoapbox.bean.model.soap.SoapRequestBean;
import org.wsssoapbox.bean.model.soap.security.keystore.KeyStoreBean;
import org.wsssoapbox.bean.model.soap.security.SignatureBean;
import org.wsssoapbox.soap.support.SoapConstants;
import org.wsssoapbox.view.util.ScopeController;
import org.wsssoapbox.xml.util.XMLUtils;

/**
 *
 * @author Peter
 */
@ManagedBean(name = "signatureForm")
@ViewScoped
public class SignatureForm implements Serializable {

   private static final long serialVersionUID = -5271814614896983845L;
   private static final Logger logger = LoggerFactory.getLogger(SignatureForm.class);
   private String rawSoapMessage;
   private Map<String, String> sigAlgorithms;
   private Map<String, String> sigCononicalizations;
   private Map<String, String> keyIdTypes;
   private String keyStoreName;
   private String aliasName;
   private String password;
   private SignatureBean signedBean = new SignatureBean();
   private Crypto crypto;
   private Map<String, String> keyStores = new HashMap<String, String>();
   private Map<String, String> aliasNames = new HashMap<String, String>();
   private Map<String, Map<String, String>> aliasNamesData = new HashMap<String, Map<String, String>>();

   public SignatureForm() throws Exception {
      logger.debug("start public SignatureForm() throws Exception");
      sigAlgorithms = SoapConstants.SIGNATURE_ALGORITHM;
      sigCononicalizations = SoapConstants.SIGNATURE_CANNONICALIZATION;
      keyIdTypes = SoapConstants.KEY_IDENTIFIER_TYPES;
      logger.debug("end public SignatureForm() throws Exception");
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

   public void addSignature() {
      try {
         String regMsg = "";
         SoapRequestBean requestBean = null;
         KeyStoreBean keyStoreBean = null;
         KeyStore keyStore = null;
         byte[] content = new byte[1024];
     //    Key privateKey = null;
     //    X509Certificate certificate = null;
         String keyStoreFile = null;
         requestBean = (SoapRequestBean) ScopeController.getSessionMap("soapRequestBean");

         if (requestBean != null) {
            regMsg = requestBean.getSoapXMLFormat();
            logger.info("Originall Message : " + regMsg);
         }

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

         logger.debug("Key Store file Path : " + keyStoreFile);
         logger.info("Key Store name : " + keyStoreName);
         logger.info("Alias name : " + aliasName);
         logger.debug("Password : " + password);
         logger.info("Cannonicalization Method : " + signedBean.getSigCononicalization());
         logger.info("Signature Algorithm : " + signedBean.getSigAlgorithm());
         logger.info("Key Indentifier Type : " + signedBean.getKeyIdType());

         Properties merlinProp = new Properties();
         merlinProp.put("org.apache.ws.security.crypto.merlin.keystore.type", "JKS");
         merlinProp.put("org.apache.ws.security.crypto.merlin.file", keyStoreFile);
         merlinProp.put("org.apache.ws.security.crypto.merlin.keystore.password", password);
         crypto = CryptoFactory.getInstance(merlinProp);
         Merlin merlin = new Merlin();

         CertificateFactory certtFactory = merlin.getCertificateFactory();
         logger.debug("Cer Name : " + certtFactory.getProvider().getName());
         logger.debug("Cert Tyep : " + certtFactory.getType());
         crypto.setCertificateFactory("#X509", certtFactory);

         WSSecSignature signature = new WSSecSignature();
         signature.setUserInfo(aliasName, password);

         //<ds:Transform Algorithm="http://www.w3.org/2001/10/xml-exc-c14n#"/>
         if (!signedBean.getSigCononicalization().equals("")) {
            signature.setSigCanonicalization(signedBean.getSigCononicalization());
         } else { // default
            signature.setSigCanonicalization(WSConstants.C14N_WITH_COMMENTS);
         }

         if (!signedBean.getSigAlgorithm().equals("")) {
            signature.setSignatureAlgorithm(signedBean.getSigAlgorithm());
         } else {// defua
            signature.setSignatureAlgorithm(WSConstants.RSA_SHA1);
         }

         if (signedBean.getKeyIdType().equals(SoapConstants.BINARY_SEC_TOKEN)) {
            //**********************************
            // Binary Security Token Case
            //  System.out.println("Vertion :" + certificate.getVersion());
            // Create the certificate (<wsse:BinarySecurityToken>)
            // ValueType="xxxxx/oasis-200401-wss-x509-token-profile-1.0#X509(x)
            signature.setKeyIdentifierType(WSConstants.BST_DIRECT_REFERENCE);
         } else if (signedBean.getKeyIdType().equals(SoapConstants.ISSUER_NAME)) {
            /**********************************
            // Issuer Name and Serial Number Case :
            // Create (<ds:X509Data>) 
            //     <ds:X509Data>
            //             <ds:X509IssuerSerial>
            //                <ds:X509IssuerName>CN=Client Cert,OU=Client Department,O=Client Org.,L=Client City,ST=Client State,C=TH</ds:X509IssuerName>
            //               <ds:X509SerialNumber>1324563157</ds:X509SerialNumber>
            //            </ds:X509IssuerSerial>
            //         </ds:X509Data>
             */
            signature.setKeyIdentifierType(WSConstants.ISSUER_SERIAL);

         } else if (signedBean.getKeyIdType().equals(SoapConstants.X509_CERTIFICATE)) {

            //**********************************
            // X.509 Certificates:
            // Create (<wsse:KeyIdentifier>)  
            // ValueType="xxxxx/oasis-200401-wss-x509-token-profile-1.0#X509v(x)
            signature.setKeyIdentifierType(WSConstants.X509_KEY_IDENTIFIER);
         } else {
            /**********************************
            // Subjectkey and Identifer:
            // Create (<wsse:KeyIdentifier>) 
             * <wsse:KeyIdentifier EncodingType="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary" 
             * ValueType="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509SubjectKeyIdentifier">
             *             zy125LT9tgijX+9NOlhUAMvVMnA=
             * </wsse:KeyIdentifier>
             **/
            signature.setKeyIdentifierType(WSConstants.SKI_KEY_IDENTIFIER);

         }

         logger.debug("Before Signing IS....");
         Document doc = XMLUtils.createDocumentFromString(regMsg);
         WSSecHeader secHeader = new WSSecHeader();
         secHeader.insertSecurityHeader(doc);

         Document signedDoc = signature.build(doc, crypto, secHeader);
         logger.info("Signed message with : "+signedBean.getKeyIdType());
         rawSoapMessage = XMLUtils.PrettyDocumentToString(signedDoc);

         logger.debug("After Signing IS....");

         signedBean.setStrSecMessage(rawSoapMessage);
         requestBean.setSecureSoapMessage(true);
         requestBean.setSecureSoapMessageValue(rawSoapMessage);
         logger.info("Soap Message : ");
         logger.info(rawSoapMessage);


         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully", "The Soap request message was signed.");
         FacesContext.getCurrentInstance().addMessage(null, msg);
         logger.info("Successfully " + "The Soap request message was signed.");
         deleteKeyStoreFile(keyStoreFile);

      } catch (WSSecurityException ex) {
         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getCause().getClass().getName(), ex.getMessage());
         FacesContext.getCurrentInstance().addMessage(null, msg);
         logger.error("WSSecurityException " + ex.getMessage());
         ex.printStackTrace();
      }
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
         logger.debug(fileName + " Deletion of failed.");
      } else {
         logger.debug(fileName + " File deleted.");
      }
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
    * @return the signedBean
    */
   public SignatureBean getSignedBean() {
      return signedBean;
   }

   /**
    * @param signedBean the signedBean to set
    */
   public void setSignedBean(SignatureBean signedBean) {
      this.signedBean = signedBean;
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
}
