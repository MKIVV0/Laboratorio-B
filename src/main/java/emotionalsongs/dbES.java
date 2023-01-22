/**
 * @author Zhang Ying Huang, Matricola 746483, CO
 * @author Alessandro Di Lorenzo, Matricola 733052, CO
 */

package emotionalsongs;

import common.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;
/**
 * it represents the class that the program interfaces with for
 * interacting with the database.
 */
public class dbES {
    /**
     * database's URL.
     */
    private static String protocol = "jdbc:postgresql" + "://";
    /**
     * default host's name in which the database is located.
     */
    private static String host = "localhost" + ':';
    /**
     * default port on which the database is located.
     */
    private static String port = 5432 + "/";
    /**
     * database's name.
     */
    private static String resource = "emotionalsongs";
    /**
     * full path for accessing the database.
     */
    private static String url = protocol + host + port;
    /**
     * default database's userid.
     */
    private static String userid = "postgres";
    /**
     * database user password.
     */
    private static String password = null;
    /**
     * static object instance of the class.
     */
    private static dbES database;
    /**
     * connection object by which a connection is established between the program and the database.
     */
    private static Connection connection;
    /**
     * object by which query statements are created.
     */
    private static Statement statement;
    /**
     * base path.
     */
    public static final String SCRIPTS_PATH = "." + File.separator + "SQLScripts" + File.separator;
    /**
     * path of the file that creates the tables in the database.
     */
    public static final String scriptPath1 = SCRIPTS_PATH + "initDB.sql";
    /**
     * path of the file that initializes the repository and gives a default user.
     */
    public static final String scriptPath2 = SCRIPTS_PATH + "initTables.sql";

    /**
     * dbES constructor.
     * It sets the credentials for connecting to the server on which the databases is
     * located on, then a connection between the database on the server and the client
     * is created in order to create the database emotionalsongs if it doesn't exist.
     * The path is then updated with the resource name and the connection is reinitialized.
     * In the end, the tables are constructed and the data are initialized.
     * @param server server's name.
     * @param p server's port on which the database service is available.
     * @param user database's userid credential.
     * @param pwd database's password credential.
     * @throws SQLException
     * @throws IOException
     */
    private dbES(String server, String p, String user, String pwd) throws SQLException, IOException {
        setCredentials(server, p, user, pwd);
        setConnection();
        createDB(statement);
        url += resource;
        setConnection();
        System.out.println("Connection successfully established.");
        initializeTables(statement, scriptPath1, scriptPath2);
    }

    /**
     * gets the single dbES instance.
     * A singleton pattern is used, in order to maintain a centralized communication structure.
     * @param server server's name.
     * @param p server's port on which the database service is available.
     * @param user database's userid credential.
     * @param pwd database's password credential.
     * @throws SQLException
     * @throws IOException
     */
    public static dbES getInstance(String server, String p, String user, String pwd) throws SQLException, IOException {

        if (database == null) {
            database = new dbES(server, p, user, pwd);
        }

        return database;
    }

    /**
     * sets the object's field for accessing the database, in case a user has other URL specifications.
     * @param server server's name.
     * @param p server's port on which the database service is available.
     * @param user database's userid credential.
     * @param pwd database's password credential.
     */
    private static void setCredentials(String server, String p, String user, String pwd) {
        if (!server.equals("")) host = server + ':';
        if (!p.equals("")) port = p + "/";
        if (!user.equals("")) userid = user;
        if (!pwd.equals("")) password = pwd;
    }

    /**
     * ìnitializes the connection to the database.
     * @throws SQLException
     */
    private static void setConnection() throws SQLException {
        connection = DriverManager.getConnection(url, userid, password);
        statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
    }

    /**
     * creates the default database.
     */
    public static void createDB(Statement st) {
        try {
            if (st.execute("CREATE DATABASE EmotionalSongs"))
                System.out.println("EmotionalSongs DB successfully created.");
        } catch (SQLException e) {
            System.err.println("EmotionalSongs DB already exists.");
        }
    }

