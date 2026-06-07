# SAFS — Surface Autonomous Fleet System

**FIAP — Global Solution 2026 | Nova Economia Espacial**  
**Turma: 2ESPG**

Sistema de orquestração de sondas autônomas para exploração e mineração em superfícies extraterrestres, desenvolvido em Java com arquitetura em camadas (DDD).

---

## Vídeo de Demonstração

**YouTube:** [INSERIR LINK DO YOUTUBE AQUI]

---

## Integrantes do Grupo

| Nome | RM |
|------|----|
| Daniel Oliveira de Souza | RM 566284 |
| João Pedro Marcilio | RM 561603 |
| Lucas Zanella Clemente | RM 563880 |
| Mateus Amaral Franze | RM 562334 |
| Pedro Henrique Silva Gregolini | RM 563342 |

---

## Arquitetura do Projeto

O projeto segue a divisão em camadas do Domain-Driven Design (DDD):

```
br.com.fiap.space
├── presentation       → Main (Interface via CLI por Scanner, menus e tratamento de exceções)
├── application        → MissaoService, CentroDeComando (Singleton)
├── domain
│   ├── Sonda (Abstrata), SondaMineradora, SondaExploradora
│   ├── enums          → Recurso, Terreno, StatusFuncionamento
│   ├── valueobjects   → Coordenada, NivelEnergia, CompartimentoCarga, RelatorioSistema
│   ├── exceptions     → BateriaCriticaException, CargaExcedidaException, TerrenoInvalidoException
│   ├── interfaces     → Recarregavel
│   └── factory        → SondaFactory
└── infrastructure     → SondaRepository (Simulação de persistência em memória)
```

## Padrões de Projeto Aplicados

- **Factory Method**: Implementado na classe `SondaFactory` com construtor privado e métodos estáticos para instanciação de sondas.
- **Singleton**: Aplicado no `CentroDeComando` com instância única gerenciada via `getInstancia()`.
- **Template Method**: Definido em `Sonda.executarRotinaAutonoma()`, que estabelece o fluxo de execução das sondas:
  1. Validar sistema (bateria e diagnóstico).
  2. Deslocar até a coordenada.
  3. `realizarAcaoLocal()` (método abstrato implementado de forma polimórfica por cada tipo de sonda).
  4. Enviar relatório ao Centro de Comando.

## Como Executar

1. Importe o projeto no Eclipse IDE.
2. Execute a classe `br.com.fiap.space.presentation.Main`.
3. Interaja com o menu exibido no console.

## Funcionalidades Principais

- Lançamento de sondas de mineração e exploração.
- Listagem de sondas ativas com seus respectivos status.
- Execução de rotina autônoma de deslocamento e ação local.
- Recarga de bateria na base (através da interface `Recarregavel`).
- Descarregamento de compartimento de carga.
- Ajuste manual de sensor de sonda exploradora.
- Persistência em memória via padrão Repository.
- Tratamento e exibição amigável de exceções customizadas sem interromper o fluxo do sistema.
