/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.database.cert;

import java.util.List;
import org.wsssoapbox.database.User;

/**
 *
 * @author Peter
 */
public interface KeyStoreDAO {

   public void insert(KeyStore keyStore);

   public void update(KeyStore keyStore);

   public List<KeyStore> query(User user);

   public void delete(KeyStore keyStore);

   public KeyStore find(String name,User user) ;
}
