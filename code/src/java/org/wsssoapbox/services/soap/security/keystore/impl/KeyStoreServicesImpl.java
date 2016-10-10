/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.services.soap.security.keystore.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wsssoapbox.bean.model.soap.security.keystore.KeyStoreBean;
import org.wsssoapbox.database.User;
import org.wsssoapbox.database.cert.KeyStore;
import org.wsssoapbox.database.cert.KeyStoreDAO;
import org.wsssoapbox.database.cert.KeyStoreDAOImpl;
import org.wsssoapbox.services.soap.security.keystore.KeyStoreServices;
import org.wsssoapbox.soap.security.keystore.support.KeyStoreHelper;

/**
 *
 * @author Peter
 */
public class KeyStoreServicesImpl implements KeyStoreServices {

   private static final Logger logger = LoggerFactory.getLogger(KeyStoreServicesImpl.class);
   private KeyStoreDAO keyStoreDAO;

   @Override
   public void insert(KeyStoreBean keyStoreBean, User user) {
      KeyStore keyStore = new KeyStore();
      keyStoreDAO = new KeyStoreDAOImpl();
      keyStoreDAO = new KeyStoreDAOImpl();
      keyStore.setUserId(user.getId());
      keyStore.setContent(keyStoreBean.getContent());
      keyStore.setName(keyStoreBean.getName());
      keyStore.setAliasName(keyStoreBean.getAliasName());
      keyStore.setType(keyStoreBean.getType());
      keyStore.setContent(keyStoreBean.getContent());
      keyStore.setPassword(keyStoreBean.getPassword());
      keyStore.setCreateDate(new Date());
      keyStore.setProvider(keyStoreBean.getProvider());

      keyStoreDAO.insert(keyStore);
   }

   @Override
   public void update(KeyStore keyStore) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public void delete(KeyStoreBean keyStoreBean, User user) {
      keyStoreDAO = new KeyStoreDAOImpl();
      KeyStore keyStore = new KeyStore();
      keyStore.setUserId(user.getId());
      keyStore.setAliasName(keyStoreBean.getAliasName());
      keyStore.setKeystoreId(keyStoreBean.getId());
      keyStore.setName(keyStoreBean.getName());
      keyStore.setContent(keyStoreBean.getContent());
      keyStoreDAO.delete(keyStore);
   }

   @Override
   public List<KeyStoreBean> query(User user) {
      keyStoreDAO = new KeyStoreDAOImpl();
      List<KeyStoreBean> ksBeans = new ArrayList<KeyStoreBean>();
      KeyStoreBean keyStoreBean = new KeyStoreBean();
      KeyStoreHelper keyStoreHelper = new KeyStoreHelper();
      KeyStoreDAO ksDAO = new KeyStoreDAOImpl();
      List<KeyStore> kStores = ksDAO.query(user);
      for (KeyStore ks : kStores) {
         keyStoreHelper = new KeyStoreHelper();
         // error point 
         byte[] content = ks.getContent();
         InputStream is = new ByteArrayInputStream(content, 0, content.length);
         keyStoreBean = keyStoreHelper.getKeyStore(is, ks.getName(), ks.getPassword());
         keyStoreBean.setId(ks.getKeystoreId());
         keyStoreBean.setUserId(ks.getUserId());
         keyStoreBean.setCreationDate(ks.getCreateDate());
         logger.debug(keyStoreBean.toString());
         try {
            is.close();
         } catch (IOException ex) {
            logger.error(ex.getMessage());
         }
         ksBeans.add(keyStoreBean);

      }
      return ksBeans;
   }

   @Override
   public KeyStoreBean find(String name, User user) {
      throw new UnsupportedOperationException("Not supported yet.");
   }
}
