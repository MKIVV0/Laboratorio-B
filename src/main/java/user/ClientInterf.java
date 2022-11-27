package user;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterf extends Remote {
    String test() throws RemoteException;
}
