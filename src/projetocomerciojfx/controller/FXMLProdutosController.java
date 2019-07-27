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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.Icon;
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
    @FXML
    private JFXButton btnExcluir;
    @FXML
    private JFXButton btnEditar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tableColumnCOD.setCellValueFactory(new PropertyValueFactory<>("codBarras"));
        tableColumnNOME.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnPRECO.setCellValueFactory(new PropertyValueFactory<>("preco"));
        
        this.setDoubleClickTable();
        this.pesquisarTodos();
    }

    @FXML
    private void pesquisarProduto(ActionEvent event) {
        String prod = labelInput.getText();
        ArrayList<Produto> lista = Produto.pesquisarProduto(prod);
        
        ObservableList<Produto> observList = FXCollections.observableArrayList();
        observList.addAll(lista);
        
        tableProdutos.setItems(observList);
    }
    
    @FXML 
    private void addProduto(ActionEvent event){
        try{
            new AddProduto().start(new Stage());
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "error ao abir janela de adicionar produto: " + ex);
        }
    }
    
    @FXML
    private void excluirProduto(ActionEvent event){
        Produto produtoSelecionado = this.getTrableRowFocudesProduto();
        if(this.confirmar("Deseja excluir o produto " + produtoSelecionado.getNome() + " ?")){
            if(produtoSelecionado.delete())
                JOptionPane.showMessageDialog(null, "Produto excluído com sucesso!");
            else
                JOptionPane.showMessageDialog(null, "Error ao deletar produto!");
        }          
        
        this.pesquisarTodos();
    }
    
    @FXML
    private void editarProduto(ActionEvent event){
        Produto produtoSelecionado = getTrableRowFocudesProduto();                 
        this.editarProduto(produtoSelecionado);
        
        this.pesquisarTodos();
    }
    
    @FXML
    private void removerProduto(KeyEvent event){
        KeyCode DELETE = KeyCode.DELETE;
        if (event.getCode() != DELETE)
            return;
        
        this.excluirProduto(null);
    }
    
    private void setDoubleClickTable(){
        tableProdutos.setOnMouseClicked(new EventHandler<MouseEvent>() {
            
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                    if(mouseEvent.getClickCount() == 1){
                        btnEditar.setDisable(false);
                        btnExcluir.setDisable(false);
                    }
                    else if(mouseEvent.getClickCount() == 2){
                        Produto produtoSelecionado = getTrableRowFocudesProduto();
                        editarProduto(produtoSelecionado);
                    }
                }
            }
        });
    }
    
    private void editarProduto(Produto produto){
        try{
            
            AddProduto addProduto = new AddProduto();
            addProduto.start(new Stage(), produto);
            addProduto.stageProduto.setOnCloseRequest((e) -> {
                this.pesquisarTodos();
            });
            
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "error ao abir janela de adicionar produto: " + ex);
        }
    }
    
    private void pesquisarTodos(){
        labelInput.setText("");
        this.pesquisarProduto(new ActionEvent());
    }
    
    private int getTableRowFocusedIndex(){
        TableView.TableViewSelectionModel<Produto> selectionModel = tableProdutos.getSelectionModel();
        return selectionModel.getFocusedIndex();
    }

    private Produto getTrableRowFocudesProduto(){
        TableView.TableViewSelectionModel<Produto> selectionModel = tableProdutos.getSelectionModel();
        return selectionModel.getSelectedItem();
    }
    
    private boolean confirmar(String message){
        return JOptionPane.showConfirmDialog(null, message) == 0;
    }
}
