package org.wsssoapbox.bean.model.wsdl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

/**
 * @author  Peter
 */
public class InterfaceBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private String prefix;
	private String namespaceURI;
	private String localPart;
	private QName qname;
	private String documentation;
	private List <OperationBean> operations;
	private Map <QName,String> otherAttributes;
	private List<?> ohterElements;
     
	@Override
	public String toString() {
		return  getName();
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
    * @return the prefix
    */
   public String getPrefix() {
      return prefix;
   }

   /**
    * @param prefix the prefix to set
    */
   public void setPrefix(String prefix) {
      this.prefix = prefix;
   }

   /**
    * @return the namespaceURI
    */
   public String getNamespaceURI() {
      return namespaceURI;
   }

   /**
    * @param namespaceURI the namespaceURI to set
    */
   public void setNamespaceURI(String namespaceURI) {
      this.namespaceURI = namespaceURI;
   }

   /**
    * @return the localPart
    */
   public String getLocalPart() {
      return localPart;
   }

   /**
    * @param localPart the localPart to set
    */
   public void setLocalPart(String localPart) {
      this.localPart = localPart;
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
    * @return the operations
    */
   public List <OperationBean> getOperations() {
      return operations;
   }

   /**
    * @param operations the operations to set
    */
   public void setOperations(List <OperationBean> operations) {
      this.operations = operations;
   }

   /**
    * @return the otherAttributes
    */
   public Map <QName,String> getOtherAttributes() {
      return otherAttributes;
   }

   /**
    * @param otherAttributes the otherAttributes to set
    */
   public void setOtherAttributes(Map <QName,String> otherAttributes) {
      this.otherAttributes = otherAttributes;
   }

   /**
    * @return the ohterElements
    */
   public List<?> getOhterElements() {
      return ohterElements;
   }

   /**
    * @param ohterElements the ohterElements to set
    */
   public void setOhterElements(List<?> ohterElements) {
      this.ohterElements = ohterElements;
   }

}
