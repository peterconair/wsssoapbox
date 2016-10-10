package wsdl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.ow2.easywsdl.schema.api.Element;
import org.ow2.easywsdl.schema.api.Schema;
import org.ow2.easywsdl.schema.api.Type;
import org.ow2.easywsdl.wsdl.api.Types;
import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.api.Description;
import org.ow2.easywsdl.wsdl.api.WSDLException;
import org.wsssoapbox.bean.model.wsdl.TypeBean;
import org.wsssoapbox.dao.wsdl.ITypeDAO;
import org.wsssoapbox.dao.wsdl.TypeDAO;
import org.wsssoapbox.datasource.wsdl.WSDLDataSource;

/**
 * @author  Peter
 */
public class TypeDAOTestCase {
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
		TypeDAOTestCase.desc = WSDLDataSource.getDesc(WSDLList.url);
	}

	@Test
	public void testGetTypesDefault() throws WSDLException, XmlException {

		System.out.println("  ****************  Types testGetTypesDefault() ***************** ");
		Types types = desc.getTypes();

		System.out.println(" Types getDocumentation:" + types.getDocumentation());
		System.out.println(" Types Imported Schemas:" + types.getImportedSchemas());
		System.out.println(" Types Other Attributes:" + types.getOtherAttributes());
		System.out.println(" Types Other Elements:" + types.getOtherElements());
		System.out.println(" Types Schemas:" + types.getSchemas());

		//       Type type = (Type) desc.getTypes();
		//		System.out.println(" Type QName" + type.getQName());
		//		System.out.println(" Type getDocumentation:" + type.getDocumentation());
		//		System.out.println(" Type Other Attributes:" + type.getOtherAttributes());

	}

	//@Test
	public void testGetTypes() throws WSDLException, XmlException {

		System.out.println("  ****************  Type testGetTypes() ***************** ");
		ITypeDAO typeDAO = new TypeDAO();
		TypeBean typeBean = new TypeBean();
		typeBean = typeDAO.getTypes(desc);
		System.out.println(" Types Qname :" + typeBean.getQname());
		System.out.println(" Types Documentation :" + typeBean.getDocumentation());
		System.out.println(" Types Import Schema :" + typeBean.getImportSchemas());
		System.out.println(" Types Schema :" + typeBean.getSchemas());
	}

	//@Test
	public void testGetTypeByElement() throws WSDLException, XmlException {
		System.out.println("  ****************  Type testGetTypeByElement() ***************** ");
		Element elememt = desc.getTypes().getSchemas().get(0).getElements().get(0);
		ITypeDAO typeDAO = new TypeDAO();
		TypeBean typeBean = new TypeBean();
		typeBean = typeDAO.getTypeByElement(elememt);
		System.out.println(" Types Qname :" + typeBean.getQname());
		System.out.println(" Types Documentation :" + typeBean.getDocumentation());
		System.out.println(" Types Import Schema :" + typeBean.getImportSchemas());
		System.out.println(" Types Schema :" + typeBean.getSchemas());
	}

	//@Test
	public void testGetTypeByPart() throws WSDLException, XmlException {
		System.out.println("  ****************  Type testGetTypeByPart() ***************** ");
		ITypeDAO typeDAO = new TypeDAO();
		TypeBean typeBean = new TypeBean();
		typeBean = typeDAO.getTypes(desc);
		System.out.println(" Types Qname :" + typeBean.getQname());
		System.out.println(" Types Documentation :" + typeBean.getDocumentation());
		System.out.println(" Types Import Schema :" + typeBean.getImportSchemas());
		System.out.println(" Types Schema :" + typeBean.getSchemas());
	}

	//@Test
	public void testGetTypeBySchema() throws XmlException {

		System.out.println("  ****************  Type testGetTypeBySchema() ***************** ");
		Schema schema = desc.getTypes().getSchemas().get(0);
		ITypeDAO typeDAO = new TypeDAO();

		List<TypeBean> typeBeanList = new ArrayList<TypeBean>();
		typeBeanList = typeDAO.getTypeBySchema(schema);
		for (TypeBean typeBean : typeBeanList) {
			System.out.println(" Types Qname :" + typeBean.getQname());
			System.out.println(" Types Documentation :" + typeBean.getDocumentation());
			System.out.println(" Types Import Schema :" + typeBean.getImportSchemas());
			System.out.println(" Types Schema :" + typeBean.getSchemas());
		}
	}
}
