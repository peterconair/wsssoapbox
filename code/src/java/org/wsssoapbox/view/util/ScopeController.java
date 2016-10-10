package org.wsssoapbox.view.util;

import java.util.Map;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ScopeController {
   
   public static void setSessionMap(String name, Object object) {
      HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
      session.setAttribute(name, object);
   }
   
      public static Map getSessionMap() {
      return  FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
   }
   
   public static Object getSessionMap(String name) {
      Object object = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(name);
      return object;
   }
   
   public static void setRequestParameterMap(String name, Object object) {
      HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
      request.setAttribute(name, object);
   }
   
   public static Object getRequestParameterMap(String name) {
      Object object = FacesContext.getCurrentInstance().
              getExternalContext().getRequestParameterMap().get(name);
      return object;
   }
   
   public static Map getRequestMap() {
      return FacesContext.getCurrentInstance().
              getExternalContext().getRequestParameterMap();
   }
   
   public static Map getApplicationMap() {
      return FacesContext.getCurrentInstance().
              getExternalContext().getApplicationMap();
   }
   
   public static Object getApplicationMap(String name) {
      return FacesContext.getCurrentInstance().
              getExternalContext().getApplicationMap().get(name);
   }
}
