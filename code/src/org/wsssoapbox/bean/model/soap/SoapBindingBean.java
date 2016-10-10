package org.wsssoapbox.bean.model.soap;

import java.io.Serializable;
import java.util.Map;
import org.wsssoapbox.soap.SoapConstants;

// this class prepare data to create soap message
/**
 * @author  Peter
 */
public class SoapBindingBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * @uml.property  name="namespacePrefix"
	 */
	private String namespacePrefix = SoapConstants.PREFIX_DEFAULT_VALUE;
	/**
	 * @uml.property  name="nameSpaceURI"
	 */
	private String nameSpaceURI = "";
	/**
	 * @uml.property  name="header"
	 */
	private boolean header;
	/**
	 * @uml.property  name="fault"
	 */
	private boolean fault;
	/**
	 * @uml.property  name="xmlStandalone"
	 */
	private boolean xmlStandalone;
	/**
	 * @uml.property  name="style"
	 */
	private String style;
	/**
	 * @uml.property  name="use"
	 */
	private String use;
	/**
	 * @uml.property  name="namespaces"
	 * @uml.associationEnd  qualifier="key:java.lang.String java.lang.String"
	 */
	private Map<String, String> namespaces;
	/**
	 * @uml.property  name="operation"
	 * @uml.associationEnd  
	 */
	private SoapOperationBean operation;
	/**
	 * @uml.property  name="soapFault"
	 * @uml.associationEnd  
	 */
	private SoapFaultBean soapFault;
	/**
	 * @uml.property  name="schemas"
	 */
	private Map<String, String> schemas;
    /**
	 * @uml.property  name="soapXMLRequest"
	 */
    private String soapXMLRequest;
    /**
	 * @uml.property  name="soapRawRequest"
	 */
    private String soapRawRequest;
    /**
	 * @uml.property  name="soapVersion"
	 */
    private String soapVersion;
    /**
	 * @uml.property  name="soapAction"
	 */
    private String soapAction;
    
	public SoapBindingBean() {
	}

	public SoapBindingBean(String namespacePrefix, String nameSpaceURI, boolean header,String soapVersion,String soapAction, Map<String, String> namespaces,
			SoapOperationBean operation, Map<String, String> schemas) {
		this.namespacePrefix = namespacePrefix;
		this.nameSpaceURI = nameSpaceURI;
		this.header = header;
		this.soapVersion = soapVersion;
		this.soapAction = soapAction;
		this.namespaces = namespaces;
		this.operation = operation;
		this.schemas = schemas;
	
	}

	/**
	 * @return
	 * @uml.property  name="schemas"
	 */
	public Map<String, String> getSchemas() {
		return schemas;
	}

	/**
	 * @param schemas
	 * @uml.property  name="schemas"
	 */
	public void setSchemas(Map<String, String> schemas) {
		this.schemas = schemas;
	}

	/**
	 * @return
	 * @uml.property  name="namespacePrefix"
	 */
	public String getNamespacePrefix() {
		return namespacePrefix;
	}

	/**
	 * @param namespacePrefix
	 * @uml.property  name="namespacePrefix"
	 */
	public void setNamespacePrefix(String namespacePrefix) {
		this.namespacePrefix = namespacePrefix;
	}

	/**
	 * @return
	 * @uml.property  name="nameSpaceURI"
	 */
	public String getNameSpaceURI() {
		return nameSpaceURI;
	}

	/**
	 * @param nameSpaceURI
	 * @uml.property  name="nameSpaceURI"
	 */
	public void setNameSpaceURI(String nameSpaceURI) {
		this.nameSpaceURI = nameSpaceURI;
	}

	/**
	 * @return
	 * @uml.property  name="namespaces"
	 */
	public Map<String, String> getNamespaces() {
		return namespaces;
	}

	/**
	 * @param namespaces
	 * @uml.property  name="namespaces"
	 */
	public void setNamespaces(Map<String, String> namespaces) {
		this.namespaces = namespaces;
	}

	/**
	 * @return
	 * @uml.property  name="operation"
	 */
	public SoapOperationBean getOperation() {
		return operation;
	}

	/**
	 * @param operation
	 * @uml.property  name="operation"
	 */
	public void setOperation(SoapOperationBean operation) {
		this.operation = operation;
	}

	/**
	 * @return
	 * @uml.property  name="header"
	 */
	public boolean isHeader() {
		return header;
	}

	/**
	 * @param header
	 * @uml.property  name="header"
	 */
	public void setHeader(boolean header) {
		this.header = header;
	}

	/**
	 * @return
	 * @uml.property  name="xmlStandalone"
	 */
	public boolean isXmlStandalone() {
		return xmlStandalone;
	}

	/**
	 * @param xmlStandalone
	 * @uml.property  name="xmlStandalone"
	 */
	public void setXmlStandalone(boolean xmlStandalone) {
		this.xmlStandalone = xmlStandalone;
	}

	/**
	 * @return
	 * @uml.property  name="fault"
	 */
	public boolean isFault() {
		return fault;
	}

	/**
	 * @param fault
	 * @uml.property  name="fault"
	 */
	public void setFault(boolean fault) {
		this.fault = fault;
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
	 * @uml.property  name="use"
	 */
	public String getUse() {
		return use;
	}

	/**
	 * @param use
	 * @uml.property  name="use"
	 */
	public void setUse(String use) {
		this.use = use;
	}

	/**
	 * @return
	 * @uml.property  name="soapXMLRequest"
	 */
	public String getSoapXMLRequest() {
		return soapXMLRequest;
	}

	/**
	 * @param soapXMLRequest
	 * @uml.property  name="soapXMLRequest"
	 */
	public void setSoapXMLRequest(String soapXMLRequest) {
		this.soapXMLRequest = soapXMLRequest;
	}

	/**
	 * @return
	 * @uml.property  name="soapRawRequest"
	 */
	public String getSoapRawRequest() {
		return soapRawRequest;
	}

	/**
	 * @param soapRawRequest
	 * @uml.property  name="soapRawRequest"
	 */
	public void setSoapRawRequest(String soapRawRequest) {
		this.soapRawRequest = soapRawRequest;
	}

	/**
	 * @return
	 * @uml.property  name="soapFault"
	 */
	public SoapFaultBean getSoapFault() {
		return soapFault;
	}

	/**
	 * @param soapFault
	 * @uml.property  name="soapFault"
	 */
	public void setSoapFault(SoapFaultBean soapFault) {
		this.soapFault = soapFault;
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
	 * @uml.property  name="soapVersion"
	 */
	public String getSoapVersion() {
		return soapVersion;
	}

	/**
	 * @param soapVersion
	 * @uml.property  name="soapVersion"
	 */
	public void setSoapVersion(String soapVersion) {
		this.soapVersion = soapVersion;
	}


}
