/**
 * @author Zhang Ying Huang, Matricola 746483, CO
 * @author Alessandro Di Lorenzo, Matricola 733052, CO
 */

package common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Classe rappresentante la valutazione di una canzone.
 * Ogni feedback è distinto dalla coppia songId ed emotion.
 */
public class Feedback implements Serializable {
    /**
     * Attributo rappresentate l'identificativo della canzone alla quale
     * è associato il feedback.
     */
    private String songId;
    /**
     * Attributo rappresentante il numero totale di votanti associato al feedback.
     */
    private HashMap<Emotions, LinkedList<Summary>> emotionSummaries;

   /**
    * Costruttore per istanziare un oggetto di tipo Feedback che è già
    * tato scritto su file.
    * @param sid l'identificativo della canzone.
    * @param e l'emozione associata.
    * @param s score cumulativo associato al feedback.
    * @param n numero di utenti totali che ha votato il feedback.
    * @param notes lista di commenti associata al feedback.
    */

    public Feedback() {
        this.emotionSummaries = new HashMap<>();
    }

    public String getSongId() {
        return songId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }


    public HashMap<Emotions, LinkedList<Summary>> getEmotionProspects() {
        return emotionSummaries;
    }


    public void addSummary(Summary summary) {
        Emotions emotion = Emotions.valueOf(summary.getEmotion_name());
        if (this.emotionSummaries.get(emotion) == null) {
            this.emotionSummaries.put(emotion, new LinkedList<>());
        }
        this.emotionSummaries.get(emotion).add(summary);
    }

    public String toString() {
        return "song id: " + this.songId + "\n" + this.emotionSummaries.toString();
    }
}
