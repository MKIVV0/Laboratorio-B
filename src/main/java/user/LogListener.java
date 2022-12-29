package user;

import common.UserException;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.EventListener;

public interface LogListener extends EventListener {
    void credenzialiFornite(LogEvent le) throws UserException, SQLException, RemoteException;
    void logout() throws UserException, RemoteException;
}
