/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.bean.backing.tool;

import com.sun.xml.wss.impl.misc.Base64;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.wsssoapbox.soap.tool.security.MessageDigests;

/**
 *
 * @author Peter
 */
@ManagedBean(name = "hashingForm")
@SessionScoped
public class HashingForm implements Serializable {

   private static final long serialVersionUID = -3926886272959051623L;
   private Map<String, String> algorithms = new HashMap<String, String>();
   private String algorithm;
   private String option = "1";
   private String input;
   private String inputURL;
   private String inputFile;
   private String inputBase64;
   private int inputBase64Length;
   private String output;
   private String outputBase64;
   private int outputBase64Length;
   private int inputLength;
   private int outputLength;
   private String saltValue;
   private int time = 1;

   public HashingForm() {
      try {

       //  algorithms = Providers.getAlgorithmsWithProvider("SUN", "MessageDigest");

          algorithms.put("SHA-1", "SHA-1");
         System.out.println(algorithms);
         SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
         saltValue = "" + random.nextInt(100000);
      } catch (NoSuchAlgorithmException ex) {
         Logger.getLogger(HashingForm.class.getName()).log(Level.SEVERE, null, ex);
      }
   }

   public void hash() {
      FacesContext fc = FacesContext.getCurrentInstance();

      try {
         System.out.println("input : " + input);
         System.out.println("input URL : " + inputURL);
         System.out.println("input File : " + inputFile);
         System.out.println("Salt : " + saltValue);
         System.out.println("Algorithm : " + algorithm);
         System.out.println("Time : " + time);


         if (algorithm != null) {
            if (input != null && option.equals("1")) {
               output = MessageDigests.digestMessage(input, algorithm);
               calculateInputLength();
            } else if (input != null && option.equals("2")) {
               output = MessageDigests.digestMessage(input, saltValue, algorithm);
               calculateInputLength();
            } else if (input != null && option.equals("3")) {
               output = MessageDigests.digestMessage(input, saltValue, time, algorithm);
               calculateInputLength();
            } else if (inputURL != null && option.equals("4")) {
               try {
                  URL urlAddress = new URL(inputURL);
                  output = MessageDigests.digestMessage(urlAddress, algorithm);
                  inputLength = urlAddress.getContent().toString().length();
               } catch (MalformedURLException ex) {
                  FacesMessage fm = new FacesMessage("Please check url. ");
                  fc.addMessage(null, fm);
               } catch (NullPointerException ex) {
                  FacesMessage fm = new FacesMessage("URL cannot null. ");
                  fc.addMessage(null, fm);
               } catch (IOException ex) {
                  FacesMessage fm = new FacesMessage("Not find url. ");
                  fc.addMessage(null, fm);
               }
            } else if (inputFile != null && option.equals("5")) {
               InputStream is = null;
               try {
                  is = new FileInputStream(inputFile);
                  inputLength = is.available();
                  output = MessageDigests.digestMessage(is, algorithm);

               } catch (FileNotFoundException ex) {
                  FacesMessage fm = new FacesMessage("Cannot find : " + inputFile);
                  fc.addMessage(null, fm);
               }
            }

            outputBase64 = Base64.encode(output.getBytes("UTF-8"));
            System.out.println("Base 64 : " +  Base64.encode(output.getBytes()));
            System.out.println("Base 64 UTF-8 : " + outputBase64);
            outputBase64Length = outputBase64.length();
            outputLength = output.length();
            System.out.println("Message Digest : " + output);

            if (inputLength == 0) {
               inputBase64 = "";
               output = "";
               outputBase64 = "";
               outputLength = 0;
               outputBase64Length = 0;
            }

         }

      } catch (MalformedURLException ex) {
         Logger.getLogger(HashingForm.class.getName()).log(Level.SEVERE, null, ex);
      } catch (NoSuchAlgorithmException ex) {
         Logger.getLogger(HashingForm.class.getName()).log(Level.SEVERE, null, ex);
      } catch (UnsupportedEncodingException ex) {
         Logger.getLogger(HashingForm.class.getName()).log(Level.SEVERE, null, ex);
      } catch (IOException ex) {
         Logger.getLogger(HashingForm.class.getName()).log(Level.SEVERE, null, ex);
      } catch (NullPointerException ex) {
         FacesMessage fm = new FacesMessage(ex.getMessage());
         fc.addMessage(null, fm);
      }
   }

   public void calculateInputLength() throws UnsupportedEncodingException {
      inputLength = input.length();
      inputBase64 =  Base64.encode(input.getBytes("UTF-8"));
      inputBase64Length = inputBase64.length();
   }

   public void optionsChangeListener() {
      System.out.println("You selected : " + getOption());
      hash();
   }

   public void calculateActionListener() {
      hash();
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
    * @return the inputBase64Length
    */
   public int getInputBase64Length() {
      return inputBase64Length;
   }

   /**
    * @param inputBase64Length the inputBase64Length to set
    */
   public void setInputBase64Length(int inputBase64Length) {
      this.inputBase64Length = inputBase64Length;
   }

   /**
    * @return the output
    */
   public String getOutput() {
      return output;
   }

   /**
    * @param output the output to set
    */
   public void setOutput(String output) {
      this.output = output;
   }

   /**
    * @return the outputBase64
    */
   public String getOutputBase64() {
      return outputBase64;
   }

   /**
    * @param outputBase64 the outputBase64 to set
    */
   public void setOutputBase64(String outputBase64) {
      this.outputBase64 = outputBase64;
   }

   /**
    * @return the outputBase64Length
    */
   public int getOutputBase64Length() {
      return outputBase64Length;
   }

   /**
    * @param outputBase64Length the outputBase64Length to set
    */
   public void setOutputBase64Length(int outputBase64Length) {
      this.outputBase64Length = outputBase64Length;
   }

   /**
    * @return the inputLength
    */
   public int getInputLength() {
      return inputLength;
   }

   /**
    * @param inputLength the inputLength to set
    */
   public void setInputLength(int inputLength) {
      this.inputLength = inputLength;
   }

   /**
    * @return the outputLength
    */
   public int getOutputLength() {
      return outputLength;
   }

   /**
    * @param outputLength the outputLength to set
    */
   public void setOutputLength(int outputLength) {
      this.outputLength = outputLength;
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
    * @return the inputURL
    */
   public String getInputURL() {
      return inputURL;
   }

   /**
    * @param inputURL the inputURL to set
    */
   public void setInputURL(String inputURL) {
      this.inputURL = inputURL;
   }

   /**
    * @return the inputFile
    */
   public String getInputFile() {
      return inputFile;
   }

   /**
    * @param inputFile the inputFile to set
    */
   public void setInputFile(String inputFile) {
      this.inputFile = inputFile;
   }
}
