package common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.LinkedList;

public interface ResourceManagerInterface extends Remote {

    LinkedList<Song> findSong(String s) throws RemoteException;

    AbstractUser login(AbstractUser u, String uid, String pw) throws RemoteException, AlreadyLoggedException, WrongCredentialsException, SQLException;

    void evaluateSong(Emotions emotion, AbstractUser user, Song song, String score, String notes) throws RemoteException, NotLoggedException, AlreadyValuedException, SQLException;

    String getFeedback(String user_id, Emotions emotion_name, Song song) throws RemoteException, NoFeedbackException, SQLException;

    AbstractUser logout(AbstractUser u) throws RemoteException, NotLoggedException;
}
