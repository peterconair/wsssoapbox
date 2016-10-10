package org.wsssoapbox.datasource.wsdl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.WSDLFactory;
import org.ow2.easywsdl.wsdl.api.Binding;
import org.ow2.easywsdl.wsdl.api.BindingOperation;
import org.ow2.easywsdl.wsdl.api.Description;
import org.ow2.easywsdl.wsdl.api.Endpoint;
import org.ow2.easywsdl.wsdl.api.InterfaceType;
import org.ow2.easywsdl.wsdl.api.Operation;
import org.ow2.easywsdl.wsdl.api.Part;
import org.ow2.easywsdl.wsdl.api.Service;
import org.ow2.easywsdl.wsdl.api.Types;
import org.ow2.easywsdl.wsdl.api.WSDLException;
import org.ow2.easywsdl.wsdl.api.WSDLReader;
import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfOperation;

import org.ow2.easywsdl.wsdl.impl.wsdl11.DescriptionImpl;
import org.ow2.easywsdl.wsdl.impl.wsdl11.MessageImpl;
import org.wsssoapbox.bean.model.wsdl.*;

import com.sun.script.util.BindingsBase;
import com.sun.xml.internal.ws.api.model.wsdl.WSDLDescriptorKind;

public class ParseWSDLCommand {
	public static final long serialVersionUID = 1L;
	private Description desc;
	private WSDLReader reader;
	
	private DescriptionBean wsdlBean;
	private BindingBean bindingBean;
	private BindingOperationBean bindingOperationBean;
	private EndpointBean endpointBean;
	private InterfaceBean interfaceBean;
	private MessagesBean messageBean;
	private OperationBean operationBean;
	private ServicesBean serviceBean;
	private TypeBean typeBean;
	
	private List <EndpointBean> bindingBeanList;
	private List <BindingOperationBean> bindingOperationBeanList;
	private List <EndpointBean> endpointBeanList;
	private List <InterfaceBean> interfaceBeanList;
	private List <MessagesBean> messageBeanList;
	private List <OperationBean> operationBeanList;
	private List <ServicesBean> serviceBeanList;
	private List <TypeBean> typeBeanList;
	
	
	private static final Log logger = LogFactory.getLog(ParseWSDLCommand.class);

	/**
	 * @param wsdlURL
	 * @throws XmlException
	 */

	public ParseWSDLCommand(String wsdlURL) {
		logger.info(">>>>>>>>>>>>>>>>> Start public ParseWSDLCommand(String wsdlURL) ");
		initail(wsdlURL);
		logger.info(">>>>>>>>>>>>>>>>> End public ParseWSDLCommand(String wsdlURL) <<<<<<<<<<<<<<<<<<<<<<<<");
	}

	private void initail(String wsdlURL) {
		logger.info(">>>>>>>>>>>>>>>> Start private void initail(String wsdlURL) ");
		try {
			reader = WSDLFactory.newInstance().newWSDLReader();
			desc = reader.read(new URL(wsdlURL));
		} catch (MalformedURLException e1) {
			logger.error("MalformedURLException");
			e1.printStackTrace();
		} catch (IOException e1) {
			logger.error("IOException");
			e1.printStackTrace();
		} catch (URISyntaxException e1) {
			logger.error("URISyntaxException");
			e1.printStackTrace();
		} catch (WSDLException e) {
			logger.error("WSDLException");
			e.printStackTrace();
		}
		logger.info(">>>>>>>>>>>>>>>>>> End private void initail(String wsdlURL) <<<<<<<<<<<<<<<<");
	}

	public ServicesBean getServiceInfo(String wsdlURL) throws WSDLException {
		logger.info(">>>>>>>>>>>>>>> Start public ServicesBean getServiceInfo(String wsdlURL) throws WSDLException ");
		Service service = desc.getServices().get(0);
		serviceBean = new ServicesBean();
		serviceBean.setName(service.getQName().getLocalPart());
		serviceBean.setQname(service.getQName());
		
	//	service.getEndpoints();
	//	service.getInterface();
	//	service.getOtherAttributes();
	//	service.getOtherElements();
	//	serviceBean.setDocumentation(documentation);
		
		
		logger.info("Servcie Name : " + service.getQName().getLocalPart());
		logger.info("Document Base URI : " + desc.getDocumentBaseURI());
		logger.info("Verstion : " + desc.getVersion());
		logger.info("Qname : " + service.getQName());
		logger.info("Target Namespace : " + desc.getTargetNamespace());
		logger.info("************ Start Name spaces ****************");
		logger.info(desc.getNamespaces());
		logger.info("************ End Name spaces ****************");
		logger.info(">>>>>>>>>>>>>>>> End public ServicesBean getServiceInfo(String wsdlURL) throws WSDLException <<<<<<<<<<<<<<<<<<");
		return serviceBean;
	}

