package wsdl;

import java.util.List;
import static org.junit.Assert.*;

import javax.xml.namespace.QName;

import org.junit.Before;
import org.junit.Test;
import org.ow2.easywsdl.schema.api.Element;
import org.ow2.easywsdl.schema.api.Schema;
import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.api.Description;
import org.ow2.easywsdl.wsdl.api.Input;
import org.ow2.easywsdl.wsdl.api.Operation;
import org.ow2.easywsdl.wsdl.impl.wsdl11.DescriptionImpl;
import org.ow2.easywsdl.wsdl.impl.wsdl11.MessageImpl;
import org.wsssoapbox.bean.model.wsdl.InputBean;
import org.wsssoapbox.dao.wsdl.IInputDAO;
import org.wsssoapbox.dao.wsdl.InputDAO;
import org.wsssoapbox.datasource.wsdl.WSDLDataSource;

/**
 * @author  Peter
 */
public class InputDAOTestCase {

   private static Description desc;
   /**
    * @uml.property  name="wsdlDocumentDataSource"
    * @uml.associationEnd  
    */
   private static WSDLDataSource wsdlDocumentDataSource;

   @Before
   public void setUp() throws Exception {
      wsdlDocumentDataSource = new WSDLDataSource(WSDLList.url);
      InputDAOTestCase.desc = WSDLDataSource.getDesc(WSDLList.url);
   }

   @Test
   public void testGetInputDefault() throws XmlException {


      List<MessageImpl> messages = ((DescriptionImpl) desc).getMessages();
      System.out.println(" Message Size : " + messages.size());
      for (MessageImpl m : messages) {
         System.out.println("************************** ");
         System.out.println(" Message: " + m.getQName());
         System.out.println("Part Size : " + m.getParts().size());
         if (!m.getParts().isEmpty()) {
            System.out.println("Part Name : " + m.getParts().get(0).getPartQName());
            System.out.println("Part Type : " + m.getParts().get(0).getType().getQName());
         }
         System.out.println("");
      }

      
            // find element in Type >> schema by element
      List<Schema> schemas = desc.getTypes().getSchemas();
      System.out.println("Schema size : "+schemas.size());
      
      for (Schema schema : schemas) {
         System.out.println("Element size : "+schema.getElements().size());
         for(Element e : schema.getElements()){
            System.out.println("Element :"+e.getQName());
         }
      }
      
      
      Input ib = desc.getInterfaces().get(0).getOperations().get(0).getInput();

      System.out.println(" ********************* Input testGetInputDefault *************************** ");
      System.out.println(" Input Name: " + ib.getName());
      System.out.println(" Input Documentation: " + ib.getDocumentation());
      System.out.println(" Input Other Attributes: " + ib.getOtherAttributes());
      System.out.println(" Input Other Elements: " + ib.getOtherElements());
      System.out.println(" *******************Element *************************** ");
      System.out.println(" Input Element: " + ib.getElement());
      System.out.println("                    Element :" + ib.getElement());
      System.out.println(" *******************Message Name *************************** ");
      System.out.println(" Input MessageName: " + ib.getMessageName());
      System.out.println("                    Name :" + ib.getMessageName().getLocalPart());
      System.out.println("                    Namespace URI:" + ib.getMessageName().getNamespaceURI());
      System.out.println("                    Prefix :" + ib.getMessageName().getPrefix());
      System.out.println(" *******************Part  *************************** ");
      System.out.println(" Input Parts: " + ib.getParts());
   }

