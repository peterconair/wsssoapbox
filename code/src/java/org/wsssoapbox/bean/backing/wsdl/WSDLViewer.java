/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.bean.backing.wsdl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wsssoapbox.database.User;
import org.wsssoapbox.view.util.ScopeController;

/**
 *
 * @author Peter
 */

public class WSDLViewer implements Serializable {

   private static final Logger logger = LoggerFactory.getLogger(WSDLViewer.class);
   private static final long serialVersionUID = 1926896705122504285L;
   private String htmlOutputFile;
   private String htmlOutputURL;
   private String htmlOutputPath = "/temp/";
   private String htmlOutput = "/temp";
   private String fileName = "";
   private String fileExtenstion = ".xhtml";
   private String xsltPath;
   private String xsltName;
   private String sp = "/";

   public WSDLViewer() {
   }

   public void genterateHTML(String strURL, User user) {
      StreamSource xmlSource = null;
      FileOutputStream fos = null;
      FacesContext fCtx = FacesContext.getCurrentInstance();
      HttpSession session = (HttpSession) fCtx.getExternalContext().getSession(false);
      String sessionId = "" + user.getId();

      if (strURL.equals("") || strURL != null) {
         try {

            WSDLImportForm wsdlImportForm = (WSDLImportForm) ScopeController.getSessionMap("wSDLImportForm");
            String file = wsdlImportForm.getService().getName();
            if (wsdlImportForm != null && file != null) {
               fileName = file;
            }

            xsltPath = sp + "resources" + sp + "xslt" + sp;
            xsltName = "wsdl-viewer.xsl";


            URL xsltURL = FacesContext.getCurrentInstance().getExternalContext().getResource(xsltPath + xsltName);

            URL wsdlURL = new URL(strURL);

            //   URL xsltURL = new URL("http://tomi.vanek.sk/xml/wsdl-viewer.xsl");
            //streamSource xsltSource = new StreamSource(new FileInputStream(xsltInputFile));
            xmlSource = new StreamSource(wsdlURL.openStream());

            StreamSource xsltSource = new StreamSource(xsltURL.openStream());

            String path = "";

            path = FacesContext.getCurrentInstance().getExternalContext().getRealPath(htmlOutput);
            String userPath = path + "\\" + sessionId;

            logger.debug("Separater path : " + sp);
            logger.debug("Temp path : " + path);
            logger.debug("User path : " + userPath);
            File f = new File(userPath);
            if (f.mkdir()) {
               logger.debug("User temp directory created in : " + userPath);
            } else {
               logger.debug("User temp directory already exits in : " + userPath);
            }

            htmlOutputFile = userPath + "\\" + fileName + "_" + sessionId + fileExtenstion;
            // "/temp/xxxxxxxxxxx/
            htmlOutputPath += sessionId + "/";


            logger.debug("User URL : " + htmlOutputPath);

            // Web Application Info            
            HttpServletRequest request = (HttpServletRequest) fCtx.getExternalContext().getRequest();
            String schema = request.getScheme();
            String domain = request.getServerName();
            String serverPort = "" + request.getServerPort();
            String contextPath = request.getContextPath();
            String serverPath = "";
            if (serverPort.equals("80")) {
               serverPath = schema + "://" + domain + contextPath;
            } else {
               serverPath = schema + "://" + domain + ":" + serverPort + contextPath;
            }
            // http://localhost:8080/wsssoapbox/temp/48891D9BFA30011EF6CF85D7D04A5F3D/PTTInfo_48891D9BFA30011EF6CF85D7D04A5F3D.xhtml
            htmlOutputURL = serverPath + htmlOutputPath + fileName + "_" + sessionId + fileExtenstion;

            logger.debug("Path of WSDL file : " + strURL);
            logger.debug("Path of XSTL file : " + xsltURL.toString());
            logger.debug("Path of Result file : " + htmlOutputFile);
            logger.debug("URL of Result URL : " + htmlOutputURL);

            // htmlOutputFile = "c:\\result.xhtml";
            fos = new FileOutputStream(htmlOutputFile);
            StreamResult transResutl = new StreamResult(fos);
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer tranformer = factory.newTransformer(xsltSource);
            tranformer.transform(xmlSource, transResutl);
            wsdlImportForm.setWsdlFile(htmlOutputURL);


         } catch (MalformedURLException ex) {
            ex.printStackTrace();
         } catch (TransformerException ex) {
            ex.printStackTrace();
         } catch (IOException ex) {
            ex.printStackTrace();
         } finally {
            try {
               if (fos != null) {
                  fos.flush();
                  fos.close();
               }

            } catch (IOException ex) {
               ex.printStackTrace();
            }
         }

      } else {
         return;
      }

   }

   /**
    * @return the htmlOutputURL
    */
   public String getHtmlOutputURL() {
      return htmlOutputURL;
   }

   /**
    * @param htmlOutputURL the htmlOutputURL to set
    */
   public void setHtmlOutputURL(String htmlOutputURL) {
      this.htmlOutputURL = htmlOutputURL;
   }
}
