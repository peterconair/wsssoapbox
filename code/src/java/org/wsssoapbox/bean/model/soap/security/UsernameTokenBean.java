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
public class UsernameTokenBean implements Serializable {


   public static final long MAX_NONCE_AGE = 900000L;
   private String passwordType ="PasswordText";
   private String usernameValue;
   private String passwordValue;
   private String passwordDigestValue;
   private String nonceValue;
   private String nonceEncodingType;
   private String createdValue;
   private boolean passwordDigest;
   private boolean created;
   private boolean nonce;

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
    * @return the usernameValue
    */
   public String getUsernameValue() {
      return usernameValue;
   }

   /**
    * @param usernameValue the usernameValue to set
    */
   public void setUsernameValue(String usernameValue) {
      this.usernameValue = usernameValue;
   }

   /**
    * @return the passwordValue
    */
   public String getPasswordValue() {
      return passwordValue;
   }

   /**
    * @param passwordValue the passwordValue to set
    */
   public void setPasswordValue(String passwordValue) {
      this.passwordValue = passwordValue;
   }

   /**
    * @return the passwordDigestValue
    */
   public String getPasswordDigestValue() {
      return passwordDigestValue;
   }

   /**
    * @param passwordDigestValue the passwordDigestValue to set
    */
   public void setPasswordDigestValue(String passwordDigestValue) {
      this.passwordDigestValue = passwordDigestValue;
   }

   /**
    * @return the nonceValue
    */
   public String getNonceValue() {
      return nonceValue;
   }

   /**
    * @param nonceValue the nonceValue to set
    */
   public void setNonceValue(String nonceValue) {
      this.nonceValue = nonceValue;
   }

   /**
    * @return the nonceEncodingType
    */
   public String getNonceEncodingType() {
      return nonceEncodingType;
   }

   /**
    * @param nonceEncodingType the nonceEncodingType to set
    */
   public void setNonceEncodingType(String nonceEncodingType) {
      this.nonceEncodingType = nonceEncodingType;
   }

   /**
    * @return the createdValue
    */
   public String getCreatedValue() {
      return createdValue;
   }

   /**
    * @param createdValue the createdValue to set
    */
   public void setCreatedValue(String createdValue) {
      this.createdValue = createdValue;
   }

   /**
    * @return the created
    */
   public boolean isCreated() {
      return created;
   }

   /**
    * @param created the created to set
    */
   public void setCreated(boolean created) {
      this.created = created;
   }

   /**
    * @return the nonce
    */
   public boolean isNonce() {
      return nonce;
   }

   /**
    * @param nonce the nonce to set
    */
   public void setNonce(boolean nonce) {
      this.nonce = nonce;
   }

   /**
    * @return the passwordDigest
    */
   public boolean isPasswordDigest() {
      if (passwordType != null || !passwordType.equals("")) {
         if (passwordType.equalsIgnoreCase("PasswordDigest")) {
            passwordDigest = true;
         }else{
             passwordDigest = false;
         }
      }
      return passwordDigest;
   }

   /**
    * @param passwordDigest the passwordDigest to set
    */
   public void setPasswordDigest(boolean passwordDigest) {
      this.passwordDigest = passwordDigest;
   }
}
