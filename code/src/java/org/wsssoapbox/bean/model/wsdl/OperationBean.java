package org.wsssoapbox.bean.model.wsdl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

/**
 * @author  Peter
 */
public class OperationBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OperationBean() {
	}

	private String name;
	private QName qname;
	private String documentation;
	private String httpFaultSerialization;
	private List <String >parameterOrdering;
	// ONE_WAY ,REQUEST_RESPONSE ,SOAP_RESPONSE 
	private String 	signature;
	private List <FaultBean> faults;
	private Map <QName,String> otherAttributes;
	private List<Object> otherElements;	
	private OutputBean output;
	private InputBean input;
	private Object pattern;


	@Override
	public String toString() {
		return getName();
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
    * @return the parameterOrdering
    */
   public List <String > getParameterOrdering() {
      return parameterOrdering;
   }

   /**
    * @param parameterOrdering the parameterOrdering to set
    */
   public void setParameterOrdering(List <String > parameterOrdering) {
      this.parameterOrdering = parameterOrdering;
   }

   /**
    * @return the signature
    */
   public String getSignature() {
      return signature;
   }

   /**
    * @param signature the signature to set
    */
   public void setSignature(String signature) {
      this.signature = signature;
   }

   /**
    * @return the faults
    */
   public List <FaultBean> getFaults() {
      return faults;
   }

   /**
    * @param faults the faults to set
    */
   public void setFaults(List <FaultBean> faults) {
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
   public List<Object> getOtherElements() {
      return otherElements;
   }

   /**
    * @param otherElements the otherElements to set
    */
   public void setOtherElements(List<Object> otherElements) {
      this.otherElements = otherElements;
   }

   /**
    * @return the output
    */
   public OutputBean getOutput() {
      return output;
   }

   /**
    * @param output the output to set
    */
   public void setOutput(OutputBean output) {
      this.output = output;
   }

   /**
    * @return the input
    */
   public InputBean getInput() {
      return input;
   }

   /**
    * @param input the input to set
    */
   public void setInput(InputBean input) {
      this.input = input;
   }

   /**
    * @return the pattern
    */
   public Object getPattern() {
      return pattern;
   }

   /**
    * @param pattern the pattern to set
    */
   public void setPattern(Object pattern) {
      this.pattern = pattern;
   }
}
