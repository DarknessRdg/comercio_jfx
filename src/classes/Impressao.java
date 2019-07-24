/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.awt.Desktop;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.Doc;
import javax.print.PrintService;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.JobName;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;
import javax.swing.JOptionPane;
/**
 *
 * @author Luan
 */
public class Impressao {
    private static PrintService impressora;

    private ArrayList<ProdutoQnt> produtos;
    private double total;
    private double desconto;
    
    private static final int LENLINHA = 45;
    private static final int LENQNT = 9;
    private static final int LENPRECO = 9;
    private static final int LENDESCRICAO = LENLINHA - LENQNT - LENPRECO;
    
    
    private static final String ERROR = "Error ao tentar imprimri cupom não fiscal";

    public Impressao(ArrayList<ProdutoQnt> produtos, double desconto) {
        this.produtos = produtos;
        total = this.calcTotal();
        this.desconto = desconto;
    }
    
    public void imprimir(){
        this.lala();
    }
    
    private void prepararImpressao(String texto){
        /*
        * Conectar com a impressora e imprimir Notinha
        */
        try{
            InputStream print = new ByteArrayInputStream("HEEELOOOO WORLDLSDSDSLLLLLL".getBytes());
            
            DocFlavor docFlavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
            HashDocAttributeSet hashdoc = new HashDocAttributeSet();
            
            Doc documentText = new SimpleDoc(print, docFlavor, hashdoc);
            PrintService impressora = PrintServiceLookup.lookupDefaultPrintService();
            
            // pega a impressora padrao
            PrintRequestAttributeSet printerAttributes = new HashPrintRequestAttributeSet();
            printerAttributes.add(new JobName("Imprimir notinha", null));
            printerAttributes.add(OrientationRequested.PORTRAIT);
            printerAttributes.add(MediaSizeName.ISO_A4);
            
            DocPrintJob printJob = impressora.createPrintJob();
            try{
                printJob.print(documentText, (PrintRequestAttributeSet) printerAttributes);
            }catch(PrintException ex){
                JOptionPane.showMessageDialog(null, this.ERROR, "Error", JOptionPane.ERROR_MESSAGE);
            }
            
            print.close();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, this.ERROR);
        }
    }
    
    private String getTextoNotinha(){
        String notinha = 
                  "DESENVOLVIMENTO DE SISTESMAS - LUAN RODRIGUES\n\r"
                
                + "---------------------------------------------\n\r"
                + "           CUPOM NAO FISCAL                  \n\r"
                + "\n\r"
                
                + "QUANTIDADE           DESCRICAO           PREÇO\n\r"
                + this.getListaProduto()
                
                + "---------------------------------------------\n\r"
                + "TOTAL BRUTO" + this.formatedReal(total,  LENLINHA - "TOTAL BRUTO".length())
                + "DESCONTO" + this.formatedReal(desconto,  LENLINHA - "DESCONTO".length())
                + "TOTAL" + this.formatedReal(this.calcDesconto(),  LENLINHA - "TOTAL".length())
                + "---------------------------------------------\n\r"
                + "\n\n\r"
                + "         OBRIGADO, VOLTE SEMPRE !            \n\r\f";
        
        return notinha;
    }
    
    private String getListaProduto(){
        String listaDeProdutos = "";
        
        for(int i = 0; i < this.produtos.size(); i++){
            ProdutoQnt produto = produtos.get(i);
            listaDeProdutos += this.formatedQuantidade(produto.getQuantidade())
                               + this.formatedDescricao(produto.getNome())
                               + this.formatedPreco(produto.getPreco());
            
            listaDeProdutos += "\n\r";
        }
        
        return listaDeProdutos;
    }
    
    private String formatedQuantidade(int quantidadeProduto){
        String quantidade = String.format("%d", quantidadeProduto);
        int qntEspacos = LENPRECO - quantidade.length();
        
        String espacos = "";
        for(int i = 0; i < qntEspacos; i++)
             espacos += " ";
        
        return quantidade + espacos;
    }
    
    private String formatedDescricao(String descricao){
        descricao = descricao.toUpperCase();
        int qntEspacos = LENDESCRICAO - descricao.length();
        
        String espacos = "";
        for(int i = 0; i < qntEspacos / 2; i++)
            espacos += " ";
        descricao = espacos + descricao;
        
        for(int i = 0; i < qntEspacos / 2; i++)
            descricao += " ";
        return descricao;
    }
    
    private String formatedPreco(String preco){
        int qntEspacos = LENPRECO - preco.length();
        
        String espacos = "";
        for(int i = 0; i < qntEspacos; i++)
            espacos += " ";
        preco = espacos + preco;
        return preco;
    }
    
    private String formatedReal(double valorReal, int lenLinha){
        String retorno = "R$ "; lenLinha -= 3;
        String valor = String.format("%.2f", valorReal).replace(".", ",");
        int qntEspacos = lenLinha - valor.length();
        
        String espacos = "";
        for(int i = 0; i < qntEspacos; i++)
            espacos += " ";
        retorno += valor;
        return espacos + retorno + "\n\r";
    }
    
    private double calcTotal(){
        double total = 0;
        for(int i = 0; i < this.produtos.size(); i++){
            ProdutoQnt produto = produtos.get(i);
            
            total += produto.getPrecoDouble() * produto.getQuantidade();
        }
        return total;
    }
    
    private double calcDesconto(){
        return this.total - this.total * this.desconto;
    }
    
    private void lala(){
        String diretorioAtual = Paths.get(".").toAbsolutePath().normalize().toString();
        
        try {
            File file = new File(diretorioAtual, "notinha.txt");
            
            FileWriter arq = new FileWriter(file);
            PrintWriter gravArg = new PrintWriter(arq);
            
            gravArg.println(this.getTextoNotinha());
            
            gravArg.close();
            arq.close();
            
            Desktop desk = Desktop.getDesktop();
            desk.print(file);
        } catch (IOException ex) {
            Logger.getLogger(Impressao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
