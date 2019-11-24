package br.ufscar.dc.cc2.generatorpdf;

import java.util.LinkedList;
import java.util.List;

public class PilhaDeTabelas {

    private final LinkedList<TabelaDeSimbolos> pilha;
    private List<EntradaTabelaDeSimbolos> simbolos;

    public PilhaDeTabelas() {
        pilha = new LinkedList<>();
    }

    public void empilhar(TabelaDeSimbolos ts) {
        pilha.push(ts);
    }

    public TabelaDeSimbolos topo() {
        return pilha.peek();
    }

    public boolean existeSimbolo(String sigla) {
        return pilha.stream().anyMatch((ts) -> (ts.existeSimbolo(sigla)));
    }

    public void desempilhar() {
        TabelaDeSimbolos ret = pilha.pop();
        Saida.println(ret.toString());
    }

    public List getTodasTabelas() {
        return pilha;
    }
}
