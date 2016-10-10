/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wsdl;

import org.ow2.easywsdl.schema.api.Attribute;
import org.ow2.easywsdl.schema.api.Restriction;
import java.util.ArrayList;
import org.wsssoapbox.bean.model.soap.SoapParameterBean;
import org.ow2.easywsdl.schema.api.SimpleType;
import org.wsssoapbox.soap.support.SoapConstants;
import java.util.List;
import org.ow2.easywsdl.schema.api.Sequence;
import org.ow2.easywsdl.schema.api.Choice;
import org.ow2.easywsdl.schema.api.All;
import org.ow2.easywsdl.schema.api.ComplexType;
import org.ow2.easywsdl.wsdl.api.Part;
import org.ow2.easywsdl.wsdl.api.Input;
import org.ow2.easywsdl.schema.api.Element;
import javax.xml.namespace.QName;
import org.ow2.easywsdl.schema.api.Type;
import java.util.Iterator;
import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.api.Description;
import org.ow2.easywsdl.schema.api.Schema;
import javax.xml.bind.JAXBElement;
import org.ow2.easywsdl.schema.org.w3._2001.xmlschema.ExplicitGroup;
import org.ow2.easywsdl.schema.org.w3._2001.xmlschema.LocalComplexType;
import org.ow2.easywsdl.schema.org.w3._2001.xmlschema.TopLevelElement;
import org.wsssoapbox.datasource.wsdl.WSDLDataSource;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Peter
 */
public class SchemaTest {

   private static Description desc;
   private static String url;
   private static WSDLDataSource wsdlDocumentDataSource;
   TopLevelElement topLevel;
   LocalComplexType localComplexType;
   ExplicitGroup explicitGroup;
   JAXBElement jaxbElement;
   Schema schema;

   @BeforeClass
   public static void setUpBeforeClass() throws Exception {
      wsdlDocumentDataSource = new WSDLDataSource(WSDLList.url);
      desc = WSDLDataSource.getDesc(WSDLList.url);

   }

   public SchemaTest() {
   }

   // @Test
   public void testGetSchemas() throws XmlException {

      Iterator<Schema> itSchemas = desc.getTypes().getSchemas().iterator();

      System.out.println("***************** Schemas  testGetSchemas()**********************");
      while (itSchemas.hasNext()) {

         Schema s = itSchemas.next();
         System.out.println("   Namespaces   : " + s.getAllNamespaces());
         System.out.println("   Attribute Form Default  : " + s.getAttributeFormDefault());
         System.out.println("   Attributes   : " + s.getAttributes());
         System.out.println("   BlockDefault   : " + s.getBlockDefault());
         System.out.println("   Documentation   : " + s.getDocumentation());
         System.out.println("   Element Form Default   : " + s.getElementFormDefault());
         System.out.println("   Elements   : " + s.getElements());
         System.out.println("   Final Default   : " + s.getFinalDefault());
         System.out.println("   Imports   : " + s.getImports());
         System.out.println("   Includes   : " + s.getIncludes());
         System.out.println("   Namespaces   : " + s.getLang());
         System.out.println("   Lang   : " + s.getOtherAttributes());
         System.out.println("   Target Namespace   : " + s.getTargetNamespace());
         System.out.println("   Types   : " + s.getTypes());
         System.out.println("   Version   : " + s.getVersion());

      }
   }

   //  @Test
   public void testGetSchemasTypes() throws XmlException {
      Iterator<Schema> itSchema = desc.getTypes().getSchemas().iterator();
      while (itSchema.hasNext()) {
         Schema schemas = itSchema.next();

         Iterator<Type> itType = schemas.getTypes().iterator();
         while (itType.hasNext()) {
            Type type = itType.next();
            System.out.println("Types :" + type.getQName());
            System.out.println("Element :");
            schemas.getElement(type.getQName());
         }
      }

   }

