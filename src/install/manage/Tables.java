package install.manage;

import classes.Conexao;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
/**
 *
 * @author Luan Rodrigues
 * @version 1.0
 */
public class Tables {
    public Tables(){
        System.out.println("Creating tables at data base .... ");
            this.login();
            this.vendedor();
            this.cliente();
            this.produto();
            this.venda();
            this.item_venda();
        System.out.println("");
    }
    
    private void login(){
        System.out.print("- Table login: ");
        String query =  "CREATE TABLE LOGIN(" +
                            "id serial NOT NULL PRIMARY KEY," +
                            "username VARCHAR(50) UNIQUE," +
                            "senha VARCHAR(255)," + 
                            "ativo BOOLEAN DEFAULT TRUE" +
                        ");";
        
        if (this.createTable(query, "login"))
            System.out.println(" OK!");
        else
            System.out.println("ERROR!");
    }
    
    private void vendedor(){
        System.out.print("- Table vendedor: ");
        String query =  "CREATE TABLE VENDEDOR(" +
                            "id serial primary key not null, " +
                            "cpf VARCHAR(11) unique, " +
                            "nome VARCHAR(100)," +
                            "id_login INT REFERENCES LOGIN(id) ON UPDATE NO ACTION ON DELETE CASCADE," +
                            "ativo BOOLEAN DEFAULT TRUE" +
                        ");";
        
        if (this.createTable(query, "vendedor"))
            System.out.println(" OK!");
        else
            System.out.println("ERROR!");
    }
    
    private void cliente(){
        System.out.print("- Table cliente: ");
        String query =  "CREATE TABLE CLIENTE(" +
                            "id serial primary key not null," +
                            "cpf VARCHAR(11) unique," +
                            "nome VARCHAR(100)," +
                            "telefone VARCHAR(14)," +
                            "ativo BOOLEAN DEFAULT TRUE" +
                        ");";
        
        if (this.createTable(query, "cliente"))
            System.out.println(" OK!");
        else
            System.out.println("ERROR!");
        
    }
    
    private void produto(){
        System.out.print("- Table produto: ");
        String query =  "CREATE TABLE PRODUTO(" +
                            "id serial NOT NULL PRIMARY KEY," +
                            "cod_barras VARCHAR(50) NOT NULL," +
                            "nome VARCHAR(100)," +
                            "preco DECIMAL(6,2), " +
                            "ativo BOOLEAN DEFAULT TRUE" +
                        ");";
        
        if (this.createTable(query, "produto"))
            System.out.println(" OK!");
        else
            System.out.println("ERROR!");
    }
    
    
    private void venda(){
        System.out.print("- Table venda: ");
        String query =  "CREATE TABLE VENDA(" +
                            "id serial NOT NULL PRIMARY KEY," +
                            "cpf_cliente VARCHAR(11) REFERENCES CLIENTE(cpf) ON UPDATE NO ACTION ON DELETE CASCADE," +
                            "cpf_vendedor VARCHAR(11) REFERENCES VENDEDOR(cpf) ON UPDATE NO ACTION ON DELETE CASCADE," +
                            "data_compra date default now()," +
                            "ativo BOOLEAN DEFAULT TRUE" +
                        ");";
        
        if (this.createTable(query, "venda"))
            System.out.println(" OK!");
        else
            System.out.println("ERROR!");
    }
    
    private void item_venda(){
        System.out.print("- Table item_venda: ");
        String query =  "CREATE TABLE ITEM_VENDA(" +
                            "id serial NOT NULL PRIMARY KEY," +
                            "id_venda int NOT NULL REFERENCES VENDA(id) ON UPDATE NO ACTION ON DELETE CASCADE," +
                            "id_produto int NOT NULL REFERENCES PRODUTO(id) ON UPDATE NO ACTION ON DELETE CASCADE," +
                            
                            "qnt_produto int NOT NULL DEFAULT 1," + 
                            "preco_produto DECIMAL(6, 2) NOT NULL" +
                        ");";
        
        if (this.createTable(query, "item_venda"))
            System.out.println(" OK!");
        else
            System.out.println("ERROR!");
    }
    
    private boolean createTable(String query, String tableName){
        Connection conn = Conexao.getConnection();
        try{
            Statement sttm = conn.createStatement();
            sttm.executeUpdate(query);
            return true;
        }catch(SQLException ex){
            // return true if aleready exists, eslse return false;
            if(ex.toString().contains("relation \"" + tableName.trim() + "\" already exists"))
                return true;
            else {
                System.out.println(ex);
                return false;
            }
        }
    }
    
    public static void create(){
        Tables c = new Tables();
    }
}