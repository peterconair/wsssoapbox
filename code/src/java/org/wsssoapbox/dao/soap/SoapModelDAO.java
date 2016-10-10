package org.wsssoapbox.dao.soap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;


import org.slf4j.Logger;
import org.ow2.easywsdl.schema.api.All;
import org.ow2.easywsdl.schema.api.Choice;
import org.ow2.easywsdl.schema.api.ComplexType;
import org.ow2.easywsdl.schema.api.Element;
import org.ow2.easywsdl.schema.api.Restriction;
import org.ow2.easywsdl.schema.api.Schema;
import org.ow2.easywsdl.schema.api.Sequence;
import org.ow2.easywsdl.schema.api.SimpleType;
import org.ow2.easywsdl.schema.api.Type;
import org.ow2.easywsdl.schema.api.XmlException;
import org.ow2.easywsdl.wsdl.api.Description;
import org.ow2.easywsdl.wsdl.api.Input;
import org.ow2.easywsdl.wsdl.api.Part;
import org.slf4j.LoggerFactory;
import org.wsssoapbox.bean.model.soap.SoapOperationBean;
import org.wsssoapbox.bean.model.soap.SoapParameterBean;
import org.wsssoapbox.soap.support.SoapConstants;
import org.wsssoapbox.bean.model.wsdl.SoapBindingBean;

public class SoapModelDAO implements ISoapModelDAO {

   private static final Logger logger = LoggerFactory.getLogger(SoapModelDAO.class);

   @Override
   public SoapBindingBean createSoapBindingBeanRPC1_1(Description desc, QName interfaceQName, QName operationQName)
           throws XmlException {

      SoapBindingBean soapBindingBean = new SoapBindingBean();
      List<SoapParameterBean> parameters = new ArrayList<SoapParameterBean>();

      logger.debug("wsld url  : " + desc.getDocumentBaseURI());
      logger.debug("Interface QName  : " + interfaceQName);
      logger.debug("Operation QName  : " + operationQName);
      logger.debug("getSchemas Size : " + desc.getTypes().getSchemas().size());


      String form = desc.getTypes().getSchemas().get(0).getElementFormDefault().value();
      soapBindingBean.setElementFormDefault(form);


      Input input = desc.getInterface(interfaceQName).getOperation(operationQName).getInput();

      List<Part> parts = input.getParts();

      for (Part part : parts) {

         String defaultValue = SoapConstants.PARA_DEFAULT_VALUE;
         int minOccure = SoapConstants.PARA_DEFAULT_MIN_OCCURE;
         String maxOccure = SoapConstants.PARA_DEFAULT_MAX_OCCURE;

         String type = part.getType().getQName().getLocalPart();
         String name = part.getPartQName().getLocalPart();
         String paraNamespaceURI = part.getPartQName().getNamespaceURI();
         String paralocalPart = part.getPartQName().getLocalPart();
         String paraPrefix = part.getPartQName().getPrefix();


         QName paraQName = new QName(paraNamespaceURI, paralocalPart, paraPrefix);
         SoapParameterBean param = new SoapParameterBean(name, type, minOccure, maxOccure, paraQName,
                 defaultValue);
         parameters.add(param);
      }



      String operationName = operationQName.getLocalPart();
      String operationPrefix = operationQName.getPrefix();
      if (operationPrefix.equals("") || operationPrefix == null) {
         operationPrefix = "tns";
      }

      SoapOperationBean operation = new SoapOperationBean(operationName, operationQName, parameters);

      SoapBindingBean soapRequest = new SoapBindingBean();

      //Choose necessary schema are well form Soap speck
      Map<String, String> namespaces = new HashMap<String, String>();
      Map<String, String> tmp = desc.getNamespaces().getNamespaces();
      List<String> knowSchemas = SoapConstants.WELL_FORM_SCHEMAS;

      logger.debug("List of Know Schemas : " + knowSchemas);
      if (tmp != null) {
         Iterator iteratorTemp = tmp.entrySet().iterator();

         for (String name : knowSchemas) {
            while (iteratorTemp.hasNext()) {
               Map.Entry<String, String> me = (Map.Entry) iteratorTemp.next();
               String key = me.getKey();
               String value = me.getValue();
               if (name.equals(value)) {
                  namespaces.put(key, value);
               }
            }
         }
      }
      logger.debug("List of Schemas : " + namespaces);

      soapRequest.setSoapVersion("1.1");
      soapRequest.setHeader(false);
      soapRequest.setXmlStandalone(true);
      soapRequest.setNamespaces(namespaces);
      soapRequest.setOperation(operation);

      return soapRequest;
   }

