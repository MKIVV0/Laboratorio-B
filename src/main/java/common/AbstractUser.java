/**
 * @author Zhang Ying Huang, Matricola 746483, CO
 * @author Alessandro Di Lorenzo, Matricola 733052, CO
 */

package common;

import java.io.Serializable;

/**
 * abstract class representing a generic user.
 * Its purpose is to allow the singleton pattern, by instantiating
 * a single object which type is dynamically changed during
 * the execution.
 */
public abstract class AbstractUser implements Serializable {

    public AbstractUser(){
    }

}
