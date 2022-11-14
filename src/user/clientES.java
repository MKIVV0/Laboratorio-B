package user;

import emotionalsongs.Song;
import emotionalsongs.serverES;

import java.io.*;
import java.net.*;

public class clientES {
    public static InetAddress address;
    public static PrintWriter toServer;
    public static BufferedReader fromServer;
    public static ObjectInputStream in;
    public static Socket socket;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        try {
            address = InetAddress.getByName("localhost");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        try {
            socket = new Socket(address, serverES.PORT);
            in = new ObjectInputStream(socket.getInputStream());
            /*
            toServer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);
            fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));*/
        } catch (IOException e) {
            e.printStackTrace();
        }

        Song s = (Song) in.readObject();
        System.err.println(s.toString());

/*
        // FUNZIONA
        int cnt = 0;
        while (cnt <= 5) {
            toServer.println("Sono il client: prova " + cnt++);
        }

        String str = null;
        while ((str = fromServer.readLine()) != null) {
            System.out.println("MSG FROM SERVER: " + str);
        }

        toServer.close();
        fromServer.close();
        */
        socket.close();
  }
}
