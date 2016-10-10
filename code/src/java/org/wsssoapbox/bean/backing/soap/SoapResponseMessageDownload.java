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
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.slf4j.Logger;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.slf4j.LoggerFactory;
import org.wsssoapbox.bean.model.soap.SoapResponseBean;
import org.wsssoapbox.view.util.ScopeController;

/**
 *
 * @author Peter
 */
@ManagedBean(name = "soapResponseDownload")
@RequestScoped
public class SoapResponseMessageDownload implements SoapMessageDownload, Serializable {

   private static final Logger logger = LoggerFactory.getLogger(SoapResponseMessageDownload.class);
   private static final long serialVersionUID = 1775029895501069761L;
   private StreamedContent file;

   public StreamedContent getFile() {
      return file;
   }

   public SoapResponseMessageDownload() {
      InputStream stream = null;
      try {
         SoapResponseBean soapResponseBean = (SoapResponseBean) ScopeController.getSessionMap("soapResponseBean");
         stream = new ByteArrayInputStream(soapResponseBean.getSoapXMLFormat().getBytes("UTF-8"));
         file = new DefaultStreamedContent(stream, "text/xml",
                 "response.xml");
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
            if (stream != null) {
               stream.close();
            }
         } catch (IOException ex) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "IOException ", ex.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            logger.error("IOException " + ex.getMessage());
         }
      }
   }
}
