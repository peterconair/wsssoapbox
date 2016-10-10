package org.wsssoapbox.bean.model.wsdl;



import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.ow2.easywsdl.schema.api.Schema;

/**
 * @author  Peter
 */
public class TypeBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private QName qname;

	private String documentation;

	private List <?> importSchemas;

	private List <SchemaBean> schemas;

	private Map <QName,String> OtherAttributes;

	private List <?> otherElements;

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

	public List<?> getImportSchemas() {
		return importSchemas;
	}

	public void setImportSchemas(List<?> importSchemas) {
		this.importSchemas = importSchemas;
	}

	public List<SchemaBean> getSchemas() {
		return schemas;
	}


	public void setSchemas(List<SchemaBean> schemas) {
		this.schemas = schemas;
	}

	public Map<QName, String> getOtherAttributes() {
		return OtherAttributes;
	}

	public void setOtherAttributes(Map<QName, String> otherAttributes) {
		OtherAttributes = otherAttributes;
	}


	public List<?> getOtherElements() {
		return otherElements;
	}

	public void setOtherElements(List<?> otherElements) {
		this.otherElements = otherElements;
	}


	@Override
	public String toString() {
		return "TypeBean [qname=" + qname + ", documentation=" + documentation + ", importSchemas=" + importSchemas
				+ ", schemas=" + schemas + ", OtherAttributes=" + OtherAttributes + ", otherElements=" + otherElements
				+ "]";
	}

}
