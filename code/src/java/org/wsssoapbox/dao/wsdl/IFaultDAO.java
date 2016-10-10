package org.wsssoapbox.dao.wsdl;

import java.util.List;

import javax.xml.namespace.QName;

import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.api.Description;
import org.ow2.easywsdl.wsdl.api.Operation;
import org.wsssoapbox.bean.model.wsdl.FaultBean;

public interface IFaultDAO {
	public List<FaultBean> getFaultsByInterfaceOperationFaultName(
			Description desc, QName interfaceQName, QName operationQName
			) throws XmlException ;
	public List<FaultBean> getFaultsByInterfaceIndexOperationFaultName(
			Description desc, int interfaceIndex, QName operationQName
			) throws XmlException;
	public FaultBean getFaultByInterfaceOperationElementQName(Description desc,
			QName interfaceQName, QName operationQName, QName elementQName) throws XmlException ;
	
	public FaultBean getFaultByInterfaceOperationFaultName(Description desc,
			QName interfaceQName, QName operationQName, String faultName)
			throws XmlException ;
	
	public List<FaultBean> getFaultsByBinding(Description desc, QName bindingQName,
			String operationName) throws XmlException ;
	
	public List<FaultBean> getFaultsByBindingOperation (Operation operation) throws XmlException ;
	
	public List<FaultBean> getFaultsByOperation (Operation operation) throws XmlException ;
}
