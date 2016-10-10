/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.bean.model.soap.security;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Peter
 */
@ManagedBean(name = "samlBean")
public class SAMLBean implements Serializable {

   private static final long serialVersionUID = 1L;
   private String keyStore;
   private String sigAlgorithm;
   private String keyIdType;
   private String version;
   private String subConfirmationMethod;
   private String issuer;
   private String nameIdFormat;
   private String subjectName;
   private String audience;
   private String authContext;
   private int expireInMin;
   private String sigCononicalization;
   private String strSecMessage;
   private String action;
   private String resource;
   private String ipAddress;
   private String dnsAddress;

   /**
    * @return the keyStore
    */
   public String getKeyStore() {
      return keyStore;
   }

   /**
    * @param keyStore the keyStore to set
    */
   public void setKeyStore(String keyStore) {
      this.keyStore = keyStore;
   }

   /**
    * @return the sigAlgorithm
    */
   public String getSigAlgorithm() {
      return sigAlgorithm;
   }

   /**
    * @param sigAlgorithm the sigAlgorithm to set
    */
   public void setSigAlgorithm(String sigAlgorithm) {
      this.sigAlgorithm = sigAlgorithm;
   }

   /**
    * @return the keyIdType
    */
   public String getKeyIdType() {
      return keyIdType;
   }

   /**
    * @param keyIdType the keyIdType to set
    */
   public void setKeyIdType(String keyIdType) {
      this.keyIdType = keyIdType;
   }

   /**
    * @return the sigCononicalization
    */
   public String getSigCononicalization() {
      return sigCononicalization;
   }

   /**
    * @param sigCononicalization the sigCononicalization to set
    */
   public void setSigCononicalization(String sigCononicalization) {
      this.sigCononicalization = sigCononicalization;
   }

   /**
    * @return the strSecMessage
    */
   public String getStrSecMessage() {
      return strSecMessage;
   }

   /**
    * @param strSecMessage the strSecMessage to set
    */
   public void setStrSecMessage(String strSecMessage) {
      this.strSecMessage = strSecMessage;
   }

   /**
    * @return the subConfirmationMethod
    */
   public String getSubConfirmationMethod() {
      return subConfirmationMethod;
   }

   /**
    * @param subConfirmationMethod the subConfirmationMethod to set
    */
   public void setSubConfirmationMethod(String subConfirmationMethod) {
      this.subConfirmationMethod = subConfirmationMethod;
   }

   /**
    * @return the issuer
    */
   public String getIssuer() {
      return issuer;
   }

   /**
    * @param issuer the issuer to set
    */
   public void setIssuer(String issuer) {
      this.issuer = issuer;
   }

   /**
    * @return the expireInMin
    */
   public int getExpireInMin() {
      return expireInMin;
   }

   /**
    * @param expireInMin the expireInMin to set
    */
   public void setExpireInMin(int expireInMin) {
      this.expireInMin = expireInMin;
   }

   /**
    * @return the authContext
    */
   public String getAuthContext() {
      return authContext;
   }

   /**
    * @param authContext the authContext to set
    */
   public void setAuthContext(String authContext) {
      this.authContext = authContext;
   }

   /**
    * @return the version
    */
   public String getVersion() {
      return version;
   }

   /**
    * @param version the version to set
    */
   public void setVersion(String version) {
      this.version = version;
   }

   /**
    * @return the nameIdFormat
    */
   public String getNameIdFormat() {
      return nameIdFormat;
   }

   /**
    * @param nameIdFormat the nameIdFormat to set
    */
   public void setNameIdFormat(String nameIdFormat) {
      this.nameIdFormat = nameIdFormat;
   }

   /**
    * @return the action
    */
   public String getAction() {
      return action;
   }

   /**
    * @param action the action to set
    */
   public void setAction(String action) {
      this.action = action;
   }

   /**
    * @return the resource
    */
   public String getResource() {
      return resource;
   }

   /**
    * @param resource the resource to set
    */
   public void setResource(String resource) {
      this.resource = resource;
   }

   /**
    * @return the ipAddress
    */
   public String getIpAddress() {
      return ipAddress;
   }

   /**
    * @param ipAddress the ipAddress to set
    */
   public void setIpAddress(String ipAddress) {
      this.ipAddress = ipAddress;
   }

   /**
    * @return the dnsAddress
    */
   public String getDnsAddress() {
      return dnsAddress;
   }

   /**
    * @param dnsAddress the dnsAddress to set
    */
   public void setDnsAddress(String dnsAddress) {
      this.dnsAddress = dnsAddress;
   }

   /**
    * @return the subjectName
    */
   public String getSubjectName() {
      return subjectName;
   }

   /**
    * @param subjectName the subjectName to set
    */
   public void setSubjectName(String subjectName) {
      this.subjectName = subjectName;
   }

   /**
    * @return the audience
    */
   public String getAudience() {
      return audience;
   }

   /**
    * @param audience the audience to set
    */
   public void setAudience(String audience) {
      this.audience = audience;
   }
}
