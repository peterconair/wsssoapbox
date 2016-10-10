package org.wsssoapbox.bean.backing.soap;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import javax.xml.namespace.QName;
import javax.xml.transform.TransformerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.wsssoapbox.bean.model.soap.SoapRequestBean;
import org.wsssoapbox.view.util.ScopeController;
import org.wsssoapbox.xml.util.XMLUtils;

@ManagedBean(name = "soapRequestForm")
@ViewScoped
public class SoapRequestFormTab implements Serializable {

   private static final long serialVersionUID = 1L;
   private static final Logger logger = LoggerFactory.getLogger(SoapRequestFormTab.class);
   SoapRequestBean soapRequestBean;

   public SoapRequestFormTab() {
      logger.debug("start public SoapRequestForm()");

      soapRequestBean = (SoapRequestBean) ScopeController.getSessionMap("soapRequestBean");

      logger.debug("end public SoapRequestForm()");
   }

   public void submitParamValue(QName qName, String value) throws TransformerException {

      logger.info("qName : " + qName);
      logger.info("Value : " + value);

      //<name>en</name> <name>xxx</name>
      //<tns:name>en</tns:name>
      String name = qName.getLocalPart();
      String prefix = qName.getPrefix();

      logger.debug("Tag Name : " + name);
      logger.debug("Prefix : " + prefix);

      
      String originallXML = "";
      if (!soapRequestBean.isSecureSoapMessage()) {
         originallXML = soapRequestBean.getSoapXMLFormat();
      } else {
         originallXML = soapRequestBean.getSecureSoapMessageValue();
      }

      //    String originallXML = soapRequestBean.getSoapOriginal();
      String modifieldlXML = "";
      logger.debug("QUALIFIED : " + soapRequestBean.getElementFormDefault());

      /*
      // Un QUALIFIED
      if (!soapRequestBean.getElementFormDefault().equalsIgnoreCase("QUALIFIED")) {
      modifieldlXML = originallXML.replaceAll(name + ">([^ ]+)</" + name + ">", name + ">" + value + "</" + name + ">");
      } else {// QUALIFIED
      modifieldlXML = originallXML.replaceAll(name + ">([^ ]+)" + name + ">", name + ">" + value + "</" + prefix + ":" + name + ">");
      }
       */

      // convert String to Dom 
      Document doc = XMLUtils.createDocumentFromString(originallXML);

      // find Node represent node that modifying
      NodeList nodeList = doc.getElementsByTagNameNS(qName.getNamespaceURI(), qName.getLocalPart());
      Node node = null;
      for (int i = 0; i < nodeList.getLength(); i++) {
         node = nodeList.item(i);
         // replace new value to the node.
         node.setTextContent(value);
      }

      // logger.debug("Doc  : " + XMLUtils.PrettyDocumentToString(doc));
      // logger.debug("Origianl Message : " + modifieldlXML);
      // convert Dom to String 
      modifieldlXML = XMLUtils.PrettyDocumentToString(doc);
      // set to reqeust message bean.


      if (!soapRequestBean.isSecureSoapMessage()) {

         if (!soapRequestBean.isSoapAttachment()) {
            soapRequestBean.setSoapXMLFormat(modifieldlXML);
            soapRequestBean.setSoapOriginal(modifieldlXML);
         } else {
            soapRequestBean.setSoapAttachmentValue(modifieldlXML);
         }
      } else {
         soapRequestBean.setSecureSoapMessageValue(modifieldlXML);
      }

      //  logger.debug("Modified Message : " + soapRequestBean.getSoapXMLFormat());

   }
}
