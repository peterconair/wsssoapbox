/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.io.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 *
 * @author Peter
 */
public class IOUtil {

   public static void InputStreamToFile(InputStream inputStream, String fileName) {
      try {
         // write the inputStream to a FileOutputStream
         OutputStream out = new FileOutputStream(new File(fileName));
         int read = 0;
         byte[] bytes = new byte[1024];

         while ((read = inputStream.read(bytes)) != -1) {
            out.write(bytes, 0, read);
         }
         inputStream.close();
         out.flush();
         out.close();
         System.out.println("New file created!");
      } catch (IOException e) {
         System.out.println(e.getMessage());
      }
   }

   public static void ByteArrayToFile(byte[] content, String fileName) {
      try {
         FileOutputStream fos = new FileOutputStream(fileName);
         fos.write(content);
         fos.close();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   public static byte[] getByteArray(InputStream is) throws Exception {
      byte[] content = new byte[is.available()];
      is.read(content);
      is.close();
      return content;
   }

   public static void StringToFile(String content, String fileName) {
      try {

         File file = new File(fileName);
         //if file doesnt exists, then create it
         if (!file.exists()) {
            file.createNewFile();
         }
         FileWriter fw = new FileWriter(file.getName());
         BufferedWriter bw = new BufferedWriter(fw);
         bw.write(content);
         bw.close();
         System.out.println("Done");
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   public static InputStream StringToInputStream(String content) throws IOException {
      // convert String into InputStream
      InputStream is = new ByteArrayInputStream(content.getBytes());
      return is;
   }

   public static String ByteToString(byte[] content) throws IOException {
      //intilize an InputStream
      InputStream is = new ByteArrayInputStream(content);
      //read it with BufferedReader
      BufferedReader br = new BufferedReader(new InputStreamReader(is));
      StringBuilder sb = new StringBuilder();
      String line;
      while ((line = br.readLine()) != null) {
         sb.append(line);
      }
      br.close();
      return sb.toString();
   }

   public static byte[] readBytes(InputStream inputStream) throws IOException {

      byte[] binaryData = new byte[inputStream.available()];
      inputStream.read(binaryData);
      inputStream.close();
      return binaryData;
   }

   public static void copyStream(InputStream in, OutputStream out) throws IOException {
      final byte[] buffer = new byte[4096];
      while (true) {
         final int bytesRead = in.read(buffer);
         if (bytesRead == -1) {
            break;
         }
         out.write(buffer, 0, bytesRead);
      }
   }

   public static String InputStreamToString(String content) throws IOException {
      //intilize an InputStream
      InputStream is = new ByteArrayInputStream(content.getBytes());
      //read it with BufferedReader
      BufferedReader br = new BufferedReader(new InputStreamReader(is));
      StringBuilder sb = new StringBuilder();
      String line;
      while ((line = br.readLine()) != null) {
         sb.append(line);
      }
      br.close();
      return sb.toString();
   }

   public static void convertToHex(PrintStream out, File file) throws IOException {
      InputStream is = new FileInputStream(file);

      int bytesCounter = 0;
      int value = 0;
      StringBuilder sbHex = new StringBuilder();
      StringBuilder sbText = new StringBuilder();
      StringBuilder sbResult = new StringBuilder();

      while ((value = is.read()) != -1) {
         //convert to hex value with "X" formatter
         sbHex.append(String.format("%02X ", value));

         //If the chracater is not convertable, just print a dot symbol "." 
         if (!Character.isISOControl(value)) {
            sbText.append((char) value);
         } else {
            sbText.append(".");
         }

         //if 16 bytes are read, reset the counter, 
         //clear the StringBuilder for formatting purpose only.
         if (bytesCounter == 15) {
            sbResult.append(sbHex).append("      ").append(sbText).append("\n");
            sbHex.setLength(0);
            sbText.setLength(0);
            bytesCounter = 0;
         } else {
            bytesCounter++;
         }
      }

      //if still got content
      if (bytesCounter != 0) {
         //add spaces more formatting purpose only
         for (; bytesCounter < 16; bytesCounter++) {
            //1 character 3 spaces
            sbHex.append("   ");
         }
         sbResult.append(sbHex).append("      ").append(sbText).append("\n");
      }

      out.print(sbResult);
      is.close();
   }

   public static void FileSize(String fileName) {
      File file = new File(fileName);

      if (file.exists()) {

         double bytes = file.length();
         double kilobytes = (bytes / 1024);
         double megabytes = (kilobytes / 1024);
         double gigabytes = (megabytes / 1024);
         double terabytes = (gigabytes / 1024);
         double petabytes = (terabytes / 1024);
         double exabytes = (petabytes / 1024);
         double zettabytes = (exabytes / 1024);
         double yottabytes = (zettabytes / 1024);

         System.out.println("bytes : " + bytes);
         System.out.println("kilobytes : " + kilobytes);
         System.out.println("megabytes : " + megabytes);
         System.out.println("gigabytes : " + gigabytes);
         System.out.println("terabytes : " + terabytes);
         System.out.println("petabytes : " + petabytes);
         System.out.println("exabytes : " + exabytes);
         System.out.println("zettabytes : " + zettabytes);
         System.out.println("yottabytes : " + yottabytes);
      } else {
         System.out.println("File does not exists!");
      }

   }
}
