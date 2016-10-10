package org.wsssoapbox.bean.backing.wsdl;

import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.ow2.easywsdl.wsdl.api.Description;
import org.ow2.easywsdl.wsdl.api.WSDLException;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.wsssoapbox.bean.backing.PageViewController;
import org.wsssoapbox.datasource.wsdl.WSDLDataSource;
import org.wsssoapbox.bean.backing.TreeBean;
import org.wsssoapbox.bean.model.wsdl.BindingOperationBean;
import org.wsssoapbox.bean.model.wsdl.DescriptionBean;
import org.wsssoapbox.bean.model.wsdl.EndpointBean;
import org.wsssoapbox.bean.model.wsdl.ServicesBean;
import org.wsssoapbox.dao.wsdl.IWSDLDAO;
import org.wsssoapbox.dao.wsdl.WSDLDAO;
import org.wsssoapbox.database.User;
import org.wsssoapbox.view.util.ScopeController;

public class WSDLImportForm implements Serializable {

   private static final long serialVersionUID = 1L;
   private static final Logger logger = LoggerFactory.getLogger(WSDLImportForm.class);
   private String url;
   private ServicesBean serviceBean;
   private DescriptionBean wsdlDucument;
   private Description desc;
   private String wsdlFile;
   private boolean downloadButton;

   public WSDLImportForm() {

      logger.debug(">>>>>>>>>>>>>>>> Stat public WSDLImportForm() ");

      logger.debug(">>>>>>>>>>>>>>>> End public WSDLImportForm() <<<<<<<<<<<<<<<<<<<<<");
   }

   public void initial() {
      logger.debug(">>>>>>>>>>>>>>>> Start initial ");
      downloadButton = false;
      wsdlDucument = new DescriptionBean();
      serviceBean = new ServicesBean();
      logger.debug(">>>>>>>>>>>>>>>> End initial <<<<<<<<<<<<<<<<<<<<<");
   }

