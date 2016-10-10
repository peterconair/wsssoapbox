package soap;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPHeader;
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

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class SAAJTestSOAPCallDocType implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Source source;

	public static void main(String[] args) {
		try {


			SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
			SOAPConnection connection = soapConnectionFactory.createConnection();

			SOAPFactory soapFactory = SOAPFactory.newInstance();

			MessageFactory factory = MessageFactory.newInstance();
			SOAPMessage message = factory.createMessage();

			SOAPPart soapPart = message.getSOAPPart();
			SOAPEnvelope envelope = soapPart.getEnvelope();
			SOAPHeader header = message.getSOAPHeader();
			SOAPBody body = message.getSOAPBody();

			soapPart.setXmlStandalone(true);
			header.detachNode();

			//message.writeTo(System.out);

			//envelope.addNamespaceDeclaration("soap", "http://schemas.xmlsoap.org/wsdl/soap/");
			//	envelope.addNamespaceDeclaration("soap12","http://schemas.xmlsoap.org/wsdl/soap12/");
			//envelope.addNamespaceDeclaration("xsi", "http://www.w3.org/1999/XMLSchema-instance");
			//envelope.addNamespaceDeclaration("xsd", "http://www.w3.org/1999/XMLSchema");
		//	envelope.addNamespaceDeclaration("tns", "http://doc.ws.wssbox.org/");


			//remove the default SOAP-ENV
			//envelope.removeNamespaceDeclaration("SOAP-ENV");


			String prefix = envelope.getPrefix();

			System.out.println("Presfix of SOAP Part :" + soapPart.getPrefix());
			System.out.println("Presfix of SOAP Envelop :" + prefix);

			System.out.println("***************************SOAP Part ************************");

			source = soapPart.getContent();
			print(source);

			Name getHelloWorldAsStringName = soapFactory.createName("getHelloWorldAsString", "tns",
					"http://doc.ws.wssbox.org/");
			SOAPBodyElement bodyElement = body.addBodyElement(getHelloWorldAsStringName);

			Name parametersName = soapFactory.createName("arg0", "tns", "http://doc.ws.wssbox.org/");
			SOAPElement language = bodyElement.addChildElement(parametersName);
			language.addTextNode("Peter").addNamespaceDeclaration("tns", "http://doc.ws.wssbox.org/");


			URL endpoint = new URL("http://localhost:8888/WebServiceTutorial/ws/doc/hello");
			SOAPMessage response = connection.call(message, endpoint);

			System.out.println("*************************** Request : ");
			
			source = soapPart.getContent();
			print(source);

			connection.close();

			SOAPBody soapBody = response.getSOAPBody();
			SOAPPart reponseSOAPPart = response.getSOAPPart();
			System.out.println(soapBody.getChildNodes().getLength());
			Iterator iterator = soapBody.getChildElements(getHelloWorldAsStringName);

			while (iterator.hasNext()) {
				bodyElement = (SOAPBodyElement) iterator.next();
				String val = bodyElement.getValue();
				System.out.println("The Value is:" + val);
			}

			System.out.println("*************************** Repsones : ");
			
			source = reponseSOAPPart.getContent();
			print(source);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void print(Source source) throws ParserConfigurationException, SAXException, IOException,
			TransformerFactoryConfigurationError, TransformerException {
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

	/**
	 * Add Item to the SOAP Body
	 * 
	 * @param body
	 *            - SOAP Body
	 * @param envelope
	 *            - SOAP Envelope
	 * @param id
	 *            - Item ID
	 * @param name
	 *            - Item Name
	 * @param price
	 *            - Item Price
	 * @throws SOAPException 
	 */
	static SOAPMessage addItem() throws SOAPException {
		SOAPMessage message = MessageFactory.newInstance().createMessage();
		
		
		return message;
	}
}