package br.com.fiap.space.domain.factory;

import br.com.fiap.space.domain.Sonda;
import br.com.fiap.space.domain.SondaExploradora;
import br.com.fiap.space.domain.SondaMineradora;

public class SondaFactory {

    private SondaFactory() {
    }

    public static SondaMineradora criarSondaMineradora(String idSonda, double capacidadeMaxima) {
        return new SondaMineradora(idSonda, capacidadeMaxima);
    }

    public static SondaExploradora criarSondaExploradora(String idSonda, double alcanceSensor) {
        return new SondaExploradora(idSonda, alcanceSensor);
    }

    public static Sonda criarSonda(String tipoMissao, String idSonda, double parametroExtra) {
        if (tipoMissao == null || tipoMissao.trim().isEmpty()) {
            throw new IllegalArgumentException("O tipo de missão não pode ser nulo ou vazio.");
        }

        String tipo = tipoMissao.toUpperCase().trim();
        switch (tipo) {
            case "MINERACAO":
                return criarSondaMineradora(idSonda, parametroExtra);
            case "EXPLORACAO":
                return criarSondaExploradora(idSonda, parametroExtra);
            default:
                throw new IllegalArgumentException(
                        "Tipo de missão desconhecido: '" + tipoMissao
                                + "'. Valores válidos: MINERACAO, EXPLORACAO.");
        }
    }
}
