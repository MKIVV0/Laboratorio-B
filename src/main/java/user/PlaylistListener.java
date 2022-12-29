package user;

import common.playlistException;

import java.rmi.RemoteException;
import java.sql.SQLException;

public interface PlaylistListener {
    void creaPlaylist(String plName) throws SQLException, playlistException, RemoteException;
    void eliminaPlaylist(String plName) throws SQLException, playlistException, RemoteException;
    void apriPlaylist(String plName);
    void rinominaPlaylist(String vecchioNome, String nuovoNome) throws SQLException, playlistException, RemoteException;
}
