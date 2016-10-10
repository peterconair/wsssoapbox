package org.wsssoapbox.dao.wsdl;

import java.util.List;

import javax.xml.namespace.QName;

import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.api.BindingOperation;
import org.ow2.easywsdl.wsdl.api.Description;
import org.wsssoapbox.bean.model.wsdl.BindingFaultBean;

public interface IBindingFaultDAO {
	public List<BindingFaultBean> getFaultsByBindingOperationFaultName(
			Description desc, QName bindingQName, String bindingOperationName)
			throws XmlException;

	public BindingFaultBean getFaultByBindingOperationFaultName(
			Description desc, QName bindingQName, String bindingOperationName,
			String faultName) throws XmlException;
	
	public List<BindingFaultBean>  getFaultsByBindingOperation (BindingOperation bindingOperation) throws XmlException;
}
