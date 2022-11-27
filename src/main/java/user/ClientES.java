package user;

import emotionalsongs.ResourceManager;
import emotionalsongs.ResourceManagerInterface;

import java.io.*;
import java.net.*;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ClientES extends UnicastRemoteObject implements ClientInterf {

    public ResourceManagerInterface g;
    private String name;

    public ClientES(String name) throws RemoteException{
        this.name = name;
    }

    @Override
    public String test() throws RemoteException {
        return name;
    }

    public static void main(String[] args) throws Exception {
        ClientES c = new ClientES("sono il client di prova");
        Registry r = LocateRegistry.getRegistry(11000);
        //Registry r2 = LocateRegistry.createRegistry(11001);
        c.g = (ResourceManagerInterface) r.lookup("Gestore");
        c.g.readMsg(c);
    }


}
