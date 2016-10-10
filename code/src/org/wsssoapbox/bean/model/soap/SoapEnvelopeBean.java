package org.wsssoapbox.bean.model.soap;

import java.io.Serializable;

import javax.xml.namespace.QName;
import javax.xml.soap.Name;


public class SoapEnvelopeBean implements Serializable{

	private static final long serialVersionUID = 1L;
	/**
	 * @uml.property  name="name"
	 */
	private String name;
	/**
	 * @uml.property  name="value"
	 */
	private String value = "";
	/**
	 * @uml.property  name="type"
	 */
	private String type = "(Envelope)";
	/**
	 * @uml.property  name="soapBody"
	 * @uml.associationEnd  
	 */
	private SoapBodyBean soapBody;
	/**
	 * @uml.property  name="soapHeader"
	 * @uml.associationEnd  
	 */
	private SoapHeaderBean soapHeader;


	/**
	 * @uml.property  name="baseURI"
	 */
	private String baseURI;
	/**
	 * @uml.property  name="elementName"
	 * @uml.associationEnd  
	 */
	private Name elementName;
	/**
	 * @uml.property  name="elementQName"
	 * @uml.associationEnd  
	 */
	private QName elementQName;
	/**
	 * @uml.property  name="encodingStyle"
	 */
	private String encodingStyle;
	/**
	 * @uml.property  name="localName"
	 */
	private String localName;
	/**
	 * @uml.property  name="namespaceURI"
	 */
	private String namespaceURI;
	/**
	 * @uml.property  name="nodeName"
	 */
	private String nodeName;
	/**
	 * @uml.property  name="nodeType"
	 */
	private short nodeType;
	/**
	 * @uml.property  name="nodeValue"
	 */
	private String nodeValue;
	/**
	 * @uml.property  name="prefix"
	 */
	private String prefix;
	/**
	 * @uml.property  name="tagName"
	 */
	private String tagName;
	/**
	 * @uml.property  name="textContent"
	 */
	private String textContent;

	/**
	 * @uml.property  name="elementsByTagNameNS"
	 */
	private String elementsByTagNameNS;
	

	public SoapEnvelopeBean() {
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
	 * @uml.property  name="baseURI"
	 */
	public String getBaseURI() {
		return baseURI;
	}

	/**
	 * @param baseURI
	 * @uml.property  name="baseURI"
	 */
	public void setBaseURI(String baseURI) {
		this.baseURI = baseURI;
	}

	/**
	 * @return
	 * @uml.property  name="encodingStyle"
	 */
	public String getEncodingStyle() {
		return encodingStyle;
	}

	/**
	 * @param encodingStyle
	 * @uml.property  name="encodingStyle"
	 */
	public void setEncodingStyle(String encodingStyle) {
		this.encodingStyle = encodingStyle;
	}

	/**
	 * @return
	 * @uml.property  name="elementsByTagNameNS"
	 */
	public String getElementsByTagNameNS() {
		return elementsByTagNameNS;
	}

	/**
	 * @param elementsByTagNameNS
	 * @uml.property  name="elementsByTagNameNS"
	 */
	public void setElementsByTagNameNS(String elementsByTagNameNS) {
		this.elementsByTagNameNS = elementsByTagNameNS;
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
	 * @uml.property  name="textContent"
	 */
	public String getTextContent() {
		return textContent;
	}

	/**
	 * @param textContent
	 * @uml.property  name="textContent"
	 */
	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}

	/**
	 * @return
	 * @uml.property  name="localName"
	 */
	public String getLocalName() {
		return localName;
	}

	/**
	 * @param localName
	 * @uml.property  name="localName"
	 */
	public void setLocalName(String localName) {
		this.localName = localName;
	}

	/**
	 * @return
	 * @uml.property  name="elementName"
	 */
	public Name getElementName() {
		return elementName;
	}

	/**
	 * @return
	 * @uml.property  name="value"
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @return
	 * @uml.property  name="elementQName"
	 */
	public QName getElementQName() {
		return elementQName;
	}

	/**
	 * @param elementQName
	 * @uml.property  name="elementQName"
	 */
	public void setElementQName(QName elementQName) {
		this.elementQName = elementQName;
	}

	/**
	 * @return
	 * @uml.property  name="nodeName"
	 */
	public String getNodeName() {
		return nodeName;
	}

	/**
	 * @param nodeName
	 * @uml.property  name="nodeName"
	 */
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	/**
	 * @return
	 * @uml.property  name="nodeType"
	 */
	public short getNodeType() {
		return nodeType;
	}

	/**
	 * @param nodeType
	 * @uml.property  name="nodeType"
	 */
	public void setNodeType(short nodeType) {
		this.nodeType = nodeType;
	}

	/**
	 * @return
	 * @uml.property  name="nodeValue"
	 */
	public String getNodeValue() {
		return nodeValue;
	}

	/**
	 * @param nodeValue
	 * @uml.property  name="nodeValue"
	 */
	public void setNodeValue(String nodeValue) {
		this.nodeValue = nodeValue;
	}

	/**
	 * @return
	 * @uml.property  name="tagName"
	 */
	public String getTagName() {
		return tagName;
	}

	/**
	 * @param tagName
	 * @uml.property  name="tagName"
	 */
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	/**
	 * @param elementName
	 * @uml.property  name="elementName"
	 */
	public void setElementName(Name elementName) {
		this.elementName = elementName;
	}

	/**
	 * @param value
	 * @uml.property  name="value"
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return
	 * @uml.property  name="soapBody"
	 */
	public SoapBodyBean getSoapBody() {
		return soapBody;
	}

	/**
	 * @param soapBody
	 * @uml.property  name="soapBody"
	 */
	public void setSoapBody(SoapBodyBean soapBody) {
		this.soapBody = soapBody;
	}

	/**
	 * @return
	 * @uml.property  name="soapHeader"
	 */
	public SoapHeaderBean getSoapHeader() {
		return soapHeader;
	}

	/**
	 * @param soapHeader
	 * @uml.property  name="soapHeader"
	 */
	public void setSoapHeader(SoapHeaderBean soapHeader) {
		this.soapHeader = soapHeader;
	}

	/**
	 * @return
	 * @uml.property  name="type"
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 * @uml.property  name="type"
	 */
	public void setType(String type) {
		this.type = type;
	}
}
