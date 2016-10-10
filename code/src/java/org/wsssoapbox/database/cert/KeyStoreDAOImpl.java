/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.database.cert;

import java.io.Serializable;
import java.util.ArrayList;
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
public class KeyStoreDAOImpl implements KeyStoreDAO, Serializable {

   private static final long serialVersionUID = -248724484063127117L;
   private static final Logger logger = LoggerFactory.getLogger(KeyStoreDAOImpl.class);
   Session session = HibernateUtil.getSessionFactory().openSession();
   // Session session = HibernateUtil.getSessionFactoryDerby().openSession();

   @Override
   public void insert(KeyStore keyStore) {
      session.beginTransaction();
      session.save(keyStore);
      session.getTransaction().commit();
      session.close();
   }

   @Override
   public void update(KeyStore keyStore) {
      session.beginTransaction();
      session.update(keyStore);
      session.getTransaction().commit();
      session.close();
   }

   @Override
   public List<KeyStore> query(User user) {
      List<KeyStore> keyStores = new ArrayList<KeyStore>();
      int userId = user.getId();
      session.beginTransaction();

      Query queuery = session.createSQLQuery("select * from key_store "
              + "where user_id=:user_id").addEntity(KeyStore.class);
      queuery.setParameter("user_id", userId);
      keyStores = queuery.list();


      session.getTransaction().commit();
      session.close();

      return keyStores;
   }

   @Override
   public void delete(KeyStore keyStore) {
      session.beginTransaction();
      session.delete(keyStore);

      /*  
      Query queuery = session.createSQLQuery("delete from key_store where keystore_id=:keystore_id "
      + "and user_id=:user_id ").addEntity(KeyStore.class);
      queuery.setParameter("keystore_id", keyStore.getKeystoreId());
      queuery.setParameter("user_id", keyStore.getUserId());
      queuery.executeUpdate();
       * 
       */

      session.getTransaction().commit();
      session.close();
   }

   @Override
   public KeyStore find(String aliasName, User user) {
      KeyStore keyStore = new KeyStore();
      int userId = user.getId();
      session.beginTransaction();
      Query queuery = session.createSQLQuery("select * from key_store "
              + "where alias_name=:alias_name and user_id=:user_id").addEntity(KeyStore.class);
      queuery.setParameter("alias_name", aliasName);
      queuery.setParameter("user_id", userId);

      if (queuery.list().size() >= 1) {
         keyStore = (KeyStore) queuery.list().get(0);
         logger.debug("ID : " + keyStore.getKeystoreId());
         logger.debug("Name :" + keyStore.getName());

      } else {
         logger.debug("Can not found  : " + aliasName + " and " + user.getUsername());
         return null;
      }
      session.getTransaction().commit();
      session.close();
      return keyStore;
   }
}
