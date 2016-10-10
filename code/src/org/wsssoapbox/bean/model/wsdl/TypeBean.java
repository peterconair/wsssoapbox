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

	/**
	 * @uml.property  name="qname"
	 * @uml.associationEnd  
	 */
	private QName qname;
	/**
	 * @uml.property  name="documentation"
	 */
	private String documentation;
	/**
	 * @uml.property  name="importSchemas"
	 */
	private List <?> importSchemas;
	/**
	 * @uml.property  name="schemas"
	 */
	private List <?> schemas;
	/**
	 * @uml.property  name="otherAttributes"
	 */
	private Map <QName,String> OtherAttributes;
	/**
	 * @uml.property  name="otherElements"
	 */
	private List <?> otherElements;
	

	/**
	 * @return
	 * @uml.property  name="qname"
	 */
	public QName getQname() {
		return qname;
	}


	/**
	 * @param qname
	 * @uml.property  name="qname"
	 */
	public void setQname(QName qname) {
		this.qname = qname;
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
	 * @uml.property  name="importSchemas"
	 */
	public List<?> getImportSchemas() {
		return importSchemas;
	}


	/**
	 * @param importSchemas
	 * @uml.property  name="importSchemas"
	 */
	public void setImportSchemas(List<?> importSchemas) {
		this.importSchemas = importSchemas;
	}


	/**
	 * @return
	 * @uml.property  name="schemas"
	 */
	public List<?> getSchemas() {
		return schemas;
	}


	/**
	 * @param schemas
	 * @uml.property  name="schemas"
	 */
	public void setSchemas(List<?> schemas) {
		this.schemas = schemas;
	}


	/**
	 * @return
	 * @uml.property  name="otherAttributes"
	 */
	public Map<QName, String> getOtherAttributes() {
		return OtherAttributes;
	}


	/**
	 * @param otherAttributes
	 * @uml.property  name="otherAttributes"
	 */
	public void setOtherAttributes(Map<QName, String> otherAttributes) {
		OtherAttributes = otherAttributes;
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


	@Override
	public String toString() {
		return "TypeBean [qname=" + qname + ", documentation=" + documentation + ", importSchemas=" + importSchemas
				+ ", schemas=" + schemas + ", OtherAttributes=" + OtherAttributes + ", otherElements=" + otherElements
				+ "]";
	}

}
