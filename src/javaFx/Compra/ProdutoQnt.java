package javaFx.compra;

import models.Produto;
/**
 *
 * @author Luan
 */

public class ProdutoQnt extends Produto{
    private int quantidade;
    
    ProdutoQnt(String cod, int quantidade){
        super(cod);
        this.quantidade = quantidade;
    }
    
    public int getQuantidade(){
        return this.quantidade;
    }
    
    public boolean igual(ProdutoQnt other) {
        return this.equals(other) && this.quantidade == other.getQuantidade();
    }
}