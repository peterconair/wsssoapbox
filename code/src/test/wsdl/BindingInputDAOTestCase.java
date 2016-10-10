package wsdl;

import static org.junit.Assert.*;

import java.util.List;

import javax.xml.namespace.QName;

import org.junit.Before;
import org.junit.Test;
import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.api.BindingInput;
import org.ow2.easywsdl.wsdl.api.BindingOperation;
import org.ow2.easywsdl.wsdl.api.Description;
import org.ow2.easywsdl.wsdl.api.binding.wsdl11.http.HTTPBinding4Wsdl11;
import org.ow2.easywsdl.wsdl.api.binding.wsdl11.soap.SOAPBinding4Wsdl11;
import org.ow2.easywsdl.wsdl.api.binding.wsdl11.soap.soap11.SOAP11Binding4Wsdl11;
import org.ow2.easywsdl.wsdl.api.binding.wsdl11.soap.soap11.SOAP11Body;
import org.ow2.easywsdl.wsdl.api.binding.wsdl11.soap.soap11.SOAP11Header;
import org.ow2.easywsdl.wsdl.api.binding.wsdl11.soap.soap12.SOAP12Binding4Wsdl11;
import org.ow2.easywsdl.wsdl.api.binding.wsdl11.soap.soap12.SOAP12Header;
import org.ow2.easywsdl.wsdl.api.binding.wsdl20.soap.SOAPBinding4Wsdl20;
import org.ow2.easywsdl.wsdl.api.binding.wsdl20.soap.SOAPHeader;
import org.ow2.easywsdl.wsdl.impl.wsdl11.binding.soap.soap11.SOAP11BindingImpl;
import org.ow2.easywsdl.wsdl.impl.wsdl11.binding.soap.soap12.SOAP12BindingImpl;
import org.wsssoapbox.bean.model.wsdl.BindingInputBean;
import org.wsssoapbox.dao.wsdl.BindingInputDAO;
import org.wsssoapbox.dao.wsdl.IBindingInputDAO;
import org.wsssoapbox.datasource.wsdl.WSDLDataSource;

/**
 * @author  Peter
 */
public class BindingInputDAOTestCase {

   private static Description desc;
   private static String url;
   /**
    * @uml.property  name="wsdlDocumentDataSource"
    * @uml.associationEnd  
    */
   private static WSDLDataSource wsdlDocumentDataSource;

   @Before
   public void setUp() throws Exception {
      wsdlDocumentDataSource = new WSDLDataSource(WSDLList.url);
      BindingInputDAOTestCase.desc = WSDLDataSource.getDesc(WSDLList.url);
   }

