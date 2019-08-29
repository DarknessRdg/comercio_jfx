package javaFx.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.Vendedor;


/**
 *
 * @author Luan
 */
public class Main extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {               
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setMaximized(true);
            
        stage.show();
    }
    
    public void start(Stage stage, Vendedor vendedor) throws Exception {               
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Main.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        
        MainController controller = loader.getController();
        controller.setVendedor(vendedor);
        
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setTitle("Comércio");

        stage.show();
    }
}