   @Override
   public SoapBindingBean createSoapBindingBeanDoc(Description desc, QName bindingQName, QName interfaceQName,
           QName operationQName) throws XmlException {

      QName inputQName = null;
      SoapBindingBean soapBindingBean = new SoapBindingBean();
      List<SoapParameterBean> parameters = new ArrayList<SoapParameterBean>();

      String operationName = operationQName.getLocalPart();
      String soapVersion = desc.getBinding(bindingQName).getVersion();
      String soapAction = desc.getBinding(bindingQName).getBindingOperation(operationName).getSoapAction();

      logger.debug("wsld url  : " + desc.getDocumentBaseURI());
      logger.debug("Interface QName  : " + interfaceQName);
      logger.debug("Operation QName  : " + operationQName);
      logger.debug("Operation Name  : " + operationName);
      logger.debug("Soap Vesion  : " + soapVersion);
      logger.debug("Soap Action  : " + soapAction);


      // get Element correspond that Operation and Interface
      Element inputElement = desc.getInterface(interfaceQName).getOperation(operationQName).getInput().getElement();

      logger.debug("input Element Name: " + inputElement.getQName().getLocalPart());
      logger.debug("inputElement.getForm(): " + inputElement.getForm());
      soapBindingBean.setElementFormDefault(inputElement.getForm().value());

      logger.debug("Schema Size  : " + desc.getTypes().getSchemas().size() + " Scheamas");
      Element element = null;

      inputQName = inputElement.getQName();

      // find elements reponsible with Input of opration
      Iterator<Schema> itSchemas = desc.getTypes().getSchemas().iterator();
      while (itSchemas.hasNext()) {
         Schema schema = itSchemas.next();
         element = schema.getElement(inputQName);
         logger.debug("Element Name : " + element.getQName().getLocalPart());
         //get Element Form Default
      }

      logger.debug("Input Element QName for Input : " + element.getQName());
      logger.debug("Input Element Min  for Input : " + element.getMinOccurs());
      logger.debug("Input Element Max  for Input : " + element.getMaxOccurs());
      logger.debug("Input Element Form  for Input : " + element.getForm());
      logger.debug("Input Element Ref  for Input : " + element.getRef());
      logger.debug("Input Element getOtherAttributes  for Input : " + element.getOtherAttributes());
      logger.debug("***************************************************");
      logger.debug("Element Type  : " + element.getType());
      logger.debug("***************************************************");

      Type paramType = element.getType();

      if (paramType instanceof ComplexType) {
         final ComplexType elementComplexType = (ComplexType) paramType;
         final All elementALL = elementComplexType.getAll();
         final Choice elementChoice = elementComplexType.getChoice();
         final Sequence elementSeq = elementComplexType.getSequence();

         logger.debug("***********: " + "ComplexType ************** ");

         logger.debug("Complex Type :" + elementComplexType);
         logger.debug("All Type :" + elementALL);
         logger.debug("Choice Type :" + elementChoice);
         logger.debug("Sequence :" + elementSeq);

         if (elementSeq instanceof Sequence) {
            final List<Element> elementSeqList = elementSeq.getElements();
            logger.debug("***********Element Sequence ************** ");
            logger.debug("Amount of Element :" + elementSeqList.size());
            Iterator<Element> iTElemnt = elementSeqList.iterator();

            while (iTElemnt.hasNext()) {
               Element e = iTElemnt.next();
               int minOccure = SoapConstants.PARA_DEFAULT_MIN_OCCURE;
               String defaultValue = SoapConstants.PARA_DEFAULT_VALUE;
               String maxOccure = SoapConstants.PARA_DEFAULT_MAX_OCCURE;
               String type = "";
               String name = "";
               String paraNamespaceURI = "";
               String paralocalPart = "";
               String paraPrefix = "";


               Type eType = e.getType();
               if (e.getType() != null && e.getQName() != null) {
                  if (eType instanceof SimpleType) {
                     logger.debug("**************************" + "SimpleType ************** ");
                     minOccure = e.getMinOccurs();
                     maxOccure = e.getMaxOccurs();
                     try {
                        type = e.getType().getQName().getLocalPart();
                     } catch (NullPointerException ex) {
                        type = "string";
                     }

                     name = e.getQName().getLocalPart();
                     paraNamespaceURI = e.getQName().getNamespaceURI();
                     paralocalPart = e.getQName().getLocalPart();
                     paraPrefix = e.getQName().getPrefix();
                  }
               }

               logger.debug(" ************************************************");

               logger.debug(" Element Name  :" + name);
               logger.debug(" Element Type  :" + type + "");
               logger.debug(" Element localPart  :" + paralocalPart);
               logger.debug(" Element Prefix  :" + paraPrefix);
               logger.debug(" Element Min Occure  :" + minOccure);
               logger.debug(" Element Max Occure  :" + maxOccure);
               logger.debug(" Element DefaultValue  :" + defaultValue);
               logger.debug(" Element paraNamespaceURI  :" + paraNamespaceURI);
               logger.debug(" Element Other Attributes  :" + e.getOtherAttributes());


               QName paraQName = new QName(paraNamespaceURI, paralocalPart, paraPrefix);
               SoapParameterBean p1 = new SoapParameterBean(name, type, minOccure, maxOccure, paraQName,
                       defaultValue);
               parameters.add(p1);

            }
         }

         if (elementALL instanceof All) {
            final List<Element> elementAllList = elementALL.getElements();
            logger.debug("*********** " + "Element ALL ************** ");
            logger.debug("Amount of Element :" + elementAllList.size());

            Iterator<Element> iTElemnt = elementAllList.iterator();

            while (iTElemnt.hasNext()) {
               Element e = iTElemnt.next();
               int minOccure = SoapConstants.PARA_DEFAULT_MIN_OCCURE;
               String defaultValue = SoapConstants.PARA_DEFAULT_VALUE;
               String maxOccure = SoapConstants.PARA_DEFAULT_MAX_OCCURE;
               String type = "";
               String name = "";
               String paraNamespaceURI = "";
               String paralocalPart = "";
               String paraPrefix = "";


               Type eType = e.getType();
               if (e.getType() != null && e.getQName() != null) {
                  if (eType instanceof SimpleType) {
                     logger.debug("**************************" + "SimpleType ************** ");
                     minOccure = e.getMinOccurs();
                     maxOccure = e.getMaxOccurs();
                     type = e.getType().getQName().getLocalPart();
                     name = e.getQName().getLocalPart();
                     paraNamespaceURI = e.getQName().getNamespaceURI();
                     paralocalPart = e.getQName().getLocalPart();
                     paraPrefix = e.getQName().getPrefix();
                  }
               }

               logger.debug(" ************************************************");

               logger.debug(" Element Name  :" + name);
               logger.debug(" Element Type  :" + type + "");
               logger.debug(" Element localPart  :" + paralocalPart);
               logger.debug(" Element Prefix  :" + paraPrefix);
               logger.debug(" Element Min Occure  :" + minOccure);
               logger.debug(" Element Max Occure  :" + maxOccure);
               logger.debug(" Element DefaultValue  :" + defaultValue);
               logger.debug(" Element paraNamespaceURI  :" + paraNamespaceURI);
               logger.debug(" Element Other Attributes  :" + e.getOtherAttributes());

               QName paraQName = new QName(paraNamespaceURI, paralocalPart, paraPrefix);
               SoapParameterBean param = new SoapParameterBean(name, type, minOccure, maxOccure, paraQName,
                       defaultValue);
               parameters.add(param);

            }
         }


         if (elementChoice instanceof Choice) {
            final List<Element> elementChoiceList = elementChoice.getElements();
            logger.debug("***********  " + "Element Choice ************** ");
            logger.debug("Amount of Element :" + elementChoiceList.size());
            Iterator<Element> iTElemnt = elementChoiceList.iterator();

            while (iTElemnt.hasNext()) {
               Element e = iTElemnt.next();
               int minOccure = SoapConstants.PARA_DEFAULT_MIN_OCCURE;
               String defaultValue = SoapConstants.PARA_DEFAULT_VALUE;
               String maxOccure = SoapConstants.PARA_DEFAULT_MAX_OCCURE;
               String type = "";
               String name = "";
               String paraNamespaceURI = "";
               String paralocalPart = "";
               String paraPrefix = "";


               Type eType = e.getType();
               if (e.getType() != null && e.getQName() != null) {
                  if (eType instanceof SimpleType) {
                     logger.debug("**************************" + "SimpleType ************** ");
                     minOccure = e.getMinOccurs();
                     maxOccure = e.getMaxOccurs();
                     type = e.getType().getQName().getLocalPart();
                     name = e.getQName().getLocalPart();
                     paraNamespaceURI = e.getQName().getNamespaceURI();
                     paralocalPart = e.getQName().getLocalPart();
                     paraPrefix = e.getQName().getPrefix();
                  }
               }

               logger.debug(" ************************************************");

               logger.debug(" Element Name  :" + name);
               logger.debug(" Element Type  :" + type + "");
               logger.debug(" Element localPart  :" + paralocalPart);
               logger.debug(" Element Prefix  :" + paraPrefix);
               logger.debug(" Element Min Occure  :" + minOccure);
               logger.debug(" Element Max Occure  :" + maxOccure);
               logger.debug(" Element DefaultValue  :" + defaultValue);
               logger.debug(" Element paraNamespaceURI  :" + paraNamespaceURI);
               logger.debug(" Element Other Attributes  :" + e.getOtherAttributes());

               QName paraQName = new QName(paraNamespaceURI, paralocalPart, paraPrefix);
               SoapParameterBean param = new SoapParameterBean(name, type, minOccure, maxOccure, paraQName,
                       defaultValue);
               parameters.add(param);

            }
         }



      } else if (paramType instanceof SimpleType) {
         final SimpleType simpleType = (SimpleType) paramType;
         final Restriction res = simpleType.getRestriction();

         logger.debug("***********: " + "Simple Type ************** ");
         logger.debug("SimpleType Element :" + simpleType);
         logger.debug("Restriction Element :" + res.getBase());
         logger.debug("Restriction Element :" + res.getEnumerations());

      }


      String operationPrefix = "";
      String operationNamesapceURI = "";

      if (inputQName.getLocalPart() != null && !inputQName.getLocalPart().equals("")) {
         operationPrefix = inputQName.getPrefix();
         operationNamesapceURI = inputQName.getNamespaceURI();
         operationName = inputQName.getLocalPart();
      } else {
         operationPrefix = operationQName.getPrefix();
         operationNamesapceURI = operationQName.getNamespaceURI();

         if (operationPrefix.equals("") || operationPrefix == null) {
            operationPrefix = inputQName.getPrefix();
         } else if (operationNamesapceURI.equals("") || operationNamesapceURI == null) {
            operationNamesapceURI = inputQName.getNamespaceURI();
         }
      }
      operationQName = new QName(operationNamesapceURI, operationName, operationPrefix);
      SoapOperationBean operation = new SoapOperationBean(operationName, operationQName, parameters);


      //Choose necessary schema are well form Soap speck
      Map<String, String> namespaces = new HashMap<String, String>();
      Map<String, String> tmp = desc.getNamespaces().getNamespaces();
      List<String> knowSchemas = SoapConstants.WELL_FORM_SCHEMAS;

      //	logger.debug("List of Know Schemas : " + knowSchemas);
      //	logger.debug("List of Know Schemas size : " + knowSchemas.size());
      if (tmp != null) {
         Iterator iteratorKnowSchemas = knowSchemas.iterator();

         for (String name : knowSchemas) {
            //	logger.debug(" Name : " + name);
            Iterator iteratorTemp = tmp.entrySet().iterator();
            while (iteratorTemp.hasNext()) {
               Map.Entry<String, String> me = (Map.Entry) iteratorTemp.next();
               String key = me.getKey();
               String value = me.getValue();
               //		logger.debug(" Key : " + key);
               //		logger.debug(" Value : " + value);
               if (name.equals(value)) {
                  namespaces.put(key, value);
               }
            }
         }
      }
      logger.debug("List of Schemas : " + namespaces);

      soapBindingBean.setHeader(false);
      soapBindingBean.setXmlStandalone(true);
      soapBindingBean.setSoapVersion(soapVersion);
      soapBindingBean.setSoapAction(soapAction);
      soapBindingBean.setNamespaces(namespaces);
      soapBindingBean.setOperation(operation);

      return soapBindingBean;
   }

