package org.wsssoapbox.dao.wsdl;

import java.util.List;

import javax.xml.namespace.QName;

import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.api.Description;
import org.ow2.easywsdl.wsdl.api.Endpoint;
import org.ow2.easywsdl.wsdl.api.WSDLException;
import org.wsssoapbox.bean.model.wsdl.BindingBean;

public interface IBindingDAO {
	public List<BindingBean> getBindings(Description desc) throws WSDLException ,XmlException;

	public BindingBean getBindingByName(Description desc, QName bindingQName)
	throws WSDLException ,XmlException;

	
	BindingBean getBindingByEndpointName(Description desc, QName serviceQName ,String endpointName)

			throws WSDLException ,XmlException;

	public BindingBean getBindingByIndex(Description desc, int index)
			throws WSDLException ,XmlException;
	
	public BindingBean getBindingByEndpoint(Endpoint endpoint)throws XmlException;
}
