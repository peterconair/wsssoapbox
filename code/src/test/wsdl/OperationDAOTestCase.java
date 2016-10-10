package wsdl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.api.Description;
import org.ow2.easywsdl.wsdl.api.InterfaceType;
import org.ow2.easywsdl.wsdl.api.Operation;
import org.ow2.easywsdl.wsdl.api.Part;
import org.ow2.easywsdl.wsdl.api.WSDLException;
import org.wsssoapbox.bean.model.wsdl.OperationBean;
import org.wsssoapbox.dao.wsdl.IOperationDAO;
import org.wsssoapbox.dao.wsdl.OperationDAO;
import org.wsssoapbox.datasource.wsdl.WSDLDataSource;

/**
 * @author  Peter
 */
public class OperationDAOTestCase {
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
		OperationDAOTestCase.desc =WSDLDataSource.getDesc(WSDLList.url);
	}

	@Test
	public void testGetOperationsInterfaceDefault() throws WSDLException, XmlException {
		QName interfaceQName = desc.getInterfaces().get(0).getQName();
		IOperationDAO operationDAO = new OperationDAO();
		List<Operation> operationList = new ArrayList<Operation>();

		System.out.println("\n *****************  Operation testGetOperationsInterfaceDefault ***************** ");

		operationList = desc.getInterfaces().get(0).getOperations();
		System.out.println("Interface QName for Operation : " + interfaceQName.toString());
		for (Operation bol : operationList) {

			System.out.println(" ************************************************* ");
			System.out.println(" Interface Operation Name: " + bol.getQName().getLocalPart());
			System.out.println(" ************************************************* ");
			System.out.println(" Interface Operation  QName : " + bol.getQName());
			System.out.println(" Interface Operation  Pattern : " + bol.getPattern());
			System.out.println(" Interface Operation  Signature : " + bol.getSignature());
			System.out.println(" Interface Operation  Parameter Ordering : " + bol.getParameterOrdering());
			System.out.println(" Interface Operation  Documentation : " + bol.getDocumentation());
			System.out.println(" Interface Operation  Other Attributes : " + bol.getOtherAttributes());
			System.out.println(" Interface Operation  Other Elements : " + bol.getOtherElements());
			
			System.out.println("\n Interface Operation  Input : " + bol.getInput());
			System.out.println(" Interface Operation  Output: " + bol.getOutput());
			System.out.println(" Interface Operation  Faults : " + bol.getFaults());

			System.out.println("\n ********************* Input *************************** ");
			System.out.println(" Input Name: " + bol.getInput().getName());
			System.out.println(" Input Documentation: " + bol.getInput().getDocumentation());
			System.out.println(" Input Other Attributes: " + bol.getInput().getOtherAttributes());
			System.out.println(" Input Other Elements: " + bol.getInput().getOtherElements());
			System.out.println(" *******************Element *************************** ");
			System.out.println(" 			Input Element: " + bol.getInput().getElement());
			System.out.println("                    Element :" + bol.getInput().getElement());
			System.out.println(" *******************Message Name *************************** ");
			System.out.println(" 					Input MessageName: " + bol.getInput().getMessageName());
			System.out.println("                    Name :" + bol.getInput().getMessageName().getLocalPart());
			System.out
					.println("                    Namespace URI:" + bol.getInput().getMessageName().getNamespaceURI());
			System.out.println("                    Prefix :" + bol.getInput().getMessageName().getPrefix());
			System.out.println(" *******************Part  *************************** ");
			System.out.println(" 					Input Parts: " + bol.getInput().getParts());

			System.out.println(" ********************* Output *************************** ");
			System.out.println(" Output Name: " + bol.getOutput().getName());
			System.out.println(" Output Documentation: " + bol.getOutput().getDocumentation());
			System.out.println(" Output Other Attributes: " + bol.getOutput().getOtherAttributes());
			System.out.println(" Output Other Elements: " + bol.getOutput().getOtherElements());
			System.out.println(" *******************Element *************************** ");
			System.out.println(" Output Element: " + bol.getOutput().getElement());
			System.out.println("                    Element :" + bol.getOutput().getElement());
			System.out.println(" *******************Message Name *************************** ");
			System.out.println(" 					Output MessageName: " + bol.getOutput().getMessageName());
			System.out.println("                    Name :" + bol.getOutput().getMessageName().getLocalPart());
			System.out.println("                    Namespace URI:"
					+ bol.getOutput().getMessageName().getNamespaceURI());
			System.out.println("                    Prefix :" + bol.getOutput().getMessageName().getPrefix());
			System.out.println(" *******************Part  *************************** ");
			System.out.println(" 					Output Parts: " + bol.getOutput().getParts());

		}

	}

	//	@Test
	public void testGetOperationsInterfaceByName() throws WSDLException, XmlException {
		QName interfaceQName = desc.getInterfaces().get(0).getQName();
		IOperationDAO operationDAO = new OperationDAO();
		List<OperationBean> operationBeanList = new ArrayList<OperationBean>();

		System.out.println("\n *****************  Operation  testGetOperationsInterfaceByName ***************** ");

		operationBeanList = operationDAO.getOperationsInterfaceByName(desc, interfaceQName);
		System.out.println("Interface QName for Operation : " + interfaceQName.toString());
		for (OperationBean bol : operationBeanList) {

			System.out.println(" ************************************************* ");
			System.out.println(" Interface Operation Name: " + bol.getName());
			System.out.println(" ************************************************* ");
			System.out.println(" Interface Operation  QName : " + bol.getQname());
			System.out.println(" Interface Operation  Pattern : " + bol.getPattern());
			System.out.println(" Interface Operation  Signature : " + bol.getSignature());
			System.out.println(" Interface Operation  Parameter Ordering : " + bol.getParameterOrdering());
			System.out.println(" Interface Operation  Documentation : " + bol.getDocumentation());
			System.out.println(" Interface Operation  Other Attributes : " + bol.getOtherAttributes());
			System.out.println(" Interface Operation  Other Elements : " + bol.getOtherElements());
			System.out.println(" Interface Operation  Input : " + bol.getInput());
			System.out.println(" Interface Operation  Output: " + bol.getOutput());
			System.out.println(" Interface Operation  Faults : " + bol.getFaults());

		}

	}

	//	@Test
	public void testGetOperationInterfaceByName() throws WSDLException, XmlException {
		System.out.println("\n *****************  Operation  testGetOperationsInterfaceByName ***************** ");
		QName interfaceQName = desc.getInterfaces().get(0).getQName();
		QName OperationQName = desc.getInterfaces().get(0).getOperations().get(0).getQName();
		IOperationDAO operationDAO = new OperationDAO();
		OperationBean bol = new OperationBean();
		bol = operationDAO.getOperationInterfaceByName(desc, interfaceQName, OperationQName);

		System.out.println(" ************************************************* ");
		System.out.println(" Interface Operation Name: " + bol.getName());
		System.out.println(" ************************************************* ");
		System.out.println(" Interface Operation  QName : " + bol.getQname());
		System.out.println(" Interface Operation  Pattern : " + bol.getPattern());
		System.out.println(" Interface Operation  Signature : " + bol.getSignature());
		System.out.println(" Interface Operation  Parameter Ordering : " + bol.getParameterOrdering());
		System.out.println(" Interface Operation  Documentation : " + bol.getDocumentation());
		System.out.println(" Interface Operation  Other Attributes : " + bol.getOtherAttributes());
		System.out.println(" Interface Operation  Other Elements : " + bol.getOtherElements());
		System.out.println(" Interface Operation  Input : " + bol.getInput());
		System.out.println(" Interface Operation  Output: " + bol.getOutput());
		System.out.println(" Interface Operation  Faults : " + bol.getFaults());

	}

	//	@Test
	public void testGetOperationInterfaceByNameIndex() throws WSDLException, XmlException {
		System.out.println("\n *****************  Operation  testGetOperationInterfaceByNameIndex ***************** ");
		QName interfaceQName = desc.getInterfaces().get(0).getQName();
		QName OperationQName = desc.getInterfaces().get(0).getOperations().get(0).getQName();
		IOperationDAO operationDAO = new OperationDAO();
		OperationBean bol = new OperationBean();
		bol = operationDAO.getOperationInterfaceByNameIndex(desc, interfaceQName, 0);

		System.out.println(" ************************************************* ");
		System.out.println(" Interface Operation Name: " + bol.getName());
		System.out.println(" ************************************************* ");
		System.out.println(" Interface Operation  QName : " + bol.getQname());
		System.out.println(" Interface Operation  Pattern : " + bol.getPattern());
		System.out.println(" Interface Operation  Signature : " + bol.getSignature());
		System.out.println(" Interface Operation  Parameter Ordering : " + bol.getParameterOrdering());
		System.out.println(" Interface Operation  Documentation : " + bol.getDocumentation());
		System.out.println(" Interface Operation  Other Attributes : " + bol.getOtherAttributes());
		System.out.println(" Interface Operation  Other Elements : " + bol.getOtherElements());
		System.out.println(" Interface Operation  Input : " + bol.getInput());
		System.out.println(" Interface Operation  Output: " + bol.getOutput());
		System.out.println(" Interface Operation  Faults : " + bol.getFaults());
	}

