package org.wsssoapbox.bean.model.wsdl;

import java.io.Serializable;
import java.util.Date;

public class MessagesBean implements Serializable{

	private static final long serialVersionUID = 5300229504680249368L;
	private Date creaeDate;
	private String name;
	private String prefix;
	private String namespaceURI;
	private String localPart;
	
	@Override
	public String toString() {
		return  getName();
	}

   /**
    * @return the creaeDate
    */
   public Date getCreaeDate() {
      return creaeDate;
   }

   /**
    * @param creaeDate the creaeDate to set
    */
   public void setCreaeDate(Date creaeDate) {
      this.creaeDate = creaeDate;
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
    * @return the prefix
    */
   public String getPrefix() {
      return prefix;
   }

   /**
    * @param prefix the prefix to set
    */
   public void setPrefix(String prefix) {
      this.prefix = prefix;
   }

   /**
    * @return the namespaceURI
    */
   public String getNamespaceURI() {
      return namespaceURI;
   }

   /**
    * @param namespaceURI the namespaceURI to set
    */
   public void setNamespaceURI(String namespaceURI) {
      this.namespaceURI = namespaceURI;
   }

   /**
    * @return the localPart
    */
   public String getLocalPart() {
      return localPart;
   }

   /**
    * @param localPart the localPart to set
    */
   public void setLocalPart(String localPart) {
      this.localPart = localPart;
   }

}
