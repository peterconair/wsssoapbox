/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.bean.model.view;


import java.io.Serializable;

/**
 *
 * @author Peter
 */
public class Page implements Serializable {

   private String name;
   private String title;
   private String pageLocation;

   public Page() {
   }

   public Page(String name, String title, String pageLocation) {
      this.name = name;
      this.title = title;
      this.pageLocation = pageLocation;
   }

   /**
    * @return the title
    */
   public String getTitle() {
      return title;
   }

   /**
    * @param title the title to set
    */
   public void setTitle(String title) {
      this.title = title;
   }

   /**
    * @return the pageLocation
    */
   public String getPageLocation() {
      return pageLocation;
   }

   /**
    * @param pageLocation the pageLocation to set
    */
   public void setPageLocation(String pageLocation) {
      this.pageLocation = pageLocation;
   }

   /**
    * @return the name
    */
   public String getName() {
      return name;
   }

   /**
    * @param name the name to set
    */
   public void setName(String name) {
      this.name = name;
   }
}
