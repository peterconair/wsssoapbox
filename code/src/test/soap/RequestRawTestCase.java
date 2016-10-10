package soap;


import org.junit.BeforeClass;
import org.junit.Test;
import org.wsssoapbox.bean.backing.soap.SoapRequestRawTab;

/**
 * @author  Peter
 */
public class RequestRawTestCase {

	/**
	 * @uml.property  name="requestRaw"
	 * @uml.associationEnd  
	 */
	static SoapRequestRawTab requestRaw;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		requestRaw = new SoapRequestRawTab();
	}

	@Test
	public void testSetRawRequestPreFormat() {
		String soapRequestXMLFormat = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\""+
			            "xmlns:http=\"http://schemas.xmlsoap.org/wsdl/http/\" " +
			            "xmlns:mime=\"http://schemas.xmlsoap.org/wsdl/mime/\" " +
			            "xmlns:s=\"http://www.w3.org/2001/XMLSchema\" " +
			            "xmlns:soap=\"http://schemas.xmlsoap.org/wsdl/soap/\"" +
			            " xmlns:soap12=\"http://schemas.xmlsoap.org/wsdl/soap12/\"" +
			            " xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" " +
			            "xmlns:tm=\"http://microsoft.com/wsdl/mime/textMatching/\" "+
			            " xmlns:tns=\"http://www.pttplc.com/ptt_webservice/\" " +
			            "xmlns:wsdl=\"http://schemas.xmlsoap.org/wsdl/\">" +
			            "<SOAP-ENV:Body>" +
			            "<tns:GetOilPrice>" +
			            "<tns:Language>?</tns:Language>" +
			            "<tns:DD>?</tns:DD>" +
			            "<tns:MM>?</tns:MM>" +
			            "<tns:YYYY>?</tns:YYYY></tns:GetOilPrice>" +
			            "</SOAP-ENV:Body>" +
			            "</SOAP-ENV:Envelope>";
		System.out.println("Bofore : " +soapRequestXMLFormat);
	//	requestRaw.setSoapRequestXMLFormat(soapRequestXMLFormat);
	}

	@Test
	public void testGetRawRequestPreFormat() {
		//System.out.println("Soap with :"+requestRaw.getSoapRequestXMLFormat());
	}
}
