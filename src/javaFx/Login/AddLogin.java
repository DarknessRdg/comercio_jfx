package javaFx.login;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 *
 * @author Luan
 */
public class AddLogin  {
    private static Stage stage;
    
    public AddLogin() {
        stage = new Stage();
    }
    
    public void start() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("AddLogin.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public static void close() {
        AddLogin.stage.close();
    }
    
    public boolean isRunning() {
        return stage.isShowing();
    }
}
