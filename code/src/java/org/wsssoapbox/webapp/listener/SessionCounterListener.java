/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.webapp.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Web application lifecycle listener.
 * @author Peter
 */
public class SessionCounterListener implements HttpSessionListener {

   private static final Logger logger = LoggerFactory.getLogger(SessionCounterListener.class);
   private static int totalActiveSessions;

   public static int getTotalActiveSession() {
      return totalActiveSessions;
   }

   @Override
   public void sessionCreated(HttpSessionEvent se) {
      totalActiveSessions++;
      String id = se.getSession().getId();
      logger.debug("sessionCreated - add one session into counter");
      logger.debug("Session Id : " + id);
      logger.debug("Amount session active : " + totalActiveSessions);
   }

   @Override
   public void sessionDestroyed(HttpSessionEvent se) {
      totalActiveSessions--;
      String id = se.getSession().getId();
      logger.debug("sessionDestroyed - deduct one session from counter");
      logger.debug("Session Id : " + id);
      logger.debug("Amount session active : " + totalActiveSessions);
   }
}
