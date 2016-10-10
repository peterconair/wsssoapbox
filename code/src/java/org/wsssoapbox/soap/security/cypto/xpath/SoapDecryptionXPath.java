/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.soap.security.cypto.xpath;

import org.apache.xml.security.encryption.EncryptedData;
import org.apache.xml.security.encryption.EncryptedKey;
import org.apache.xml.security.encryption.Reference;
import org.apache.xml.security.encryption.XMLCipher;
import org.apache.xml.security.encryption.XMLEncryptionException;
import org.apache.xml.security.keys.keyresolver.KeyResolverException;

import com.sun.xml.wss.XWSSecurityException;
import com.sun.xml.wss.core.X509SecurityToken;
import com.sun.xml.wss.core.reference.X509IssuerSerial;
import com.sun.xml.wss.impl.MessageConstants;
import com.sun.xml.wss.impl.SecurityTokenException;

import java.io.IOException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.xml.namespace.NamespaceContext;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.apache.xml.security.utils.Base64;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.wsssoapbox.bean.model.soap.SoapPartBean;
import org.wsssoapbox.soap.security.CertUtil;
import org.wsssoapbox.xml.util.SimpleNamespaceContext;

/**
 *
 * @author Peter
 */
public class SoapDecryptionXPath implements SoapCryptoDAO {

   private XPath xpath;

   public SoapDecryptionXPath() {

      xpath = XPathFactory.newInstance().newXPath();
      NamespaceContext ns = new SimpleNamespaceContext();
      xpath.setNamespaceContext(ns);

   }

   @Override
   public EncryptedKey findEncryptedKeyByEncryptedData(SOAPMessage soapMessage, String encDataId) throws Exception {
      System.out.println("**********************************************************");
      System.out.println("*********  Find EncryptedKey  By EncrypteData ************");
      System.out.println("Finding ... By EncryptedData Id : " + encDataId);
      System.out.println("**********************************************************");

      EncryptedKey ek = null;
      List<EncryptedKey> eks = findEncryptedKeys(soapMessage);

      System.out.println("Size : " + eks.size());
      for (EncryptedKey k : eks) {
         Iterator it = k.getReferenceList().getReferences();
         while (it.hasNext()) {
            Reference ref = (Reference) it.next();
            String id = ref.getURI();
            id = id.replaceFirst("#", "");
            System.out.println("Ref URI : " + id);
            System.out.println("encDataId :" + encDataId);
            if (id.equals(encDataId)) {
               ek = k;
            }
         }
      }
      return ek;
   }

   @Override
   public EncryptedData findEncryptedDataByEncryptedKey(SOAPMessage soapMessage, String encKeyId) throws Exception {
      System.out.println("**********************************************************");
      System.out.println("**********  Find EncrypteData By EncryptedKey ************");
      System.out.println("Finding ... By  EncryptedKey Id : " + encKeyId);
      System.out.println("**********************************************************");
      EncryptedData ed = null;
      EncryptedKey ek = findEncryptedKey(soapMessage, encKeyId);
      if (ek != null) {
         System.out.println("Id :" + ek.getId());
         System.out.println("Cert : " + ek.getKeyInfo().getX509Certificate());
         Iterator it = ek.getReferenceList().getReferences();
         while (it.hasNext()) {
            Reference ref = (Reference) it.next();
            String id = ref.getURI();
            System.out.println("Ref URI : " + id);
            ed = findEncryptedData(soapMessage, id);
         }
      }
      return ed;
   }

