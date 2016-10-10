package org.wsssoapbox.bean.model.wsdl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

/**
 * @author  Peter
 */
public class OutputBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * @uml.property  name="name"
	 */
	private String name;
	/**
	 * @uml.property  name="documentation"
	 */
	private String documentation;
	/**
	 * @uml.property  name="otherAttributes"
	 */
	private Map<QName, String> otherAttributes;
	/**
	 * @uml.property  name="otherElements"
	 */
	private List<?> otherElements;
	/**
	 * @uml.property  name="element"
	 */
	private Object element;
	/**
	 * @uml.property  name="messageName"
	 * @uml.associationEnd  
	 */
	private QName messageName;
	/**
	 * @uml.property  name="parts"
	 */
	private List <?> parts;
	
	
	/**
	 * @return
	 * @uml.property  name="name"
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name
	 * @uml.property  name="name"
	 */
	public void setName(String name) {
		this.name = name;
	}
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
	 * @uml.property  name="messageName"
	 */
	public QName getMessageName() {
		return messageName;
	}
	/**
	 * @param messageName
	 * @uml.property  name="messageName"
	 */
	public void setMessageName(QName messageName) {
		this.messageName = messageName;
	}
	/**
	 * @return
	 * @uml.property  name="parts"
	 */
	public List<?> getParts() {
		return parts;
	}
	/**
	 * @param parts
	 * @uml.property  name="parts"
	 */
	public void setParts(List<?> parts) {
		this.parts = parts;
	}

	@Override
	public String toString() {
		return "OutputBean [name=" + name + ", documentation=" + documentation + ", otherAttributes=" + otherAttributes
				+ ", otherElements=" + otherElements + ", element=" + element + ", messageName=" + messageName
				+ ", parts=" + parts + "]";
	}
}
