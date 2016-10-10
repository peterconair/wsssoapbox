/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.bean.backing.logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.faces.view.facelets.FaceletContext;
import org.primefaces.model.LazyDataModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wsssoapbox.database.User;
import org.wsssoapbox.database.logger.UserLog;
import org.wsssoapbox.services.logger.UserLogService;
import org.wsssoapbox.services.logger.UserLogServiceImpl;
import org.wsssoapbox.view.util.ScopeController;

/**
 *
 * @author Peter
 */
@ManagedBean(name = "userLogForm")
@ViewScoped
public class UserLogForm implements Serializable {

   private static final long serialVersionUID = -9023755028656182522L;
   private static final Logger logger = LoggerFactory.getLogger(UserLogForm.class);
   private List<UserLog> userLogs = new ArrayList<UserLog>();
   private SelectItem[] levelOptions;
   private final static String[] levels;
   private UserLog[] selectedLogs;
   private UserLogService userLogService;
   private UserLogDataModel userLogDataModel;

   static {
      levels = new String[6];
      levels[0] = "TRACE";
      levels[1] = "DEBUG";
      levels[2] = "INFO";
      levels[3] = "WARN";
      levels[4] = "ERROR";
      levels[5] = "FATAL";
   }

   public UserLogForm() {
      levelOptions = createFilterOptions(levels);
   }

   @PostConstruct
   public void init() {
      User user = (User) ScopeController.getSessionMap("user");
      userLogService = new UserLogServiceImpl();
      userLogs = new ArrayList<UserLog>();
      if (user != null) {
         logger.debug("User : " + user.getUsername());
         userLogs = userLogService.query(user);
         userLogDataModel = new UserLogDataModel(userLogs);
         logger.debug("List of user log : " + userLogs.size());
      }
   }

   public void deleteRows() {
      UserLogService userLogSerive = new UserLogServiceImpl();

      try {
         int a = selectedLogs.length;
         if (a > 0) {
            logger.debug(a + " rows deleted ");
            userLogSerive.delete(Arrays.asList(selectedLogs));
            //clear selected value
            selectedLogs = new UserLog[0];
            // refresh data 
            refreshLog();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", a + " rows deleted ");
            FacesContext.getCurrentInstance().addMessage(null, msg);
         } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "You must select at least one row to perform this action.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
         }
      } catch (NullPointerException ex) {
         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "You must select at least one row to perform this action.");
         FacesContext.getCurrentInstance().addMessage(null, msg);
         ex.printStackTrace();
      } 
   }

   public void refreshLog() {
      User user = (User) ScopeController.getSessionMap("user");
      userLogService = new UserLogServiceImpl();
      userLogs = new ArrayList<UserLog>();
      if (user != null) {
         userLogs = userLogService.query(user);
         userLogDataModel = new UserLogDataModel(userLogs);
         logger.debug("Refresh all data" + userLogs.size() + " records");
         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Found " + userLogs.size() + ".");
         FacesContext.getCurrentInstance().addMessage(null, msg);
      }
   }

   private SelectItem[] createFilterOptions(String[] data) {
      SelectItem[] options = new SelectItem[data.length + 1];

      options[0] = new SelectItem("", "Select");
      for (int i = 0; i < data.length; i++) {
         options[i + 1] = new SelectItem(data[i], data[i]);
      }
      return options;
   }


   /**
    * @return the userLogs
    */
   public List<UserLog> getUserLogs() {
      return userLogs;
   }

   /**
    * @param userLogs the userLogs to set
    */
   public void setUserLogs(List<UserLog> userLogs) {
      this.userLogs = userLogs;
   }

   /**
    * @return the userLogDataModel
    */
   public UserLogDataModel getUserLogDataModel() {
      return userLogDataModel;
   }

   /**
    * @param userLogDataModel the userLogDataModel to set
    */
   public void setUserLogDataModel(UserLogDataModel userLogDataModel) {
      this.userLogDataModel = userLogDataModel;
   }

   /**
    * @return the selectedLogs
    */
   public UserLog[] getSelectedLogs() {
      return selectedLogs;
   }

   /**
    * @param selectedLogs the selectedLogs to set
    */
   public void setSelectedLogs(UserLog[] selectedLogs) {
      this.selectedLogs = selectedLogs;
   }

   /**
    * @return the levelOptions
    */
   public SelectItem[] getLevelOptions() {
      return levelOptions;
   }
}
