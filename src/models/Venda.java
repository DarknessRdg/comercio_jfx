package models;

import classes.Conexao;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import javax.swing.JOptionPane;

/**
 *
 * @author Luan
 */
public class Venda {
    private int id;
    private Date date;
    private String cpfCliente;
    private String cpfVendedor;
    
    
    public Venda(int idVenda){
        this.getVenda(idVenda);
    }
    
    public Venda(String cpfCliente, String cpfVendedor){
        this.cpfCliente = cpfCliente;
        this.cpfVendedor = cpfVendedor;
        this.date = Date.valueOf(LocalDate.now());
        this.id = this.getLastIdVenda() + 1;
    }
    
    public int getIdVenda(){
        return this.id;
    }
    
    public boolean save(){
        if(cpfCliente == null || cpfVendedor == null)
            return false; 
        
        boolean existCliente = new Cliente(cpfCliente).exists();
        boolean existVendedor = new Vendedor(cpfVendedor).exists();
        
        if (existCliente && existVendedor && this.id > 0){
            if (!this.exists())
                return this.inserirVenda();
        }
        return false;
    }
    
    public boolean delete(){
        return this.removerVenda();
    }
    
    private boolean removerVenda(){
        Connection conn = Conexao.getConnection();
        String query = "DELETE FROM VENDA WHERE ID_VENDA = ?";
        
        try{
            PreparedStatement sttm = conn.prepareStatement(query);
            
            sttm.setInt(1, this.id);
            sttm.execute();
            return true;
        }catch(SQLException ex){
            System.out.println("Error removerVenda " + ex);
            return false;
        }
    }
    
    private boolean exists(){
        Connection conn = Conexao.getConnection();
        String query = "SELECT * FROM VENDA WHERE ID_VENDA = ? ";
        
        try{
            PreparedStatement sttm = conn.prepareStatement(query);
            
            sttm.setInt(1, this.id);
            
            ResultSet result = sttm.executeQuery();
            
            result.first();
            result.getString("id_venda");
            Conexao.closeConnection(conn, sttm, result);
            return true;
        }catch(SQLException ex){
            System.out.println("Error exists(): " + ex);
            Conexao.closeConnection(conn);
            return false;
        }
    }
    private boolean inserirVenda(){
        Connection conn = Conexao.getConnection();
        String query = "INSERT INTO VENDA VALUES(?, ?, ?, ?)";
        PreparedStatement sttm;
        
        try{
            sttm = conn.prepareStatement(query);
            
            sttm.setInt(1, id);
            sttm.setString(2, cpfCliente);
            sttm.setString(3, cpfVendedor);
            sttm.setDate(4, this.date);
            
            sttm.execute();
            Conexao.closeConnection(conn, sttm);
            return true;
        }catch(SQLException ex){
            Conexao.closeConnection(conn);
            JOptionPane.showMessageDialog(null, "Error InserirVenda: " + ex);
            return false;
        }
        
    }
    
    private void getVenda(int id){
        Connection conexao = Conexao.getConnection();
        String query = "SELECT * FROM VENDA WHERE ID_VENDA = ?";
        PreparedStatement sttm;
        try{
            sttm = conexao.prepareStatement(query);
            sttm.setInt(1, id);
            
            ResultSet result = sttm.executeQuery();
            result.first();
            
            this.id = result.getInt("id_venda");
            this.cpfCliente = result.getString("id_cliente");
            this.cpfVendedor = result.getString("id_vendedor");
            this.date = result.getDate("data_compra");
            Conexao.closeConnection(conexao, sttm);
        }catch (SQLException ex){
            this.cpfCliente = null;
            this.cpfVendedor = null;
            Conexao.closeConnection(conexao);
        }
    }
    
    private int getLastIdVenda(){
        Connection conexao = Conexao.getConnection();
        String query = "SELECT * FROM VENDA";
        PreparedStatement sttm;
        
        try{
            sttm = conexao.prepareStatement(query);
            ResultSet result = sttm.executeQuery();
            result.last();
            Conexao.closeConnection(conexao, sttm);
            return result.getInt("id_venda");
        }catch (SQLException ex){
            System.out.println("Error getLastIdVenda: " + ex);
            Conexao.closeConnection(conexao);
            return -2;
        }
    }
}
