/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.soap.security.keystore.support;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wsssoapbox.bean.model.soap.security.keystore.KeyStoreBean;
import org.wsssoapbox.bean.model.soap.security.keystore.CertificateBean;

/**
 *
 * @author Peter
 */
public class KeyStoreHelper {

   private static final Logger logger = LoggerFactory.getLogger(KeyStoreHelper.class);
   private KeyStore keyStore;

   public KeyStoreHelper(InputStream is, String password) throws Exception {
      logger.debug("IS : " + is.available() + "  Byes");
      if (is.available() > 0) {
         keyStore = KeyStore.getInstance("JKS", "SUN");
         keyStore.load(is, password.toCharArray());
      }

   }

   public KeyStoreHelper(KeyStore keyStore) {
      this.keyStore = keyStore;
   }

   public KeyStoreHelper() {
   }
   
 

   public KeyStoreBean getKeyStore(InputStream is, String ksFileName, String password) {

      logger.debug("start public KeyStoreBean getKeyStore(InputStream is, String ksFileName, String password) ");
      KeyStoreBean keyStoreBean = new KeyStoreBean();

      try {

         keyStore = KeyStore.getInstance("JKS", "SUN");
         keyStore.load(is, password.toCharArray());
         is.reset();
         int lenght = is.available();
         logger.debug("Input Sream lenght :" + lenght);
         if (keyStore != null) {
            byte[] content = new byte[lenght];
            content = IOUtils.toByteArray(is);
            keyStoreBean.setContent(content);
            keyStoreBean.setName(ksFileName);
            keyStoreBean.setPassword(password);
            keyStoreBean.setKeyStore(keyStore);
            keyStoreBean.setType(keyStore.getType());
            keyStoreBean.setProvider(keyStore.getProvider().getName());
            keyStoreBean.setAliasName(getAliasName());
            keyStoreBean.setStrAliasNames(getAliasNames());

         }
      } catch (NoSuchProviderException ex) {
         ex.printStackTrace();
      } catch (CertificateException ex) {
         ex.printStackTrace();
      } catch (NoSuchAlgorithmException ex) {
         ex.printStackTrace();
      } catch (FileNotFoundException ex) {
         // Keystore does not exist
         ex.printStackTrace();
      } catch (KeyStoreException ex) {
         ex.printStackTrace();
      } catch (IOException ex) {
         ex.printStackTrace();
      
      }
      logger.debug("end public KeyStoreBean getKeyStore(InputStream is, String ksFileName, String password) ");
      return keyStoreBean;
   }

   public String getAliasName() throws KeyStoreException {
      if (getKeyStore() != null) {
         Enumeration keyStoreEnum = getKeyStore().aliases();
         while (keyStoreEnum.hasMoreElements()) {
            String alias = (String) keyStoreEnum.nextElement();
            boolean isKey = getKeyStore().isKeyEntry(alias);
            // Does alias refer to a private key?
            if (isKey) {
               logger.debug("Key alias : " + alias);
               return alias;
            }
         }
      }
      return null;
   }

   public List<String> getAliasNames() throws KeyStoreException {
      List<String> aliasNames = new ArrayList<String>();
      if (getKeyStore() != null) {
         try {
            // List the aliases
            Enumeration aliasEnum = getKeyStore().aliases();
            while (aliasEnum.hasMoreElements()) {
               String alias = (String) aliasEnum.nextElement();
               aliasNames.add(alias);
            }
         } catch (KeyStoreException ex) {
            ex.printStackTrace();
         }
      }
      return aliasNames;
   }

   public List<CertificateBean> getCertificates(KeyStore keyStore) throws KeyStoreException {
      List<CertificateBean> certBeans = new ArrayList<CertificateBean>();
      try {
         CertificateBean certBean = null;
         Enumeration keyStoreEnum = keyStore.aliases();
         while (keyStoreEnum.hasMoreElements()) {
            certBean = new CertificateBean();
            String alias = (String) keyStoreEnum.nextElement();
            logger.debug("All Alias : " + alias);
            // Does alias refer to a trusted certificate?
            boolean isCert = keyStore.isCertificateEntry(alias);
            if (isCert) {
               X509Certificate cert = (X509Certificate) keyStore.getCertificate(alias);
               CertificateHelperImpl certHelper = new CertificateHelperImpl(cert, alias);
               certBean.setAliasName(alias);
               logger.debug("Cert alias name " + certBean.getAliasName());
               certBean = certHelper.createCertificate();
               certBeans.add(certBean);
            }
            boolean isKey = keyStore.isKeyEntry(alias);
            if (isKey) {
               X509Certificate cert = (X509Certificate) keyStore.getCertificate(alias);
               CertificateHelperImpl certHelper = new CertificateHelperImpl(cert, alias);
               certBean.setAliasName(alias);
               logger.debug("Cert alias name " + certBean.getAliasName());
               certBean = certHelper.createCertificate();
               certBeans.add(certBean);
            }
         }
      } catch (Exception ex) {
         ex.printStackTrace();
      }
      return certBeans;
   }

   public CertificateBean getCertificate(KeyStore keyStore, String aliasName) throws KeyStoreException {
      CertificateBean certBean = null;
      try {
         Enumeration keyStoreEnum = keyStore.aliases();
         while (keyStoreEnum.hasMoreElements()) {
            certBean = new CertificateBean();
            String alias = (String) keyStoreEnum.nextElement();
            boolean isCert = keyStore.isCertificateEntry(alias);
            if (isCert) {
               X509Certificate cert = (X509Certificate) keyStore.getCertificate(alias);
               CertificateHelperImpl certHelper = new CertificateHelperImpl(cert);
               certBean = certHelper.createCertificate();
            }
         }
      } catch (Exception ex) {
         ex.printStackTrace();
      }

      return certBean;
   }

   public static void addToKeyStore(File keystoreFile, char[] keystorePassword,
           String alias, java.security.cert.Certificate cert) {
      try {
         // Create an empty keystore object
         KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());

         // Load the keystore contents
         FileInputStream in = new FileInputStream(keystoreFile);
         keystore.load(in, keystorePassword);
         in.close();

         // Add the certificate
         keystore.setCertificateEntry(alias, cert);

         // Save the new keystore contents
         FileOutputStream out = new FileOutputStream(keystoreFile);
         keystore.store(out, keystorePassword);
         out.close();
      } catch (java.security.cert.CertificateException e) {
      } catch (NoSuchAlgorithmException e) {
      } catch (FileNotFoundException e) {
         // Keystore does not exist
      } catch (KeyStoreException e) {
      } catch (IOException e) {
      }
   }

   public KeyPair getPrivateKey(KeyStore keystore, String alias, char[] password) {
      try {
         // Get private key
         Key key = keystore.getKey(alias, password);
         if (key instanceof PrivateKey) {
            // Get certificate of public key
            java.security.cert.Certificate cert = keystore.getCertificate(alias);

            // Get public key
            PublicKey publicKey = cert.getPublicKey();

            // Return a key pair
            return new KeyPair(publicKey, (PrivateKey) key);
         }
      } catch (UnrecoverableKeyException e) {
      } catch (NoSuchAlgorithmException e) {
      } catch (KeyStoreException e) {
      }
      return null;
   }

   /**
    * @return the keyStore
    */
   public KeyStore getKeyStore() {
      return keyStore;
   }

   /**
    * @param keyStore the keyStore to set
    */
   public void setKeyStore(KeyStore keyStore) {
      this.keyStore = keyStore;
   }
}
