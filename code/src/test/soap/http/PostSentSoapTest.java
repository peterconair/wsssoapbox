/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soap.http;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
import java.net.Socket;
import java.net.InetAddress;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.MessageFactory;
import java.io.InputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.wsssoapbox.xml.util.XMLUtils;
import static org.junit.Assert.*;

/**
 *
 * @author Peter
 */
public class PostSentSoapTest {

   public PostSentSoapTest() {
   }

   @BeforeClass
   public static void setUpClass() throws Exception {
   }

   @AfterClass
   public static void tearDownClass() throws Exception {
   }

 //  @Test
   public void testSentPost() throws MalformedURLException, IOException, SOAPException {

      String endpoint = "http://www.pttplc.com/pttinfo.asmx";
      MimeHeaders mimeHeaders = new MimeHeaders();
      mimeHeaders.addHeader("SOAPAction", "http://www.pttplc.com/ptt_webservice/CurrentOilPrice");
      mimeHeaders.addHeader("User-Agen", "WS-SSOAPBox Tool");
      mimeHeaders.addHeader("Content-Type", "text/xml; charset=utf-8");
      mimeHeaders.addHeader("Accep", "text/xml, text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2");

      URL url = new URL(endpoint);
      HttpURLConnection connection = null;

      connection = (HttpURLConnection) url.openConnection();
      connection.setDoOutput(true);
      InputStream is = connection.getInputStream();
      SOAPMessage soapMessage = MessageFactory.newInstance().createMessage(mimeHeaders, is);

      System.out.println(
              XMLUtils.prettyPrint(soapMessage));


   }

   @Test
   public void testPostSoap() {
      try {

         String xmldata = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><SOAP-ENV:Envelope "
                 + "xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" "
                 + "xmlns:s=\"http://www.w3.org/2001/XMLSchema\" "
                 + "xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" "
                 + "xmlns:tns=\"http://www.pttplc.com/ptt_webservice/\"> "
                 + "<SOAP-ENV:Body>"
                 + "<tns:CurrentOilPrice>"
                 + "<tns:Language xmlns:tns=\"http://www.pttplc.com/ptt_webservice/\">en</tns:Language>"
                 + "</tns:CurrentOilPrice>"
                 + "</SOAP-ENV:Body>"
                 + "</SOAP-ENV:Envelope>";


         //Create socket
         String hostname = "www.pttplc.com";
         int port = 80;
         InetAddress addr = InetAddress.getByName(hostname);
         Socket sock = new Socket(addr, port);

         //Send header
    

         BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream(), "UTF-8"));
         // You can use "UTF8" for compatibility with the Microsoft virtual machine.
         wr.write("POST /pttinfo.asmx HTTP/1.1\r\n");
         wr.write("Host: www.pttplc.com\r\n");
         wr.write("SOAPAction: http://www.pttplc.com/ptt_webservice/CurrentOilPrice\r\n");
         wr.write("Content-Length: " + xmldata.length() + "\r\n");
         wr.write("Content-Type: text/xml; charset=\"utf-8\"\r\n");
         wr.write("\r\n");

         //Send data
         wr.write(xmldata);
         wr.flush();

         // Response
         BufferedReader rd = new BufferedReader(new InputStreamReader(sock.getInputStream()));
         String line;
         while ((line = rd.readLine()) != null) {
            System.out.println(line);
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
}
