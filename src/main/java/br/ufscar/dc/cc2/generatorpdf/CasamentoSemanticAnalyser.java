/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.cc2.generatorpdf;

import br.ufscar.dc.cc2.casamentoparser.casamentoBaseVisitor;
import br.ufscar.dc.cc2.casamentoparser.casamentoParser;
import br.ufscar.dc.cc2.generatorpdf.TabelaPadrinhos;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 *
 * @author Gabrieli Santos
 */
public class CasamentoSemanticAnalyser extends casamentoBaseVisitor<Void> {

    //PilhaDeTabelas pilhaDeTabelas = new PilhaDeTabelas();
    
    ArrayList<TabelaPadrinhos> padrinhos = new ArrayList<>(); 
    
    
    //TabelaDeSimbolos presentes;

    boolean erroSemantico = false;
    
    private void imprimirMensagemErroSemantico(String txt)
    {
        doc.add(new Paragraph(txt).setFontSize(8).setFontColor(Color.RED));
        erroSemantico = true;
    }

    public boolean isErroSemantico() {
        return erroSemantico;
    }
    
    
    //PilhaDeTabelas pilhaDeTabelas = new PilhaDeTabelas;
    Document doc;

    public CasamentoSemanticAnalyser(Document doc) {
        this.doc = doc;
    }

    @Override
    public Void visitPrograma(casamentoParser.ProgramaContext ctx) {
//        pilhaDeTabelas.empilhar(new TabelaDeSimbolos("global"));
        super.visitPrograma(ctx);
//        pilhaDeTabelas.desempilhar();
         // TabelaPadrinhos padrinho = new TabelaPadrinhos("global");
          //padrinhos.add(padrinho);

        return null;
    }

    /*@Override
    public Void visitListaPresentes(casamentoParser.ListaPresentesContext ctx) {
        pilhaDeTabelas.empilhar(new TabelaDeSimbolos("local"));
        super.visitListaPresentes(ctx);
        pilhaDeTabelas.desempilhar();

        return null;
    }*/

    @Override
    public Void visitPadrinho(casamentoParser.PadrinhoContext ctx) {
        String textoSigla = ctx.NOME().getText();
        String textoCasal = ctx.STRING().getText();
        
        
        TabelaPadrinhos padrinho = new TabelaPadrinhos("global");
        
        for(int i=0; i < padrinhos.size(); i++){
            if(!padrinhos.get(i).existeSimbolo(textoSigla)){
                padrinho.adicionarSimbolo(textoSigla, textoCasal);
                padrinhos.add(padrinho);
            }
            else{
                System.out.println("estou entrando aqui");
                imprimirMensagemErroSemantico("A sigla " + textoSigla + " ja foi atribuida anteriormente para outro casal de padrinhos");
            }
        }
        
        System.out.println(padrinhos);

        /*//se a sigla de padrinho ja esta na tabela de simbolos
        if (pilhaDeTabelas.existeSimbolo(textoSigla)) {
            imprimirMensagemErroSemantico("A sigla " + textoSigla + " ja foi atribuida anteriormente para outro casal de padrinhos");
        } else {
            pilhaDeTabelas.topo().adicionarSimbolo(textoSigla, textoCasal);
        }*/
        return null;
    }

    /*@Override
    public Void visitPresente(casamentoParser.PresenteContext ctx) {
        String textoPresente = ctx.descricao.getText();
        String textoSite = ctx.url.getText();
        
        List<String> padrinhosPresente = new ArrayList<>();
        
        for(TerminalNode tSigla: ctx.NOME()) {
            String textoSigla = tSigla.getText();

            if (!pilhaDeTabelas.existeSimbolo(textoSigla)) {
                imprimirMensagemErroSemantico("O casal " + textoSigla + " nao sao padrinhos");
            }
            else {
                padrinhosPresente.add(textoSigla);
            }
        }
        
        // verificar se o presente já existe
        
        // verificar se o padrinho já aparece em algum presente
        
        // Adicionar a entrada na tabela de presentes
        presentes.adicionarSimbolos(textoSite, textoPresente, padrinhosPresente);
        
            else {
                if (!pilhaDeTabelas.topo().existeSimbolo(textoSigla)) {
                    pilhaDeTabelas.topo().adicionarSimbolo(textoPresente, textoSite, textoSigla);
                } else {
                    imprimirMensagemErroSemantico("O casal " + textoSigla + " foi escalado para mais de um presente");
                }
            }
    }
            
        /*TODO:
        * Tratar quando tiver mais de uma sigla por presente
        * Tratar quando nao tiver padrinhos alocados em um presente
         
        return null;
    }*/

}
