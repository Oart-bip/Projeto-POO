Sistema de Gestão de Produtos

Sobre o Projeto
Sistema desenvolvido para demonstrar conceitos de Programação Orientada a Objetos, incluindo processamento assíncrono de pedidos, validações robustas e arquitetura em camadas.
O sistema permite o gerenciamento completo de clientes, produtos e pedidos, com processamento automático em segundo plano através de threads dedicadas.

Funcionalidades
Módulo de Clientes

Cadastro de clientes com validação de dados
Validação de formato de e-mail
Listagem de clientes cadastrados
Consulta de pedidos por cliente

Módulo de Produtos

Cadastro de produtos com categorias predefinidas
Validação de preços (apenas valores positivos)
Listagem geral e filtrada por categoria
Sistema de categorização usando ENUM

Módulo de Pedidos

Criação de pedidos vinculados a clientes
Adição de múltiplos itens ao pedido
Cálculo automático do valor total
Sistema de status do pedido (ABERTO, FILA, PROCESSANDO, FINALIZADO)

Sistema de Processamento Assíncrono

Thread separada para processar pedidos
Fila thread-safe para gerenciar pedidos pendentes
Mudança automática de status durante o processamento
Simulação de tempo de processamento
Sistema continua responsivo durante o processamento

Relatórios e Consultas

Listagem de pedidos por status
Listagem de pedidos por cliente
Estatísticas gerais do sistema
Monitoramento do status do processador


Estrutura do Projeto
src/
├── enums/
│   ├── CategoriaProduto.java    # Enumeração de categorias
│   └── StatusPedido.java         # Enumeração de status
│
├── exception/
│   ├── ValidacaoException.java   # Exceção customizada para validações
│   └── PedidoException.java      # Exceção customizada para pedidos
│
├── util/
│   ├── ValidadorEmail.java       # Utilitário de validação de e-mail
│   └── GeradorId.java            # Gerador de IDs único thread-safe
│
├── model/
│   ├── Cliente.java              # Entidade de domínio Cliente
│   ├── Produto.java              # Entidade de domínio Produto
│   ├── ItemPedido.java           # Entidade de domínio ItemPedido
│   └── Pedido.java               # Entidade de domínio Pedido
│
├── repository/
│   ├── ClienteRepository.java    # Camada de persistência - Clientes
│   ├── ProdutoRepository.java    # Camada de persistência - Produtos
│   └── PedidoRepository.java     # Camada de persistência - Pedidos
│
├── service/
│   ├── ClienteService.java       # Camada de serviço - Clientes
│   ├── ProdutoService.java       # Camada de serviço - Produtos
│   ├── PedidoService.java        # Camada de serviço - Pedidos
│   └── ProcessadorPedidos.java   # Processador assíncrono de pedidos
│
└── Main.java                      # Classe principal com interface console

Como Compilar e Executar
Pré-requisitos

JDK 11 ou superior instalado
Variável de ambiente JAVA_HOME configurada

Opção 1: Usando IDE (Recomendado)

Crie um novo projeto Java na sua IDE (IntelliJ IDEA, Eclipse, VS Code)
Crie a estrutura de pacotes conforme descrito acima
Copie cada arquivo para seu respectivo pacote
Execute a classe Main.java

Opção 2: Linha de Comando (Windows)
cmd# Navegar até a pasta do projeto
cd caminho/do/projeto

# Compilar todos os arquivos
javac -d bin -sourcepath src src/Main.java

# Executar o sistema
java -cp bin Main
Opção 3: Linha de Comando (Linux/Mac)
bash# Navegar até a pasta do projeto
cd caminho/do/projeto

# Compilar todos os arquivos
javac -d bin -sourcepath src src/Main.java

# Executar o sistema
java -cp bin Main
Opção 4: Compilação Manual
cmdcd src

