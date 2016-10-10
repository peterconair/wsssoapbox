package org.wsssoapbox.bean.model.wsdl;

import java.io.Serializable;
import java.util.Map;

import javax.xml.namespace.QName;

/**
 * @author  Peter
 */
public class DocumentionBean implements Serializable {

	private static final long serialVersionUID = 1L;
private String content;
private DocumentionBean documentation;
private Map <QName,String> otherAttributes;

@Override
public String toString() {
	return "DocumentionBean [content=" + getContent() + ", documentation=" + getDocumentation() + ", otherAttributes="
			+ getOtherAttributes() + "]";
}

   /**
    * @return the content
    */
   public String getContent() {
      return content;
   }

   /**
    * @param content the content to set
    */
   public void setContent(String content) {
      this.content = content;
   }

   /**
    * @return the documentation
    */
   public DocumentionBean getDocumentation() {
      return documentation;
   }

   /**
    * @param documentation the documentation to set
    */
   public void setDocumentation(DocumentionBean documentation) {
      this.documentation = documentation;
   }

   /**
    * @return the otherAttributes
    */
   public Map <QName,String> getOtherAttributes() {
      return otherAttributes;
   }

   /**
    * @param otherAttributes the otherAttributes to set
    */
   public void setOtherAttributes(Map <QName,String> otherAttributes) {
      this.otherAttributes = otherAttributes;
   }
}
