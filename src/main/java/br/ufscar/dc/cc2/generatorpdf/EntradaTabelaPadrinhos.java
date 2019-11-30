package br.ufscar.dc.cc2.generatorpdf;

/**
 *
 * @author Gabrieli Santos
 */
public class EntradaTabelaPadrinhos {
    private final String sigla;
    private final String casal;
    
    public EntradaTabelaPadrinhos(String sigla, String casal) {
        this.sigla = sigla;
        this.casal = casal;
    }
    
    public String getSigla() {
        return sigla;
    }
    
    public String getCasal() {
        return casal;
    }
    
    @Override
    public String toString() {
        return sigla+"("+casal+")";
    }
}
