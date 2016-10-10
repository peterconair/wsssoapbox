package soap;


import java.io.IOException;
import java.net.URISyntaxException;

import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.api.Description;
import org.ow2.easywsdl.wsdl.api.WSDLException;
import org.slf4j.LoggerFactory;
import org.wsssoapbox.bean.backing.soap.SoapMessageSender;
import org.wsssoapbox.bean.model.wsdl.SoapBindingBean;
import org.wsssoapbox.dao.soap.ISoapModelDAO;
import org.wsssoapbox.dao.soap.SoapModelDAO;
import org.wsssoapbox.datasource.wsdl.WSDLDataSource;
import org.wsssoapbox.soap.support.SoapCreator;
import org.wsssoapbox.xml.util.XMLUtils;
import org.xml.sax.SAXException;

/**
 * @author  Peter
 */
public class SoapMessageSenderTestCase {

	/**
	 * @uml.property  name="soapBindingBean"
	 * @uml.associationEnd  
	 */
	private static SoapBindingBean soapBindingBean;
	/**
	 * @uml.property  name="message"
	 * @uml.associationEnd  
	 */
	private SOAPMessage message;
	private static SOAPConnection connection;
	 private static final Logger logger = LoggerFactory.getLogger(SoapMessageSenderTestCase.class);
	private static String endpoint;
	private static SOAPMessage soapResponse;
	private static QName interfaceQName;
	private static QName operationQName;
	private static QName bindingQName;
	private static Description desc;
	private static String url;
	/**
	 * @uml.property  name="wsdlDocumentDataSource"
	 * @uml.associationEnd  
	 */
	private static WSDLDataSource wsdlDocumentDataSource;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	//@Test
	public void testSentPTTCurrentOilPrice1_1() throws SOAPException, IOException, ParserConfigurationException,
			SAXException, TransformerFactoryConfigurationError, TransformerException, WSDLException, URISyntaxException {
		operationQName = new QName("http://www.pttplc.com/ptt_webservice/", "CurrentOilPrice");
		interfaceQName = new QName("http://www.pttplc.com/ptt_webservice/", "PTTInfoSoap");
		bindingQName = new QName("http://www.pttplc.com/ptt_webservice/", "PTTInfoSoap");

		url = "http://www.pttplc.com/pttinfo.asmx?WSDL";
		//	endpoint = "http://www.pttplc.com/pttinfo.asmx";
		endpoint = "http://localhost:2000/pttinfo.asmx";

		//wsdlDocumentDataSource = new WSDLDataSource(url);
		desc = WSDLDataSource.getDesc(url);

		try {
			// Create a SOAP connection
			SOAPConnectionFactory scf = SOAPConnectionFactory.newInstance();
			connection = scf.createConnection();

		} catch (Exception e) {
			logger.error("Unable to open a SOAPConnection :" + e.getMessage());
		}

		ISoapModelDAO soapModelDAO = new SoapModelDAO();

		try {
			soapBindingBean = soapModelDAO.createSoapBindingBeanDoc(desc, bindingQName, interfaceQName, operationQName);
		} catch (XmlException e) {
			e.printStackTrace();
			logger.error(" XmlException : " + e.getMessage());
		}

		// create soap message using saaj 
		message = SoapCreator.createSOAPMessage(message, soapBindingBean);
		logger.debug(" Request Mesaage Form Binding : ");
		XMLUtils.print(message);
		// convert to xml format
		String soapRequestXMLFormat = XMLUtils.getSoapMessageXML(message);

		logger.debug(" Message : \n" + soapRequestXMLFormat);

		//soapResponse = SoapMessageSender.sent(connection, message, endpoint);

		String msg = XMLUtils.getSoapMessageString(soapResponse);
		logger.debug("Tansform to String format : \n" + msg);

		//		String escapeXml = StringEscapeUtils.escapeXml(msg);

		String unescapeXml = StringEscapeUtils.unescapeXml(msg);

		//		logger.debug("escapeXml : \n" + escapeXml);

		logger.debug("escapeHtml : \n" + unescapeXml);

		//	XMLUtils.print(soapResponse);
		//	String responseString = XMLUtils.getSoapRequestXML(soapResponse);
		//	logger.debug("Ttansform to XML format : " + responseString);

		String responsePretyString = XMLUtils.prettyPrint(unescapeXml);
		logger.debug("Ttansform to Prety format \n: " + responsePretyString);

	}

		@Test
	public void testSentPTTCurrentOilPrice1_2() throws SOAPException, IOException, ParserConfigurationException,
			SAXException, TransformerFactoryConfigurationError, TransformerException, WSDLException, URISyntaxException {

		operationQName = new QName("http://www.pttplc.com/ptt_webservice/", "CurrentOilPrice");
		interfaceQName = new QName("http://www.pttplc.com/ptt_webservice/", "PTTInfoSoap");
		bindingQName = new QName("http://www.pttplc.com/ptt_webservice/", "PTTInfoSoap12");

		url = "http://www.pttplc.com/pttinfo.asmx?WSDL";
		//	endpoint = "http://www.pttplc.com/pttinfo.asmx";
		endpoint = "http://localhost:2000/pttinfo.asmx";

		//wsdlDocumentDataSource = new WSDLDataSource(url);
		desc = WSDLDataSource.getDesc(url);

		try {
			// Create a SOAP connection
			SOAPConnectionFactory scf = SOAPConnectionFactory.newInstance();
			connection = scf.createConnection();

		} catch (Exception e) {
			logger.error("Unable to open a SOAPConnection :" + e.getMessage());
		}

		ISoapModelDAO soapModelDAO = new SoapModelDAO();

		try {
			soapBindingBean = soapModelDAO.createSoapBindingBeanDoc(desc, bindingQName, interfaceQName, operationQName);
		} catch (XmlException e) {
			e.printStackTrace();
			logger.error(" XmlException : " + e.getMessage());
		}

		// create soap message using saaj 
		message = SoapCreator.createSOAPMessage(message, soapBindingBean);
		logger.debug(" Request Mesaage Form Binding : ");
		XMLUtils.print(message);
		// convert to xml format
		String soapRequestXMLFormat = XMLUtils.getSoapMessageXML(message);

		logger.debug(" Message : \n" + soapRequestXMLFormat);

		//soapResponse = SoapMessageSender.sent(connection, message, endpoint);

		String msg = XMLUtils.getSoapMessageString(soapResponse);
		logger.debug("Tansform to String format : \n" + msg);

		//		String escapeXml = StringEscapeUtils.escapeXml(msg);

		String unescapeXml = StringEscapeUtils.unescapeXml(msg);

		//		logger.debug("escapeXml : \n" + escapeXml);

		logger.debug("escapeHtml : \n" + unescapeXml);

		//	XMLUtils.print(soapResponse);
		//	String responseString = XMLUtils.getSoapRequestXML(soapResponse);
		//	logger.debug("Ttansform to XML format : " + responseString);

		String responsePretyString = XMLUtils.prettyPrint(unescapeXml);
		logger.debug("Ttansform to Prety format \n: " + responsePretyString);

	}

}
