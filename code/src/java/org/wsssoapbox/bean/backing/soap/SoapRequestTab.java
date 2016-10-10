package org.wsssoapbox.bean.backing.soap;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import javax.faces.bean.ViewScoped;
import org.primefaces.event.TabChangeEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wsssoapbox.bean.backing.PageViewController;
import org.wsssoapbox.view.util.ScopeController;

@ManagedBean(name = "soapRequestTab")
@ViewScoped
public class SoapRequestTab implements Serializable {

   private static final Logger logger = LoggerFactory.getLogger(SoapRequestTab.class);
   private static final long serialVersionUID = 6294500846016104289L;

   public SoapRequestTab() {
      logger.debug("start public SoapRequestTab() ");

      logger.debug("end public SoapRequestTab() ");
   }

   public void onTabChange(TabChangeEvent event) {
      logger.debug("Tab Changed Active Tab: " + event.getTab().getTitle());
      PageViewController pageController = (PageViewController) ScopeController.getSessionMap("pageViewController");
      String tabSelected = event.getTab().getTitle();
      if (tabSelected.equalsIgnoreCase("Form")) {
         pageController.setMessageRequestTabIndex(0);
      } else if (tabSelected.equalsIgnoreCase("Raw")) {
         pageController.setMessageRequestTabIndex(1);
      } else if (tabSelected.equalsIgnoreCase("XML")) {
         pageController.setMessageRequestTabIndex(2);
      } else if (tabSelected.equalsIgnoreCase("HTTP MIME Header")) {
         pageController.setMessageRequestTabIndex(3);
      } else if (tabSelected.equalsIgnoreCase("Attachement")) {
         pageController.setMessageRequestTabIndex(4);
      }
      logger.debug("Sub Request Tab index: " + pageController.getMessageRequestTabIndex());
      logger.debug("Request Tab index: " + pageController.getMessageTabIndex());
   }
}
