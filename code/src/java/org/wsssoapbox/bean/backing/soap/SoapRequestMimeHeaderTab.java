/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.bean.backing.soap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wsssoapbox.bean.model.soap.MimeHeaderBean;
import org.wsssoapbox.bean.model.soap.SoapRequestBean;
import org.wsssoapbox.view.util.ScopeController;

/**
 *
 * @author Peter
 */
@ManagedBean(name = "soapRequestMimeHeaderTab")
@SessionScoped
public class SoapRequestMimeHeaderTab implements Serializable {

   private static final long serialVersionUID = -1049208730168511247L;
   private MimeHeaderBean mimeHeaderBean = new MimeHeaderBean();
   private List<MimeHeaderBean> mimeHeaderBeanList = new ArrayList<MimeHeaderBean>();
   private static final Logger logger = LoggerFactory.getLogger(SoapRequestMimeHeaderTab.class);

   public SoapRequestMimeHeaderTab() {
   }

   public String addMimeHeader(MimeHeaderBean mimeHeader) {
      logger.debug("start public String addMimeHeader() ");
      SoapRequestBean soapRequestBean = (SoapRequestBean) ScopeController.getSessionMap("soapRequestBean");
     // Map<String, String> userMimeHeaders = new HashMap<String, String>();

      if (soapRequestBean != null) {
         logger.info("User mimeheader size : " + mimeHeaderBeanList.size());
         logger.info("you added mime header : " + mimeHeader.getHeader() + " : " + mimeHeader.getValue());
         // check exits item
         if (mimeHeaderBeanList.contains(mimeHeader)) {
            logger.info("Cannot add mimeheader . : " + mimeHeader.getHeader() + " : " + mimeHeader.getValue() + " already exist");
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cannot add mimeheader .", mimeHeader.getHeader() + " : " + mimeHeader.getValue() + " already exist");
            FacesContext.getCurrentInstance().addMessage(null, message);
            mimeHeaderBean = new MimeHeaderBean();
            return null;

         } else {
            mimeHeaderBeanList.add(mimeHeader);
         //   userMimeHeaders.put(mimeHeader.getHeader(), mimeHeader.getValue());
          //  soapRequestBean.setUserMimeHeaders(userMimeHeaders);
            soapRequestBean.getSOAPRequest().getMimeHeaders().setHeader(mimeHeader.getHeader(), mimeHeader.getValue());
            // reset form
            mimeHeaderBean = new MimeHeaderBean();
         }
      }



      logger.info("Current mimeheader size : " + mimeHeaderBeanList.size());
      logger.debug("end public String addMimeHeader() ");
      return null;
   }

   public String deleteMimeHeader(MimeHeaderBean mimeHeaderBean) {
      logger.debug("start public String deleteMimeHeader(MimeHeaderBean mimeHeaderBean) ");
      SoapRequestBean soapRequestBean = (SoapRequestBean) ScopeController.getSessionMap("soapRequestBean");
      if (soapRequestBean != null) {


         soapRequestBean.getSOAPRequest().getMimeHeaders().removeHeader(mimeHeaderBean.getHeader());
   //      soapRequestBean.getUserMimeHeaderLists().remove(mimeHeaderBean);
         logger.info("you removed mime header : " + mimeHeaderBean.getHeader() + " : " + mimeHeaderBean.getValue());
         FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "you removed mime header .", mimeHeaderBean.getHeader() + " : " + mimeHeaderBean.getValue());
         FacesContext.getCurrentInstance().addMessage(null, message);

      }
      logger.debug("end public String deleteMimeHeader(MimeHeaderBean mimeHeaderBean) ");
      return null;
   }

   /**
    * @return the mimeHeaderBean
    */
   public MimeHeaderBean getMimeHeaderBean() {
      return mimeHeaderBean;
   }

   /**
    * @param mimeHeaderBean the mimeHeaderBean to set
    */
   public void setMimeHeaderBean(MimeHeaderBean mimeHeaderBean) {
      this.mimeHeaderBean = mimeHeaderBean;
   }

   /**
    * @return the mimeHeaderBeanList
    */
   public List<MimeHeaderBean> getMimeHeaderBeanList() {
      return mimeHeaderBeanList;
   }

   /**
    * @param mimeHeaderBeanList the mimeHeaderBeanList to set
    */
   public void setMimeHeaderBeanList(List<MimeHeaderBean> mimeHeaderBeanList) {
      this.mimeHeaderBeanList = mimeHeaderBeanList;
   }
}