   @Test
   public void testGetInputBindingByBindingSOAP11Binding4Wsdl11() throws XmlException {

      BindingOperation bo = desc.getServices().get(0).getEndpoints().get(0).getBinding().getBindingOperations().get(0);
      IBindingInputDAO bindingInputDAO = new BindingInputDAO();
      BindingInputBean bi = new BindingInputBean();
      bi = bindingInputDAO.getInputByBindingOperation(bo);

      System.out.println(" ******************* testGetInputBindingByBinding() **************************** ");
      System.out.println(" Input Name: " + bi.getName());
      System.out.println(" Input Name: " + bi.getHttpContentEncoding());
      System.out.println(" Input Documentation: " + bi.getDocumentation());
      System.out.println(" Input Other Attributes: " + bi.getOtherAttributes());
      System.out.println(" Input Other Elements: " + bi.getOtherElements());
      System.out.println(" Input HTTP Binding4Wsdl11: " + bi.getHTTPBinding4Wsdl11());
      System.out.println(" Input HTTP Binding4Wsdl20: " + bi.getHTTPBinding4Wsdl20());
      System.out.println(" Input MIME Binding4Wsdl11: " + bi.getMIMEBinding4Wsdl11());
      System.out.println(" Input SOAP11 Binding4Wsdl11: " + bi.getSOAP11Binding4Wsdl11());
      System.out.println(" Input SOAP12 Binding4Wsdl11: " + bi.getSOAP12Binding4Wsdl11());
      System.out.println(" Input SOAP12 Binding4Wsdl20: " + bi.getSOAP12Binding4Wsdl20());


      SOAP11Binding4Wsdl11 bindings114Wsdl11 = (SOAP11Binding4Wsdl11) bo.getInput().getSOAP11Binding4Wsdl11();
      SOAP12Binding4Wsdl11 bindings114Wsdl12 = (SOAP12Binding4Wsdl11) bo.getInput().getSOAP12Binding4Wsdl11();
      SOAPBinding4Wsdl20 bindings124Wsdl12 = (SOAPBinding4Wsdl20) bo.getInput().getSOAP12Binding4Wsdl20();

      System.out.println(bindings114Wsdl11);
      System.out.println(bindings114Wsdl12);
      System.out.println(bindings124Wsdl12);

      if (bindings114Wsdl11 != null) {//SOAP11Binding4Wsdl11
         
         for (SOAP11Header h : bindings114Wsdl11.getHeaders()) {
            System.out.println("h.getMessage() " + h.getMessage());
         }
      } else if (bindings114Wsdl12 != null) {//SOAP12Binding4Wsdl11
         for (SOAP12Header h : bindings114Wsdl12.getHeaders()) {
            System.out.println("h.getMessage() " + h.getMessage());
         }
      } else {//SOAPBinding4Wsdl20
         for (SOAPHeader h : bindings124Wsdl12.getHeaders()) {
            System.out.println("h.getMessage() " + h.getElement());
         }
      }


      System.out.println("Namespace () " + bindings114Wsdl11.getBody().getNamespace());
      System.out.println("binding4Wsdl11.getBody()" + bindings114Wsdl11.getBody());
      System.out.println("binding4Wsdl11.getBody().getRequired()" + bindings114Wsdl11.getBody().getRequired());
      System.out.println("binding4Wsdl11.getBody().getParts()" + bindings114Wsdl11.getBody().getParts());
      System.out.println("binding4Wsdl11.getBody().getUse()" + bindings114Wsdl11.getBody().getUse());
      System.out.println("binding4Wsdl11.getBody().getEncodingStyles() " + bindings114Wsdl11.getBody().getEncodingStyles());
      //System.out.println("Part () " + soapBody.getUse());

      System.out.println("**************** binding4Wsdl11.getBody() ****************************");
      System.out.println("binding4Wsdl11.getBody().getEncodingStyles() " + bindings114Wsdl11.getBody().getEncodingStyles());
      System.out.println("binding4Wsdl11.getBody().getNamespace() " + bindings114Wsdl11.getBody().getNamespace());
      System.out.println("binding4Wsdl11.getBody().getRequired() " + bindings114Wsdl11.getBody().getRequired());
      System.out.println("binding4Wsdl11.getBody().getUse() " + bindings114Wsdl11.getBody().getUse());
      System.out.println("binding4Wsdl11.getBody().getParts() " + bindings114Wsdl11.getBody().getParts());

   }

//   @Test
   public void testGetInputBindingByBindingSOAP12Binding4Wsdl11() throws XmlException {

      BindingOperation bo = desc.getServices().get(0).getEndpoints().get(0).getBinding().getBindingOperations().get(0);
      IBindingInputDAO bindingInputDAO = new BindingInputDAO();
      BindingInputBean bi = new BindingInputBean();
      bi = bindingInputDAO.getInputByBindingOperation(bo);


      System.out.println(" ******************* testGetInputBindingByBinding() **************************** ");
      System.out.println(" Input Name: " + bi.getName());
      System.out.println(" Input Name: " + bi.getHttpContentEncoding());
      System.out.println(" Input Documentation: " + bi.getDocumentation());
      System.out.println(" Input Other Attributes: " + bi.getOtherAttributes());
      System.out.println(" Input Other Elements: " + bi.getOtherElements());
      System.out.println(" Input HTTP Binding4Wsdl11: " + bi.getHTTPBinding4Wsdl11());
      System.out.println(" Input HTTP Binding4Wsdl20: " + bi.getHTTPBinding4Wsdl20());
      System.out.println(" Input MIME Binding4Wsdl11: " + bi.getMIMEBinding4Wsdl11());
      System.out.println(" Input SOAP11 Binding4Wsdl11: " + bi.getSOAP11Binding4Wsdl11());
      System.out.println(" Input SOAP12 Binding4Wsdl11: " + bi.getSOAP12Binding4Wsdl11());
      System.out.println(" Input SOAP12 Binding4Wsdl20: " + bi.getSOAP12Binding4Wsdl20());

      SOAP12Binding4Wsdl11 bindings114Wsdl12 = (SOAP12BindingImpl) bo.getInput().getSOAP12Binding4Wsdl11();



      for (SOAP12Header h : bindings114Wsdl12.getHeaders()) {
         System.out.println("h.getMessage() " + h.getMessage());
      }


      System.out.println("Namespace () " + bindings114Wsdl12.getBody().getNamespace());
      System.out.println("binding4Wsdl11.getBody()" + bindings114Wsdl12.getBody());
      System.out.println("binding4Wsdl11.getBody().getRequired()" + bindings114Wsdl12.getBody().getRequired());
      System.out.println("binding4Wsdl11.getBody().getParts()" + bindings114Wsdl12.getBody().getParts());
      System.out.println("binding4Wsdl11.getBody().getUse()" + bindings114Wsdl12.getBody().getUse());
      System.out.println("binding4Wsdl11.getBody().getEncodingStyles() " + bindings114Wsdl12.getBody().getEncodingStyle());
      //System.out.println("Part () " + soapBody.getUse());

      System.out.println("**************** bindings114Wsdl12.getBody() ****************************");
      System.out.println("binding4Wsdl11.getBody().getEncodingStyles() " + bindings114Wsdl12.getBody().getEncodingStyle());
      System.out.println("binding4Wsdl11.getBody().getNamespace() " + bindings114Wsdl12.getBody().getNamespace());
      System.out.println("binding4Wsdl11.getBody().getRequired() " + bindings114Wsdl12.getBody().getRequired());
      System.out.println("binding4Wsdl11.getBody().getUse() " + bindings114Wsdl12.getBody().getUse());
      System.out.println("binding4Wsdl11.getBody().getParts() " + bindings114Wsdl12.getBody().getParts());

   }

