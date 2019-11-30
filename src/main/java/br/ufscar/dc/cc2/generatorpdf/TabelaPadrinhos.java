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
public class TabelaPadrinhos {
    private final String escopo;
    private final List<EntradaTabelaPadrinhos> simbolos;
    
    public TabelaPadrinhos(String escopo) {
        simbolos = new ArrayList<>();
        this.escopo = escopo;
    }
    
    public void adicionarSimbolo(String sigla, String casal) {
        simbolos.add(new EntradaTabelaPadrinhos(sigla,casal));
    }
    
    public String getCasal (String sigla){
        for(EntradaTabelaPadrinhos etds:simbolos) {
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
