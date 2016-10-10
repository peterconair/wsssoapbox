package org.wsssoapbox.bean.model.wsdl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

/**
 * @author  Peter
 */
public class PartBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 902845005345553162L;

	private String documentation;
	private Object element;
	private Map<QName, String> otherAttributes;
	private List<?> otherElements;
	private QName partQName;
	private TypeBean type;

	
	@Override
	public String toString() {
		return "PartBean [documentation=" + getDocumentation() + ", element=" + getElement() + ", otherAttributes="
				+ getOtherAttributes() + ", otherElements=" + getOtherElements() + ", partQName=" + getPartQName() + ", type=" + getType()
				+ "]";
	}

   /**
    * @return the documentation
    */
   public String getDocumentation() {
      return documentation;
   }

   /**
    * @param documentation the documentation to set
    */
   public void setDocumentation(String documentation) {
      this.documentation = documentation;
   }

   /**
    * @return the element
    */
   public Object getElement() {
      return element;
   }

   /**
    * @param element the element to set
    */
   public void setElement(Object element) {
      this.element = element;
   }

   /**
    * @return the otherAttributes
    */
   public Map<QName, String> getOtherAttributes() {
      return otherAttributes;
   }

   /**
    * @param otherAttributes the otherAttributes to set
    */
   public void setOtherAttributes(Map<QName, String> otherAttributes) {
      this.otherAttributes = otherAttributes;
   }

   /**
    * @return the otherElements
    */
   public List<?> getOtherElements() {
      return otherElements;
   }

   /**
    * @param otherElements the otherElements to set
    */
   public void setOtherElements(List<?> otherElements) {
      this.otherElements = otherElements;
   }

   /**
    * @return the partQName
    */
   public QName getPartQName() {
      return partQName;
   }

   /**
    * @param partQName the partQName to set
    */
   public void setPartQName(QName partQName) {
      this.partQName = partQName;
   }

   /**
    * @return the type
    */
   public TypeBean getType() {
      return type;
   }

   /**
    * @param type the type to set
    */
   public void setType(TypeBean type) {
      this.type = type;
   }
}
