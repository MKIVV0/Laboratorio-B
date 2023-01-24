/**
 * @author Zhang Ying Huang, Matricola 746483, CO
 * @author Alessandro Di Lorenzo, Matricola 733052, CO
 */

package common;

import java.io.Serializable;

/**
 * This class contains the statistics for a given emotion for a
 * given song.
 */
public class Summary implements Serializable {
    /**
     * It represents the name of the emotion.
     */
    private String emotionName;
    /**
     * It represents the number of users that left a feedback
     * for a given emotion.
     */
    private String numberOfVotes;
    /**
     * It represents the average score.
     */
    private double AVGscore;
    /**
     * It represents the list of comments left by the users.
     */
    private String[] noteList;
    /**
     * Empty constructor.
     */
    public Summary() {}
    /**
     * songId attribute setter.
     * @return emotionName
     */
    public String getEmotionName() {
        return this.emotionName;
    }
    /**
     * songId attribute setter.
     * @param emotion_name
     */
    public void setEmotionName(String emotion_name) {
        this.emotionName = emotion_name;
    }
    /**
     * numberOfVotes attribute setter.
     * @param numberOfVotes
     */
    public void setNumberOfVotes(String numberOfVotes) {
        this.numberOfVotes = numberOfVotes;
    }
    /**
     * AVGScore attribute setter.
     * @param AVGscore
     */
    public void setAVGscore(double AVGscore) {
        this.AVGscore = AVGscore;
    }
    /**
     * noteList attribute setter.
     * @param note_list
     */
    public void setNoteList(String[] note_list) {
        this.noteList = note_list;
    }

    /**
     * Overrides the default toString() method, by giving a
     * format to the current object data.
     * @return the current object info.
     */
    public String toString() {
        String tmp = "Emotion: " + this.emotionName +
                "\nNumber of votes: " + this.numberOfVotes +
                "\nAverage score: " + this.AVGscore +
                "\nComments\n";
        for (String s : this.noteList)
            tmp += s + "\n";
        return tmp;
    }
}
