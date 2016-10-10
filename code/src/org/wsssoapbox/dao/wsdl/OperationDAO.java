package org.wsssoapbox.dao.wsdl;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.api.BindingOperation;
import org.ow2.easywsdl.wsdl.api.Description;
import org.ow2.easywsdl.wsdl.api.InterfaceType;
import org.ow2.easywsdl.wsdl.api.Operation;
import org.ow2.easywsdl.wsdl.api.WSDLException;
import org.wsssoapbox.bean.model.wsdl.FaultBean;
import org.wsssoapbox.bean.model.wsdl.InputBean;
import org.wsssoapbox.bean.model.wsdl.OperationBean;
import org.wsssoapbox.bean.model.wsdl.OutputBean;

public class OperationDAO implements IOperationDAO {
	private OperationBean operationBean;
	private List<OperationBean> operationBeans;


	public List<OperationBean> getOperationsInterfaceByName(Description desc, QName interfaceQName) throws XmlException {
		operationBeans = new ArrayList<OperationBean>();
		List<Operation> operations = desc.getInterface(interfaceQName).getOperations();
		for (Operation o : operations) {
			operationBean = new OperationBean();

			operationBean.setName(o.getQName().getLocalPart());
			operationBean.setQname(o.getQName());
			operationBean.setDocumentation(o.getDocumentation().getContent());
			operationBean.setPattern(o.getPattern());
			operationBean.setSignature(o.getSignature());
			operationBean.setParameterOrdering(o.getParameterOrdering());

			// for set Input ,Output and Fault
			QName operationQName = o.getQName();

			// add output to operation
			IOutputDAO outputDAO = new OutputDAO();
			OutputBean outputBean = new OutputBean();
			outputBean = outputDAO.getOutputByInterface(desc, interfaceQName, operationQName);
			operationBean.setOutput(outputBean);

			// add input to operation
			IInputDAO inputDAO = new InputDAO();
			InputBean inputBean = new InputBean();
			inputBean = inputDAO.getInputByInterface(desc, interfaceQName, operationQName);
			operationBean.setInput(inputBean);

			// add faults to operation
			IFaultDAO faultDAO = new FaultDAO();
			List<FaultBean> fualtBeanList = new ArrayList<FaultBean>();
			fualtBeanList = faultDAO.getFaultsByInterfaceOperationFaultName(desc, interfaceQName, operationQName);
			operationBean.setFaults(fualtBeanList);

			try {
				operationBean.setOtherAttributes(o.getOtherAttributes());
			} catch (XmlException e) {
				e.printStackTrace();
			}
			operationBeans.add(operationBean);
		}
		return operationBeans;
	}

	public OperationBean getOperationInterfaceByName(Description desc, QName interfaceQName, QName qname)
			throws XmlException {
		operationBean = new OperationBean();
		Operation o = desc.getInterface(interfaceQName).getOperation(qname);
		operationBean.setName(o.getQName().getLocalPart());
		operationBean.setQname(o.getQName());
		operationBean.setDocumentation(o.getDocumentation().getContent());
		operationBean.setPattern(o.getPattern());
		operationBean.setSignature(o.getSignature());
		operationBean.setParameterOrdering(o.getParameterOrdering());

		// for set Input ,Output and Fault
		QName operationQName = o.getQName();

		// add output to operation
		IOutputDAO outputDAO = new OutputDAO();
		OutputBean outputBean = new OutputBean();
		outputBean = outputDAO.getOutputByInterface(desc, interfaceQName, operationQName);
		operationBean.setOutput(outputBean);

		// add input to operation
		IInputDAO inputDAO = new InputDAO();
		InputBean inputBean = new InputBean();
		inputBean = inputDAO.getInputByInterface(desc, interfaceQName, operationQName);
		operationBean.setInput(inputBean);

		// add faults to operation
		IFaultDAO faultDAO = new FaultDAO();
		List<FaultBean> fualtBeanList = new ArrayList<FaultBean>();
		fualtBeanList = faultDAO.getFaultsByInterfaceOperationFaultName(desc, interfaceQName, operationQName);
		operationBean.setFaults(fualtBeanList);

		return operationBean;
	}

