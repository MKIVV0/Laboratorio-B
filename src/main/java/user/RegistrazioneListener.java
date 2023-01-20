/**
 * @author Zhang Ying Huang, Matricola 746483, CO
 * @author Alessandro Di Lorenzo, Matricola 733052, CO
 */

package user;

import common.UserException;

import java.rmi.RemoteException;
import java.util.EventListener;

/**
 * Interface for Frame - ToolBar comunication
 */
public interface RegistrazioneListener extends EventListener {
    void datiForniti(RegistrazioneEvent re) throws UserException, RemoteException;
}
