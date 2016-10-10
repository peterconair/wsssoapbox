package org.wsssoapbox.bean.model.wsdl;

import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

/**
 * @author  Peter
 */
public class IncludeBean {
	/**
	 * @uml.property  name="documentation"
	 */
	private String documentation;
	/**
	 * @uml.property  name="locationURI"
	 */
	private String locationURI;
	/**
	 * @uml.property  name="otherAttributes"
	 */
	private Map<QName,String>otherAttributes;
	/**
	 * @uml.property  name="otherElements"
	 */
	private List<?> otherElements;
	/**
	 * @uml.property  name="description"
	 * @uml.associationEnd  
	 */
	private DescriptionBean description;
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
	/**
	 * @return
	 * @uml.property  name="description"
	 */
	public DescriptionBean getDescription() {
		return description;
	}
	/**
	 * @param description
	 * @uml.property  name="description"
	 */
	public void setDescription(DescriptionBean description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "IncludeBean [documentation=" + documentation + ", locationURI=" + locationURI + ", otherAttributes="
				+ otherAttributes + ", otherElements=" + otherElements + ", description=" + description + "]";
	}
}
