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

/**
 *
 * @author rodri
 */
public class CasamentoPDFGenerator extends casamentoBaseVisitor<Void>{

    Document doc;

    public CasamentoPDFGenerator(Document doc) {
        this.doc = doc;
    }
    
    
    
    @Override
    public Void visitPadrinho(casamentoParser.PadrinhoContext ctx) {
        doc.add(new Paragraph(ctx.STRING().getText()));
        return super.visitPadrinho(ctx); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
