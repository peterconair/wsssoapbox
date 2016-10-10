package org.wsssoapbox.bean.model.wsdl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

/**
 * @author  Peter
 */
public class SchemaBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * @uml.property  name="lang"
	 */
	private String lang;
	/**
	 * @uml.property  name="allNamespaces"
	 */
	private Object allNamespaces;
	/**
	 * @uml.property  name="attributeFormDefault"
	 */
	private Object attributeFormDefault;
	/**
	 * @uml.property  name="attributes"
	 */
	private List<?>attributes;
	/**
	 * @uml.property  name="blockDefault"
	 */
	private List<String> blockDefault;
	/**
	 * @uml.property  name="documentation"
	 */
	private String documentation;
	/**
	 * @uml.property  name="documentURI"
	 */
	private Object documentURI;
	/**
	 * @uml.property  name="elementFormDefault"
	 */
	private Object elementFormDefault;
	/**
	 * @uml.property  name="elements"
	 */
	private List<?> elements;
	/**
	 * @uml.property  name="finalDefault"
	 */
	List <String> finalDefault;
	/**
	 * @uml.property  name="imports"
	 */
	List <?> imports;
	/**
	 * @uml.property  name="includes"
	 */
	List <?> includes;
	/**
	 * @uml.property  name="otherAttributes"
	 */
	private Map <QName,String> otherAttributes;
	/**
	 * @uml.property  name="targetNamespace"
	 */
	private String targetNamespace;
	/**
	 * @uml.property  name="types"
	 */
	private List <?> types;
	/**
	 * @uml.property  name="version"
	 */
	private String  version;
	
	
	/**
	 * @return
	 * @uml.property  name="lang"
	 */
	public String getLang() {
		return lang;
	}
	/**
	 * @param lang
	 * @uml.property  name="lang"
	 */
	public void setLang(String lang) {
		this.lang = lang;
	}
	/**
	 * @return
	 * @uml.property  name="allNamespaces"
	 */
	public Object getAllNamespaces() {
		return allNamespaces;
	}
	/**
	 * @param allNamespaces
	 * @uml.property  name="allNamespaces"
	 */
	public void setAllNamespaces(Object allNamespaces) {
		this.allNamespaces = allNamespaces;
	}
	/**
	 * @return
	 * @uml.property  name="attributeFormDefault"
	 */
	public Object getAttributeFormDefault() {
		return attributeFormDefault;
	}
	/**
	 * @param attributeFormDefault
	 * @uml.property  name="attributeFormDefault"
	 */
	public void setAttributeFormDefault(Object attributeFormDefault) {
		this.attributeFormDefault = attributeFormDefault;
	}
	/**
	 * @return
	 * @uml.property  name="attributes"
	 */
	public List<?> getAttributes() {
		return attributes;
	}
	/**
	 * @param attributes
	 * @uml.property  name="attributes"
	 */
	public void setAttributes(List<?> attributes) {
		this.attributes = attributes;
	}
	/**
	 * @return
	 * @uml.property  name="blockDefault"
	 */
	public List<String> getBlockDefault() {
		return blockDefault;
	}
	/**
	 * @param blockDefault
	 * @uml.property  name="blockDefault"
	 */
	public void setBlockDefault(List<String> blockDefault) {
		this.blockDefault = blockDefault;
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
	 * @uml.property  name="documentURI"
	 */
	public Object getDocumentURI() {
		return documentURI;
	}
	/**
	 * @param documentURI
	 * @uml.property  name="documentURI"
	 */
	public void setDocumentURI(Object documentURI) {
		this.documentURI = documentURI;
	}
	/**
	 * @return
	 * @uml.property  name="elementFormDefault"
	 */
	public Object getElementFormDefault() {
		return elementFormDefault;
	}
	/**
	 * @param elementFormDefault
	 * @uml.property  name="elementFormDefault"
	 */
	public void setElementFormDefault(Object elementFormDefault) {
		this.elementFormDefault = elementFormDefault;
	}
	/**
	 * @return
	 * @uml.property  name="elements"
	 */
	public List<?> getElements() {
		return elements;
	}
	/**
	 * @param elements
	 * @uml.property  name="elements"
	 */
	public void setElements(List<?> elements) {
		this.elements = elements;
	}
	/**
	 * @return
	 * @uml.property  name="finalDefault"
	 */
	public List<String> getFinalDefault() {
		return finalDefault;
	}
	/**
	 * @param finalDefault
	 * @uml.property  name="finalDefault"
	 */
	public void setFinalDefault(List<String> finalDefault) {
		this.finalDefault = finalDefault;
	}
	/**
	 * @return
	 * @uml.property  name="imports"
	 */
	public List<?> getImports() {
		return imports;
	}
	/**
	 * @param imports
	 * @uml.property  name="imports"
	 */
	public void setImports(List<?> imports) {
		this.imports = imports;
	}
	/**
	 * @return
	 * @uml.property  name="includes"
	 */
	public List<?> getIncludes() {
		return includes;
	}
	/**
	 * @param includes
	 * @uml.property  name="includes"
	 */
	public void setIncludes(List<?> includes) {
		this.includes = includes;
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
	 * @uml.property  name="types"
	 */
	public List<?> getTypes() {
		return types;
	}
	/**
	 * @param types
	 * @uml.property  name="types"
	 */
	public void setTypes(List<?> types) {
		this.types = types;
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
	@Override
	public String toString() {
		return "SchemaBean [lang=" + lang + ", allNamespaces=" + allNamespaces + ", attributeFormDefault="
				+ attributeFormDefault + ", attributes=" + attributes + ", blockDefault=" + blockDefault
				+ ", documentation=" + documentation + ", documentURI=" + documentURI + ", elementFormDefault="
				+ elementFormDefault + ", elements=" + elements + ", finalDefault=" + finalDefault + ", imports="
				+ imports + ", includes=" + includes + ", otherAttributes=" + otherAttributes + ", targetNamespace="
				+ targetNamespace + ", types=" + types + ", version=" + version + "]";
	}
}
