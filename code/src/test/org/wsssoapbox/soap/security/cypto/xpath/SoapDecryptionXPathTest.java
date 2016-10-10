/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.soap.security.cypto.xpath;

import java.util.List;
import org.w3c.dom.Element;
import java.io.UnsupportedEncodingException;
import org.wsssoapbox.soap.support.SoapCreator;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPException;
import org.junit.Before;
import javax.xml.soap.SOAPMessage;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Peter
 */
public class SoapDecryptionXPathTest {

   SOAPMessage soapMessage;
   String strMessage =
           "<soapenv:Envelope xmlns:oss=\"http://payment.javatech.com/ws/schema/oss\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">"
           + "<soapenv:Header>"
           + "</soapenv:Header>"
           + "<soapenv:Body>"
           + "<oss:paymentRequest>"
           + "<oss:amount>239</oss:amount>"
           + "<oss:cardNumber>1232323454565</oss:cardNumber>"
           + "<oss:expirationDate>12/12</oss:expirationDate>"
           + "<oss:securityCode>567</oss:securityCode>"
           + "<oss:nameOnCard>Pongsak Gran</oss:nameOnCard>"
           + "</oss:paymentRequest>"
           + "</soapenv:Body>"
           + "</soapenv:Envelope>";

   public SoapDecryptionXPathTest() {
   }

   @Before
   public void setUp() throws SOAPException, UnsupportedEncodingException {
      soapMessage = MessageFactory.newInstance().createMessage();
      soapMessage = SoapCreator.createSOAPMessageFromString(strMessage);
   }

   //@Test
   public void testFindEncryptedKeyByEncryptedData() throws Exception {
   }

   // @Test
   public void testFindEncryptedDataByEncryptedKey() throws Exception {
   }

   //@Test
   public void testFindAllEncrypteDataByDataReferences() throws Exception {
   }

   //@Test
   public void testFindEncryptedKey() throws Exception {
   }

   //@Test
   public void testFindEncryptedKeys() throws Exception {
   }

   //@Test
   public void testFindEncryptedData() throws Exception {
   }

   //@Test
   public void testFindEncryptedDatas() throws Exception {
   }

   // @Test
   public void testVerifyCertificate() throws Exception {
   }

   @Test
   public void testFindAllElementInBody() throws Exception {

      SoapDecryptionXPath soapXpath = new SoapDecryptionXPath();

      List<Element> elements = soapXpath.findAllElementInBody(soapMessage);

      System.out.println("Size : " + elements.size());

      System.out.println("Elements in Body :");
      for (Element e : elements) {
         String tagName = e.getTagName();
         System.out.println(" : " + e.getNamespaceURI());
         System.out.println(" : " + e.getNodeName());
         System.out.println(" : " + e.getTextContent());
         System.out.println(" : " + tagName);
      }
   }
}
