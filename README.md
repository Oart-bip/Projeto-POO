# Sistema de Gestão de Produtos
Sistema de gestão de produtos, clientes e pedidos com processamento assíncrono desenvolvido em Java.

-------------------------------------------------------------

# Instruções para Compilar e Executar

- Execute a classe Main.java  

-------------------------------------------------------------

# Fundamentos de POO Aplicados

-------------------------------------------------------------

# Classes e Objetos
- Classes de domínio: Cliente, Produto, Pedido, ItemPedido  
- Classes de serviço: ClienteService, ProdutoService, PedidoService  
- Classes utilitárias: ValidadorEmail, GeradorId  
- Repositories para persistência: ClienteRepository, ProdutoRepository, PedidoRepository  

-------------------------------------------------------------

# Encapsulamento
- Atributos privados em todas as classes  
- Acesso controlado através de métodos públicos  
- Validações centralizadas nos construtores  
- Uso de final para atributos imutáveis (IDs)  
- Retorno de coleções imutáveis: Collections.unmodifiableList()  

-------------------------------------------------------------

# Herança
- Exceções customizadas herdam de Exception:  
  - ValidacaoException extends Exception  
  - PedidoException extends Exception  

-------------------------------------------------------------

# Polimorfismo
- Sobrescrita de métodos: toString(), equals(), hashCode() em todas as entidades  
- Interface Runnable implementada por ProcessadorPedidos  
- Tratamento polimórfico de exceções  

-------------------------------------------------------------

# Interfaces
- Runnable em ProcessadorPedidos para processamento assíncrono  
- Uso de interfaces do Java Collections Framework  

-------------------------------------------------------------

# Composição
- Pedido composto por lista de ItemPedido  
- ItemPedido possui referência a Produto  
- Pedido possui referência a Cliente  

-------------------------------------------------------------

# Princípios SOLID

## Single Responsibility Principle
- ClienteService: apenas lógica de clientes  
- ProdutoService: apenas lógica de produtos  
- PedidoService: apenas lógica de pedidos  
- ProcessadorPedidos: apenas processamento assíncrono  
- ValidadorEmail: apenas validação de e-mail  

-------------------------------------------------------------

## Open/Closed Principle
- ENUMs permitem adicionar novos valores sem modificar código  
- Sistema de exceções extensível  
- Classes abertas para extensão, fechadas para modificação  

-------------------------------------------------------------

## Liskov Substitution Principle
- Subclasses de Exception substituíveis pela classe base  
- Tratamento polimórfico mantém comportamento esperado  

-------------------------------------------------------------

## Interface Segregation Principle
- Repositories com métodos específicos para suas entidades  
- Interface Runnable focada em execução de thread  

-------------------------------------------------------------

## Dependency Inversion Principle
- Services recebem dependências via construtor:

public PedidoService(PedidoRepository repository,
ClienteService clienteService,
ProdutoService produtoService)

markdown
Copiar código

- Dependência de abstrações, não de implementações concretas  

-------------------------------------------------------------

# Object Calisthenics

1. Métodos Pequenos  
   - Métodos com no máximo 15 linhas, focados em uma única tarefa.  

2. Classes Coesas  
   - Cada classe possui uma única responsabilidade bem definida.  

3. Encapsulamento Adequado  
   - Sem atributos públicos  
   - Sem setters triviais  
   - Atualização através de métodos com validação  

4. Nomes Significativos  
   - validarEmailUnico() ao invés de checkEmail()  
   - calcularValorTotal() ao invés de getTotal()  
   - finalizarParaFila() ao invés de finish()  

5. Evitar Retornos Nulos  
   - Uso de Optional<T> nos repositories:  

public Optional<Cliente> buscarPorId(Long id) {
return Optional.ofNullable(clientes.get(id));
}

markdown
Copiar código

6. Coleções Apropriadas  
   - Map<Long, T> para busca eficiente por ID  
   - List<ItemPedido> para manter ordem  
   - BlockingQueue<Pedido> para fila thread-safe  

-------------------------------------------------------------

# Tratamento de Concorrência

## BlockingQueue
- Thread-safe por padrão, gerencia fila de pedidos automaticamente:  
private final BlockingQueue<Pedido> fila = new LinkedBlockingQueue<>();

markdown
Copiar código

## AtomicLong
- Geração de IDs únicos sem sincronização explícita:  
private static final AtomicLong contadorCliente = new AtomicLong(1);

public static Long gerarIdCliente() {
return contadorCliente.getAndIncrement();
}

markdown
Copiar código

## Métodos Sincronizados
- Previne condições de corrida no repository:  
public synchronized void salvar(Pedido pedido) {
pedidos.put(pedido.getId(), pedido);
}

csharp
Copiar código

## Flag Volatile
- Garante visibilidade de mudanças entre threads:  
private volatile boolean executando;

markdown
Copiar código

-------------------------------------------------------------

# Fluxo de Processamento
- Pedido finalizado entra na fila (status: FILA)  
- Thread processadora retira pedido (bloqueante)  
- Status atualizado: PROCESSANDO  
- Simula processamento (Thread.sleep)  
- Status atualizado: FINALIZADO  
- Thread aguarda próximo pedido  

-------------------------------------------------------------

# Equipe de Desenvolvimento

## Arthur
- Desenvolvedor Backend - Camada de Modelo e Exceções  
- Entidades de domínio (Cliente, Produto, Pedido, ItemPedido)  
- Exceções customizadas (ValidacaoException, PedidoException)  
- ENUMs (CategoriaProduto, StatusPedido)  
- Validações e métodos de cálculo  

-------------------------------------------------------------

## André
- Desenvolvedor Backend - Camada de Persistência e Serviços  
- Repositories (ClienteRepository, ProdutoRepository, PedidoRepository)  
- Services (ClienteService, ProdutoService, PedidoService)  
- Lógica de negócio e integração entre camadas  
- Aplicação dos princípios SOLID  

-------------------------------------------------------------

## Luiz
- Desenvolvedor Backend - Processamento Assíncrono e Interface  
- ProcessadorPedidos e gerenciamento de threads  
- Interface console (Main.java)  
- Utilitários (ValidadorEmail, GeradorId)  
- Tratamento de concorrência  
- Documentação técnica  

-------------------------------------------------------------
