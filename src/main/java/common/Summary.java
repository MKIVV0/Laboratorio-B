package common;

import java.io.Serializable;
import java.util.LinkedList;

public class Summary implements Serializable {
    private String emotion_name;
    /**
     * Attributo rappresentante l'emozione associata al feedback.
     */
    private String numberOfVotes;
    /**
     * Attributo rappresentante lo score cumulativo associato al feedback.
     */
    private String AVGscore;

    private String note_list;

    public Summary() {}

    public String getEmotion_name() {
        return emotion_name;
    }

    public void setEmotionName(String emotion_name) {
        this.emotion_name = emotion_name;
    }

    public String getNumberOfVotes() {
        return numberOfVotes;
    }

    public void setNumberOfVotes(String numberOfVotes) {
        this.numberOfVotes = numberOfVotes;
    }

    public String getAVGscore() {
        return AVGscore;
    }

    public void setAVGscore(String AVGscore) {
        this.AVGscore = AVGscore;
    }

    public String getNoteList() {
        return note_list;
    }

    public void setNoteList(String note_list) {
        this.note_list = note_list;
    }

    public String toString() {
        return "emotion name: " + emotion_name +
                "\nnumber of votes: " + numberOfVotes +
                "\naverage score: " + AVGscore +
                "\nnote list: " + note_list;
    }
}
