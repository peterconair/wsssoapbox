package org.wsssoapbox.bean.model.wsdl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.xml.namespace.QName;

/**
 * @author  Peter
 */
@ManagedBean(name = "bindingBean")
@SessionScoped
public class BindingBean implements Serializable {

   private static final long serialVersionUID = 1L;
   private String name;
   private QName qname;
   private String documentation;
   private String httpQueryParameterSeparatorDefault;
   private String httpContentEncodingDefault;
   private String httpDefaultMethod;
   private Enum style;
   private String transportProtocol;
   private String typeOfBinding;
   private String version;
   private List<?> otherElements;
   private Map<QName, String> otherAttributes;
   private InterfaceBean interfaces;
   private List<BindingOperationBean> bindingOperations;

   @Override
   public String toString() {
      return getName();
   }

   /**
    * @return the name
    */
   public String getName() {
      return name;
   }

   /**
    * @param name the name to set
    */
   public void setName(String name) {
      this.name = name;
   }

   /**
    * @return the qname
    */
   public QName getQname() {
      return qname;
   }

   /**
    * @param qname the qname to set
    */
   public void setQname(QName qname) {
      this.qname = qname;
   }

   /**
    * @return the documentation
    */
   public String getDocumentation() {
      return documentation;
   }

   /**
    * @param documentation the documentation to set
    */
   public void setDocumentation(String documentation) {
      this.documentation = documentation;
   }

   /**
    * @return the httpQueryParameterSeparatorDefault
    */
   public String getHttpQueryParameterSeparatorDefault() {
      return httpQueryParameterSeparatorDefault;
   }

   /**
    * @param httpQueryParameterSeparatorDefault the httpQueryParameterSeparatorDefault to set
    */
   public void setHttpQueryParameterSeparatorDefault(String httpQueryParameterSeparatorDefault) {
      this.httpQueryParameterSeparatorDefault = httpQueryParameterSeparatorDefault;
   }

   /**
    * @return the httpContentEncodingDefault
    */
   public String getHttpContentEncodingDefault() {
      return httpContentEncodingDefault;
   }

   /**
    * @param httpContentEncodingDefault the httpContentEncodingDefault to set
    */
   public void setHttpContentEncodingDefault(String httpContentEncodingDefault) {
      this.httpContentEncodingDefault = httpContentEncodingDefault;
   }

   /**
    * @return the httpDefaultMethod
    */
   public String getHttpDefaultMethod() {
      return httpDefaultMethod;
   }

   /**
    * @param httpDefaultMethod the httpDefaultMethod to set
    */
   public void setHttpDefaultMethod(String httpDefaultMethod) {
      this.httpDefaultMethod = httpDefaultMethod;
   }

   /**
    * @return the style
    */
   public Enum getStyle() {
      return style;
   }

   /**
    * @param style the style to set
    */
   public void setStyle(Enum style) {
      this.style = style;
   }

   /**
    * @return the transportProtocol
    */
   public String getTransportProtocol() {
      return transportProtocol;
   }

   /**
    * @param transportProtocol the transportProtocol to set
    */
   public void setTransportProtocol(String transportProtocol) {
      this.transportProtocol = transportProtocol;
   }

   /**
    * @return the typeOfBinding
    */
   public String getTypeOfBinding() {
      return typeOfBinding;
   }

   /**
    * @param typeOfBinding the typeOfBinding to set
    */
   public void setTypeOfBinding(String typeOfBinding) {
      this.typeOfBinding = typeOfBinding;
   }

   /**
    * @return the version
    */
   public String getVersion() {
      return version;
   }

   /**
    * @param version the version to set
    */
   public void setVersion(String version) {
      this.version = version;
   }

   /**
    * @return the otherElements
    */
   public List<?> getOtherElements() {
      return otherElements;
   }

   /**
    * @param otherElements the otherElements to set
    */
   public void setOtherElements(List<?> otherElements) {
      this.otherElements = otherElements;
   }

   /**
    * @return the otherAttributes
    */
   public Map<QName, String> getOtherAttributes() {
      return otherAttributes;
   }

   /**
    * @param otherAttributes the otherAttributes to set
    */
   public void setOtherAttributes(Map<QName, String> otherAttributes) {
      this.otherAttributes = otherAttributes;
   }

   /**
    * @return the interfaces
    */
   public InterfaceBean getInterfaces() {
      return interfaces;
   }

   /**
    * @param interfaces the interfaces to set
    */
   public void setInterfaces(InterfaceBean interfaces) {
      this.interfaces = interfaces;
   }

   /**
    * @return the bindingOperations
    */
   public List<BindingOperationBean> getBindingOperations() {
      return bindingOperations;
   }

   /**
    * @param bindingOperations the bindingOperations to set
    */
   public void setBindingOperations(List<BindingOperationBean> bindingOperations) {
      this.bindingOperations = bindingOperations;
   }
}
