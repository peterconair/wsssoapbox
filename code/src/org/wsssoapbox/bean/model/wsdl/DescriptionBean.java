package org.wsssoapbox.bean.model.wsdl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

/**
 * @author  Peter
 */
public class DescriptionBean implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * @uml.property  name="name"
	 */
	private String name;
	/**
	 * @uml.property  name="prefix"
	 */
	private String prefix;
	/**
	 * @uml.property  name="localPart"
	 */
	private String localPart;
	
	/**
	 * @uml.property  name="qName"
	 * @uml.associationEnd  
	 */
	private QName qName;
	/**
	 * @uml.property  name="documentation"
	 */
	private String documentation;
	/**
	 * @uml.property  name="targetNamespace"
	 */
	private String targetNamespace;
	/**
	 * @uml.property  name="documentBaseURI"
	 */
	private String documentBaseURI;
	/**
	 * @uml.property  name="version"
	 */
	private String version;
	
	/**
	 * @uml.property  name="namespaces"
	 */
	private Map <String,String> namespaces;
	/**
	 * @uml.property  name="otherAttributes"
	 */
	private Map <QName ,String > otherAttributes;
	/**
	 * @uml.property  name="otherElements"
	 */
	private List<?> otherElements;
	/**
	 * @uml.property  name="schemaLocation"
	 */
	private Map <String,String> schemaLocation;
	
	/**
	 * @uml.property  name="services"
	 */
	private List <ServicesBean> services;
	/**
	 * @uml.property  name="type"
	 * @uml.associationEnd  
	 */
	private TypeBean type;
	/**
	 * @uml.property  name="interfaces"
	 */
	private List <InterfaceBean> Interfaces;
	/**
	 * @uml.property  name="bindings"
	 */
	private List <BindingBean> bindings;
	/**
	 * @uml.property  name="imports"
	 */
	private List <ImportBean> imports;
	/**
	 * @uml.property  name="includes"
	 */
	private List <IncludeBean> includes;
	public DescriptionBean() {
	}

	public DescriptionBean(String name) {
		this.name = name;
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
	 * @uml.property  name="targetNamespace"
	 */
	public String getTargetNamespace() {
		return targetNamespace;
	}

	/**
	 * @param targetNamespace
	 * @uml.property  name="targetNamespace"
	 */
	public void setTargetNamespace(String targetNamespace) {
		this.targetNamespace = targetNamespace;
	}

	/**
	 * @return
	 * @uml.property  name="documentBaseURI"
	 */
	public String getDocumentBaseURI() {
		return documentBaseURI;
	}

	/**
	 * @param documentBaseURI
	 * @uml.property  name="documentBaseURI"
	 */
	public void setDocumentBaseURI(String documentBaseURI) {
		this.documentBaseURI = documentBaseURI;
	}

	/**
	 * @return
	 * @uml.property  name="version"
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version
	 * @uml.property  name="version"
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	public QName getQName() {
		return qName;
	}

	public void setQName(QName qname) {
		this.qName = qname;
	}

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
	 * @uml.property  name="services"
	 */
	public List<ServicesBean> getServices() {
		return services;
	}

	/**
	 * @param servicesBean
	 * @uml.property  name="services"
	 */
	public void setServices(List<ServicesBean> servicesBean) {
		this.services = servicesBean;
	}

	/**
	 * @return
	 * @uml.property  name="type"
	 */
	public TypeBean getType() {
		return type;
	}

	public void setTypesBean(TypeBean typesBean) {
		this.type = typesBean;
	}

	/**
	 * @return
	 * @uml.property  name="interfaces"
	 */
	public List<InterfaceBean> getInterfaces() {
		return Interfaces;
	}

	/**
	 * @param interfacesBean
	 * @uml.property  name="interfaces"
	 */
	public void setInterfaces(List<InterfaceBean> interfacesBean) {
		Interfaces = interfacesBean;
	}
	public InterfaceBean getInterface(QName interfaceQName) {
		InterfaceBean Interface = new InterfaceBean();
		return Interface;
	}
	/**
	 * @return
	 * @uml.property  name="bindings"
	 */
	public List<BindingBean> getBindings() {
		return bindings;
	}

	public void setBindingBean(List<BindingBean> bindingBean) {
		this.bindings = bindingBean;
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

	/**
	 * @return
	 * @uml.property  name="namespaces"
	 */
	public Map<String, String> getNamespaces() {
		return namespaces;
	}

	/**
	 * @param namespaces
	 * @uml.property  name="namespaces"
	 */
	public void setNamespaces(Map<String, String> namespaces) {
		this.namespaces = namespaces;
	}

	/**
	 * @param type
	 * @uml.property  name="type"
	 */
	public void setType(TypeBean type) {
		this.type = type;
	}

	/**
	 * @param bindings
	 * @uml.property  name="bindings"
	 */
	public void setBindings(List<BindingBean> bindings) {
		this.bindings = bindings;
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
	 * @uml.property  name="schemaLocation"
	 */
	public Map<String, String> getSchemaLocation() {
		return schemaLocation;
	}

	/**
	 * @param schemaLocation
	 * @uml.property  name="schemaLocation"
	 */
	public void setSchemaLocation(Map<String, String> schemaLocation) {
		this.schemaLocation = schemaLocation;
	}

	/**
	 * @return
	 * @uml.property  name="imports"
	 */
	public List<ImportBean> getImports() {
		return imports;
	}

	/**
	 * @param imports
	 * @uml.property  name="imports"
	 */
	public void setImports(List<ImportBean> imports) {
		this.imports = imports;
	}

	/**
	 * @return
	 * @uml.property  name="includes"
	 */
	public List<IncludeBean> getIncludes() {
		return includes;
	}

	/**
	 * @param includes
	 * @uml.property  name="includes"
	 */
	public void setIncludes(List<IncludeBean> includes) {
		this.includes = includes;
	}

	@Override
	public String toString() {
		return name;
	}
}
