package org.wsssoapbox.bean.model.soap;

import java.io.Serializable;

import javax.xml.namespace.QName;
import javax.xml.soap.Name;

import org.wsssoapbox.soap.SoapConstants;

public class SoapHeaderBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @uml.property  name="name"
	 */
	String name;

	/**
	 * @uml.property  name="type"
	 */
	private String type = "(Header)";
	/**
	 * @uml.property  name="prefix"
	 */
	private String prefix;
	/**
	 * @uml.property  name="baseURI"
	 */
	private String baseURI;
	/**
	 * @uml.property  name="elementQName"
	 * @uml.associationEnd  
	 */
	private QName elementQName;
	/**
	 * @uml.property  name="elementName"
	 * @uml.associationEnd  
	 */
	private Name elementName;
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
	 * @uml.property  name="tagName"
	 */
	private String tagName;
	/**
	 * @uml.property  name="textContent"
	 */
	private String textContent;
	/**
	 * @uml.property  name="setNodeType"
	 */
	private short setNodeType;
	/**
	 * @uml.property  name="value"
	 */
	private String value;
	
	public SoapHeaderBean() {
		
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
	 * @uml.property  name="value"
	 */
	public String getValue() {
		return value;
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
	 * @param elementQName
	 * @uml.property  name="elementQName"
	 */
	public void setElementQName(QName elementQName) {
		this.elementQName = elementQName;
	}

	/**
	 * @param elementName
	 * @uml.property  name="elementName"
	 */
	public void setElementName(Name elementName) {
		this.elementName = elementName;
	}


	/**
	 * @return
	 * @uml.property  name="setNodeType"
	 */
	public short getSetNodeType() {
		return setNodeType;
	}


	/**
	 * @param setNodeType
	 * @uml.property  name="setNodeType"
	 */
	public void setSetNodeType(short setNodeType) {
		this.setNodeType = setNodeType;
	}


	/**
	 * @return
	 * @uml.property  name="elementQName"
	 */
	public QName getElementQName() {
		return elementQName;
	}


	/**
	 * @return
	 * @uml.property  name="elementName"
	 */
	public Name getElementName() {
		return elementName;
	}
}
