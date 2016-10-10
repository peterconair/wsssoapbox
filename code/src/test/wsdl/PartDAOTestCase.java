package wsdl;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.api.Description;
import org.ow2.easywsdl.wsdl.api.Input;
import org.ow2.easywsdl.wsdl.api.Part;
import org.wsssoapbox.datasource.wsdl.WSDLDataSource;

/**
 * @author  Peter
 */
public class PartDAOTestCase {

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
		PartDAOTestCase.desc = WSDLDataSource.getDesc(WSDLList.url);
	}

	@Test
	public void testGetPartsDefault() throws XmlException {
		List<Part> partList = desc.getBindings().get(0).getBindingOperations().get(0).getOperation().getInput()
				.getParts();
		for (Part p : partList) {
			System.out.println("**************Part testGetPartsDefault() **************");
			System.out.println(" Part QName  : " + p.getPartQName());
			System.out.println(" Element  : " + p.getElement());
			System.out.println(" Documentation  : " + p.getDocumentation());
			System.out.println(" Other Attributes : " + p.getOtherAttributes());
			System.out.println(" Other Elements : " + p.getOtherElements());
			System.out.println(" Type  : " + p.getType());

		}
	}

//	@Test
	public void testGetPartsByInput() {
		fail("Not yet implemented");
	}

	//@Test
	public void testGetPartsByOuput() {
		fail("Not yet implemented");
	}

	//@Test
	public void testGetPartsByFault() {
		fail("Not yet implemented");
	}

}
