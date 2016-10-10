/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package security.digest;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Peter
 */
public class ProtectedServer {

   public boolean authenticate(InputStream inStream)
           throws IOException, NoSuchAlgorithmException {
      DataInputStream in = new DataInputStream(inStream);

      String user = in.readUTF();
      long t1 = in.readLong();
      double q1 = in.readDouble();
      int length = in.readInt();
      byte[] protected1 = new byte[length];
      in.readFully(protected1);

      String password = lookupPassword(user);
      byte[] local = Protection.makeDigest(user, password, t1, q1);
      return MessageDigest.isEqual(protected1, local);
   }

   protected String lookupPassword(String user) {
      return "1234";
   }

   public static void main(String[] args) throws Exception {
      int port = 7999;
      ServerSocket s = new ServerSocket(port);
      Socket client = s.accept();
      System.out.println("Server Startded .......");
      ProtectedServer server = new ProtectedServer();

         if (server.authenticate(client.getInputStream())) {
            System.out.println("Client logged in.");
         } else {
            System.out.println("Client failed to log in.");
         }
      s.close();
   }
}
