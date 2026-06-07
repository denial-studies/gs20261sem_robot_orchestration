package br.com.fiap.space.domain;

import br.com.fiap.space.domain.interfaces.Recarregavel;
import br.com.fiap.space.domain.valueobjects.NivelEnergia;

public class SondaExploradora extends Sonda implements Recarregavel {

    private double alcanceSensor;

    private static final double CUSTO_VARREDURA = 8.0;

    public SondaExploradora(String idSonda, double alcanceSensor) {
        super(idSonda, new NivelEnergia(100.0, 100.0));
        if (alcanceSensor <= 0) {
            throw new IllegalArgumentException(
                    "O alcance do sensor deve ser estritamente positivo (> 0). Recebido: " + alcanceSensor);
        }
        this.alcanceSensor = alcanceSensor;
    }

    public double getAlcanceSensor() {
        return alcanceSensor;
    }

    public void ajustarSensor(double novoAlcance) {
        if (novoAlcance <= 0) {
            throw new IllegalArgumentException(
                    "O novo alcance do sensor deve ser estritamente positivo (> 0). Recebido: " + novoAlcance);
        }
        this.alcanceSensor = novoAlcance;
    }

    public boolean transmitirDadosAtuais() {
        return true;
    }

    @Override
    protected void realizarAcaoLocal() {
        double novaCapacidade = bateria.getCapacidadeAtual() - CUSTO_VARREDURA;
        this.bateria = new NivelEnergia(novaCapacidade, bateria.getCapacidadeMaxima());
        transmitirDadosAtuais();
    }

    @Override
    public void conectarBase() {
        this.bateria = new NivelEnergia(bateria.getCapacidadeMaxima(), bateria.getCapacidadeMaxima());
    }

    @Override
    public String getTipo() {
        return "EXPLORADORA";
    }
}
