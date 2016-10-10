package org.wsssoapbox.webapp.filter;

import java.io.IOException;
import java.io.Serializable;

import java.util.Iterator;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;


import javax.xml.soap.MimeHeader;
import javax.xml.soap.SOAPMessage;
import org.slf4j.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.slf4j.LoggerFactory;
import org.wsssoapbox.bean.model.soap.SoapAttachmentBean;
import org.wsssoapbox.bean.model.soap.SoapRequestBean;
import org.wsssoapbox.view.util.ScopeController;

@ManagedBean(name="soapAttachmentController")
@RequestScoped
public class SoapAttachmentController implements Serializable {

   /**
    * 
    */
   private static final long serialVersionUID = 1L;
   private UploadedFile file;
   private static final Logger logger = LoggerFactory.getLogger(SoapAttachmentController.class);

   public UploadedFile getFile() {
      return file;
   }

   public void setFile(UploadedFile file) {
      this.file = file;
   }

   public void handleFileUpload(FileUploadEvent event) throws IOException {
      file = event.getFile();

      SoapRequestBean soapRequestBean = (SoapRequestBean) ScopeController.getSessionMap("soapRequestBean");
      if (soapRequestBean != null) {

         List<SoapAttachmentBean> soapAttachmentBeanList = soapRequestBean.getSoapAttachments();

         SoapAttachmentBean attatchement = new SoapAttachmentBean();
         attatchement.setContentID(file.getFileName());
         attatchement.setName(file.getFileName());
         attatchement.setSize(file.getSize());
         attatchement.setContentType(file.getContentType());
         attatchement.setContent(file.getContents());
         attatchement.setInputStream(file.getInputstream());
         // find file type or extension
         String type = "";
         if (file.getContentType() != null || file.getContentType().equals("")) {
            int index = file.getContentType().indexOf('/');
            type = file.getContentType().substring(index + 1);
         }
         attatchement.setType(type);

         soapAttachmentBeanList.add(attatchement);
         soapRequestBean.setSoapAttachments(soapAttachmentBeanList);

         SOAPMessage soapMessage = soapRequestBean.getSOAPRequest();
         
         soapMessage.removeAllAttachments();
         
        // soapMessage.getMimeHeaders().removeAllHeaders();
  

         logger.info("File Name : " + file.getFileName());
         logger.info("File Conten Type : " + file.getContentType());
         logger.info("File Name : " + file.getSize());
         logger.info("Amount in attachment  : " + soapRequestBean.getSoapAttachments().size());


         logger.debug(" ****************After add ************");
         logger.info("Attachement size :" + soapRequestBean.getSoapAttachments().size());
         for (SoapAttachmentBean sab : soapRequestBean.getSoapAttachments()) {
            logger.info("getContentID  : " + sab.getContentID());
            logger.info("getContentType  : " + sab.getContentType());
         }

         logger.debug("********* Mime Header  in SOAPMessage ******** ");
         Iterator iter2 = soapRequestBean.getSOAPRequest().getMimeHeaders().getAllHeaders();
         while (iter2.hasNext()) {
            MimeHeader mime = (MimeHeader) iter2.next();
            logger.debug(mime.getName() + " : " + mime.getValue());
         }

         FacesMessage msg = new FacesMessage("Successfully", file.getFileName() + " is uploaded.");
         FacesContext.getCurrentInstance().addMessage(null, msg);
      }
   }

   public void handleUploadRequest(FileUploadEvent event) throws IOException {
      file = event.getFile();
   }
}
