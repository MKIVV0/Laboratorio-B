/**
 * @author Zhang Ying Huang, Matricola 746483, CO
 * @author Alessandro Di Lorenzo, Matricola 733052, CO
 */

package user;

import common.Emotions;
import common.Song;

/**
 * This class is used to complete the song's evaluation
 */
public class FeedbackForm {
    /**
     * The emotion enum.
     */
    Emotions emotions;
    /**
     * The song to evaluate.
     */
    Song song;
    /**
     * The score given.
     */
    int score;
    /**
     * The comment left by the user.
     */
    String notes;

    /**
     * Feedback form Constructor
     * @param emotions emotion regarding the song
     * @param song the song considered
     * @param score the score given to the song
     * @param notes the notes left on the song
     */
    public FeedbackForm(Emotions emotions, Song song, int score, String notes) {
        this.emotions = emotions;
        this.song = song;
        this.score = score;
        this.notes = notes;
    }
    /**
     * Song getter
     * @return song's title
     */
    public Song getSong() {
        return song;
    }

}
