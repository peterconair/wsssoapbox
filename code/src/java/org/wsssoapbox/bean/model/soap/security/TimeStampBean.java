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
public class TimeStampBean implements Serializable{

   private static String XSD_DATE_TIME;
   public static final long MAX_CLOCK_SKEW = 300000L;
   public static final long TIMESTAMP_FRESHNESS_LIMIT = 300000L;
 
   private String created;
   private long timeout;
   private String createdValueType;
   private String expires;
   private String expiresValueType;
   private String wsuId;
   
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
    * @return the timeout
    */
   public long getTimeout() {
      return timeout;
   }

   /**
    * @param timeout the timeout to set
    */
   public void setTimeout(long timeout) {
      this.timeout = timeout;
   }

   /**
    * @return the createdValueType
    */
   public String getCreatedValueType() {
      return createdValueType;
   }

   /**
    * @param createdValueType the createdValueType to set
    */
   public void setCreatedValueType(String createdValueType) {
      this.createdValueType = createdValueType;
   }

   /**
    * @return the expires
    */
   public String getExpires() {
      return expires;
   }

   /**
    * @param expires the expires to set
    */
   public void setExpires(String expires) {
      this.expires = expires;
   }

   /**
    * @return the expiresValueType
    */
   public String getExpiresValueType() {
      return expiresValueType;
   }

   /**
    * @param expiresValueType the expiresValueType to set
    */
   public void setExpiresValueType(String expiresValueType) {
      this.expiresValueType = expiresValueType;
   }

   /**
    * @return the wsuId
    */
   public String getWsuId() {
      return wsuId;
   }

   /**
    * @param wsuId the wsuId to set
    */
   public void setWsuId(String wsuId) {
      this.wsuId = wsuId;
   }
}
