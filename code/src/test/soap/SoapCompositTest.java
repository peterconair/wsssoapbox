package soap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
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
import org.wsssoapbox.bean.model.wsdl.InputBean;
import org.wsssoapbox.bean.model.wsdl.OperationBean;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


public class SoapCompositTest {

	public static void main(String[] args) throws SOAPException, IOException, ParserConfigurationException,
			SAXException, TransformerFactoryConfigurationError, TransformerException {

		String localName = "Operation";
		String prefix = "tns";
		String uri = "http://www.wsssoaobox.org/";

		SOAPMessage message = MessageFactory.newInstance().createMessage();
		SOAPPart part = message.getSOAPPart();
		SOAPEnvelope envelope = part.getEnvelope();
		SOAPBody body = message.getSOAPBody();

		SOAPHeader header = message.getSOAPHeader();
		header.detachNode();

		QName operationQName = new QName("http://wsssoapbox.org/", "operation1", "ns");
		OperationBean operationBean = new OperationBean();
		operationBean.setName("Operationxxxxxx");
		operationBean.setQname(operationQName);
		InputBean inputBean = new InputBean();
		inputBean.setName("para1");
		
		operationBean.setInput(inputBean);

		SOAPBodyElement bodyElement = addBodyElementItem(body,  operationBean);

		print(message);
		//	message.writeTo(System.out);

	}

	public static SOAPMessage createSOAPRequestMessage(Map a, Map b ) throws SOAPException{
		SOAPMessage message = MessageFactory.newInstance().createMessage();
		
		SOAPPart part = message.getSOAPPart();
		SOAPEnvelope envelope = part.getEnvelope();
		SOAPBody body = message.getSOAPBody();

		SOAPHeader header = message.getSOAPHeader();
		header.detachNode();
		
		return null;
	}
	public static SOAPBodyElement addBodyElementItem(SOAPBody body, OperationBean operaiton) throws SOAPException {
		SOAPBodyElement bodyElement;
		//Operation data
		String localName = operaiton.getQname().getLocalPart();
		String prefix = operaiton.getQname().getPrefix();
		String uri = operaiton.getQname().getNamespaceURI();

		Name operationName = SOAPFactory.newInstance().createName(localName, prefix, uri);

		// Parameter Data
		InputBean inputBean = new InputBean();
		inputBean = operaiton.getInput();
		List<InputBean> parameters = new ArrayList<InputBean>();
		parameters.add(inputBean);
		Name parameterName = SOAPFactory.newInstance().createName(inputBean.getName(), prefix, uri);

		bodyElement = body.addBodyElement(operationName);
		String value = "xxxx";
		for (InputBean ib : parameters) {
			bodyElement.addChildElement(parameterName).addTextNode(value);
		}
		return bodyElement;
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
