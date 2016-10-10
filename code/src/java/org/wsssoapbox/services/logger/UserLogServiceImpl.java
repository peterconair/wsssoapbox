/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.services.logger;

import java.io.Serializable;
import java.util.List;
import org.wsssoapbox.database.User;
import org.wsssoapbox.database.logger.UserLog;
import org.wsssoapbox.database.logger.UserLogDAO;
import org.wsssoapbox.database.logger.UserLogDAOImpl;

/**
 *
 * @author Peter
 */
public class UserLogServiceImpl implements UserLogService ,Serializable{
   private static final long serialVersionUID = 8856390112485608003L;

    private UserLogDAO userLogDAO;
   @Override
   public void insert(UserLog log) {
      userLogDAO.insert(log);
   }

   @Override
   public void update(UserLog log) {
      userLogDAO.update(log);
   }

   @Override
   public List<UserLog> query(User user) {
      userLogDAO = new UserLogDAOImpl();
     return  userLogDAO.query(user);
   }

   @Override
   public List<UserLog> queryLevel(User user, String level) {
      return userLogDAO.queryLevel(user, level);
   }

   @Override
   public void delete(UserLog log) {
     userLogDAO.equals(log);
   }

   @Override
   public void delete(List<UserLog> logs) {
       userLogDAO = new UserLogDAOImpl();
       userLogDAO.delete(logs);
   }

  
   
}
