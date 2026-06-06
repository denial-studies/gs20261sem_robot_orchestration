package br.com.fiap.space.domain.valueobjects;

import br.com.fiap.space.domain.Recurso;
import br.com.fiap.space.domain.exceptions.CargaExcedidaException;

public final class CompartimentoCarga {

    private final double volumeOcupado;
    private final double volumeMaximo;
    private final Recurso tipoCarga;

    public CompartimentoCarga(double volumeMaximo) {
        this(0.0, volumeMaximo, null);
    }

    private CompartimentoCarga(double volumeOcupado, double volumeMaximo, Recurso tipoCarga) {
        if (volumeMaximo <= 0) {
            throw new IllegalArgumentException(
                    "O volume máximo do compartimento deve ser positivo. Recebido: " + volumeMaximo);
        }
        if (volumeOcupado < 0) {
            throw new IllegalArgumentException(
                    "O volume ocupado não pode ser negativo. Recebido: " + volumeOcupado);
        }
        if (volumeOcupado > volumeMaximo) {
            throw new IllegalArgumentException(
                    "O volume ocupado (" + volumeOcupado
                            + ") não pode exceder o volume máximo (" + volumeMaximo + ").");
        }
        this.volumeOcupado = volumeOcupado;
        this.volumeMaximo = volumeMaximo;
        this.tipoCarga = tipoCarga;
    }

    public double getVolumeOcupado() {
        return volumeOcupado;
    }

    public double getVolumeMaximo() {
        return volumeMaximo;
    }

    public Recurso getTipoCarga() {
        return tipoCarga;
    }

    public CompartimentoCarga adicionarVolume(Recurso recurso, int quantidade) {
        if (recurso == null) {
            throw new IllegalArgumentException("O recurso não pode ser nulo.");
        }
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser positiva.");
        }
        if (this.tipoCarga != null && this.tipoCarga != recurso) {
            throw new IllegalArgumentException(
                    "Compartimento já contém " + this.tipoCarga.getNome()
                            + ". Não é possível misturar com " + recurso.getNome() + ".");
        }

        double volumeAdicionado = recurso.getPesoPorUnidade() * quantidade;
        double novoVolume = this.volumeOcupado + volumeAdicionado;

        if (novoVolume > this.volumeMaximo) {
            throw new CargaExcedidaException(
                    "Carga excedida! Tentativa de adicionar " + volumeAdicionado
                            + " ao compartimento (ocupado: " + this.volumeOcupado
                            + ", máximo: " + this.volumeMaximo + ").");
        }

        return new CompartimentoCarga(novoVolume, this.volumeMaximo, recurso);
    }

    public CompartimentoCarga descarregarCarga() {
        return new CompartimentoCarga(this.volumeMaximo);
    }
}
