package org.wsssoapbox.dao.wsdl;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.api.Description;
import org.wsssoapbox.bean.model.wsdl.BindingBean;
import org.wsssoapbox.bean.model.wsdl.DescriptionBean;
import org.wsssoapbox.bean.model.wsdl.ImportBean;
import org.wsssoapbox.bean.model.wsdl.IncludeBean;
import org.wsssoapbox.bean.model.wsdl.InterfaceBean;
import org.wsssoapbox.bean.model.wsdl.ServicesBean;
import org.wsssoapbox.bean.model.wsdl.TypeBean;

public class WSDLDAO implements IWSDLDAO {

	private DescriptionBean wsdlBean;

	private static final Log logger = LogFactory.getLog(WSDLDAO.class);

	public WSDLDAO() {

	}

	public DescriptionBean getWSDLDocument(Description desc) throws XmlException {

		wsdlBean = new DescriptionBean();

		if ((desc.getQName() != null) && (desc != null)) {
			if (desc.getQName().getLocalPart().equals("") || desc.getQName().getLocalPart() == null) {
				wsdlBean.setName(desc.getDocumentBaseURI().toString());
			} else {
				wsdlBean.setName("[" + desc.getQName().getLocalPart() + "]" + desc.getDocumentBaseURI().toString());
			}
			//	wsdlBean.setName(desc.getDocumentBaseURI().toString());
			wsdlBean.setQName(desc.getQName());
			wsdlBean.setPrefix(desc.getQName().getPrefix());
			wsdlBean.setLocalPart(desc.getQName().getLocalPart());
		} else {
			wsdlBean.setQName(desc.getQName());
			wsdlBean.setName(desc.getDocumentBaseURI().toString());
			wsdlBean.setPrefix("");
			wsdlBean.setLocalPart("");
		}
		wsdlBean.setDocumentation(desc.getDocumentation().getContent());
		wsdlBean.setTargetNamespace(desc.getTargetNamespace());
		wsdlBean.setDocumentBaseURI(desc.getDocumentBaseURI().toString());
		wsdlBean.setVersion(desc.getVersion().toString());

		wsdlBean.setNamespaces(desc.getNamespaces().getNamespaces());

		wsdlBean.setOtherAttributes(desc.getOtherAttributes());
		wsdlBean.setOtherElements(desc.getOtherElements());
		wsdlBean.setSchemaLocation(desc.getSchemaLocation());

		// get and set Import (One Doc have Many Imported)
		IImportDAO importDAO = new ImportDAO();
		List<ImportBean> importBeanList = new ArrayList<ImportBean>();
		importBeanList = importDAO.getImportByDescription(desc);
		wsdlBean.setImports(importBeanList);

		IIncludeDAO includeDAO = new IncludeDAO();
		List<IncludeBean> includeBeanList = new ArrayList<IncludeBean>();
		includeBeanList = includeDAO.getIncludeByDescription(desc);

		wsdlBean.setIncludes(includeBeanList);

		// get and set Service (One Doc have Many Services)
		IServiceDAO serviceDAO = new ServiceDAO();
		List<ServicesBean> serviceBeanList = serviceDAO.getServices(desc);
		wsdlBean.setServices(serviceBeanList);

		// get and set Interface (One Doc have Many Interface)
		IInterfaceDAO interfaceDAO = new InterfaceDAO();
		List<InterfaceBean> interfaceBeanList = interfaceDAO.getInterfaces(desc);
		wsdlBean.setInterfaces(interfaceBeanList);

		// get and set binding (One Doc have Many Binding)
		IBindingDAO bindingDAO = new BindingDAO();
		List<BindingBean> bindingBeanList = bindingDAO.getBindings(desc);
		wsdlBean.setBindingBean(bindingBeanList);

		// get and set Type (One Doc have one Type)
		ITypeDAO typeDAO = new TypeDAO();
		TypeBean typeBean = typeDAO.getTypes(desc);
		wsdlBean.setTypesBean(typeBean);

		return wsdlBean;
	}

	public InterfaceBean getInterface(QName interfaceQName) {
		InterfaceBean interfaceBean = new InterfaceBean();
		
		
		
		return interfaceBean;
	}
}