	public OperationBean getOperationInterfaceByNameIndex(Description desc, QName interfaceQName, int index)
			throws XmlException {
		operationBean = new OperationBean();
		Operation o = desc.getInterface(interfaceQName).getOperations().get(index);

		operationBean.setName(o.getQName().getLocalPart());
		operationBean.setQname(o.getQName());
		operationBean.setDocumentation(o.getDocumentation().getContent());
		operationBean.setPattern(o.getPattern());
		operationBean.setSignature(o.getSignature());
		operationBean.setParameterOrdering(o.getParameterOrdering());

		// for set Input ,Output and Fault
		QName operationQName = o.getQName();

		// add output to operation
		IOutputDAO outputDAO = new OutputDAO();
		OutputBean outputBean = new OutputBean();
		outputBean = outputDAO.getOutputByInterface(desc, interfaceQName, operationQName);
		operationBean.setOutput(outputBean);

		// add input to operation
		IInputDAO inputDAO = new InputDAO();
		InputBean inputBean = new InputBean();
		inputBean = inputDAO.getInputByInterface(desc, interfaceQName, operationQName);
		operationBean.setInput(inputBean);

		// add faults to operation
		IFaultDAO faultDAO = new FaultDAO();
		List<FaultBean> fualtBeanList = new ArrayList<FaultBean>();
		fualtBeanList = faultDAO.getFaultsByInterfaceOperationFaultName(desc, interfaceQName, operationQName);
		operationBean.setFaults(fualtBeanList);

		return operationBean;
	}

	public List<OperationBean> getOperationsInterfaceByIndex(Description desc, int interfaceIndex) throws XmlException {
		operationBeans = new ArrayList<OperationBean>();
		List<Operation> operations = desc.getInterfaces().get(interfaceIndex).getOperations();
		for (Operation o : operations) {

			operationBean = new OperationBean();
			operationBean.setName(o.getQName().getLocalPart());
			operationBean.setQname(o.getQName());
			operationBean.setDocumentation(o.getDocumentation().getContent());
			operationBean.setPattern(o.getPattern());
			operationBean.setSignature(o.getSignature());
			operationBean.setParameterOrdering(o.getParameterOrdering());

			// for set Input ,Output and Fault
			QName operationQName = o.getQName();

			// add output to operation
			IOutputDAO outputDAO = new OutputDAO();
			OutputBean outputBean = new OutputBean();
			outputBean = outputDAO.getOutputByInterfaceIndex(desc, interfaceIndex, operationQName);
			operationBean.setOutput(outputBean);

			// add input to operation
			IInputDAO inputDAO = new InputDAO();
			InputBean inputBean = new InputBean();
			inputBean = inputDAO.getInputByInterfaceIndex(desc, interfaceIndex, operationQName);
			operationBean.setInput(inputBean);

			// add faults to operation
			IFaultDAO faultDAO = new FaultDAO();
			List<FaultBean> fualtBeanList = new ArrayList<FaultBean>();
			fualtBeanList = faultDAO.getFaultsByInterfaceIndexOperationFaultName(desc, interfaceIndex, operationQName);
			operationBean.setFaults(fualtBeanList);

			try {
				operationBean.setOtherAttributes(o.getOtherAttributes());
			} catch (XmlException e) {
				e.printStackTrace();
			}
			operationBeans.add(operationBean);
		}
		return operationBeans;
	}

