package org.wsssoapbox.bean.backing.wsdl;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.slf4j.LoggerFactory;
import org.wsssoapbox.view.util.ScopeController;

@ManagedBean(name = "wsdlFileDownload")
@RequestScoped
public class WSDLFileDownload implements Serializable {
   private static final long serialVersionUID = 1L;

   private StreamedContent file;
   private static final Logger logger = LoggerFactory.getLogger(WSDLFileDownload.class);

   public WSDLFileDownload() {
      logger.debug("start public WSDLFileDownload() ");
      {
         InputStream stream = null;
         try {
            WSDLImportForm wsdlImportForm = (WSDLImportForm) ScopeController.getSessionMap("wSDLImportForm");
            //    String fileName = wsdlImportForm.getService().getName();
            String fileName = wsdlImportForm.getWsdlDucument().getServices().get(0).getName();
            String url = wsdlImportForm.getUrl();
            URL wsdlURL = new URL(url);
            stream = wsdlURL.openStream();
            logger.debug("Downloading .... ");
            logger.debug("URL : " + url);
            logger.debug("File Name : " + fileName + ".wsdl");

            if (stream.available() >= 0 || stream != null) {
               
               file = new DefaultStreamedContent(stream, "application/wsdl", fileName + ".wsdl");
               logger.debug("File size : " + file.getStream().available() + " bytes ");
            } else {
               logger.error("File NotFound ...");
               FacesMessage msg = new FacesMessage("Error ", "File not found. Please check URL :" + url);
               FacesContext.getCurrentInstance().addMessage(null, msg);
            }
         } catch (NullPointerException ex1) {
            logger.error("NullPointerException : " + ex1.getMessage());
            FacesMessage msg = new FacesMessage("Error ", "File not found. Please check URL .");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            ex1.printStackTrace();
         } catch (IOException ex) {
            FacesMessage msg = new FacesMessage("Error ", "File not found. Please check URL .");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            logger.error(ex.getMessage(), ex);
         } catch (Exception ex) {
            FacesMessage msg = new FacesMessage("Error ", ex.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            logger.error(ex.getMessage(), ex);
         } finally {
            if (stream != null) {
               try {
                  stream.close();
               } catch (IOException ex) {
                  ex.printStackTrace();
               }

            }
         }
      }
      logger.debug("end  public WSDLFileDownload() ");
   }
   /*
   public void handleFileDownload(ActionEvent event) {
   logger.debug("start public void handleFileDownload(ActionEvent event) ");
   InputStream stream = null;
   try {
   
   WSDLImportForm wsdlImportForm = (WSDLImportForm) ScopeController.getSessionMap("wSDLImportForm");
   //  String fileName = wsdlImportForm.getService().getName();
   String fileName = wsdlImportForm.getWsdlDucument().getServices().get(0).getName();
   String url = wsdlImportForm.getUrl();
   logger.debug("URL :" + url);
   URL wsdlURL = new URL(url);
   stream = wsdlURL.openStream();
   
   //connection = wsdlURL.openConnection();
   // stream = connection.getInputStream();
   
   // file store in same class directory.
   //   InputStream stream = this.getClass().getResourceAsStream("NetworkCfg.xml");
   //   file store in physical hard drive.
   //   InputStream stream = new FileInputStream(new File("C:/test.txt"));
   if (stream.available() >= 0 || stream != null) {
   file = new DefaultStreamedContent(stream, "application/wsdl", fileName + ".wsdl");
   } else {
   logger.error("File NotFound ...");
   FacesMessage msg = new FacesMessage("Error ", "File not found. Please check URL :" + url);
   FacesContext.getCurrentInstance().addMessage(null, msg);
   }
   } catch (NullPointerException ex1) {
   logger.error("NullPointerException : " + ex1.getMessage());
   FacesMessage msg = new FacesMessage("Error ", "File not found. Please check URL .");
   FacesContext.getCurrentInstance().addMessage(null, msg);
   ex1.printStackTrace();
   } catch (IOException ex2) {
   logger.error("IOException : " + ex2.getMessage());
   FacesMessage msg = new FacesMessage("Error ", "File not found. Please check URL .");
   FacesContext.getCurrentInstance().addMessage(null, msg);
   ex2.printStackTrace();
   } catch (Exception ex) {
   logger.error("Exception : " + ex.getMessage());
   FacesMessage msg = new FacesMessage("Error ", "File not found. Please check URL .");
   FacesContext.getCurrentInstance().addMessage(null, msg);
   ex.printStackTrace();
   } finally {
   if (stream != null) {
   try {
   stream.close();
   } catch (IOException ex) {
   ex.printStackTrace();
   }
   
   }
   }
   
   
   logger.debug("end public void handleFileDownload(ActionEvent event) <<<<<<<<<<<<<<<<<<<<<<<<<<<<< ");
   }
    */

   public StreamedContent getFile() {
      return file;
   }
}
