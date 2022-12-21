package emotionalsongs;

import common.*;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;

public class ResourceManager extends UnicastRemoteObject implements ResourceManagerInterface {

    private static HashMap<String, Song> songRepo;

    public ResourceManager(String server, String database, String port, String user, String password) throws IOException, SQLException {
        super();
        dbES.getInstance(server, database, port, user, password);
        songRepo = dbES.importAllSongs();
    }

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
     * metodo di login che restituisce un logged user se e solo se l'utente (parametro u) non è già loggato
     * e le credenziali (parametri uid e pw) sono corrette.
     * Uso del DB per verificare le credenziali
     * in caso di successo, alla creazione del LoggedUser è necessario interagire nuovamente col DB al fine di
     * ricostruire correttamente i suoi campi (creazione delle sue playlists ecc..)
     */
    @Override
    public synchronized AbstractUser login(AbstractUser user, String uid, String pw) throws RemoteException, AlreadyLoggedException, SQLException, WrongCredentialsException {
        if (user instanceof LoggedUser)
            throw new AlreadyLoggedException();

        return dbES.getLoggedUser(uid, pw);

        //debug
        /*
        if(!uid.equals("ale") || !pw.equals("pw"))
            throw new WrongCredentialsException("cred sbagliate");
        return new LoggedUser(uid, pw);*/

    }

    @Override
    public synchronized AbstractUser logout(AbstractUser user) throws RemoteException, NotLoggedException {
        if (user instanceof NotLoggedUser)
            throw new NotLoggedException();

        //SALVATAGGIO DATI SU DB

        return new NotLoggedUser();
    }

    // MODIFICATA IMPLEMENTAZIONE DA TEO
    @Override
    public synchronized void evaluateSong(Emotions emotion, AbstractUser user, Song song, String score, String notes) throws RemoteException, NotLoggedException, AlreadyValuedException, SQLException {
        if (user instanceof NotLoggedUser)
            throw new NotLoggedException();

        //Feedback tmp = new FeedBack(u, s, e, score)

        //IF (TMP ESISTE GIA SU DB)
        //throw new AlreadyValuedException("Errore: hai gia lasciato un feedback per questo brano!");
        boolean feedbackAdded = dbES.addFeedback(emotion, ((LoggedUser) user).getId(), song.getId(), score, notes);
        if (!feedbackAdded)
            throw new AlreadyValuedException("Error! You already left a feedback for this song.");
        else
            System.out.println("Thank you for your feedback!");
        //ELSE{
        //SALVA TMP SU DB
        //System.out.println("Grazie per il feedback!");
        //}

        //debug
        System.out.println("Evaluated " + song + " \nsong: " + emotion + " " + score + " " + notes + "\n");
    }

    // MODIFICATO DA TEO - DA DISCUTERE L'IMPLMENTAZIONE, VISTO L'OVERLOADING DEL METODO getFeedback di dbES
    @Override
    public synchronized String getFeedback(AbstractUser user, Emotions emotion_name, Song song) throws RemoteException, NoFeedbackException, SQLException {
        if (song == null)
            throw new NullPointerException();

        //Feedback tmp = dbES.GET_FEEDBACK_OF_THE_SONG(s);

        //if(tmp == null)
        //throw new NoFeedbackException("Ancora nessun feedback per questo brano!");

        String feedback = dbES.getFeedback(((LoggedUser)user).getId(), emotion_name, song.getId());
        if (feedback == null)
            throw new NoFeedbackException("You haven't left any feedback for this song for the parameter you inserted!");

        //return tmp;

        return feedback;//TMCH
    }

    public synchronized LinkedList<String> getFeedback(Song song) throws SQLException, NoFeedbackException, RemoteException {
        if (song == null)
            throw new NullPointerException();

        LinkedList<String> feedback = dbES.getFeedback(song.getId());
        if (feedback == null)
            throw new NoFeedbackException("No feedbacks present for this song!");

        return feedback;
    }

