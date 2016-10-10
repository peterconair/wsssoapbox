/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.soap.security.crypto;

import org.apache.xml.security.encryption.EncryptedData;
import org.apache.xml.security.encryption.ReferenceList;
import org.apache.xml.security.encryption.XMLCipher;
import org.apache.xml.security.encryption.XMLEncryptionException;
import org.apache.xml.security.encryption.Reference;


import com.sun.xml.wss.XWSSecurityException;
import com.sun.xml.wss.core.KeyInfoHeaderBlock;
import com.sun.xml.wss.core.ReferenceElement;
import com.sun.xml.wss.core.SecurityHeader;
import com.sun.xml.wss.core.SecurityTokenReference;
import com.sun.xml.wss.core.reference.DirectReference;
import com.sun.xml.wss.impl.SecurableSoapMessage;
import com.sun.xml.wss.impl.XMLUtil;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.UUID;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import org.apache.xml.security.encryption.EncryptedKey;
import org.apache.xml.security.keys.KeyInfo;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.wsssoapbox.bean.model.soap.SoapPartBean;
import org.wsssoapbox.soap.support.SoapConstants;
import org.wsssoapbox.soap.support.SoapCreator;
import org.wsssoapbox.soap.security.support.BinarySecurityTokenType;
import org.wsssoapbox.soap.security.support.IssuerNameType;
import org.wsssoapbox.soap.security.support.KeyIndentifierType;
import org.wsssoapbox.soap.security.support.SubjectkeyIdentifierType;
import org.wsssoapbox.soap.security.support.X509CertificateType;
import org.wsssoapbox.xml.util.XMLUtils;

/**
 *
 * @author Peter
 */
public class SoapEncryption implements SoapCrypto {

   private SOAPMessage soapMessage;
   private String encDataId;

   public SoapEncryption(String encDataId) {
      this.encDataId = encDataId;
   }

   @Override
   public String encryptSoap(String strMessage, String keyEncAlg, String keyIdType, String symmetricEncAlg, X509Certificate certificate) throws SOAPException, UnsupportedEncodingException, XWSSecurityException, NoSuchAlgorithmException, XMLEncryptionException, Exception {
      soapMessage = MessageFactory.newInstance().createMessage();
      soapMessage = SoapCreator.createSOAPMessageFromString(strMessage);
      return encryptSoap(soapMessage, keyEncAlg, keyIdType, symmetricEncAlg, certificate);
   }

