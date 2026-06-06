package br.com.fiap.space.domain.valueobjects;

import br.com.fiap.space.domain.exceptions.BateriaCriticaException;

public final class NivelEnergia {

    private final double capacidadeAtual;
    private final double capacidadeMaxima;

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

    public NivelEnergia consumir(double quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade de consumo deve ser positiva.");
        }
        if (quantidade > this.capacidadeAtual) {
            throw new BateriaCriticaException(
                    "Bateria crítica! Tentativa de consumir " + quantidade
                            + " unidades, mas apenas " + this.capacidadeAtual + " disponíveis.");
        }
        return new NivelEnergia(this.capacidadeAtual - quantidade, this.capacidadeMaxima);
    }

    public NivelEnergia recarregar() {
        return new NivelEnergia(this.capacidadeMaxima, this.capacidadeMaxima);
    }

    public boolean isSuficiente(double consumoNecessario) {
        return this.capacidadeAtual >= consumoNecessario;
    }
}