   //@Test
   public void testGetInputByInterface() throws XmlException {
      QName interfaceQName = desc.getInterfaces().get(0).getQName();
      QName operationQName = desc.getInterfaces().get(0).getOperations().get(0).getQName();
      IInputDAO inputDAO = new InputDAO();
      InputBean ib = new InputBean();
      ib = inputDAO.getInputByInterface(desc, interfaceQName, operationQName);

      System.out.println(" ********************* Input testGetInputByInterface() *************************** ");
      System.out.println(" Input Name: " + ib.getName());
      System.out.println(" Input Documentation: " + ib.getDocumentation());
      System.out.println(" Input Other Attributes: " + ib.getOtherAttributes());
      System.out.println(" Input Other Elements: " + ib.getOtherElements());
      System.out.println(" *******************Element *************************** ");
      System.out.println(" Input Element: " + ib.getElement());
      System.out.println("                    Element :" + ib.getElement());
      System.out.println(" *******************Message Name *************************** ");
      System.out.println(" Input MessageName: " + ib.getMessageName());
      System.out.println("                    Name :" + ib.getMessageName().getLocalPart());
      System.out.println("                    Namespace URI:" + ib.getMessageName().getNamespaceURI());
      System.out.println("                    Prefix :" + ib.getMessageName().getPrefix());
      System.out.println(" *******************Part  *************************** ");
      System.out.println(" Input Parts: " + ib.getParts());
   }

//	@Test
   public void testGetInputByInterfaceIndex() throws XmlException {

      QName operationQName = desc.getInterfaces().get(0).getOperations().get(0).getQName();
      IInputDAO inputDAO = new InputDAO();
      InputBean ib = new InputBean();
      ib = inputDAO.getInputByInterfaceIndex(desc, 0, operationQName);

      System.out.println(" ********************* Input testGetInputByInterfaceIndex() *************************** ");
      System.out.println(" Input Name: " + ib.getName());
      System.out.println(" Input Documentation: " + ib.getDocumentation());
      System.out.println(" Input Other Attributes: " + ib.getOtherAttributes());
      System.out.println(" Input Other Elements: " + ib.getOtherElements());
      System.out.println(" *******************Element *************************** ");
      System.out.println(" Input Element: " + ib.getElement());
      System.out.println("                    Element :" + ib.getElement());
      System.out.println(" *******************Message Name *************************** ");
      System.out.println(" Input MessageName: " + ib.getMessageName());
      System.out.println("                    Name :" + ib.getMessageName().getLocalPart());
      System.out.println("                    Namespace URI:" + ib.getMessageName().getNamespaceURI());
      System.out.println("                    Prefix :" + ib.getMessageName().getPrefix());
      System.out.println(" *******************Part  *************************** ");
      System.out.println(" Input Parts: " + ib.getParts());
   }

//	@Test
   public void testGetInputByBinding() throws XmlException {
      QName bindingQName = desc.getBindings().get(0).getQName();
      String operationName = desc.getBinding(bindingQName).getBindingOperations().get(0).getQName().getLocalPart();

      System.out.println(" bindingQName :" + bindingQName);
      System.out.println(" operationName :" + operationName);

      IInputDAO inputDAO = new InputDAO();
      InputBean ib = new InputBean();

      ib = inputDAO.getInputByBinding(desc, bindingQName, operationName);

      if (ib.getMessageName() != null) {
         System.out.println(" ********************* Input testGetInputByBinding() *************************** ");
         System.out.println(" Input Name: " + ib.getName());
         System.out.println(" Input Documentati *************************** ");
         System.out.println(" Input Name: " + ib.getName());
         System.out.println(" Input Documentation: " + ib.getDocumentation());
         System.out.println(" Input Other Attributes: " + ib.getOtherAttributes());
         System.out.println(" Input Other Elements: " + ib.getOtherElements());
         System.out.println(" *******************Element *************************** ");
         System.out.println(" Input Element: " + ib.getElement());
         System.out.println("                    Element :" + ib.getElement());
         System.out.println(" *******************Message Name *************************** ");
         System.out.println(" Input MessageName: " + ib.getMessageName());
         System.out.println("                    Name :" + ib.getMessageName().getLocalPart());
         System.out.println("                    Namespace URI:" + ib.getMessageName().getNamespaceURI());
         System.out.println("                    Prefix :" + ib.getMessageName().getPrefix());
         System.out.println(" *******************Part  *************************** ");
         System.out.println(" Input Parts: " + ib.getParts());
      }
   }

//	@Test
   public void testGetInputByBindingOperation() throws XmlException {

      IInputDAO inputDAO = new InputDAO();
      InputBean ib = new InputBean();
      Operation operation = desc.getInterfaces().get(0).getOperations().get(0);
      ib = inputDAO.getInputByBindingOperation(operation);

      System.out.println(" ********************* Input testGetInputByBindingOperation() *************************** ");
      System.out.println(" Input Name: " + ib.getName());
      System.out.println(" Input Documentation: " + ib.getDocumentation());
      System.out.println(" Input Other Attributes: " + ib.getOtherAttributes());
      System.out.println(" Input Other Elements: " + ib.getOtherElements());
      System.out.println(" *******************Element *************************** ");
      System.out.println(" Input Element: " + ib.getElement());
      System.out.println("                    Element :" + ib.getElement());
      System.out.println(" *******************Message Name *************************** ");
      System.out.println(" Input MessageName: " + ib.getMessageName());
      System.out.println("                    Name :" + ib.getMessageName().getLocalPart());
      System.out.println("                    Namespace URI:" + ib.getMessageName().getNamespaceURI());
      System.out.println("                    Prefix :" + ib.getMessageName().getPrefix());
      System.out.println(" *******************Part  *************************** ");
      System.out.println(" Input Parts: " + ib.getParts());
   }

   //	@Test
   public void testGetInputByOperation() throws XmlException {

      Operation operation = desc.getInterfaces().get(0).getOperations().get(0);
      IInputDAO inputDAO = new InputDAO();
      InputBean ib = new InputBean();
      ib = inputDAO.getInputByOperation(operation);

      System.out.println(" ********************* Input testGetInputByOperation() *************************** ");
      System.out.println(" Input Name: " + ib.getName());
      System.out.println(" Input Documentation: " + ib.getDocumentation());
      System.out.println(" Input Other Attributes: " + ib.getOtherAttributes());
      System.out.println(" Input Other Elements: " + ib.getOtherElements());
      System.out.println(" *******************Element *************************** ");
      System.out.println(" Input Element: " + ib.getElement());
      System.out.println("                    Element :" + ib.getElement());
      System.out.println(" *******************Message Name *************************** ");
      System.out.println(" Input MessageName: " + ib.getMessageName());
      System.out.println("                    Name :" + ib.getMessageName().getLocalPart());
      System.out.println("                    Namespace URI:" + ib.getMessageName().getNamespaceURI());
      System.out.println("                    Prefix :" + ib.getMessageName().getPrefix());
      System.out.println(" *******************Part  *************************** ");
      System.out.println(" Input Parts: " + ib.getParts());
   }
}
