package br.com.fiap.space.domain;

import br.com.fiap.space.domain.exceptions.BateriaCriticaException;
import br.com.fiap.space.domain.exceptions.TerrenoInvalidoException;
import br.com.fiap.space.domain.valueobjects.Coordenada;
import br.com.fiap.space.domain.valueobjects.NivelEnergia;
import br.com.fiap.space.domain.valueobjects.RelatorioSistema;

public abstract class Sonda {

    private final String idSonda;
    protected NivelEnergia bateria;
    protected Coordenada posicaoAtual;

    private static final double CUSTO_BASE_MOVIMENTO = 10.0;

    protected Sonda(String idSonda, NivelEnergia bateria) {
        if (idSonda == null || idSonda.trim().isEmpty()) {
            throw new IllegalArgumentException("O ID da sonda não pode ser nulo ou vazio.");
        }
        if (bateria == null) {
            throw new IllegalArgumentException("A bateria não pode ser nula.");
        }
        this.idSonda = idSonda;
        this.bateria = bateria;
        this.posicaoAtual = new Coordenada(0, 0); 
    }

    public String getIdSonda() {
        return idSonda;
    }

    public double nivelDaBateria() {
        return bateria.getCapacidadeAtual();
    }

    public Coordenada getPosicaoAtual() {
        return posicaoAtual;
    }

    public NivelEnergia getBateria() {
        return bateria;
    }

    public void mover(Coordenada destino, Terreno terreno) {
        if (destino == null) {
            throw new IllegalArgumentException("A coordenada de destino não pode ser nula.");
        }
        if (terreno == null) {
            throw new IllegalArgumentException("O terreno não pode ser nulo.");
        }

        if (terreno == Terreno.CRATERA) {
            throw new TerrenoInvalidoException(
                    "Terreno inválido! A sonda '" + idSonda
                            + "' (com rodas) não pode acessar uma Cratera profunda.");
        }

        double custoEnergia = CUSTO_BASE_MOVIMENTO * terreno.getMultiplicadorConsumo();

        if (!bateria.isSuficiente(custoEnergia)) {
            throw new BateriaCriticaException(
                    "Bateria crítica na sonda '" + idSonda
                            + "'! Energia insuficiente para mover. Necessário: " + custoEnergia
                            + ", disponível: " + bateria.getCapacidadeAtual() + ".");
        }

        this.bateria = bateria.consumir(custoEnergia);
        this.posicaoAtual = destino;

        System.out.println("  [MOVER] Sonda '" + idSonda + "' movida para ("
                + destino.getEixoX() + ", " + destino.getEixoY() + ")"
                + " | Terreno: " + terreno.getTipoSolo()
                + " | Energia consumida: " + custoEnergia
                + " | Bateria restante: " + String.format("%.1f", bateria.getCapacidadeAtual()));
    }

    protected abstract void realizarAcaoLocal();

    public final void executarRotinaAutonoma(Coordenada destino, Terreno terreno) {
        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println("║  ROTINA AUTÔNOMA — Sonda: " + idSonda);
        System.out.println("╚══════════════════════════════════════════════════╝");

        System.out.println("\n[PASSO 1] Validando sistema...");
        RelatorioSistema relatorio = validarSistema();
        System.out.println("  === Relatório do Sistema ===");
        System.out.println("    Bateria:   " + relatorio.getStatusBateria());
        System.out.println("    Rodas:     " + relatorio.getStatusRodas());
        System.out.println("    Software:  " + relatorio.getStatusSoftware());
        System.out.println("    Sensores:  " + relatorio.getStatusSensores());
        System.out.println("    Funções:   " + relatorio.getStatusFuncoes());

        if (!relatorio.isTudoOperante()) {
            System.out.println("  ⚠ ALERTA: Subsistema(s) com defeito detectado(s)!");
        }

        System.out.println("\n[PASSO 2] Deslocando para ("
                + destino.getEixoX() + ", " + destino.getEixoY() + ")...");
        mover(destino, terreno);

        System.out.println("\n[PASSO 3] Realizando ação local...");
        realizarAcaoLocal();

        System.out.println("\n[PASSO 4] Enviando relatório ao Centro de Comando...");
        RelatorioSistema relatorioFinal = validarSistema();
        System.out.println("  === Relatório do Sistema ===");
        System.out.println("    Bateria:   " + relatorioFinal.getStatusBateria());
        System.out.println("    Rodas:     " + relatorioFinal.getStatusRodas());
        System.out.println("    Software:  " + relatorioFinal.getStatusSoftware());
        System.out.println("    Sensores:  " + relatorioFinal.getStatusSensores());
        System.out.println("    Funções:   " + relatorioFinal.getStatusFuncoes());
        System.out.println("  ✔ Relatório enviado com sucesso.");

        System.out.println("\n══════════════════════════════════════════════════");
        System.out.println("  Rotina autônoma concluída para sonda: " + idSonda);
        System.out.println("══════════════════════════════════════════════════\n");
    }

    public RelatorioSistema validarSistema() {
        StatusFuncionamento statusBateria = StatusFuncionamento.DEFEITUOSO;
        if ((bateria.getCapacidadeAtual() / bateria.getCapacidadeMaxima()) >= 0.2) {
            statusBateria = StatusFuncionamento.OPERANTE;
        }

        return new RelatorioSistema(
                statusBateria,
                StatusFuncionamento.OPERANTE,  
                StatusFuncionamento.OPERANTE,  
                StatusFuncionamento.OPERANTE,  
                StatusFuncionamento.OPERANTE   
        );
    }

    public abstract String getTipo();
}