    /**
     * initializes the database tables.
     * @param st statement object.
     * @param scriptPath1 first script's path for initializing the database.
     * @param scriptPath2 second script's path for initializing the tables.
     * @throws SQLException
     * @throws IOException
     */
    public static void initializeTables(Statement st, String scriptPath1, String scriptPath2) throws SQLException, IOException {
        String script = null;
        String line;
        BufferedReader br = new BufferedReader(new FileReader(scriptPath1));

        while ((line = br.readLine()) != null) {
            if (script == null) script = line;
            else script += line;
        }

        st.executeUpdate(script);

        ResultSet rs = st.executeQuery("SELECT * FROM Song");
        if (!rs.next()) br = new BufferedReader(new FileReader(scriptPath2));
        while ((line = br.readLine()) != null) {
            if (script == null) script = line;
            else script += line;
        }
        br.close();

        st.executeUpdate(script);


        System.out.println("DB successfully initialized.");
    }


    /**
     * gets a LoggedUser object from the database.
     * @param user_id the username.
     * @param pwd the password.
     * @throws SQLException
     * @return a LoggedUser object.
     */
    public static synchronized LoggedUser getLoggedUser(String user_id, String pwd) throws SQLException {
        String query1 = "SELECT * FROM registereduser WHERE user_id = \'" + user_id + "\' AND password = \'" + pwd + "\'";
        ResultSet rs = statement.executeQuery(query1);
        LoggedUser tmp;
        if (!rs.next()) {
            return null;
        } else {
            rs.first();
            tmp = new LoggedUser();
            tmp.setUserID(user_id);
            tmp.setFirstName(rs.getString("first_name"));
            tmp.setLastName(rs.getString("last_name"));
            tmp.setFC(rs.getString("fiscal_code"));
            tmp.setAddress(rs.getString("home_address"));
            tmp.setEmail(rs.getString("email"));
            tmp.setPassword(rs.getString("password"));
            tmp.setPlaylistList(getPlaylists(user_id));
        }
        return tmp;
    }

    /**
     * gets a LoggedUser object's playlists from the database.
     * It is used in the getLoggedUser() method.
     * @param user_id the username.
     * @throws SQLException
     * @return the user's playlists.
     */
    private static LinkedList<Playlist> getPlaylists(String user_id) throws SQLException {
        String query = "SELECT p.playlist_name, s.song_id, s.title,s.author, s.year_released \n" +
                "FROM playlist p \n" +
                "    JOIN registereduser ru ON p.user_id = ru.user_id \n" +
                "    JOIN Song s ON p.song_id = s.song_id \n" +
                "WHERE p.user_id = '" + user_id + "'\n" +
                "ORDER BY playlist_name";
        ResultSet rs = statement.executeQuery(query);

        LinkedList<Playlist> playlists = new LinkedList<>();
        String plName = "";
        if (rs.next()) {
            rs.first();
            plName = rs.getString("playlist_name");
            rs.beforeFirst();
        } else {
            return playlists;
        }

        Playlist pl = new Playlist(plName);

        while (rs.next()) {
            if (!pl.getPlaylistName().equals(rs.getString("playlist_name"))) {
                playlists.add(pl);
                plName = rs.getString("playlist_name");
                pl = new Playlist(plName);
            }
            Song song = new Song();
            song.setId(rs.getString("song_id"));
            song.setTitle(rs.getString("title"));
            song.setAuthor(rs.getString("author"));
            song.setYearReleased(Integer.parseInt(rs.getString("year_released")));
            pl.addSong(song);
        }
        playlists.add(pl);

        return playlists;
    }


    /**
     * gets a LoggedUser object's playlists from the database.
     * It is used in the getLoggedUser() method.
     * @param fn the user's first name.
     * @param ln the user's last name.
     * @param FC the user's fiscal code.
     * @param addr the user's address.
     * @param email the user's email.
     * @param uid the user's username.
     * @param pwd the user's password.
     * @return true if the involved table has been modified, false otherwise.
     */
    public static boolean registerUser(String fn, String ln, String FC, String addr, String email, String uid, String pwd) {
            String query = "INSERT INTO RegisteredUser VALUES (\'" + uid + "\', \'" + pwd + "\', \'" + email + "\', \'" + fn + "\', \'" + ln + "\', \'" + addr + "\', \'" + FC + "\')";
            int count;
            try{
               count = statement.executeUpdate(query);
               System.out.println("count = " + count + " User " + uid + " registered successfully.");
//               if (count > 0) return true;
            } catch (SQLException e){
                 return false;
            }
           return true;
    }


