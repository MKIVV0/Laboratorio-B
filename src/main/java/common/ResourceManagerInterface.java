package common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.LinkedList;

public interface ResourceManagerInterface extends Remote {

    LinkedList<Song> findSong(String s) throws RemoteException;

    AbstractUser login(AbstractUser u, String uid, String pw) throws RemoteException, AlreadyLoggedException, WrongCredentialsException, SQLException;

    void evaluateSong(Emotions emotion, AbstractUser user, Song song, String score, String notes) throws RemoteException, NotLoggedException, AlreadyValuedException, SQLException;

    String getFeedback(AbstractUser user, Emotions emotion_name, Song song) throws RemoteException, NoFeedbackException, SQLException;

    AbstractUser logout(AbstractUser u) throws RemoteException, NotLoggedException;

    void registerUser(String fn, String ln, String FC, String addr, String email, String uid, String pwd) throws SQLException, AlreadyRegisteredException, RemoteException;

    LinkedList<String> getFeedback(Song song) throws SQLException, NoFeedbackException, RemoteException;

    LinkedList<String> getFeedback(Song song, AbstractUser user) throws SQLException, NoFeedbackException, RemoteException;

    void deleteFeedback(Emotions emotion, String user_id, String song_id) throws SQLException, NoFeedbackException,RemoteException;

    void modifyFeedback(Emotions emotion, String user_id, String song_id, String param_name, String param_value) throws SQLException, NoFeedbackException, RemoteException;

    void createPlaylist(String pl_name, String song_id, String user_id) throws SQLException, playlistException, RemoteException;

    void removeSongFromPlaylist(String pl_name, String song_id, String user_id) throws SQLException, playlistException, RemoteException;

    void renamePlaylist(String curr_pl_name, String new_pl_name, String user_id) throws SQLException, playlistException, RemoteException;

    void deletePlaylist(String pl_name, String user_id) throws SQLException, playlistException, RemoteException;

    void addSongToPlaylist(String pl_name, String song_id, String user_id) throws SQLException, playlistException, RemoteException;

    void modifyUserParam(String user_id, String param_name, String param_value) throws SQLException, UserException, RemoteException;

    void deleteUser(String user_id) throws SQLException, UserException, RemoteException;
}
