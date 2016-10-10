/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.database.cert;

import org.wsssoapbox.database.User;
import java.io.FileNotFoundException;
import org.apache.commons.io.IOUtils;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Peter
 */
public class KeyStoreDAOImplTest {

   public KeyStoreDAOImplTest() {
   }

   @BeforeClass
   public static void setUpClass() throws Exception {
   }

   @AfterClass
   public static void tearDownClass() throws Exception {
   }

    @Test
   public void testInsert() {
      InputStream is = null;
      try {

         String keyStoreFile = "F:/Develope/sourecode/netbeans/Web_Service/Prodiver/ws-payment-btoken/src/main/webapp/WEB-INF/config/certificates/client/client.jks";
         File file = new File(keyStoreFile);
         is = new FileInputStream(file);
         String name = file.getName();
         String fileType = name.substring(name.indexOf(".") + 1);



         byte[] content = IOUtils.toByteArray(is);



         KeyStore keyStore = new KeyStore();
         keyStore.setName(name);
         keyStore.setAliasName("client_keystore");
         keyStore.setProvider("SUN");
         keyStore.setType(fileType);
         keyStore.setPassword("123456");
         keyStore.setContent(content);
         keyStore.setCreateDate(new Date());
         keyStore.setUserId(1);
         // keyStore.set
         String strAction = "Insert record";
         System.out.println("====================" + strAction + "====================");
         KeyStoreDAO userDAO = new KeyStoreDAOImpl();
         userDAO.insert(keyStore);
      } catch (FileNotFoundException ex) {
         Logger.getLogger(KeyStoreDAOImplTest.class.getName()).log(Level.SEVERE, null, ex);
      } catch (IOException ex) {
         Logger.getLogger(KeyStoreDAOImplTest.class.getName()).log(Level.SEVERE, null, ex);
      } finally {
         try {
            is.close();
         } catch (IOException ex) {
            Logger.getLogger(KeyStoreDAOImplTest.class.getName()).log(Level.SEVERE, null, ex);
         }
      }

   }

   //  @Test
   public void testUpdate() {
   }

   // @Test
   public void testQuery() {
      List< KeyStore> keyStores = new ArrayList<KeyStore>();
      User user = new User();
      user.setId(1);
      String strAction = "Result record";
      System.out.println("====================" + strAction + "====================");
      KeyStoreDAO keyStoreDAO = new KeyStoreDAOImpl();
      keyStores = keyStoreDAO.query(user);
      System.out.println(keyStores);
      System.out.println("Size  :" + keyStores.size());
      for (KeyStore ks : keyStores) {
         System.out.println("Name :" + ks.getAliasName());
         System.out.println("Name :" + ks.getName());
      }

      assertTrue(keyStores.size() > 0);
   }

   //  @Test
   public void testDelete() {
   }

  // @Test
   public void testFind() {
      FileOutputStream fout = null;
      try {
         KeyStore keyStore = new KeyStore();
         User user = new User();
         user.setId(1);
         user.setUsername("peter_conair");
         String keyStroeName = "server_keystore";
         String strAction = "Find record";
         System.out.println("====================" + strAction + "====================");
         KeyStoreDAO keyStoreDAO = new KeyStoreDAOImpl();
         keyStore = keyStoreDAO.find(keyStroeName, user);
         System.out.println("KeyStore Name : " + keyStore.getName());
         System.out.println("KeyStore Type : " + keyStore.getType());
         System.out.println("Owner Key Id : " + keyStore.getUserId());
         byte[] content = keyStore.getContent();
         fout = new FileOutputStream("C:\\" + keyStore.getName());
         IOUtils.write(content, fout);
      } catch (FileNotFoundException ex) {
         Logger.getLogger(KeyStoreDAOImplTest.class.getName()).log(Level.SEVERE, null, ex);
      } catch (IOException ex) {
         Logger.getLogger(KeyStoreDAOImplTest.class.getName()).log(Level.SEVERE, null, ex);
      } finally {
         try {
            fout.close();
         } catch (IOException ex) {
            Logger.getLogger(KeyStoreDAOImplTest.class.getName()).log(Level.SEVERE, null, ex);
         }
      }



   }
}
