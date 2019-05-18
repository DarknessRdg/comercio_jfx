/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetocomerciojfx.controller;

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

/**
 * FXML Controller class
 *
 * @author Luan
 */
public class FXMLAddProdutoController implements Initializable {
    @FXML
    private JFXButton btnSalvar;
    @FXML
    private JFXTextField textCod;
    @FXML
    private JFXTextField textNome;
    @FXML
    private JFXTextField textPreco;
    @FXML
    private Label labelFeedback;
    private boolean editar = false;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.btnSalvar.setDisable(true);
    }
    
    public void initData(Produto produto){
        this.textCod.setText(produto.getCod());
        this.textNome.setText(produto.getNome());
        this.textPreco.setText(produto.getPreco());
        
        this.btnSalvar.setDisable(false);
    }

    @FXML
    private void salvar(ActionEvent event) {
        String cod = textCod.getText();
        String nome = textNome.getText();
        double preco = Double.parseDouble(textPreco.getText().replace(',', '.'));
        
        Produto produto = new Produto(cod, nome, preco);
        if (produto.save()){
            labelFeedback.setText("Produto adicionado com sucesso !");
            labelFeedback.setTextFill(Paint.valueOf("#18d63e"));
            labelFeedback.setVisible(true);
            
            this.clean();
        }else{
            labelFeedback.setText("Error ao adicionar produto. Tente novamente.");
            labelFeedback.setTextFill(Paint.valueOf("#f11b1b"));
            labelFeedback.setVisible(true);
        }
    }
    
    private void clean(){
        textNome.setText("");
        textPreco.setText("");
        textCod.setText("");
        
        btnSalvar.setDisable(true);
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
        
        if (!this.stringEmpty(cod) && 
            !this.stringEmpty(nome) &&
            !this.stringEmpty(preco) &&
            this.isNumber(preco))
                btnSalvar.setDisable(false);
        else
            btnSalvar.setDisable(true);
    }
    
    private boolean isNumber(String string){
        int contVigula = 0, contPonto = 0;
        
        for(int i = 0; i < string.length(); i++) {
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
            } // end switch
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
