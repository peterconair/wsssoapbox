package org.wsssoapbox.bean.backing.soap;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wsssoapbox.bean.model.soap.SoapOperationBean;
import org.wsssoapbox.bean.model.soap.SoapParameterBean;
import org.wsssoapbox.bean.model.soap.SoapMessageBean;
import org.wsssoapbox.bean.util.ScopeController;

@ManagedBean(name = "soapRequestForm")
@ViewScoped
public class SoapRequestForm implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Log logger = LogFactory.getLog(SoapRequestForm.class);

	public SoapRequestForm() {
		logger.debug("start public SoapRequestForm()");
	
	
		logger.debug("end public SoapRequestForm()");
	}

}
