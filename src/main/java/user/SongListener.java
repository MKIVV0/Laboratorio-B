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
    Feedback guardaFeedback(Song song) throws NoFeedbackException, SQLException, RemoteException;
    void addSong(Song song) throws SQLException, playlistException, RemoteException;
    void removeSong(Song song) throws SQLException, playlistException, RemoteException;
    void valutaSong(FeedbackForm ff) throws UserException, SQLException, AlreadyValuedException, RemoteException;
}