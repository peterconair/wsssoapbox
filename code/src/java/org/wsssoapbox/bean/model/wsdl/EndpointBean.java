package org.wsssoapbox.bean.model.wsdl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.xml.namespace.QName;


/**
 * @author  Peter
 */
@ManagedBean(name="endpointBean")
@SessionScoped
public class EndpointBean implements Serializable   {

	private static final long serialVersionUID = 1L;
	private String name;
	private String prefix;
	private String localPart;
	private String address;
	private String httpAuthenticationRealm;
	private String httpAuthenticationScheme;
	private String documentation;
	private List <?> otherElements;
	private Map <QName ,String> otherAttributes;
	private BindingBean binding;
	private ServicesBean  service;
	public EndpointBean() {
	}

 
	
	public EndpointBean(String name) {
		this.name = name;
	}
	


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

   /**
    * @return the localPart
    */
   public String getLocalPart() {
      return localPart;
   }

   /**
    * @param localPart the localPart to set
    */
   public void setLocalPart(String localPart) {
      this.localPart = localPart;
   }

   /**
    * @return the address
    */
   public String getAddress() {
      return address;
   }

   /**
    * @param address the address to set
    */
   public void setAddress(String address) {
      this.address = address;
   }

   /**
    * @return the httpAuthenticationRealm
    */
   public String getHttpAuthenticationRealm() {
      return httpAuthenticationRealm;
   }

   /**
    * @param httpAuthenticationRealm the httpAuthenticationRealm to set
    */
   public void setHttpAuthenticationRealm(String httpAuthenticationRealm) {
      this.httpAuthenticationRealm = httpAuthenticationRealm;
   }

   /**
    * @return the httpAuthenticationScheme
    */
   public String getHttpAuthenticationScheme() {
      return httpAuthenticationScheme;
   }

   /**
    * @param httpAuthenticationScheme the httpAuthenticationScheme to set
    */
   public void setHttpAuthenticationScheme(String httpAuthenticationScheme) {
      this.httpAuthenticationScheme = httpAuthenticationScheme;
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
    * @return the otherAttributes
    */
   public Map <QName ,String> getOtherAttributes() {
      return otherAttributes;
   }

   /**
    * @param otherAttributes the otherAttributes to set
    */
   public void setOtherAttributes(Map <QName ,String> otherAttributes) {
      this.otherAttributes = otherAttributes;
   }

   /**
    * @return the binding
    */
   public BindingBean getBinding() {
      return binding;
   }

   /**
    * @param binding the binding to set
    */
   public void setBinding(BindingBean binding) {
      this.binding = binding;
   }

   /**
    * @return the service
    */
   public ServicesBean getService() {
      return service;
   }

   /**
    * @param service the service to set
    */
   public void setService(ServicesBean service) {
      this.service = service;
   }
  @Override
   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      }
      if (getClass() != obj.getClass()) {
         return false;
      }
      final EndpointBean other = (EndpointBean) obj;
      if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
         return false;
      }
      if ((this.prefix == null) ? (other.prefix != null) : !this.prefix.equals(other.prefix)) {
         return false;
      }
      if ((this.localPart == null) ? (other.localPart != null) : !this.localPart.equals(other.localPart)) {
         return false;
      }
      return true;
   }

   @Override
   public int hashCode() {
      int hash = 7;
      hash = 89 * hash + (this.name != null ? this.name.hashCode() : 0);
      hash = 89 * hash + (this.prefix != null ? this.prefix.hashCode() : 0);
      hash = 89 * hash + (this.localPart != null ? this.localPart.hashCode() : 0);
      return hash;
   }
   
   
	@Override
	public String toString() {
		return name ;
	}

}
