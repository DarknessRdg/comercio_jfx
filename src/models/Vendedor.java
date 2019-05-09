package models;

import classes.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Luan
 */
public class Vendedor {
    private String nome;
    private String cpf;
    private int id_login;
    
    public Vendedor(String cpf){
        this.cpf = cpf;
        this.setVendor();
    }
    
    public Vendedor(String cpf, String nome, int id_login){
        this.cpf = cpf;
        this.nome = nome;
        this.id_login = id_login;
    }
    
    public String getNome(){
        return this.nome;
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
            return true;
        }catch(SQLException ex){
            System.out.println("Error exists(): " + ex);
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
            return true;
        }catch(SQLException ex){
            System.out.println("Error removerVendedor " + ex);
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
            return true;
        }catch(SQLException ex){
            System.out.println("Error removerLogin " + ex);
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
            return true;
        }catch(SQLException ex){
            System.out.println("Error updateVendedor " + ex);
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
            return true;
        }catch(SQLException ex){
            System.out.println("Error inserirVendedor " + ex);
            return false;
        }
    }
    
    private void setVendor(){
        Connection conn = Conexao.getConnection();
        String query = "SELECT * FROM VENDEDOR WHERE CPF = ? ";
        
        try{
            PreparedStatement sttm = conn.prepareStatement(query);
            
            sttm.setString(1, this.cpf);
            
            ResultSet result = sttm.executeQuery();
            
            result.first();
            this.cpf = result.getString("cpf");
            this.nome = result.getString("nome");
            this.id_login = result.getInt("id_login");
            
        }catch(SQLException ex){
            System.out.println("Error getVendedor " + ex);
            this.nome = null;
        }
    }
}
