/**
 * @author Zhang Ying Huang, Matricola 746483, CO
 * @author Alessandro Di Lorenzo, Matricola 733052, CO
 */
package common;

/**
 * This class represents the exception thrown if a client
 * tries to log with wrong credentials.
 */
public class WrongCredentialsException extends Exception {
    public WrongCredentialsException(String msg) {
        super(msg);
    }
}
