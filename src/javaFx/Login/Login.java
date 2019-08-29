package javaFx.login;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Luan Rodrigues
 */
public class Login extends Application {
    private static Stage stage;
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        
        Scene scene = new Scene(root);

        Login.stage = stage;
        stage.setScene(scene);

        stylizationStage();
        stage.show();

        install.Install.main();
    }

    private void stylizationStage() {
        stage.getScene().setFill(null);
        stage.initStyle(StageStyle.TRANSPARENT);
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
