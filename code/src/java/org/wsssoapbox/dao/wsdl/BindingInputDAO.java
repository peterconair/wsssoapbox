package org.wsssoapbox.dao.wsdl;

import javax.xml.namespace.QName;

import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.api.BindingInput;
import org.ow2.easywsdl.wsdl.api.BindingOperation;
import org.ow2.easywsdl.wsdl.api.Description;
import org.wsssoapbox.bean.model.wsdl.BindingInputBean;

public class BindingInputDAO implements IBindingInputDAO{

	public BindingInputBean getInputBindingByBinding(Description desc, QName bindingQName, String operationName)
			throws XmlException {
BindingInput bindingInput = desc.getBinding(bindingQName).getBindingOperation(operationName).getInput();
		
		BindingInputBean bindingInputBean = new BindingInputBean();
		
		bindingInputBean.setName(bindingInput.getName());
		bindingInputBean.setName(bindingInput.getDocumentation().getContent());
		bindingInputBean.setOtherAttributes(bindingInput.getOtherAttributes());
		bindingInputBean.setOtherElements(bindingInput.getOtherElements());
		bindingInputBean.setHTTPBinding4Wsdl11(bindingInput.getHTTPBinding4Wsdl11());
		bindingInputBean.setHTTPBinding4Wsdl20(bindingInput.getHTTPBinding4Wsdl20());
		bindingInputBean.setHttpContentEncoding(bindingInput.getHttpContentEncoding());
		bindingInputBean.setMIMEBinding4Wsdl11(bindingInput.getMIMEBinding4Wsdl11());	
		bindingInputBean.setSOAP11Binding4Wsdl11(bindingInput.getSOAP11Binding4Wsdl11());
		bindingInputBean.setSOAP12Binding4Wsdl11(bindingInput.getSOAP12Binding4Wsdl11());
		bindingInputBean.setSOAP12Binding4Wsdl20(bindingInput.getSOAP12Binding4Wsdl20());
	
		return bindingInputBean;
	}

	public BindingInputBean getInputByBindingOperation(BindingOperation bindingOperation) throws XmlException {
BindingInput bindingInput = bindingOperation.getInput();
		
		BindingInputBean bindingInputBean = new BindingInputBean();
		
		bindingInputBean.setName(bindingInput.getName());
		bindingInputBean.setName(bindingInput.getDocumentation().getContent());
		bindingInputBean.setOtherAttributes(bindingInput.getOtherAttributes());
		bindingInputBean.setOtherElements(bindingInput.getOtherElements());
		bindingInputBean.setHTTPBinding4Wsdl11(bindingInput.getHTTPBinding4Wsdl11());
		bindingInputBean.setHTTPBinding4Wsdl20(bindingInput.getHTTPBinding4Wsdl20());
		bindingInputBean.setHttpContentEncoding(bindingInput.getHttpContentEncoding());
		bindingInputBean.setMIMEBinding4Wsdl11(bindingInput.getMIMEBinding4Wsdl11());	
		bindingInputBean.setSOAP11Binding4Wsdl11(bindingInput.getSOAP11Binding4Wsdl11());
		bindingInputBean.setSOAP12Binding4Wsdl11(bindingInput.getSOAP12Binding4Wsdl11());
		bindingInputBean.setSOAP12Binding4Wsdl20(bindingInput.getSOAP12Binding4Wsdl20());

		return bindingInputBean;
	}


}
