package common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;

public interface ResourceManagerInterface extends Remote {

    Song getSong(String s) throws RemoteException;

    AbstractUser login(AbstractUser u, String uid, String pw) throws RemoteException, AlreadyLoggedException, WrongCredentialsException, SQLException;

    void valutaBrano(AbstractUser u, Song s, int score) throws RemoteException, NotLoggedException;

    AbstractUser logout(AbstractUser u) throws RemoteException, NotLoggedException;
}
