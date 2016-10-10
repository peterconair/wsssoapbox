package org.wsssoapbox.dao.soap;

import javax.xml.namespace.QName;

import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.api.Description;
import org.wsssoapbox.bean.model.wsdl.SoapBindingBean;

public interface ISoapModelDAO {

   public SoapBindingBean createSoapBindingBeanDoc(Description desc, QName bindingQName, QName interfaceQName,
           QName operationQName) throws XmlException;

   // WSDL 1.1 
   public SoapBindingBean createSoapBindingBeanRPC1_1(Description desc, QName interfaceQName, QName operationQName)
           throws XmlException;
   
   public SoapBindingBean createSoapBindingBeanRPC(Description desc, QName bindingQName, QName interfaceQName, QName operationQName) throws XmlException;

}
