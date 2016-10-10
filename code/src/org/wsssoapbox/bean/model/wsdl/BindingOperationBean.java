package org.wsssoapbox.bean.model.wsdl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

/**
 * @author  Peter
 */
public class BindingOperationBean implements Serializable {

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
	 * @uml.property  name="namespaceURI"
	 */
	private String namespaceURI;

	/**
	 * @uml.property  name="transportProtocol"
	 */
	private String transportProtocol;
	/**
	 * @uml.property  name="documentation"
	 */
	private String documentation;
	/**
	 * @uml.property  name="httpContentEncodingDefault"
	 */
	private String httpContentEncodingDefault;
	/**
	 * @uml.property  name="httpFaultSerialization"
	 */
	private String httpFaultSerialization;
	/**
	 * @uml.property  name="httpInputSerialization"
	 */
	private String httpInputSerialization;
	/**
	 * @uml.property  name="httpLocation"
	 */
	private String httpLocation;
	/**
	 * @uml.property  name="httpMethod"
	 */
	private String httpMethod;
	/**
	 * @uml.property  name="httpOutputSerialization"
	 */
	private String httpOutputSerialization;
	/**
	 * @uml.property  name="httpQueryParameterSeparator"
	 */
	private String httpQueryParameterSeparator;
	// ONE_WAY ,REQUEST_RESPONSE ,SOAP_RESPONSE 
	/**
	 * @uml.property  name="mEP"
	 */
	private Enum MEP;
	// Doc and RPC
	/**
	 * @uml.property  name="style"
	 */
	private String style;
	/**
	 * @uml.property  name="soapAction"
	 */
	private String soapAction;
	/**
	 * @uml.property  name="faults"
	 */
	List <BindingFaultBean> faults;
	/**
	 * @uml.property  name="otherAttributes"
	 */
	private Map <QName,String> otherAttributes;
	/**
	 * @uml.property  name="otherElements"
	 */
	private List<?> otherElements;	
	/**
	 * @uml.property  name="output"
	 * @uml.associationEnd  
	 */
	private BindingOutputBean output;
	/**
	 * @uml.property  name="input"
	 * @uml.associationEnd  
	 */
	private BindingInputBean input;
	/**
	 * @uml.property  name="operation"
	 * @uml.associationEnd  
	 */
	private OperationBean operation;
	
	
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
	 * @uml.property  name="httpFaultSerialization"
	 */
	public String getHttpFaultSerialization() {
		return httpFaultSerialization;
	}
	/**
	 * @param httpFaultSerialization
	 * @uml.property  name="httpFaultSerialization"
	 */
	public void setHttpFaultSerialization(String httpFaultSerialization) {
		this.httpFaultSerialization = httpFaultSerialization;
	}
	/**
	 * @return
	 * @uml.property  name="httpInputSerialization"
	 */
	public String getHttpInputSerialization() {
		return httpInputSerialization;
	}
	/**
	 * @param httpInputSerialization
	 * @uml.property  name="httpInputSerialization"
	 */
	public void setHttpInputSerialization(String httpInputSerialization) {
		this.httpInputSerialization = httpInputSerialization;
	}
	/**
	 * @return
	 * @uml.property  name="httpLocation"
	 */
	public String getHttpLocation() {
		return httpLocation;
	}
	/**
	 * @param httpLocation
	 * @uml.property  name="httpLocation"
	 */
	public void setHttpLocation(String httpLocation) {
		this.httpLocation = httpLocation;
	}
	/**
	 * @return
	 * @uml.property  name="httpMethod"
	 */
	public String getHttpMethod() {
		return httpMethod;
	}

	/**
	 * @param httpMethod
	 * @uml.property  name="httpMethod"
	 */
	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}
	/**
	 * @return
	 * @uml.property  name="httpOutputSerialization"
	 */
	public String getHttpOutputSerialization() {
		return httpOutputSerialization;
	}
	/**
	 * @param httpOutputSerialization
	 * @uml.property  name="httpOutputSerialization"
	 */
	public void setHttpOutputSerialization(String httpOutputSerialization) {
		this.httpOutputSerialization = httpOutputSerialization;
	}
	/**
	 * @return
	 * @uml.property  name="httpQueryParameterSeparator"
	 */
	public String getHttpQueryParameterSeparator() {
		return httpQueryParameterSeparator;
	}
	/**
	 * @param httpQueryParameterSeparator
	 * @uml.property  name="httpQueryParameterSeparator"
	 */
	public void setHttpQueryParameterSeparator(String httpQueryParameterSeparator) {
		this.httpQueryParameterSeparator = httpQueryParameterSeparator;
	}
	/**
	 * @return
	 * @uml.property  name="mEP"
	 */
	public Enum getMEP() {
		return MEP;
	}
	/**
	 * @param mEP
	 * @uml.property  name="mEP"
	 */
	public void setMEP(Enum mEP) {
		MEP = mEP;
	}
	/**
	 * @return
	 * @uml.property  name="style"
	 */
	public String getStyle() {
		return style;
	}
	/**
	 * @param style
	 * @uml.property  name="style"
	 */
	public void setStyle(String style) {
		this.style = style;
	}
	/**
	 * @return
	 * @uml.property  name="soapAction"
	 */
	public String getSoapAction() {
		return soapAction;
	}
	/**
	 * @param soapAction
	 * @uml.property  name="soapAction"
	 */
	public void setSoapAction(String soapAction) {
		this.soapAction = soapAction;
	}
	/**
	 * @return
	 * @uml.property  name="faults"
	 */
	public List<BindingFaultBean> getFaults() {
		return faults;
	}
	/**
	 * @param faults
	 * @uml.property  name="faults"
	 */
	public void setFaults(List<BindingFaultBean> faults) {
		this.faults = faults;
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
	 * @uml.property  name="output"
	 */
	public BindingOutputBean getOutput() {
		return output;
	}
	/**
	 * @param output
	 * @uml.property  name="output"
	 */
	public void setOutput(BindingOutputBean output) {
		this.output = output;
	}
	/**
	 * @return
	 * @uml.property  name="input"
	 */
	public BindingInputBean getInput() {
		return input;
	}
	/**
	 * @param input
	 * @uml.property  name="input"
	 */
	public void setInput(BindingInputBean input) {
		this.input = input;
	}
	
	/**
	 * @return
	 * @uml.property  name="operation"
	 */
	public OperationBean getOperation() {
		return operation;
	}
	/**
	 * @param operation
	 * @uml.property  name="operation"
	 */
	public void setOperation(OperationBean operation) {
		this.operation = operation;
	}
	@Override
	public String toString() {
		return  name;
	}
	


}
