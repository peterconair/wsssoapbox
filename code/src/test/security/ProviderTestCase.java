/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package security;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Provider.Service;
import java.security.Security;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.crypto.Cipher;
import org.junit.BeforeClass;
import org.junit.Test;
import org.wsssoapbox.bean.backing.tool.AlgorithmImp;
import org.wsssoapbox.soap.tool.security.MessageDigests;
import org.wsssoapbox.soap.tool.security.Providers;

/**
 *
 * @author Peter
 */
public class ProviderTestCase {

   public ProviderTestCase() {
   }

   @BeforeClass
   public static void setUpClass() throws Exception {
   }

   public void tearDown() {
   }

   //   @Test
   public void getNameProvidersTest() {
      Provider providers[] = Security.getProviders();
      Provider provider = null;
      for (int i = 0; i < providers.length; i++) {
         provider = providers[i];
         System.out.println("Provider Name : " + provider.getName());
         System.out.println("Provider Version : " + provider.getVersion());
         System.out.println("Provider Info : " + provider.getInfo());
         System.out.println("");
      }
   }

   //   @Test
   public void getProvidersInfoTest() {
      Provider providers[] = Security.getProviders();
      Provider provider = null;
      for (int i = 0; i < providers.length; i++) {
         provider = providers[i];

         System.out.println("Provider Name : " + provider.getName());
         System.out.println("Provider Version : " + provider.getVersion());
         System.out.println("Provider Info : " + provider.getInfo());

         Iterator it = provider.getServices().iterator();
         while (it.hasNext()) {
            Service service = (Service) it.next();
            System.out.println("Algorithm : " + service.getAlgorithm());
            System.out.println("Type : " + service.getType());
            System.out.println("Classs Name : " + service.getClassName());

         }
         //      System.out.println(provider.getServices());

         System.out.println("");

      }

   }
   

   // @Test
   public void getProvidersMessageDigestTest() {

      Provider provider = Security.getProvider("SUN");
      System.out.println("Provider Name : " + provider.getName());
      System.out.println("Provider Version : " + provider.getVersion());
      System.out.println("Provider Info : " + provider.getInfo());
      System.out.println("Number of Service : " + provider.getServices().size());
      Iterator it = provider.getServices().iterator();
      while (it.hasNext()) {
         Service service = (Service) it.next();
         if (service.getType().equals("MessageDigest")) {
            System.out.println("Algorithm : " + service.getAlgorithm());
            System.out.println("Type : " + service.getType());
            System.out.println("Classs Name : " + service.getClassName());
         }
      }
      System.out.println("");
   }

   //@Test
   public void getProvidersAlgorithmTest() {

      Provider[] providers = Security.getProviders();

      for (Provider provider : providers) {
         String providerName = provider.getName();
         Iterator its = provider.getServices().iterator();
         String type = "";
         String tmp = "";
         System.out.println("-----------------------------------------------------------------");
         System.out.println("Provider Name : " + providerName);
         System.out.println("Version : " + provider.getVersion());
         System.out.println("Class Name :  " + provider.getClass());
         System.out.println("Number of Service : " + provider.getServices().size());
         System.out.println("Info : " + provider.getInfo());
        System.out.println("-----------------------------------------------------------------");
         int i = 1;
         while (its.hasNext()) {
            Service service = (Service) its.next();
            tmp = service.getType();



            if (!type.equals(tmp)) {
               type = service.getType();

               Map<String, String> algorithms = AlgorithmImp.getAlgorithmsWithProvider(providerName, type);

               System.out.println("[" + i++ + "]" + service.getType() + " >> Number of Algorithm : " + algorithms.size() + " >>  Class Name  : " + service.getClassName());


               Iterator ita = algorithms.entrySet().iterator();
               int k = 1;
               while (ita.hasNext()) {
                  System.out.print("  (" + k++ + ")");
                  Map.Entry object = (Map.Entry) ita.next();
                  System.out.println(object.getValue());
               }
            
            }
         }
         System.out.println("-----------------------------------------------------------------");
      }
   }

   // @Test
   public void getProviderOfSunJCEAlgorithmTest() {

      String providerName = "SunJCE";
      Provider provider = Security.getProvider(providerName);

      Iterator its = provider.getServices().iterator();
      String type = "";
      String tmp = "";
      System.out.println("Provider Name : " + providerName);
      System.out.println("Version : " + provider.getVersion());
      System.out.println("Class Name :  " + provider.getClass());
      System.out.println("Number of Service : " + provider.getServices().size());
      System.out.println("Info : " + provider.getInfo());
      System.out.println("");
      int i = 1;
      while (its.hasNext()) {
         Service service = (Service) its.next();
         tmp = service.getType();



         if (!type.equals(tmp)) {
            type = service.getType();

            Map<String, String> algorithms = AlgorithmImp.getAlgorithmsWithProvider(providerName, type);

            System.out.println("[" + i++ + "]" + service.getType() + " >> Number of Algorithm : " + algorithms.size() + " >>  Class Name  : " + service.getClassName());


            Iterator ita = algorithms.entrySet().iterator();
            int k = 1;
            while (ita.hasNext()) {
               System.out.print("  (" + k++ + ")");
               Map.Entry object = (Map.Entry) ita.next();
               System.out.println(object.getValue());
            }


            System.out.println("");
         }
      }
   }

    //@Test
   public void getProviderSunJCECipherTest() {
      System.out.println(AlgorithmImp.getAlgorithmsWithProvider("SunJCE", "Cipher"));
   }

   // @Test
   public void getProviderOfSunMessageDigestAlgorithmTest() {
      System.out.println(AlgorithmImp.getAlgorithmsWithProvider("SUN", "MessageDigest"));
   }

   // @Test
   public void HashingTest() throws NoSuchAlgorithmException, UnsupportedEncodingException {

      Map<String, String> algorithms = AlgorithmImp.getAlgorithmsWithProvider("SUN", "MessageDigest");

      String text = "Pongsak";
      Iterator it = algorithms.keySet().iterator();
      while (it.hasNext()) {
         String algorithm = (String) it.next();
         System.out.println("Algorithm : " + algorithm);
         String digestMessage = MessageDigests.digestMessage(text, algorithm);
         System.out.println("Provider : SUN");
         System.out.println("Algorithm Type : MessageDigest ");
         System.out.println("Digest Message  : " + digestMessage);
      }
   }

  // @Test 
   
   public void getSizeAlgorithm(){
      Providers.checkKeySize("Cipher");
   }
   
  //  @Test
   public void getProviderByNameTest() {
      System.out.println(Providers.getProviderByName("SunJCE"));
   }

        @Test
   public void getServicesTest() {
      System.out.println(Providers.getServices("SunJCE"));
   }

}
