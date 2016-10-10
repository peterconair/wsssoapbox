package org.wsssoapbox.dao.wsdl;

import javax.xml.namespace.QName;

import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.api.Description;
import org.ow2.easywsdl.wsdl.api.Operation;
import org.wsssoapbox.bean.model.wsdl.OutputBean;

public interface IOutputDAO {
	public OutputBean getOutputByInterface(Description desc,
			QName interfaceQName, QName operationQName) throws XmlException;
	public OutputBean getOutputByInterfaceIndex(Description desc,
			int interfaceIndex, QName operationQName) throws XmlException ;
	public OutputBean getOutputByBinding (Description desc,
			QName bindingQName, String operationName) throws XmlException ;
	
	public OutputBean getOutputByBindingOperation(Operation operation)throws XmlException ;
	
	public OutputBean getOutputByOperation (Operation operation)throws XmlException ;
}
