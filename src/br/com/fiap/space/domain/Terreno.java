package br.com.fiap.space.domain;

public enum Terreno {

    PLANICIE("Planicie", 1.0),
    CRATERA("Cratera", 4.0),
    SOLO_ROCHOSO("Solo Rochoso", 2.5);

    private final String tipoSolo;
    private final double multiplicadorConsumo;

    Terreno(String tipoSolo, double multiplicadorConsumo) {
        if (multiplicadorConsumo <= 0) {
            throw new IllegalArgumentException("O multiplicador de consumo deve ser estritamente positivo (> 0).");
        }
        this.tipoSolo = tipoSolo;
        this.multiplicadorConsumo = multiplicadorConsumo;
    }

    public String getTipoSolo() {
        return tipoSolo;
    }

    public double getMultiplicadorConsumo() {
        return multiplicadorConsumo;
    }

    @Override
    public String toString() {
        return tipoSolo + " (x" + multiplicadorConsumo + ")";
    }
}
