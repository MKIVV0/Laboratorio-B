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
     * @param plName playlist's name
     * @throws SQLException
     * @throws playlistException
     * @throws RemoteException
     */
    void creaPlaylist(String plName) throws SQLException, playlistException, RemoteException;
    /**
     * Delete the playlist specified by the name given.
     * @param plName playlist's name
     * @throws SQLException
     * @throws playlistException
     * @throws RemoteException
     */
    void eliminaPlaylist(String plName) throws SQLException, playlistException, RemoteException;
    /**
     * Open the palylist specified by the name given.
     * @param plName playlist's name
     */
    void apriPlaylist(String plName);
    /**
     * Rename the playlist specified by the first argument with the name passed with the second one.
     * @param vecchioNome playlist's old name
     * @param nuovoNome playlist's new name
     * @throws SQLException
     * @throws playlistException
     * @throws RemoteException
     */
    void rinominaPlaylist(String vecchioNome, String nuovoNome) throws SQLException, playlistException, RemoteException;
}