   @Override
   public List<EncryptedData> findAllEncrypteDataByDataReferences(SOAPMessage soapMessage) throws XPathExpressionException, SOAPException, XMLEncryptionException, KeyResolverException {
      List<EncryptedData> eds = new ArrayList<EncryptedData>();
      EncryptedData ed = null;
      System.out.println("**********************************************************");
      System.out.println("******  Find All EncrypteData By DataReferences URI ******");
      System.out.println("**********************************************************");

      XPathExpression expr = null;
      //find all DataReference in envelope 
      String exprStr1 = "/soapenv:Envelope/soapenv:Header/wsse:Security/xenc:EncryptedKey/xenc:ReferenceList/xenc:DataReference/@URI";
      System.out.println("Using expresion : " + exprStr1);
      expr = xpath.compile(exprStr1);
      Object dataRef = expr.evaluate(soapMessage.getSOAPHeader().getOwnerDocument(), XPathConstants.NODESET);
      NodeList dataNodes = (NodeList) dataRef;
      //    System.out.println("Length : " + nodes.getLength());
      String dataRefURI = "";
      System.out.println("We founds : " + dataNodes.getLength() + " DataReference... ");
      // Operate all DataReference values
      for (int i = 0; i < dataNodes.getLength(); i++) {
         dataRefURI = dataNodes.item(i).getTextContent();
         // remove '#' charactor
         dataRefURI = dataRefURI.substring(1);
         ed = findEncryptedData(soapMessage, dataRefURI);
         eds.add(ed);
      }
      return eds;
   }

   @Override
   public EncryptedKey findEncryptedKey(SOAPMessage soapMessage, String encKeyId) throws SOAPException, XPathExpressionException, XMLEncryptionException, KeyResolverException {
      EncryptedKey ek = null;

      System.out.println("******************************************");
      System.out.println("*******  Find the encrypted key  *********");
      System.out.println("******************************************");

      XPathExpression expr = null;
      XMLCipher xmlCipher = null;
      Element decryptedKeyElem = null;

      if (soapMessage != null) {
         // find all EncryptedKey nodes
         String exprStr = "//soapenv:Envelope/soapenv:Header/wsse:Security/xenc:EncryptedKey[@Id='" + encKeyId + "']";
         System.out.println("Using expresion : " + exprStr);
         expr = xpath.compile(exprStr);
         Object encResult = expr.evaluate(soapMessage.getSOAPHeader().getOwnerDocument(), XPathConstants.NODESET);
         NodeList encNodes = (NodeList) encResult;

         System.out.println("Found " + encNodes.getLength() + " <xenc:EncryptedKey> elements.");
         for (int i = 0; i < encNodes.getLength(); i++) {
            System.out.println("Node Name : " + encNodes.item(i).getNodeName());
            // Assign xenc:EncryptedKey that found to element. 
            decryptedKeyElem = (Element) encNodes.item(i);
            //To unmarshal an  xenc:EncryptedKey  Element to an EncryptedKey 
            xmlCipher = XMLCipher.getInstance();
            xmlCipher.init(XMLCipher.DECRYPT_MODE, null);
            ek = xmlCipher.loadEncryptedKey(decryptedKeyElem);
            ek.getKeyInfo().getPublicKey();
         }
      }
      return ek;
   }

   @Override
   public List<EncryptedKey> findEncryptedKeys(SOAPMessage soapMessage) throws SOAPException, XPathExpressionException, XMLEncryptionException, KeyResolverException {
      List<EncryptedKey> eks = new ArrayList<EncryptedKey>();
      EncryptedKey ek = null;

      System.out.println("******************************************");
      System.out.println("*******  Find the encrypted key  *********");
      System.out.println("******************************************");

      XPathExpression expr = null;
      XMLCipher xmlCipher = null;
      Element decryptedKeyElem = null;

      if (soapMessage != null) {
         // find all EncryptedKey nodes
         String exprStr = "//soapenv:Envelope/soapenv:Header/wsse:Security/*";
         System.out.println("Using expresion : " + exprStr);
         expr = xpath.compile(exprStr);

         Object encResult = expr.evaluate(soapMessage.getSOAPHeader().getOwnerDocument(), XPathConstants.NODESET);
         NodeList encNodes = (NodeList) encResult;

         System.out.println("Found " + encNodes.getLength() + " <xenc:EncryptedKey> elements.");

         for (int i = 0; i < encNodes.getLength(); i++) {
            System.out.println("Node Name : " + encNodes.item(i).getNodeName());

            if (encNodes.item(i).getNodeName().equals("xenc:EncryptedKey")) {
               // Assign xenc:EncryptedKey that found to element. 
               decryptedKeyElem = (Element) encNodes.item(i);
               //To unmarshal an  xenc:EncryptedKey  Element to an EncryptedKey 
               xmlCipher = XMLCipher.getInstance();
               xmlCipher.init(XMLCipher.DECRYPT_MODE, null);
               ek = xmlCipher.loadEncryptedKey(decryptedKeyElem);
               ek.getKeyInfo().getPublicKey();
               eks.add(ek);
            }
         }
      }
      return eks;
   }

