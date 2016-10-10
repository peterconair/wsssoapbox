package org.wsssoapbox.dao.wsdl;

import java.util.List;

import javax.xml.namespace.QName;

import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.api.Description;
import org.ow2.easywsdl.wsdl.api.Endpoint;
import org.ow2.easywsdl.wsdl.api.Service;
import org.ow2.easywsdl.wsdl.api.WSDLException;
import org.wsssoapbox.bean.model.wsdl.EndpointBean;

public interface IEndpointDAO {
	public List<EndpointBean> getEndpointsByServiceName(Description desc, QName qname) throws WSDLException,
			XmlException;

	public EndpointBean getEndpointByName(Description desc, QName serviceQName, String endpontName)
			throws WSDLException, XmlException;

	public List<EndpointBean> getEndpointsByService(Service service) throws XmlException;
}
