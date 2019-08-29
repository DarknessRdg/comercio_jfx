package javaFx.login;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javaFx.styles.colors.Color;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import models.Login;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import javaFx.main.Main;


/**
 * FXML Controller class
 *
 * @author Luan Rodrigues
 */
public class LoginController implements Initializable {
    @FXML
    private AnchorPane paneForIconClose;
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
        Text iconClose = GlyphsDude.createIcon(FontAwesomeIcon.CLOSE, "27px");
        iconClose.setFill(Color.LIGHT);
        paneForIconClose.getChildren().add(iconClose);
        var btn = paneForIconClose.getChildren().get(0);
        paneForIconClose.getChildren().remove(0);
        paneForIconClose.getChildren().add(btn);


        AnchorPane.setTopAnchor(iconClose, 8.0);
        AnchorPane.setRightAnchor(iconClose, 10.0);
    }
    
    @FXML
    public void close(MouseEvent event){
        System.exit(0);
    }
    
    @FXML // onClick
    public void entrar(ActionEvent event){
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
        if(user.isUserAuthenticated())
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
        javaFx.login.Login.close();
        
        new Main().start(new Stage(), user.getVendedor());
    }
}
