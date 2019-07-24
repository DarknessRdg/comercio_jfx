/**
 * Classe para backend da view de Nova Compra;
 */
package projetocomerciojfx.controller;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import classes.ProdutoQnt;
import classes.Carrinho;
import classes.Impressao;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Luan
 */
public class FXMLNovaCompraController implements Initializable {
    @FXML
    private AnchorPane panePrincipal;
    @FXML
    private TableView<ProdutoQnt> tableCompra;
    @FXML
    private JFXTextField textCodigoProduto;
    @FXML
    private JFXTextField textQuantidade;
    @FXML
    private Label labelTotal;
    @FXML
    private Label labelMensagem;
    @FXML
    private TableColumn<ProdutoQnt, Integer> tableColumnQUANTIDADE;
    @FXML
    private TableColumn<ProdutoQnt, String> tableColumnNOME;
    @FXML
    private TableColumn<ProdutoQnt, String> tableColumnPRECO;
    private Carrinho carrinho;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carrinho = new Carrinho();
        tableColumnNOME.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnQUANTIDADE.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        tableColumnPRECO.setCellValueFactory(new PropertyValueFactory<>("preco"));
    }
    
    @FXML
    private void addProduto(ActionEvent event) {
        String cod = this.getCodigo();
        int qnt = this.getQuantidade();
        
        if(cod.equals("")){
            this.showMessage("Digite um código de produto válido!");
            return;
        }else if(qnt == 0){
            this.showMessage("Quantidade de produto zero. Altere a quantidade!");
            return;
        }else if(qnt < 0){
            this.showMessage("Quantidade negativa. Altere a quantidade para uma positiva!");
        }
        
        ProdutoQnt ultimoInserido = carrinho.addProduto(cod, qnt);
        if (ultimoInserido == null){
            this.showMessage("Produto não encontrado");
            return;
        }
        
        tableCompra.getItems().add(ultimoInserido);
        this.atualizarCampos();
    }
    
    @FXML
    private void removerProduto(KeyEvent event){
        KeyCode DELETE = KeyCode.DELETE;
        if (event.getCode() != DELETE)
            return;
        
        // pressed delete;
        int indexSelected = this.getTableRowFocusedIndex();
        ProdutoQnt produtoRemover = this.getTrableRowFocudesProduto();
        
        carrinho.removerProduto(produtoRemover);
        tableCompra.getItems().remove(indexSelected);
        
        this.atualizarCampos();
    }
    
    @FXML
    private void finalizarCompra(ActionEvent event){
        String desconto_entrada = JOptionPane.showInputDialog("Digite o desconto em % (porcentagem):");
        Double desconto = 0.0;
        try{
            desconto = Integer.parseInt(desconto_entrada) / 100.0;
        }catch(NumberFormatException ex){
        }
        
        carrinho.addDesconto(desconto);
        String compra = carrinho.finalizarCompra(1);
        if(compra.equals("Compra finalizada com sucesso!")){
            Impressao impressao = new Impressao(this.carrinho.getListaProdutos(), 0);
            impressao.imprimir();
            
            this.carrinho = new Carrinho();
            this.tableCompra.getItems().clear();
            this.atualizarCampos();
        }
        JOptionPane.showMessageDialog(null, compra);
    }
    
    private void showMessage(String message){
        labelMensagem.setText(message);
    }
    
    private void atualizarCampos(){
        labelMensagem.setText("");
        labelTotal.setText(carrinho.getPrecoFormated());
        textCodigoProduto.setText("");
        textQuantidade.setText("1");
        
        textCodigoProduto.clear();
    }
    
    private int getQuantidade(){
        return Integer.parseInt(textQuantidade.getText());
    }
    
    private String getCodigo(){
        return textCodigoProduto.getText();
     }
    
    private int getTableRowFocusedIndex(){
        TableView.TableViewSelectionModel<ProdutoQnt> selectionModel = tableCompra.getSelectionModel();
        return selectionModel.getFocusedIndex();
    }
    
    private ProdutoQnt getTrableRowFocudesProduto(){
        TableView.TableViewSelectionModel<ProdutoQnt> selectionModel = tableCompra.getSelectionModel();
        return selectionModel.getSelectedItem();
    }
}
