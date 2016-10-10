package org.wsssoapbox.bean.model.wsdl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

/**
 * @author  Peter
 */
public class BindingOperationBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private QName qname;
	private String namespaceURI;
	private String transportProtocol;
	private String documentation;
	private String httpContentEncodingDefault;
	private String httpFaultSerialization;
	private String httpInputSerialization;
	private String httpLocation;
	private String httpMethod;
	private String httpOutputSerialization;
	private String httpQueryParameterSeparator;
	private Enum MEP;
	// Doc and RPC
	private String style;
	private String soapAction;
	private List <BindingFaultBean> faults;
	private Map <QName,String> otherAttributes;
	private List<?> otherElements;	
	private BindingOutputBean output;
	private BindingInputBean input;
	private OperationBean operation;
	
	
	@Override
	public String toString() {
		return  getName();
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
    * @return the qname
    */
   public QName getQname() {
      return qname;
   }

   /**
    * @param qname the qname to set
    */
   public void setQname(QName qname) {
      this.qname = qname;
   }

   /**
    * @return the namespaceURI
    */
   public String getNamespaceURI() {
      return namespaceURI;
   }

   /**
    * @param namespaceURI the namespaceURI to set
    */
   public void setNamespaceURI(String namespaceURI) {
      this.namespaceURI = namespaceURI;
   }

   /**
    * @return the transportProtocol
    */
   public String getTransportProtocol() {
      return transportProtocol;
   }

   /**
    * @param transportProtocol the transportProtocol to set
    */
   public void setTransportProtocol(String transportProtocol) {
      this.transportProtocol = transportProtocol;
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
    * @return the httpContentEncodingDefault
    */
   public String getHttpContentEncodingDefault() {
      return httpContentEncodingDefault;
   }

   /**
    * @param httpContentEncodingDefault the httpContentEncodingDefault to set
    */
   public void setHttpContentEncodingDefault(String httpContentEncodingDefault) {
      this.httpContentEncodingDefault = httpContentEncodingDefault;
   }

   /**
    * @return the httpFaultSerialization
    */
   public String getHttpFaultSerialization() {
      return httpFaultSerialization;
   }

   /**
    * @param httpFaultSerialization the httpFaultSerialization to set
    */
   public void setHttpFaultSerialization(String httpFaultSerialization) {
      this.httpFaultSerialization = httpFaultSerialization;
   }

   /**
    * @return the httpInputSerialization
    */
   public String getHttpInputSerialization() {
      return httpInputSerialization;
   }

   /**
    * @param httpInputSerialization the httpInputSerialization to set
    */
   public void setHttpInputSerialization(String httpInputSerialization) {
      this.httpInputSerialization = httpInputSerialization;
   }

   /**
    * @return the httpLocation
    */
   public String getHttpLocation() {
      return httpLocation;
   }

   /**
    * @param httpLocation the httpLocation to set
    */
   public void setHttpLocation(String httpLocation) {
      this.httpLocation = httpLocation;
   }

   /**
    * @return the httpMethod
    */
   public String getHttpMethod() {
      return httpMethod;
   }

   /**
    * @param httpMethod the httpMethod to set
    */
   public void setHttpMethod(String httpMethod) {
      this.httpMethod = httpMethod;
   }

   /**
    * @return the httpOutputSerialization
    */
   public String getHttpOutputSerialization() {
      return httpOutputSerialization;
   }

   /**
    * @param httpOutputSerialization the httpOutputSerialization to set
    */
   public void setHttpOutputSerialization(String httpOutputSerialization) {
      this.httpOutputSerialization = httpOutputSerialization;
   }

   /**
    * @return the httpQueryParameterSeparator
    */
   public String getHttpQueryParameterSeparator() {
      return httpQueryParameterSeparator;
   }

   /**
    * @param httpQueryParameterSeparator the httpQueryParameterSeparator to set
    */
   public void setHttpQueryParameterSeparator(String httpQueryParameterSeparator) {
      this.httpQueryParameterSeparator = httpQueryParameterSeparator;
   }

   /**
    * @return the MEP
    */
   public Enum getMEP() {
      return MEP;
   }

   /**
    * @param MEP the MEP to set
    */
   public void setMEP(Enum MEP) {
      this.MEP = MEP;
   }

   /**
    * @return the style
    */
   public String getStyle() {
      return style;
   }

   /**
    * @param style the style to set
    */
   public void setStyle(String style) {
      this.style = style;
   }

   /**
    * @return the soapAction
    */
   public String getSoapAction() {
      return soapAction;
   }

   /**
    * @param soapAction the soapAction to set
    */
   public void setSoapAction(String soapAction) {
      this.soapAction = soapAction;
   }

   /**
    * @return the faults
    */
   public List <BindingFaultBean> getFaults() {
      return faults;
   }

   /**
    * @param faults the faults to set
    */
   public void setFaults(List <BindingFaultBean> faults) {
      this.faults = faults;
   }

   /**
    * @return the otherAttributes
    */
   public Map <QName,String> getOtherAttributes() {
      return otherAttributes;
   }

   /**
    * @param otherAttributes the otherAttributes to set
    */
   public void setOtherAttributes(Map <QName,String> otherAttributes) {
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
    * @return the output
    */
   public BindingOutputBean getOutput() {
      return output;
   }

   /**
    * @param output the output to set
    */
   public void setOutput(BindingOutputBean output) {
      this.output = output;
   }

   /**
    * @return the input
    */
   public BindingInputBean getInput() {
      return input;
   }

   /**
    * @param input the input to set
    */
   public void setInput(BindingInputBean input) {
      this.input = input;
   }

   /**
    * @return the operation
    */
   public OperationBean getOperation() {
      return operation;
   }

   /**
    * @param operation the operation to set
    */
   public void setOperation(OperationBean operation) {
      this.operation = operation;
   }
	


}
