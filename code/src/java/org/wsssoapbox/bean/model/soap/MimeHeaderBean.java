package org.wsssoapbox.bean.model.soap;

import java.io.Serializable;

public class MimeHeaderBean implements Serializable{
   private static final long serialVersionUID = 1L;

   private String header;
   private String value;
   public String getHeader() {
      return header;
   }
   public void setHeader(String header) {
      this.header = header;
   }
   public String getValue() {
      return value;
   }
   public void setValue(String value) {
      this.value = value;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      }
      if (getClass() != obj.getClass()) {
         return false;
      }
      final MimeHeaderBean other = (MimeHeaderBean) obj;
      if ((this.header == null) ? (other.header != null) : !this.header.equals(other.header)) {
         return false;
      }
      return true;
   }

   @Override
   public int hashCode() {
      int hash = 7;
      hash = 79 * hash + (this.header != null ? this.header.hashCode() : 0);
      return hash;
   }
   
 


}
