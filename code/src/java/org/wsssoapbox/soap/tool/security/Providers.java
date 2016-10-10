/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.soap.tool.security;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Provider.Service;
import java.security.Security;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import javax.crypto.Cipher;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.wsssoapbox.bean.backing.tool.AlgorithmImp;

/**
 *
 * @author Peter
 */
@ManagedBean(name = "providers")
@SessionScoped
public class Providers implements Serializable {

   private static final long serialVersionUID = -4815362326429872908L;

   public Providers() {
   }

   public static Map<String, String> getAlgorithmsWithProvider(String strProvider, String type) {
      Map<String, String> algorithms = new HashMap<String, String>();
      Provider provider = Security.getProvider(strProvider);
      try {
         Iterator it = provider.getServices().iterator();
         while (it.hasNext()) {
            Service service = (Service) it.next();
            if (service.getType().equals(type)) {
               String key = service.getAlgorithm();
               String value = service.getAlgorithm();
               algorithms.put(key, value);
            }
         }
         return algorithms;
      } catch (NullPointerException ex) {
         ex.printStackTrace();
         return algorithms;
      }

   }

   public static Map<String, String> getProviderNames() {
      Map<String, String> mapProviders = new HashMap<String, String>();
      Provider[] providers = Security.getProviders();

      for (int i = 0; i < providers.length; i++) {
         Provider p = providers[i];
         mapProviders.put(p.getName(), p.getName());
      }
      return mapProviders;
   }

   public static Provider getProviderByName(String providerName) {
      Provider provider = Security.getProvider(providerName);
      return provider;
   }

   public static Map<String, String> getServices(String providerName) {
      Provider provider = Security.getProvider(providerName);
      Map<String, String> services = new LinkedHashMap<String, String>();
      Iterator it = provider.getServices().iterator();
      while (it.hasNext()) {
         Service s = (Service) it.next();
         String type = s.getType();
         services.put(type, type);
      }
      return services;
   }

   public static void checkKeySize(String type) {
      try {
         Set<String> algorithms = Security.getAlgorithms(type);
         int i = 1;
         for (String algorithm : algorithms) {
            int max;
            max = Cipher.getMaxAllowedKeyLength(algorithm);
            System.out.printf("(" + i++ + ") %-22s: %dbit%n", algorithm, max);
         }
      } catch (NoSuchAlgorithmException e) {
         e.printStackTrace();
      }
   }

   public static int maxKeySize(String type, String algorithmName) {
      int max = 0;
      try {
         Set<String> algorithms = Security.getAlgorithms(type);
         for (String algorithm : algorithms) {
            if (algorithmName.equals(algorithm)) {
               max = Cipher.getMaxAllowedKeyLength(algorithm);
            }
         }
         return max;
      } catch (NoSuchAlgorithmException e) {
         e.printStackTrace();
         return 0;
      }
   }
}
