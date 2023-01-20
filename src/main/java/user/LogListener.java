/**
 * @author Zhang Ying Huang, Matricola 746483, CO
 * @author Alessandro Di Lorenzo, Matricola 733052, CO
 */

package user;

import common.UserException;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.EventListener;

/**
 * Interface for Frame - ToolBar comunication
 */
public interface LogListener extends EventListener {
    /**
     * Method for the user's login.
     */
    void credenzialiFornite(LogEvent le) throws UserException, SQLException, RemoteException;
    /**
     * Logout method.
     */
    void logout() throws UserException, RemoteException;
}
