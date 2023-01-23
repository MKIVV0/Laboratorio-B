/**
 * @author Zhang Ying Huang, Matricola 746483, CO
 * @author Alessandro Di Lorenzo, Matricola 733052, CO
 */

package emotionalsongs;

import common.*;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
/**
 * this class represents the bridge between the program and the
 * functions that interacts with the dbms.
 */
public class ResourceManager extends UnicastRemoteObject implements ResourceManagerInterface {
    /**
     * this hashmap contains all the songs retrieved from
     * the database. It is, therefore, the song repository.
     */
    private static HashMap<String, Song> songRepo;
    /**
     * this hashmap contains all the registered users
     * retrieved from the database.
     */
    private static HashMap<String, LoggedUser> users;

    /**
     * gets the single dbES instance and initializes the
     * hashmaps.
     * A singleton pattern is used, in order to maintain a centralized communication structure.
     * @param server server's name.
     * @param port server's port on which the database service is available.
     * @param user database's userid credential.
     * @param password database's password credential.
     * @throws SQLException
     * @throws IOException
     */
    public ResourceManager(String server, String port, String user, String password) throws IOException, SQLException {
        super();
        dbES.getInstance(server, port, user, password);
        songRepo = dbES.importAllSongs();
        users = new HashMap<>();
    }

    /**
     * finds all the songs that contains a given string in
     * the title.
     * @param title the string or substring of a song title.
     * @return a list of all the songs that are found.
     */
    @Override
    public synchronized LinkedList<Song> findSong(String title) {
        LinkedList<Song> tmp = new LinkedList<>();
        for (Song b : songRepo.values()) {
            if (b.getTitle().toLowerCase().contains(title.toLowerCase()))
                tmp.add(b);
        }
        return tmp;
    }

    /**
     * finds all the songs that have as author that contains
     * a given string, while the year has to be precisely stated.
     * (e.g: 2004, not 4).
     * @param author the song's author
     * @param year the song's year
     * @return a list of all the songs that are found.
     */
    @Override
    public synchronized LinkedList<Song> findSong(String author, int year){
        LinkedList<Song> tmp = new LinkedList<>();
        for(Song b: songRepo.values()) {
            if(b.getAuthor().toLowerCase().contains(author.toLowerCase()) && b.getYear() == year)
                tmp.add(b);
        }
        return tmp;
    }

    /**
     * it returns a LoggedUser only and only if the user is not already logged and the creaentials are
     * correct.
     * @param user the singleton AbstractUser object present in each client.
     * @param uid the username.
     * @param pw the password.
     * @throws RemoteException
     * @throws UserException
     * @throws SQLException
     * @return an instance of a LoggedUser.
     */
    @Override
    public synchronized LoggedUser login(LoggedUser user, String uid, String pw) throws RemoteException, UserException, SQLException {
        if (user != null)
            throw new UserException("already logged!");
        LoggedUser u = dbES.getLoggedUser(uid, pw);
        if(u == null)
            throw new UserException("Wrong username or password");
        if(users.containsKey(u.getId()))
            throw new UserException("already logged!");
        else
            users.put(u.getId(), u);
        return u;
    }

    /**
     * it logs the user out by returning a NotLoggedUser object.
     * @param user the singleton AbstractUser object present in each client.
     * @throws RemoteException
     * @throws UserException
     * @return null, which stands for a not logged user.
     */
    @Override
    public synchronized LoggedUser logout(LoggedUser user) throws RemoteException, UserException {
        if (user == null)
            throw new UserException("already NOT logged!");
        users.remove(user.getId());
        return null;
    }

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
     * @throws UserException
     */
    @Override
    public synchronized void evaluateSong(Emotions emotion, LoggedUser user, Song song, String score, String notes) throws RemoteException, UserException, AlreadyValuedException, SQLException {
        if (user == null)
            throw new UserException("NOT logged!");
        boolean feedbackAdded = dbES.addFeedback(emotion, user.getId(), song.getId(), score, notes);
        if (!feedbackAdded)
            throw new AlreadyValuedException("Error! You already left a feedback for this song.");
    }


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
    public synchronized Feedback getFeedback(Song song) throws SQLException, NoFeedbackException, RemoteException {
        if (song == null)
            throw new NullPointerException();
        Feedback feedback = dbES.getFeedback(song.getId());
        if (feedback == null)
            throw new NoFeedbackException("No feedbacks present for this song!");
        return feedback;
    }


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
     * @throws UserException
     * @throws RemoteException
     */
    public synchronized void registerUser(String fn, String ln, String FC, String addr, String email, String uid, String pwd) throws UserException, RemoteException {
        if (!dbES.registerUser(fn, ln, FC, addr, email, uid, pwd))
            throw new UserException("The user with these data already exists!");
    }

