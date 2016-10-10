package wsdl;


import org.junit.BeforeClass;
import org.junit.Test;
import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.api.Description;
import org.ow2.easywsdl.wsdl.api.WSDLReader;
import org.wsssoapbox.bean.model.wsdl.DescriptionBean;
import org.wsssoapbox.dao.wsdl.IWSDLDAO;
import org.wsssoapbox.dao.wsdl.WSDLDAO;
import org.wsssoapbox.datasource.wsdl.WSDLDataSource;

/**
 * @author  Peter
 */
public class WSDLDAOTestCase {
	static Description desc;
	static WSDLReader reader;
	static String url;
	/**
	 * @uml.property  name="wsdlDocumentDataSource"
	 * @uml.associationEnd  
	 */
	static WSDLDataSource wsdlDocumentDataSource;
	/**
	 * @uml.property  name="wsdlDucument"
	 * @uml.associationEnd  
	 */
	static DescriptionBean wsdlDucument;
	
	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		wsdlDocumentDataSource = new WSDLDataSource(WSDLList.url);
		WSDLDAOTestCase.desc = WSDLDataSource.getDesc(WSDLList.url);
	}


	@Test
	public void testGetWSDLDocument() throws XmlException {
		System.out.println("  ****************  WSDL Document ***************** ");
		IWSDLDAO wsdlDAO = new WSDLDAO();
		wsdlDucument = wsdlDAO.getWSDLDocument(desc);
		System.out.println(" WSDL Ducument Name :" + wsdlDucument.getName());
		System.out.println(" WSDL Ducument Prefix : " + wsdlDucument.getPrefix());
		System.out.println(" WSDL Ducument Local Part : " + wsdlDucument.getLocalPart());
			
		System.out.println(" WSDL Ducument Qname : " + wsdlDucument.getQName());
		System.out.println(" WSDL Ducument Target Namespace() : " + wsdlDucument.getTargetNamespace());
		System.out.println(" WSDL Ducument Document BaseURI() : " + wsdlDucument.getDocumentBaseURI());
		System.out.println(" WSDL Ducument Documentation : " + wsdlDucument.getDocumentation());
		System.out.println(" WSDL Ducument Version() : " + wsdlDucument.getVersion());
		System.out.println(" WSDL Ducument Namespaces() : " + wsdlDucument.getNamespaces());
		System.out.println(" WSDL Ducument Schema Location() : " + wsdlDucument.getSchemaLocation());
		System.out.println(" WSDL Ducument Other Attributes() : " + wsdlDucument.getOtherAttributes());
		System.out.println(" WSDL Ducument Other Elements : " + wsdlDucument.getOtherElements());
		
		System.out.println(" WSDL Ducument  Binding Bean : " + wsdlDucument.getBindings());
		System.out.println(" WSDL Ducument  InterfacesBean : " + wsdlDucument.getInterfaces());
		System.out.println(" WSDL Ducument  ServicesBean : " + wsdlDucument.getServices());
		System.out.println(" WSDL Ducument  TypesBean : " + wsdlDucument.getType());	
		System.out.println(" WSDL Ducument  IncludeBean : " + wsdlDucument.getIncludes());	
		System.out.println(" WSDL Ducument  ImportBean : " + wsdlDucument.getImports());	
	}
	
	@Test 
	public void getWSDLInfoByDefualt() throws XmlException {
		System.out.println("  ****************  WSDL Document use Original ***************** ");
		
		System.out.println(" WSDL Ducument Qname : " + desc.getQName());
		System.out.println(" WSDL Ducument Target Namespace() : " + desc.getTargetNamespace());
		System.out.println(" WSDL Ducument Document BaseURI() : " + desc.getDocumentBaseURI());
		System.out.println(" WSDL Ducument Documentation : " + desc.getDocumentation());
		System.out.println(" WSDL Ducument Version() : " + desc.getVersion());
		System.out.println(" WSDL Ducument Namespaces() : " + desc.getNamespaces());
		System.out.println(" WSDL Ducument Schema Location() : " + desc.getSchemaLocation());
		System.out.println(" WSDL Ducument Other Attributes() : " + desc.getOtherAttributes());
		System.out.println(" WSDL Ducument Other Elements : " + desc.getOtherElements());
		
		System.out.println(" WSDL Ducument  Binding  : " + desc.getBindings());
		System.out.println(" WSDL Ducument  Interfaces : " + desc.getInterfaces());
		System.out.println(" WSDL Ducument  Services : " + desc.getServices());
		System.out.println(" WSDL Ducument  Types : " + desc.getTypes());	
		System.out.println(" WSDL Ducument  Include : " + desc.getIncludes());	
		System.out.println(" WSDL Ducument  Import : " + desc.getImports());	
	}
	

}
