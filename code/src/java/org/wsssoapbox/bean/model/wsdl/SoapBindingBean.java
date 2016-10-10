package org.wsssoapbox.bean.model.wsdl;

import java.io.Serializable;
import java.util.Map;
import org.wsssoapbox.bean.model.soap.SoapFaultBean;
import org.wsssoapbox.bean.model.soap.SoapOperationBean;
import org.wsssoapbox.soap.support.SoapConstants;

// this class prepare data to create soap message
/**
 * @author  Peter
 */
public class SoapBindingBean implements Serializable {

   /**
    * 
    */
   private static final long serialVersionUID = 1L;
   private String namespacePrefix = SoapConstants.PREFIX_DEFAULT_VALUE;
   private String nameSpaceURI = "";
   private String elementFormDefault = "QUALIFIED";
   private boolean header;
   private boolean fault;
   private boolean xmlStandalone;
   private String style;
   private String use;
   private Map<String, String> namespaces;
   private SoapOperationBean operation;
   private SoapFaultBean soapFault;
   private Map<String, String> schemas;
   private String soapXMLRequest;
   private String soapRawRequest;
   private String soapVersion;
   private String soapAction;

   public SoapBindingBean() {
   }

   /*
   public SoapBindingBean(String namespacePrefix, String nameSpaceURI, boolean header, String soapVersion, String soapAction, Map<String, String> namespaces,
           SoapOperationBean operation, Map<String, String> schemas) {
      this.namespacePrefix = namespacePrefix;
      this.nameSpaceURI = nameSpaceURI;
      this.header = header;
      this.soapVersion = soapVersion;
      this.soapAction = soapAction;
      this.namespaces = namespaces;
      this.operation = operation;
      this.schemas = schemas;

   }
*/

   public Map<String, String> getSchemas() {
      return schemas;
   }

   public void setSchemas(Map<String, String> schemas) {
      this.schemas = schemas;
   }

   public String getNamespacePrefix() {
      return namespacePrefix;
   }

   public void setNamespacePrefix(String namespacePrefix) {
      this.namespacePrefix = namespacePrefix;
   }

   public String getNameSpaceURI() {
      return nameSpaceURI;
   }

   public void setNameSpaceURI(String nameSpaceURI) {
      this.nameSpaceURI = nameSpaceURI;
   }

   public Map<String, String> getNamespaces() {
      return namespaces;
   }

   public void setNamespaces(Map<String, String> namespaces) {
      this.namespaces = namespaces;
   }

   public SoapOperationBean getOperation() {
      return operation;
   }

   public void setOperation(SoapOperationBean operation) {
      this.operation = operation;
   }

   public boolean isHeader() {
      return header;
   }

   public void setHeader(boolean header) {
      this.header = header;
   }

   public boolean isXmlStandalone() {
      return xmlStandalone;
   }

   public void setXmlStandalone(boolean xmlStandalone) {
      this.xmlStandalone = xmlStandalone;
   }

   public boolean isFault() {
      return fault;
   }

   public void setFault(boolean fault) {
      this.fault = fault;
   }

   public String getStyle() {
      return style;
   }

   public void setStyle(String style) {
      this.style = style;
   }

   public String getUse() {
      return use;
   }

   public void setUse(String use) {
      this.use = use;
   }

   public String getSoapXMLRequest() {
      return soapXMLRequest;
   }

   public void setSoapXMLRequest(String soapXMLRequest) {
      this.soapXMLRequest = soapXMLRequest;
   }

   public String getSoapRawRequest() {
      return soapRawRequest;
   }

   public void setSoapRawRequest(String soapRawRequest) {
      this.soapRawRequest = soapRawRequest;
   }

   public SoapFaultBean getSoapFault() {
      return soapFault;
   }

   public void setSoapFault(SoapFaultBean soapFault) {
      this.soapFault = soapFault;
   }

   public String getSoapAction() {
      return soapAction;
   }

   public void setSoapAction(String soapAction) {
      this.soapAction = soapAction;
   }

   public String getSoapVersion() {
      return soapVersion;
   }

   public void setSoapVersion(String soapVersion) {
      this.soapVersion = soapVersion;
   }

   public String getElementFormDefault() {
      return elementFormDefault;
   }

   public void setElementFormDefault(String elementFormDefault) {
      this.elementFormDefault = elementFormDefault;
   }
}