	public EndpointBean getgetEndpointInfoByName(String name) {
		endpointBean = new EndpointBean(name);
		Service service = desc.getServices().get(0);
		Endpoint endpoint = service.getEndpoint(name);
		endpointBean.setAddress(endpoint.getAddress());
		endpointBean.setDocumentation(endpoint.getDocumentation().toString());
		endpointBean.setHttpAuthenticationRealm(endpoint
				.getHttpAuthenticationRealm());
		endpointBean.setHttpAuthenticationScheme(endpoint.getHttpAuthenticationScheme());
		endpointBean.setBinding(this.bindingBean);
		endpointBean.setOtherElements(null);
		endpointBean.setService(this.serviceBean);
		return endpointBean;
	}

	public List<EndpointBean> getEndpointInfo(String wsdlURL)
			throws XmlException {

		bindingBeanList = new ArrayList<EndpointBean>();

		System.out.println("************** Endpoint*********");
		Service service = desc.getServices().get(0);
		List<Endpoint> endpoints = service.getEndpoints();
		Iterator iteratorEnpoint = endpoints.iterator();
		while (iteratorEnpoint.hasNext()) {
			EndpointBean ednpointBean = new EndpointBean();
			Endpoint endpoint = (Endpoint) iteratorEnpoint.next();
			ednpointBean.setName(endpoint.getName());
			ednpointBean.setAddress(endpoint.getAddress());
			ednpointBean.setDocumentation(endpoint.getDocumentation()
					.toString());
			ednpointBean.setHttpAuthenticationRealm(endpoint
					.getHttpAuthenticationRealm());
			ednpointBean.setHttpAuthenticationScheme(endpoint
					.getHttpAuthenticationScheme());
			// ednpointBean.setOtherElements(endpoint.getOtherElements());
			// ednpointBean.setBindings(endpoint.getBinding());
			// ednpointBean.setServices(endpoint.getService());

			bindingBeanList.add(ednpointBean);

			System.out.println("***********************");
			System.out.println("getName: " + endpoint.getName());
			System.out.println("***********************");
			/*
			 * System.out.println("getAddress : " + endpoint.getAddress());
			 * System.out.println("getHttpAuthenticationRealm : " +
			 * endpoint.getHttpAuthenticationRealm());
			 * System.out.println("getHttpAuthenticationScheme : " +
			 * endpoint.getHttpAuthenticationScheme());
			 * System.out.println("getDocumentation :" +
			 * endpoint.getDocumentation());
			 * System.out.println("getOtherElements : " +
			 * endpoint.getOtherElements()); //
			 * System.out.println("getBinding : " +endpoint.getBinding()); //
			 * System.out.println("getService : " + endpoint.getService());
			 */
		}

		return bindingBeanList;
	}

	public List<BindingBean> getBindingInfo(String wsdlURL) throws XmlException {
		List<BindingBean> bindingBeanList = new ArrayList<BindingBean>();
		System.out.println("************** Binding *********");
		List<Binding> bindings = desc.getBindings();
		Iterator iteratorBinding = bindings.iterator();

		List<BindingOperation> bindingOperatonList = new ArrayList<BindingOperation>();
		// bindingOperatonList = bindings.get(0).getBindingOperations();

		while (iteratorBinding.hasNext()) {
			Binding binding = (Binding) iteratorBinding.next();

			System.out.println("**************  *********");
			System.out.println("getQName : " + binding.getQName());
			System.out.println("getClass : " + binding.getClass());
			System.out.println("**************  *********");
			System.out.println("getTransportProtocol : "
					+ binding.getTransportProtocol());
			System.out.println("getTypeOfBinding : "
					+ binding.getTypeOfBinding());
			System.out.println("getStyle : " + binding.getStyle());
			System.out.println("getOtherElements : "
					+ binding.getOtherElements());
			System.out.println("getOtherAttributes : "
					+ binding.getOtherAttributes());
			System.out.println("getDocumentation : "
					+ binding.getDocumentation());
			System.out.println("getHttpDefaultMethod : "
					+ binding.getHttpDefaultMethod());
			System.out.println("getHttpContentEncodingDefault : "
					+ binding.getHttpContentEncodingDefault());
			System.out.println("getHttpQueryParameterSeparatorDefault : "
					+ binding.getHttpQueryParameterSeparatorDefault());
			System.out.println("getInterface : " + binding.getInterface());
			System.out.println("getBindingOperations() :"
					+ binding.getBindingOperations());

		}

		return bindingBeanList;
	}

