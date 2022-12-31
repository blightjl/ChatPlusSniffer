package other;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;

public class client2 {
  public static void main(String[] args) {
    BufferedReader dataIn = null;
    BufferedReader dataInServer = null;
    PrintStream dataOut = null;
    Socket socket = null;
    try {
      System.out.println("Connecting to server...");
      // connect to ip address and port
      socket = new Socket(args[0], Integer.parseInt(args[1]));

      System.out.println("Connected to server.");
      // get input from System.in
      dataIn = new BufferedReader(new InputStreamReader(System.in));

      // get input from other.Server
//      dataInServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

      // send output to System.in
      dataOut = new PrintStream(socket.getOutputStream());
    }
    catch (IOException e) {
      System.out.println(e);
    }

    String message = "";

    System.out.println("Enter text.");
    while (!message.equals("done")) {
      try {
        assert dataIn instanceof BufferedReader;
        message = "> " + dataIn.readLine();
        System.out.println(message);
        dataOut.println(message);

//        String serverMsg = dataInServer.readLine();
//        System.out.println(serverMsg);
      } catch (IOException err) {
        System.out.println(err);
      }
    }

    // close connection
    try {
      dataIn.close();
      dataOut.close();
      socket.close();
    }
    catch (IOException error) {
      System.out.println(error);
    }
  }
}