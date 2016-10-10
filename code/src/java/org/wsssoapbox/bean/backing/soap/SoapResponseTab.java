package org.wsssoapbox.bean.backing.soap;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import javax.faces.bean.ViewScoped;
import org.slf4j.Logger;
import org.primefaces.event.TabChangeEvent;
import org.slf4j.LoggerFactory;
import org.wsssoapbox.bean.backing.PageViewController;
import org.wsssoapbox.view.util.ScopeController;

@ManagedBean(name = "soapResponseTab")
@ViewScoped
public class SoapResponseTab implements Serializable {

   private static final Logger logger = LoggerFactory.getLogger(SoapResponseTab.class);
   private static final long serialVersionUID = 572926545991199664L;

   public SoapResponseTab() {
      logger.debug("start public SoapResponseTab() ");

      logger.debug("end public SoapResponseTab() ");
   }

   public String onTabChangeUpdate() {
      return "";
   }

   public void onTabChange(TabChangeEvent event) {
      PageViewController pageController = (PageViewController) ScopeController.getSessionMap("pageViewController");

      logger.info("Tab Changed Active Tab: " + event.getTab().getTitle());
      String tabSelected = event.getTab().getTitle();

      if (tabSelected.equalsIgnoreCase("Raw")) {
         pageController.setMessageResponseTabIndex(0);
      } else if (tabSelected.equalsIgnoreCase("Prety XML")) {
         pageController.setMessageResponseTabIndex(1);
      } else if (tabSelected.equalsIgnoreCase("XML")) {
         pageController.setMessageResponseTabIndex(2);
      } else if (tabSelected.equalsIgnoreCase("HTML Header")) {
         pageController.setMessageResponseTabIndex(3);
      } else if (tabSelected.equalsIgnoreCase("Attache File")) {
         pageController.setMessageResponseTabIndex(4);
      }
   }
}
