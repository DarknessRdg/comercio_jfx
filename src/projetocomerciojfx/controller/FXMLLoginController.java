package projetocomerciojfx.controller;
import javafx.scene.control.Label;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import classes.Login;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import projetocomerciojfx.Main;


/**
 * FXML Controller class
 *
 * @author Luan Rodrigues
 */
public class FXMLLoginController implements Initializable {
    @FXML
    private JFXTextField textUsuario;
    @FXML
    private JFXPasswordField textSenha;
    @FXML
    private JFXButton btnEntrar;
    @FXML
    private JFXButton btnClose;
    @FXML
    private Label labelMensagem;
    @FXML
    private AnchorPane paneMenu;
    
    @FXML
    public void close(MouseEvent event){
        System.exit(0);
    }
    
    @FXML // onClick
    public void entrar(MouseEvent event){
        Login login = new Login(textUsuario.getText(), textSenha.getText());
        
        if(login.logued()){
            try{
                projetocomerciojfx.Login.close();
                new Main().start(new Stage());
            }catch(Exception ex){
                Logger.getLogger(FXMLMainController.class.getName()).log(Level.SEVERE, null, ex);
            }         
        }
        else{
            labelMensagem.setText("Usuário/senha inválido");
        }
    }
    
    // defaultButton
    public void entrar(){
        Login login = new Login(textUsuario.getText(), textSenha.getText());
        
        if(login.logued()){
            labelMensagem.setText("Usuário verificado!");
            labelMensagem.setStyle("-fx-text-fill: green ;");
            try{
                projetocomerciojfx.Login.close();
                new Main().start(new Stage());
            }catch(Exception ex){
                Logger.getLogger(FXMLMainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else{
            labelMensagem.setText("Usuário/senha inválido");
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        btnEntrar.setOnAction(e -> {
            entrar();
        });
    }    

}
