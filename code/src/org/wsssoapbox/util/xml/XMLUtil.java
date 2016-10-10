/**
 * PETALS - PETALS Services Platform.
 * Copyright (c) 2006 EBM Websourcing
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * -------------------------------------------------------------------------
 * $Id: XMLUtil.java 98 2006-02-24 16:18:48Z alouis $
 * -------------------------------------------------------------------------
 */

package org.wsssoapbox.util.xml;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


/**
 * Contains utilities methods for XML operations.
 * 
 * @author alouis - EBMWebsourcing
 * @author ddesjardins - EBMWebsourcing
 * @author gblondelle - EBMWebsourcing
 * @since 1.0
 * 
 */
public final class XMLUtil {

	private static final String XML_ENCODING_ATTRIBUTE = "encoding";

	private static final String XML_HEADER_END = "?>";

	private static final String XML_HEADER_START = "<?xml";

	private static final String DEFAULT_CHARSET_ENCODING = "UTF-8";

	private static final Logger LOGGER = Logger.getLogger(XMLUtil.class.getName());

	private final static ThreadLocal<DocumentBuilder> documentBuilderThreadLocal = new ThreadLocal<DocumentBuilder>() {

		@Override
		protected DocumentBuilder initialValue() {
			try {
				final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
				documentBuilderFactory.setNamespaceAware(true);
				final DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
				return documentBuilder;
			} catch (ParserConfigurationException e) {
				throw new RuntimeException("Failed to create DocumentBuilder", e);
			}
		}
	};

	/**
	 * <p>
	 * This transformer has the following features:
	 * <ul>
	 * <li>omit the XML declaration,</li>
	 * <li>default encoding</li>
	 * </ul>
	 * </p>
	 */
	private final static ThreadLocal<Transformer> transformerWithoutXmlDeclarationThreadLocal = new ThreadLocal<Transformer>() {

		@Override
		protected Transformer initialValue() {
			try {

				final Transformer transformer = TransformerFactory.newInstance().newTransformer();
				Properties props = new Properties();
				props.put(OutputKeys.OMIT_XML_DECLARATION, "yes");
				props.put(OutputKeys.METHOD, "xml");
				transformer.setOutputProperties(props);
				return transformer;

			} catch (TransformerConfigurationException e) {
				throw new RuntimeException("Failed to create Transformer", e);
			}
		}
	};

	/**
	 * <p>
	 * This transformer has the following features:
	 * <ul>
	 * <li>doesn't omit the XML declaration,</li>
	 * <li>default encoding</li>
	 * </ul>
	 * </p>
	 */
	private final static ThreadLocal<Transformer> defaultTransformerThreadLocal = new ThreadLocal<Transformer>() {

		@Override
		protected Transformer initialValue() {
			try {

				final Transformer transformer = TransformerFactory.newInstance().newTransformer();
				Properties props = new Properties();
				props.put(OutputKeys.OMIT_XML_DECLARATION, "no");
				props.put(OutputKeys.METHOD, "xml");
				transformer.setOutputProperties(props);
				return transformer;

			} catch (TransformerConfigurationException e) {
				throw new RuntimeException("Failed to create Transformer", e);
			}
		}
	};

	private XMLUtil() {
		// Do nothing
	}

	/**
	 * Create an attribute node with the specified value
	 * 
	 * @param document
	 *            XML document
	 * @param att
	 *            attribute name
	 * @param value
	 *            attribute value
	 * @return an attribute, null if the document is null
	 */
	public static Node createAttribute(final Document document, final String att, final String value) {
		Node element = null;
		if (document != null) {
			element = document.createAttribute(att);
			element.setNodeValue(value);
		}
		return element;
	}

	/**
	 * Create a node with the specified name, attributes and values. attVal is a
	 * suite : att1,val1,att2,val2,...
	 * 
	 * @param document
	 *            xlm document
	 * @param nodeName
	 *            node name
	 * @param attVal
	 *            attribute values
	 * @return the node with its attribute, null if the array is not correct or
	 *         the document is null.
	 */
	public static Node createNode(final Document document, final String nodeName, final String... attVal) {
		Node element = null;
		if (document != null && attVal.length % 2 == 0) {
			element = document.createElement(nodeName);
			for (int i = 0; i < attVal.length; i = i + 2) {
				element.getAttributes().setNamedItem(createAttribute(document, attVal[i], attVal[i + 1]));
			}
		}
		return element;
	}

