package org.wsssoapbox.bean.model.soap;

import java.io.Serializable;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * @author  Peter
 */
@ManagedBean(name="soapMessageBean")
@SessionScoped
public class SoapMessageBean implements Serializable{
	private static final long serialVersionUID = 1L;
     private SoapPartBean soapPartBean;
     private List<MimeHeaderBean> mimeHeaderBeans;
	private List<SoapAttachmentBean> soapAttachmentBeans;

	public SoapMessageBean() {
		soapPartBean = new SoapPartBean();
	}

   /**
    * @return the soapPartBean
    */
   public SoapPartBean getSoapPartBean() {
      return soapPartBean;
   }

   /**
    * @param soapPartBean the soapPartBean to set
    */
   public void setSoapPartBean(SoapPartBean soapPartBean) {
      this.soapPartBean = soapPartBean;
   }

   /**
    * @return the soapAttachmentBeans
    */
   public List<SoapAttachmentBean> getSoapAttachmentBeans() {
      return soapAttachmentBeans;
   }

   /**
    * @param soapAttachmentBeans the soapAttachmentBeans to set
    */
   public void setSoapAttachmentBeans(List<SoapAttachmentBean> soapAttachmentBeans) {
      this.soapAttachmentBeans = soapAttachmentBeans;
   }

   /**
    * @return the mimeHeaderBeans
    */
   public List<MimeHeaderBean> getMimeHeaderBeans() {
      return mimeHeaderBeans;
   }

   /**
    * @param mimeHeaderBeans the mimeHeaderBeans to set
    */
   public void setMimeHeaderBeans(List<MimeHeaderBean> mimeHeaderBeans) {
      this.mimeHeaderBeans = mimeHeaderBeans;
   }
}
