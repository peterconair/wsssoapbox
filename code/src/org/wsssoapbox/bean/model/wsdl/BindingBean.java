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
@ManagedBean(name = "bindingBean")
@SessionScoped
public class BindingBean implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * @uml.property  name="name"
	 */
	private String name;
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
	 * @uml.property  name="httpQueryParameterSeparatorDefault"
	 */
	private String httpQueryParameterSeparatorDefault;
	/**
	 * @uml.property  name="httpContentEncodingDefault"
	 */
	private String httpContentEncodingDefault;
	/**
	 * @uml.property  name="httpDefaultMethod"
	 */
	private String httpDefaultMethod;
	/**
	 * @uml.property  name="style"
	 */
	private Enum style;
	/**
	 * @uml.property  name="transportProtocol"
	 */
	private String transportProtocol;
	/**
	 * @uml.property  name="typeOfBinding"
	 */
	private String typeOfBinding;
	/**
	 * @uml.property  name="version"
	 */
	private String version;
	/**
	 * @uml.property  name="otherElements"
	 */
	private List<?> otherElements;
	/**
	 * @uml.property  name="otherAttributes"
	 */
	Map<QName, String> otherAttributes;
	/**
	 * @uml.property  name="interfaces"
	 * @uml.associationEnd  
	 */
	private InterfaceBean interfaces;
	/**
	 * @uml.property  name="bindingOperations"
	 */
	private List<BindingOperationBean> bindingOperations;

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
	 * @uml.property  name="httpContentEncodingDefault"
	 */
	public String getHttpContentEncodingDefault() {
		return httpContentEncodingDefault;
	}

	/**
	 * @param httpContentEncodingDefault
	 * @uml.property  name="httpContentEncodingDefault"
	 */
	public void setHttpContentEncodingDefault(String httpContentEncodingDefault) {
		this.httpContentEncodingDefault = httpContentEncodingDefault;
	}

	/**
	 * @return
	 * @uml.property  name="httpDefaultMethod"
	 */
	public String getHttpDefaultMethod() {
		return httpDefaultMethod;
	}

	/**
	 * @param httpDefaultMethod
	 * @uml.property  name="httpDefaultMethod"
	 */
	public void setHttpDefaultMethod(String httpDefaultMethod) {
		this.httpDefaultMethod = httpDefaultMethod;
	}

	/**
	 * @return
	 * @uml.property  name="httpQueryParameterSeparatorDefault"
	 */
	public String getHttpQueryParameterSeparatorDefault() {
		return httpQueryParameterSeparatorDefault;
	}

	/**
	 * @param httpQueryParameterSeparatorDefault
	 * @uml.property  name="httpQueryParameterSeparatorDefault"
	 */
	public void setHttpQueryParameterSeparatorDefault(String httpQueryParameterSeparatorDefault) {
		this.httpQueryParameterSeparatorDefault = httpQueryParameterSeparatorDefault;
	}

	/**
	 * @return
	 * @uml.property  name="style"
	 */
	public Enum getStyle() {
		return style;
	}

	/**
	 * @param style
	 * @uml.property  name="style"
	 */
	public void setStyle(Enum style) {
		this.style = style;
	}

	/**
	 * @return
	 * @uml.property  name="transportProtocol"
	 */
	public String getTransportProtocol() {
		return transportProtocol;
	}

	/**
	 * @param transportProtocol
	 * @uml.property  name="transportProtocol"
	 */
	public void setTransportProtocol(String transportProtocol) {
		this.transportProtocol = transportProtocol;
	}

	/**
	 * @return
	 * @uml.property  name="typeOfBinding"
	 */
	public String getTypeOfBinding() {
		return typeOfBinding;
	}

	/**
	 * @param typeOfBinding
	 * @uml.property  name="typeOfBinding"
	 */
	public void setTypeOfBinding(String typeOfBinding) {
		this.typeOfBinding = typeOfBinding;
	}

	/**
	 * @return
	 * @uml.property  name="version"
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version
	 * @uml.property  name="version"
	 */
	public void setVersion(String version) {
		this.version = version;
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
	 * @uml.property  name="interfaces"
	 */
	public InterfaceBean getInterfaces() {
		return interfaces;
	}

	/**
	 * @param interfaces
	 * @uml.property  name="interfaces"
	 */
	public void setInterfaces(InterfaceBean interfaces) {
		this.interfaces = interfaces;
	}

	/**
	 * @return
	 * @uml.property  name="bindingOperations"
	 */
	public List<BindingOperationBean> getBindingOperations() {
		return bindingOperations;
	}

	/**
	 * @param bindingOperations
	 * @uml.property  name="bindingOperations"
	 */
	public void setBindingOperations(List<BindingOperationBean> bindingOperations) {
		this.bindingOperations = bindingOperations;
	}

	@Override
	public String toString() {
		return name;
	}

}
