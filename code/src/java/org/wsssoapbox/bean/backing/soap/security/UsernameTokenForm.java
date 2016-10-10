/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.bean.backing.soap.security;

import com.sun.xml.wss.XWSSecurityException;
import com.sun.xml.wss.core.SecurityHeader;
import com.sun.xml.wss.core.Timestamp;
import com.sun.xml.wss.core.UsernameToken;
import com.sun.xml.wss.impl.SecurableSoapMessage;
import com.sun.xml.wss.impl.SecurityTokenException;
import java.io.IOException;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wsssoapbox.bean.backing.tool.PasswordDigest;
import org.wsssoapbox.bean.model.soap.SoapRequestBean;
import org.wsssoapbox.bean.model.soap.security.SecurityHeaderBean;
import org.wsssoapbox.bean.model.soap.security.TimeStampBean;
import org.wsssoapbox.bean.model.soap.security.UsernameTokenBean;
import org.wsssoapbox.soap.support.SoapConstants;
import org.wsssoapbox.soap.support.SoapCreator;
import org.wsssoapbox.view.util.ScopeController;
import org.wsssoapbox.xml.util.XMLUtils;
import org.xml.sax.SAXException;

/**
 *
 * @author Peter
 */
@ManagedBean(name = "tokenForm")
@SessionScoped
public class UsernameTokenForm implements Serializable {

   private static final long serialVersionUID = 4758678329733017054L;
   private static final Logger logger = LoggerFactory.getLogger(UsernameTokenForm.class);
   private SecurityHeaderBean secureHB;
   private UsernameTokenBean usernameTokenBean;
   private TimeStampBean tsBean;
   private String outputBox = "";
   private String passwordType;
   private Map<String, String> passwordTypes;
   private String passwordDigest;
   private String nonce;
   private String created;
   private String rawSoapMessage;
   private int timeStampCount = 1;

   public UsernameTokenForm() {
      secureHB = new SecurityHeaderBean();
      secureHB.setUsernameToken(true);
      usernameTokenBean = new UsernameTokenBean();
      tsBean = new TimeStampBean();
      passwordTypes = new HashMap();
      passwordTypes = SoapConstants.PASSWORD_TYPE;
   }

