package org.wsssoapbox.bean.model.soap;

import java.io.Serializable;

import javax.xml.namespace.QName;
import javax.xml.soap.Name;

public class SoapParameterBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * @uml.property  name="name"
	 */
	private String name;
	/**
	 * @uml.property  name="value"
	 */
	private String value;
	/**
	 * @uml.property  name="type"
	 */
	private String type;
	/**
	 * @uml.property  name="minOccurs"
	 */
	private int minOccurs;
	/**
	 * @uml.property  name="maxOccurs"
	 */
	private String maxOccurs;
	/**
	 * @uml.property  name="qName"
	 * @uml.associationEnd  
	 */
	private QName QName;
	/**
	 * @uml.property  name="defaultValue"
	 */
	private String defaultValue;
	/**
	 * @uml.property  name="prefix"
	 */
	private String prefix;

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
	 * @uml.property  name="tagName"
	 */
	private String tagName;
	/**
	 * @uml.property  name="textContent"
	 */
	private String textContent;

	public SoapParameterBean() {

	}

	public SoapParameterBean(String name, String type, int minOccurs, String maxOccurs, QName QName, String defaultValue) {
		this.name = name;
		this.type = type;
		this.minOccurs = minOccurs;
		this.maxOccurs = maxOccurs;
		this.QName = QName;
		this.defaultValue = defaultValue;
		this.prefix = QName.getPrefix();
		this.tagName = prefix+":"+name;
		this.value = defaultValue;
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
	 * @uml.property  name="minOccurs"
	 */
	public int getMinOccurs() {
		return minOccurs;
	}

	/**
	 * @param minOccurs
	 * @uml.property  name="minOccurs"
	 */
	public void setMinOccurs(int minOccurs) {
		this.minOccurs = minOccurs;
	}

	/**
	 * @return
	 * @uml.property  name="maxOccurs"
	 */
	public String getMaxOccurs() {
		return maxOccurs;
	}

	/**
	 * @param maxOccurs
	 * @uml.property  name="maxOccurs"
	 */
	public void setMaxOccurs(String maxOccurs) {
		this.maxOccurs = maxOccurs;
	}

	/**
	 * @return
	 * @uml.property  name="defaultValue"
	 */
	public String getDefaultValue() {
		return defaultValue;
	}

	/**
	 * @param defaultValue
	 * @uml.property  name="defaultValue"
	 */
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	/**
	 * @return
	 * @uml.property  name="qName"
	 */
	public QName getQName() {
		return QName;
	}

	/**
	 * @param qName
	 * @uml.property  name="qName"
	 */
	public void setQName(QName qName) {
		QName = qName;
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

	/**
	 * @param elementQName
	 * @uml.property  name="elementQName"
	 */
	public void setElementQName(QName elementQName) {
		this.elementQName = elementQName;
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
	 * @param elementName
	 * @uml.property  name="elementName"
	 */
	public void setElementName(Name elementName) {
		this.elementName = elementName;
	}

	@Override
	public String toString() {
		return "Parameter [name=" + name + "]";
	}

}
