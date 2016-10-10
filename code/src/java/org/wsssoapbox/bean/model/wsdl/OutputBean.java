package org.wsssoapbox.bean.model.wsdl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

/**
 * @author  Peter
 */
public class OutputBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private String documentation;
	private Map<QName, String> otherAttributes;
	private List<?> otherElements;
	private Object element;
	private QName messageName;
	private List <?> parts;
	


	@Override
	public String toString() {
		return "OutputBean [name=" + getName() + ", documentation=" + getDocumentation() + ", otherAttributes=" + getOtherAttributes()
				+ ", otherElements=" + getOtherElements() + ", element=" + getElement() + ", messageName=" + getMessageName()
				+ ", parts=" + getParts() + "]";
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
    * @return the messageName
    */
   public QName getMessageName() {
      return messageName;
   }

   /**
    * @param messageName the messageName to set
    */
   public void setMessageName(QName messageName) {
      this.messageName = messageName;
   }

   /**
    * @return the parts
    */
   public List <?> getParts() {
      return parts;
   }

   /**
    * @param parts the parts to set
    */
   public void setParts(List <?> parts) {
      this.parts = parts;
   }
}