   @Override
   public SoapBindingBean createSoapBindingBeanRPC(Description desc, QName bindingQName, QName interfaceQName, QName operationQName)
           throws XmlException {
      SoapBindingBean soapBindingBean = new SoapBindingBean();
      List<SoapParameterBean> parameters = new ArrayList<SoapParameterBean>();

      String operationName = operationQName.getLocalPart();
      String soapVersion = desc.getBinding(bindingQName).getVersion();
      String soapAction = desc.getBinding(bindingQName).getBindingOperation(operationName).getSoapAction();

      logger.debug("wsld url  : " + desc.getDocumentBaseURI());
      logger.debug("Interface QName  : " + interfaceQName);
      logger.debug("Operation QName  : " + operationQName);
      logger.debug("Operation Name  : " + operationName);
      logger.debug("Soap Vesion  : " + soapVersion);
      logger.debug("Soap Action  : " + soapAction);


      // get Element correspond that Operation and Interface
      Element inputElement = desc.getInterface(interfaceQName).getOperation(operationQName).getInput().getElement();
      Input input = desc.getInterface(interfaceQName).getOperation(operationQName).getInput();
      Iterator<Part> itoratorParts = input.getParts().iterator();

      while (itoratorParts.hasNext()) {
         Part part = itoratorParts.next();

         logger.debug("Element  : " + part.getElement());
         logger.debug("Part QName  : " + part.getPartQName());
         logger.debug("Type  : " + part.getType());
         logger.debug("Other Attributes : " + part.getOtherAttributes());
         logger.debug("Other Elements  : " + part.getOtherElements());


         logger.debug("Schema Size  : " + desc.getTypes().getSchemas().size() + " Scheamas");
         Element element = null;
         Type type = null;

         QName inputPartQName = part.getType().getQName();
         logger.debug("Input Part QName  : " + inputPartQName);
         logger.debug("Schema Size  : " + desc.getTypes().getSchemas().size() + " Scheamas");
         // find elements reponsible with Input of opration
         Iterator<Schema> itSchemas = desc.getTypes().getSchemas().iterator();
         while (itSchemas.hasNext()) {
            Schema schema = itSchemas.next();

            logger.debug("Schema Elements  : " + schema.getElements());
            logger.debug("Schema Types  : " + schema.getTypes());

            element = schema.getElement(inputPartQName);
            type = schema.getType(inputPartQName);
            Type paramType = null;

            if (element != null) {
               logger.debug("Input Element QName for Input : " + element.getQName());
               logger.debug("Input Element Min  for Input : " + element.getMinOccurs());
               logger.debug("Input Element Max  for Input : " + element.getMaxOccurs());
               logger.debug("Input Element Form  for Input : " + element.getForm());
               logger.debug("Input Element Ref  for Input : " + element.getRef());
               logger.debug("Input Element getOtherAttributes  for Input : " + element.getOtherAttributes());
               logger.debug("***************************************************");
               logger.debug("Element Type  : " + element.getType());
               logger.debug("***************************************************");

               paramType = element.getType();

            }
            if (type != null) {
               logger.debug("Type QName: " + type.getQName());
               paramType = type;
            }

            if (paramType instanceof ComplexType) {
               final ComplexType elementComplexType = (ComplexType) paramType;
               final All elementALL = elementComplexType.getAll();
               final Choice elementChoice = elementComplexType.getChoice();
               final Sequence elementSeq = elementComplexType.getSequence();

               logger.debug("***********: " + "ComplexType ************** ");

               logger.debug("Complex Type :" + elementComplexType);
               logger.debug("All Type :" + elementALL);
               logger.debug("Choice Type :" + elementChoice);
               logger.debug("Sequence :" + elementSeq);

               if (elementSeq instanceof Sequence) {
                  final List<Element> elementSeqList = elementSeq.getElements();
                  logger.debug("***********Element Sequence ************** ");
                  logger.debug("Amount of Element :" + elementSeqList.size());
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
                           logger.debug("**************************" + "SimpleType ************** ");
                           minOccure = e.getMinOccurs();
                           maxOccure = e.getMaxOccurs();
                           seqType = e.getType().getQName().getLocalPart();
                           name = e.getQName().getLocalPart();
                           paraNamespaceURI = e.getQName().getNamespaceURI();
                           paralocalPart = e.getQName().getLocalPart();
                           paraPrefix = e.getQName().getPrefix();
                        }
                     }

                     logger.debug(" ************************************************");

                     logger.debug(" Element Name  :" + name);
                     logger.debug(" Element Type  :" + seqType + "");
                     logger.debug(" Element localPart  :" + paralocalPart);
                     logger.debug(" Element Prefix  :" + paraPrefix);
                     logger.debug(" Element Min Occure  :" + minOccure);
                     logger.debug(" Element Max Occure  :" + maxOccure);
                     logger.debug(" Element DefaultValue  :" + defaultValue);
                     logger.debug(" Element paraNamespaceURI  :" + paraNamespaceURI);
                     logger.debug(" Element Other Attributes  :" + e.getOtherAttributes());


                     QName paraQName = new QName(paraNamespaceURI, paralocalPart, paraPrefix);
                     SoapParameterBean p1 = new SoapParameterBean(name, seqType, minOccure, maxOccure, paraQName,
                             defaultValue);
                     parameters.add(p1);

                  }
               }

               if (elementALL instanceof All) {
                  final List<Element> elementAllList = elementALL.getElements();
                  logger.debug("*********** " + "Element ALL ************** ");
                  logger.debug("Amount of Element :" + elementAllList.size());

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
                           logger.debug("**************************" + "SimpleType ************** ");
                           minOccure = e.getMinOccurs();
                           maxOccure = e.getMaxOccurs();
                           allType = e.getType().getQName().getLocalPart();
                           name = e.getQName().getLocalPart();
                           paraNamespaceURI = e.getQName().getNamespaceURI();
                           paralocalPart = e.getQName().getLocalPart();
                           paraPrefix = e.getQName().getPrefix();
                        }
                     }

