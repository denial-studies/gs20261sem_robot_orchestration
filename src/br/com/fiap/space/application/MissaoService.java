package br.com.fiap.space.application;

import br.com.fiap.space.domain.Sonda;
import br.com.fiap.space.domain.SondaExploradora;
import br.com.fiap.space.domain.SondaMineradora;
import br.com.fiap.space.domain.enums.Terreno;
import br.com.fiap.space.domain.factory.SondaFactory;
import br.com.fiap.space.domain.interfaces.Recarregavel;
import br.com.fiap.space.domain.valueobjects.Coordenada;

import java.util.List;

public class MissaoService {

    private CentroDeComando centroDeComando;

    public MissaoService(CentroDeComando centroDeComando) {
        if (centroDeComando == null) {
            throw new IllegalArgumentException("O Centro de Comando não pode ser nulo.");
        }
        this.centroDeComando = centroDeComando;
    }

    public Sonda lancarSonda(String tipoMissao, String idSonda, double parametroExtra) {
        if (tipoMissao == null || tipoMissao.trim().isEmpty()) {
            throw new IllegalArgumentException("O tipo de missão não pode ser nulo ou vazio.");
        }

        String tipo = tipoMissao.toUpperCase().trim();
        Sonda sonda;
        if (tipo.equals("MINERACAO")) {
            sonda = SondaFactory.criarSondaMineradora(idSonda, parametroExtra);
        } else if (tipo.equals("EXPLORACAO")) {
            sonda = SondaFactory.criarSondaExploradora(idSonda, parametroExtra);
        } else {
            throw new IllegalArgumentException(
                    "Tipo de missão desconhecido: '" + tipoMissao
                            + "'. Valores válidos: MINERACAO, EXPLORACAO.");
        }

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
}
