package org.wsssoapbox.bean.backing;

import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;
import javax.xml.namespace.QName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.api.Description;
import org.ow2.easywsdl.wsdl.api.WSDLException;
import org.ow2.easywsdl.wsdl.api.WSDLReader;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.wsssoapbox.datasource.wsdl.WSDLDocumentDataSource;
import org.wsssoapbox.view.bean.TreeBean;
import org.wsssoapbox.bean.model.wsdl.*;
import org.wsssoapbox.dao.wsdl.*;

public class WSDLImportForm implements Serializable {

	private static final long serialVersionUID = 1L;
	private String url;

	private List<PortBean> portList;
	private OperationBean operationBean;
	private BindingOperationBean bindingOperatonBean;
	private List<BindingOperationBean> bindingOperationBeanList;
	private List<OperationBean> operationBeanList;
	private List<MessagesBean> messageBeanList;
	private List<PortBean> portBeanList;
	private ServicesBean serviceBean;
	private List<ServicesBean> serviceBeanList;
	private List<EndpointBean> endpointBeanList;
	private List<BindingBean> bindingBeanList;
	private List<InterfaceBean> interfaceBeanList;
	private DescriptionBean wsdlDucument;
	private Description desc;
	private WSDLReader reader;
	private boolean downloadButton;

	private static final Log logger = LogFactory.getLog(WSDLImportForm.class);

	public WSDLImportForm() {

		logger.debug(">>>>>>>>>>>>>>>> Stat public WSDLImportForm() ");

		// url = "http://localhost:8888/WebServiceTutorial/ws/doc/hello?wsdl";
		// http://www.pttplc.com/pttinfo.asmx?WSDL
		url = "http://www.pttplc.com/pttinfo.asmx?WSDL";
		logger.debug(">>>>>>>>>>>>>>>> End public WSDLImportForm() <<<<<<<<<<<<<<<<<<<<<");
	}

	public void clearScreen() {
		logger.debug(">>>>>>>>>>>>>>>> Start clearScreen ");
		downloadButton = false;
		wsdlDucument = null;
		serviceBean = null;
		operationBean = null;
		endpointBeanList = null;
		portBeanList = null;
		serviceBeanList = null;
		operationBeanList = null;
		messageBeanList = null;
		bindingBeanList = null;
		bindingOperationBeanList = null;
		interfaceBeanList = null;
		logger.debug(">>>>>>>>>>>>>>>> End clearScreen <<<<<<<<<<<<<<<<<<<<<");
	}

	public void initial() {
		logger.debug(">>>>>>>>>>>>>>>> Start initial ");
		downloadButton = false;
		wsdlDucument = new DescriptionBean();
		serviceBean = new ServicesBean();
		operationBean = new OperationBean();
		serviceBeanList = new ArrayList<ServicesBean>();
		endpointBeanList = new ArrayList<EndpointBean>();
		portBeanList = new ArrayList<PortBean>();
		operationBeanList = new ArrayList<OperationBean>();
		messageBeanList = new ArrayList<MessagesBean>();
		bindingBeanList = new ArrayList<BindingBean>();
		bindingOperationBeanList = new ArrayList<BindingOperationBean>();
		interfaceBeanList = new ArrayList<InterfaceBean>();
		logger.debug(">>>>>>>>>>>>>>>> End initial <<<<<<<<<<<<<<<<<<<<<");
	}

