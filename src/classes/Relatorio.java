/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import models.Produto;

import java.util.ArrayList;

/**
 *
 * @author Luan
 */
public class Relatorio {
    private ArrayList<VendaRelatorio> vendas;
    
    public Relatorio(Date date){
        vendas = new ArrayList<>();
        this.getRelatorioByDay(date);
    }
    
    public Relatorio(int month){
        vendas = new ArrayList<>();
        this.getRelatorioByMonth(month);
    }
    
    private void getRelatorioByDay(Date date){
        this.getReletorio("data_compra = '" + date.toString() + "'");
    }
    
    private void getRelatorioByMonth(int month){
        String[] date = Date.valueOf(LocalDate.now()).toString().split("-"); 
        String YEAR = date[0];
        String data1 = "'" + YEAR + "-" + month + "-" + "1" + "'";
        String data2 = "'" + YEAR + "-" + month + "-" + "31" + "'";
        this.getReletorio("data_compra between " + data1 + " and " + data2);
    }
    
    private void getReletorio(String queryset){
        Connection conexao = Conexao.getConnection();
        String query = "SELECT * FROM VENDA_DATA WHERE " + queryset;
        PreparedStatement sttm;
        try{
            sttm = conexao.prepareStatement(query);
            ResultSet result = sttm.executeQuery();
            while(result.next()){
                Produto new_produto = new Produto(
                        result.getInt("id"),
                        result.getString("cod_barras"), 
                        result.getString("nome_produto"), 
                        result.getDouble("preco"));
            }
            
        }catch (SQLException ex){
            System.out.println("Error Relatorio: " + ex);
        }
    }
}


class VendaRelatorio {
    private ArrayList<Produto> produtos;
    private double total;
    
    public VendaRelatorio(){
        produtos = new ArrayList<>();
        total = 0.0;
    }
    
    public void add(Produto produto){
        total += produto.getPrecoDouble();
        produtos.add(produto);
    }
}
