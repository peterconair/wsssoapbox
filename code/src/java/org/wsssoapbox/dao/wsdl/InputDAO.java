package org.wsssoapbox.dao.wsdl;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;


import org.slf4j.Logger;

import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.api.BindingInput;
import org.ow2.easywsdl.wsdl.api.Description;
import org.ow2.easywsdl.wsdl.api.Input;
import org.ow2.easywsdl.wsdl.api.Operation;
import org.slf4j.LoggerFactory;
import org.wsssoapbox.bean.model.wsdl.ElementBean;
import org.wsssoapbox.bean.model.wsdl.InputBean;
import org.wsssoapbox.bean.model.wsdl.PartBean;

public class InputDAO implements IInputDAO {
	 private static final Logger logger=LoggerFactory.getLogger(InputDAO.class);

    @Override
	public InputBean getInputByInterface(Description desc, QName interfaceQName, QName operationQName)
			throws XmlException {
		logger.debug("start public InputBean getInputByInterface(Description desc, QName interfaceQName, QName operationQName)");
		InputBean inputBean = new InputBean();
		Input input = desc.getInterface(interfaceQName).getOperation(operationQName).getInput();
		inputBean.setName(input.getName());
		logger.debug("Input Name : "+input.getName());
		inputBean.setDocumentation(input.getDocumentation().getContent());

		//Element (one Input have one Element)
		ElementDAO elementDAO = new ElementDAO();
		ElementBean elementBean = new ElementBean();
		elementBean = elementDAO.getElementByInput(input);
		inputBean.setElement(elementBean);

		inputBean.setMessageName(input.getMessageName());
		inputBean.setOtherAttributes(input.getOtherAttributes());
		inputBean.setOtherElements(input.getOtherElements());

		//part ( one Input have  many Part)
		List<PartBean> partBeanList = new ArrayList<PartBean>();
		IPartDAO partDAO = new PartDAO();
		partBeanList = partDAO.getPartsByInput(input);
		inputBean.setParts(partBeanList);				

		
		
		logger.debug("end public InputBean getInputByInterface(Description desc, QName interfaceQName, QName operationQName)");
		return inputBean;
	}

    @Override
	public InputBean getInputByInterfaceIndex(Description desc, int interfaceIndex, QName operationQName)
			throws XmlException {
		logger.debug("start public InputBean getInputByInterfaceIndex(Description desc, int interfaceIndex, QName operationQName)");
		InputBean inputBean = new InputBean();
		Input input = desc.getInterfaces().get(interfaceIndex).getOperation(operationQName).getInput();

		inputBean.setName(input.getName());
		logger.debug("Input Name : "+input.getName());
		inputBean.setDocumentation(input.getDocumentation().getContent());
		//Element (one Input have one Element)
		ElementDAO elementDAO = new ElementDAO();
		ElementBean elementBean = new ElementBean();
		elementBean = elementDAO.getElementByInput(input);
		inputBean.setElement(elementBean);

		inputBean.setMessageName(input.getMessageName());
		inputBean.setOtherAttributes(input.getOtherAttributes());
		inputBean.setOtherElements(input.getOtherElements());

		//part ( one Input have  many Part)
		IPartDAO partDAO = new PartDAO();
		List<PartBean> partBeanList = new ArrayList<PartBean>();
		partBeanList = partDAO.getPartsByInput(input);
		inputBean.setParts(partBeanList);

		logger.debug("end public InputBean getInputByInterfaceIndex(Description desc, int interfaceIndex, QName operationQName)");
		return inputBean;

	}