   @Override
   public EncryptedData findEncryptedData(SOAPMessage soapMessage, String encDataId) throws SOAPException, XPathExpressionException, XMLEncryptionException {
      EncryptedData ed = null;

      System.out.println("*************************************************");
      System.out.println("************ Find encrypted data ****************");
      System.out.println("*************************************************");

      XPathExpression expr = null;
      XMLCipher xmlCipher = null;
      Element encryptedDataElem = null;
      //find all DataReference in envelope 
      if (encDataId.startsWith("#")) {
         encDataId = encDataId.substring(1);
      }
      String exprStr = "//xenc:EncryptedData[@Id='" + encDataId + "']";
      System.out.println("Using expresion : " + exprStr);
      expr = xpath.compile(exprStr);
      Object dataRef = expr.evaluate(soapMessage.getSOAPPart().getEnvelope().getOwnerDocument(), XPathConstants.NODESET);

      NodeList dataNodes = (NodeList) dataRef;
      //    System.out.println("Length : " + nodes.getLength());

      System.out.println("We founds : " + dataNodes.getLength() + " DataReference... ");
      // Operate all DataReference values
      for (int i = 0; i < dataNodes.getLength(); i++) {
         // Assign xenc:EncryptedKey that found to element. 
         encryptedDataElem = (Element) dataNodes.item(i);
         //To unmarshal an  xenc:EncryptedKey  Element to an EncryptedKey 
          xmlCipher = XMLCipher.getInstance();
       //  xmlCipher = XMLCipher.getInstance(XMLCipher.AES_128, Canonicalizer.ALGO_ID_C14N_WITH_COMMENTS);
         xmlCipher.init(XMLCipher.DECRYPT_MODE, null);
         ed = xmlCipher.loadEncryptedData(soapMessage.getSOAPPart().getEnvelope().getOwnerDocument(), encryptedDataElem);
      }
      return ed;
   }

   @Override
   public List<EncryptedData> findEncryptedDatas(SOAPMessage soapMessage) throws SOAPException, XPathExpressionException, XMLEncryptionException {
      EncryptedData ed = null;
      List<EncryptedData> eds = new ArrayList<EncryptedData>();

      System.out.println("*************************************************");
      System.out.println("************ Find encrypted data ****************");
      System.out.println("*************************************************");

      XPathExpression expr = null;
      XMLCipher xmlCipher = null;
      Element encryptedDataElem = null;
      //find all DataReference in envelope 
      String exprStr = "//xenc:EncryptedData";
      System.out.println("Using expresion : " + exprStr);
      expr = xpath.compile(exprStr);
      Object dataRef = expr.evaluate(soapMessage.getSOAPPart().getEnvelope().getOwnerDocument(), XPathConstants.NODESET);
      NodeList dataNodes = (NodeList) dataRef;
      //    System.out.println("Length : " + nodes.getLength());
      System.out.println("We founds : " + dataNodes.getLength() + " DataReference... ");
      // Operate all DataReference values
      for (int i = 0; i < dataNodes.getLength(); i++) {
         // Assign xenc:EncryptedKey that found to element. 
         encryptedDataElem = (Element) dataNodes.item(i);
         //To unmarshal an  xenc:EncryptedKey  Element to an EncryptedKey 
         xmlCipher = XMLCipher.getInstance();
         xmlCipher.init(XMLCipher.DECRYPT_MODE, null);
         ed = xmlCipher.loadEncryptedData(soapMessage.getSOAPPart().getEnvelope().getOwnerDocument(), encryptedDataElem);
         eds.add(ed);
      }
      return eds;
   }

