/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package security;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.BadPaddingException;
import java.security.InvalidAlgorithmParameterException;
import javax.crypto.IllegalBlockSizeException;
import java.security.NoSuchAlgorithmException;
import java.security.InvalidKeyException;
import javax.crypto.SecretKey;
import org.junit.Test;
import org.wsssoapbox.soap.tool.security.SymmetricEncryption;
import sun.misc.BASE64Encoder;

/**
 *
 * @author Peter
 */
public class SymmetricCipherTest {

   public SymmetricCipherTest() {
   }
   private static byte[] iv = {0x0a, 0x01, 0x02, 0x03, 0x04, 0x0b, 0x0c, 0x0d};

   @Test
   public void testSymmetricCipher() throws NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, InvalidAlgorithmParameterException, UnsupportedEncodingException {
      //  String xform = "DES/ECB/PKCS5Padding";
      String providerName = "SUNJCE";
      String engine = "SecretKey";
      String algorithm = "AES";
      String mode = "ECB";
      String padding = "PKCS5Padding";
      String xform = algorithm + "/" + mode + "/" + padding;

      int keysize = 128;

      String inputString = "ก 1 2 3 4 5 a b A ฮ ";
      // Generate a secret key
      //   KeyGenerator kg = KeyGenerator.getInstance("DES");
      KeyGenerator kg = KeyGenerator.getInstance(algorithm);
      kg.init(keysize); // 56 iinputStrings the keysize. Fixed for DES
      SecretKey key = kg.generateKey();

      System.out.println("Key Algorithm : " + key.getAlgorithm());
      System.out.println("Key Format: " + key.getFormat());
      System.out.println("Key Bytes : " + key.getEncoded());
      System.out.println("");

      byte[] dataBytes = inputString.getBytes("UTF-8");


      System.out.println("Input Data String: " + inputString);
      System.out.println("Input Data HEX : " + SymmetricEncryption.byteToHexString(dataBytes));
      System.out.println("Input Data Base64 : " + new BASE64Encoder().encode(dataBytes));
      System.out.println("Input Data Byte : " + dataBytes);

      System.out.println("");
      byte[] encBytes = SymmetricEncryption.encrypt(dataBytes, key, xform);

      System.out.println("Encrypted Data String : " + new String(encBytes));
      System.out.println("Encrypted Data HEX : " + SymmetricEncryption.byteToHexString(encBytes));
      System.out.println("Encrypted Data Base64 : " + new BASE64Encoder().encode(encBytes));
      System.out.println("Encrypted Data Byte : " + encBytes);
      System.out.println("");
      byte[] decBytes = SymmetricEncryption.decrypt(encBytes, key, xform);

      System.out.println("Decrypted Data String : " + new String(decBytes));
      System.out.println("Decrypted Data HEX: " + SymmetricEncryption.byteToHexString(decBytes));
      System.out.println("Decrypted Data Base64 : " + new BASE64Encoder().encode(decBytes));
      System.out.println("Decrypted Data Byte : " + decBytes);
      System.out.println("");
      boolean expected = java.util.Arrays.equals(dataBytes, decBytes);
      System.out.println("Test " + (expected ? "SUCCEEDED!" : "FAILED!"));
   }

   // @Test
   public void testSymmetricCipher1() throws NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, InvalidAlgorithmParameterException, UnsupportedEncodingException {
      //  String xform = "DES/ECB/PKCS5Padding";
      String providerName = "SUNJCE";
      String engine = "SecretKey";
      String algorithm = "AES";
      String mode = "ECB";
      String padding = "PKCS5Padding";
      String xform = algorithm + "/" + mode + "/" + padding;

      int keysize = 128;

      String inputString = "0 1 2 3 4 5 a b A 0 ";
      // Generate a secret key
      //   KeyGenerator kg = KeyGenerator.getInstance("DES");
      KeyGenerator kg = KeyGenerator.getInstance(algorithm);
      kg.init(keysize); // 56 iinputStrings the keysize. Fixed for DES
      SecretKey key = kg.generateKey();

      System.out.println("Key Algorithm : " + key.getAlgorithm());
      System.out.println("Key Format: " + key.getFormat());
      System.out.println("Key Bytes : " + key.getEncoded());
      System.out.println("");

      byte[] dataBytes = inputString.getBytes("UTF-8");


      System.out.println("Input Data String: " + inputString);
      System.out.println("Input Data HEX : " + SymmetricEncryption.byteToHexString(dataBytes));
      System.out.println("Input Data Base64 : " + new BASE64Encoder().encode(dataBytes));
      System.out.println("Input Data Byte : " + dataBytes);

      System.out.println("");
      byte[] encBytes = SymmetricEncryption.encrypt(inputString, key, xform);

      System.out.println("Encrypted Data String : " + new String(encBytes));
      System.out.println("Encrypted Data HEX : " + SymmetricEncryption.byteToHexString(encBytes));
      System.out.println("Encrypted Data Base64 : " + new BASE64Encoder().encode(encBytes));
      System.out.println("Encrypted Data Byte : " + encBytes);
      System.out.println("");

      String cipherString = new String(encBytes);
      byte[] decBytes = SymmetricEncryption.decrypt(cipherString, key, xform);

      System.out.println("Decrypted Data String : " + new String(decBytes));
      System.out.println("Decrypted Data HEX: " + SymmetricEncryption.byteToHexString(decBytes));
      System.out.println("Decrypted Data Base64 : " + new BASE64Encoder().encode(decBytes));
      System.out.println("Decrypted Data Byte : " + decBytes);
      System.out.println("");
      boolean expected = java.util.Arrays.equals(dataBytes, decBytes);
      System.out.println("Test " + (expected ? "SUCCEEDED!" : "FAILED!"));
   }

