package wsdl;


import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.api.Binding;
import org.ow2.easywsdl.wsdl.api.Description;
import org.ow2.easywsdl.wsdl.api.Endpoint;
import org.ow2.easywsdl.wsdl.api.WSDLException;
import org.wsssoapbox.bean.model.wsdl.BindingBean;
import org.wsssoapbox.dao.wsdl.BindingDAO;
import org.wsssoapbox.dao.wsdl.IBindingDAO;
import org.wsssoapbox.datasource.wsdl.WSDLDataSource;

/**
 * @author  Peter
 */
public class BindingDAOTestCase  {
	private static Description desc;
	/**
	 * @uml.property  name="wsdlDocumentDataSource"
	 * @uml.associationEnd  
	 */
	private static WSDLDataSource wsdlDocumentDataSource;


	@Before
	public void setUp() throws Exception {
		wsdlDocumentDataSource = new WSDLDataSource(WSDLList.url);
		BindingDAOTestCase.desc = WSDLDataSource.getDesc(WSDLList.url);
	}

	// get all Binding from Description used Bean
//	@Test
	public void testGetBindings() throws XmlException {

		IBindingDAO bindingDAO = new BindingDAO();

		System.out.println("  ****************All BindingBean from WSDL Document ***************** ");
		List<BindingBean> bbList = new ArrayList<BindingBean>();
		bbList = bindingDAO.getBindings(desc);

		assertNotNull("Binding List Null ", bbList);
	//	assertEquals(2, bbList.size());
	//	assertEquals("PTTInfoSoap",bbList.get(0).getQname().getLocalPart());
	//	assertEquals("PTTInfoSoap12",bbList.get(1).getQname().getLocalPart());
		
		if (bbList != null) {
			for (BindingBean bb : bbList) {
				System.out.println("***********************************************");
				System.out.println(" Binding Name() : " + bb.getName());
				System.out.println("***********************************************");
				System.out.println(" Binding Qname : " + bb.getQname());
				System.out.println(" Binding Documentation : " + bb.getDocumentation());
				System.out.println(" Binding Http Content Encoding : " + bb.getHttpContentEncodingDefault());
				System.out.println(" Binding Http Default Method : " + bb.getHttpDefaultMethod());
				System.out.println(" Binding Transport Protocol : " + bb.getTransportProtocol());
				System.out.println(" Binding Type Of Binding : " + bb.getTypeOfBinding());
				System.out.println(" Binding Version : " + bb.getVersion());
				System.out.println(" Binding Style : " + bb.getStyle());
				System.out.println(" Binding Other Attributes : " + bb.getOtherAttributes());
				System.out.println(" Binding Other Elements : " + bb.getOtherElements());
				System.out.println(" Binding Binding OperationList : " + bb.getBindingOperations());
				System.out.println(" Binding Interfaces : " + bb.getInterfaces());
			}
		}
	}

