package connectionDatabase;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;

/**
 *
 * @author Luan Rodrigues
 */
public class Conexao {
    private static final String DRIVER = "org.postgresql.Driver";
    private static final String URL = "jdbc:postgresql://localhost:5432/db_sistema_comercio";
    private static final String USER = "postgres";
    private static final String PASS = "Newera1.0";
    private static final String ERROR_CONNECT_TO_DATABASE = "Error ao tentar se connectar com banco de dados."
            + "Feche o programa e abra novamente!\n";
    private static final String ERROR_CLOSE_CONNECTION = "Close Connection Failed:"
            + "Feche o programa e abra novamente!\n";
    
    public static Connection getConnection(){
        try { 
            return getConnectionDatabase();
        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(null, ERROR_CONNECT_TO_DATABASE + exception);
        }
        return null;
    }
    
    public static void closeConnection(Connection connection){
        if(connection != null){
            try{
                closeConnectionToDatabase(connection);
            }
            catch(SQLException exception){
                JOptionPane.showMessageDialog(null, ERROR_CLOSE_CONNECTION + exception.toString());
            }
        }
    }
    
    public static void closeConnection(Connection connection, PreparedStatement statement){
        if(statement != null){
            try{
                closePreparedStatement(statement);
            }
            catch(SQLException exception){
                JOptionPane.showMessageDialog(null, ERROR_CLOSE_CONNECTION + exception.toString());
            }
        }
        closeConnection(connection);
    }
    
    public static void closeConnection(Connection connection, PreparedStatement statement, ResultSet result){
        if (result != null) {
            try{
                closeConnection(connection, statement);
                closeResultSet(result);
            }
            catch(SQLException exception){
                JOptionPane.showMessageDialog(null, ERROR_CLOSE_CONNECTION + exception.toString());
            }
        }
        closeConnection(connection, statement);
    }
    
    private static Connection getConnectionDatabase() throws SQLException {
        Connection connection = null;
        try {
            getClassToConnect();
            connection = DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException exception) {
           JOptionPane.showMessageDialog(null, ERROR_CLOSE_CONNECTION + exception.toString());
        }
        
        return connection;
    }
    
    private static void getClassToConnect() throws ClassNotFoundException {
        Class.forName(DRIVER);
    }
    
    private static void closeConnectionToDatabase(Connection connection) throws SQLException {
        connection.close();
    }
    
    private static void closePreparedStatement(PreparedStatement statement) throws SQLException {
        statement.close();
    }
    
    private static void closeResultSet(ResultSet result) throws SQLException {
        result.close();
    }
}
