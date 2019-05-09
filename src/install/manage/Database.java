package install.manage;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Luan
 */
public class Database {
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String USER = "root";
    private static final String PASS = "";
    
    public static boolean create(){
        System.out.println("Creating database name: 'db_sistema_comercio'");
        try{
            Class.forName(DRIVER);
            DriverManager.getConnection(URL, USER, PASS).createStatement().executeUpdate("create database if not exists db_sistema_comercio");
            
            System.out.println("- database created successfully");
            return true;
        }
        catch(ClassNotFoundException exception){
            System.out.println("Error creating database");
            System.out.println("Connection Failed Class Not Found ; classes.Conexão: " + exception);
            return false;
        }
        catch(SQLException exception){
            System.out.println("Error creating database");
            System.out.println("Conection falied SQLException; classes.Conexão: " + exception);
            return false;
        }
    }
}