    /**
     * deletes a user's feedback for a given song and a given emotion to
     * the database.
     * @param emotion the emotion involved.
     * @param user the user.
     * @param song the song involved.
     * @throws SQLException
     * @throws NoFeedbackException
     * @throws RemoteException
     * @return true if the involved table has been modified, false otherwise.
     */
    public synchronized void deleteFeedback(Emotions emotion, LoggedUser user, Song song) throws SQLException, NoFeedbackException, RemoteException {
        if (!dbES.deleteFeedback(emotion, user.getId(), song.getId()))
            throw new NoFeedbackException("This song with these parameters doesn't have any feedbacks!");
    }

    /**
     * creates an empty playlist for a user.
     * @param pl_name the playlist's name.
     * @param user the user.
     * @throws SQLException
     * @throws playlistException
     * @throws RemoteException
     * @return the newly created playlist.
     */
    public synchronized Playlist createPlaylist(String pl_name, LoggedUser user) throws SQLException, playlistException, RemoteException {
        if (!dbES.createPlaylist(pl_name, user.getId()))
            throw new playlistException("This playlist already exists!");
        else
            return new Playlist(pl_name);
    }

    /**
     * removes a song from a given playlist of the user.
     * @param pl_name the playlist's name.
     * @param user the user.
     * @param song the song involved.
     * @throws SQLException
     * @throws playlistException
     * @throws RemoteException
     */
    public synchronized void removeSongFromPlaylist(String pl_name, Song song, LoggedUser user) throws SQLException, playlistException, RemoteException {
        if (!dbES.removeSongFromPlaylist(pl_name, song.getId(), user.getId()))
            throw new playlistException("This playlist doesn't exist!");
    }

    /**
     * renames a given playlist of the user.
     * @param curr_pl_name the playlist's current name.
     * @param new_pl_name the playlist's new name.
     * @param user the user.
     * @throws SQLException
     * @throws playlistException
     * @throws RemoteException
     */
    public synchronized void renamePlaylist(String curr_pl_name, String new_pl_name, LoggedUser user) throws SQLException, playlistException, RemoteException {
        if (!dbES.renamePlaylist(curr_pl_name, new_pl_name, user.getId()))
            throw new playlistException("A playlist with this name already exists!");
    }

    /**
     * deletes a given playlist.
     * @param pl_name the playlist's name.
     * @param user the user.
     * @throws SQLException
     * @throws playlistException
     * @throws RemoteException
     */
    public synchronized void deletePlaylist(String pl_name, LoggedUser user) throws SQLException, playlistException, RemoteException {
        if (!dbES.deletePlaylist(pl_name, user.getId()))
            throw new playlistException("This playlist doesn't exist!");
    }

    /**
     * adds a song to a given playlist.
     * @param pl_name the playlist's name.
     * @param song the song involved.
     * @param user the user.
     * @throws SQLException
     * @throws playlistException
     * @throws RemoteException
     */
    public synchronized void addSongToPlaylist(String pl_name, Song song, LoggedUser user) throws SQLException, playlistException, RemoteException {
        if (!dbES.addSongToPlaylist(pl_name, song.getId(), user.getId()))
            throw new playlistException("You already have this song");
    }

    /**
     * modifies a user's given data field with a new value.
     * @param user the user.
     * @param param_name the parameter that has to be modified.
     * @param param_value the new value to assign.
     * @throws SQLException
     * @throws UserException
     * @throws RemoteException
     */
    public synchronized void modifyUserParam(LoggedUser user, String param_name, String param_value) throws SQLException, UserException, RemoteException {
        if (!dbES.modifyUserParam(user.getId(), param_name, param_value))
            throw new UserException("This user doesn't exist!");
        else {
            LoggedUser tmp = users.remove(user.getId());
            switch (param_name) {
                case "user_id" -> tmp.setUserID(param_value);
                case "password" -> tmp.setPassword(param_value);
            }
            users.put(tmp.getId(), tmp);
        }
    }

    /**
     * deletes a user from the system.
     * @param user the user.
     * @throws SQLException
     * @throws UserException
     * @throws RemoteException
     */
    public synchronized void deleteUser(LoggedUser user) throws SQLException, UserException, RemoteException {
        if (!dbES.deleteUser(user.getId()))
            throw new UserException("This user doesn't exist!");
    }
}
