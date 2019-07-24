/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetocomerciojfx.controller;

import estilos.icons.Icon;
import estilos.colors.Color;
import javax.swing.JOptionPane;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Luan
 */
public class FXMLMainController implements Initializable {
    @FXML
    private JFXButton btnProdutos;
    @FXML
    private JFXButton btnRelatorio;
    @FXML
    private JFXButton btnNovaCompra;
    @FXML
    private AnchorPane anchorMenu;
    @FXML
    private Pane panePerfil;
    @FXML
    private AnchorPane anchorConteudo;
    @FXML
    private ImageView imgProduto;
    @FXML
    private ImageView imgCompra;
    @FXML
    private ImageView imgRelatorio;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.selectBtn("");
    }
    
    @FXML
    private void abrirProdutos(ActionEvent event) {
        try {
            AnchorPane paneProdutos = (AnchorPane) FXMLLoader.load(getClass().getResource("/projetocomerciojfx/fxml/FXMLProdutos.fxml"));
            anchorConteudo.getChildren().setAll(paneProdutos);
            
            this.fitToParent(paneProdutos);
            this.selectBtn("produto");
                        
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "error abrirProdutos() : " + ex);
        } catch (NullPointerException ex){
            JOptionPane.showMessageDialog(null, "error abrirProdutos() : " + ex);
        }
    }
    
    @FXML
    private void abrirCompra(ActionEvent event) {
        try {
            AnchorPane paneCompra = (AnchorPane) FXMLLoader.load(
                    getClass().getResource(
                            "/projetocomerciojfx/fxml/FXMLCompra.fxml"
                    ));
            anchorConteudo.getChildren().setAll(paneCompra);
            
            this.fitToParent(paneCompra);
            this.selectBtn("compra");
                        
        } catch (IOException ex) {
            System.out.println("error abrirCompra() : " + ex);
        }
        
    }
    
    @FXML
    private void abrirRelatorio(ActionEvent event) {
        try {
            AnchorPane paneRelatorio = (AnchorPane) FXMLLoader.load(
                    getClass().getResource(
                            "/projetocomerciojfx/fxml/FXMLRelatorio.fxml"
                    ));
            anchorConteudo.getChildren().setAll(paneRelatorio);
            
            this.fitToParent(paneRelatorio);
            this.selectBtn("relatorio");
                        
        } catch (IOException ex) {
            System.out.println("error abrirRelatorio() : " + ex);
        }
    }
    
    private void fitToParent(AnchorPane pane){
        AnchorPane.setTopAnchor(pane, 0.0);
        AnchorPane.setLeftAnchor(pane, 4.0);
        AnchorPane.setRightAnchor(pane, 4.0);
        AnchorPane.setBottomAnchor(pane, 0.0);
    }
    
    private void selectBtn(String buttonName){        
        switch (buttonName) {
            case "produto":
                btnProdutos.setTextFill(Color.danger);
                imgProduto.setImage(Icon.produtoSelected);
                
                this.resetBtnCompra();
                this.resetBtnRelatorio();
                break;
            case "compra":
                btnNovaCompra.setTextFill(Color.danger);
                imgCompra.setImage(Icon.compraSelected);
                
                this.resetBtnProduto();
                this.resetBtnRelatorio();
                break;
            case "relatorio":
                btnRelatorio.setTextFill(Color.danger);
                imgRelatorio.setImage(Icon.relatorioSelected);
                
                this.resetBtnCompra();
                this.resetBtnProduto();
                break;
            
            default:  // reset all
                this.resetBtnCompra();
                this.resetBtnProduto();
                this.resetBtnRelatorio();
                break;
        }
    }
    
    private void resetBtnProduto(){
        this.btnProdutos.setTextFill(Color.dark);        
        imgProduto.setImage(Icon.produtoNormal);
    }
    
    private void resetBtnCompra(){
        this.btnNovaCompra.setTextFill(Color.dark);
        imgCompra.setImage(Icon.compraNormal);
    }
    
    private void resetBtnRelatorio(){
        this.btnRelatorio.setTextFill(Color.dark);
        imgRelatorio.setImage(Icon.relatorioNormal);
    }
}
