package org.wsssoapbox.bean.model.wsdl;

import java.io.Serializable;
import java.util.Map;

import javax.xml.namespace.QName;

/**
 * @author  Peter
 */
public class ElementBean implements Serializable{

	private static final long serialVersionUID = 1L;
	/**
	 * @uml.property  name="documentation"
	 */
	String documentation;
	/**
	 * @uml.property  name="maxOccurs"
	 */
	String maxOccurs;
	/**
	 * @uml.property  name="minOccurs"
	 */
	int minOccurs;
	/**
	 * @uml.property  name="otherAttributes"
	 */
	Map<QName, String> otherAttributes;

	/**
	 * @uml.property  name="qname"
	 * @uml.associationEnd  
	 */
	QName qname;
	/**
	 * @uml.property  name="ref"
	 * @uml.associationEnd  
	 */
	QName ref;
	/**
	 * @uml.property  name="type"
	 * @uml.associationEnd  
	 */
	TypeBean type;
	/**
	 * @uml.property  name="form"
	 */
	Object form;
	/**
	 * @return
	 * @uml.property  name="documentation"
	 */
	public String getDocumentation() {
		return documentation;
	}
	/**
	 * @param documentation
	 * @uml.property  name="documentation"
	 */
	public void setDocumentation(String documentation) {
		this.documentation = documentation;
	}
	/**
	 * @return
	 * @uml.property  name="maxOccurs"
	 */
	public String getMaxOccurs() {
		return maxOccurs;
	}
	/**
	 * @param maxOccurs
	 * @uml.property  name="maxOccurs"
	 */
	public void setMaxOccurs(String maxOccurs) {
		this.maxOccurs = maxOccurs;
	}
	/**
	 * @return
	 * @uml.property  name="minOccurs"
	 */
	public int getMinOccurs() {
		return minOccurs;
	}
	/**
	 * @param minOccurs
	 * @uml.property  name="minOccurs"
	 */
	public void setMinOccurs(int minOccurs) {
		this.minOccurs = minOccurs;
	}
	/**
	 * @return
	 * @uml.property  name="otherAttributes"
	 */
	public Map<QName, String> getOtherAttributes() {
		return otherAttributes;
	}
	/**
	 * @param otherAttributes
	 * @uml.property  name="otherAttributes"
	 */
	public void setOtherAttributes(Map<QName, String> otherAttributes) {
		this.otherAttributes = otherAttributes;
	}
	/**
	 * @return
	 * @uml.property  name="qname"
	 */
	public QName getQname() {
		return qname;
	}
	/**
	 * @param qname
	 * @uml.property  name="qname"
	 */
	public void setQname(QName qname) {
		this.qname = qname;
	}
	/**
	 * @return
	 * @uml.property  name="ref"
	 */
	public QName getRef() {
		return ref;
	}
	/**
	 * @param ref
	 * @uml.property  name="ref"
	 */
	public void setRef(QName ref) {
		this.ref = ref;
	}
	/**
	 * @return
	 * @uml.property  name="type"
	 */
	public TypeBean getType() {
		return type;
	}
	/**
	 * @param type
	 * @uml.property  name="type"
	 */
	public void setType(TypeBean type) {
		this.type = type;
	}
	/**
	 * @return
	 * @uml.property  name="form"
	 */
	public Object getForm() {
		return form;
	}
	/**
	 * @param form
	 * @uml.property  name="form"
	 */
	public void setForm(Object form) {
		this.form = form;
	}
	@Override
	public String toString() {
		return "ElementBean [documentation=" + documentation + ", maxOccurs=" + maxOccurs + ", minOccurs=" + minOccurs
				+ ", otherAttributes=" + otherAttributes + ", qname=" + qname + ", ref=" + ref + ", type=" + type
				+ ", form=" + form + "]";
	}

}
