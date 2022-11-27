package emotionalsongs;

import common.LoggedUser;
import common.Playlist;
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

    // Controllo credenziali
    // Verifica la presenza di una tupla nella tabella registereduser. Ritorna l'oggetto, se l'utente è
    // presente nella tabella, lancia un'eccezione altrimenti.
    public static synchronized LoggedUser getLoggedUser(String user, String pwd) throws SQLException, WrongCredentialsException {
        String query1 = "SELECT * FROM registereduser WHERE user_id = '" + user + "' AND ' password = '" + pwd + "\'";
        ResultSet rs = statement.executeQuery(query1);
        LoggedUser tmp;

        if (!rs.next()) {
            throw new WrongCredentialsException("The user " + user + " doesn't exist!");
        }
        return reconstructLoggedUser(rs);
    }

    // Ricostruzione utenti
    private static LoggedUser reconstructLoggedUser(ResultSet rs) throws SQLException {
        LoggedUser tmp = null;
        String firstName = null;
        /**
         * Attributo rappresentate il cognome dell'utente registrato.
         */
        String lastName = null;
        /**
         * Attributo rappresentate il codice fiscale dell'utente registrato.
         */
        String FC = null;
        /**
         * Attributo rappresentate l'indirizzo fisico dell'utente registrato.
         */
        String address = null;
        /**
         * Attributo rappresentate l'indirizzo email dell'utente registrato.
         */
        String email = null;
        /**
         * Attributo rappresentate l'identificativo dell'utente registrato.
         */
        String userID = null;
        /**
         * Attributo rappresentate la password dell'utente registrato.
         */
        String password = null;
        /**
         * Attributo rappresentate la lista di playlist associata.
         * all'utente registrato.
         */
        LinkedList<Playlist> playlistList = null;
/*
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        while (rs.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                String columnValue = rs.getString(i);
                if (i == 1) firstName = columnValue;
                else if (i == 2) lastName = columnValue;
                else if (i == 3) FC = columnValue;
                else if (i == 4) address = columnValue;
                else if (i == 5) email = columnValue;
                else if (i == 6) userID = columnValue;
                else if (i == 7) password = columnValue;
                else playlistList.add(new Playlist(columnValue));
            }
            tmp = new LoggedUser(firstName, lastName, FC, address, email, userID, password);
            System.out.println("");
        }*/

        return tmp;
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
