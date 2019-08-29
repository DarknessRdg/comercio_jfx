/*
 * Classe para criar no banco de dados as funcoes nescessarias
 * para facilitar a manipulacao dos dados com o banco.
 */
package install.manage;

import connectionDatabase.Conexao;
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
        this.inserirProduto();
        this.getProduto();
        this.pesquisarProduto();
        this.deletarProduto();
        this.inserirVenda();
        this.deletarVendaPermanentemente();
        this.inserirProdutoDaCompra();
        this.createNewVendedor();
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
    
    private void inserirVenda() {
        System.out.print("- inserir_venda ");
        String header = "inserir_venda (vendedor varchar, cliente varchar, venda_paga boolean) ";
        String declare = "id_v int; id_c int;";
        String returns = " setof venda ";
        String body = "select id into id_v from vendedor where cpf = $1;" +
                      "select id into id_c from cliente where cpf = $1;" +
                      "insert into venda(id, cpf_cliente, cpf_vendedor, paga) values (default, cliente, vendedor, venda_paga);" +
                      "return query select * from venda where id in (select max(id) from venda);";
        
        if (createFunction(header, declare, returns, body))
            System.out.println(" OK!");
        else
            System.out.println(" ERROR!");     
    }
    
    private void deletarVendaPermanentemente(){
        System.out.print("- deletar_venda_permanentemente ");
        String header = "deletar_venda_permanentemente(id_ int)";
        String returns = "varchar";
        String body = "if not exists (select * from venda where id = $1) then " +
                            "return 'Não existe uma venda com o id passadao.';" +
                       "end if;" +
                       "delete from venda where id = $1;" +
                       "return 'Venda deletada com sucesso.';";
        
        if (createFunction(header, returns, body))
            System.out.println(" OK!");
        else
            System.out.println(" ERROR!");     
    }
    
    private void inserirProdutoDaCompra(){
        System.out.print("- inserir_produto_da_compra ");
        String header = "inserir_produto_da_compra(id_c int, id_p int, preco_p double precision, qnt int)";
        String returns = "setof item_venda ";
        String body = "insert into item_venda(id, id_venda, id_produto, qnt_produto, preco_produto) values " +
                "(default, id_c, id_p, qnt, cast(preco_p as decimal(6,2))); " + 
                "return query select * from item_venda where id_venda = id_c and id_produto = id_p and " +
                "qnt_produto = qnt and preco_produto = preco_p;";
        
        if (createFunction(header, returns, body))
            System.out.println(" OK!");
        else
            System.out.println(" ERROR!");
    }

    private void createNewVendedor() {
        System.out.print("- create_new_vendedor ");
        String header = "create_new_vendedor(nome varchar, cpf varchar, username  varchar,password varchar ) ";
        String returns = "void ";
        String declare = "new_id_login int; ";
        String body = "if exists (select * from login l where l.username = $3 and l.password = $4) then " +
                "update login l set ativo = true where l.username = $3 and l.password = $4; " +
                "else " +
                    "insert into login(username, password) values ($3, $4);" +
                    "select id into new_id_login from login l where l.username = $3 and l.password = $4; " +
                "end if ;" +
                "insert into vendedor(nome, cpf, id_login) values ($1, $2, new_id_login); ";

        if (createFunction(header, declare, returns, body))
            System.out.println(" OK!");
        else
            System.out.println(" ERROR!");


    }
    
    private boolean createFunction(String header, String declare, String returns, String body) {
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
