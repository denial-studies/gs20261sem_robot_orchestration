package br.com.fiap.space.application;

import br.com.fiap.space.domain.Sonda;
import br.com.fiap.space.domain.SondaExploradora;
import br.com.fiap.space.domain.SondaMineradora;
import br.com.fiap.space.domain.DroneMineradora;
import br.com.fiap.space.domain.DroneExploradora;
import br.com.fiap.space.domain.enums.Terreno;
import br.com.fiap.space.domain.factory.SondaFactory;
import br.com.fiap.space.domain.interfaces.Recarregavel;
import br.com.fiap.space.domain.interfaces.TrocarBateria;
import br.com.fiap.space.domain.valueobjects.Coordenada;

import java.util.List;

public class MissaoService {

    private CentroDeComando centroDeComando;

    public MissaoService(CentroDeComando centroDeComando) {
        if (centroDeComando == null) {
            throw new IllegalArgumentException("O Centro de Comando nao pode ser nulo.");
        }
        this.centroDeComando = centroDeComando;
    }

    public Sonda lancarSonda(String tipoMissao, String idSonda, double parametroExtra) {
        if (tipoMissao == null || tipoMissao.trim().isEmpty()) {
            throw new IllegalArgumentException("O tipo de missao nao pode ser nulo ou vazio.");
        }

        String tipo = tipoMissao.toUpperCase().trim();
        Sonda sonda;
        if (tipo.equals("MINERACAO")) {
            sonda = SondaFactory.criarSondaMineradora(idSonda, parametroExtra);
        } else if (tipo.equals("EXPLORACAO")) {
            sonda = SondaFactory.criarSondaExploradora(idSonda, parametroExtra);
        } else if (tipo.equals("DRONE_MINERACAO")) {
            sonda = SondaFactory.criarDroneMineradora(idSonda, parametroExtra);
        } else if (tipo.equals("DRONE_EXPLORACAO")) {
            sonda = SondaFactory.criarDroneExploradora(idSonda, parametroExtra);
        } else {
            throw new IllegalArgumentException(
                    "Tipo de missao desconhecido: '" + tipoMissao
                            + "'. Valores validos: MINERACAO, EXPLORACAO, DRONE_MINERACAO, DRONE_EXPLORACAO.");
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
            throw new IllegalArgumentException("Sonda nao encontrada com ID: " + idSonda);
        }

        sonda.executarRotinaAutonoma(destino, terreno);
    }

    public void descarregarCompartimento(String idSonda) {
        Sonda sonda = centroDeComando.buscarSonda(idSonda);
        if (sonda == null) {
            throw new IllegalArgumentException("Sonda nao encontrada com ID: " + idSonda);
        }

        if (sonda instanceof SondaMineradora) {
            ((SondaMineradora) sonda).descarregarCompartimento();
        } else if (sonda instanceof DroneMineradora) {
            ((DroneMineradora) sonda).descarregarCompartimento();
        } else {
            throw new IllegalArgumentException("A sonda '" + idSonda + "' nao e uma mineradora.");
        }
    }

    public void ajustarSensor(String idSonda, double novoAlcance) {
        Sonda sonda = centroDeComando.buscarSonda(idSonda);
        if (sonda == null) {
            throw new IllegalArgumentException("Sonda nao encontrada com ID: " + idSonda);
        }

        if (sonda instanceof SondaExploradora) {
            ((SondaExploradora) sonda).ajustarSensor(novoAlcance);
        } else if (sonda instanceof DroneExploradora) {
            ((DroneExploradora) sonda).ajustarSensor(novoAlcance);
        } else {
            throw new IllegalArgumentException("A sonda '" + idSonda + "' nao e uma exploradora.");
        }
    }

    public void recarregarSonda(String idSonda) {
        Sonda sonda = centroDeComando.buscarSonda(idSonda);
        if (sonda == null) {
            throw new IllegalArgumentException("Sonda nao encontrada com ID: " + idSonda);
        }

        if (sonda instanceof Recarregavel) {
            ((Recarregavel) sonda).conectarBase();
        } else if (sonda instanceof TrocarBateria) {
            ((TrocarBateria) sonda).trocarBateria();
        } else {
            throw new IllegalArgumentException("A sonda '" + idSonda + "' nao suporta recarga.");
        }
    }
}

