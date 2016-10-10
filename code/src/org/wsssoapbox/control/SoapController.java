package org.wsssoapbox.control;

import java.io.IOException;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
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

public class SoapController {
	private static Source source;
	private static SOAPConnectionFactory soapConnectionFactory;
	private static SOAPConnection soapConnection;
	private static SOAPFactory soapFactory;
	private static MessageFactory messageFactory;
	private static SOAPMessage soapMessageRequest;;
	private static SOAPMessage soapMessgeResponse;
	private static SOAPPart reponseSOAPPart;
	private static SOAPEnvelope soapEnvelope;
	private static SOAPPart soapPart;
	private static SOAPHeader soapHeader;
	private static SOAPBody soapBody;
	private static SOAPBodyElement bodyElement;

	public static void createInstant() throws SOAPException {
		soapConnectionFactory = SOAPConnectionFactory.newInstance();
		soapConnection = soapConnectionFactory.createConnection();

		soapFactory = SOAPFactory.newInstance();
		messageFactory = MessageFactory.newInstance();
		soapMessageRequest = messageFactory.createMessage();

		soapPart = soapMessageRequest.getSOAPPart();
		soapEnvelope = soapPart.getEnvelope();
		soapHeader = soapMessageRequest.getSOAPHeader();
		soapBody = soapMessageRequest.getSOAPBody();

		soapPart.setXmlStandalone(false);
		soapHeader.detachNode();
	}

	public static void printMessage(Source source) throws ParserConfigurationException, SAXException, IOException,
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

	public static boolean addNamespaceEnvelope(SOAPEnvelope envelope, String prefix, String uri) throws SOAPException {
		if (envelope != null && envelope instanceof SOAPEnvelope) {
			envelope.addNamespaceDeclaration(prefix, uri);
			System.out.println("added Namespace : xmlns: " + prefix + "=" + uri);
			return true;
		} else {
			return false;
		}
	}

	static void addChildElementItem(SOAPEnvelope envelope, SOAPBodyElement bodyElementItem, String localName,
			String prefix, String uri, String value) {
		try {
			System.out.println("added Child Element of : localName = " + localName + ", prefix = " + prefix + ",uri ="
					+ value);
			bodyElementItem.addChildElement(envelope.createName(localName, prefix, uri)).addTextNode(value);
		} catch (Throwable e) {

		}
	}

	static SOAPBodyElement addBodyElementItem(SOAPEnvelope envelope, SOAPBody soapBody, QName qname)
			throws SOAPException {
		return soapBody.addBodyElement(qname);
	}
}
