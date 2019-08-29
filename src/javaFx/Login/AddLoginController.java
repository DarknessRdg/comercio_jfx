package javaFx.login;

import java.net.URL;
import java.util.ResourceBundle;

import javaFx.styles.colors.Color;
import javafx.fxml.Initializable;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import models.Vendedor;
import utils.validators.CPF;
import utils.validators.Name;


/**
 * FXML Controller class
 *
 * @author Luan
 */
public class AddLoginController implements Initializable {
    @FXML
    private JFXTextField textCpf;
    @FXML
    private JFXButton btnSalvar;
    @FXML
    private JFXTextField textName;
    @FXML
    private JFXTextField textPassword;
    @FXML
    private JFXPasswordField passwordField;
    @FXML
    private JFXToggleButton btnMostrarSenha;
    @FXML
    private JFXTextField textUsername;
    @FXML
    private Label labelMessage;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void keyReleasedCpf(KeyEvent event) {
        formatCpf();
    }
    
    @FXML
    private void mostrarSenha(ActionEvent event) {
        if (btnMostrarSenha.isSelected())
            showPasswordText();
        else
            hidePassword();
    }
    
    @FXML
    private void releasedOnTextPassword(KeyEvent event) {
        passwordField.setText(textPassword.getText());
    }
    
    @FXML
    private void salvar(ActionEvent event) {
        if (allowedToSave()) {
            var cpf = textCpf.getText().replace(".", "").replace("-", "");
            Vendedor.createNewVendedor(textName.getText(), cpf, textUsername.getText(), passwordField.getText());

            AddLogin.close();
            labelMessage.setText("");
        }
    }

    private void showErrorMessage(String message) {
        labelMessage.setText(message);
        labelMessage.setTextFill(Color.DANGER);
    }
    
    private void showPasswordText() {
        passwordField.setVisible(false);
        textPassword.setText(passwordField.getText());
        textPassword.setVisible(true);
    }
    
    private void hidePassword() {
        passwordField.setVisible(true);
        passwordField.setText(textPassword.getText());
        textPassword.setVisible(false);
    }

    private boolean allowedToSave() {
        return validateName() && validateCpf() && validateUsername()
                && validatePassword();
    }

    private boolean validateName() {
        var validator = new Name(textName.getText());
        if (validator.isValid())
            return true;

        showErrorMessage(validator.getError());
        return false;
    }

    private boolean validateUsername() {
        var validator = new Name(textUsername.getText());
        if (validator.isValid())
            return true;

        showErrorMessage(validator.getError().replace("Nome", "Username"));
        return false;
    }

    private boolean validatePassword() {
        var validator = new Name(passwordField.getText());
        if (validator.isValid())
            return true;

        showErrorMessage(validator.getError().replace("Nome", "Senha"));
        return false;
    }

    private boolean validateCpf() {
        var validator = new CPF(textCpf.getText());
        if (validator.isValid())
            return true;

        showErrorMessage(validator.getError());
        return false;
    }

    private void formatCpf() {
        int LIMITE_CPF = 14;
        String cpfFormated = "";
        String cpf = textCpf.getText().replace("-", "").replace(".", "");
        for(int index = 0; index < cpf.length(); index++) {
            if (index >= 11)
                break;
            if (cpfFormated.length() == 3 || cpfFormated.length() == 7)
                cpfFormated += ".";
            else if (cpfFormated.length() == 11) 
                cpfFormated += "-";
            
            cpfFormated += cpf.charAt(index);
        }
        
        textCpf.setText(cpfFormated);
        textCpf.positionCaret(LIMITE_CPF);
    }
    
}
