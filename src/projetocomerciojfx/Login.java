/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetocomerciojfx;

import install.Install;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Produto;

/**
 *
 * @author Luan
 */
public class Login extends Application {
    private static Stage stage;
    
    @Override
    public void start(Stage stage) throws Exception {               
        Install.prepareDb();
        
        Parent root = FXMLLoader.load(getClass().getResource("/projetocomerciojfx/fxml/FXMLLogin.fxml"));
        
        Scene scene = new Scene(root);
        Login.stage = stage;
        
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
            
        stage.show();
    }
    
    public static void close(){
        Login.stage.close();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
