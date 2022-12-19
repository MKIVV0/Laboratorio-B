package user;

import common.AlreadyRegisteredException;

import java.rmi.RemoteException;
import java.util.EventListener;

public interface RegistrazioneListener extends EventListener {
    void datiForniti(RegistrazioneEvent re) throws AlreadyRegisteredException, RemoteException;
}
