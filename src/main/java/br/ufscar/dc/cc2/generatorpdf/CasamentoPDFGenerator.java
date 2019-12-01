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
import com.itextpdf.layout.border.Border;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.TabSettings;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.ListItem;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Tab;
import com.itextpdf.layout.element.TabStop;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TabAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.text.pdf.PdfPTable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rodri
 */
public class CasamentoPDFGenerator extends casamentoBaseVisitor<Void>{

    Document doc;
    List presentes_nao_atribuidos;
    List padrinhos_nao_atribuidos;
    Paragraph p = new Paragraph();
    float custoFotografo;
    float custoBuffet;
    float custoCerimonial;
    float custoDecoracao;
    float custoLocal;
    float custoConvites;
    float custoLuaDeMel;
        

    public CasamentoPDFGenerator(Document doc) {
        this.doc = doc;
        this.presentes_nao_atribuidos = new ArrayList();
        this.padrinhos_nao_atribuidos = new ArrayList();
        p.addTabStops(new TabStop(56f,TabAlignment.LEFT));
        float custoFotografo = 0;
        float custoDecoracao = 0;
        float custoBuffet = 0;
        float custoCerimonial = 0;
        float custoLocal = 0;
        float custoConvites = 0;
        float custoLuaDeMel = 0;
    }
    
    public Void visitTitulo(casamentoParser.TituloContext ctx){
        doc.add(new Paragraph("Planejamento para o casamento de \n"+ctx.STRING().getText()+"").setFontSize(24).setTextAlignment(TextAlignment.CENTER));
        return super.visitTitulo(ctx);
    }
    
    public Void visitNumConvidados(casamentoParser.NumConvidadosContext ctx){
//        doc.add(Chunk.TABBING);
//        doc.add(new Chunk("Hello World with tab."));
        Paragraph p = new Paragraph();
        doc.add(new Paragraph("Número de convidados: "+ctx.NUM_INT().getText()).setFontSize(16));
        return super.visitNumConvidados(ctx);
    }
    
    public Void visitData(casamentoParser.DataContext ctx){
        doc.add(new Paragraph("Data do casamento: "+ctx.DATA().getText()).setFontSize(16));
        return super.visitData(ctx);
    }
    
    public Void visitOrcamento(casamentoParser.OrcamentoContext ctx){
        doc.add(new Paragraph("Orçamento disponivel: R$"+ctx.NUM_REAL().getText()).setFontSize(16));
        return super.visitOrcamento(ctx);
    }
    
    @Override
    public Void visitListaPadrinhos(casamentoParser.ListaPadrinhosContext ctx){
        doc.add(new Paragraph("Lista de padrinhos:").setFontSize(16));
        for(int i = 0; i < ctx.padrinho().size(); i++ ){
            doc.add(new Paragraph("Casal de padrinhos "+(i+1)+": "+ctx.padrinho(i).completo.getText()+"\n Sigla correspondente: "+ctx.padrinho(i).sigla.getText()).setFontSize(12));
            //populando a lista de padrinhos
            this.padrinhos_nao_atribuidos.add(ctx.padrinho(i).sigla.getText());
        }        
        return super.visitListaPadrinhos(ctx);
    }
    
