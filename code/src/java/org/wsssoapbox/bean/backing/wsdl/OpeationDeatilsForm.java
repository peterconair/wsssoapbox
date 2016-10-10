package org.wsssoapbox.bean.backing.wsdl;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.faces.application.FacesMessage;
import org.wsssoapbox.xml.util.XMLUtils;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeader;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import org.slf4j.Logger;

import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.api.Description;
import org.slf4j.LoggerFactory;
import org.wsssoapbox.bean.backing.PageViewController;
import org.wsssoapbox.bean.model.wsdl.SoapBindingBean;
import org.wsssoapbox.bean.model.soap.SoapRequestBean;
import org.wsssoapbox.bean.model.wsdl.BindingBean;
import org.wsssoapbox.bean.model.wsdl.InterfaceBean;
import org.wsssoapbox.bean.model.wsdl.OperationBean;
import org.wsssoapbox.services.soap.ISoapModelBo;
import org.wsssoapbox.services.soap.impl.SoapModelBo;
import org.wsssoapbox.view.util.ScopeController;
import org.wsssoapbox.soap.support.SoapCreator;
import org.xml.sax.SAXException;

@ManagedBean(name = "operationDeailsForm")
@SessionScoped
public class OpeationDeatilsForm implements Serializable {
   
   private static final Logger logger = LoggerFactory.getLogger(OpeationDeatilsForm.class);
   private static final long serialVersionUID = 1L;
   private OperationBean operationBean;
   private InterfaceBean interfaceBean;
   private BindingBean bindingBean;
   private SoapBindingBean soapBindingBean;
   private SoapRequestBean soapRequestBean;
   private Map<String, String> mimeHeaders;
   
   public OpeationDeatilsForm() {
      logger.debug(" >>>>>>>>>>>>> StartOpeationDeatilsForm()");
      updateContent();
      logger.debug(" >>>>>>>>>>>>> End OpeationDeatilsForm() <<<<<<<<<<<<<<<< ");
   }
   
   public void updateContent() {
      
      soapBindingBean = new SoapBindingBean();
      
      operationBean = new OperationBean();
      operationBean = (OperationBean) ScopeController.getSessionMap("operationBean");
      
      interfaceBean = new InterfaceBean();
      interfaceBean = (InterfaceBean) ScopeController.getSessionMap("interfaceBean");
      
      bindingBean = new BindingBean();
      bindingBean = (BindingBean) ScopeController.getSessionMap("bindingBean");
      
      soapRequestBean = new SoapRequestBean();
      
      logger.debug("Operation Name     : " + operationBean.getName());
      logger.debug("Operation QName    : " + operationBean.getQname());
      logger.debug("Operation Signature: " + operationBean.getSignature());
      logger.debug("OperationInput     : " + operationBean.getInput());
      
   }
   
   public String createSOAPRequest() throws SOAPException, IOException, ParserConfigurationException, SAXException,
           TransformerFactoryConfigurationError, TransformerException {
      logger.debug(" >>>>>>>>>>>>> Start public String submit()");
      
      SOAPMessage requestMessage = null;

      // get new data from session and initiate page
      updateContent();
      
      requestMessage = MessageFactory.newInstance().createMessage();
      
      Description desc = (Description) ScopeController.getSessionMap("desc");
      //  ISoapModelDAO soapModelDAO = new SoapModelDAO();
      ISoapModelBo soapModelBo = new SoapModelBo();
      
      QName interfaceQName = interfaceBean.getQname();
      QName operationQName = operationBean.getQname();
      QName bindingQName = bindingBean.getQname();
      
      logger.debug(" Operation QName : " + operationQName);
      logger.debug(" Interface QName : " + interfaceQName);
      logger.debug(" Binding QName : " + bindingQName);
      
      try {
         //    soapBindingBean = soapModelDAO.createSoapBindingBeanDoc(desc, bindingQName, interfaceQName, operationQName);       
         soapBindingBean = soapModelBo.createSoapBindingBean(desc, bindingQName, interfaceQName, operationQName);
         
         logger.debug(" SOAP Version  : " + soapBindingBean.getSoapVersion());

         // hide navigator tree layout in ui.xhtml page
         PageViewController pageViewController = (PageViewController) ScopeController.getSessionMap("pageViewController");
         // hide navigator layout
         pageViewController.setNavigator(false);
         pageViewController.setNavigatorCollapse(true);
         
         pageViewController.setMessageTabIndex(0);         
         pageViewController.setMessageRequestTabIndex(0);
         pageViewController.setResponseTab(false);
         pageViewController.setMessageResponseTabIndex(0);
         
         
      } catch (NullPointerException ex) {
         logger.error("Not suppored WSDL :" + ex.getMessage());
         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Not suppored WSDL ", ex.getMessage());
         FacesContext.getCurrentInstance().addMessage(null, msg);
         ex.printStackTrace();
      } catch (XmlException ex) {
         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "XmlException ", ex.getMessage());
         FacesContext.getCurrentInstance().addMessage(null, msg);
         ex.printStackTrace();
         logger.error(" XmlException : " + ex.getMessage());
      }
      
      
      if (soapBindingBean != null) {

         // create soap message using saaj 
         requestMessage = SoapCreator.createSOAPMessage(requestMessage, soapBindingBean);
         
         Iterator<?> names = requestMessage.getMimeHeaders().getAllHeaders();
         
         mimeHeaders = new HashMap<String, String>();
         logger.debug(" *****************Original from Operation Deatails Form ***************");
         while (names.hasNext()) {
            MimeHeader h = (MimeHeader) names.next();
            mimeHeaders.put(h.getName(), h.getValue());
            logger.debug(" Mime Header before : " + h.getName() + " : " + h.getValue());
         }
         logger.debug(" ************************************");
         
         logger.debug(" Request Mesaage Form Binding : ");
         XMLUtils.print(requestMessage);

         // convert message to String
         String soapRequtestTextFormat = XMLUtils.getSoapMessageString(requestMessage);

         // convert to xml format
         String pretyFormat = XMLUtils.prettyPrint(soapRequtestTextFormat);
         
         logger.debug("SOAP message created : \n" + pretyFormat);
         soapRequestBean = SoapCreator.createSOAPRequestBean(requestMessage, operationQName);
         soapRequestBean.setSOAPRequest(requestMessage);
         soapRequestBean.setSoapOriginal(pretyFormat);
         soapRequestBean.setSoapXMLFormat(pretyFormat);
         soapRequestBean.setSoapTextFormat(soapRequtestTextFormat);
         soapRequestBean.setMimeHeaders(mimeHeaders);
         soapRequestBean.setElementFormDefault(soapBindingBean.getElementFormDefault());
         soapRequestBean.getSoapPart().getSoapEnvelope().getSoapBody().getOperation().setParameters(soapBindingBean.getOperation().getParameters());

         // set soapRequestBean to session
         ScopeController.setSessionMap("soapRequestBean", soapRequestBean);
         logger.debug(" >>>>>>>>>>>>> End public String submit() <<<<<<<<<<<<<<<< ");
         return "/soap/soapmessages?faces-redirect=true";
      } else {
         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Not suppored WSDL ", null);
         FacesContext.getCurrentInstance().addMessage(null, msg);
         logger.error("Not suppored WSDL");
         return "/wsdl/impportwsdl?faces-redirect=true";
      }
      
      
   }
  
}
