/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.bean.backing.soap.security.keystore;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.FileUploadEvent;
import org.wsssoapbox.bean.model.soap.security.keystore.CertificateBean;
import org.wsssoapbox.soap.security.keystore.support.CertificateHelperImpl;

/**
 *
 * @author Peter
 */
@ManagedBean(name = "certUpload")
@RequestScoped
public class CertificateUploadController {

   public void handleFileUpload(FileUploadEvent event) {

      System.out.println("start public void handleFileUpload(FileUploadEvent event)");
      CertificateBean certBean = new CertificateBean();
      CertificateHelperImpl certDAO = new CertificateHelperImpl();
      try {
         certBean = certDAO.createCertificate(event.getFile().getInputstream());
      } catch (IOException ex) {
         FacesMessage msg = new FacesMessage("Failed", event.getFile().getFileName() + " is not upload.");
         FacesContext.getCurrentInstance().addMessage(null, msg);
         Logger.getLogger(CertificateUploadController.class.getName()).log(Level.SEVERE, null, ex);
      }
      System.out.println("end public void handleFileUpload(FileUploadEvent event)");
   }
}
