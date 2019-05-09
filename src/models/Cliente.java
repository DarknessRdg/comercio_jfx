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
public class Cliente {
    private String cpf;
    private String nome;
    private String telefone;
    
    public Cliente(String cpf){
        this.setCliente(cpf);
    }
    
    public Cliente(String cpf, String nome, String telefone){
        this.cpf = cpf;
        this.nome = nome;
        this.telefone = telefone;
    }
    
    public String getCpf(){ return this.cpf;}
    
    public String getNome(){ return this.nome;}
    
    public String getTelefone(){ return this.telefone; }
    
    public boolean save(){
        if (!this.exists()){
            if(this.nome != null)
                return this.inserirCliente();
            else
                return false;
        }else
            return this.updateCliente();
    }
    
    public boolean delete(){
        return this.removerCliente();
    }
    
    private boolean updateCliente(){
        Connection conn = Conexao.getConnection();
        String query = "UPDATE CLIENTE SET CPF = ?, NOME = ?, TELEFONE = ? "
                + "WHERE cpf = ? ";
        
        try{
            PreparedStatement sttm = conn.prepareStatement(query);
            sttm.setString(1, cpf);
            sttm.setString(2, nome);
            sttm.setString(3, telefone);
            sttm.setString(4, cpf);
            
            sttm.execute();
            return true;
        }catch(SQLException ex){
            System.out.println("Error updateCliente " + ex);
            return false;
        }
    }
    
    private boolean removerCliente(){
        Connection conn = Conexao.getConnection();
        String query = "DELETE FROM CLIENTE WHERE cpf = ?";
        
        try{
            PreparedStatement sttm = conn.prepareStatement(query);
            
            sttm.setString(1, cpf);
            sttm.execute();
            return true;
        }catch(SQLException ex){
            System.out.println("Error removerCliente " + ex);
            return false;
        }
    }
    
    private boolean inserirCliente(){
        Connection conn = Conexao.getConnection();
        String query = "INSERT INTO CLIENTE VALUES(?, ?, ?)";
        
        try{
            PreparedStatement sttm = conn.prepareStatement(query);
            
            sttm.setString(1, cpf);
            sttm.setString(2, nome);
            sttm.setString(3, telefone);
            
            sttm.execute();
            return true;
        }catch(SQLException ex){
            System.out.println("Error inserir " + ex);
            return false;
        }
    }
    
    private boolean setCliente(String cpf){
        Connection conn = Conexao.getConnection();
        
        String query = "SELECT * FROM CLIENTE WHERE cpf = ? ";
        
        try{
            PreparedStatement sttm = conn.prepareStatement(query);
            
            sttm.setString(1, cpf);
            
            ResultSet result = sttm.executeQuery();
            
            result.first();
            this.cpf = result.getString("cpf");
            this.nome = result.getString("nome");
            this.telefone = result.getString("telefone");
            
            return true;
            
        }catch(SQLException ex){
            System.out.println("Error setCliente " + ex);
            this.nome = null;
            this.telefone = null;
            return false;
        }
    }
    
    public boolean exists(){        
        Connection conn = Conexao.getConnection();
        String query = "SELECT * FROM CLIENTE WHERE cpf = ? ";
        
        try{
            PreparedStatement sttm = conn.prepareStatement(query);
            
            sttm.setString(1, this.cpf);
            
            ResultSet result = sttm.executeQuery();
            
            result.first();
            result.getString("cpf");
            return true;
            
        }catch(SQLException ex){
            return false;
        }
    }
}
