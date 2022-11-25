package emotionalsongs;

import user.ClientInterf;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class ResourceManager extends UnicastRemoteObject implements ResourceManagerInterface {
    HashMap<String, String> repoSong;

    public ResourceManager() throws RemoteException {
        super();
        this.repoSong = new HashMap<>();
    }

    public void loadSongs() {
        // Query al db per caricare tutte le canzoni nella hashmap
    }


    @Override
    public Song getSong(Song s) {
        return null;
    }

    @Override
    public void readMsg(ClientInterf c) throws RemoteException {
        System.out.println(c.test() + " visto da server");
    }

    public static void main(String[] args) throws Exception {
        ResourceManager g = new ResourceManager();
        Registry r = LocateRegistry.createRegistry(11000);
        r.rebind("Gestore", g);
    }
}
