package common;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;

public class Playlist implements Serializable {

    private String userId;
	private String playlistName;
	private LinkedList<Song> songList = new LinkedList<>();

	public Playlist(String userId, String pName) {
		this.userId = userId;
		this.playlistName = pName;
	}

	public Playlist(String userId, String pName, LinkedList<Song> l) {
        this.userId = userId;
		this.playlistName = pName;
		this.songList = l;
	}

	public Playlist(){}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setPlaylistName(String playlistName) {
		this.playlistName = playlistName;
	}

	public void setSongList(LinkedList<Song> songList) {
		this.songList = songList;
	}

	public void addSong(Song song){
		for (Song s: this.songList)
			if (s.equals(song)) {
				System.out.println("\nCanzone già presente nella playlist!");
				return;
			}
		this.songList.add(song);
	}

	public LinkedList<Song> getSongList() { 
		return this.songList;
	}

	public boolean isEmpty(){
		return this.songList.isEmpty();
	}

	public String getPlaylistName() {
		return this.playlistName;
	}

	@Override
    public String toString() {
        String tmp = "";
        for (Song s: this.songList)
            tmp += s.toString() + "\n";
        return "Brani nella playlist " + this.playlistName + ": \n" + tmp;
    }

}