/**
 * @author Zhang Ying Huang, Matricola 746483, CO
 * @author Alessandro Di Lorenzo, Matricola 733052, CO
 */

package common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * This class represents a summary of the feedbacks, for each emotion,
 * given by the users for a given song.
 */
public class Feedback implements Serializable {
    /**
     * Identifier of a given song.
     */
    private String songId;
    /**
     * It represents the list of a given song's summaries, each of which
     * is an object of type Summary.
     */
    private HashMap<Emotions, LinkedList<Summary>> emotionSummaries;

   /**
    * Constructor for instantiating an object of type Feedback.
    */
    public Feedback() {
        this.emotionSummaries = new HashMap<>();
    }

    /**
     * songId attribute setter.
     * @param songId a song id.
     */
    public void setSongId(String songId) {
        this.songId = songId;
    }

    /**
     * Adds an object of type Summary to the list of summaries, which is
     * a summarized list of data that belongs to a given song.
     * @param summary Summary object.
     */
    public void addSummary(Summary summary) {
        Emotions emotion = Emotions.valueOf(summary.getEmotionName());
        if (this.emotionSummaries.get(emotion) == null) {
            this.emotionSummaries.put(emotion, new LinkedList<>());
        }
        this.emotionSummaries.get(emotion).add(summary);
    }

    /**
     * Overrides the default toString() method, by giving a
     * format to the current object data.
     * @return the current object info.
     */
    public String toString() {
        String tmp = "";
        for (LinkedList<Summary> summaryLinkedList : this.emotionSummaries.values())
            for (Summary summary : summaryLinkedList)
                tmp += summary.toString() + "\n";
        return tmp;
    }
}
