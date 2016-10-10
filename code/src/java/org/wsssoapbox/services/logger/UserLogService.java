/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.services.logger;

import java.util.List;
import org.wsssoapbox.database.User;
import org.wsssoapbox.database.logger.UserLog;

/**
 *
 * @author Peter
 */
public interface UserLogService {

   public void insert(UserLog log);

   public void update(UserLog log);

   public List<UserLog> query(User user);

   public List<UserLog> queryLevel(User user, String level);

   public void delete(UserLog log);

   public void delete(List<UserLog> logs);
}
