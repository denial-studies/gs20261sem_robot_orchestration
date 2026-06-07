package br.com.fiap.space.domain.factory;

import br.com.fiap.space.domain.DroneMineradora;
import br.com.fiap.space.domain.DroneExploradora;
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

    public static DroneMineradora criarDroneMineradora(String idSonda, double capacidadeMaxima) {
        return new DroneMineradora(idSonda, capacidadeMaxima);
    }

    public static DroneExploradora criarDroneExploradora(String idSonda, double alcanceSensor) {
        return new DroneExploradora(idSonda, alcanceSensor);
    }
}
