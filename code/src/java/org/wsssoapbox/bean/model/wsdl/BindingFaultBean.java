package org.wsssoapbox.bean.model.wsdl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

/**
 * @author  Peter
 */
public class BindingFaultBean implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private QName ref;
	private String name;
	private String documentation;
	private Object HTTPBinding4Wsdl11;
	private Object HTTPBinding4Wsdl20;
	private Object HttpContentEncoding;
	private Object MIMEBinding4Wsdl11;
	private Map<QName, String> otherAttributes;
	private List<?> otherElements;
	private Object SOAP11Binding4Wsdl11;
	private Object SOAP12Binding4Wsdl11;
	private Object SOAP12Binding4Wsdl20;

	
	@Override
	public String toString() {
		return "BindingFaultBean [ref=" + getRef() + ", name=" + getName() + ", documentation=" + getDocumentation()
				+ ", HTTPBinding4Wsdl11=" + getHTTPBinding4Wsdl11() + ", HTTPBinding4Wsdl20=" + getHTTPBinding4Wsdl20()
				+ ", HttpContentEncoding=" + getHttpContentEncoding() + ", MIMEBinding4Wsdl11=" + getMIMEBinding4Wsdl11()
				+ ", otherAttributes=" + getOtherAttributes() + ", otherElements=" + getOtherElements()
				+ ", SOAP11Binding4Wsdl11=" + getSOAP11Binding4Wsdl11() + ", SOAP12Binding4Wsdl11=" + getSOAP12Binding4Wsdl11()
				+ ", SOAP12Binding4Wsdl20=" + getSOAP12Binding4Wsdl20() + "]";
	}

   /**
    * @return the ref
    */
   public QName getRef() {
      return ref;
   }

   /**
    * @param ref the ref to set
    */
   public void setRef(QName ref) {
      this.ref = ref;
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
    * @return the HTTPBinding4Wsdl11
    */
   public Object getHTTPBinding4Wsdl11() {
      return HTTPBinding4Wsdl11;
   }

   /**
    * @param HTTPBinding4Wsdl11 the HTTPBinding4Wsdl11 to set
    */
   public void setHTTPBinding4Wsdl11(Object HTTPBinding4Wsdl11) {
      this.HTTPBinding4Wsdl11 = HTTPBinding4Wsdl11;
   }

   /**
    * @return the HTTPBinding4Wsdl20
    */
   public Object getHTTPBinding4Wsdl20() {
      return HTTPBinding4Wsdl20;
   }

   /**
    * @param HTTPBinding4Wsdl20 the HTTPBinding4Wsdl20 to set
    */
   public void setHTTPBinding4Wsdl20(Object HTTPBinding4Wsdl20) {
      this.HTTPBinding4Wsdl20 = HTTPBinding4Wsdl20;
   }

   /**
    * @return the HttpContentEncoding
    */
   public Object getHttpContentEncoding() {
      return HttpContentEncoding;
   }

   /**
    * @param HttpContentEncoding the HttpContentEncoding to set
    */
   public void setHttpContentEncoding(Object HttpContentEncoding) {
      this.HttpContentEncoding = HttpContentEncoding;
   }

   /**
    * @return the MIMEBinding4Wsdl11
    */
   public Object getMIMEBinding4Wsdl11() {
      return MIMEBinding4Wsdl11;
   }

   /**
    * @param MIMEBinding4Wsdl11 the MIMEBinding4Wsdl11 to set
    */
   public void setMIMEBinding4Wsdl11(Object MIMEBinding4Wsdl11) {
      this.MIMEBinding4Wsdl11 = MIMEBinding4Wsdl11;
   }

   /**
    * @return the otherAttributes
    */
   public Map<QName, String> getOtherAttributes() {
      return otherAttributes;
   }

   /**
    * @param otherAttributes the otherAttributes to set
    */
   public void setOtherAttributes(Map<QName, String> otherAttributes) {
      this.otherAttributes = otherAttributes;
   }

   /**
    * @return the otherElements
    */
   public List<?> getOtherElements() {
      return otherElements;
   }

   /**
    * @param otherElements the otherElements to set
    */
   public void setOtherElements(List<?> otherElements) {
      this.otherElements = otherElements;
   }

   /**
    * @return the SOAP11Binding4Wsdl11
    */
   public Object getSOAP11Binding4Wsdl11() {
      return SOAP11Binding4Wsdl11;
   }

   /**
    * @param SOAP11Binding4Wsdl11 the SOAP11Binding4Wsdl11 to set
    */
   public void setSOAP11Binding4Wsdl11(Object SOAP11Binding4Wsdl11) {
      this.SOAP11Binding4Wsdl11 = SOAP11Binding4Wsdl11;
   }

   /**
    * @return the SOAP12Binding4Wsdl11
    */
   public Object getSOAP12Binding4Wsdl11() {
      return SOAP12Binding4Wsdl11;
   }

   /**
    * @param SOAP12Binding4Wsdl11 the SOAP12Binding4Wsdl11 to set
    */
   public void setSOAP12Binding4Wsdl11(Object SOAP12Binding4Wsdl11) {
      this.SOAP12Binding4Wsdl11 = SOAP12Binding4Wsdl11;
   }

   /**
    * @return the SOAP12Binding4Wsdl20
    */
   public Object getSOAP12Binding4Wsdl20() {
      return SOAP12Binding4Wsdl20;
   }

   /**
    * @param SOAP12Binding4Wsdl20 the SOAP12Binding4Wsdl20 to set
    */
   public void setSOAP12Binding4Wsdl20(Object SOAP12Binding4Wsdl20) {
      this.SOAP12Binding4Wsdl20 = SOAP12Binding4Wsdl20;
   }

}
