/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.webapp.listener;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Web application lifecycle listener.
 * @author Peter
 */
public class AttributeListener implements HttpSessionAttributeListener {

   private static final long serialVersionUID = 1L;
   private static final Logger logger = LoggerFactory.getLogger(AttributeListener.class);

   @Override
   public void attributeAdded(HttpSessionBindingEvent event) {
      String attributeName = event.getName();
      Object attributeValue = event.getValue();
      String sessionId = event.getSession().getId();
      logger.debug("Session Id  : " + sessionId + "  " + "Attribute added : " + attributeName + " : " + attributeValue);
   }

   @Override
   public void attributeRemoved(HttpSessionBindingEvent event) {
      String attributeName = event.getName();
      Object attributeValue = event.getValue();
      String sessionId = event.getSession().getId();
      logger.debug("Session Id  : " + sessionId + "  " + "Attribute removed : " + attributeName + " : " + attributeValue);
   }

   @Override
   public void attributeReplaced(HttpSessionBindingEvent event) {
      String attributeName = event.getName();
      Object attributeValue = event.getValue();
      String sessionId = event.getSession().getId();
      logger.debug("Session Id  : " + sessionId + "  " + "Attribute replaced : " + attributeName + " : " + attributeValue);
   }
}
