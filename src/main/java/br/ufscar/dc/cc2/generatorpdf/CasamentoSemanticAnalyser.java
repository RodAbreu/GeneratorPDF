/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.cc2.generatorpdf;

import br.ufscar.dc.cc2.casamentoparser.casamentoBaseVisitor;
import br.ufscar.dc.cc2.casamentoparser.casamentoParser;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

/**
 *
 * @author Gabrieli Santos
 */
public class CasamentoSemanticAnalyser extends casamentoBaseVisitor<Void> {
    PilhaDeTabelas pilhaDeTabelas = new PilhaDeTabelas();
    
    //PilhaDeTabelas pilhaDeTabelas = new PilhaDeTabelas;
    Document doc;
    
    public CasamentoSemanticAnalyser(Document doc){
        this.doc = doc;
    }
    
    @Override
    public Void visitPrograma(casamentoParser.ProgramaContext ctx){
        pilhaDeTabelas.empilhar(new TabelaDeSimbolos("global"));
        super.visitPrograma(ctx);
        pilhaDeTabelas.desempilhar();
               
        return null;
    }
    
    @Override 
    public Void visitListaPresentes(casamentoParser.ListaPresentesContext ctx){
        pilhaDeTabelas.empilhar(new TabelaDeSimbolos("local"));
        super.visitListaPresentes(ctx);
        pilhaDeTabelas.desempilhar();
               
        return null;
    }
    
    @Override
    public Void visitPadrinho(casamentoParser.PadrinhoContext ctx){
        String textoSigla = ctx.NOME().getText();
        String textoCasal = ctx.STRING().getText();
        
        //se a sigla de padrinho ja esta na tabela de simbolos
        if(pilhaDeTabelas.existeSimbolo(textoSigla)){
            doc.add(new Paragraph("A sigla " + textoSigla + " ja foi atribuida anteriormente para outro casal de padrinhos").setFontSize(8).setFontColor(Color.RED));
        }
        else{
            pilhaDeTabelas.topo().adicionarSimbolo(textoSigla, textoCasal);
        }
        return null;
    }
    
    @Override
    public Void visitPresente(casamentoParser.PresenteContext ctx){
        String textoPresente = ctx.descricao.getText();
        String textoSite = ctx.url.getText();
        String textoSigla = ctx.NOME(0).getText();
       
        if(!pilhaDeTabelas.topo().existeSimbolo(textoSigla)){
            pilhaDeTabelas.topo().adicionarSimbolo(textoPresente, textoSite, textoSigla);  
        }
        else{
            doc.add(new Paragraph("O casal " + textoSigla + " foi escalado para mais de um presente").setFontSize(8).setFontColor(Color.RED));
        }
        
        /*TODO:
        * Tratar quando tiver mais de uma sigla por presente
        * Tratar quando nao tiver padrinhos alocados em um presente
        */
        
    
        return null;
    }
    
}
