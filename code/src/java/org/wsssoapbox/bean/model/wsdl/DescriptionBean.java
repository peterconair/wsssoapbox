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

@ManagedBean(name="wsdlDucument")
@SessionScoped
public class DescriptionBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private String prefix;
	private String localPart;
	
	private QName qName;
	private String documentation;
	private String targetNamespace;
	private String documentBaseURI;
	private String version;
	private Map <String,String> namespaces;
	private Map <QName ,String > otherAttributes;
	private List<?> otherElements;
	private Map <String,String> schemaLocation;
	private List <ServicesBean> services;
	private TypeBean type;
	private List <InterfaceBean> Interfaces;
	private List <BindingBean> bindings;
	private List <ImportBean> imports;
	private List <IncludeBean> includes;
	public DescriptionBean() {
	}

	public DescriptionBean(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTargetNamespace() {
		return targetNamespace;
	}
	public void setTargetNamespace(String targetNamespace) {
		this.targetNamespace = targetNamespace;
	}

	public String getDocumentBaseURI() {
		return documentBaseURI;
	}
	public void setDocumentBaseURI(String documentBaseURI) {
		this.documentBaseURI = documentBaseURI;
	}

	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}

	public QName getQName() {
		return qName;
	}

	public void setQName(QName qname) {
		this.qName = qname;
	}

	public String getDocumentation() {
		return documentation;
	}

	public void setDocumentation(String documentation) {
		this.documentation = documentation;
	}
	public List<ServicesBean> getServices() {
		return services;
	}

	public void setServices(List<ServicesBean> servicesBean) {
		this.services = servicesBean;
	}

	public TypeBean getType() {
		return type;
	}

	public void setTypesBean(TypeBean typesBean) {
		this.type = typesBean;
	}

	public List<InterfaceBean> getInterfaces() {
		return Interfaces;
	}
	public void setInterfaces(List<InterfaceBean> interfacesBean) {
		Interfaces = interfacesBean;
	}
	public InterfaceBean getInterface(QName interfaceQName) {
		InterfaceBean Interface = new InterfaceBean();
		return Interface;
	}

	public List<BindingBean> getBindings() {
		return bindings;
	}

	public void setBindingBean(List<BindingBean> bindingBean) {
		this.bindings = bindingBean;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}


	public String getLocalPart() {
		return localPart;
	}


	public void setLocalPart(String localPart) {
		this.localPart = localPart;
	}

	public Map<String, String> getNamespaces() {
		return namespaces;
	}

	public void setNamespaces(Map<String, String> namespaces) {
		this.namespaces = namespaces;
	}

	public void setType(TypeBean type) {
		this.type = type;
	}

	public void setBindings(List<BindingBean> bindings) {
		this.bindings = bindings;
	}

	public Map<QName, String> getOtherAttributes() {
		return otherAttributes;
	}


	public void setOtherAttributes(Map<QName, String> otherAttributes) {
		this.otherAttributes = otherAttributes;
	}


	public List<?> getOtherElements() {
		return otherElements;
	}


	public void setOtherElements(List<?> otherElements) {
		this.otherElements = otherElements;
	}


	public Map<String, String> getSchemaLocation() {
		return schemaLocation;
	}


	public void setSchemaLocation(Map<String, String> schemaLocation) {
		this.schemaLocation = schemaLocation;
	}


	public List<ImportBean> getImports() {
		return imports;
	}


	public void setImports(List<ImportBean> imports) {
		this.imports = imports;
	}


	public List<IncludeBean> getIncludes() {
		return includes;
	}


	public void setIncludes(List<IncludeBean> includes) {
		this.includes = includes;
	}

	@Override
	public String toString() {
		return name;
	}
}