    public Void visitListaPresentes(casamentoParser.ListaPresentesContext ctx){
        
        doc.add(new Paragraph("Lista de presentes:").setFontSize(16));
        float [] pointColumnWidths = {50F,250F, 220F};
        Table tablePresentes = new Table(pointColumnWidths);        
        tablePresentes.addCell(new Cell().add(("")));
        tablePresentes.addCell(new Cell().add(("Descrição")));
        tablePresentes.addCell(new Cell().add(("URL")));
        //aprensentando todos os presentes
        
        String auxS;
        for(int i=0; i<ctx.presente().size(); i++ ){
            auxS = "";
            tablePresentes.addCell(new Cell().add((i+1+"")));
            tablePresentes.addCell(new Cell().add(ctx.presente(i).descricao.getText()));
            tablePresentes.addCell(new Cell().add(ctx.presente(i).url.getText()));
//            tablePresentes.addCell(new Cell().add(ctx.presente(i).descricao.getText()));
//            doc.add(new Paragraph((i+1)+": "+ctx.presente(i).descricao.getText()));
//            doc.add(new Paragraph("URL de sugestão: "+ctx.presente(i).url.getText()).setFontSize(12));      
//            for(int j=0;j<ctx.presente(i).NOME().size();j++){
//                auxS += ctx.presente(i).NOME().get(j).getText()+"; ";
////                doc.add(new Paragraph("Sigla dos padrinhos atribuidos: "+ctx.presente(i).NOME().get(j).getText()).setFontSize(12));   
//            }
//            tablePresentes.addCell(new Cell().add(auxS));
        }
        
        tablePresentes.setKeepTogether(true);
        doc.add(tablePresentes);
       
        doc.add(new Paragraph("\nAtribuição de presentes aos padrinhos:").setFontSize(16));
        
        String padrinhos = "";
        
        float [] pointColumnWidths2 = {200F, 150F, 150F};
        Table table = new Table(pointColumnWidths2);
        table.addCell(new Cell().add("Presentes"));
        table.addCell(new Cell().add("Padrinhos"));
        table.addCell(new Cell().add("Atribuição"));
        
        
        //padrinhos já selecionados
        for(int i=0;i<ctx.presente().size();i++){
            this.presentes_nao_atribuidos.add(ctx.presente(i).descricao.getText());
            if (!ctx.presente(i).nome.isEmpty()){
                for (int t = 0; t<ctx.presente(i).nome.size(); t++){
                    //removendo o padrinho da minha lista
                    this.padrinhos_nao_atribuidos.remove(ctx.presente(i).NOME(t).getText());
                    padrinhos += ctx.presente(i).NOME(t).getText()+", ";
                }
                
                table.addCell(new Cell().add(ctx.presente(i).descricao.getText()));
                table.addCell(new Cell().add(padrinhos));
                table.addCell(new Cell().add("Pelos noivos"));
                
//                doc.add(new Paragraph("O presente "+ctx.presente(i).descricao.getText()+
//                        " foi atribuido pelos noivos aos padrinhos "+padrinhos));
                padrinhos = "";
                this.presentes_nao_atribuidos.remove(ctx.presente(i).descricao.getText());
            }
        }
        
        int tama = this.padrinhos_nao_atribuidos.size();
        boolean aux = true;
        
        for(int j = 0;j<this.presentes_nao_atribuidos.size();j++){
            if(j>=tama){
                aux = false;
                table.setKeepTogether(true);
                doc.add(table);
                doc.add(new Paragraph("Não há mais padrinhos para serem atribuidos aos presentes.")); 
                break;
            }
            
            table.addCell(new Cell().add(this.presentes_nao_atribuidos.get(j).toString()));
            table.addCell(new Cell().add(this.padrinhos_nao_atribuidos.get(j).toString()));
            table.addCell(new Cell().add("Geração automático."));
            
//            doc.add(new Paragraph("O presente "+this.presentes_nao_atribuidos.get(j).toString()+
//                    " foi atribuido aos padrinhos "+this.padrinhos_nao_atribuidos.get(j).toString()));
        }
        
        if(aux){
            table.setKeepTogether(true);
            doc.add(table.setBorder(Border.NO_BORDER));
        }
        
        return super.visitListaPresentes(ctx);
    }
    
    @Override
    public Void visitListaConvidados(casamentoParser.ListaConvidadosContext ctx){
        doc.add(new Paragraph("Convidados:").setFontSize(16));  
        float [] pointColumnWidths = {200F, 150F, 150F};
        Table table = new Table(pointColumnWidths);
        for(int i = 0; i < ctx.STRING().size(); i++ ){
            table.addCell(new Cell().add(((i+1)+" :"+ctx.STRING(i).getText())));
//            doc.add(new Paragraph("Convidado "+(i+1)+": "+ctx.STRING(i).getText()).setFontSize(12));
        }
        table.setKeepTogether(true);
        doc.add(table);
        return super.visitListaConvidados(ctx);        
    }
    
