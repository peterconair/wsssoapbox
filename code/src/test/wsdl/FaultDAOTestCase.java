package wsdl;

import static org.junit.Assert.*;

import java.util.List;

import javax.xml.namespace.QName;

import org.junit.BeforeClass;
import org.junit.Test;
import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.api.Description;
import org.ow2.easywsdl.wsdl.api.Fault;
import org.ow2.easywsdl.wsdl.api.Operation;
import org.wsssoapbox.bean.model.wsdl.FaultBean;
import org.wsssoapbox.dao.wsdl.FaultDAO;
import org.wsssoapbox.dao.wsdl.IFaultDAO;
import org.wsssoapbox.datasource.wsdl.WSDLDataSource;

/**
 * @author  Peter
 */
public class FaultDAOTestCase {
	private static Description desc;
	private static String url;
	/**
	 * @uml.property  name="wsdlDocumentDataSource"
	 * @uml.associationEnd  
	 */
	private static WSDLDataSource wsdlDocumentDataSource;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		url = "http://www.pttplc.com/pttinfo.asmx?WSDL";
		wsdlDocumentDataSource = new WSDLDataSource(WSDLList.url);
		FaultDAOTestCase.desc = WSDLDataSource.getDesc(WSDLList.url);
	}
	
	@Test
	public void testGetFaultsDefault() throws XmlException {

		List <Fault> faultList = desc.getInterfaces().get(0).getOperations().get(0).getFaults();

		for(Fault f :faultList){
		System.out.println(" ********************* Fault testGetFaultsDefault()  *************************** ");
		System.out.println(" Fault Name: " + f.getName());
		System.out.println(" Fault Documentation: " + f.getDocumentation());
		System.out.println(" Fault Other Attrfutes: " + f.getOtherAttributes());
		System.out.println(" Fault Other Elements: " + f.getOtherElements());
		System.out.println(" *******************Element *************************** ");
		System.out.println(" Fault Element: " + f.getElement());
		System.out.println("                    Element :" + f.getElement());
		System.out.println(" *******************Message Name *************************** ");
		System.out.println(" Fault MessageName: " + f.getMessageName());
		System.out.println("                    Name :" + f.getMessageName().getLocalPart());
		System.out.println("                    Namespace URI:" + f.getMessageName().getNamespaceURI());
		System.out.println("                    Prefix :" + f.getMessageName().getPrefix());
		System.out.println(" *******************Part  *************************** ");
		System.out.println(" Fault Parts: " + f.getParts());
		}
	}

	@Test
	public void testGetFaultsByInterfaceOperationFaultName() throws XmlException {

		QName interfaceQName = desc.getInterfaces().get(0).getQName();
		QName operationQName = desc.getInterfaces().get(0).getOperations().get(0).getQName();
		String faultName = "";
		IFaultDAO faultDAO = new FaultDAO();
		FaultBean f =  faultDAO.getFaultByInterfaceOperationFaultName(desc, interfaceQName, operationQName, faultName);
 	
		System.out.println(" ********************* Fault testGetFaultsByInterfaceOperationFaultName()  *************************** ");
		System.out.println(" Fault Name: " + f.getName());
		System.out.println(" Fault Documentation: " + f.getDocumentation());
		System.out.println(" Fault Other Attrfutes: " + f.getOtherAttributes());
		System.out.println(" Fault Other Elements: " + f.getOtherElements());
		System.out.println(" *******************Element *************************** ");
		System.out.println(" Fault Element: " + f.getElement());
		System.out.println("                    Element :" + f.getElement());
		System.out.println(" *******************Message Name *************************** ");
		System.out.println(" Fault MessageName: " + f.getMessageName());
		System.out.println("                    Name :" + f.getMessageName().getLocalPart());
		System.out.println("                    Namespace URI:" + f.getMessageName().getNamespaceURI());
		System.out.println("                    Prefix :" + f.getMessageName().getPrefix());
		System.out.println(" *******************Part  *************************** ");
		System.out.println(" Fault Parts: " + f.getParts());

	}

	@Test
	public void testGetFaultsByInterfaceIndexOperationFaultName() throws XmlException {

		QName operationQName = desc.getInterfaces().get(0).getOperations().get(0).getQName();
		IFaultDAO faultDAO = new FaultDAO();
		List <FaultBean> faultBeanList =  faultDAO.getFaultsByInterfaceIndexOperationFaultName(desc, 0, operationQName);
 

		for(FaultBean f :faultBeanList){
		System.out.println(" ********************* Fault testGetFaultsByInterfaceIndexOperationFaultName()  *************************** ");
		System.out.println(" Fault Name: " + f.getName());
		System.out.println(" Fault Documentation: " + f.getDocumentation());
		System.out.println(" Fault Other Attrfutes: " + f.getOtherAttributes());
		System.out.println(" Fault Other Elements: " + f.getOtherElements());
		System.out.println(" *******************Element *************************** ");
		System.out.println(" Fault Element: " + f.getElement());
		System.out.println("                    Element :" + f.getElement());
		System.out.println(" *******************Message Name *************************** ");
		System.out.println(" Fault MessageName: " + f.getMessageName());
		System.out.println("                    Name :" + f.getMessageName().getLocalPart());
		System.out.println("                    Namespace URI:" + f.getMessageName().getNamespaceURI());
		System.out.println("                    Prefix :" + f.getMessageName().getPrefix());
		System.out.println(" *******************Part  *************************** ");
		System.out.println(" Fault Parts: " + f.getParts());
		}
	}


	@Test
	public void testGetFaultByInterfaceOperationElementQName() throws XmlException {
		QName interfaceQName = desc.getInterfaces().get(0).getQName();
		QName operationQName = desc.getInterfaces().get(0).getOperations().get(0).getQName();
		QName elementQName =  desc.getInterfaces().get(0).getOperations().get(0).getFaults().get(0).getElement().getQName();
		IFaultDAO faultDAO = new FaultDAO();
		FaultBean f =  faultDAO.getFaultByInterfaceOperationElementQName(desc, interfaceQName, operationQName, elementQName);

		System.out.println(" ********************* Fault testGetFaultByInterfaceOperationElementQName()  *************************** ");
		System.out.println(" Fault Name: " + f.getName());
		System.out.println(" Fault Documentation: " + f.getDocumentation());
		System.out.println(" Fault Other Attrfutes: " + f.getOtherAttributes());
		System.out.println(" Fault Other Elements: " + f.getOtherElements());
		System.out.println(" *******************Element *************************** ");
		System.out.println(" Fault Element: " + f.getElement());
		System.out.println("                    Element :" + f.getElement());
		System.out.println(" *******************Message Name *************************** ");
		System.out.println(" Fault MessageName: " + f.getMessageName());
		System.out.println("                    Name :" + f.getMessageName().getLocalPart());
		System.out.println("                    Namespace URI:" + f.getMessageName().getNamespaceURI());
		System.out.println("                    Prefix :" + f.getMessageName().getPrefix());
		System.out.println(" *******************Part  *************************** ");
		System.out.println(" Fault Parts: " + f.getParts());
	}

	@Test
	public void testGetFaultByInterfaceOperationFaultName() throws XmlException {
		QName interfaceQName = desc.getInterfaces().get(0).getQName();
		QName operationQName = desc.getInterfaces().get(0).getOperations().get(0).getQName();
		String faultName = desc.getInterfaces().get(0).getOperations().get(0).getFaults().get(0).getName();
		IFaultDAO faultDAO = new FaultDAO();
		FaultBean f =  faultDAO.getFaultByInterfaceOperationFaultName(desc, interfaceQName, operationQName, faultName);

		System.out.println(" ********************* Fault testGetOuputputDefault()  *************************** ");
		System.out.println(" Fault Name: " + f.getName());
		System.out.println(" Fault Documentation: " + f.getDocumentation());
		System.out.println(" Fault Other Attrfutes: " + f.getOtherAttributes());
		System.out.println(" Fault Other Elements: " + f.getOtherElements());
		System.out.println(" *******************Element *************************** ");
		System.out.println(" Fault Element: " + f.getElement());
		System.out.println("                    Element :" + f.getElement());
		System.out.println(" *******************Message Name *************************** ");
		System.out.println(" Fault MessageName: " + f.getMessageName());
		System.out.println("                    Name :" + f.getMessageName().getLocalPart());
		System.out.println("                    Namespace URI:" + f.getMessageName().getNamespaceURI());
		System.out.println("                    Prefix :" + f.getMessageName().getPrefix());
		System.out.println(" *******************Part  *************************** ");
		System.out.println(" Fault Parts: " + f.getParts());

	}

	@Test
	public void testGetFaultsByBinding() throws XmlException {
		QName bindingQName = desc.getBindings().get(0).getQName();
		String operationName = desc.getBindings().get(0).getBindingOperations().get(0).getOperation().getQName().getLocalPart();
		IFaultDAO faultDAO = new FaultDAO();
		List <FaultBean> faultBeanList =  faultDAO.getFaultsByBinding(desc, bindingQName, operationName);

		for(FaultBean f :faultBeanList){
		System.out.println(" ********************* Fault testGetOuputputDefault()  *************************** ");
		System.out.println(" Fault Name: " + f.getName());
		System.out.println(" Fault Documentation: " + f.getDocumentation());
		System.out.println(" Fault Other Attrfutes: " + f.getOtherAttributes());
		System.out.println(" Fault Other Elements: " + f.getOtherElements());
		System.out.println(" *******************Element *************************** ");
		System.out.println(" Fault Element: " + f.getElement());
		System.out.println("                    Element :" + f.getElement());
		System.out.println(" *******************Message Name *************************** ");
		System.out.println(" Fault MessageName: " + f.getMessageName());
		System.out.println("                    Name :" + f.getMessageName().getLocalPart());
		System.out.println("                    Namespace URI:" + f.getMessageName().getNamespaceURI());
		System.out.println("                    Prefix :" + f.getMessageName().getPrefix());
		System.out.println(" *******************Part  *************************** ");
		System.out.println(" Fault Parts: " + f.getParts());
		}
	}

	@Test
	public void testGetFaultsByBindingOperation() throws XmlException {

		Operation operation = desc.getBindings().get(0).getBindingOperations().get(0).getOperation();
		IFaultDAO faultDAO = new FaultDAO();
		List <FaultBean> faultBeanList =  faultDAO.getFaultsByBindingOperation(operation);

		for(FaultBean f :faultBeanList){
		System.out.println(" ********************* Fault testGetOuputputDefault()  *************************** ");
		System.out.println(" Fault Name: " + f.getName());
		System.out.println(" Fault Documentation: " + f.getDocumentation());
		System.out.println(" Fault Other Attrfutes: " + f.getOtherAttributes());
		System.out.println(" Fault Other Elements: " + f.getOtherElements());
		System.out.println(" *******************Element *************************** ");
		System.out.println(" Fault Element: " + f.getElement());
		System.out.println("                    Element :" + f.getElement());
		System.out.println(" *******************Message Name *************************** ");
		System.out.println(" Fault MessageName: " + f.getMessageName());
		System.out.println("                    Name :" + f.getMessageName().getLocalPart());
		System.out.println("                    Namespace URI:" + f.getMessageName().getNamespaceURI());
		System.out.println("                    Prefix :" + f.getMessageName().getPrefix());
		System.out.println(" *******************Part  *************************** ");
		System.out.println(" Fault Parts: " + f.getParts());
		}
	}

	@Test
	public void testGetFaultsByOperation() throws XmlException {
		
		QName bindigQName = desc.getBindings().get(0).getQName();
		String operationName = desc.getBindings().get(0).getBindingOperations().get(0).getOperation().getQName().getLocalPart();
		IFaultDAO faultDAO = new FaultDAO();
		List <FaultBean> faultBeanList =  faultDAO.getFaultsByBinding(desc, bindigQName, operationName);

		for(FaultBean f :faultBeanList){
		System.out.println(" ********************* Fault testGetOuputputDefault()  *************************** ");
		System.out.println(" Fault Name: " + f.getName());
		System.out.println(" Fault Documentation: " + f.getDocumentation());
		System.out.println(" Fault Other Attrfutes: " + f.getOtherAttributes());
		System.out.println(" Fault Other Elements: " + f.getOtherElements());
		System.out.println(" *******************Element *************************** ");
		System.out.println(" Fault Element: " + f.getElement());
		System.out.println("                    Element :" + f.getElement());
		System.out.println(" *******************Message Name *************************** ");
		System.out.println(" Fault MessageName: " + f.getMessageName());
		System.out.println("                    Name :" + f.getMessageName().getLocalPart());
		System.out.println("                    Namespace URI:" + f.getMessageName().getNamespaceURI());
		System.out.println("                    Prefix :" + f.getMessageName().getPrefix());
		System.out.println(" *******************Part  *************************** ");
		System.out.println(" Fault Parts: " + f.getParts());
		}
	}

}
