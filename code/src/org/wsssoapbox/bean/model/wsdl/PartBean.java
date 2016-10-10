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
	/**
	 * @uml.property  name="documentation"
	 */
	String documentation;
	/**
	 * @uml.property  name="element"
	 */
	Object element;
	/**
	 * @uml.property  name="otherAttributes"
	 */
	Map<QName, String> otherAttributes;
	/**
	 * @uml.property  name="otherElements"
	 */
	List<?> otherElements;
	/**
	 * @uml.property  name="partQName"
	 * @uml.associationEnd  
	 */
	QName partQName;
	/**
	 * @uml.property  name="type"
	 * @uml.associationEnd  
	 */
	TypeBean type;

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
	 * @uml.property  name="element"
	 */
	public Object getElement() {
		return element;
	}

	/**
	 * @param element
	 * @uml.property  name="element"
	 */
	public void setElement(Object element) {
		this.element = element;
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
	 * @uml.property  name="otherElements"
	 */
	public List<?> getOtherElements() {
		return otherElements;
	}

	/**
	 * @param otherElements
	 * @uml.property  name="otherElements"
	 */
	public void setOtherElements(List<?> otherElements) {
		this.otherElements = otherElements;
	}

	/**
	 * @return
	 * @uml.property  name="partQName"
	 */
	public QName getPartQName() {
		return partQName;
	}

	/**
	 * @param partQName
	 * @uml.property  name="partQName"
	 */
	public void setPartQName(QName partQName) {
		this.partQName = partQName;
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

	@Override
	public String toString() {
		return "PartBean [documentation=" + documentation + ", element=" + element + ", otherAttributes="
				+ otherAttributes + ", otherElements=" + otherElements + ", partQName=" + partQName + ", type=" + type
				+ "]";
	}
}
