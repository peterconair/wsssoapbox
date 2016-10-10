/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package security.ssl;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;

/**
 *
 * @author Peter
 */
public class SocketUtil {
   public static void printSocketInfo(Socket socket, String dir) {

    try {

      InetSocketAddress localAddr, remoteAddr;

      localAddr = (InetSocketAddress)socket.getLocalSocketAddress();

      remoteAddr = (InetSocketAddress)socket.getRemoteSocketAddress();



      System.out.println("  Connection   : " +

          localAddr.getHostName() + ":" + localAddr.getPort() + dir +

          remoteAddr.getHostName() + ":" + remoteAddr.getPort());



      SSLSession sess = ((SSLSocket)socket).getSession();

      System.out.println("  Protocol     : " + sess.getProtocol());

      System.out.println("  Cipher Suite : " + sess.getCipherSuite());

      Certificate[] localCerts = sess.getLocalCertificates();

      if (localCerts != null && localCerts.length > 0)

        printCertDNs(localCerts, "  Local Cert");



      Certificate[] remoteCerts = null;

      try {

        remoteCerts = sess.getPeerCertificates();

        printCertDNs(remoteCerts, "  Remote Cert");

      } catch (SSLPeerUnverifiedException exc){

        System.out.println("  Remote Certs: Unverified");

      }

    } catch (Exception exc){

      System.err.println("Could not print Socket Information: " + exc);

    }

  }



  private static void printCertDNs(Certificate[] certs, String label){

    for (int i = 0; i < certs.length; i++){

      System.out.println(label + "[" + i + "]: " +

          ((X509Certificate)certs[i]).getSubjectDN());

    }

  }
}
