/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.database.cert;

import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.wsssoapbox.database.User;

/**
 *
 * @author Peter
 */
public class KeyStoreDAOTest {
   
   public KeyStoreDAOTest() {
   }

   @BeforeClass
   public static void setUpClass() throws Exception {
   }

   @AfterClass
   public static void tearDownClass() throws Exception {
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
   public void testDelete() {
   }

   @Test
   public void testFind() {
   }

   public class KeyStoreDAOImpl implements KeyStoreDAO {

      public void insert(KeyStore keyStore) {
      }

      public void update(KeyStore keyStore) {
      }


      public void delete(KeyStore keyStore) {
      }

      public KeyStore find(String name, User user) {
         return null;
      }

      @Override
      public List<KeyStore> query(User user) {
         throw new UnsupportedOperationException("Not supported yet.");
      }
   }
}
