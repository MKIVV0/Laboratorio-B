package emotionalsongs;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

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

    public static void createDB(Statement st) {
        try {
            if (st.execute("CREATE DATABASE EmotionalSongs"))
                System.out.println("EmotionalSongs DB successfully created.");
        } catch (SQLException e) {
            System.out.println("EmotionalSongs DB already exists.");
        }
    }

    public static void initializeTables(Statement st, String scriptPath1, String scriptPath2) throws SQLException, IOException {
        String script = null;
        String line;
        BufferedReader br = new BufferedReader(new FileReader(scriptPath1));

        while ((line = br.readLine()) != null) {
            if (script == null) script = line;
            else script += line;
        }

        st.executeUpdate(script);

        // Esegue le insert se la tabella Song è vuota
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
    public static synchronized void getLoggedUser(String user, String pwd) throws SQLException {

    }
}