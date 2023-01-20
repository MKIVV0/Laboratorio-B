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

    String fn, ln, FC, addr, em, uid, pw;

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

    public String getFn() {
        return fn;
    }

    public void setFn(String fn) {
        this.fn = fn;
    }

    public String getLn() {
        return ln;
    }

    public void setLn(String ln) {
        this.ln = ln;
    }

    public String getFC() {
        return FC;
    }

    public void setFC(String FC) {
        this.FC = FC;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getEm() {
        return em;
    }

    public void setEm(String em) {
        this.em = em;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }
}
