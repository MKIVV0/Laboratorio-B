package user;

import common.NoFeedbackException;
import common.Song;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.LinkedList;

public interface SongListener {
    LinkedList<String> guardaFeedback(Song song) throws NoFeedbackException, SQLException, RemoteException;
    void addSong(Song song);
}
