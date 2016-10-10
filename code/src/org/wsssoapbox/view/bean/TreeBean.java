package org.wsssoapbox.view.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.primefaces.context.RequestContext;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.wsssoapbox.bean.backing.PageViewController;
import org.wsssoapbox.bean.model.wsdl.BindingBean;
import org.wsssoapbox.bean.model.wsdl.BindingOperationBean;
import org.wsssoapbox.bean.model.wsdl.EndpointBean;
import org.wsssoapbox.bean.model.wsdl.InterfaceBean;
import org.wsssoapbox.bean.model.wsdl.OperationBean;
import org.wsssoapbox.bean.model.wsdl.ServicesBean;

@ManagedBean(name = "treeBean")
@SessionScoped
public class TreeBean implements Serializable {
	private static final long serialVersionUID = 790235955333925301L;

	private TreeNode root;
	private TreeNode selectedNode;
	private TreeNode serviceTreeNode;
	private String selected;
	private TreeNode portTreeNode;
	private TreeNode operationTreeNode;
	private TreeNode messageTreeNode;
	private String page;

	private static final Log logger = LogFactory.getLog(TreeBean.class);

	@SuppressWarnings("unused")
	public TreeBean() {
		logger.debug(" >>>>>>>>>>>>>>> Start public TreeBean() ");
		root = new DefaultTreeNode("root", null);
		page = "/wsdl/documentdetails.xhtml";

		logger.debug(">>>>>>>>>>>>>>> End public TreeBean() <<<<<<<<<<<<<<<<<<<");
	}

	/*
	 * public void displaySelectedSingle(ActionEvent actionEvent) {
	 * StringBuilder builder = new StringBuilder();
	 * builder.append(getSelectedNode().getData().toString()); FacesMessage msg
	 * = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected",
	 * builder.toString()); FacesContext.getCurrentInstance().addMessage(null,
	 * msg);
	 * 
	 * }
	 */
	public void nodeSelectedSingle(NodeSelectEvent nodeSelectEven) {
		logger.debug(">>>>>>>>>>>>>>>>> Start public void nodeSelectedSingle(NodeSelectEvent nodeSelectEven)");
		StringBuilder builder = new StringBuilder();
		builder.append(getSelectedNode().getData().toString());
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected", builder.toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
		logger.debug(">>>>>>>>>>>>>>>>> End  public void nodeSelectedSingle(NodeSelectEvent nodeSelectEven) <<<<<<<<<<<<<<");
	}

