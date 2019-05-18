package models;

import classes.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Luan
 */
public class Produto {
    private String codBarras;
    private String nome;
    private double preco;
    
    public Produto(String codBarras){
        this.setProduto(codBarras);
    }
    
    public Produto(String codBarras, String nome, double preco){
        this.codBarras = codBarras;
        this.nome = nome;
        this.preco = preco;
    }
    
    public String getNome(){ 
        if (this.nome == null)
            return null;
        return this.nome.toUpperCase();
    }
    
    public String getCod(){
        return this.codBarras;
    }
    
    public String getPreco(){ 
        return String.format("%.2f", this.preco).replace('.', ',');
    }
    
    public double getPrecoDouble(){ return this.preco;}
    
    public String getCodBarras(){ return this.codBarras; }
    
    public boolean save(){
        if(!this.exists()){
            if(this.nome != null && this.verificarCod())
                return this.inserirProduto();
            else
                return false;
        }else{
            return this.updateProduto();
        }
    }
    
    public boolean igual(Produto other){
        return this.getCodBarras().equals(other.getCodBarras());
    }
    
    private boolean verificarCod(){
        for(int i = 0; i < codBarras.length(); i ++){
            char caractere = codBarras.charAt(i);
            if (!('0' <= caractere && caractere <= '9'))
                return false;    
        }
        return true;
    }
    
    public boolean delete(){
        return this.removerProduto();
    }
    
    public boolean removerProduto(){
        Connection conn = Conexao.getConnection();
        String query = "DELETE FROM PRODUTO WHERE COD_BARRAS = ?";
        
        try{
            PreparedStatement sttm = conn.prepareStatement(query);
            
            sttm.setString(1, codBarras);
            sttm.execute();
            return true;
        }catch(SQLException ex){
            System.out.println("Error removerProduto " + ex);
            return false;
        }    
    }
    
    public boolean updateProduto(){
        Connection conn = Conexao.getConnection();
        String query = "UPDATE PRODUTO SET COD_BARRAS = ?, NOME = ?, PRECO = ? "
                + "WHERE COD_BARRAS = ? ";
        
        try{
            PreparedStatement sttm = conn.prepareStatement(query);
            sttm.setString(1, codBarras);
            sttm.setString(2, nome);
            sttm.setString(3, this.getPreco().replace(',', '.'));
            sttm.setString(4, codBarras);
            
            sttm.execute();
            return true;
        }catch(SQLException ex){
            System.out.println("Error updateProduto " + ex);
            return false;
        }
    }
    
    public boolean inserirProduto(){
        Connection conn = Conexao.getConnection();
        String query = "INSERT INTO PRODUTO VALUES(?, ?, ?)";
        
        try{
            PreparedStatement sttm = conn.prepareStatement(query);
            
            sttm.setString(1, codBarras);
            sttm.setString(2, nome);
            sttm.setString(3, this.getPreco().replace(',','.'));
            
            sttm.execute();
            return true;
        }catch(SQLException ex){
            System.out.println("Error inserirProduto " + ex);
            return false;
        }
    }
    
    public boolean exists(){        
        Connection conn = Conexao.getConnection();
        String query = "SELECT * FROM PRODUTO WHERE COD_BARRAS = ? ";
        
        try{
            PreparedStatement sttm = conn.prepareStatement(query);
            
            sttm.setString(1, this.codBarras);
            
            ResultSet result = sttm.executeQuery();
            
            result.first();
            result.getString("cod_barras");
            return true;
            
        }catch(SQLException ex){
            return false;
        }
    }
    
    private void setProduto(String codBarras){
        Connection conn = Conexao.getConnection();
        String query = "SELECT * FROM PRODUTO WHERE COD_BARRAS = ? ";
        
        try{
            PreparedStatement sttm = conn.prepareStatement(query);
            
            sttm.setString(1, codBarras);
            
            ResultSet result = sttm.executeQuery();
            
            result.first();
            this.codBarras = result.getString("cod_barras");
            this.nome = result.getString("nome");
            this.preco = result.getDouble("preco");
            
        }catch(SQLException ex){
            System.out.println("Error setProduto " + ex);
            this.nome = null;
            this.preco = 0.0;
        }
    }
    
    public static ArrayList<Produto> pesquisarProduto(String nome){
        ArrayList<Produto> lista = new ArrayList<>();
        
        Connection conn = Conexao.getConnection();
        
        String query = "SELECT * FROM PRODUTO WHERE NOME LIKE ? OR COD_BARRAS = ?";
        
        PreparedStatement sttm;
        try {
            sttm = conn.prepareStatement(query);
            
            sttm.setString(1, "%"+ nome + "%");
            sttm.setString(2, nome);
            
            ResultSet result = sttm.executeQuery();
            while(result.next()){
                lista.add(new Produto(
                        result.getString("cod_barras"),
                        result.getString("nome"),
                        result.getDouble("preco"))
                    );
            } // end while
            
            sttm.close();
        } catch (SQLException ex) {
            System.out.println("Error pesquisar produto: " + ex);    
        }
        return lista;   
    }
}
