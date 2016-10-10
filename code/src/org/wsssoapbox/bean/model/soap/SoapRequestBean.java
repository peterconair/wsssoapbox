package org.wsssoapbox.bean.model.soap;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;

/**
 * @author  Peter
 */
public class SoapRequestBean implements Serializable{

	private static final long serialVersionUID = 1580546377095328466L;
	/**
	 * @uml.property  name="soapAttachments"
	 */
	private List<SoapAttachmentBean> soapAttachments;
	/**
	 * @uml.property  name="soapPart"
	 * @uml.associationEnd  
	 */
	private SoapPartBean soapPart;
	/**
	 * @uml.property  name="soapTextFormat"
	 */
	private String soapTextFormat;
	/**
	 * @uml.property  name="soapXMLFormat"
	 */
	private String soapXMLFormat;
	/**
	 * @uml.property  name="mimeHeaders"
	 */
	private Map<String,String> mimeHeaders;
	
	
	/**
	 * @return
	 * @uml.property  name="soapAttachments"
	 */
	public List<SoapAttachmentBean> getSoapAttachments() {
		return soapAttachments;
	}
	/**
	 * @param soapAttachments
	 * @uml.property  name="soapAttachments"
	 */
	public void setSoapAttachments(List<SoapAttachmentBean> soapAttachments) {
		this.soapAttachments = soapAttachments;
	}
	/**
	 * @return
	 * @uml.property  name="soapPart"
	 */
	public SoapPartBean getSoapPart() {
		return soapPart;
	}
	/**
	 * @param soapPart
	 * @uml.property  name="soapPart"
	 */
	public void setSoapPart(SoapPartBean soapPart) {
		this.soapPart = soapPart;
	}
	/**
	 * @return
	 * @uml.property  name="soapTextFormat"
	 */
	public String getSoapTextFormat() {
		return soapTextFormat;
	}
	/**
	 * @param soapTextFormat
	 * @uml.property  name="soapTextFormat"
	 */
	public void setSoapTextFormat(String soapTextFormat) {
		this.soapTextFormat = soapTextFormat;
	}
	/**
	 * @return
	 * @uml.property  name="soapXMLFormat"
	 */
	public String getSoapXMLFormat() {
		return soapXMLFormat;
	}
	/**
	 * @param soapXMLFormat
	 * @uml.property  name="soapXMLFormat"
	 */
	public void setSoapXMLFormat(String soapXMLFormat) {
		this.soapXMLFormat = soapXMLFormat;
	}
	/**
	 * @return
	 * @uml.property  name="mimeHeaders"
	 */
	public Map<String,String> getMimeHeaders() {
		return mimeHeaders;
	}
	/**
	 * @param mimeHeaders
	 * @uml.property  name="mimeHeaders"
	 */
	public void setMimeHeaders(Map<String,String> mimeHeaders) {
		this.mimeHeaders = mimeHeaders;
	}


}
