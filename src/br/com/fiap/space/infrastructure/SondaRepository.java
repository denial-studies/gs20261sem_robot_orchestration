package br.com.fiap.space.infrastructure;

import br.com.fiap.space.domain.Sonda;

import java.util.ArrayList;
import java.util.List;

public class SondaRepository {

    private final List<Sonda> bancoDeDados;

    public SondaRepository() {
        this.bancoDeDados = new ArrayList<>();
        System.out.println("  [DATABASE] Conexão com o banco de dados simulado iniciada.");
    }

    public void registrar(Sonda sonda) {
        if (sonda == null) {
            throw new IllegalArgumentException("A sonda não pode ser nula.");
        }

        for (int i = 0; i < bancoDeDados.size(); i++) {
            if (bancoDeDados.get(i).getIdSonda().equals(sonda.getIdSonda())) {
                throw new IllegalArgumentException(
                        "Já existe uma sonda registrada com o ID: " + sonda.getIdSonda());
            }
        }

        bancoDeDados.add(sonda);
        System.out.println("  [DATABASE] INSERT: Sonda '" + sonda.getIdSonda()
                + "' salva no banco. Registros totais: " + bancoDeDados.size());
    }

    public Sonda buscarPorId(String idSonda) {
        System.out.println("  [DATABASE] SELECT: Buscando sonda com ID = '" + idSonda + "'...");

        if (idSonda == null || idSonda.trim().isEmpty()) {
            return null;
        }

        for (int i = 0; i < bancoDeDados.size(); i++) {
            if (bancoDeDados.get(i).getIdSonda().equals(idSonda)) {
                System.out.println("  [DATABASE] Registro encontrado.");
                return bancoDeDados.get(i);
            }
        }

        System.out.println("  [DATABASE] Nenhum registro encontrado.");
        return null;
    }

    public List<Sonda> listarTodas() {
        System.out.println("  [DATABASE] SELECT ALL: Listando " + bancoDeDados.size() + " sonda(s) do banco.");
        return new ArrayList<>(bancoDeDados);
    }

    public boolean remover(String idSonda) {
        System.out.println("  [DATABASE] DELETE: Removendo sonda com ID = '" + idSonda + "'...");

        for (int i = 0; i < bancoDeDados.size(); i++) {
            if (bancoDeDados.get(i).getIdSonda().equals(idSonda)) {
                bancoDeDados.remove(i);
                System.out.println("  [DATABASE] Registro removido com sucesso.");
                return true;
            }
        }

        System.out.println("  [DATABASE] Nenhum registro encontrado para remoção.");
        return false;
    }
}
