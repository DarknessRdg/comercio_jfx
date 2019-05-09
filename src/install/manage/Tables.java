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
                            "id int primary key auto_increment," +
                            "username varchar(50) unique," +
                            "senha varchar(20)" +
                        ");";
        
        if (this.createTable(query, "login"))
            System.out.println(" OK!");
        else
            System.out.println("ERROR!");
    }
    
    private void vendedor(){
        System.out.print("- Table vendedor: ");
        String query =  "CREATE TABLE VENDEDOR(" +
                            "cpf varchar(11) primary key not null," +
                            "nome varchar(100)," +
                            "id_login int references login(id)" +
                        ");";
        
        if (this.createTable(query, "vendedor"))
            System.out.println(" OK!");
        else
            System.out.println("ERROR!");
    }
    
    private void cliente(){
        System.out.print("- Table cliente: ");
        String query =  "CREATE TABLE CLIENTE(" +
                            "cpf varchar(11) primary key not null," +
                            "nome varchar(100)," +
                            "telefone varchar(14)" +
                        ");";
        
        if (this.createTable(query, "cliente"))
            System.out.println(" OK!");
        else
            System.out.println("ERROR!");
        
    }
    
    private void produto(){
        System.out.print("- Table produto: ");
        String query =  "CREATE TABLE PRODUTO(" +
                            "cod_barras varchar(50) primary key not null," +
                            "nome varchar(100)," +
                            "preco decimal(6,2)" +
                        ");";
        
        if (this.createTable(query, "produto"))
            System.out.println(" OK!");
        else
            System.out.println("ERROR!");
    }
    
    
    private void venda(){
        System.out.print("- Table venda: ");
        String query =  "CREATE TABLE VENDA(" +
                            "id int not null auto_increment primary key," +
                            "cpf_cliente varchar(11) not null references cliente(cpf)," +
                            "cpf_vendedor varchar(11) not null references vendedor(cpf)," +
                            "data_compra date" +
                        ");";
        
        if (this.createTable(query, "venda"))
            System.out.println(" OK!");
        else
            System.out.println("ERROR!");
    }
    
    private void item_venda(){
        System.out.print("- Table item_venda: ");
        String query =  "CREATE TABLE ITEM_VENDA(" +
                            "id int not null auto_increment primary key," +
                            "id_venda int not null references venda(id_venda)," +
                            "id_produto varchar(50) not null references produto(id_produto)," +
                            
                            "qnt_produto int not null default 1," + 
                            "preco_produto decimal(5, 2)" +
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
            return ex.toString().contains("Table '" + tableName.trim() + "' already exists");
        }
    }
    
    public static void create(){
        Tables c = new Tables();
    }
}