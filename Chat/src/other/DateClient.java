package other;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.*;

public class DateClient {
  public static void main(String[] args) throws IOException {
    Socket clientSocket = new Socket("192.168.1.50", 9090);
    BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    String msg = reader.readLine();
    JOptionPane.showMessageDialog(null, msg);
  }
}
