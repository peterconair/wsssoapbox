package org.wsssoapbox.bean.model.wsdl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.xml.namespace.QName;

import org.ow2.easywsdl.schema.api.Documentation;
import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.api.Binding;
import org.ow2.easywsdl.wsdl.api.Endpoint;
import org.ow2.easywsdl.wsdl.api.Service;
import org.w3c.dom.Element;

/**
 * @author  Peter
 */
@ManagedBean(name="endpointBean")
@SessionScoped
public class EndpointBean implements Serializable   {

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
	 * @uml.property  name="localPart"
	 */
	private String localPart;
	/**
	 * @uml.property  name="address"
	 */
	private String address;
	/**
	 * @uml.property  name="httpAuthenticationRealm"
	 */
	private String httpAuthenticationRealm;
	/**
	 * @uml.property  name="httpAuthenticationScheme"
	 */
	private String httpAuthenticationScheme;
	/**
	 * @uml.property  name="documentation"
	 */
	private String documentation;
	/**
	 * @uml.property  name="otherElements"
	 */
	private List <?> otherElements;
	/**
	 * @uml.property  name="otherAttributes"
	 */
	private Map <QName ,String> otherAttributes;
	/**
	 * @uml.property  name="binding"
	 * @uml.associationEnd  
	 */
	private BindingBean binding;
	/**
	 * @uml.property  name="service"
	 * @uml.associationEnd  
	 */
	private ServicesBean  service;
	public EndpointBean() {
	}
	
	public EndpointBean(String name) {
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
	 * @uml.property  name="address"
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address
	 * @uml.property  name="address"
	 */
	public void setAddress(String address) {
		this.address = address;
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
	 * @uml.property  name="service"
	 */
	public ServicesBean getService() {
		return service;
	}
	/**
	 * @param service
	 * @uml.property  name="service"
	 */
	public void setService(ServicesBean service) {
		this.service = service;
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
	 * @uml.property  name="binding"
	 */
	public BindingBean getBinding() {
		return binding;
	}
	/**
	 * @param binding
	 * @uml.property  name="binding"
	 */
	public void setBinding(BindingBean binding){
		this.binding = binding;
	}

	/**
	 * @return
	 * @uml.property  name="httpAuthenticationRealm"
	 */
	public String getHttpAuthenticationRealm() {
		return httpAuthenticationRealm;
	}

	/**
	 * @param httpAuthenticationRealm
	 * @uml.property  name="httpAuthenticationRealm"
	 */
	public void setHttpAuthenticationRealm(String httpAuthenticationRealm) {
		this.httpAuthenticationRealm = httpAuthenticationRealm;
	}

	/**
	 * @return
	 * @uml.property  name="httpAuthenticationScheme"
	 */
	public String getHttpAuthenticationScheme() {
		return httpAuthenticationScheme;
	}

	/**
	 * @param httpAuthenticationScheme
	 * @uml.property  name="httpAuthenticationScheme"
	 */
	public void setHttpAuthenticationScheme(String httpAuthenticationScheme) {
		this.httpAuthenticationScheme = httpAuthenticationScheme;
	}

	@Override
	public String toString() {
		return name ;
	}


}
