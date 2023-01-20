/**
 * @author Zhang Ying Huang, Matricola 746483, CO
 * @author Alessandro Di Lorenzo, Matricola 733052, CO
 */

package user;

import java.util.EventObject;

/**
 * This class is used to provide the user's registration data to the resource manager
 */
public class RegistrationEvent extends EventObject {
    /**
     * The information for the user's registration.
     */
    String fn, ln, FC, addr, em, uid, pw;
    /**
     * RegistrationEvent constructor
     * @param source
     * @param fn the user's first name.
     * @param ln the user's last name.
     * @param FC the user's fiscal code.
     * @param addr the user's address.
     * @param em the user's email.
     * @param uid the user's username.
     * @param pw the user's password.
     */
    public RegistrationEvent(Object source, String fn, String ln, String FC, String addr, String em, String uid, String pw) {
        super(source);
        this.fn = fn;
        this.ln = ln;
        this.FC = FC;
        this.addr = addr;
        this.em = em;
        this.uid = uid;
        this.pw = pw;
    }

}
