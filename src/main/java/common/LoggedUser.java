/**
 * @author Zhang Ying Huang, Matricola 746483, CO
 * @author Alessandro Di Lorenzo, Matricola 733052, CO
 */
package common;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * it represents an authenticated user.
 */
public class LoggedUser implements Serializable {
    /**
     * user's first name.
     */
    private String firstName;
    /**
     * user's last name.
     */
    private String lastName;
    /**
     * user's fiscal code.
     */
    private String FC;
    /**
     * user's physical address.
     */
    private String address;
    /**
     * user's email address.
     */
    private String email;
    /**
     * user's username.
     */
    private String userID;
    /**
     * user's password.
     */
    private String password;
    /**
     * user's playlists.
     */
    private LinkedList<Playlist> playlistList;

    /**
     * empty constructor.
     */
    public LoggedUser(){}
    /**
     * first name setter.
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    /**
     * last name setter.
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    /**
     * fiscal code setter.
     * @param FC fiscal code.
     */
    public void setFC(String FC) {
        this.FC = FC;
    }
    /**
     * address setter.
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }
    /**
     * email setter.
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * userid setter.
     * @param userID
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }
    /**
     * password setter.
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * playlist list setter.
     * @param playlistList
     */
    public void setPlaylistList(LinkedList<Playlist> playlistList) {
        this.playlistList = playlistList;
    }
    /**
     * userid getter.
     * @return userid
     */
    public String getId() {
        return this.userID;
    }

    /**
     * user password getter.
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * playlist list getter.
     * @return playlistList
     */
    public LinkedList<Playlist> getPlaylistList() {
        return playlistList;
    }
    /**
     * adds a playlist to the users playlist list.
     * @param p playlist.
     */
    public void addPlaylist(Playlist p){
        playlistList.add(p);
    }
    /**
     * deletes a playlist.
     * @param pName playlist's name.
     */
    public void deletePlaylist(String pName){
        for (Playlist p: playlistList)
            if(p.getPlaylistName().equals(pName)){
                playlistList.remove(p);
                break;
            }
    }
    /**
     * gets a playlist from the list.
     * @param plName playlist's name.
     * @return the playlist object if the list contains it,
     * null otherwise.
     */
    public Playlist getPlaylist(String plName){
        for (Playlist p: playlistList)
            if(p.getPlaylistName().equals(plName))
                return p;
        return null; //TMCH
    }

    /**
     * overrides the toString() method by
     * giving a format to the printing of
     * the user's info.
     * @return user's info.
     */
    @Override
    public String toString() {
        String pl;
        if (this.playlistList == null) pl = "undefined";
        else pl = this.playlistList.toString();

        return "Hello! I'm " + this.userID + " and here are my info!" +
                "\nemail address: " + this.email +
                "\nfirst name: " + this.firstName +
                "\nlast_name: " + this.lastName +
                "\nhome address: " + this.address +
                "\nfiscal code: " + this.FC +
                "\npassword: " + this.password +
                "\nplaylists:\n" + pl;
    }
}