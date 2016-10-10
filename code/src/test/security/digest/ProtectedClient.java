/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package security.digest;

import com.sun.org.apache.xml.internal.security.utils.Base64;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 *
 * @author Peter
 */
public class ProtectedClient {

   public void sendAuthentication(String user, String password,
           OutputStream outStream) throws IOException, NoSuchAlgorithmException {
      DataOutputStream out = new DataOutputStream(outStream);
      long t1 = (new Date()).getTime();
      double q1 = Math.random();
      byte[] protected1 = Protection.makeDigest(user, password, t1, q1);

      out.writeUTF(user);
      out.writeLong(t1);
      out.writeDouble(q1);
      out.writeInt(protected1.length);
      out.write(protected1);
      out.flush();

      System.out.println("Data send :" + new String(protected1));
      System.out.println("Data send Base64 :" + Base64.encode(protected1));
   }

   public static void main(String[] args) throws Exception {

      // String host = args[0];
      String host = "localhost";
      int port = 7999;
      String user = "peter";
      String password = "1234";
      Socket s = new Socket(host, port);

      ProtectedClient client = new ProtectedClient();
      client.sendAuthentication(user, password, s.getOutputStream());

      s.close();
   }
}
