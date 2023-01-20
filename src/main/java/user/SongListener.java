/**
 * @author Zhang Ying Huang, Matricola 746483, CO
 * @author Alessandro Di Lorenzo, Matricola 733052, CO
 */

package user;

import common.*;

import java.rmi.RemoteException;
import java.sql.SQLException;

/**
 * Interface for Frame - ObjectAreaPanel comunication
 */
public interface SongListener {
    /**
     * Return the feedback related to the song passed by argument.
     * @param song
     * @throws NoFeedbackException
     * @throws SQLException
     * @throws RemoteException
     */
    Feedback guardaFeedback(Song song) throws NoFeedbackException, SQLException, RemoteException;
    /**
     * Add the song passed by argument to a playlist.
     * @param song
     * @throws SQLException
     * @throws playlistException
     * @throws RemoteException
     */
    void addSong(Song song) throws SQLException, playlistException, RemoteException;
    /**
     * Delete the song passed by argument from a playlist.
     * @param song
     * @throws SQLException
     * @throws playlistException
     * @throws RemoteException
     */
    void removeSong(Song song) throws SQLException, playlistException, RemoteException;
    /**
     * Evaluates a song with the feedback passed.
     * @param ff Feedback Form
     * @throws UserException
     * @throws SQLException
     * @throws AlreadyValuedException
     * @throws RemoteException
     */
    void valutaSong(FeedbackForm ff) throws UserException, SQLException, AlreadyValuedException, RemoteException;
}