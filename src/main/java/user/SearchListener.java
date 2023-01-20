/**
 * @author Zhang Ying Huang, Matricola 746483, CO
 * @author Alessandro Di Lorenzo, Matricola 733052, CO
 */

package user;

import java.util.EventListener;

/**
 * Interface for Frame - SearchPanel comunication
 */
public interface SearchListener extends EventListener {
    /**
     * Search a song in the repository.
     * @param ce Search event
     */
    void cercaEventListener(SearchEvent ce);
}