   @Override
   public boolean verifyCertificate(SOAPMessage soapMessage, X509Certificate certificate) throws XPathExpressionException, SOAPException, SecurityTokenException, IOException, CertificateEncodingException, XWSSecurityException {
      System.out.println("*************************************************");
      System.out.println("************ Verify Certificate ****************");
      System.out.println("*************************************************");

      XPathExpression expr = null;

      // find all Key Identifier Type 
      String exprStr1 = "/soapenv:Envelope/soapenv:Header/wsse:Security/xenc:EncryptedKey/ds:KeyInfo/wsse:SecurityTokenReference/*";
      System.out.println("Using expresion : " + exprStr1);
      expr = xpath.compile(exprStr1);
      Object result = expr.evaluate(soapMessage.getSOAPHeader().getOwnerDocument(), XPathConstants.NODESET);
      NodeList nodes = (NodeList) result;
      String nodeName = "";
      System.out.println("Length : " + nodes.getLength());

      for (int i = 0; i < nodes.getLength(); i++) {

         System.out.println("Node Name : " + nodes.item(i).getNodeName());
         nodeName = nodes.item(i).getNodeName();
         System.out.println("");

         // Subjectkey Identifier and // X509 Certificate 
         if (nodeName.equals("wsse:KeyIdentifier")) {
            System.out.println("KeyIdentifier...............");
            // expr = xpath.compile("/*/*/*/*/*/*/node()");
            String exprStr2 = "/soapenv:Envelope/soapenv:Header/wsse:Security/xenc:EncryptedKey/ds:KeyInfo/wsse:SecurityTokenReference/node()";
            System.out.println("Using expresion : " + exprStr2);
            expr = xpath.compile(exprStr2);
            String subKeyIndetifier = "";
            Object resultRef = expr.evaluate(soapMessage.getSOAPHeader().getOwnerDocument(), XPathConstants.NODESET);
            NodeList refNodes = (NodeList) resultRef;
            //   System.out.println("Length : " + refNodes.getLength());
            for (int j = 0; j < refNodes.getLength(); j++) {
               if (refNodes.item(j).getNodeName().equals("wsse:KeyIdentifier")) {
                  subKeyIndetifier = refNodes.item(j).getTextContent();
                  break;
               }
            }

            String idTypeValue = "";
            String toolSubKeyId = "";

            expr = xpath.compile("/soapenv:Envelope/soapenv:Header/wsse:Security/xenc:EncryptedKey/ds:KeyInfo/wsse:SecurityTokenReference/wsse:KeyIdentifier/@ValueType");
            Object idType = expr.evaluate(soapMessage.getSOAPHeader().getOwnerDocument(), XPathConstants.NODESET);
            NodeList idTypeNodes = (NodeList) idType;
            for (int j = 0; j < idTypeNodes.getLength(); j++) {
               idTypeValue = idTypeNodes.item(j).getTextContent();
            }

            System.out.println("Value Type : " + idTypeValue);

            // Subjectkey Identifier
            if (idTypeValue.equals(MessageConstants.X509SubjectKeyIdentifier_NS)) {
               System.out.println("Subjectkey Identifier............");
               toolSubKeyId = CertUtil.getSubjectKeyIdentity(certificate);
            } else { // X509 Certificate 
               System.out.println("X509 Certificate............");
               toolSubKeyId = Base64.encode(certificate.getEncoded());
            }

            boolean certMatch = Arrays.equals(toolSubKeyId.getBytes(), subKeyIndetifier.getBytes());
            System.out.println("*******************************");
            System.out.println("Subject Key Identifier : " + subKeyIndetifier);
            System.out.println("Tool Subject Key Indentifier : " + toolSubKeyId);
            System.out.println("Comparation result : " + certMatch);
            System.out.println("*******************************");

            return certMatch;
         }


         // Issuer Name and Serial Number
         if (nodeName.equals("ds:X509Data")) {
            System.out.println("Issuer Name and Serial Number.................");

            expr = xpath.compile("/*/*/*/*/*/*/*/*/node()");

            String issuerName = "";
            String serialNumber = "";
            Object resultRef = expr.evaluate(soapMessage.getSOAPHeader().getOwnerDocument(), XPathConstants.NODESET);
            NodeList refNodes = (NodeList) resultRef;
            //  System.out.println("Length : " + refNodes.getLength());
            for (int j = 0; j < refNodes.getLength(); j++) {
               if (refNodes.item(j).getNodeName().equals("ds:X509IssuerName")) {
                  System.out.println("X509IssuerName.............");
                  issuerName = refNodes.item(j).getTextContent();
               }
               if (refNodes.item(j).getNodeName().equals("ds:X509SerialNumber")) {
                  System.out.println("X509SerialNumber.............");
                  serialNumber = refNodes.item(j).getTextContent();
                  break;
               }
            }

            X509IssuerSerial x509IssuerSerial = new X509IssuerSerial(soapMessage.getSOAPHeader().getOwnerDocument(), certificate);

            String toolIssuerName = x509IssuerSerial.getIssuerName();
            String toolSerialNumber = x509IssuerSerial.getSerialNumber().toString();

            boolean certIssuerMatch = Arrays.equals(toolIssuerName.getBytes(), issuerName.getBytes());
            boolean certSerialMatch = Arrays.equals(toolSerialNumber.getBytes(), serialNumber.getBytes());
            boolean certMatch = certIssuerMatch && certSerialMatch ? true : false;


            System.out.println("*******************************");
            System.out.println("Issuer Name : " + issuerName);
            System.out.println("Serial Number : " + serialNumber);

            System.out.println("Tool Issuer Name : " + toolIssuerName);
            System.out.println("Tool Serial Number : " + toolSerialNumber);
            System.out.println("Comparation result : " + certMatch);
            System.out.println("*******************************");
            return certMatch;

         }
         if (nodeName.equals("wsse:Reference")) {
            System.out.println("Binary Security Token.................");
            String certId = "";
            expr = xpath.compile("/soapenv:Envelope/soapenv:Header/wsse:Security/xenc:EncryptedKey/ds:KeyInfo/wsse:SecurityTokenReference/wsse:Reference/@URI");
            System.out.println("Base URI : " + nodes.item(i).getLocalName());

            String binValueText = "";
            Object resultRef = expr.evaluate(soapMessage.getSOAPHeader().getOwnerDocument(), XPathConstants.NODESET);
            NodeList refNodes = (NodeList) resultRef;
            // System.out.println("Length : " + refNodes.getLength());
            for (int j = 0; j < refNodes.getLength(); j++) {

               certId = refNodes.item(j).getTextContent();

               if (!certId.equals("")) {
                  System.out.println("Getting Binary Token.............");
                  expr = xpath.compile("/soapenv:Envelope/soapenv:Header/wsse:Security/wsse:BinarySecurityToken/@wsu:Id");
                  Object binRef = expr.evaluate(soapMessage.getSOAPHeader().getOwnerDocument(), XPathConstants.NODESET);
                  NodeList binNodes = (NodeList) binRef;
                  // System.out.println("Length : " + binNodes.getLength());
                  for (int k = 0; k < binNodes.getLength(); k++) {
                     System.out.println("Binary Token :" + binNodes.item(k));

                     expr = xpath.compile("/soapenv:Envelope/soapenv:Header/wsse:Security/wsse:BinarySecurityToken/text()");
                     Object binValueRef = expr.evaluate(soapMessage.getSOAPHeader().getOwnerDocument(), XPathConstants.NODESET);
                     NodeList binValueNodes = (NodeList) binValueRef;
                     for (int l = 0; l < binValueNodes.getLength(); l++) {
                        binValueText = binValueNodes.item(l).getTextContent();
                     }
                  }
               }
            }


            X509SecurityToken x509SecurityToken = new X509SecurityToken(soapMessage.getSOAPHeader().getOwnerDocument(), certificate);
            String toolBinToken = Base64.encode(x509SecurityToken.getCertificate().getEncoded());
            boolean certMatch = Arrays.equals(toolBinToken.getBytes(), binValueText.getBytes());

            System.out.println("*******************************");
            System.out.println("Reference URI : " + certId);
            System.out.println("Binary Token : " + binValueText);
            System.out.println("Tool Binary Token : " + toolBinToken);
            System.out.println("Comparation result : " + certMatch);
            System.out.println("*******************************");

            return certMatch;
         }
      }


      return false;
   }

