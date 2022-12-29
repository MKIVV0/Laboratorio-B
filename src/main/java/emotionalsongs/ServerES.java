/**
 * @author Zhang Ying Huang, Matricola 746483, CO
 * @author Alessandro Di Lorenzo, Matricola 733052, CO
 */
package emotionalsongs;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
/**
 * This class contains the server main.
 */
public class ServerES {

    /**
     * Main method, the entry point of the program.
     * @param args argument list that is passed to the program.
     */
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

        ResourceManager g = new ResourceManager(server,port,user,password);
        Registry r = LocateRegistry.createRegistry(11000);
        r.rebind("Gestore", g);

        System.err.println("server started");

    }

}
