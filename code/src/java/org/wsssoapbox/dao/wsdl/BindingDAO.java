package org.wsssoapbox.dao.wsdl;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;


import org.slf4j.Logger;
import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.api.Binding;
import org.ow2.easywsdl.wsdl.api.Description;
import org.ow2.easywsdl.wsdl.api.Endpoint;
import org.ow2.easywsdl.wsdl.api.WSDLException;
import org.slf4j.LoggerFactory;
import org.wsssoapbox.bean.model.wsdl.BindingBean;
import org.wsssoapbox.bean.model.wsdl.BindingOperationBean;
import org.wsssoapbox.bean.model.wsdl.InterfaceBean;

public class BindingDAO implements IBindingDAO {
	private BindingBean bindingBean;
	private List<BindingBean> bindingBeans;
	 private static final Logger logger=LoggerFactory.getLogger(BindingDAO.class);
	public List<BindingBean> getBindings(Description desc) throws WSDLException, XmlException {
bindingBeans = new ArrayList<BindingBean>();
		
		List<Binding> bindings = desc.getBindings();

		for (Binding binding : bindings) {
			
			bindingBean = new BindingBean();
			
			bindingBean.setName(binding.getQName().getLocalPart());
			
			bindingBean.setQname(binding.getQName());
			bindingBean.setDocumentation(binding.getDocumentation().getContent());
			bindingBean.setHttpContentEncodingDefault(binding.getHttpContentEncodingDefault());
			bindingBean.setHttpDefaultMethod(binding.getHttpDefaultMethod());
			bindingBean.setHttpQueryParameterSeparatorDefault(binding.getHttpQueryParameterSeparatorDefault());
			bindingBean.setOtherAttributes(binding.getOtherAttributes());
			bindingBean.setOtherElements(binding.getOtherElements());
			bindingBean.setStyle(binding.getStyle());
			bindingBean.setTransportProtocol(binding.getTransportProtocol());
			bindingBean.setTypeOfBinding(binding.getTypeOfBinding().toString());
			bindingBean.setVersion(binding.getVersion());

			// Set Interface (One Binding have One Interface)
			IInterfaceDAO iinterfaceDAO = new InterfaceDAO();
			InterfaceBean interfaceBean = new InterfaceBean();
			interfaceBean = iinterfaceDAO.getInterfaceByBinding(binding);
			bindingBean.setInterfaces(interfaceBean);

			// Set BindingOperation (One Binding have many BindingOperation)
			IBindingOperationDAO ibindingOperationDAO = new BindingOperationDAO();
			List<BindingOperationBean> boList = new ArrayList<BindingOperationBean>();
			boList = ibindingOperationDAO.getBindingOperationsByBinding(binding);
			bindingBean.setBindingOperations(boList);

			bindingBeans.add(bindingBean);
		}
		return bindingBeans;
	}
	public BindingBean getBindingByName(Description desc, QName bindingQName) throws WSDLException, XmlException {
bindingBean = new BindingBean();
		
		Binding binding = desc.getBinding(bindingQName);

		bindingBean.setName(binding.getQName().getLocalPart());
		
		bindingBean.setQname(binding.getQName());
		bindingBean.setDocumentation(binding.getDocumentation().getContent());
		bindingBean.setHttpContentEncodingDefault(binding.getHttpContentEncodingDefault());
		bindingBean.setHttpDefaultMethod(binding.getHttpDefaultMethod());
		bindingBean.setHttpQueryParameterSeparatorDefault(binding.getHttpQueryParameterSeparatorDefault());
		bindingBean.setOtherAttributes(binding.getOtherAttributes());
		bindingBean.setOtherElements(binding.getOtherElements());
		bindingBean.setStyle(binding.getStyle());
		bindingBean.setTransportProtocol(binding.getTransportProtocol());
		bindingBean.setTypeOfBinding(binding.getTypeOfBinding().toString());
		bindingBean.setVersion(binding.getVersion());

		// Set Interface (One Binding have One Interface)
		IInterfaceDAO iinterfaceDAO = new InterfaceDAO();
		InterfaceBean interfaceBean = new InterfaceBean();
		interfaceBean = iinterfaceDAO.getInterfaceByBinding(binding);
		bindingBean.setInterfaces(interfaceBean);

		// Set BindingOperation (One Binding have many BindingOperation)
		IBindingOperationDAO ibindingOperationDAO = new BindingOperationDAO();
		List<BindingOperationBean> boList = new ArrayList<BindingOperationBean>();
		boList = ibindingOperationDAO.getBindingOperationsByBinding(binding);
		bindingBean.setBindingOperations(boList);

		return bindingBean;
	}
	public BindingBean getBindingByEndpointName(Description desc, QName serviceQName, String endpointName)
			throws WSDLException, XmlException {
		bindingBean = new BindingBean();
		Binding binding = desc.getService(serviceQName).getEndpoint(endpointName).getBinding();

		bindingBean.setName(binding.getQName().getLocalPart());
		
		bindingBean.setQname(binding.getQName());
		bindingBean.setDocumentation(binding.getDocumentation().getContent());
		bindingBean.setHttpContentEncodingDefault(binding.getHttpContentEncodingDefault());
		bindingBean.setHttpDefaultMethod(binding.getHttpDefaultMethod());
		bindingBean.setHttpQueryParameterSeparatorDefault(binding.getHttpQueryParameterSeparatorDefault());
		bindingBean.setOtherAttributes(binding.getOtherAttributes());
		bindingBean.setOtherElements(binding.getOtherElements());
		bindingBean.setStyle(binding.getStyle());
		bindingBean.setTransportProtocol(binding.getTransportProtocol());
		bindingBean.setTypeOfBinding(binding.getTypeOfBinding().toString());
		bindingBean.setVersion(binding.getVersion());

		// Set Interface (One Binding have One Interface)
		IInterfaceDAO iinterfaceDAO = new InterfaceDAO();
		InterfaceBean interfaceBean = new InterfaceBean();
		interfaceBean = iinterfaceDAO.getInterfaceByBinding(binding);
		bindingBean.setInterfaces(interfaceBean);

		// Set BindingOperation (One Binding have many BindingOperation)
		IBindingOperationDAO ibindingOperationDAO = new BindingOperationDAO();
		List<BindingOperationBean> boList = new ArrayList<BindingOperationBean>();
		boList = ibindingOperationDAO.getBindingOperationsByBinding(binding);
		bindingBean.setBindingOperations(boList);

		return bindingBean;
	}
	public BindingBean getBindingByIndex(Description desc, int index) throws WSDLException, XmlException {
		bindingBean = new BindingBean();
		Binding binding = desc.getBindings().get(index);

		bindingBean.setName(binding.getQName().getLocalPart());
		
		bindingBean.setQname(binding.getQName());
		bindingBean.setDocumentation(binding.getDocumentation().getContent());
		bindingBean.setHttpContentEncodingDefault(binding.getHttpContentEncodingDefault());
		bindingBean.setHttpDefaultMethod(binding.getHttpDefaultMethod());
		bindingBean.setHttpQueryParameterSeparatorDefault(binding.getHttpQueryParameterSeparatorDefault());
		bindingBean.setOtherAttributes(binding.getOtherAttributes());
		bindingBean.setOtherElements(binding.getOtherElements());
		bindingBean.setStyle(binding.getStyle());
		bindingBean.setTransportProtocol(binding.getTransportProtocol());
		bindingBean.setTypeOfBinding(binding.getTypeOfBinding().toString());
		bindingBean.setVersion(binding.getVersion());

		// Set Interface (One Binding have One Interface)
		IInterfaceDAO iinterfaceDAO = new InterfaceDAO();
		InterfaceBean interfaceBean = new InterfaceBean();
		interfaceBean = iinterfaceDAO.getInterfaceByBinding(binding);
		bindingBean.setInterfaces(interfaceBean);

		// Set BindingOperation (One Binding have many BindingOperation)
		IBindingOperationDAO ibindingOperationDAO = new BindingOperationDAO();
		List<BindingOperationBean> boList = new ArrayList<BindingOperationBean>();
		boList = ibindingOperationDAO.getBindingOperationsByBinding(binding);
		bindingBean.setBindingOperations(boList);

		return bindingBean;
	}
	public BindingBean getBindingByEndpoint(Endpoint endpoint) throws XmlException {
		bindingBean = new BindingBean();
		Binding binding = endpoint.getBinding();
        
		bindingBean.setName(binding.getQName().getLocalPart());
		
		bindingBean.setQname(binding.getQName());
		bindingBean.setDocumentation(binding.getDocumentation().getContent());
		bindingBean.setHttpContentEncodingDefault(binding.getHttpContentEncodingDefault());
		bindingBean.setHttpDefaultMethod(binding.getHttpDefaultMethod());
		bindingBean.setHttpQueryParameterSeparatorDefault(binding.getHttpQueryParameterSeparatorDefault());
		bindingBean.setOtherAttributes(binding.getOtherAttributes());
		bindingBean.setOtherElements(binding.getOtherElements());
		bindingBean.setStyle(binding.getStyle());
		bindingBean.setTransportProtocol(binding.getTransportProtocol());
		bindingBean.setTypeOfBinding(binding.getTypeOfBinding().toString());
		bindingBean.setVersion(binding.getVersion());

		// Set Interface (One Binding have One Interface)
		IInterfaceDAO iinterfaceDAO = new InterfaceDAO();
		InterfaceBean interfaceBean = new InterfaceBean();
		interfaceBean = iinterfaceDAO.getInterfaceByBinding(binding);
		bindingBean.setInterfaces(interfaceBean);

		// Set BindingOperation (One Binding have many BindingOperation)
		IBindingOperationDAO ibindingOperationDAO = new BindingOperationDAO();
		List<BindingOperationBean> boList = new ArrayList<BindingOperationBean>();
		boList = ibindingOperationDAO.getBindingOperationsByBinding(binding);
		bindingBean.setBindingOperations(boList);

		return bindingBean;
	}


	

}
