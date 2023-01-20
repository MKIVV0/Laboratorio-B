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
     * Constructor.
     */
    public SearchEvent(Object source, String testo, int year, String tipoRicerca) {
        super(source);
        this.testo = testo;
        this.tipoRicerca = tipoRicerca;
        this.year = year;
    }

    /**
     * Getter
     */
    public String getTipoRicerca() {
        return tipoRicerca;
    }
    /**
     * Getter
     */
    public String getTesto() {
        return testo;
    }
    /**
     * Getter
     */
    public int getYear() {
        return year;
    }
}




























   /* private String marca;
    private String modello;
    private boolean vendita;
    private String targa;
    private String cambio;
    private int bagagliaio;
    private String alimentazione;
    private int numeroPosti;
    private int cilindrata;

    public FormEvent(Object source) {
        super(source);
    }

    public FormEvent(Object source, String marca, String modello, boolean vendita, String targa, String cambio, int bagagliaio, String alimentazione, int numeroPosti, int cilindrata) {
        super(source);
        this.marca = marca;
        this.modello = modello;
        this.vendita = vendita;
        this.targa = targa;
        this.cambio = cambio;
        this.bagagliaio = bagagliaio;
        this.alimentazione = alimentazione;
        this.numeroPosti = numeroPosti;
        this.cilindrata = cilindrata;
    }

    public int getCilindrata() {
        return cilindrata;
    }

    public void setCilindrata(int cilindrata) {
        this.cilindrata = cilindrata;
    }

    public int getNumeroPosti() {
        return numeroPosti;
    }

    public void setNumeroPosti(int numeroPosti) {
        this.numeroPosti = numeroPosti;
    }

    public String getAlimentazione() {
        return alimentazione;
    }

    public void setAlimentazione(String alimentazione) {
        this.alimentazione = alimentazione;
    }

    public String getCambio() {
        return cambio;
    }

    public int getBagagliaio() {
        return bagagliaio;
    }

    public void setBagagliaio(int bagagliaio) {
        this.bagagliaio = bagagliaio;
    }

    public void setCambio(String cambio) {
        this.cambio = cambio;
    }

    public boolean isVendita() {
        return vendita;
    }

    public void setVendita(boolean vendita) {
        this.vendita = vendita;
    }

    public String getTarga() {
        return targa;
    }

    public void setTarga(String targa) {
        this.targa = targa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModello() {
        return modello;
    }

    public void setModello(String modello) {
        this.modello = modello;
    }
}*/