/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soap.attachement;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Iterator;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.xml.soap.AttachmentPart;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeader;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.wsssoapbox.bean.model.soap.SoapAttachmentBean;

/**
 *
 * @author Peter
 */
public class AttachementTestCase {

   public AttachementTestCase() {
   }

   @BeforeClass
   public static void setUpClass() throws Exception {
   }

   @Before
   public void setUp() {
   }

   @Test
   public void hello() throws SOAPException, IOException {


      String messageString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>"
              + "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tns=\"http://attachment.doc.ws.wssbox.org/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">"
              + " <SOAP-ENV:Body> "
              + " <tns:getTextFile> "
              + "  <arg0>Peter Conair</arg0>"
              + " </tns:getTextFile>"
              + "</SOAP-ENV:Body>"
              + "</SOAP-ENV:Envelope>";


      SOAPFactory soapFactory = SOAPFactory.newInstance();
      MessageFactory messageFactory = MessageFactory.newInstance();
      SOAPMessage message = messageFactory.createMessage();
      SOAPPart soapPart = message.getSOAPPart();



      Source xmlInput = new StreamSource(new StringReader(messageString));
      soapPart.setContent(xmlInput);


      System.out.println("Original Request Messsage : ");
      message.writeTo(System.out);
      System.out.println("");
      System.out.println("********MimeHeader Original*********");
      Iterator iter1 = message.getMimeHeaders().getAllHeaders();
      while (iter1.hasNext()) {
         MimeHeader mime = (MimeHeader) iter1.next();
         System.out.println(mime.getName() + " : " + mime.getValue());
      }

      /*
      ByteArrayOutputStream attachmentStream = new ByteArrayOutputStream();
      InputStream is = new BufferedInputStream(dataHandler.getInputStream());
      
      int size = is.available();
      int bytesRead = 0;
      byte[] buffer = new byte[size];
      while ((bytesRead = is.read(buffer, 0, size)) != -1) {
      attachmentStream.write(buffer, 0, bytesRead);
      }
       * //      dataHandler.writeTo(attachmentStream);
      //      is.close();
      //     attachmentStream.close();
       */

      File file = new File("C:/test.txt");
      File file1 = new File("C:/test1.txt");
      FileInputStream fis = new FileInputStream(file);
      FileInputStream fis1 = new FileInputStream(file1);
      InputStream is = new BufferedInputStream(fis);
      InputStream is1 = new BufferedInputStream(fis1);

      AttachmentPart attachmentPart = message.createAttachmentPart();
      attachmentPart.setContentId("Text");
      attachmentPart.setRawContent(is, "text/plain");
      message.addAttachmentPart(attachmentPart);

      AttachmentPart attachmentPart1 = message.createAttachmentPart();
      attachmentPart1.setContentId("Text1");
      attachmentPart1.setRawContent(is, "text/plain");
      message.addAttachmentPart(attachmentPart1);

      /*
      DataSource dataSource = new FileDataSource("C:/test.txt");
      DataHandler dataHandler = new DataHandler(dataSource);
      
      AttachmentPart attachmentPart = message.createAttachmentPart(dataHandler);
      attachmentPart.setContentId("A1");
      message.addAttachmentPart(attachmentPart);
      
       */


     // message.saveChanges();
      
      System.out.println("attachment added  :");
      Iterator iter = message.getAttachments();
      while (iter.hasNext()) {
         AttachmentPart at = (AttachmentPart) iter.next();
         System.out.println(
                 "Attachment " + at.getContentId() + " has content type " + at.getContentType());
         if (at.getContentType().equals("text/plain")) {
            Object content = at.getContent();
            System.out.println("Attachment contains:\n" + content);
         }
      }


      System.out.println("********MimeHeader after add attachment*********");
      Iterator iter2 = message.getMimeHeaders().getAllHeaders();
      while (iter2.hasNext()) {
         MimeHeader mime = (MimeHeader) iter2.next();
         System.out.println(mime.getName() + " : " + mime.getValue());
      }

      System.out.println("Requet Message after add attache: before sent :");
      message.writeTo(System.out);
       //  message.saveChanges();


      SOAPConnection con = SOAPConnectionFactory.newInstance().createConnection();
      String endpoint = "http://localhost:7777/WebServiceProvider/ws/doc/attachement";

      System.out.println("Sending to : " + endpoint);
      SOAPMessage response = con.call(message, endpoint);

      System.out.println("");
      System.out.println(" Response form Endpint :");
      response.writeTo(System.out);
      
      
      
      
      System.out.println("********MimeHeader after saveChange*********");
      Iterator iter3 = message.getMimeHeaders().getAllHeaders();
      while (iter3.hasNext()) {
         MimeHeader mime = (MimeHeader) iter3.next();
         System.out.println(mime.getName() + " : " + mime.getValue());
      }

         System.out.println("attachment after saveChange  :");
      Iterator iter6 = message.getAttachments();
      while (iter6.hasNext()) {
         AttachmentPart at = (AttachmentPart) iter6.next();
         System.out.println(
                 "Attachment " + at.getContentId() + " has content type " + at.getContentType());
         if (at.getContentType().equals("text/plain")) {
            Object content = at.getContent();
            System.out.println("Attachment contains:\n" + content);
         }
      }
      

      if (response.countAttachments() > 0) {
         Iterator iterator = response.getAttachments();
         while (iterator.hasNext()) {
            AttachmentPart attached = (AttachmentPart) iterator.next();

            String id = attached.getContentId();
            String type = attached.getContentType();
            System.out.println(
                    "Attachment " + id + " has content type " + type);

            if (type.equals("text/plain")) {
               Object content = attached.getContent();
               System.out.println("Attachment contains:\n" + content);
            }
         }
      }

   }