   @Test
   public void testGetParameter() throws XmlException {
      QName bindingQName = desc.getBindings().get(0).getQName();
      QName interfaceQName = desc.getBindings().get(0).getInterface().getQName();
      QName operationQName = desc.getBindings().get(0).getBindingOperations().get(0).getQName();
      testFullLoop(desc, bindingQName, interfaceQName, operationQName);
   }

   private void testFullLoop(Description desc, QName bindingQName, QName interfaceQName, QName operationQName)
           throws XmlException {

      List<SoapParameterBean> parameters = new ArrayList<SoapParameterBean>();
      String operationName = operationQName.getLocalPart();
      String soapVersion = desc.getBinding(bindingQName).getVersion();
      String soapAction = desc.getBinding(bindingQName).getBindingOperation(operationName).getSoapAction();

      System.out.println("wsld url  : " + desc.getDocumentBaseURI());
      System.out.println("Interface QName  : " + interfaceQName);
      System.out.println("Operation QName  : " + operationQName);
      System.out.println("Operation Name  : " + operationName);
      System.out.println("Soap Vesion  : " + soapVersion);
      System.out.println("Soap Action  : " + soapAction);


      // get Element correspond that Operation and Interface
      Input input = desc.getInterface(interfaceQName).getOperation(operationQName).getInput();
      Iterator<Part> itoratorParts = input.getParts().iterator();

      while (itoratorParts.hasNext()) {
         Part part = itoratorParts.next();

         System.out.println("Element  : " + part.getElement());
         System.out.println("Part QName  : " + part.getPartQName());
         System.out.println("Type  : " + part.getType());
         System.out.println("Other Attributes : " + part.getOtherAttributes());
         System.out.println("Other Elements  : " + part.getOtherElements());


         System.out.println("Schema Size  : " + desc.getTypes().getSchemas().size() + " Scheamas");
         Element element = null;
         Type type = null;

         QName inputPartQName = part.getType().getQName();
         System.out.println("Input Part QName  : " + inputPartQName);
         System.out.println("Schema Size  : " + desc.getTypes().getSchemas().size() + " Scheamas");


         // find elements reponsible with Input of opration
         Iterator<Schema> itSchemas = desc.getTypes().getSchemas().iterator();
         while (itSchemas.hasNext()) {
            Schema schema = itSchemas.next();

            System.out.println("Schema Elements  : " + schema.getElements());
            System.out.println("Schema Types  : " + schema.getTypes());

            element = schema.getElement(inputPartQName);
            type = schema.getType(inputPartQName);
            Type paramType = null;

            if (element != null) {
               System.out.println("Input Element QName for Input : " + element.getQName());
               System.out.println("Input Element Min  for Input : " + element.getMinOccurs());
               System.out.println("Input Element Max  for Input : " + element.getMaxOccurs());
               System.out.println("Input Element Form  for Input : " + element.getForm());
               System.out.println("Input Element Ref  for Input : " + element.getRef());
               System.out.println("Input Element getOtherAttributes  for Input : " + element.getOtherAttributes());
               System.out.println("***************************************************");
               System.out.println("Element Type  : " + element.getType());
               System.out.println("***************************************************");

               paramType = element.getType();

            }
            if (type != null) {
               System.out.println("Type QName: " + type.getQName());
               paramType = type;
            }


            if (paramType instanceof ComplexType) {
               final ComplexType elementComplexType = (ComplexType) paramType;
               final All elementALL = elementComplexType.getAll();
               final Choice elementChoice = elementComplexType.getChoice();
               final Sequence elementSeq = elementComplexType.getSequence();
               final List<Attribute> attributes = elementComplexType.getAttributes();

               System.out.println("******: " + "ComplexType ************** ");

               System.out.println("Complex Type :" + elementComplexType);
               System.out.println("All Type :" + elementALL);
               System.out.println("Choice Type :" + elementChoice);
               System.out.println("Sequence :" + elementSeq);

               if (elementSeq instanceof Sequence) {
                  final List<Element> elementSeqList = elementSeq.getElements();
                  System.out.println("***********Element Sequence ************** ");
                  System.out.println("Amount of Element :" + elementSeqList.size());
                  Iterator<Element> iTElemnt = elementSeqList.iterator();

                  while (iTElemnt.hasNext()) {
                     Element e = iTElemnt.next();
                     int minOccure = SoapConstants.PARA_DEFAULT_MIN_OCCURE;
                     String defaultValue = SoapConstants.PARA_DEFAULT_VALUE;
                     String maxOccure = SoapConstants.PARA_DEFAULT_MAX_OCCURE;
                     String seqType = "";
                     String name = "";
                     String paraNamespaceURI = "";
                     String paralocalPart = "";
                     String paraPrefix = "";


                     Type eType = e.getType();
                     if (e.getType() != null && e.getQName() != null) {
                        if (eType instanceof SimpleType) {
                           System.out.println("**************************" + "Simple Type ************** ");
                           minOccure = e.getMinOccurs();
                           maxOccure = e.getMaxOccurs();
                           seqType = e.getType().getQName().getLocalPart();
                           name = e.getQName().getLocalPart();
                           paraNamespaceURI = e.getQName().getNamespaceURI();
                           paralocalPart = e.getQName().getLocalPart();
                           paraPrefix = e.getQName().getPrefix();


                        }
                        if (eType instanceof ComplexType) {
                           System.out.println("**************************" + "Complex Type ************** ");

                           final ComplexType subElementComplexType = (ComplexType) eType;
                           final Sequence subElementSequence = subElementComplexType.getSequence();
                           Iterator<Element> it = subElementSequence.getElements().iterator();

                           while (it.hasNext()) {
                              Element et = it.next();
                              minOccure = et.getMinOccurs();
                              maxOccure = et.getMaxOccurs();
                              seqType = et.getType().getQName().getLocalPart();
                              name = et.getQName().getLocalPart();
                              paraNamespaceURI = et.getQName().getNamespaceURI();
                              paralocalPart = et.getQName().getLocalPart();
                              paraPrefix = et.getQName().getPrefix();

                           }
                        }

                     }

                     System.out.println(" ************************************************");

                     System.out.println(" Element Name  :" + name);
                     System.out.println(" Element Type  :" + seqType + "");
                     System.out.println(" Element localPart  :" + paralocalPart);
                     System.out.println(" Element Prefix  :" + paraPrefix);
                     System.out.println(" Element Min Occure  :" + minOccure);
                     System.out.println(" Element Max Occure  :" + maxOccure);
                     System.out.println(" Element DefaultValue  :" + defaultValue);
                     System.out.println(" Element paraNamespaceURI  :" + paraNamespaceURI);
                     System.out.println(" Element Other Attributes  :" + e.getOtherAttributes());


                     QName paraQName = new QName(paraNamespaceURI, paralocalPart, paraPrefix);
                     SoapParameterBean p1 = new SoapParameterBean(name, seqType, minOccure, maxOccure, paraQName,
                             defaultValue);
                     parameters.add(p1);

                  }


               }

               if (elementALL instanceof All) {
                  final List<Element> elementAllList = elementALL.getElements();
                  System.out.println("*********** " + "Element ALL ************** ");
                  System.out.println("Amount of Element :" + elementAllList.size());

                  Iterator<Element> iTElemnt = elementAllList.iterator();

                  while (iTElemnt.hasNext()) {
                     Element e = iTElemnt.next();
                     int minOccure = SoapConstants.PARA_DEFAULT_MIN_OCCURE;
                     String defaultValue = SoapConstants.PARA_DEFAULT_VALUE;
                     String maxOccure = SoapConstants.PARA_DEFAULT_MAX_OCCURE;
                     String allType = "";
                     String name = "";
                     String paraNamespaceURI = "";
                     String paralocalPart = "";
                     String paraPrefix = "";


                     Type eType = e.getType();
                     if (e.getType() != null && e.getQName() != null) {
                        if (eType instanceof SimpleType) {
                           System.out.println("**************************" + "SimpleType ************** ");
                           minOccure = e.getMinOccurs();
                           maxOccure = e.getMaxOccurs();
                           allType = e.getType().getQName().getLocalPart();
                           name = e.getQName().getLocalPart();
                           paraNamespaceURI = e.getQName().getNamespaceURI();
                           paralocalPart = e.getQName().getLocalPart();
                           paraPrefix = e.getQName().getPrefix();
                        }
                     }

                     System.out.println(" ************************************************");

                     System.out.println(" Element Name  :" + name);
                     System.out.println(" Element Type  :" + type + "");
                     System.out.println(" Element localPart  :" + paralocalPart);
                     System.out.println(" Element Prefix  :" + paraPrefix);
                     System.out.println(" Element Min Occure  :" + minOccure);
                     System.out.println(" Element Max Occure  :" + maxOccure);
                     System.out.println(" Element DefaultValue  :" + defaultValue);
                     System.out.println(" Element paraNamespaceURI  :" + paraNamespaceURI);
                     System.out.println(" Element Other Attributes  :" + e.getOtherAttributes());

                     QName paraQName = new QName(paraNamespaceURI, paralocalPart, paraPrefix);
                     SoapParameterBean param = new SoapParameterBean(name, allType, minOccure, maxOccure, paraQName,
                             defaultValue);
                     parameters.add(param);

                  }
               }


               if (elementChoice instanceof Choice) {
                  final List<Element> elementChoiceList = elementChoice.getElements();
                  System.out.println("***********  " + "Element Choice ************** ");
                  System.out.println("Amount of Element :" + elementChoiceList.size());
                  Iterator<Element> iTElemnt = elementChoiceList.iterator();

                  while (iTElemnt.hasNext()) {
                     Element e = iTElemnt.next();
                     int minOccure = SoapConstants.PARA_DEFAULT_MIN_OCCURE;
                     String defaultValue = SoapConstants.PARA_DEFAULT_VALUE;
                     String maxOccure = SoapConstants.PARA_DEFAULT_MAX_OCCURE;
                     String choiceType = "";
                     String name = "";
                     String paraNamespaceURI = "";
                     String paralocalPart = "";
                     String paraPrefix = "";


                     Type eType = e.getType();
                     if (e.getType() != null && e.getQName() != null) {
                        if (eType instanceof SimpleType) {
                           System.out.println("**************************" + "SimpleType ************** ");
                           minOccure = e.getMinOccurs();
                           maxOccure = e.getMaxOccurs();
                           choiceType = e.getType().getQName().getLocalPart();
                           name = e.getQName().getLocalPart();
                           paraNamespaceURI = e.getQName().getNamespaceURI();
                           paralocalPart = e.getQName().getLocalPart();
                           paraPrefix = e.getQName().getPrefix();
                        }
                     }

                     System.out.println(" ************************************************");

                     System.out.println(" Element Name  :" + name);
                     System.out.println(" Element Type  :" + type + "");
                     System.out.println(" Element localPart  :" + paralocalPart);
                     System.out.println(" Element Prefix  :" + paraPrefix);
                     System.out.println(" Element Min Occure  :" + minOccure);
                     System.out.println(" Element Max Occure  :" + maxOccure);
                     System.out.println(" Element DefaultValue  :" + defaultValue);
                     System.out.println(" Element paraNamespaceURI  :" + paraNamespaceURI);
                     System.out.println(" Element Other Attributes  :" + e.getOtherAttributes());

                     QName paraQName = new QName(paraNamespaceURI, paralocalPart, paraPrefix);
                     SoapParameterBean param = new SoapParameterBean(name, choiceType, minOccure, maxOccure, paraQName,
                             defaultValue);
                     parameters.add(param);

                  }
               }


            } else if (paramType instanceof SimpleType) {
               final SimpleType simpleType = (SimpleType) paramType;
               final Restriction res = simpleType.getRestriction();
               System.out.println("***********: " + "Simple Type ************** ");
               System.out.println("SimpleType Element :" + simpleType);
               System.out.println("Restriction Element :" + res.getBase());
               System.out.println("Restriction Element :" + res.getEnumerations());

            }
         }// end loop Messages
      } // end loop  parts

   }
}
