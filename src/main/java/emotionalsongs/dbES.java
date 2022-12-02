package emotionalsongs;

import common.LoggedUser;
import common.Playlist;
import common.Song;
import common.WrongCredentialsException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;

public class dbES {
    private static String protocol 	= "jdbc:postgresql" + "://";
    private static String host		= "localhost" + ':';
    private static String port         = 5432 + "/";
    private static String resource	= "emotionalsongs";

    private static String url  = protocol + host + port;
    private static String userid = "postgres";
    private static String password = "postgres";

    private static dbES database;
    private static Connection connection;
    private static Statement statement;

    String scriptPath1 = "C:\\Users\\huang\\OneDrive\\Uni\\LabB-2021-22\\SQLScripts\\initDB.sql";
    String scriptPath2 = "C:\\Users\\huang\\OneDrive\\Uni\\LabB-2021-22\\SQLScripts\\initTableSong.sql";

    // Questo costruttore, grazie al metodo getInstance, potrà
    // essere invocato una volta sola.
    private dbES(String server, String p, String user, String pwd) throws SQLException, IOException {
        setCredentials(server, p, user, pwd); // si settano le credenziali per collegarsi al server database
        setConnection(); // si setta la connessione con le credenziali prese prima
        createDB(statement); // si crea il db emotionalsongs, se non esiste
        url += resource; // si concatena il db emotionalsongs al resto dell'url
        setConnection(); // si risetta la connesione al db
        initializeTables(statement, scriptPath1, scriptPath2); // si inizializzano le tabelle
    }

    // Sfrutto il pattern Singleton per assicurarmi di gestire la
    // comunicazione con il db in modo centralizzato.
    public static dbES getInstance(String server, String db, String p, String user, String pwd) throws SQLException, IOException {

        if (database == null) {
            database = new dbES(server, p, user, pwd);
        }

        return database;
    }

    private void setCredentials(String server, String p, String user, String pwd) {
        if (!server.equals("")) host = server + ':';
        if (!p.equals("")) port = p + "/";
        if (!user.equals("")) userid = user;
        if (!pwd.equals("")) password = pwd;
    }

    private void setConnection() throws SQLException {
        connection = DriverManager.getConnection(url, userid, password);
        statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        System.out.println("Connection successfully established.");
    }

    // Crea il db - DONE
    public static void createDB(Statement st) {
        try {
            if (st.execute("CREATE DATABASE EmotionalSongs"))
                System.out.println("EmotionalSongs DB successfully created.");
        } catch (SQLException e) {
            System.err.println("EmotionalSongs DB already exists.");
        }
    }

    // Inizializza le tabelle - DONE
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

    // FOR TESTING PURPOSES
    private static ResultSet submitQuery(String query) throws SQLException {

        if ( statement.execute(query) ) {

            return statement.getResultSet();
        }

        return null;
    }


    // Controllo credenziali
    // Verifica la presenza di una tupla nella tabella registereduser. Ritorna l'oggetto, se l'utente è
    // presente nella tabella, lancia un'eccezione altrimenti.
    public static synchronized LoggedUser getLoggedUser(String user_id, String pwd) throws SQLException {
        String query1 = "SELECT * FROM registereduser WHERE user_id = \'" + user_id + "\' AND password = \'" + pwd + "\'";
        ResultSet rs = statement.executeQuery(query1);
        LoggedUser tmp;

        if (!rs.next()) {
            throw new SQLException("Wrong username or wrong password");
        }
        else {
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
                "WHERE p.user_id = \'" + user_id + "\' " +
                "ORDER BY playlist_name";
        ResultSet rs = statement.executeQuery(query);

        LinkedList<Playlist> playlists = new LinkedList<>();
        String plName = "";
        if (rs.next()) {
            rs.first();
            plName = rs.getString("playlist_name");
            rs.beforeFirst();
        } else {
            return null;
        }

        Playlist pl = new Playlist(user_id, plName);

        while(rs.next()) {
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

    // Caricamento delle playlist

    // Registrazione - DONE
    public static void registerUser(String fn, String ln, String FC, String a, String email, String uid, String pwd) {
        try {
            String query = "INSERT INTO RegisteredUser VALUES (\'" + uid + "\', \'" + pwd + "\', \'" +  email + "\', \'" + fn + "\', \'" + ln + "\', \'" +  a + "\', \'" + FC + "\')";
            System.out.println(query);
            statement.executeUpdate(query);
            System.out.println("User " + uid + " registered successfully.");
        } catch (SQLException e) {
            System.err.println("The user " + uid + " already exists.");
            System.err.println(e.toString());
        }
    }

    // Registra feedback

    // Mostra feedback
}
