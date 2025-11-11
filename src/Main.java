
import enums.CategoriaProduto;
import enums.StatusPedido;
import exception.PedidoException;
import exception.ValidacaoException;
import model.Cliente;
import model.Pedido;
import model.Produto;
import repository.ClienteRepository;
import repository.PedidoRepository;
import repository.ProdutoRepository;
import service.ClienteService;
import service.PedidoService;
import service.ProcessadorPedidos;
import service.ProdutoService;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    
    private static ClienteService clienteService;
    private static ProdutoService produtoService;
    private static PedidoService pedidoService;
    private static ProcessadorPedidos processador;
    private static Thread threadProcessador;
    private static Scanner scanner;

    public static void main(String[] args) {
        inicializar();
        carregarDadosIniciais();
        exibirMenuPrincipal();
        encerrar();
    }

    private static void inicializar() {
        ClienteRepository clienteRepo = new ClienteRepository();
        ProdutoRepository produtoRepo = new ProdutoRepository();
        PedidoRepository pedidoRepo = new PedidoRepository();
        
        clienteService = new ClienteService(clienteRepo);
        produtoService = new ProdutoService(produtoRepo);
        pedidoService = new PedidoService(pedidoRepo, clienteService, produtoService);
        
        processador = new ProcessadorPedidos(pedidoService, 3);
        threadProcessador = new Thread(processador);
        threadProcessador.start();
        
        scanner = new Scanner(System.in);
    }

    private static void carregarDadosIniciais() {
        try {
            clienteService.cadastrar("João Silva", "joao@email.com");
            clienteService.cadastrar("Maria Santos", "maria@email.com");
            
            produtoService.cadastrar("Arroz 5kg", 25.90, CategoriaProduto.ALIMENTOS);
            produtoService.cadastrar("Feijão 1kg", 8.50, CategoriaProduto.ALIMENTOS);
            produtoService.cadastrar("Notebook Dell", 3500.00, CategoriaProduto.ELETRONICOS);
            produtoService.cadastrar("Mouse Sem Fio", 45.00, CategoriaProduto.ELETRONICOS);
            produtoService.cadastrar("Clean Code", 89.90, CategoriaProduto.LIVROS);
            
        } catch (ValidacaoException e) {
            System.err.println("Erro ao carregar dados: " + e.getMessage());
        }
    }

    private static void exibirMenuPrincipal() {
        boolean continuar = true;
        
        while (continuar) {
            imprimirCabecalho();
            System.out.println("1 - Gerenciar Clientes");
            System.out.println("2 - Gerenciar Produtos");
            System.out.println("3 - Gerenciar Pedidos");
            System.out.println("4 - Consultas");
            System.out.println("5 - Status do Sistema");
            System.out.println("0 - Sair");
            System.out.print("\nEscolha uma opção: ");
            
            try {
                int opcao = scanner.nextInt();
                scanner.nextLine();
                
                switch (opcao) {
                    case 1 -> menuClientes();
                    case 2 -> menuProdutos();
                    case 3 -> menuPedidos();
                    case 4 -> menuConsultas();
                    case 5 -> exibirStatusSistema();
                    case 0 -> continuar = false;
                    default -> System.out.println("\n❌ Opção inválida!");
                }
            } catch (InputMismatchException e) {
                System.out.println("\n❌ Digite um número válido!");
                scanner.nextLine();
            }
            
            if (continuar) pausar();
        }
    }

    private static void menuClientes() {
        System.out.println("\n═══ GERENCIAR CLIENTES ═══");
        System.out.println("1 - Cadastrar Cliente");
        System.out.println("2 - Listar Clientes");
        System.out.print("\nEscolha: ");
        
        try {
            int opcao = scanner.nextInt();
            scanner.nextLine();
            
            switch (opcao) {
                case 1 -> cadastrarCliente();
                case 2 -> listarClientes();
                default -> System.out.println("\n❌ Opção inválida!");
            }
        } catch (InputMismatchException e) {
            System.out.println("\n❌ Digite um número válido!");
            scanner.nextLine();
        }
    }

    private static void cadastrarCliente() {
        System.out.println("\n─── Cadastrar Cliente ───");
        
        try {
            System.out.print("Nome: ");
            String nome = scanner.nextLine();
            
            System.out.print("E-mail: ");
            String email = scanner.nextLine();
            
            Cliente cliente = clienteService.cadastrar(nome, email);
            System.out.println("\n✓ Cliente cadastrado com sucesso!");
            System.out.println(cliente);
            
        } catch (ValidacaoException e) {
            System.out.println("\n❌ Erro: " + e.getMessage());
        }
    }

    private static void listarClientes() {
        System.out.println("\n─── Lista de Clientes ───");
        List<Cliente> clientes = clienteService.listarTodos();
        
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }
        
        clientes.forEach(System.out::println);
        System.out.println("\nTotal: " + clientes.size() + " cliente(s)");
    }

    private static void menuProdutos() {
        System.out.println("\n═══ GERENCIAR PRODUTOS ═══");
        System.out.println("1 - Cadastrar Produto");
        System.out.println("2 - Listar Produtos");
        System.out.println("3 - Listar por Categoria");
        System.out.print("\nEscolha: ");
        
        try {
            int opcao = scanner.nextInt();
            scanner.nextLine();
            
            switch (opcao) {
                case 1 -> cadastrarProduto();
                case 2 -> listarProdutos();
                case 3 -> listarProdutosPorCategoria();
                default -> System.out.println("\n❌ Opção inválida!");
            }
        } catch (InputMismatchException e) {
            System.out.println("\n❌ Digite um número válido!");
            scanner.nextLine();
        }
    }

    private static void cadastrarProduto() {
        System.out.println("\n─── Cadastrar Produto ───");
        
        try {
            System.out.print("Nome: ");
            String nome = scanner.nextLine();
            
            System.out.print("Preço: R$ ");
            double preco = scanner.nextDouble();
            scanner.nextLine();
            
            System.out.println("\nCategorias disponíveis:");
            CategoriaProduto[] categorias = CategoriaProduto.values();
            for (int i = 0; i < categorias.length; i++) {
                System.out.println((i + 1) + " - " + categorias[i].getDescricao());
            }
            System.out.print("Escolha a categoria: ");
            int catIndex = scanner.nextInt() - 1;
            scanner.nextLine();
            
            if (catIndex < 0 || catIndex >= categorias.length) {
                System.out.println("\n❌ Categoria inválida!");
                return;
            }
            
            Produto produto = produtoService.cadastrar(nome, preco, categorias[catIndex]);
            System.out.println("\n✓ Produto cadastrado com sucesso!");
            System.out.println(produto);
            
        } catch (ValidacaoException e) {
            System.out.println("\n❌ Erro: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("\n❌ Digite valores válidos!");
            scanner.nextLine();
        }
    }

    private static void listarProdutos() {
        System.out.println("\n─── Lista de Produtos ───");
        List<Produto> produtos = produtoService.listarTodos();
        
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
            return;
        }
        
        produtos.forEach(System.out::println);
        System.out.println("\nTotal: " + produtos.size() + " produto(s)");
    }

    private static void listarProdutosPorCategoria() {
        System.out.println("\n─── Listar por Categoria ───");
        System.out.println("\nCategorias:");
        CategoriaProduto[] categorias = CategoriaProduto.values();
        for (int i = 0; i < categorias.length; i++) {
            System.out.println((i + 1) + " - " + categorias[i].getDescricao());
        }
        
        try {
            System.out.print("\nEscolha a categoria: ");
            int catIndex = scanner.nextInt() - 1;
            scanner.nextLine();
            
            if (catIndex < 0 || catIndex >= categorias.length) {
                System.out.println("\n❌ Categoria inválida!");
                return;
            }
            
            List<Produto> produtos = produtoService.listarPorCategoria(categorias[catIndex]);
            
            if (produtos.isEmpty()) {
                System.out.println("\nNenhum produto nesta categoria.");
                return;
            }
            
            System.out.println("\n─── " + categorias[catIndex].getDescricao() + " ───");
            produtos.forEach(System.out::println);
            System.out.println("\nTotal: " + produtos.size() + " produto(s)");
            
        } catch (InputMismatchException e) {
            System.out.println("\n❌ Digite um número válido!");
            scanner.nextLine();
        }
    }

    private static void menuPedidos() {
        System.out.println("\n═══ GERENCIAR PEDIDOS ═══");
        System.out.println("1 - Criar Novo Pedido");
        System.out.println("2 - Adicionar Item ao Pedido");
        System.out.println("3 - Finalizar e Enviar para Fila");
        System.out.println("4 - Listar Pedidos");
        System.out.print("\nEscolha: ");
        
        try {
            int opcao = scanner.nextInt();
            scanner.nextLine();
            
            switch (opcao) {
                case 1 -> criarPedido();
                case 2 -> adicionarItemPedido();
                case 3 -> finalizarPedido();
                case 4 -> listarPedidos();
                default -> System.out.println("\n❌ Opção inválida!");
            }
        } catch (InputMismatchException e) {
            System.out.println("\n❌ Digite um número válido!");
            scanner.nextLine();
        }
    }

    private static void criarPedido() {
        System.out.println("\n─── Criar Novo Pedido ───");
        
        listarClientes();
        
        try {
            System.out.print("\nID do Cliente: ");
            Long clienteId = scanner.nextLong();
            scanner.nextLine();
            
            Pedido pedido = pedidoService.criarPedido(clienteId);
            System.out.println("\n✓ Pedido criado com sucesso!");
            System.out.println("Pedido #" + pedido.getId() + " - Status: " + pedido.getStatus());
            System.out.println("\nAgora adicione itens ao pedido (opção 2 do menu).");
            
        } catch (ValidacaoException | PedidoException e) {
            System.out.println("\n❌ Erro: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("\n❌ Digite um ID válido!");
            scanner.nextLine();
        }
    }

    private static void adicionarItemPedido() {
        System.out.println("\n─── Adicionar Item ao Pedido ───");
        
        List<Pedido> pedidosAbertos = pedidoService.listarPorStatus(StatusPedido.ABERTO);
        
        if (pedidosAbertos.isEmpty()) {
            System.out.println("Nenhum pedido aberto. Crie um pedido primeiro.");
            return;
        }
        
        System.out.println("\nPedidos Abertos:");
        pedidosAbertos.forEach(p -> System.out.println("Pedido #" + p.getId() + " - " + p.getCliente().getNome()));
        
        try {
            System.out.print("\nID do Pedido: ");
            Long pedidoId = scanner.nextLong();
            scanner.nextLine();
            
            listarProdutos();
            
            System.out.print("\nID do Produto: ");
            Long produtoId = scanner.nextLong();
            
            System.out.print("Quantidade: ");
            int quantidade = scanner.nextInt();
            scanner.nextLine();
            
            pedidoService.adicionarItem(pedidoId, produtoId, quantidade);
            
            Pedido pedido = pedidoService.buscarPorId(pedidoId);
            System.out.println("\n✓ Item adicionado com sucesso!");
            System.out.println("Valor atual do pedido: R$ " + String.format("%.2f", pedido.calcularValorTotal()));
            
        } catch (ValidacaoException | PedidoException e) {
            System.out.println("\n❌ Erro: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("\n❌ Digite valores válidos!");
            scanner.nextLine();
        }
    }

    private static void finalizarPedido() {
        System.out.println("\n─── Finalizar Pedido ───");
        
        List<Pedido> pedidosAbertos = pedidoService.listarPorStatus(StatusPedido.ABERTO);
        
        if (pedidosAbertos.isEmpty()) {
            System.out.println("Nenhum pedido aberto para finalizar.");
            return;
        }
        
        System.out.println("\nPedidos Abertos:");
        pedidosAbertos.forEach(p -> {
            System.out.println("\n" + p);
            System.out.println("─".repeat(50));
        });
        
        try {
            System.out.print("\nID do Pedido para finalizar: ");
            Long pedidoId = scanner.nextLong();
            scanner.nextLine();
            
            Pedido pedido = pedidoService.buscarPorId(pedidoId);
            pedidoService.finalizarPedido(pedidoId);
            
            System.out.println("\n✓ Pedido finalizado e enviado para a fila!");
            System.out.println("O pedido será processado automaticamente em background.");
            
            processador.adicionarNaFila(pedido);
            
        } catch (ValidacaoException | PedidoException e) {
            System.out.println("\n❌ Erro: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("\n❌ Digite um ID válido!");
            scanner.nextLine();
        }
    }

    private static void listarPedidos() {
        System.out.println("\n─── Lista de Pedidos ───");
        List<Pedido> pedidos = pedidoService.listarTodos();
        
        if (pedidos.isEmpty()) {
            System.out.println("Nenhum pedido cadastrado.");
            return;
        }
        
        pedidos.forEach(p -> {
            System.out.println("\n" + p);
            System.out.println("─".repeat(50));
        });
        
        System.out.println("\nTotal: " + pedidos.size() + " pedido(s)");
    }

    private static void menuConsultas() {
        System.out.println("\n═══ CONSULTAS ═══");
        System.out.println("1 - Pedidos por Status");
        System.out.println("2 - Pedidos por Cliente");
        System.out.println("3 - Estatísticas Gerais");
        System.out.print("\nEscolha: ");
        
        try {
            int opcao = scanner.nextInt();
            scanner.nextLine();
            
            switch (opcao) {
                case 1 -> consultarPedidosPorStatus();
                case 2 -> consultarPedidosPorCliente();
                case 3 -> exibirEstatisticas();
                default -> System.out.println("\n❌ Opção inválida!");
            }
        } catch (InputMismatchException e) {
            System.out.println("\n❌ Digite um número válido!");
            scanner.nextLine();
        }
    }

    private static void consultarPedidosPorStatus() {
        System.out.println("\n─── Pedidos por Status ───");
        System.out.println("\nStatus:");
        StatusPedido[] status = StatusPedido.values();
        for (int i = 0; i < status.length; i++) {
            System.out.println((i + 1) + " - " + status[i].getDescricao());
        }
        
        try {
            System.out.print("\nEscolha o status: ");
            int statusIndex = scanner.nextInt() - 1;
            scanner.nextLine();
            
            if (statusIndex < 0 || statusIndex >= status.length) {
                System.out.println("\n❌ Status inválido!");
                return;
            }
            
            List<Pedido> pedidos = pedidoService.listarPorStatus(status[statusIndex]);
            
            if (pedidos.isEmpty()) {
                System.out.println("\nNenhum pedido com status: " + status[statusIndex]);
                return;
            }
            
            System.out.println("\n─── Pedidos " + status[statusIndex].getDescricao() + " ───");
            pedidos.forEach(p -> {
                System.out.println("\n" + p);
                System.out.println("─".repeat(50));
            });
            System.out.println("\nTotal: " + pedidos.size() + " pedido(s)");
            
        } catch (InputMismatchException e) {
            System.out.println("\n❌ Digite um número válido!");
            scanner.nextLine();
        }
    }

    private static void consultarPedidosPorCliente() {
        System.out.println("\n─── Pedidos por Cliente ───");
        
        listarClientes();
        
        try {
            System.out.print("\nID do Cliente: ");
            Long clienteId = scanner.nextLong();
            scanner.nextLine();
            
            Cliente cliente = clienteService.buscarPorId(clienteId);
            List<Pedido> pedidos = pedidoService.listarPorCliente(clienteId);
            
            if (pedidos.isEmpty()) {
                System.out.println("\nNenhum pedido para o cliente: " + cliente.getNome());
                return;
            }
            
            System.out.println("\n─── Pedidos de " + cliente.getNome() + " ───");
            pedidos.forEach(p -> {
                System.out.println("\n" + p);
                System.out.println("─".repeat(50));
            });
            System.out.println("\nTotal: " + pedidos.size() + " pedido(s)");
            
        } catch (ValidacaoException e) {
            System.out.println("\n❌ Erro: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("\n❌ Digite um ID válido!");
            scanner.nextLine();
        }
    }

    private static void exibirEstatisticas() {
        System.out.println("\n═══ ESTATÍSTICAS GERAIS ═══");
        System.out.println("Clientes cadastrados: " + clienteService.contarTotal());
        System.out.println("Produtos cadastrados: " + produtoService.contarTotal());
        System.out.println("Total de pedidos: " + pedidoService.contarTotal());
        
        System.out.println("\n─── Pedidos por Status ───");
        for (StatusPedido status : StatusPedido.values()) {
            int count = pedidoService.contarPorStatus(status);
            if (count > 0) {
                System.out.println(status.getDescricao() + ": " + count);
            }
        }
    }

    private static void exibirStatusSistema() {
        System.out.println("\n═══ STATUS DO SISTEMA ═══");
        System.out.println("Processador: " + (processador.estaExecutando() ? "✓ Ativo" : "✗ Inativo"));
        System.out.println("Pedidos na fila: " + processador.tamanhoDaFila());
        System.out.println("Pedidos processando: " + pedidoService.contarPorStatus(StatusPedido.PROCESSANDO));
        System.out.println("Pedidos finalizados: " + pedidoService.contarPorStatus(StatusPedido.FINALIZADO));
    }

    private static void imprimirCabecalho() {
        System.out.println("\n" + "═".repeat(50));
        System.out.println("     SISTEMA DE GESTÃO DE PRODUTOS");
        System.out.println("═".repeat(50));
    }

    private static void pausar() {
        System.out.print("\nPressione ENTER para continuar...");
        scanner.nextLine();
    }

    private static void encerrar() {
        System.out.println("\n═══ ENCERRANDO SISTEMA ═══");
        processador.parar();
        
        try {
            threadProcessador.join(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        scanner.close();
        System.out.println("✓ Sistema encerrado com sucesso!");
    }
}