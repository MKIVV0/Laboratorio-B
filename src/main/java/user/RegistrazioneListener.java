package user;

import common.UserException;

import java.rmi.RemoteException;
import java.util.EventListener;

public interface RegistrazioneListener extends EventListener {
    void datiForniti(RegistrazioneEvent re) throws UserException, RemoteException;
}
