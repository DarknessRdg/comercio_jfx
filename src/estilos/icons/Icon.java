/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estilos.icons;

import javafx.scene.image.Image;

/**
 *
 * @author Luan
 */
public class Icon {
    private final static String urlImg = "/images/icons/";
    
    public static Image produtoNormal = new Image(urlImg + "produto.png");
    public static Image produtoSelected = new Image(urlImg + "produto-danger.png");
    
    public static Image compraNormal = new Image(urlImg + "compra.png");
    public static Image compraSelected  = new Image(urlImg + "compra-danger.png");
    
    public static Image relatorioNormal = new Image(urlImg + "relatorio.png");
    public static Image relatorioSelected = new Image(urlImg + "relatorio-danger.png");
    
}
