/**
 * @author Zhang Ying Huang, Matricola 746483, CO
 * @author Alessandro Di Lorenzo, Matricola 733052, CO
 */

package common;

import java.io.Serializable;
import java.util.HashMap;
/**
 * Classe rappresentante la valutazione di una canzone.
 * Ogni feedback è distinto dalla coppia songId ed emotion.
 */
public class Feedback implements Serializable {
    /**
     * Attributo rappresentate l'identificativo della canzone alla quale
     * è associato il feedback.
     */
    private String user;
    /**
    * Attributo rappresentante l'emozione associata al feedback.
    */
    private String emotion;
   /**
    * Attributo rappresentante lo score cumulativo associato al feedback.
    */
    private String score;
    /**
     * Attributo rappresentante il numero totale di votanti associato al feedback.
     */
    private int totalNumberOfScores;
    /**
     * Attributo rappresentante la lista i commenti al feedback.
     */
    private String notes;

   /**
    * Costruttore per istanziare un oggetto di tipo Feedback che è già
    * tato scritto su file.
    * @param sid l'identificativo della canzone.
    * @param e l'emozione associata.
    * @param s score cumulativo associato al feedback.
    * @param n numero di utenti totali che ha votato il feedback.
    * @param notes lista di commenti associata al feedback.
    */
    public Feedback(String emotion, String user, String score, String notes) {
        this.user = user;
        this.emotion = emotion;
        this.score = score;
        this.notes = notes;
    }

    public Feedback() {}

    /**
     * Aggiunge cumulativamente uno score al feedback.
     * @param score lo score da aggiungere.
     */
    public void addScore(int score) {
        this.score += score;
        this.totalNumberOfScores++;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getEmotion() {
        return emotion;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String toString() {
        return "emotion: " + this.emotion +
                "\nuser: " + this.user +
                "\nscore: " + this.score +
                "\nnotes: " + this.notes;
    }
}
