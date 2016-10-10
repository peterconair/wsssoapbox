/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.database.logger;

import java.util.List;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.wsssoapbox.database.User;

/**
 *
 * @author Peter
 */
public class UserLogDAOTest {
   
   public UserLogDAOTest() {
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

   @Test
   public void testInsert() {
   }

   @Test
   public void testUpdate() {
   }

   @Test
   public void testQuery() {
   }

   @Test
   public void testQueryLevel() {
   }

   @Test
   public void testDelete() {
   }

   public class UserLogDAOImpl implements UserLogDAO {

      public void insert(UserLog log) {
      }

      public void update(UserLog log) {
      }

      public List<UserLog> query(User user) {
         return null;
      }

      public List<UserLog> queryLevel(User user, String level) {
         return null;
      }

      public void delete(UserLog log) {
      }

      @Override
      public void delete(List<UserLog> logs) {
         throw new UnsupportedOperationException("Not supported yet.");
      }
   }
}