    public synchronized LinkedList<String> getFeedback(Song song, AbstractUser user) throws SQLException, NoFeedbackException, RemoteException {
        if (song == null)
            throw new NullPointerException();

        LinkedList<String> feedback = dbES.getFeedback(song.getId(), ((LoggedUser) user).getId());
        if (feedback == null)
            throw new NoFeedbackException("No feedbacks present for this song under the user" + ((LoggedUser) user).getId() + " !");

        return feedback;
    }

    public synchronized void registerUser(String fn, String ln, String FC, String addr, String email, String uid, String pwd) throws AlreadyRegisteredException, RemoteException {
        if (!dbES.registerUser(fn, ln, FC, addr, email, uid, pwd))
            throw new AlreadyRegisteredException("The user with these data already exists!");
//        else System.out.println("User registered successfully!");
    }

    public synchronized void deleteFeedback(Emotions emotion, AbstractUser user, Song song) throws SQLException, NoFeedbackException, RemoteException {
        if (!dbES.deleteFeedback(emotion, ((LoggedUser) user).getId(), song.getId()))
            throw new NoFeedbackException("This song with these parameters doesn't have any feedbacks!");
        else
            System.out.println("Thank you for your feedback!");
    }

    public synchronized void modifyFeedback(Emotions emotion, AbstractUser user, Song song, String param_name, String param_value) throws SQLException, NoFeedbackException, RemoteException {
        if (!dbES.modifyFeedback(emotion, ((LoggedUser) user).getId(), song.getId(), param_name, param_value))
            throw new NoFeedbackException("This song with these parameters doesn't have any feedbacks!");
        else
            System.out.println("Feedback modified successfully!");
    }

    // La creazione di una playlist comporta l'inserimento di almeno una canzone
    public synchronized Playlist createPlaylist(String pl_name, AbstractUser user) throws SQLException, playlistException, RemoteException {
        if (!dbES.createPlaylist(pl_name, ((LoggedUser) user).getId()))
            throw new playlistException("This playlist already exists!");
        else
            return new Playlist(((LoggedUser) user).getId(), pl_name);
    }

    public synchronized void removeSongFromPlaylist(String pl_name, Song song, AbstractUser user) throws SQLException, playlistException, RemoteException {
        if (!dbES.removeSongFromPlaylist(pl_name, song.getId(), ((LoggedUser) user).getId()))
            throw new playlistException("This playlist doesn't exist!");
        else
            System.out.println("Song removed successfully!");
    }

    public synchronized void renamePlaylist(String curr_pl_name, String new_pl_name, AbstractUser user) throws SQLException, playlistException, RemoteException {
        if (!dbES.renamePlaylist(curr_pl_name, new_pl_name, ((LoggedUser) user).getId()))
            throw new playlistException("This playlist doesn't exist!");
        else
            System.out.println("Playlist renamed successfully!");
    }

    public synchronized void deletePlaylist(String pl_name, AbstractUser user) throws SQLException, playlistException, RemoteException {
        if (!dbES.deletePlaylist(pl_name, ((LoggedUser) user).getId()))
            throw new playlistException("This playlist doesn't exist!");
        else
            System.out.println("Playlist deleted successfully!");
    }

    public synchronized void addSongToPlaylist(String pl_name, Song song, AbstractUser user) throws SQLException, playlistException, RemoteException {
        if (!dbES.addSongToPlaylist(pl_name, song.getId(), ((LoggedUser) user).getId()))
            throw new playlistException("This playlist doesn't exist!");
        else
            System.out.println("Song added successfully!");
    }

    public synchronized void modifyUserParam(AbstractUser user, String param_name, String param_value) throws SQLException, UserException, RemoteException {
        if (!dbES.modifyUserParam(((LoggedUser) user).getId(), param_name, param_value))
            throw new UserException("This user doesn't exist!");
        else
            System.out.println("User parameters modified successfully!");
    }

    public synchronized void deleteUser(AbstractUser user) throws SQLException, UserException, RemoteException {
        if (!dbES.deleteUser(((LoggedUser) user).getId()))
            throw new UserException("This user doesn't exist!");
        else
            System.out.println("User deleted successfully!");
    }
}
