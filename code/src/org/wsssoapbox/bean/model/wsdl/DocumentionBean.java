package org.wsssoapbox.bean.model.wsdl;

import java.io.Serializable;
import java.util.Map;

import javax.xml.namespace.QName;

/**
 * @author  Peter
 */
public class DocumentionBean implements Serializable {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
/**
 * @uml.property  name="content"
 */
private String content;
/**
 * @uml.property  name="documentation"
 * @uml.associationEnd  
 */
private DocumentionBean documentation;
/**
 * @uml.property  name="otherAttributes"
 */
private Map <QName,String> otherAttributes;
/**
 * @return
 * @uml.property  name="content"
 */
public String getContent() {
	return content;
}
/**
 * @param content
 * @uml.property  name="content"
 */
public void setContent(String content) {
	this.content = content;
}
/**
 * @return
 * @uml.property  name="documentation"
 */
public DocumentionBean getDocumentation() {
	return documentation;
}
/**
 * @param documentation
 * @uml.property  name="documentation"
 */
public void setDocumentation(DocumentionBean documentation) {
	this.documentation = documentation;
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
@Override
public String toString() {
	return "DocumentionBean [content=" + content + ", documentation=" + documentation + ", otherAttributes="
			+ otherAttributes + "]";
}
}
