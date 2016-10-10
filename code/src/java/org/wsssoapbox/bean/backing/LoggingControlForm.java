/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.bean.backing;

import java.io.Serializable;
import java.util.HashMap;

import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Peter
 */
@ManagedBean(name = "loggingControlForm")
@SessionScoped
public class LoggingControlForm implements Serializable {
   private static final long serialVersionUID = 1L;
   
   private Map<String, String> loggings;
   private String logging;
   private static final Logger logger = LoggerFactory.getLogger(LoggingControlForm.class);

   public LoggingControlForm() {
      loggings = new HashMap<String, String>();
      loggings.put("INFO", "INFO");
      loggings.put("DEBUG", "DEBUG");
      loggings.put("ERROR", "ERROR");
   }
   
   public void handleSelect(String name) {
      // update to database
      logger.debug("You selected : " + name);
      logging = name;
      FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected:" + name, null);     
      FacesContext.getCurrentInstance().addMessage(null, message);
      
   }

   /**
    * @return the loggings
    */
   public Map<String, String> getLoggings() {
      return loggings;
   }

   /**
    * @param loggings the loggings to set
    */
   public void setLoggings(Map<String, String> loggings) {
      this.loggings = loggings;
   }

   /**
    * @return the logging
    */
   public String getLogging() {
      return logging;
   }

   /**
    * @param logging the logging to set
    */
   public void setLogging(String logging) {
      this.logging = logging;
   }
}
