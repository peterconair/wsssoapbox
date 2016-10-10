/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package security.ssl;

import java.net.Socket;
import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;

/**
 *
 * @author Peter
 */
public class EchoClient {
   public static void main(String[] args) throws Exception {

    String hostname = "localhost";

    if (args.length > 0)

      hostname = args[0];

    SocketFactory sf = SSLSocketFactory.getDefault();

    Socket socket = sf.createSocket(hostname, 2950);

    System.out.println("Connection established.");

    SocketUtil.printSocketInfo(socket, " --> ");



    java.io.InputStream is = socket.getInputStream();

    java.io.OutputStream os = socket.getOutputStream();

    byte[] buf = new byte[1024];

    java.io.BufferedReader br = new java.io.BufferedReader(

        new java.io.InputStreamReader(System.in));



    while (true){

      System.out.print("Enter Message (Type \"quit\" to exit): ");

      System.out.flush();

      String inp = br.readLine();

      if (inp.equalsIgnoreCase("quit"))

        break;

      os.write(inp.getBytes());

      int n = is.read(buf);

      System.out.println("Server Returned: " + new String(buf, 0, n));

    }

    socket.close();

    System.out.println("Connection closed.");

  }
}
