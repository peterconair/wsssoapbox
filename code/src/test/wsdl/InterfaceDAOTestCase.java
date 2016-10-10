package wsdl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import org.junit.Before;
import org.junit.Test;
import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.api.Binding;
import org.ow2.easywsdl.wsdl.api.Description;
import org.ow2.easywsdl.wsdl.api.InterfaceType;
import org.ow2.easywsdl.wsdl.api.Service;
import org.ow2.easywsdl.wsdl.api.WSDLException;
import org.wsssoapbox.bean.model.wsdl.InterfaceBean;
import org.wsssoapbox.dao.wsdl.IInterfaceDAO;
import org.wsssoapbox.dao.wsdl.InterfaceDAO;
import org.wsssoapbox.datasource.wsdl.WSDLDataSource;

/**
 * @author  Peter
 */
public class InterfaceDAOTestCase {
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
		InterfaceDAOTestCase.desc = WSDLDataSource.getDesc(WSDLList.url);
	}

	@Test
	public void testGetInterfaces() throws WSDLException, XmlException {
		System.out.println("   ***************  Interface testGetInterfaces() ***************** ");
		IInterfaceDAO interfaceDAO = new InterfaceDAO();
		List<InterfaceBean> interfaceBeanList = new ArrayList<InterfaceBean>();
		interfaceBeanList = interfaceDAO.getInterfaces(desc);

		for (InterfaceBean ib : interfaceBeanList) {
			System.out.println(" Interface Name : " + ib.getName());
			System.out.println(" Interface NamespaceURI : " + ib.getNamespaceURI());
			System.out.println(" Interface Qname : " + ib.getQname());
			System.out.println(" Interface Documentation : " + ib.getDocumentation());
			System.out.println(" Interface Ohter Elements : " + ib.getOhterElements());
			System.out.println(" Interface Other Attributes : " + ib.getOtherAttributes());
			System.out.println(" Interface Operations : " + ib.getOperations());

		}
	}

	@Test
	public void testGetInterfacesDefault() throws WSDLException, XmlException {
		System.out.println("   ***************  Interface testGetInterfacesDefault() ***************** ");

		List<InterfaceType> interfaceList = new ArrayList<InterfaceType>();
		interfaceList = desc.getInterfaces();

		for (InterfaceType ib : interfaceList) {
			System.out.println(" Interface Name : " + ib.getQName().getLocalPart());
			System.out.println(" Interface NamespaceURI : " + ib.getQName().getNamespaceURI());
			System.out.println(" Interface Qname : " + ib.getQName());
			System.out.println(" Interface Documentation : " + ib.getDocumentation());
			System.out.println(" Interface Ohter Elements : " + ib.getOtherElements());
			System.out.println(" Interface Other Attributes : " + ib.getOtherAttributes());
			System.out.println(" Interface Operations : " + ib.getOperations());

		}
	}

	@Test
	public void testGetInterfaceByName() throws WSDLException, XmlException {
		System.out.println("   ***************  Interface testGetInterfaceByName() ***************** ");
		IInterfaceDAO interfaceDAO = new InterfaceDAO();
		InterfaceBean ib = new InterfaceBean();
		QName interfaceQName = new QName("http://www.pttplc.com/ptt_webservice/", "PTTInfoSoap");
		ib = interfaceDAO.getInterfaceByName(desc, interfaceQName);

		System.out.println(" Interface Name : " + ib.getName());
		System.out.println(" Interface NamespaceURI : " + ib.getNamespaceURI());
		System.out.println(" Interface Qname : " + ib.getQname());
		System.out.println(" Interface Documentation : " + ib.getDocumentation());
		System.out.println(" Interface Ohter Elements : " + ib.getOhterElements());
		System.out.println(" Interface Other Attributes : " + ib.getOtherAttributes());
		System.out.println(" Interface Operations : " + ib.getOperations());

		assertEquals("PTTInfoSoap", ib.getName());
	}

	@Test
	public void testGetInterfaceByIndex() throws WSDLException, XmlException {
		System.out.println("   ***************  Interface testGetInterfaceByIndex() ***************** ");
		IInterfaceDAO interfaceDAO = new InterfaceDAO();
		InterfaceBean ib = new InterfaceBean();

		ib = interfaceDAO.getInterfaceByIndex(desc, 0);

		System.out.println(" Interface Name : " + ib.getName());
		System.out.println(" Interface NamespaceURI : " + ib.getNamespaceURI());
		System.out.println(" Interface Qname : " + ib.getQname());
		System.out.println(" Interface Documentation : " + ib.getDocumentation());
		System.out.println(" Interface Ohter Elements : " + ib.getOhterElements());
		System.out.println(" Interface Other Attributes : " + ib.getOtherAttributes());
		System.out.println(" Interface Operations : " + ib.getOperations());

		assertEquals("PTTInfoSoap", ib.getName());
	}

	@Test
	public void testGetInterfaceByBinding() throws XmlException {
		System.out.println("   ***************  Interface testGetInterfaceByIndex() ***************** ");
		IInterfaceDAO interfaceDAO = new InterfaceDAO();
		InterfaceBean ib = new InterfaceBean();
        Binding binding = desc.getBindings().get(0);
		ib = interfaceDAO.getInterfaceByBinding(binding);

		System.out.println(" Interface Name : " + ib.getName());
		System.out.println(" Interface NamespaceURI : " + ib.getNamespaceURI());
		System.out.println(" Interface Qname : " + ib.getQname());
		System.out.println(" Interface Documentation : " + ib.getDocumentation());
		System.out.println(" Interface Ohter Elements : " + ib.getOhterElements());
		System.out.println(" Interface Other Attributes : " + ib.getOtherAttributes());
		System.out.println(" Interface Operations : " + ib.getOperations());

		assertEquals("PTTInfoSoap", ib.getName());
	}

	@Test
	public void testGetInterfaceByService() throws XmlException {
		System.out.println("   ***************  Interface testGetInterfaceByService() ***************** ");
		IInterfaceDAO interfaceDAO = new InterfaceDAO();
		InterfaceBean ib = new InterfaceBean();
        Service service = desc.getServices().get(0);
		ib = interfaceDAO.getInterfaceByService(service);

		System.out.println(" Interface Name : " + ib.getName());
		System.out.println(" Interface NamespaceURI : " + ib.getNamespaceURI());
		System.out.println(" Interface Qname : " + ib.getQname());
		System.out.println(" Interface Documentation : " + ib.getDocumentation());
		System.out.println(" Interface Ohter Elements : " + ib.getOhterElements());
		System.out.println(" Interface Other Attributes : " + ib.getOtherAttributes());
		System.out.println(" Interface Operations : " + ib.getOperations());

		assertEquals("PTTInfoSoap", ib.getName());
	}

}
