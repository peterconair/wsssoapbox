package org.wsssoapbox.dao.wsdl;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.api.BindingOutput;
import org.ow2.easywsdl.wsdl.api.Description;
import org.ow2.easywsdl.wsdl.api.Operation;
import org.ow2.easywsdl.wsdl.api.Output;
import org.wsssoapbox.bean.model.wsdl.ElementBean;
import org.wsssoapbox.bean.model.wsdl.OutputBean;
import org.wsssoapbox.bean.model.wsdl.PartBean;

public class OutputDAO implements IOutputDAO {
	private static final Log logger = LogFactory.getLog(OutputDAO.class);

	public OutputBean getOutputByInterface(Description desc, QName interfaceQName, QName operationQName)
			throws XmlException {
		logger.debug("start public OutputBean getOutputByInterface(Description desc, QName interfaceQName, QName operationQName)");
		OutputBean outputBean = new OutputBean();
		Output output = desc.getInterface(interfaceQName).getOperation(operationQName).getOutput();
		outputBean.setName(output.getName());
		outputBean.setDocumentation(output.getDocumentation().getContent());

		// Set Element (One Output have one Element)
		IElementDAO elementDAO = new ElementDAO();
		ElementBean elementBean = new ElementBean();
		elementBean = elementDAO.getElementByOutput(output);
		outputBean.setElement(elementBean);

		outputBean.setMessageName(output.getMessageName());
		outputBean.setOtherAttributes(output.getOtherAttributes());
		outputBean.setOtherElements(output.getOtherElements());

		//part ( one Input have  many Part)
		List<PartBean> partBeanList = new ArrayList<PartBean>();
		IPartDAO partDAO = new PartDAO();
		partBeanList = partDAO.getPartsByOuput(output);
		outputBean.setParts(partBeanList);

		logger.debug("end public OutputBean getOutputByInterface(Description desc, QName interfaceQName, QName operationQName)");
		return outputBean;
	}

	public OutputBean getOutputByInterfaceIndex(Description desc, int interfaceIndex, QName operationQName)
			throws XmlException {
		logger.debug("start public OutputBean getOutputByInterfaceIndex(Description desc, int interfaceIndex, QName operationQName)");
		OutputBean outputBean = new OutputBean();
		Output output = desc.getInterfaces().get(interfaceIndex).getOperation(operationQName).getOutput();
		outputBean.setName(output.getName());
		logger.debug("Ouput Name : " + output.getName());
		outputBean.setDocumentation(output.getDocumentation().getContent());

		// Set Element (One Output have one Element)
		IElementDAO elementDAO = new ElementDAO();
		ElementBean elementBean = new ElementBean();
		elementBean = elementDAO.getElementByOutput(output);
		outputBean.setElement(elementBean);

		outputBean.setMessageName(output.getMessageName());
		outputBean.setOtherAttributes(output.getOtherAttributes());
		outputBean.setOtherElements(output.getOtherElements());

		//part ( one Input have  many Part)
		List<PartBean> partBeanList = new ArrayList<PartBean>();
		IPartDAO partDAO = new PartDAO();
		partBeanList = partDAO.getPartsByOuput(output);
		outputBean.setParts(partBeanList);

		logger.debug("end public OutputBean getOutputByInterfaceIndex(Description desc, int interfaceIndex, QName operationQName)");
		return outputBean;
	}

	public OutputBean getOutputByBinding(Description desc, QName bindingQName, String operationName)
			throws XmlException {
		logger.debug("start public OutputBean getOutputByBinding(Description desc, QName bindingQName, String operationQName)");
		OutputBean outputBean = new OutputBean();
		BindingOutput bindingOutput = desc.getBinding(bindingQName).getBindingOperation(operationName).getOutput();
		outputBean.setName(bindingOutput.getName());
		logger.debug("Ouput Name : " + bindingOutput.getName());
		outputBean.setDocumentation(bindingOutput.getDocumentation().getContent());
		outputBean.setOtherAttributes(bindingOutput.getOtherAttributes());
		outputBean.setOtherElements(bindingOutput.getOtherElements());
		logger.debug("end public OutputBean getOutputByBinding(Description desc, QName bindingQName, String operationQName)");
		return outputBean;
	}

	public OutputBean getOutputByBindingOperation(Operation operation) throws XmlException {
		logger.debug("start public OutputBean getOutputByBindingOperation(Operation operation)");
		OutputBean outputBean = new OutputBean();
		Output output = operation.getOutput();
		outputBean.setName(output.getName());
		logger.debug("Ouput Name : " + output.getName());
		outputBean.setDocumentation(output.getDocumentation().getContent());
		outputBean.setMessageName(output.getMessageName());
		outputBean.setOtherAttributes(output.getOtherAttributes());
		outputBean.setOtherElements(output.getOtherElements());

		// Set Element (One Output have one Element)
		IElementDAO elementDAO = new ElementDAO();
		ElementBean elementBean = new ElementBean();
		elementBean = elementDAO.getElementByOutput(output);
		outputBean.setElement(elementBean);

		//Set Part (One Output have many Part)
		IPartDAO partDAO = new PartDAO();
		List<PartBean> partBeanList = new ArrayList<PartBean>();
		partBeanList = partDAO.getPartsByOuput(output);
		outputBean.setParts(partBeanList);
		logger.debug("end public OutputBean getOutputByBindingOperation(Operation operation)");
		return outputBean;
	}

	public OutputBean getOutputByOperation(Operation operation) throws XmlException {
		logger.debug("start public OutputBean getOutputByOperation(Operation operation)");
		OutputBean outputBean = new OutputBean();
		Output output = operation.getOutput();
		outputBean.setName(output.getName());
		logger.debug("Ouput Name : " + output.getName());
		outputBean.setDocumentation(output.getDocumentation().getContent());
		outputBean.setMessageName(output.getMessageName());
		outputBean.setOtherAttributes(output.getOtherAttributes());
		outputBean.setOtherElements(output.getOtherElements());

		// Set Element (One Output have one Element)
		IElementDAO elementDAO = new ElementDAO();
		ElementBean elementBean = new ElementBean();
		elementBean = elementDAO.getElementByOutput(output);
		outputBean.setElement(elementBean);

		//Set Part (One Output have many Part)
		IPartDAO partDAO = new PartDAO();
		List<PartBean> partBeanList = new ArrayList<PartBean>();
		partBeanList = partDAO.getPartsByOuput(output);
		outputBean.setParts(partBeanList);
		logger.debug("start public OutputBean getOutputByOperation(Operation operation)");
		return outputBean;
	}

}
