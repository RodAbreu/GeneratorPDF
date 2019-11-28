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
        

    public CasamentoPDFGenerator(Document doc) {
        this.doc = doc;
        this.presentes_nao_atribuidos = new ArrayList();
        this.padrinhos_nao_atribuidos = new ArrayList();
        p.addTabStops(new TabStop(56f,TabAlignment.LEFT));
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
        //aprensentando todos os presentes
        for(int i=0; i<ctx.presente().size(); i++ ){
            this.p.add(new Tab());
//            p.add("Presente "+(i+1));
            doc.add(p);
            p = new Paragraph();
            doc.add(new Paragraph((i+1)+": "+ctx.presente(i).descricao.getText()));
            doc.add(new Paragraph("URL de sugestão: "+ctx.presente(i).url.getText()).setFontSize(12));      
            for(int j=0;j<ctx.presente(i).NOME().size();j++){
                doc.add(new Paragraph("Sigla dos padrinhos atribuidos: "+ctx.presente(i).NOME().get(j).getText()).setFontSize(12));   
            }
        }
       
        doc.add(new Paragraph("Atribuição de presentes aos padrinhos:").setFontSize(16));
        
        String padrinhos = "";
        
        float [] pointColumnWidths = {200F, 150F, 150F};
        Table table = new Table(pointColumnWidths);
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
                
                doc.add(new Paragraph("O presente "+ctx.presente(i).descricao.getText()+
                        " foi atribuido pelos noivos aos padrinhos "+padrinhos));
                padrinhos = "";
                this.presentes_nao_atribuidos.remove(ctx.presente(i).descricao.getText());
            }
        }
        
        int tama = this.padrinhos_nao_atribuidos.size();
        boolean aux = true;
        for(int j = 0;j<this.presentes_nao_atribuidos.size();j++){
            if(j>=tama){
                aux = false;
                 doc.add(table);
                 doc.add(new Paragraph("Não há mais padrinhos para serem atribuidos")); 
                 break;
            }
            
            table.addCell(new Cell().add(this.presentes_nao_atribuidos.get(j).toString()));
            table.addCell(new Cell().add(this.padrinhos_nao_atribuidos.get(j).toString()));
            table.addCell(new Cell().add("Geração automático."));
            
            doc.add(new Paragraph("O presente "+this.presentes_nao_atribuidos.get(j).toString()+
                    " foi atribuido aos padrinhos "+this.padrinhos_nao_atribuidos.get(j).toString()));
        }
        
        if(aux){
            doc.add(table);
        }
        
        return super.visitListaPresentes(ctx);
    }
    
    @Override
    public Void visitListaConvidados(casamentoParser.ListaConvidadosContext ctx){
        doc.add(new Paragraph("Convidados:").setFontSize(16));  
        for(int i = 0; i < ctx.STRING().size(); i++ ){
            doc.add(new Paragraph("Convidado "+(i+1)+": "+ctx.STRING(i).getText()).setFontSize(12));
        }
        return super.visitListaConvidados(ctx);        
    }
    
    @Override
    public Void visitListaServicos(casamentoParser.ListaServicosContext ctx){
        doc.add(new Paragraph("Serviços").setFontSize(16));
        
        doc.add(new Paragraph("Fotografo").setFontSize(14));
        for(int i = 0; i < ctx.fotografo().size(); i++ ){
//            doc.add(new Paragraph(ctx.fotografo(i).getText()));
            doc.add(new Paragraph("Nome: "+ctx.fotografo(i).nome.getText()));
            doc.add(new Paragraph("Contato: "+ctx.fotografo(i).contato.getText()));
            doc.add(new Paragraph("Preço: "+ctx.fotografo(i).preco.getText()));
        }
        doc.add(new Paragraph("Buffet").setFontSize(14));
        for(int i = 0; i < ctx.buffet().size(); i++ ){
            //Nome
            doc.add(new Paragraph("Empresa: "+ctx.buffet(i).STRING().getText()));
            //Contato
            doc.add(new Paragraph("Contato: "+ctx.buffet(i).NUM_INT().getText()));
            //Preco
            doc.add(new Paragraph("Preco: "+ctx.buffet(i).NUM_REAL().getText()));
        }
        
        doc.add(new Paragraph("Cerimonial").setFontSize(14));
        for(int i = 0; i < ctx.cerimonial().size(); i++ ){
            //Nome
            doc.add(new Paragraph("Nome: "+ctx.cerimonial(i).STRING().getText()));
            //Contato
            doc.add(new Paragraph("Contato: "+ctx.cerimonial(i).NUM_INT().getText()));
            //Preco
            doc.add(new Paragraph("Preco: "+ctx.cerimonial(i).NUM_REAL().getText()));
        }
        
        doc.add(new Paragraph("Local").setFontSize(14));
        for(int i = 0; i < ctx.local().size(); i++ ){
            doc.add(new Paragraph("Empresa: "+ctx.local(i).nome.getText()));
            doc.add(new Paragraph("Endereço: "+ctx.local(i).endereco.getText()));
            doc.add(new Paragraph("Contato: "+ctx.local(i).contato.getText()));
            doc.add(new Paragraph("Horario de início: "+ctx.local(i).horario_inic.getText()));
            doc.add(new Paragraph("Horario de término: "+ctx.local(i).horario_fim.getText()));
            //preco
            doc.add(new Paragraph("Preço: "+ctx.local(i).NUM_REAL().getText()));
            doc.add(new Paragraph("Capacidade: "+ctx.local(i).capacidade.getText()));
        }   
        
        doc.add(new Paragraph("Decoração").setFontSize(14));
        for(int i = 0; i < ctx.decoracao().size(); i++ ){
            //Nome
            doc.add(new Paragraph("Empresa: "+ctx.decoracao(i).nome.getText()));
            //Contato
            doc.add(new Paragraph("Contato: "+ctx.decoracao(i).NUM_INT().getText()));
            //Preco
            doc.add(new Paragraph("Preço: "+ctx.decoracao(i).NUM_REAL().getText()));
            //itens decoracao
            for(int j=0;j<ctx.decoracao(i).item.size();j++){
                doc.add(new Paragraph("Item de decoração "+(j+1)+": "+ctx.decoracao(i).item.get(j).getText()));
            }
        }      
        
        doc.add(new Paragraph("Convites").setFontSize(14));
        for(int i = 0; i < ctx.convites().size(); i++ ){
            //nome
            doc.add(new Paragraph("Empresa: "+ctx.convites(i).STRING().getText()));
            doc.add(new Paragraph("Contato: "+ctx.convites(i).contato.getText()));
            doc.add(new Paragraph("Quantidade de convites: "+ctx.convites(i).quant_convites.getText()));
            doc.add(new Paragraph("Preço: "+ctx.convites(i).NUM_REAL().getText()));
            
        }   
        doc.add(new Paragraph("Lua de mel").setFontSize(14));
        for(int i = 0; i < ctx.lua_de_mel().size(); i++ ){
            doc.add(new Paragraph("Localização:"+ctx.lua_de_mel(i).loc.getText()));
            doc.add(new Paragraph("Hospedagem: "+ctx.lua_de_mel(i).hospedagem.getText()));
            doc.add(new Paragraph("Contato hospedagem: "+ctx.lua_de_mel(i).NUM_INT().getText()));
            doc.add(new Paragraph("Preco: R$"+ctx.lua_de_mel(i).preco.getText()));
            doc.add(new Paragraph("Data ida: "+ctx.lua_de_mel(i).data_ida.getText()));
            doc.add(new Paragraph("Data volta: "+ctx.lua_de_mel(i).data_volta.getText()));
            doc.add(new Paragraph("Valor passagem: R$"+ctx.lua_de_mel(i).passagem.getText()));
        }   
        
        return super.visitListaServicos(ctx);
    }
    
}
