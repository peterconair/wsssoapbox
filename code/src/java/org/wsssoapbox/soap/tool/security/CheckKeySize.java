/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.soap.tool.security;

import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.Set;
import javax.crypto.Cipher;

/**
 *
 * @author Peter
 */
public class CheckKeySize {

   public static void main(String[] args) {
      try {
         Set<String> algorithms = Security.getAlgorithms("Cipher");
         for (String algorithm : algorithms) {
            int max;
            max = Cipher.getMaxAllowedKeyLength(algorithm);
            System.out.printf("%-22s: %dbit%n", algorithm, max);
         }
      } catch (NoSuchAlgorithmException e) {
         e.printStackTrace();
      }
   }
}
