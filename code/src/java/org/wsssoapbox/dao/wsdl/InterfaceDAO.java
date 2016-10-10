package org.wsssoapbox.dao.wsdl;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.api.Binding;
import org.ow2.easywsdl.wsdl.api.Description;
import org.ow2.easywsdl.wsdl.api.InterfaceType;
import org.ow2.easywsdl.wsdl.api.Service;
import org.ow2.easywsdl.wsdl.api.WSDLException;
import org.wsssoapbox.bean.model.wsdl.InterfaceBean;
import org.wsssoapbox.bean.model.wsdl.OperationBean;

public class InterfaceDAO implements IInterfaceDAO {
	private InterfaceBean interfaceBean;

	public List<InterfaceBean> getInterfaces(Description desc) throws WSDLException, XmlException {
		List<InterfaceBean> interfaceBeanList = new ArrayList<InterfaceBean>();
		List<InterfaceType> interfaceTypeList = desc.getInterfaces();

		for (InterfaceType f : interfaceTypeList) {
			QName qname = f.getQName();
			interfaceBean = new InterfaceBean();

			interfaceBean.setName(f.getQName().getLocalPart());
			interfaceBean.setNamespaceURI(f.getQName().getNamespaceURI());
			interfaceBean.setQname(f.getQName());
			interfaceBean.setDocumentation(f.getDocumentation().getContent());
			interfaceBean.setOhterElements(f.getOtherElements());
			interfaceBean.setOtherAttributes(f.getOtherAttributes());

			//Set Opertion to Interface (One Interface have many Operation)
			IOperationDAO operationDAO = new OperationDAO();
			List<OperationBean> operationBeanList = operationDAO.getOperationsByInterface(f);
			interfaceBean.setOperations(operationBeanList);

			interfaceBeanList.add(interfaceBean);
		}
		return interfaceBeanList;
	}

	public InterfaceBean getInterfaceByName(Description desc, QName interfaceQName) throws WSDLException, XmlException {
		InterfaceType f = desc.getInterface(interfaceQName);
		interfaceBean = new InterfaceBean();

		interfaceBean.setName(f.getQName().getLocalPart());
		interfaceBean.setNamespaceURI(f.getQName().getNamespaceURI());
		interfaceBean.setQname(f.getQName());
		interfaceBean.setDocumentation(f.getDocumentation().getContent());
		interfaceBean.setOhterElements(f.getOtherElements());
		interfaceBean.setOtherAttributes(f.getOtherAttributes());

		//Set Opertion to Interface (One Interface have many Operation)
		IOperationDAO operationDAO = new OperationDAO();
		List<OperationBean> operationBeanList = operationDAO.getOperationsByInterface(f);
		interfaceBean.setOperations(operationBeanList);

		return interfaceBean;
	}

	public InterfaceBean getInterfaceByIndex(Description desc, int index) throws WSDLException, XmlException {
		InterfaceType f = desc.getInterfaces().get(index);
		interfaceBean = new InterfaceBean();

		interfaceBean.setName(f.getQName().getLocalPart());
		interfaceBean.setNamespaceURI(f.getQName().getNamespaceURI());
		interfaceBean.setQname(f.getQName());
		interfaceBean.setDocumentation(f.getDocumentation().getContent());
		interfaceBean.setOhterElements(f.getOtherElements());
		interfaceBean.setOtherAttributes(f.getOtherAttributes());

		//Set Opertion to Interface (One Interface have many Operation)
		IOperationDAO operationDAO = new OperationDAO();
		List<OperationBean> operationBeanList = operationDAO.getOperationsByInterface(f);
		interfaceBean.setOperations(operationBeanList);
		return interfaceBean;
	}

	public InterfaceBean getInterfaceByBinding(Binding binding) throws XmlException {
		InterfaceType f = binding.getInterface();
		interfaceBean = new InterfaceBean();

		interfaceBean.setName(f.getQName().getLocalPart());
		interfaceBean.setNamespaceURI(f.getQName().getNamespaceURI());
		interfaceBean.setQname(f.getQName());
		interfaceBean.setDocumentation(f.getDocumentation().getContent());
		interfaceBean.setOhterElements(f.getOtherElements());
		interfaceBean.setOtherAttributes(f.getOtherAttributes());

		//Set Opertion to Interface (One Interface have many Operation)
		IOperationDAO operationDAO = new OperationDAO();
		List<OperationBean> operationBeanList = operationDAO.getOperationsByInterface(f);
		interfaceBean.setOperations(operationBeanList);

		return interfaceBean;
	}

   @Override
	public InterfaceBean getInterfaceByService(Service service) throws XmlException {

		InterfaceType f = service.getInterface();
		interfaceBean = new InterfaceBean();

		interfaceBean.setName(f.getQName().getLocalPart());
		interfaceBean.setNamespaceURI(f.getQName().getNamespaceURI());
		interfaceBean.setQname(f.getQName());
		interfaceBean.setDocumentation(f.getDocumentation().getContent());
		interfaceBean.setOhterElements(f.getOtherElements());
		interfaceBean.setOtherAttributes(f.getOtherAttributes());

		//Set Opertion to Interface (One Interface have many Operation)
		IOperationDAO operationDAO = new OperationDAO();
		List<OperationBean> operationBeanList = operationDAO.getOperationsByInterface(f);
		interfaceBean.setOperations(operationBeanList);

		return interfaceBean;
	}

	public InterfaceBean getInterface(Description desc, QName interfaceQName) throws XmlException {
		InterfaceType f = desc.getInterface(interfaceQName);
		interfaceBean = new InterfaceBean();

		interfaceBean.setName(f.getQName().getLocalPart());
		interfaceBean.setNamespaceURI(f.getQName().getNamespaceURI());
		interfaceBean.setQname(f.getQName());
		interfaceBean.setDocumentation(f.getDocumentation().getContent());
		interfaceBean.setOhterElements(f.getOtherElements());
		interfaceBean.setOtherAttributes(f.getOtherAttributes());

		//Set Opertion to Interface (One Interface have many Operation)
		IOperationDAO operationDAO = new OperationDAO();
		List<OperationBean> operationBeanList = operationDAO.getOperationsByInterface(f);
		interfaceBean.setOperations(operationBeanList);

		return interfaceBean;
	}


}
