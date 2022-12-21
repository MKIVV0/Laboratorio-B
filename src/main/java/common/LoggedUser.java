package common;

import java.util.LinkedList;

public class LoggedUser extends AbstractUser {

    private String firstName;
    private String lastName;
    private String FC;
    private String address;
    private String email;
    private String userID;
    private String password;
    private LinkedList<Playlist> playlistList;

    public LoggedUser(String uid, String pwd) {
        this.userID = uid;
        this.password = pwd;
        this.playlistList = new LinkedList<>();
    }

    public LoggedUser(){}

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFC(String FC) {
        this.FC = FC;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPlaylistList(LinkedList<Playlist> playlistList) {
        this.playlistList = playlistList;
    }

    public String getId() {
        return this.userID;
    }

    public LinkedList<Playlist> getPlaylistList() {
        return playlistList;
    }

    public void addPlaylist(Playlist p){
        playlistList.add(p);
    }

    public void deletePlaylist(String s){
        for (Playlist p: playlistList)
            if(p.getPlaylistName().equals(s)){
                playlistList.remove(p);
                break;
            }
    }

    public Playlist getPlaylist(String plName){
        for (Playlist p: playlistList)
            if(p.getPlaylistName().equals(plName))
                return p;
        return null; //TMCH
    }

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