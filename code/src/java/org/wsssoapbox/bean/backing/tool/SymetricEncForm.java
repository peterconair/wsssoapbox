/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.bean.backing.tool;

import com.sun.xml.wss.impl.misc.Base64;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.wsssoapbox.soap.tool.security.MessageDigests;
import org.wsssoapbox.soap.tool.security.Providers;
import org.wsssoapbox.soap.tool.security.SymmetricEncryption;

/**
 *
 * @author Peter
 */
@ManagedBean(name = "symetricEncForm")
@SessionScoped
public class SymetricEncForm implements Serializable {

   private static final long serialVersionUID = -3926886272959051623L;
   private Map<String, String> providers = new LinkedHashMap<String, String>();
   private String provider;
   private Map<String, String> services = new LinkedHashMap<String, String>();
   private String service;
   private Map<String, String> algorithms = new LinkedHashMap<String, String>();
   private String algorithm;
   private Map<String, String> options = new LinkedHashMap<String, String>();
   private String option = "1";
   private String input;
   private String inputHex;
   private String inputBase64;
   private String inputByte;
   private String inputEncrypted;
   private String hexEncrypted;
   private String base64Encrypted;
   private String byteEncrypted;
   private String outputDecrypted;
   private String hexDecrypted;
   private String base64Decrypted;
   private String byteDecrypted;
   private int inputLenght;
   private int outputLenght;
   private String saltValue;
   private int time = 1;
   private String url;
   private long urlLength;
   private String keyValue;
   private String mode;
   private Map<String,String> modes;
   private Map<String,String> keySizes;
   private int keySize;

   public SymetricEncForm() {
      try {

         options.put("1", "Simple Encode");
         options.put("2", "Encode with Salt");
         options.put("3", "Encode with Salt n time");
         options.put("3", "Encode with File");

         providers = Providers.getProviderNames();
         services = new LinkedHashMap<String, String>();
         algorithms = new LinkedHashMap<String, String>();

         provider = "SunJCE";
         service = "Cipher";
         if (algorithm == null || algorithm.equals("")) {
            algorithm = "AES";
         }


         SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
         saltValue = "" + random.nextInt(100000);
      } catch (NoSuchAlgorithmException ex) {
         ex.printStackTrace();
         Logger.getLogger(SymetricEncForm.class.getName()).log(Level.SEVERE, null, ex);
      }
   }

   public void handleProviderChange() {
      if (provider != null && !provider.equals("")) {
         services = Providers.getServices(provider);
         algorithms = new HashMap<String, String>();
      } else {
         services = new HashMap<String, String>();
      }
   }

   public void handleServiceChange() {
      if (service != null && !service.equals("")) {
         algorithms = Providers.getAlgorithmsWithProvider(provider, service);
      } else {
         algorithms = new HashMap<String, String>();
      }
   }

   public void encrypt() {

      try {

         System.out.println("input : " + getInput());
         System.out.println("Salt : " + getSaltValue());
         System.out.println("Algorithm : " + getAlgorithm());


         String mode = "ECB";
         String padding = "PKCS5Padding";
         String xform = algorithm + "/" + mode + "/" + padding;
         int keysize = 128;
         //  String input = "ก 1 2 3 4 5 a b A ฮ ";


         KeyGenerator kg = KeyGenerator.getInstance(algorithm);
         kg.init(keysize); // 56 iinputStrings the keysize. Fixed for DES
         SecretKey key = kg.generateKey();

         
         if (getInput() != null || getInput().equals("")) {
            if (getOption().equals("1")) {
               try {

                  byte[] inputByte = input.getBytes("UTF-8");
                  inputHex = SymmetricEncryption.byteToHexString(inputByte);
                  inputBase64 = Base64.encode(inputByte);


                  byte[] encryptedByte = SymmetricEncryption.encrypt(inputByte, key, algorithm);

                  inputEncrypted = new String(encryptedByte, "UTF-8");
                  hexEncrypted = SymmetricEncryption.byteToHexString(encryptedByte);
                  base64Encrypted = Base64.encode(encryptedByte);

                  byte[] decryptByte = SymmetricEncryption.decrypt(encryptedByte, key, algorithm);

                  outputDecrypted = new String(decryptByte, "UTF-8");
                  hexDecrypted = SymmetricEncryption.byteToHexString(decryptByte);
                  base64Decrypted = Base64.encode(decryptByte);

                  if (input.equals("") || input == null) {
                     inputHex = "";
                     inputBase64 = "";

                     inputEncrypted = "";
                     hexEncrypted = "";
                     base64Encrypted = "";

                     outputDecrypted = "";
                     hexDecrypted = "";
                     base64Decrypted = "";
                  }
               } catch (InvalidKeyException ex) {
                  Logger.getLogger(SymetricEncForm.class.getName()).log(Level.SEVERE, null, ex);
               } catch (IllegalBlockSizeException ex) {
                  Logger.getLogger(SymetricEncForm.class.getName()).log(Level.SEVERE, null, ex);
               } catch (NoSuchPaddingException ex) {
                  Logger.getLogger(SymetricEncForm.class.getName()).log(Level.SEVERE, null, ex);
               } catch (BadPaddingException ex) {
                  Logger.getLogger(SymetricEncForm.class.getName()).log(Level.SEVERE, null, ex);
               } catch (InvalidAlgorithmParameterException ex) {
                  Logger.getLogger(SymetricEncForm.class.getName()).log(Level.SEVERE, null, ex);
               }

            } else if (getOption().equals("2")) {
               outputDecrypted = MessageDigests.digestMessage(getInput(), getSaltValue(), getAlgorithm());
            } else if (getOption().equals("3")) {
               outputDecrypted = MessageDigests.digestMessage(getInput(), getSaltValue(), getTime(), getAlgorithm());
            } else if (getOption().equals("4")) {
               if (!url.equals("") || getOption() != null) {
                  URL urlAddress = new URL(getUrl());
                  setUrlLength(urlAddress.getContent().toString().length());
                  outputDecrypted = MessageDigests.digestMessage(urlAddress, getAlgorithm());
               }
            }
         }
         setInputLenght(getInput().length());
         setOutputLenght(outputDecrypted.length());
         if (getInput().length() == 0) {
            outputDecrypted = ("");
            setOutputLenght(0);
         }
         System.out.println("Message Digest : " + outputDecrypted);
      } catch (MalformedURLException ex) {
         Logger.getLogger(SymetricEncForm.class.getName()).log(Level.SEVERE, null, ex);
      } catch (NoSuchAlgorithmException ex) {
         Logger.getLogger(SymetricEncForm.class.getName()).log(Level.SEVERE, null, ex);
      } catch (UnsupportedEncodingException ex) {
         Logger.getLogger(SymetricEncForm.class.getName()).log(Level.SEVERE, null, ex);
      } catch (IOException ex) {
         Logger.getLogger(SymetricEncForm.class.getName()).log(Level.SEVERE, null, ex);
      }
   }

