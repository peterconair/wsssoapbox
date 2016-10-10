/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.services.soap.security.keystore;

import java.util.List;
import org.wsssoapbox.bean.model.soap.security.keystore.KeyStoreBean;
import org.wsssoapbox.database.User;
import org.wsssoapbox.database.cert.KeyStore;

/**
 *
 * @author Peter
 */
public interface KeyStoreServices {

  public void insert(KeyStoreBean keyStoreBean,User user);

   public void update(KeyStore keyStore);

   public List<KeyStoreBean> query(User user);

   public void delete(KeyStoreBean keyStoreBean ,User user);

   public KeyStoreBean find(String name,User user) ;
}
