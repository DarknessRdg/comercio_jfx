package models;

import connectionDatabase.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

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
        this.codBarras = codBarras;
        this.setProduto();
    }
    
    public Produto(String codBarras, String nome, double preco){
        this.id = id;
        this.codBarras = codBarras;
        this.preco = preco;
        setNome(nome);
    }
    
    public Produto(int id, String codBarras, String nome, double preco){
        this.id = id;
        this.codBarras = codBarras;
        this.preco = preco;
        setNome(nome);
    }
    
    private void setNome(String nome) {
        this.nome = nome.toLowerCase();
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
    
    public int getId(){ 
        return this.id; 
    }
    
    public void setPreco(Double preco){ 
        this.preco = preco; 
    }
    
    public double getPrecoDouble(){ 
        return this.preco;
    }
    
    public String getCodBarras(){ 
        return this.codBarras; 
    }
    
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
        try {
            deleteProdutoOnDataBase();
            return true;
        } catch (SQLException exception ) {
            JOptionPane.showMessageDialog(null, "Error removerProduto " + exception);
            return false;
        }
    }
    
    public boolean inserirProduto(){
        if (!this.verificarCod())
            return false;
        
        try {
            insertProdutoOnDatabase();
            return true;
        } catch(SQLException ex){
            System.out.println("Error inserirProduto " + ex);
            return false;
        }
    }
    
    private void setProduto(){
        try {
            getProdutoOnDataBase();
        } catch(SQLException ex) {
            System.out.println("Error setProduto " + ex);
            this.nome = null;
            this.preco = 0.0;
        }
    }
    
    private void getProdutoOnDataBase() throws SQLException {
        Connection conn = Conexao.getConnection();
        String query = "select * from get_produto(?)";
        
        PreparedStatement sttm = conn.prepareStatement(query);    
        sttm.setString(1, codBarras);
        ResultSet result = sttm.executeQuery();
        result.next();
        
        setProdutoFromResult(result);

        Conexao.closeConnection(conn, sttm, result);
    }
    
    private void setProdutoFromResult(ResultSet result) throws SQLException {
        id = result.getInt("id");
        codBarras = result.getString("cod_barras");
        nome = result.getString("nome");
        preco = result.getDouble("preco");
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
            }
            
            Conexao.closeConnection(conn, sttm, result);
        } catch (SQLException ex) {
            Conexao.closeConnection(conn);
            System.out.println("Error pesquisar produto: " + ex);    
        } catch (NullPointerException ex) {
            return lista;
        }
        return lista;   
    }
    
    private void insertProdutoOnDatabase() throws SQLException {
        String query = "SELECT * FROM INSERIR_PRODUTO(?, ?, ?)";
        
        Connection connection = Conexao.getConnection();
        PreparedStatement sttm = connection.prepareStatement(query);
        
        sttm.setString(1, codBarras);
        sttm.setString(2, nome);
        sttm.setDouble(3, preco);

        ResultSet result = sttm.executeQuery();
        result.next();
        
        id = result.getInt("id");
        
    }
    
    private void deleteProdutoOnDataBase() throws SQLException {
        String query = "SELECT * FROM DELETAR_PRODUTO(?)";
        
        Connection connetion = Conexao.getConnection();
        PreparedStatement sttm = connetion.prepareStatement(query);
        sttm.setString(1, codBarras);
        sttm.executeQuery();
        
        Conexao.closeConnection(connetion, sttm);
    }
}
