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
@ManagedBean(name="service")
@SessionScoped
public class ServicesBean implements Serializable{

	private static final long serialVersionUID = 1L;

	public ServicesBean() {
	}

	private String name ;
	private String prefix;
	private String namespaceURI;
	private String localPart;
	private QName qname;
	private String documentation;
	private Map <QName,String> otherAttributes;
	private List <?> otherElements;
	private InterfaceBean interfaceType;
	List <EndpointBean> endpoints;
	
	public ServicesBean(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public QName getQname() {
		return qname;
	}

	public void setQname(QName qname) {
		this.qname = qname;
	}

	public String getDocumentation() {
		return documentation;
	}

	public void setDocumentation(String documentation) {
		this.documentation = documentation;
	}

	public Map<QName,String> getOtherAttributes() {
		return otherAttributes;
	}

	public void setOtherAttributes(Map<QName,String> otherAttributes) {
		this.otherAttributes = otherAttributes;
	}

	public List<?> getOtherElements() {
		return otherElements;
	}

	public void setOtherElements(List<?> otherElements) {
		this.otherElements = otherElements;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getNamespaceURI() {
		return namespaceURI;
	}

	public void setNamespaceURI(String namespaceURI) {
		this.namespaceURI = namespaceURI;
	}

	public String getLocalPart() {
		return localPart;
	}

	public void setLocalPart(String localPart) {
		this.localPart = localPart;
	}

	public List<EndpointBean> getEndpoints() {
		return endpoints;
	}

	public void setEndpoints(List<EndpointBean> endpoints) {
		this.endpoints = endpoints;
	}

	public InterfaceBean getInterfaceType() {
		return interfaceType;
	}

	public void setInterfaceType(InterfaceBean interfaceType) {
		this.interfaceType = interfaceType;
	}

	@Override
	public String toString() {
		return name;
	}

   @Override
   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      }
      if (getClass() != obj.getClass()) {
         return false;
      }
      final ServicesBean other = (ServicesBean) obj;
      if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
         return false;
      }
      if ((this.prefix == null) ? (other.prefix != null) : !this.prefix.equals(other.prefix)) {
         return false;
      }
      if ((this.localPart == null) ? (other.localPart != null) : !this.localPart.equals(other.localPart)) {
         return false;
      }
      if (this.qname != other.qname && (this.qname == null || !this.qname.equals(other.qname))) {
         return false;
      }
      if ((this.documentation == null) ? (other.documentation != null) : !this.documentation.equals(other.documentation)) {
         return false;
      }
      return true;
   }

   @Override
   public int hashCode() {
      int hash = 7;
      hash = 37 * hash + (this.name != null ? this.name.hashCode() : 0);
      hash = 37 * hash + (this.prefix != null ? this.prefix.hashCode() : 0);
      hash = 37 * hash + (this.namespaceURI != null ? this.namespaceURI.hashCode() : 0);
      hash = 37 * hash + (this.localPart != null ? this.localPart.hashCode() : 0);
      hash = 37 * hash + (this.qname != null ? this.qname.hashCode() : 0);
      hash = 37 * hash + (this.documentation != null ? this.documentation.hashCode() : 0);
      return hash;
   }

	

}
