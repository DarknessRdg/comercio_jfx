/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetocomerciojfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.Produto;
import projetocomerciojfx.controller.FXMLAddProdutoController;

/**
 *
 * @author Luan
 */
public class AddProduto extends Application {
    public Stage stageProduto;
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/projetocomerciojfx/fxml/FXMLAddProduto.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setResizable(false);
        this.stageProduto = stage;
        stage.show();
    }
    
    public void start(Stage stage, Produto produto) throws Exception {               
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/projetocomerciojfx/fxml/FXMLAddProduto.fxml"));
        Parent root = loader.load();
        
        Scene scene = new Scene(root);
        
        FXMLAddProdutoController controller = loader.getController();
        controller.initData(produto);
        
        stage.setScene(scene);
        stage.setResizable(false);
        this.stageProduto = stage;    
        stage.show();
    }
    
}