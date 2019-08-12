package javaFx.Compra;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXToggleButton;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Luan
 */
public class CompraController implements Initializable {
    @FXML
    private AnchorPane panePrincipal;
    @FXML
    private JFXToggleButton compraFiada;
    @FXML
    private JFXSpinner spinner;
    @FXML
    private JFXButton btnFinalizarCompra;
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
    private ContextMenu contextMenu;
    @FXML
    private MenuItem menuItem;
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
    
    private void clearAll() {
        this.carrinho = new Carrinho();
        this.atualizarCampos();
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
        
        this.deletarProduto(null);
        
    }
    
    @FXML
    private void finalizarCompra(ActionEvent event){ 
        this.dialogFinalizarCompra();
//        String desconto_entrada = JOptionPane.showInputDialog("Digite o desconto em % (porcentagem):");
//        Double desconto = 0.0;
//        try{
//            desconto = Integer.parseInt(desconto_entrada) / 100.0;
//        }catch(NumberFormatException ex){
//        }
//        
//        carrinho.addDesconto(desconto);
//        String compra = carrinho.finalizarCompra("");
//        if(compra.equals("Compra finalizada com sucesso!")){
//            Impressao impressao = new Impressao(this.carrinho, desconto);
//            impressao.imprimir();
//            
//            this.carrinho = new Carrinho();
//            this.tableCompra.getItems().clear();
//            this.atualizarCampos();
//        }
//        JOptionPane.showMessageDialog(null, compra);
    }
    
    @FXML
    private void deletarProduto(ActionEvent event) {
        int indexSelected = this.getTableRowFocusedIndex();
        ProdutoQnt produtoRemover = this.getTrableRowFocudesProduto();
        
        carrinho.removerProduto(produtoRemover);
        tableCompra.getItems().remove(indexSelected);
        this.atualizarCampos();
    }
    
    @FXML
    private void desconto(ActionEvent event){ 
        String desconto_entrada = JOptionPane.showInputDialog("Digite o desconto em % (porcentagem):");
        double desconto = Double.parseDouble(desconto_entrada.replace(",", ".")) / 100;
        
        ProdutoQnt produtoSelecionado = this.getTrableRowFocudesProduto();
        produtoSelecionado.setPreco(produtoSelecionado.getPrecoDouble() * (1 - desconto));
        this.atualizarCampos();
    }
    
    private void dialogFinalizarCompra() {
        try{
            openFinalizarCompraFXM();
        }catch(IOException ex) {
            JOptionPane.showMessageDialog(null, "Error abrir finalizacao da compra : " + ex);
        }
    }
    
    private void openFinalizarCompraFXM() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/javaFx/Compra/FinalizarCompra.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        
        initDataOnFinalizarCompra(loader);
        setStageFinalizarCompra(scene);
    }
    
    private void initDataOnFinalizarCompra(FXMLLoader loader) {
        FinalizarCompraController controller = loader.getController();
        controller.initData(this.carrinho, this.compraFiada.isSelected());
    }
    
    private void setStageFinalizarCompra(Scene scene) {
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);

        addBlur();
        
        stage.show();
        stage.setOnCloseRequest((e) -> {
            removerBlur();
        });
    }
    
    private void addBlur() {
        // TODO implements add blur when FinalizarCompra is opened
    }
    
    private void removerBlur() {
        // TODO implements remover blur when FinalizarCompra is closed
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
        tableCompra.getItems().clear();
        for(ProdutoQnt produto: carrinho.getListaProdutos()) 
            tableCompra.getItems().add(produto);
        textCodigoProduto.requestFocus();
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
