package org.wsssoapbox.bean.model.wsdl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

/**
 * @author  Peter
 */
public class SchemaBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private String lang;
	private Object allNamespaces;
	private Object attributeFormDefault;
	private List<?>attributes;
	private List<String> blockDefault;
	private String documentation;
	private Object documentURI;
	private Object elementFormDefault;
	private List<?> elements;
	List <String> finalDefault;
	List <?> imports;
	List <?> includes;
	private Map <QName,String> otherAttributes;
	private String targetNamespace;
	private List <?> types;
	private String  version;
	
	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public Object getAllNamespaces() {
		return allNamespaces;
	}

	public void setAllNamespaces(Object allNamespaces) {
		this.allNamespaces = allNamespaces;
	}

	public Object getAttributeFormDefault() {
		return attributeFormDefault;
	}

	public void setAttributeFormDefault(Object attributeFormDefault) {
		this.attributeFormDefault = attributeFormDefault;
	}
	
	public List<?> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<?> attributes) {
		this.attributes = attributes;
	}

	public List<String> getBlockDefault() {
		return blockDefault;
	}

	public void setBlockDefault(List<String> blockDefault) {
		this.blockDefault = blockDefault;
	}

	public String getDocumentation() {
		return documentation;
	}

	public void setDocumentation(String documentation) {
		this.documentation = documentation;
	}

	public Object getDocumentURI() {
		return documentURI;
	}

	public void setDocumentURI(Object documentURI) {
		this.documentURI = documentURI;
	}

	public Object getElementFormDefault() {
		return elementFormDefault;
	}

	public void setElementFormDefault(Object elementFormDefault) {
		this.elementFormDefault = elementFormDefault;
	}

	public List<?> getElements() {
		return elements;
	}

	public void setElements(List<?> elements) {
		this.elements = elements;
	}

	public List<String> getFinalDefault() {
		return finalDefault;
	}

	public void setFinalDefault(List<String> finalDefault) {
		this.finalDefault = finalDefault;
	}

	public List<?> getImports() {
		return imports;
	}

	public void setImports(List<?> imports) {
		this.imports = imports;
	}

	public List<?> getIncludes() {
		return includes;
	}

	public void setIncludes(List<?> includes) {
		this.includes = includes;
	}

	public Map<QName, String> getOtherAttributes() {
		return otherAttributes;
	}

	public void setOtherAttributes(Map<QName, String> otherAttributes) {
		this.otherAttributes = otherAttributes;
	}

	public String getTargetNamespace() {
		return targetNamespace;
	}

	public void setTargetNamespace(String targetNamespace) {
		this.targetNamespace = targetNamespace;
	}

	public List<?> getTypes() {
		return types;
	}

	public void setTypes(List<?> types) {
		this.types = types;
     }
	public String getVersion() {
		return version;
	}

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
