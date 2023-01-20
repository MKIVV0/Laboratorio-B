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
     * Constrctor.
     */
    public FeedbackForm(Emotions emotions, Song song, int score, String notes) {
        this.emotions = emotions;
        this.song = song;
        this.score = score;
        this.notes = notes;
    }
    /**
     * Getter
     */
    public Song getSong() {
        return song;
    }

}
