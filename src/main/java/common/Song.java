package common;

import java.io.Serializable;

public class Song implements Serializable {

    private String id;
    private String title;
    private String author;
    private int yearReleased;

    public Song(int yearReleased, String id, String author, String title){
        this.id = id;
        this.title = title;
        this.author = author;
        this.yearReleased = yearReleased;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Song)
            return this.id.equals(((Song)o).id);
        else return false;
    }

    public String getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getAuthor() {
        return this.author;
    }

    public String toString() {
        return "Song id: " + this.id + "\nSong title: " + this.title + "\nAuthor: " + this.author + "\nYear: " + this.yearReleased;
    }

}