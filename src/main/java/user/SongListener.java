package user;

import common.NoFeedbackException;
import common.Playlist;
import common.Song;
import common.playlistException;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.LinkedList;

public interface SongListener {
    LinkedList<String> guardaFeedback(Song song) throws NoFeedbackException, SQLException, RemoteException;
    void addSong(Song song) throws SQLException, playlistException, RemoteException;
    void removeSong(Song song) throws SQLException, playlistException, RemoteException;
}
