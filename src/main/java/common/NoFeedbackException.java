/**
 * @author Zhang Ying Huang, Matricola 746483, CO
 * @author Alessandro Di Lorenzo, Matricola 733052, CO
 */
package common;

/**
 * This class represents the exception thrown if there's no
 * feedback present for a given song.
 */
public class NoFeedbackException extends Exception {
    public NoFeedbackException(String message) {
        super(message);
    }
}