	// may not correct 
	/**
	 * Convert BindingInput to Input all attributes may be not assign
	 */
    @Override
	public InputBean getInputByBinding(Description desc, QName bindingQName, String operationName) throws XmlException {
		logger.debug("start public InputBean getInputByBinding(Description desc, QName bindingQName, String operationQName)");
		
		InputBean inputBean = new InputBean();
		BindingInput bindingInput = desc.getBinding(bindingQName).getBindingOperation(operationName).getInput();
		Input input =               desc.getBinding(bindingQName).getBindingOperation(operationName).getOperation().getInput();
		
		logger.debug("Binding QName : "+bindingQName);
		logger.debug("Operation Name : "+operationName);
		logger.debug("InputBean : "+inputBean);
		logger.debug("Input Name : "+bindingInput.getName());
		inputBean.setName(bindingInput.getName());		
		inputBean.setDocumentation(bindingInput.getDocumentation().getContent());
		inputBean.setOtherAttributes(bindingInput.getOtherAttributes());
		inputBean.setOtherElements(bindingInput.getOtherElements());
		
		
		logger.debug("Input  : "+input);
		logger.debug("Name  : "+input.getName());
		logger.debug("Documentation  : "+input.getDocumentation());
		logger.debug("Other Attributes  : "+input.getOtherAttributes());
		logger.debug("Other Elements  : "+input.getOtherElements());
		logger.debug("Element  : "+input.getElement());
		logger.debug("Message Name  : "+input.getMessageName());
		logger.debug("Parts  : "+input.getParts());
		
//		logger.debug("Http Content Encoding  : "+input.getHttpContentEncoding());
//		logger.debug("HTTP Binding4Wsdl11  : "+input.getHTTPBinding4Wsdl11());		
//		logger.debug("HTTP Binding4Wsdl20  : "+input.getHTTPBinding4Wsdl20());
//		logger.debug("MIME Binding4Wsdl11  : "+input.getMIMEBinding4Wsdl11());
//		logger.debug("SOAP11 Binding4Wsdl11  : "+input.getSOAP11Binding4Wsdl11());
//		logger.debug("SOAP12 Binding4Wsdl11  : "+input.getSOAP12Binding4Wsdl11());
//		logger.debug("SOAP12 Binding4Wsdl20  : "+input.getSOAP12Binding4Wsdl20());
		
		
		logger.debug("Name  : "+bindingInput.getName());
		logger.debug("Http Content Encoding  : "+bindingInput.getHttpContentEncoding());
		logger.debug("Documentation  : "+bindingInput.getDocumentation());
		logger.debug("HTTP Binding4Wsdl11  : "+bindingInput.getHTTPBinding4Wsdl11());		
		logger.debug("HTTP Binding4Wsdl20  : "+bindingInput.getHTTPBinding4Wsdl20());
		logger.debug("MIME Binding4Wsdl11  : "+bindingInput.getMIMEBinding4Wsdl11());
		logger.debug("Other Attributes  : "+bindingInput.getOtherAttributes());
		logger.debug("Other Elements  : "+bindingInput.getOtherElements());
		logger.debug("SOAP11 Binding4Wsdl11  : "+bindingInput.getSOAP11Binding4Wsdl11());
		logger.debug("SOAP12 Binding4Wsdl11  : "+bindingInput.getSOAP12Binding4Wsdl11());
		logger.debug("SOAP12 Binding4Wsdl20  : "+bindingInput.getSOAP12Binding4Wsdl20());

			
		logger.debug("end public InputBean getInputByBinding(Description desc, QName bindingQName, String operationQName)");
		return inputBean;
	}

    @Override
	public InputBean getInputByBindingOperation(Operation operation) throws XmlException {
		logger.debug("start public InputBean getInputByBindingOperation(Operation operation)");
		InputBean inputBean = new InputBean();
		Input input = operation.getInput();
		inputBean.setName(input.getName());
		logger.debug("Input Name : "+input.getName());
		inputBean.setDocumentation(input.getDocumentation().getContent());
		
		//Element (one Input have one Element)
		ElementDAO elementDAO = new ElementDAO();
		ElementBean elementBean = new ElementBean();
		elementBean = elementDAO.getElementByInput(input);
		inputBean.setElement(elementBean);

		inputBean.setMessageName(input.getMessageName());
		inputBean.setOtherAttributes(input.getOtherAttributes());
		inputBean.setOtherElements(input.getOtherElements());

		//part ( one Input have  many Part)
		IPartDAO partDAO = new PartDAO();
		List<PartBean> partBeanList = new ArrayList<PartBean>();
		partBeanList = partDAO.getPartsByInput(input);
		inputBean.setParts(partBeanList);
		
		logger.debug("end public InputBean getInputByBindingOperation(Operation operation)");
		return inputBean;
	}

    @Override
	public InputBean getInputByOperation(Operation operation) throws XmlException {
		logger.debug("start public InputBean getInputByOperation(Operation operation)");
		InputBean inputBean = new InputBean();
		Input input = operation.getInput();
		inputBean.setName(input.getName());
		logger.debug("Input Name : "+input.getName());
		inputBean.setDocumentation(input.getDocumentation().getContent());
		
		//Element (one Input have one Element)
		ElementDAO elementDAO = new ElementDAO();
		ElementBean elementBean = new ElementBean();
		elementBean = elementDAO.getElementByInput(input);
		inputBean.setElement(elementBean);

		inputBean.setMessageName(input.getMessageName());
		inputBean.setOtherAttributes(input.getOtherAttributes());
		inputBean.setOtherElements(input.getOtherElements());

		//part ( one Input have  many Part)
		IPartDAO partDAO = new PartDAO();
		List<PartBean> partBeanList = new ArrayList<PartBean>();
		partBeanList = partDAO.getPartsByInput(input);
		inputBean.setParts(partBeanList);
		
		logger.debug("end public InputBean getInputByOperation(Operation operation) ");
		return inputBean;
	}

	
}
