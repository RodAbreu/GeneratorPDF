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
import t1cc2.SaidaParser;

/**
 *
 * @author Gabrieli Santos
 */
public class CasamentoSemanticAnalyser extends casamentoBaseVisitor<Void> {
    SaidaParser out;
    
    public CasamentoSemanticAnalyser(SaidaParser out){
        this.out = out;
    }
}
