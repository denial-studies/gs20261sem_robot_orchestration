package br.com.fiap.space.domain;

import br.com.fiap.space.domain.enums.StatusFuncionamento;
import br.com.fiap.space.domain.enums.Terreno;
import br.com.fiap.space.domain.exceptions.BateriaCriticaException;
import br.com.fiap.space.domain.exceptions.TerrenoInvalidoException;
import br.com.fiap.space.domain.valueobjects.Coordenada;
import br.com.fiap.space.domain.valueobjects.NivelEnergia;
import br.com.fiap.space.domain.valueobjects.RelatorioSistema;

public abstract class Sonda {

    private String idSonda;
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

    public double nivelAtualBateria() {
        return bateria.getCapacidadeAtual();
    }

    public Coordenada getPosicaoAtual() {
        return posicaoAtual;
    }

    public void mover(Coordenada posicao, Terreno terreno) {
        if (posicao == null) {
            throw new IllegalArgumentException("A coordenada de destino não pode ser nula.");
        }
        if (terreno == null) {
            throw new IllegalArgumentException("O terreno não pode ser nulo.");
        }

        if (terreno == Terreno.CRATERA && getTipoSonda().equals("ROVER")) {
            throw new TerrenoInvalidoException(
                    "Terreno invalido! A sonda '" + idSonda
                            + "' (com rodas) nao pode acessar uma Cratera profunda.");
        }

        double custoEnergia = CUSTO_BASE_MOVIMENTO * terreno.getMultiplicadorConsumo();

        if (bateria.getCapacidadeAtual() < custoEnergia) {
            throw new BateriaCriticaException(
                    "Bateria crítica na sonda '" + idSonda
                            + "'! Energia insuficiente para mover. Necessário: " + custoEnergia
                            + ", disponível: " + bateria.getCapacidadeAtual() + ".");
        }

        double novaCapacidade = bateria.getCapacidadeAtual() - custoEnergia;
        this.bateria = new NivelEnergia(novaCapacidade, bateria.getCapacidadeMaxima());
        this.posicaoAtual = posicao;
    }

    public abstract String getTipoSonda();

    public abstract String getTipoFuncao();

    protected abstract void realizarAcaoLocal();

    public void executarRotinaAutonoma(Coordenada destino, Terreno terreno) {
        validarSistema();
        mover(destino, terreno);
        realizarAcaoLocal();
        enviarRelatorio();
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

    public RelatorioSistema enviarRelatorio() {
        return validarSistema();
    }

    public String getTipo() {
        return getTipoSonda() + " " + getTipoFuncao();
    }
}
