/**
 * @author Zhang Ying Huang, Matricola 746483, CO
 * @author Alessandro Di Lorenzo, Matricola 733052, CO
 */
package common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * this class represents the interface that provides the ResourceManager
 * methods to both the client and the server.
 */
public interface ResourceManagerInterface extends Remote {
    /**
     * finds all the songs that contains a given string in
     * the title.
     * @param s the string or substring of a song title.
     */
    LinkedList<Song> findSong(String s) throws RemoteException;
    /**
     * finds all the songs that have as author that contains
     * a given string, while the year has to be precisely stated.
     * (e.g: 2004, not 4).
     * @param author the song's author
     * @param year the song's year
     */
    LinkedList<Song> findSong(String author, int year) throws RemoteException;
    /**
     * it returns a LoggedUser only and only if the user is not already logged and the creaentials are
     * correct.
     * @param u the singleton AbstractUser object present in each client.
     * @param uid the username.
     * @param pw the password.
     * @throws RemoteException
     * @throws AlreadyLoggedException
     * @throws SQLException
     * @throws WrongCredentialsException
     */
    LoggedUser login(LoggedUser u, String uid, String pw) throws RemoteException, AlreadyLoggedException, WrongCredentialsException, SQLException;

    /**
     * adds a user's feedback for a given song and a given emotion to
     * the database.
     * @param emotion the emotion involved.
     * @param user the user.
     * @param song the song involved.
     * @param score the score left by the user.
     * @param notes the notes left by the user.
     * @throws SQLException
     * @throws RemoteException
     * @throws NotLoggedException
     * @throws AlreadyLoggedException
     */
    void evaluateSong(Emotions emotion, LoggedUser user, Song song, String score, String notes) throws RemoteException, NotLoggedException, AlreadyValuedException, SQLException;

    /**
     * it logs the user out by returning a NotLoggedUser object.
     * @param u the singleton AbstractUser object present in each client.
     * @throws RemoteException
     * @throws NotLoggedException
     */
    LoggedUser logout(LoggedUser u) throws RemoteException, NotLoggedException;

    /**
     * gets a LoggedUser object's playlists from the database.
     * It is used in the getLoggedUser() method.
     * @param fn the user's first name.
     * @param ln the user's last name.
     * @param FC the user's fiscal code.
     * @param addr the user's address.
     * @param email the user's email.
     * @param uid the user's username.
     * @param pwd the user's password.
     * @throws AlreadyLoggedException
     * @throws RemoteException
     */
    void registerUser(String fn, String ln, String FC, String addr, String email, String uid, String pwd) throws AlreadyRegisteredException, RemoteException;

    /**
     * gets a Feedback object related to a given song.
     * A feedback represents a summary of the feedbacks given by
     * the users for each emotion related to the given song.
     * @param song the song involved.
     * @throws SQLException
     * @throws NoFeedbackException
     * @throws RemoteException
     * @return a Feedback object.
     */
    Feedback getFeedback(Song song) throws SQLException, NoFeedbackException, RemoteException;

    /**
     * deletes a user's feedback for a given song and a given emotion to
     * the database.
     * @param emotion the emotion involved.
     * @param user the user.
     * @param song the song involved.
     * @throws SQLException
     * @throws NoFeedbackException
     * @throws RemoteException
     */
    void deleteFeedback(Emotions emotion, LoggedUser user, Song song) throws SQLException, NoFeedbackException, RemoteException;

    /**
     * creates an empty playlist for a user.
     * @param pl_name the playlist's name.
     * @param user the user.
     * @throws SQLException
     * @throws playlistException
     * @throws RemoteException
     */
    Playlist createPlaylist(String pl_name, LoggedUser user) throws SQLException, playlistException, RemoteException;

    /**
     * removes a song from a given playlist of the user.
     * @param pl_name the playlist's name.
     * @param user the user.
     * @param song the song involved.
     * @throws SQLException
     * @throws playlistException
     * @throws RemoteException
     */
    void removeSongFromPlaylist(String pl_name, Song song, LoggedUser user) throws SQLException, playlistException, RemoteException;

    /**
     * renames a given playlist of the user.
     * @param curr_pl_name the playlist's current name.
     * @param new_pl_name the playlist's new name.
     * @param user the user.
     * @throws SQLException
     * @throws playlistException
     * @throws RemoteException
     */
    void renamePlaylist(String curr_pl_name, String new_pl_name, LoggedUser user) throws SQLException, playlistException, RemoteException;

    /**
     * deletes a given playlist.
     * @param pl_name the playlist's name.
     * @param user the user.
     * @throws SQLException
     * @throws playlistException
     * @throws RemoteException
     */
    void deletePlaylist(String pl_name, LoggedUser user) throws SQLException, playlistException, RemoteException;

    /**
     * adds a song to a given playlist.
     * @param pl_name the playlist's name.
     * @param song the song involved.
     * @param user the user.
     * @throws SQLException
     * @throws playlistException
     * @throws RemoteException
     */
    void addSongToPlaylist(String pl_name, Song song, LoggedUser user) throws SQLException, playlistException, RemoteException;

    /**
     * modifies a user's given data field with a new value.
     * @param user the user.
     * @param param_name the parameter that has to be modified.
     * @param param_value the new value to assign.
     * @throws SQLException
     * @throws UserException
     * @throws RemoteException
     */
    void modifyUserParam(LoggedUser user, String param_name, String param_value) throws SQLException, UserException, RemoteException;

    /**
     * deletes a user from the system.
     * @param user the user.
     * @throws SQLException
     * @throws UserException
     * @throws RemoteException
     */
    void deleteUser(LoggedUser user) throws SQLException, UserException, RemoteException;
}
