package org.wsssoapbox.bean.backing.soap;

import java.net.MalformedURLException;

import java.net.URL;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SoapMessageSender {

   private static final Logger logger = LoggerFactory.getLogger(SoapMessageSender.class);

   static public SOAPMessage send(SOAPMessage message, String endpointURL) throws MalformedURLException, SOAPException {
      SOAPMessage response = null;


      if (endpointURL != null && message != null) {
         SOAPConnection connection = null;
         try {
            URL url = new URL(endpointURL);
            logger.info("Endpoint address : " + url.toString());
            SOAPConnectionFactory scf = SOAPConnectionFactory.newInstance();
            connection = scf.createConnection(); //point-to-point connectio          
            response = connection.call(message, url);
         } catch (SOAPException ex) {
            throw new SOAPException();
         } finally {
            if (connection != null) {
               try {
                  connection.close();
               } catch (SOAPException ex) {
                  logger.error("Can't close SOAPConnection:", ex);
                  System.out.print("Can't close SOAPConnection:" + ex);
                  throw new SOAPException();
               }
            }
         }
      }
      return response;
   }
}