   @Override
   public String encryptSoap(SOAPMessage soapMessage, String keyEncAlg, String keyIdType, String symmetricEncAlg, X509Certificate certificate) throws SOAPException, UnsupportedEncodingException, XWSSecurityException, NoSuchAlgorithmException, XMLEncryptionException, Exception {

      SOAPEnvelope envelope = soapMessage.getSOAPPart().getEnvelope();
      SecurableSoapMessage secureMessage = new SecurableSoapMessage(soapMessage);
      SOAPHeader soapHeader = soapMessage.getSOAPHeader();
      SOAPBody soapBody = soapMessage.getSOAPBody();
      SecurityHeader secureHeader = secureMessage.findWsseSecurityHeaderBlock(true, true);

      if (encDataId.equals("") || encDataId == null) {
         encDataId = "EncDataId-" + UUID.randomUUID().toString();
      }

      String encKeyId = "#EncKeyId-" + UUID.randomUUID().toString();
      //String encKeyId = "#EncKeyId-1";
      KeyGenerator keyGenerator = null;
      XMLCipher cipherData = null;
      // Select Symmetric encrypt algorithms for encrypt the data.
      if (symmetricEncAlg.equals(XMLCipher.TRIPLEDES)) {
         keyGenerator = KeyGenerator.getInstance("DESede");
         cipherData = XMLCipher.getInstance(XMLCipher.TRIPLEDES);
      } else if (symmetricEncAlg.equals(XMLCipher.AES_192)) {
         keyGenerator = KeyGenerator.getInstance("AES");
         keyGenerator.init(192);
         cipherData = XMLCipher.getInstance(XMLCipher.AES_192);
      } else if (symmetricEncAlg.equals(XMLCipher.AES_256)) {
         keyGenerator = KeyGenerator.getInstance("AES");
         keyGenerator.init(256);
         cipherData = XMLCipher.getInstance(XMLCipher.AES_256);
      } else {
         keyGenerator = KeyGenerator.getInstance("AES");
         keyGenerator.init(128);
         cipherData = XMLCipher.getInstance(XMLCipher.AES_128);
      }

      // create session key for encrypt the data
      SecretKey sessionKey = keyGenerator.generateKey();
      cipherData.init(XMLCipher.ENCRYPT_MODE, sessionKey);
      // Do the encrypt soapBody Content (true) , Element (false)
      EncryptedData encryptedData = cipherData.encryptData(envelope.getOwnerDocument(), soapBody, true);
      encryptedData.setId(encDataId);
      Element encryptedDataElem = cipherData.martial(encryptedData);

      /*
      KeyInfoHeaderBlock dataKeyInfo = new KeyInfoHeaderBlock(envelope.getOwnerDocument());
      
      KeyInfo keyInof = new KeyInfo(envelope.getOwnerDocument());
      
      SecurityTokenReference dataTokenReference = new SecurityTokenReference(envelope.getOwnerDocument());
      ReferenceElement referenceElement = new DirectReference();
      referenceElement.setAttribute("URI", encKeyId);
      dataTokenReference.setReference(referenceElement);
      
      dataKeyInfo.addSecurityTokenReference(dataTokenReference);
      // Insert KeyInfo to EncryptedData
      encryptedDataElem.insertBefore(dataKeyInfo.getAsSoapElement(), encryptedDataElem.getFirstChild());
       */

      SecurityTokenReference dataSecTokenReference = new SecurityTokenReference(envelope.getOwnerDocument());
      ReferenceElement referenceElement = new DirectReference();
      referenceElement.setAttribute("URI", encKeyId);
      dataSecTokenReference.setReference(referenceElement);
      XMLUtils.printElement(dataSecTokenReference.getAsSoapElement());
      Element dataKeyInfo = XMLUtil.createElement(envelope.getOwnerDocument(), "KeyInfo", "http://www.w3.org/2000/09/xmldsig#", "ds");
      dataKeyInfo.appendChild(dataSecTokenReference.getAsSoapElement());
      XMLUtils.printElement(dataKeyInfo);
      encryptedDataElem.insertBefore(dataKeyInfo, encryptedDataElem.getFirstChild());

      // Remove SoapBody Content. 
      soapBody.removeContents();
      //soapBody.removeChild(soapBody.getFirstChild());
      // insert  Encrypted Data to body element
      soapBody.appendChild(encryptedDataElem);


      // Create an XMLCipher 
      //The encryption is performed  The data encryption algorithm is specified when getting an XMLCipher instance. 
      //The  XMLCipher instance must be initialised with the key to encrypt the data, which should be the session key generated
      XMLCipher keyCipher = null;
      // Select algorithm for encrypt session key 
      if (keyEncAlg.equals(XMLCipher.RSA_v1dot5)) {
         keyCipher = XMLCipher.getInstance(XMLCipher.RSA_v1dot5);
      } else {
         keyCipher = XMLCipher.getInstance(XMLCipher.RSA_OAEP);
      }

      // creates an XMLCipher, initialised for key wrapping using the public key of the certificate, and encrypts the session key
      keyCipher.init(XMLCipher.WRAP_MODE, certificate.getPublicKey());
      EncryptedKey encryptedKey = keyCipher.encryptKey(envelope.getOwnerDocument(), sessionKey);
      encryptedKey.setId(encKeyId);

      //Create ReferenceList  and add to EncryptedKey Each  EncryptedKey must contain a ReferenceList  and have a single 
      //DataReference  with the URI attribute set to the  Id attribute of the encrypted data 
      ReferenceList referenceList = keyCipher.createReferenceList(ReferenceList.DATA_REFERENCE);
      encryptedKey.setReferenceList(referenceList);
      // Reference to target EncryptedData using Id. 
      Reference dataReference = referenceList.newDataReference("#" + encDataId);
      referenceList.add(dataReference);
      encryptedKey.setReferenceList(referenceList);

      /*
      // Create the keyinfo referring to the certificate (<ds:KeyInfo>)
      KeyInfoHeaderBlock keyInfoHeaderBlock = new KeyInfoHeaderBlock(envelope.getOwnerDocument()); 
      Element keElement = keyInfoHeaderBlock.getKeyInfo().getElement();
      KeyInfo ki = new KeyInfo(keElement.getOwnerDocument());
      // Set SUN KeyInfoHeaderBlock to Apache KeyInfo
      encryptedKey.setKeyInfo(ki);
      // Mashall 
      Element encryptedKeyElem = keyCipher.martial(encryptedKey);
       */



      // Create block (<wsse:SecurityTokenReference>) 
      SecurityTokenReference securityTokenReference = new SecurityTokenReference(envelope.getOwnerDocument());

      // ReferenceListHeaderBlock refListHeaderBlock = new ReferenceListHeaderBlock(envelope.getOwnerDocument());

      if (SoapConstants.BINARY_SEC_TOKEN.equals(keyIdType)) {
         BinarySecurityTokenType kit = new BinarySecurityTokenType();
         securityTokenReference = kit.getKeyIndentifierType(securityTokenReference, soapHeader.getOwnerDocument(), certificate);
         // insert <wsse:BinarySecurityTokent> into <wsse:Security> only Key Identifier Type are Binary Security Token
         secureHeader.insertHeaderBlock(kit.getX509SecurityToken());
      }
      if (SoapConstants.ISSUER_NAME.equals(keyIdType)) {
         KeyIndentifierType kit = new IssuerNameType();
         securityTokenReference = kit.getKeyIndentifierType(securityTokenReference, envelope.getOwnerDocument(), certificate);
      }
      if (SoapConstants.SUBJECT_KEY_INDENTIFIER.equals(keyIdType)) {
         KeyIndentifierType kit = new SubjectkeyIdentifierType();
         securityTokenReference = kit.getKeyIndentifierType(securityTokenReference, envelope.getOwnerDocument(), certificate);
      }
      if (SoapConstants.X509_CERTIFICATE.equals(keyIdType)) {
         KeyIndentifierType kit = new X509CertificateType();
         securityTokenReference = kit.getKeyIndentifierType(securityTokenReference, envelope.getOwnerDocument(), certificate);
      }

      // Insert  <wsse:Reference> or <> or <> or <>  <wsse:SecurityTokenReference> Depend on Key Indetifier Type 
      // keyInfoHeaderBlock.addSecurityTokenReference(securityTokenReference);
      //  XMLUtils.printElement(securityTokenReference);

      // Create the keyinfo referring to the certificate (<ds:KeyInfo>)
      Element keElement = XMLUtil.createElement(envelope.getOwnerDocument(), "KeyInfo", "http://www.w3.org/2000/09/xmldsig#", "ds");
      KeyInfo ki = new KeyInfo(keElement.getOwnerDocument());

      SOAPElement secRef = securityTokenReference.getAsSoapElement();
      // add <wsse:SecurityTokenReference> to (<ds:KeyInfo>)
      ki.addUnknownElement(secRef);

      //  kiElement.appendChild(securityTokenReference);

      // Set SUN KeyInfoHeaderBlock to Apache KeyInfo
      encryptedKey.setKeyInfo(ki);
      // Mashall 
      Element encryptedKeyElem = keyCipher.martial(encryptedKey);

      encryptedDataElem.insertBefore(dataKeyInfo, encryptedDataElem.getFirstChild());


      // Insert <xenc:ReferenceList>  to <xenc:EncryptedKey> 
      // encryptedKeyHeaderBlock.setReferenceList(refListHeaderBlock);
      // Insert EncryptedKey into Header (<wsse:Security>)
      secureHeader.appendChild(encryptedKeyElem);

      // System.out.println("Soap Message : " + XMLUtils.prettyPrint(soapMessage));
      String encMessage = XMLUtils.prettyPrint(soapMessage);
      //   System.out.println("Response Message : " + encMessage);

      return encMessage;
   }

