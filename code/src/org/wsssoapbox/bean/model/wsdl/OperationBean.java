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

	/**
	 * @uml.property  name="name"
	 */
	private String name;
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
	 * @uml.property  name="httpFaultSerialization"
	 */
	private String httpFaultSerialization;
	/**
	 * @uml.property  name="parameterOrdering"
	 */
	private List <String >parameterOrdering;
	// ONE_WAY ,REQUEST_RESPONSE ,SOAP_RESPONSE 
	/**
	 * @uml.property  name="signature"
	 */
	private String 	signature;
	/**
	 * @uml.property  name="faults"
	 */
	List <FaultBean> faults;
	/**
	 * @uml.property  name="otherAttributes"
	 */
	private Map <QName,String> otherAttributes;
	/**
	 * @uml.property  name="otherElements"
	 */
	private List<Object> otherElements;	
	/**
	 * @uml.property  name="output"
	 * @uml.associationEnd  
	 */
	private OutputBean output;
	/**
	 * @uml.property  name="input"
	 * @uml.associationEnd  
	 */
	private InputBean input;
	/**
	 * @uml.property  name="pattern"
	 */
	private Object pattern;


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
	 * @uml.property  name="httpFaultSerialization"
	 */
	public String getHttpFaultSerialization() {
		return httpFaultSerialization;
	}




	/**
	 * @param httpFaultSerialization
	 * @uml.property  name="httpFaultSerialization"
	 */
	public void setHttpFaultSerialization(String httpFaultSerialization) {
		this.httpFaultSerialization = httpFaultSerialization;
	}




	/**
	 * @return
	 * @uml.property  name="parameterOrdering"
	 */
	public List<String> getParameterOrdering() {
		return parameterOrdering;
	}




	/**
	 * @param parameterOrdering
	 * @uml.property  name="parameterOrdering"
	 */
	public void setParameterOrdering(List<String> parameterOrdering) {
		this.parameterOrdering = parameterOrdering;
	}




	/**
	 * @return
	 * @uml.property  name="signature"
	 */
	public String getSignature() {
		return signature;
	}




	/**
	 * @param signature
	 * @uml.property  name="signature"
	 */
	public void setSignature(String signature) {
		this.signature = signature;
	}




	/**
	 * @return
	 * @uml.property  name="faults"
	 */
	public List<FaultBean> getFaults() {
		return faults;
	}




	/**
	 * @param faults
	 * @uml.property  name="faults"
	 */
	public void setFaults(List<FaultBean> faults) {
		this.faults = faults;
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
	public List<Object> getOtherElements() {
		return otherElements;
	}




	/**
	 * @param otherElements
	 * @uml.property  name="otherElements"
	 */
	public void setOtherElements(List<Object> otherElements) {
		this.otherElements = otherElements;
	}




	/**
	 * @return
	 * @uml.property  name="output"
	 */
	public OutputBean getOutput() {
		return output;
	}




	/**
	 * @param output
	 * @uml.property  name="output"
	 */
	public void setOutput(OutputBean output) {
		this.output = output;
	}




	/**
	 * @return
	 * @uml.property  name="input"
	 */
	public InputBean getInput() {
		return input;
	}




	/**
	 * @param input
	 * @uml.property  name="input"
	 */
	public void setInput(InputBean input) {
		this.input = input;
	}




	/**
	 * @return
	 * @uml.property  name="pattern"
	 */
	public Object getPattern() {
		return pattern;
	}




	/**
	 * @param pattern
	 * @uml.property  name="pattern"
	 */
	public void setPattern(Object pattern) {
		this.pattern = pattern;
	}




	@Override
	public String toString() {
		return name;
	}
}
