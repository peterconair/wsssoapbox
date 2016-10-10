/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.bean.model.soap.security;

/**
 *
 * @author Peter
 */
public class SoapPartsBean {
   private String Id;
   private String tagName;
   private String namespace;
   private String encode;

   /**
    * @return the Id
    */
   public String getId() {
      return Id;
   }

   /**
    * @param Id the Id to set
    */
   public void setId(String Id) {
      this.Id = Id;
   }

   /**
    * @return the tagName
    */
   public String getTagName() {
      return tagName;
   }

   /**
    * @param tagName the tagName to set
    */
   public void setTagName(String tagName) {
      this.tagName = tagName;
   }

   /**
    * @return the namespace
    */
   public String getNamespace() {
      return namespace;
   }

   /**
    * @param namespace the namespace to set
    */
   public void setNamespace(String namespace) {
      this.namespace = namespace;
   }

   /**
    * @return the encode
    */
   public String getEncode() {
      return encode;
   }

   /**
    * @param encode the encode to set
    */
   public void setEncode(String encode) {
      this.encode = encode;
   }
}
