/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetocomerciojfx.controller;

import javax.swing.JOptionPane;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;

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
    
    // vairiables to pattern colors and url
    private String darkColor;
    private String dangerColor;
    private String urlImg;

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
    
    private void selectBtn(String button){        
        switch (button) {
            case "produto":
                btnProdutos.setTextFill(Paint.valueOf(dangerColor));
                imgProduto.setImage(new Image(urlImg + "produto-danger.png"));
                
                this.resetBtnCompra();
                this.resetBtnRelatorio();
                break;
            case "compra":
                btnNovaCompra.setTextFill(Paint.valueOf(dangerColor));
                imgCompra.setImage(new Image(urlImg + "compra-danger.png"));
                
                this.resetBtnProduto();
                this.resetBtnRelatorio();
                break;
            case "relatorio":
                btnRelatorio.setTextFill(Paint.valueOf(dangerColor));
                imgRelatorio.setImage(new Image(urlImg + "relatorio-danger.png"));
                
                this.resetBtnCompra();
                this.resetBtnProduto();
                break;
        }
    }
    
    private void resetBtnCompra(){
        this.btnNovaCompra.setTextFill(Paint.valueOf(darkColor));
        imgCompra.setImage(new Image(urlImg + "compra.png"));
    }
    
    private void resetBtnRelatorio(){
        this.btnRelatorio.setTextFill(Paint.valueOf(darkColor));
        imgRelatorio.setImage(new Image(urlImg + "relatorio.png"));
    }
    
    private void resetBtnProduto(){
        this.btnProdutos.setTextFill(Paint.valueOf(darkColor));        
        imgProduto.setImage(new Image(urlImg + "produto.png"));
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.darkColor = "4f5153";
        this.dangerColor = "ff2d36";
        this.urlImg = "/images/icons/";
    }
    
}
