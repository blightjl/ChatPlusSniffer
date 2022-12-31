package other;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class DateServer {
  public static void main(String[] args) throws IOException {
    System.out.println("other.Server started...");
    ServerSocket socket = new ServerSocket(9090);
    Socket client = socket.accept();

    PrintStream out = new PrintStream(client.getOutputStream(), true);
    String date = (new Date()).toString();
    System.out.println(date);
    out.print(date);

    System.out.println("other.Server closing...");
    client.close();
    socket.close();
  }
}
