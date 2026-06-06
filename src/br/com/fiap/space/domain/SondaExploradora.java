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
        System.out.println("  [SENSOR] Sensor da sonda '" + getIdSonda()
                + "' ajustado para alcance: " + novoAlcance);
    }

    public boolean transmitirDados() {
        System.out.println("  [TRANSMITIR] Sonda '" + getIdSonda()
                + "' transmitindo dados de varredura ao Centro de Comando...");
        System.out.println("  [TRANSMITIR] Dados da posição ("
                + getPosicaoAtual().getEixoX() + ", " + getPosicaoAtual().getEixoY() + ")"
                + " com alcance de sensor " + alcanceSensor + " transmitidos com sucesso.");
        return true;
    }

    @Override
    protected void realizarAcaoLocal() {
        System.out.println("  [AÇÃO LOCAL] Exploradora realizando varredura de área...");

        this.bateria = bateria.consumir(CUSTO_VARREDURA);

        System.out.println("  [VARREDURA] Área varrida no raio de " + alcanceSensor
                + " unidades a partir de ("
                + getPosicaoAtual().getEixoX() + ", " + getPosicaoAtual().getEixoY() + ")"
                + " | Energia consumida: " + CUSTO_VARREDURA
                + " | Bateria restante: " + String.format("%.1f", bateria.getCapacidadeAtual()));

        transmitirDados();
    }

    @Override
    public void conectarBase() {
        this.bateria = bateria.recarregar();
        System.out.println("  [RECARREGAR] Sonda exploradora '" + getIdSonda()
                + "' conectada à base. Bateria recarregada: "
                + String.format("%.1f / %.1f", bateria.getCapacidadeAtual(), bateria.getCapacidadeMaxima()));
    }

    @Override
    public String getTipo() {
        return "EXPLORADORA";
    }
}
