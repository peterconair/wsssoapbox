package wsdl;

import static org.junit.Assert.*;

import javax.xml.namespace.QName;

import org.junit.BeforeClass;
import org.junit.Test;
import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.api.Description;
import org.ow2.easywsdl.wsdl.api.Operation;
import org.ow2.easywsdl.wsdl.api.Output;
import org.wsssoapbox.bean.model.wsdl.OutputBean;
import org.wsssoapbox.dao.wsdl.IOutputDAO;
import org.wsssoapbox.dao.wsdl.OutputDAO;
import org.wsssoapbox.datasource.wsdl.WSDLDataSource;

/**
 * @author  Peter
 */
public class OutputDAOTestCase {
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
		OutputDAOTestCase.desc = WSDLDataSource.getDesc(WSDLList.url);
	}

//	@Test
	public void testGetOuputputDefault() throws XmlException {

		Output ob = desc.getInterfaces().get(0).getOperations().get(0).getOutput();

		System.out.println(" ********************* Output testGetOuputputDefault()  *************************** ");
		System.out.println(" Output Name: " + ob.getName());
		System.out.println(" Output Documentation: " + ob.getDocumentation());
		System.out.println(" Output Other Attrobutes: " + ob.getOtherAttributes());
		System.out.println(" Output Other Elements: " + ob.getOtherElements());
		System.out.println(" *******************Element *************************** ");
		System.out.println(" Output Element: " + ob.getElement());
		System.out.println("                    Element :" + ob.getElement());
		System.out.println(" *******************Message Name *************************** ");
		System.out.println(" Output MessageName: " + ob.getMessageName());
		System.out.println("                    Name :" + ob.getMessageName().getLocalPart());
		System.out.println("                    Namespace URI:" + ob.getMessageName().getNamespaceURI());
		System.out.println("                    Prefix :" + ob.getMessageName().getPrefix());
		System.out.println(" *******************Part  *************************** ");
		System.out.println(" Output Parts: " + ob.getParts());
	}

	@Test
	public void testGetOutputByInterface() throws XmlException {
		QName interfaceQName = desc.getInterfaces().get(0).getQName();
		QName operationQName = desc.getInterfaces().get(0).getOperations().get(0).getQName();
		IOutputDAO outputDAO = new OutputDAO();
		OutputBean ob = new OutputBean();
		ob = outputDAO.getOutputByInterface(desc, interfaceQName, operationQName);

		System.out.println(" ********************* Output testGetOutputByInterface()  *************************** ");
		System.out.println(" Output Name: " + ob.getName());
		System.out.println(" Output Documentation: " + ob.getDocumentation());
		System.out.println(" Output Other Attrobutes: " + ob.getOtherAttributes());
		System.out.println(" Output Other Elements: " + ob.getOtherElements());
		System.out.println(" *******************Element *************************** ");
		System.out.println(" Output Element: " + ob.getElement());
		System.out.println("                    Element :" + ob.getElement());
		System.out.println(" *******************Message Name *************************** ");
		System.out.println(" Output MessageName: " + ob.getMessageName());
		System.out.println("                    Name :" + ob.getMessageName().getLocalPart());
		System.out.println("                    Namespace URI:" + ob.getMessageName().getNamespaceURI());
		System.out.println("                    Prefix :" + ob.getMessageName().getPrefix());
		System.out.println(" *******************Part  *************************** ");
		System.out.println(" Output Parts: " + ob.getParts());
	}

	@Test
	public void testGetOutputByInterfaceIndex() throws XmlException {

		QName operationQName = desc.getInterfaces().get(0).getOperations().get(0).getQName();
		IOutputDAO outputDAO = new OutputDAO();
		OutputBean ob = new OutputBean();
		ob = outputDAO.getOutputByInterfaceIndex(desc, 0, operationQName);

		System.out.println(" ********************* Output testGetOutputByInterface()  *************************** ");
		System.out.println(" Output Name: " + ob.getName());
		System.out.println(" Output Documentation: " + ob.getDocumentation());
		System.out.println(" Output Other Attrobutes: " + ob.getOtherAttributes());
		System.out.println(" Output Other Elements: " + ob.getOtherElements());
		System.out.println(" *******************Element *************************** ");
		System.out.println(" Output Element: " + ob.getElement());
		System.out.println("                    Element :" + ob.getElement());
		System.out.println(" *******************Message Name *************************** ");
		System.out.println(" Output MessageName: " + ob.getMessageName());
		System.out.println("                    Name :" + ob.getMessageName().getLocalPart());
		System.out.println("                    Namespace URI:" + ob.getMessageName().getNamespaceURI());
		System.out.println("                    Prefix :" + ob.getMessageName().getPrefix());
		System.out.println(" *******************Part  *************************** ");
		System.out.println(" Output Parts: " + ob.getParts());
	}

	@Test
	public void testGetOutputByBinding() throws XmlException {
		QName bindingQName = desc.getBindings().get(0).getQName();
		String operationName = desc.getInterfaces().get(0).getOperations().get(0).getQName().getLocalPart();
		IOutputDAO outputDAO = new OutputDAO();
		OutputBean ob = new OutputBean();
		ob = outputDAO.getOutputByBinding(desc, bindingQName, operationName);

		System.out.println(" ********************* Output testGetOutputByOperation()  *************************** ");
		System.out.println(" Output Name: " + ob.getName());
		System.out.println(" Output Documentation: " + ob.getDocumentation());
		System.out.println(" Output Other Attrobutes: " + ob.getOtherAttributes());
		System.out.println(" Output Other Elements: " + ob.getOtherElements());
		System.out.println(" *******************Element *************************** ");
		System.out.println(" Output Element: " + ob.getElement());
		System.out.println("                    Element :" + ob.getElement());
		System.out.println(" *******************Message Name *************************** ");
		System.out.println(" Output MessageName: " + ob.getMessageName());
		System.out.println("                    Name :" + ob.getMessageName().getLocalPart());
		System.out.println("                    Namespace URI:" + ob.getMessageName().getNamespaceURI());
		System.out.println("                    Prefix :" + ob.getMessageName().getPrefix());
		System.out.println(" *******************Part  *************************** ");
		System.out.println(" Output Parts: " + ob.getParts());
	}

	@Test
	public void testGetOutputByBindingOperation() throws XmlException {
		
		Operation operation = desc.getInterfaces().get(0).getOperations().get(0);
		IOutputDAO outputDAO = new OutputDAO();
		OutputBean ob = new OutputBean();
		ob = outputDAO.getOutputByBindingOperation(operation);

		System.out.println(" ********************* Output testGetOutputByOperation()  *************************** ");
		System.out.println(" Output Name: " + ob.getName());
		System.out.println(" Output Documentation: " + ob.getDocumentation());
		System.out.println(" Output Other Attrobutes: " + ob.getOtherAttributes());
		System.out.println(" Output Other Elements: " + ob.getOtherElements());
		System.out.println(" *******************Element *************************** ");
		System.out.println(" Output Element: " + ob.getElement());
		System.out.println("                    Element :" + ob.getElement());
		System.out.println(" *******************Message Name *************************** ");
		System.out.println(" Output MessageName: " + ob.getMessageName());
		System.out.println("                    Name :" + ob.getMessageName().getLocalPart());
		System.out.println("                    Namespace URI:" + ob.getMessageName().getNamespaceURI());
		System.out.println("                    Prefix :" + ob.getMessageName().getPrefix());
		System.out.println(" *******************Part  *************************** ");
		System.out.println(" Output Parts: " + ob.getParts());
	}

	@Test
	public void testGetOutputByOperation() throws XmlException {

		Operation operation = desc.getInterfaces().get(0).getOperations().get(0);
		IOutputDAO outputDAO = new OutputDAO();
		OutputBean ob = new OutputBean();
		ob = outputDAO.getOutputByOperation(operation);

		System.out.println(" ********************* Output testGetOutputByOperation()  *************************** ");
		System.out.println(" Output Name: " + ob.getName());
		System.out.println(" Output Documentation: " + ob.getDocumentation());
		System.out.println(" Output Other Attrobutes: " + ob.getOtherAttributes());
		System.out.println(" Output Other Elements: " + ob.getOtherElements());
		System.out.println(" *******************Element *************************** ");
		System.out.println(" Output Element: " + ob.getElement());
		System.out.println("                    Element :" + ob.getElement());
		System.out.println(" *******************Message Name *************************** ");
		System.out.println(" Output MessageName: " + ob.getMessageName());
		System.out.println("                    Name :" + ob.getMessageName().getLocalPart());
		System.out.println("                    Namespace URI:" + ob.getMessageName().getNamespaceURI());
		System.out.println("                    Prefix :" + ob.getMessageName().getPrefix());
		System.out.println(" *******************Part  *************************** ");
		System.out.println(" Output Parts: " + ob.getParts());
	}

}
