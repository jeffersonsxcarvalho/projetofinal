package service;

import model.*;
import repository.CarrinhoRepository;
import repository.ClienteRepository;
import repository.ItemCarrinhoRepository;
import repository.ProdutoRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Cadastro {
    private static List<Cliente> clientes = new ArrayList<>();
    private List<Produto> produtos = new ArrayList<>();
    private List<Carrinho> carrinhos = new ArrayList<>();

    ClienteRepository repoC = new ClienteRepository();
    ProdutoRepository repoP = new ProdutoRepository();
    ProdutoRepository produtoRepo = new ProdutoRepository();
    CarrinhoRepository repoCar = new CarrinhoRepository();
    ItemCarrinhoRepository repoI = new ItemCarrinhoRepository();

    public void cadastrarCliente(String nome, String cpf, String email) {

        try{
            repoC.salvar(new Cliente(nome, cpf, email));
            System.out.println("Cliente cadastrado com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao acessar arquivo: " + e.getMessage());
        }
    }

    public void listarClientes() {
        clientes.forEach(System.out::println);

        try{
            // Listar todos
            System.out.println("\nLista de Clientes:");
            for (Cliente cliente : repoC.listarTodos()) {
                System.out.println("Cliente: " + cliente.getNome() + " | CPF: " + cliente.getCpf() + " | Email:" + cliente.getEmail());
            }
        }
        catch (IOException e) {
            System.out.println("Erro ao acessar arquivo: " + e.getMessage());
        }
    }

    public void atualizarCliente(String cpfCliente, String novoNome, String novoEmail) {

        try{

            // Buscar por CPF
            Cliente cliente = repoC.buscarPorCpf(cpfCliente);
            if (cliente != null) {
                System.out.println("Cliente: " + cliente.getNome() + " | Email: " + cliente.getEmail());

                // Atualizar produto
                cliente.setNome(novoNome);
                cliente.setEmail(novoEmail);
                repoC.atualizar(cliente);

                System.out.println("Cliente atualizado com sucesso.");
            }else{
                System.out.println("Cliente com CPF " + cpfCliente + " não encontrado.");
            }
        } catch (IOException e) {
            System.out.println("Erro ao acessar arquivo: " + e.getMessage());
        }
    }

    public void cadastrarProduto(String nome, int quantidadeEstoque, double valorCompra) {
        produtos.add(new Produto(nome, quantidadeEstoque, valorCompra));

        //Cadastro de Produtos
        try{
            repoP.salvar(new Produto(nome, quantidadeEstoque, valorCompra));
            System.out.println("Produto cadastrado com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao acessar arquivo: " + e.getMessage());
        }
    }

    public void listarProdutos() {
        produtos.forEach(System.out::println);

        try{
            // Listar todos
            System.out.println("\nLista de Produtos:");
            for (Produto produto : repoP.listarTodos()) {
                System.out.println("Produto: " + produto.getNome() + " | Quantidade em Estoque: " + produto.getQuantidadeEstoque() + " | Valor Unitário Compra: R$" +  String.format("%.2f", produto.getValorCompra()) + " | Valor Total Estoque: R$" + String.format("%.2f", produto.getValorTotalCompraEstoque()));
            }
        }
        catch (IOException e) {
            System.out.println("Erro ao acessar arquivo: " + e.getMessage());
        }
    }

    public void atualizarProduto(String nome, int novaQuantidade, double novoValorCompra) {

        try{

            // Buscar por CPF
            Produto produto = repoP.buscarPorNome(nome);
            if (produto != null) {
                System.out.println("Produto: " + produto.getNome() + " | Quantidade em Estoque: " + produto.getQuantidadeEstoque() + " | Valor Unitário Compra: R$" +  String.format("%.2f", produto.getValorCompra()) + " | Valor Total Estoque: R$" + String.format("%.2f", produto.getValorTotalCompraEstoque()));

                // Atualizar produto
                produto.setQuantidadeEstoque(novaQuantidade);
                produto.setValorCompra(novoValorCompra);
                repoP.atualizar(produto);

                System.out.println("Produto atualizado com sucesso.");
            }
        } catch (IOException e) {
            System.out.println("Erro ao acessar arquivo: " + e.getMessage());
        }
    }

    public double calcularValorTotalEstoque(int a) {

        try{
            List<Produto> produtos = repoP.listarTodos();

            if(a==1){
                return produtos.stream()
                        .mapToDouble(Produto::getValorTotalCompraEstoque)
                        .sum();
            }else if(a==2){
                return produtos.stream()
                        .mapToDouble(Produto::getValorTotalVendaEstoque)
                        .sum();
            }

        } catch (IOException e) {
            System.out.println("Erro ao acessar arquivo: " + e.getMessage());
        }

        return 0.0;
    }



    //Relacionado a carrinhos

    public void cadastrarCarrinho(String cpfCliente, Cadastro cadastro) {

        //Cadastro de Produtos
        try{
            Cliente cliente = repoC.buscarPorCpf(cpfCliente);
            //if adicionado
            if (repoCar.buscarPorCpf(cpfCliente) != null){
                System.out.println("Pedido já existe, digite 10 para atualizar o pedido.");
                return;
            }else{
                Scanner scanner = new Scanner(System.in);
                int opcao;
                int itensAdicionados = 0;

                do {
                    System.out.println("=====MENU CARRINHO DE " + cliente.getNome().toUpperCase() + "=====");
                    System.out.println("1. Cadastrar item no carrinho");
                    System.out.println("0. Finalizar");

                    System.out.println("Entre com uma das opções: ");
                    opcao = scanner.nextInt();
                    scanner.nextLine();

                    switch (opcao) {
                        case 1 -> {
                            System.out.print("Nome do produto: ");
                            String nomeProduto = scanner.nextLine();
                            System.out.print("Quantidade de compra: ");
                            int quantidade = scanner.nextInt();
                            System.out.print("Valor de compra: ");
                            double preco = scanner.nextDouble();
                            scanner.nextLine();// Limpa o buffer
                            if(cadastro.adicionarItem(cpfCliente, nomeProduto, quantidade, preco)){
                                itensAdicionados++;
                            }
                        }

                    }
                } while (opcao != 0);

                    if(itensAdicionados > 0) {
                        repoCar.salvar(new Carrinho(cpfCliente));
                        System.out.println("Pedido cadastrado com sucesso.");
                    }else {
                        System.out.println("Pedido não cadastrado. Pois não foram adicionados itens.");
                    }


            }
        } catch (IOException e) {
            System.out.println("Erro ao acessar arquivo: " + e.getMessage());
        }
    }

    public void listarCarrinhos() {

        try{
            // Listar todos
            System.out.println("\nLista de Carrinhos:");
            for (Carrinho carrinho : repoCar.listarTodos()) {
                System.out.println("CPF do Cliente: " + carrinho.getCpfCliente() + " | Valor total: " + carrinho.getValorTotal() +  " | Status: " + carrinho.getStatus() + " | Cadastrado em: " + carrinho.getDataCriacao() + " | Modificado em: " + carrinho.getDataAtualizacao());
            }
        }
        catch (IOException e) {
            System.out.println("Erro ao acessar arquivo: " + e.getMessage());
        }
    }

    public void atualizarCarrinho(String cpfCliente, Cadastro cadastro) {

        menuItens(cpfCliente, cadastro);

        try{

            // Buscar carrinho por CPF
            Carrinho carrinho = repoCar.buscarPorCpf(cpfCliente);
            if (carrinho != null) {
                System.out.println("Carrinho CPF: " + carrinho.getCpfCliente() + " | Status: " + carrinho.getStatus() + " | Valor total: R$" +  String.format("%.2f", carrinho.getValorTotal()));

                // Atualizar produto
                carrinho.setValorTotal(valorTotalCarrinho(cpfCliente));
                carrinho.atualizarDatas();
                repoCar.atualizar(carrinho);

                System.out.println("Pedido atualizado com sucesso.");
            }
        } catch (IOException e) {
            System.out.println("Erro ao acessar arquivo: " + e.getMessage());
        }
    }

    public double valorTotalCarrinho(String cpfCliente) {

        double valorTotalCarrinho = 0.0;

        List<ItemCarrinho> itensCarrinho= repoI.buscarPorCpf(cpfCliente);

        for (ItemCarrinho itemCarrinho : itensCarrinho) {
            valorTotalCarrinho += itemCarrinho.calcularTotal();
        }

        return valorTotalCarrinho;
    }

    // Relacionado a itens do carrinho

    public boolean adicionarItem(String cpfCliente, String nomeProduto, int quantidade, double preco) {

        try {

            Produto produto = produtoRepo.buscarPorNome(nomeProduto);
            // Buscar carrinho por CPF

            Carrinho carrinho = repoCar.buscarPorCpf(cpfCliente);

            ItemCarrinho itemCarrinho = repoI.buscarPorCpfENome(cpfCliente, nomeProduto);

            if (produto == null) {
                System.out.println("Produto não encontrado.");
                return false;
            }

            if (quantidade > produto.getQuantidadeEstoque()) {
                System.out.println("Estoque insuficiente! Disponível: " + produto.getQuantidadeEstoque());
                return false;
            }

            if(itemCarrinho != null) {
                itemCarrinho.setQuantidade(itemCarrinho.getQuantidade() + quantidade);
                repoI.atualizar(itemCarrinho);
                System.out.println("Item atualizado com sucesso.");
            }else{
                repoI.salvar(new ItemCarrinho(cpfCliente, nomeProduto, quantidade, preco));
                System.out.println("Item cadastrado com sucesso.");
            }



            atualizarProduto(nomeProduto, produto.getQuantidadeEstoque()-quantidade,produto.getValorCompra());


            if(carrinho != null) {
                carrinho.atualizarDatas();
            }

        } catch (IOException e) {
            System.out.println("Erro ao acessar arquivo: " + e.getMessage());
        }
        return true;
    }

    public void listarItens(String cpfClinte) {

            // Listar todos os itens dos carrinhos
            System.out.println("\nLista de itens no carrinho:");
            for (ItemCarrinho itemCarrinho : repoI.listarTodos()) {
                if (cpfClinte.equals(itemCarrinho.getCpfCliente())){
                    System.out.println("CPF do Cliente: " + itemCarrinho.getCpfCliente()
                            + " | Nome do Produto: " + itemCarrinho.getNomeProduto()
                            + " | Quantidade deste item: " + itemCarrinho.getQuantidade()
                            + " | Preço: R$"
                            +  String.format("%.2f", itemCarrinho.getPreco()));
                }
            }

    }

    public void atualizarItem(String cpfCliente, String nomeProduto, int novaQuantidade, double novoPreco) {

        try{
            // Buscar Produto para comparar estoque.
            Produto produto = produtoRepo.buscarPorNome(nomeProduto);

            //Carrinho
            Carrinho carrinho = repoCar.buscarPorCpf(cpfCliente);

            // Buscar por CPF
            ItemCarrinho itemCarrinho = repoI.buscarPorCpfENome(cpfCliente, nomeProduto);
            if (itemCarrinho != null) {

                int difQuantidade = novaQuantidade - itemCarrinho.getQuantidade();

                // Atualizar produto

                if(novaQuantidade > 0) {
                    if(difQuantidade <= 0) {
                        itemCarrinho.setQuantidade(novaQuantidade);
                        itemCarrinho.setPreco(novoPreco);
                        System.out.println("CPF do Cliente: " + itemCarrinho.getCpfCliente() + " | Nome do Produto: " + itemCarrinho.getNomeProduto() + " | Nova quantidade deste item: " + itemCarrinho.getQuantidade() + " | Preço: R$" +  String.format("%.2f", itemCarrinho.getPreco()));

                        atualizarProduto(nomeProduto,produto.getQuantidadeEstoque()-difQuantidade, produto.getValorCompra());
                        carrinho.setValorTotal(valorTotalCarrinho(cpfCliente));
                    }else if (novaQuantidade > produto.getQuantidadeEstoque()) {
                        System.out.println("Estoque insuficiente! Disponível: " + produto.getQuantidadeEstoque());
                        return;
                    }
                }else if(novaQuantidade <= 0) {
                    System.out.println("A nova quantidade deve ser um numero positivo.");
                    return;
                }

                repoI.atualizar(itemCarrinho);

                System.out.println("Item atualizado com sucesso.");
            } else {
                System.out.println("Item não encontrado no carrinho");
                return;
            }

        } catch (IOException e) {
            System.out.println("Erro ao acessar arquivo: " + e.getMessage());
        }
    }

    public void removerItem(String cpfCliente, String nomeProduto) {
        try{
            int qtdeItensCarrinho = repoI.buscarPorCpf(cpfCliente).size();
            int quantidadeItem = repoI.buscarPorCpfENome(cpfCliente, nomeProduto).getQuantidade();

            Carrinho carrinho = repoCar.buscarPorCpf(cpfCliente);

            Produto produto = repoP.buscarPorNome(nomeProduto);

            if (qtdeItensCarrinho > 1){
                repoI.removerItem(cpfCliente, nomeProduto);
                atualizarProduto(nomeProduto, produto.getQuantidadeEstoque()+quantidadeItem, produto.getValorCompra());
                carrinho.setValorTotal(valorTotalCarrinho(cpfCliente));
            } else {
                System.out.println("O carrinho precisa ter pelo menos 1 item cadastrado.");
            }

        } catch (IOException e) {
            System.out.println("Erro ao acessar arquivo: " + e.getMessage());
        }
    }

    public double valorTotalItem(String cpfCliente, String nomeProduto) {

        try{
            double valorTotal = repoI.buscarPorCpfENome(cpfCliente, nomeProduto).calcularTotal();
            return valorTotal;
        } catch (IOException e) {
            System.out.println("Erro ao acessar arquivo " + e.getMessage());
        }
        return 0.0;
    }

    //Menu itens do carrinho

    public void menuItens(String cpfCliente, Cadastro cadastro) {

        try{
            Cliente cliente = repoC.buscarPorCpf(cpfCliente);

            Scanner scanner = new Scanner(System.in);
            int opcao;

            do {
                System.out.println("=====ATUALIZAR PEDIDO DE " + cliente.getNome().toUpperCase() + "=====");
                System.out.println("1. Cadastrar item no carrinho");
                System.out.println("2. Listar itens do carrinho");
                System.out.println("3. Atualizar item do carrinho");
                System.out.println("4. Remover item do carrinho");
                System.out.println("0. Voltar ao menu anterior");

                System.out.println("Entre com uma das opções: ");
                opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1 -> {
                        System.out.print("Nome do produto: ");
                        String nomeProduto = scanner.nextLine();
                        System.out.print("Quantidade de compra: ");
                        int quantidade = scanner.nextInt();
                        System.out.print("Valor de compra: ");
                        double preco = scanner.nextDouble();
                        scanner.nextLine(); // Limpa o buffer
                        cadastro.adicionarItem(cpfCliente, nomeProduto, quantidade, preco);
                    }

                    case 2 -> cadastro.listarItens(cpfCliente);

                    case 3 -> {
                        System.out.print("Nome do produto: ");
                        String nomeProduto = scanner.nextLine();
                        System.out.print("Nova quantidade: ");
                        int novaQuantidade = scanner.nextInt();
                        System.out.print("Novo Preço: ");
                        double novoPreco = scanner.nextDouble();
                        scanner.nextLine(); // Limpa o buffer
                        cadastro.atualizarItem(cpfCliente, nomeProduto, novaQuantidade, novoPreco);
                    }

                    case 4 -> {
                        System.out.print("Nome do produto: ");
                        String nomeProduto = scanner.nextLine();
                        cadastro.removerItem(cpfCliente, nomeProduto);
                    }

                }
            } while (opcao != 0);

        }catch (IOException e) {
            System.out.println("Não foi possível acessar arquivo" + e.getMessage());
        }
    }

    public static List<Cliente> getClientes() { return clientes; }
    public List<Produto> getProdutos() { return produtos; }
}
