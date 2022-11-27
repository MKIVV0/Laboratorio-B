package common;

import java.util.LinkedList;

public class LoggedUser extends AbstractUser {

    private String firstName;
    private String lastName;
    private String CF;
    private String email;
    private String userID;
    private String password;
    private LinkedList<Playlist> playlistList;

    public LoggedUser(String uid, String pwd) {
        this.userID = uid;
        this.password = pwd;
        this.playlistList = new LinkedList<>();
    }

    public String getId() {
        return this.userID;
    }

    public AbstractUser logout(){
        return null;
    }

    /*public void valutaBrano(Song s, int score){
        if(userID.equals("ale"))
            System.out.println("Brano " + s + " valutato: " + score);
    }*/
}