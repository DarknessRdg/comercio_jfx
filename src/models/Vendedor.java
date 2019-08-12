package models;

import connectionDatabase.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Luan
 */
public class Vendedor {
    private String nome;
    private String cpf;
    private int id_login;
    private static final String ERROR_GET_VENDEDOR = "Error get vendedor:\n";
    
    public Vendedor(String cpf, String nome, int id_login){
        this.cpf = cpf;
        this.nome = nome;
        this.id_login = id_login;
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
        return this.nome;
    }
    
    public String getNomeCapitalized() {
        String nameCapitalized = "";
        for(String name: nome.split(" ")) {
            nameCapitalized += name.substring(0, 1).toUpperCase() + name.substring(1) + " ";
        }
        
        return nameCapitalized;
    }
    
    public boolean save(){
        if(!this.exists()){
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
        
        try{
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
   
    private boolean removerVendedor(){
        if(!this.removerLogin())
            return false;  // nao foi possivel remover login (delete em cascata)
        
        Connection conn = Conexao.getConnection();
        String query = "DELETE FROM VENDEDOR WHERE COD_BARRAS = ?";
        try{
            PreparedStatement sttm = conn.prepareStatement(query);
            
            sttm.setString(1, this.cpf);
            sttm.execute();
            Conexao.closeConnection(conn, sttm);
            return true;
        }catch(SQLException ex){
            System.out.println("Error removerVendedor " + ex);
            Conexao.closeConnection(conn);
            return false;
        }    
    }
    
    private boolean removerLogin(){
        Connection conn = Conexao.getConnection();
        String query = "DELETE FROM LOGIN WHERE ID_LOGIN = ?";
        
        try{
            PreparedStatement sttm = conn.prepareStatement(query);
            
            sttm.setInt(1, this.id_login);
            sttm.execute();
            Conexao.closeConnection(conn, sttm);
            return true;
        }catch(SQLException ex){
            System.out.println("Error removerLogin " + ex);
            Conexao.closeConnection(conn);
            return false;
        }
    }
    
    private boolean updateVendedor(){
        Connection conn = Conexao.getConnection();
        String query = "UPDATE VENDEDOR SET CPF = ?, NOME = ?, ID_LOGIN = ? "
                + "WHERE CPF = ?";
        
        try{
            PreparedStatement sttm = conn.prepareStatement(query);
            sttm.setString(1, this.cpf);
            sttm.setString(2, this.nome);
            sttm.setInt(3, this.id_login);
            
            sttm.execute();
            Conexao.closeConnection(conn, sttm);
            return true;
        }catch(SQLException ex){
            System.out.println("Error updateVendedor " + ex);
            Conexao.closeConnection(conn);
            return false;
        }
    }
    
    private boolean inserirVendedor(){
        Connection conn = Conexao.getConnection();
        String query = "INSERT INTO VENDEDOR VALUES (?, ?, ?)";
        
        try{
            PreparedStatement sttm = conn.prepareStatement(query);
            sttm.setString(1, this.cpf);
            sttm.setString(2, this.nome);
            sttm.setInt(3, this.id_login);
            
            sttm.execute();
            Conexao.closeConnection(conn, sttm);
            return true;
        }catch(SQLException ex){
            System.out.println("Error inserirVendedor " + ex);
            Conexao.closeConnection(conn);
            return false;
        }
    }
    
    private void setVendor() throws SQLException {
        Connection conn = Conexao.getConnection();
        PreparedStatement sttm = null;
        ResultSet result = null;
        String query = "SELECT * FROM VENDEDOR WHERE CPF = ? ";
        
        try{
            sttm = conn.prepareStatement(query);
            sttm.setString(1, this.cpf);
            
            result = sttm.executeQuery();
            result.next();
            
            setVendedorFromResult(result);
        }catch(SQLException ex){
            System.out.println("Error getVendedor " + ex);
            
        }
        Conexao.closeConnection(conn, sttm, result);
    }
    
    private void setVendedorFromResult(ResultSet result) throws SQLException {
        cpf = result.getString("cpf");
        nome = result.getString("nome");
        id_login = result.getInt("id_login");
    }
}
