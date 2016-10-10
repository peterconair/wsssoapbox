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
public class Theme implements Serializable {

   private static final long serialVersionUID = -7961161481633865324L;
   private String name;
   private String image;

   public Theme() {
   }

   public Theme(String name, String image) {
      this.name = name;
      this.image = image;
   }

   public String getImage() {
      return image;
   }

   public void setImage(String image) {
      this.image = image;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   @Override
   public String toString() {
      return name;
   }
}
