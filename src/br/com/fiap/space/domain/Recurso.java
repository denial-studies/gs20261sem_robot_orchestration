package br.com.fiap.space.domain;

public enum Recurso {

    GELO("Gelo", 10.0),
    REGOLITO("Regolito", 25.0),
    TITANIO("Titanio", 60.0);

    private final String nome;
    private final double pesoPorUnidade;

    Recurso(String nome, double pesoPorUnidade) {
        if (pesoPorUnidade <= 0) {
            throw new IllegalArgumentException("O peso por unidade deve ser estritamente positivo (> 0).");
        }
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
