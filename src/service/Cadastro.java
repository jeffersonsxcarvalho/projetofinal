package service;

import model.*;
import repository.CarrinhoRepository;
import repository.ClienteRepository;
import repository.ProdutoRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Cadastro {
    private static List<Cliente> clientes = new ArrayList<>();
    private List<Produto> produtos = new ArrayList<>();
    private List<Carrinho> carrinhos = new ArrayList<>();

    ClienteRepository repoC = new ClienteRepository();
    ProdutoRepository repoP = new ProdutoRepository();
    CarrinhoRepository repoCar = new CarrinhoRepository();

    public void cadastrarCliente(String nome, String cpf, String email) {
        clientes.add(new Cliente(nome, cpf, email));

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

    public void atualizarCliente(String cpf, String novoNome, String novoEmail) {
        for (Cliente c : clientes) {
            if (c.getCpf().equals(cpf)) {
                c.setNome(novoNome);
                c.setEmail(novoEmail);
                return;
            }
        }
        System.out.println("Cliente com CPF " + cpf + " não encontrado.");

        try{

            // Buscar por CPF
            Cliente cliente = repoC.buscarPorCpf(cpf);
            if (cliente != null) {
                System.out.println("Cliente: " + cliente.getNome() + " | Email: " + cliente.getEmail());

                // Atualizar produto
                cliente.setNome(novoNome);
                cliente.setEmail(novoEmail);
                repoC.atualizar(cliente);

                System.out.println("Cliente atualizado com sucesso.");
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
                System.out.println("Produto: " + produto.getNome() + " | Quantidade em Estoque: " + produto.getQuantidadeEstoque() + " | Valor Unitário Compra: R$" +  String.format("%.2f", produto.getValorCompra()) + " | Valor Total Estoque: R$" + String.format("%.2f", produto.getValorTotalEstoque()));
            }
        }
        catch (IOException e) {
            System.out.println("Erro ao acessar arquivo: " + e.getMessage());
        }
    }

    public void atualizarProduto(String nome, int novaQuantidade, double novoValorCompra) {
        for (Produto p : produtos) {
            if (p.getNome().equalsIgnoreCase(nome)) {
                p.setQuantidadeEstoque(novaQuantidade);
                p.setValorCompra(novoValorCompra);
                return;
            }
        }
        System.out.println("Produto " + nome + " não encontrado.");

        try{

            // Buscar por CPF
            Produto produto = repoP.buscarPorNome(nome);
            if (produto != null) {
                System.out.println("Produto: " + produto.getNome() + " | Quantidade em Estoque: " + produto.getQuantidadeEstoque() + " | Valor Unitário Compra: R$" +  String.format("%.2f", produto.getValorCompra()) + " | Valor Total Estoque: R$" + String.format("%.2f", produto.getValorTotalEstoque()));

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

    public double calcularValorTotalEstoque() {
        return produtos.stream()
                .mapToDouble(Produto::getValorTotalEstoque)
                .sum();
    }



    //Relacionado a carrinhos

    public void listarCarrinhos() {
        carrinhos.forEach(System.out::println);

        try{
            // Listar todos
            System.out.println("\nLista de Carrinhos:");
            for (Carrinho carrinho : repoCar.listarTodos()) {
                System.out.println("CPF do Cliente: " + carrinho.getCpfCliente() + " | Valor total: " + carrinho.getValorTotal() +  " | Status: " + carrinho.getStatus() + " | Cadastrado em: " + carrinho.getDataCriacao());
            }
        }
        catch (IOException e) {
            System.out.println("Erro ao acessar arquivo: " + e.getMessage());
        }
    }

    // Relacionado a itens do carrinho

    /*public void adicionarItem(String produtoNome, int quantidade, double preco) {

        try {

            Produto produto = produtoRepo.buscarPorNome(produtoNome);
            if (produto == null) {
                System.out.println("Produto não encontrado.");
                return;
            }
            if (quantidade > produto.getQuantidadeEstoque()) {
                System.out.println("Estoque insuficiente! Disponível: " + produto.getQuantidadeEstoque());
                return;
            }
            itens.add(new ItemCarrinho(produtoNome, quantidade, preco));
            atualizarDatas();

        } catch (IOException e) {
            System.out.println("Erro ao acessar arquivo: " + e.getMessage());
        }

    }

    public void alterarQuantidade(String produtoNome, int novaQuantidade) {

        try{

            Produto produto = produtoRepo.buscarPorNome(produtoNome);
            if (produto == null) {
                System.out.println("Produto não encontrado.");
                return;
            }
            if (novaQuantidade > produto.getQuantidadeEstoque()) {
                System.out.println("Estoque insuficiente! Disponível: " + produto.getQuantidadeEstoque());
                return;
            }
            for (ItemCarrinho item : itens) {
                if (item.getProdutoNome().equalsIgnoreCase(produtoNome)) {
                    item.setQuantidade(novaQuantidade);
                }
            }
            atualizarDatas();

        } catch (IOException e) {
            System.out.println("Erro ao acessar arquivo: " + e.getMessage());
        }
    }

    public void removerItem(String produtoNome) {
        itens.removeIf(item -> item.getProdutoNome().equalsIgnoreCase(produtoNome));
        atualizarDatas();
    }*/

    public static List<Cliente> getClientes() { return clientes; }
    public List<Produto> getProdutos() { return produtos; }
}
