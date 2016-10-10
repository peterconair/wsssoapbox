/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.bean.backing.auth;

import java.io.IOException;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.MDC;
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
@ManagedBean(name = "loginForm")
@SessionScoped
public class LoginForm implements Serializable {

   private static final long serialVersionUID = -1622707425006425150L;
   private static final Logger logger = LoggerFactory.getLogger(LoginForm.class);
   private static final String LOGIN_SCREEN = "/main/login.xhtml";
   private User user;
   private String username;
   private String password;
   private UserDAO userDAO;

   public LoginForm() {
      //  user = new User();
   }

   public String login() throws IOException {

      logger.debug("start   public String login()");
      try {
         // getRequest().login(userName, password);
         logger.debug("*************************");
         logger.debug("Username : " + username);
         //   logger.debug("password : " + password);
         logger.debug("*************************");

         //   getRequest().login(userName, password);
         userDAO = new UserDAOImpl();
         //   String hashPassword = DatabaseUtil.hash("SHA-256",password);
         //  logger.debug("Hash : " + hashPassword);
         setUser(userDAO.find(username, password));

         if (user != null) {
            MDC.put("user", user.getId());
            ScopeController.setSessionMap("user", user);
            //   HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            //   session.setAttribute("user", user);
            logger.info("Welcome! " + user.getFirstName());
            if (getRequest().isUserInRole("Admin")) {
               logger.debug("User : " + username + " has " + " Admin Roles");
            }

            String originalUrl = FacesContext.getCurrentInstance().getViewRoot().getViewId();
            logger.debug("Original URL :" + originalUrl);
            if (!LOGIN_SCREEN.equals(originalUrl)) {
               logger.info("redirecting to " + originalUrl + " ......");
               return originalUrl.replaceAll(".xhtml", "") + "?faces-redirect=true";
            } else {
               logger.info("redirecting to main page......");
               return "/wsdl/importwsdl?faces-redirect=true";
            }
         } else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Incorrect Username or Password. ", null);
            FacesContext.getCurrentInstance().addMessage(null, message);
            logger.info("Incorrect Username or Password.");

            return null;
         }
      } catch (Exception ex) {
         FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Login failed. ", ex.getMessage());
         FacesContext.getCurrentInstance().addMessage(null, message);
         logger.info("Login failed. ");
         logger.debug("Exception : " + ex.getMessage());
         ex.printStackTrace();
         return null;
      } finally {
         //MDC.remove("user");
      }
   }

   public String logout() throws ServletException {

      logger.debug("user id : " + user.getId() + " username :  " + user.getId() + " name : " + user.getFirstName());
      logger.debug("try to logout");

      logger.info("Logging out ...");
      HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
      session.invalidate();
      logger.info("Bye !");
      MDC.remove("user");
      this.setUser(null);
      return "/main/login.xhtml?faces-redirect=true";
   }

   public static HttpServletRequest getRequest() {
      Object request = FacesContext.getCurrentInstance().getExternalContext().getRequest();
      return request instanceof HttpServletRequest ? (HttpServletRequest) request : null;
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