 //  @Test
   public void testInOutStream() throws NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, InvalidAlgorithmParameterException, UnsupportedEncodingException, FileNotFoundException {
      //  String xform = "DES/ECB/PKCS5Padding";
      String providerName = "SUNJCE";
      String engine = "SecretKey";
      String algorithm = "AES";
      String mode = "ECB";
      String padding = "PKCS5Padding";
      String xform = algorithm + "/" + mode + "/" + padding;

      int keysize = 128;

      // Generate a secret key
      //   KeyGenerator kg = KeyGenerator.getInstance("DES");
      KeyGenerator kg = KeyGenerator.getInstance(algorithm);
      kg.init(keysize); // 56 iinputStrings the keysize. Fixed for DES
      SecretKey key = kg.generateKey();


      System.out.println("Key Algorithm : " + key.getAlgorithm());
      System.out.println("Key Format: " + key.getFormat());
      System.out.println("Key Bytes : " + key.getEncoded());
      System.out.println("");

      // Encrypt
      SymmetricEncryption.encrypt(new FileInputStream("C:/test.txt"), new FileOutputStream("C:/testEncrypted.txt"), key, xform);
      // Decrypt
      SymmetricEncryption.decrypt(new FileInputStream("c:/testEncrypted.txt"), new FileOutputStream("c:/testDecrypted.txt"), key, xform);

      
         // Encrypt
      SymmetricEncryption.encrypt("C:/test1.txt","C:/testEncrypted1.txt", key, xform);
      // Decrypt
      SymmetricEncryption.decrypt("c:/testEncrypted1.txt","c:/testDecrypted1.txt", key, xform);
      
   }
   
     //@Test
   public void testAllAlgorithmCipher() throws NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, InvalidAlgorithmParameterException, UnsupportedEncodingException {
      //  String xform = "DES/ECB/PKCS5Padding";
      String providerName = "SUNJCE";
      String engine = "SecretKey";
      String algorithm = "AES";
      String mode = "ECB";
      String padding = "PKCS5Padding";
      String xform = algorithm + "/" + mode + "/" + padding;

      int keysize = 128;

      String inputString = "ก 1 2 3 4 5 a b A ฮ ";
      // Generate a secret key
      //   KeyGenerator kg = KeyGenerator.getInstance("DES");
      KeyGenerator kg = KeyGenerator.getInstance(algorithm);
      kg.init(keysize); // 56 iinputStrings the keysize. Fixed for DES
      SecretKey key = kg.generateKey();

      System.out.println("Key Algorithm : " + key.getAlgorithm());
      System.out.println("Key Format: " + key.getFormat());
      System.out.println("Key Bytes : " + key.getEncoded());
      System.out.println("");

      byte[] dataBytes = inputString.getBytes("UTF-8");


      System.out.println("Input Data String: " + inputString);
      System.out.println("Input Data HEX : " + SymmetricEncryption.byteToHexString(dataBytes));
      System.out.println("Input Data Base64 : " + new BASE64Encoder().encode(dataBytes));
      System.out.println("Input Data Byte : " + dataBytes);

      System.out.println("");
      byte[] encBytes = SymmetricEncryption.encrypt(dataBytes, key, xform);

      System.out.println("Encrypted Data String : " + new String(encBytes));
      System.out.println("Encrypted Data HEX : " + SymmetricEncryption.byteToHexString(encBytes));
      System.out.println("Encrypted Data Base64 : " + new BASE64Encoder().encode(encBytes));
      System.out.println("Encrypted Data Byte : " + encBytes);
      System.out.println("");
      byte[] decBytes = SymmetricEncryption.decrypt(encBytes, key, xform);

      System.out.println("Decrypted Data String : " + new String(decBytes));
      System.out.println("Decrypted Data HEX: " + SymmetricEncryption.byteToHexString(decBytes));
      System.out.println("Decrypted Data Base64 : " + new BASE64Encoder().encode(decBytes));
      System.out.println("Decrypted Data Byte : " + decBytes);
      System.out.println("");
      boolean expected = java.util.Arrays.equals(dataBytes, decBytes);
      System.out.println("Test " + (expected ? "SUCCEEDED!" : "FAILED!"));
   }
   
   
}
