package org.wsssoapbox.bean.backing.soap;

import java.io.IOException;
import java.io.InputStream;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.slf4j.Logger;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.slf4j.LoggerFactory;
import org.wsssoapbox.bean.model.soap.SoapAttachmentBean;
import org.wsssoapbox.bean.model.soap.SoapRequestBean;
import org.wsssoapbox.view.util.ScopeController;

@ManagedBean(name = "attachmentFileDownload")
@RequestScoped
public class AttachmentFileDownload {

   private StreamedContent attachmentFile;
   private static final Logger logger = LoggerFactory.getLogger(AttachmentFileDownload.class);

   public AttachmentFileDownload() {
      logger.debug("start public AttachmentFileDownload() ");

      logger.debug("end  public AttachmentFileDownload() ");
   }

   public void handleFileDownload(ActionEvent event) {
      logger.debug("start public void handleFileDownload(ActionEvent event) ");


      logger.debug("end public void handleFileDownload(ActionEvent event) ");
   }

   public void handleFileDownload(SoapAttachmentBean soapAttachmentBean) {
      logger.debug("start    public void handleFileDownload(SoapAttachmentBean soapAttachmentBean)   ");
      try {
         SoapRequestBean soapRequestBean = (SoapRequestBean) ScopeController.getSessionMap("soapRequestBean");
         SoapAttachmentBean attchmentBean = soapAttachmentBean;

         if (attchmentBean != null) {

            String fileName = attchmentBean.getName();
            String contentType = attchmentBean.getContentType();
            String fileExtenstion = attchmentBean.getType();
            InputStream stream = attchmentBean.getInputStream();
            logger.debug("File Name :" + fileName);
            logger.debug("Content Type :" + contentType);
            logger.debug("Extenstion:" + fileExtenstion);

            if (stream.available() >= 0 || stream != null) {
               attachmentFile = new DefaultStreamedContent(stream, contentType, fileName + fileExtenstion);
            } else {
               logger.debug("File NotFound ...");
               FacesMessage msg = new FacesMessage("Error ", "File not found. :" + fileName);
               FacesContext.getCurrentInstance().addMessage(null, msg);
            }
         }
      } catch (NullPointerException ex1) {
         logger.error("NullPointerException : " + ex1.getMessage());
         ex1.printStackTrace();
         FacesMessage msg = new FacesMessage("Error ", "File not found. Please check URL .");
         FacesContext.getCurrentInstance().addMessage(null, msg);
      } catch (IOException ex2) {
         logger.error("IOException : " + ex2.getMessage());
         logger.info("File NotFound ...");
         ex2.printStackTrace();
         FacesMessage msg = new FacesMessage("Error ", "File not found. Please check URL .");
         FacesContext.getCurrentInstance().addMessage(null, msg);
         return;
      }
      logger.debug("end  public void handleFileDownload(SoapAttachmentBean soapAttachmentBean)   ");
   }

   /**
    * @return the attachmentFile
    */
   public StreamedContent getAttachmentFile() {
      return attachmentFile;
   }

   /**
    * @param attachmentFile the attachmentFile to set
    */
   public void setAttachmentFile(StreamedContent attachmentFile) {
      this.attachmentFile = attachmentFile;
   }
}
