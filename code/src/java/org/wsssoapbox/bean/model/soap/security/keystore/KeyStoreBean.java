/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.bean.model.soap.security.keystore;

import java.io.Serializable;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Peter
 */
public class KeyStoreBean implements Serializable {

   private static final long serialVersionUID = -2610801260993459525L;
   private Integer id;
   private Integer userId;
   private String name;
   private String aliasName;
   private String password;
   private String type;
   private String provider;
   private byte[] content;
   private Date creationDate;
   private List<String> strAliasNames = new ArrayList<String>();
   private KeyStore keyStore;
   private List<CertificateBean> certBeans;

   public KeyStoreBean() {
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

   /**
    * @return the provider
    */
   public String getProvider() {
      return provider;
   }

   /**
    * @param provider the provider to set
    */
   public void setProvider(String provider) {
      this.provider = provider;
   }

   /**
    * @return the creationDate
    */
   public Date getCreationDate() {
      return creationDate;
   }

   /**
    * @param creationDate the creationDate to set
    */
   public void setCreationDate(Date creationDate) {
      this.creationDate = creationDate;
   }

   /**
    * @return the name
    */
   public String getName() {
      return name;
   }

   /**
    * @param name the name to set
    */
   public void setName(String name) {
      this.name = name;
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
    * @return the content
    */
   public byte[] getContent() {
      return content;
   }

   /**
    * @param content the content to set
    */
   public void setContent(byte[] content) {
      this.content = content;
   }

   /**
    * @return the keyStore
    */
   public KeyStore getKeyStore() {
      return keyStore;
   }

   /**
    * @param keyStore the keyStore to set
    */
   public void setKeyStore(KeyStore keyStore) {
      this.keyStore = keyStore;
   }

   /**
    * @return the certBeans
    */
   public List<CertificateBean> getCertBeans() {
      return certBeans;
   }

   /**
    * @param certBeans the certBeans to set
    */
   public void setCertBeans(List<CertificateBean> certBeans) {
      this.certBeans = certBeans;
   }

   /**
    * @return the strAliasNames
    */
   public List<String> getStrAliasNames() {
      return strAliasNames;
   }

   /**
    * @param strAliasNames the strAliasNames to set
    */
   public void setStrAliasNames(List<String> strAliasNames) {
      this.strAliasNames = strAliasNames;
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
    * @return the id
    */
   public Integer getId() {
      return id;
   }

   /**
    * @param id the id to set
    */
   public void setId(Integer id) {
      this.id = id;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      }
      if (getClass() != obj.getClass()) {
         return false;
      }
      final KeyStoreBean other = (KeyStoreBean) obj;
      if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
         return false;
      }
      if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
         return false;
      }
      if ((this.aliasName == null) ? (other.aliasName != null) : !this.aliasName.equals(other.aliasName)) {
         return false;
      }
      if ((this.password == null) ? (other.password != null) : !this.password.equals(other.password)) {
         return false;
      }
      if ((this.type == null) ? (other.type != null) : !this.type.equals(other.type)) {
         return false;
      }
      if ((this.provider == null) ? (other.provider != null) : !this.provider.equals(other.provider)) {
         return false;
      }
      if (!Arrays.equals(this.content, other.content)) {
         return false;
      }
      if (this.creationDate != other.creationDate && (this.creationDate == null || !this.creationDate.equals(other.creationDate))) {
         return false;
      }
      if (this.strAliasNames != other.strAliasNames && (this.strAliasNames == null || !this.strAliasNames.equals(other.strAliasNames))) {
         return false;
      }
      if (this.keyStore != other.keyStore && (this.keyStore == null || !this.keyStore.equals(other.keyStore))) {
         return false;
      }
      if (this.certBeans != other.certBeans && (this.certBeans == null || !this.certBeans.equals(other.certBeans))) {
         return false;
      }
      return true;
   }

   @Override
   public int hashCode() {
      int hash = 5;
      hash = 17 * hash + (this.id != null ? this.id.hashCode() : 0);
      hash = 17 * hash + (this.name != null ? this.name.hashCode() : 0);
      hash = 17 * hash + (this.aliasName != null ? this.aliasName.hashCode() : 0);
      hash = 17 * hash + (this.password != null ? this.password.hashCode() : 0);
      hash = 17 * hash + (this.type != null ? this.type.hashCode() : 0);
      hash = 17 * hash + (this.provider != null ? this.provider.hashCode() : 0);
      hash = 17 * hash + Arrays.hashCode(this.content);
      hash = 17 * hash + (this.creationDate != null ? this.creationDate.hashCode() : 0);
      hash = 17 * hash + (this.strAliasNames != null ? this.strAliasNames.hashCode() : 0);
      hash = 17 * hash + (this.keyStore != null ? this.keyStore.hashCode() : 0);
      hash = 17 * hash + (this.certBeans != null ? this.certBeans.hashCode() : 0);
      return hash;
   }

   @Override
   public String toString() {
      return "KeyStoreBean{" + "id=" + id + ", userId=" + userId + ", name=" + name + ", aliasName=" + aliasName + ", password=" + password + ", type=" + type + ", provider=" + provider + ", creationDate=" + creationDate + ", strAliasNames=" + strAliasNames + ", keyStore=" + keyStore + ", certBeans=" + certBeans + '}';
   }

   /**
    * @return the userId
    */
   public Integer getUserId() {
      return userId;
   }

   /**
    * @param userId the userId to set
    */
   public void setUserId(Integer userId) {
      this.userId = userId;
   }
}