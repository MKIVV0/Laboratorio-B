/**
 * @author Zhang Ying Huang, Matricola 746483, CO
 * @author Alessandro Di Lorenzo, Matricola 733052, CO
 */
package common;

/**
 * This class represents the exception thrown if a client
 * tries to register with credentials that are already registered.
 */
public class AlreadyRegisteredException extends Exception {
    /**
     * Exception constructor.
     * @param message
     */
    public AlreadyRegisteredException(String message) {
        super(message);
    }
}
