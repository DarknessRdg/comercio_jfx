package javaFx.produto;


import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javaFx.styles.colors.Color;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Dialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.StageStyle;
import models.Produto;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 *
 * @author luan Rodrigues
 */
public class ProdutosController implements Initializable {
    @FXML
    private AnchorPane paneForIconPesquisar;
    @FXML
    private AnchorPane paneForIconAdicionar;
    @FXML
    private AnchorPane paneForIconEditar;
    @FXML
    private AnchorPane paneForIconExcluir;
    @FXML
    private JFXTextField labelInput;
    @FXML
    private TableView<Produto> tableProdutos;
    @FXML
    private TableColumn<Produto, String> tableColumnCOD;
    @FXML
    private TableColumn<Produto, String> tableColumnNOME;
    @FXML
    private TableColumn<Produto, String> tableColumnPRECO;

    private String iconSize = "28px";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setTableColumnsProprieties();
        setIcons();
    }

    @FXML
    private void clickTable(MouseEvent event) {
        if (event.getClickCount() == 2)
            editarProduto(new ActionEvent());
    }

    @FXML
    private void pesquisarProduto(ActionEvent event) {
        ArrayList<Produto> lista = getListaDeProdutos();
        updateTableView(lista);
    }

    @FXML
    private void addProduto(ActionEvent event) {
        try{
            openAddProduto(null);
        } catch(Exception ex){
            JOptionPane.showMessageDialog(null, "error ao abir janela de adicionar produto: " + ex);
        }
    }

    @FXML
    private void editarProduto(ActionEvent event){
        Produto produtoSelecionado = getTrableRowFocudesProduto();

        try {
            this.openAddProduto(produtoSelecionado);
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, "Error editar Produto " + exception);
        }

        this.pesquisarTodos();
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
    private void removerProduto(KeyEvent event){
        KeyCode DELETE = KeyCode.DELETE;
        if (event.getCode() != DELETE)
            return;

        this.excluirProduto(null);
    }

    private void setTableColumnsProprieties() {
        tableColumnCOD.setCellValueFactory(new PropertyValueFactory<>("codBarras"));
        tableColumnNOME.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnPRECO.setCellValueFactory(new PropertyValueFactory<>("preco"));
    }

    private void setIcons() {
        setIconPesquisar();
        setIconEditar();
        setIconExcluir();
        setIconAdicionar();
    }

    private void setIconPesquisar() {
        Text icon = GlyphsDude.createIcon(FontAwesomeIcon.SEARCH, iconSize);
        icon.setFill(Color.LIGHT);
        paneForIconPesquisar.getChildren().add(icon);

        fitToParentIcon(icon);
    }

    private void setIconAdicionar() {
        Text icon = GlyphsDude.createIcon(FontAwesomeIcon.PLUS, iconSize);
        icon.setFill(Color.LIGHT);
        paneForIconAdicionar.getChildren().add(icon);

        fitToParentIcon(icon);
    }

    private void setIconEditar() {
        Text icon = GlyphsDude.createIcon(FontAwesomeIcon.PENCIL, iconSize);
        icon.setFill(Color.LIGHT);
        paneForIconEditar.getChildren().add(icon);

        fitToParentIcon(icon);
    }

    private void setIconExcluir() {
        Text icon = GlyphsDude.createIcon(FontAwesomeIcon.TRASH_ALT, iconSize);
        icon.setFill(Color.LIGHT);
        paneForIconExcluir.getChildren().add(icon);

        fitToParentIcon(icon);
    }

    private ArrayList<Produto> getListaDeProdutos() {
        String prod = labelInput.getText();
        ArrayList<Produto> lista = Produto.pesquisarProduto(prod);
        
        ordenarListaDeProdutos(lista);
        return lista;
    }
    
    private void ordenarListaDeProdutos(ArrayList<Produto> listaDeProdutos) {
        Collections.sort(listaDeProdutos, new Comparator<Produto>() {
            @Override
            public int compare(Produto first, Produto other) {
                return  first.getNome().compareTo(other.getNome());
            }
        });
    }
    
    private void updateTableView(ArrayList<Produto> listaDeProdutos) {
        ObservableList<Produto> observList = FXCollections.observableArrayList();
        observList.addAll(listaDeProdutos);
        tableProdutos.setItems(observList);
    }

    private void openAddProduto(Produto produto) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("AddProduto.fxml"));
        Dialog dialog = new Dialog();
        dialog.getDialogPane().setContent(root);
        dialog.initStyle(StageStyle.TRANSPARENT);

        dialog.show();
    }

    private void openAddProduto1(Produto produto) throws Exception {
        AddProduto addProduto = new AddProduto();
        if (produto == null)
            addProduto.start(new Stage());
        else
            addProduto.start(new Stage(), produto);
    }

    private void pesquisarTodos() {
        labelInput.setText("");
        this.pesquisarProduto(new ActionEvent());
    }

    private Produto getTrableRowFocudesProduto() {
        TableView.TableViewSelectionModel<Produto> selectionModel = tableProdutos.getSelectionModel();
        return selectionModel.getSelectedItem();
    }
    
    private boolean confirmar(String message){
        return JOptionPane.showConfirmDialog(null, message) == 0;
    }

    private void fitToParentIcon(Text icon) {
        AnchorPane.setTopAnchor(icon, 2.0);
        AnchorPane.setLeftAnchor(icon, 0.0);
    }
}
