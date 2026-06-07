package br.com.fiap.space.domain;

import br.com.fiap.space.domain.interfaces.TrocarBateria;
import br.com.fiap.space.domain.valueobjects.NivelEnergia;

public class DroneExploradora extends Sonda implements TrocarBateria {

    private double alcanceSensor;

    private static final double CUSTO_VARREDURA = 8.0;

    public DroneExploradora(String idSonda, double alcanceSensor) {
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
    public void trocarBateria() {
        this.bateria = new NivelEnergia(bateria.getCapacidadeMaxima(), bateria.getCapacidadeMaxima());
    }

    @Override
    public String getTipoSonda() {
        return "DRONE";
    }

    @Override
    public String getTipoFuncao() {
        return "EXPLORADORA";
    }
}
