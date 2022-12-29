package emotionalsongs;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class ServerES {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        String server = "";
        String port = "";
        String user = "";
        String password = "";

        System.out.println("Server [Enter for default]:");
        server = sc.nextLine();
        System.out.println("Port [Enter for default]:");
        port = sc.nextLine();
        System.out.println("User[Enter for default]:");
        user = sc.nextLine();
        System.out.println("Password [Enter for default]:");
        password = sc.nextLine();
        // Sets the connection to the database and initializes it

        ResourceManager g = new ResourceManager(server,port,user,password);
        Registry r = LocateRegistry.createRegistry(11000);
        r.rebind("Gestore", g);

        System.err.println("server started");

    }

}