   //  @Test
   public void testAttachePaintTextInputStream() throws SOAPException, IOException {


//      DataSource dataSource = new FileDataSource("C:/test.txt");
      DataSource dataSource = new FileDataSource("C:/test.pdf");
      DataHandler dataHandler = new DataHandler(dataSource);
      InputStream is = new BufferedInputStream(dataHandler.getInputStream());

      SoapAttachmentBean attachementBean = new SoapAttachmentBean();
      attachementBean.setContentID("");
      attachementBean.setInputStream(is);
      attachementBean.setName(null);



      MessageFactory messageFactory = MessageFactory.newInstance();
      SOAPMessage message = messageFactory.createMessage();
      SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
      SOAPBody soapBody = envelope.getBody();
      SOAPHeader header = envelope.getHeader();
      SOAPFactory soapFactory = SOAPFactory.newInstance();

      AttachmentPart attachmentPart = message.createAttachmentPart();
      //     attachmentPart.setRawContent(is, "test/paint");
      attachmentPart.setRawContent(is, "application/pdf");
      attachmentPart.setContentId("A1");
      message.addAttachmentPart(attachmentPart);
      message.saveChanges();
      message.writeTo(System.out);
      is.close();
   }

//   @Test
   public void testAttachePaintText() {
      FileReader fr = null;
      BufferedReader br = null;
      String line = "";

      try {
         MessageFactory messageFactory = MessageFactory.newInstance();
         SOAPMessage message = messageFactory.createMessage();
         SOAPHeader header = message.getSOAPHeader();
         SOAPBody body = message.getSOAPBody();
         header.detachNode();

         AttachmentPart attachment1 = message.createAttachmentPart();

         fr = new FileReader(new File("rose1.txt"));
         br = new BufferedReader(fr);

         String stringContent = "";
         line = br.readLine();

         while (line != null) {
            stringContent = stringContent.concat(line);
            stringContent = stringContent.concat("\n");
            line = br.readLine();
         }

         attachment1.setContent(stringContent, "text/plain");
         attachment1.setContentId("attached_text");

         message.addAttachmentPart(attachment1);
         Iterator iterator = message.getAttachments();

         while (iterator.hasNext()) {
            AttachmentPart attached = (AttachmentPart) iterator.next();

            String id = attached.getContentId();
            String type = attached.getContentType();
            System.out.println(
                    "Attachment " + id + " has content type " + type);

            if (type.equals("text/plain")) {
               Object content = attached.getContent();
               System.out.println("Attachment contains:\n" + content);
            }
         }
      } catch (FileNotFoundException e) {
         System.out.println("File not found: " + e.toString());
         System.exit(1);
      } catch (IOException e) {
         System.out.println("I/O exception: " + e.toString());
         System.exit(1);
      } catch (ArrayIndexOutOfBoundsException e) {
         System.out.println("array exception" + e.toString());
      } catch (Exception ex) {
         ex.printStackTrace();
      }
   }
}
