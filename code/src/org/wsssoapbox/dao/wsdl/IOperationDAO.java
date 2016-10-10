package org.wsssoapbox.dao.wsdl;

import java.util.List;

import javax.xml.namespace.QName;

import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.api.BindingOperation;
import org.ow2.easywsdl.wsdl.api.Description;
import org.ow2.easywsdl.wsdl.api.InterfaceType;
import org.ow2.easywsdl.wsdl.api.WSDLException;
import org.wsssoapbox.bean.model.wsdl.OperationBean;

public interface IOperationDAO {

	public List<OperationBean> getOperationsInterfaceByIndex(Description desc, int interfaceIndex)
			throws WSDLException, XmlException;

	public List<OperationBean> getOperationsInterfaceByName(Description desc, QName interfaceQName)
			throws WSDLException, XmlException;

	public OperationBean getOperationInterfaceByName(Description desc, QName interfaceQName, QName OperationQName)
			throws WSDLException, XmlException;

	public OperationBean getOperationInterfaceByNameIndex(Description desc, QName interfaceQName, int index)
			throws WSDLException, XmlException;

	public OperationBean getOperationByBinding(Description desc, QName bindingQname, String operationQName)
			throws WSDLException, XmlException;

	public OperationBean getOperationByBindingOperation(BindingOperation bindingOperation) throws XmlException;

	public List<OperationBean> getOperationsByInterface (InterfaceType interfaceType) throws XmlException;
}
