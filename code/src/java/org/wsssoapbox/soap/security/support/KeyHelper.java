/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.soap.security.support;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Peter
 */
public class KeyHelper {

   private static final Logger logger = LoggerFactory.getLogger(KeyHelper.class);

   // This method adds a certificate with the specified alias to the specified keystore file.
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

   public static List<Certificate> getCertificates(KeyStore keystore) {
      try {
         List<Certificate> certs = new ArrayList<Certificate>();
         Enumeration keyStoreEnum = keystore.aliases();
         while (keyStoreEnum.hasMoreElements()) {
            String alias = (String) keyStoreEnum.nextElement();
            // Get certificate
            Certificate cert = keystore.getCertificate(alias);
            certs.add(cert);
         }
         return certs;
      } catch (KeyStoreException e) {
      }

      return null;
   }

   public static Certificate getCertificate(KeyStore keystore, String alias) {
      try {
         // Get certificate
         Certificate cert = keystore.getCertificate(alias);
         return cert;
      } catch (KeyStoreException e) {
      }
      return null;
   }

   public static KeyPair getPrivateKey(KeyStore keystore, String alias, char[] password) {
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

   public static List<String> getAliasNames(KeyStore keystore) throws KeyStoreException {
      List<String> aliasNames = new ArrayList<String>();
      try {
         // List the aliases
         Enumeration aliasEnum = keystore.aliases();
         while (aliasEnum.hasMoreElements()) {
            String alias = (String) aliasEnum.nextElement();
            aliasNames.add(alias);
         }
      } catch (KeyStoreException ex) {
         ex.printStackTrace();
      }
      return aliasNames;
   }

   public static String getAliasName(KeyStore keystore) throws KeyStoreException {
      Enumeration keyStoreEnum = keystore.aliases();
      while (keyStoreEnum.hasMoreElements()) {
         String alias = (String) keyStoreEnum.nextElement();
         boolean isKey = keystore.isKeyEntry(alias);
         // Does alias refer to a private key?
         if (isKey) {
            logger.debug("Key alias : " + alias);
            return alias;
         }
      }
      return null;
   }

   private void showAliasNames(KeyStore keystore) throws KeyStoreException {

      Enumeration keyStoreEnum = keystore.aliases();
      while (keyStoreEnum.hasMoreElements()) {
         String alias = (String) keyStoreEnum.nextElement();
         boolean b = keystore.isKeyEntry(alias);
         // Does alias refer to a private key?
         if (b) {
            logger.debug("Key alias : " + alias);
         }
         // Does alias refer to a trusted certificate?
         b = keystore.isCertificateEntry(alias);
         if (b) {
            logger.debug("Certificate alias : " + alias);
         }
      }
   }
}
