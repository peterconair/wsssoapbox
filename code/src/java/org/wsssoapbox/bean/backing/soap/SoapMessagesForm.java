package org.wsssoapbox.bean.backing.soap;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.xml.namespace.QName;
import javax.xml.soap.AttachmentPart;
import javax.xml.soap.MimeHeader;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.wsssoapbox.bean.backing.PageViewController;
import org.wsssoapbox.bean.model.soap.MimeHeaderBean;
import org.wsssoapbox.bean.model.soap.SoapAttachmentBean;
import org.wsssoapbox.bean.model.soap.SoapRequestBean;
import org.wsssoapbox.bean.model.soap.SoapResponseBean;
import org.wsssoapbox.bean.model.wsdl.EndpointBean;
import org.wsssoapbox.bean.model.wsdl.OperationBean;
import org.wsssoapbox.database.User;
import org.wsssoapbox.view.util.ScopeController;
import org.wsssoapbox.soap.support.SoapCreator;
import org.wsssoapbox.xml.util.XMLUtils;

@ManagedBean(name = "soapMessageForm")
@RequestScoped
public class SoapMessagesForm implements Serializable {

   private static final long serialVersionUID = -7158705865526653595L;
   private static final Logger logger = LoggerFactory.getLogger(SoapMessagesForm.class);
   private String endpointURL;
   private SoapRequestBean soapRequestBean;
   private SoapResponseBean soapResponseBean;
   private QName operationQName;
   private FacesContext context;
   // private SOAPMessage soapResponse;
   // private SOAPMessage soapRequest;

   public SoapMessagesForm() {
      logger.debug("start public void SoapMessagesForm()  ");
      // endpointURL = "";
      //  soapRequest = null;
      //soapResponseBean = new SoapResponseBean();
      //soapRequestBean = new SoapRequestBean();
      //context = FacesContext.getCurrentInstance();

      logger.debug("end public void SoapMessagesForm() ) ");
   }

   public void init() {
      endpointURL = "";
      soapResponseBean = new SoapResponseBean();
      soapRequestBean = new SoapRequestBean();
      context = FacesContext.getCurrentInstance();

      EndpointBean endpointBean = (EndpointBean) ScopeController.getSessionMap("endpointBean");
      endpointURL = (endpointBean != null) ? endpointBean.getAddress() : "";

      OperationBean operationBean = (OperationBean) ScopeController.getSessionMap("operationBean");
      operationQName = operationBean.getQname();

      soapRequestBean = (SoapRequestBean) ScopeController.getSessionMap("soapRequestBean");
      // soapRequest = soapRequestBean.getSOAPRequest();

   }

