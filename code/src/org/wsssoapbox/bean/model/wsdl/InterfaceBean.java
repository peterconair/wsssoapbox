package org.wsssoapbox.bean.model.wsdl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

/**
 * @author  Peter
 */
public class InterfaceBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * @uml.property  name="name"
	 */
	private String name;
	/**
	 * @uml.property  name="prefix"
	 */
	private String prefix;
	/**
	 * @uml.property  name="namespaceURI"
	 */
	private String namespaceURI;
	/**
	 * @uml.property  name="localPart"
	 */
	private String localPart;
	/**
	 * @uml.property  name="qname"
	 * @uml.associationEnd  
	 */
	private QName qname;
	/**
	 * @uml.property  name="documentation"
	 */
	private String documentation;
	/**
	 * @uml.property  name="operations"
	 */
	private List <OperationBean> operations;
	/**
	 * @uml.property  name="otherAttributes"
	 */
	private Map <QName,String> otherAttributes;
	/**
	 * @uml.property  name="ohterElements"
	 */
	private List<?> ohterElements;
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
	 * @uml.property  name="operations"
	 */
	public List<OperationBean> getOperations() {
		return operations;
	}
	/**
	 * @param operations
	 * @uml.property  name="operations"
	 */
	public void setOperations(List <OperationBean> operations) {
		this.operations = operations;
	}
	/**
	 * @return
	 * @uml.property  name="otherAttributes"
	 */
	public Map<QName,String> getOtherAttributes() {
		return otherAttributes;
	}
	/**
	 * @param otherAttributes
	 * @uml.property  name="otherAttributes"
	 */
	public void setOtherAttributes(Map<QName,String> otherAttributes) {
		this.otherAttributes = otherAttributes;
	}
	/**
	 * @return
	 * @uml.property  name="ohterElements"
	 */
	public List<?> getOhterElements() {
		return ohterElements;
	}
	/**
	 * @param ohterElements
	 * @uml.property  name="ohterElements"
	 */
	public void setOhterElements(List<?> ohterElements) {
		this.ohterElements = ohterElements;
	}
	/**
	 * @return
	 * @uml.property  name="prefix"
	 */
	public String getPrefix() {
		return prefix;
	}
	/**
	 * @param prefix
	 * @uml.property  name="prefix"
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	/**
	 * @return
	 * @uml.property  name="namespaceURI"
	 */
	public String getNamespaceURI() {
		return namespaceURI;
	}
	/**
	 * @param namespaceURI
	 * @uml.property  name="namespaceURI"
	 */
	public void setNamespaceURI(String namespaceURI) {
		this.namespaceURI = namespaceURI;
	}
	/**
	 * @return
	 * @uml.property  name="localPart"
	 */
	public String getLocalPart() {
		return localPart;
	}
	/**
	 * @param localPart
	 * @uml.property  name="localPart"
	 */
	public void setLocalPart(String localPart) {
		this.localPart = localPart;
	}
	
	@Override
	public String toString() {
		return  name;
	}

}
