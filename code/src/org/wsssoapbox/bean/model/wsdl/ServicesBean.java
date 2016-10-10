package org.wsssoapbox.bean.model.wsdl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.xml.namespace.QName;


/**
 * @author  Peter
 */
@ManagedBean(name="service")
@SessionScoped
public class ServicesBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ServicesBean() {
	}

	/**
	 * @uml.property  name="name"
	 */
	private String name ;
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
	 * @uml.property  name="otherAttributes"
	 */
	private Map <QName,String> otherAttributes;
	/**
	 * @uml.property  name="otherElements"
	 */
	private List <?> otherElements;
	/**
	 * @uml.property  name="interfaceType"
	 * @uml.associationEnd  
	 */
	private InterfaceBean interfaceType;
	/**
	 * @uml.property  name="endpoints"
	 */
	List <EndpointBean> endpoints;
	
	public ServicesBean(String name) {
		this.name = name;
	}

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

	/**
	 * @return
	 * @uml.property  name="endpoints"
	 */
	public List<EndpointBean> getEndpoints() {
		return endpoints;
	}

	/**
	 * @param endpoints
	 * @uml.property  name="endpoints"
	 */
	public void setEndpoints(List<EndpointBean> endpoints) {
		this.endpoints = endpoints;
	}

	/**
	 * @return
	 * @uml.property  name="interfaceType"
	 */
	public InterfaceBean getInterfaceType() {
		return interfaceType;
	}

	/**
	 * @param interfaceType
	 * @uml.property  name="interfaceType"
	 */
	public void setInterfaceType(InterfaceBean interfaceType) {
		this.interfaceType = interfaceType;
	}

	@Override
	public String toString() {
		return name;
	}

	

}
