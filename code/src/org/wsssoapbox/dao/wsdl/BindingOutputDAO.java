package org.wsssoapbox.dao.wsdl;

import javax.xml.namespace.QName;

import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.api.BindingOperation;
import org.ow2.easywsdl.wsdl.api.BindingOutput;
import org.ow2.easywsdl.wsdl.api.Description;
import org.wsssoapbox.bean.model.wsdl.BindingOutputBean;

public class BindingOutputDAO implements IBindingOutputDAO{

	public BindingOutputBean getOutputByBindingName(Description desc, QName bindingQName, String operationName)
			throws XmlException {
		BindingOutputBean bindingOutputBean = new BindingOutputBean();

		BindingOutput bindingOutput = desc.getBinding(bindingQName)
				.getBindingOperation(operationName).getOutput();
		bindingOutputBean.setName(bindingOutput.getName());
		bindingOutputBean.setName(bindingOutput.getDocumentation().toString());
		bindingOutputBean.setHTTPBinding4Wsdl11(bindingOutput
				.getHTTPBinding4Wsdl11());
		bindingOutputBean.setHTTPBinding4Wsdl20(bindingOutput
				.getHTTPBinding4Wsdl20());
		bindingOutputBean.setHttpContentEncoding(bindingOutput
				.getHttpContentEncoding());
		bindingOutputBean.setMIMEBinding4Wsdl11(bindingOutput
				.getMIMEBinding4Wsdl11());
		bindingOutputBean
				.setOtherAttributes(bindingOutput.getOtherAttributes());
		bindingOutputBean.setOtherElements(bindingOutput.getOtherElements());
		bindingOutputBean.setSOAP11Binding4Wsdl11(bindingOutput
				.getSOAP11Binding4Wsdl11());
		bindingOutputBean.setSOAP12Binding4Wsdl11(bindingOutput
				.getSOAP12Binding4Wsdl11());
		bindingOutputBean.setSOAP12Binding4Wsdl20(bindingOutput
				.getSOAP12Binding4Wsdl20());

		return bindingOutputBean;
	}

	public BindingOutputBean getOutputByBindingOperation(BindingOperation bindingOperation) throws XmlException {
		BindingOutputBean bindingOutputBean = new BindingOutputBean();

		BindingOutput bindingOutput = bindingOperation.getOutput();
		
		bindingOutputBean.setName(bindingOutput.getName());
		bindingOutputBean.setName(bindingOutput.getDocumentation().toString());
		bindingOutputBean.setHTTPBinding4Wsdl11(bindingOutput
				.getHTTPBinding4Wsdl11());
		bindingOutputBean.setHTTPBinding4Wsdl20(bindingOutput
				.getHTTPBinding4Wsdl20());
		bindingOutputBean.setHttpContentEncoding(bindingOutput
				.getHttpContentEncoding());
		bindingOutputBean.setMIMEBinding4Wsdl11(bindingOutput
				.getMIMEBinding4Wsdl11());
		bindingOutputBean
				.setOtherAttributes(bindingOutput.getOtherAttributes());
		bindingOutputBean.setOtherElements(bindingOutput.getOtherElements());
		bindingOutputBean.setSOAP11Binding4Wsdl11(bindingOutput
				.getSOAP11Binding4Wsdl11());
		bindingOutputBean.setSOAP12Binding4Wsdl11(bindingOutput
				.getSOAP12Binding4Wsdl11());
		bindingOutputBean.setSOAP12Binding4Wsdl20(bindingOutput
				.getSOAP12Binding4Wsdl20());

		return bindingOutputBean;
	}

}
