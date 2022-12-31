import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiServer {
  private static final ArrayList<String> names = new ArrayList<>(Arrays.asList(new String[]{"Bjergsen", "Doublelift", "Faker"}));
  private static final Random rand = new Random();
  private static final int PORT = 9090;
  public static int totalConnections;

  private static ArrayList<ClientHandler> clients = new ArrayList<>();
  private static ExecutorService pool = Executors.newFixedThreadPool(4);

  public static void main(String[] args) throws IOException {
    System.out.println("Server started...");
    ServerSocket socket = new ServerSocket(PORT);

    while (true) {
      Socket client = socket.accept();
      ClientHandler clientThread = new ClientHandler(client, clients);
      clients.add(clientThread);
      pool.execute(clientThread);
    }
  }

  public static String getName() {
    int x = rand.nextInt(3);
    return names.get(x);
  }

  public static void updateConnections() {
    totalConnections++;
    System.out.println("Total Connections: " + totalConnections
    + " " + "Current Connections: " + clients.size());
  }
}
