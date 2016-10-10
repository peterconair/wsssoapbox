package org.wsssoapbox.dao.wsdl;

import javax.xml.namespace.QName;

import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.api.BindingOperation;
import org.ow2.easywsdl.wsdl.api.Description;
import org.wsssoapbox.bean.model.wsdl.BindingOutputBean;

public interface IBindingOutputDAO {
	public BindingOutputBean getOutputByBindingName(Description desc,
			QName bindingQName, String operationName) throws XmlException ;
	
	public BindingOutputBean getOutputByBindingOperation (BindingOperation bindingOperation) throws XmlException;

}
