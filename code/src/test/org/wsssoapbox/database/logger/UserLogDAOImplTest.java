/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.database.logger;

import org.wsssoapbox.database.User;
import org.wsssoapbox.services.logger.UserLogServiceImpl;
import org.wsssoapbox.services.logger.UserLogService;
import java.util.List;
import java.util.ArrayList;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Peter
 */
public class UserLogDAOImplTest {

   public UserLogDAOImplTest() {
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

//   @Test
   public void testInsert() {
   }

   //  @Test
   public void testUpdate() {
   }

   @Test
   public void testQuery() {
      List<UserLog> userLogs = new ArrayList<UserLog>();
      UserLogService logService = new UserLogServiceImpl();
      UserLog userLog = new UserLog();
      User user = new User();
      user.setId(1);
      user.setUsername("peter_conair");

      userLogs = logService.query(user);
      
      System.out.println("List of user : "+userLogs.size());
      for(UserLog uLog : userLogs){
         System.out.println(uLog.getLogId() + " : Message : " + uLog.getMessage());
      }



   }

//   @Test
   public void testQueryLevel() {
   }

//   @Test
   public void testDelete() {
   }
}
