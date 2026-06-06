package br.com.fiap.space.domain.valueobjects;

public final class Coordenada {

    private final int eixoX;
    private final int eixoY;

    public Coordenada(int eixoX, int eixoY) {
        if (eixoX < 0 || eixoY < 0) {
            throw new IllegalArgumentException(
                    "Coordenadas não podem ser negativas. Recebido: (" + eixoX + ", " + eixoY + ").");
        }
        this.eixoX = eixoX;
        this.eixoY = eixoY;
    }

    public int getEixoX() {
        return eixoX;
    }

    public int getEixoY() {
        return eixoY;
    }
}
