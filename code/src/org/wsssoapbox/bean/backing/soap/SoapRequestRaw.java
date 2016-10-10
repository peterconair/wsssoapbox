package org.wsssoapbox.bean.backing.soap;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
@ManagedBean
@RequestScoped
public class SoapRequestRaw implements Serializable{

	private static final long serialVersionUID = 1L;
	private static final Log logger = LogFactory.getLog(SoapRequestRaw.class);
	
	public SoapRequestRaw(){
		logger.debug("start public SoapRequestRaw()");
		
		logger.debug("end public SoapRequestRaw()");
	}

}