javac enums/*.java
javac exception/*.java
javac util/*.java
javac model/*.java
javac repository/*.java
javac service/*.java
javac Main.java

java Main

Fundamentos de POO Aplicados
Classes e Objetos

Classes de Domínio: Cliente, Produto, Pedido, ItemPedido
Classes de Serviço: ClienteService, ProdutoService, PedidoService
Classes Utilitárias: ValidadorEmail, GeradorId
Todas as classes possuem responsabilidades bem definidas

Encapsulamento

Atributos privados em todas as entidades
Acesso controlado através de métodos públicos
Validações centralizadas nos construtores e métodos de atualização
Uso de final para atributos imutáveis (como IDs)

Exemplo em Cliente.java:
javaprivate final Long id;
private String nome;
private String email;

public void atualizarDados(String nome, String email) throws ValidacaoException {
    validarEAtribuir(nome, email);
}
Herança

Exceções customizadas herdam de Exception
ValidacaoException e PedidoException estendem funcionalidades base
Permite tratamento específico de diferentes tipos de erros

Polimorfismo

Sobrescrita de métodos: toString(), equals(), hashCode() em todas as entidades
Polimorfismo de exceções: Tratamento genérico e específico de erros
Interface Runnable implementada por ProcessadorPedidos

Exemplo em Pedido.java:
java@Override
public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(String.format("Pedido #%d - %s\n", id, status));
    // ...
    return sb.toString();
}
Interfaces e Classes Abstratas

Interface Runnable para processamento assíncrono
Uso implícito de interfaces do Java Collections Framework
Estrutura preparada para futuras abstrações

Composição

Pedido composto por lista de ItemPedido
ItemPedido possui referência a Produto
Pedido possui referência a Cliente
Relacionamentos modelados através de composição ao invés de herança


Princípios SOLID
S - Single Responsibility Principle (Princípio da Responsabilidade Única)
Cada classe possui uma única responsabilidade bem definida:

Model: Representam entidades de domínio
Repository: Gerenciam persistência de dados
Service: Implementam regras de negócio
Util: Fornecem funcionalidades auxiliares
ProcessadorPedidos: Exclusivamente processa pedidos em background

O - Open/Closed Principle (Princípio Aberto/Fechado)

Classes abertas para extensão através de herança
Fechadas para modificação através de validações encapsuladas
Uso de ENUMs permite adicionar novos valores sem modificar código existente
Sistema de exceções permite adicionar novas validações sem alterar fluxo principal

L - Liskov Substitution Principle (Princípio da Substituição de Liskov)

Subclasses de Exception podem ser substituídas pela classe base
Tratamento polimórfico de exceções mantém comportamento esperado
Objetos podem ser substituídos por suas abstrações

I - Interface Segregation Principle (Princípio da Segregação de Interface)

Interfaces específicas e focadas (ex: Runnable para processamento)
Repositories possuem métodos específicos para suas entidades
Nenhuma classe é forçada a implementar métodos desnecessários

D - Dependency Inversion Principle (Princípio da Inversão de Dependência)

Services dependem de Repositories (abstrações de dados)
PedidoService recebe ClienteService e ProdutoService via construtor
ProcessadorPedidos recebe PedidoService como dependência
Facilita testes e manutenção

Exemplo:
javapublic class PedidoService {
    private final PedidoRepository repository;
    private final ClienteService clienteService;
    private final ProdutoService produtoService;

    public PedidoService(PedidoRepository repository, 
                        ClienteService clienteService, 
                        ProdutoService produtoService) {
        this.repository = repository;
        this.clienteService = clienteService;
        this.produtoService = produtoService;
    }
}

Object Calisthenics
1. Métodos Pequenos
Todos os métodos possuem no máximo 15 linhas de código, focando em uma única tarefa:
javaprivate void validarNome(String nome) throws ValidacaoException {
    if (nome == null || nome.trim().isEmpty()) {
        throw new ValidacaoException("Nome do cliente não pode ser vazio");
    }
    if (nome.trim().length() < 3) {
        throw new ValidacaoException("Nome deve ter pelo menos 3 caracteres");
    }
}
2. Classes Coesas
Cada classe possui uma única responsabilidade:

ValidadorEmail: Apenas valida e-mails
GeradorId: Apenas gera IDs únicos
ItemPedido: Apenas representa um item com quantidade

3. Encapsulamento Adequado

Nenhum atributo público
Getters apenas quando necessário
Sem setters triviais (atualização através de métodos com validação)
Uso de Collections.unmodifiableList() para proteger listas internas

Exemplo em Pedido.java:
javapublic List<ItemPedido> getItens() {
    return Collections.unmodifiableList(itens);
}
4. Nomes Significativos
Variáveis, métodos e classes com nomes autoexplicativos:

validarEmailUnico() ao invés de checkEmail()
calcularValorTotal() ao invés de getTotal()
finalizarParaFila() ao invés de finish()
ProcessadorPedidos ao invés de OrderProcessor

5. Evitar Retornos Nulos
Uso de Optional<T> nos repositories para evitar NullPointerException:
javapublic Optional<Cliente> buscarPorId(Long id) {
    return Optional.ofNullable(clientes.get(id));
}
6. Coleções como Tipos de Primeira Classe
Uso apropriado de estruturas de dados:

Map<Long, Cliente> para busca eficiente por ID
List<ItemPedido> para manter ordem dos itens
BlockingQueue<Pedido> para fila thread-safe


Tratamento de Concorrência
Problema Identificado
Múltiplas threads poderiam acessar simultaneamente:

Fila de pedidos
Repositório de pedidos
Status dos pedidos

Soluções Implementadas
1. BlockingQueue para Fila de Processamento
javaprivate final BlockingQueue<Pedido> fila;

public ProcessadorPedidos(PedidoService pedidoService, int tempoProcessamentoSegundos) {
    this.fila = new LinkedBlockingQueue<>();
    // ...
}
Vantagem: Thread-safe por padrão, bloqueia automaticamente quando vazia.
2. AtomicLong para Geração de IDs
javapublic class GeradorId {
    private static final AtomicLong contadorCliente = new AtomicLong(1);
    
    public static Long gerarIdCliente() {
        return contadorCliente.getAndIncrement();
    }
}
Vantagem: Operações atômicas garantem IDs únicos sem sincronização explícita.
3. Métodos Sincronizados no Repository
javapublic synchronized void salvar(Pedido pedido) {
    pedidos.put(pedido.getId(), pedido);
}

public synchronized Optional<Pedido> buscarPorId(Long id) {
    return Optional.ofNullable(pedidos.get(id));
}
Vantagem: Previne condições de corrida ao acessar o HashMap de pedidos.
4. Flag Volatile para Controle de Thread
javaprivate volatile boolean executando;

public void parar() {
    executando = false;
}
Vantagem: Garante visibilidade da mudança de estado entre threads.
5. Interrupção Segura de Thread
javatry {
    Pedido pedido = fila.take();
    processar(pedido);
} catch (InterruptedException e) {
    if (executando) {
        System.err.println("[PROCESSADOR] Interrompido");
    }
    Thread.currentThread().interrupt();
    break;
}
Vantagem: Permite encerramento gracioso do processador.
Fluxo de Processamento Assíncrono

Usuário finaliza pedido (status: ABERTO → FILA)
Pedido é adicionado à BlockingQueue
Thread processadora retira pedido da fila (bloqueante)
Status atualizado para PROCESSANDO (sincronizado)
Simula processamento com Thread.sleep()
Status atualizado para FINALIZADO (sincronizado)
Thread aguarda próximo pedido

Benefícios da Implementação

Sistema continua responsivo durante processamento
Sem perda de dados por condições de corrida
Encerramento seguro sem corrupção de estado
Fila gerencia automaticamente múltiplos pedidos


Equipe de Desenvolvimento
Arthur
Função: Desenvolvedor Backend - Camada de Modelo e Exceções
Responsabilidades:

Implementação das entidades de domínio (Cliente, Produto, Pedido, ItemPedido)
Criação das exceções customizadas (ValidacaoException, PedidoException)
Definição dos ENUMs (CategoriaProduto, StatusPedido)
Implementação de validações nas entidades
Desenvolvimento dos métodos de cálculo (subtotal, valor total)

André
Função: Desenvolvedor Backend - Camada de Persistência e Serviços
Responsabilidades:

Implementação dos Repositories (ClienteRepository, ProdutoRepository, PedidoRepository)
Desenvolvimento das classes de Service (ClienteService, ProdutoService, PedidoService)
Implementação da lógica de negócio
Integração entre camadas de serviço e persistência
Aplicação dos princípios SOLID na arquitetura

Luiz
Função: Desenvolvedor Backend - Processamento Assíncrono e Interface
Responsabilidades:

Implementação do ProcessadorPedidos e gerenciamento de threads
Desenvolvimento da interface console (Main.java)
Criação dos utilitários (ValidadorEmail, GeradorId)
Implementação do tratamento de concorrência
Testes de integração e validação do fluxo completo
Documentação técnica (README)


