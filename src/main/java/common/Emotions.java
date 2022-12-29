/**
 * @author Zhang Ying Huang, Matricola 746483, CO
 * @author Alessandro Di Lorenzo, Matricola 733052, CO
 */

package common;

import java.io.Serializable;

/**
 * this class contains enumerative constants that represents
 * each single emotion that can be associated to a single song.
 * <p>each constant has its own description.</p>
 */
public enum Emotions implements Serializable {
    /**
     * Amazement emotion.
     */
    AMAZEMENT("Feeling of wonder or happiness"),
    /**
     * Solemnity emotion.
     */
    SOLEMNITY("Feeling of transcendence, inspiration. Thrills."),
    /**
     * Tenderness emotion.
     */
    TENDERNESS("Sensuality, affect, feeling of love"),
    /**
     * Nostalgia emotion.
     */
    NOSTALGIA("Dreamy, melancholic, sentimental feelings"),
    /**
     * Calmness emotion.
     */
    CALMNESS("Relaxation, serenity, meditativeness"),
    /**
     * Power emotion.
     */
    POWER("Feeling strong, heroic, triumphant, energetic"),
    /**
     * Joy emotion.
     */
    JOY("Feels like dancing, bouncy feeling, animated, amused"),
    /**
     * Tension emotion.
     */
    TENSION("Feeling Nervous, impatient, irritated"),
    /**
     * Sadness emotion.
     */
    SADNESS("Feeling Depressed, sorrowful");

    /**
     * emotion descriptor.
    */
    private String explanation;


    /**
     * Enum constructor.
     * @param expl emotion descriptor.
     */
    Emotions(String expl){
        this.explanation = expl;
    }


    /**
     * overrides the toString() method by
     * giving a format to the printing of
     * the emotion's name.
     * @return emotion's name.
     */
    @Override
    public String toString() {
        switch(this) {
            case AMAZEMENT: return "AMAZEMENT";
            case SOLEMNITY: return "SOLEMNITY";
            case TENDERNESS: return "TENDERNESS";
            case NOSTALGIA: return "NOSTALGIA";
            case CALMNESS:return "CALMNESS";
            case POWER:return "POWER";
            case JOY:return "JOY";
            case TENSION:return "TENSION";
            case SADNESS:return "SADNESS";
            default: return "";
        }
    }
}