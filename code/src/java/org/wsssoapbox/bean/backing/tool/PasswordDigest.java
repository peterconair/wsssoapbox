/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.bean.backing.tool;

import com.sun.xml.wss.impl.misc.Base64;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.xml.crypto.dsig.DigestMethod;

/**
 *
 * @author Peter
 */
public class PasswordDigest {

   public static String getSHA1Hex(String text)
           throws NoSuchAlgorithmException, UnsupportedEncodingException {
      MessageDigest md;
      md = MessageDigest.getInstance(DigestMethod.SHA1);

      md.update(text.getBytes("UTF-8"), 0, text.length());
      byte[] sha1hash = md.digest();
      System.out.println("**************");
      System.out.println("Digest is " + sha1hash.length * 8 + " bits, " + sha1hash.length + " bytes long.");
      String encodedText = convertToHex(sha1hash);
      System.out.println("Hex encoded : ");
      System.out.println("Digest is " + encodedText.length() * 8 + " bits, " + encodedText.length() + " bytes long.");
      return encodedText;

   }

   public static byte[] getSHA1(String text)
           throws NoSuchAlgorithmException, UnsupportedEncodingException {
      MessageDigest md;
      md = MessageDigest.getInstance(DigestMethod.SHA1);

      md.update(text.getBytes("UTF-8"), 0, text.length());
      byte[] sha1hash = md.digest();
      System.out.println("**************");
      System.out.println(" Digest is " + sha1hash.length * 8 + " bits, " + sha1hash.length + " bytes long.");
      return sha1hash;
   }

   public String getSHA1Base64(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {

      MessageDigest md = MessageDigest.getInstance(DigestMethod.SHA1);

      md.update(text.getBytes("UTF-8"), 0, text.length());
      byte[] sha1hash = md.digest();
      System.out.println("**************");
      System.out.println("Digest is " + sha1hash.length * 8 + " bits, " + sha1hash.length + " bytes long.");
      String encodedText = Base64.encode(sha1hash);
      System.out.println("Base64 encoded : ");
      System.out.println("Digest is " + encodedText.length() * 8 + " bits, " + encodedText.length() + " bytes long.");
      return encodedText;
   }

   private static String convertToHex(byte[] data) {
      StringBuilder buf = new StringBuilder();
      for (int i = 0; i < data.length; i++) {
         int halfbyte = (data[i] >>> 4) & 0x0F;
         int two_halfs = 0;
         do {
            if ((0 <= halfbyte) && (halfbyte <= 9)) {
               buf.append((char) ('0' + halfbyte));
            } else {
               buf.append((char) ('a' + (halfbyte - 10)));
            }
            halfbyte = data[i] & 0x0F;
         } while (two_halfs++ < 1);
      }
      return buf.toString();
   }

   public static String generatePasswordDigest(String nonce, String created, String secret) throws NoSuchAlgorithmException {


      String password;

      nonce = nonce == null ? "" : nonce;
      created = created == null ? "" : created;

      String beforeEncryption = nonce + created + secret;
      System.out.println("before encryption, encoding: " + beforeEncryption);

      MessageDigest md = MessageDigest.getInstance("SHA1");
      // should be 20 bytes, 160 bits long

      try {
         md.reset();
         byte[] toEncrypt = beforeEncryption.getBytes("UTF-8");
         md.update(toEncrypt, 0, toEncrypt.length);

      } catch (UnsupportedEncodingException uee) {
         throw new RuntimeException(uee);
      }

      byte[] encryptedRaw = md.digest();
      System.out.println("**************");
      System.out.println("HEX  : " + convertToHex(encryptedRaw));
      System.out.println("Digest is " + encryptedRaw.length * 8 + " bits, " + encryptedRaw.length + " bytes long.");

      password = Base64.encode(encryptedRaw);
      System.out.println("Base64 encoded : ");
      System.out.println("Digest is " + password.length() * 8 + " bits, " + password.length() + " bytes long.");
      return password;

   }
}
