package classes;

import com.mysql.jdbc.PreparedStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;

/**
 *
 * @author Luan
 */
public class Conexao {
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/db_sistema_comercio";
    private static final String USER = "root";
    private static final String PASS = "";
    
    public static Connection getConnection(){
        try{
            Class.forName(DRIVER);
            return DriverManager.getConnection(URL, USER, PASS);
        }
        catch(ClassNotFoundException exception){
            throw new RuntimeException("Connection Failed Class Not Found ; classes.Conexão: " + exception);
        }
        catch(SQLException exception){
            throw new RuntimeException("Conection falied SQLException; classes.Conexão: " + exception);
        }
    }
       
    private static void closeConnection(Connection conn){
        if(conn != null){
            try{
                conn.close();
            }
            catch(SQLException exception){
                throw new RuntimeException("Close Connection Failed: " + 
                        exception);
            }
        }
    }
    
    public static void closeConnection(Connection conn, PreparedStatement command){
        if(command != null){
            try{
                command.close();
            }
            catch(SQLException exception){
                throw new RuntimeException("Close Connection Failed: " + 
                        exception);
            }
            closeConnection(conn);
        }
    }
    
    public static void closeConnection(Connection conn, PreparedStatement command, ResultSet result){
        if(command != null){
            try{
                result.close();
            }
            catch(SQLException exception){
                throw new RuntimeException("Close Connection Failed: " + exception);
            }
            
            closeConnection(conn, command);
        }
    }
}
