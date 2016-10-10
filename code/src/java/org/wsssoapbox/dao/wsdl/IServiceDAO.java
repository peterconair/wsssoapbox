package org.wsssoapbox.dao.wsdl;

import java.util.List;

import javax.xml.namespace.QName;

import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.api.Description;
import org.ow2.easywsdl.wsdl.api.Endpoint;
import org.ow2.easywsdl.wsdl.api.Service;
import org.ow2.easywsdl.wsdl.api.WSDLException;
import org.wsssoapbox.bean.model.wsdl.ServicesBean;

public interface IServiceDAO {
	public List<ServicesBean> getServices(Description desc) throws WSDLException ,XmlException ;

	public ServicesBean getServiceByName(Description desc, QName qname) throws WSDLException ,XmlException;

	public ServicesBean getServiceByIndex(Description desc, int index) throws WSDLException ,XmlException ;

	public ServicesBean getServiceByEndpoint (Endpoint endpoint) throws XmlException;

	public ServicesBean getServiceByEndpoint(Service service) throws XmlException;
}
