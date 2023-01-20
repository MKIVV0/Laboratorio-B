/**
 * @author Zhang Ying Huang, Matricola 746483, CO
 * @author Alessandro Di Lorenzo, Matricola 733052, CO
 */

package user;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * The main user's class
 */
public class ClientES { // CLIENTES

    /**
     * The main method.
     */
    public static void main(String[] args) throws NotBoundException, RemoteException {

        new Frame();

    }

}
