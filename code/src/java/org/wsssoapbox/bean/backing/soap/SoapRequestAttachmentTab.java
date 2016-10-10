/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.bean.backing.soap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.xml.soap.AttachmentPart;
import javax.xml.soap.MimeHeader;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wsssoapbox.bean.model.soap.SoapAttachmentBean;
import org.wsssoapbox.bean.model.soap.SoapRequestBean;
import org.wsssoapbox.view.util.ScopeController;

/**
 *
 * @author Peter
 */
@ManagedBean(name = "soapRequestAttachmentTab")
@RequestScoped
public class SoapRequestAttachmentTab implements Serializable {
   
   private static final Logger logger = LoggerFactory.getLogger(SoapRequestAttachmentTab.class);
   private static final long serialVersionUID = 5026806662381223830L;
   List<SoapAttachmentBean> soapAttachmentBeanList = new ArrayList<SoapAttachmentBean>();
   
   public void deleteAttachFile(SoapAttachmentBean attach) {
      SoapRequestBean soapRequestBean = (SoapRequestBean) ScopeController.getSessionMap("soapRequestBean");
      
      if (soapRequestBean != null) {
         
         soapAttachmentBeanList = soapRequestBean.getSoapAttachments();
         logger.debug("********Attach in SoapRequestBean**********");
         logger.debug(" Number of attachment file  " + soapRequestBean.getSoapAttachments().size());
         for (SoapAttachmentBean sab : soapRequestBean.getSoapAttachments()) {
            logger.debug("Content ID : " + sab.getContentID());
         }
         
         SOAPMessage soapMessage = soapRequestBean.getSOAPRequest();
         logger.debug("********Attach in SOAPMessage**********");
         
         soapRequestBean.setSoapXMLFormat(soapRequestBean.getSoapOriginal());
         
         logger.debug("********Attach in SOAP*********");
         Iterator iter = soapMessage.getAttachments();
         while (iter.hasNext()) {
            AttachmentPart at = (AttachmentPart) iter.next();
            logger.debug("Content ID : " + at.getContentId());
            logger.debug("Content Type: " + at.getContentType());
         }

         // remove all 
         soapMessage.removeAllAttachments();
         Iterator iter3 = soapMessage.getAttachments();
         while (iter3.hasNext()) {
            AttachmentPart at = (AttachmentPart) iter.next();
            logger.debug("Content ID : " + at.getContentId());
            logger.debug("Content Type: " + at.getContentType());
         }
         
         logger.debug("********MimeHeader in SOAPMessage**********");
         Iterator iter1 = soapMessage.getMimeHeaders().getAllHeaders();
         while (iter1.hasNext()) {
            MimeHeader mime = (MimeHeader) iter1.next();
            logger.debug(mime.getName() + " : " + mime.getValue());
         }         
         soapMessage.getMimeHeaders().removeHeader(attach.getContentType());
         soapAttachmentBeanList.remove(attach);
         /* */
         logger.debug("********After Remove Attachement (Mime Header)**********");
         Iterator iter2 = soapMessage.getMimeHeaders().getAllHeaders();
         while (iter2.hasNext()) {
            MimeHeader mime = (MimeHeader) iter2.next();
            logger.debug(mime.getName() + " : " + mime.getValue());
         }
         
         
      }
      
   }
}
