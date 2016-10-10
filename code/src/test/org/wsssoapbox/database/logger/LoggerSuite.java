/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.database.logger;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author Peter
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({org.wsssoapbox.database.logger.UserLogTest.class, org.wsssoapbox.database.logger.UserLogDAOTest.class, org.wsssoapbox.database.logger.UserLogDAOImplTest.class})
public class LoggerSuite {

   @BeforeClass
   public static void setUpClass() throws Exception {
   }

   @AfterClass
   public static void tearDownClass() throws Exception {
   }

   @Before
   public void setUp() throws Exception {
   }
   
}
