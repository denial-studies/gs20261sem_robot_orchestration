package br.com.fiap.space.domain.valueobjects;

import br.com.fiap.space.domain.enums.StatusFuncionamento;

public class RelatorioSistema {

    private StatusFuncionamento statusBateria;
    private StatusFuncionamento statusRodas;
    private StatusFuncionamento statusSoftware;
    private StatusFuncionamento statusDanos;
    private StatusFuncionamento statusFuncoes;

    public RelatorioSistema(StatusFuncionamento statusBateria,
                            StatusFuncionamento statusRodas,
                            StatusFuncionamento statusSoftware,
                            StatusFuncionamento statusDanos,
                            StatusFuncionamento statusFuncoes) {
        this.statusBateria = statusBateria;
        this.statusRodas = statusRodas;
        this.statusSoftware = statusSoftware;
        this.statusDanos = statusDanos;
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

    public StatusFuncionamento getStatusDanos() {
        return statusDanos;
    }

    public StatusFuncionamento getStatusFuncoes() {
        return statusFuncoes;
    }
}
