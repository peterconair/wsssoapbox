package wsdl;

import static org.junit.Assert.*;

import java.util.List;

import javax.xml.namespace.QName;

import org.junit.Before;
import org.junit.Test;
import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.api.Description;
import org.ow2.easywsdl.wsdl.api.Endpoint;
import org.ow2.easywsdl.wsdl.api.Service;
import org.ow2.easywsdl.wsdl.api.WSDLException;
import org.wsssoapbox.bean.model.wsdl.EndpointBean;
import org.wsssoapbox.dao.wsdl.EndpointDAO;
import org.wsssoapbox.dao.wsdl.IEndpointDAO;
import org.wsssoapbox.datasource.wsdl.WSDLDataSource;

/**
 * @author  Peter
 */
public class EndpointDAOTestCase {

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
		EndpointDAOTestCase.desc = WSDLDataSource.getDesc(WSDLList.url);
	}

	@Test
	public void testGetEndpointsByServiceName() throws WSDLException, XmlException {

		System.out.println("   ***************  EndPoint testGetEndpointsByServiceName() ***************** ");

		IEndpointDAO endpointDAO = new EndpointDAO();
		QName serviceQName = desc.getServices().get(0).getQName();
		System.out.println("Service QName for Endpoint : " + serviceQName.getLocalPart());

		List<EndpointBean> endpointBeanList = endpointDAO.getEndpointsByServiceName(desc, serviceQName);

		for (EndpointBean epb : endpointBeanList) {
			System.out.println("EndPoint Name : " + epb.getName());
			System.out.println("EndPoint Address : " + epb.getAddress());
			System.out.println("EndPoint Documentation : " + epb.getDocumentation());
			System.out.println("EndPoint Http Authentication Reaml : " + epb.getHttpAuthenticationRealm());
			System.out.println("EndPoint Http Authetication Scheme : " + epb.getHttpAuthenticationScheme());
			System.out.println("EndPoint Other Elements : " + epb.getOtherElements());
			System.out.println("EndPoint Other Attributes : " + epb.getOtherAttributes());
			System.out.println("EndPoint Binding: " + epb.getBinding());
			System.out.println("EndPoint Service : " + epb.getService());
		}
	}

	@Test
	public void testGetEndpointsByServiceNameDefualt() throws WSDLException, XmlException {

		System.out.println("   ***************  EndPoint testGetEndpointsByServiceNameDefualt() ***************** ");

		List<Endpoint> endpointList = desc.getServices().get(0).getEndpoints();

		for (Endpoint epb : endpointList) {
			System.out.println("EndPoint Name : " + epb.getName());
			System.out.println("EndPoint Address : " + epb.getAddress());
			System.out.println("EndPoint Documentation : " + epb.getDocumentation());
			System.out.println("EndPoint Http Authentication Reaml : " + epb.getHttpAuthenticationRealm());
			System.out.println("EndPoint Http Authetication Scheme : " + epb.getHttpAuthenticationScheme());
			System.out.println("EndPoint Other Elements : " + epb.getOtherElements());
			System.out.println("EndPoint Other Attributes : " + epb.getOtherAttributes());
			System.out.println("EndPoint Binding: " + epb.getBinding());
			System.out.println("EndPoint Service : " + epb.getService());
		}
	}

	@Test
	public void testGetEndpointByName() throws WSDLException, XmlException {
		System.out.println("   ***************  EndPoint testGetEndpointByName() ***************** ");

		IEndpointDAO endpointDAO = new EndpointDAO();
		EndpointBean epb = new EndpointBean();
		QName serviceQName = desc.getServices().get(0).getQName();
		String endpontName = desc.getServices().get(0).getEndpoints().get(0).getName();

		epb = endpointDAO.getEndpointByName(desc, serviceQName, endpontName);

		System.out.println("EndPoint Name : " + epb.getName());
		System.out.println("EndPoint Address : " + epb.getAddress());
		System.out.println("EndPoint Documentation : " + epb.getDocumentation());
		System.out.println("EndPoint Http Authentication Reaml : " + epb.getHttpAuthenticationRealm());
		System.out.println("EndPoint Http Authetication Scheme : " + epb.getHttpAuthenticationScheme());
		System.out.println("EndPoint Other Elements : " + epb.getOtherElements());
		System.out.println("EndPoint Other Attributes : " + epb.getOtherAttributes());
		System.out.println("EndPoint Binding: " + epb.getBinding());
		System.out.println("EndPoint Service : " + epb.getService());

	}

	@Test
	public void testGetEndpointsByService() throws XmlException {
		System.out.println("   ***************  EndPoint testGetEndpointsByService() ***************** ");

		IEndpointDAO endpointDAO = new EndpointDAO();
		Service service= desc.getServices().get(0);			

		System.out.println("Service QName for Endpoint : " + service.getQName().getLocalPart());

		List<EndpointBean> endpointBeanList = endpointDAO.getEndpointsByService(service);

		for (EndpointBean epb : endpointBeanList) {
			System.out.println("EndPoint Name : " + epb.getName());
			System.out.println("EndPoint Address : " + epb.getAddress());
			System.out.println("EndPoint Documentation : " + epb.getDocumentation());
			System.out.println("EndPoint Http Authentication Reaml : " + epb.getHttpAuthenticationRealm());
			System.out.println("EndPoint Http Authetication Scheme : " + epb.getHttpAuthenticationScheme());
			System.out.println("EndPoint Other Elements : " + epb.getOtherElements());
			System.out.println("EndPoint Other Attributes : " + epb.getOtherAttributes());
			System.out.println("EndPoint Binding: " + epb.getBinding());
			System.out.println("EndPoint Service : " + epb.getService());
		}
	}

}