   @Override
   public String encryptSoapParts(String strMessage, List<SoapPartBean> soapParts, String keyEncAlg, String keyIdType, String symmetricEncAlg, X509Certificate certificate) throws SOAPException, UnsupportedEncodingException, XWSSecurityException, NoSuchAlgorithmException, XMLEncryptionException, Exception {
      soapMessage = MessageFactory.newInstance().createMessage();
      soapMessage = SoapCreator.createSOAPMessageFromString(strMessage);
      return encryptSoapParts(soapMessage, soapParts, keyEncAlg, keyIdType, symmetricEncAlg, certificate);
   }

   @Override
   public String encryptSoapParts(SOAPMessage soapMessage, List<SoapPartBean> soapParts, String keyEncAlg, String keyIdType, String symmetricEncAlg, X509Certificate certificate) throws SOAPException, UnsupportedEncodingException, XWSSecurityException, NoSuchAlgorithmException, XMLEncryptionException, Exception {

      SOAPEnvelope envelope = soapMessage.getSOAPPart().getEnvelope();
      SecurableSoapMessage secureMessage = new SecurableSoapMessage(soapMessage);
      SOAPHeader soapHeader = soapMessage.getSOAPHeader();
      SOAPBody soapBody = soapMessage.getSOAPBody();
      SecurityHeader secureHeader = secureMessage.findWsseSecurityHeaderBlock(true, true);


      String encKeyId = "#EncKeyId-" + UUID.randomUUID().toString();
      SecretKey sessionKey = null;
      for (SoapPartBean soapPart : soapParts) {

         encDataId = soapPart.getContentId();


         //String encKeyId = "#EncKeyId-1";
         KeyGenerator keyGenerator = null;
         XMLCipher cipherData = null;
         // Select Symmetric encrypt algorithms for encrypt the data.
         if (symmetricEncAlg.equals(XMLCipher.TRIPLEDES)) {
            keyGenerator = KeyGenerator.getInstance("DESede");
            cipherData = XMLCipher.getInstance(XMLCipher.TRIPLEDES);
         } else if (symmetricEncAlg.equals(XMLCipher.AES_192)) {
            keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(192);
            cipherData = XMLCipher.getInstance(XMLCipher.AES_192);
         } else if (symmetricEncAlg.equals(XMLCipher.AES_256)) {
            keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256);
            cipherData = XMLCipher.getInstance(XMLCipher.AES_256);
         } else {
            keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128);
            cipherData = XMLCipher.getInstance(XMLCipher.AES_128);
         }

