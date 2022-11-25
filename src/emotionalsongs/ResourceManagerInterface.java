package emotionalsongs;

import java.rmi.Remote;

public interface ResourceManagerInterface extends Remote {
    public Song getSong(Song s);
}
