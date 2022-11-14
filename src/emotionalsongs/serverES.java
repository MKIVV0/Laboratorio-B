package emotionalsongs;

import java.io.*;
import java.net.*;

public class serverES {

  public static final int PORT = 13333;
  static ServerSocket server;
  static Socket connection;
  public static PrintWriter toClient;
  public static BufferedReader fromClient;

  public static ObjectOutputStream os;

  public static void main(String[] args) {
    Song song = new Song(2022, "song01", "Pippo", "Ciao");
    while (true) {
      try {
        server = new ServerSocket(PORT);
        connection = server.accept();
        os = new ObjectOutputStream(connection.getOutputStream());

        os.writeObject(song);
        /*
        toClient = new PrintWriter(new OutputStreamWriter(connection.getOutputStream()),true);
        fromClient = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        int cnt = 0;
        while (cnt <= 5) {
          toClient.println("Sono il server: prova " + cnt++ * 10);
        }

        String str = null;
        while ((str = fromClient.readLine()) != null) {
          System.out.println("MSG FROM CLIENT: " + str);
        }*/

      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
