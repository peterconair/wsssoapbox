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
public class SecurityHeaderBean implements Serializable{
   private static final long serialVersionUID = -7962060672619431274L;

   private boolean mustUnderstand = true;
   private boolean timeStamp;
   private boolean usernameToken = true ;
   private boolean doCreate = true;

   
   public boolean isMustUnderstand() {
      return mustUnderstand;
   }

   public void setMustUnderstand(boolean mustUnderstand) {
      this.mustUnderstand = mustUnderstand;
   }

   /**
    * @return the doCreate
    */
   public boolean isDoCreate() {
      return doCreate;
   }

   /**
    * @param doCreate the doCreate to set
    */
   public void setDoCreate(boolean doCreate) {
      this.doCreate = doCreate;
   }

   /**
    * @return the timeStamp
    */
   public boolean isTimeStamp() {
      return timeStamp;
   }

   /**
    * @param timeStamp the timeStamp to set
    */
   public void setTimeStamp(boolean timeStamp) {
      this.timeStamp = timeStamp;
   }

   /**
    * @return the usernameToken
    */
   public boolean isUsernameToken() {
      return usernameToken ;
   }

   /**
    * @param usernameToken the usernameToken to set
    */
   public void setUsernameToken(boolean usernameToken) {
      this.usernameToken = usernameToken;
   }
}
