package org.wsssoapbox.bean.model.wsdl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.wsdl.Binding;
import javax.wsdl.Definition;
import javax.wsdl.Message;
import javax.wsdl.Port;
import javax.wsdl.PortType;
import javax.wsdl.Service;
import javax.wsdl.WSDLException;
import javax.wsdl.xml.WSDLReader;
import javax.xml.namespace.QName;
import javax.wsdl.Operation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.wsdl.xml.WSDLReaderImpl;

public class PharseWSDL {
	
	/**
	 * @uml.property  name="portList"
	 */
	List<PortBean> portList;
	/**
	 * @uml.property  name="operationList"
	 */
	List<OperationBean> operationList;
	/**
	 * @uml.property  name="messageList"
	 */
	List<MessagesBean> messageList;
	/**
	 * @uml.property  name="serviceList"
	 */
	List<ServicesBean> serviceList;
	private static final Log log = LogFactory.getLog(PharseWSDL.class);

	/*
	 * public static void main(String[] args) throws WSDLException { String url
	 * = "http://localhost:8181/hello?wsdl"; WSDLReader reader = new
	 * WSDLReaderImpl(); Definition def =
	 * reader.readWSDL("http://localhost:8181/hello?wsdl"); QName qname =
	 * def.getQName();
	 * 
	 * Service service = def.getService(qname); String serviceName = ""; // Port
	 * port = service.
	 * 
	 * String baseURL = def.getDocumentBaseURI();
	 * System.out.println(" BASE URL  = " + baseURL);
	 * System.out.println(" QName  = " + qname);
	 * 
	 * WSDLWriterImpl writer = new WSDLWriterImpl();
	 * 
	 * //PharseWSDL.getOperaters(url); //PharseWSDL.getMessages(url);
	 * 
	 * }
	 */

	public void getWSDLInfo(String url) {
		WSDLReader reader = new WSDLReaderImpl();
		Definition def = null;
		try {
			def = reader.readWSDL(url);
		} catch (WSDLException e) {
			e.printStackTrace();
		}
		Map services = def.getServices();

		System.out.println("def.getAllServices().keySet()"
				+ def.getAllServices().keySet());

		Iterator iteratorServices = services.values().iterator();

		boolean bPortFound = false;
		while ((iteratorServices.hasNext()) && !(bPortFound)) {
			Service service = (Service) iteratorServices.next();
			System.out.println("service.getPorts().keySet()"
					+ service.getPorts().keySet());
			
			Map ports  = service.getPorts();
		
			String portName = "";
		//	if ((port = service.getPort(portName)) != null) {
		//		bPortFound = true;
		//	}
		}
	}

	public List<ServicesBean> getServices(String url) {
		List<ServicesBean> serviceList = new ArrayList<ServicesBean>();
		WSDLReader reader = new WSDLReaderImpl();
		Definition def = null;
		try {
			def = reader.readWSDL(url);
		} catch (WSDLException e) {
			e.printStackTrace();
		}
		Map services = def.getServices();
		Iterator iteratorServices = services.values().iterator();
		boolean bPortFound = false;
		while ((iteratorServices.hasNext()) && !(bPortFound)) {
			Service service = (Service) iteratorServices.next();
			// service.get
		}

		return serviceList;
	}

	public List<OperationBean> getOperaters(String url) {
		// System.out.println("getOperaters: " );
		WSDLReader reader = new WSDLReaderImpl();
		Definition def = null;
		try {
			def = reader.readWSDL(url);
		} catch (WSDLException e) {
			e.printStackTrace();
		}
		QName qname = def.getQName();
		System.out.println("qname.getNamespaceURI(): "
				+ qname.getNamespaceURI());
		log.info("qname.getNamespaceURI(): " + qname.getNamespaceURI());
		log.info("qname.getPrefix(): " + qname.getPrefix());
		log.info("qname.getLocalPart(): " + qname.getLocalPart());

		Service service = def.getService(qname);

		Port port = service.getPort("HelloWorldImplPort");
		Binding binding = port.getBinding();
		PortType portType = binding.getPortType();

		List<OperationBean> operationsBeanList = new ArrayList<OperationBean>();

		List<Operation> operations = portType.getOperations();
		Iterator<Operation> opIterator = operations.iterator();
		OperationBean oprerationBean = new OperationBean();
		while (opIterator.hasNext()) {
			Operation operation = (Operation) opIterator.next();

			if (!operation.isUndefined()) {

				oprerationBean.setName(operation.getName());
				System.out.println(operation.getName());
				operationsBeanList.add(oprerationBean);

			}
		}
		return operationsBeanList;
	}

	public static void getMessages(String wsdl) throws WSDLException {

		WSDLReader reader = new WSDLReaderImpl();
		Definition def = reader.readWSDL(wsdl);
		Map messages = def.getMessages();
		Iterator msgIterator = messages.values().iterator();

		while (msgIterator.hasNext()) {
			Message msg = (Message) msgIterator.next();

			if (!msg.isUndefined()) {
				System.out.println(msg.getQName());
			}
		}
	}
}
