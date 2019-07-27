package classes;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import models.Produto;

/**
 *
 * @author Luan
 */
public class Carrinho {
    private int idVenda;
    private double precoTotal;
    private ArrayList<ProdutoQnt> lista;
    
    public Carrinho(){
        this.precoTotal = 0.0;
        this.lista = new ArrayList<>();
    }
    
    /**
     * Get preco do carrinho sem desconto algum
     * @return double
     */
    public double getPrecoTotal(){ return this.precoTotal; }
    
    /**
     * Get preco do carrinho atual
     * @return double: somatorio dos precos dos produtos
     */
    public double getPreco() {
        double soma = 0;
        for(ProdutoQnt produto: this.lista)
            soma += produto.getPrecoDouble() * produto.getQuantidade();
        return soma;
    }
    
    public String getPrecoFormated(){
        return String.format("%.2f", this.getPreco()).replace(".", ",");
    }
    
    public int getIdVenda(){ return this.idVenda; }
    
    public ArrayList<ProdutoQnt> getListaProdutos(){ return this.lista;}
    
    public void addProduto(Produto produto, int qnt){
        this.addProduto(produto.getCod(), qnt);
    }
    
    public ProdutoQnt addProduto(String cod, int qnt){
        ProdutoQnt produtoNovo = new ProdutoQnt(cod, qnt);
        
        if (produtoNovo.getNome() == null){
            return null;
        }
       
        lista.add(produtoNovo);
        this.precoTotal += produtoNovo.getPrecoDouble() * produtoNovo.getQuantidade();
        
        return produtoNovo;
    }
    
    /**
     * Adiciona desconto
     * @param desconto (1-0)
     */
    public void addDesconto(Double desconto){
        int qnt = 0;
        for(ProdutoQnt produto: this.lista)
            qnt += produto.getQuantidade();
        
        double  retirar = (this.precoTotal * desconto) / qnt ;
        
        for(int i = 0; i < lista.size(); i++){
            ProdutoQnt produto = lista.get(i);
            produto.setPreco(
                Double.parseDouble(String.format("%.2f", produto.getPrecoDouble()).replace(",", ".")) - retirar
            );
        }
    }
    
    public void removerProduto(ProdutoQnt produto){
        for(int i = 0; i < lista.size(); i++){
            if (lista.get(i).igual(produto)){
                ProdutoQnt produtoRemovido = lista.get(i);
                this.precoTotal -= produtoRemovido.getQuantidade() * produtoRemovido.getPrecoDouble();
                lista.remove(i);
                break;
            }
        }
    }
    
    public ProdutoQnt getLastProduto(){
        return this.getProduto(lista.size() - 1);
    }
    
    public ProdutoQnt getProduto(int index){
        return lista.get(index);
    }
    
    public void limpar(){
        lista.clear();
        this.precoTotal = 0.0;
    }
    
    public String finalizarCompra(String vendedor, boolean fiada){
        return this.finalizarCompra("", vendedor, fiada);
    }
    
    public String finalizarCompra(String cliente, String vendedor, boolean fiada){
        if(!this.inserirVenda(cliente, vendedor, fiada))
            return "Error ao inserir a venda!";
        
        if(!this.inserirProduto())
            return "Error ao inserir produto";
        
        return "Compra finalizada com sucesso!";
    }
    
    private boolean inserirProduto(){
        Connection conn = Conexao.getConnection();
        String query = "select * from inserir_produto_da_compra(?,?,?,?)";
        for(ProdutoQnt produto: lista){
            try{
                PreparedStatement sttm = conn.prepareStatement(query);
                
                sttm.setInt(1, this.idVenda);
                sttm.setInt(2, produto.getId());
                sttm.setDouble(3, produto.getPrecoDouble());
                sttm.setInt(4, produto.getQuantidade());
                
                sttm.executeQuery();
                
                sttm.close();
            }catch(SQLException ex){
                Conexao.closeConnection(conn);
                System.out.println("Error inserir produto " + ex);
                return false;
            }
        }
        Conexao.closeConnection(conn);
        return true;
    }
    
    private boolean inserirVenda(String cliente, String vendedor, boolean fiada){
        Connection conn = Conexao.getConnection();
        String query = "SELECT * FROM INSERIR_VENDA(?,?,?)";
        PreparedStatement sttm;
        
        try{
            sttm = conn.prepareStatement(query);
            if (vendedor.equals(""))
                sttm.setNull(1, Types.VARCHAR);
            else
                sttm.setString(1, vendedor);
            
            if (cliente.equals(""))
                sttm.setNull(2, Types.VARCHAR);
            else
                sttm.setString(2, vendedor);
            sttm.setBoolean(3, fiada);
            
            ResultSet result = sttm.executeQuery();
            if(result.next())
                this.idVenda = result.getInt("id");
            else
                return false;
            Conexao.closeConnection(conn, sttm);
            return true;
        }catch(SQLException ex){
            Conexao.closeConnection(conn);
            System.out.println("Error InserirVenda: " + ex);
            return false;
        }
    }
}