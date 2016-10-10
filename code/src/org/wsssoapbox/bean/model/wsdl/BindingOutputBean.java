package org.wsssoapbox.bean.model.wsdl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

/**
 * @author  Peter
 */
public class BindingOutputBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * @uml.property  name="name"
	 */
	private String name;
	/**
	 * @uml.property  name="documentation"
	 */
	private String documentation;
	/**
	 * @uml.property  name="hTTPBinding4Wsdl11"
	 */
	private Object HTTPBinding4Wsdl11;
	/**
	 * @uml.property  name="hTTPBinding4Wsdl20"
	 */
	private Object HTTPBinding4Wsdl20;
	/**
	 * @uml.property  name="httpContentEncoding"
	 */
	private Object httpContentEncoding;
	/**
	 * @uml.property  name="mIMEBinding4Wsdl11"
	 */
	private Object MIMEBinding4Wsdl11;


	/**
	 * @uml.property  name="otherAttributes"
	 */
	Map<QName, String> otherAttributes;
	/**
	 * @uml.property  name="otherElements"
	 */
	List<?> otherElements;

	/**
	 * @uml.property  name="sOAP11Binding4Wsdl11"
	 */
	private Object SOAP11Binding4Wsdl11;
	/**
	 * @uml.property  name="sOAP12Binding4Wsdl11"
	 */
	private Object SOAP12Binding4Wsdl11;
	/**
	 * @uml.property  name="sOAP12Binding4Wsdl20"
	 */
	private Object SOAP12Binding4Wsdl20;
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
	 * @uml.property  name="hTTPBinding4Wsdl11"
	 */
	public Object getHTTPBinding4Wsdl11() {
		return HTTPBinding4Wsdl11;
	}
	/**
	 * @param hTTPBinding4Wsdl11
	 * @uml.property  name="hTTPBinding4Wsdl11"
	 */
	public void setHTTPBinding4Wsdl11(Object hTTPBinding4Wsdl11) {
		HTTPBinding4Wsdl11 = hTTPBinding4Wsdl11;
	}
	/**
	 * @return
	 * @uml.property  name="hTTPBinding4Wsdl20"
	 */
	public Object getHTTPBinding4Wsdl20() {
		return HTTPBinding4Wsdl20;
	}
	/**
	 * @param hTTPBinding4Wsdl20
	 * @uml.property  name="hTTPBinding4Wsdl20"
	 */
	public void setHTTPBinding4Wsdl20(Object hTTPBinding4Wsdl20) {
		HTTPBinding4Wsdl20 = hTTPBinding4Wsdl20;
	}

	/**
	 * @return
	 * @uml.property  name="mIMEBinding4Wsdl11"
	 */
	public Object getMIMEBinding4Wsdl11() {
		return MIMEBinding4Wsdl11;
	}
	/**
	 * @param mIMEBinding4Wsdl11
	 * @uml.property  name="mIMEBinding4Wsdl11"
	 */
	public void setMIMEBinding4Wsdl11(Object mIMEBinding4Wsdl11) {
		MIMEBinding4Wsdl11 = mIMEBinding4Wsdl11;
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
	 * @uml.property  name="sOAP11Binding4Wsdl11"
	 */
	public Object getSOAP11Binding4Wsdl11() {
		return SOAP11Binding4Wsdl11;
	}
	/**
	 * @param sOAP11Binding4Wsdl11
	 * @uml.property  name="sOAP11Binding4Wsdl11"
	 */
	public void setSOAP11Binding4Wsdl11(Object sOAP11Binding4Wsdl11) {
		SOAP11Binding4Wsdl11 = sOAP11Binding4Wsdl11;
	}
	/**
	 * @return
	 * @uml.property  name="sOAP12Binding4Wsdl11"
	 */
	public Object getSOAP12Binding4Wsdl11() {
		return SOAP12Binding4Wsdl11;
	}
	/**
	 * @param sOAP12Binding4Wsdl11
	 * @uml.property  name="sOAP12Binding4Wsdl11"
	 */
	public void setSOAP12Binding4Wsdl11(Object sOAP12Binding4Wsdl11) {
		SOAP12Binding4Wsdl11 = sOAP12Binding4Wsdl11;
	}
	/**
	 * @return
	 * @uml.property  name="sOAP12Binding4Wsdl20"
	 */
	public Object getSOAP12Binding4Wsdl20() {
		return SOAP12Binding4Wsdl20;
	}
	/**
	 * @param sOAP12Binding4Wsdl20
	 * @uml.property  name="sOAP12Binding4Wsdl20"
	 */
	public void setSOAP12Binding4Wsdl20(Object sOAP12Binding4Wsdl20) {
		SOAP12Binding4Wsdl20 = sOAP12Binding4Wsdl20;
	}
	/**
	 * @return
	 * @uml.property  name="httpContentEncoding"
	 */
	public Object getHttpContentEncoding() {
		return httpContentEncoding;
	}
	/**
	 * @param httpContentEncoding
	 * @uml.property  name="httpContentEncoding"
	 */
	public void setHttpContentEncoding(Object httpContentEncoding) {
		this.httpContentEncoding = httpContentEncoding;
	}
	@Override
	public String toString() {
		return "BindingOutputBean [name=" + name + ", documentation=" + documentation + ", HTTPBinding4Wsdl11="
				+ HTTPBinding4Wsdl11 + ", HTTPBinding4Wsdl20=" + HTTPBinding4Wsdl20 + ", HTTPContentEncoding="
				+ httpContentEncoding + ", MIMEBinding4Wsdl11=" + MIMEBinding4Wsdl11 + ", otherAttributes="
				+ otherAttributes + ", otherElements=" + otherElements + ", SOAP11Binding4Wsdl11="
				+ SOAP11Binding4Wsdl11 + ", SOAP12Binding4Wsdl11=" + SOAP12Binding4Wsdl11 + ", SOAP12Binding4Wsdl20="
				+ SOAP12Binding4Wsdl20 + "]";
	}

	
}
