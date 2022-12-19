package user;

import common.AlreadyLoggedException;
import common.NotLoggedException;
import common.WrongCredentialsException;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.EventListener;

public interface LogListener extends EventListener { //per il tasto login
    void credenzialiFornite(LogEvent le) throws AlreadyLoggedException, SQLException, RemoteException, WrongCredentialsException;
    void logout() throws NotLoggedException, RemoteException;
}
