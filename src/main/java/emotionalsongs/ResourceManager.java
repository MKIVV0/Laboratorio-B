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
    public AbstractUser login(AbstractUser u, String uid, String pw) throws RemoteException, AlreadyLoggedException, CredentialUncorrectExcepion, SQLException {
        if (u instanceof LoggedUser)
            throw new AlreadyLoggedException();

        //CONTROLLO CREDENZIALI SU DB

        if (dbES.verifyUserExistence(uid, pw)) //se credenziali NON sono ok
            throw new CredentialUncorrectExcepion();

        else {

            //CARICAMENTO DATI UTENTI DAL DB

            return new LoggedUser(uid, pw);
        }
    }

    @Override
    public void valutaBrano(AbstractUser u, Song s, int score) throws RemoteException, NotLoggedException {
        if (u instanceof NotLoggedUser)
            throw new NotLoggedException();

        //SALVA SU DB LA VALUTAZIONE, (RICORDATI DI FARE IL CHECK PERO')

        //debug
        System.out.println("Brano " + s + " \nvalutato: " + score);
    }

    @Override
    public AbstractUser logout(AbstractUser u) throws RemoteException, NotLoggedException {
        if (u instanceof NotLoggedUser)
            throw new NotLoggedException();

        //SALVATAGGIO DATI SU DB

        return new NotLoggedUser();
    }

    public static void main(String[] args) throws Exception {
        ResourceManager g = new ResourceManager();
        Registry r = LocateRegistry.createRegistry(11000);
        r.rebind("Gestore", g);
        System.err.println("server started");
    }

}
