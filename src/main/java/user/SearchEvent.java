/**
 * @author Zhang Ying Huang, Matricola 746483, CO
 * @author Alessandro Di Lorenzo, Matricola 733052, CO
 */

package user;

import java.util.EventObject;

/**
 * This class is used to complete the search functions
 */
public class SearchEvent extends EventObject {
    /**
     * The song's or author's name given.
     */
    private String testo;
    /**
     * The year given in search by author case.
     */
    private int year;
    /**
     * Name or Author.
     */
    private String tipoRicerca;

    /**
     * SearchEvent constructor
     * @param source
     * @param testo
     * @param year
     * @param tipoRicerca
     */
    public SearchEvent(Object source, String testo, int year, String tipoRicerca) {
        super(source);
        this.testo = testo;
        this.tipoRicerca = tipoRicerca;
        this.year = year;
    }

    /**
     * ResearchType getter
     */
    public String getTipoRicerca() {
        return tipoRicerca;
    }
    /**
     * Text getter
     */
    public String getTesto() {
        return testo;
    }
    /**
     * Year getter
     */
    public int getYear() {
        return year;
    }
}

