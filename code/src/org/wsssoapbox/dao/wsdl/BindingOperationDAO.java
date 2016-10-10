package org.wsssoapbox.dao.wsdl;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.api.Binding;
import org.ow2.easywsdl.wsdl.api.BindingOperation;
import org.ow2.easywsdl.wsdl.api.Description;
import org.ow2.easywsdl.wsdl.api.WSDLException;

import org.wsssoapbox.bean.model.wsdl.BindingFaultBean;
import org.wsssoapbox.bean.model.wsdl.BindingInputBean;
import org.wsssoapbox.bean.model.wsdl.BindingOperationBean;
import org.wsssoapbox.bean.model.wsdl.BindingOutputBean;
import org.wsssoapbox.bean.model.wsdl.OperationBean;

public class BindingOperationDAO implements IBindingOperationDAO {
	private List<BindingOperationBean> bindingOperations;

	public List<BindingOperationBean> getBindingOperations(Description desc, QName bindingQName) throws WSDLException,
			XmlException {
		bindingOperations = new ArrayList<BindingOperationBean>();

		List<BindingOperation> bindingOperationList = desc.getBinding(bindingQName).getBindingOperations();

		for (BindingOperation bo : bindingOperationList) {

			BindingOperationBean bob = new BindingOperationBean();

			bob.setName(bo.getQName().getLocalPart());
			bob.setNamespaceURI(bo.getQName().getNamespaceURI());
			bob.setQname(bo.getQName());
			bob.setDocumentation(bo.getDocumentation().getContent());
			bob.setHttpContentEncodingDefault(bo.getHttpContentEncodingDefault());
			bob.setHttpFaultSerialization(bo.getHttpFaultSerialization());
			bob.setHttpLocation(bo.getHttpLocation());
			bob.setHttpMethod(bo.getHttpMethod());
			bob.setHttpQueryParameterSeparator(bo.getHttpQueryParameterSeparator());
			bob.setHttpOutputSerialization(bo.getHttpOutputSerialization());
			bob.setMEP(bo.getMEP());

			// set operation to bean
			IOperationDAO operationDAO = new OperationDAO();
			String operationName = bo.getOperation().getQName().getLocalPart();
			OperationBean operationBean = new OperationBean();
			operationBean = operationDAO.getOperationByBinding(desc, bindingQName, operationName);

			bob.setOperation(operationBean);

			bob.setSoapAction(bo.getSoapAction());
			bob.setOtherAttributes(bo.getOtherAttributes());
			bob.setOtherElements(bo.getOtherElements());
			bob.setStyle(bo.getStyle().name());

			// for set BindingInput ,BindingOutput and BindingFault	to BindingOperation
			// assign BindingOperationName get form BindingOperationName
			String bindingOperationName = bo.getQName().getLocalPart();

			// add output to BindingOperation
			IBindingOutputDAO bindingOutputDAO = new BindingOutputDAO();
			BindingOutputBean bindingOutput = new BindingOutputBean();
			bindingOutput = bindingOutputDAO.getOutputByBindingName(desc, bindingQName, bindingOperationName);
			bob.setOutput(bindingOutput);

			// add input to BindingOperation
			IBindingInputDAO bindingInputDAO = new BindingInputDAO();
			BindingInputBean bindingInput = new BindingInputBean();
			bindingInput = bindingInputDAO.getInputBindingByBinding(desc, bindingQName, bindingOperationName);
			bob.setInput(bindingInput);

			// add bindingFaults to BindingOperation
			IBindingFaultDAO bindingFaultDAO = new BindingFaultDAO();
			List<BindingFaultBean> fualtBeanList = new ArrayList<BindingFaultBean>();
			fualtBeanList = bindingFaultDAO.getFaultsByBindingOperationFaultName(desc, bindingQName,
					bindingOperationName);
			bob.setFaults(fualtBeanList);

			bindingOperations.add(bob);
		}
		return bindingOperations;
	}