    /**
     * gets a Feedback object related to a given song.
     * A feedback represents a summary of the feedbacks given by
     * the users for each emotion related to the given song.
     * @param song_id the song's identifier.
     * @throws SQLException
     * @return a list of summaries.
     */
    public static Feedback getFeedback(String song_id) throws SQLException {
        String query = "SELECT emotion_name, COUNT(DISTINCT user_id) AS number_of_users, AVG(score) AS score, array_agg(user_id || ': ' || notes) AS note_list\n" +
                       "FROM emotion\n" +
                       "WHERE song_id = '" + song_id + "'\n" +
                       "GROUP BY emotion_name;";
        ResultSet rs = statement.executeQuery(query);
        if (!rs.next()) return null;
        rs.beforeFirst();

        Feedback feedback = new Feedback();
        feedback.setSongId(song_id);

        while (rs.next()) {
            Summary tmp = new Summary();
            tmp.setEmotionName(rs.getString("emotion_name").toUpperCase());
            tmp.setNumberOfVotes(rs.getString("number_of_users"));
            tmp.setAVGscore(Double.parseDouble(rs.getString("score").substring(0, 4)));

            String[] notes = rs.getString("note_list").split(",");
            notes = reformatFeedbackComments(notes);

            tmp.setNoteList(notes);
            feedback.addSummary(tmp);
        }

        return feedback;
    }

    /**
     * It serves to reformat the comment lists retrieved from the database,
     * in order to allow a better displaying.
     * @param comments an array of comments.
     * @return the list of formatted comments.
     */
    private static String[] reformatFeedbackComments(String[] comments) {
        int length = comments.length;
        int commentLength = 0;
        int endingIndex = 0;
        for (int i = 0; i < length; i++) {
            commentLength = comments[i].length();
            if (commentLength - 1 > 0) endingIndex = commentLength - 2;

            if (i == 0) comments[i] = comments[i].substring(2, endingIndex);
            else if (i == comments.length - 1) comments[length - 1] = comments[length - 1].substring(1, endingIndex);
            else comments[i] = comments[i].substring(1, endingIndex);
        }
        return comments;
    }

    /**
     * adds a user's feedback for a given song and a given emotion to
     * the database.
     * @param emotion the emotion involved.
     * @param user_id the user's username.
     * @param song_id the song identifier involved.
     * @param score the score left by the user.
     * @param notes the notes left by the user.
     * @return true if the involved table has been modified, false otherwise.
     */
    public static boolean addFeedback(Emotions emotion, String user_id, String song_id, String score, String notes) {
        String query = "INSERT INTO emotion(emotion_name, user_id, song_id, score, notes)\n" +
                "SELECT '" + emotion.toString().toLowerCase() + "', '" + user_id + "', '" + song_id + "', '" + score + "', '" + notes + "'\n" +
                "WHERE " +
                "EXISTS (SELECT * FROM Playlist WHERE song_id = '" + song_id + "')";
        int count;
        try {
            count = statement.executeUpdate(query);
        }catch (SQLException e){
            return false;
        }
        if (count > 0) return true;
        else return false;
    }

    /**
     * deletes a user's feedback for a given song and a given emotion to
     * the database.
     * @param emotion the emotion involved.
     * @param user_id the user's username.
     * @param song_id the song identifier involved.
     * @throws SQLException
     * @return true if the involved table has been modified, false otherwise.
     */
    public static boolean deleteFeedback(Emotions emotion, String user_id, String song_id) throws SQLException {
        String query = "DELETE FROM emotion" +
                " WHERE emotion_name = '" + emotion.toString().toLowerCase()
                + "' AND user_id = '" + user_id + "' AND song_id = '" + song_id + "'";
        int count = statement.executeUpdate(query);
        if (count > 0) return true;
        else return false;
    }

    /**
     * creates a new playlist that is added to the database.
     * @param pl_name the playlist's name.
     * @param user_id the user's username.
     * @throws SQLException
     * @return true if the involved table has been modified, false otherwise.
     */
    public static boolean createPlaylist(String pl_name, String user_id) throws SQLException {
        String query = "INSERT INTO playlist VALUES ('" + pl_name + "', 'ZZZZZZZZZZZZZZZZZZ', '" + user_id + "')";
        int count = statement.executeUpdate(query);
        if (count > 0) return true;
        else return false;
    }

