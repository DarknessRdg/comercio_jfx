package javaFx.Produto;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.Produto;

/**
 *
 * @author Luan
 */
public class AddProduto extends Application {
    public Stage stageProduto;
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("AddProduto.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setResizable(false);
        this.stageProduto = stage;
        stage.show();
    }
    
    public void start(Stage stage, Produto produto) throws Exception {               
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("AddProduto.fxml"));
        Parent root = loader.load();
        
        Scene scene = new Scene(root);
        
        AddProdutoController controller = loader.getController();
        controller.initData(produto);
        
        stage.setScene(scene);
        stage.setResizable(false);
        this.stageProduto = stage;    
        stage.show();
    }
    
}