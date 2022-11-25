package emotionalsongs;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class ResourceManager extends UnicastRemoteObject implements ResourceManagerInterface {
    HashMap<String, String> repoSong;

    protected ResourceManager() throws RemoteException {
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
}
