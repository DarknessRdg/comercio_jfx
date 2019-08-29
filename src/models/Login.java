package models;

import java.sql.*;

import connectionDatabase.Conexao;
import java.util.ArrayList;
import javax.print.DocFlavor;
import javax.swing.JOptionPane;


/**
 *
 * @author Luan Rorigues
 */
public class Login {
    private String username;
    private String password;
    private int id;

    protected Login(int id, String username, String password) {
        this.username = username;
        this.password = password;
        this.id = id;
    }

    public Login(String username, String password) {
        this.username = username;
        this.password = password;
        this.id = -1;
    }
    
    public Login(ResultSet result) {
        Login newLogin = Login.getLogin(result);
        if (newLogin == null )
            return;
        username = newLogin.getUsername();
        password = newLogin.getPassword();
    }

    public int getId() {
        return id;
    }
    
    private String getUsername() {
        return username;
    }
    
    private String getPassword() {
        return password;
    }
    
    public boolean isUserAuthenticated() {
        for(Login loginLoop: Login.all()) {
            var usernamesEquals = loginLoop.getUsername().equals(username);
            var passwordsEquals = loginLoop.getPassword().equals(password);
            if (usernamesEquals && passwordsEquals)
                return true;
        }
        return false;
    }

    public void save() {
        if(!alreadyCreated())
            insert();
        else
            update();
    }

    private boolean alreadyCreated() {
        for(Login login: Login.all()) {
            if (login.getUsername().equals(username))
                return true;
        }
        return false;
    }
   
    private void insert() {
        try {
            insertLoginOnDatabase();
        } catch (SQLException ex ) {
            JOptionPane.showMessageDialog(null, "Error inserir novo Login : " + ex);
        } 
    }
    
    private void insertLoginOnDatabase() throws SQLException {
        String query = "INSERT INTO LOGIN(USERNAME, PASSWORD) VALUES (?, ?)";
        Connection connection = Conexao.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, username);
        statement.setString(2, password);
        
        statement.execute();
    }
    
    private void update() {
        try {
            updateLoginOnDatabase();
        } catch (SQLException ex ){
            JOptionPane.showMessageDialog(null, "Error update Login : " + ex);
        } 
    }

    private void updateLoginOnDatabase() throws SQLException {
        String query = "UPDATE LOGIN SET USERNAME = ? AND PASSWORD = ? WHERE USERNAME = ? AND ATIVO = TRUE";
        Connection connection = Conexao.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, username);
        statement.setString(2, password);
        statement.setString(3, username);

        statement.execute();
    }

    public void delete() {
        try {
            deleteLoginOnDatabase();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error delete Login : " + ex);
        }
    }

    private void deleteLoginOnDatabase() throws SQLException {
        var query = "UPDATE LOGIN SET ATIVO = ? WHERE USERNAME = ?";
        var connection = Conexao.getConnection();
        var statement = connection.prepareStatement(query);
        statement.setBoolean(1, false);
        statement.setString(2, username);
        statement.execute();

        Conexao.closeConnection(connection, statement);
    }
    
    public Vendedor getVendedor() {
        try{
            return getVendedorOnDatabase();
        } catch(SQLException exceptiton) {
            System.out.println("Error getVendedor on login.Login: " + exceptiton);
            return null;
        }
    }
    
    private Vendedor getVendedorOnDatabase() throws SQLException {
        String query = "SELECT V.*, ID_LOGIN FROM LOGIN L JOIN VENDEDOR V ON L.ID = V.ID_LOGIN"
                + " WHERE L.USERNAME = ?";

        Connection connection = Conexao.getConnection();
        PreparedStatement statment = connection.prepareStatement(query);
        statment.setString(1, username);
        ResultSet result = statment.executeQuery();
        result.next();
        
        Vendedor vendedor = new Vendedor(result);
        Conexao.closeConnection(connection, statment, result);
        
        return vendedor;
    }
    
    public static Login getLogin(ResultSet result) {
        Login newLogin = null;
        try {
            newLogin = getLoginFromResult(result);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error getLogin(ResultSet): " + ex);
        }
        
        return null;
    }
    
    public static ArrayList<Login> all() {
        ArrayList<Login> lista;
        try {
            lista = Login.filterOnDataBase(null);
        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(null, "Error getAll Login: " + exception);
            lista = new ArrayList<>();
        }
        return lista;
    }

    private static ArrayList<Login> filterOnDataBase(String filter) throws SQLException {
        ArrayList<Login> queryList = new ArrayList<>();
        String query = prepareQuery(filter);

        Connection connection = Conexao.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet result = statement.executeQuery();
        
        while (result.next())
            queryList.add(Login.getLoginFromResult(result));
        
        Conexao.closeConnection(connection, statement, result);
        return queryList;
    }

    private static String prepareQuery(String filter) {
        var whereAtivo = " ATIVO = TRUE";
        filter = filter == null ? " WHERE" + whereAtivo : filter + whereAtivo;

        return "SELECT * FROM LOGIN " + filter;
    }
    
    private static Login getLoginFromResult(ResultSet result) throws SQLException {
        Login newLogin = new Login(result.getString("username"), result.getString("password"));
        return newLogin;
    }

    public static Login get(String username) throws Exception {
        for(Login loginLoop: Login.all()) {
            var usernameEquals = loginLoop.getUsername().equals(username);
            if (usernameEquals)
                return loginLoop;
        }
        throw new Exception("User not found with username '" + username + "'");
    }

    public static Login get(int id) throws Exception {
        for(Login loginLoop: Login.all()) {
            var usernameEquals = loginLoop.getId() == id;
            if (usernameEquals)
                return loginLoop;
        }

        throw new Exception("User not found with id '" + id + "'");
    }
}
