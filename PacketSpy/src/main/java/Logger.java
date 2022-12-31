import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class Logger {
  private final BufferedWriter bufferedWriter;

  public Logger() throws IOException {
    try {
      // use filewriter + bufferedwriter instead of fileoutputstream
      // for better performance.
      FileWriter fileWriter = new FileWriter("interceptedMessages.txt", true);
      this.bufferedWriter = new BufferedWriter(fileWriter);
      this.startSession();
    } catch (IOException e) {
      throw new IOException("Unable to open file.");
    }
  }

  public void add(String detail) {
    try {
      this.bufferedWriter.append(detail);
      this.bufferedWriter.newLine();
    } catch (IOException e) {
      System.out.println(e);
    }
  }

  private void startSession() throws IOException {
    this.bufferedWriter.append(new Date().toString());
    this.bufferedWriter.newLine();
    this.bufferedWriter.append("~start of session~");
    this.bufferedWriter.newLine();
  }

  public void close() throws IOException {
    this.bufferedWriter.append("~end of session~");
    this.bufferedWriter.newLine();
    this.bufferedWriter.newLine();
    this.bufferedWriter.close();
  }
}
