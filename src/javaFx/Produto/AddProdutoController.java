package javaFx.produto;

import javafx.stage.Stage;
import models.Produto;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.Label;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Paint;

import javax.swing.*;

/**
 *
 * @author Luan
 */
public class AddProdutoController implements Initializable {
    @FXML
    private Label textAddProduto;
    @FXML
    private JFXButton btnSalvar;
    @FXML
    private JFXButton btnCancelar;
    @FXML
    private JFXTextField textCod;
    @FXML
    private JFXTextField textNome;
    @FXML
    private JFXTextField textPreco;
    @FXML
    private Label labelFeedback;
    private boolean stageAsEditar = false;
    private String COLOR_SUCCESS = "004d40";
    private String COLOR_ERROR = "b71c1c";
    private String SAVED_SUCCESS = "Produto adicionado com sucesso!";
    private String EDITED_SUCCESS = "Produto editado com sucesso!";
    private String SAVED_WRONG = "Error ao adicionar produto!";
    private String EDITED_WRONG = "Error ao editar produto!";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.btnSalvar.setDisable(true);
    }
    
    public void initData(Produto produto){
        this.textAddProduto.setText("EDITAR PRODUTO");
        this.textCod.setText(produto.getCod());
        this.textNome.setText(produto.getNome());
        this.textPreco.setText(produto.getPreco());
        
        this.btnSalvar.setDisable(false);
        stageAsEditar = true;
    }

    @FXML
    private void salvar(ActionEvent event) {
        var codBarras = textCod.getText();

        if (Produto.exists(codBarras) && !stageAsEditar)
            confirmUpdateProduto();
        else
            saveProduto();
    }

    private void confirmUpdateProduto() {
        var YES = 0; var NO = 1; var CANCEL = 2;
        var confirmMessage = "Já existe um produto salvo com este código de barras. Deseja alterar os dados deste produto?";
        var choice = JOptionPane.showConfirmDialog(null, confirmMessage);

        if (choice == YES) {
            saveProduto();
        }

        cleanFields();
    }

    private void saveProduto() {
        String cod = textCod.getText();
        String nome = textNome.getText();
        double preco = Double.parseDouble(textPreco.getText().replace(',', '.'));

        Produto produto = new Produto(cod, nome, preco);
        if (produto.save())
            showMessageSuccess();
        else
            showMessageError();
    }

    private void showMessageSuccess() {
        String message;
        if (stageAsEditar)
            message = EDITED_SUCCESS;
        else
            message = SAVED_SUCCESS;

        labelFeedback.setText(message);
        labelFeedback.setTextFill(Paint.valueOf("#" + COLOR_SUCCESS));
        labelFeedback.setVisible(true);
    }

    private void showMessageError() {
        String message;
        if (stageAsEditar)
            message = EDITED_WRONG;
        else
            message = SAVED_WRONG;

        labelFeedback.setText(message);
        labelFeedback.setTextFill(Paint.valueOf("#" + COLOR_ERROR));
        labelFeedback.setVisible(true);

    }

    @FXML
    private void close(ActionEvent event) {
        var stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }
    
    private void cleanFields() {
        if (!stageAsEditar) {
            textNome.setText("");
            textPreco.setText("");
            textCod.setText("");

            btnSalvar.setDisable(true);
        }
    }
    
    @FXML
    private void codReleased(KeyEvent event) {
        this.autenticar();
    }
    
    @FXML
    private void nomeReleased(KeyEvent event) {
        this.autenticar();
    }
    
    @FXML
    private void precoReleased(KeyEvent event) {
        this.autenticar();
    }
    
    @FXML 
    private void autenticar(){
        String cod, nome, preco;
        cod = textCod.getText();
        nome = textNome.getText();
        preco = textPreco.getText();
        
        if (!stringEmpty(cod) &&
            !stringEmpty(nome) &&
            !stringEmpty(preco) &&
             isNumber(preco))
                btnSalvar.setDisable(false);
        else
            btnSalvar.setDisable(true);
    }
    
    private boolean isNumber(String string){
        int contVigula = 0, contPonto = 0;
        
        for (int i = 0; i < string.length(); i++) {
            switch (string.charAt(i)) {
                case '1': case '2': case '3': case '4': case '5': case '6': case '7': case '8':
                case '9': case '0':
                    continue;
                case ',':
                    contVigula ++;
                    break;
                case '.':
                    contPonto ++;
                    break;
                default:
                    return false;
            }
        }
        
        if ((contPonto == 1 && contVigula == 0) || 
            (contVigula == 1 && contPonto == 0) || 
            (contPonto == 0  && contVigula == 0))
            return true;
        else
            return false;
    }
    
    private boolean stringEmpty(String string){
        for(int i = 0; i < string.length(); i ++){
            if (!(string.charAt(i) == ' '))
                return false;
        }
        return true;
    }
    
}
