/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.cc2.generatorpdf;

import br.ufscar.dc.cc2.casamentoparser.casamentoLexer;
import br.ufscar.dc.cc2.casamentoparser.casamentoParser;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import t1cc2.SaidaParser;

/**
 *
 * @author rodri
 */
public class Main {

    public static void main(String args[]) throws IOException {
        String casamentoFile = args[0];
        File pdfFile = new File(args[1]);
        SaidaParser out = new SaidaParser();
        
        //System.out.println(pdfFile.getAbsolutePath());

        //pdfFile.getParentFile().mkdirs();
        
        FileOutputStream fos = new FileOutputStream(pdfFile);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        CharStream input = CharStreams.fromFileName(casamentoFile); //entrada
        casamentoLexer lexer = new casamentoLexer(input);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        casamentoParser parser = new casamentoParser(tokenStream);
        //parser.addErrorListener(new ErrorListener(out));
        casamentoParser.ProgramaContext arvore = parser.programa();
        
        //analise semantica
        if(!out.isModificado()){
            CasamentoSemanticAnalyser semantico = new CasamentoSemanticAnalyser(out);
            semantico.visitPrograma(arvore);
        }

        document.add(new Paragraph("Documento gerado"));
        
        
        
        CasamentoPDFGenerator cpg = new CasamentoPDFGenerator(document);
        cpg.visit(arvore);
        
        document.close();

    }

}
