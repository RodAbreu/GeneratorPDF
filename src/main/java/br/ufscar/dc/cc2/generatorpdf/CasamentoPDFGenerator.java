/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.cc2.generatorpdf;

import br.ufscar.dc.cc2.casamentoparser.casamentoBaseVisitor;
import br.ufscar.dc.cc2.casamentoparser.casamentoParser;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import java.util.List;

/**
 *
 * @author rodri
 */
public class CasamentoPDFGenerator extends casamentoBaseVisitor<Void>{

    Document doc;

    public CasamentoPDFGenerator(Document doc) {
        this.doc = doc;
    }
    
    
//    @Override
//    public Void visitPadrinho(casamentoParser.PadrinhoContext ctx) {
//        doc.add(new Paragraph("Padrinhos:").setFontSize(16));
//        doc.add(new Paragraph(ctx.STRING().getText()));        
//        return super.visitPadrinho(ctx); //To change body of generated methods, choose Tools | Templates.
//    }
    
    @Override
    public Void visitListaPadrinhos(casamentoParser.ListaPadrinhosContext ctx){
        doc.add(new Paragraph("Padrinhos:").setFontSize(16));
        for(int i = 0; i < ctx.padrinho().size(); i++ ){
            doc.add(new Paragraph(ctx.padrinho(i).getText()));
        }        
        return super.visitListaPadrinhos(ctx);
    }
    
    @Override
    public Void visitListaPresentes(casamentoParser.ListaPresentesContext ctx){
        doc.add(new Paragraph("Presentes:").setFontSize(16));
        for(int i=0; i<ctx.presente().size(); i++ ){
            doc.add(new Paragraph(ctx.presente(i).getText()));
        }
        
        return super.visitListaPresentes(ctx);
    }
    
    /*@Override
    public Void visitPresente(casamentoParser.PresenteContext ctx){
        doc.add(new Paragraph("Presentes:").setFontSize(16));
        doc.add(new Paragraph(ctx.descricao.getText()));
        doc.add(new Paragraph(ctx.url.getText()));
        return super.visitPresente(ctx);
    }*/
    
    @Override
    public Void visitListaConvidados(casamentoParser.ListaConvidadosContext ctx){
        doc.add(new Paragraph("Convidados:").setFontSize(16));  
        for(int i = 0; i < ctx.STRING().size(); i++ ){
            doc.add(new Paragraph(ctx.STRING(i).getText()));
        }
        return super.visitListaConvidados(ctx);        
    }
    
//    @Override
//    public Void visitListaServicos(casamentoParser.ListaServicosContext ctx){
//        doc.add(new Paragraph("Serviços").setFontSize(16));
//        
//        if(ctx. ){
//            
//        }
//        return super.visitListaServicos(ctx);
//    }
}
