package org.wsssoapbox.database;
// Generated Sep 2, 2011 2:03:50 PM by Hibernate Tools 3.2.1.GA

import java.io.Serializable;
import org.slf4j.Logger;


public class User  implements Serializable {
   private static final long serialVersionUID = -877556810116719106L;
   
     private int id;
     private String username;
     private String password;
     private String firstName;
     private String lastName;

    public User() {
    }

	
    public User(int id, String username) {
        this.id = id;
        this.username = username;
    }
    public User(int id, String username, String password, String firstName, String lastName) {
       this.id = id;
       this.username = username;
       this.password = password;
       this.firstName = firstName;
       this.lastName = lastName;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public String getLastName() {
        return this.lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

   /**
    * @return the firstName
    */
   public String getFirstName() {
      return firstName;
   }

   /**
    * @param firstName the firstName to set
    */
   public void setFirstName(String firstName) {
      this.firstName = firstName;
   }

   /**
    * @return the password
    */
   public String getPassword() {
      return password;
   }

   /**
    * @param password the password to set
    */
   public void setPassword(String password) {
      this.password = password;
   }

   /**
    * @return the username
    */
   public String getUsername() {
      return username;
   }

   /**
    * @param username the username to set
    */
   public void setUsername(String username) {
      this.username = username;
   }




}


