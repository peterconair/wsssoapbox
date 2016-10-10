package org.wsssoapbox.bean.model.wsdl;

import java.io.Serializable;

public class ResponseBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String name;
	private String type;

	public ResponseBean(String name, String type) {
		this.name = name;
		this.type = type;
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
    * @return the type
    */
   public String getType() {
      return type;
   }

   /**
    * @param type the type to set
    */
   public void setType(String type) {
      this.type = type;
   }



}
