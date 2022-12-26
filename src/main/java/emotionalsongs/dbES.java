package emotionalsongs;

import common.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;

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
    public static final String scriptPath2 = SCRIPTS_PATH + "initTableSong.sql";

    // Questo costruttore, grazie al metodo getInstance, potrà
    // essere invocato una volta sola.
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
     */
    private dbES(String server, String p, String user, String pwd) throws SQLException, IOException {
        setCredentials(server, p, user, pwd); // si settano le credenziali per collegarsi al server database
        setConnection(); // si setta la connessione con le credenziali prese prima
        createDB(statement); // si crea il db emotionalsongs, se non esiste
        url += resource; // si concatena il db emotionalsongs al resto dell'url
        setConnection(); // si risetta la connesione al db
        initializeTables(statement, scriptPath1, scriptPath2); // si inizializzano le tabelle
    }

    /**
     * gets the single dbES instance.
     * A singleton pattern is used, in order to maintain a centralized communication structure.
     */
    public static dbES getInstance(String server, String db, String p, String user, String pwd) throws SQLException, IOException {

        if (database == null) {
            database = new dbES(server, p, user, pwd);
        }

        return database;
    }

    /**
     * sets the object's field for accessing the database, in case a user has other URL specifications.
     */
    private void setCredentials(String server, String p, String user, String pwd) {
        if (!server.equals("")) host = server + ':';
        if (!p.equals("")) port = p + "/";
        if (!user.equals("")) userid = user;
        if (!pwd.equals("")) password = pwd;
    }

    /**
     * ìnitializes the connection to the database.
     */
    private void setConnection() throws SQLException {
        connection = DriverManager.getConnection(url, userid, password);
        statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        System.out.println("Connection successfully established.");
    }

    // Crea il db - DONE
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
     */
    public static void initializeTables(Statement st, String scriptPath1, String scriptPath2) throws SQLException, IOException {
        String script = null;
        String line;
        BufferedReader br = new BufferedReader(new FileReader(scriptPath1));

        // Effettua la creazione delle tabelle Song, Playlist, Emotion e RegisteredUser
        while ((line = br.readLine()) != null) {
            if (script == null) script = line;
            else script += line;
        }

        st.executeUpdate(script);

        // Esegue le insert se la tabella common.Song è vuota
        ResultSet rs = st.executeQuery("SELECT * FROM Song");
        if (!rs.next()) br = new BufferedReader(new FileReader(scriptPath2));
        while ((line = br.readLine()) != null) {
            if (script == null) script = line;
            else script += line;
        }
        br.close();

        st.executeUpdate(script);


        System.out.println("DB successfully initialized");
    }


    // Controllo credenziali
    // Verifica la presenza di una tupla nella tabella registereduser. Ritorna l'oggetto, se l'utente è
    // presente nella tabella, lancia un'eccezione altrimenti.
    /**
     * gets a LoggedUser object from the
     */
    public static synchronized LoggedUser getLoggedUser(String user_id, String pwd) throws SQLException, WrongCredentialsException {
        String query1 = "SELECT * FROM registereduser WHERE user_id = \'" + user_id + "\' AND password = \'" + pwd + "\'";
        ResultSet rs = statement.executeQuery(query1);
        LoggedUser tmp;

        if (!rs.next()) {
            throw new WrongCredentialsException("Wrong username or wrong password");
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

    // Data la query specificata, vengono poi ricostruite tutte le playlist
    // appartenenti all'utente. - DONE
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
//            return null;
            return playlists;
        }

        Playlist pl = new Playlist(user_id, plName);

        while (rs.next()) {
            if (!pl.getPlaylistName().equals(rs.getString("playlist_name"))) {
                playlists.add(pl);
                plName = rs.getString("playlist_name");
                pl = new Playlist(user_id, plName);
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

    // Registrazione - DONE
    public static boolean registerUser(String fn, String ln, String FC, String a, String email, String uid, String pwd) {
            String query = "INSERT INTO RegisteredUser VALUES (\'" + uid + "\', \'" + pwd + "\', \'" + email + "\', \'" + fn + "\', \'" + ln + "\', \'" + a + "\', \'" + FC + "\')";
            System.out.println(query);
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

    // Mostra feedback - DONE
    /*
    public static String getFeedback(String user_id, Emotions emotion_name, String song_id) throws SQLException {
        String query = "SELECT emotion_name, user_id, title, score, notes\n" +
                "FROM Emotion e JOIN\n" +
                "Song s ON e.song_id = s.song_id\n" +
                "WHERE user_id = \'" + user_id + "\'\n" +
                "AND emotion_name = \'" + emotion_name.toString() + "\'\n" +
                "AND s.song_id = \'" + song_id + "\'\n";
        ResultSet rs = statement.executeQuery(query);
        if (!rs.next()) return null;

        return "Your feedback for the song \"" + rs.getString("title") + "\":\n"
                + "Emotion: " + rs.getString("emotion_name")
                + "\nScore: " + rs.getString("score")
                + "\nNotes: " + rs.getString("notes");
    }*/

    public static LinkedList<String> getFeedback(String song_id) throws SQLException {
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
        System.out.println(feedback);

        return null;
    }

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

    /*
    public static LinkedList<String> getFeedback(String song_id, String user_id) throws SQLException {
        String query = "SELECT * " +
                        "FROM Emotion " +
                        "WHERE song_id = '" + song_id + "' AND user_id = '" + user_id + "'";
        ResultSet rs = statement.executeQuery(query);
        if (!rs.next()) return null;

        LinkedList<String> feedback_list = new LinkedList<>();
        String feedback = "";

        while (rs.next()) {
            feedback += "emotion: " + rs.getString("emotion_name")
                    + "\n score: " + rs.getString("score") +
                    "\n notes: " + rs.getString("notes") + "\n";

        }

        return feedback_list;
    }*/


    // DAL CLIENT VERSO IL DB
    // NB: IMPLEMENTARE UNA CODA DI QUERY

    // FEEDBACK
    // 1) Aggiungi feedback - DONE
    // VERIFICARE CHE L'AGGIUNTA DI UN FEEDBACK SIA PERMESSA SOLO SE
    // LA CANZONE ESISTE IN UNA DELLE PLAYLIST DELL'UTENTE.
    public static boolean addFeedback(Emotions emotion, String user_id, String song_id, String score, String notes) throws SQLException {
        String query = "INSERT INTO emotion(emotion_name, user_id, song_id, score, notes)\n" +
                "SELECT '" + emotion.toString().toLowerCase() + "', '" + user_id + "', '" + song_id + "', '" + score + "', '" + notes + "'\n" +
                "WHERE " +
                "EXISTS (SELECT * FROM Playlist WHERE song_id = '" + song_id + "')";
        System.err.println(query);
        int count = statement.executeUpdate(query);
        if (count > 0) return true;
        else return false;
    }

    // 2) Elimina feedback - DONE
    public static boolean deleteFeedback(Emotions emotion, String user_id, String song_id) throws SQLException {
        String query = "DELETE FROM emotion" +
                " WHERE emotion_name = '" + emotion.toString().toLowerCase()
                + "' AND user_id = '" + user_id + "' AND song_id = '" + song_id + "'";
        int count = statement.executeUpdate(query);
        if (count > 0) return true;
        else return false;
    }

    /* 3) Commenta canzone -> nel caso in cui un utente lasci una valutazione - DONE
     * senza commento, può aggiungerlo anche successivamente */
    // Possono essere modificati score e note
    /*
    public static boolean modifyFeedback(Emotions emotion, String user_id, String song_id, String param_name, String param_value) throws SQLException {
        String query = "UPDATE emotion\n" +
                "SET " +  param_name + " = '" + param_value + "'\n"
                + "WHERE emotion_name = '" + emotion.toString().toLowerCase()
                + "' AND user_id = '" + user_id + "' AND song_id = '" + song_id + "'";
        int count = statement.executeUpdate(query);
        if (count > 0) return true;
        else return false;
    } */

    // PLAYLIST
    // 1) Crea playlist - DONE
    public static boolean createPlaylist(String pl_name, String user_id) throws SQLException {
        String query = "INSERT INTO playlist VALUES ('" + pl_name + "', 'ZZZZZZZZZZZZZZZZZZ', '" + user_id + "')";
        // AGGIORNARE CON INSERT CONDIZIONATA: LA PLAYLIST ESISTE GIA'
        System.out.println(query);
        int count = statement.executeUpdate(query);
        if (count > 0) return true;
        else return false;
    }

    // 2) Modifica playlist -> togli canzone - DONE
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

    // 3) Rinomina playlist
    public static boolean renamePlaylist(String curr_pl_name, String new_pl_name, String user_id) throws SQLException {
        // VERIFICARE CHE LA PLAYLIST ESISTA
        String query = "UPDATE playlist\n" +
                "SET playlist_name = '" + new_pl_name + "'\n"
                + "WHERE playlist_name = '" + curr_pl_name
                + "' AND user_id = '" + user_id + "'";
        int count = statement.executeUpdate(query);
        if (count > 0) return true;
        else return false;
    }

    // 4) Elimina playlist - DONE
    public static boolean deletePlaylist(String pl_name, String user_id) throws SQLException {
        String query = "DELETE FROM playlist\n"
                + "WHERE playlist_name = '" + pl_name
                + "' AND user_id = '" + user_id + "' AND (SELECT COUNT(*) " +
                "FROM Playlist WHERE playlist_name = '" + pl_name + "') > 0";
        int count = statement.executeUpdate(query);
        if (count > 0) return true;
        else return false;
    }

    // 5) Aggiungi brani -> sfrutta la hashmap di canzoni presente nel programma - DONE
    public static boolean addSongToPlaylist(String pl_name, String song_id, String user_id) throws SQLException {
        String query = "INSERT INTO playlist (playlist_name, song_id, user_id)" +
                "SELECT '" + pl_name + "', '" + song_id + "', '" + user_id + "'" +
                "WHERE " +
                "(SELECT COUNT(*) FROM Playlist WHERE playlist_name = '" + pl_name + "') >= 1";
        // AGGIORNARE CON INSERT CONDIZIONATA: LA PLAYLIST ESISTE GIA'
        int count = statement.executeUpdate(query);
        if (count > 0) return true;
        else return false;
    }

    // USER
    /* 1) Modifica parametri (tutti) -> prima di implementare questa funzione,
     * verificare se i cascade funzionano su db (7 metodi) DA RIVEDERE ->
     * Soluzione terminal != soluzione gui */
    public static boolean modifyUserParam(String user_id, String param_name, String param_value) throws SQLException {
        // UPDATE FROM RegisteredUser SET "nome_parametro" = "parametro_nuovo" WHERE user_id = "utente"
        // PER EVITARE DI IMPLEMENTARE TRE METODI UGUALI
        String query = "UPDATE registereduser\n" +
                "SET " + param_name + " = '" + param_value + "'\n"
                + "WHERE user_id = '" + user_id + "'";
        int count = statement.executeUpdate(query);
        if (count > 0) return true;
        else return false;
    }

    // 2) Elimina utente corrente dal sistema
    public static boolean deleteUser(String user_id) throws SQLException {
        String query = "DELETE FROM registereduser\n" +
                "WHERE user_id = '" + user_id + "'";
        int count = statement.executeUpdate(query);
        if (count > 0) return true;
        else return false;
    }

    // SONG
    // 1) Restituisci prospetto riassuntivo di una canzone (statistiche canzoni, e.g. media valutazioni)

    // 2) Importa tutte le canzoni e ricostruisci i rispettivi oggetti nella lista nel gestore - DONE*
    // * Gotta adapt it to the real resourceManager
    public static synchronized HashMap<String, Song> importAllSongs() throws SQLException {
        // Aggiunta della richiesta alla coda di query
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
