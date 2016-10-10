package org.wsssoapbox.bean.model.soap.security.keystore;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.Date;

/**
 *
 * @author Peter
 */
public class KeyStoreEntry {

  public enum Type {

      CERT("Certificate"), KEYPair("Keypair"), KEY("Private Key");
      String type;

      Type(String type) {
         this.type = type;
      }

      public String getType() {
         return type;
      }
   }
   private Object entry;
   private String aliasName;
   private Date createDate;
   private String type;
   
   
   /**
    * @return the entry
    */
   public Object getEntry() {
      return entry;
   }

   /**
    * @param entry the entry to set
    */
   public void setEntry(Object entry) {
      this.entry = entry;
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
    * @return the createDate
    */
   public Date getCreateDate() {
      return createDate;
   }

   /**
    * @param createDate the createDate to set
    */
   public void setCreateDate(Date createDate) {
      this.createDate = createDate;
   }

   /**
    * @return the type
    */
   public String getType() {
      return type;
   }

   /**
    * @param type the type to set
    */
   public void setType(String type) {
      this.type = type;
   }
}
