/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.bean.model.soap.security;

import java.math.BigInteger;
import java.util.List;
import org.wsssoapbox.bean.model.soap.security.keystore.CertificateBean;

/**
 *
 * @author Peter
 */
public class TrustedStoreBean {

   private BigInteger id;
   private String aliasName;
   private String name;
   private List<CertificateBean> certificateBeans;

   /**
    * @return the id
    */
   public BigInteger getId() {
      return id;
   }

   /**
    * @param id the id to set
    */
   public void setId(BigInteger id) {
      this.id = id;
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
    * @return the certificateBeans
    */
   public List<CertificateBean> getCertificateBeans() {
      return certificateBeans;
   }

   /**
    * @param certificateBeans the certificateBeans to set
    */
   public void setCertificateBeans(List<CertificateBean> certificateBeans) {
      this.certificateBeans = certificateBeans;
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

}
