package emotionalsongs;

import common.*;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;

public class ResourceManager extends UnicastRemoteObject implements ResourceManagerInterface {

    private static HashMap<String, Song> songRepo = new HashMap<>();

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

    @Override
    public void valutaBrano(AbstractUser u, Song s, Emotions e, int score) throws RemoteException, NotLoggedException, AlreadyValuedException {
        if (u instanceof NotLoggedUser)
            throw new NotLoggedException();

        //Feedback tmp = new FeedBack(u, s, e, score)

        //IF (TMP ESISTE GIA SU DB)
            //throw new AlreadyValuedException("Errore: hai gia lasciato un feedback per questo brano!");

        //ELSE{
            //SALVA TMP SU DB
            //System.out.println("Grazie per il feedback!");
        //}

        //debug
        System.out.println("Brano " + s + " \nvalutato: " + e + " " + score + "\n");
    }

    @Override
    public Feedback getFeedback(Song s) throws RemoteException, NoFeedbackException {
        if (s == null)
            throw new NullPointerException();

        //Feedback tmp = dbES.GET_FEEDBACK_OF_THE_SONG(s);

        //if(tmp == null)
           //throw new NoFeedbackException("Ancora nessun feedback per questo brano!");

        //return tmp;

        return null;//TMCH
    }



}