	public BindingOperationBean getOperationByBinding(Description desc, QName bindingQname, String bindingOperationName)
			throws WSDLException, XmlException {
		BindingOperationBean bob = new BindingOperationBean();

		BindingOperation bo = desc.getBinding(bindingQname).getBindingOperation(bindingOperationName);

		bob.setName(bo.getQName().getLocalPart());
		bob.setNamespaceURI(bo.getQName().getNamespaceURI());
		bob.setQname(bo.getQName());
		bob.setDocumentation(bo.getDocumentation().getContent());
		bob.setHttpContentEncodingDefault(bo.getHttpContentEncodingDefault());
		bob.setHttpFaultSerialization(bo.getHttpFaultSerialization());
		bob.setHttpLocation(bo.getHttpLocation());
		bob.setHttpMethod(bo.getHttpMethod());
		bob.setHttpQueryParameterSeparator(bo.getHttpQueryParameterSeparator());
		bob.setHttpOutputSerialization(bo.getHttpOutputSerialization());
		bob.setMEP(bo.getMEP());

		// set operation to bean
		IOperationDAO operationDAO = new OperationDAO();
		OperationBean operationBean = new OperationBean();
		operationBean = operationDAO.getOperationByBinding(desc, bindingQname, bindingOperationName);
		bob.setOperation(operationBean);

		bob.setSoapAction(bo.getSoapAction());
		bob.setOtherAttributes(bo.getOtherAttributes());
		bob.setOtherElements(bo.getOtherElements());

		// for set BindingInput ,BindingOutput and BindingFault	to BindingOperation

		// add output to BindingOperation
		IBindingOutputDAO bindingOutputDAO = new BindingOutputDAO();
		BindingOutputBean bindingOutput = new BindingOutputBean();
		bindingOutput = bindingOutputDAO.getOutputByBindingName(desc, bindingQname, bindingOperationName);
		bob.setOutput(bindingOutput);

		// add input to BindingOperation
		IBindingInputDAO bindingInputDAO = new BindingInputDAO();
		BindingInputBean bindingInput = new BindingInputBean();
		bindingInput = bindingInputDAO.getInputBindingByBinding(desc, bindingQname, bindingOperationName);
		bob.setInput(bindingInput);

		// add bindingFaults to BindingOperation
		IBindingFaultDAO bindingFaultDAO = new BindingFaultDAO();
		List<BindingFaultBean> fualtBeanList = new ArrayList<BindingFaultBean>();
		fualtBeanList = bindingFaultDAO.getFaultsByBindingOperationFaultName(desc, bindingQname, bindingOperationName);
		bob.setFaults(fualtBeanList);

		bob.setStyle(bo.getStyle().name());

		return bob;
	}

	public List<BindingOperationBean> getBindingOperationsByBinding(Binding binding) throws XmlException {
		bindingOperations = new ArrayList<BindingOperationBean>();

		List<BindingOperation> bindingOperationList = binding.getBindingOperations();

		for (BindingOperation bo : bindingOperationList) {

			BindingOperationBean bob = new BindingOperationBean();

			bob.setName(bo.getQName().getLocalPart());
			bob.setNamespaceURI(bo.getQName().getNamespaceURI());
			bob.setQname(bo.getQName());
			bob.setDocumentation(bo.getDocumentation().getContent());
			bob.setHttpContentEncodingDefault(bo.getHttpContentEncodingDefault());
			bob.setHttpFaultSerialization(bo.getHttpFaultSerialization());
			bob.setHttpLocation(bo.getHttpLocation());
			bob.setHttpMethod(bo.getHttpMethod());
			bob.setHttpQueryParameterSeparator(bo.getHttpQueryParameterSeparator());
			bob.setHttpOutputSerialization(bo.getHttpOutputSerialization());
			bob.setMEP(bo.getMEP());

			// set operation (one BindingOperation have one Opertaion
			IOperationDAO operationDAO = new OperationDAO();
			OperationBean operationBean = new OperationBean();
			operationBean = operationDAO.getOperationByBindingOperation(bo);
			bob.setOperation(operationBean);

			bob.setSoapAction(bo.getSoapAction());
			bob.setOtherAttributes(bo.getOtherAttributes());
			bob.setOtherElements(bo.getOtherElements());
			if (bo.getStyle() != null)
				bob.setStyle(bo.getStyle().name());

			// for set BindingInput ,BindingOutput and BindingFault	to BindingOperation
			// assign BindingOperationName get form BindingOperationName

			// add output to BindingOperation
			IBindingOutputDAO bindingOutputDAO = new BindingOutputDAO();
			BindingOutputBean bindingOutput = new BindingOutputBean();
			bindingOutput = bindingOutputDAO.getOutputByBindingOperation(bo);
			bob.setOutput(bindingOutput);

			// add input to BindingOperation
			IBindingInputDAO bindingInputDAO = new BindingInputDAO();
			BindingInputBean bindingInput = new BindingInputBean();
			bindingInput = bindingInputDAO.getInputByBindingOperation(bo);
			bob.setInput(bindingInput);

			// add bindingFaults to BindingOperation
			IBindingFaultDAO bindingFaultDAO = new BindingFaultDAO();
			List<BindingFaultBean> fualtBeanList = new ArrayList<BindingFaultBean>();
			fualtBeanList = bindingFaultDAO.getFaultsByBindingOperation(bo);
			bob.setFaults(fualtBeanList);

			bindingOperations.add(bob);
		}
		return bindingOperations;
	}



}
