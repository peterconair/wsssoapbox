/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.bean.backing.auth;

import java.io.IOException;
import java.io.Serializable;
import java.security.Principal;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wsssoapbox.database.User;
import org.wsssoapbox.database.UserDAO;
import org.wsssoapbox.database.UserDAOImpl;
import org.wsssoapbox.view.util.ScopeController;

/**
 *
 * @author Peter
 */
@ManagedBean(name = "authForm")
@SessionScoped
public class AuthenticationForm implements  Serializable {

   private static final long serialVersionUID = -1622707425006425150L;
   private static final Logger logger = LoggerFactory.getLogger(AuthenticationForm.class);
   private User user;
   private String username;
   private String password;
   private UserDAO userDAO;

   public AuthenticationForm() {
      //  user = new User();
   }

   public String login() throws IOException {
      logger.debug("start   public String login()");
      FacesContext context = FacesContext.getCurrentInstance();
      HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

      try {
         request.login(username, password);
         userDAO = new UserDAOImpl();
         user = userDAO.find(username, password);
         ScopeController.setRequestParameterMap("user", user);
         Principal principal = request.getUserPrincipal();

         //you can fetch user from database for authenticated principal and do some action           
         logger.info("Authenticated user: " + principal.getName());
         logger.info("Auth Type : " + request.getAuthType());

         if (request.isUserInRole("admin")) {
            logger.info("redirecting to admin page......");
            return "/admin/admin?faces-redirect=true";
         }
         if (request.isUserInRole("user")) {
            logger.info("redirecting to user main page......");
            return "/wsdl/importwsdl?faces-redirect=true";
         }
         return null;

      } catch (ServletException ex) {
         FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Incorrect Username or Password. ", null);
         FacesContext.getCurrentInstance().addMessage(null, message);
         return null;
      } catch (Exception ex) {
         FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Login failed. ", ex.getMessage());
         FacesContext.getCurrentInstance().addMessage(null, message);
         logger.debug("Exception : " + ex.getMessage());
         ex.printStackTrace();
         return null;
      }
   }

   public String logout() {
      String result = "/main/login.xhtml?faces-redirect=true";
      this.setUser(null);
      logger.info("Logging out ...");
      FacesContext context = FacesContext.getCurrentInstance();
      HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
      try {
         request.logout();
      } catch (ServletException e) {
         logger.debug("Logout failed!");
         result = "/main/login.xhtml?faces-redirect=true";
      }
      return result;
   }

   /**
    * @return the password
    */
   public String getPassword() {
      return password;
   }

   /**
    * @param password the password to set
    */
   public void setPassword(String password) {
      this.password = password;
   }

   /**
    * @return the username
    */
   public String getUsername() {
      return username;
   }

   /**
    * @param username the username to set
    */
   public void setUsername(String username) {
      this.username = username;
   }

   /**
    * @return the user
    */
   public User getUser() {
      return user;
   }

   /**
    * @param user the user to set
    */
   public void setUser(User user) {
      this.user = user;
   }
}