//	@Test
	public void testGetOperationByBinding() {
		fail("Not yet implemented");
	}

//	@Test
	public void testGetOperationByBindingOperation() {
		fail("Not yet implemented");
	}

//	@Test
	public void testGetOperationsByInterface() throws XmlException {

		System.out.println("\n *****************  Operation  testGetOperationsByInterface ***************** ");
		QName interfaceQName = desc.getInterfaces().get(0).getQName();
		QName OperationQName = desc.getInterfaces().get(0).getOperations().get(0).getQName();

		IOperationDAO operationDAO = new OperationDAO();
		List<OperationBean> operationBeanList = new ArrayList<OperationBean>();
		InterfaceType interfaceType = desc.getInterfaces().get(0);
		operationBeanList = operationDAO.getOperationsByInterface(interfaceType);
		for (OperationBean bol : operationBeanList) {
			System.out.println(" ************************************************* ");
			System.out.println(" Interface Operation Name: " + bol.getName());
			System.out.println(" ************************************************* ");
			System.out.println(" Interface Operation  QName : " + bol.getQname());
			System.out.println(" Interface Operation  Pattern : " + bol.getPattern());
			System.out.println(" Interface Operation  Signature : " + bol.getSignature());
			System.out.println(" Interface Operation  Parameter Ordering : " + bol.getParameterOrdering());
			System.out.println(" Interface Operation  Documentation : " + bol.getDocumentation());
			System.out.println(" Interface Operation  Other Attributes : " + bol.getOtherAttributes());
			System.out.println(" Interface Operation  Other Elements : " + bol.getOtherElements());
			System.out.println(" Interface Operation  Input : " + bol.getInput());
			System.out.println(" Interface Operation  Output: " + bol.getOutput());
			System.out.println(" Interface Operation  Faults : " + bol.getFaults());
		}
	}

}
