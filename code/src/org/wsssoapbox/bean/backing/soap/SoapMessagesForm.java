package org.wsssoapbox.bean.backing.soap;

import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.util.Iterator;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.xml.namespace.QName;
import javax.xml.soap.MimeHeader;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wsssoapbox.bean.backing.PageViewController;
import org.wsssoapbox.bean.model.soap.SoapRequestBean;
import org.wsssoapbox.bean.model.soap.SoapResponseBean;
import org.wsssoapbox.bean.model.wsdl.EndpointBean;
import org.wsssoapbox.bean.model.wsdl.OperationBean;
import org.wsssoapbox.soap.support.SoapCreator;
import org.wsssoapbox.view.util.ScopeController;
import org.wsssoapbox.xml.util.XMLUtils;

@ManagedBean(name = "soapMessageForm")
@SessionScoped
public class SoapMessagesForm implements Serializable {

   private static final long serialVersionUID = -7158705865526653595L;
   private static final Log logger = LogFactory.getLog(SoapMessagesForm.class);
   private SoapResponseBean soapResponseBean;
   private SOAPMessage soapResponse;
   private SOAPMessage soapRequest;
   // Connection to send messages.
   private static SOAPConnection connection;
   private String endpointURL;
   FacesContext context = FacesContext.getCurrentInstance();

   public void init() {
      logger.debug("start public void init() ");
      logger.debug("end public void init() ");
   }

   public SoapMessagesForm() {
      logger.debug("start public void SoapMessagesForm()  ");

      logger.debug("end public void SoapMessagesForm() ) ");
   }

