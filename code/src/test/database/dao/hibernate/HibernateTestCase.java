/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database.dao.hibernate;

import java.util.ArrayList;
import java.util.List;
import org.wsssoapbox.database.UserDAO;
import org.wsssoapbox.database.User;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.wsssoapbox.database.UserDAOImpl;
import static org.junit.Assert.*;

/**
 *
 * @author Peter
 */
public class HibernateTestCase {

   public HibernateTestCase() {
   }

   @BeforeClass
   public static void setUpClass() throws Exception {
   }

   @AfterClass
   public static void tearDownClass() throws Exception {
   }

   @Before
   public void setUp() {
   }

   @After
   public void tearDown() {
   }

//   @Test
   public void insertUserDAO() {

      User user = new User(1, "peter_conair", "1234", "Peter", "Conair");
      User user1 = new User(2, "pongsak", "1234", "Pongsak", "Gransamran");

      String strAction = "Insert record";
      System.out.println("====================" + strAction + "====================");
      UserDAO userDAO = new UserDAOImpl();
      userDAO.insert(user);
      userDAO.insert(user1);
   }

    @Test
   public void queryUsersDAO() {

      List< User> users = new ArrayList<User>();

      String strAction = "Result record";
      System.out.println("====================" + strAction + "====================");
      UserDAO userDAO = new UserDAOImpl();
      users = userDAO.query();
      System.out.println(users);
     assertTrue(users.size() > 0);

   }

 //  @Test
   public void findUserDAO() {

      User user = new User();
      String userName = "peter_conair";
      String password = "1234";

      String strAction = "Result record";
      System.out.println("====================" + strAction + "====================");
      UserDAO userDAO = new UserDAOImpl();
      user = userDAO.find(userName, password);

      assertNotNull(user);

   }
}
