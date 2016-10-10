/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.database.logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wsssoapbox.database.HibernateUtil;
import org.wsssoapbox.database.User;

/**
 *
 * @author Peter
 */
public class UserLogDAOImpl implements UserLogDAO {

   private static final Logger logger = LoggerFactory.getLogger(UserLogDAOImpl.class);
   Session session = HibernateUtil.getSessionFactory().openSession();

   @Override
   public void insert(UserLog log) {
      session.beginTransaction();
      session.save(log);
      session.getTransaction().commit();
      session.close();
   }

   @Override
   public void update(UserLog log) {
      session.beginTransaction();
      session.update(log);
      session.getTransaction().commit();
      session.close();
   }

   @Override
   public List<UserLog> query(User user) {
      List<UserLog> logs = new ArrayList<UserLog>();
      session.beginTransaction();
      Query queuery = session.createSQLQuery("select * from user_log "
              + "where user_id=:user_id").addEntity(UserLog.class);
      queuery.setParameter("user_id", user.getId());
      logs = queuery.list();
      session.getTransaction().commit();
      session.close();

      return logs;
   }

   @Override
   public List<UserLog> queryLevel(User user, String level) {
      List<UserLog> logs = new ArrayList<UserLog>();
      session.beginTransaction();
      Query queuery = session.createSQLQuery("select * from user_log "
              + "where user_id=:user_id and level=:levle ").addEntity(UserLog.class);
      queuery.setParameter("user_id", user.getId());
      queuery.setParameter("level", level);
      logs = queuery.list();
      session.getTransaction().commit();
      session.close();

      return logs;
   }

   @Override
   public void delete(UserLog log) {
      session.beginTransaction();
      session.delete(log);
      session.getTransaction().commit();
      session.close();
   }

   @Override
   public void delete(List<UserLog> logs) {
      session.beginTransaction();
      Iterator it = logs.iterator();
      while (it.hasNext()) {
         UserLog log = (UserLog) it.next();
     //    logger.info(log.getLogId() + "");
         session.delete(log);
      }
      session.getTransaction().commit();
      session.close();


   }
}
