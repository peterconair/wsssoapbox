/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.soap.tool.security;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 *
 * @author Peter
 */
public class SymmetricEncryption implements Encryption {

   public static byte[] encrypt(String inputString, SecretKey key, String algorithm) throws
           NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException,
           NoSuchPaddingException, BadPaddingException, InvalidAlgorithmParameterException, UnsupportedEncodingException {

      byte[] inputBytes = inputString.getBytes("UTF-8");
      Cipher cipher = Cipher.getInstance(algorithm);
      cipher.init(Cipher.ENCRYPT_MODE, key);
      return cipher.doFinal(inputBytes);
   }

   public static byte[] decrypt(String cipherString, SecretKey key, String algorithm) throws
           NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException,
           NoSuchPaddingException, BadPaddingException, InvalidAlgorithmParameterException {
      byte[] cipherBytes = cipherString.getBytes();
      Cipher cipher = Cipher.getInstance(algorithm);
      cipher.init(Cipher.DECRYPT_MODE, key);
      return cipher.doFinal(cipherBytes);
   }

   public static byte[] encrypt(byte[] inputBytes, SecretKey key, String algorithm) throws
           NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException,
           NoSuchPaddingException, BadPaddingException, InvalidAlgorithmParameterException, UnsupportedEncodingException {
      Cipher cipher = Cipher.getInstance(algorithm);
      cipher.init(Cipher.ENCRYPT_MODE, key);
      return cipher.doFinal(inputBytes);
   }

   public static byte[] decrypt(byte[] cipherBytes, SecretKey key, String algorithm) throws
           NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException,
           NoSuchPaddingException, BadPaddingException, InvalidAlgorithmParameterException {
      Cipher cipher = Cipher.getInstance(algorithm);
      cipher.init(Cipher.DECRYPT_MODE, key);
      return cipher.doFinal(cipherBytes);
   }

   public static void encrypt(InputStream in, OutputStream out, SecretKey key, String algorithm) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
      try {
         Cipher cipher = Cipher.getInstance(algorithm);
         cipher.init(Cipher.ENCRYPT_MODE, key);
         // Bytes written to out will be encrypted
         out = new CipherOutputStream(out, cipher);
         byte[] buf = new byte[1024];
         // Read in the cleartext bytes and write to out to encrypt
         int numRead = 0;
         while ((numRead = in.read(buf)) >= 0) {
            out.write(buf, 0, numRead);
         }
         out.close();

      } catch (java.io.IOException e) {
         e.printStackTrace();
      }
   }

   public static void encrypt(String inputFile, String outputFile, SecretKey key, String algorithm) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
      try {

         InputStream in = new FileInputStream(new File(inputFile));
         OutputStream out = new FileOutputStream(new File(outputFile));

         Cipher cipher = Cipher.getInstance(algorithm);
         cipher.init(Cipher.ENCRYPT_MODE, key);
         // Bytes written to out will be encrypted
         out = new CipherOutputStream(out, cipher);
         byte[] buf = new byte[1024];
         // Read in the cleartext bytes and write to out to encrypt
         int numRead = 0;
         while ((numRead = in.read(buf)) >= 0) {
            out.write(buf, 0, numRead);
         }
         out.close();
      } catch (java.io.IOException e) {
         e.printStackTrace();
      }
   }

   public static OutputStream decrypt(InputStream in, OutputStream out, SecretKey key, String algorithm) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
      try {
         Cipher deCipher = Cipher.getInstance(algorithm);
         deCipher.init(Cipher.DECRYPT_MODE, key);

         // Bytes read from in will be decrypted
         in = new CipherInputStream(in, deCipher);

         byte[] buf = new byte[1024];
         // Read in the decrypted bytes and write the cleartext to out
         int numRead = 0;
         while ((numRead = in.read(buf)) >= 0) {
            out.write(buf, 0, numRead);
         }
         return out;

      } catch (java.io.IOException e) {
         e.printStackTrace();
         return null;
      } finally {
         try {
            out.close();
         } catch (IOException ex) {
            Logger.getLogger(SymmetricEncryption.class.getName()).log(Level.SEVERE, null, ex);
         }
      }
   }

   public static void decrypt(String inputFile, String outputFile, SecretKey key, String algorithm) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
      try {
         Cipher deCipher = Cipher.getInstance(algorithm);
         deCipher.init(Cipher.DECRYPT_MODE, key);


         InputStream in = new FileInputStream(new File(inputFile));
         OutputStream out = new FileOutputStream(new File(outputFile));

         // Bytes read from in will be decrypted
         in = new CipherInputStream(in, deCipher);

         byte[] buf = new byte[1024];
         // Read in the decrypted bytes and write the cleartext to out
         int numRead = 0;
         while ((numRead = in.read(buf)) >= 0) {
            out.write(buf, 0, numRead);
         }
         out.close();
      } catch (java.io.IOException e) {
         e.printStackTrace();
      }
   }

   public static String byteToHexString(byte[] data) {
      StringBuilder painTextBuffer = new StringBuilder();
      for (int i = 0; i < data.length; i++) {
         painTextBuffer.append(Integer.toHexString((data[i] & 0xFF) | 0x100).substring(1, 3));
      }
      return painTextBuffer.toString();
   }

   public static byte[] hexToByte(String hexString) {
      int len = hexString.length();
      byte[] ba = new byte[len / 2];
      for (int i = 0; i < len; i += 2) {
         ba[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4) + Character.digit(hexString.charAt(i + 1), 16));
      }
      return ba;
   }
}
