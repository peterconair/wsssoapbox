package wsdl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import org.junit.BeforeClass;
import org.junit.Test;
import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.api.BindingFault;
import org.ow2.easywsdl.wsdl.api.BindingOperation;
import org.ow2.easywsdl.wsdl.api.Description;
import org.wsssoapbox.bean.model.wsdl.BindingFaultBean;
import org.wsssoapbox.dao.wsdl.BindingFaultDAO;
import org.wsssoapbox.dao.wsdl.IBindingFaultDAO;
import org.wsssoapbox.datasource.wsdl.WSDLDataSource;

/**
 * @author  Peter
 */
public class BindingFaultDAOTestCase {
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
		BindingFaultDAOTestCase.desc = WSDLDataSource.getDesc(WSDLList.url);
	}

	@Test
	public void testGetFaultsByBindingOperationDefault() throws XmlException {

		List<BindingFault> bindingFaultList = desc.getBindings().get(0).getBindingOperations().get(0).getFaults();

		for (BindingFault bf : bindingFaultList) {
			System.out
					.println(" ******************* BindingFault testGetFaultsByBindingOperationDefault() **************************** ");
			System.out.println(" BinddingFault Name: " + bf.getName());
			System.out.println(" BinddingFault Http Content Encoding: " + bf.getHttpContentEncoding());
			System.out.println(" BinddingFault Documentation: " + bf.getDocumentation());
			System.out.println(" BinddingFault Other Attributes: " + bf.getOtherAttributes());
			System.out.println(" BinddingFault Other Elements: " + bf.getOtherElements());
			System.out.println(" BinddingFault HTTP Binding4Wsdl11: " + bf.getHTTPBinding4Wsdl11());
			System.out.println(" BinddingFault HTTP Binding4Wsdl20: " + bf.getHTTPBinding4Wsdl20());
			System.out.println(" BinddingFault MIME Binding4Wsdl11: " + bf.getMIMEBinding4Wsdl11());
			System.out.println(" BinddingFault SOAP11 Binding4Wsdl11: " + bf.getSOAP11Binding4Wsdl11());
			System.out.println(" BinddingFault SOAP12 Binding4Wsdl11: " + bf.getSOAP12Binding4Wsdl11());
			System.out.println(" BinddingFault SOAP12 Binding4Wsdl20: " + bf.getSOAP12Binding4Wsdl20());

		}
	}

	@Test
	public void testGetFaultsByBindingOperationFaultName() throws XmlException {

		QName bindingQName = desc.getBindings().get(0).getQName();
		String bindingOperationName = desc.getBindings().get(0).getBindingOperations().get(0).getOperation().getQName()
				.getLocalPart();

		IBindingFaultDAO bindingFaultDAO = new BindingFaultDAO();

		List<BindingFaultBean> bindingFaultBeanList = new ArrayList<BindingFaultBean>();
		bindingFaultBeanList = bindingFaultDAO.getFaultsByBindingOperationFaultName(desc, bindingQName,
				bindingOperationName);

		for (BindingFaultBean bf : bindingFaultBeanList) {
			System.out
					.println(" ******************* BindingFault testGetFaultsByBindingOperationDefault() **************************** ");
			System.out.println(" BinddingFault Name: " + bf.getName());
			System.out.println(" BinddingFault Http Content Encoding: " + bf.getHttpContentEncoding());
			System.out.println(" BinddingFault Documentation: " + bf.getDocumentation());
			System.out.println(" BinddingFault Other Attributes: " + bf.getOtherAttributes());
			System.out.println(" BinddingFault Other Elements: " + bf.getOtherElements());
			System.out.println(" BinddingFault HTTP Binding4Wsdl11: " + bf.getHTTPBinding4Wsdl11());
			System.out.println(" BinddingFault HTTP Binding4Wsdl20: " + bf.getHTTPBinding4Wsdl20());
			System.out.println(" BinddingFault MIME Binding4Wsdl11: " + bf.getMIMEBinding4Wsdl11());
			System.out.println(" BinddingFault SOAP11 Binding4Wsdl11: " + bf.getSOAP11Binding4Wsdl11());
			System.out.println(" BinddingFault SOAP12 Binding4Wsdl11: " + bf.getSOAP12Binding4Wsdl11());
			System.out.println(" BinddingFault SOAP12 Binding4Wsdl20: " + bf.getSOAP12Binding4Wsdl20());
		}
	}

	@Test
	public void testGetFaultByBindingOperationFaultName() throws XmlException {
		QName bindingQName = desc.getBindings().get(0).getQName();
		String bindingOperationName = desc.getBindings().get(0).getBindingOperations().get(0).getOperation().getQName()
				.getLocalPart();
		String faultName = desc.getBindings().get(0).getBindingOperations().get(0).getOperation().getFaults().get(0)
				.getName();

		IBindingFaultDAO bindingFaultDAO = new BindingFaultDAO();

		BindingFaultBean bf = new BindingFaultBean();
		bf = bindingFaultDAO.getFaultByBindingOperationFaultName(desc, bindingQName, bindingOperationName, faultName);

		System.out
				.println(" ******************* BindingFault testGetFaultsByBindingOperationDefault() **************************** ");
		System.out.println(" BinddingFault Name: " + bf.getName());
		System.out.println(" BinddingFault Http Content Encoding: " + bf.getHttpContentEncoding());
		System.out.println(" BinddingFault Documentation: " + bf.getDocumentation());
		System.out.println(" BinddingFault Other Attributes: " + bf.getOtherAttributes());
		System.out.println(" BinddingFault Other Elements: " + bf.getOtherElements());
		System.out.println(" BinddingFault HTTP Binding4Wsdl11: " + bf.getHTTPBinding4Wsdl11());
		System.out.println(" BinddingFault HTTP Binding4Wsdl20: " + bf.getHTTPBinding4Wsdl20());
		System.out.println(" BinddingFault MIME Binding4Wsdl11: " + bf.getMIMEBinding4Wsdl11());
		System.out.println(" BinddingFault SOAP11 Binding4Wsdl11: " + bf.getSOAP11Binding4Wsdl11());
		System.out.println(" BinddingFault SOAP12 Binding4Wsdl11: " + bf.getSOAP12Binding4Wsdl11());
		System.out.println(" BinddingFault SOAP12 Binding4Wsdl20: " + bf.getSOAP12Binding4Wsdl20());
	}

	@Test
	public void testGetFaultsByBindingOperation() throws XmlException {

		BindingOperation bindingOperation = desc.getBindings().get(0).getBindingOperations().get(0);
		IBindingFaultDAO bindingFaultDAO = new BindingFaultDAO();
		List<BindingFaultBean> bindingFaultBeanList = new ArrayList<BindingFaultBean>();
		bindingFaultBeanList = bindingFaultDAO.getFaultsByBindingOperation(bindingOperation);

		for (BindingFaultBean bf : bindingFaultBeanList) {
			System.out
					.println(" ******************* BindingFault testGetFaultsByBindingOperationDefault() **************************** ");
			System.out.println(" BinddingFault Name: " + bf.getName());
			System.out.println(" BinddingFault Http Content Encoding: " + bf.getHttpContentEncoding());
			System.out.println(" BinddingFault Documentation: " + bf.getDocumentation());
			System.out.println(" BinddingFault Other Attributes: " + bf.getOtherAttributes());
			System.out.println(" BinddingFault Other Elements: " + bf.getOtherElements());
			System.out.println(" BinddingFault HTTP Binding4Wsdl11: " + bf.getHTTPBinding4Wsdl11());
			System.out.println(" BinddingFault HTTP Binding4Wsdl20: " + bf.getHTTPBinding4Wsdl20());
			System.out.println(" BinddingFault MIME Binding4Wsdl11: " + bf.getMIMEBinding4Wsdl11());
			System.out.println(" BinddingFault SOAP11 Binding4Wsdl11: " + bf.getSOAP11Binding4Wsdl11());
			System.out.println(" BinddingFault SOAP12 Binding4Wsdl11: " + bf.getSOAP12Binding4Wsdl11());
			System.out.println(" BinddingFault SOAP12 Binding4Wsdl20: " + bf.getSOAP12Binding4Wsdl20());
		}
	}

}
