package common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.LinkedList;

public interface ResourceManagerInterface extends Remote {

    LinkedList<Song> findSong(String s) throws RemoteException;

    AbstractUser login(AbstractUser u, String uid, String pw) throws RemoteException, AlreadyLoggedException, WrongCredentialsException, SQLException;

    void valutaBrano(AbstractUser u, Song s, Emotions e, int score) throws RemoteException, NotLoggedException, AlreadyValuedException;

    Feedback getFeedback(Song s) throws RemoteException, NoFeedbackException;

    AbstractUser logout(AbstractUser u) throws RemoteException, NotLoggedException;
}
