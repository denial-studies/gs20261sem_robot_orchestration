package br.com.fiap.space.domain.valueobjects;

public class NivelEnergia {

    private double capacidadeAtual;
    private double capacidadeMaxima;

    public NivelEnergia(double capacidadeAtual, double capacidadeMaxima) {
        if (capacidadeMaxima <= 0) {
            throw new IllegalArgumentException(
                    "A capacidade máxima de energia deve ser positiva. Recebido: " + capacidadeMaxima);
        }
        if (capacidadeAtual < 0) {
            throw new IllegalArgumentException(
                    "A capacidade atual de energia não pode ser negativa. Recebido: " + capacidadeAtual);
        }
        if (capacidadeAtual > capacidadeMaxima) {
            throw new IllegalArgumentException(
                    "A capacidade atual (" + capacidadeAtual
                            + ") não pode exceder a capacidade máxima (" + capacidadeMaxima + ").");
        }
        this.capacidadeAtual = capacidadeAtual;
        this.capacidadeMaxima = capacidadeMaxima;
    }

    public double getCapacidadeAtual() {
        return capacidadeAtual;
    }

    public double getCapacidadeMaxima() {
        return capacidadeMaxima;
    }
}
