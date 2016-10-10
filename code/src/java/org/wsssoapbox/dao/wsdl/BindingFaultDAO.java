package org.wsssoapbox.dao.wsdl;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.api.BindingFault;
import org.ow2.easywsdl.wsdl.api.BindingOperation;
import org.ow2.easywsdl.wsdl.api.Description;
import org.wsssoapbox.bean.model.wsdl.BindingFaultBean;

public class BindingFaultDAO implements IBindingFaultDAO{

	public List<BindingFaultBean> getFaultsByBindingOperationFaultName(Description desc, QName bindingQName,
			String bindingOperationName) throws XmlException {
		 List <BindingFaultBean> bindingFaultBeanList = new ArrayList<BindingFaultBean>();
			BindingFaultBean bindingFaultBean = new  BindingFaultBean();
			List <BindingFault> bindingFaultList =   desc.getBinding(bindingQName).getBindingOperation(bindingOperationName).getFaults();
			for (BindingFault bindingFault :bindingFaultList){
				bindingFaultBean.setName(bindingFault.getName());
				bindingFaultBean.setRef(bindingFault.getRef());
				bindingFaultBean.setDocumentation(bindingFault.getDocumentation().getContent());
				bindingFaultBean.setHTTPBinding4Wsdl11(bindingFault.getHTTPBinding4Wsdl11());
				bindingFaultBean.setHTTPBinding4Wsdl20(bindingFault.getHTTPBinding4Wsdl20());
				bindingFaultBean.setHttpContentEncoding(bindingFault.getHttpContentEncoding());
				bindingFaultBean.setMIMEBinding4Wsdl11(bindingFault.getMIMEBinding4Wsdl11());
				bindingFaultBean.setOtherAttributes(bindingFault.getOtherAttributes());
				bindingFaultBean.setOtherElements(bindingFault.getOtherElements());
				bindingFaultBean.setSOAP11Binding4Wsdl11(bindingFault.getSOAP11Binding4Wsdl11());
				bindingFaultBean.setSOAP12Binding4Wsdl11(bindingFault.getSOAP12Binding4Wsdl11());
				bindingFaultBean.setSOAP12Binding4Wsdl20(bindingFault.getSOAP12Binding4Wsdl20());
				bindingFaultBeanList.add(bindingFaultBean);
			}		
			return bindingFaultBeanList;	
	}

	public BindingFaultBean getFaultByBindingOperationFaultName(Description desc, QName bindingQName,
			String bindingOperationName, String faultName) throws XmlException {
		BindingFaultBean bindingFaultBean = new  BindingFaultBean();
		BindingFault bindingFault =  desc.getBinding(bindingQName).getBindingOperation(bindingOperationName).getFault(faultName);
		bindingFaultBean.setName(bindingFault.getName());
		bindingFaultBean.setRef(bindingFault.getRef());
		bindingFaultBean.setDocumentation(bindingFault.getDocumentation().getContent());
		bindingFaultBean.setHTTPBinding4Wsdl11(bindingFault.getHTTPBinding4Wsdl11());
		bindingFaultBean.setHTTPBinding4Wsdl20(bindingFault.getHTTPBinding4Wsdl20());
		bindingFaultBean.setHttpContentEncoding(bindingFault.getHttpContentEncoding());
		bindingFaultBean.setMIMEBinding4Wsdl11(bindingFault.getMIMEBinding4Wsdl11());
		bindingFaultBean.setOtherAttributes(bindingFault.getOtherAttributes());
		bindingFaultBean.setOtherElements(bindingFault.getOtherElements());
		bindingFaultBean.setSOAP11Binding4Wsdl11(bindingFault.getSOAP11Binding4Wsdl11());
		bindingFaultBean.setSOAP12Binding4Wsdl11(bindingFault.getSOAP12Binding4Wsdl11());
		bindingFaultBean.setSOAP12Binding4Wsdl20(bindingFault.getSOAP12Binding4Wsdl20());
		return bindingFaultBean;
	}

	public List<BindingFaultBean> getFaultsByBindingOperation(BindingOperation bindingOperation) throws XmlException {
		List <BindingFaultBean> bindingFaultBeanList = new ArrayList<BindingFaultBean>();
		BindingFaultBean bindingFaultBean = new  BindingFaultBean();
		List <BindingFault> bindingFaultList =   bindingOperation.getFaults();
		for (BindingFault bindingFault :bindingFaultList){
			bindingFaultBean.setName(bindingFault.getName());
			bindingFaultBean.setRef(bindingFault.getRef());
			bindingFaultBean.setDocumentation(bindingFault.getDocumentation().getContent());
			bindingFaultBean.setHTTPBinding4Wsdl11(bindingFault.getHTTPBinding4Wsdl11());
			bindingFaultBean.setHTTPBinding4Wsdl20(bindingFault.getHTTPBinding4Wsdl20());
			bindingFaultBean.setHttpContentEncoding(bindingFault.getHttpContentEncoding());
			bindingFaultBean.setMIMEBinding4Wsdl11(bindingFault.getMIMEBinding4Wsdl11());
			bindingFaultBean.setOtherAttributes(bindingFault.getOtherAttributes());
			bindingFaultBean.setOtherElements(bindingFault.getOtherElements());
			bindingFaultBean.setSOAP11Binding4Wsdl11(bindingFault.getSOAP11Binding4Wsdl11());
			bindingFaultBean.setSOAP12Binding4Wsdl11(bindingFault.getSOAP12Binding4Wsdl11());
			bindingFaultBean.setSOAP12Binding4Wsdl20(bindingFault.getSOAP12Binding4Wsdl20());
			bindingFaultBeanList.add(bindingFaultBean);
		}		
		return bindingFaultBeanList;
	}
	
}
