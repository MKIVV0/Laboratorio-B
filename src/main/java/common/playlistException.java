/**
 * @author Zhang Ying Huang, Matricola 746483, CO
 * @author Alessandro Di Lorenzo, Matricola 733052, CO
 */
package common;

/**
 * This class represents the exception thrown if an abnormal
 * condition occurs while handling playlists.
 */
public class playlistException extends Exception {
    public playlistException(String msg) {
        super(msg);
    }
}
