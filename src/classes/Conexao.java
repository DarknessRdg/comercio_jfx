package classes;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;

/**
 *
 * @author Luan
 */
public class Conexao {
    private static final String DRIVER = "org.postgresql.Driver";
    private static final String URL = "jdbc:postgresql://localhost:5432/db_sistema_comercio";
    private static final String USER = "postgres";
    private static final String PASS = "Newera1.0";
    
    public static Connection getConnection(){
        try{
            Class.forName(DRIVER);
            return DriverManager.getConnection(URL, USER, PASS);
        }
        catch(ClassNotFoundException exception){
            throw new RuntimeException("Connection Failed Class Not Found ; classes.Conexão: " + exception);
        }
        catch(SQLException exception){
            System.out.println("Close Connection Failed: " + exception.toString());
        }
        return null;
    }
       
    public static void closeConnection(Connection conn){
        if(conn != null){
            try{
                conn.close();
            }
            catch(SQLException exception){
                System.out.println("Close Connection Failed: " + exception.toString());
            }
        }
    }
    
    public static void closeConnection(Connection conn, PreparedStatement command){
        if(command != null){
            try{
                command.close();
            }
            catch(SQLException exception){
                System.out.println("Close Connection Failed: " + exception.toString());
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
                System.out.println("Close Connection Failed: " + exception.toString());
            }
            
            closeConnection(conn, command);
        }
    }
}