	public OperationBean getOperationInfoByName(String operationName) {

		return null;
	}

	public List<OperationBean> getOperatersInfo(String wsdlURL) {
		logger.info(">>>>>>>>>>>>>>>>> Start public List<OperationBean> getOperatersInfo(String wsdlURL)  ");
		List<InterfaceType> inter = desc.getInterfaces();
		Iterator interfaces = inter.iterator();
		List<Operation> operations = null;
		logger.info("************ Interfaces ****************");
		while (interfaces.hasNext()) {
			InterfaceType interfaceType = (InterfaceType) interfaces.next();
			// logger.info(itf.getOperations());
			operations = interfaceType.getOperations();
			logger.info(interfaceType.getQName().getNamespaceURI());
		}

		List<OperationBean> operationsBeanList = new ArrayList<OperationBean>();
		Iterator iteratorOperators = operations.iterator();
		AbsItfOperation operation = null;

		logger.info("************ Operation ****************");
		while (iteratorOperators.hasNext()) {
			operation = (AbsItfOperation) iteratorOperators.next();
			logger.info("Operataion QName : " + operation.getQName());
			logger.info("Operataion LocalPart : "
					+ operation.getQName().getLocalPart());
			logger.info("Operataion Prefix : "
					+ operation.getQName().getPrefix());
			logger.info("Operataion URI : "
					+ operation.getQName().getNamespaceURI());
			logger.info("Operataion Signature : " + operation.getSignature());
			// logger.info("Operataion Imput :" +
			// operation.getInput().getParts());
			// logger.info("Operataion Output :"+
			// operation.getOutput().getParts());
			OperationBean oprerationBean = new OperationBean();
			oprerationBean.setName(operation.getQName().getLocalPart());
			operationsBeanList.add(oprerationBean);
		}
		logger.info(">>>>>>>>>>>>>>>  End public List<OperationBean> getOperatersInfo(String wsdlURL) <<<<<<<<<<<<<<<<<< ) ");
		return operationsBeanList;
	}

	public DescriptionBean getWSDLInfo(String wsdlURL) throws WSDLException {

		DescriptionBean wsdlBean = new DescriptionBean();
		logger.info("************** Description*********");
		logger.info("desc.getDocumentation() :" + desc.getDocumentation());
		System.out
				.println("getTargetNamespace() :" + desc.getTargetNamespace());
		logger.info("getSchemaLocation() :" + desc.getSchemaLocation());
		logger.info("getDocumentBaseURI :" + desc.getDocumentBaseURI());
		logger.info("getVersion :" + desc.getVersion());
		logger.info("getQName :" + desc.getQName());
		logger.info("getImports :" + desc.getImports());
		logger.info("getIncludes() :" + desc.getIncludes());
		logger.info("getNamespaces :" + desc.getNamespaces());
		// logger.info("getBindings :" + desc.getBindings());
		// logger.info("desc.getInterfaces :" + desc.getInterfaces());
		// logger.info("getServices :" + desc.getServices());
		// logger.info("getTypes :" + desc.getTypes());

		wsdlBean.setName(desc.getDocumentBaseURI().toString());
		wsdlBean.setDocumentBaseURI(desc.getDocumentBaseURI().toString());
		if (desc.getQName() != null)
		wsdlBean.setVersion(desc.getVersion().toString());
		wsdlBean.setTargetNamespace(desc.getTargetNamespace());
		return wsdlBean;
	}

