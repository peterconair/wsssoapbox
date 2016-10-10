/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wsdl.uddi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;
import javax.xml.registry.BulkResponse;
import javax.xml.registry.BusinessQueryManager;
import javax.xml.registry.Connection;
import javax.xml.registry.ConnectionFactory;
import javax.xml.registry.FindQualifier;
import javax.xml.registry.JAXRException;
import javax.xml.registry.RegistryService;
import javax.xml.registry.infomodel.EmailAddress;
import javax.xml.registry.infomodel.Organization;
import javax.xml.registry.infomodel.PersonName;
import javax.xml.registry.infomodel.RegistryObject;
import javax.xml.registry.infomodel.Service;
import javax.xml.registry.infomodel.ServiceBinding;
import javax.xml.registry.infomodel.TelephoneNumber;
import javax.xml.registry.infomodel.User;

/**
 *
 * @author Peter
 */
public class JaxrQuery {

   public static void main(String[] args) throws Exception {
      
     String QUERY_URL= "http://localhost:10000/juddi/inquiry";

      
      String queryString = "";
      Connection connection = null;

      // Define connection configuration properties
      // To query, you need only the query URL
      Properties props = new Properties();
      props.setProperty("javax.xml.registry.queryManagerURL",QUERY_URL);
      props.setProperty("javax.xml.registry.factoryClass","com.sun.xml.registry.uddi.ConnectionFactoryImpl");

      // Create the connection, passing it the configuration properties
      ConnectionFactory factory = ConnectionFactory.newInstance();
      factory.setProperties(props);

      connection = factory.createConnection();
      
      // Get registry service and business query manager
      RegistryService rs = connection.getRegistryService();
      
      BusinessQueryManager bqm = rs.getBusinessQueryManager();

      System.out.println("We have the Business Query Manager");

      // Define find qualifiers and name patterns
      Collection findQualifiers = new ArrayList();
      findQualifiers.add(FindQualifier.EXACT_NAME_MATCH);
      Collection namePatterns = new ArrayList();
      namePatterns.add("%" + queryString + "%");

      // Find based upon qualifier type and values
      System.out.println("\n-- searching the registry --\n");

      BulkResponse response =
              bqm.findOrganizations(findQualifiers,
              namePatterns,
              null,
              null,
              null,
              null);

      // check how many organisation we have matched
      Collection orgs = response.getCollection();
      System.out.println("\n-- Matched " + orgs.size() + " organisations --\n");

      // then step through them
      for (Iterator orgIter = orgs.iterator(); orgIter.hasNext();) {
         Organization org = (Organization) orgIter.next();
         System.out.println("Org name: " + getName(org));
         System.out.println("Org description: " + getDescription(org));
         System.out.println("Org key id: " + getKey(org));

         // Display primary contact information
         User pc = org.getPrimaryContact();
         if (pc != null) {
            PersonName pcName = pc.getPersonName();
            System.out.println(" Contact name: " + pcName.getFullName());

            Collection phNums = pc.getTelephoneNumbers(pc.getType());
            for (Iterator phIter = phNums.iterator(); phIter.hasNext();) {
               TelephoneNumber num = (TelephoneNumber) phIter.next();
               System.out.println("  Phone number: " + num.getNumber());
            }

            Collection eAddrs = pc.getEmailAddresses();
            for (Iterator eaIter = eAddrs.iterator(); eaIter.hasNext();) {
               System.out.println("  Email Address: " + (EmailAddress) eaIter.next());
            }
         }

         // Display service and binding information
         Collection services = org.getServices();
         for (Iterator svcIter = services.iterator(); svcIter.hasNext();) {
            Service svc = (Service) svcIter.next();
            System.out.println(" Service name: " + getName(svc));
            System.out.println(" Service description: " + getDescription(svc));

            Collection serviceBindings = svc.getServiceBindings();
            for (Iterator sbIter = serviceBindings.iterator(); sbIter.hasNext();) {
               ServiceBinding sb = (ServiceBinding) sbIter.next();
               System.out.println("  Binding Description: " + getDescription(sb));
               System.out.println("  Access URI: " + sb.getAccessURI());
            }
         }
         // Print spacer between organizations
         System.out.println(" --- ");
      }
      connection.close();
   }

   private static String getName(RegistryObject ro) throws JAXRException {
      if (ro != null && ro.getName() != null) {
         return ro.getName().getValue();
      }
      return "";
   }

   private static String getDescription(RegistryObject ro) throws JAXRException {
      if (ro != null && ro.getDescription() != null) {
         return ro.getDescription().getValue();
      }
      return "";
   }

   private static String getKey(RegistryObject ro) throws JAXRException {
      if (ro != null && ro.getKey() != null) {
         return ro.getKey().getId();
      }
      return "";
   }
}
