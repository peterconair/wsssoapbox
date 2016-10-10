package org.wsssoapbox.dao.wsdl;

import javax.xml.namespace.QName;

import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.api.BindingOperation;
import org.ow2.easywsdl.wsdl.api.Description;
import org.wsssoapbox.bean.model.wsdl.BindingInputBean;

public interface IBindingInputDAO {
	public BindingInputBean getInputBindingByBinding(Description desc,
			QName bindingQName, String operationName) throws XmlException ;
	

	BindingInputBean getInputByBindingOperation(BindingOperation bindingOperation) throws XmlException;
}
