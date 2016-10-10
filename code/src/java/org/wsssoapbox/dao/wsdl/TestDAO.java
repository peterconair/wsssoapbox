package org.wsssoapbox.dao.wsdl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.WSDLFactory;
import org.ow2.easywsdl.wsdl.api.Description;
import org.ow2.easywsdl.wsdl.api.WSDLException;
import org.ow2.easywsdl.wsdl.api.WSDLReader;
import org.wsssoapbox.bean.model.wsdl.BindingBean;
import org.wsssoapbox.bean.model.wsdl.DescriptionBean;
import org.wsssoapbox.bean.model.wsdl.InterfaceBean;
import org.wsssoapbox.bean.model.wsdl.ServicesBean;
import org.wsssoapbox.bean.model.wsdl.TypeBean;

public class TestDAO {
	static Description desc;
	static WSDLReader reader;

	public static void main(String args[]) throws XmlException {
		String url = "http://localhost:8888/WebServiceTutorial/ws/doc/hello?wsdl";


		TestDAO.initail(url);
	
		System.out.println("********************** WSDL **********************");
		DescriptionBean wsdlBean = new DescriptionBean();
		IWSDLDAO wsdlDAO = new WSDLDAO();
		wsdlBean = wsdlDAO.getWSDLDocument(desc);		
		System.out.println("  Qname  : " + wsdlBean.getQName());
		
		
		
		System.out.println("********************** Service **********************");
		IServiceDAO serviceDAO = new ServiceDAO();
		List<ServicesBean> serviceBeanList = new ArrayList<ServicesBean>();
		serviceBeanList = serviceDAO.getServices(desc);

		for (ServicesBean serviceBean : serviceBeanList) {
			System.out.println("  Qname  : " + serviceBean.getQname());
			System.out.println("  name  : " + serviceBean.getName());
			System.out.println("  name  : " +serviceBean.getInterfaceType().getOperations().get(0).getQname());

		}
		
		System.out.println("********************** Interface **********************");
		List <InterfaceBean> interfaceBeanList = new ArrayList<InterfaceBean>();
		IInterfaceDAO interfaceDAO = new InterfaceDAO();
		interfaceBeanList = interfaceDAO.getInterfaces(desc);
		for (InterfaceBean i : interfaceBeanList) {
			System.out.println("  Qname  : " + i.getQname());
			System.out.println("  name  : " + i.getName());
			System.out.println("  Operations  : " + i.getOperations());
		}
		
		System.out.println("********************** Binding **********************");
		List <BindingBean> bindingBeanList = new ArrayList<BindingBean>();
		IBindingDAO bindingDAO = new BindingDAO();
		bindingBeanList = bindingDAO.getBindings(desc);
		for (BindingBean i : bindingBeanList) {
			System.out.println("  Qname  : " + i.getQname());
			System.out.println("  name  : " + i.getName());
		}
		
		System.out.println("********************** Type **********************");
		TypeBean typeBean = new TypeBean();
		ITypeDAO typeDAO = new TypeDAO();
		typeBean = typeDAO.getTypes(desc);
			System.out.println("  Qname  : " + typeBean.getQname());
			System.out.println("  Schema  "+typeBean.getSchemas());
			System.out.println("  Import Schema  "+typeBean.getImportSchemas());
			System.out.println("  Import Other Attributes  "+typeBean.getOtherAttributes());
			System.out.println("  Import Other Elements "+typeBean.getOtherElements());
			
	
			
		
	}

	public static void initail(String wsdlURL) {

		System.out.println(">>>>>>>>>>>>>>>> Start private void initail(String wsdlURL) ");
		try {
			reader = WSDLFactory.newInstance().newWSDLReader();
			desc = reader.read(new URL(wsdlURL));
		} catch (MalformedURLException e1) {
			System.out.println("MalformedURLException");
			e1.printStackTrace();
		} catch (IOException e1) {
			System.out.println("IOException");
			e1.printStackTrace();
		} catch (URISyntaxException e1) {
			System.out.println("URISyntaxException");
			e1.printStackTrace();
		} catch (WSDLException e) {
			System.out.println("WSDLException");
			e.printStackTrace();
		}
		System.out.println(">>>>>>>>>>>>>>>>>> End private void initail(String wsdlURL) <<<<<<<<<<<<<<<<");
	}
}