	public void getWSDLInfo1(String wsdlURL) throws XmlException, Throwable,
			Throwable, Throwable {

		WSDLReader reader = WSDLFactory.newInstance().newWSDLReader();
		Description desc = reader.read(new URL(wsdlURL));

		logger.info("Service Name : " + desc.getQName().getNamespaceURI());
		logger.info("Document Base URI : " + desc.getDocumentBaseURI());
		logger.info("Verstion : " + desc.getVersion());
		logger.info("Qname : " + desc.getQName());
		logger.info("Target Namespace : " + desc.getTargetNamespace());
		logger.info("************ Start Name spaces ****************");
		logger.info(desc.getNamespaces());
		logger.info("************ End Name spaces ****************");

		System.out.println();
		logger.info("************ Types ****************");
		Types types = desc.getTypes();

		logger.info("Types Documents : " + types.getDocumentation());
		logger.info("Types List Import Schemas : " + types.getImportedSchemas());
		logger.info("Types List Other Attributes: "
				+ types.getOtherAttributes());

		logger.info("Types List Other Elements : " + types.getOtherElements());

		System.out.println();
		logger.info("************ Messages ****************");
		// List messages
		List<MessageImpl> messages = ((DescriptionImpl) desc).getMessages();
		List<Part> parts;
		Iterator iteratorMessages = messages.iterator();
		int index = 0;
		logger.info("************ getParts ****************");
		while (iteratorMessages.hasNext()) {
			index++;
			MessageImpl m = (MessageImpl) iteratorMessages.next();
			logger.info("Messages Name : " + m.getParts());

		}

		List<InterfaceType> inter = desc.getInterfaces();
		Iterator interfaces = inter.iterator();
		List<Operation> topt = null;

		logger.info("************ Interfaces ****************");
		while (interfaces.hasNext()) {
			InterfaceType itf = (InterfaceType) interfaces.next();
			logger.info(itf.getOperations());
			topt = itf.getOperations();
			logger.info(itf.getQName());
		}

		AbsItfOperation opt = null;
		System.out.println();
		logger.info("************ Operation ****************");
		for (int i = 0; i < topt.size(); i++) {
			opt = topt.get(i);
			logger.info("Operataion QName :" + opt.getQName());
			logger.info("Operataion LocalPart :"
					+ opt.getQName().getLocalPart());
			logger.info("Operataion Prefix :" + opt.getQName().getPrefix());
			logger.info("Operataion URI :" + opt.getQName().getNamespaceURI());
			logger.info("Operataion Signature :" + opt.getSignature());
			System.out
					.println("Operataion Imput :" + opt.getInput().getParts());
			logger.info("Operataion Output :" + opt.getOutput().getParts());
		}

		List<Binding> bindings = desc.getBindings();
		Iterator iteratorBindings = bindings.iterator();
		System.out.println();
		logger.info("************ Bindings ****************");
		while (iteratorBindings.hasNext()) {
			Binding b = (Binding) iteratorBindings.next();
			logger.info("*******************");
			logger.info("Binding Name : " + b.getQName());
			logger.info("Version : " + b.getVersion());

			List<BindingOperation> listBO = b.getBindingOperations();
			BindingOperation bo = null;
			Iterator iteratorBO = listBO.iterator();
			while (iteratorBO.hasNext()) {
				logger.info("*******************");
				bo = (BindingOperation) iteratorBO.next();
				logger.info("Operation  : " + bo.getQName());
				logger.info("Opration Name : "
						+ bo.getOperation().getQName().getLocalPart());
				logger.info("Description : "
						+ bo.getDocumentation().getContent());
				logger.info("Input: " + bo.getInput());
				logger.info("Http Method : " + bo.getHttpMethod());
				logger.info("Sytle : " + bo.getStyle());
				logger.info("MEP : " + bo.getMEP());
				logger.info("SOAP Action : " + bo.getSoapAction());
				logger.info("Signature : " + bo.getOperation().getSignature());
			}

		}

		// Endpoints take place in services.
		// Select a service
		List<Service> services = desc.getServices();
		Service service = desc.getServices().get(0);
		logger.info("Service Name : " + service.getQName().getLocalPart());
		// List endpoints
		List<Endpoint> endpoints = service.getEndpoints();
		Iterator iteratorEndpoints = service.getEndpoints().iterator();
		System.out.println();
		logger.info("************ Endpoint ****************");
		while (iteratorEndpoints.hasNext()) {
			Endpoint e = (Endpoint) iteratorEndpoints.next();
			logger.info("Endpoint: " + e.getName());
		}

		Iterator iteratorServices = services.iterator();
		System.out.println();
		logger.info("************ Service ****************");
		while (iteratorServices.hasNext()) {
			Service s = (Service) iteratorServices.next();
			logger.info("s.getOtherAttributes().keySet()");

		}

	}

	/**
	 * @param args
	 * @throws Throwable
	 * @throws WSDLException
	 */
	/*	*/
	public static void main(String[] args) throws Throwable {

		String wsdlURL = "http://www.pttplc.com/pttinfo.asmx?WSDL";
		// ParseWSDLCommand p = new ParseWSDLCommand();

		// String wsdlURL =
		// "http://localhost:8888/WebServiceTutorial/ws/doc/hello?wsdl";
		ParseWSDLCommand p = new ParseWSDLCommand(wsdlURL);
		// p.getWSDLInfo(wsdlURL);
		// p.getOperaters(wsdlURL);
		p.getServiceInfo(wsdlURL);

	}

}
