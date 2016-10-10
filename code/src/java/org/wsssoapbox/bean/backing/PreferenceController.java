package org.wsssoapbox.bean.backing;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wsssoapbox.bean.model.view.Page;

@ManagedBean(name = "preferenceController")
@SessionScoped
public class PreferenceController implements Serializable {

   private static final long serialVersionUID = -5167271981012275093L;
   private List<Page> pages;
   private Page page;
   private static final Logger logger = LoggerFactory.getLogger(PreferenceController.class);

   static {
   }

   public PreferenceController() {
      logger.debug("start public PreferenceController()");
      page = new Page("Theme", "Theme", "/windows/theme.xhtml");
      pages = new ArrayList<Page>();
      pages.add(new Page("Theme", "Theme", "/windows/theme.xhtml"));
      pages.add(new Page("WSDL", "WSDL", "/windows/prewsdl.xhtml"));
      pages.add(new Page("Logging", "Logging", "/windows/prelogging.xhtml"));
      pages.add(new Page("SOAP", "SOAP", "/windows/presoap.xhtml"));

      logger.debug("end public PreferenceController()");
   }

   public void selectedPage(String name) {
      logger.debug("Page Lacation: " + name);
      for (Page p : getPages()) {
         if (p.getName().equals(name)) {
            this.page = p;
            logger.debug("Page show Name: " + p.getName());
            logger.debug("Page show Location : " + p.getPageLocation());
         }
      }
   }

   /**
    * @return the pages
    */
   public List<Page> getPages() {
      return pages;
   }

   /**
    * @param pages the pages to set
    */
   public void setPages(List<Page> pages) {
      this.setPages(pages);
   }

   /**
    * @return the page
    */
   public Page getPage() {
      return page;
   }

   /**
    * @param page the page to set
    */
   public void setPage(Page page) {
      this.page = page;
   }
}
