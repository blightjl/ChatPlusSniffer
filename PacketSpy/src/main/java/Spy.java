import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.pcap4j.core.BpfProgram;
import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PacketListener;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.packet.Packet;
import org.pcap4j.util.NifSelector;
public class Spy {
  static PcapNetworkInterface setNetworkDevice() {
    PcapNetworkInterface device = null;
    try {
      device = new NifSelector().selectNetworkInterface();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return device;
  }
  static BufferedReader readUserInput = new BufferedReader(new InputStreamReader(System.in));

  public static void main(String[] args) throws PcapNativeException, NotOpenException, IOException {
    int snapshotLength = 65536; // in bytes
    int readTimeout = 10;
    PcapNetworkInterface device = setNetworkDevice();
    System.out.println("Network selected: " + device);
    PcapHandle handle = device.openLive(snapshotLength,
            PcapNetworkInterface.PromiscuousMode.PROMISCUOUS, readTimeout);

    // Set a filter to only listen for tcp packets on port 80 (HTTP)
    String filter = "tcp port 9090";
    handle.setFilter(filter, BpfProgram.BpfCompileMode.OPTIMIZE);

    System.out.println("Listening started...");

    Logger log = new Logger();
    Runnable userChecker = () -> {
      while (true) {
        String userInput;
        try {
          userInput = readUserInput.readLine();
          if (userInput.equals("stop")) {
            handle.breakLoop();
          }
        } catch (IOException | NotOpenException e) {
          break;
        }
      }
    };
    Thread checkUserInput = new Thread(userChecker);
    checkUserInput.start();
    // Create a listener that defines what to do with the received packets
    PacketListener listener = new PacketListener() {
      @Override
      public void gotPacket(Packet packet) {
        // Print packet information to screen
//        System.out.println(handle.getTimestamp());
//        System.out.println(packet);
        if (onlyHexStream(packet).contains("Hex stream:")) {
          String hexStream = onlyHexStream(packet);
          System.out.println(hexStream);
          String filtered = filterOut(hexStream);
          String msg = hexToString(filtered);
          System.out.println(msg);
          log.add(msg);
        }
      }
    };

    // Tell the handle to loop using the listener we created
    try {
      handle.loop(-1, listener);
    } catch (InterruptedException e) {
      System.out.println("completed loop");
    } finally {
      log.close();
      System.out.println("Logger closed.");
    }
    System.exit(0);
  }

  static String onlyHexStream(Packet packet) {
    String packetInfo = packet.toString();
    String[] infoArray = packetInfo.split("\n");
    String dataStream = infoArray[infoArray.length - 1];
    return dataStream.contains("Hex stream:") ? dataStream : "";
  }

  static String hexToString(String str) {
    StringBuilder msg = new StringBuilder();
    for (int i = 0; i < str.length(); i+=2) {
      String hex = str.substring(i, i + 2);
      msg.append((char) Integer.parseInt(hex, 16));
    }
    return msg.toString();
  }

  static String filterOut(String str) {
    StringBuilder stringBuilder = new StringBuilder(str);
    int index = stringBuilder.indexOf("Hex stream:");
    if (index >= 0) {
      stringBuilder.delete(index, index + "Hex stream:".length());
    }
    stringBuilder.replace(0, stringBuilder.length(), stringBuilder.toString().
            replace(" ", ""));
    return stringBuilder.toString();
  }
}

// NOTE: create a parsing method that looks at the last line of the packet
// another method to convert the hexadecimol represented by ascii into words.
// convert all printlns -> print
// create a exploiter man in the middle attaack as another component to this project
// learn rust? for the other project
// remake jar file through terminal for server/client
// add caesar cypher + a aes encryption method for chat and spy options
// need to create command line arg parser for this
// rewrite in python/rust/c++
