/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.cc2.generatorpdf;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gabrieli Santos
 */
public class TabelaPresentes {
    private final String escopo;
    private final List<EntradaTabelaPresentes> simbolos;
//    private LinkedList<TabelaDeSimbolos> pilha;
    
    public TabelaPresentes(String escopo) {
        simbolos = new ArrayList<>();
        this.escopo = escopo;
    }
    
    public void adicionarSimbolo(String presente, String site, String sigla){
        simbolos.add(new EntradaTabelaPresentes(presente,site,sigla));
    }
    
    public String getPresente (String sigla){
        for(EntradaTabelaPresentes etds:simbolos) {
            if(etds.getSigla().equals(sigla)) {
                return etds.getPresente();
            }
        }
        return "null";
    }
    
     public String getSite (String sigla){
        for (EntradaTabelaPresentes etds : simbolos) {
            if(etds.getSigla().equals(sigla)) {
                return etds.getSite();
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
