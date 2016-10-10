package soap;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.stream.StreamSource;

import org.junit.BeforeClass;
import org.junit.Test;
import org.wsssoapbox.bean.model.soap.SoapRequestBean;
import org.wsssoapbox.bean.model.soap.SoapPartBean;
import org.wsssoapbox.soap.support.SoapCreator;
import org.wsssoapbox.xml.util.XMLUtils;
import org.xml.sax.SAXException;

public class SoapRequestCreatorTestCase {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void testCreateSOAPMessageBean() throws SOAPException, IOException, ParserConfigurationException, SAXException, TransformerFactoryConfigurationError, TransformerException {
		
	      String fault =
	            "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/"
	                + "soap/envelope/\"><SOAP-ENV:Header/><SOAP-ENV:Body><SOAP-ENV:"
	                + "Fault><faultcode>SOAP-ENV:Server</faultcode><faultstring>"
	                + "Fault string, and possibly fault code, not set</faultstring>"
	                + "<detail><ns1:InvalidProductCode xmlns:ns1=\"http://www."
	                + "Something.com\"><ns1:Reason>InvalidProductCode</ns1:Reason><ns1:"
	                + "ProductNumber>600510</ns1:ProductNumber></ns1:InvalidProductCode>"
	                + "</detail></SOAP-ENV:Fault></SOAP-ENV:Body></SOAP-ENV:Envelope>";
		
	      

	      
		String testDoc = "<env:Envelope xmlns:env='http://schemas.xmlsoap.org/soap/envelope/'"+ 
				" xmlns:ns1='http://example.com/wsdl'>" +
				"  <env:Body>" + 
				"    <ns1:sayHello>" +
				"      <name>" +
				"          Hello Peter Conair" +
				"      </name>" +
				"    </ns1:sayHello>" + 
				"  </env:Body>" +
				"</env:Envelope>";

		String testDoc1 = "<env:Envelope xmlns:env='http://schemas.xmlsoap.org/soap/envelope/'"+ 
		" xmlns:ns1='http://example.com/wsdl'>" +
		"  <env:Body>" + 
		"    <ns1:sayHello>" +
		"      <name>" +
		"          Hello Peter Conair" +
		"      </name>" +
		"    </ns1:sayHello>" + 
		"    <ns1:sayHello1>" +
		"      <name>" +
		"          Hello Peter Conair1" +
		"      </name>" +
		"    </ns1:sayHello1>" + 
		"  </env:Body>" +
		"</env:Envelope>";

		
		byte[] testDocBytes = testDoc1.getBytes("UTF-8");
		ByteArrayInputStream bais = new ByteArrayInputStream(testDocBytes);
		StreamSource strSource = new StreamSource(bais);
		MessageFactory mf = MessageFactory.newInstance();
		SOAPMessage msg = mf.createMessage();
		SOAPPart sp = msg.getSOAPPart();
		sp.setContent(strSource);
		assertTrue(sp.getChildNodes().getLength() > 0);

		QName operationQName = new QName ("http://example.com/wsdl","sayHello", "ns");
	
		XMLUtils.print(msg);
		
		SoapRequestBean soapMessageBean = new SoapRequestBean();
		SoapPartBean soapPartBean = new SoapPartBean();

		soapMessageBean = SoapCreator.createSOAPRequestBean(msg,operationQName);
		assertNotNull(soapMessageBean);
	}

}
