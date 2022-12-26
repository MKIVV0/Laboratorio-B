package user;

import common.UserException;

import java.rmi.RemoteException;
import java.sql.SQLException;

public interface SettingsListener {
    void modifyUsername(String nuovo) throws SQLException, UserException, RemoteException;
}
