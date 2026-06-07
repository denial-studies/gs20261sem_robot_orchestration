package br.com.fiap.space.domain.enums;

public enum Terreno {

    PLANICIE("Planicie", 1.0),
    CRATERA("Cratera", 4.0),
    SOLO_ROCHOSO("Solo Rochoso", 2.5);

    private String tipoSolo;
    private double multiplicadorConsumo;

    Terreno(String tipoSolo, double multiplicadorConsumo) {
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
