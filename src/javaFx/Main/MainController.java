/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaFx.Main;

import javaFx.estyles.icons.Icon;
import javaFx.estyles.colors.Color;
import javax.swing.JOptionPane;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import models.Vendedor;

/**
 * FXML Controller class
 *
 * @author Luan
 */
public class MainController implements Initializable {
    @FXML
    private JFXButton btnProdutos;
    @FXML
    private JFXButton btnRelatorio;
    @FXML
    private JFXButton btnNovaCompra;
    @FXML
    private AnchorPane anchorMenu;
    @FXML
    private Pane panePerfil;
    @FXML
    private AnchorPane anchorConteudo;
    @FXML
    private ImageView imgProduto;
    @FXML
    private ImageView imgCompra;
    @FXML
    private ImageView imgRelatorio;
    @FXML
    private Label labelVendedorName;

    private Vendedor vendedor;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.selectBtn("");
    }
    
    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
        labelVendedorName.setText(vendedor.getNomeCapitalized());
    }
    
    @FXML
    private void abrirProdutos(ActionEvent event) {
        try {
            openProdutosFxml();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "error abrirProdutos() IOexcpt: " + ex);
        }
    }
    
    private void openProdutosFxml() throws IOException {
        AnchorPane paneProdutos = (AnchorPane) FXMLLoader.load(getClass().getResource("/javaFx/Produto/Produtos.fxml"));
        fitToParent(paneProdutos);
        selectBtn("produto");
    }
    
    @FXML
    private void abrirCompra(ActionEvent event) {
        try {
            openCompraFxml();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "error abrirCompra() : " + ex);
        }
    }
    
    private void openCompraFxml() throws IOException {
        AnchorPane paneCompra = (AnchorPane) FXMLLoader.load(getClass().getResource("/javaFx/Compra/Compra.fxml"));
        fitToParent(paneCompra);
        selectBtn("compra");
    }
    
    @FXML
    private void abrirRelatorio(ActionEvent event) {
        try {
            openRelatorioFxml();
        } catch (IOException ex) {
            System.out.println("error abrirRelatorio() : " + ex);
        }
    }
    
    private void openRelatorioFxml() throws IOException {
        AnchorPane paneRelatorio = (AnchorPane) FXMLLoader.load(getClass().getResource("/javaFx/Relatorio/Relatorio.fxml"));
        this.fitToParent(paneRelatorio);
        this.selectBtn("relatorio");  
    }
    
    private void fitToParent(AnchorPane pane){
        anchorConteudo.getChildren().setAll(pane);
        
        AnchorPane.setTopAnchor(pane, 0.0);
        AnchorPane.setLeftAnchor(pane, 4.0);
        AnchorPane.setRightAnchor(pane, 4.0);
        AnchorPane.setBottomAnchor(pane, 0.0);
    }
    
    private void selectBtn(String buttonName){  
        switch (buttonName) {
            case "produto":
                selectBtnProduto();
                break;
            case "compra":
                selectBtnCompra();
                break;
            case "relatorio":
                selectBtnRelatorio();
                break;
            
            default:
                resetAllBtn();
                break;
        }
    }
    
    private void selectBtnProduto() {
        btnProdutos.setTextFill(Color.danger);
        imgProduto.setImage(Icon.produtoSelected);

        resetBtnCompra();
        resetBtnRelatorio();
    }
    
    private void selectBtnCompra() {
        btnNovaCompra.setTextFill(Color.danger);
        imgCompra.setImage(Icon.compraSelected);

        resetBtnProduto();
        resetBtnRelatorio();
    }
    
    private void selectBtnRelatorio() {
        btnRelatorio.setTextFill(Color.danger);
        imgRelatorio.setImage(Icon.relatorioSelected);

        resetBtnCompra();
        resetBtnProduto();
    }
    
    private void resetAllBtn() {
        resetBtnCompra();
        resetBtnProduto();
        resetBtnRelatorio();
    }
    
    private void resetBtnProduto(){
        btnProdutos.setTextFill(Color.dark);        
        imgProduto.setImage(Icon.produtoNormal);
    }
    
    private void resetBtnCompra(){
        btnNovaCompra.setTextFill(Color.dark);
        imgCompra.setImage(Icon.compraNormal);
    }
    
    private void resetBtnRelatorio(){
        btnRelatorio.setTextFill(Color.dark);
        imgRelatorio.setImage(Icon.relatorioNormal);
    }
}
