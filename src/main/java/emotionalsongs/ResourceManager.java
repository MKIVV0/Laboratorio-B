package emotionalsongs;


import common.*;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.HashMap;

public class ResourceManager extends UnicastRemoteObject implements ResourceManagerInterface {

    private HashMap<String, Song> repoSong;

    public ResourceManager() throws RemoteException {
        super();
        this.repoSong = new HashMap<>();
    }

    @Override
    public Song getSong(String s) {
        //debug
        return new Song(1, "id brano", "autore", "titolo");
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

        return dbES.getLoggedUser(uid, pw);

        //debug
       /* if(!uid.equals("ale") || !pw.equals("pw"))
            throw new WrongCredentialsException("cred sbagliate");
        return new LoggedUser(uid, pw);*/

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

    public static void main(String[] args) throws Exception {
        ResourceManager g = new ResourceManager();
        Registry r = LocateRegistry.createRegistry(11000);
        r.rebind("Gestore", g);
        System.err.println("server started");
    }

}
