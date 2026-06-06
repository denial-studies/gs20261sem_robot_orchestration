package br.com.fiap.space.domain;

import br.com.fiap.space.domain.interfaces.Recarregavel;
import br.com.fiap.space.domain.valueobjects.CompartimentoCarga;
import br.com.fiap.space.domain.valueobjects.NivelEnergia;

public class SondaMineradora extends Sonda implements Recarregavel {

    private CompartimentoCarga carga;

    private static final double CUSTO_MINERACAO = 5.0;

    public SondaMineradora(String idSonda, double capacidadeMaxima) {
        super(idSonda, new NivelEnergia(100.0, 100.0));
        if (capacidadeMaxima <= 0) {
            throw new IllegalArgumentException(
                    "A capacidade máxima do compartimento deve ser positiva.");
        }
        this.carga = new CompartimentoCarga(capacidadeMaxima);
    }

    public double consultarCompartimentoOcupado() {
        return carga.getVolumeOcupado();
    }

    public CompartimentoCarga getCarga() {
        return carga;
    }

    public void descarregarCompartimento() {
        this.carga = carga.descarregarCarga();
        System.out.println("  [DESCARGA] Compartimento da sonda '" + getIdSonda()
                + "' descarregado com sucesso.");
    }

    public void minerar(Recurso recurso, int quantidade) {
        if (recurso == null) {
            throw new IllegalArgumentException("O recurso não pode ser nulo.");
        }

        this.bateria = bateria.consumir(CUSTO_MINERACAO);

        this.carga = carga.adicionarVolume(recurso, quantidade);

        String tipoCarga = carga.getTipoCarga() != null ? carga.getTipoCarga().getNome() : "Vazio";
        System.out.println("  [MINERAR] Sonda '" + getIdSonda() + "' extraiu "
                + quantidade + " unidade(s) de " + recurso.getNome()
                + " | Carga [" + tipoCarga + "]: "
                + String.format("%.1f", carga.getVolumeOcupado()) + " / "
                + String.format("%.1f", carga.getVolumeMaximo()) + " kg");
    }

    @Override
    protected void realizarAcaoLocal() {
        System.out.println("  [AÇÃO LOCAL] Mineradora realizando extração automática de REGOLITO...");
        minerar(Recurso.REGOLITO, 1);
    }

    @Override
    public void conectarBase() {
        this.bateria = bateria.recarregar();
        System.out.println("  [RECARREGAR] Sonda mineradora '" + getIdSonda()
                + "' conectada à base. Bateria recarregada: "
                + String.format("%.1f / %.1f", bateria.getCapacidadeAtual(), bateria.getCapacidadeMaxima()));
    }

    @Override
    public String getTipo() {
        return "MINERADORA";
    }
}
