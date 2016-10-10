package wsdl;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import org.wsssoapbox.bean.model.wsdl.ServicesBean;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.api.Description;
import org.ow2.easywsdl.wsdl.api.Endpoint;
import org.ow2.easywsdl.wsdl.api.Service;
import org.ow2.easywsdl.wsdl.api.WSDLException;
import org.wsssoapbox.dao.wsdl.IServiceDAO;
import org.wsssoapbox.dao.wsdl.ServiceDAO;
import org.wsssoapbox.datasource.wsdl.WSDLDataSource;

/**
 * @author  Peter
 */
public class ServiceDAOTestCase {
	private static Description desc;
	private static String url;
	/**
	 * @uml.property  name="wsdlDocumentDataSource"
	 * @uml.associationEnd  
	 */
	private static WSDLDataSource wsdlDocumentDataSource;

	@Before
	public void setUp() throws Exception {
		wsdlDocumentDataSource = new WSDLDataSource(WSDLList.url);
		ServiceDAOTestCase.desc = WSDLDataSource.getDesc(WSDLList.url);
	}

	@Test
	public void testGetServices() throws WSDLException, XmlException {

		List<ServicesBean> servicesBeanList = new ArrayList<ServicesBean>();
		System.out.println("  ****************  Service testGetServices ***************** ");
		IServiceDAO serviceDAO = new ServiceDAO();

		servicesBeanList = serviceDAO.getServices(desc);
		for (ServicesBean serviceBean : servicesBeanList) {
			System.out.println(" Servic Name : " + serviceBean.getName());
			System.out.println(" Servic Namespace URI: " + serviceBean.getNamespaceURI());
			System.out.println(" Service Qname : " + serviceBean.getQname());
			System.out.println(" service Documentation : " + serviceBean.getDocumentation());
			System.out.println(" Servic Other Attributes: " + serviceBean.getOtherAttributes());
			System.out.println(" Servic Other Elements : " + serviceBean.getOtherElements());
			System.out.println(" Servic Endpoints: " + serviceBean.getEndpoints());
			System.out.println(" Servic Interfacs : " + serviceBean.getInterfaceType());
		}
		assertTrue(servicesBeanList.size() == 1);
	}

	@Test
	public void testGetServicesDefault() throws WSDLException, XmlException {

		List<Service> serviceList = new ArrayList<Service>();
		System.out.println("  ****************  Service testGetServicesDefault ***************** ");

		serviceList = desc.getServices();
		for (Service service : serviceList) {
			System.out.println(" Servic Name : " + service.getQName().getLocalPart());
			System.out.println(" Servic Namespace URI: " + service.getQName().getNamespaceURI());
			System.out.println(" Service Qname : " + service.getQName());
			System.out.println(" service Documentation : " + service.getDocumentation());
			System.out.println(" Servic Other Attributes: " + service.getOtherAttributes());
			System.out.println(" Servic Other Elements : " + service.getOtherElements());
			System.out.println(" Servic Endpoints: " + service.getEndpoints());
			System.out.println(" Servic Interfac : " + service.getInterface());
		}
		assertTrue(serviceList.size() == 1);
	}

	@Test
	public void testGetServiceByName() throws WSDLException, XmlException {

		System.out.println("  ****************  Service testGetServiceByName ***************** ");
		ServicesBean serviceBean = new ServicesBean();
		IServiceDAO serviceDAO = new ServiceDAO();
		QName serviceQName = new QName("http://www.pttplc.com/ptt_webservice/", "PTTInfo");

		serviceBean = serviceDAO.getServiceByName(desc, serviceQName);
		System.out.println(" Servic Name : " + serviceBean.getName());
		System.out.println(" Servic Namespace URI: " + serviceBean.getNamespaceURI());
		System.out.println(" Service Qname : " + serviceBean.getQname());
		System.out.println(" service Documentation : " + serviceBean.getDocumentation());
		System.out.println(" Servic Other Attributes: " + serviceBean.getOtherAttributes());
		System.out.println(" Servic Other Elements : " + serviceBean.getOtherElements());
		System.out.println(" Servic Endpoints: " + serviceBean.getEndpoints());
		System.out.println(" Servic Interfacs : " + serviceBean.getInterfaceType());

		assertEquals("PTTInfo", serviceBean.getName());
	}

	@Test
	public void testGetServiceByIndex() throws WSDLException, XmlException {
		System.out.println("  ****************  Service testGetServiceByIndex ***************** ");
		ServicesBean serviceBean = new ServicesBean();
		IServiceDAO serviceDAO = new ServiceDAO();

		serviceBean = serviceDAO.getServiceByIndex(desc, 0);
		System.out.println(" Servic Name : " + serviceBean.getName());
		System.out.println(" Servic Namespace URI: " + serviceBean.getNamespaceURI());
		System.out.println(" Service Qname : " + serviceBean.getQname());
		System.out.println(" service Documentation : " + serviceBean.getDocumentation());
		System.out.println(" Servic Other Attributes: " + serviceBean.getOtherAttributes());
		System.out.println(" Servic Other Elements : " + serviceBean.getOtherElements());
		System.out.println(" Servic Endpoints: " + serviceBean.getEndpoints());
		System.out.println(" Servic Interfacs : " + serviceBean.getInterfaceType());

		assertEquals("PTTInfo", serviceBean.getName());
	}

	@Test
	public void testGetServiceByEndpoint() throws XmlException {
		System.out.println("  ****************  Service testGetServiceByEndpoint ***************** ");
		ServicesBean serviceBean = new ServicesBean();
		IServiceDAO serviceDAO = new ServiceDAO();

		Endpoint endpoint = desc.getServices().get(0).getEndpoints().get(0);
		serviceBean = serviceDAO.getServiceByEndpoint(endpoint);

		System.out.println(" Servic Name : " + serviceBean.getName());
		System.out.println(" Servic Namespace URI: " + serviceBean.getNamespaceURI());
		System.out.println(" Service Qname : " + serviceBean.getQname());
		System.out.println(" service Documentation : " + serviceBean.getDocumentation());
		System.out.println(" Servic Other Attributes: " + serviceBean.getOtherAttributes());
		System.out.println(" Servic Other Elements : " + serviceBean.getOtherElements());
		System.out.println(" Servic Endpoints: " + serviceBean.getEndpoints());
		System.out.println(" Servic Interfacs : " + serviceBean.getInterfaceType());

		assertEquals("PTTInfo", serviceBean.getName());
	}

}
