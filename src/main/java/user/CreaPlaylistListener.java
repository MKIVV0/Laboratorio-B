package user;

import common.Playlist;
import common.playlistException;

import java.rmi.RemoteException;
import java.sql.SQLException;

public interface CreaPlaylistListener {
    void creaPlaylist(String plName) throws SQLException, playlistException, RemoteException;
}
