/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.cc2.generatorpdf;

/**
 *
 * @author Gabrieli Santos
 */
public class EntradaTabelaPresentes {
    private final String sigla;
    private final String presente;
    private final String site;
    
    public EntradaTabelaPresentes(String presente, String site, String sigla){
        this.presente = presente;
        this.site = site;
        this.sigla = sigla;
    }
    
    public String getSigla() {
        return sigla;
    }
    
    public String getPresente(){
        return presente;
    }
    
    public String getSite(){
        return site;
    }   
}
