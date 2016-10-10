package org.wsssoapbox.dao.wsdl;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.api.BindingFault;
import org.ow2.easywsdl.wsdl.api.Description;
import org.ow2.easywsdl.wsdl.api.Fault;
import org.ow2.easywsdl.wsdl.api.Operation;
import org.wsssoapbox.bean.model.wsdl.ElementBean;
import org.wsssoapbox.bean.model.wsdl.FaultBean;
import org.wsssoapbox.bean.model.wsdl.PartBean;

public class FaultDAO implements IFaultDAO {
	private static final Log logger = LogFactory.getLog(WSDLDAO.class);

	public List<FaultBean> getFaultsByInterfaceOperationFaultName(Description desc, QName interfaceQName,
			QName operationQName) throws XmlException {
		logger.debug("start public List<FaultBean> getFaultsByInterfaceOperationFaultName(Description desc, QName interfaceQName, QName operationQName)");
		List<FaultBean> faultBeanList = new ArrayList<FaultBean>();
		List<Fault> faultList = desc.getInterface(interfaceQName).getOperation(operationQName).getFaults();
		for (Fault fault : faultList) {
			FaultBean faultBean = new FaultBean();
			faultBean.setName(fault.getName());
			faultBean.setDocumentation(fault.getDocumentation().getContent());
			faultBean.setOtherAttributes(fault.getOtherAttributes());
			faultBean.setOtherElements(fault.getOtherElements());
			faultBean.setElement(fault.getElement());
			faultBean.setMessageName(fault.getMessageName());
			faultBean.setParts(fault.getParts());
			faultBeanList.add(faultBean);
		}
		logger.debug("end public List<FaultBean> getFaultsByInterfaceOperationFaultName(Description desc, QName interfaceQName, QName operationQName)");
		return faultBeanList;
	}

	public List<FaultBean> getFaultsByInterfaceIndexOperationFaultName(Description desc, int interfaceIndex,
			QName operationQName) throws XmlException {
		logger.debug("start public List<FaultBean> getFaultsByInterfaceIndexOperationFaultName(Description desc, int interfaceIndex, QName operationQName)");
		List<FaultBean> faultBeanList = new ArrayList<FaultBean>();
		List<Fault> faultList = desc.getInterfaces().get(interfaceIndex).getOperation(operationQName).getFaults();
		for (Fault fault : faultList) {
			FaultBean faultBean = new FaultBean();
			faultBean.setName(fault.getName());
			faultBean.setDocumentation(fault.getDocumentation().getContent());
			faultBean.setOtherAttributes(fault.getOtherAttributes());
			faultBean.setOtherElements(fault.getOtherElements());
			faultBean.setElement(fault.getElement());
			faultBean.setMessageName(fault.getMessageName());
			faultBean.setParts(fault.getParts());
			faultBeanList.add(faultBean);
		}
		logger.debug("end 	public List<FaultBean> getFaultsByInterfaceIndexOperationFaultName(Description desc, int interfaceIndex, QName operationQName)");
		return faultBeanList;
	}

	public FaultBean getFaultByInterfaceOperationElementQName(Description desc, QName interfaceQName,
			QName operationQName, QName elementQName) throws XmlException {
		logger.debug("start public FaultBean getFaultByInterfaceOperationElementQName(Description desc,QName interfaceQName, QName operationQName, QName elementQName)");
		Fault fault = desc.getInterface(interfaceQName).getOperation(operationQName)
				.getFaultByElementName(elementQName);
		FaultBean faultBean = new FaultBean();
		faultBean.setName(fault.getName());
		faultBean.setDocumentation(fault.getDocumentation().getContent());
		faultBean.setOtherAttributes(fault.getOtherAttributes());
		faultBean.setOtherElements(fault.getOtherElements());
		faultBean.setElement(fault.getElement());
		faultBean.setMessageName(fault.getMessageName());
		faultBean.setParts(fault.getParts());
		logger.debug("end public FaultBean getFaultByInterfaceOperationElementQName(Description desc,QName interfaceQName, QName operationQName, QName elementQName)");
		return faultBean;
	}

