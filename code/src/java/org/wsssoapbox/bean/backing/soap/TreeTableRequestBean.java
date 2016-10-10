package org.wsssoapbox.bean.backing.soap;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.slf4j.Logger;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.slf4j.LoggerFactory;
import org.wsssoapbox.bean.model.soap.SoapBodyBean;
import org.wsssoapbox.bean.model.soap.SoapEnvelopeBean;
import org.wsssoapbox.bean.model.soap.SoapFaultBean;
import org.wsssoapbox.bean.model.soap.SoapHeaderBean;
import org.wsssoapbox.bean.model.soap.SoapRequestBean;
import org.wsssoapbox.bean.model.soap.SoapOperationBean;
import org.wsssoapbox.bean.model.soap.SoapParameterBean;
import org.wsssoapbox.view.util.ScopeController;

@ManagedBean(name = "treeTableRequestBean")
@RequestScoped
public class TreeTableRequestBean implements Serializable{
   private static final long serialVersionUID = -8663488970565023074L;

   SoapEnvelopeBean soapEnvelopeBean;
   SoapHeaderBean soapHeaderBean;
   SoapBodyBean soapBodyBean;
   SoapOperationBean soapOperationBean;
   List<SoapBodyBean> soapBodyBeanEntryList;
   SoapFaultBean soapFautlBean;
   List<SoapFaultBean> soapFaultBeanList;
   List<SoapParameterBean> soapParameterBeanList;
   TreeTableRequestBean treeTableBean;
   private static final Logger logger = LoggerFactory.getLogger(TreeTableRequestBean.class);
   private TreeNode root;

   public TreeTableRequestBean() {

      logger.debug("start public TreeTableRequestBean()");
      root = new DefaultTreeNode("root", null);


      root = constructTreeTable();

      logger.debug("end public TreeTableRequestBean()");
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

      SoapRequestBean soapRequestBean = new SoapRequestBean();
      soapRequestBean = (SoapRequestBean) ScopeController.getSessionMap("soapRequestBean");

      if (soapRequestBean != null) {
         soapEnvelopeBean = soapRequestBean.getSoapPart().getSoapEnvelope();
         soapHeaderBean = soapRequestBean.getSoapPart().getSoapEnvelope().getSoapHeader();
         soapBodyBean = soapRequestBean.getSoapPart().getSoapEnvelope().getSoapBody();
         soapOperationBean = soapRequestBean.getSoapPart().getSoapEnvelope().getSoapBody().getOperation();
         soapFautlBean = soapRequestBean.getSoapPart().getSoapEnvelope().getSoapBody().getFaultBean();
         soapParameterBeanList = soapRequestBean.getSoapPart().getSoapEnvelope().getSoapBody().getOperation().getParameters();
      }

      logger.debug("Tag name : " + soapOperationBean.getTagName());

      logger.debug(" Soap Envelope : " + envelopTreeNode.getData());

      if (soapHeaderBean != null) {
         TreeNode headerTreeNode = new DefaultTreeNode("header", soapHeaderBean, envelopTreeNode);
         logger.debug(" Soap Header : " + headerTreeNode.getData());
      }

      TreeNode bodyTreeNode = new DefaultTreeNode("body", soapBodyBean, envelopTreeNode);
      logger.debug(" Soap Body : " + bodyTreeNode.getData());
      TreeNode operationTreeNode = new DefaultTreeNode("operation", soapOperationBean, bodyTreeNode);

      logger.debug(" Soap Operation : " + operationTreeNode.getData() + ": Operation Name(); " + soapOperationBean.getTagName());
      for (SoapParameterBean pb : soapParameterBeanList) {
         TreeNode parameterTreeNode = new DefaultTreeNode("parameter", pb, operationTreeNode);
         logger.debug(" Soap Parameter : " + parameterTreeNode.getData() + ": Parameter TagName :" + pb.getTagName());
      }

      logger.debug("********************** end constructTreeTable ************************ ");
      return root;

   }
}
