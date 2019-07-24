package classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Luan
 */
public class Login {
    private String loginDB;
    private String senhaDB;
    private String login;
    private String senha;
    private int cod;
    
    public Login(String login, String senha){
        loginDB = "-z }{/;d s kJDdsUnsJOOUsndsçald 2 23 32 sd/.  sa";
        senhaDB = "-z;}{/d s kJDdsUnsJOOUsndsçald 2 23 32 sd/. sadsa213231s";
        this.login = login;
        this.senha = senha;
        this.cod = -1;
        
        this.getLoginDB();
    }
    
    public String getLogin(){
        return this.login;
    }
    
    public boolean logued(){
        return loginDB.equals(login) && senhaDB.equals(senha);
    }
    
    private void getLoginDB(){
        Connection conn = Conexao.getConnection();
        String query = "select * from login where username = '" + login.toLowerCase() + "'";
        ResultSet result;
        
        try{
            try (PreparedStatement sttm = conn.prepareStatement(query)) {
                result = sttm.executeQuery();
                if(result.next()){
                    
                loginDB = result.getString("username");
                senhaDB = result.getString("senha");
                cod = result.getInt("id");
                }
            }
            conn.close();
            result.close();
        }catch(SQLException ex){
            System.out.println("ERROR GETLOGINDB  = " + ex );
        }
        
    }
    
    private int getCod(){
        return cod;
    }
    
    
}
