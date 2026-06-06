package br.com.fiap.space.domain.valueobjects;

import br.com.fiap.space.domain.StatusFuncionamento;

public final class RelatorioSistema {

    private final StatusFuncionamento statusBateria;
    private final StatusFuncionamento statusRodas;
    private final StatusFuncionamento statusSoftware;
    private final StatusFuncionamento statusSensores;
    private final StatusFuncionamento statusFuncoes;

    public RelatorioSistema(StatusFuncionamento statusBateria,
                            StatusFuncionamento statusRodas,
                            StatusFuncionamento statusSoftware,
                            StatusFuncionamento statusSensores,
                            StatusFuncionamento statusFuncoes) {
        this.statusBateria = statusBateria;
        this.statusRodas = statusRodas;
        this.statusSoftware = statusSoftware;
        this.statusSensores = statusSensores;
        this.statusFuncoes = statusFuncoes;
    }

    public StatusFuncionamento getStatusBateria() {
        return statusBateria;
    }

    public StatusFuncionamento getStatusRodas() {
        return statusRodas;
    }

    public StatusFuncionamento getStatusSoftware() {
        return statusSoftware;
    }

    public StatusFuncionamento getStatusSensores() {
        return statusSensores;
    }

    public StatusFuncionamento getStatusFuncoes() {
        return statusFuncoes;
    }

    public boolean isTudoOperante() {
        return statusBateria == StatusFuncionamento.OPERANTE
                && statusRodas == StatusFuncionamento.OPERANTE
                && statusSoftware == StatusFuncionamento.OPERANTE
                && statusSensores == StatusFuncionamento.OPERANTE
                && statusFuncoes == StatusFuncionamento.OPERANTE;
    }
}
