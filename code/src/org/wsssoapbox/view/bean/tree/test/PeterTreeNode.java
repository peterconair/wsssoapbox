package org.wsssoapbox.view.bean.tree.test;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.wsssoapbox.bean.backing.WSDLImportForm;

@ManagedBean(name = "peterBean")
@SessionScoped
public class PeterTreeNode implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Log logger = LogFactory.getLog(PeterTreeNode.class);
	private TreeNode root;
	private TreeNode selectedNode;

	public PeterTreeNode() {
		logger.debug(" Start  PeterTree() ");
		root = new DefaultTreeNode("Root", null);
		TreeNode node0 = new DefaultTreeNode("Peter Node", root);
		TreeNode peter = new DefaultTreeNode("Child of Peter ", node0);
		logger.debug(" End  PeterTree() ");
	}
	public void onNodeSelect(NodeSelectEvent event) {  
		logger.debug(" Start  public void onNodeSelect(NodeSelectEvent evnt) ");
		TreeNode node = event.getTreeNode();
		logger.info("Selected Node : " + node.toString());
		logger.info("Parent Node : " + node.getParent());
		logger.info("Chilren Node : " + node.getChildren());
		
		PeterTestBean peterBean = (PeterTestBean) getSessionMap("peterTestBean");
		peterBean.setName(node.toString());
		
		/*
		TreeNode node0 = new DefaultTreeNode("Peter Node Changed", root);
		TreeNode peter = new DefaultTreeNode("Child of Peter Changed", node0);
		*/
		logger.info("*******************************************");
		logger.info("Root Node for getRoot : " +getRoot().getData());
		logger.info("Parent Node for getRoot : " + getRoot().getParent());
		logger.info("Children Node for getRoot : " + getRoot().getChildren());
		logger.debug(" End  public void onNodeSelect(NodeSelectEvent evnt) ");
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
	public void setSessionMap(String name, Object object) {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		session.setAttribute(name, object);
	}

	public Object getSessionMap(String name) {
		Object object = FacesContext.getCurrentInstance().getExternalContext()
				.getSessionMap().get(name);
		return object;
	}

}