	public void onSelectEven(NodeSelectEvent event) {
		logger.debug(">>>>>>>>>>>>>>>>> Start public void onSelectEven(NodeSelectEvent event) ");
		TreeNode selectedNode = (TreeNode) event.getTreeNode();
		TreeNode parentNode = (TreeNode) event.getTreeNode().getParent();
		List<TreeNode> sibling = parentNode.getChildren();

		// int parentCount = parentNode.getParent().
		int childCount = parentNode.getChildCount();
		String parentOfParentNodeValue = "";
		String parentNodeValue = parentNode.getData().toString();
		if (parentNode.getParent() != null) { // if null the selected node are root node.
			parentOfParentNodeValue = parentNode.getParent().getData().toString();
		}
		String selectedNodeValue = selectedNode.getData().toString();
		String nodeType = selectedNode.getType();

		String serviceName = selectedNode.getData().toString();
		String operationName = selectedNode.getData().toString();
		String endpointName = selectedNode.getData().toString();

		setSelected(selectedNodeValue);

		logger.info(" Node selected : " + selectedNodeValue);
		logger.info(" Parent Of ParentNode Value : " + parentOfParentNodeValue);
		logger.info(" Parent Node selected : " + parentNodeValue);
		logger.info(" Sibling  Node count  : " + childCount);
		for (TreeNode n : sibling) {
			logger.info("Sibling  Node of " + selectedNodeValue + " :Node Selected " + n.getData().toString());
		}

		// Get Page Controller from session
		PageViewController pageContoller = (PageViewController) getSessionMap("pageViewController");
		pageContoller.setOverviewPanel(true);

		// check WSDL document node
		if (nodeType.equals("wsdldoc")) {
			page = "/wsdl/documentdetails.xhtml";

		} else if (nodeType.equals("service")) {
			page = "/wsdl/servicesdetails.xhtml";

			ServicesBean serviceBean = new ServicesBean();

			List<ServicesBean> serviceBeanList = new ArrayList<ServicesBean>();
			serviceBeanList = (List<ServicesBean>) getSessionMap("serviceBeanList");

			for (ServicesBean sb : serviceBeanList) {
				if (sb.getName().equals(serviceName)) {
					serviceBean = sb;
				}
			}
			serviceBean = (ServicesBean) getSessionMap("serviceBean");

			InterfaceBean interfaceBean = new InterfaceBean();
			interfaceBean = serviceBean.getInterfaceType();
			logger.debug("interface QName   : " + interfaceBean.getQname());
			setSessionMap("interfaceBean", interfaceBean);

			// check Binding node
		} else if (nodeType.equals("endpoint")) {
			page = "/wsdl/bindingdetails.xhtml";

			List<EndpointBean> endpointBeanList = new ArrayList<EndpointBean>();
			endpointBeanList = (List<EndpointBean>) getSessionMap("endpointBeanList");

			EndpointBean endpointBean = new EndpointBean();

			if (endpointName != null) {
				logger.debug("Endpoint Name  : " + endpointName);
				for (EndpointBean eb : endpointBeanList) {
					if (eb.getName().equals(endpointName)) {
						endpointBean = eb;
					}
				}
			}
			setSessionMap("endpointBean", endpointBean);

			String bindingName = endpointBean.getBinding().getName();
			List<BindingBean> bindingBeanList = new ArrayList<BindingBean>();
			bindingBeanList = (List<BindingBean>) getSessionMap("bindingBeanList");

			BindingBean bindingBean = new BindingBean();

			if (bindingName != null) {
				logger.debug("Binding Name  : " + bindingName);
				for (BindingBean bb : bindingBeanList) {
					if (bb.getName().equals(bindingName)) {
						bindingBean = bb;
					} else {
						bindingBean = endpointBean.getBinding();
					}
				}
			}
			setSessionMap("bindingBean", bindingBean);

			InterfaceBean interfaceBean = new InterfaceBean();
			interfaceBean = bindingBean.getInterfaces();

			logger.debug("interface QName  : " + interfaceBean.getQname());
			setSessionMap("interfaceBean", interfaceBean);

			// check Operation node
		} else if (nodeType.equals("operation")) {

			page = "/wsdl/operationdetails.xhtml";
			setSessionMap("operationName", operationName);
			List<OperationBean> operationBeanList = new ArrayList<OperationBean>();
			operationBeanList = (List<OperationBean>) getSessionMap("operationBeanList");

			OperationBean operationBean = new OperationBean();
			if (operationName != null) {
				logger.debug("Operation Name  : " + operationName);
				for (OperationBean ob : operationBeanList) {
					if (ob.getName().equals(operationName)) {
						operationBean = ob;
					}
				}
			}

			setSessionMap("operationBean", operationBean);

			List<BindingOperationBean> bindingOperationBeanList = new ArrayList<BindingOperationBean>();
			bindingOperationBeanList = (ArrayList<BindingOperationBean>) getSessionMap("bindingOperationBeanList");

			BindingOperationBean bindingOperationBean = new BindingOperationBean();

			if (operationName != null) {
				logger.debug("Operation Name : " + operationName);
				for (BindingOperationBean bob : bindingOperationBeanList) {
					if (bob.getName().equals(operationName)) {
						bindingOperationBean = bob;
					}
				}
			}
			setSessionMap("bindingOperationBean", bindingOperationBean);

			List<EndpointBean> endpointBeanList = new ArrayList<EndpointBean>();
			endpointBeanList = (List<EndpointBean>) getSessionMap("endpointBeanList");

			EndpointBean endpointBean = new EndpointBean();

			if (parentNodeValue != null) {
				for (EndpointBean eb : endpointBeanList) {
					if (eb.getName().equals(parentNodeValue)) {
						endpointBean = eb;
					}
				}
			}
			setSessionMap("endpointBean", endpointBean);

			logger.debug("Endpoint Name : " + endpointBean.getName());
			logger.debug("Endpoint Address : " + endpointBean.getAddress());
			logger.debug("Endpoint Http Authentication Realm : " + endpointBean.getHttpAuthenticationRealm());

			BindingBean bindingBean = endpointBean.getBinding();

			logger.info("Binding Bean : " + bindingBean.getName());
			setSessionMap("bindingBean", bindingBean);

			InterfaceBean interfaceBean = new InterfaceBean();
			interfaceBean = bindingBean.getInterfaces();

			logger.debug("interface QName  : " + interfaceBean.getQname());
			setSessionMap("interfaceBean", interfaceBean);

			List<ServicesBean> serviceBeanList = new ArrayList<ServicesBean>();
			serviceBeanList = (List<ServicesBean>) getSessionMap("serviceBeanList");
			ServicesBean serviceBean = new ServicesBean();

			if (parentOfParentNodeValue != null) {

				for (ServicesBean sb : serviceBeanList) {
					if (sb.getName().equals(parentOfParentNodeValue)) {
						serviceBean = sb;
					}
				}
			}
			setSessionMap("serviceBean", serviceBean);
			logger.debug("serviceBean  : " + serviceBean.getName());

		}

		logger.info("Page to displayed : " + page);
		RequestContext rc = RequestContext.getCurrentInstance();
		rc.addPartialUpdateTarget("importForm:includePanel");

		logger.debug(">>>>>>>>>>>>>>>>> End public void onSelectEven(NodeSelectEvent event) ");
	}

	public TreeNode getRoot() {
		return root;
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}

	public TreeNode getSelectedNode() {
		return selectedNode;
	}

	public void setSelectedNode(TreeNode selectedNode) {
		this.selectedNode = selectedNode;
	}

	public TreeNode getServiceTreeNode() {
		return serviceTreeNode;
	}

	public void setServiceTreeNode(TreeNode serviceTreeNode) {
		this.serviceTreeNode = serviceTreeNode;
	}

	public TreeNode getPortTreeNode() {
		return portTreeNode;
	}

	public void setPortTreeNode(TreeNode portTreeNode) {
		this.portTreeNode = portTreeNode;
	}

	public TreeNode getOperationTreeNode() {
		return operationTreeNode;
	}

	public void setOperationTreeNode(TreeNode operationTreeNode) {
		this.operationTreeNode = operationTreeNode;
	}

	public TreeNode getMessageTreeNode() {
		return messageTreeNode;
	}

	public void setMessageTreeNode(TreeNode messageTreeNode) {
		this.messageTreeNode = messageTreeNode;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public void setSessionMap(String name, Object object) {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		session.setAttribute(name, object);
	}

	public Object getSessionMap(String name) {
		Object object = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(name);
		return object;
	}

}
