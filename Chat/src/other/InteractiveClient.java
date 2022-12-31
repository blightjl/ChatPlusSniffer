package other;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.swing.*;

public class InteractiveClient {
  private static final String SERVER_IP = "192.168.1.50";
  private static final int SERVER_PORT = 9090;

  public static void main(String[] args) throws IOException {
    Socket clientSocket = new Socket(SERVER_IP, SERVER_PORT);

    BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
    PrintStream out = new PrintStream(clientSocket.getOutputStream(), true);

    while (true) {
      System.out.println(">");
      String msg = keyboard.readLine();

      if (msg.equals("quit")) {
        break;
      }

      out.println(msg);

      String serverResponse = input.readLine();
      System.out.println("[SERVER]: " + serverResponse);
    }
    clientSocket.close();
  }
}
