package br.com.fiap.space.domain;

import br.com.fiap.space.domain.enums.Recurso;
import br.com.fiap.space.domain.interfaces.Recarregavel;
import br.com.fiap.space.domain.valueobjects.CompartimentoCarga;
import br.com.fiap.space.domain.valueobjects.NivelEnergia;

public class SondaMineradora extends Sonda implements Recarregavel {

    private CompartimentoCarga carga;

    private static final double CUSTO_MINERACAO = 5.0;

    public SondaMineradora(String idSonda, double capacidadeMaxima) {
        super(idSonda, new NivelEnergia(100.0, 100.0));
        if (capacidadeMaxima <= 0) {
            throw new IllegalArgumentException(
                    "A capacidade máxima do compartimento deve ser positiva.");
        }
        this.carga = new CompartimentoCarga(0, capacidadeMaxima, null);
    }

    public CompartimentoCarga getCarga() {
        return carga;
    }

    public double consultarCompartimentoOcupado() {
        return carga.getVolumeOcupado();
    }

    public void descarregarCompartimento() {
        this.carga = carga.descarregarVolume();
    }

    @Override
    protected void realizarAcaoLocal() {
        double novaCapacidade = bateria.getCapacidadeAtual() - CUSTO_MINERACAO;
        this.bateria = new NivelEnergia(novaCapacidade, bateria.getCapacidadeMaxima());
        this.carga = carga.adicionarVolume(Recurso.REGOLITO, 1);
    }

    @Override
    public void conectarBase() {
        this.bateria = new NivelEnergia(bateria.getCapacidadeMaxima(), bateria.getCapacidadeMaxima());
    }

    @Override
    public String getTipo() {
        return "MINERADORA";
    }
}
