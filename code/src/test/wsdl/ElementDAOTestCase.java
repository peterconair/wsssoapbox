package wsdl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.ow2.easywsdl.schema.api.Element;
import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.api.Description;
import org.ow2.easywsdl.wsdl.api.Fault;
import org.ow2.easywsdl.wsdl.api.Input;
import org.ow2.easywsdl.wsdl.api.Output;
import org.wsssoapbox.bean.model.wsdl.ElementBean;
import org.wsssoapbox.dao.wsdl.ElementDAO;
import org.wsssoapbox.dao.wsdl.IElementDAO;
import org.wsssoapbox.datasource.wsdl.WSDLDataSource;

/**
 * @author  Peter
 */
public class ElementDAOTestCase {
	private static Description desc;
	private static WSDLDataSource wsdlDocumentDataSource;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		wsdlDocumentDataSource = new WSDLDataSource(WSDLList.url);
		ElementDAOTestCase.desc = WSDLDataSource.getDesc(WSDLList.url);
	}

	@Test
	public void testGetElementDefault() throws XmlException {
		List<Element> elementList = desc.getTypes().getSchemas().get(0).getElements();
		System.out.println("***************** Element  testGetElementDefault()**********************");
		for (Element element : elementList) {
			System.out.println("\n**************************************");
			System.out.println("   QName   : " + element.getQName());
			System.out.println("   Form   : " + element.getForm());
			System.out.println("   MaxOccurs   : " + element.getMaxOccurs());
			System.out.println("   MinOccurs   : " + element.getMinOccurs());
			System.out.println("   Ref   : " + element.getRef());
			System.out.println("   OtherAttributes   : " + element.getOtherAttributes());
			System.out.println("   Documentation   : " + element.getDocumentation());
			System.out.println("   Type   : " + element.getType());

		}
	}

	@Test
	public void testGetElementByInput() throws XmlException {
		Input input = desc.getInterfaces().get(0).getOperations().get(0).getInput();
		IElementDAO elementDAO = new ElementDAO();
		ElementBean element = elementDAO.getElementByInput(input);

		System.out.println("\n***************** Element  testGetElementByInput()**********************");
		System.out.println("   QName   : " + element.getQname());
		System.out.println("   Form   : " + element.getForm());
		System.out.println("   MaxOccurs   : " + element.getMaxOccurs());
		System.out.println("   MinOccurs   : " + element.getMinOccurs());
		System.out.println("   Ref   : " + element.getRef());
		System.out.println("   OtherAttributes   : " + element.getOtherAttributes());
		System.out.println("   Documentation   : " + element.getDocumentation());
		System.out.println("   Type   : " + element.getType());

	}

	@Test
	public void testGetElementByPart() throws XmlException {
		Input input = desc.getInterfaces().get(0).getOperations().get(0).getInput();
		IElementDAO elementDAO = new ElementDAO();
		ElementBean element = elementDAO.getElementByInput(input);

		System.out.println("\n***************** Element  testGetElementByPart()**********************");
		System.out.println("   QName   : " + element.getQname());
		System.out.println("   Form   : " + element.getForm());
		System.out.println("   MaxOccurs   : " + element.getMaxOccurs());
		System.out.println("   MinOccurs   : " + element.getMinOccurs());
		System.out.println("   Ref   : " + element.getRef());
		System.out.println("   OtherAttributes   : " + element.getOtherAttributes());
		System.out.println("   Documentation   : " + element.getDocumentation());
		System.out.println("   Type   : " + element.getType());

	}

	@Test
	public void testGetElementByOutput() throws XmlException {
		Output output = desc.getInterfaces().get(0).getOperations().get(0).getOutput();
		IElementDAO elementDAO = new ElementDAO();
		ElementBean element = elementDAO.getElementByOutput(output);

		System.out.println("\n***************** Element  testGetElementByOutput()**********************");
		System.out.println("   QName   : " + element.getQname());
		System.out.println("   Form   : " + element.getForm());
		System.out.println("   MaxOccurs   : " + element.getMaxOccurs());
		System.out.println("   MinOccurs   : " + element.getMinOccurs());
		System.out.println("   Ref   : " + element.getRef());
		System.out.println("   OtherAttributes   : " + element.getOtherAttributes());
		System.out.println("   Documentation   : " + element.getDocumentation());
		System.out.println("   Type   : " + element.getType());

	}

	@Test
	public void testGetElementByFault() throws XmlException {
		Fault fault = desc.getInterfaces().get(0).getOperations().get(0).getFaults().get(0);
		IElementDAO elementDAO = new ElementDAO();
		ElementBean element = elementDAO.getElementByFault(fault);
			System.out.println("\n***************** Element  testGetElementByFault()**********************");
			System.out.println("   QName   : " + element.getQname());
			System.out.println("   Form   : " + element.getForm());
			System.out.println("   MaxOccurs   : " + element.getMaxOccurs());
			System.out.println("   MinOccurs   : " + element.getMinOccurs());
			System.out.println("   Ref   : " + element.getRef());
			System.out.println("   OtherAttributes   : " + element.getOtherAttributes());
			System.out.println("   Documentation   : " + element.getDocumentation());
			System.out.println("   Type   : " + element.getType());
	}
}