   public void sendMessage(ActionEvent actionEvent) {
      User user = (User) ScopeController.getSessionMap("user");
      MDC.put("user", "" + user.getId());
      logger.debug("public void sendMessage(ActionListener event)");
      PageViewController pageViewController = (PageViewController) ScopeController.getSessionMap("pageViewController");
      try {

         init();

         long start = System.currentTimeMillis();
         handleRequest();
         handleResponse();
         long end = System.currentTimeMillis();
         String responseTime = "" + (end - start);
         logger.info("Round trip response time = " + (end - start) + " millis");

         // show all attachment
         Iterator it1 = soapRequestBean.getSOAPRequest().getAttachments();
         while (it1.hasNext()) {
            AttachmentPart at = (AttachmentPart) it1.next();
            logger.info("Attachement ID: " + at.getContentId());
            logger.info("Attachement Content Type : " + at.getContentType());
         }

         logger.info("Request MIME Header : ");
         Iterator n = soapRequestBean.getSOAPRequest().getMimeHeaders().getAllHeaders();
         while (n.hasNext()) {
            MimeHeader h = (MimeHeader) n.next();
            logger.info(h.getName() + " : " + h.getValue());
         }


         logger.info("Response MIME Header : ");
         Iterator n1 = soapResponseBean.getSoapResponse().getMimeHeaders().getAllHeaders();
         while (n1.hasNext()) {
            MimeHeader h = (MimeHeader) n1.next();
            logger.info(h.getName() + " : " + h.getValue());
         }
         logger.debug(" ******************************************************");

         if (soapResponseBean != null) {
            soapResponseBean.setResponseTime(responseTime);
            // Display Response Tab the First tab
            pageViewController.setMessageTabIndex(1);
            pageViewController.setMessageResponseTabIndex(0);
            // set visible Response tab 
            pageViewController.setResponseTab(true);
            logger.debug(" Enable response tab : value : " + pageViewController.isResponseTab());
            logger.debug(" Active Table : value : " + pageViewController.getMessageTabIndex());
         }

         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully", "Invoke endpoint " + endpointURL + " is successfully");
         FacesContext.getCurrentInstance().addMessage(null, msg);
         logger.info("Invoke endpoint {} is Successfully", endpointURL);


      } catch (ArrayIndexOutOfBoundsException ex) {
         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ArrayIndexOutOfBoundsException", ex.getMessage());
         FacesContext.getCurrentInstance().addMessage(null, msg);
         logger.error(ex.getMessage(), ex);
         pageViewController.setResponseTab(false);
         ex.printStackTrace();
      } catch (UnsupportedEncodingException ex) {
         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ArrayIndexOutOfBoundsException", ex.getMessage());
         FacesContext.getCurrentInstance().addMessage(null, msg);
         logger.error(ex.getMessage(), ex);
         pageViewController.setResponseTab(false);
         ex.printStackTrace();
      } catch (MalformedURLException ex) {
         context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                 "Error", "Please check you endpoint :" + endpointURL));
         logger.error("MalformedURLException  :" + endpointURL);
         logger.error("Please check the URL  :" + endpointURL);
         pageViewController.setResponseTab(false);
         ex.printStackTrace();
      } catch (SOAPException ex) {
         context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                 "Error", "Connot sent to endpoint :" + endpointURL));
         logger.error("Connot sent to endpoint : " + endpointURL, ex);
         pageViewController.setResponseTab(false);
         ex.printStackTrace();
      } catch (Exception ex) {
         context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                 "Error", ex.getMessage()));
         logger.error("Exception  :" + ex.getMessage(), ex);
         pageViewController.setResponseTab(false);
         ex.printStackTrace();
      }
      logger.debug("end public void sendMessage(ActionListener event)");
   }

   private void handleRequest() throws Exception {
      logger.debug("start private void handleRequest() ");
      String reqMsg = "";

      SOAPMessage soapRequest = soapRequestBean.getSOAPRequest();

      if (soapRequestBean.getSoapAttachments().size() > 0) {
         reqMsg = soapRequestBean.getSoapOriginal();
      } else {
         reqMsg = soapRequestBean.getSoapXMLFormat();
      }
      logger.info(" Original soap Request Message : \n" + reqMsg);


      if (soapRequest.getAttachments() != null) {
         // when user add attachent at the Attachement tab in soapmessage.httml
         if (soapRequestBean.getSoapAttachments().size() > 0) {
            // add attchement to soap request message
            soapRequest = addAttachement(soapRequest, soapRequestBean);
            String message = XMLUtils.getSoapMessageString(soapRequest);
            logger.info("SOAP request message with attachment :");
            soapRequestBean.setSoapXMLFormat(message);
         }
      }





      StringBuilder reqHeader = new StringBuilder();
      // Add POST method base on HTTP/1.1  to header.
      // http://www.kirupafx.com/WebService/TopMovies.asmx
      URL url = new URL(endpointURL);
      String postUrl = url.getPath();
      reqHeader.append("POST ").append(postUrl).append(" HTTP/1.1\r\n");

      String domain = url.getHost();
      //  soapRequest.getMimeHeaders().setHeader("Host", InetAddress.getByName(domain).getHostName());
      reqHeader.append("Host: ").append(InetAddress.getByName(domain).getHostName()).append("\n");

      //check for secure message
      if (!soapRequestBean.isSecureSoapMessage()) {
         reqMsg = soapRequestBean.getSoapXMLFormat();
      } else {
         reqMsg = soapRequestBean.getSecureSoapMessageValue();
      }

      // add only soap part 
      soapRequest = SoapCreator.createSOAPMessageFromString(reqMsg, soapRequest);

      logger.info(" ******************* SOAP Request Message  ***********************");
      logger.info(XMLUtils.getSoapMessageString(soapRequest));

      // Add host name and Content-Length t MIME Header
      String msg = XMLUtils.getSoapMessageString(soapRequest);
      soapRequest.getMimeHeaders().setHeader("Content-Length", "" + msg.codePointCount(0, msg.length()));

      reqHeader.append(getMimeHeaderString(soapRequest));

      // set MIME header for show on raw and xml request message tab.
      logger.debug(" *********************************************************");
      logger.info("Final MIME Header : \n " + reqHeader.toString());
      logger.debug(" *********************************************************");

      // soapRequest.writeTo(System.out);
      System.out.println("");
      soapRequest.saveChanges();
      soapRequestBean.setRequestHeader(reqHeader.toString());
      soapRequestBean.setSOAPRequest(soapRequest);

      logger.info(" *****************************************************************");

      logger.debug("end private void handleRequest() ");

   }

   private void handleResponse() throws Exception {
      logger.debug("start private void handleResponse()  ");

      String resMessageString = "";

      SOAPMessage soapRequest = soapRequestBean.getSOAPRequest();
      // Sent soap message to endpiont
      SOAPMessage soapResponse = SoapMessageSender.send(soapRequest, endpointURL);

      // sent message successfully will update mime header

      if (soapResponse != null) {
         if (soapResponse.saveRequired()) {
            soapResponse.saveChanges();
         }

         try {
            // convert soap message to string.
            resMessageString = XMLUtils.getSoapMessageString(soapResponse);
         } catch (SOAPException e) {
            context.addMessage(null, new FacesMessage("Error", "Details : " + e.getMessage()));
            logger.error("Error  Details : " + e.getMessage(), e);
            e.printStackTrace();
         } catch (IOException e) {
            logger.error("Error  Details : " + e.getMessage(), e);
            context.addMessage(null, new FacesMessage("Error", "Details : " + e.getMessage()));
            e.printStackTrace();
         }
         logger.debug("**************** Response ***********************");
         logger.debug("Tansform to String format : \n" + resMessageString);


         // convert to xml format

         String unescapeXml = StringEscapeUtils.unescapeXml(resMessageString);
//            logger.debug("escapeHtml : \n" + unescapeXml);

         // align format with buatyful xml
         String responsePretyString = XMLUtils.prettyPrint(unescapeXml);
         logger.info("Ttansform to Prety format \n: " + responsePretyString);

         soapResponseBean = new SoapResponseBean();

         try {
            // construct soap reponse bean for display 
            soapResponseBean = SoapCreator.createSOAPResponseBean(soapResponse, operationQName);
            StringBuilder rsHeader = new StringBuilder();

            Map<String, String> mimeHeaders = new HashMap<String, String>();
            logger.info("*********** HTTP Header from Enpoint *************");
            logger.debug("Response MIME Header : ");
            Iterator n1 = soapResponse.getMimeHeaders().getAllHeaders();
            while (n1.hasNext()) {
               MimeHeader h = (MimeHeader) n1.next();
               mimeHeaders.put(h.getName(), h.getValue());
            }

            rsHeader = getResponeMimeHeaderString(soapResponse);
            logger.debug(rsHeader.toString());
            soapResponseBean.setMimeHeaders(mimeHeaders);
            soapResponseBean.setResponseHeader(rsHeader.toString());

            logger.info(" *************************************************");
            logger.info(" **************Attachement Response **************");
            List<SoapAttachmentBean> attachementBeanList = new ArrayList<SoapAttachmentBean>();
            SoapAttachmentBean soapAttachmentBean = null;

            Iterator iteratorAttache = soapResponse.getAttachments();
            while (iteratorAttache.hasNext()) {
               try {
                  soapAttachmentBean = new SoapAttachmentBean();
                  AttachmentPart attatchement = (AttachmentPart) iteratorAttache.next();
                  soapAttachmentBean.setContentID(attatchement.getContentId());
                  soapAttachmentBean.setContentType(attatchement.getContentType());
                  soapAttachmentBean.setType(attatchement.getContentType());
                  if (attatchement.getContentLocation() == null) {
                     soapAttachmentBean.setName("Untitle");
                  } else {
                     soapAttachmentBean.setName(attatchement.getContentLocation());
                  }
                  soapAttachmentBean.setSize(attatchement.getSize());
                  soapAttachmentBean.setInputStream(attatchement.getRawContent());
                  attachementBeanList.add(soapAttachmentBean);

                  logger.info("Contenet ID : " + soapAttachmentBean.getContentID());
                  logger.info("Contenet Type: " + soapAttachmentBean.getContentType());
                  logger.info("Contenet Size : " + soapAttachmentBean.getSize());
               } catch (SOAPException ex) {
                  logger.error(ex.getMessage());
               }
            }
            soapResponseBean.setSoapAttachments(attachementBeanList);

            // try to print attachment only text format
            Iterator iterator = soapResponse.getAttachments();
            while (iterator.hasNext()) {
               AttachmentPart attachment = (AttachmentPart) iterator.next();
               String id = attachment.getContentId();
               String type = attachment.getContentType();
               logger.info("Attachment " + id
                       + " has content type " + type);
               if ("text/plain".equals(type)) {
                  Object content = attachment.getContent();
                  logger.info("Attachment "
                          + "contains:\n" + content);
               }
            }
            logger.info(" ************************************");

         } catch (UnsupportedOperationException e) {
            context.addMessage(null, new FacesMessage("Error", "Deaatials : " + e.getMessage()));
            logger.error(e.getMessage(), e);
            e.printStackTrace();
         }
         // for display response message in Response tab
         soapResponseBean.setSoapResponse(soapResponse);
         soapResponseBean.setSoapXMLFormat(new String(responsePretyString.getBytes(), "UTF-8"));
         soapResponseBean.setSoapTextFormat(new String(resMessageString.getBytes(), "UTF-8"));
         ScopeController.setSessionMap("soapResponseBean", soapResponseBean);
      }
      logger.debug("end private void handleResponse()  ");
   }

   private SOAPMessage addUserMimeHeader(SOAPMessage soapRequest, SoapRequestBean soapRequestBean) {
      logger.debug("start private SOAPMessage addUserMimeHeader(SOAPMessage soapRequest, SoapRequestBean soapRequestBean) ");
      for (MimeHeaderBean m : soapRequestBean.getUserMimeHeaderLists()) {
         // Exits mime header or not if not will add new one.
         if (soapRequest.getMimeHeaders().getHeader(m.getHeader()) == null) {
            soapRequest.getMimeHeaders().addHeader(m.getHeader(), m.getValue());
         }
         logger.info("adding User Mimeheader : " + m.getHeader() + " : " + m.getValue());
      }
      logger.debug("end private SOAPMessage addUserMimeHeader(SOAPMessage soapRequest, SoapRequestBean soapRequestBean) ");
      return soapRequest;
   }

   private SOAPMessage addAttachement(SOAPMessage soapRequest, SoapRequestBean soapRequestBean) throws SOAPException {

      logger.debug("start private SOAPMessage addAttachement(SOAPMessage soapRequest, SoapRequestBean soapRequestBean)");
      logger.info("Attachement size :" + soapRequestBean.getSoapAttachments().size());
      logger.info("***** attachment adding ***************************");

      for (SoapAttachmentBean sab : soapRequestBean.getSoapAttachments()) {
         AttachmentPart attachement = soapRequest.createAttachmentPart();
         attachement.setContentId(sab.getContentID());
         attachement.setRawContent(sab.getInputStream(), sab.getContentType());
         logger.info("adding attachment : " + sab.getContentID());
         logger.info("Content :" + sab.getContentID());
         logger.info("Content Type :" + sab.getContentType());
         if (sab.isBase64()) {
            attachement.setBase64Content(sab.getInputStream(), sab.getContentType());
         }
         soapRequest.addAttachmentPart(attachement);

      }




      logger.debug("end private SOAPMessage addAttachement(SOAPMessage soapRequest, SoapRequestBean soapRequestBean)");
      return soapRequest;

   }

   public String backAction() {
      logger.debug("start public String backAction()");

      //back to importwsdl.xhtml and  show navigator tree layout in ui.xhtml page
      PageViewController pageViewController = (PageViewController) ScopeController.getSessionMap("pageViewController");
      pageViewController.setNavigator(true);
      pageViewController.setNavigatorCollapse(false);

      // no render Response tab 
      pageViewController.setResponseTab(false);
      pageViewController.setMessageTabIndex(0);
      pageViewController.setMessageRequestTabIndex(0);
      pageViewController.setMessageResponseTabIndex(0);

      ScopeController.setSessionMap("soapResponseBean", null);
      ScopeController.setSessionMap("soapRequestBean", null);
      // clear respone time in response tab.
      //sResBean = null;
      //  sResBean.setResponseTime(null);

      logger.debug("end public String backAction()");

      return "/wsdl/importwsdl?faces-redirect=true";
   }

   public StringBuilder getMimeHeaderString(SOAPMessage soapRequest) {

      Iterator it = soapRequest.getMimeHeaders().getAllHeaders();
      StringBuilder reqHeader = new StringBuilder();
      while (it.hasNext()) {
         MimeHeader h = (MimeHeader) it.next();
         reqHeader.append(h.getName()).append(" : ").append(h.getValue()).append("\n");
      }
      return reqHeader;
   }

   public StringBuilder getResponeMimeHeaderString(SOAPMessage soap) {

      Iterator it = soap.getMimeHeaders().getAllHeaders();
      StringBuilder reqHeader = new StringBuilder();
      while (it.hasNext()) {
         MimeHeader h = (MimeHeader) it.next();
         String temp = "";
         if (soap.getMimeHeaders().getHeader(h.getName()).length > 1) {
            String str[] = soap.getMimeHeaders().getHeader(h.getName());
            for (int i = 0; i < str.length; i++) {
               if (i == str.length - 1) {
                  temp += str[i];
               } else {
                  temp += str[i] + ", ";
               }
            }
            if (reqHeader.toString().indexOf(temp) == -1) {
               reqHeader.append(h.getName()).append(" : ").append(temp).append("\n");
            }
         } else {
            reqHeader.append(h.getName()).append(" : ").append(h.getValue()).append("\n");
         }
      }

      return reqHeader;
   }
}
