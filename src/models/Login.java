package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import connectionDatabase.Conexao;
import java.io.IOException;
import java.util.ArrayList;
import javafx.scene.Cursor;
import javax.swing.JOptionPane;


/**
 * Class to verify login to database of given username and password
 * @author Luan Rorigues
 */
public class Login {
    private String loginDB;
    private String senhaDB;
    private String login;
    private String senha;
    private int id;
    
    public Login(String login, String senha) {
        loginDB = "-z }{/;d s kJDdsUnsJOOUsndsçald 2 23 32 sd/.  sa";
        senhaDB = "-z;}{/d s kJDdsUnsJOOUsndsçald 2 23 32 sd/. sadsa213231s";
        this.login = login;
        this.senha = senha;
        this.id = -1;
        
        this.getLoginOnDB();
    }
    
    public Login(ResultSet result) {
        Login newLogin = Login.getLogin(result);
        if (newLogin == null )
            return;
        loginDB = newLogin.getLoginDB();
        senhaDB = newLogin.getSenhaDB();
    }
    
    public String getLogin() {
        return loginDB;
    }
    
    private String getLoginDB() {
        return loginDB;
    }
    
    private String getSenhaDB() {
        return senhaDB;
    }
    
    public boolean logued(){
        return loginDB.equals(login) && senhaDB.equals(senha) && id > -1;
    }
    
    public Vendedor getVendedor() {
        try{
            return getVendedorOnDatabase();
        } catch(SQLException exceptiton) {
            System.out.println(exceptiton);
            return null;
        }
    }
    
    private void getLoginOnDB(){
        try{
            selectLoginOnDatabase();
        }catch(SQLException exception){
            JOptionPane.showMessageDialog(null, "ERROR GETLOGIN ON DB: " + exception);
        }
    }
    
    private void selectLoginOnDatabase() throws SQLException {
        Connection conn = Conexao.getConnection();
        String query = "SELECT * FROM LOGIN WHERE USERNAME = '" + login.toLowerCase() + "'";
        ResultSet result;
        
        PreparedStatement sttm = conn.prepareStatement(query);
        result = sttm.executeQuery();
        
        if(result.next()) {
            loginDB = result.getString("username");
            senhaDB = result.getString("senha");
            id = result.getInt("id");
        }
        Conexao.closeConnection(conn, sttm, result);
    }
    
    private Vendedor getVendedorOnDatabase() throws SQLException {
        Connection conn = Conexao.getConnection();
        String query = "SELECT V.*, ID_LOGIN FROM LOGIN L JOIN VENDEDOR V ON L.ID = V.ID_LOGIN"
                + " WHERE L.USERNAME = '" + login.toLowerCase() + "'";
        
        PreparedStatement sttm = conn.prepareStatement(query);
        ResultSet result = sttm.executeQuery();
        result.next();
        
        Vendedor vendedor = new Vendedor(result);
        Conexao.closeConnection(conn, sttm, result);
        
        return vendedor;
    }
    
    public static Login getLogin(ResultSet result) {
        Login newLogin = null;
        try {
            newLogin = Login.getLoginFromResult(result);
            result.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error getLogin(ResultSet): " + ex);
        }
        
        return null;
    }
    
    public static ArrayList<Login> all() {
        ArrayList<Login> lista = new ArrayList<>();
        try {
            lista = Login.filterOnDataBase(null);
        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(null, "Error getAll Login: " + exception);
        }
        return lista;
    }
    
    public static ArrayList<Login> filter(String filter) {
        ArrayList<Login> lista = new ArrayList<>();
        try {
            lista = Login.filterOnDataBase(filter);
        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(null, "Error getFilter Login: " + exception);
        }
        return lista;
    }
    
    private static ArrayList<Login> filterOnDataBase(String filter) throws SQLException {
        ArrayList<Login> queryList = new ArrayList<>();
        filter = filter == null ? " " : filter;
        
        String query = "SELECT * FROM LOGIN " + filter;
        
        Connection connection = Conexao.getConnection();
        PreparedStatement sttm = connection.prepareStatement(query);
        ResultSet result = sttm.executeQuery();
        
        while (result.next())
            queryList.add(Login.getLoginFromResult(result));
        
        Conexao.closeConnection(connection, sttm, result);
        return queryList;
    }
    
    private static Login getLoginFromResult(ResultSet result) throws SQLException {
        Login newLogin = new Login(result.getString("username"), result.getString("senha"));
        return newLogin;
    }
}