   //	@Test
   public void testGetInputBindingByBindingDefault() throws XmlException {
      BindingInput bi = desc.getServices().get(0).getEndpoints().get(0).getBinding().getBindingOperations().get(0).getInput();
      System.out.println(" ******************* testGetInputBindingByBindingDefault() **************************** ");
      System.out.println(" Input Name: " + bi.getName());
      System.out.println(" Input Name: " + bi.getHttpContentEncoding());
      System.out.println(" Input Documentation: " + bi.getDocumentation());
      System.out.println(" Input Other Attributes: " + bi.getOtherAttributes());
      System.out.println(" Input Other Elements: " + bi.getOtherElements());
      System.out.println(" Input HTTP Binding4Wsdl11: " + bi.getHTTPBinding4Wsdl11());
      System.out.println(" Input HTTP Binding4Wsdl20: " + bi.getHTTPBinding4Wsdl20());
      System.out.println(" Input MIME Binding4Wsdl11: " + bi.getMIMEBinding4Wsdl11());
      System.out.println(" Input SOAP11 Binding4Wsdl11: " + bi.getSOAP11Binding4Wsdl11());
      System.out.println(" Input SOAP12 Binding4Wsdl11: " + bi.getSOAP12Binding4Wsdl11());
      System.out.println(" Input SOAP12 Binding4Wsdl20: " + bi.getSOAP12Binding4Wsdl20());

      System.out.println(" ******************* SOAP Body **************************** ");
      System.out.println(" Input Parts : " + bi.getSOAP11Binding4Wsdl11().getBody().getParts());
      System.out.println(" Input Namespace : " + bi.getSOAP11Binding4Wsdl11().getBody().getNamespace());
      System.out.println(" Input Use : " + bi.getSOAP11Binding4Wsdl11().getBody().getUse());
      System.out.println(" Input Required : " + bi.getSOAP11Binding4Wsdl11().getBody().getRequired());
      System.out.println(" Input Parts : " + bi.getSOAP11Binding4Wsdl11().getBody().getParts());

      if (bi.getSOAP11Binding4Wsdl11().getFault() != null) {
         System.out.println(" ******************* SOAP Fault **************************** ");
         System.out.println(" Input Name : " + bi.getSOAP11Binding4Wsdl11().getFault().getName());
         System.out.println(" Input Parts : " + bi.getSOAP11Binding4Wsdl11().getFault().getParts());
         System.out.println(" Input Namespace : " + bi.getSOAP11Binding4Wsdl11().getFault().getNamespace());
         System.out.println(" Input Use : " + bi.getSOAP11Binding4Wsdl11().getFault().getUse());
         System.out.println(" Input Required : " + bi.getSOAP11Binding4Wsdl11().getFault().getRequired());
         System.out.println(" Input Encoding Styles : "
                 + bi.getSOAP11Binding4Wsdl11().getFault().getEncodingStyles());
      }
   }

   //	@Test
   public void testGetInputByBindingOperation() throws XmlException {

      IBindingInputDAO bindingInputDAO = new BindingInputDAO();
      BindingInputBean bi = new BindingInputBean();
      QName bindingQName = desc.getServices().get(0).getEndpoints().get(0).getBinding().getQName();
      String operationName = "CurrentOilPrice";
      bi = bindingInputDAO.getInputBindingByBinding(desc, bindingQName, operationName);

      System.out.println(" ******************* testGetInputByBindingOperation() **************************** ");
      System.out.println(" Input Name: " + bi.getName());
      System.out.println(" Input Name: " + bi.getHttpContentEncoding());
      System.out.println(" Input Documentation: " + bi.getDocumentation());
      System.out.println(" Input Other Attributes: " + bi.getOtherAttributes());
      System.out.println(" Input Other Elements: " + bi.getOtherElements());
      System.out.println(" Input HTTP Binding4Wsdl11: " + bi.getHTTPBinding4Wsdl11());
      System.out.println(" Input HTTP Binding4Wsdl20: " + bi.getHTTPBinding4Wsdl20());
      System.out.println(" Input MIME Binding4Wsdl11: " + bi.getMIMEBinding4Wsdl11());
      System.out.println(" Input SOAP11 Binding4Wsdl11: " + bi.getSOAP11Binding4Wsdl11());
      System.out.println(" Input SOAP12 Binding4Wsdl11: " + bi.getSOAP12Binding4Wsdl11());
      System.out.println(" Input SOAP12 Binding4Wsdl20: " + bi.getSOAP12Binding4Wsdl20());
   }
}
