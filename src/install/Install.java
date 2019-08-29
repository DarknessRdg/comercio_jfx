/**
 * Classe parar prepara o banco de dados, caso nao esteja criado;
 * Banco de dados: PostgreSQL
 */
package install;

import install.manage.*;
import connectionDatabase.Conexao;
import javaFx.login.AddLogin;
import javax.swing.JOptionPane;
import models.Login;

/**
 *
 * @author Luan Rodrigues
 */
public class Install{
    public static void main(){
        if (!dataBaseCreated())
            createDatabase();
        else if (noUserOnDatabase())
            createFirstUser();
    }
    
    public static boolean dataBaseCreated() {
        return Conexao.ableToConnectToDatabase();
    }

    public static void createDatabase() {
        Database.create();
        Tables.create();
        Functions.create();
        createFirstUser();
    }
    
    private static boolean noUserOnDatabase() {
        return Login.all().isEmpty();
    }
    
    private static void createFirstUser() {
        try {
            openAddLogin();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error abrir cadastro de atendete: " + ex);
        }
    }
    
    private static void openAddLogin() throws Exception {
        AddLogin window = new AddLogin();
        window.start();
    }
    

}