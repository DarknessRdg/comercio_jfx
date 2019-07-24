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
public class Login {
    private int id;
    private String login;
    private String senha;
    
    
    public Login(int id){
        this.id = id;
        this.setLogin(id);
    }
    
    public Login(String login){
        this.login = login;
        this.setLogin();
    }
    
    public Login(String login, String senha){
        this.login = login;
        this.senha = senha;
        this.id = -1;
    }
    
    public String getLogin(){
        return this.login;
    }
    
    public int getId(){
        return this.id;
    }
    
    public void mudarSenha(String nova){
        this.senha = nova;
    }
    
    public boolean save(){
        if(!this.exists()){
            if(this.login != null && this.senha != null)
                return this.inserirLogin();
            else
                return false;
        }
        else
            return this.updateLogin();
    }
    
    public boolean delete(){
        return this.removerLogin();
    }
    
    private boolean updateLogin(){
        Connection conn = Conexao.getConnection();
        String query = "UPDATE LOGIN SET SENHA = ? WHERE ID_LOGIN = ?";
        try{
            PreparedStatement sttm = conn.prepareStatement(query);
            sttm.setString(1, senha);
            sttm.setInt(2, id);
            
            sttm.execute();
            Conexao.closeConnection(conn, sttm);
            return true;
        }catch(SQLException ex){
            System.out.println("Error updateLogin " + ex);
            
            Conexao.closeConnection(conn);
            return false; 
        }
    }
    
    private boolean removerLogin(){
        Connection conn = Conexao.getConnection();
        String query = "DELETE FROM LOGIN WHERE ID_LOGIN = ?";
        
        try{
            PreparedStatement sttm = conn.prepareStatement(query);
            
            sttm.setInt(1, this.id);
            sttm.execute();
            
            Conexao.closeConnection(conn, sttm);
            return true;
        }catch(SQLException ex){
            System.out.println("Error removerLogin " + ex);
            Conexao.closeConnection(conn);
            return false;
        }
    }
    private boolean inserirLogin(){
        Connection conn = Conexao.getConnection();
        String query = "INSERT INTO LOGIN(USERNAME, SENHA) VALUES (?, ?) ";
        
        try{
            PreparedStatement sttm = conn.prepareStatement(query);
            
            sttm.setString(1, this.login);
            sttm.setString(2, this.senha);
            
            sttm.execute();
            
            this.setLogin();
            Conexao.closeConnection(conn, sttm);
            return true;
        }catch(SQLException ex){
            System.out.println("Error inserirLogin " + ex);
            Conexao.closeConnection(conn);
            return false;
        }
    }
    
    private boolean exists(){
        Connection conn = Conexao.getConnection();
        String query = "SELECT * FROM LOGIN WHERE LOGIN = ? ";
        
        try{
            PreparedStatement sttm = conn.prepareStatement(query);
            
            sttm.setString(1, this.login);
            
            ResultSet result = sttm.executeQuery();
            
            result.first();
            result.getInt("id");
            Conexao.closeConnection(conn, sttm);
            return true;
        }catch(SQLException ex){
            Conexao.closeConnection(conn);
            return false;
        }
    }
    
    private void setLogin(int id){
        Connection conn = Conexao.getConnection();
        String query = "SELECT * FROM LOGIN WHERE ID = ? ";
        
        try{
            PreparedStatement sttm = conn.prepareStatement(query);
            
            sttm.setInt(1, this.id);
            
            ResultSet result = sttm.executeQuery();
            
            result.first();
            this.id = result.getInt("id");
            this.login = result.getString("username");
            this.senha = result.getString("senha");
            
            Conexao.closeConnection(conn, sttm);
        }catch(SQLException ex){
            System.out.println("Error setLogin" + ex);
            this.login = null;
            this.senha = null;
            
            Conexao.closeConnection(conn);
        }
    }
    
    private void setLogin(){
        Connection conn = Conexao.getConnection();
        String query = "SELECT * FROM LOGIN WHERE LOGIN = ? ";
        
        try{
            PreparedStatement sttm = conn.prepareStatement(query);
            
            sttm.setString(1, this.login);
            
            ResultSet result = sttm.executeQuery();
            
            result.first();
            this.id = result.getInt("id_login");
            this.login = result.getString("login");
            this.senha = result.getString("senha");
            
            Conexao.closeConnection(conn, sttm);
        }catch(SQLException ex){
            System.out.println("Error setLogin" + ex);
            this.senha = null;
            this.id = -1;
            
            Conexao.closeConnection(conn);
        }
    }
}
