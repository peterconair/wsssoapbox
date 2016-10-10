package org.wsssoapbox.dao.wsdl;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.api.Description;
import org.ow2.easywsdl.wsdl.api.Endpoint;
import org.ow2.easywsdl.wsdl.api.InterfaceType;
import org.ow2.easywsdl.wsdl.api.Service;
import org.ow2.easywsdl.wsdl.api.WSDLException;
import org.wsssoapbox.bean.model.wsdl.BindingBean;
import org.wsssoapbox.bean.model.wsdl.EndpointBean;
import org.wsssoapbox.bean.model.wsdl.InterfaceBean;
import org.wsssoapbox.bean.model.wsdl.ServicesBean;

public class ServiceDAO implements IServiceDAO {

	public List<ServicesBean> getServices(Description desc) throws WSDLException, XmlException {
		List<Service> services = desc.getServices();
		ServicesBean serviceBean = new ServicesBean();
		List<ServicesBean> serviceBeanList = new ArrayList<ServicesBean>();

		for (Service service : services) {

			serviceBean = new ServicesBean();

			serviceBean.setName(service.getQName().getLocalPart());
			serviceBean.setQname(service.getQName());

			serviceBean.setDocumentation(service.getDocumentation().getContent());
			serviceBean.setOtherAttributes(service.getOtherAttributes());
			serviceBean.setOtherElements(service.getOtherElements());

			// Set Endpoint (One Service has one Endpoint)
			IEndpointDAO endpointDAO = new EndpointDAO();
			List<EndpointBean> endpointBeanList = endpointDAO.getEndpointsByService(service);
			serviceBean.setEndpoints(endpointBeanList);

			// Set Interface (One Service has one Interface)
			IInterfaceDAO intefaceDAO = new InterfaceDAO();
			InterfaceBean interfaceBean = new InterfaceBean();
			interfaceBean = intefaceDAO.getInterfaceByService(service);
			serviceBean.setInterfaceType(interfaceBean);

			serviceBeanList.add(serviceBean);

		}
		return serviceBeanList;
	}

	public ServicesBean getServiceByName(Description desc, QName qname) throws WSDLException, XmlException {

		Service service = desc.getService(qname);
		ServicesBean serviceBean = new ServicesBean();

		serviceBean.setName(service.getQName().getLocalPart());
		serviceBean.setQname(service.getQName());

		serviceBean.setDocumentation(service.getDocumentation().getContent());
		serviceBean.setOtherAttributes(service.getOtherAttributes());
		serviceBean.setOtherElements(service.getOtherElements());

		// Set Endpoint (One Service has one Endpoint)
		IEndpointDAO endpointDAO = new EndpointDAO();
		List<EndpointBean> endpointBeanList = endpointDAO.getEndpointsByService(service);
		serviceBean.setEndpoints(endpointBeanList);

		// Set Interface (One Service has one Interface)
		IInterfaceDAO intefaceDAO = new InterfaceDAO();
		InterfaceBean interfaceBean = new InterfaceBean();
		interfaceBean = intefaceDAO.getInterfaceByService(service);
		serviceBean.setInterfaceType(interfaceBean);

		return serviceBean;
	}

	public ServicesBean getServiceByIndex(Description desc, int index) throws WSDLException, XmlException {
		Service service = desc.getServices().get(index);

		ServicesBean serviceBean = new ServicesBean();

		serviceBean.setName(service.getQName().getLocalPart());
		serviceBean.setQname(service.getQName());

		serviceBean.setDocumentation(service.getDocumentation().getContent());
		serviceBean.setOtherAttributes(service.getOtherAttributes());
		serviceBean.setOtherElements(service.getOtherElements());

		// Set Endpoint (One Service has one Endpoint)
		IEndpointDAO endpointDAO = new EndpointDAO();
		List<EndpointBean> endpointBeanList = endpointDAO.getEndpointsByService(service);
		serviceBean.setEndpoints(endpointBeanList);

		// Set Interface (One Service has one Interface)
		IInterfaceDAO intefaceDAO = new InterfaceDAO();
		InterfaceBean interfaceBean = new InterfaceBean();
		interfaceBean = intefaceDAO.getInterfaceByService(service);
		serviceBean.setInterfaceType(interfaceBean);

		return serviceBean;
	}

	public ServicesBean getServiceByEndpoint(Endpoint endpoint) throws XmlException {
		Service service = endpoint.getService();

		ServicesBean serviceBean = new ServicesBean();

		serviceBean.setName(service.getQName().getLocalPart());
		serviceBean.setQname(service.getQName());

		serviceBean.setDocumentation(service.getDocumentation().getContent());
		serviceBean.setOtherAttributes(service.getOtherAttributes());
		serviceBean.setOtherElements(service.getOtherElements());

		// Set Endpoint (One Service has one Endpoint)
		IEndpointDAO endpointDAO = new EndpointDAO();
		List<EndpointBean> endpointBeanList = endpointDAO.getEndpointsByService(service);
		serviceBean.setEndpoints(endpointBeanList);

		// Set Interface (One Service has one Interface)
		IInterfaceDAO intefaceDAO = new InterfaceDAO();
		InterfaceBean interfaceBean = new InterfaceBean();
		interfaceBean = intefaceDAO.getInterfaceByService(service);
		serviceBean.setInterfaceType(interfaceBean);

		return serviceBean;
	}

	public ServicesBean getServiceByEndpoint(Service service) throws XmlException {
		ServicesBean serviceBean = new ServicesBean();
		serviceBean.setDocumentation(service.getDocumentation().getContent());
		serviceBean.setOtherAttributes(service.getOtherAttributes());
		serviceBean.setOtherElements(service.getOtherElements());

		// Set Endpoint (One Service has one Endpoint)
		IEndpointDAO endpointDAO = new EndpointDAO();
		List<EndpointBean> endpointBeanList = endpointDAO.getEndpointsByService(service);
		serviceBean.setEndpoints(endpointBeanList);

		// Set Interface (One Service has one Interface)
		IInterfaceDAO intefaceDAO = new InterfaceDAO();
		InterfaceBean interfaceBean = new InterfaceBean();
		interfaceBean = intefaceDAO.getInterfaceByService(service);
		serviceBean.setInterfaceType(interfaceBean);

		return serviceBean;
	}

}
