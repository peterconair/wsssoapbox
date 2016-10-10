package org.wsssoapbox.bean.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.wsssoapbox.bean.backing.WSDLImportForm;

public class FileDownloadController {
	private StreamedContent file;
	private static final Log logger = LogFactory.getLog(FileDownloadController.class);

	public FileDownloadController() {
		logger.debug(">>>>>>>>>>>>>>>>>  public FileDownloadController() ");
		logger.debug(">>>>>>>>>>>>>>>>>  public FileDownloadController() <<<<<<<<<<<<<<<<<<<<<<<<<<<");
	}

	public void handleFileDownload(ActionEvent event) {
		logger.debug(">>>>>>>>>>>>>>>>>  public void handleFileDownload(ActionEvent event) ");

		try {

			WSDLImportForm wsdlImportForm = (WSDLImportForm) getSessionMap("wSDLImportForm");
			String fileName = wsdlImportForm.getService().getName();
			String url = wsdlImportForm.getUrl();
			URL wsdlURL = new URL(url);
			InputStream stream = wsdlURL.openStream();

			// file store in same class directory.
			// InputStream stream = this.getClass().getResourceAsStream("NetworkCfg.xml");
			// file store in physical hard drive.
			// InputStream stream = new FileInputStream(new File("C:/NetworkCfg.xml"));
			if (stream.available() != 0) {
				file = new DefaultStreamedContent(stream, "application/wsdl", fileName + ".wsdl");
				logger.info(stream.toString());			 
			} else {
				logger.info("File NotFound ...");
				 FacesMessage msg = new FacesMessage("Error ", "File not found. Please check URL :"+url);
				 FacesContext.getCurrentInstance().addMessage(null, msg);
			}
			// InputStream stream =
			// this.getClass().getResourceAsStream("C:\\NetworkCfg.xml");
			// new FileInputStream(new File("C:/cfg/logfile.log"));

		} catch (NullPointerException ex1) {
			logger.error("NullPointerException : " + ex1.getMessage());
			 FacesMessage msg = new FacesMessage("Error ", "File not found. Please check URL .");
			 FacesContext.getCurrentInstance().addMessage(null, msg);
		}catch (IOException ex2){
			logger.error("IOException : " + ex2.getMessage());
			logger.info("File NotFound ...");
			 FacesMessage msg = new FacesMessage("Error ", "File not found. Please check URL .");
			 FacesContext.getCurrentInstance().addMessage(null, msg);
			 return;
		}

		logger.debug(">>>>>>>>>>>>>>>>>  public void handleFileDownload(ActionEvent event) <<<<<<<<<<<<<<<<<<<<<<<<<<<<< ");
	}

	public StreamedContent getFile() {
		return file;
	}

	public void setFile(StreamedContent file) {
		this.file = file;
	}

	public Object getSessionMap(String name) {
		Object object = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(name);
		return object;
	}
}
