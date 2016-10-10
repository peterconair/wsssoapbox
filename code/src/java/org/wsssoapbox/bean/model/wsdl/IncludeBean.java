package org.wsssoapbox.bean.model.wsdl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

/**
 * @author  Peter
 */
public class IncludeBean implements Serializable{
   private static final long serialVersionUID = 5615704783785760618L;

	private String documentation;

	private String locationURI;

	private Map<QName,String>otherAttributes;

	private List<?> otherElements;

	private DescriptionBean description;

	@Override
	public String toString() {
		return "IncludeBean [documentation=" + getDocumentation() + ", locationURI=" + getLocationURI() + ", otherAttributes="
				+ getOtherAttributes() + ", otherElements=" + getOtherElements() + ", description=" + getDescription() + "]";
	}

   /**
    * @return the documentation
    */
   public String getDocumentation() {
      return documentation;
   }

   /**
    * @param documentation the documentation to set
    */
   public void setDocumentation(String documentation) {
      this.documentation = documentation;
   }

   /**
    * @return the locationURI
    */
   public String getLocationURI() {
      return locationURI;
   }

   /**
    * @param locationURI the locationURI to set
    */
   public void setLocationURI(String locationURI) {
      this.locationURI = locationURI;
   }

   /**
    * @return the otherAttributes
    */
   public Map<QName,String> getOtherAttributes() {
      return otherAttributes;
   }

   /**
    * @param otherAttributes the otherAttributes to set
    */
   public void setOtherAttributes(Map<QName,String> otherAttributes) {
      this.otherAttributes = otherAttributes;
   }

   /**
    * @return the otherElements
    */
   public List<?> getOtherElements() {
      return otherElements;
   }

   /**
    * @param otherElements the otherElements to set
    */
   public void setOtherElements(List<?> otherElements) {
      this.otherElements = otherElements;
   }

   /**
    * @return the description
    */
   public DescriptionBean getDescription() {
      return description;
   }

   /**
    * @param description the description to set
    */
   public void setDescription(DescriptionBean description) {
      this.description = description;
   }
}