   public void optionsChangeListener() {
      System.out.println("You selected : " + option);
   }

   public void calculateActionListener() {
   }

   /**
    * @return the algorithms
    */
   public Map<String, String> getAlgorithms() {
      return algorithms;
   }

   /**
    * @param algorithms the algorithms to set
    */
   public void setAlgorithms(Map<String, String> algorithms) {
      this.algorithms = algorithms;
   }

   /**
    * @return the algorithm
    */
   public String getAlgorithm() {
      return algorithm;
   }

   /**
    * @param algorithm the algorithm to set
    */
   public void setAlgorithm(String algorithm) {
      this.algorithm = algorithm;
   }

   /**
    * @return the providers
    */
   public Map<String, String> getProviders() {
      return providers;
   }

   /**
    * @param providers the providers to set
    */
   public void setProviders(Map<String, String> providers) {
      this.providers = providers;
   }

   /**
    * @return the provider
    */
   public String getProvider() {
      return provider;
   }

   /**
    * @param provider the provider to set
    */
   public void setProvider(String provider) {
      this.provider = provider;
   }

   /**
    * @return the services
    */
   public Map<String, String> getServices() {
      return services;
   }

   /**
    * @param services the services to set
    */
   public void setServices(Map<String, String> services) {
      this.services = services;
   }

   /**
    * @return the service
    */
   public String getService() {
      return service;
   }

   /**
    * @param service the service to set
    */
   public void setService(String service) {
      this.service = service;
   }

   /**
    * @return the options
    */
   public Map<String, String> getOptions() {
      return options;
   }

   /**
    * @param options the options to set
    */
   public void setOptions(Map<String, String> options) {
      this.options = options;
   }

   /**
    * @return the option
    */
   public String getOption() {
      return option;
   }

   /**
    * @param option the option to set
    */
   public void setOption(String option) {
      this.option = option;
   }

   /**
    * @return the input
    */
   public String getInput() {
      return input;
   }

   /**
    * @param input the input to set
    */
   public void setInput(String input) {
      this.input = input;
   }

   /**
    * @return the inputLenght
    */
   public int getInputLenght() {
      return inputLenght;
   }

   /**
    * @param inputLenght the inputLenght to set
    */
   public void setInputLenght(int inputLenght) {
      this.inputLenght = inputLenght;
   }

   /**
    * @return the outputLenght
    */
   public int getOutputLenght() {
      return outputLenght;
   }

   /**
    * @param outputLenght the outputLenght to set
    */
   public void setOutputLenght(int outputLenght) {
      this.outputLenght = outputLenght;
   }

   /**
    * @return the saltValue
    */
   public String getSaltValue() {
      return saltValue;
   }

   /**
    * @param saltValue the saltValue to set
    */
   public void setSaltValue(String saltValue) {
      this.saltValue = saltValue;
   }

   /**
    * @return the time
    */
   public int getTime() {
      return time;
   }

