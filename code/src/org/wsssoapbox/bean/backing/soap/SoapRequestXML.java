package org.wsssoapbox.bean.backing.soap;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@ManagedBean(name="soapRequestXML")
@RequestScoped
public class SoapRequestXML implements Serializable{
	private static final long serialVersionUID = 7257307013913297472L;
	private static final Log logger = LogFactory.getLog(SoapRequestXML.class);
	
	public SoapRequestXML(){
		logger.debug("start public SoapRequestXML()");
		
		
		logger.debug("end public SoapRequestXML()");
	}
	public void updateContent(){

	}
}
