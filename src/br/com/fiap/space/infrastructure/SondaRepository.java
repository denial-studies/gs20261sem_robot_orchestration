package br.com.fiap.space.infrastructure;

import br.com.fiap.space.domain.Sonda;

import java.util.ArrayList;
import java.util.List;

public class SondaRepository {

    // Tabela simulada: lista em memória representando uma tabela SQL de sondas
    private final List<Sonda> tabelaSondas;

    public SondaRepository() {
        this.tabelaSondas = new ArrayList<>();
    }

    public void registrar(Sonda sonda) {
        if (sonda == null) {
            throw new IllegalArgumentException("A sonda não pode ser nula.");
        }

        for (int i = 0; i < tabelaSondas.size(); i++) {
            if (tabelaSondas.get(i).getIdSonda().equals(sonda.getIdSonda())) {
                throw new IllegalArgumentException(
                        "Já existe uma sonda registrada com o ID: " + sonda.getIdSonda());
            }
        }

        tabelaSondas.add(sonda);
    }

    public Sonda buscarPorId(String idSonda) {
        if (idSonda == null || idSonda.trim().isEmpty()) {
            return null;
        }

        for (int i = 0; i < tabelaSondas.size(); i++) {
            if (tabelaSondas.get(i).getIdSonda().equals(idSonda)) {
                return tabelaSondas.get(i);
            }
        }

        return null;
    }

    public List<Sonda> listarTodas() {
        return new ArrayList<>(tabelaSondas);
    }

}
