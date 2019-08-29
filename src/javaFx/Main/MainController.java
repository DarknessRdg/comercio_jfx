package javaFx.main;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javaFx.styles.colors.Color;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import models.Vendedor;


/**
 *
 * @author Luan Rodrigues
 */
public class MainController implements Initializable {
    @FXML
    private AnchorPane paneForIconProdutos;
    @FXML
    private AnchorPane paneForIconCompra;
    @FXML
    private AnchorPane paneForIconRelatorio;
    @FXML
    private AnchorPane paneForIconFuncionarios;
    @FXML
    private JFXButton btnProdutos;
    @FXML
    private JFXButton btnRelatorio;
    @FXML
    private JFXButton btnCompra;
    @FXML
    private JFXButton btnFuncionarios;
    @FXML
    private AnchorPane anchorConteudo;
    @FXML
    private Label labelVendedorName;

    private final String iconSize = "40px";
    private Text iconProdutos;
    private Text iconCompra;
    private Text iconRelatorio;
    private Text iconFuncionarios;

    private Vendedor vendedor;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setButtonsIcons();
        selectBtn("");
    }
    
    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
        labelVendedorName.setText(vendedor.getNomeCapitalized());
    }

    private void setButtonsIcons() {
        createIconProdutos();
        createIconCompra();
        createIconRelatorio();
        createIconFuncionarios();
    }

    private void createIconProdutos() {
        iconProdutos = GlyphsDude.createIcon(FontAwesomeIcon.ARCHIVE, iconSize);
        iconProdutos.setFill(Color.DARK);
        paneForIconProdutos.getChildren().add(iconProdutos);
        fitToParentIcon(iconProdutos);
    }

    private void createIconCompra() {
        iconCompra = GlyphsDude.createIcon(FontAwesomeIcon.SHOPPING_CART, iconSize);
        iconCompra.setFill(Color.DARK);
        paneForIconCompra.getChildren().add(iconCompra);
        fitToParentIcon(iconCompra);
    }

    private void createIconRelatorio() {
        iconRelatorio = GlyphsDude.createIcon(FontAwesomeIcon.NEWSPAPER_ALT, iconSize);
        iconRelatorio.setFill(Color.DARK);
        paneForIconRelatorio.getChildren().add(iconRelatorio);
        fitToParentIcon(iconRelatorio);
    }

    private void createIconFuncionarios() {
        iconFuncionarios = GlyphsDude.createIcon(FontAwesomeIcon.USERS, iconSize);
        iconFuncionarios.setFill(Color.DARK);
        paneForIconFuncionarios.getChildren().add(iconFuncionarios);
        fitToParentIcon(iconFuncionarios);
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
        AnchorPane paneProdutos = (AnchorPane) FXMLLoader.load(getClass().getResource("/javaFx/produto/Produtos.fxml"));
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
        AnchorPane paneCompra = (AnchorPane) FXMLLoader.load(getClass().getResource("/javaFx/compra/Compra.fxml"));

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
        AnchorPane paneRelatorio = (AnchorPane) FXMLLoader.load(getClass().getResource("/javaFx/relatorio/Relatorio.fxml"));
        this.fitToParent(paneRelatorio);
        this.selectBtn("relatorio");  
    }


    @FXML
    private void abrirFuncionarios(ActionEvent event) {
        try {
            openFuncionariosFxml();
        } catch (IOException ex) {
            System.out.println("error abrirRelatorio() : " + ex);
        }
    }

    private void openFuncionariosFxml() throws IOException {
        AnchorPane paneFuncionarios = (AnchorPane) FXMLLoader.load(getClass().getResource("/javaFx/funcionarios/Funcionarios.fxml"));
        this.fitToParent(paneFuncionarios);
        this.selectBtn("funcionario");
    }

    private void fitToParent(AnchorPane pane){
        anchorConteudo.getChildren().setAll(pane);
        
        AnchorPane.setTopAnchor(pane, 0.0);
        AnchorPane.setLeftAnchor(pane, 4.0);
        AnchorPane.setRightAnchor(pane, 4.0);
        AnchorPane.setBottomAnchor(pane, 0.0);
    }

    private void fitToParentIcon(Text icon) {
        AnchorPane.setTopAnchor(icon, 0.0);
        AnchorPane.setLeftAnchor(icon, 0.0);
        AnchorPane.setRightAnchor(icon, 0.0);
        AnchorPane.setBottomAnchor(icon, 0.0);
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
            case "funcionario":
                selectBtnFuncionario();
                break;

            default:
                resetAllBtn();
                break;
        }
    }
    
    private void selectBtnProduto() {
        iconProdutos.setFill(Color.DANGER);
        btnProdutos.setTextFill(Color.DANGER);

        resetBtnCompra();
        resetBtnRelatorio();
        resetBtnFuncionarios();
    }
    
    private void selectBtnCompra() {
        iconCompra.setFill(Color.DANGER);
        btnCompra.setTextFill(Color.DANGER);

        resetBtnProduto();
        resetBtnRelatorio();
        resetBtnFuncionarios();
    }
    
    private void selectBtnRelatorio() {
        iconRelatorio.setFill(Color.DANGER);
        btnRelatorio.setTextFill(Color.DANGER);

        resetBtnCompra();
        resetBtnProduto();
        resetBtnFuncionarios();
    }

    private void selectBtnFuncionario() {
        iconFuncionarios.setFill(Color.DANGER);
        btnFuncionarios.setTextFill(Color.DANGER);

        resetBtnCompra();
        resetBtnProduto();
        resetBtnRelatorio();
    }
    
    private void resetAllBtn() {
        resetBtnCompra();
        resetBtnProduto();
        resetBtnRelatorio();
        resetBtnFuncionarios();
    }
    
    private void resetBtnProduto() {
        btnProdutos.setTextFill(Color.DARK);
        iconProdutos.setFill(Color.DARK);
    }
    
    private void resetBtnCompra() {
        btnCompra.setTextFill(Color.DARK);
        iconCompra.setFill(Color.DARK);
    }
    
    private void resetBtnRelatorio() {
        btnRelatorio.setTextFill(Color.DARK);
        iconRelatorio.setFill(Color.DARK);
    }

    private void resetBtnFuncionarios() {
        btnFuncionarios.setTextFill(Color.DARK);
        iconFuncionarios.setFill(Color.DARK);
    }
}
