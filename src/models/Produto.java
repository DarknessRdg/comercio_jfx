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
    private int id;
    private String codBarras;
    private String nome;
    private double preco;

    public Produto(String codBarras){
        this.setProduto(codBarras);
    }
    public Produto(String codBarras, String nome, double preco){
        this.id = id;
        this.codBarras = codBarras;
        this.nome = nome;
        this.preco = preco;
    }
    
    public Produto(int id, String codBarras, String nome, double preco){
        this.id = id;
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
    
    public int getId(){ return this.id; }
    
    public void setPreco(Double preco){ this.preco = preco; }
    public double getPrecoDouble(){ return this.preco;}
    
    public String getCodBarras(){ return this.codBarras; }
    
    public boolean save(){    
        return this.inserirProduto();
    }
    
    public boolean igual(Produto other){
        return this.getCodBarras().equals(other.getCodBarras());
    }
    
    private boolean verificarCod(){
        for(int i = 0; i < codBarras.length(); i ++){
            char caractere = codBarras.charAt(i);
            if (!('0' <= caractere && caractere <= '9')) { 
                System.out.println("Codigo de barras inválido");
                return false;    
            }
        }
        return true;
    }
    
    public boolean delete(){
        return this.removerProduto();
    }
    
    public boolean removerProduto(){
        Connection conn = Conexao.getConnection();
        String query = "SELECT * FROM DELETAR_PRODUTO(?)";
        
        try{
            PreparedStatement sttm = conn.prepareStatement(query);
            
            sttm.setString(1, codBarras);
            ResultSet result = sttm.executeQuery();
            result.next();
            boolean equals = result.getString("deletar_produto").toUpperCase().equals("PRODUTO DELETEADO COM SUCESSO!");
            Conexao.closeConnection(conn, sttm, result);
            return equals;
        }catch(SQLException ex){
            Conexao.closeConnection(conn);
            System.out.println("Error removerProduto " + ex);
            return false;
        }    
    }
    
    public boolean inserirProduto(){
        Connection conn = Conexao.getConnection();
        String query = "SELECT * FROM INSERIR_PRODUTO(?, ?, ?)";
        if (!this.verificarCod())
            return false;
        try{
            PreparedStatement sttm = conn.prepareStatement(query);
            
            sttm.setString(1, codBarras);
            sttm.setString(2, nome.toLowerCase());
            sttm.setDouble(3, this.getPrecoDouble());
            
            ResultSet result = sttm.executeQuery();
            result.next();
            this.id = result.getInt("id");
            
            Conexao.closeConnection(conn, sttm, result);
            return true;
        }catch(SQLException ex){
            Conexao.closeConnection(conn);
            System.out.println("Error inserirProduto " + ex);
            return false;
        }
    }
        
    private void setProduto(String cod_barras){
        Connection conn = Conexao.getConnection();
        String query = "SELECT * GET_PRODUTO(?)";
        
        try{
            PreparedStatement sttm = conn.prepareStatement(query);
            
            sttm.setString(1, cod_barras);
            
            ResultSet result = sttm.executeQuery();
            
            result.first();
            this.id = result.getInt("id");
            this.codBarras = result.getString("cod_barras");
            this.nome = result.getString("nome");
            this.preco = result.getDouble("preco");
            Conexao.closeConnection(conn, sttm, result);
        }catch(SQLException ex){
            System.out.println("Error setProduto " + ex);
            Conexao.closeConnection(conn);
            this.nome = null;
            this.preco = 0.0;
        }
    }
    
    public static ArrayList<Produto> pesquisarProduto(String nome){
        ArrayList<Produto> lista = new ArrayList<>();
        
        Connection conn = Conexao.getConnection();
        
        String query = "SELECT * FROM PESQUISAR_PRODUTO(?)";
        
        PreparedStatement sttm;
        try {
            sttm = conn.prepareStatement(query);
            
            sttm.setString(1, nome);
            
            ResultSet result = sttm.executeQuery();
            while(result.next()){
                lista.add(new Produto(
                        result.getInt("id"),
                        result.getString("cod_barras"),
                        result.getString("nome"),
                        result.getDouble("preco"))
                    );
            } // end while
            
            Conexao.closeConnection(conn, sttm, result);
        } catch (SQLException ex) {
            Conexao.closeConnection(conn);
            System.out.println("Error pesquisar produto: " + ex);    
        }
        return lista;   
    }
}