	/**
	 * lookup a node in a node list.
	 * 
	 * @param nodeName
	 *            the local name of the name
	 * @param nameSpace
	 *            the namespace of this node. If null, the namespace is ignored
	 * @param nl
	 *            the list of node
	 * @return the node naming by the nodeName
	 */
	private static Node lookupNodeInNodeList(final String nodeName, final String namespace, final NodeList nl) {
		Node result = null;
		for (int i = 0; i < nl.getLength() && result == null; i++) {
			if (((namespace != null) && (nl.item(i).getNamespaceURI() != null))
					&& ((nl.item(i).getNamespaceURI().equals(namespace)) && (nodeName.equals(nl.item(i).getLocalName())))) {
				result = nl.item(i);
			} else if (nodeName.equals(nl.item(i).getNodeName())) {
				result = nl.item(i);
			}

		}
		return result;
	}

	/**
	 * Search for a child with the given nodeName. If recursive, search in all
	 * the child of firdt level, then if not found, search in the 2nd level of
	 * the first child, ...
	 * 
	 * @param node
	 *            parent node
	 * @param namespaceURI
	 *            The namespaceURI of the node. if null, the namespace is
	 *            ignored
	 * @param nodeName
	 *            node name
	 * @param recursive
	 *            boolean to know if we got through the xml tree
	 * @return a node
	 */
	public static Node findChild(final Node node, final String nodeName, final String namespaceURI,
			final boolean recursive) {
		Node result = null;
		if (node != null && nodeName != null) {
			node.normalize();
			NodeList nl = node.getChildNodes();
			result = lookupNodeInNodeList(nodeName, namespaceURI, nl);
			// now, search recursively if required
			if (result == null && recursive) {
				for (int i = 0; i < nl.getLength() && result == null; i++) {
					result = findChild(nl.item(i), nodeName, namespaceURI, true);
				}
			}
		}
		return result;
	}

	/**
	 * Transform an XML {@link Node} into a String
	 * 
	 * @param node
	 *            the XML {@link Node} to parse
	 * @return the resulting String, null if node is null
	 * @throws TransformerException
	 *             if errors occured during transformation
	 */
	public static String parseToString(final Node node) throws TransformerException {
		String result = null;
		if (node != null) {
			node.normalize();
			final StringWriter stringWriter = new StringWriter();
			XMLUtil.defaultTransformerThreadLocal.get().transform(new DOMSource(node), new StreamResult(stringWriter));
			final StringBuffer buffer = stringWriter.getBuffer();
			result = buffer.toString();
		}
		return result;
	}

	/**
	 * Return the value of the attribute in the node
	 * 
	 * @param n
	 *            the node
	 * @param attName
	 *            the name of the attribute
	 * @return the value of the attribute (can be empty), null if not found
	 */
	public static String getAttributeValue(final Node n, final String attName) {
		String ret = null;
		if (n != null) {
			NamedNodeMap atts = n.getAttributes();
			Node att = atts.getNamedItem(attName);
			if (att != null) {
				ret = att.getNodeValue();
			}
		}
		return ret;
	}

	/**
	 * Return the value of the attribute in the node. Throws an exception if
	 * missing attribute.
	 * 
	 * @param n
	 *            the node
	 * @param attName
	 *            the name of the attribute, must be non null
	 * @return the value of the attribute (can be empty).
	 * @throws NullPointerException
	 *             if attribute not found in the node
	 */
	public static String getRequiredAttributeValue(final Node n, final String attName) {
		String ret = null;
		if (n != null && !StringHelper.isNullOrEmpty(attName)) {
			NamedNodeMap atts = n.getAttributes();
			Node att = atts.getNamedItem(attName);
			if (att == null) {
				throw new NullPointerException("Required attribute '" + attName + "' not found in the node.");
			}
			if (att != null) {
				ret = att.getNodeValue();
			}
		}
		return ret;
	}

	/**
	 * Return the first child of a node, regardless <i>text</i> node and CDATA
	 * sections
	 * 
	 * @param node
	 * @return
	 */
	public static Node getFirstChild(final Node node) {
		Node result = node.getFirstChild();
		while (result != null
				&& (result.getNodeType() == Node.TEXT_NODE || result.getNodeType() == Node.CDATA_SECTION_NODE)) {
			result = result.getNextSibling();
		}
		return result;
	}

	/**
	 * Return the next sibling of a node, regardless <i>text</i> node and CDATA
	 * sections
	 * 
	 * @param node
	 * @return The next sibling node, or <code>null</code> if <code>node</code>
	 *         is null or no sibling node exists.
	 */
	public static Node getNextSibling(final Node node) {
		Node result = null;
		if (node != null) {
			result = node.getNextSibling();
			while (result != null
					&& (result.getNodeType() == Node.TEXT_NODE || result.getNodeType() == Node.CDATA_SECTION_NODE)) {
				result = result.getNextSibling();
			}
		}
		return result;
	}

