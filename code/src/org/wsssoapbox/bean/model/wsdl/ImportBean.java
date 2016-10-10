package org.wsssoapbox.bean.model.wsdl;

import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

/**
 * @author  Peter
 */
public class ImportBean {
	/**
	 * @uml.property  name="documentation"
	 */
	private String documentation;
	/**
	 * @uml.property  name="locationURI"
	 */
	private String locationURI;
	/**
	 * @uml.property  name="namespaceURI"
	 */
	private String namespaceURI;
	/**
	 * @uml.property  name="otherAttributes"
	 */
	private Map <QName,String>  otherAttributes;
	/**
	 * @uml.property  name="schema"
	 * @uml.associationEnd  
	 */
	private SchemaBean schema;
	
	/**
	 * @uml.property  name="descriptionBean"
	 * @uml.associationEnd  
	 */
	private DescriptionBean descriptionBean;
	/**
	 * @uml.property  name="otherElements"
	 */
	List <?> otherElements;
	
	
	
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
	 * @uml.property  name="locationURI"
	 */
	public String getLocationURI() {
		return locationURI;
	}
	/**
	 * @param locationURI
	 * @uml.property  name="locationURI"
	 */
	public void setLocationURI(String locationURI) {
		this.locationURI = locationURI;
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
	 * @uml.property  name="schema"
	 */
	public SchemaBean getSchema() {
		return schema;
	}
	/**
	 * @param schema
	 * @uml.property  name="schema"
	 */
	public void setSchema(SchemaBean schema) {
		this.schema = schema;
	}
	/**
	 * @return
	 * @uml.property  name="descriptionBean"
	 */
	public DescriptionBean getDescriptionBean() {
		return descriptionBean;
	}
	/**
	 * @param descriptionBean
	 * @uml.property  name="descriptionBean"
	 */
	public void setDescriptionBean(DescriptionBean descriptionBean) {
		this.descriptionBean = descriptionBean;
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
	@Override
	public String toString() {
		return "ImportBean [documentation=" + documentation + ", locationURI=" + locationURI + ", namespaceURI="
				+ namespaceURI + ", otherAttributes=" + otherAttributes + ", schema=" + schema + ", descriptionBean="
				+ descriptionBean + ", otherElements=" + otherElements + "]";
	}
}
