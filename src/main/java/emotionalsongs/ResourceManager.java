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
        dbES.getInstance(server,database,port,user,password);
        songRepo = dbES.importAllSongs();
    }

    @Override
    public LinkedList<Song> findSong(String title){
        LinkedList<Song> tmp = new LinkedList<>();
        for(Song b: songRepo.values()) {
            if(b.getTitle().toLowerCase().contains(title.toLowerCase()))
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
    public AbstractUser login(AbstractUser u, String uid, String pw) throws RemoteException, AlreadyLoggedException, SQLException, WrongCredentialsException {
        if (u instanceof LoggedUser)
            throw new AlreadyLoggedException();

//        return dbES.getLoggedUser(uid, pw);

        //debug
        if(!uid.equals("ale") || !pw.equals("pw"))
            throw new WrongCredentialsException("cred sbagliate");
        return new LoggedUser(uid, pw);

    }

    @Override
    public AbstractUser logout(AbstractUser u) throws RemoteException, NotLoggedException {
        if (u instanceof NotLoggedUser)
            throw new NotLoggedException();

        //SALVATAGGIO DATI SU DB

        return new NotLoggedUser();
    }

    // MODIFICATA IMPLEMENTAZIONE DA TEO
    @Override
    public void evaluateSong(Emotions emotion, AbstractUser user, Song song, String score, String notes) throws RemoteException, NotLoggedException, AlreadyValuedException, SQLException {
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
    public String getOwnFeedback(String user_id, Emotions emotion_name, Song song) throws RemoteException, NoFeedbackException, SQLException {
        if (song == null)
            throw new NullPointerException();

        //Feedback tmp = dbES.GET_FEEDBACK_OF_THE_SONG(s);

        //if(tmp == null)
           //throw new NoFeedbackException("Ancora nessun feedback per questo brano!");

        String feedback = dbES.getFeedback(user_id, emotion_name, song.getId());
        if (feedback == null)
            throw new NoFeedbackException("You haven't left any feedback for this song for the parameter you inserted!");

        //return tmp;

        return feedback;//TMCH
    }


}
