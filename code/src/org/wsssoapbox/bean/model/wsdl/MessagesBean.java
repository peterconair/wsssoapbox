package org.wsssoapbox.bean.model.wsdl;

import java.io.Serializable;
import java.util.Date;

public class MessagesBean implements Serializable{

	private static final long serialVersionUID = 5300229504680249368L;
	/**
	 * @uml.property  name="creaeDate"
	 */
	Date creaeDate;
	/**
	 * @uml.property  name="name"
	 */
	String name;
	/**
	 * @uml.property  name="prefix"
	 */
	private String prefix;
	/**
	 * @uml.property  name="namespaceURI"
	 */
	private String namespaceURI;
	/**
	 * @uml.property  name="localPart"
	 */
	private String localPart;
	
	public MessagesBean() {
	}
	public MessagesBean(String name) {
		this.name = name;
	}
	public MessagesBean(Date creaeDate, String name) {
		this.creaeDate = creaeDate;
		this.name = name;
	}

	/**
	 * @return
	 * @uml.property  name="creaeDate"
	 */
	public Date getCreaeDate() {
		return creaeDate;
	}

	/**
	 * @param creaeDate
	 * @uml.property  name="creaeDate"
	 */
	public void setCreaeDate(Date creaeDate) {
		this.creaeDate = creaeDate;
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
	 * @uml.property  name="localPart"
	 */
	public String getLocalPart() {
		return localPart;
	}
	/**
	 * @param localPart
	 * @uml.property  name="localPart"
	 */
	public void setLocalPart(String localPart) {
		this.localPart = localPart;
	}
	@Override
	public String toString() {
		return  name;
	}

}
