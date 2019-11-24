package br.ufscar.dc.cc2.generatorpdf;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TabelaDeSimbolos {
    private final String escopo;
    private final List<EntradaTabelaDeSimbolos> simbolos;
    private LinkedList<TabelaDeSimbolos> pilha;
    
    public TabelaDeSimbolos(String escopo) {
        simbolos = new ArrayList<EntradaTabelaDeSimbolos>();
        this.escopo = escopo;
    }
    
    public void adicionarSimbolo(String sigla, String casal) {
        simbolos.add(new EntradaTabelaDeSimbolos(sigla,casal));
    }
    
    public void adicionarSimbolo(String presente, String site, String sigla){
        simbolos.add(new EntradaTabelaDeSimbolos(presente,site,sigla));
    }
    
    public String getPresente (String sigla){
        for(EntradaTabelaDeSimbolos etds:simbolos) {
            if(etds.getSigla().equals(sigla)) {
                return etds.getPresente();
            }
        }
        return "null";
    }
    
     public String getSite (String sigla){
        for(EntradaTabelaDeSimbolos etds:simbolos) {
            if(etds.getSigla().equals(sigla)) {
                return etds.getSite();
            }
        }
        return "null";
    }
    
    public String getCasal (String sigla){
        for(EntradaTabelaDeSimbolos etds:simbolos) {
            if(etds.getSigla().equals(sigla)) {
                return etds.getCasal();
            }
        }
        return "null";
    }
    
    public boolean existeSimbolo(String sigla) {
        return simbolos.stream().anyMatch((etds) -> (etds.getSigla().equals(sigla)));
    }
    
    @Override
    public String toString() {
        String ret = "Escopo: "+escopo;
        return simbolos.stream().map((etds) -> "\n   "+etds).reduce(ret, String::concat);
    }
}
