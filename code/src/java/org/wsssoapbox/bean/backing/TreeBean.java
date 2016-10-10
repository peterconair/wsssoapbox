package org.wsssoapbox.bean.backing;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;


import org.slf4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.slf4j.LoggerFactory;
import org.wsssoapbox.bean.model.wsdl.BindingBean;
import org.wsssoapbox.bean.model.wsdl.BindingOperationBean;
import org.wsssoapbox.bean.model.wsdl.DescriptionBean;
import org.wsssoapbox.bean.model.wsdl.EndpointBean;
import org.wsssoapbox.bean.model.wsdl.InterfaceBean;
import org.wsssoapbox.bean.model.wsdl.OperationBean;
import org.wsssoapbox.bean.model.wsdl.ServicesBean;
import org.wsssoapbox.view.util.ScopeController;

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
   private static final Logger logger = LoggerFactory.getLogger(TreeBean.class);

   public TreeBean() {
      logger.debug(" >>>>>>>>>>>>>>> Start public TreeBean() ");
      root = new DefaultTreeNode("root", null);
      page = "/wsdl/documentdetails.xhtml";
      logger.debug(">>>>>>>>>>>>>>> End public TreeBean() <<<<<<<<<<<<<<<<<<<");
   }

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

      logger.info("Node selected : " + selectedNodeValue);
      logger.debug("Parent Of ParentNode Value : " + parentOfParentNodeValue);
      logger.debug("Parent Node selected : " + parentNodeValue);
      logger.debug("Sibling  Node count  : " + childCount);
      for (TreeNode n : sibling) {
         logger.debug("Sibling  Node of " + selectedNodeValue + " :Node Selected " + n.getData().toString());
      }

      // Get Page Controller from session
      PageViewController pageContoller = (PageViewController) ScopeController.getSessionMap("pageViewController");
      pageContoller.setOverviewPanel(true);

      DescriptionBean wsdlDucument = new DescriptionBean();
      wsdlDucument = (DescriptionBean) ScopeController.getSessionMap("wsdlDucument");


      if (wsdlDucument != null) {

         // check WSDL document node
         if (nodeType.equals("wsdldoc")) {
            page = "/wsdl/documentdetails.xhtml";

         } else if (nodeType.equals("service")) {
            page = "/wsdl/servicesdetails.xhtml";
            List<ServicesBean> serviceBeanList = new ArrayList<ServicesBean>();
            ServicesBean serviceBean = new ServicesBean();
            serviceBeanList = wsdlDucument.getServices();

            for (ServicesBean sb : serviceBeanList) {
               if (sb.getName().equals(serviceName)) {
                  serviceBean = sb;
               }
            }

            InterfaceBean interfaceBean = new InterfaceBean();
            interfaceBean = serviceBean.getInterfaceType();
            logger.debug("interface QName   : " + interfaceBean.getQname());

            ScopeController.setSessionMap("serviceBean", serviceBean);
            ScopeController.setSessionMap("interfaceBean", interfaceBean);

            // check Binding node
         } else if (nodeType.equals("endpoint")) {
            page = "/wsdl/bindingdetails.xhtml";
            ServicesBean serviceBean = new ServicesBean();
            
         //   serviceBean = (ServicesBean) ScopeController.getSessionMap("serviceBean");
            
           serviceBean =  (ServicesBean) wsdlDucument.getServices().get(0);
            
            List<EndpointBean> endpointBeanList = new ArrayList<EndpointBean>();
            endpointBeanList = serviceBean.getEndpoints();

            EndpointBean endpointBean = new EndpointBean();

            if (endpointName != null) {
               logger.debug("Endpoint Name  : " + endpointName);
               for (EndpointBean eb : endpointBeanList) {
                  if (eb.getName().equals(endpointName)) {
                     endpointBean = eb;
                  }
               }
            }
            ScopeController.setSessionMap("endpointBean", endpointBean);

            String bindingName = endpointBean.getBinding().getName();
            List<BindingBean> bindingBeanList = new ArrayList<BindingBean>();
            bindingBeanList = wsdlDucument.getBindings();

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
            ScopeController.setSessionMap("bindingBean", bindingBean);

            InterfaceBean interfaceBean = new InterfaceBean();
            interfaceBean = bindingBean.getInterfaces();

            logger.debug("interface QName  : " + interfaceBean.getQname());
            ScopeController.setSessionMap("interfaceBean", interfaceBean);

            // check Operation node
         } else if (nodeType.equals("operation")) {

            page = "/wsdl/operationdetails.xhtml";

            ServicesBean serviceBean = new ServicesBean();
            serviceBean = (ServicesBean) ScopeController.getSessionMap("serviceBean");

            if (parentOfParentNodeValue != null || parentOfParentNodeValue.equals("")) {
               if (serviceBean == null) {
                  for (ServicesBean sb : wsdlDucument.getServices()) {
                     if (sb.getName().equals(parentOfParentNodeValue)) {
                        serviceBean = sb;
                     }
                  }
               }
            }

            logger.info(" ParentNode Value : " + parentNodeValue);
            logger.info(" Parent Of ParentNode Value : " + parentOfParentNodeValue);
            logger.info(" Services Bean : " + serviceBean.getName());

            List<EndpointBean> endpointBeanList = new ArrayList<EndpointBean>();
            endpointBeanList = serviceBean.getEndpoints();

            EndpointBean endpointBean = new EndpointBean();

            if (parentNodeValue != null) {
               for (EndpointBean eb : endpointBeanList) {
                  logger.debug("Endpoint Name : "+ eb.getName());
                  if (eb.getName().equals(parentNodeValue)) {
                     endpointBean = eb;
                  }
               }
            }
            logger.info(" endpoint Bean : " + endpointBean.getName());
            ScopeController.setSessionMap("endpointBean", endpointBean);

            List<OperationBean> operationBeanList = new ArrayList<OperationBean>();
            operationBeanList = serviceBean.getInterfaceType().getOperations();

            OperationBean operationBean = new OperationBean();
            if (operationName != null) {
               logger.debug("Operation Name  : " + operationName);
               for (OperationBean ob : operationBeanList) {
                  if (ob.getName().equals(operationName)) {
                     operationBean = ob;
                  }
               }
               // enable the createSoap button in operationdetails.xhtml 
               pageContoller.setCreateSoapButton(false);
            } else {
               // enable the createSoap button in operationdetails.xhtml 
               pageContoller.setCreateSoapButton(true);
            }

            ScopeController.setSessionMap("operationName", operationName);
            ScopeController.setSessionMap("operationBean", operationBean);

            List<BindingOperationBean> bindingOperationBeanList = new ArrayList<BindingOperationBean>();
            bindingOperationBeanList = serviceBean.getEndpoints().get(0).getBinding().getBindingOperations();

            BindingOperationBean bindingOperationBean = new BindingOperationBean();

            if (operationName != null) {
               logger.debug("Operation Name : " + operationName);
               for (BindingOperationBean bob : bindingOperationBeanList) {
                  if (bob.getName().equals(operationName)) {
                     bindingOperationBean = bob;
                  }
               }
            }
            ScopeController.setSessionMap("bindingOperationBean", bindingOperationBean);

            logger.debug("Endpoint Name : " + endpointBean.getName());
            logger.debug("Endpoint Address : " + endpointBean.getAddress());
            logger.debug("Endpoint Http Authentication Realm : " + endpointBean.getHttpAuthenticationRealm());

            BindingBean bindingBean = endpointBean.getBinding();

            logger.info("Binding Bean : " + bindingBean.getName());
            ScopeController.setSessionMap("bindingBean", bindingBean);

            InterfaceBean interfaceBean = new InterfaceBean();
            interfaceBean = bindingBean.getInterfaces();

            logger.debug("interface QName  : " + interfaceBean.getQname());
            ScopeController.setSessionMap("interfaceBean", interfaceBean);

            List<ServicesBean> serviceBeanList = new ArrayList<ServicesBean>();
            serviceBeanList = wsdlDucument.getServices();

            if (parentOfParentNodeValue != null) {

               for (ServicesBean sb : serviceBeanList) {
                  if (sb.getName().equals(parentOfParentNodeValue)) {
                     serviceBean = sb;
                  }
               }
            }
            ScopeController.setSessionMap("serviceBean", serviceBean);
            logger.debug("serviceBean  : " + serviceBean.getName());
         }

         logger.info("Page to displayed : " + page);
         RequestContext rc = RequestContext.getCurrentInstance();
         rc.addPartialUpdateTarget("importForm:includePanel");

         logger.debug(">>>>>>>>>>>>>>>>> End public void onSelectEven(NodeSelectEvent event) ");

      }
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
}
