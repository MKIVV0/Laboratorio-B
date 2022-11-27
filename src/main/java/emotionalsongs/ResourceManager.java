package emotionalsongs;


import common.*;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class ResourceManager extends UnicastRemoteObject implements ResourceManagerInterface {

    private HashMap<String, Song> repoSong;
   // private LinkedList<LoggedUserInterf> clients;

    public ResourceManager() throws RemoteException {
        super();
       // this.clients = new LinkedList<>();
        this.repoSong = new HashMap<>();
    }

    @Override
    public Song getSong(String s) {
        return new Song(1, "id brano", "autore", "titolo");
    }

    @Override
    public AbstractUser login(AbstractUser u, String uid, String pw) throws RemoteException, AlreadyLoggedException {
        //CONTROLLO CREDENZIALI SU DB
        if(u instanceof LoggedUser)
            throw new AlreadyLoggedException();
        if(!uid.equals("ale") && !pw.equals("pw"))
            return null;
        else return new LoggedUser(uid, pw);
    }

   @Override
    public void valutaBrano(AbstractUser u, Song s, int score) throws RemoteException {
        if(u instanceof NotLoggedUser){
            System.out.println("non sei loggatooooo non puoi valutare");
            return;
        }
        //salva su db (prima però fai i check)
        System.out.println("Brano " + s + " \nvalutato: " + score);
    }

    @Override
    public AbstractUser logout(AbstractUser u) throws RemoteException {
        if(u instanceof LoggedUser) {
            //salvataggio dati u su db
        }
        return new NotLoggedUser();
    }

    public static void main(String[] args) throws Exception {
        ResourceManager g = new ResourceManager();
        Registry r = LocateRegistry.createRegistry(11000);
        r.rebind("Gestore", g);
        System.err.println("server started");
    }

}
