package soap;

import static org.junit.Assert.*;

import java.io.IOException;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;

import org.junit.Before;
import org.junit.Test;
import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.api.Description;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.wsssoapbox.bean.model.wsdl.SoapBindingBean;
import org.wsssoapbox.dao.soap.ISoapModelDAO;
import org.wsssoapbox.dao.soap.SoapModelDAO;
import org.wsssoapbox.datasource.wsdl.WSDLDataSource;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import org.wsssoapbox.soap.support.SoapCreator;

import wsdl.WSDLList;

/**
 * @author  Peter
 */
public class SoapModelDAOTestCase {

   private static Description desc;
   private static WSDLDataSource wsdlDocumentDataSource;

   @Before
   public void setUp() throws Exception {
      wsdlDocumentDataSource = new WSDLDataSource(WSDLList.url);
      SoapModelDAOTestCase.desc = wsdlDocumentDataSource.getDesc(WSDLList.url);
   }


   //  @Test
   public void getSoapRequestRPC1_1() throws SOAPException, IOException, ParserConfigurationException,
           SAXException, TransformerFactoryConfigurationError, TransformerException, XmlException {
      MessageFactory factory = MessageFactory.newInstance();
      SOAPMessage message = factory.createMessage();



      //	QName interfaceQName = new QName("http://rpc.ws.wssbox.org/", "Hello");
      //	QName operationQName = new QName("http://rpc.ws.wssbox.org/", "getCustomer");


      QName interfaceQName = desc.getInterfaces().get(0).getQName();
      QName operationQName = desc.getInterfaces().get(0).getOperations().get(0).getQName();


      ISoapModelDAO soapModelDAO = new SoapModelDAO();
      SoapBindingBean soapRequest = soapModelDAO.createSoapBindingBeanRPC1_1(desc, interfaceQName, operationQName);

      System.out.println("Operations : " + soapRequest.getOperation());
      System.out.println("Operations Pamam: " + soapRequest.getOperation().getParameters());
      System.out.println("SOAP Version : " + soapRequest.getSoapVersion());

      message = SoapCreator.createSOAPMessage(message, soapRequest);

      print(message);
   }

   @Test
   public void testGetSoapLocalhostDocumentType_1() throws SOAPException, IOException, ParserConfigurationException,
           SAXException, TransformerFactoryConfigurationError, TransformerException, XmlException {
      MessageFactory factory = MessageFactory.newInstance();
      SOAPMessage message = factory.createMessage();

      QName interfaceQName = desc.getInterfaces().get(0).getQName();
      QName operationQName = desc.getInterfaces().get(0).getOperations().get(0).getQName();
      QName bindingQName = desc.getBindings().get(0).getQName();

      ISoapModelDAO soapModelDAO = new SoapModelDAO();
      SoapBindingBean soapRequest = soapModelDAO.createSoapBindingBeanDoc(desc, bindingQName, interfaceQName, operationQName);

      System.out.println("Operations : " + soapRequest.getOperation());
      System.out.println("Operations Pamam: " + soapRequest.getOperation().getParameters());
      System.out.println("SOAP Version : " + soapRequest.getSoapVersion());

      message = SoapCreator.createSOAPMessage(message, soapRequest);

      print(message);
   }

   //@Test
   public void testGetSoapLocalhostDocumentType() throws SOAPException, IOException, ParserConfigurationException,
           SAXException, TransformerFactoryConfigurationError, TransformerException, XmlException {
      MessageFactory factory = MessageFactory.newInstance();
      SOAPMessage message = factory.createMessage();

      QName interfaceQName = new QName("http://doc.ws.wssbox.org/", "HelloWorld");
      QName operationQName = new QName("http://doc.ws.wssbox.org/", "getCustomer");

      QName bindingQName = new QName("http://doc.ws.wssbox.org/", "getCustomer");

      ISoapModelDAO soapModelDAO = new SoapModelDAO();
      SoapBindingBean soapRequest = soapModelDAO.createSoapBindingBeanDoc(desc, bindingQName, interfaceQName, operationQName);

      message = SoapCreator.createSOAPMessage(message, soapRequest);

      print(message);
   }

//@Test
   public void testGetSopLocalhostRPCType() throws SOAPException, IOException, ParserConfigurationException,
           SAXException, TransformerFactoryConfigurationError, TransformerException, XmlException {

      MessageFactory factory = MessageFactory.newInstance();
      SOAPMessage message = factory.createMessage();

      QName interfaceQName = new QName("http://rpc.ws.wssbox.org/", "Hello");
      QName operationQName = new QName("http://rpc.ws.wssbox.org/", "getBoolean");
      QName bindingQName = new QName("http://doc.ws.wssbox.org/", "getCustomer");


      ISoapModelDAO soapModelDAO = new SoapModelDAO();
      SoapBindingBean soapRequest = soapModelDAO.createSoapBindingBeanDoc(desc, bindingQName, interfaceQName, operationQName);

      message = SoapCreator.createSOAPMessage(message, soapRequest);

      print(message);
   }

   //  @Test
   public void testGetSoapModelByOperation() throws SOAPException, IOException, ParserConfigurationException,
           SAXException, TransformerFactoryConfigurationError, TransformerException, XmlException {
      MessageFactory factory = MessageFactory.newInstance();
      SOAPMessage message = factory.createMessage();

      QName interfaceQName = new QName("http://www.pttplc.com/ptt_webservice/", "PTTInfoSoap");
      QName operationQName = new QName("http://www.pttplc.com/ptt_webservice/", "CurrentOilPrice");
      QName bindingQName = new QName("http://doc.ws.wssbox.org/", "getCustomer");

      ISoapModelDAO soapModelDAO = new SoapModelDAO();
      SoapBindingBean soapRequest = soapModelDAO.createSoapBindingBeanDoc(desc, bindingQName, interfaceQName, operationQName);

      message = SoapCreator.createSOAPMessage(message, soapRequest);

      print(message);
   }

   public static void print(SOAPMessage message) throws ParserConfigurationException, SAXException, IOException,
           TransformerFactoryConfigurationError, TransformerException, SOAPException {
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
      transformer.transform(new DOMSource(root), new StreamResult(System.out));
   }
}
