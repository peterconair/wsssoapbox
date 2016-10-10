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
public class EncrytionBean implements Serializable {

   private static final long serialVersionUID = 1L;

   private String keyIdType;
   private String keyEncryptionAlg;
   private String symmetricEncAlg;
   private String encCononicalization;
   private String strSecMessage;

  
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
    * @return the encCononicalization
    */
   public String getEncCononicalization() {
      return encCononicalization;
   }

   /**
    * @param encCononicalization the encCononicalization to set
    */
   public void setEncCononicalization(String encCononicalization) {
      this.encCononicalization = encCononicalization;
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
    * @return the keyEncryptionAlg
    */
   public String getKeyEncryptionAlg() {
      return keyEncryptionAlg;
   }

   /**
    * @param keyEncryptionAlg the keyEncryptionAlg to set
    */
   public void setKeyEncryptionAlg(String keyEncryptionAlg) {
      this.keyEncryptionAlg = keyEncryptionAlg;
   }

   /**
    * @return the symmetricEncAlg
    */
   public String getSymmetricEncAlg() {
      return symmetricEncAlg;
   }

   /**
    * @param symmetricEncAlg the symmetricEncAlg to set
    */
   public void setSymmetricEncAlg(String symmetricEncAlg) {
      this.symmetricEncAlg = symmetricEncAlg;
   }

   
}
