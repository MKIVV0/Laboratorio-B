package user;

import common.Emotions;
import common.Song;

public class FeedbackForm {

    Emotions emotions;
    Song song;
    int score;
    String notes;

    public FeedbackForm(Emotions emotions, Song song, int score, String notes) {
        this.emotions = emotions;
        this.song = song;
        this.score = score;
        this.notes = notes;
    }

    public Song getSong() {
        return song;
    }

}
