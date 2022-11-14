package emotionalsongs;

import java.io.IOException;
import java.net.*;

public class serverES {

  public static final int PORT = 13333;
  static ServerSocket server;
  static Socket connection = null;

  public static void main(String[] args) {
    while (true) {
      try {
        connection = server.accept();

      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
