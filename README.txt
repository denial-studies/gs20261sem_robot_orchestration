===============================================================
  SAFS - Surface Autonomous Fleet System
  FIAP - Global Solution 2026 | Nova Economia Espacial
  Turma: 2ESPG
===============================================================

Sistema de orquestracao de sondas autonomas para exploracao
e mineracao em superficies extraterrestres, desenvolvido
em Java com arquitetura em camadas (DDD).

---------------------------------------------------------------
  VIDEO DE DEMONSTRACAO
---------------------------------------------------------------

YouTube: [INSERIR LINK DO YOUTUBE AQUI]

---------------------------------------------------------------
  INTEGRANTES DO GRUPO
---------------------------------------------------------------

Nome                                          | RM
----------------------------------------------|--------
Daniel Oliveira de Souza                      | RM 566284
João Pedro Marcilio                           | RM 561603
Lucas Zanella Clemente                        | RM 563880
Mateus Amaral Franze                          | RM 562334
Pedro Henrique Silva Gregolini                | RM 563342

---------------------------------------------------------------
  ARQUITETURA DO PROJETO
---------------------------------------------------------------

br.com.fiap.space
  presentation       -> Main (CLI via Scanner, menus e try/catch)
  application        -> MissaoService, CentroDeComando (Singleton)
  domain
    Sonda (Abstrata), SondaMineradora, SondaExploradora
    enums            -> Recurso, Terreno, StatusFuncionamento
    valueobjects     -> Coordenada, NivelEnergia, CompartimentoCarga, RelatorioSistema
    exceptions       -> BateriaCriticaException, CargaExcedidaException, TerrenoInvalidoException
    interfaces       -> Recarregavel
    factory          -> SondaFactory
  infrastructure     -> SondaRepository (simulacao de banco de dados em memoria)

---------------------------------------------------------------
  PADROES DE PROJETO APLICADOS
---------------------------------------------------------------

1. Factory Method  - SondaFactory com construtor privado e metodos estaticos
2. Singleton       - CentroDeComando com instancia unica via getInstancia()
3. Template Method - Sonda.executarRotinaAutonoma() define o esqueleto:
   Passo 1: Validar sistema (Bateria e Diagnostico)
   Passo 2: Deslocar ate a coordenada
   Passo 3: realizarAcaoLocal() (hook abstrato - cada sonda faz o seu)
   Passo 4: Enviar relatorio ao Centro de Comando

---------------------------------------------------------------
  COMO EXECUTAR
---------------------------------------------------------------

1. Abrir o projeto no Eclipse IDE
2. Executar a classe br.com.fiap.space.presentation.Main
3. Interagir com o menu via console

===============================================================
