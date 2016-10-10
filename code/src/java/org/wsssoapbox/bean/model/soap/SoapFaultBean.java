package org.wsssoapbox.bean.model.soap;

import java.io.Serializable;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.soap.Name;

/**
 * @author  Peter
 */
public class SoapFaultBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private String name;
	private String value;
	private String type;
	private String baseURI;
	private QName elementQName;
	private Name elementName;
	private String encodingStyle;
	private String faultActor;
	private String faultCode;
	private Name faultCodeAsName;
	private QName faultCodeAsQName;
	private String faultNode;
	private String faultRole;
	private String faultString;
	private String localName;
	private String namespaceURI;
	private String ndeName;
	private String nodeValue;
	private String tagName;
	private String textContent;
	private String nodeName;
	private short nodeType;	
	private String prefix;

   /**
    * @return the name
    */
   public String getName() {
      return name;
   }

   /**
    * @param name the name to set
    */
   public void setName(String name) {
      this.name = name;
   }

   /**
    * @return the value
    */
   public String getValue() {
      return value;
   }

   /**
    * @param value the value to set
    */
   public void setValue(String value) {
      this.value = value;
   }

   /**
    * @return the type
    */
   public String getType() {
      return type;
   }

   /**
    * @param type the type to set
    */
   public void setType(String type) {
      this.type = type;
   }

   /**
    * @return the baseURI
    */
   public String getBaseURI() {
      return baseURI;
   }

   /**
    * @param baseURI the baseURI to set
    */
   public void setBaseURI(String baseURI) {
      this.baseURI = baseURI;
   }

   /**
    * @return the elementQName
    */
   public QName getElementQName() {
      return elementQName;
   }

   /**
    * @param elementQName the elementQName to set
    */
   public void setElementQName(QName elementQName) {
      this.elementQName = elementQName;
   }

   /**
    * @return the elementName
    */
   public Name getElementName() {
      return elementName;
   }

   /**
    * @param elementName the elementName to set
    */
   public void setElementName(Name elementName) {
      this.elementName = elementName;
   }

   /**
    * @return the encodingStyle
    */
   public String getEncodingStyle() {
      return encodingStyle;
   }

   /**
    * @param encodingStyle the encodingStyle to set
    */
   public void setEncodingStyle(String encodingStyle) {
      this.encodingStyle = encodingStyle;
   }

   /**
    * @return the faultActor
    */
   public String getFaultActor() {
      return faultActor;
   }

   /**
    * @param faultActor the faultActor to set
    */
   public void setFaultActor(String faultActor) {
      this.faultActor = faultActor;
   }

   /**
    * @return the faultCode
    */
   public String getFaultCode() {
      return faultCode;
   }

   /**
    * @param faultCode the faultCode to set
    */
   public void setFaultCode(String faultCode) {
      this.faultCode = faultCode;
   }

   /**
    * @return the faultCodeAsName
    */
   public Name getFaultCodeAsName() {
      return faultCodeAsName;
   }

   /**
    * @param faultCodeAsName the faultCodeAsName to set
    */
   public void setFaultCodeAsName(Name faultCodeAsName) {
      this.faultCodeAsName = faultCodeAsName;
   }

   /**
    * @return the faultCodeAsQName
    */
   public QName getFaultCodeAsQName() {
      return faultCodeAsQName;
   }

   /**
    * @param faultCodeAsQName the faultCodeAsQName to set
    */
   public void setFaultCodeAsQName(QName faultCodeAsQName) {
      this.faultCodeAsQName = faultCodeAsQName;
   }

   /**
    * @return the faultNode
    */
   public String getFaultNode() {
      return faultNode;
   }

   /**
    * @param faultNode the faultNode to set
    */
   public void setFaultNode(String faultNode) {
      this.faultNode = faultNode;
   }

   /**
    * @return the faultRole
    */
   public String getFaultRole() {
      return faultRole;
   }

   /**
    * @param faultRole the faultRole to set
    */
   public void setFaultRole(String faultRole) {
      this.faultRole = faultRole;
   }

   /**
    * @return the faultString
    */
   public String getFaultString() {
      return faultString;
   }

   /**
    * @param faultString the faultString to set
    */
   public void setFaultString(String faultString) {
      this.faultString = faultString;
   }

   /**
    * @return the localName
    */
   public String getLocalName() {
      return localName;
   }

   /**
    * @param localName the localName to set
    */
   public void setLocalName(String localName) {
      this.localName = localName;
   }

   /**
    * @return the namespaceURI
    */
   public String getNamespaceURI() {
      return namespaceURI;
   }

   /**
    * @param namespaceURI the namespaceURI to set
    */
   public void setNamespaceURI(String namespaceURI) {
      this.namespaceURI = namespaceURI;
   }

   /**
    * @return the ndeName
    */
   public String getNdeName() {
      return ndeName;
   }

   /**
    * @param ndeName the ndeName to set
    */
   public void setNdeName(String ndeName) {
      this.ndeName = ndeName;
   }

   /**
    * @return the nodeValue
    */
   public String getNodeValue() {
      return nodeValue;
   }

   /**
    * @param nodeValue the nodeValue to set
    */
   public void setNodeValue(String nodeValue) {
      this.nodeValue = nodeValue;
   }

   /**
    * @return the tagName
    */
   public String getTagName() {
      return tagName;
   }

   /**
    * @param tagName the tagName to set
    */
   public void setTagName(String tagName) {
      this.tagName = tagName;
   }

   /**
    * @return the textContent
    */
   public String getTextContent() {
      return textContent;
   }

   /**
    * @param textContent the textContent to set
    */
   public void setTextContent(String textContent) {
      this.textContent = textContent;
   }

   /**
    * @return the nodeName
    */
   public String getNodeName() {
      return nodeName;
   }

   /**
    * @param nodeName the nodeName to set
    */
   public void setNodeName(String nodeName) {
      this.nodeName = nodeName;
   }

   /**
    * @return the nodeType
    */
   public short getNodeType() {
      return nodeType;
   }

   /**
    * @param nodeType the nodeType to set
    */
   public void setNodeType(short nodeType) {
      this.nodeType = nodeType;
   }

   /**
    * @return the prefix
    */
   public String getPrefix() {
      return prefix;
   }

   /**
    * @param prefix the prefix to set
    */
   public void setPrefix(String prefix) {
      this.prefix = prefix;
   }

}
