/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.soap.security.crypto;

import org.apache.xml.security.encryption.XMLEncryptionException;
import com.sun.xml.wss.XWSSecurityException;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.List;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import org.wsssoapbox.bean.model.soap.SoapPartBean;

/**
 *
 * @author Peter
 */
public interface SoapCrypto {

   public String encryptSoap(String strMessage, String keyEncAlg, String keyIdType, String symmetricEncAlg, X509Certificate certificate) throws SOAPException, UnsupportedEncodingException, XWSSecurityException, NoSuchAlgorithmException, XMLEncryptionException, Exception;

   public String encryptSoap(SOAPMessage soapMessage, String keyEncAlg, String keyIdType, String symmetricEncAlg, X509Certificate certificate) throws SOAPException, UnsupportedEncodingException, XWSSecurityException, NoSuchAlgorithmException, XMLEncryptionException, Exception;

   public String encryptSoapParts(SOAPMessage soapMessage, List<SoapPartBean> soapParts, String keyEncAlg, String keyIdType, String symmetricEncAlg, X509Certificate certificate) throws SOAPException, UnsupportedEncodingException, XWSSecurityException, NoSuchAlgorithmException, XMLEncryptionException, Exception;

   public String encryptSoapParts(String strMessage, List<SoapPartBean> soapParts, String keyEncAlg, String keyIdType, String symmetricEncAlg, X509Certificate certificate) throws SOAPException, UnsupportedEncodingException, XWSSecurityException, NoSuchAlgorithmException, XMLEncryptionException, Exception;

   public String decryptSoap(String strMessage, Key privateKey, boolean content) throws Exception;
}
