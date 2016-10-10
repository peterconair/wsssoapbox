/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.bean.backing.soap;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.slf4j.Logger;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.slf4j.LoggerFactory;
import org.wsssoapbox.bean.model.soap.SoapRequestBean;
import org.wsssoapbox.view.util.ScopeController;

/**
 *
 * @author Peter
 */
@ManagedBean(name = "soapRequestDownload")
@RequestScoped
public class SoapRequestMessageDownload implements SoapMessageDownload, Serializable {

   private static final Logger logger = LoggerFactory.getLogger(SoapRequestMessageDownload.class);
   private static final long serialVersionUID = -1944885100823053131L;
   private StreamedContent file;

   public SoapRequestMessageDownload() {

      InputStream is = null;
      try {
         SoapRequestBean soapRequestBean = (SoapRequestBean) ScopeController.getSessionMap("soapRequestBean");

         if (soapRequestBean.isSecureSoapMessage()) {
            is = new ByteArrayInputStream(soapRequestBean.getSecureSoapMessageValue().getBytes("UTF-8"));
         } else {
            is = new ByteArrayInputStream(soapRequestBean.getSoapXMLFormat().getBytes("UTF-8"));
         }
         file = new DefaultStreamedContent(is, "text/xml", "request.xml");
      } catch (UnsupportedEncodingException ex) {
         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "UnsupportedEncodingException ", ex.getMessage());
         FacesContext.getCurrentInstance().addMessage(null, msg);
         logger.error("UnsupportedEncodingException " + ex.getMessage());
      } catch (Exception ex) {
         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Exception ", ex.getMessage());
         FacesContext.getCurrentInstance().addMessage(null, msg);
         logger.error("Exception " + ex.getMessage());
      } finally {
         try {
            is.close();
         } catch (IOException ex) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "IOException ", ex.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            logger.error("IOException " + ex.getMessage());
         }
      }
   }

   /**
    * @return the file
    */
   @Override
   public StreamedContent getFile() {
      return file;
   }
}