   public void sendMessage(ActionEvent actionEvent) {

      logger.debug("public void sendMessage(ActionListener evennt)");
      FacesContext context = FacesContext.getCurrentInstance();
      String endpoint = "";
      String requestMessageString = "";
      String msg = "";
      QName operationQName = null;
      SOAPMessage requestMessage = null;
      try {
         EndpointBean endpointBean = (EndpointBean) ScopeController.getSessionMap("endpointBean");
         endpointURL = endpointBean.getAddress();

         requestMessage = (SOAPMessage) ScopeController.getSessionMap("requestMessage");

         OperationBean operationBean = (OperationBean) ScopeController.getSessionMap("operationBean");
         operationQName = operationBean.getQname();
         SoapRequestBean soapRequestBean = (SoapRequestBean) ScopeController.getSessionMap("soapRequestBean");
         requestMessageString = soapRequestBean.getSoapXMLFormat();

      } catch (NullPointerException e) {
         context.addMessage(null, new FacesMessage("Error", "Details : " + e.getMessage()));
         e.printStackTrace();
         logger.debug("Enpoint address connot be null " + e.getMessage());
      }
      logger.debug(" Original soap Request Message : \n" + requestMessageString);
      logger.debug(" Endpoint address : " + endpoint);
      try {

         Iterator names = requestMessage.getMimeHeaders().getAllHeaders();
         logger.debug(" *****************Original from Operation Deatails Form ***************");
         while (names.hasNext()) {
            MimeHeader h = (MimeHeader) names.next();
            logger.debug(" Mime Header before : " + h.getName() + " : " + h.getValue());
         }
         logger.debug(" ************************************");

         soapRequest = SoapCreator.createSOAPMessageFromString(requestMessageString, requestMessage);


         //	message.getMimeHeaders().setHeader("HOST", endpointURL + " HTTP/1.1");

         Iterator n = soapRequest.getMimeHeaders().getAllHeaders();

         logger.debug(" ************** After coverert form User *******************");
         while (n.hasNext()) {
            MimeHeader h = (MimeHeader) n.next();
            logger.debug(" Mime Header after : " + h.getName() + " : " + h.getValue());
         }
         logger.debug(" ************************************");
      } catch (SOAPException e) {

         context.addMessage(null, new FacesMessage("Error", "Details : " + e.getMessage()));
         e.printStackTrace();
      } catch (IOException e) {
         context.addMessage(null, new FacesMessage("Error", "Details : " + e.getMessage()));
         e.printStackTrace();
      }

      try {
         // Create a SOAP connection
         SOAPConnectionFactory scf = SOAPConnectionFactory.newInstance();
         connection = scf.createConnection();

      } catch (Exception e) {
         context.addMessage(null, new FacesMessage("Error", "Unable to open a SOAPConnection : " + e.getMessage()));
         logger.error("Unable to open a SOAPConnection :" + e.getMessage());
      }
      try {

         soapResponse = SoapMessageSender.sent(connection, soapRequest, endpointURL);

         logger.debug(" *************** Response ********************");
         Iterator names = soapResponse.getMimeHeaders().getAllHeaders();
         while (names.hasNext()) {
            MimeHeader h = (MimeHeader) names.next();
            logger.debug(" Mime Header before : " + h.getName() + " : " + h.getValue());
         }
         logger.debug(" ************************************");

      } catch (MalformedURLException e) {
         context.addMessage(null, new FacesMessage("Error", "Details : " + e.getMessage()));
         e.printStackTrace();
      } catch (SOAPException e) {
         context.addMessage(null, new FacesMessage("Error", "Details : " + e.getMessage()));
         e.printStackTrace();
      }

      try {
         msg = XMLUtils.getSoapMessageString(soapResponse);
      } catch (SOAPException e) {
         context.addMessage(null, new FacesMessage("Error", "Details : " + e.getMessage()));
         e.printStackTrace();
      } catch (IOException e) {
         context.addMessage(null, new FacesMessage("Error", "Details : " + e.getMessage()));
         e.printStackTrace();
      }
      logger.debug("Tansform to String format : \n" + msg);

      String unescapeXml = StringEscapeUtils.unescapeXml(msg);

      logger.debug("escapeHtml : \n" + unescapeXml);
      String responsePretyString = XMLUtils.prettyPrint(unescapeXml);
      logger.debug("Ttansform to Prety format \n: " + responsePretyString);
      soapResponseBean = new SoapResponseBean();
      PageViewController pageViewController = (PageViewController) ScopeController.getSessionMap("pageViewController");

      try {
         soapResponseBean = SoapCreator.createSOAPResponseBean(soapResponse, operationQName);
         pageViewController.setResponseOutlineTab(true);
      } catch (UnsupportedOperationException e) {
         // Render Response tab 
         soapResponseBean = new SoapResponseBean();
         pageViewController.setResponseOutlineTab(false);
         context.addMessage(null, new FacesMessage("Error", "Deaatials : " + e.getMessage()));
      } catch (SOAPException e) {

         e.printStackTrace();
      }

      soapResponseBean.setSoapXMLFormat(responsePretyString);
      soapResponseBean.setSoapTextFormat(unescapeXml);
      ScopeController.setSessionMap("soapResponseBean", soapResponseBean);

      logger.debug(" get : value : " + pageViewController.isResponseTab());

      // Render Response tab 
      pageViewController.setResponseTab(true);
      // Display Response Tab the First tab
      pageViewController.setMessageTabIndex(1);
      ScopeController.setSessionMap("pageViewController", pageViewController);

      pageViewController = (PageViewController) ScopeController.getSessionMap("pageViewController");

      logger.debug(" Enable response tab : value : " + pageViewController.isResponseTab());

   }

   public void addMimeHeader(SOAPMessage msg) {
   }

   public String backAction() {
      logger.debug("start public String backAction()");

      //back to importwsdl.xhtml and  show navigator tree layout in ui.xhtml page
      PageViewController pageViewController = (PageViewController) ScopeController.getSessionMap("pageViewController");
      pageViewController.setNavigatorCollapse(false);

      // not render Response tab 
      pageViewController.setResponseTab(false);
      pageViewController.setMessageTabIndex(0);

      ScopeController.setSessionMap("pageViewController", pageViewController);
      logger.debug("end public String backAction()");
      return "/wsdl/importwsdl?faces-redirect=true";

   }
}
