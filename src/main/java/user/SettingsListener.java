/**
 * @author Zhang Ying Huang, Matricola 746483, CO
 * @author Alessandro Di Lorenzo, Matricola 733052, CO
 */

package user;

import common.UserException;

import java.rmi.RemoteException;
import java.sql.SQLException;

/**
 * Interface for Frame - ToolBar comunication
 */
public interface SettingsListener {
    /**
     * Change the username of the logged user.
     */
    void modifyUsername(String nuovo) throws SQLException, UserException, RemoteException;
    /**
     * Change the password of the logged user.
     */
    void modifyPassword(String nuovo) throws SQLException, UserException, RemoteException;
}
