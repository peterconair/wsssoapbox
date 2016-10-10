package soap;

import java.io.FileInputStream;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
import javax.xml.ws.WebServiceException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.sun.xml.internal.messaging.saaj.soap.ver1_1.Message1_1Impl;
import com.sun.xml.internal.ws.api.SOAPVersion;
import com.sun.xml.internal.ws.binding.SOAPBindingImpl;

public class DispatchSOAPMessageTestCase {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @uml.property  name="soapMsg"
	 * @uml.associationEnd  readOnly="true"
	 */
	SOAPMessage soapMsg;
	/**
	 * @uml.property  name="msgFactory"
	 * @uml.associationEnd  readOnly="true"
	 */
	MessageFactory msgFactory;

	@Test
	public void buildSoapEnv() {
		try {
			URL wsdl = new URL("http://www.pttplc.com/pttinfo.asmx?WSDL");
			String ns = "http://www.pttplc.com/ptt_webservice/";
			//Create the Service name
			String svcName = "PTTInfo";
			QName svcQName = new QName(ns, svcName);
			//Get a delegate wrapper
			Service service = Service.create(wsdl, svcQName);

			System.out.println(" Service Name : " + service.getServiceName());
			System.out.println(" WSDL location : " + service.getWSDLDocumentLocation());
			Iterator<QName> i = service.getPorts();

			while (i.hasNext()) {
				QName name = i.next();
				System.out.println(" Poart QName : " + name);
			}

			//Create the Port name
			String portName = "PTTInfoSoap12";
			QName portQName = new QName(ns, portName);

			Dispatch<SOAPMessage> dispatch = service.createDispatch(portQName, SOAPMessage.class, Service.Mode.MESSAGE);

			System.out.println(" Binding " + dispatch.getBinding());

			System.out.println(" Endpoint Reference " + dispatch.getEndpointReference());

			System.out.println(" Request Context " + dispatch.getRequestContext());

			//Dispatch<SOAPMessage> dispatch1 = service
			//	.createDispatch(portQName, SOAPMessage.class, Service.Mode.PAYLOAD);

			System.out.println(dispatch.getBinding());

			System.out.println("Content Type : " + SOAPBindingImpl.getDefaultBinding().getSOAPVersion().contentType);
			System.out.println("http Binding Id : "
					+ SOAPBindingImpl.getDefaultBinding().getSOAPVersion().httpBindingId);
			System.out.println("implicit Role : " + SOAPBindingImpl.getDefaultBinding().getSOAPVersion().implicitRole);
			System.out.println("ns Uri : " + SOAPBindingImpl.getDefaultBinding().getSOAPVersion().nsUri);
			System.out.println("role Attribute Name : "
					+ SOAPBindingImpl.getDefaultBinding().getSOAPVersion().roleAttributeName);
			System.out.println("Name : " + SOAPBindingImpl.getDefaultBinding().getSOAPVersion().name());
			System.out.println("ordinal : " + SOAPBindingImpl.getDefaultBinding().getSOAPVersion().ordinal());
			System.out.println("role Attribute Name : "
					+ SOAPBindingImpl.getDefaultBinding().getSOAPVersion().ordinal());
			System.out.println("To String : " + SOAPBindingImpl.getDefaultBinding().getSOAPVersion().toString());
			System.out.println("faultCodeClient : "
					+ SOAPBindingImpl.getDefaultBinding().getSOAPVersion().faultCodeClient);
			System.out.println("faultCodeMustUnderstand : "
					+ SOAPBindingImpl.getDefaultBinding().getSOAPVersion().faultCodeMustUnderstand);
			SOAPBindingImpl.getDefaultBinding().getSOAPVersion();
			System.out.println("Value : " + SOAPVersion.values());

			MessageFactory msgFacory = SOAPBindingImpl.getDefaultBinding().getSOAPVersion().saajMessageFactory;

			
			SOAPMessage msg = msgFacory.createMessage();
			
			msg.writeTo(System.out);

			SOAPMessage soapMsg = MessageFactory.newInstance().createMessage(); //Get the body from the envelope 
			SOAPPart soapPart = soapMsg.getSOAPPart();
			SOAPEnvelope env = soapPart.getEnvelope();
			SOAPBody body = env.getBody();

			//Create a qualified name for the namespace of the //objects used by the service. 
			String iNs = "http://www.pttplc.com/ptt_webservice/";
			String elementName = "CurrentOilPrice";
			QName isbnQName = new QName(iNs, elementName, "tns");

			//Add the <isbn> element to the SOAP body //as its only child
			SOAPElement bodyElement = body.addChildElement(isbnQName);
			QName paraQName = new QName("http://www.pttplc.com/ptt_webservice/", "Language", "tns");

			SOAPElement para = bodyElement.addChildElement(paraQName);
			para.addTextNode("en");

			//	 Message1_1Impl 

			System.out.println(" Messsgee : " + soapMsg);
			//debug print what we're sending soapMsg.writeTo(System.out);
			System.out.println("\nInvoking...");

			//send the message as request to service and get response 
			SOAPMessage response = dispatch.invoke(soapMsg);

			//just show in the console for now
			response.writeTo(System.out);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public void dispatchMsgIsbnTest() {
		try {
			URL wsdl = new URL("http://localhost:8080/CatalogService/Catalog?wsdl");
			String ns = "http://ns.soacookbook.com/ws/catalog";
			//Create the Service qualified name
			String svcName = "CatalogService";
			QName svcQName = new QName(ns, svcName);
			//Get a delegate wrapper
			Service service = Service.create(wsdl, svcQName);
			//Create the Port name
			String portName = "CatalogPort";
			QName portQName = new QName(ns, portName);
			//Create the delegate to send the request:
			Dispatch<SOAPMessage> dispatch = service.createDispatch(portQName, SOAPMessage.class, Service.Mode.MESSAGE);

			String dataFile = "/path/src/xml/ch03/isbnMsg.txt";
			//read in the data to use in building the soap message from a file
			FileInputStream fis = new FileInputStream(dataFile);
			//create the message, using contents of file as envelope
			SOAPMessage request = MessageFactory.newInstance().createMessage(null, fis);
			//debug print what we're sending
			request.writeTo(System.out);
			System.out.println("\nInvoking...");

			//send the message as request to service and get response
			SOAPMessage response = dispatch.invoke(request);
			//just show in the console for now
			response.writeTo(System.out);
		} catch (MalformedURLException mue) {
			mue.printStackTrace();
		} catch (WebServiceException wsex) {
			wsex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
