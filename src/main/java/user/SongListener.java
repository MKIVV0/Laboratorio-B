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
     */
    Feedback guardaFeedback(Song song) throws NoFeedbackException, SQLException, RemoteException;
    /**
     * Add the song passed by argument to a playlist.
     */
    void addSong(Song song) throws SQLException, playlistException, RemoteException;
    /**
     * Delete the song passed by argument from a playlist.
     */
    void removeSong(Song song) throws SQLException, playlistException, RemoteException;
    /**
     * Evaluates a song with the feedback passed.
     */
    void valutaSong(FeedbackForm ff) throws UserException, SQLException, AlreadyValuedException, RemoteException;
}