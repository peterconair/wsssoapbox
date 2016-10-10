/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soap.security.encryption.exmple;

import com.sun.org.apache.xml.internal.security.keys.KeyInfo;
import com.sun.org.apache.xml.internal.security.keys.content.X509Data;
import com.sun.org.apache.xml.internal.security.keys.content.keyvalues.RSAKeyValue;
import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.KeyStore;
import java.security.cert.X509Certificate;


/**
 *
 * @author Peter
 */
public class CreateKeyInfo {

    /**
     * Method main
     *
     * @param unused
     * @throws Exception
     */
    public static void main(String unused[]) throws Exception {

        KeyStore ks = KeyStore.getInstance("JKS");
        FileInputStream fis = new FileInputStream(
            "samples/data/keystore.jks");

        ks.load(fis, "xmlsecurity".toCharArray());

        javax.xml.parsers.DocumentBuilderFactory dbf =
            javax.xml.parsers.DocumentBuilderFactory.newInstance();

        dbf.setNamespaceAware(true);

        javax.xml.parsers.DocumentBuilder db = dbf.newDocumentBuilder();
        org.w3c.dom.Document doc = db.newDocument();
        KeyInfo ki = new KeyInfo(doc);

        doc.appendChild(ki.getElement());
        ki.setId("myKI");
        ki.addKeyName("A simple key");

        X509Certificate cert = (X509Certificate) ks.getCertificate("test");

        ki.addKeyValue(cert.getPublicKey());

        X509Data x509Data = new X509Data(doc);

        ki.add(x509Data);
        x509Data.addCertificate(cert);
        x509Data.addSubjectName("Subject name");
        x509Data.addIssuerSerial("Subject nfsdfhs", 6786);
        ki.add(new RSAKeyValue(doc, new BigInteger("678"), new BigInteger("6870")));
        XMLUtils.outputDOMc14nWithComments(doc, System.out);
    }
    
}