package models;

import connectionDatabase.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.plaf.synth.SynthTextAreaUI;

/**
 *
 * @author Luan Rodrigues
 */
public class Vendedor {
    private int id;
    private String nome;
    private String cpf;
    private int id_login;
    private static final String ERROR_GET_VENDEDOR = "Error get vendedor:\n";
    
    public Vendedor(String cpf, String nome, int id_login){
        this.cpf = cpf;
        this.nome = nome;
        this.id_login = id_login;
        id = -1;
    }

    public Vendedor(String cpf){
        this.cpf = cpf;
        
        try {
            this.setVendor();
        } catch(SQLException exception) {
            JOptionPane.showMessageDialog(null, ERROR_GET_VENDEDOR + exception.toString());
            this.nome = null;
        }
    }
    
    public Vendedor(ResultSet result) {
        try {
            setVendedorFromResult(result);
        } catch(SQLException exception) {
            JOptionPane.showMessageDialog(null, ERROR_GET_VENDEDOR + exception.toString());
            this.nome = null;
        }
    }
    
    public String getNome(){
        return this.getNomeCapitalized();
    }
    
    public String getNomeCapitalized() {
        String nameCapitalized = "";
        for(String name: nome.split(" ")) {
            nameCapitalized += name.substring(0, 1).toUpperCase() + name.substring(1) + " ";
        }
        
        return nameCapitalized;
    }
    
    public boolean save(){
        if (!this.exists()) {
            if (this.nome != null)
                return this.inserirVendedor();
            else
                return false;
        }
        else
            return this.updateVendedor();
    }
    
    public boolean delete(){
        return this.removerVendedor();
    }
    
    public boolean exists(){
        Connection conn = Conexao.getConnection();
        String query = "SELECT * FROM VENDEDOR WHERE CPF = ? ";
        
        try {
            PreparedStatement sttm = conn.prepareStatement(query);
            
            sttm.setString(1, this.cpf);
            
            ResultSet result = sttm.executeQuery();
            
            result.first();
            result.getString("cpf");
            Conexao.closeConnection(conn, sttm, result);
            return true;
        }catch(SQLException ex){
            System.out.println("Error exists(): " + ex);
            Conexao.closeConnection(conn);
            return false;
        }
    }

    private boolean inserirVendedor(){
        Connection conn = Conexao.getConnection();
        String query = "INSERT INTO VENDEDOR VALUES (?, ?, ?)";

        try {
            PreparedStatement sttm = conn.prepareStatement(query);
            sttm.setString(1, cpf);
            sttm.setString(2, nome);
            sttm.setInt(3, id_login);

            sttm.execute();
            Conexao.closeConnection(conn, sttm);
            return true;
        } catch(SQLException ex) {
            System.out.println("Error inserirVendedor " + ex);
            Conexao.closeConnection(conn);
            return false;
        }
    }

    private boolean updateVendedor(){
        Connection conn = Conexao.getConnection();
        String query = "UPDATE VENDEDOR SET CPF = ?, NOME = ?, ID_LOGIN = ? "
                + "WHERE CPF = ?";
        
        try {
            PreparedStatement sttm = conn.prepareStatement(query);
            sttm.setString(1, cpf);
            sttm.setString(2, nome);
            sttm.setInt(3, id_login);
            
            sttm.execute();
            Conexao.closeConnection(conn, sttm);
            return true;
        } catch(SQLException ex) {
            System.out.println("Error updateVendedor " + ex);
            Conexao.closeConnection(conn);
            return false;
        }
    }

    private boolean removerVendedor(){

        Connection conn = Conexao.getConnection();
        String deleteVendedor = "UPDATE VENDEDOR SET ATIVO = ? WHERE CPF = ? ";
        String deleteLogin = "UPDATE LOGIN SET ATIVO = ? WHERE ID = ? ";
        String query = "BEING " + deleteVendedor + deleteLogin + " COMMIT;";

        try{
            PreparedStatement sttm = conn.prepareStatement(query);

            sttm.setBoolean(1, false);
            sttm.setString(2, cpf);
            sttm.setBoolean(3, false);
            sttm.setInt(4, id_login);
            sttm.execute();
            Conexao.closeConnection(conn, sttm);
            return true;
        }catch(SQLException ex){
            System.out.println("Error removerVendedor " + ex);
            Conexao.closeConnection(conn);
            return false;
        }
    }
    
    private void setVendor() throws SQLException {
        var conn = Conexao.getConnection();
        String query = "SELECT * FROM VENDEDOR WHERE CPF = ? ";

        var sttm = conn.prepareStatement(query);
        sttm.setString(1, cpf);

        var result = sttm.executeQuery();
        result.next();

        setVendedorFromResult(result);
        Conexao.closeConnection(conn, sttm, result);
    }
    
    private void setVendedorFromResult(ResultSet result) throws SQLException {
        id = result.getInt("id");
        cpf = result.getString("cpf");
        nome = result.getString("nome");
        id_login = result.getInt("id_login");
    }

    public static ArrayList<Vendedor> all() {
        ArrayList<Vendedor> list;
        try {
            list = Vendedor.filterOnDatabase(null);
        } catch (SQLException e) {
            list = new ArrayList<>();
        }

        return list;
    }

    private static ArrayList<Vendedor> filterOnDatabase(String filter) throws SQLException {
        var query = prepareQuery(filter);

        var connection = Conexao.getConnection();
        var statement = connection.prepareStatement(query);
        var result = statement.executeQuery();

        var lista = new ArrayList<Vendedor>();
        while (result.next())
                lista.add(new Vendedor(result));
        return lista;
    }

    private static String prepareQuery(String filter) {
        var whereAtivo = " ATIVO = TRUE";
        if (filter == null)
            filter = " WHERE " + whereAtivo;
        else
            filter = " WHERE " + filter + " AND " + whereAtivo;

        return filter;
    }

    public static void createNewVendedor(String nome, String cpf, String username, String password) {
        try {
            createVendedorAndLogin(nome, cpf, username, password);
        } catch (SQLException e) {
            System.out.println("Error createNewVendedor : " + e);;
        }
    }

    private static void createVendedorAndLogin(String nome, String cpf, String username, String password) throws SQLException {
        var query = "SELECT * FROM CREATE_NEW_VENDEDOR(?,?,?,?);";

        var connection  = Conexao.getConnection();
        var statement = connection.prepareStatement(query);
        statement.setString(1, nome);
        statement.setString(2, cpf);
        statement.setString(3, username);
        statement.setString(4, password);
        statement.executeQuery();
    }
}