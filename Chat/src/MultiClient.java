import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.swing.*;

public class MultiClient {
  private static final String SERVER_IP = "192.168.1.50";
  private static final int SERVER_PORT = 9090;

  public static void main(String[] args) throws IOException {
    Socket serverSocket = new Socket(SERVER_IP, SERVER_PORT);

    MultiServerConnection connection = new MultiServerConnection(serverSocket);
//    BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
    PrintStream out = new PrintStream(serverSocket.getOutputStream(), true);
    Thread client = new Thread(connection);
    client.start();
    while (true) {
      System.out.println(">");
      String msg = keyboard.readLine();

      if (msg.equals("quit")) break;
      out.print(msg);
    }
    serverSocket.close();
    System.exit(0);
  }
}
