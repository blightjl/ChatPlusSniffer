package other;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class InteractiveServer {
  private static final ArrayList<String> names = new ArrayList<>(Arrays.asList(new String[]{"Bjergsen", "Doublelift", "Faker"}));
  private static final Random rand = new Random(1);
  private static final int PORT = 9090;
  private static int totalConnections = 0;

  public static void main(String[] args) throws IOException {
    System.out.println("other.Server started...");
    ServerSocket socket = new ServerSocket(PORT);
    Socket client = socket.accept();
    totalConnections++;
    System.out.println("Total connections: " + totalConnections);

    PrintStream out = new PrintStream(client.getOutputStream(), true);
    BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

    try {
      while (true) {
        String request = in.readLine();
        if (request != null && request.contains("lol")) {
          out.println(InteractiveServer.getName());
        } else {
          out.println("other.Server message failed.");
        }
      }
    } finally {
      System.out.println("other.Server closing...");
      client.close();
      socket.close();
    }
  }

  public static String getName() {
    int x = rand.nextInt(3);
    return names.get(x);
  }
}
