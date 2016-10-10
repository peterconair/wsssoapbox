package org.wsssoapbox.bean.model.wsdl;

import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

/**
 * @author  Peter
 */
public class ImportBean {

	private String documentation;
	private String locationURI;
	private String namespaceURI;
	private Map <QName,String>  otherAttributes;
	private SchemaBean schema;
	private DescriptionBean descriptionBean;
	private List <?> otherElements;
	
	
	@Override
	public String toString() {
		return "ImportBean [documentation=" + getDocumentation() + ", locationURI=" + getLocationURI() + ", namespaceURI="
				+ getNamespaceURI() + ", otherAttributes=" + getOtherAttributes() + ", schema=" + getSchema() + ", descriptionBean="
				+ getDescriptionBean() + ", otherElements=" + getOtherElements() + "]";
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
    * @return the otherAttributes
    */
   public Map <QName,String> getOtherAttributes() {
      return otherAttributes;
   }

   /**
    * @param otherAttributes the otherAttributes to set
    */
   public void setOtherAttributes(Map <QName,String> otherAttributes) {
      this.otherAttributes = otherAttributes;
   }

   /**
    * @return the schema
    */
   public SchemaBean getSchema() {
      return schema;
   }

   /**
    * @param schema the schema to set
    */
   public void setSchema(SchemaBean schema) {
      this.schema = schema;
   }

   /**
    * @return the descriptionBean
    */
   public DescriptionBean getDescriptionBean() {
      return descriptionBean;
   }

   /**
    * @param descriptionBean the descriptionBean to set
    */
   public void setDescriptionBean(DescriptionBean descriptionBean) {
      this.descriptionBean = descriptionBean;
   }

   /**
    * @return the otherElements
    */
   public List <?> getOtherElements() {
      return otherElements;
   }

   /**
    * @param otherElements the otherElements to set
    */
   public void setOtherElements(List <?> otherElements) {
      this.otherElements = otherElements;
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
}
