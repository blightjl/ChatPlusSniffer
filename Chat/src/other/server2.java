package other;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class server2 {
  // port for listening
  private static final int PORT = 6666;

  public static void main(String[] args) throws IOException {
      System.out.println("other.Server started...");
      // create server with port
      ServerSocket server = new ServerSocket(PORT);
      System.out.println("Waiting for client...");
      // wait for client connection
      Socket serverSocket = server.accept();
      System.out.println("other.Client connected.");

      // get input from client
      BufferedReader data = new BufferedReader(new InputStreamReader(
              serverSocket.getInputStream()));

      // read server message from System.in
      BufferedReader serverMsg = new BufferedReader(new InputStreamReader(System.in));
      // send information to server's socket for end point to read
      PrintStream out = new PrintStream(serverSocket.getOutputStream());
      // prepare string to display
      while (true) {
//        String serverOutput = "other.Server announcement: " + serverMsg.readLine();
//        out.println(serverOutput);
//        System.out.println(serverOutput);
        String msg = data.readLine();
        System.out.println(msg);
        if (msg.equals("> done")) {
          break;
        }
      }
      System.out.println("other.Server closing...");
      // close connection
      server.close();
      serverSocket.close();
      System.out.println("other.Server closed.");
  }
}
