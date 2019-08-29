package javaFx.relatorio;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;


/**
 *
 * @author Luan
 */
public class RelatorioController implements Initializable {
     @FXML
    private DatePicker datePicker;
    @FXML
    private AnchorPane main;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     // TODO   
    }
    
    @FXML
    void dataSelecionada(ActionEvent event) {
         LocalDate data = datePicker.getValue();
         System.out.println("data : " + data.toString());
    }
}
