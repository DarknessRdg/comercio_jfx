/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetocomerciojfx.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;


/**
 * FXML Controller class
 *
 * @author Luan
 */
public class FXMLRelatorioController implements Initializable {
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
