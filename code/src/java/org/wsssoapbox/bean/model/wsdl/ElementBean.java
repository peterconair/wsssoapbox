package org.wsssoapbox.bean.model.wsdl;

import java.io.Serializable;
import java.util.Map;

import javax.xml.namespace.QName;

/**
 * @author  Peter
 */
public class ElementBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private String documentation;
	private String maxOccurs;
	private int minOccurs;
	private Map<QName, String> otherAttributes;
	private QName qname;
	private QName ref;
	private TypeBean type;
	private Object form;
     
	
	@Override
	public String toString() {
		return "ElementBean [documentation=" + getDocumentation() + ", maxOccurs=" + getMaxOccurs() + ", minOccurs=" + getMinOccurs()
				+ ", otherAttributes=" + getOtherAttributes() + ", qname=" + getQname() + ", ref=" + getRef() + ", type=" + getType()
				+ ", form=" + getForm() + "]";
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
    * @return the maxOccurs
    */
   public String getMaxOccurs() {
      return maxOccurs;
   }

   /**
    * @param maxOccurs the maxOccurs to set
    */
   public void setMaxOccurs(String maxOccurs) {
      this.maxOccurs = maxOccurs;
   }

   /**
    * @return the minOccurs
    */
   public int getMinOccurs() {
      return minOccurs;
   }

   /**
    * @param minOccurs the minOccurs to set
    */
   public void setMinOccurs(int minOccurs) {
      this.minOccurs = minOccurs;
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
    * @return the qname
    */
   public QName getQname() {
      return qname;
   }

   /**
    * @param qname the qname to set
    */
   public void setQname(QName qname) {
      this.qname = qname;
   }

   /**
    * @return the ref
    */
   public QName getRef() {
      return ref;
   }

   /**
    * @param ref the ref to set
    */
   public void setRef(QName ref) {
      this.ref = ref;
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

   /**
    * @return the form
    */
   public Object getForm() {
      return form;
   }

   /**
    * @param form the form to set
    */
   public void setForm(Object form) {
      this.form = form;
   }

}
