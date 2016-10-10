package org.wsssoapbox.dao.wsdl;

import javax.xml.namespace.QName;

import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.api.Description;
import org.ow2.easywsdl.wsdl.api.Operation;
import org.wsssoapbox.bean.model.wsdl.InputBean;
import org.wsssoapbox.bean.model.wsdl.OutputBean;

public interface IInputDAO {
	public InputBean getInputByInterface(Description desc,
			QName interfaceQName, QName operationQName) throws XmlException;
	public InputBean getInputByInterfaceIndex(Description desc,
			int interfaceIndex, QName operationQName) throws XmlException ;
	public InputBean getInputByBinding (Description desc,
			QName bindingQName, String operationName) throws XmlException ;
	
	public InputBean getInputByBindingOperation(Operation operation) throws XmlException;
	public InputBean getInputByOperation (Operation operation) throws XmlException;
}