                     logger.debug(" ************************************************");

                     logger.debug(" Element Name  :" + name);
                     logger.debug(" Element Type  :" + type + "");
                     logger.debug(" Element localPart  :" + paralocalPart);
                     logger.debug(" Element Prefix  :" + paraPrefix);
                     logger.debug(" Element Min Occure  :" + minOccure);
                     logger.debug(" Element Max Occure  :" + maxOccure);
                     logger.debug(" Element DefaultValue  :" + defaultValue);
                     logger.debug(" Element paraNamespaceURI  :" + paraNamespaceURI);
                     logger.debug(" Element Other Attributes  :" + e.getOtherAttributes());

                     QName paraQName = new QName(paraNamespaceURI, paralocalPart, paraPrefix);
                     SoapParameterBean param = new SoapParameterBean(name, allType, minOccure, maxOccure, paraQName,
                             defaultValue);
                     parameters.add(param);

                  }
               }


               if (elementChoice instanceof Choice) {
                  final List<Element> elementChoiceList = elementChoice.getElements();
                  logger.debug("***********  " + "Element Choice ************** ");
                  logger.debug("Amount of Element :" + elementChoiceList.size());
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
                           logger.debug("**************************" + "SimpleType ************** ");
                           minOccure = e.getMinOccurs();
                           maxOccure = e.getMaxOccurs();
                           choiceType = e.getType().getQName().getLocalPart();
                           name = e.getQName().getLocalPart();
                           paraNamespaceURI = e.getQName().getNamespaceURI();
                           paralocalPart = e.getQName().getLocalPart();
                           paraPrefix = e.getQName().getPrefix();
                        }
                     }

                     logger.debug(" ************************************************");

                     logger.debug(" Element Name  :" + name);
                     logger.debug(" Element Type  :" + type + "");
                     logger.debug(" Element localPart  :" + paralocalPart);
                     logger.debug(" Element Prefix  :" + paraPrefix);
                     logger.debug(" Element Min Occure  :" + minOccure);
                     logger.debug(" Element Max Occure  :" + maxOccure);
                     logger.debug(" Element DefaultValue  :" + defaultValue);
                     logger.debug(" Element paraNamespaceURI  :" + paraNamespaceURI);
                     logger.debug(" Element Other Attributes  :" + e.getOtherAttributes());

                     QName paraQName = new QName(paraNamespaceURI, paralocalPart, paraPrefix);
                     SoapParameterBean param = new SoapParameterBean(name, choiceType, minOccure, maxOccure, paraQName,
                             defaultValue);
                     parameters.add(param);

                  }
               }


            } else if (paramType instanceof SimpleType) {
               final SimpleType simpleType = (SimpleType) paramType;
               final Restriction res = simpleType.getRestriction();
               logger.debug("***********: " + "Simple Type ************** ");
               logger.debug("SimpleType Element :" + simpleType);
               logger.debug("Restriction Element :" + res.getBase());
               logger.debug("Restriction Element :" + res.getEnumerations());

            }



         }// end loop Messages
      } // end loop  parts





      String operationPrefix = operationQName.getPrefix();
      String operationNamesapceURI = operationQName.getNamespaceURI();

      /*
      if (operationPrefix.equals("") || operationPrefix == null) {
      operationPrefix = inputPartQName.getPrefix();
      } else if (operationNamesapceURI.equals("") || operationNamesapceURI == null) {
      operationNamesapceURI = inputPartQName.getNamespaceURI();
      }
       */

      operationQName = new QName(operationNamesapceURI, operationName, operationPrefix);
      SoapOperationBean operation = new SoapOperationBean(operationName, operationQName, parameters);

      //Choose necessary schema are well form Soap speck
      Map<String, String> namespaces = new HashMap<String, String>();
      Map<String, String> tmp = desc.getNamespaces().getNamespaces();
      List<String> knowSchemas = SoapConstants.WELL_FORM_SCHEMAS;

      //	logger.debug("List of Know Schemas : " + knowSchemas);
      //	logger.debug("List of Know Schemas size : " + knowSchemas.size());
      if (tmp != null) {
         Iterator iteratorKnowSchemas = knowSchemas.iterator();

         for (String name : knowSchemas) {
            //	logger.debug(" Name : " + name);
            Iterator iteratorTemp = tmp.entrySet().iterator();
            while (iteratorTemp.hasNext()) {
               Map.Entry<String, String> me = (Map.Entry) iteratorTemp.next();
               String key = me.getKey();
               String value = me.getValue();
               //		logger.debug(" Key : " + key);
               //		logger.debug(" Value : " + value);
               if (name.equals(value)) {
                  namespaces.put(key, value);
               }
            }
         }
      }
      logger.debug("List of Schemas : " + namespaces);

      soapBindingBean.setHeader(false);
      soapBindingBean.setXmlStandalone(true);
      soapBindingBean.setSoapVersion(soapVersion);
      soapBindingBean.setSoapAction(soapAction);
      soapBindingBean.setNamespaces(namespaces);
      soapBindingBean.setOperation(operation);

      return soapBindingBean;
   }
}