	public FaultBean getFaultByInterfaceOperationFaultName(Description desc, QName interfaceQName,
			QName operationQName, String faultName) throws XmlException {
		logger.debug("start public FaultBean getFaultByInterfaceOperationFaultName(Description desc,QName interfaceQName, QName operationQName, String faultName)");
		Fault fault = desc.getInterface(interfaceQName).getOperation(operationQName).getFault(faultName);
		FaultBean faultBean = new FaultBean();
		faultBean.setName(fault.getName());
		faultBean.setDocumentation(fault.getDocumentation().getContent());
		faultBean.setOtherAttributes(fault.getOtherAttributes());
		faultBean.setOtherElements(fault.getOtherElements());
		faultBean.setElement(fault.getElement());
		faultBean.setMessageName(fault.getMessageName());
		faultBean.setParts(fault.getParts());
		logger.debug("start public FaultBean getFaultByInterfaceOperationFaultName(Description desc,QName interfaceQName, QName operationQName, String faultName)");
		return faultBean;
	}

	public List<FaultBean> getFaultsByBinding(Description desc, QName bindingQName, String operationName)
			throws XmlException {
		logger.debug("start public List<FaultBean> getFaultsByBinding(Description desc,QName bindingQName, String operationName)");
		List<BindingFault> bindingFaultList = desc.getBinding(bindingQName).getBindingOperation(operationName)
				.getFaults();
		List<FaultBean> faultBeanList = new ArrayList<FaultBean>();
		FaultBean faultBean = new FaultBean();
		for (BindingFault faultBinding : bindingFaultList) {
			faultBean = new FaultBean();
			faultBean.setName(faultBinding.getName());
			faultBean.setDocumentation(faultBinding.getDocumentation().getContent());
			faultBean.setOtherAttributes(faultBinding.getOtherAttributes());
			faultBean.setOtherElements(faultBinding.getOtherElements());
			faultBeanList.add(faultBean);
		}
		logger.debug("start public List<FaultBean> getFaultsByBinding(Description desc,QName bindingQName, String operationName)");
		return faultBeanList;
	}

	public List<FaultBean> getFaultsByBindingOperation(Operation operation) throws XmlException {
		logger.debug("start public List<FaultBean> getFaultsByBindingOperation(Operation operation)");
		List<FaultBean> faultBeanList = new ArrayList<FaultBean>();
		List<Fault> faultList = operation.getFaults();
		for (Fault fault : faultList) {
			FaultBean faultBean = new FaultBean();
			faultBean.setName(fault.getName());
			faultBean.setDocumentation(fault.getDocumentation().getContent());
			faultBean.setOtherAttributes(fault.getOtherAttributes());
			faultBean.setOtherElements(fault.getOtherElements());
			faultBean.setElement(fault.getElement());
			faultBean.setMessageName(fault.getMessageName());
			faultBean.setParts(fault.getParts());
			faultBeanList.add(faultBean);
		}
		logger.debug("end public List<FaultBean> getFaultsByBindingOperation(Operation operation)");
		return faultBeanList;
	}

	public List<FaultBean> getFaultsByOperation(Operation operation) throws XmlException {
		logger.debug("start public List<FaultBean> getFaultsByOperation(Operation operation)");
		List<FaultBean> faultBeanList = new ArrayList<FaultBean>();
		List<Fault> faultList = operation.getFaults();
		for (Fault fault : faultList) {
			FaultBean faultBean = new FaultBean();
			faultBean.setName(fault.getName());
			faultBean.setDocumentation(fault.getDocumentation().getContent());
			faultBean.setOtherAttributes(fault.getOtherAttributes());
			faultBean.setOtherElements(fault.getOtherElements());
			faultBean.setMessageName(fault.getMessageName());

			//Set Element (One Fualt have many Element)
			IElementDAO elementDAO = new ElementDAO();
			List<ElementBean> elementBeanList = new ArrayList<ElementBean>();
			elementBeanList = elementDAO.getElementByFault(fault);
			faultBean.setElement(elementBeanList);

			IPartDAO partDAO = new PartDAO();
			List<PartBean> partBeanList = new ArrayList<PartBean>();
			partBeanList = partDAO.getPartsByFault(fault);
			faultBean.setParts(partBeanList);

			faultBeanList.add(faultBean);
		}
		logger.debug("end public List<FaultBean> getFaultsByOperation(Operation operation) ");
		return faultBeanList;
	}

}
