/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetocomerciojfx.controller;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
    private AnchorPane anchorMenu;

    @FXML
    private Pane panePerfil;

    @FXML
    private AnchorPane anchorConteudo;
    @FXML
    private JFXButton btnNovaCompra;

    @FXML
    private void abrirProdutos(ActionEvent event) {
        
        try {
            AnchorPane paneProdutos = (AnchorPane) FXMLLoader.load(getClass().getResource("/projetocomerciojfx/fxml/FXMLProdutos.fxml"));
            anchorConteudo.getChildren().setAll(paneProdutos);
            
            this.fitToParent(paneProdutos);
                        
        } catch (IOException ex) {
            System.out.println("error abrirProdutos() : " + ex);
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
                        
        } catch (IOException ex) {
            System.out.println("error abrirCompra() : " + ex);
        }
        
    }
    
    private void fitToParent(AnchorPane pane){
        AnchorPane.setTopAnchor(pane, 0.0);
        AnchorPane.setBottomAnchor(pane, 0.0);
        AnchorPane.setLeftAnchor(pane, 0.0);
        AnchorPane.setRightAnchor(pane, 0.0);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
}
