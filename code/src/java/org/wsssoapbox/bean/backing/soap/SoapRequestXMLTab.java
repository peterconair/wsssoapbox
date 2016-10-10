package org.wsssoapbox.bean.backing.soap;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedBean(name="soapRequestXML")
@RequestScoped
public class SoapRequestXMLTab implements Serializable{
	private static final long serialVersionUID = 7257307013913297472L;
	 private static final Logger logger=LoggerFactory.getLogger(SoapRequestXMLTab.class);
	
	public SoapRequestXMLTab(){
		logger.debug("start public SoapRequestXML()");
		
		
		logger.debug("end public SoapRequestXML()");
	}
	public void updateContent(){

	}
}
