/**
 * @author Zhang Ying Huang, Matricola 746483, CO
 * @author Alessandro Di Lorenzo, Matricola 733052, CO
 */
package common;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * This class represents a playlist, which is retrieved from
 * the database.
 */
public class Playlist implements Serializable {
	/**
	 * playlist's name.
	 */
	private String playlistName;
	/**
	 * playlist's songs.
	 */
	private LinkedList<Song> songList = new LinkedList<>();

	/**
	 * Playlist constructor.
	 * @param pName playlist's name.
	 */
	public Playlist(String pName) {
		this.playlistName = pName;
	}

	/**
	 * playlist name setter.
	 * @param playlistName
	 */
	public void setPlaylistName(String playlistName) {
		this.playlistName = playlistName;
	}

	/**
	 * adds a song to the playlist.
	 * @param song the song to be added.
	 */
	public void addSong(Song song){
		this.songList.add(song);
	}

	/**
	 * song list getter.
	 * @return songList
	 */
	public LinkedList<Song> getSongList() { 
		return this.songList;
	}

	/**
	 * removes a song from the playlist.
	 * @param song
	 */
	public void removeSong(Song song){
		songList.remove(song);
	}

	/**
	 * playlist name getter.
	 * @return playlistName
	 */
	public String getPlaylistName() {
		return this.playlistName;
	}

	/**
	 * overrides the toString() method by
	 * giving a format to the printing of
	 * the playlist's info.
	 * @return playlist's info.
	 */
	@Override
    public String toString() {
        String tmp = "";
        for (Song s: this.songList)
            tmp += s.toString() + "\n";
        return "Brani nella playlist " + this.playlistName + ": \n" + tmp;
    }

}