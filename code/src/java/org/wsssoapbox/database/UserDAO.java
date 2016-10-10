/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.database;

import java.util.List;

/**
 *
 * @author Peter
 */
public interface UserDAO {

   public void insert(User user);

   public void update(User user);

   public List<User> query();

   public void delete(User user);

   public User find(String username, String password);
}