	/**
	 * Return the <i>text</i> element of a node, even if it is contained in a
	 * CDATA section
	 * 
	 * @param node
	 * @return
	 */
	public static String getTextContent(final Node node) {
		String result = null;
		NodeList list = node.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node currentNode = list.item(i);
			if (currentNode.getNodeType() == Node.TEXT_NODE || currentNode.getNodeType() == Node.CDATA_SECTION_NODE) {
				result = currentNode.getNodeValue();

				Node nextSiblingNode = currentNode.getNextSibling();
				if (nextSiblingNode != null && nextSiblingNode.getNodeType() == Node.CDATA_SECTION_NODE) {
					String cdataContent = nextSiblingNode.getNodeValue();
					if (cdataContent != null) {
						result = result + cdataContent;
					}
				}

				result = result.replace('\t', ' ').replace('\n', ' ').trim();

				break;
			}
		}
		return result;
	}

	/**
	 * Create a String result from a DOM document
	 * 
	 * @param document
	 *            the DOM Document. Must not be null
	 * @return a String representation of the DOM Document
	 * @throws TransformerException
	 */
	public static String createStringFromDOMDocument(final Node document) throws TransformerException {
		return createStringFromDOMNode(document, false);
	}

	/**
	 * Create a String result from a DOM Node
	 * 
	 * @param node
	 *            the DOM Node. Must not be null
	 * @return a String representation of the DOM Document
	 * @throws TransformerException
	 */
	public static String createStringFromDOMNode(final Node node) throws TransformerException {
		return createStringFromDOMNode(node, true);
	}

	/**
	 * Create a {@link String} from a Node list
	 * 
	 * @param list
	 * @return
	 * @throws TransformerException
	 */
	public static String createStringFromDOMNodeList(final NodeList list) throws TransformerException {
		StringBuffer sb = new StringBuffer("");
		if (list != null) {
			for (int i = 0; i < list.getLength(); i++) {
				sb.append(createStringFromDOMNode(list.item(i)));
			}
		}
		return sb.toString();
	}

	/**
	 * Create a String result from a DOM Node
	 * 
	 * @param node
	 *            the DOM Node. Must not be null
	 * @parma moitDeclaration If <code>true</code> no XML declaration will be
	 *        added.
	 * @return a String representation of the DOM Document
	 * @throws TransformerException
	 */
	public static String createStringFromDOMNode(final Node node, final boolean omitDeclaration)
			throws TransformerException {
		node.normalize();
		final Source source = new DOMSource(node);
		final StringWriter out = new StringWriter();
		final Result resultStream = new StreamResult(out);
		if (omitDeclaration) {
			XMLUtil.transformerWithoutXmlDeclarationThreadLocal.get().transform(source, resultStream);
		} else {
			XMLUtil.defaultTransformerThreadLocal.get().transform(source, resultStream);
		}
		return out.toString();
	}

	/**
	 * Create a document from a String
	 * 
	 * @param xml
	 *            an xml string
	 * @return a {@link Document} representing the document, null if an error
	 *         occured
	 */
	public static Document createDocumentFromString(final String xml) {
		Document doc = null;
		try {
			InputStream in = new ByteArrayInputStream(xml.getBytes());
			InputSource inputSource = new InputSource(in);
			doc = XMLUtil.documentBuilderThreadLocal.get().parse(inputSource);
			doc.normalize();
		} catch (SAXException e) {
			LOGGER.log(Level.SEVERE, "Bad XML fragment can't be transformed to a DOM tree.", e);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Unexpected Error", e);
		}

		return doc;
	}

	/**
	 * Return an array of String representing each Text element of the nodes
	 * which are in the list.
	 * 
	 * @param list
	 * @return
	 */
	public static List<String> getTextContents(final NodeList list) {
		List<String> result = null;
		if (list != null) {
			result = new ArrayList<String>();
			for (int i = 0; i < list.getLength(); i++) {
				Node pathElement = list.item(i);
				if (pathElement.getNodeType() != Node.TEXT_NODE) {
					result.add(getTextContent(pathElement));
				}
			}
		}
		return result;
	}

	/**
	 * Return the QName extracted from the targeted attribute of the given
	 * {@link Node}
	 * 
	 * @param node
	 *            the node from which attribute must be extracted
	 * @param attrName
	 *            name of the targeted attribute
	 * @return the {@link QName} value of the targeted {@link Node} attribute,
	 *         null if not found
	 * @throws IllegalArgumentException
	 *             If the String does not conform to one of the following
	 *             pattern : "localPart", "{ns}localPart" or "ns:localPart".
	 */
	public static QName extractXmlAttributeQName(final Node node, final String attrName) {
		String attr = XMLUtil.getAttributeValue(node, attrName);

		QName qName = null;

		if (attr != null) {
			// qname like "xmlns:name"
			if ((attr.indexOf(':') > -1) && (attr.charAt(0) != '{')) {
				String ns = attr.substring(0, attr.indexOf(':'));

				String namespace = node.lookupNamespaceURI(ns);
				/*
				 * String namespace = source.getDocumentElement().getAttribute(
				 * "xmlns:" + ns);
				 */

				qName = new QName(namespace, attr.substring(attr.indexOf(':') + 1), ns);
			} else {
				// qname like "{ns}name" or "name"
				qName = QName.valueOf(attr);
			}
		}
		return qName;
	}

	/**
	 * Extract the xml header charset encoding from the given xml String
	 * 
	 * @param msg
	 *            the xml String
	 * @return the xml header charset encoding extrated from the given String or
	 *         default encoding (UTF-8) if not found
	 */
	public static String extractXmlEncoding(String msg) {
		String xmlEncoding = DEFAULT_CHARSET_ENCODING;
		if (msg.indexOf(XML_HEADER_START) > -1) {
			String xmlHeader = msg.substring(msg.indexOf(XML_HEADER_START), msg.indexOf(XML_HEADER_END) + 2);
			xmlHeader = StringHelper.replace(xmlHeader, '"', '\'');
			if (xmlHeader.indexOf(XML_ENCODING_ATTRIBUTE) > -1) {
				System.out.println(xmlHeader);
				xmlEncoding = xmlHeader.substring(xmlHeader.indexOf(XML_ENCODING_ATTRIBUTE)
						+ XML_ENCODING_ATTRIBUTE.length() + 2);
				xmlEncoding = xmlEncoding.substring(0, xmlEncoding.indexOf("'"));
			}
		}
		return xmlEncoding;
	}

	/**
	 * @param node
	 * @param attrName
	 * @return
	 */
	public static QName extractRequiredXmlAttributeQName(final Node node, final String attrName) {
		String attr = XMLUtil.getRequiredAttributeValue(node, attrName);

		QName qName = null;

		// qname like "xmlns:name"
		if ((attr.indexOf(':') > -1) && (attr.charAt(0) != '{')) {
			String ns = attr.substring(0, attr.indexOf(':'));

			String namespace = node.lookupNamespaceURI(ns);
			/*
			 * String namespace = source.getDocumentElement().getAttribute(
			 * "xmlns:" + ns);
			 */

			qName = new QName(namespace, attr.substring(attr.indexOf(':') + 1), ns);
		} else {
			// qname like "{ns}name" or "name"
			qName = QName.valueOf(attr);
		}
		return qName;
	}

	/**
	 * A List of Node that contains all children of this node; nodes must be of
	 * type Node.ELEMENT_NODE to be returned. If there are no children, this is
	 * a NodeList containing no nodes.
	 * 
	 * @param node
	 *            the node from which children will be extracted
	 * @return a List of child Node of type Node.ELEMENT_NODE
	 */
	public static List<Node> getNodeChildren(final Node node) {
		List<Node> children = new ArrayList<Node>();
		if (node != null) {
			NodeList childrenList = node.getChildNodes();
			for (int i = 0; i < childrenList.getLength(); i++) {
				Node child = childrenList.item(i);
				if (child.getNodeType() == Node.ELEMENT_NODE) {
					children.add(child);
				}
			}
		}

		return children;
	}

	/**
	 * @param document
	 * @param elementName
	 * @return the first matching node, null otherwise, or if document/path is
	 *         null
	 */
	public static Node getNode(Document document, String elementName) {
		Node result = null;
		if (document != null && elementName != null) {
			NodeList resList = document.getElementsByTagName(elementName);
			if (resList != null && resList.getLength() > 0) {
				return resList.item(0);
			}
		}
		return result;
	}

	/**
	 * @param document
	 * @param elementName
	 * @param attributeName
	 * @param attributeValue
	 * @return the first matching node, null otherwise, or if document/path is
	 *         null
	 */
	public static Node getNodeWithAttribute(Document document, String elementName, String attributeName,
			String attributeValue) {
		Node result = null;
		if (document != null && elementName != null && attributeName != null && attributeValue != null) {
			NodeList resList = document.getElementsByTagName(elementName);
			for (int i = 0; i < resList.getLength() && result == null; i++) {
				Element curNode = (Element) resList.item(i);
				if (attributeValue.equals(curNode.getAttribute(attributeName))) {
					result = curNode;
				}
			}
		}
		return result;
	}

	/**
	 * Write a document to an output stream. The output stream and the document
	 * must not be null.
	 * 
	 * @param document
	 * @param outputFile
	 */
	public static void writeDocument(final Document document, final OutputStream outputStream) throws Exception {

		if (document != null && outputStream != null) {
			XMLUtil.defaultTransformerThreadLocal.get().transform(new DOMSource(document),
					new StreamResult(outputStream));
		} else {
			throw new Exception("Can not write document to output stream");
		}
	}

	/**
	 * Load a document from an input stream.
	 * 
	 * @param inputStream
	 * @return
	 * @throws IOException
	 * @throws SAXException
	 */
	public static Document loadDocument(InputStream inputStream) throws IOException, SAXException {

		Document document = null;
		try {
			document = XMLUtil.documentBuilderThreadLocal.get().parse(inputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return document;
	}

	/**
	 * Get a new document
	 * 
	 * @return
	 */
	public static Document createDocument() {
		return documentBuilderThreadLocal.get().newDocument();
	}

	public static String getSoapMessageString(SOAPMessage message) throws SOAPException, IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		message.writeTo(out);
		String strMsg = new String(out.toByteArray());

		return strMsg;
	}

	public static String getSoapMessageXML(SOAPMessage message) throws SOAPException, IOException,
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

	public static void writeToXMLFormat(String content, String file) throws TransformerFactoryConfigurationError,
			FileNotFoundException, TransformerException {
		Source xmlInput = new StreamSource(new StringReader(content));
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		transformer.transform(xmlInput, new StreamResult(new FileOutputStream(file)));
	}

	public void logSoapMessage(SOAPMessage message) {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			message.writeTo(out);
			System.out.println(out.toString());
		} catch (Exception e) {
		}
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
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

		transformer.transform(new DOMSource(root), new StreamResult(System.out));

	}

	public static void print1(SOAPMessage message) throws ParserConfigurationException, SAXException, IOException,
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
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

		transformer.transform(new DOMSource(root), new StreamResult(System.out));

	}

	public static String prettyPrint(String unformattedString) {
		try {
			String oneLine = trimXML(unformattedString);
			InputSource inputSource = new InputSource(new StringReader(oneLine));
			StringWriter writer = new StringWriter();

			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document document = builder.parse(inputSource);

			DOMSource domSource = new DOMSource(document);
			StreamResult streamResult = new StreamResult(writer);
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			// serializer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM,"users.dtd");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "3");
			transformer.transform(domSource, streamResult);

			return writer.getBuffer().toString();
		} catch (ParserConfigurationException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			return unformattedString;
		} catch (SAXException e) {
			// Malformed XML, this only fills up the log, whereas user did not
			// provide well formed XML, so ignore
			return unformattedString;
		} catch (FactoryConfigurationError e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			return unformattedString;
		} catch (IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			return unformattedString;
		} catch (TransformerConfigurationException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			return unformattedString;
		} catch (TransformerException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			return unformattedString;
		}
	}

	/**
	 * remove blanks, tabs and linebreaks between the tags
	 * 
	 * @param xml
	 * @return
	 */
	private static String trimXML(String xml) {

		int len = xml.length();
		StringBuffer result = new StringBuffer(len);
		int pos = 0;
		while (pos < len) {
			// skip spaces forward
			while ((pos < len) && (isSpace(xml.charAt(pos))))
				pos += 1;
			if (pos >= len)
				break;
			int startCopy = pos;
			// search "<"
			while ((pos < len) && (xml.charAt(pos) != '<'))
				pos += 1;
			int nextLess = pos;
			pos -= 1;
			// skip spaces backward
			while ((pos >= startCopy) && (isSpace(xml.charAt(pos))))
				pos -= 1;
			int endCopy = pos;
			// do copy
			if (endCopy >= startCopy)
				result.append(xml.substring(startCopy, endCopy + 1));

			if (nextLess >= len)
				break;

			pos = nextLess;
			// search ">"
			while ((pos < len) && (xml.charAt(pos) != '>'))
				pos += 1;
			int nextGreater = pos;
			// do copy
			if (nextGreater >= nextLess)
				result.append(xml.substring(nextLess, nextGreater + 1));

			pos = nextGreater + 1;
		}
		return result.toString();
	}

	private static boolean isSpace(char c) {
		return (" \t\r\n".indexOf(c) != -1);
	}

}