	// get all Bindings used WSDL API
	//@Test
	public void testGetBindingsDefault() throws XmlException {

		System.out.println("  ****************All Binding testGetBindingsDefault ***************** ");
		List<Binding> bList = new ArrayList<Binding>();
		bList = desc.getBindings();

		assertNotNull("Binding List Null ", bList);

		if (bList != null) {
			for (Binding bb : bList) {

				System.out.println("***********************************************");
				System.out.println(" Binding Name() : " + bb.getQName().getLocalPart());
				System.out.println("***********************************************");
				System.out.println(" Binding Namespace URI: " + bb.getQName().getNamespaceURI());
				System.out.println(" Binding Qname : " + bb.getQName());
				System.out.println(" Binding Documentation : " + bb.getDocumentation());
				System.out.println(" Binding Http Content Encoding : " + bb.getHttpContentEncodingDefault());
				System.out.println(" Binding Http Default Method : " + bb.getHttpDefaultMethod());
				System.out.println(" Binding Http Query Parameter Default : "
						+ bb.getHttpQueryParameterSeparatorDefault());
				System.out.println(" Binding Transport Protocol : " + bb.getTransportProtocol());
				System.out.println(" Binding Type Of Binding : " + bb.getTypeOfBinding());
				System.out.println(" Binding Version : " + bb.getVersion());
				System.out.println(" Binding Style : " + bb.getStyle());
				System.out.println(" Binding Other Attributes : " + bb.getOtherAttributes());
				System.out.println(" Binding Other Elements : " + bb.getOtherElements());
				System.out.println(" Binding Binding OperationList : " + bb.getBindingOperations());
				System.out.println(" Binding Interfaces : " + bb.getInterface());
			}
		}
	}

//	@Test
	public void testGetBindingByEndpointName() throws WSDLException, XmlException {
		
		IBindingDAO bindingDAO = new BindingDAO();

		System.out.println("  ****************BindingBean from testGetBindingByEndpointName ***************** ");
		BindingBean bb = new BindingBean();
		QName serviceQName = desc.getServices().get(0).getQName();
		String endpointName = desc.getServices().get(0).getEndpoints().get(0).getName();
		
		bb = bindingDAO.getBindingByEndpointName(desc, serviceQName, endpointName);
		
		assertNotNull(bb);
	//	assertEquals("PTTInfoSoap", bb.getName());
		//
		System.out.println("***********************************************");
		System.out.println(" Binding Name() : " + bb.getName());
		System.out.println("***********************************************");
		System.out.println(" Binding Qname : " + bb.getQname());
		System.out.println(" Binding Documentation : " + bb.getDocumentation());
		System.out.println(" Binding Http Content Encoding : " + bb.getHttpContentEncodingDefault());
		System.out.println(" Binding Http Default Method : " + bb.getHttpDefaultMethod());
		System.out.println(" Binding Transport Protocol : " + bb.getTransportProtocol());
		System.out.println(" Binding Type Of Binding : " + bb.getTypeOfBinding());
		System.out.println(" Binding Version : " + bb.getVersion());
		System.out.println(" Binding Style : " + bb.getStyle());
		System.out.println(" Binding Other Attributes : " + bb.getOtherAttributes());
		System.out.println(" Binding Other Elements : " + bb.getOtherElements());
		System.out.println(" Binding Binding OperationList : " + bb.getBindingOperations());
		System.out.println(" Binding Interfaces : " + bb.getInterfaces());
	}

	//@Test
	public void testGetBindingByIndex() throws WSDLException, XmlException {

		IBindingDAO bindingDAO = new BindingDAO();

		System.out.println("  ****************BindingBean from testGetBindingByIndex ***************** ");
		BindingBean bb = new BindingBean();
	
		bb = bindingDAO.getBindingByIndex(desc, 0);
		
		assertNotNull(bb);
//		assertEquals("PTTInfoSoap", bb.getName());
		
		System.out.println("***********************************************");
		System.out.println(" Binding Name() : " + bb.getName());
		System.out.println("***********************************************");
		System.out.println(" Binding Qname : " + bb.getQname());
		System.out.println(" Binding Documentation : " + bb.getDocumentation());
		System.out.println(" Binding Http Content Encoding : " + bb.getHttpContentEncodingDefault());
		System.out.println(" Binding Http Default Method : " + bb.getHttpDefaultMethod());
		System.out.println(" Binding Transport Protocol : " + bb.getTransportProtocol());
		System.out.println(" Binding Type Of Binding : " + bb.getTypeOfBinding());
		System.out.println(" Binding Version : " + bb.getVersion());
		System.out.println(" Binding Style : " + bb.getStyle());
		System.out.println(" Binding Other Attributes : " + bb.getOtherAttributes());
		System.out.println(" Binding Other Elements : " + bb.getOtherElements());
		System.out.println(" Binding Binding OperationList : " + bb.getBindingOperations());
		System.out.println(" Binding Interfaces : " + bb.getInterfaces());
		
		bb = bindingDAO.getBindingByIndex(desc, 0);
		
		assertNotNull(bb);
//		assertEquals("PTTInfoSoap12", bb.getName());

	}

