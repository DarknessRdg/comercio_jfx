package install.manage;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Luan
 */
public class Database {
    private static final String DRIVER = "org.postgresql.Driver";
    private static final String URL = "jdbc:postgresql://localhost:5432/";
    private static final String USER = "postgres";
    private static final String PASS = "Newera1.0";
    
    public static boolean create(){
        System.out.println("Creating database name: 'db_sistema_comercio'");
        try{
            Class.forName(DRIVER);
            DriverManager.getConnection(URL, USER, PASS).createStatement().executeUpdate("create database db_sistema_comercio");
            
            System.out.println("- database created successfully");
            return true;
        }
        catch(ClassNotFoundException exception){
            System.out.println("Error creating database");
            System.out.println("Connection Failed Class Not Found ; classes.Conexão: " + exception);
            return false;
        }
        catch(SQLException exception){
            if (exception.toString().contains("database \"db_sistema_comercio\" already exists")) {
                System.out.println("- database already exists!");
                return false;
            }
            
            System.out.println("Error creating database - sql");
            System.out.println("Conection falied SQLException; classes.Conexão: " + exception);
            throw new RuntimeException("Error create database " + exception);
        }
    }
}