         // create session key for encrypt the data
         sessionKey = keyGenerator.generateKey();
         cipherData.init(XMLCipher.ENCRYPT_MODE, sessionKey);
         // Do the encrypt soapBody Content (true) , Element (false)

         EncryptedData encryptedData = null;
         if (soapPart.isContent()) {
            encryptedData = cipherData.encryptData(envelope.getOwnerDocument(), soapPart.getElement(), true);
         } else {
            encryptedData = cipherData.encryptData(envelope.getOwnerDocument(), soapPart.getElement(), false);
         }


         encryptedData.setId(encDataId);
         Element encryptedDataElem = cipherData.martial(encryptedData);

         /*
         KeyInfoHeaderBlock dataKeyInfo = new KeyInfoHeaderBlock(envelope.getOwnerDocument());
         SecurityTokenReference dataTokenReference = new SecurityTokenReference(envelope.getOwnerDocument());
         ReferenceElement referenceElement = new DirectReference();
         referenceElement.setAttribute("URI", encKeyId);
         dataTokenReference.setReference(referenceElement);
         dataKeyInfo.addSecurityTokenReference(dataTokenReference);
         // Insert KeyInfo to EncryptedData
         encryptedDataElem.insertBefore(dataKeyInfo.getAsSoapElement(), encryptedDataElem.getFirstChild());
          */
         Element dataKeyInfo = XMLUtil.createElement(envelope.getOwnerDocument(), "KeyInfo", "http://www.w3.org/2000/09/xmldsig#", "ds");
         SecurityTokenReference dataSecTokenReference = new SecurityTokenReference(envelope.getOwnerDocument());
         ReferenceElement referenceElement = new DirectReference();
         referenceElement.setAttribute("URI", encKeyId);
         dataSecTokenReference.setReference(referenceElement);
         dataKeyInfo.appendChild(dataSecTokenReference.getAsSoapElement());
         encryptedDataElem.insertBefore(dataKeyInfo, encryptedDataElem.getFirstChild());


         Element targetElement = soapPart.getElement();
         SOAPElement targetSoap = XMLUtils.convertToSoapElement(targetElement.getOwnerDocument(), targetElement);
         SOAPElement encSoap = XMLUtils.convertToSoapElement(encryptedDataElem.getOwnerDocument(), encryptedDataElem);

