package emotionalsongs;

/**
 * @author Zhang Ying Huang, Matricola 746483, CO
 * @author Alessandro Di Lorenzo, Matricola 733052, CO
 */

import java.io.Serializable;

/**
 * La classe Song rappresenta la singola canzone, che contiene i campi
 * da un identificativo, un titolo, un autore e un anno di rilascio.
 */
public class Song implements Serializable {

    /**
     * Attributo che rappresenta l'identificativo
     * univoco della canzone.
     */
    private String id;

    /**
     * Attributo che rappresenta il titolo della
     * canzone.
     */
    private String title;

    /**
     * Attributo che rappresenta l'autore della
     * canzone, costituito da nome e cognome.
     */
    private String author;

    /**
     * Attributo che rappresenta l'anno di pubblicazione
     * della canzone.
     */
    private int yearReleased;

    /**
     * Costruttore per istanziare un oggetto di tipo Song.
     * @param yearReleased anno di rilascio
     * @param id identificativo della canzone
     * @param author autore della canzone
     * @param title titolo della canzone
     */
    public Song(int yearReleased, String id, String author, String title){
        this.id = id;
        this.title = title;
        this.author = author;
        this.yearReleased = yearReleased;
    }

    /**
     * Override del metodo equals(). Verifica se un oggetto di tipo song
     * passato come parametro è uguale all'oggetto chiamante.
     * @param o oggetto da confrontare.
     * @return true se i due oggetti corrispondono, false altrimenti.
     */
    @Override
    public boolean equals(Object o) {
        if(o instanceof Song)
            return this.id.equals(((Song)o).id);
        else return false;
    }

    /**
     * Ritorna l'identificativo dell'istanza di Song chiamante.
     * @return identificativo del chiamante.
     */
    public String getId() {
        return this.id;
    }

    /**
     * Ritorna il titolo dell'istanza di Song chiamante.
     * @return titolo del chiamante.
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Ritorna l'autore dell'istanza di Song chiamante.
     * @return autore del chiamante.
     */
    public String getAuthor() {
        return this.author;
    }

    public String toString() {
        return "Song id: " + this.id + "\nSong title: " + this.title + "\nAuthor: " + this.author + "\nYear: " + this.yearReleased;
    }
}