	public void submit(ActionEvent event) throws WSDLException, XmlException {
		logger.debug(">>>>>>>>>>>>>>>> Strat submit(ActionEvent event)");

		initial();
		WSDLDocumentDataSource wsdlDocumentDataSource = null;

		try {
			wsdlDocumentDataSource = new WSDLDocumentDataSource(url);

			//	setSessionMap("wsdlDataSource", wsdlDocumentDataSource);

			//	wsdlDocumentDataSource = (WSDLDocumentDataSource) getSessionMap("wsdlDataSource");
			this.reader = wsdlDocumentDataSource.getReader();
			this.desc = wsdlDocumentDataSource.getDesc();
					
			setSessionMap("desc", desc);
			
			downloadButton = true;

			logger.info("  ****************  WSDL Document ***************** ");

			IWSDLDAO wsdlDAO = new WSDLDAO();
			wsdlDucument = wsdlDAO.getWSDLDocument(desc);

			logger.info(" WSDL Ducument Name :" + wsdlDucument.getName());
			logger.info(" WSDL Ducument Qname : " + wsdlDucument.getQName());
			logger.info(" WSDL Ducument Prefix : " + wsdlDucument.getPrefix());
			logger.info(" WSDL Ducument Local Part : " + wsdlDucument.getLocalPart());
			logger.info(" WSDL Ducument Target Namespace() : " + wsdlDucument.getTargetNamespace());
			logger.info(" WSDL Ducument Document BaseURI() : " + wsdlDucument.getDocumentBaseURI());
			logger.info(" WSDL Ducument Documentation : " + wsdlDucument.getDocumentation());
			logger.info(" WSDL Ducument Version() : " + wsdlDucument.getVersion());
			logger.info(" WSDL Ducument Namespaces() : " + wsdlDucument.getNamespaces());
			logger.info(" WSDL Ducument  Binding Bean : " + wsdlDucument.getBindings());
			logger.info(" WSDL Ducument  InterfacesBean : " + wsdlDucument.getInterfaces());
			logger.info(" WSDL Ducument  ServicesBean : " + wsdlDucument.getServices());
			logger.info(" WSDL Ducument  TypesBean : " + wsdlDucument.getType());

			setSessionMap("wsdlDucument", wsdlDucument);

			logger.info("  ****************  Type of WSDL Document ***************** ");

		//	ITypeDAO typeDAO = new TypeDAO();
			TypeBean typeBean = new TypeBean();
			//	typeBean = typeDAO.getTypes(desc);
			typeBean = wsdlDucument.getType();
			logger.info(" Type Qname :" + typeBean.getQname());
			logger.info(" Type Documentation :" + typeBean.getDocumentation());
			logger.info(" Type Import Schema :" + typeBean.getImportSchemas());
			logger.info(" Type Schema :" + typeBean.getSchemas());

			logger.info("  **************** Schema Type of WSDL Document ***************** ");
			ISchemaDAO schemaDAO = new SchemaDAO();
			List<SchemaBean> schemaBeanList = new ArrayList<SchemaBean>();
			schemaBeanList = schemaDAO.getSchemas(desc);
			for (SchemaBean sb : schemaBeanList) {
				logger.info(" Schema Lang :" + sb.getLang());
				logger.info(" Schema Documentation :" + sb.getDocumentation());
				logger.info(" Schema Target Namespac :" + sb.getTargetNamespace());
				logger.info(" Schema Version :" + sb.getVersion());
				logger.info(" Schema All Namespaces :" + sb.getAllNamespaces());
				logger.info(" Schema Attribute Form Default :" + sb.getAttributeFormDefault());
				logger.info(" Schema Attributes :" + sb.getAttributes());
				logger.info(" Schema Block Default :" + sb.getBlockDefault());
				logger.info(" Schema Document URI :" + sb.getDocumentURI());
				logger.info(" Schema Element Form Default :" + sb.getElementFormDefault());
				logger.info(" Schema Elements :" + sb.getElements());
				logger.info(" Schema Elements :" + sb.getFinalDefault());
				logger.info(" Schema Imports :" + sb.getImports());
				logger.info(" Schema Includes :" + sb.getIncludes());
				logger.info(" Schema Other Attributes :" + sb.getOtherAttributes());
				logger.info(" Schema Types :" + sb.getTypes());
			}

			// All Binding

			logger.info("  ****************All Binding from WSDL Document ***************** ");

			List<BindingBean> bbList = wsdlDucument.getBindings();

			if (bbList != null) {
				for (BindingBean bb : bbList) {
					logger.info("***********************************************");
					logger.info(" Binding Name() : " + bb.getName());
					logger.info("***********************************************");
					logger.info(" Binding Qname : " + bb.getQname());
					logger.info(" Binding Documentation : " + bb.getDocumentation());
					logger.info(" Binding Http Content Encoding : " + bb.getHttpContentEncodingDefault());
					logger.info(" Binding Http Default Method : " + bb.getHttpDefaultMethod());
					logger.info(" Binding Transport Protocol : " + bb.getTransportProtocol());
					logger.info(" Binding Type Of Binding : " + bb.getTypeOfBinding());
					logger.info(" Binding Version : " + bb.getVersion());
					logger.info(" Binding Style : " + bb.getStyle());
					logger.info(" Binding Other Attributes : " + bb.getOtherAttributes());
					logger.info(" Binding Other Elements : " + bb.getOtherElements());
					logger.info(" Binding Binding OperationList : " + bb.getBindingOperations());
					logger.info(" Binding Interfaces : " + bb.getInterfaces());

				}
			}

			// Service Important support only onservice
			logger.info("  ****************  Service ***************** ");
			//  IServiceDAO serviceDAO = new ServiceDAO();
			//	serviceBean = serviceDAO.getServiceByIndex(desc, 0);

			serviceBean = wsdlDucument.getServices().get(0);

			logger.info(" Servic Name : " + serviceBean.getName());
			logger.info(" Service Qname : " + serviceBean.getQname());
			logger.info(" serviceBean.getDocumentation() : " + serviceBean.getDocumentation());
			logger.info(" Servic Namespace URI: " + serviceBean.getNamespaceURI());
			logger.info(" Servic Other Attributes: " + serviceBean.getOtherAttributes());
			logger.info(" Servic Other Elements() : " + serviceBean.getOtherElements());
			logger.info(" Servic Endpoints: " + serviceBean.getEndpoints());
			logger.info(" Servic Interfacs : " + serviceBean.getInterfaceType());

			setSessionMap("serviceBean", serviceBean);
			
			
			serviceBeanList = wsdlDucument.getServices();
			setSessionMap("serviceBeanList", serviceBeanList);
			
						
			// Binding
			//IBindingDAO bindingDAO = new BindingDAO();
			logger.info("  ****************  Binding ***************** ");

			//	bindingBeanList = bindingDAO.getBindings(desc);
			bindingBeanList = wsdlDucument.getBindings();

			// logger.info("Binding Qname() : " + bindingBean.getQname());

			for (BindingBean bb : bindingBeanList) {
				logger.info("***********************************************");
				logger.info(" Binding Name() : " + bb.getName());
				logger.info("***********************************************");
				logger.info(" Binding Qname : " + bb.getQname());
				logger.info(" Binding Documentation : " + bb.getDocumentation());
				logger.info(" Binding Http Content Encoding : " + bb.getHttpContentEncodingDefault());
				logger.info(" Binding Http Default Method : " + bb.getHttpDefaultMethod());
				logger.info(" Binding Transport Protocol : " + bb.getTransportProtocol());
				logger.info(" Binding Type Of Binding : " + bb.getTypeOfBinding());
				logger.info(" Binding Version : " + bb.getVersion());
				logger.info(" Binding Style : " + bb.getStyle());
				logger.info(" Binding Other Attributes : " + bb.getOtherAttributes());
				logger.info(" Binding Other Elements : " + bb.getOtherElements());
				logger.info(" Binding Binding OperationList : " + bb.getBindingOperations());
				logger.info(" Binding Interfaces : " + bb.getInterfaces());

				bindingOperationBeanList = bb.getBindingOperations();

				for (BindingOperationBean bol : bindingOperationBeanList) {
					logger.info(" ************************************************* ");
					logger.info(" Binding Operation Name: " + bol.getName());
					logger.info(" ************************************************* ");
					logger.info(" Binding Operation  QName : " + bol.getQname());
					logger.info(" Binding Operation  Style : " + bol.getStyle());
					logger.info(" Binding Operation  MEP : " + bol.getMEP());
					logger.info(" Binding Operation  Documentation : " + bol.getDocumentation());
					logger.info(" Binding Operation  Http Content Encoding Default: "
							+ bol.getHttpContentEncodingDefault());
					logger.info(" Binding Operation  Http Input Serialization : " + bol.getHttpInputSerialization());
					logger.info(" Binding Operation  HttpLocation : " + bol.getHttpLocation());
					logger.info(" Binding Operation  HttpMethod : " + bol.getHttpMethod());
					logger.info(" Binding Operation  Http Output Serialization : " + bol.getHttpOutputSerialization());
					logger.info(" Binding Operation  Http Query Parameter Separator : "
							+ bol.getHttpQueryParameterSeparator());
					logger.info(" Binding Operation  Namespace URI : " + bol.getNamespaceURI());
					logger.info(" Binding Operation  Soap Action : " + bol.getSoapAction());
					logger.info(" Binding Operation  Style : " + bol.getStyle());
					logger.info(" Binding Operation  Faults : " + bol.getFaults());
					logger.info(" Binding Operation  Input : " + bol.getInput());
					logger.info(" Binding Operation  Other Attributes : " + bol.getOtherAttributes());
					logger.info(" Binding Operation  Other Elements : " + bol.getOtherElements());
					logger.info(" Binding Operation  Output: " + bol.getOutput());
					logger.info(" Binding Operation  OperatonBean : " + bol.getOperation());
				}
				logger.info(" Interfac  : " + bb.getInterfaces().getName());

			}

			setSessionMap("bindingBeanList", bindingBeanList);
			setSessionMap("bindingOperationBeanList", bindingOperationBeanList);

			// Endpoint
			logger.info("   ***************  EndPoint ***************** ");
			//IEndpointDAO endpointDAO = new EndpointDAO();
			//QName serviceQName = serviceBean.getQname();
			logger.info("Service QName for Endpoint : " + serviceBean.getQname());

			//		endpointBeanList = endpointDAO.getEndpointsByServiceName(desc, serviceQName);
			endpointBeanList = serviceBean.getEndpoints();

			for (EndpointBean epb : endpointBeanList) {
				logger.info("EndPoint Name : " + epb.getName());
				logger.info("EndPoint Address : " + epb.getAddress());
				logger.info("EndPoint Documentation : " + epb.getDocumentation());
				logger.info("EndPoint Http Authentication Reaml : " + epb.getHttpAuthenticationRealm());
				logger.info("EndPoint Http Authetication Scheme : " + epb.getHttpAuthenticationRealm());
				logger.info("EndPoint Other Elements : " + epb.getOtherElements());
				logger.info("EndPoint Other Attributes : " + epb.getOtherAttributes());
				logger.info("EndPoint Binding: " + epb.getBinding());
				logger.info("EndPoint Service : " + epb.getService());
			}

			setSessionMap("endpointBeanList", endpointBeanList);

			// Interface
			logger.info("   ***************  Interface ***************** ");
			//IInterfaceDAO interfaceDAO = new InterfaceDAO();
			List<InterfaceBean> interfaceList = new ArrayList<InterfaceBean>();

			interfaceList = wsdlDucument.getInterfaces();
			//		interfaceList = interfaceDAO.getInterfaces(desc);

			QName interfaceQName = new QName("");
			for (InterfaceBean ib : interfaceList) {
				logger.info(" Interface Name : " + ib.getName());
				logger.info(" Interface Qname : " + ib.getQname());
				logger.info(" Interface Documentation : " + ib.getDocumentation());
				logger.info(" Interface NamespaceURI : " + ib.getNamespaceURI());
				logger.info(" Interface Ohter Elements : " + ib.getOhterElements());
				logger.info(" Interface Other Attributes : " + ib.getOtherAttributes());
				logger.info(" Interface Operations : " + ib.getOperations());

				interfaceQName = ib.getQname();

			}

			setSessionMap("interfaceList", interfaceList);

			logger.info(" *****************  Operation ***************** ");
			IOperationDAO operationDAO = new OperationDAO();

			//		operationBeanList = operationDAO.getOperationsInterfaceByName(desc, interfaceQName);
			operationBeanList = operationDAO.getOperationsInterfaceByName(desc, interfaceQName);
			logger.info("Interface QName for Operation : " + interfaceQName.toString());
			for (OperationBean bol : operationBeanList) {

				logger.info(" ************************************************* ");
				logger.info(" Interface Operation Name: " + bol.getName());
				logger.info(" ************************************************* ");
				logger.info(" Interface Operation  QName : " + bol.getQname());
				logger.info(" Interface Operation  Parameter Ordering : " + bol.getParameterOrdering());
				logger.info(" Interface Operation  Documentation : " + bol.getDocumentation());
				logger.info(" Interface Operation  Faults : " + bol.getFaults());
				logger.info(" Interface Operation  Input : " + bol.getInput());
				logger.info(" Interface Operation  Other Attributes : " + bol.getOtherAttributes());
				logger.info(" Interface Operation  Other Elements : " + bol.getOtherElements());
				logger.info(" Interface Operation  Output: " + bol.getOutput());

			
			}
			setSessionMap("operationBeanList", operationBeanList);

		} catch (MalformedURLException e3) {
			e3.printStackTrace();
			logger.error("MalformedURLException :" + e3.getMessage());
			e3.printStackTrace();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error ", e3.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, msg);
			clearScreen();
		} catch (URISyntaxException e3) {
			e3.printStackTrace();
			logger.error("URISyntaxException :" + e3.getMessage());
			e3.printStackTrace();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error ", e3.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, msg);
			clearScreen();
		} catch (IOException e3) {
			e3.printStackTrace();
			logger.error("IOException :" + e3.getMessage());
			e3.printStackTrace();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error ", e3.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, msg);
			clearScreen();

		} catch (WSDLException e1) {
			e1.printStackTrace();
			logger.error("Exception :" + e1.getMessage());
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO", e1.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, msg);
			// refresh page
			clearScreen();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception :" + e.getMessage());
			e.printStackTrace();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error ", e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, msg);
			clearScreen();
		}

		// Get Page Controller from session
		PageViewController pageContoller = (PageViewController) getSessionMap("pageViewController");
		pageContoller.setIncludePanel(true);
		pageContoller.setOverviewPanel(true);

		// Get TestTreeBean from seesion
		TreeBean treeBean = (TreeBean) getSessionMap("treeBean");
		TreeNode rootNode = treeBean.getRoot();

		logger.info(rootNode.getChildren());

		// set root node for tree node.
		treeBean.setRoot(constructTreeNode());

		// Get request context object
		RequestContext rc = RequestContext.getCurrentInstance();
		// update tree menu
		rc.addPartialUpdateTarget("treeForm:treePanel");
		// rc.addPartialUpdateTarget("treeForm:treeView");

		logger.debug(">>>>>>>>>>>>>>>>> End submit(ActionEvent event) <<<<<<<<<<<<<<<<<<");
	}