    /**
     * removes a song from an existing playlist.
     * @param pl_name the playlist's name.
     * @param song_id the song's identifier.
     * @param user_id the user's username.
     * @throws SQLException
     * @return true if the involved table has been modified, false otherwise.
     */
    public static boolean removeSongFromPlaylist(String pl_name, String song_id, String user_id) throws SQLException {
        // LANCIARE UN MESSAGGIO NEL CASO IN CUI CI SIA SOLO UNA OCCORRENZA DI pl_name
        // LA SUA RIMOZIONE COMPORTA L'ELIMINAZIONE DELLA PLAYLIST STESSA
        String query = "DELETE FROM playlist" +
                " WHERE playlist_name = '" + pl_name
                + "' AND song_id = '" + song_id + "' AND user_id = '" + user_id + "' AND (SELECT COUNT(*) " +
                "FROM Playlist WHERE playlist_name = '" + pl_name + "') > 1";
        int count = statement.executeUpdate(query);
        if (count > 0) return true;
        else return false;
    }

    /**
     * renames a given playlist.
     * @param curr_pl_name the playlist's old name.
     * @param new_pl_name the playlist's new name.
     * @param user_id the user's username.
     * @throws SQLException
     * @return true if the involved table has been modified, false otherwise.
     */
    public static boolean renamePlaylist(String curr_pl_name, String new_pl_name, String user_id) throws SQLException {
        String query = "UPDATE playlist\n" +
                "SET playlist_name = '" + new_pl_name + "'\n"
                + "WHERE playlist_name = '" + curr_pl_name
                + "' AND user_id = '" + user_id + "'";
        int count = statement.executeUpdate(query);
        if (count > 0) return true;
        else return false;
    }

    /**
     * deletes a given playlist.
     * @param pl_name the playlist's name.
     * @param user_id the user's username.
     * @throws SQLException
     * @return true if the involved table has been modified, false otherwise.
     */
    public static boolean deletePlaylist(String pl_name, String user_id) throws SQLException {
        String query = "DELETE FROM playlist\n"
                + "WHERE playlist_name = '" + pl_name
                + "' AND user_id = '" + user_id + "' AND (SELECT COUNT(*) " +
                "FROM Playlist WHERE playlist_name = '" + pl_name + "') > 0";
        int count = statement.executeUpdate(query);
        if (count > 0) return true;
        else return false;
    }

    /**
     * adds a song to a given playlist.
     * @param pl_name the playlist's name.
     * @param song_id song involved.
     * @param user_id the user's username.
     * @throws SQLException
     * @return true if the involved table has been modified, false otherwise.
     */
    public static boolean addSongToPlaylist(String pl_name, String song_id, String user_id) throws SQLException {
        String query = "INSERT INTO playlist (playlist_name, song_id, user_id)" +
                "SELECT '" + pl_name + "', '" + song_id + "', '" + user_id + "'" +
                "WHERE " +
                "(SELECT COUNT(*) FROM Playlist WHERE playlist_name = '" + pl_name + "') >= 1";
        int count = statement.executeUpdate(query);
        if (count > 0) return true;
        else return false;
    }

    /**
     * modifies a user's given data field with a new value.
     * @param user_id the user's username.
     * @param param_name the parameter that has to be modified.
     * @param param_value the new value to assign.
     * @throws SQLException
     * @return true if the involved table has been modified, false otherwise.
     */
    public static boolean modifyUserParam(String user_id, String param_name, String param_value) throws SQLException {
        String query = "UPDATE registereduser\n" +
                "SET " + param_name + " = '" + param_value + "'\n"
                + "WHERE user_id = '" + user_id + "'";
        int count = statement.executeUpdate(query);
        if (count > 0) return true;
        else return false;
    }

    /**
     * deletes a user from the system.
     * @param user_id the user's username.
     * @throws SQLException
     * @return true if the involved table has been modified, false otherwise.
     */
    public static boolean deleteUser(String user_id) throws SQLException {
        String query = "DELETE FROM registereduser\n" +
                "WHERE user_id = '" + user_id + "'";
        int count = statement.executeUpdate(query);
        if (count > 0) return true;
        else return false;
    }

    /**
     * it loads all the repository songs from the database.
     * @throws SQLException
     * @return a hashmap with all the songs.
     */
    public static synchronized HashMap<String, Song> importAllSongs() throws SQLException {
        String query = "SELECT * FROM Song ORDER BY year_released, author, song_id";
        ResultSet rs = statement.executeQuery(query);

        HashMap<String, Song> tmp = new HashMap<>();

        while (rs.next()) {
            Song song = new Song();
            song.setId(rs.getString("song_id"));
            song.setTitle(rs.getString("title"));
            song.setAuthor(rs.getString("author"));
            song.setYearReleased(Integer.parseInt(rs.getString("year_released")));
            tmp.put(song.getId(), song);
        }

        return tmp;
    }
}
