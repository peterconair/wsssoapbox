package org.wsssoapbox.bean.backing.soap;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@ManagedBean
@RequestScoped
public class SoapRequestRawTab implements Serializable{

	private static final long serialVersionUID = 1L;
	 private static final Logger logger=LoggerFactory.getLogger(SoapRequestRawTab.class);
	
	public SoapRequestRawTab(){
		logger.debug("start public SoapRequestRaw()");
		
		logger.debug("end public SoapRequestRaw()");
	}

}
