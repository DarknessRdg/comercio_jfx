package install;

import install.manage.*;
import models.Login;
/**
 *
 * @author Luan
 */
public class Install{
    public static void prepareDb(){
        if(Database.create())
            Tables.create();
        
        Login admin = new Login("admin", "admin");
        admin.save();
    }
}