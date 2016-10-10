/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wsdl;

import java.io.IOException;
import java.net.MalformedURLException;
import javax.xml.transform.Transformer;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Peter
 */
public class WSDLViewerTestCase {

   public WSDLViewerTestCase() {
   }

   @BeforeClass
   public static void setUpClass() throws Exception {
   }

   @AfterClass
   public static void tearDownClass() throws Exception {
   }

   @Before
   public void setUp() {
   }

   @After
   public void tearDown() {
   }
   // TODO add test methods here.
   // The methods must be annotated with annotation @Test. For example:
   //

   @Test
   public void convertToHTML() throws FileNotFoundException, TransformerException, MalformedURLException, IOException {


      URL wsdlURL = new URL("http://www.pttplc.com/pttinfo.asmx?WSDL");
      String xmlInputFile = "xxxx.xml";
      //   StreamSource xmlSource = new StreamSource(new FileInputStream(xmlInputFile));
      StreamSource xmlSource = new StreamSource(wsdlURL.openStream());

      URL xsltURL = new URL("http://tomi.vanek.sk/xml/wsdl-viewer.xsl");

//    String xsltInputFile = "xxxx.xsl";
//   StreamSource xsltSource = new StreamSource(new FileInputStream(xsltInputFile));
      StreamSource xsltSource = new StreamSource(xsltURL.openStream());
      String htmlOutputFile = "c:\\result.html";

      StreamResult transResutl = new StreamResult(new FileOutputStream(htmlOutputFile));

      TransformerFactory factory = TransformerFactory.newInstance();

      Transformer tranformer = factory.newTransformer(xsltSource);
      tranformer.transform(xmlSource, transResutl);

   }
}
