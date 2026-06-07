package br.com.fiap.space.domain.enums;

public enum Recurso {

    GELO("Gelo", 10.0),
    REGOLITO("Regolito", 25.0),
    TITANIO("Titanio", 60.0);

    private String nome;
    private double pesoPorUnidade;

    Recurso(String nome, double pesoPorUnidade) {
        this.nome = nome;
        this.pesoPorUnidade = pesoPorUnidade;
    }

    public String getNome() {
        return nome;
    }

    public double getPesoPorUnidade() {
        return pesoPorUnidade;
    }

    @Override
    public String toString() {
        return nome + " (" + pesoPorUnidade + " kg/un)";
    }
}
