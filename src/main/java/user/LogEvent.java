/**
 * @author Zhang Ying Huang, Matricola 746483, CO
 * @author Alessandro Di Lorenzo, Matricola 733052, CO
 */

package user;

import java.util.EventObject;

/**
 * This class is used to provide the user's login data to the resource manager
 */
public class LogEvent extends EventObject {
    /**
     * The username given
     */
    private String username;
    /**
     * The password given.
     */
    private String password;

    /**
     * LogEvent Constructor
     * @param source
     * @param username
     * @param password
     */
    public LogEvent(Object source, String username, String password) {
        super(source);
        this.username = username;
        this.password = password;
    }
    /**
     * Username getter
     */
    public String getUsername() {
        return username;
    }
    /**
     * Getter
     */
    public String getPassword() {
        return password;
    }

}
