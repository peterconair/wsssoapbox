package org.wsssoapbox.view.bean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.wsssoapbox.bean.model.soap.SoapBodyBean;
import org.wsssoapbox.bean.model.soap.SoapEnvelopeBean;
import org.wsssoapbox.bean.model.soap.SoapFaultBean;
import org.wsssoapbox.bean.model.soap.SoapHeaderBean;
import org.wsssoapbox.bean.model.soap.SoapRequestBean;
import org.wsssoapbox.bean.model.soap.SoapOperationBean;
import org.wsssoapbox.bean.model.soap.SoapParameterBean;
import org.wsssoapbox.bean.model.soap.SoapMessageBean;
import org.wsssoapbox.bean.model.soap.SoapResponseBean;
import org.wsssoapbox.bean.util.ScopeController;

@ManagedBean(name="treeTableResponseBean")
@RequestScoped

public class TreeTableResponseBean {
	
	SoapEnvelopeBean soapEnvelopeBean;
	SoapHeaderBean soapHeaderBean;
	SoapBodyBean soapBodyBean;
	SoapOperationBean soapOperationBean;
	List<SoapBodyBean> soapBodyBeanEntryList;
	SoapFaultBean soapFautlBean;
	List<SoapFaultBean> soapFaultBeanList;
	List<SoapParameterBean> soapParameterBeanList;
	TreeTableResponseBean treeTableBean;

	private static final Log logger = LogFactory.getLog(TreeTableResponseBean.class);
	private TreeNode root;

	public TreeTableResponseBean() {
		
		logger.debug("start public TreeTableResponseBean()");
		root = new DefaultTreeNode("root", null);
		
		SoapResponseBean soapRequestBean = new SoapResponseBean();
		soapRequestBean = (SoapResponseBean) ScopeController.getSessionMap("soapResponseBean");

		if (soapRequestBean != null) {
			soapEnvelopeBean = soapRequestBean.getSoapPart().getSoapEnvelope();
			soapHeaderBean = soapRequestBean.getSoapPart().getSoapEnvelope().getSoapHeader();
			soapBodyBean = soapRequestBean.getSoapPart().getSoapEnvelope().getSoapBody();
			soapOperationBean = soapRequestBean.getSoapPart().getSoapEnvelope().getSoapBody().getOperation();
			soapFautlBean = soapRequestBean.getSoapPart().getSoapEnvelope().getSoapBody().getFaultBean();
			soapParameterBeanList = soapRequestBean.getSoapPart().getSoapEnvelope().getSoapBody().getOperation().getParameters();
		}
		
		logger.debug("Tag name : " + soapOperationBean.getTagName());		
		root = constructTreeTable();
		
		logger.debug("end public TreeTableResponseBean()");
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}

	public TreeNode getRoot() {
		return root;
	}

	public TreeNode constructTreeTable() {

		logger.debug("**********************  constructTreeTable ************************ ");
		TreeNode root = new DefaultTreeNode("root", null);
		TreeNode envelopTreeNode = new DefaultTreeNode("envelope", soapEnvelopeBean, root);

		logger.debug(" Soap Envelope : " + envelopTreeNode.getData());

		if (soapHeaderBean != null) {
			TreeNode headerTreeNode = new DefaultTreeNode("header", soapHeaderBean, envelopTreeNode);
			logger.debug(" Soap Header : " + headerTreeNode.getData());
		}

		TreeNode bodyTreeNode = new DefaultTreeNode("body", soapBodyBean, envelopTreeNode);
		logger.debug(" Soap Body : " + bodyTreeNode.getData());
		TreeNode operationTreeNode = new DefaultTreeNode("operation", soapOperationBean, bodyTreeNode);
		
		logger.debug(" Soap Operation : " + operationTreeNode.getData()  +": Operation Name(); "+soapOperationBean.getTagName());
		for (SoapParameterBean pb : soapParameterBeanList) {
			TreeNode parameterTreeNode = new DefaultTreeNode("parameter", pb, operationTreeNode);
			logger.debug(" Soap Parameter : " + parameterTreeNode.getData() +": Parameter TagName :" +pb.getTagName());
		}

		logger.debug("********************** end constructTreeTable ************************ ");
		return root;

	}
}
