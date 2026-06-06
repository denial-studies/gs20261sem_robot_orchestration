package br.com.fiap.space.presentation;

import br.com.fiap.space.application.CentroDeComando;
import br.com.fiap.space.application.MissaoService;
import br.com.fiap.space.domain.*;
import br.com.fiap.space.domain.exceptions.*;
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
                        minerarRecurso(scanner, missaoService);
                        break;
                    case "5":
                        descarregarCompartimento(scanner, missaoService);
                        break;
                    case "6":
                        ajustarSensor(scanner, missaoService);
                        break;
                    case "7":
                        recarregarBateria(scanner, missaoService);
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
        System.out.println("  [2] Listar Sondas (Status, Bateria, Posição, Carga)");
        System.out.println("  [3] Executar Rotina Autônoma (Template Method)");
        System.out.println("  [4] Minerar Recurso (SondaMineradora)");
        System.out.println("  [5] Descarregar Compartimento (SondaMineradora)");
        System.out.println("  [6] Ajustar Sensor (SondaExploradora)");
        System.out.println("  [7] Recarregar Bateria na Base");
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
        System.out.println("\n  ✔ Sonda lançada com sucesso: [" + sonda.getTipo() + "] "
                + sonda.getIdSonda() + " | Posição: ("
                + sonda.getPosicaoAtual().getEixoX() + ", " + sonda.getPosicaoAtual().getEixoY() + ")"
                + " | Bateria: " + String.format("%.1f / %.1f",
                        sonda.getBateria().getCapacidadeAtual(),
                        sonda.getBateria().getCapacidadeMaxima()));
    }

    private static void listarSondas(MissaoService missaoService) {
        System.out.println("  ── SONDAS ATIVAS ──");
        List<Sonda> sondas = missaoService.listarSondas();

        if (sondas.isEmpty()) {
            System.out.println("  Nenhuma sonda registrada no Centro de Comando.");
            return;
        }

        System.out.println("  Total: " + sondas.size() + " sonda(s)\n");
        for (int i = 0; i < sondas.size(); i++) {
            Sonda s = sondas.get(i);
            System.out.println("  " + (i + 1) + ". [" + s.getTipo() + "] "
                    + s.getIdSonda() + " | Posição: ("
                    + s.getPosicaoAtual().getEixoX() + ", " + s.getPosicaoAtual().getEixoY() + ")"
                    + " | Bateria: " + String.format("%.1f / %.1f",
                            s.getBateria().getCapacidadeAtual(),
                            s.getBateria().getCapacidadeMaxima()));

            RelatorioSistema rel = s.validarSistema();
            if (rel.isTudoOperante()) {
                System.out.println("     ✔ Todos os sistemas operantes");
            } else {
                System.out.println("     ⚠ Sistema(s) com defeito");
            }
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

        missaoService.executarRotina(id, destino, terrenos[terrenoIdx]);
    }

    private static void minerarRecurso(Scanner scanner, MissaoService missaoService) {
        System.out.println("  ── MINERAR RECURSO ──");
        System.out.print("  ID da sonda mineradora: ");
        String id = scanner.nextLine().trim();

        System.out.println("  Recursos disponíveis:");
        Recurso[] recursos = Recurso.values();
        for (int i = 0; i < recursos.length; i++) {
            System.out.println("    [" + (i + 1) + "] " + recursos[i]);
        }
        System.out.print("  Selecione o recurso: ");
        int recursoIdx = Integer.parseInt(scanner.nextLine().trim()) - 1;

        if (recursoIdx < 0 || recursoIdx >= recursos.length) {
            throw new IllegalArgumentException("Índice de recurso inválido.");
        }

        System.out.print("  Quantidade (unidades): ");
        int quantidade = Integer.parseInt(scanner.nextLine().trim());

        missaoService.minerar(id, recursos[recursoIdx], quantidade);
    }

    private static void descarregarCompartimento(Scanner scanner, MissaoService missaoService) {
        System.out.println("  ── DESCARREGAR COMPARTIMENTO ──");
        System.out.print("  ID da sonda mineradora: ");
        String id = scanner.nextLine().trim();

        missaoService.descarregarCompartimento(id);
    }

    private static void ajustarSensor(Scanner scanner, MissaoService missaoService) {
        System.out.println("  ── AJUSTAR SENSOR ──");
        System.out.print("  ID da sonda exploradora: ");
        String id = scanner.nextLine().trim();

        System.out.print("  Novo alcance do sensor: ");
        double novoAlcance = Double.parseDouble(scanner.nextLine().trim());

        missaoService.ajustarSensor(id, novoAlcance);
    }

    private static void recarregarBateria(Scanner scanner, MissaoService missaoService) {
        System.out.println("  ── RECARREGAR BATERIA ──");
        System.out.print("  ID da sonda: ");
        String id = scanner.nextLine().trim();

        missaoService.recarregarSonda(id);
    }
}
