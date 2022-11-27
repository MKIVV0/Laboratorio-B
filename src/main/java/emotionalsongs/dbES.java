package emotionalsongs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class dbES {

    private final static String protocol 	= "jdbc:postgresql" + "://";
    private final static String host		= "localhost" + "/";
    private final static String resource	= "EmotionalSongs";

    private final static String url  = protocol + host + resource;
    private final static String user = "postgres";
    private final static String pass = "bl4ckvultur3";

    private static dbES database;
    private static Connection connection;
    private static Statement statement;

    // Questo costruttore, grazie al metodo getInstance, potrà
    // essere invocato una volta sola.
    private dbES() throws SQLException {

        connection = DriverManager.getConnection(url, user, pass);
        statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
    }

    // Sfrutto il pattern Singleton per assicurarmi di gestire la
    // comunicazione con il db in modo centralizzato.
    public static dbES getInstance() throws SQLException {

        if (database == null) {
            database = new dbES();
        }

        return database;
    }

    public void initializeDB() throws SQLException {
        statement.execute("DROP IF EXISTS TABLE " + resource);
        statement.execute("CREATE TABLE " + resource);

    }

    // TODO: mancano i metodi di chiusura della connection e della statement
    // TODO: mancano i metodi per rigenerare la statement e per sottomettere una query

    public ResultSet submitQuery(String query) throws SQLException {

        if ( statement.execute(query) ) {

            return statement.getResultSet();
        }

        return null;
    }
}
