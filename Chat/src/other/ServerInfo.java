package other;

public class ServerInfo {
  private static int totalConnections = 0;

  public static void update() {
    totalConnections++;
  }

  public static int currentConnections() {
    return totalConnections;
  }
}
