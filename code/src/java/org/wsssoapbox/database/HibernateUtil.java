/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.database;

import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.SessionFactory;

/**
 * Hibernate Utility class with a convenient method to get Session Factory object.
 *
 * @author Peter
 */
public class HibernateUtil {

   private static final SessionFactory sessionFactoryMySQL;
   private static final SessionFactory sessionFactoryDerby;
   private static final String databaseProvider = "mysql";

   /*
   {
   try {
   String path = "/config/database/db.properties";
   Properties props = new Properties();
   props.loadFromXML(new FileInputStream(path));
   System.out.println(props);
   
   Session session = HibernateUtil.getSessionFactory("").openSession();
   // Session session = HibernateUtil.getSessionFactoryDerby().openSession();
   } catch (InvalidPropertiesFormatException ex) {
   } catch (FileNotFoundException ex) {
   } catch (IOException ex) {
   }
   }
    */
   static {
      try {
         // Create the SessionFactory from standard (hibernate.cfg.xml) 
         // config file.
         sessionFactoryMySQL = new AnnotationConfiguration().configure("hibernate_mysql.cfg.xml").buildSessionFactory();
         sessionFactoryDerby = new AnnotationConfiguration().configure("hibernate_derby.cfg.xml").buildSessionFactory();
      } catch (Throwable ex) {
         // Log the exception. 
         System.err.println("Initial SessionFactory creation failed." + ex);
         throw new ExceptionInInitializerError(ex);
      }
   }

   public static SessionFactory getSessionFactory() {
      if (databaseProvider.equals("mysql")) {
         return sessionFactoryMySQL;
      } else {
         return sessionFactoryDerby;
      }


   }
}