    @Override
    public Void visitListaServicos(casamentoParser.ListaServicosContext ctx){
        doc.add(new Paragraph("Serviços").setFontSize(16));
        doc.add(new Paragraph("Fotografo").setFontSize(14).setBold());
        
        //-----------------------FOTOGRAFO
        
        float [] pointColumnWidths = {200F, 150F, 150F};
        Table tableFotografo = new Table(pointColumnWidths);
        
        tableFotografo.addCell(new Cell().add("Nome"));
        tableFotografo.addCell(new Cell().add("Contato"));
        tableFotografo.addCell(new Cell().add("Preço"));
        
        for(int i = 0; i < ctx.fotografo().size(); i++ ){
//            doc.add(new Paragraph(ctx.fotografo(i).getText()));
            tableFotografo.addCell(new Cell().add(ctx.fotografo(i).nome.getText()));
            tableFotografo.addCell(new Cell().add(ctx.fotografo(i).contato.getText()));
            tableFotografo.addCell(new Cell().add("R$: "+ctx.fotografo(i).preco.getText()));
            custoFotografo += Float.valueOf(ctx.fotografo(i).preco.getText());
//            doc.add(new Paragraph("Nome: "+ctx.fotografo(i).nome.getText()));
//            doc.add(new Paragraph("Contato: "+ctx.fotografo(i).contato.getText()));
//            doc.add(new Paragraph("Preço: "+ctx.fotografo(i).preco.getText()));
        }
        
        tableFotografo.setKeepTogether(true);
        doc.add(tableFotografo);
        
        //-----------------------BUFFET
        
        Table tableBuffet = new Table(pointColumnWidths);
        
        tableFotografo.addCell(new Cell().add("Nome"));
        tableFotografo.addCell(new Cell().add("Contato"));
        tableFotografo.addCell(new Cell().add("Preço"));
        
        doc.add(new Paragraph("Buffet").setFontSize(14).setBold());
        for(int i = 0; i < ctx.buffet().size(); i++ ){
            //Nome
            tableBuffet.addCell(new Cell().add(ctx.buffet(i).STRING().getText()));
            tableBuffet.addCell(new Cell().add(ctx.buffet(i).NUM_INT().getText()));
            tableBuffet.addCell(new Cell().add("R$: "+ctx.buffet(i).NUM_REAL().getText()));
            custoBuffet += Float.valueOf(ctx.buffet(i).NUM_REAL().getText());
            
//            doc.add(new Paragraph("Empresa: "+ctx.buffet(i).STRING().getText()));
//            //Contato
//            doc.add(new Paragraph("Contato: "+ctx.buffet(i).NUM_INT().getText()));
//            //Preco
//            doc.add(new Paragraph("Preco: "+ctx.buffet(i).NUM_REAL().getText()));
        }
        tableBuffet.setKeepTogether(true);
        doc.add(tableBuffet);
        
        //-----------------------CERIMONIAL
        
        Table tableCerimonial = new Table(pointColumnWidths);
        
        tableCerimonial.addCell(new Cell().add("Nome"));
        tableCerimonial.addCell(new Cell().add("Contato"));
        tableCerimonial.addCell(new Cell().add("Preço"));
        
        doc.add(new Paragraph("Cerimonial").setFontSize(14).setBold());
        for(int i = 0; i < ctx.cerimonial().size(); i++ ){
//            //Nome
//            doc.add(new Paragraph("Nome: "+ctx.cerimonial(i).STRING().getText()));
//            //Contato
//            doc.add(new Paragraph("Contato: "+ctx.cerimonial(i).NUM_INT().getText()));
//            //Preco
//            doc.add(new Paragraph("Preco: "+ctx.cerimonial(i).NUM_REAL().getText()));
            
            tableCerimonial.addCell(new Cell().add(ctx.cerimonial(i).STRING().getText()));
            tableCerimonial.addCell(new Cell().add(ctx.cerimonial(i).NUM_INT().getText()));
            tableCerimonial.addCell(new Cell().add("R$: "+ctx.cerimonial(i).NUM_REAL().getText()));   
            custoCerimonial += Float.valueOf(ctx.cerimonial(i).NUM_REAL().getText());
        }
        
        tableCerimonial.setKeepTogether(true);
        doc.add(tableCerimonial);
        
        //-----------------------LOCAL
        
        float [] pointColumnWidths2 = {120F,120F,120F,120F};
        
        Table tableLocal = new Table(pointColumnWidths2);
        doc.add(new Paragraph("Local").setFontSize(14).setBold());
        
//        tableLocal.addCell(new Cell().add("Empresa"));
//        tableLocal.addCell(new Cell().add("Endereço"));
//        tableLocal.addCell(new Cell().add("Contato"));
        tableLocal.addCell(new Cell().add("Horario de início"));
        tableLocal.addCell(new Cell().add("Horario de término"));
        tableLocal.addCell(new Cell().add("Preço"));
        tableLocal.addCell(new Cell().add("Capacidade"));
        
        for(int i = 0; i < ctx.local().size(); i++ ){
            doc.add(new Paragraph("Empresa: "+ctx.local(i).nome.getText()));
            doc.add(new Paragraph("Endereço: "+ctx.local(i).endereco.getText()));
            doc.add(new Paragraph("Contato: "+ctx.local(i).contato.getText()));
//            doc.add(new Paragraph("Horario de início: "+ctx.local(i).horario_inic.getText()));
//            doc.add(new Paragraph("Horario de término: "+ctx.local(i).horario_fim.getText()));
//            //preco
//            doc.add(new Paragraph("Preço: "+ctx.local(i).NUM_REAL().getText()));
//            doc.add(new Paragraph("Capacidade: "+ctx.local(i).capacidade.getText()));
            
//            tableLocal.addCell(new Cell().add(ctx.local(i).nome.getText()));
//            tableLocal.addCell(new Cell().add(ctx.local(i).endereco.getText()));
//            tableLocal.addCell(new Cell().add(ctx.local(i).contato.getText()));
            tableLocal.addCell(new Cell().add(ctx.local(i).horario_inic.getText()));
            tableLocal.addCell(new Cell().add(ctx.local(i).horario_fim.getText()));
            tableLocal.addCell(new Cell().add("R$: "+ctx.local(i).NUM_REAL().getText()));
            custoLocal+= Float.valueOf(ctx.local(i).NUM_REAL().getText());
            tableLocal.addCell(new Cell().add(ctx.local(i).capacidade.getText()));
            
            
        }
        
        tableLocal.setKeepTogether(true);
        doc.add(tableLocal);

        //-----------------------DECORAÇÃO

        float [] pointColumnWidths3 = {150F, 200F, 120F};
        
        Table tableDecoracao = new Table(pointColumnWidths3);
        doc.add(new Paragraph("Decoração").setFontSize(14).setBold());

        for(int i = 0; i < ctx.decoracao().size(); i++ ){
            //Nome
            doc.add(new Paragraph("Empresa: "+ctx.decoracao(i).nome.getText()));
            //Contato
            doc.add(new Paragraph("Contato: "+ctx.decoracao(i).NUM_INT().getText()));
            //Preco
            doc.add(new Paragraph("Preço: "+ctx.decoracao(i).NUM_REAL().getText()));
            custoDecoracao += Float.valueOf(ctx.decoracao(i).NUM_REAL().getText());
            //itens decoracao
            doc.add(new Paragraph("Itens de decoração:"));
            for(int j=0;j<ctx.decoracao(i).item.size();j++){
//                doc.add(new Paragraph("Item de decoração "+(j+1)+": "+ctx.decoracao(i).item.get(j).getText()));
                tableDecoracao.addCell(new Cell().add((j+1)+" :"+ctx.decoracao(i).item.get(j).getText()));
            }
        }
        
        tableDecoracao.setKeepTogether(true);
        doc.add(tableDecoracao);
        
        //-----------------------CONVITES
        
        doc.add(new Paragraph("Convites").setFontSize(14).setBold());
        
        for(int i = 0; i < ctx.convites().size(); i++ ){
            //nome
            doc.add(new Paragraph("Empresa: "+ctx.convites(i).STRING().getText()));
            doc.add(new Paragraph("Contato: "+ctx.convites(i).contato.getText()));
            doc.add(new Paragraph("Quantidade de convites: "+ctx.convites(i).quant_convites.getText()));
            doc.add(new Paragraph("Preço: "+ctx.convites(i).NUM_REAL().getText()));
            custoConvites += Float.valueOf(ctx.convites(i).NUM_REAL().getText());
            
        }   
        
        doc.add(new Paragraph("Lua de mel").setFontSize(14).setBold());
        
        float [] pointColumnWidths4 = {120F, 120F, 120F, 120F};
        
        Table tableLuaDeMel = new Table(pointColumnWidths4);
        
//        tableLuaDeMel.addCell(new Cell().add("Localização"));
//        tableLuaDeMel.addCell(new Cell().add("Hospedagem"));
//        tableLuaDeMel.addCell(new Cell().add("Contato hospedagem"));
        tableLuaDeMel.addCell(new Cell().add("Preco"));
        tableLuaDeMel.addCell(new Cell().add("Data ida"));
        tableLuaDeMel.addCell(new Cell().add("Data volta"));
        tableLuaDeMel.addCell(new Cell().add("Valor passagem"));
        
        
        for(int i = 0; i < ctx.lua_de_mel().size(); i++ ){
            
//            tableLuaDeMel.addCell(new Cell().add(ctx.lua_de_mel(i).loc.getText()));
//            tableLuaDeMel.addCell(new Cell().add(ctx.lua_de_mel(i).hospedagem.getText()));
//            tableLuaDeMel.addCell(new Cell().add(ctx.lua_de_mel(i).NUM_INT().getText()));
            tableLuaDeMel.addCell(new Cell().add("R$: "+ctx.lua_de_mel(i).preco.getText()));
            
            
            tableLuaDeMel.addCell(new Cell().add(ctx.lua_de_mel(i).data_ida.getText()));
            tableLuaDeMel.addCell(new Cell().add(ctx.lua_de_mel(i).data_volta.getText()));
            tableLuaDeMel.addCell(new Cell().add("R$: "+ctx.lua_de_mel(i).passagem.getText()));
            custoLuaDeMel += Float.parseFloat(ctx.lua_de_mel(i).passagem.getText());
            
            doc.add(new Paragraph("Localização:"+ctx.lua_de_mel(i).loc.getText()));
            doc.add(new Paragraph("Hospedagem: "+ctx.lua_de_mel(i).hospedagem.getText()));
            doc.add(new Paragraph("Contato hospedagem: "+ctx.lua_de_mel(i).NUM_INT().getText()));
//            doc.add(new Paragraph("Preco: R$"+ctx.lua_de_mel(i).preco.getText()));
//            doc.add(new Paragraph("Data ida: "+ctx.lua_de_mel(i).data_ida.getText()));
//            doc.add(new Paragraph("Data volta: "+ctx.lua_de_mel(i).data_volta.getText()));
//            doc.add(new Paragraph("Valor passagem: R$"+ctx.lua_de_mel(i).passagem.getText()));
        }
        
        tableLuaDeMel.setKeepTogether(true);
        doc.add(tableLuaDeMel);
        
        
        doc.add(new Paragraph("\nOrçamento").setFontSize(16));
//        doc.add(new Paragraph(String.valueOf(custoTotal)));

        float [] pointColumnWidthsCustos = {200F, 200F};
        
        Table tableCustos = new Table(pointColumnWidthsCustos);
        
        tableCustos.addCell(new Cell().add("Gasto"));
        tableCustos.addCell(new Cell().add("Valor"));
        
        tableCustos.addCell(new Cell().add("Fotografo")); 
        tableCustos.addCell(new Cell().add("R$"+custoFotografo)); 
        tableCustos.addCell(new Cell().add("Buffet"));
        tableCustos.addCell(new Cell().add("R$"+custoBuffet)); 
        tableCustos.addCell(new Cell().add("Cerimonial"));
        tableCustos.addCell(new Cell().add("R$"+custoCerimonial)); 
        tableCustos.addCell(new Cell().add("Local"));
        tableCustos.addCell(new Cell().add("R$"+custoLocal)); 
        tableCustos.addCell(new Cell().add("Decoração"));
        tableCustos.addCell(new Cell().add("R$"+custoDecoracao)); 
        tableCustos.addCell(new Cell().add("Convites"));
        tableCustos.addCell(new Cell().add("R$"+custoConvites)); 
        tableCustos.addCell(new Cell().add("Lua de Mel"));
        tableCustos.addCell(new Cell().add("R$"+custoLuaDeMel)); 
        tableCustos.addCell(new Cell().add("Total"));
        tableCustos.addCell(new Cell().add("R$"+(custoConvites+custoDecoracao+custoLocal+custoCerimonial+custoLuaDeMel+custoFotografo+custoBuffet)).setBold());
        tableCustos.setKeepTogether(true);
        doc.add(tableCustos);
        
        return super.visitListaServicos(ctx);
    }
    
}
