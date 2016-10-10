package org.wsssoapbox.dao.wsdl;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.xml.namespace.QName;

import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.api.Description;
import org.ow2.easywsdl.wsdl.api.Endpoint;
import org.ow2.easywsdl.wsdl.api.Service;
import org.ow2.easywsdl.wsdl.api.WSDLException;
import org.wsssoapbox.bean.model.wsdl.BindingBean;
import org.wsssoapbox.bean.model.wsdl.EndpointBean;
import org.wsssoapbox.bean.model.wsdl.ServicesBean;


public class EndpointDAO implements IEndpointDAO {

	public List<EndpointBean> getEndpointsByServiceName(Description desc, QName qname) throws WSDLException,
	List<EndpointBean> endpointBeanList = new ArrayList<EndpointBean>();
	List<Endpoint> endpointList = new ArrayList<Endpoint>();
	endpointList = desc.getService(qname).getEndpoints();
	for (Endpoint ep : endpointList) {
		EndpointBean endpointBean = new EndpointBean();

		endpointBean.setName(ep.getName());
		endpointBean.setAddress(ep.getAddress());
		endpointBean.setHttpAuthenticationRealm(ep.getHttpAuthenticationScheme());
		endpointBean.setHttpAuthenticationScheme(ep.getHttpAuthenticationRealm());
		endpointBean.setDocumentation(ep.getDocumentation().getContent());
		endpointBean.setOtherAttributes(ep.getOtherAttributes());			
		endpointBean.setOtherElements(ep.getOtherElements());
		
		IBindingDAO bindingDAO = new BindingDAO();
		BindingBean bindingBean = new BindingBean();								
		bindingBean  = bindingDAO.getBindingByEndpoint(ep);	
		
		endpointBean.setBinding(bindingBean);
		
		IServiceDAO serviceDAO = new ServiceDAO();
		ServicesBean serviceBean = new ServicesBean();
		Service service = ep.getService();
		//serviceBean  = serviceDAO.getServiceByName(desc, qname);
		serviceBean.setName(service.getQName().getLocalPart());
		serviceBean.setNamespaceURI(service.getQName().getNamespaceURI());
		serviceBean.setLocalPart(service.getQName().getLocalPart());
		serviceBean.setQname(service.getQName());
		serviceBean.setDocumentation(service.getDocumentation().getContent());
		serviceBean.setOtherAttributes(service.getOtherAttributes());			
		endpointBean.setService(serviceBean);
		
		endpointBeanList.add(endpointBean);
	}

	return endpointBeanList;
	}

	public EndpointBean getEndpointByName(Description desc, QName serviceQName, String endpontName)
			throws WSDLException, XmlException {

		EndpointBean endpointBean = new EndpointBean();
		Endpoint ep  = desc.getService(serviceQName).getEndpoint(endpontName);
		
		
		endpointBean.setName(ep.getName());
		endpointBean.setAddress(ep.getAddress());
		endpointBean.setHttpAuthenticationRealm(ep.getHttpAuthenticationScheme());
		endpointBean.setHttpAuthenticationScheme(ep.getHttpAuthenticationRealm());
		endpointBean.setDocumentation(ep.getDocumentation().getContent());
		endpointBean.setOtherAttributes(ep.getOtherAttributes());		
		endpointBean.setOtherElements(ep.getOtherElements());
		
		IBindingDAO bindingDAO = new BindingDAO();
		BindingBean bindingBean = new BindingBean();			           
		bindingBean  = bindingDAO.getBindingByEndpoint(ep);				
		endpointBean.setBinding(bindingBean);
		
		IServiceDAO serviceDAO = new ServiceDAO();
		ServicesBean serviceBean = new ServicesBean();
		Service service = ep.getService();
		//serviceBean  = serviceDAO.getServiceByName(desc, qname);
		serviceBean.setName(service.getQName().getLocalPart());
		serviceBean.setNamespaceURI(service.getQName().getNamespaceURI());
		serviceBean.setLocalPart(service.getQName().getLocalPart());
		serviceBean.setQname(service.getQName());
		serviceBean.setDocumentation(service.getDocumentation().getContent());
		serviceBean.setOtherAttributes(service.getOtherAttributes());			
		endpointBean.setService(serviceBean);
				
		return endpointBean;
	}

	public List<EndpointBean> getEndpointsByService(Service service) throws XmlException {
		List<EndpointBean> endpointBeanList = new ArrayList<EndpointBean>();
		List<Endpoint> endpointList = new ArrayList<Endpoint>();
		endpointList = service.getEndpoints();
		for (Endpoint ep : endpointList) {
			EndpointBean endpointBean = new EndpointBean();

			endpointBean.setName(ep.getName());
			endpointBean.setAddress(ep.getAddress());
			endpointBean.setHttpAuthenticationRealm(ep.getHttpAuthenticationScheme());
			endpointBean.setHttpAuthenticationScheme(ep.getHttpAuthenticationRealm());
			endpointBean.setDocumentation(ep.getDocumentation().getContent());
			endpointBean.setOtherAttributes(ep.getOtherAttributes());			
			endpointBean.setOtherElements(ep.getOtherElements());
			
			IBindingDAO bindingDAO = new BindingDAO();
			BindingBean bindingBean = new BindingBean();			           
			bindingBean  = bindingDAO.getBindingByEndpoint(ep);				
			endpointBean.setBinding(bindingBean);
			
			IServiceDAO serviceDAO = new ServiceDAO();
			ServicesBean serviceBean = new ServicesBean();
			Service service1 = ep.getService();
			//serviceBean  = serviceDAO.getServiceByName(desc, qname);
			serviceBean.setName(service1.getQName().getLocalPart());
			serviceBean.setNamespaceURI(service1.getQName().getNamespaceURI());
			serviceBean.setLocalPart(service1.getQName().getLocalPart());
			serviceBean.setQname(service1.getQName());
			serviceBean.setDocumentation(service1.getDocumentation().getContent());
			serviceBean.setOtherAttributes(service1.getOtherAttributes());			
			endpointBean.setService(serviceBean);
			
			endpointBeanList.add(endpointBean);
		}

		return endpointBeanList;
	}


	


}
