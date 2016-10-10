package org.wsssoapbox.view.bean.tree.test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.wsssoapbox.bean.lifecycle.LoggingPhaseListener;
import org.wsssoapbox.bean.model.wsdl.MessagesBean;
import org.wsssoapbox.bean.model.wsdl.OperationBean;
import org.wsssoapbox.bean.model.wsdl.PortBean;
import org.wsssoapbox.bean.model.wsdl.RequestBean;
import org.wsssoapbox.bean.model.wsdl.ServicesBean;
import org.wsssoapbox.view.bean.TreeBean;

@ManagedBean(name = "testTreeBean")
@SessionScoped
public class TestTreeBean implements Serializable {
	private static final long serialVersionUID = 790235955333925301L;

	private TreeNode root;
	TreeNode serviceTreeNode;
	TreeNode portTreeNode;
	TreeNode operationTreeNode;
	TreeNode messageTreeNode;
    private String test;
	private TreeNode selectedNode;
	private static final Log log = LogFactory.getLog(TreeBean.class);

	@SuppressWarnings("unused")
	public TestTreeBean() {
		System.out.println(" Start TestTreeBean() Contructor ");
		//initTreeNode();
		root = new DefaultTreeNode("root", null);
		System.out.println(" End TestTreeBean() Contructor ");

	}

	public void initTreeNode() {
		
		List<PortBean> portList = new ArrayList<PortBean>();
		List<OperationBean> operationList = new ArrayList<OperationBean>();
		List<MessagesBean> messagesList = new ArrayList<MessagesBean>();

		RequestBean req = new RequestBean("Request", "request");
	//	Response res = new Response("Response", "response");

		MessagesBean m1 = new MessagesBean(new Date(), new Date().toString());
		MessagesBean m2 = new MessagesBean(new Date(), new Date().toString());

		messagesList.add(m1);
		// messagesList.add(m2);

		OperationBean o1 = new OperationBean();
		OperationBean o2 = new OperationBean();

		operationList.add(o1);
		// operationList.add(o2);

		PortBean p1 = new PortBean("Port1", operationList);
		PortBean p2 = new PortBean("Port2", operationList);

		portList.add(p1);
		// portList.add(p2);

		root = new DefaultTreeNode("root", null);
		ServicesBean service = new ServicesBean("HelloWorldImpService");

		log.info("Test: " + " Value in Service Bean :" + service.getName());
		System.out.println(" Value in Service Bean <TestTreeBean> :"
				+ service.getName());

		// = new DefaultTreeNode("service", service, root);
		serviceTreeNode = new DefaultTreeNode(service, root);

		for (int i = 0; i < portList.size(); i++) {
			portTreeNode = new DefaultTreeNode("port", portList.get(i),
					serviceTreeNode);
			/*
			 * for (int j = 0; j < operationList.size(); j++) {
			 * operationTreeNode = new DefaultTreeNode("opt",
			 * operationList.get(j), portTreeNode); for (int k = 0; k <
			 * messagesList.size(); k++) { messageTreeNode = new
			 * DefaultTreeNode("message", messagesList.get(k),
			 * operationTreeNode); } }
			 */
		}
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

		StringBuilder builder = new StringBuilder();
		builder.append(getSelectedNode().getData().toString());
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Selected", builder.toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
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

	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}
}
