package br.com.fiap.space.application;

import br.com.fiap.space.domain.*;
import br.com.fiap.space.domain.factory.SondaFactory;
import br.com.fiap.space.domain.interfaces.Recarregavel;
import br.com.fiap.space.domain.valueobjects.Coordenada;

import java.util.List;

public class MissaoService {

    private final CentroDeComando centroDeComando;

    public MissaoService(CentroDeComando centroDeComando) {
        if (centroDeComando == null) {
            throw new IllegalArgumentException("O Centro de Comando não pode ser nulo.");
        }
        this.centroDeComando = centroDeComando;
    }

    public Sonda lancarSonda(String tipoMissao, String idSonda, double parametroExtra) {
        Sonda sonda = SondaFactory.criarSonda(tipoMissao, idSonda, parametroExtra);
        centroDeComando.registrarSonda(sonda);
        return sonda;
    }

    public List<Sonda> listarSondas() {
        return centroDeComando.listarSondasAtivas();
    }

    public void executarRotina(String idSonda, Coordenada destino, Terreno terreno) {
        Sonda sonda = centroDeComando.buscarSonda(idSonda);
        if (sonda == null) {
            throw new IllegalArgumentException("Sonda não encontrada com ID: " + idSonda);
        }

        sonda.executarRotinaAutonoma(destino, terreno);
    }

    public void minerar(String idSonda, Recurso recurso, int quantidade) {
        Sonda sonda = centroDeComando.buscarSonda(idSonda);
        if (sonda == null) {
            throw new IllegalArgumentException("Sonda não encontrada com ID: " + idSonda);
        }

        if (!(sonda instanceof SondaMineradora)) {
            throw new IllegalArgumentException("A sonda '" + idSonda + "' não é uma mineradora.");
        }

        SondaMineradora mineradora = (SondaMineradora) sonda;
        mineradora.minerar(recurso, quantidade);
    }

    public void descarregarCompartimento(String idSonda) {
        Sonda sonda = centroDeComando.buscarSonda(idSonda);
        if (sonda == null) {
            throw new IllegalArgumentException("Sonda não encontrada com ID: " + idSonda);
        }

        if (!(sonda instanceof SondaMineradora)) {
            throw new IllegalArgumentException("A sonda '" + idSonda + "' não é uma mineradora.");
        }

        SondaMineradora mineradora = (SondaMineradora) sonda;
        mineradora.descarregarCompartimento();
    }

    public void ajustarSensor(String idSonda, double novoAlcance) {
        Sonda sonda = centroDeComando.buscarSonda(idSonda);
        if (sonda == null) {
            throw new IllegalArgumentException("Sonda não encontrada com ID: " + idSonda);
        }

        if (!(sonda instanceof SondaExploradora)) {
            throw new IllegalArgumentException("A sonda '" + idSonda + "' não é uma exploradora.");
        }

        SondaExploradora exploradora = (SondaExploradora) sonda;
        exploradora.ajustarSensor(novoAlcance);
    }

    public void recarregarSonda(String idSonda) {
        Sonda sonda = centroDeComando.buscarSonda(idSonda);
        if (sonda == null) {
            throw new IllegalArgumentException("Sonda não encontrada com ID: " + idSonda);
        }

        if (!(sonda instanceof Recarregavel)) {
            throw new IllegalArgumentException("A sonda '" + idSonda + "' não suporta recarga.");
        }

        Recarregavel recarregavel = (Recarregavel) sonda;
        recarregavel.conectarBase();
    }

    public boolean transmitirDados(String idSonda) {
        Sonda sonda = centroDeComando.buscarSonda(idSonda);
        if (sonda == null) {
            throw new IllegalArgumentException("Sonda não encontrada com ID: " + idSonda);
        }

        if (!(sonda instanceof SondaExploradora)) {
            throw new IllegalArgumentException("A sonda '" + idSonda + "' não é uma exploradora.");
        }

        SondaExploradora exploradora = (SondaExploradora) sonda;
        return exploradora.transmitirDados();
    }
}
