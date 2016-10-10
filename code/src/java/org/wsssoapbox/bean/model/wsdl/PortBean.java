package org.wsssoapbox.bean.model.wsdl;

import java.io.Serializable;
import java.util.List;

/**
 * @author  Peter
 */
public class PortBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private String prefix;
	private String namespaceURI;
	private String localPart;
	private String name;
     private Object operation ;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
		result = prime * result
				+ ((getOperation() == null) ? 0 : getOperation().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PortBean other = (PortBean) obj;
		if (getName() == null) {
			if (other.getName() != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (getOperation() == null) {
			if (other.getOperation() != null)
				return false;
		} else if (!operation.equals(other.operation))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return  getName();
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
    * @return the operation
    */
   public Object getOperation() {
      return operation;
   }

   /**
    * @param operation the operation to set
    */
   public void setOperation(Object operation) {
      this.operation = operation;
   }
}