	public TreeNode constructTreeNode() {

		logger.debug("**********************  constructTreeNode ************************ ");
		TreeNode root = new DefaultTreeNode("root", null);
		TreeNode wsdlTreeNode = new DefaultTreeNode("wsdldoc", wsdlDucument, root);

		TreeNode serviceTreeNode = new DefaultTreeNode("service", serviceBean, wsdlTreeNode);

		logger.info(" Service Name : " + serviceBean.getName());
		logger.info(" Endpont Count: " + endpointBeanList.size());
		logger.info(" Operaiton Count: " + operationBeanList.size());

		for (EndpointBean eb : endpointBeanList) {
			logger.info(" Endpont Name: " + eb.getName());
			TreeNode endpointTreeNode = new DefaultTreeNode("endpoint", eb, serviceTreeNode);
			for (OperationBean ob : operationBeanList) {
				logger.info(" Operation Name : " + ob.getName());
				TreeNode operationTreeNode = new DefaultTreeNode("operation", ob, endpointTreeNode);
			}

			logger.info("*******************************I am a root node : " + root.getChildren());
		}
		logger.debug("********************** end constructTreeNode ************************ ");
		return root;

	}

	public void setSessionMap(String name, Object object) {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		session.setAttribute(name, object);
	}

	public Object getSessionMap(String name) {
		Object object = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(name);
		return object;
	}

	public DescriptionBean getWsdlDucument() {
		return wsdlDucument;
	}

	public void setWsdlDucument(DescriptionBean wsdlDucument) {
		this.wsdlDucument = wsdlDucument;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<OperationBean> getOperationList() {
		return operationBeanList;
	}

	public void setOperationList(List<OperationBean> operationList) {
		this.operationBeanList = operationList;
	}

	public List<PortBean> getPortList() {
		return portList;
	}

	public void setPortList(List<PortBean> portList) {
		this.portList = portList;
	}

	public ServicesBean getService() {
		return serviceBean;
	}

	public void setServiceList(ServicesBean service) {
		this.serviceBean = service;
	}

	public OperationBean getOperationBean() {
		return operationBean;
	}

	public void setOperationBean(OperationBean operationBean) {
		this.operationBean = operationBean;
	}

	public List<EndpointBean> getEndpointBeanList() {
		return endpointBeanList;
	}

	public void setEndpointBeanList(List<EndpointBean> endpointBeanList) {
		this.endpointBeanList = endpointBeanList;
	}

	public boolean isDownloadButton() {
		return downloadButton;
	}

	public void setDownloadButton(boolean downloadButton) {
		this.downloadButton = downloadButton;
	}

}
