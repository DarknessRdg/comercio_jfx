/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import classes.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Luan
 */
public class ItemVenda {
    private int idVenda;
    private String codBarras;
    private int qntProduto;
    private double preco;
    
    public ItemVenda(int idVenda, String codBarras, int qntProduto, double preco){
        this.idVenda = idVenda;
        this.codBarras = codBarras;
        this.qntProduto = qntProduto;
        this.preco = preco;
    }
    
    public boolean save(){
        Connection conn = Conexao.getConnection();
        String query = "INSERT INTO ITEM_VENDA"
                + "(ID_VENDA, ID_PRODUTO, QNT_PRODUTO, PRECO_PRODUTO) "
                + "VALUES(?, ?, ?, ?)";
        
        try{
            PreparedStatement sttm = conn.prepareStatement(query);
            
            sttm.setInt(1, idVenda);
            sttm.setString(2, codBarras);
            sttm.setInt(3, qntProduto);
            sttm.setString(4, String.format("%.2f", preco).replace(',', '.'));
            
            sttm.execute();
            Conexao.closeConnection(conn, sttm);
            return true;
        }catch(SQLException ex){
            Conexao.closeConnection(conn);
            System.out.println("Error save " + ex);
            return false;
        }
    }
}
