package projetocomerciojfx.controller;

import models.Produto;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import projetocomerciojfx.AddProduto;

/**
 * FXML Controller class
 *
 * @author Luan
 */
public class FXMLProdutosController implements Initializable {
    @FXML
    private JFXTextField labelInput;
    @FXML
    private AnchorPane panePrincipal;
    @FXML
    private TableView<Produto> tableProdutos;
    @FXML
    private TableColumn<Produto, String> tableColumnCOD;
    @FXML
    private TableColumn<Produto, String> tableColumnNOME;
    @FXML
    private TableColumn<Produto, String> tableColumnPRECO;
    @FXML
    private JFXButton btnPesquisar;
     @FXML
    private JFXButton btnAdicionarProduto;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tableColumnCOD.setCellValueFactory(new PropertyValueFactory<>("codBarras"));
        tableColumnNOME.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnPRECO.setCellValueFactory(new PropertyValueFactory<>("preco"));
    }

    @FXML
    private void pesquisarProduto(ActionEvent event) {
        String prod = labelInput.getText();
        ArrayList<Produto> lista = Produto.pesquisarProduto(prod);
        
        ObservableList<Produto> observList = FXCollections.observableArrayList();
        observList.addAll(lista);
        
        tableProdutos.setItems(observList);
    }
    
    @FXML private void addProduto(ActionEvent event){
        try{
            new AddProduto().start(new Stage());
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "error ao abir janela de adicionar produto: " + ex);
        }
    }
}
