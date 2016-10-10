/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.soap.security.crypto;

import org.apache.xml.security.encryption.EncryptedKey;
import org.apache.xml.security.encryption.XMLCipher;
import org.apache.xml.security.encryption.EncryptedData;
import org.apache.xml.security.encryption.XMLEncryptionException;
import com.sun.xml.wss.XWSSecurityException;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.wsssoapbox.bean.model.soap.SoapPartBean;
import org.wsssoapbox.soap.support.SoapCreator;
import org.wsssoapbox.soap.security.cypto.xpath.SoapCryptoDAO;
import org.wsssoapbox.soap.security.cypto.xpath.SoapDecryptionXPath;
import org.wsssoapbox.xml.util.XMLUtils;

/**
 *
 * @author Peter
 */
public class SoapDecryption implements SoapCrypto {

   private SOAPMessage soapMessage;
   private SoapCryptoDAO soapCrytpoDAO;

   @Override
   public String decryptSoap(String strMessage, Key privateKey, boolean content) throws Exception {
      System.out.println("**********************************************");
      System.out.println("************  Decryption Parts **************");
      System.out.println("**********************************************");

      soapMessage = MessageFactory.newInstance().createMessage();
      soapMessage = SoapCreator.createSOAPMessageFromString(strMessage);

      soapCrytpoDAO = new SoapDecryptionXPath();
      List<EncryptedData> eds = soapCrytpoDAO.findAllEncrypteDataByDataReferences(soapMessage);
      System.out.println("Founds EncryptedData : " + eds.size() + " : " + "elements ");

      String encAlg = "";
      String decMessage = "";
      for (EncryptedData ed : eds) {

         String Id = ed.getId();
         System.out.println("Encrypted Data Id : " + Id);

         EncryptedKey ek = soapCrytpoDAO.findEncryptedKeyByEncryptedData(soapMessage, Id);
         encAlg = ed.getEncryptionMethod().getAlgorithm();

         
         XMLCipher xmlCipher = XMLCipher.getInstance();

         Element el = xmlCipher.martial(ek);
         Element d1 = xmlCipher.martial(ed);

         // XMLUtils.printElement(el);
         // XMLUtils.printElement(d1);

         xmlCipher.init(XMLCipher.UNWRAP_MODE, privateKey);
         Key sKey = xmlCipher.decryptKey(ek, encAlg);

         xmlCipher.init(XMLCipher.DECRYPT_MODE, sKey);

         DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
         DocumentBuilder db = dbf.newDocumentBuilder();
         Document doc = db.newDocument();

         if(content){
            doc = xmlCipher.doFinal(soapMessage.getSOAPBody().getOwnerDocument(), soapMessage.getSOAPBody(), content);
         }else{
            doc = xmlCipher.doFinal(soapMessage.getSOAPBody().getOwnerDocument(), soapMessage.getSOAPBody());
         }
         decMessage = XMLUtils.prettyPrintDoc(doc);
         //  System.out.println(XMLUtils.prettyPrintDoc(d));
      }
      return decMessage;
   }

   @Override
   public String encryptSoap(String strMessage, String keyEncAlg, String keyIdType, String symmetricEncAlg, X509Certificate certificate) throws SOAPException, UnsupportedEncodingException, XWSSecurityException, NoSuchAlgorithmException, XMLEncryptionException, Exception {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public String encryptSoapParts(String strMessage, List<SoapPartBean> soapParts, String keyEncAlg, String keyIdType, String symmetricEncAlg, X509Certificate certificate) throws SOAPException, UnsupportedEncodingException, XWSSecurityException, NoSuchAlgorithmException, XMLEncryptionException, Exception {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public String encryptSoap(SOAPMessage soapMessage, String keyEncAlg, String keyIdType, String symmetricEncAlg, X509Certificate certificate) throws SOAPException, UnsupportedEncodingException, XWSSecurityException, NoSuchAlgorithmException, XMLEncryptionException, Exception {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public String encryptSoapParts(SOAPMessage soapMessage, List<SoapPartBean> soapParts, String keyEncAlg, String keyIdType, String symmetricEncAlg, X509Certificate certificate) throws SOAPException, UnsupportedEncodingException, XWSSecurityException, NoSuchAlgorithmException, XMLEncryptionException, Exception {
      throw new UnsupportedOperationException("Not supported yet.");
   }
}
