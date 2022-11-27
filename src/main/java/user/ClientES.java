package user;


import common.*;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ClientES extends UnicastRemoteObject implements ClientInterf, Runnable {

    public ResourceManagerInterface g;
    private AbstractUser user;
    private boolean logged;
    private Thread io;

    public ClientES() throws RemoteException{
        user = new NotLoggedUser();
        logged = false;
        io = new Thread(this);
    }

    public static void main(String[] args) throws Exception {
        ClientES c = new ClientES();
        Registry r = LocateRegistry.getRegistry(11000);
        c.g = (ResourceManagerInterface) r.lookup("Gestore");

        c.start();


    }


    @Override
    public boolean logged() throws RemoteException {
        return logged;
    }

    @Override
    public void run() {
        try {
            Song song = g.getSong("stringa");
            System.out.println("brano prima di login: \n" + song); //visibile


            if(user instanceof NotLoggedUser)
               // this.loggedUser = g.login(this, "ale", "pw");
                this.user = g.login("ale", "pw");

            if (user instanceof LoggedUser)
                logged = true;
            else
                logged = false;

            System.out.println("\nloggato = " + logged());


            song = g.getSong("stringa");
            System.out.println("\nbrano dopo login: \n" + song); //visibile


            ((LoggedUser)user).valutaBrano(song, 4);

            user = g.logout(user);

            if (user instanceof LoggedUser)
                logged = true;
            else
                logged = false;

            System.out.println("\nloggato = " + logged());

            System.err.println("\nterminato client\n");

        }catch (Exception e){}
    }

    private void start(){
        io.start();
    }
}
