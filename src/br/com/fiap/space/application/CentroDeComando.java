package br.com.fiap.space.application;

import br.com.fiap.space.domain.Sonda;
import br.com.fiap.space.infrastructure.SondaRepository;

import java.util.ArrayList;
import java.util.List;

public final class CentroDeComando {

    private static CentroDeComando instancia;

    private final List<Sonda> sondasAtivas;
    private final SondaRepository sondaRepository;

    private CentroDeComando(SondaRepository sondaRepository) {
        this.sondasAtivas = new ArrayList<>();
        this.sondaRepository = sondaRepository;
    }

    public static CentroDeComando getInstancia(SondaRepository sondaRepository) {
        if (instancia == null) {
            instancia = new CentroDeComando(sondaRepository);
        }
        return instancia;
    }

    public static CentroDeComando getInstancia() {
        if (instancia == null) {
            throw new IllegalStateException(
                    "CentroDeComando ainda não foi inicializado. Chame getInstancia(SondaRepository) primeiro.");
        }
        return instancia;
    }

    public void registrarSonda(Sonda sonda) {
        if (sonda == null) {
            throw new IllegalArgumentException("A sonda não pode ser nula.");
        }

        for (int i = 0; i < sondasAtivas.size(); i++) {
            if (sondasAtivas.get(i).getIdSonda().equals(sonda.getIdSonda())) {
                throw new IllegalArgumentException(
                        "Já existe uma sonda ativa com o ID: " + sonda.getIdSonda());
            }
        }

        // Registra no sistema ao vivo
        sondasAtivas.add(sonda);
        System.out.println("  [CENTRO DE COMANDO] Sonda '" + sonda.getIdSonda()
                + "' ativa no sistema. Total de sondas ativas: " + sondasAtivas.size());

        // Persiste no banco de dados (simulado)
        sondaRepository.registrar(sonda);
    }

    public Sonda buscarSonda(String idSonda) {
        if (idSonda == null || idSonda.trim().isEmpty()) {
            return null;
        }
        for (int i = 0; i < sondasAtivas.size(); i++) {
            if (sondasAtivas.get(i).getIdSonda().equals(idSonda)) {
                return sondasAtivas.get(i);
            }
        }
        return null;
    }

    public List<Sonda> listarSondasAtivas() {
        return new ArrayList<>(sondasAtivas);
    }

    public boolean removerSonda(String idSonda) {
        for (int i = 0; i < sondasAtivas.size(); i++) {
            if (sondasAtivas.get(i).getIdSonda().equals(idSonda)) {
                sondasAtivas.remove(i);
                sondaRepository.remover(idSonda);
                System.out.println("  [CENTRO DE COMANDO] Sonda '" + idSonda + "' removida do sistema.");
                return true;
            }
        }
        return false;
    }
}
