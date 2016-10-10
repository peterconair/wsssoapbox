package org.wsssoapbox.view.bean.tree.test;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

@ManagedBean(name = "peterTestBean")
@SessionScoped
public class PeterTestBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	TreeNode root;

	public PeterTestBean() {

	}

	public void submit(ActionEvent event) {
		System.out.println("You click Submit Button ");
		PeterTreeNode treeNode = (PeterTreeNode)getSessionMap("peterBean");
		System.out.println("getRoot().getParent() " +treeNode.getRoot().getParent());
		System.out.println("getRoot().getChildren() " +treeNode.getRoot().getChildren());
		System.out.println("getRoot() " +treeNode.getRoot());
		
		
		TreeNode root = new DefaultTreeNode("Root", null);
		TreeNode node0 = new DefaultTreeNode("Java", root);
		TreeNode peter = new DefaultTreeNode("JSP ", node0);
		TreeNode peter1 = new DefaultTreeNode("JSF ", node0);
		treeNode.setRoot(root);
		
		RequestContext rc = RequestContext.getCurrentInstance();
		//Update form
		//rc.addPartialUpdateTarget("peterForm");
		//Update tree
		//rc.addPartialUpdateTarget("peterTree");
		//Update div
		rc.addPartialUpdateTarget("peterForm:treePanel");
		
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TreeNode getRoot() {
		return root;
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}
}
