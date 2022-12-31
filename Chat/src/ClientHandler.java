import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;

import other.InteractiveServer;
import other.ServerInfo;

public class ClientHandler implements Runnable {
  private Socket client;
  private BufferedReader in;
  private PrintStream out;
  private ArrayList<ClientHandler> clients;

  public ClientHandler(Socket client, ArrayList<ClientHandler> clients) throws IOException {
    this.client = client;
    this.clients = clients;
    this.in = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
    this.out = new PrintStream(this.client.getOutputStream(), true);
  }
  @Override
  public void run() {
    ServerInfo.update();
    try {
      while (true) {
        String request = in.readLine();
        if (request != null && request.contains("lol")) {
          out.print(InteractiveServer.getName());
        }
        else if (request.startsWith("say")) {
          int firstSpace = request.indexOf(" ");
          if (firstSpace != -1) {
            outToAll(request.substring(firstSpace + 1));
          }
        }
        else if (request.equals("connections?")) {
          outToAll("" + ServerInfo.currentConnections());
        }
        else {
          out.print("Server message failed.");
        }
      }
    } catch (IOException e) {
      System.out.println(e);
    } finally {
      System.out.println("Server closing...");
      try {
        in.close();
      }
      catch (IOException o) {
        System.out.println(o);
      }
      out.close();
    }
  }

  private void outToAll(String string) {
    for (ClientHandler client : clients) {
      client.out.print(string);
    }
  }
}
