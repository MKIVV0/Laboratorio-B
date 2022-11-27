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

    public ClientES() throws RemoteException {
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

    private void start() {
        io.start();
    }

    @Override
    public boolean logged() throws RemoteException {
        return logged;
    }

    @Override
    public void run() {

        //tutto debug

        int i = 0;
        while (i++ < 3)
            try {
                System.out.println("\n\nCICLO NUMERO " + i + "\n\n");
                Song song = g.getSong("stringa");
                // System.out.println("brano prima di login: \n" + song); //visibile


                if (i - 1 != 0)
                    try {
                        this.user = g.login(user, "ale", "pw");
                    } catch (AlreadyLoggedException e) {
                        System.out.println("gia loggatooooooooooo!");
                    } catch (WrongCredentialsException ex) {
                        System.out.println("credenziali sbagliateeeeee!");
                    }


                if (user instanceof LoggedUser)
                    logged = true;
                else
                    logged = false;

                System.out.println("\nloggato = " + logged());


                //song = g.getSong("stringa");
                //System.out.println("\nbrano dopo login: \n" + song); //visibile


                try {
                    g.valutaBrano(user, song, 4);
                } catch (NotLoggedException e) {
                    System.out.println("non sei loggatooooo non puoi valutare");
                }

                if (i - 1 % 2 == 0)
                    try {
                        user = g.logout(user);
                    } catch (NotLoggedException e) {
                        System.out.println("sei gia sloggatoooooooo");
                    }


                if (user instanceof LoggedUser)
                    logged = true;
                else
                    logged = false;

                System.out.println("\nloggato = " + logged());

                //System.err.println("\nterminato client\n");

            } catch (Exception e) {
            }
    }

}
