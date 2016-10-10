/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wsssoapbox.soap.security.cypto.xpath;


import org.apache.xml.security.encryption.EncryptedData;
import org.apache.xml.security.encryption.XMLEncryptionException;
import org.apache.xml.security.keys.keyresolver.KeyResolverException;
import com.sun.xml.wss.XWSSecurityException;
import com.sun.xml.wss.impl.SecurityTokenException;
import java.io.IOException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.List;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.xpath.XPathExpressionException;
import org.apache.xml.security.encryption.EncryptedKey;
import org.w3c.dom.Element;
import org.wsssoapbox.bean.model.soap.SoapPartBean;

/**
 *
 * @author Peter
 */
public interface SoapCryptoDAO {

   public EncryptedKey findEncryptedKeyByEncryptedData(SOAPMessage soapMessage, String encDataId) throws Exception;

   public EncryptedData findEncryptedDataByEncryptedKey(SOAPMessage soapMessage, String encKeyId) throws Exception;

   public List<EncryptedData> findAllEncrypteDataByDataReferences(SOAPMessage soapMessage) throws XPathExpressionException, SOAPException, XMLEncryptionException, KeyResolverException;

   public EncryptedKey findEncryptedKey(SOAPMessage soapMessage, String encKeyId) throws SOAPException, XPathExpressionException, XMLEncryptionException, KeyResolverException;

   public List<EncryptedKey> findEncryptedKeys(SOAPMessage soapMessage) throws SOAPException, XPathExpressionException, XMLEncryptionException, KeyResolverException;

   public EncryptedData findEncryptedData(SOAPMessage soapMessage, String encDataId) throws SOAPException, XPathExpressionException, XMLEncryptionException;

   public List<EncryptedData> findEncryptedDatas(SOAPMessage soapMessage) throws SOAPException, XPathExpressionException, XMLEncryptionException;

   public boolean verifyCertificate(SOAPMessage soapMessage, X509Certificate certificate) throws XPathExpressionException, SOAPException, SecurityTokenException, IOException, CertificateEncodingException, XWSSecurityException;

   public List<SoapPartBean> findAllSoapPartsInBody(SOAPMessage soapMessage) throws SOAPException, XPathExpressionException, XMLEncryptionException;
   public List<Element> findAllElementInBody(SOAPMessage soapMessage) throws SOAPException, XPathExpressionException, XMLEncryptionException;
}