   /**
    * @param time the time to set
    */
   public void setTime(int time) {
      this.time = time;
   }

   /**
    * @return the url
    */
   public String getUrl() {
      return url;
   }

   /**
    * @param url the url to set
    */
   public void setUrl(String url) {
      this.url = url;
   }

   /**
    * @return the urlLength
    */
   public long getUrlLength() {
      return urlLength;
   }

   /**
    * @param urlLength the urlLength to set
    */
   public void setUrlLength(long urlLength) {
      this.urlLength = urlLength;
   }

   /**
    * @return the keyValue
    */
   public String getKeyValue() {
      return keyValue;
   }

   /**
    * @param keyValue the keyValue to set
    */
   public void setKeyValue(String keyValue) {
      this.keyValue = keyValue;
   }

   /**
    * @return the keySize
    */
   public int getKeySize() {
      return keySize;
   }

   /**
    * @param keySize the keySize to set
    */
   public void setKeySize(int keySize) {
      this.keySize = keySize;
   }

   /**
    * @return the inputHex
    */
   public String getInputHex() {
      return inputHex;
   }

   /**
    * @param inputHex the inputHex to set
    */
   public void setInputHex(String inputHex) {
      this.inputHex = inputHex;
   }

   /**
    * @return the inputBase64
    */
   public String getInputBase64() {
      return inputBase64;
   }

   /**
    * @param inputBase64 the inputBase64 to set
    */
   public void setInputBase64(String inputBase64) {
      this.inputBase64 = inputBase64;
   }

   /**
    * @return the inputByte
    */
   public String getInputByte() {
      return inputByte;
   }

   /**
    * @param inputByte the inputByte to set
    */
   public void setInputByte(String inputByte) {
      this.inputByte = inputByte;
   }

   /**
    * @return the inputEncrypted
    */
   public String getInputEncrypted() {
      return inputEncrypted;
   }

   /**
    * @param inputEncrypted the inputEncrypted to set
    */
   public void setInputEncrypted(String inputEncrypted) {
      this.inputEncrypted = inputEncrypted;
   }

   /**
    * @return the hexEncrypted
    */
   public String getHexEncrypted() {
      return hexEncrypted;
   }

   /**
    * @param hexEncrypted the hexEncrypted to set
    */
   public void setHexEncrypted(String hexEncrypted) {
      this.hexEncrypted = hexEncrypted;
   }

   /**
    * @return the base64Encrypted
    */
   public String getBase64Encrypted() {
      return base64Encrypted;
   }

   /**
    * @param base64Encrypted the base64Encrypted to set
    */
   public void setBase64Encrypted(String base64Encrypted) {
      this.base64Encrypted = base64Encrypted;
   }

   /**
    * @return the byteEncrypted
    */
   public String getByteEncrypted() {
      return byteEncrypted;
   }

   /**
    * @param byteEncrypted the byteEncrypted to set
    */
   public void setByteEncrypted(String byteEncrypted) {
      this.byteEncrypted = byteEncrypted;
   }

   /**
    * @return the outputDecrypted
    */
   public String getOutputDecrypted() {
      return outputDecrypted;
   }

   /**
    * @param outputDecrypted the outputDecrypted to set
    */
   public void setOutputDecrypted(String outputDecrypted) {
      this.outputDecrypted = outputDecrypted;
   }

   /**
    * @return the hexDecrypted
    */
   public String getHexDecrypted() {
      return hexDecrypted;
   }

   /**
    * @param hexDecrypted the hexDecrypted to set
    */
   public void setHexDecrypted(String hexDecrypted) {
      this.hexDecrypted = hexDecrypted;
   }

   /**
    * @return the base64Decrypted
    */
   public String getBase64Decrypted() {
      return base64Decrypted;
   }

   /**
    * @param base64Decrypted the base64Decrypted to set
    */
   public void setBase64Decrypted(String base64Decrypted) {
      this.base64Decrypted = base64Decrypted;
   }

   /**
    * @return the byteDecrypted
    */
   public String getByteDecrypted() {
      return byteDecrypted;
   }

   /**
    * @param byteDecrypted the byteDecrypted to set
    */
   public void setByteDecrypted(String byteDecrypted) {
      this.byteDecrypted = byteDecrypted;
   }

   /**
    * @return the mode
    */
   public String getMode() {
      return mode;
   }

   /**
    * @param mode the mode to set
    */
   public void setMode(String mode) {
      this.mode = mode;
   }

   /**
    * @return the modes
    */
   public Map<String,String> getModes() {
      return modes;
   }

   /**
    * @param modes the modes to set
    */
   public void setModes(Map<String,String> modes) {
      this.modes = modes;
   }

   /**
    * @return the keySizes
    */
   public Map<String,String> getKeySizes() {
      return keySizes;
   }

   /**
    * @param keySizes the keySizes to set
    */
   public void setKeySizes(Map<String,String> keySizes) {
      this.keySizes = keySizes;
   }
}