   public void addUsernameToken() throws Exception {

      System.out.println(" start public void add() ");
      System.out.println(" Password Type : " + passwordType);
      usernameTokenBean.setPasswordType(passwordType);

      SecurityHeader secureHeader = null;
      SOAPMessage soapMessage = null;
      SecurableSoapMessage secureMessage = null;
      SOAPElement usernameElement = null;
      SOAPElement timeStampElement = null;
      String regMsg = "";
      SoapRequestBean requestBean = null;

      try {

         requestBean = (SoapRequestBean) ScopeController.getSessionMap("soapRequestBean");
         soapMessage = MessageFactory.newInstance().createMessage();

         if (requestBean != null) {
            regMsg = requestBean.getSoapXMLFormat();
            System.out.println("Originall Message : " + regMsg);
            soapMessage = SoapCreator.createSOAPMessageFromString(regMsg, soapMessage);
            soapMessage.saveChanges();
         }

         secureMessage = new SecurableSoapMessage(soapMessage);
         secureHeader = createSecurityHeader(secureMessage, secureHB);

         if (secureHB.isUsernameToken()) {
            usernameElement = addUsernameToken(secureMessage, usernameTokenBean);
            secureHeader.addChildElement(usernameElement);
         }
         if (secureHB.isTimeStamp()) {
            // increte sequence of id 
            timeStampCount++;
            tsBean.setWsuId("Timestamp-" + timeStampCount);
            if (tsBean.getTimeout() == 0) {
               tsBean.setTimeout(60);
            }
            timeStampElement = addTimeStmap(tsBean);
            secureHeader.addChildElement(timeStampElement);
         }

         secureMessage.generateId();
         secureMessage.saveChanges();

         //   System.out.println("Securable Soap Message :");
         //   XMLUtils.print(secureMessage);

         // set flag to use secure message see SoapMessagesForm
         requestBean.setSecureSoapMessage(true);
         SoapRequestBean soapRequestBean = (SoapRequestBean) ScopeController.getSessionMap("soapRequestBean");
         System.out.println("is secure message : " + soapRequestBean.isSecureSoapMessage());
         rawSoapMessage = XMLUtils.getSoapMessageXML(soapMessage);
         outputBox = rawSoapMessage;

         if (passwordType.equalsIgnoreCase("PasswordDigest") && secureHB.isUsernameToken()) {
            try {
               String password = usernameTokenBean.getPasswordValue();

               Iterator it = usernameElement.getChildElements();
               while (it.hasNext()) {
                  SOAPElement soap = (SOAPElement) it.next();
                  System.out.println(soap.getElementQName());
               }

               QName userQName = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Created", "wsu");
               it = usernameElement.getChildElements(userQName);
               while (it.hasNext()) {
                  SOAPElement soap = (SOAPElement) it.next();
                  created = soap.getValue();
               }

               userQName = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "Nonce", "wsse");
               it = usernameElement.getChildElements(userQName);

               while (it.hasNext()) {
                  SOAPElement soap = (SOAPElement) it.next();
                  nonce = soap.getValue();
               }

               passwordDigest = PasswordDigest.generatePasswordDigest(nonce, created, password);

               System.out.println("");
               System.out.println("Passwordd Digetst : " + passwordDigest);
            } catch (NoSuchAlgorithmException ex) {
               FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Exception ", ex.getMessage());
               FacesContext.getCurrentInstance().addMessage(null, msg);
               logger.error("Exception " + ex.getMessage());
            }
         }

         requestBean.setSecureSoapMessageValue(rawSoapMessage);
         logger.debug("Raw SOAP Message : " + rawSoapMessage);

         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Succesful", "The Soap request message was created.");
         FacesContext.getCurrentInstance().addMessage(null, msg);

      } catch (ParserConfigurationException ex) {
         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ParserConfigurationException ", ex.getMessage());
         FacesContext.getCurrentInstance().addMessage(null, msg);
         logger.error("ParserConfigurationException " + ex.getMessage());
      } catch (SAXException ex) {
         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "SAXException ", ex.getMessage());
         FacesContext.getCurrentInstance().addMessage(null, msg);
         logger.error("SAXException " + ex.getMessage());
      } catch (IOException ex) {
         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "IOException ", ex.getMessage());
         FacesContext.getCurrentInstance().addMessage(null, msg);
         logger.error("IOException " + ex.getMessage());
      } catch (TransformerFactoryConfigurationError ex) {
         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "TransformerFactoryConfigurationError ", ex.getMessage());
         FacesContext.getCurrentInstance().addMessage(null, msg);
         logger.error("TransformerFactoryConfigurationError " + ex.getMessage());
      } catch (TransformerException ex) {
         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "TransformerException ", ex.getMessage());
         FacesContext.getCurrentInstance().addMessage(null, msg);
         logger.error("TransformerException " + ex.getMessage());
      } catch (SOAPException ex) {
         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "SOAPException ", ex.getMessage());
         FacesContext.getCurrentInstance().addMessage(null, msg);
         logger.error("SOAPException " + ex.getMessage());
      } catch (Exception ex) {
         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Exception ", ex.getMessage());
         FacesContext.getCurrentInstance().addMessage(null, msg);
         logger.error("Exception " + ex.getMessage());
      }




      System.out.println(" end public void add() ");
   }

   private SecurityHeader createSecurityHeader(SecurableSoapMessage secMessage, SecurityHeaderBean secureHB) throws XWSSecurityException {
      boolean mustUndstand = secureHB.isMustUnderstand();
      boolean doCteate = secureHB.isDoCreate();
      SecurityHeader secureHeader = secMessage.findWsseSecurityHeaderBlock(doCteate, mustUndstand);

      return secureHeader;
   }

   private SOAPElement addUsernameToken(SecurableSoapMessage secMessage, UsernameTokenBean uNameToken) throws SecurityTokenException {

      UsernameToken usernameToken;

      String userName = uNameToken.getUsernameValue();
      String password = uNameToken.getPasswordValue();
      boolean passwordDigestValue = uNameToken.isPasswordDigest();
      boolean nonceValue = uNameToken.isNonce();
      boolean createdValue = uNameToken.isCreated();
      usernameToken = new UsernameToken(secMessage.getSOAPPart(), userName, password, nonceValue, createdValue, passwordDigestValue);
      SOAPElement usernameElement = usernameToken.getAsSoapElement();

      System.out.println("Usernmae : " + uNameToken.getUsernameValue());
      System.out.println("Password : " + uNameToken.getPasswordValue());
      System.out.println("Password Digest: " + uNameToken.getPasswordDigestValue());
      System.out.println("Password Type: " + uNameToken.getPasswordType());
      System.out.println("Nonce Value : " + uNameToken.getNonceValue());
      System.out.println("Created  Value : " + uNameToken.getCreatedValue());
      System.out.println("Encoding Type : " + uNameToken.getNonceEncodingType());

      return usernameElement;

   }

   private SOAPElement addTimeStmap(TimeStampBean tsBean) throws XWSSecurityException {

      Timestamp timeStamp;
      SOAPElement timeStampElement;

      String id = tsBean.getWsuId();
      Long timeOut = tsBean.getTimeout();

      // convert to seconde 
      timeOut = timeOut * 60 * 1000;
      timeStamp = new Timestamp();
      timeStamp.setId(id);
      timeStamp.setTimeout(timeOut);
      timeStampElement = timeStamp.getAsSoapElement();

      return timeStampElement;
   }

   /**
    * @return the secureHB
    */
   public SecurityHeaderBean getSecureHB() {
      return secureHB;
   }

   /**
    * @param secureHB the secureHB to set
    */
   public void setSecureHB(SecurityHeaderBean secureHB) {
      this.secureHB = secureHB;
   }

   /**
    * @return the tsBean
    */
   public TimeStampBean getTsBean() {
      return tsBean;
   }

   /**
    * @param tsBean the tsBean to set
    */
   public void setTsBean(TimeStampBean tsBean) {
      this.tsBean = tsBean;
   }

   /**
    * @return the uNameTokenBean
    */
   public UsernameTokenBean getUsernameTokenBean() {
      return usernameTokenBean;
   }

   /**
    * @param uNameTokenBean the uNameTokenBean to set
    */
   public void setUsernameTokenBean(UsernameTokenBean uNameTokenBean) {
      this.usernameTokenBean = uNameTokenBean;
   }

   /**
    * @return the passwordDigest
    */
   public String getPasswordDigest() {
      return passwordDigest;
   }

   /**
    * @param passwordDigest the passwordDigest to set
    */
   public void setPasswordDigest(String passwordDigest) {
      this.passwordDigest = passwordDigest;
   }

   /**
    * @return the created
    */
   public String getCreated() {
      return created;
   }

   /**
    * @param created the created to set
    */
   public void setCreated(String created) {
      this.created = created;
   }

   /**
    * @return the nonce
    */
   public String getNonce() {
      return nonce;
   }

   /**
    * @param nonce the nonce to set
    */
   public void setNonce(String nonce) {
      this.nonce = nonce;
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
    * @return the passwordType
    */
   public String getPasswordType() {
      return passwordType;
   }

   /**
    * @param passwordType the passwordType to set
    */
   public void setPasswordType(String passwordType) {
      this.passwordType = passwordType;
   }

   /**
    * @return the passwordTyes
    */
   public Map<String, String> getPasswordTypes() {
      return passwordTypes;
   }

   /**
    * @param passwordTyes the passwordTyes to set
    */
   public void setPasswordTypes(Map<String, String> passwordTyes) {
      this.passwordTypes = passwordTyes;
   }

   /**
    * @return the outputBox
    */
   public String getOutputBox() {
      return outputBox;
   }

   /**
    * @param outputBox the outputBox to set
    */
   public void setOutputBox(String outputBox) {
      this.outputBox = outputBox;
   }
}
