/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.webapp.listener;

import java.io.IOException;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wsssoapbox.database.User;
import org.wsssoapbox.webapp.process.CleanProcess;
import org.wsssoapbox.webapp.process.TempFileCleanProcessImpl;

/**
 *
 * @author Peter
 */
public class AuthenticationPhaseListener implements PhaseListener, CleanProcess {

   private static final Logger logger = LoggerFactory.getLogger(AuthenticationPhaseListener.class);
   private static final String LOGIN_SCREEN = "/main/login.xhtml";
   private static final long serialVersionUID = 4069656136548721105L;
   private FacesContext context;
   private String userId;

   @Override
   public void afterPhase(PhaseEvent phaseEvent) {
      context = phaseEvent.getFacesContext();
      User user = (User) context.getExternalContext().getSessionMap().get("user");
      // if session of user avalable that mean this user logged in. 
      if (isAuthenticated(context)) {
         userId = "" + user.getId();
         logger.debug("*****************************************************");
         logger.debug("Current logged in user : " + user.getUsername());
         logger.debug("*****************************************************");
         return;
      } else { // user not logged in
         HttpServletRequest httpRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

         String path = context.getViewRoot().getViewId();
         logger.debug("*****************************************************");
         logger.debug("Default Login URL : " + LOGIN_SCREEN);
         logger.info("Request URL : " + path);
         logger.info("Who try to access URI : " + httpRequest.getRequestURI());
         logger.info("You are not Logged in! ");

         // if not login page re-direct login page.
         if (!LOGIN_SCREEN.equals(path)) {
            try {
               // Blacking process to call (Delete all temp file)
               String contextName = "/" + context.getExternalContext().getContextName();
               logger.debug("Context Name : " + contextName);
               String redirectURL = contextName + LOGIN_SCREEN;
               logger.debug("Redirecting to  : " + redirectURL);
               context.getExternalContext().redirect(redirectURL);
            } catch (IOException ex) {
               logger.debug("IOException : " + ex.getMessage());
               ex.printStackTrace();
            }
         }
         try {
            doClean();
         } catch (NullPointerException ex) {
            ex.printStackTrace();
         }


         logger.debug("*****************************************************");
      }
   }

   @Override
   public void beforePhase(PhaseEvent phaseEvent) {
   }

   @Override
   public PhaseId getPhaseId() {
      return PhaseId.RESTORE_VIEW;
   }

   private boolean isAuthenticated(FacesContext context) {
      if (context.getExternalContext().getSessionMap().get("user") != null) {
         return true;
      } else {
         return false;
      }
   }

   @Override
   public void doClean() {
      try {
         String path = "/temp/" + userId;
         String userDir = FacesContext.getCurrentInstance().getExternalContext().getRealPath(path);
         CleanProcess cp = new TempFileCleanProcessImpl(userId, userDir);
         cp.doClean();
      } catch (Exception ex) {
         ex.printStackTrace();
      }
   }
}
