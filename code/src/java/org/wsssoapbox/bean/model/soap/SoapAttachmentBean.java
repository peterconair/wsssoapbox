package org.wsssoapbox.bean.model.soap;

import java.io.InputStream;
import java.io.Serializable;

public class SoapAttachmentBean implements Serializable {


   private static final long serialVersionUID = -8623744689609820149L;
   private String contentID;
   private String name;
   private String type;
   private boolean catchValue;
   private String contentType;
   private long size;
   private InputStream inputStream;
   private byte[] content;
   private String path;
   private byte[] base64Content;
   private boolean base64;

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
    * @return the inputStream
    */
   public InputStream getInputStream() {
      return inputStream;
   }

   /**
    * @return the path
    */
   public String getPath() {
      return path;
   }

   /**
    * @param path the path to set
    */
   public void setPath(String path) {
      this.path = path;
   }

   /**
    * @param inputStream the inputStream to set
    */
   public void setInputStream(InputStream inputStream) {
      this.inputStream = inputStream;
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
    * @return the type
    */
   public String getType() {
      return type;
   }

   /**
    * @param type the type to set
    */
   public void setType(String type) {
      if (type.equals("") || type == null) {
         this.type = "UNKNOW";
      }
      this.type = type;
   }

   /**
    * @return the catchValue
    */
   public boolean isCatchValue() {
      return catchValue;
   }

   /**
    * @param catchValue the catchValue to set
    */
   public void setCatchValue(boolean catchValue) {
      this.catchValue = catchValue;
   }

   /**
    * @return the contentType
    */
   public String getContentType() {
      return contentType;
   }

   /**
    * @param contentType the contentType to set
    */
   public void setContentType(String contentType) {
      this.contentType = contentType;
   }


   /**
    * @return the contentID
    */
   public String getContentID() {
      return contentID;
   }

   /**
    * @param contentID the contentID to set
    */
   public void setContentID(String contentID) {
      this.contentID = contentID;
   }


   /**
    * @return the size
    */
   public long getSize() {
      return size;
   }

   /**
    * @param size the size to set
    */
   public void setSize(long size) {
      this.size = size;
   }

   /**
    * @return the base64Content
    */
   public byte[] getBase64Content() {
      return base64Content;
   }

   /**
    * @param base64Content the base64Content to set
    */
   public void setBase64Content(byte[] base64Content) {
      this.base64Content = base64Content;
   }

   /**
    * @return the base64
    */
   public boolean isBase64() {
      return base64;
   }

   /**
    * @param base64 the base64 to set
    */
   public void setBase64(boolean base64) {
      this.base64 = base64;
   }
}