	@Test
	public void testGetBindingByName() throws WSDLException, XmlException {
		IBindingDAO bindingDAO = new BindingDAO();

		System.out.println("  ****************BindingBean from testGetBindingByIndex ***************** ");
		BindingBean bb = new BindingBean();
		QName bindingQName = desc.getBindings().get(0).getQName();
		bb = bindingDAO.getBindingByName(desc, bindingQName);
		
		assertNotNull(bb);
//		assertEquals("PTTInfoSoap", bb.getName());
//		assertEquals("CurrentOilPrice", bb.getBindingOperations().get(0).getName());
//		assertEquals("PTTInfoSoap", bb.getInterfaces().getName());
		
		System.out.println("***********************************************");
		System.out.println(" Binding Name() : " + bb.getName());
		System.out.println("***********************************************");
		System.out.println(" Binding Qname : " + bb.getQname());
		System.out.println(" Binding Documentation : " + bb.getDocumentation());
		System.out.println(" Binding Http Content Encoding : " + bb.getHttpContentEncodingDefault());
		System.out.println(" Binding Http Default Method : " + bb.getHttpDefaultMethod());
		System.out.println(" Binding Transport Protocol : " + bb.getTransportProtocol());
		System.out.println(" Binding Type Of Binding : " + bb.getTypeOfBinding());
		System.out.println(" Binding Version : " + bb.getVersion());
		System.out.println(" Binding Style : " + bb.getStyle());
		System.out.println(" Binding Other Attributes : " + bb.getOtherAttributes());
		System.out.println(" Binding Other Elements : " + bb.getOtherElements());
		System.out.println(" Binding Binding OperationList : " + bb.getBindingOperations());
		System.out.println(" Binding Interfaces : " + bb.getInterfaces());
		
	}

	//@Test
	public void testGetBindingByEndpoint() throws XmlException {
		
		IBindingDAO bindingDAO = new BindingDAO();
		System.out.println("  ****************BindingBean from testGetBindingByIndex ***************** ");
		BindingBean bb = new BindingBean();
		QName bindingQName = desc.getBindings().get(0).getQName();
		Endpoint endpoint = desc.getServices().get(0).getEndpoints().get(0);
		
		bb = bindingDAO.getBindingByEndpoint(endpoint);
		
		assertNotNull(bb);
//		assertEquals("PTTInfoSoap", bb.getName());
//		assertEquals("CurrentOilPrice", bb.getBindingOperations().get(0).getName());
//		assertEquals("PTTInfoSoap", bb.getInterfaces().getName());
		
		System.out.println("***********************************************");
		System.out.println(" Binding Name() : " + bb.getName());
		System.out.println("***********************************************");
		System.out.println(" Binding Qname : " + bb.getQname());
		System.out.println(" Binding Documentation : " + bb.getDocumentation());
		System.out.println(" Binding Http Content Encoding : " + bb.getHttpContentEncodingDefault());
		System.out.println(" Binding Http Default Method : " + bb.getHttpDefaultMethod());
		System.out.println(" Binding Transport Protocol : " + bb.getTransportProtocol());
		System.out.println(" Binding Type Of Binding : " + bb.getTypeOfBinding());
		System.out.println(" Binding Version : " + bb.getVersion());
		System.out.println(" Binding Style : " + bb.getStyle());
		System.out.println(" Binding Other Attributes : " + bb.getOtherAttributes());
		System.out.println(" Binding Other Elements : " + bb.getOtherElements());
		System.out.println(" Binding Binding OperationList : " + bb.getBindingOperations());
		System.out.println(" Binding Interfaces : " + bb.getInterfaces());
	}

}
