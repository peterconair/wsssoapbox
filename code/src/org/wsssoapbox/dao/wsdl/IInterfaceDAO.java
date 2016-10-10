package org.wsssoapbox.dao.wsdl;

import java.util.List;

import javax.xml.namespace.QName;

import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.api.Binding;
import org.ow2.easywsdl.wsdl.api.Description;
import org.ow2.easywsdl.wsdl.api.Service;
import org.ow2.easywsdl.wsdl.api.WSDLException;
import org.wsssoapbox.bean.model.wsdl.InterfaceBean;

public interface IInterfaceDAO {
	public List<InterfaceBean> getInterfaces(Description desc)
			throws WSDLException, XmlException;

	public InterfaceBean getInterfaceByName(Description desc, QName interfaceQName)
			throws WSDLException,XmlException;

	public InterfaceBean getInterfaceByIndex(Description desc, int index)
			throws WSDLException, XmlException;
	
	public InterfaceBean getInterfaceByBinding(Binding binding) throws XmlException;
	public InterfaceBean getInterfaceByService (Service service) throws XmlException;



	public InterfaceBean getInterface(Description desc,QName interfaceQName) throws XmlException ;
}
