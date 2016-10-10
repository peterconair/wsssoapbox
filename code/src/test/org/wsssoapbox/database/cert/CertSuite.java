/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.database.cert;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author Peter
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({org.wsssoapbox.database.cert.KeyStoreDAOTest.class, org.wsssoapbox.database.cert.KeyStoreDAOImplTest.class, org.wsssoapbox.database.cert.KeyStoreTest.class})
public class CertSuite {

   @BeforeClass
   public static void setUpClass() throws Exception {
   }

   @AfterClass
   public static void tearDownClass() throws Exception {
   }
   
}
