/**
 * Classe parar prepara o banco de dados, caso nao esteja criado;
 * Banco de dados: PostgreSQL
 */
package install;

import install.manage.*;
import models.Login;
/**
 *
 * @author Luan
 */
public class Install{
    public static void prepareDb(){
        if(Database.create()) {
            Tables.create();
            Functions.create();
        }
        Functions.create();
    
        Login admin = new Login("admin", "admin");
        admin.save();
    }
}