package org.wsssoapbox.bean.model.soap;

import java.io.Serializable;

import javax.xml.namespace.QName;
import javax.xml.soap.Name;


public class SoapHeaderBean implements Serializable{
	private static final long serialVersionUID = 1L;
	String name;
	private String type = "(Header)";
	private String prefix;
	private String baseURI;
	private QName elementQName;
	private Name elementName;
	private String encodingStyle;
	private String localName;
	private String namespaceURI;
	private String nodeName;
	private String tagName;
	private String textContent;
	private short setNodeType;
	private String value;
	
	public SoapHeaderBean() {
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
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

	public String getLocalName() {
		return localName;
	}

	public void setLocalName(String localName) {
		this.localName = localName;
	}

	public String getNamespaceURI() {
		return namespaceURI;
	}

	public void setNamespaceURI(String namespaceURI) {
		this.namespaceURI = namespaceURI;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getTextContent() {
		return textContent;
	}

	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}

	public void setElementQName(QName elementQName) {
		this.elementQName = elementQName;
	}

	public void setElementName(Name elementName) {
		this.elementName = elementName;
	}

	public short getSetNodeType() {
		return setNodeType;
	}

	public void setSetNodeType(short setNodeType) {
		this.setNodeType = setNodeType;
	}

	public QName getElementQName() {
		return elementQName;
	}

	public Name getElementName() {
		return elementName;
	}
}
