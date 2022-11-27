package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ResourceManagerInterface extends Remote {

    public Song getSong(String s) throws RemoteException;

    //boolean login(ClientInterf c, String uid, String pw) throws RemoteException;
   // LoggedUser login(ClientInterf c, String uid, String pw) throws RemoteException;
    AbstractUser login(String uid, String pw) throws RemoteException;

    //void valutaBrano(LoggedUser u, Song s, int score) throws RemoteException;

    AbstractUser logout(AbstractUser u) throws RemoteException;
}
