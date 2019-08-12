
package javaFx.Compra;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Luan
 */
public class FinalizarCompraController implements Initializable {
    @FXML
    private Label labelTotal;
    @FXML
    private JFXTextField textCPF;
    @FXML
    private AnchorPane paneButtons;
    @FXML
    private JFXTextField textDinheiro;
    @FXML
    private JFXTextField textDesconto;
    @FXML
    private Label labelTroco;
    @FXML
    private AnchorPane paneSpinner;
    @FXML
    private AnchorPane paneMenu;
    private boolean finalizadaComSucesso;
    private boolean compraFiada;
    private Carrinho carrinho;
    private double totalCompra;
    private final String TOTAL = "Total: R$ ";
    private final String TROCO = "Troco: R$ ";
            
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.carrinho = new Carrinho();
        this.finalizadaComSucesso = false;
        
        // add focus lost to textDesconto
        textDesconto.focusedProperty().addListener((obs, oldVal, newV) -> {
           if (!newV) { // focus lost
               System.out.println("add ");
              this.addDesconto();
           }
        });
    }
    
    public void initData(Carrinho carrinho, boolean fiada) {
        this.compraFiada = fiada;
        this.carrinho = carrinho;
        this.totalCompra = carrinho.getPreco();
        this.labelTotal.setText(TOTAL + String.format("%.2f", carrinho.getPreco()));
    }
    
    public String getCpfCliente() {
        return this.textCPF.getText();
    }
    
    private void addDesconto() {
        double desconto = Double.parseDouble(textDesconto.getText().replace(",", "."));
        carrinho.addDesconto(desconto);
    }
    
    @FXML
    private void fomartarCpf(ActionEvent event) {
        String cpf = this.textCPF.getText().replace("-", "");
        if(cpf.length() > 11)
            return;
        String x = cpf.subSequence(0, 3) + "." + 
                   cpf.subSequence(3, 6) + "." +
                   cpf.subSequence(6, 9) + "-" + 
                   cpf.substring(9);
    }
    
    @FXML
    private void calcularTroco(ActionEvent event) {
        double dinehiro = Double.parseDouble(
                this.labelTroco.getText().replace(",", ".").replace(TROCO, "").trim());
        if (dinehiro < this.totalCompra){
            this.labelTroco.setText(TROCO + "00,00");
            return;
        }
        
        this.labelTroco.setText(TROCO + String.format("%.2f", dinehiro - totalCompra));
    }
    
    @FXML
    private void cancelar(MouseEvent event) {
        System.exit(0);
    }
    
    @FXML
    private void finalizar(MouseEvent event) {
        carrinho.finalizarCompra("", this.compraFiada);
    }
}