	/**
	 * Convert Operation to BindingOpertion
	 * 
	 * @throws XmlException
	 */
	public OperationBean getOperationByBinding(Description desc, QName bindingQname, String operationQName)
			throws XmlException {

		operationBean = new OperationBean();
		//
		BindingOperation bo = desc.getBinding(bindingQname).getBindingOperation(operationQName);

		// transform OperationBinding to Operation (OperationBindingBean to
		// OperationBean )
		operationBean.setName(bo.getQName().getLocalPart());
		operationBean.setQname(bo.getQName());
		operationBean.setDocumentation(bo.getDocumentation().getContent());
		operationBean.setPattern("unknow");
		operationBean.setSignature("unknow");
		operationBean.setParameterOrdering(null);

		// add output to operation
		IOutputDAO outputDAO = new OutputDAO();
		OutputBean outputBean = new OutputBean();
		// Convert BindingOutput to Output 
		outputBean = outputDAO.getOutputByBinding(desc, bindingQname, operationQName);
		operationBean.setOutput(outputBean);

		// add input to operation
		IInputDAO inputDAO = new InputDAO();
		InputBean inputBean = new InputBean();
		inputBean = inputDAO.getInputByBinding(desc, bindingQname, operationQName);
		operationBean.setInput(inputBean);

		// add faults to operation
		IFaultDAO faultDAO = new FaultDAO();
		List<FaultBean> fualtBeanList = new ArrayList<FaultBean>();
		fualtBeanList = faultDAO.getFaultsByBinding(desc, bindingQname, operationQName);
		operationBean.setFaults(fualtBeanList);
		
		return operationBean;

	}

	public OperationBean getOperationByBindingOperation(BindingOperation bindingOperation) throws XmlException {
		operationBean = new OperationBean();
		Operation o = bindingOperation.getOperation();
		operationBean.setName(o.getQName().getLocalPart());
		operationBean.setQname(o.getQName());
		operationBean.setDocumentation(o.getDocumentation().getContent());
		operationBean.setPattern(o.getPattern());
		operationBean.setSignature(o.getSignature());
		operationBean.setParameterOrdering(o.getParameterOrdering());

		// for set Input ,Output and Fault
		QName operationQName = o.getQName();

		// add output to operation
		IOutputDAO outputDAO = new OutputDAO();
		OutputBean outputBean = new OutputBean();
		outputBean = outputDAO.getOutputByBindingOperation(o);
		operationBean.setOutput(outputBean);

		// add input to operation
		IInputDAO inputDAO = new InputDAO();
		InputBean inputBean = new InputBean();
		inputBean = inputDAO.getInputByBindingOperation(o);
		operationBean.setInput(inputBean);

		// add faults to operation
		IFaultDAO faultDAO = new FaultDAO();
		List<FaultBean> fualtBeanList = new ArrayList<FaultBean>();
		fualtBeanList = faultDAO.getFaultsByBindingOperation(o);
		operationBean.setFaults(fualtBeanList);

		return operationBean;
	}

	public List<OperationBean> getOperationsByInterface(InterfaceType interfaceType) throws XmlException {
		operationBeans = new ArrayList<OperationBean>();
		List<Operation> operations = interfaceType.getOperations();
		for (Operation o : operations) {
			operationBean = new OperationBean();

			operationBean.setName(o.getQName().getLocalPart());
			operationBean.setQname(o.getQName());
			operationBean.setDocumentation(o.getDocumentation().getContent());
			operationBean.setPattern(o.getPattern());
			operationBean.setSignature(o.getSignature());
			operationBean.setParameterOrdering(o.getParameterOrdering());

			// for set Input ,Output and Fault
			QName operationQName = o.getQName();

			// add output to operation
			IOutputDAO outputDAO = new OutputDAO();
			OutputBean outputBean = new OutputBean();
			outputBean = outputDAO.getOutputByOperation(o);
			operationBean.setOutput(outputBean);

			// add input to operation
			IInputDAO inputDAO = new InputDAO();
			InputBean inputBean = new InputBean();
			inputBean = inputDAO.getInputByOperation(o);
			operationBean.setInput(inputBean);

			// add faults to operation
			IFaultDAO faultDAO = new FaultDAO();
			List<FaultBean> fualtBeanList = new ArrayList<FaultBean>();
			fualtBeanList = faultDAO.getFaultsByOperation(o);
			operationBean.setFaults(fualtBeanList);

			if (o.getOtherAttributes() != null)
				operationBean.setOtherAttributes(o.getOtherAttributes());
			
			operationBeans.add(operationBean);
		}
		return operationBeans;
	}

	

}
