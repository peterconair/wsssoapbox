package org.wsssoapbox.bean.model.soap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;

/**
 * @author  Peter
 */
@ManagedBean(name="soapMessageBean")
@SessionScoped
public class SoapMessageBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * @uml.property  name="soapRequestMessageBean"
	 * @uml.associationEnd  
	 */
	SoapRequestBean soapRequestMessageBean ;
	/**
	 * @uml.property  name="soapEnvelopeBean"
	 * @uml.associationEnd  
	 */
	SoapEnvelopeBean soapEnvelopeBean;
	/**
	 * @uml.property  name="soapHeaderBean"
	 * @uml.associationEnd  
	 */
	SoapHeaderBean soapHeaderBean;
	/**
	 * @uml.property  name="soapBodyBean"
	 * @uml.associationEnd  
	 */
	SoapBodyBean soapBodyBean;
	/**
	 * @uml.property  name="soapOperationBean"
	 * @uml.associationEnd  
	 */
	SoapOperationBean soapOperationBean;
	/**
	 * @uml.property  name="soapBodyBeanEntryList"
	 */
	List<SoapBodyBean> soapBodyBeanEntryList;
	/**
	 * @uml.property  name="soapFautlBean"
	 * @uml.associationEnd  
	 */
	SoapFaultBean soapFautlBean;
	/**
	 * @uml.property  name="soapFaultBeanList"
	 */
	List<SoapFaultBean> soapFaultBeanList;
	/**
	 * @uml.property  name="soapParameterBeanList"
	 */
	List<SoapParameterBean> soapParameterBeanList;

	public SoapMessageBean() {
		soapEnvelopeBean = new SoapEnvelopeBean();
		soapHeaderBean = new SoapHeaderBean();
		soapBodyBean = new SoapBodyBean();
		soapBodyBeanEntryList = new ArrayList<SoapBodyBean>();
		soapOperationBean = new SoapOperationBean();
		soapFautlBean = new SoapFaultBean();
		soapFaultBeanList = new ArrayList<SoapFaultBean>();
		soapParameterBeanList = new ArrayList<SoapParameterBean>();
	}


	/**
	 * @return
	 * @uml.property  name="soapRequestMessageBean"
	 */
	public SoapRequestBean getSoapRequestMessageBean() {
		return soapRequestMessageBean;
	}

	/**
	 * @param soapRequestMessageBean
	 * @uml.property  name="soapRequestMessageBean"
	 */
	public void setSoapRequestMessageBean(SoapRequestBean soapRequestMessageBean) {
		this.soapRequestMessageBean = soapRequestMessageBean;
	}
	/**
	 * @return
	 * @uml.property  name="soapEnvelopeBean"
	 */
	public SoapEnvelopeBean getSoapEnvelopeBean() {
		return soapEnvelopeBean;
	}

	/**
	 * @param soapEnvelopeBean
	 * @uml.property  name="soapEnvelopeBean"
	 */
	public void setSoapEnvelopeBean(SoapEnvelopeBean soapEnvelopeBean) {
		this.soapEnvelopeBean = soapEnvelopeBean;
	}

	/**
	 * @return
	 * @uml.property  name="soapHeaderBean"
	 */
	public SoapHeaderBean getSoapHeaderBean() {
		return soapHeaderBean;
	}

	/**
	 * @param soapHeaderBean
	 * @uml.property  name="soapHeaderBean"
	 */
	public void setSoapHeaderBean(SoapHeaderBean soapHeaderBean) {
		this.soapHeaderBean = soapHeaderBean;
	}

	/**
	 * @return
	 * @uml.property  name="soapBodyBean"
	 */
	public SoapBodyBean getSoapBodyBean() {
		return soapBodyBean;
	}

	/**
	 * @param soapBodyBean
	 * @uml.property  name="soapBodyBean"
	 */
	public void setSoapBodyBean(SoapBodyBean soapBodyBean) {
		this.soapBodyBean = soapBodyBean;
	}

	/**
	 * @return
	 * @uml.property  name="soapOperationBean"
	 */
	public SoapOperationBean getSoapOperationBean() {
		return soapOperationBean;
	}

	/**
	 * @param soapOperationBean
	 * @uml.property  name="soapOperationBean"
	 */
	public void setSoapOperationBean(SoapOperationBean soapOperationBean) {
		this.soapOperationBean = soapOperationBean;
	}

	/**
	 * @return
	 * @uml.property  name="soapBodyBeanEntryList"
	 */
	public List<SoapBodyBean> getSoapBodyBeanEntryList() {
		return soapBodyBeanEntryList;
	}

	/**
	 * @param soapBodyBeanEntryList
	 * @uml.property  name="soapBodyBeanEntryList"
	 */
	public void setSoapBodyBeanEntryList(List<SoapBodyBean> soapBodyBeanEntryList) {
		this.soapBodyBeanEntryList = soapBodyBeanEntryList;
	}

	/**
	 * @return
	 * @uml.property  name="soapFautlBean"
	 */
	public SoapFaultBean getSoapFautlBean() {
		return soapFautlBean;
	}

	/**
	 * @param soapFautlBean
	 * @uml.property  name="soapFautlBean"
	 */
	public void setSoapFautlBean(SoapFaultBean soapFautlBean) {
		this.soapFautlBean = soapFautlBean;
	}

	/**
	 * @return
	 * @uml.property  name="soapFaultBeanList"
	 */
	public List<SoapFaultBean> getSoapFaultBeanList() {
		return soapFaultBeanList;
	}

	/**
	 * @param soapFaultBeanList
	 * @uml.property  name="soapFaultBeanList"
	 */
	public void setSoapFaultBeanList(List<SoapFaultBean> soapFaultBeanList) {
		this.soapFaultBeanList = soapFaultBeanList;
	}

	/**
	 * @return
	 * @uml.property  name="soapParameterBeanList"
	 */
	public List<SoapParameterBean> getSoapParameterBeanList() {
		return soapParameterBeanList;
	}

	/**
	 * @param soapParameterBeanList
	 * @uml.property  name="soapParameterBeanList"
	 */
	public void setSoapParameterBeanList(List<SoapParameterBean> soapParameterBeanList) {
		this.soapParameterBeanList = soapParameterBeanList;
	}

}
