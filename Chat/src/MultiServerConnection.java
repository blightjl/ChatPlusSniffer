import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class MultiServerConnection implements Runnable {
  private Socket server;
  private BufferedReader in;

  public MultiServerConnection(Socket server) throws IOException {
    this.server = server;
    this.in = new BufferedReader(new InputStreamReader(this.server.getInputStream()));
  }

  @Override
  public void run() {
      try {
        while (true) {
          String serverResponse = in.readLine();
          if (serverResponse == null) break;
          System.out.println("connection: " + serverResponse);
        }
      } catch (IOException e) {
        System.out.println(e);
      } finally {
        try {
          in.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
    }
  }
}
