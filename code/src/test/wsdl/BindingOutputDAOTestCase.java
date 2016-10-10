package wsdl;

import static org.junit.Assert.*;

import javax.xml.namespace.QName;

import org.junit.BeforeClass;
import org.junit.Test;
import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.api.BindingOperation;
import org.ow2.easywsdl.wsdl.api.BindingOutput;
import org.ow2.easywsdl.wsdl.api.Description;
import org.wsssoapbox.bean.model.wsdl.BindingOutputBean;
import org.wsssoapbox.dao.wsdl.BindingOutputDAO;
import org.wsssoapbox.dao.wsdl.IBindingOutputDAO;
import org.wsssoapbox.datasource.wsdl.WSDLDataSource;

/**
 * @author  Peter
 */
public class BindingOutputDAOTestCase {
	private static Description desc;
	private static String url;
	/**
	 * @uml.property  name="wsdlDocumentDataSource"
	 * @uml.associationEnd  
	 */
	private static WSDLDataSource wsdlDocumentDataSource;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		wsdlDocumentDataSource = new WSDLDataSource(WSDLList.url);
		BindingOutputDAOTestCase.desc = WSDLDataSource.getDesc(WSDLList.url);
	}

	@Test
	public void testGetOutputByBindingDefault() throws XmlException {

		BindingOutput bo = desc.getBindings().get(0).getBindingOperations().get(0).getOutput();

		System.out
				.println(" ******************* BindingOutput testGetOutputByBindingDefault() **************************** ");
		System.out.println(" Output Name: " + bo.getName());
		System.out.println(" Output Http Content Encoding: " + bo.getHttpContentEncoding());
		System.out.println(" Output Documentation: " + bo.getDocumentation());
		System.out.println(" Output Other Attributes: " + bo.getOtherAttributes());
		System.out.println(" Output Other Elements: " + bo.getOtherElements());
		System.out.println(" Output HTTP Binding4Wsdl11: " + bo.getHTTPBinding4Wsdl11());
		System.out.println(" Output HTTP Binding4Wsdl20: " + bo.getHTTPBinding4Wsdl20());
		System.out.println(" Output MIME Binding4Wsdl11: " + bo.getMIMEBinding4Wsdl11());
		System.out.println(" Output SOAP11 Binding4Wsdl11: " + bo.getSOAP11Binding4Wsdl11());
		System.out.println(" Output SOAP12 Binding4Wsdl11: " + bo.getSOAP12Binding4Wsdl11());
		System.out.println(" Output SOAP12 Binding4Wsdl20: " + bo.getSOAP12Binding4Wsdl20());
	}

	@Test
	public void testGetOutputByBindingName() throws XmlException {

		QName bindingQName = desc.getBindings().get(0).getQName();
		String operationName = desc.getBindings().get(0).getBindingOperations().get(0).getOperation().getQName()
				.getLocalPart();
		IBindingOutputDAO bindingOutputDAO = new BindingOutputDAO();

		BindingOutputBean bo = new BindingOutputBean();
		bo = bindingOutputDAO.getOutputByBindingName(desc, bindingQName, operationName);

		System.out
				.println(" ******************* BindingOutput testGetOutputByBindingName() **************************** ");
		System.out.println(" Output Name: " + bo.getName());
		System.out.println(" Output HTTP Content Encoding: " + bo.getHttpContentEncoding());
		System.out.println(" Output Documentation: " + bo.getDocumentation());
		System.out.println(" Output Other Attributes: " + bo.getOtherAttributes());
		System.out.println(" Output Other Elements: " + bo.getOtherElements());
		System.out.println(" Output HTTP Binding4Wsdl11: " + bo.getHTTPBinding4Wsdl11());
		System.out.println(" Output HTTP Binding4Wsdl20: " + bo.getHTTPBinding4Wsdl20());
		System.out.println(" Output MIME Binding4Wsdl11: " + bo.getMIMEBinding4Wsdl11());
		System.out.println(" Output SOAP11 Binding4Wsdl11: " + bo.getSOAP11Binding4Wsdl11());
		System.out.println(" Output SOAP12 Binding4Wsdl11: " + bo.getSOAP12Binding4Wsdl11());
		System.out.println(" Output SOAP12 Binding4Wsdl20: " + bo.getSOAP12Binding4Wsdl20());
	}

	@Test
	public void testGetOutputByBindingOperation() throws XmlException {

		BindingOperation bindingOperation = desc.getBindings().get(0).getBindingOperations().get(0);
		IBindingOutputDAO bindingOutputDAO = new BindingOutputDAO();

		BindingOutputBean bo = new BindingOutputBean();
		bo = bindingOutputDAO.getOutputByBindingOperation(bindingOperation);

		System.out
				.println(" ******************* BindingOutput testGetOutputByBindingName() **************************** ");
		System.out.println(" Output Name: " + bo.getName());
		System.out.println(" Output HTTP Content Encoding: " + bo.getHttpContentEncoding());
		System.out.println(" Output Documentation: " + bo.getDocumentation());
		System.out.println(" Output Other Attributes: " + bo.getOtherAttributes());
		System.out.println(" Output Other Elements: " + bo.getOtherElements());
		System.out.println(" Output HTTP Binding4Wsdl11: " + bo.getHTTPBinding4Wsdl11());
		System.out.println(" Output HTTP Binding4Wsdl20: " + bo.getHTTPBinding4Wsdl20());
		System.out.println(" Output MIME Binding4Wsdl11: " + bo.getMIMEBinding4Wsdl11());
		System.out.println(" Output SOAP11 Binding4Wsdl11: " + bo.getSOAP11Binding4Wsdl11());
		System.out.println(" Output SOAP12 Binding4Wsdl11: " + bo.getSOAP12Binding4Wsdl11());
		System.out.println(" Output SOAP12 Binding4Wsdl20: " + bo.getSOAP12Binding4Wsdl20());
	}

}
