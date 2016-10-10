/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.database.logger;

import java.util.List;
import org.wsssoapbox.database.User;

/**
 *
 * @author Peter
 */
public interface UserLogDAO {

   public void insert(UserLog log);

   public void update(UserLog log);

   public List<UserLog> query(User user);

   public List<UserLog> queryLevel(User user, String level);

   public void delete(UserLog log);
   public void delete(List<UserLog> logs);

 //  public UserLog find(User user);
}
