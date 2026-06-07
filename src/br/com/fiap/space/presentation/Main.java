package br.com.fiap.space.presentation;

import br.com.fiap.space.application.CentroDeComando;
import br.com.fiap.space.application.MissaoService;
import br.com.fiap.space.domain.Sonda;
import br.com.fiap.space.domain.SondaExploradora;
import br.com.fiap.space.domain.SondaMineradora;
import br.com.fiap.space.domain.enums.Terreno;
import br.com.fiap.space.domain.exceptions.BateriaCriticaException;
import br.com.fiap.space.domain.exceptions.CargaExcedidaException;
import br.com.fiap.space.domain.exceptions.TerrenoInvalidoException;
import br.com.fiap.space.domain.valueobjects.Coordenada;
import br.com.fiap.space.domain.valueobjects.RelatorioSistema;
import br.com.fiap.space.infrastructure.SondaRepository;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final String SEPARADOR = "════════════════════════════════════════════════════";
    private static final String TITULO = "\n" +
            "╔══════════════════════════════════════════════════╗\n" +
            "║   🚀  SURFACE AUTONOMOUS FLEET SYSTEM (SAFS)    ║\n" +
            "║       FIAP - Global Solution 2026               ║\n" +
            "║       Nova Economia Espacial                    ║\n" +
            "╚══════════════════════════════════════════════════╝\n";

    public static void main(String[] args) {

        // Camada de Infraestrutura: repositório simulado (banco de dados em memória)
        SondaRepository repositorio = new SondaRepository();

        // Camada de Aplicação: Singleton do Centro de Comando recebe o repositório
        CentroDeComando centroDeComando = CentroDeComando.getInstancia(repositorio);

        // Camada de Aplicação: Serviço de missão recebe o Centro de Comando
        MissaoService missaoService = new MissaoService(centroDeComando);

        Scanner scanner = new Scanner(System.in);
        boolean executando = true;

        System.out.println(TITULO);

        while (executando) {
            exibirMenu();
            System.out.print("  ➤ Opção: ");
            String opcao = scanner.nextLine().trim();

            System.out.println();

            try {
                switch (opcao) {
                    case "1":
                        lancarSonda(scanner, missaoService);
                        break;
                    case "2":
                        listarSondas(missaoService);
                        break;
                    case "3":
                        executarRotina(scanner, missaoService);
                        break;
                    case "4":
                        descarregarCompartimento(scanner, missaoService);
                        break;
                    case "5":
                        ajustarSensor(scanner, missaoService);
                        break;
                    case "6":
                        recarregarBateria(scanner, missaoService);
                        break;
                    case "7":
                        consultarBancoDeDados(repositorio);
                        break;
                    case "0":
                        System.out.println("  Encerrando o sistema SAFS. Até a próxima missão, Comandante!");
                        executando = false;
                        break;
                    default:
                        System.out.println("  ⚠ Opção inválida. Tente novamente.");
                        break;
                }
            } catch (BateriaCriticaException e) {
                System.out.println("\n  🔋 ALERTA — BATERIA CRÍTICA!");
                System.out.println("  " + e.getMessage());
            } catch (TerrenoInvalidoException e) {
                System.out.println("\n  🪨 ALERTA — TERRENO INVÁLIDO!");
                System.out.println("  " + e.getMessage());
            } catch (CargaExcedidaException e) {
                System.out.println("\n  📦 ALERTA — CARGA EXCEDIDA!");
                System.out.println("  " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("\n  ⚠ Entrada numérica inválida. Por favor, insira um número válido.");
            } catch (IllegalArgumentException e) {
                System.out.println("\n  ⚠ ERRO DE VALIDAÇÃO: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("\n  ❌ Erro inesperado: " + e.getMessage());
            }

            System.out.println();
        }

        scanner.close();
    }

    private static void exibirMenu() {
        System.out.println(SEPARADOR);
        System.out.println("  MENU DE COMANDOS — Comandante de Missão");
        System.out.println(SEPARADOR);
        System.out.println("  [1] Lançar nova Sonda (Factory)");
        System.out.println("  [2] Listar Sondas Ativas (Centro de Comando)");
        System.out.println("  [3] Executar Rotina Autônoma (Template Method)");
        System.out.println("  [4] Descarregar Compartimento (SondaMineradora)");
        System.out.println("  [5] Ajustar Sensor (SondaExploradora)");
        System.out.println("  [6] Recarregar Bateria na Base");
        System.out.println("  [7] Consultar Banco de Dados (Repository)");
        System.out.println("  [0] Sair");
        System.out.println(SEPARADOR);
    }

    private static void lancarSonda(Scanner scanner, MissaoService missaoService) {
        System.out.println("  ── LANÇAR NOVA SONDA ──");
        System.out.println("  Tipos de missão: MINERACAO | EXPLORACAO");
        System.out.print("  Tipo de missão: ");
        String tipo = scanner.nextLine().trim();

        System.out.print("  ID da sonda (ex: SND-001): ");
        String id = scanner.nextLine().trim();

        double param;
        if (tipo.equalsIgnoreCase("MINERACAO")) {
            System.out.print("  Capacidade máxima do compartimento de carga (kg): ");
            param = Double.parseDouble(scanner.nextLine().trim());
        } else {
            System.out.print("  Alcance do sensor (unidades): ");
            param = Double.parseDouble(scanner.nextLine().trim());
        }

        Sonda sonda = missaoService.lancarSonda(tipo, id, param);
        System.out.println("\n  ✔ Sonda lançada e salva no banco com sucesso!");
        System.out.println("  [" + sonda.getTipo() + "] " + sonda.getIdSonda()
                + " | Posição: (" + sonda.getPosicaoAtual().getEixoX()
                + ", " + sonda.getPosicaoAtual().getEixoY() + ")"
                + " | Bateria: " + String.format("%.1f", sonda.nivelAtualBateria()));
    }

    private static void listarSondas(MissaoService missaoService) {
        System.out.println("  ── SONDAS ATIVAS (Centro de Comando) ──");
        List<Sonda> sondas = missaoService.listarSondas();

        if (sondas.isEmpty()) {
            System.out.println("  Nenhuma sonda ativa no Centro de Comando.");
            return;
        }

        System.out.println("  Total: " + sondas.size() + " sonda(s)\n");
        for (int i = 0; i < sondas.size(); i++) {
            Sonda s = sondas.get(i);
            System.out.println("  " + (i + 1) + ". [" + s.getTipo() + "] "
                    + s.getIdSonda() + " | Posição: ("
                    + s.getPosicaoAtual().getEixoX() + ", " + s.getPosicaoAtual().getEixoY() + ")"
                    + " | Bateria: " + String.format("%.1f", s.nivelAtualBateria()));

            if (s instanceof SondaMineradora) {
                SondaMineradora m = (SondaMineradora) s;
                String tipoCarga = m.getCarga().getTipoCarga() != null
                        ? m.getCarga().getTipoCarga().getNome() : "Vazio";
                System.out.println("     Carga [" + tipoCarga + "]: "
                        + String.format("%.1f / %.1f kg",
                                m.getCarga().getVolumeOcupado(),
                                m.getCarga().getVolumeMaximo()));
            }

            if (s instanceof SondaExploradora) {
                SondaExploradora e = (SondaExploradora) s;
                System.out.println("     Alcance Sensor: " + e.getAlcanceSensor() + " unidades");
            }

            RelatorioSistema rel = s.validarSistema();
            System.out.println("     Bateria:  " + rel.getStatusBateria());
            System.out.println("     Rodas:    " + rel.getStatusRodas());
            System.out.println("     Software: " + rel.getStatusSoftware());
            System.out.println("     Danos:    " + rel.getStatusDanos());
            System.out.println("     Funções:  " + rel.getStatusFuncoes());
        }
    }

    private static void executarRotina(Scanner scanner, MissaoService missaoService) {
        System.out.println("  ── EXECUTAR ROTINA AUTÔNOMA ──");
        System.out.print("  ID da sonda: ");
        String id = scanner.nextLine().trim();

        System.out.print("  Coordenada X de destino: ");
        int x = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("  Coordenada Y de destino: ");
        int y = Integer.parseInt(scanner.nextLine().trim());

        Coordenada destino = new Coordenada(x, y);

        System.out.println("  Tipos de terreno:");
        Terreno[] terrenos = Terreno.values();
        for (int i = 0; i < terrenos.length; i++) {
            System.out.println("    [" + (i + 1) + "] " + terrenos[i]);
        }
        System.out.print("  Selecione o terreno: ");
        int terrenoIdx = Integer.parseInt(scanner.nextLine().trim()) - 1;

        if (terrenoIdx < 0 || terrenoIdx >= terrenos.length) {
            throw new IllegalArgumentException("Índice de terreno inválido.");
        }

        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println("║  ROTINA AUTÔNOMA — Sonda: " + id);
        System.out.println("╚══════════════════════════════════════════════════╝");

        System.out.println("\n  [PASSO 1] Validando sistema...");
        System.out.println("  [PASSO 2] Deslocando para (" + x + ", " + y + ")...");
        System.out.println("  [PASSO 3] Realizando ação local...");
        System.out.println("  [PASSO 4] Enviando relatório ao Centro de Comando...");

        missaoService.executarRotina(id, destino, terrenos[terrenoIdx]);

        System.out.println("\n  ✔ Rotina autônoma concluída com sucesso!");
        System.out.println("══════════════════════════════════════════════════");
    }

    private static void descarregarCompartimento(Scanner scanner, MissaoService missaoService) {
        System.out.println("  ── DESCARREGAR COMPARTIMENTO ──");
        System.out.print("  ID da sonda mineradora: ");
        String id = scanner.nextLine().trim();

        missaoService.descarregarCompartimento(id);
        System.out.println("  ✔ Compartimento descarregado com sucesso.");
    }

    private static void ajustarSensor(Scanner scanner, MissaoService missaoService) {
        System.out.println("  ── AJUSTAR SENSOR ──");
        System.out.print("  ID da sonda exploradora: ");
        String id = scanner.nextLine().trim();

        System.out.print("  Novo alcance do sensor: ");
        double novoAlcance = Double.parseDouble(scanner.nextLine().trim());

        missaoService.ajustarSensor(id, novoAlcance);
        System.out.println("  ✔ Sensor ajustado para alcance: " + novoAlcance);
    }

    private static void recarregarBateria(Scanner scanner, MissaoService missaoService) {
        System.out.println("  ── RECARREGAR BATERIA ──");
        System.out.print("  ID da sonda: ");
        String id = scanner.nextLine().trim();

        missaoService.recarregarSonda(id);
        System.out.println("  ✔ Bateria recarregada com sucesso.");
    }

    private static void consultarBancoDeDados(SondaRepository repositorio) {
        System.out.println("  ── BANCO DE DADOS (SondaRepository) ──");

        List<Sonda> registros = repositorio.listarTodas();

        if (registros.isEmpty()) {
            System.out.println("  Tabela 'sondas' vazia. Nenhum registro encontrado.");
            return;
        }

        System.out.println();
        System.out.println("  ┌────────────┬──────────────┬───────────┬────────────────┬────────────────────┐");
        System.out.println("  │     ID     │     TIPO     │  POSIÇÃO  │    BATERIA     │       CARGA        │");
        System.out.println("  ├────────────┼──────────────┼───────────┼────────────────┼────────────────────┤");

        for (int i = 0; i < registros.size(); i++) {
            Sonda s = registros.get(i);

            String idSonda = s.getIdSonda();
            String tipo = s.getTipo();
            String posicao = "(" + s.getPosicaoAtual().getEixoX() + ", "
                    + s.getPosicaoAtual().getEixoY() + ")";
            String bateria = String.format("%.1f", s.nivelAtualBateria());

            String carga = "---";
            if (s instanceof SondaMineradora) {
                SondaMineradora m = (SondaMineradora) s;
                carga = String.format("%.1f/%.1f kg",
                        m.getCarga().getVolumeOcupado(),
                        m.getCarga().getVolumeMaximo());
            }

            System.out.println(String.format("  │ %-10s │ %-12s │ %-9s │ %-14s │ %-18s │",
                    idSonda, tipo, posicao, bateria, carga));
        }

        System.out.println("  └────────────┴──────────────┴───────────┴────────────────┴────────────────────┘");
        System.out.println("  Total de registros: " + registros.size());
    }
}
