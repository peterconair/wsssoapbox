package org.wsssoapbox.dao.soap;

import javax.xml.namespace.QName;

import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.api.Description;
import org.wsssoapbox.bean.model.soap.SoapBindingBean;

public interface ISoapModelDAO {

	public SoapBindingBean createSoapBindingBeanDoc(Description desc, QName bindingQName, QName interfaceQName,
			QName operationQName) throws XmlException ;
	public SoapBindingBean getSoapRequestRPC(Description desc, QName interfaceQName, QName operationQName)
	throws XmlException ;
}
