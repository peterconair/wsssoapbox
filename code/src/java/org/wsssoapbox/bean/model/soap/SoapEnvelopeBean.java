package org.wsssoapbox.bean.model.soap;

import java.io.Serializable;

import javax.xml.namespace.QName;
import javax.xml.soap.Name;


public class SoapEnvelopeBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private String name;
	private String value = "";
	private String type = "(Envelope)";
	private SoapBodyBean soapBody;
	private SoapHeaderBean soapHeader;
	private String baseURI;
	private Name elementName;
	private QName elementQName;
	private String encodingStyle;
	private String localName;
	private String namespaceURI;
	private String nodeName;
	private short nodeType;
	private String nodeValue;
	private String prefix;
	private String tagName;
	private String textContent;
	private String elementsByTagNameNS;
	

	public SoapEnvelopeBean() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNamespaceURI() {
		return namespaceURI;
	}
	public void setNamespaceURI(String namespaceURI) {
		this.namespaceURI = namespaceURI;
	}
	public String getBaseURI() {
		return baseURI;
	}

	public void setBaseURI(String baseURI) {
		this.baseURI = baseURI;
	}
	public String getEncodingStyle() {
		return encodingStyle;
	}
	public void setEncodingStyle(String encodingStyle) {
		this.encodingStyle = encodingStyle;
	}

	public String getElementsByTagNameNS() {
		return elementsByTagNameNS;
	}
	public void setElementsByTagNameNS(String elementsByTagNameNS) {
		this.elementsByTagNameNS = elementsByTagNameNS;
	}
	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getTextContent() {
		return textContent;
	}

	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}

	public String getLocalName() {
		return localName;
	}
	public void setLocalName(String localName) {
		this.localName = localName;
	}

	public Name getElementName() {
		return elementName;
	}

	public String getValue() {
		return value;
	}

	public QName getElementQName() {
		return elementQName;
	}

	public void setElementQName(QName elementQName) {
		this.elementQName = elementQName;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public short getNodeType() {
		return nodeType;
	}

	public void setNodeType(short nodeType) {
		this.nodeType = nodeType;
	}

	public String getNodeValue() {
		return nodeValue;
	}

	public void setNodeValue(String nodeValue) {
		this.nodeValue = nodeValue;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public void setElementName(Name elementName) {
		this.elementName = elementName;
	}

	public void setValue(String value) {
		this.value = value;
	}
	public SoapBodyBean getSoapBody() {
		return soapBody;
	}
	public void setSoapBody(SoapBodyBean soapBody) {
		this.soapBody = soapBody;
	}
	public SoapHeaderBean getSoapHeader() {
		return soapHeader;
	}
	public void setSoapHeader(SoapHeaderBean soapHeader) {
		this.soapHeader = soapHeader;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