   @Override
   public List<Element> findAllElementInBody(SOAPMessage soapMessage) throws SOAPException, XPathExpressionException, XMLEncryptionException {
      System.out.println("*************************************************");
      System.out.println("************ Find All Element in Soap Envelope ****************");
      System.out.println("*************************************************");

      Element element = null;
      List<Element> elements = new ArrayList<Element>();

      XPathExpression expr = null;

      //find all DataReference in envelope 
      String exprStr = "//*/*/node()";
      System.out.println("Using expresion : " + exprStr);
      expr = xpath.compile(exprStr);
      Object dataRef = expr.evaluate(soapMessage.getSOAPPart().getEnvelope().getOwnerDocument(), XPathConstants.NODESET);
      NodeList dataNodes = (NodeList) dataRef;
      //    System.out.println("Length : " + nodes.getLength());
      System.out.println("We founds : " + dataNodes.getLength() + " Elements... ");
      // Operate all DataReference values
      for (int i = 0; i < dataNodes.getLength(); i++) {
         try {// get only elemnt
            element = (Element) dataNodes.item(i);
            elements.add(element);
         } catch (ClassCastException ex) {
         }

      }
      return elements;
   }

   @Override
   public List<SoapPartBean> findAllSoapPartsInBody(SOAPMessage soapMessage) throws SOAPException, XPathExpressionException, XMLEncryptionException {
      System.out.println("*************************************************");
      System.out.println("************ Find All Element in Soap Envelope ****************");
      System.out.println("*************************************************");

      Element e = null;
      SoapPartBean p = new SoapPartBean();
      List<SoapPartBean> parts = new ArrayList<SoapPartBean>();

      XPathExpression expr = null;

      //find all DataReference in envelope 
      String exprStr = "//*/*/node()";
      System.out.println("Using expresion : " + exprStr);
      expr = xpath.compile(exprStr);
      Object dataRef = expr.evaluate(soapMessage.getSOAPPart().getEnvelope().getOwnerDocument(), XPathConstants.NODESET);
      NodeList dataNodes = (NodeList) dataRef;
      //    System.out.println("Length : " + nodes.getLength());
      System.out.println("We founds : " + dataNodes.getLength() + " Elements... ");
      // Operate all DataReference values
      for (int i = 0; i < dataNodes.getLength(); i++) {
         try {// get only elemnt
            e = (Element) dataNodes.item(i);
            p = new SoapPartBean();
            p.setNodeName(e.getNodeName());
            p.setBaseURI(e.getBaseURI());
            p.setNamespaceURI(e.getNamespaceURI());
            p.setTextContent(e.getTextContent());
            p.setPrefix(e.getPrefix());
            p.setLocalName(e.getLocalName());
            p.setTagName(e.getTagName());
            p.setValue(e.getNodeValue());
            p.setElement(e);
            System.out.println("Name : " +e.getTagName());
            parts.add(p);
         } catch (ClassCastException ex) {
         }

      }
      return parts;
   }
}
