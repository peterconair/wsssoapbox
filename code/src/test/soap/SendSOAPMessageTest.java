/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soap;

import java.util.Map;
import java.util.Iterator;
import java.util.Set;
import java.io.OutputStreamWriter;
import java.net.URLConnection;
import java.net.HttpURLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.soap.SOAPException;
import org.wsssoapbox.xml.util.XMLUtils;
import java.net.URL;
import org.wsssoapbox.soap.support.SoapCreator;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.OutputStream;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPFactory;
import org.apache.commons.lang.StringEscapeUtils;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Peter
 */
public class SendSOAPMessageTest {

   String SOAP_ACTION = "\"\"";
   String endpoint = "http://localhost:2000/spring-ws/krams/ws";
   /*
   String requestMessageString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
   + "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tns=\"http://krams915.blogspot.com/ws/schema/oss\">"
   + " <SOAP-ENV:Body>"
   + " <tns:subscription>"
   + " <tns:id>77777777777</tns:id>"
   + " <tns:name>?</tns:name>"
   + " <tns:email>?</tns:email>"
   + " </tns:subscription>"
   + " </SOAP-ENV:Body>"
   + "</SOAP-ENV:Envelope>";
    */
   String requestMessageString = "<?xml version =\"1.0\" ?>"
           + "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\""
           + " xmlns:oss=\"http://krams915.blogspot.com/ws/schema/oss\">"
           + "<soapenv:Header />"
           + " <soapenv:Body>"
           + "   <oss:subscriptionRequest>"
           + "   <oss:id>1111111133</oss:id>"
           + "   <oss:name>pongsaka</oss:name>"
           + "    <oss:email>peter@test.com</oss:email>"
           + "  </oss:subscriptionRequest>"
           + "</soapenv:Body>"
           + "</soapenv:Envelope>";

   //String endpoint = "http://www.pttplc.com/pttinfo.asmx";
   //  String SOAP_ACTION = "http://www.pttplc.com/ptt_webservice/CurrentOilPrice";
  /*
   String requestMessageString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>"
   + "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:s=\"http://www.w3.org/2001/XMLSchema\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:tns=\"http://www.pttplc.com/ptt_webservice/\">"
   + "<SOAP-ENV:Body>"
   + " <tns:CurrentOilPrice>"
   + "   <tns:Language>en</tns:Language>"
   + " </tns:CurrentOilPrice>"
   + "</SOAP-ENV:Body>"
   + "</SOAP-ENV:Envelope>";
    */
   public SendSOAPMessageTest() {
   }

   @BeforeClass
   public static void setUpClass() throws Exception {
   }

   // @Test
   public void sentMessage() throws Exception {



      SOAPFactory soapFactory = SOAPFactory.newInstance();
      MessageFactory mf = MessageFactory.newInstance();
      SOAPMessage soapRequest = mf.createMessage();
      soapRequest = SoapCreator.createSOAPMessageFromString(requestMessageString, soapRequest);
      soapRequest.saveChanges();

      String out = sendSoapMessage(soapRequest, endpoint);

   }

   public String sendSoapMessage(SOAPMessage message, String serverurl) throws Exception {


      // Send the message
      System.out.println("Send the SOAP message\n");
      SOAPMessage soapResponse = null;
      OutputStream out = null;
      OutputStreamWriter outWriter = null;
      URL url = null;
      URLConnection urlConneciton = null;
      HttpURLConnection connection = null;
      InputStream is = null;
      String response = "";

      try {
         if (out == null) {

            if (urlConneciton == null) {
               url = new URL(serverurl);
               urlConneciton = url.openConnection();
            }

            connection = (HttpURLConnection) urlConneciton;

            connection.setDoOutput(true);
            connection.setDoInput(true);
            //  connection.setRequestMethod("POST");
            connection.setRequestProperty("SOAPAction", SOAP_ACTION);
            connection.setRequestProperty("Accept-Encoding", "text/xml");
            connection.setRequestProperty("Content-Type", "application/soap; charset=utf-8");

            out = connection.getOutputStream();


            System.out.println("URL : " + connection.getURL());
            System.out.println("Request Method :" + connection.getRequestMethod());
            connection.connect();

            Set set = connection.getHeaderFields().entrySet();
            Iterator i = set.iterator();
            System.out.println("HTTP request header : ");
            while (i.hasNext()) {
               Map.Entry me = (Map.Entry) i.next();
               System.out.print(me.getKey() + ": ");
               System.out.println(me.getValue());
            }

         }
         if (outWriter == null) {
            outWriter = new OutputStreamWriter(out);
         }
         System.out.println("********************");
         String soapmessage = XMLUtils.getSoapMessageString(message);
         System.out.println(soapmessage);
         outWriter.write(soapmessage);
         outWriter.flush();
         //wout.close();
         System.out.println("");
         System.out.println("*********************");
         //   System.out.println("Response Message : "+httpURLConnection.getContent());

         if (is == null) {
            Set set = connection.getHeaderFields().entrySet();
            Iterator i = set.iterator();
            System.out.println("HTTP response header : ");
            while (i.hasNext()) {
               Map.Entry me = (Map.Entry) i.next();
               System.out.print(me.getKey() + ": ");
               System.out.println(me.getValue());
            }

            is = connection.getInputStream();

         }

         response = readServerResponseMessage(is);
         //is.close();


         System.out.println("[SERVER RESPONSE]" + response);

      } catch (IOException e) {
         System.err.println(e);
         e.printStackTrace();
      } catch (Exception eb) {
         System.err.println(eb);
         eb.printStackTrace();
      }

      return response;
   }

   public String readServerResponseMessage(InputStream is) {
      BufferedReader br = new BufferedReader(new InputStreamReader(is));
      char[] readChars = new char[65000];
      String response = "";
      try {
         while (br.read(readChars) != -1) {
            response = new String(readChars);
            break;

         }

      } catch (IOException e) {
         e.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      }

      return response;
   }

   @Test
   public void testSAAJSOAP() throws SOAPException {
      try {

         SOAPMessage soapResponse = null;
         SOAPConnection connection = null;
         URL url = new URL(endpoint);

         MessageFactory mf = MessageFactory.newInstance();
         SOAPMessage soapRequest = mf.createMessage();
         soapRequest = SoapCreator.createSOAPMessageFromString(requestMessageString, soapRequest);
         soapRequest.getMimeHeaders().setHeader("SOAPAction", SOAP_ACTION);
         soapRequest.saveChanges();




         // Create a SOAP connection
         SOAPConnectionFactory scf = SOAPConnectionFactory.newInstance();
         connection = scf.createConnection();

         soapRequest = SoapCreator.createSOAPMessageFromString(requestMessageString, soapRequest);

         String msg = XMLUtils.getSoapMessageString(soapRequest);
         System.out.println("Requst : \n" + StringEscapeUtils.unescapeXml(msg));


         soapResponse = connection.call(soapRequest, url);
         msg = XMLUtils.getSoapMessageString(soapResponse);

         String unescapeXml = StringEscapeUtils.unescapeXml(msg);

         String responsePretyString = XMLUtils.prettyPrint(unescapeXml);
         System.out.println("Response :  \n: " + responsePretyString);
      } catch (IOException ex) {
         Logger.getLogger(SendSOAPMessageTest.class.getName()).log(Level.SEVERE, null, ex);
      }


   }
}
