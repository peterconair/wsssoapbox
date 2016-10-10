package org.wsssoapbox.bean.backing;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wsssoapbox.view.util.ScopeController;

@ManagedBean(name = "windowController")
@SessionScoped
public class WindowController implements Serializable {
   
   private static final long serialVersionUID = -1573690900677346162L;
   private static final Logger logger = LoggerFactory.getLogger(WindowController.class);
   
   public WindowController() {
      logger.debug("start public WindowController()");
      
      logger.debug("end public WindowController()");
   }
   
   public String showNavigator() {
      PageViewController pageContoller = (PageViewController) ScopeController.getSessionMap("pageViewController");
      pageContoller.setNavigator(true);
      // expanded navigator
      pageContoller.setNavigatorCollapse(false);
      return "/wsdl/importwsdl?faces-redirect=true";
   }
}
