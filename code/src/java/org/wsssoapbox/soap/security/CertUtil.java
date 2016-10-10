/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.soap.security;

import com.sun.xml.wss.impl.misc.Base64;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.x509.SubjectKeyIdentifier;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;

/**
 *
 * @author Peter
 */
public class CertUtil {

   public static KeyStore loadKeyStore(String type, String keystoreFile, String password) throws Exception {
      KeyStore ks = KeyStore.getInstance(type);
      FileInputStream fis = new FileInputStream(keystoreFile);
      ks.load(fis, password.toCharArray());
      return ks;
   }

   public static KeyStore loadKeyStore(String type, InputStream is, String password) throws Exception {
      KeyStore ks = KeyStore.getInstance(type);
      ks.load(is, password.toCharArray());
      return ks;
   }

   public static String doFingerprint(byte[] certificateBytes, String algorithm) throws Exception {

      StringBuilder value = new StringBuilder();
      MessageDigest md = MessageDigest.getInstance(algorithm);
      md.update(certificateBytes);
      byte[] digest = md.digest();
      value.append(":");
      for (int i = 0; i < digest.length; i++) {
         if (i != 0) {
         }
         int b = digest[i] & 0xff;
         String hex = Integer.toHexString(b);
         if (hex.length() == 1) {
            value.append("0");
         }
         value.append(hex);
      }
      return value.toString().toUpperCase();
   }

   public static String hashToBase64(byte[] certificateBytes, String algorithm) throws NoSuchAlgorithmException {
      MessageDigest md = MessageDigest.getInstance(algorithm);
      md.update(certificateBytes);
      byte[] digest = md.digest();
      return Base64.encode(digest);
   }

   public static String byteToHex(byte[] srcByte) {

      StringBuilder value = new StringBuilder();
      for (int i = 0; i < srcByte.length; i++) {
         int b = srcByte[i] & 0xff;
         String hex = Integer.toHexString(b);
         if (hex.length() == 1) {
            value.append("0");
         }
         value.append(hex);
      }
      return value.toString();
   }

   public static String getSubjectKeyIdentity(X509Certificate certificate) throws IOException {
      // try to construct subjectkeyidentifer 
      ByteArrayInputStream bIn = new ByteArrayInputStream(certificate.getPublicKey().getEncoded());
      SubjectPublicKeyInfo info = new SubjectPublicKeyInfo((ASN1Sequence) new ASN1InputStream(bIn).readObject());
      SubjectKeyIdentifier identiy = new SubjectKeyIdentifier(info);
      return Base64.encode(identiy.getKeyIdentifier());
   }
}
