package common;

import java.io.Serializable;
import java.util.Arrays;
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
    private double AVGscore;

    private String[] note_list;

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

    public double getAVGscore() {
        return AVGscore;
    }

    public void setAVGscore(double AVGscore) {
        this.AVGscore = AVGscore;
    }

    public String[] getNoteList() {
        return note_list;
    }

    public void setNoteList(String[] note_list) {
        this.note_list = note_list;
    }

    public String toString() {
        String tmp = "emotion name: " + emotion_name +
                "\nnumber of votes: " + numberOfVotes +
                "\naverage score: " + AVGscore +
                "\nnote list:\n";
        for (String s : this.note_list)
            tmp += s + "\n";
        return tmp;
    }
}
