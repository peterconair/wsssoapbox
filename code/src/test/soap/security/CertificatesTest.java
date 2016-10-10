/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soap.security;

import org.wsssoapbox.soap.security.CertUtil;
import java.security.MessageDigest;
import com.sun.xml.wss.impl.misc.Base64;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.cert.CertificateFactory;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.security.cert.CertificateExpiredException;
import org.junit.Before;
import org.junit.Test;
import sun.security.x509.X500Name;

/**
 *
 * @author Peter
 */
public class CertificatesTest {

   FileInputStream fis;
   BufferedInputStream bis;
   CertificateFactory cf;

   public CertificatesTest() {
   }

   @Before
   public void setUp() throws Exception {

      // Generate sample X509Certificate
      fis = new FileInputStream("F:/Develope/sourecode/netbeans/J2EE/WS-SSOAPBox/src/test/soap/usernametoken/test.pem");

      bis = new BufferedInputStream(fis);
      cf = CertificateFactory.getInstance("X.509");

   }

   @Test
   public void testCertificate() throws IOException, CertificateException, Exception {


      X509Certificate cert = null;
      while (bis.available() > 0) {
         cert = (X509Certificate) cf.generateCertificate(bis);
      }

      System.out.println("Cert : ");
      System.out.println("Serial Number : " + cert.getSerialNumber().toString(16));
      System.out.println("Signature Algorithm Name  : " + cert.getSigAlgName());
      System.out.println("Signature Algorithm ID : " + cert.getSigAlgOID());
      System.out.println("Basic Constraints  : " + cert.getBasicConstraints());
      System.out.println("Verion  : " + cert.getVersion());
      System.out.println("Signature Base64  : " + Base64.encode(cert.getSignature()));
      System.out.println("Signature Hex : " + CertUtil.byteToHex(cert.getSignature()));
      System.out.println("Cert Type : " + cert.getType());

      System.out.println("");
      System.out.println("Issuer DN : " + cert.getIssuerDN().getName());
      System.out.println("Issuer X500 Principal " + cert.getIssuerX500Principal().getName());

      X500Name x500Issuer = new X500Name(cert.getIssuerX500Principal().getName());
      x500Issuer.getCommonName();

      System.out.println("Issuer CN : " + x500Issuer.getCommonName());
      System.out.println("Issuer OU : " + x500Issuer.getOrganizationalUnit());
      System.out.println("Issuer C : " + x500Issuer.getCountry());
      System.out.println("Issuer O : " + x500Issuer.getOrganization());
      System.out.println("Issuer ST : " + x500Issuer.getState());
      System.out.println("Issuer L : " + x500Issuer.getLocality());

      System.out.println("");
      System.out.println("Sub DN : " + cert.getSubjectDN().getName());
      System.out.println("Subject X500 Principal : " + cert.getSubjectX500Principal().getName());
      System.out.println("Valid from " + cert.getNotBefore()
              + " to " + cert.getNotAfter());


      System.out.println("Public Key : ");
      System.out.println("Algorithm : " + cert.getPublicKey().getAlgorithm());
      System.out.println("Value : " + cert.getPublicKey().getFormat());
      System.out.println("Value : " + Base64.encode(cert.getPublicKey().getEncoded()));



      try {
         cert.checkValidity();
      } catch (CertificateExpiredException e) {
         System.out.println("This cer expired !");
      }

      byte[] certificateData = cert.getEncoded();
      System.out.println("Certificate Fingerprints:");
      System.out.println("MD5 : " + CertUtil.doFingerprint(certificateData, "MD5"));

      System.out.println("SHA : " + CertUtil.doFingerprint(certificateData, "SHA"));

      System.out.println("Signature algorithm name: " + cert.getSigAlgName());
      System.out.println("Version: " + cert.getVersion());

      System.out.println("");
      System.out.println("Cert encoded : " + Base64.encode(cert.getEncoded()));
   }

   protected static void doFingerprint(byte[] certificateBytes, String algorithm) throws Exception {
      System.out.print("  " + algorithm + ": ");
      MessageDigest md = MessageDigest.getInstance(algorithm);
      md.update(certificateBytes);
      byte[] digest = md.digest();
      for (int i = 0; i < digest.length; i++) {
         if (i != 0) {
            System.out.print(":");
         }
         int b = digest[i] & 0xff;
         String hex = Integer.toHexString(b);
         if (hex.length() == 1) {
            System.out.print("0");
         }
         System.out.print(hex);
      }
      System.out.println();
   }
}
