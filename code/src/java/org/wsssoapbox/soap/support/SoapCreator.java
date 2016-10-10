package org.wsssoapbox.soap.support;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeader;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.Name;
import javax.xml.soap.Node;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.slf4j.Logger;
import org.ow2.easywsdl.schema.org.w3._2001.xmlschema.FormChoice;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.wsssoapbox.bean.model.soap.SoapAttachmentBean;
import org.wsssoapbox.bean.model.soap.SoapBodyBean;
import org.wsssoapbox.bean.model.soap.SoapEnvelopeBean;
import org.wsssoapbox.bean.model.soap.SoapFaultBean;
import org.wsssoapbox.bean.model.soap.SoapHeaderBean;
import org.wsssoapbox.bean.model.soap.SoapRequestBean;
import org.wsssoapbox.bean.model.soap.SoapOperationBean;
import org.wsssoapbox.bean.model.soap.SoapPartBean;
import org.wsssoapbox.bean.model.wsdl.SoapBindingBean;
import org.wsssoapbox.bean.model.soap.SoapResponseBean;

import org.wsssoapbox.bean.model.soap.SoapParameterBean;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class SoapCreator {

   private static final Logger logger = LoggerFactory.getLogger(SoapCreator.class);
   private MessageFactory mSOAPMsgFactory;

   public SoapCreator() throws SOAPException {
   }

   public SOAPMessage createEmptySOAPMessage() throws SOAPException {
      // default soap version 1.1
      SOAPMessage theMsg = mSOAPMsgFactory.createMessage();

      SOAPPart thePart = theMsg.getSOAPPart();
      SOAPEnvelope theEnvelope = thePart.getEnvelope();
      SOAPBody theBody = theEnvelope.getBody();
      SOAPHeader theHeader = theEnvelope.getHeader();

      logger.debug("The long way:");
      logger.debug(" The SOAP part: " + thePart);
      logger.debug(" The SOAP envelope: " + theEnvelope);
      logger.debug(" The SOAP body: " + theBody);
      logger.debug(" The SOAP header: " + theHeader);

      theBody = theMsg.getSOAPBody();
      theHeader = theMsg.getSOAPHeader();

      logger.debug("The short way:");
      logger.debug(" The SOAP body: " + theBody);
      logger.debug(" The SOAP header: " + theHeader);

      return theMsg;
   }

   public static SOAPMessage createSOAPMessage(SOAPMessage msg, SoapBindingBean soapBindingBean) throws SOAPException,
           IOException {

      MessageFactory messageFactory = null;
      MimeHeaders mimeHeaders = null;
      String soapVersion = soapBindingBean.getSoapVersion();
      if (soapVersion.equals("1.2")) { // check soap version before create soap message
         messageFactory = MessageFactory.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL);
         //headers.addHeader(name, value);
      } else { // default version 1.1
         messageFactory = MessageFactory.newInstance();
      }

      msg = messageFactory.createMessage();
      SOAPEnvelope envelope = msg.getSOAPPart().getEnvelope();

      SOAPPart soapPart = msg.getSOAPPart();
      SOAPHeader soapHeader = msg.getSOAPHeader();
      mimeHeaders = msg.getMimeHeaders();

      // if soapActoion not have value will set to "" for compile with WS-I 1.0 
      String SOAPAction = (soapBindingBean.getSoapAction() == null || soapBindingBean.getSoapAction().equals("")) ? "\"\"" : soapBindingBean.getSoapAction();

      logger.debug("************ Mime Header ******************");
      // add soap action to Mime header 

      mimeHeaders.addHeader("SOAPAction", SOAPAction);
      mimeHeaders.setHeader("User-Agent", SoapConstants.PARA_DEFAULT_USER_AGENT);

      Iterator interator = mimeHeaders.getAllHeaders();
      while (interator.hasNext()) {
         MimeHeader m = (MimeHeader) interator.next();
         logger.debug(m.getName() + " : " + m.getValue());
      }
      logger.debug("************ End Meme Header ******************");

      logger.debug("Envelope prefix : " + envelope.getPrefix());

      envelope.removeNamespaceDeclaration(envelope.getPrefix());
      // add namesapces to envelope
      Map<String, String> namesapces = soapBindingBean.getNamespaces();
      Iterator it = namesapces.entrySet().iterator();
      while (it.hasNext()) {
         Map.Entry pairs = (Map.Entry) it.next();
         logger.debug(pairs.getKey() + " = " + pairs.getValue());
         String envelopeURI = (String) pairs.getValue();
         String envelopePrefix = (String) pairs.getKey();
         envelope.addNamespaceDeclaration(envelopePrefix, envelopeURI);
      }

      logger.debug("XmlStandalone : " + soapBindingBean.isXmlStandalone());
      if (soapBindingBean.isXmlStandalone()) {
         soapPart.setXmlStandalone(true);
      }
      logger.debug("Soap Header : " + soapBindingBean.isHeader());

      if (!soapBindingBean.isHeader()) {
         soapHeader.detachNode();
      } else { //if message contain the header 
         msg = addSOAPHeaderElement(msg, soapBindingBean);
      }
      if (soapBindingBean.isFault()) {
         msg = addSOAPFaultElement(msg, soapBindingBean);
      }

      msg = addSOAPBodyElement(msg, soapBindingBean);

      return msg;
   }

   public static SoapRequestBean createSOAPRequestBean(SOAPMessage msg, QName operationQName) throws SOAPException,
           IOException {

      SoapRequestBean soapRequestBean = new SoapRequestBean();
      SoapPartBean soapPartBean = new SoapPartBean();

      soapPartBean = addSOAPPartElementBean(msg, operationQName);

      List<SoapAttachmentBean> soapAttachmentBeanList = new ArrayList<SoapAttachmentBean>();

      // One Message has one Part
      soapRequestBean.setSoapPart(soapPartBean);

      // One Message has many Attachments
      soapRequestBean.setSoapAttachments(soapAttachmentBeanList);

      return soapRequestBean;
   }

   private static SoapPartBean addSOAPPartElementBean(SOAPMessage msg, QName operationQName) throws SOAPException {
      SoapPartBean soapPartBean = new SoapPartBean();
      SoapEnvelopeBean soapEnvelopeBean = new SoapEnvelopeBean();

      SOAPPart soapPart = msg.getSOAPPart();

      logger.debug(" ******************  Soap Part : ********************");
      logger.debug("  Soap Part getBaseURI: " + soapPart.getBaseURI());
      logger.debug("  Soap Part getContentId: " + soapPart.getContentId());
      logger.debug("  Soap Part getContentLocation: " + soapPart.getContentLocation());
      logger.debug("  Soap Part getDocumentURI: " + soapPart.getDocumentURI());
      logger.debug("  Soap Part getInputEncoding: " + soapPart.getInputEncoding());
      logger.debug("  Soap Part getNamespaceURI: " + soapPart.getNamespaceURI());
      logger.debug("  Soap Part getNodeName: " + soapPart.getNodeName());
      logger.debug("  Soap Part getNodeValue: " + soapPart.getNodeValue());
      logger.debug("  Soap Part getPrefix: " + soapPart.getPrefix());
      logger.debug("  Soap Part getStrictErrorChecking: " + soapPart.getStrictErrorChecking());
      logger.debug("  Soap Part getValue: " + soapPart.getValue());
      logger.debug("  Soap Part getXmlEncoding: " + soapPart.getXmlEncoding());
      logger.debug("  Soap Part getXmlStandalone: " + soapPart.getXmlStandalone());
      logger.debug("  Soap Part getXmlVersion: " + soapPart.getXmlVersion());
      logger.debug("  Soap Part getAllMimeHeaders: " + soapPart.getAllMimeHeaders());

      soapPartBean.setBaseURI(soapPart.getBaseURI());
      soapPartBean.setContentId(soapPart.getContentId());
      soapPartBean.setContentLocation(soapPart.getContentLocation());
      soapPartBean.setDocumentURI(soapPart.getDocumentURI());
      soapPartBean.setInputEncoding(soapPart.getInputEncoding());
      soapPartBean.setNamespaceURI(soapPart.getNamespaceURI());
      soapPartBean.setNodeName(soapPart.getNodeName());
      soapPartBean.setNodeType(soapPart.getNodeType());
      soapPartBean.setNodeValue(soapPart.getNodeValue());
      soapPartBean.setPrefix(soapPart.getPrefix());
      soapPartBean.setStrictErrorChecking(soapPart.getStrictErrorChecking());
      soapPartBean.setTextContent(soapPart.getTextContent());
      soapPartBean.setValue(soapPart.getValue());
      soapPartBean.setXmlEncoding(soapPart.getXmlEncoding());
      soapPartBean.setXmlStandalone(soapPart.getXmlStandalone());
      soapPartBean.setXmlVersion(soapPart.getXmlVersion());
      soapPartBean.setAllMimeHeaders(soapPart.getAllMimeHeaders());

      // One Part has one Envelope
      soapEnvelopeBean = addSOAPEnvelopeElementBean(msg, operationQName);
      soapPartBean.setSoapEnvelope(soapEnvelopeBean);

      return soapPartBean;
   }

   private static SoapEnvelopeBean addSOAPEnvelopeElementBean(SOAPMessage msg, QName operationQName)
           throws SOAPException {

      SoapEnvelopeBean soapEnvelopeBean = new SoapEnvelopeBean();
      SoapBodyBean soapBodyBean = new SoapBodyBean();
      SoapHeaderBean soapHeaderBean = new SoapHeaderBean();

      SOAPEnvelope soapEnvelope = msg.getSOAPPart().getEnvelope();

      logger.debug(" ******************  Soap Envelope : ********************");

      logger.debug("  Soap Envelope getBaseURI: " + soapEnvelope.getBaseURI());
      logger.debug("  Soap Envelope getElementName: " + soapEnvelope.getElementName());
      logger.debug("  Soap Envelope getElementQName: " + soapEnvelope.getElementQName());
      logger.debug("  Soap Envelope getEncodingStyle: " + soapEnvelope.getEncodingStyle());
      logger.debug("  Soap Envelope getLocalName: " + soapEnvelope.getLocalName());
      logger.debug("  Soap Envelope getNamespaceURI: " + soapEnvelope.getNamespaceURI());
      logger.debug("  Soap Envelope getNodeName: " + soapEnvelope.getNodeName());
      logger.debug("  Soap Envelope getNodeValue: " + soapEnvelope.getNodeValue());
      logger.debug("  Soap Envelope getPrefix: " + soapEnvelope.getPrefix());
      logger.debug("  Soap Envelope getValue: " + soapEnvelope.getValue());
      logger.debug("  Soap Envelope getNodeType: " + soapEnvelope.getNodeType());
      logger.debug("  Soap Envelope getTagName: " + soapEnvelope.getTagName());
      logger.debug("  Soap Envelope getTextContent: " + soapEnvelope.getTextContent());

      soapEnvelopeBean.setBaseURI(soapEnvelope.getBaseURI());
      soapEnvelopeBean.setElementName(soapEnvelope.getElementName());
      soapEnvelopeBean.setElementQName(soapEnvelope.getElementQName());
      soapEnvelopeBean.setEncodingStyle(soapEnvelope.getEncodingStyle());
      soapEnvelopeBean.setLocalName(soapEnvelope.getLocalName());
      soapEnvelopeBean.setNamespaceURI(soapEnvelope.getNamespaceURI());
      soapEnvelopeBean.setNodeName(soapEnvelope.getNodeName());
      soapEnvelopeBean.setNodeType(soapEnvelope.getNodeType());
      soapEnvelopeBean.setNodeValue(soapEnvelope.getNodeValue());
      soapEnvelopeBean.setPrefix(soapEnvelope.getPrefix());
      soapEnvelopeBean.setTagName(soapEnvelope.getTagName());
      soapEnvelopeBean.setTextContent(soapEnvelope.getTextContent());
      soapEnvelopeBean.setValue(soapEnvelope.getValue());

      // One Envelope has one Header
      soapHeaderBean = addSOAPHeaderElementBean(msg);
      soapEnvelopeBean.setSoapHeader(soapHeaderBean);

      // One Envelope has one body
      soapBodyBean = addSOAPBodyElementBean(msg, operationQName);
      soapEnvelopeBean.setSoapBody(soapBodyBean);

      return soapEnvelopeBean;
   }

   private static SoapHeaderBean addSOAPHeaderElementBean(SOAPMessage msg) throws SOAPException {
      SoapHeaderBean soapHeaderBean = new SoapHeaderBean();
      SOAPHeader soapHeader = msg.getSOAPHeader();

      logger.debug(" ******************  Soap Header : ********************");
      logger.debug("  Soap Header : " + soapHeader);
      if (soapHeader != null) {
         logger.debug("  Soap Header getBaseURI: " + soapHeader.getBaseURI());
         logger.debug("  Soap Header getElementName: " + soapHeader.getElementName());
         logger.debug("  Soap Header getElementQName: " + soapHeader.getElementQName());
         logger.debug("  Soap Header getEncodingStyle: " + soapHeader.getEncodingStyle());
         logger.debug("  Soap Header getLocalName: " + soapHeader.getLocalName());
         logger.debug("  Soap Header getNamespaceURI: " + soapHeader.getNamespaceURI());
         logger.debug("  Soap Header getNodeName: " + soapHeader.getNodeName());
         logger.debug("  Soap Header getNodeValue: " + soapHeader.getNodeValue());
         logger.debug("  Soap Header getPrefix: " + soapHeader.getPrefix());
         logger.debug("  Soap Header getValue: " + soapHeader.getValue());
         logger.debug("  Soap Header getNodeType: " + soapHeader.getNodeType());
         logger.debug("  Soap Header getTagName: " + soapHeader.getTagName());
         logger.debug("  Soap Header getTextContent: " + soapHeader.getTextContent());

      }
      if (soapHeader != null) {
         soapHeaderBean.setPrefix(soapHeader.getPrefix());
         soapHeaderBean.setValue(soapHeader.getValue());
         soapHeaderBean.setElementName(soapHeader.getElementName());
         soapHeaderBean.setBaseURI(soapHeader.getBaseURI());
         soapHeaderBean.setElementQName(soapHeader.getElementQName());
         soapHeaderBean.setEncodingStyle(soapHeader.getEncodingStyle());
         soapHeaderBean.setLocalName(soapHeader.getLocalName());
         soapHeaderBean.setNamespaceURI(soapHeader.getNamespaceURI());
         soapHeaderBean.setNodeName(soapHeader.getNodeName());
         soapHeaderBean.setValue(soapHeader.getValue());
         soapHeaderBean.setSetNodeType(soapHeader.getNodeType());
         soapHeaderBean.setTagName(soapHeader.getTagName());
         soapHeaderBean.setTextContent(soapHeader.getTextContent());
      }
      return soapHeaderBean;
   }

   private static SOAPMessage addSOAPHeaderElement(SOAPMessage msg, SoapBindingBean soapRequest) throws SOAPException,
           IOException {
      SOAPHeader header = msg.getSOAPHeader();

      return msg;
   }

   private static SoapBodyBean addSOAPBodyElementBean(SOAPMessage msg, QName operationQName) throws SOAPException {
      SoapBodyBean soapBodyBean = new SoapBodyBean();
      SoapFaultBean soapFaultBean = new SoapFaultBean();
      SoapOperationBean soapOperationBean = new SoapOperationBean();

      SOAPBody soapBody = msg.getSOAPBody();

      logger.debug(" ******************  Soap Body : ********************");
      logger.debug("  Soap Body : " + soapBody);
      if (soapBody != null) {
         logger.debug("  Soap Body getBaseURI: " + soapBody.getBaseURI());
         logger.debug("  Soap Body getElementName: " + soapBody.getElementName());
         logger.debug("  Soap Body getElementQName: " + soapBody.getElementQName());
         logger.debug("  Soap Body getEncodingStyle: " + soapBody.getEncodingStyle());
         logger.debug("  Soap Body getLocalName: " + soapBody.getLocalName());
         logger.debug("  Soap Body getNamespaceURI: " + soapBody.getNamespaceURI());
         logger.debug("  Soap Body getNodeName: " + soapBody.getNodeName());
         logger.debug("  Soap Body getNodeValue: " + soapBody.getNodeValue());
         logger.debug("  Soap Body getPrefix: " + soapBody.getPrefix());
         logger.debug("  Soap Body getValue: " + soapBody.getValue());
         logger.debug("  Soap Body getNodeType: " + soapBody.getNodeType());
         logger.debug("  Soap Body getTagName: " + soapBody.getTagName());
         logger.debug("  Soap Body getTextContent: " + soapBody.getTextContent());

         logger.debug("  Soap Body getAllAttributes: " + soapBody.getAllAttributes());
         logger.debug("  Soap Body getAllAttributesAsQNames: " + soapBody.getAllAttributesAsQNames());
         logger.debug("  Soap Body getAttributes: " + soapBody.getAttributes());
         logger.debug("  Soap Body getChildElements: " + soapBody.getChildElements());
         logger.debug("  Soap Body getChildNodes: " + soapBody.getChildNodes());
         logger.debug("  Soap Body getFault: " + soapBody.getFault());
         logger.debug("  Soap Body getFirstChild: " + soapBody.getFirstChild());
         logger.debug("  Soap Body getLastChild: " + soapBody.getLastChild());
         logger.debug("  Soap Body getNextSibling: " + soapBody.getNextSibling());
         logger.debug("  Soap Body getOwnerDocument: " + soapBody.getOwnerDocument());
         logger.debug("  Soap Body getParentElement: " + soapBody.getParentElement());
         logger.debug("  Soap Body getTextContent: " + soapBody.getPreviousSibling());
         logger.debug("  Soap Body getPreviousSibling: " + soapBody.getSchemaTypeInfo());
         logger.debug("  Soap Body getVisibleNamespacePrefixes: " + soapBody.getVisibleNamespacePrefixes());

         soapBodyBean.setBaseURI(soapBody.getBaseURI());
         soapBodyBean.setElementName(soapBody.getElementName());
         soapBodyBean.setElementQName(soapBody.getElementQName());
         soapBodyBean.setEncodingStyle(soapBody.getEncodingStyle());
         soapBodyBean.setLocalName(soapBody.getLocalName());
         soapBodyBean.setNamespaceURI(soapBody.getNamespaceURI());
         soapBodyBean.setNodeName(soapBody.getNodeName());
         soapBodyBean.setNodeType(soapBody.getNodeType());
         soapBodyBean.setNodeValue(soapBody.getNodeValue());
         soapBodyBean.setPrefix(soapBody.getPrefix());
         soapBodyBean.setTagName(soapBody.getTagName());
         soapBodyBean.setTextContent(soapBody.getTextContent());
         soapBodyBean.setValue(soapBody.getValue());
      }

      soapOperationBean = addSOAPOperationElementBean(msg, operationQName);
      soapBodyBean.setOperation(soapOperationBean);

      // diable for bug 
      //soapFaultBean = addSOAPFaultElementBean(msg);
      soapBodyBean.setFaultBean(soapFaultBean);

      return soapBodyBean;

   }

   private static SoapOperationBean addSOAPOperationElementBean(SOAPMessage msg, QName operationQName)
           throws SOAPException {
      SoapOperationBean soapOperationBean = new SoapOperationBean();
      SOAPPart part = msg.getSOAPPart();
      SOAPEnvelope envelope = part.getEnvelope();
      SOAPBody body = envelope.getBody();

      //		Name operationName = soapFactory.createName("sayHello1", "ns1", "http://example.com/wsdl");
      //		Name paraName = soapFactory.createName("name");
      Iterator iterator = body.getChildElements(operationQName);
      while (iterator.hasNext()) {
         SOAPBodyElement operation = (SOAPBodyElement) iterator.next();
         logger.debug("Operaton Name  : " + operation.getLocalName());
         logger.debug(" ******************  Soap Operation : ********************");
         logger.debug("  Soap Operation : " + operation);
         if (operation != null) {
            logger.debug("  Soap Operation getBaseURI: " + operation.getBaseURI());
            logger.debug("  Soap Operation getElementName: " + operation.getElementName());
            logger.debug("  Soap Operation getElementQName: " + operation.getElementQName());
            logger.debug("  Soap Operation getEncodingStyle: " + operation.getEncodingStyle());
            logger.debug("  Soap Operation getLocalName: " + operation.getLocalName());
            logger.debug("  Soap Operation getNamespaceURI: " + operation.getNamespaceURI());
            logger.debug("  Soap Operation getNodeName: " + operation.getNodeName());
            logger.debug("  Soap Operation getNodeValue: " + operation.getNodeValue());
            logger.debug("  Soap Operation getPrefix: " + operation.getPrefix());
            logger.debug("  Soap Operation getValue: " + operation.getValue());
            logger.debug("  Soap Operation getNodeType: " + operation.getNodeType());
            logger.debug("  Soap Operation getTagName: " + operation.getTagName());
            logger.debug("  Soap Operation getTextContent: " + operation.getTextContent());

            soapOperationBean.setBaseURI(operation.getBaseURI());
            soapOperationBean.setElementName(operation.getElementName());
            soapOperationBean.setElementQName(operation.getElementQName());
            soapOperationBean.setEncodingStyle(operation.getEncodingStyle());
            soapOperationBean.setLocalName(operation.getLocalName());
            soapOperationBean.setNamespaceURI(operation.getNamespaceURI());
            soapOperationBean.setNodeName(operation.getNodeName());
            soapOperationBean.setNodeType(operation.getNodeType());
            soapOperationBean.setNodeValue(operation.getNodeValue());
            soapOperationBean.setPrefix(operation.getPrefix());
            soapOperationBean.setTagName(operation.getTagName());
            soapOperationBean.setTextContent(operation.getTextContent());
            soapOperationBean.setValue(operation.getValue());
         }
         /*
          * Iterator iParam = operation.getChildElements(paraName); while
          * (iParam.hasNext()) { SOAPElement paramElement = (SOAPElement)
          * iParam.next(); String paraValue = paramElement.getValue();
          * System.out.print("value of Parameter is : " + paraValue); }
          */
      }
      List<SoapParameterBean> SoapParameterBeanList = new ArrayList<SoapParameterBean>();

      //SoapParameterBeanList = addSOAPParameterElementBean(msg);
      soapOperationBean.setParameters(SoapParameterBeanList);

      return soapOperationBean;
   }

   private static List<SoapParameterBean> addSOAPParameterElementBean(SOAPMessage msg) throws SOAPException {

      List<SoapParameterBean> soapParameterBeanList = new ArrayList<SoapParameterBean>();
      SoapParameterBean soapParameterBean = new SoapParameterBean();
      SOAPBody body = msg.getSOAPBody();

      SOAPElement operation = (SOAPElement) body.getFirstChild();
      Iterator parameterIterator = operation.getChildElements();

      while (parameterIterator.hasNext()) {
         SOAPElement parameter = (SOAPElement) operation.getChildElements().next();
         soapParameterBean = new SoapParameterBean();

         soapParameterBean.setBaseURI(parameter.getBaseURI());
         soapParameterBean.setElementName(operation.getElementName());
         soapParameterBean.setElementQName(parameter.getElementQName());
         soapParameterBean.setEncodingStyle(parameter.getEncodingStyle());
         soapParameterBean.setLocalName(parameter.getLocalName());
         soapParameterBean.setNamespaceURI(parameter.getNamespaceURI());
         soapParameterBean.setNodeName(parameter.getNodeName());
         soapParameterBean.setNodeType(parameter.getNodeType());
         soapParameterBean.setNodeValue(parameter.getNodeValue());
         soapParameterBean.setPrefix(parameter.getPrefix());
         soapParameterBean.setTagName(parameter.getTagName());
         soapParameterBean.setTextContent(parameter.getTextContent());
         soapParameterBean.setValue(parameter.getValue());

         // add to list 
         soapParameterBeanList.add(soapParameterBean);

      }

      return soapParameterBeanList;
   }

   private static SOAPMessage addSOAPBodyElement(SOAPMessage msg, SoapBindingBean soapRequest) {
      try {
         logger.debug("************Before add Body Element*******");
         msg.writeTo(System.out);
         logger.debug("\n******************************");
         logger.debug(" soapRequest.getElementFormDefault()  : " + soapRequest.getElementFormDefault());
         logger.debug(" FormChoice.QUALIFIED : " + FormChoice.QUALIFIED);

         boolean qualifield = false;
         if (soapRequest.getElementFormDefault().equalsIgnoreCase(FormChoice.QUALIFIED.value())) {
            qualifield = true;
         }

         String operationURI = soapRequest.getOperation().getQName().getNamespaceURI();
         String operationPrefix = soapRequest.getOperation().getQName().getPrefix();
         String operationLocalName = soapRequest.getOperation().getQName().getLocalPart();

         SOAPFactory soapFactory = SOAPFactory.newInstance();
         SOAPEnvelope envelope = msg.getSOAPPart().getEnvelope();
         envelope.addNamespaceDeclaration(operationPrefix, operationURI);
         SOAPBody body = envelope.getBody();

         Name operationName = soapFactory.createName(operationLocalName, operationPrefix, operationURI);
         QName operationQName = soapRequest.getOperation().getQName();
         logger.debug("operationQName :" + operationQName);
         logger.debug("Element Form Default :" + qualifield);
         SOAPBodyElement bodyElement = body.addBodyElement(operationQName);

         List<SoapParameterBean> parameters = soapRequest.getOperation().getParameters();
         for (SoapParameterBean p : parameters) {


            QName parameterQName = p.getQName();
            String prefix = p.getQName().getPrefix();
            String uri = p.getQName().getNamespaceURI();
            String localName = p.getQName().getLocalPart();
            Name paraName = soapFactory.createName(localName, prefix, uri);
            String textNode = p.getDefaultValue();


            if (qualifield) {
               SOAPElement para = bodyElement.addChildElement(parameterQName);
               //para.addTextNode(textNode).addNamespaceDeclaration(prefix, uri);
                 para.addTextNode(textNode);
            } else {
               parameterQName = new QName(localName);
               SOAPElement para = bodyElement.addChildElement(parameterQName);
               para.addTextNode(textNode);
            }
            logger.debug("parameterQName :" + parameterQName);
         }

         logger.debug("************After add Body Show Body Only*******");
         logger.debug("\n******************************");

      } catch (Exception e) {
      }
      return msg;
   }

   private static SOAPMessage addSOAPFaultElement(SOAPMessage msg, SoapBindingBean soapRequest) throws SOAPException {
      SOAPFault fault = msg.getSOAPBody().getFault();
      return msg;
   }

   private static SoapFaultBean addSOAPFaultElementBean(SOAPMessage msg) throws SOAPException {
      SoapFaultBean soapFautlBean = new SoapFaultBean();
      SOAPFault soapFault = msg.getSOAPBody().getFault();

      if (soapFault != null) {
         soapFautlBean.setBaseURI(soapFault.getBaseURI());
         soapFautlBean.setFaultNode(soapFault.getFaultNode());
         soapFautlBean.setElementQName(soapFault.getElementQName());
         soapFautlBean.setElementName(soapFault.getElementName());
         soapFautlBean.setEncodingStyle(soapFault.getEncodingStyle());
         soapFautlBean.setFaultActor(soapFault.getFaultActor());
         soapFautlBean.setFaultCode(soapFault.getFaultCode());
         soapFautlBean.setFaultCodeAsName(soapFault.getFaultCodeAsName());
         soapFautlBean.setFaultCodeAsQName(soapFault.getFaultCodeAsQName());
         soapFautlBean.setFaultNode(soapFault.getFaultNode());
         soapFautlBean.setFaultRole(soapFault.getFaultRole());
         soapFautlBean.setFaultString(soapFault.getFaultString());
         soapFautlBean.setLocalName(soapFault.getLocalName());
         soapFautlBean.setNamespaceURI(soapFault.getNamespaceURI());
         soapFautlBean.setNodeName(soapFault.getNodeName());
         soapFautlBean.setNodeType(soapFault.getNodeType());
         soapFautlBean.setPrefix(soapFault.getPrefix());
         soapFautlBean.setNodeValue(soapFault.getNodeValue());
         soapFautlBean.setTagName(soapFault.getTagName());
         soapFautlBean.setTextContent(soapFault.getTextContent());
         soapFautlBean.setValue(soapFault.getValue());
      }
      return soapFautlBean;
   }

   public static String createSOAPMessageText(SOAPMessage message) throws SOAPException, IOException {
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      message.writeTo(out);
      String strMsg = new String(out.toByteArray());

      return strMsg;
   }

   public static String createSOAPMessageXML(SOAPMessage message) throws SOAPException, IOException,
           TransformerException, ParserConfigurationException, SAXException {

      ByteArrayOutputStream out = new ByteArrayOutputStream();

      SOAPPart part = message.getSOAPPart();
      Source source = part.getContent();

      Node root = null;
      if (source instanceof DOMSource) {
         root = (Node) ((DOMSource) source).getNode();

      } else if (source instanceof SAXSource) {
         InputSource inSource = ((SAXSource) source).getInputSource();
         DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
         dbf.setNamespaceAware(true);
         DocumentBuilder db = null;
         db = dbf.newDocumentBuilder();
         Document doc = db.parse(inSource);
         root = (Node) doc.getDocumentElement();
      }
      Transformer transformer = TransformerFactory.newInstance().newTransformer();
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");
      transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

      transformer.transform(new DOMSource(root), new StreamResult(out));
      String strMsg = new String(out.toByteArray());

      return strMsg;
   }


   public SOAPMessage createSOAPMessageFromFile(String SOAP_FILE) throws SOAPException, IOException {

      FileInputStream theFIS = new FileInputStream(SOAP_FILE);
      MimeHeaders theMimeHdrs = new MimeHeaders();
      SOAPMessage theMsg = mSOAPMsgFactory.createMessage(theMimeHdrs, theFIS);
      theFIS.close();

      SOAPBody theBody = theMsg.getSOAPBody();
      SOAPHeader theHeader = theMsg.getSOAPHeader();

      logger.debug("Retrieving parts of SOAP message:");
      logger.debug(" The SOAP body: " + theBody);
      logger.debug(" The SOAP header: " + theHeader);

      return theMsg;
   }


   public static SOAPMessage createSOAPMessageFromString(String message) throws UnsupportedEncodingException,
           SOAPException {

      byte[] docBytes = message.getBytes("UTF-8");
      ByteArrayInputStream bais = new ByteArrayInputStream(docBytes);
      StreamSource strSource = new StreamSource(bais);
      MessageFactory mf = MessageFactory.newInstance();
      SOAPMessage smg = mf.createMessage();
      SOAPPart soapPart = smg.getSOAPPart();
      soapPart.setContent(strSource);
      return smg;
   }

   public static SOAPMessage createSOAPMessageFromString(String message, SOAPMessage msg)
           throws UnsupportedEncodingException, SOAPException {
      byte[] docBytes = message.getBytes("UTF-8");
      ByteArrayInputStream bais = new ByteArrayInputStream(docBytes);
      StreamSource strSource = new StreamSource(bais);
      SOAPPart soapPart = msg.getSOAPPart();
      soapPart.setContent(strSource);
      return msg;

   }

   public static SoapResponseBean createSOAPResponseBean(SOAPMessage msg, QName operationQName) throws SOAPException {
      SoapResponseBean soapResponseBean = new SoapResponseBean();
      SoapPartBean soapPartBean = new SoapPartBean();

      soapPartBean = addSOAPPartElementBean(msg, operationQName);

      List<SoapAttachmentBean> soapAttachmentBeanList = new ArrayList<SoapAttachmentBean>();

      // One Message has one Part
      soapResponseBean.setSoapPart(soapPartBean);

      // One Message has many Attachments
      soapResponseBean.setSoapAttachments(soapAttachmentBeanList);

      return soapResponseBean;
   }

 
}
