package javaFx.Login;

import javafx.scene.control.Label;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import models.Login;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import javaFx.Main.Main;


/**
 * FXML Controller class
 *
 * @author Luan Rodrigues
 */
public class LoginController implements Initializable {
    @FXML
    private JFXTextField textUsername;
    @FXML
    private JFXPasswordField textPassword;
    @FXML
    private JFXButton btnLogin;
    @FXML
    private Label labelMessage;
    private Login user;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        btnLogin.setOnAction(event -> {
            entrar();
        });
    }
    
    @FXML
    public void close(MouseEvent event){
        System.exit(0);
    }
    
    @FXML // onClick
    public void entrar(MouseEvent event){
        entrar();
    }
    
    public void entrar(){
        getUser();
        if (user == null)
            showMessageErrorLogin();
        else
            validateUser();
    }
    
    private void getUser() {
        String username = textUsername.getText();
        String password = textPassword.getText();
        user = new Login(username, password);
    }
    
    private void validateUser() {
        if(user.logued())
            openMainWindow();
        else
            showMessageErrorLogin();
    }
    
    private void showMessageErrorLogin() {
        labelMessage.setText("Usuário/senha inválido");
    }
    
    private void openMainWindow() {
        try {
            openMainJavaFx();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error ao abrir Página Principal.\n"
                    + ex.toString());
        }
    }
    
    private void openMainJavaFx() throws Exception {
        javaFx.Login.Login.close();
        
        new Main().start(new Stage(), user.getVendedor());
    }
}
