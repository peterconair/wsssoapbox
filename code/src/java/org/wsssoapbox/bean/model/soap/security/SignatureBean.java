/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.bean.model.soap.security;

import java.io.Serializable;

/**
 *
 * @author Peter
 */
public class SignatureBean implements Serializable {
   private static final long serialVersionUID = 1L;
   
   private String keyStore;
   private String sigAlgorithm;   
   private String keyIdType;
   private String sigCononicalization;
   private String strSecMessage;

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
   
   
   
}
