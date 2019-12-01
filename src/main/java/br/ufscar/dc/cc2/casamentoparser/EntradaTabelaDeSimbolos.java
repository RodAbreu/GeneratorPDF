package br.ufscar.dc.cc2.generatorpdf;

public class EntradaTabelaDeSimbolos {
    private String sigla, casal;
    private String presente, site;
    private String[] siglas;
    
    public EntradaTabelaDeSimbolos(String sigla, String casal) {
        this.sigla = sigla;
        this.casal = casal;
    }
    
    public EntradaTabelaDeSimbolos(String presente, String site, String sigla){
        this.presente = presente;
        this.site = site;
        this.sigla = sigla;
    }
    
    public String getSigla() {
        return sigla;
    }
    
    public String getCasal() {
        return casal;
    }
    
    public String getPresente(){
        return presente;
    }
    
    public String getSite(){
        return site;
    }
    
    @Override
    public String toString() {
        return sigla+"("+casal+")";
    }
}
