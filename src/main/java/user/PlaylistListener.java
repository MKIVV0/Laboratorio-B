/**
 * @author Zhang Ying Huang, Matricola 746483, CO
 * @author Alessandro Di Lorenzo, Matricola 733052, CO
 */

package user;

import common.playlistException;

import java.rmi.RemoteException;
import java.sql.SQLException;

/**
 * Interface for Frame - PlaylistPanel comunication
 */
public interface PlaylistListener {
    /**
     * Create a new playlist with the name given.
     */
    void creaPlaylist(String plName) throws SQLException, playlistException, RemoteException;
    /**
     * Delete the playlist specified by the name given.
     */
    void eliminaPlaylist(String plName) throws SQLException, playlistException, RemoteException;
    /**
     * Open the palylist specified by the name given.
     */
    void apriPlaylist(String plName);
    /**
     * Rename the playlist specified by the first argument with the name passed with the second one.
     */
    void rinominaPlaylist(String vecchioNome, String nuovoNome) throws SQLException, playlistException, RemoteException;
}
