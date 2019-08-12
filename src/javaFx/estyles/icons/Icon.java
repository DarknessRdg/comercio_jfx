package javaFx.estyles.icons;

import javafx.scene.image.Image;

/**
 *
 * @author Luan Rodrigues
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
