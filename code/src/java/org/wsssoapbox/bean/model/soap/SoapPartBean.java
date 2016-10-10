package org.wsssoapbox.bean.model.soap;

import java.io.Serializable;
import java.util.Iterator;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.w3c.dom.Element;

@ManagedBean(name = "soapPartBean")
@SessionScoped
public class SoapPartBean implements Serializable{
	
	private static final long serialVersionUID = 8249620693178614401L;

	private String baseURI;
	private String contentId;
	private String contentLocation;
	private String cocumentURI;
	private String documentURI;
     private String tagName;
     private String localName;
	private String inputEncoding;
	private String namespaceURI;
	private String nodeName;
	private short nodeType;
	private String nodeValue;
	private String prefix;
	private boolean strictErrorChecking;
	private String textContent;
	private String value;
	private String xmlEncoding;
	private boolean xmlStandalone;
     private boolean content;
	private String xmlVersion;		
	Iterator AllMimeHeaders;	
	SoapEnvelopeBean soapEnvelope;
     private Element element;


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
	 * @uml.property  name="contentId"
	 */
	public String getContentId() {
		return contentId;
	}

	/**
	 * @param contentId
	 * @uml.property  name="contentId"
	 */
	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	/**
	 * @return
	 * @uml.property  name="contentLocation"
	 */
	public String getContentLocation() {
		return contentLocation;
	}

	/**
	 * @param contentLocation
	 * @uml.property  name="contentLocation"
	 */
	public void setContentLocation(String contentLocation) {
		this.contentLocation = contentLocation;
	}

	/**
	 * @return
	 * @uml.property  name="cocumentURI"
	 */
	public String getCocumentURI() {
		return cocumentURI;
	}

	/**
	 * @param cocumentURI
	 * @uml.property  name="cocumentURI"
	 */
	public void setCocumentURI(String cocumentURI) {
		this.cocumentURI = cocumentURI;
	}

	/**
	 * @return
	 * @uml.property  name="inputEncoding"
	 */
	public String getInputEncoding() {
		return inputEncoding;
	}

	/**
	 * @param inputEncoding
	 * @uml.property  name="inputEncoding"
	 */
	public void setInputEncoding(String inputEncoding) {
		this.inputEncoding = inputEncoding;
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
	 * @uml.property  name="documentURI"
	 */
	public String getDocumentURI() {
		return documentURI;
	}

	/**
	 * @param documentURI
	 * @uml.property  name="documentURI"
	 */
	public void setDocumentURI(String documentURI) {
		this.documentURI = documentURI;
	}


	/**
	 * @return
	 * @uml.property  name="nodeValue"
	 */
	public String getNodeValue() {
		return nodeValue;
	}

	/**
	 * @param nodeType
	 * @uml.property  name="nodeType"
	 */
	public void setNodeType(short nodeType) {
		this.nodeType = nodeType;
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
	 * @uml.property  name="xmlStandalone"
	 */
	public boolean isXmlStandalone() {
		return xmlStandalone;
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
	 * @uml.property  name="xmlEncoding"
	 */
	public String getXmlEncoding() {
		return xmlEncoding;
	}

	/**
	 * @return
	 * @uml.property  name="soapEnvelope"
	 */
	public SoapEnvelopeBean getSoapEnvelope() {
		return soapEnvelope;
	}

	/**
	 * @param soapEnvelope
	 * @uml.property  name="soapEnvelope"
	 */
	public void setSoapEnvelope(SoapEnvelopeBean soapEnvelope) {
		this.soapEnvelope = soapEnvelope;
	}

	/**
	 * @param xmlEncoding
	 * @uml.property  name="xmlEncoding"
	 */
	public void setXmlEncoding(String xmlEncoding) {
		this.xmlEncoding = xmlEncoding;
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
	 * @uml.property  name="xmlVersion"
	 */
	public String getXmlVersion() {
		return xmlVersion;
	}

	/**
	 * @param xmlVersion
	 * @uml.property  name="xmlVersion"
	 */
	public void setXmlVersion(String xmlVersion) {
		this.xmlVersion = xmlVersion;
	}

	/**
	 * @return
	 * @uml.property  name="allMimeHeaders"
	 */
	public Iterator getAllMimeHeaders() {
		return AllMimeHeaders;
	}

	/**
	 * @param allMimeHeaders
	 * @uml.property  name="allMimeHeaders"
	 */
	public void setAllMimeHeaders(Iterator allMimeHeaders) {
		AllMimeHeaders = allMimeHeaders;
	}

	/**
	 * @return
	 * @uml.property  name="strictErrorChecking"
	 */
	public boolean isStrictErrorChecking() {
		return strictErrorChecking;
	}

	/**
	 * @param strictErrorChecking
	 * @uml.property  name="strictErrorChecking"
	 */
	public void setStrictErrorChecking(boolean strictErrorChecking) {
		this.strictErrorChecking = strictErrorChecking;
	}

	/**
	 * @return
	 * @uml.property  name="nodeType"
	 */
	public short getNodeType() {
		return nodeType;
	}

   /**
    * @return the content
    */
   public boolean isContent() {
      return content;
   }

   /**
    * @param content the content to set
    */
   public void setContent(boolean content) {
      this.content = content;
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
    * @return the element
    */
   public Element getElement() {
      return element;
   }

   /**
    * @param element the element to set
    */
   public void setElement(Element element) {
      this.element = element;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      }
      if (getClass() != obj.getClass()) {
         return false;
      }
      final SoapPartBean other = (SoapPartBean) obj;
      if ((this.tagName == null) ? (other.tagName != null) : !this.tagName.equals(other.tagName)) {
         return false;
      }
      return true;
   }

   @Override
   public int hashCode() {
      int hash = 7;
      hash = 59 * hash + (this.tagName != null ? this.tagName.hashCode() : 0);
      return hash;
   }


   
   
}