         System.out.println("Target Element :" + targetElement.getNodeName());
         // Not leaf node 
         if (targetElement.getFirstChild() != null && targetElement.getFirstChild().getNodeType() == Node.ELEMENT_NODE) {
            // Content 
            if (soapPart.isContent()) {
               System.out.println("Not Leaf Node Content ****************");
               // delete any children nodes
               System.out.println("Child Node :" + targetElement.getFirstChild());
               while (targetElement.hasChildNodes()) {
                  targetElement.removeChild(targetElement.getFirstChild());
               }
               targetSoap.addChildElement(encSoap);
               //Element
            } else {
               System.out.println("Not Leaf Node Element ****************");
               NodeList newNodes = (NodeList) targetElement.getChildNodes();
               while (targetElement.hasChildNodes()) {
                  targetElement.removeChild(targetElement.getFirstChild());
               }
               targetSoap.addChildElement(encSoap);
            }
            soapBody.removeContents();
            soapBody.addChildElement(XMLUtil.convertToSoapElement(targetElement.getOwnerDocument(), targetElement));
            // Leaf Note
         } else {
            if (soapPart.isContent()) {
               System.out.println("Leaf Node Content ****************");
               if (targetSoap.getParentElement() != null) {
                  SOAPElement parentSoap = XMLUtils.convertToSoapElement(targetSoap.getOwnerDocument(), targetSoap.getParentElement());
                  //   XMLUtils.printElement(targetSoap);
                  //   XMLUtils.printElement(encSoap);
                  targetSoap.removeContents();
                  targetSoap.addChildElement(encSoap);
                  //   XMLUtils.printElement(targetSoap);
                  //  XMLUtils.printElement(parentSoap);
                  soapBody.removeContents();
                  soapBody.addChildElement(XMLUtil.convertToSoapElement(parentSoap.getOwnerDocument(), parentSoap));
               }
            } else {
               System.out.println("Leaf Node Element ****************");
               if (targetSoap.getParentElement() != null) {
                  SOAPElement parentSoap = XMLUtils.convertToSoapElement(targetSoap.getOwnerDocument(), targetSoap.getParentElement());
                  //  XMLUtils.printElement(parentSoap);
                  //  XMLUtils.printElement(targetSoap);
                  //  XMLUtils.printElement(encSoap);
                  //  XMLUtils.printDocument(parentSoap.getOwnerDocument());
                  //  XMLUtils.printDocument(encSoap.getOwnerDocument());

                  //parentSoap.insertBefore(encSoap, targetSoap);
                  //   parentSoap.replaceChild(targetSoap, encSoap);
                  //   parentSoap.appendChild(encSoap);
                  //   parentSoap.appendChild(encSoap);
//                  parentSoap.getOwnerDocument().appendChild(encSoap);
                  parentSoap.addChildElement(encSoap);


                  //  parentSoap.appendChild(targetSoap);
                  //   parentSoap.insertBefore(targetSoap, targetSoap);
                  //   targetSoap.removeContents();
                  targetSoap.detachNode();



                  soapBody.removeContents();
                  soapBody.addChildElement(XMLUtil.convertToSoapElement(parentSoap.getOwnerDocument(), parentSoap));
               }
            }
         }


      }


      // Create an XMLCipher 
      //The encryption is performed  The data encryption algorithm is specified when getting an XMLCipher instance. 
      //The  XMLCipher instance must be initialised with the key to encrypt the data, which should be the session key generated
      XMLCipher keyCipher = null;
      // Select algorithm for encrypt session key 
      if (keyEncAlg.equals(XMLCipher.RSA_v1dot5)) {
         keyCipher = XMLCipher.getInstance(XMLCipher.RSA_v1dot5);
      } else {
         keyCipher = XMLCipher.getInstance(XMLCipher.RSA_OAEP);
      }

      // creates an XMLCipher, initialised for key wrapping using the public key of the certificate, and encrypts the session key
      keyCipher.init(XMLCipher.WRAP_MODE, certificate.getPublicKey());
      EncryptedKey encryptedKey = keyCipher.encryptKey(envelope.getOwnerDocument(), sessionKey);
      encryptedKey.setId(encKeyId);

      //Create ReferenceList  and add to EncryptedKey Each  EncryptedKey must contain a ReferenceList  and have a single 
      //DataReference  with the URI attribute set to the  Id attribute of the encrypted data 
      ReferenceList referenceList = keyCipher.createReferenceList(ReferenceList.DATA_REFERENCE);
      encryptedKey.setReferenceList(referenceList);

      for (SoapPartBean soapPart : soapParts) {
         // Reference to target EncryptedData using Id. 
         Reference dataReference = referenceList.newDataReference("#" + soapPart.getContentId());
         referenceList.add(dataReference);
      }
      /*
      
      encryptedKey.setReferenceList(referenceList);
      // Create the keyinfo referring to the certificate (<ds:KeyInfo>)
      KeyInfoHeaderBlock keyInfoHeaderBlock = new KeyInfoHeaderBlock(envelope.getOwnerDocument());
      
      // Set SUN KeyInfoHeaderBlock to Apache KeyInfo
      Element keElement = keyInfoHeaderBlock.getKeyInfo().getElement();
      KeyInfo ki = new KeyInfo(keElement.getOwnerDocument());
      // Set SUN KeyInfoHeaderBlock to Apache KeyInfo
      encryptedKey.setKeyInfo(ki);
      // Mashall 
      Element encryptedKeyElem = keyCipher.martial(encryptedKey);
      // Create block (<wsse:SecurityTokenReference>) 
      SecurityTokenReference securityTokenReference = new SecurityTokenReference(envelope.getOwnerDocument());
       */


      encryptedKey.setReferenceList(referenceList);
      // Create the keyinfo referring to the certificate (<ds:KeyInfo>)
      Element keyInfoHeaderBlock = XMLUtil.createElement(envelope.getOwnerDocument(), "KeyInfo", "http://www.w3.org/2000/09/xmldsig#", "ds");
      KeyInfo ki = new KeyInfo(keyInfoHeaderBlock.getOwnerDocument());
      // Set SUN KeyInfoHeaderBlock to Apache KeyInfo
      encryptedKey.setKeyInfo(ki);
      // Mashall 
      Element encryptedKeyElem = keyCipher.martial(encryptedKey);
      // Create block (<wsse:SecurityTokenReference>) 
      SecurityTokenReference securityTokenReference = new SecurityTokenReference(envelope.getOwnerDocument());





      if (SoapConstants.BINARY_SEC_TOKEN.equals(keyIdType)) {
         BinarySecurityTokenType kit = new BinarySecurityTokenType();
         securityTokenReference = kit.getKeyIndentifierType(securityTokenReference, soapHeader.getOwnerDocument(), certificate);
         // insert <wsse:BinarySecurityTokent> into <wsse:Security> only Key Identifier Type are Binary Security Token
         secureHeader.insertHeaderBlock(kit.getX509SecurityToken());
      }
      if (SoapConstants.ISSUER_NAME.equals(keyIdType)) {
         KeyIndentifierType kit = new IssuerNameType();
         securityTokenReference = kit.getKeyIndentifierType(securityTokenReference, envelope.getOwnerDocument(), certificate);
      }
      if (SoapConstants.SUBJECT_KEY_INDENTIFIER.equals(keyIdType)) {
         KeyIndentifierType kit = new SubjectkeyIdentifierType();
         securityTokenReference = kit.getKeyIndentifierType(securityTokenReference, envelope.getOwnerDocument(), certificate);
      }
      if (SoapConstants.X509_CERTIFICATE.equals(keyIdType)) {
         KeyIndentifierType kit = new X509CertificateType();
         securityTokenReference = kit.getKeyIndentifierType(securityTokenReference, envelope.getOwnerDocument(), certificate);
      }

      // Insert  <wsse:Reference> or <> or <> or <>  <wsse:SecurityTokenReference> Depend on Key Indetifier Type 
      //keyInfoHeaderBlock.addSecurityTokenReference(securityTokenReference);
      keyInfoHeaderBlock.appendChild(securityTokenReference.getAsSoapElement());
      // Insert <xenc:ReferenceList>  to <xenc:EncryptedKey> 
      // encryptedKeyHeaderBlock.setReferenceList(refListHeaderBlock);
      // Insert EncryptedKey into Header (<wsse:Security>)

      secureHeader.appendChild(encryptedKeyElem);

      // System.out.println("Soap Message : " + XMLUtils.prettyPrint(soapMessage));
      String encMessage = XMLUtils.prettyPrint(soapMessage);
      //   System.out.println("Response Message : " + encMessage);

      return encMessage;
   }

   @Override
   public String decryptSoap(String strMessage, Key privateKey, boolean content) throws Exception {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   /* 
    * filter all elements whose tag name = filter 
    */
   public void filterElements(Node parent, String filter) {
      NodeList children = parent.getChildNodes();

      for (int i = 0; i < children.getLength(); i++) {
         Node child = children.item(i);

// only interested in elements  
         if (child.getNodeType() == Node.ELEMENT_NODE) {
// remove elements whose tag name  = filter  
// otherwise check its children for filtering with a recursive call  
            if (child.getNodeName().equals(filter)) {
               parent.removeChild(child);
            } else {
               filterElements(child, filter);
            }
         }
      }
   }

   public void removeAll(Node node, short nodeType, String name) {
      if (node.getNodeType() == nodeType && (name == null || node.getNodeName().equals(name))) {
         node.getParentNode().removeChild(node);
      } else {
         NodeList list = node.getChildNodes();
         for (int i = 0; i < list.getLength(); i++) {
            removeAll(list.item(i), nodeType, name);
         }
      }
   }
}
