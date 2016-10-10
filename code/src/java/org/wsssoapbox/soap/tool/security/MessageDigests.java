/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.soap.tool.security;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Peter
 */
public class MessageDigests {

   private static MessageDigest md;

   public static String digestMessage(String input, String algorithrm) throws NoSuchAlgorithmException, UnsupportedEncodingException {

      md = MessageDigest.getInstance(algorithrm);
      md.reset();
      byte[] data = input.getBytes("UTF-8");
      md.update(data);
      byte[] digests = md.digest();

      return byteToHexString(digests);

   }

   public static String digestMessage(String input, String salt, String algorithrm) throws NoSuchAlgorithmException, UnsupportedEncodingException {

      byte[] btyeSalt = salt.getBytes("UTF-8");
      byte[] data = input.getBytes("UTF-8");

      md = MessageDigest.getInstance(algorithrm);
      md.reset();
      md.update(btyeSalt);
      byte[] digests = md.digest(data);

      return byteToHexString(digests);
   }

   public static String digestMessage(String input, String salt, int time, String algorithrm) throws NoSuchAlgorithmException, UnsupportedEncodingException {

      byte[] btyeSalt = salt.getBytes("UTF-8");
      byte[] data = input.getBytes("UTF-8");

      md = MessageDigest.getInstance(algorithrm);
      md.reset();
      md.update(btyeSalt);
      md.digest(data);

      for (int i = 0; i < time; i++) {
         md.reset();
         data = md.digest(data);
      }
      return byteToHexString(data);
   }

   public static String digestMessage(URL url, String algorithrm) throws NoSuchAlgorithmException, IOException {

      md = MessageDigest.getInstance(algorithrm);
      md.reset();
      InputStream is = url.openStream();

      int n = 0;
      byte[] data = new byte[1024];
      while ((n = is.read(data)) != -1) {
         md.update(data, 0, n);
      }

      byte[] digests = md.digest();

      return byteToHexString(digests);
   }

   public static String digestMessage(InputStream is, String algorithrm) throws NoSuchAlgorithmException, IOException {

      md = MessageDigest.getInstance(algorithrm);
      md.reset();
      int n = 0;
      byte[] data = new byte[1024];
      while ((n = is.read(data)) != -1) {
         md.update(data, 0, n);
      }

      byte[] digests = md.digest();

      return byteToHexString(digests);
   }

   private static String byteToHexString(byte[] data) {
      StringBuilder painTextBuffer = new StringBuilder();
      for (int i = 0; i < data.length; i++) {
         painTextBuffer.append(Integer.toHexString((data[i] & 0xFF) | 0x100).substring(1, 3));
      }
      return painTextBuffer.toString();
   }

   public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException, MalformedURLException, IOException {

      System.out.println("**************  String *********");
      String chiperText = MessageDigests.digestMessage("peter", "MD5");
      System.out.println("MD5 Cipher Text :" + chiperText);

      chiperText = MessageDigests.digestMessage("peter", "SHA1");
      System.out.println("SHA1 Cipher Text :" + chiperText);


      System.out.println("**************  String Sault *********");
      chiperText = MessageDigests.digestMessage("peter", "1234", "MD5");
      System.out.println("MD5 Cipher Text :" + chiperText);

      chiperText = MessageDigests.digestMessage("peter", "1234", "SHA-1");
      System.out.println("SHA1 Cipher Text :" + chiperText);



      System.out.println("**************  String Sault time *********");
      chiperText = MessageDigests.digestMessage("peter", "1234", 1, "MD5");
      System.out.println("MD5 Cipher Text :" + chiperText);

      chiperText = MessageDigests.digestMessage("peter", "1234", 1, "SHA1");
      System.out.println("SHA1 Cipher Text :" + chiperText);

     
      System.out.println("**************  URL *********");
      URL url = new URL("http://www.java2s.com/Tutorial/Java/0060__Operators/TheinstanceofKeyword.htm");
      chiperText = MessageDigests.digestMessage(url, "MD5");
      System.out.println("MD5 Cipher Text :" + chiperText);
   
      /*  
      chiperText = MessageDigests.digestMessage(url, "SHA1");
      System.out.println("SHA1 Cipher Text :" + chiperText);
      
      System.out.println("**************  File *********");
      File file = new File("C:\\test.txt");
      chiperText = MessageDigests.digestMessage(file, "MD5");
      System.out.println("MD5 Cipher Text :" + chiperText);
      
      chiperText = MessageDigests.digestMessage(file, "SHA1");
      System.out.println("SHA1 Cipher Text :" + chiperText);
       */
   }
}
