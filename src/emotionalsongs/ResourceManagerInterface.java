package emotionalsongs;

import user.ClientInterf;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ResourceManagerInterface extends Remote {
    public Song getSong(Song s) throws RemoteException;

    void readMsg(ClientInterf c) throws RemoteException;
}
