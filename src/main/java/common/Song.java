/**
 * @author Zhang Ying Huang, Matricola 746483, CO
 * @author Alessandro Di Lorenzo, Matricola 733052, CO
 */
package common;

import java.io.Serializable;
/**
 * this class represents a song.
 */
public class Song implements Serializable {
    /**
     * song's identifier.
     */
    private String id;
    /**
     * @song's title.
     */
    private String title;
    /**
     * song's author.
     */
    private String author;
    /**
     * song's release year.
     */
    private int yearReleased;

    /**
     * Song constructor.
     * @param yearReleased song's release year.
     * @param id song's identifier.
     * @param author song's author.
     * @param title song's title.
     */
    public Song(int yearReleased, String id, String author, String title){
        this.id = id;
        this.title = title;
        this.author = author;
        this.yearReleased = yearReleased;
    }

    /**
     * Song empty constructor.
     */
    public Song(){}
    /**
     * song id setter.
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }
    /**
     * song title setter.
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }
    /**
     * song author setter.
     * @param author
     */
    public void setAuthor(String author) {
        this.author = author;
    }
    /**
     * song release year setter.
     * @param yearReleased
     */
    public void setYearReleased(int yearReleased) {
        this.yearReleased = yearReleased;
    }
    /**
     * it overrides the method equals.
     * It checks whether two instances of Song are
     * the same.
     * @param o a generic object.
     */
    @Override
    public boolean equals(Object o) {
        if(o instanceof Song)
            return this.id.equals(((Song)o).id);
        else return false;
    }
    /**
     * song id getter.
     */
    public String getId() {
        return this.id;
    }
    /**
     * song title getter.
     */
    public String getTitle() {
        return this.title;
    }
    /**
     * song author getter.
     */
    public String getAuthor() {
        return this.author;
    }
    /**
     * song year release getter.
     */
    public int getYear() {
        return yearReleased;
    }
    /**
     * overrides the toString() method by
     * giving a format to the printing of
     * the song's info.
     * @return song's info.
     */
    public String toString() {
        return  this.title + " (" + this.author + ", " + this.yearReleased + ")";
//        return "Song id: " + this.id + "\nSong title: " + this.title + "\nAuthor: " + this.author + "\nYear: " + this.yearReleased;
    }

}