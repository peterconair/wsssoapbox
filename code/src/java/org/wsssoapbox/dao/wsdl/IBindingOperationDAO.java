package org.wsssoapbox.dao.wsdl;

import java.util.List;

import javax.xml.namespace.QName;

import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.api.Binding;
import org.ow2.easywsdl.wsdl.api.Description;
import org.ow2.easywsdl.wsdl.api.WSDLException;
import org.wsssoapbox.bean.model.wsdl.BindingOperationBean;

public interface IBindingOperationDAO {
	
	public List<BindingOperationBean> getBindingOperations(
			Description desc, QName bindingQName) throws WSDLException, XmlException;
		

	public BindingOperationBean getOperationByBinding(Description desc,
			QName bindingQname,String bindingOperationName) throws WSDLException ,XmlException;
	
	public List <BindingOperationBean>  getBindingOperationsByBinding(Binding binding) throws XmlException;
}