   public String importWSDL() {
      logger.debug(">>>>>>>>>>>>>>>> Strat submit(ActionEvent event)");

      initial();
      User user = (User) ScopeController.getSessionMap("user");
      MDC.put("user", "" + user.getId());

      // WSDLDataSource wsdlDataSource = null;

      try {
         // wsdlDataSource = new WSDLDataSource(url);
         // this.reader = wsdlDocumentDataSource.getReader();
         this.desc = WSDLDataSource.getDesc(url);

         ScopeController.setSessionMap("desc", desc);
         downloadButton = true;

         IWSDLDAO wsdlDAO = new WSDLDAO();
         wsdlDucument = wsdlDAO.getWSDLDocument(desc);
         ScopeController.setSessionMap("wsdlDucument", wsdlDucument);

         // Get TestTreeBean from seesion
         TreeBean treeBean = (TreeBean) ScopeController.getSessionMap("treeBean");


         // set root node for tree node.
         treeBean.setRoot(constructTreeNode());
         //Generate view WSDL file 
         WSDLViewer wsdlViewer = new WSDLViewer();

         logger.debug("User sessin id : " + user.getId());
         logger.debug("WSDL Viewer Generating...........");
         wsdlViewer.genterateHTML(url, user);
         wsdlFile = wsdlViewer.getHtmlOutputURL();
         logger.debug("WSDL Viewer Generated...........");

         // Get Page Controller from session
         PageViewController pageContoller = (PageViewController) ScopeController.getSessionMap("pageViewController");
         pageContoller.setIncludePanel(true);
         pageContoller.setOverviewPanel(true);
         //show navigator tree layout in ui.xhtml page
         pageContoller.setNavigator(true);
         pageContoller.setNavigatorCollapse(false);

         logger.debug("Navigator : " + pageContoller.isNavigator());
         logger.debug("Navigator Collapse : " + pageContoller.isNavigatorCollapse());

         // Get request context object
         RequestContext rc = RequestContext.getCurrentInstance();
         // update tree menu
         rc.addPartialUpdateTarget("treeForm:treePanel");
         logger.debug(">>>>>>>>>>>>>>>>> End submit(ActionEvent event) <<<<<<<<<<<<<<<<<<");

         return "/wsdl/importwsdl?faces-redirect=true";

      } catch (MalformedURLException ex) {
         logger.error("MalformedURLException :" + ex.getMessage(), ex);
         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Malformed URL ", ex.getMessage());
         FacesContext.getCurrentInstance().addMessage(null, msg);
         ex.printStackTrace();
      } catch (URISyntaxException ex) {
         logger.error("URISyntaxException :" + ex.getMessage(), ex);
         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "URI Syntax ", ex.getMessage());
         FacesContext.getCurrentInstance().addMessage(null, msg);
         ex.printStackTrace();
      } catch (IOException ex) {
         logger.error("IOException :" + ex.getMessage(), ex);
         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unknown Host ", ex.getMessage());
         FacesContext.getCurrentInstance().addMessage(null, msg);
         ex.printStackTrace();
      } catch (WSDLException ex) {
         logger.error("WSDLException :" + ex.getMessage(), ex);
         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "WSDL Exception", ex.getMessage());
         FacesContext.getCurrentInstance().addMessage(null, msg);
         // refresh page
         ex.printStackTrace();
      } catch (StringIndexOutOfBoundsException ex) {
         logger.error("StringIndexOutOfBoundsException :" + ex.getMessage(), ex);
         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error ", ex.getMessage());
         FacesContext.getCurrentInstance().addMessage(null, msg);
         ex.printStackTrace();
      } catch (Exception ex) {
         logger.error("Exception :" + ex.getMessage(), ex);
         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error ", ex.getMessage());
         FacesContext.getCurrentInstance().addMessage(null, msg);
         ex.printStackTrace();
      }
      return null;
   }

   public TreeNode constructTreeNode() {
      logger.debug("**********************  constructTreeNode ************************ ");

      //  serviceBean = wsdlDucument.getServices().get(0);
      ServicesBean sBean = wsdlDucument.getServices().get(0);
      ScopeController.setSessionMap("serviceBean", sBean);

      //   List<ServicesBean> serviceBeanList = wsdlDucument.getServices();
      //   List<BindingBean> bindingBeanList = wsdlDucument.getBindings();
      List<EndpointBean> endpointBeanList = sBean.getEndpoints();

      TreeNode root = new DefaultTreeNode("root", null);
      TreeNode wsdlTreeNode = new DefaultTreeNode("wsdldoc", wsdlDucument, root);

      TreeNode serviceTreeNode = new DefaultTreeNode("service", sBean, wsdlTreeNode);

      logger.info("WSDL Url : " + wsdlDucument.getDocumentBaseURI());
      logger.info("WSDL Target Namespace : " + wsdlDucument.getTargetNamespace());
      logger.info("Service Name : " + sBean.getName());
      logger.info("Endpont Count: " + endpointBeanList.size());

      for (EndpointBean eb : endpointBeanList) {
         logger.info("Endpont Name: " + eb.getName());
         TreeNode endpointTreeNode = new DefaultTreeNode("endpoint", eb, serviceTreeNode);

         logger.info("Operaiton Count: " + eb.getBinding().getBindingOperations().size());
         for (BindingOperationBean bob : eb.getBinding().getBindingOperations()) {
            logger.info("Operation Name : " + bob.getName());
            TreeNode operationTreeNode = new DefaultTreeNode("operation", bob, endpointTreeNode);
         }
      }
      logger.debug("********************** end constructTreeNode ************************ ");
      return root;

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

   public ServicesBean getService() {
      return serviceBean;
   }

   public void setServiceList(ServicesBean service) {
      this.serviceBean = service;
   }

   /*   
   public List<OperationBean> getOperationList() {
   return operationBeanList;
   }
   
   public void setOperationList(List<OperationBean> operationList) {
   this.operationBeanList = operationList;
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
   
   
   public List<ServicesBean> getServiceBeanList() {
   return serviceBeanList;
   }
   
   public void setServiceBeanList(List<ServicesBean> serviceBeanList) {
   this.serviceBeanList = serviceBeanList;
   }
   
   
   public List<BindingBean> getBindingBeanList() {
   return bindingBeanList;
   }
   
   public void setBindingBeanList(List<BindingBean> bindingBeanList) {
   this.bindingBeanList = bindingBeanList;
   }   
    */
   public boolean isDownloadButton() {
      return downloadButton;
   }

   public void setDownloadButton(boolean downloadButton) {
      this.downloadButton = downloadButton;
   }

   /**
    * @return the wsdlFile
    */
   public String getWsdlFile() {
      return wsdlFile;
   }

   /**
    * @param wsdlFile the wsdlFile to set
    */
   public void setWsdlFile(String wsdlFile) {
      this.wsdlFile = wsdlFile;
   }
   /**
    * @return the wsdlViewFile
    */
}
