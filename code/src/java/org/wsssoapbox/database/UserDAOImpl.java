/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Peter
 */
public class UserDAOImpl implements UserDAO, Serializable {

   private static final long serialVersionUID = -4027398440504847867L;
   private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);
   Session session = HibernateUtil.getSessionFactory().openSession();

   @Override
   public void insert(User user) {

      session.beginTransaction();
      session.save(user);
      session.getTransaction().commit();
      session.close();

   }

   @Override
   public void update(User user) {
      session.beginTransaction();
      session.update(user);
      session.getTransaction().commit();
      session.close();
   }

   @Override
   public List<User> query() {
      List<User> users = new ArrayList<User>();
      session.beginTransaction();
      Query query = session.createSQLQuery("select * from users ").addEntity(User.class);
      users = query.list();
      session.getTransaction().commit();
      session.close();

      return users;
   }

   @Override
   public void delete(User user) {
      session.beginTransaction();
      session.delete(user);
      session.getTransaction().commit();
      session.close();
   }

   @Override
   public User find(String username, String password) {
      User user = new User();
      session.beginTransaction();
      Query queuery = session.createSQLQuery("select * from users "
              + "where username=:username and password=:password").addEntity(User.class);
      queuery.setParameter("username", username);
      queuery.setParameter("password", password);

      if (queuery.list().size() >= 1) {
         user = (User) queuery.list().get(0);
         logger.debug("ID : " + user.getId());
         logger.debug("Name :" + user.getFirstName());
         logger.debug("User Name :" + user.getLastName());
      } else {
         logger.debug("Can not found  : " + username + " and " + password);
         return null;
      }
      session.getTransaction().commit();
      session.close();
      return user;
   }
}
