package soap;


import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.MessageFactory;

import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
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

import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.wsssoapbox.bean.model.wsdl.SoapBindingBean;
import org.wsssoapbox.bean.model.soap.SoapOperationBean;
import org.wsssoapbox.bean.model.soap.SoapParameterBean;
import org.wsssoapbox.soap.support.SoapCreator;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class SoapRequestMessageTestCase {
	private static SOAPConnectionFactory soapConnectionFactory;
	private static SOAPConnection connection;
	/**
	 * @uml.property  name="url"
	 */
	String url = "http://localhost:8888/WebServiceTutorial/ws/doc/hello?wsdl";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		soapConnectionFactory = SOAPConnectionFactory.newInstance();
		connection = soapConnectionFactory.createConnection();
	}

	@Test
	
	
	public void testCreateSOAPMessageSOAPMessageSoapModel() throws SOAPException, ParserConfigurationException,
			SAXException, IOException, TransformerFactoryConfigurationError, TransformerException {

		MessageFactory factory = MessageFactory.newInstance();
		SOAPMessage message = factory.createMessage();

		List<SoapParameterBean> parameters = new ArrayList<SoapParameterBean>();
		QName p1Name = new QName("http://doc.ws.wssbox.org/", "arg0", "tns");
		SoapParameterBean p1 = new SoapParameterBean("arg0", "string", 0, "1", p1Name, "?");
		
		QName p2Name = new QName("http://doc.ws.wssbox.org/", "arg1", "tns");
		SoapParameterBean p2 = new SoapParameterBean("arg1", "string", 0, "1", p2Name, "?");
		parameters.add(p1);
		parameters.add(p2);

		String localPart = "getHelloWorld";
		String prefix = "tns";
		String namespaceURI = "http://doc.ws.wssbox.org/";
		QName operationQName = new QName(namespaceURI, localPart, prefix);

		SoapOperationBean operation = new SoapOperationBean(localPart, operationQName, parameters);

		SoapBindingBean soapModel = new SoapBindingBean();
		Map<String, String> schemas = new HashMap<String, String>();
		schemas.put("tns", "http://doc.ws.wssbox.org/");
		schemas.put("xsd", "http://www.w3.org/2001/XMLSchema");
		schemas.put("xml", "http://www.w3.org/XML/1998/namespace");
		schemas.put("soap","http://schemas.xmlsoap.org/soap/envelope/");
		soapModel.setHeader(false);
		soapModel.setXmlStandalone(true);
		soapModel.setNamespaces(schemas);
		soapModel.setOperation(operation);

		message = SoapCreator.createSOAPMessage(message, soapModel);

		print(message);
		assertNotNull(message);
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
