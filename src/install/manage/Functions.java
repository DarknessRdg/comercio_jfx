/*
 * Classe para criar no banco de dados as funcoes nescessarias
 * para facilitar a manipulacao dos dados com o banco.
 */
package install.manage;

import classes.Conexao;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Luan
 */
public class Functions {
    public Functions() {
        System.out.println("Cretaing function ...");
        // produto
        this.inserirProduto();
        this.getProduto();
        this.pesquisarProduto();
        this.deletarProduto();
        // compra
        this.fazerCompra();
    }
    
    private void getProduto() {
        System.out.print("- get_produto ");
        String header = "get_produto(cod varchar)";
        String returns = "setof produto";
        String body = "return query select * from produto where cod_barras = $1 and ativo = true;";
        
        if (createFunction(header, returns, body))
            System.out.println(" OK!");
        else
            System.out.println(" ERROR!");
    }
    
    private void pesquisarProduto() {
        System.out.print("- pesquisar_produto ");
        String header = "pesquisar_produto(nome_pesquisa varchar)";
        String returns = "setof produto";
        String body = "return query select * from produto where (nome ilike '%' || $1 || '%' or cod_barras = $1) "
                + "and ativo = true;";
        
        if (createFunction(header, returns, body))
            System.out.println(" OK!");
        else
            System.out.println(" ERROR!");     
    }
    
    private void inserirProduto() {
        System.out.print("- inserir_produto ");
        
        String header = "inserir_produto(cod varchar, nome varchar,preco double precision)";
        String returns = "setof produto";
        String body = "if exists (select * from produto where cod_barras = $1) then " +
                           "update produto set nome = $2, preco = cast($3 AS decimal(6,2)), ativo = true " +
                           "where cod_barras = $1;" +
                      "else " +
                           "INSERT INTO PRODUTO(id, cod_barras, nome, preco) VALUES(DEFAULT, $1, $2, cast($3 AS decimal(6,2))); " + 
                      "end if; " +
                      "return query select * from produto where cod_barras = $1;" ;
        
        if (createFunction(header, returns, body))
            System.out.println(" OK!");
        else
            System.out.println(" ERROR!");     
    }
    
    private void deletarProduto() {
        System.out.print("- deletar_produto ");
        String header = "deletar_produto(cod varchar)";
        String returns = "varchar";
        String body = "if exists (select * from produto where cod_barras = $1) then " +
                           "update produto set ativo = false where cod_barras = $1; " +
                           "return 'Produto deleteado com sucesso!'; " +
                       "end if; " +
                       "return 'Produto nao encontrado.';" ;
        
        if (createFunction(header, returns, body))
            System.out.println(" OK!");
        else
            System.out.println(" ERROR!");     
    }
    
    private void fazerCompra(){
        System.out.print("- fazer_compra ");
        String header = "fazer_compra(array_produtos int[], id_vendedor int, id_cliente int)";
        String returns = "setof item_venda";
        String declare = "id_venda_variavel int; produto_loop int; prod int; cont int; preco_do_produto decimal(6,2);";
        
        String body = "select coalesce(max(id), 0) + 1 into id_venda_variavel from venda;" +
                      "insert into venda(id, cpf_cliente, cpf_vendedor) values (id_venda_variavel, $2, $3);" +
                      "foreach produto_loop in array array_produtos loop " +
                            "if exists (select * from item_venda where id_venda = id_venda_variavel and id_produto = produto_loop) then " +
                                "continue; " +
                            "end if;" +
                            "cont := 0;" +
                            "foreach prod in array array_produtos loop " +
                                "if prod = produto_loop then cont := cont + 1; end if;" +
                            "end loop;" +
                            "select preco into preco_do_produto from produto where id = produto_loop; " +
                            "insert into item_venda(id, id_venda, id_produto, qnt_produto, preco_produto) values (default, id_venda_variavel, produto_loop, cont, preco_do_produto);" +   
                        "end loop;" +
                "return query select * from item_venda where id_venda = id_venda_variavel;";
                      
        if (createFunction(header, declare, returns, body))
            System.out.println(" OK!");
        else
            System.out.println(" ERROR!");     
    }
    
    private boolean createFunction(String header, String declare,String returns, String body) {
        String query = "CREATE OR REPLACE FUNCTION " + header.toUpperCase() + " RETURNS " + returns.toUpperCase() + 
                " AS $$" +
                " DECLARE " + declare +
                " BEGIN " 
                    + body.toUpperCase() + 
                " END ; " + 
                " $$ LANGUAGE plpgsql;";
        Connection conn = Conexao.getConnection();
        try{
            Statement sttm = conn.createStatement();
            sttm.executeUpdate(query);
            
            sttm.close();
            Conexao.closeConnection(conn);
            return true;
        } catch (SQLException exception) {
            System.out.println("Error create function " + header + " " + exception);
            System.out.println("query: \n" + query);
            Conexao.closeConnection(conn);
            return false;
        }
    }
    
    private boolean createFunction(String header, String returns, String body) {
        String query = "CREATE OR REPLACE FUNCTION " + header.toUpperCase() + " RETURNS " + returns.toUpperCase() + 
                " AS $$" +
                " BEGIN " 
                    + body.toUpperCase() + 
                " END ; " + 
                " $$ LANGUAGE plpgsql;";
        Connection conn = Conexao.getConnection();
        try{
            Statement sttm = conn.createStatement();
            sttm.executeUpdate(query);
            sttm.close();
            Conexao.closeConnection(conn);
            return true;
        } catch (SQLException exception) {
            System.out.println("Error create function " + header + " " + exception);
            System.out.println("query: \n" + query);
            return false;
        }
    }
    
    public static void create(){
        Functions c = new Functions();
    }
}
