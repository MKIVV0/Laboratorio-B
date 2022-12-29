/**
 * @author Zhang Ying Huang, Matricola 746483, CO
 * @author Alessandro Di Lorenzo, Matricola 733052, CO
 */
package common;

/**
 * This class represents the exception thrown if a client
 * tries to valuate an emotion related to a song that has already
 * been valued.
 */
public class AlreadyValuedException extends Exception {
    public AlreadyValuedException(String message) {
        super(message);
    }
}
