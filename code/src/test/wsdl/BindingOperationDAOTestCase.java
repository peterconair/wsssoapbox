package wsdl;

import static org.junit.Assert.*;

import java.util.List;

import javax.xml.bind.JAXBElement;

import org.junit.Before;
import org.junit.Test;
import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.api.Binding;
import org.ow2.easywsdl.wsdl.api.BindingOperation;
import org.ow2.easywsdl.wsdl.api.Description;
import org.ow2.easywsdl.wsdl.impl.wsdl11.BindingInputImpl;
import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.TBindingOperationMessage;
import org.ow2.easywsdl.wsdl.org.xmlsoap.schemas.wsdl.soap.TBody;
import org.wsssoapbox.bean.model.wsdl.BindingOperationBean;
import org.wsssoapbox.dao.wsdl.BindingOperationDAO;
import org.wsssoapbox.dao.wsdl.IBindingOperationDAO;
import org.wsssoapbox.datasource.wsdl.WSDLDataSource;

/**
 * @author  Peter
 */
public class BindingOperationDAOTestCase {
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
		BindingOperationDAOTestCase.desc = WSDLDataSource.getDesc(WSDLList.url);
	}

	@Test
	public void testGetOperationByBinding() throws XmlException {

		Binding binding = desc.getBindings().get(0);
		IBindingOperationDAO bindingOpertaionDAO = new BindingOperationDAO();
		List<BindingOperationBean> bindingOperationBeanList = bindingOpertaionDAO
				.getBindingOperationsByBinding(binding);
		System.out.println(" ******************* testGetOperationByBinding() **************************** ");
		for (BindingOperationBean bol : bindingOperationBeanList) {

			System.out.println(" Binding Operation Name: " + bol.getName());
			System.out.println(" ************************************************* ");
			System.out.println(" Binding Operation  Namespace URI : " + bol.getNamespaceURI());
			System.out.println(" Binding Operation  QName : " + bol.getQname());
			System.out.println(" Binding Operation  Style : " + bol.getStyle());
			System.out.println(" Binding Operation  MEP : " + bol.getMEP());
			System.out.println(" Binding Operation  Documentation : " + bol.getDocumentation());
			System.out.println(" Binding Operation  Http Content Encoding Default: "
					+ bol.getHttpContentEncodingDefault());

			System.out.println(" Binding Operation  Http Input Serialization : " + bol.getHttpInputSerialization());
			System.out.println(" Binding Operation  HttpLocation : " + bol.getHttpLocation());
			System.out.println(" Binding Operation  HttpMethod : " + bol.getHttpMethod());
			System.out.println(" Binding Operation  Http Output Serialization : " + bol.getHttpOutputSerialization());
			System.out.println(" Binding Operation  Http Query Parameter Separator : "
					+ bol.getHttpQueryParameterSeparator());
			System.out.println(" Binding Operation  Soap Action : " + bol.getSoapAction());
			System.out.println(" Binding Operation  Style : " + bol.getStyle());
			System.out.println(" Binding Operation  Other Attributes : " + bol.getOtherAttributes());
			System.out.println(" Binding Operation  Other Elements : " + bol.getOtherElements());
			System.out.println(" Binding Operation  Input : " + bol.getInput());
			System.out.println(" Binding Operation  Output: " + bol.getOutput());
			System.out.println(" Binding Operation  Faults : " + bol.getFaults());
			System.out.println(" Binding Operation  OperatonBean : " + bol.getOperation());
			System.out.println(" ************************************************* ");
			assertNotNull(bol.getInput());
			assertNotNull(bol.getOutput());
		}
	}

//	@Test
	public void testGetOperationByBindingDefualt() throws XmlException {

		List<BindingOperation> bindingOperationList = desc.getBindings().get(0).getBindingOperations();
		System.out.println(" ****************** testGetOperationByBindingDefualt() **************************** ");
		for (BindingOperation bol : bindingOperationList) {

			System.out.println(" Binding Operation Name: " + bol.getQName().getLocalPart());
			System.out.println(" ************************************************* ");
			System.out.println(" Binding Operation  Namespace URI : " + bol.getQName().getNamespaceURI());
			System.out.println(" Binding Operation  QName : " + bol.getQName());
			System.out.println(" Binding Operation  Style : " + bol.getStyle());
			System.out.println(" Binding Operation  MEP : " + bol.getMEP());
			System.out.println(" Binding Operation  Documentation : " + bol.getDocumentation());
			System.out.println(" Binding Operation  Http Content Encoding Default: "
					+ bol.getHttpContentEncodingDefault());

			System.out.println(" Binding Operation  Http Input Serialization : " + bol.getHttpInputSerialization());
			System.out.println(" Binding Operation  HttpLocation : " + bol.getHttpLocation());
			System.out.println(" Binding Operation  HttpMethod : " + bol.getHttpMethod());
			System.out.println(" Binding Operation  Http Output Serialization : " + bol.getHttpOutputSerialization());
			System.out.println(" Binding Operation  Http Query Parameter Separator : "
					+ bol.getHttpQueryParameterSeparator());
			System.out.println(" Binding Operation  Soap Action : " + bol.getSoapAction());
			System.out.println(" Binding Operation  Style : " + bol.getStyle());
			System.out.println(" Binding Operation  Other Attributes : " + bol.getOtherAttributes());
			System.out.println(" Binding Operation  Other Elements : " + bol.getOtherElements());
			System.out.println(" Binding Operation  Input : " + bol.getInput());
			//TBindingOperationMessage t = (TBindingOperationMessage) bol.getInput();
			BindingInputImpl in1 = (BindingInputImpl) bol.getInput();
			System.out.println(" Binding Operation  Model : " + in1.getModel());
			System.out.println(" Binding Operation  Model Any : " + in1.getModel().getAny());
			System.out.println(" Binding Operation  Model Any : " + in1.getModel().getAny().get(0));
			JAXBElement jaxB = (JAXBElement) in1.getModel().getAny().get(0);
			System.out.println(" Binding Operation  Model Value : " + jaxB.getValue());
			TBody tbody = (TBody) jaxB.getValue();
			System.out.println(" Binding Operation  Model Body : " + tbody.getParts());
			System.out.println(" Binding Operation  Output: " + bol.getOutput());
			System.out.println(" Binding Operation  Faults : " + bol.getFaults());
			System.out.println(" Binding Operation  OperatonBean : " + bol.getOperation());
			System.out.println(" ************************************************* ");
			assertNotNull(bol.getInput());
			assertNotNull(bol.getOutput());
		}
	}

}
