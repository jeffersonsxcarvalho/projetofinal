package controller;


import service.Cadastro;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Cadastro cadastro = new Cadastro();
        Scanner scanner = new Scanner(System.in);


        int opcao;



        do {
            System.out.println("\n--- MENU ---");
            System.out.println("1.  Cadastrar Cliente");
            System.out.println("2.  Listar Clientes");
            System.out.println("3.  Atualizar Cliente");
            System.out.println("4.  Cadastrar Produto");
            System.out.println("5.  Listar Produtos");
            System.out.println("6.  Atualizar Produto");
            System.out.println("7.  Mostrar Valor Total do Estoque");
            System.out.println("8.  Cadastrar Pedido");
            System.out.println("9.  Listar Pedidos");
            System.out.println("10. Atualizar Pedido");
            System.out.println("0.  Sair");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer

            switch (opcao) {
                case 1 -> {
                    System.out.print("Nome: ");
                    String nome = scanner.nextLine();
                    System.out.print("CPF: ");
                    String cpf = scanner.nextLine();
                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    cadastro.cadastrarCliente(nome, cpf, email);
                }

                case 2 -> cadastro.listarClientes();

                case 3 -> {
                    System.out.print("CPF do cliente: ");
                    String cpf = scanner.nextLine();
                    System.out.print("Novo nome: ");
                    String novoNome = scanner.nextLine();
                    System.out.print("Novo email: ");
                    String novoEmail = scanner.nextLine();
                    cadastro.atualizarCliente(cpf, novoNome, novoEmail);
                }

                case 4 -> {
                    System.out.print("Nome do produto: ");
                    String nome = scanner.nextLine();
                    System.out.print("Quantidade em estoque: ");
                    int quantidade = scanner.nextInt();
                    System.out.print("Valor de compra: ");
                    double valorCompra = scanner.nextDouble();
                    scanner.nextLine(); // Limpa o buffer
                    cadastro.cadastrarProduto(nome, quantidade, valorCompra);
                }

                case 5 -> cadastro.listarProdutos();

                case 6 -> {
                    System.out.print("Nome do produto: ");
                    String nome = scanner.nextLine();
                    System.out.print("Nova quantidade em estoque: ");
                    int novaQuantidade = scanner.nextInt();
                    System.out.print("Novo valor de compra: ");
                    double novoValorCompra = scanner.nextDouble();
                    scanner.nextLine(); // Limpa o buffer
                    cadastro.atualizarProduto(nome, novaQuantidade, novoValorCompra);
                }

                case 7 -> {
                    double totalEstoque = cadastro.calcularValorTotalEstoque();
                    System.out.println("Valor total do estoque: R$" + String.format("%.2f", totalEstoque));
                }

                case 8 -> {
                    System.out.print("CPF do Cliente: ");
                    String cpf = scanner.nextLine();

                    cadastro.cadastrarCarrinho(cpf, cadastro);
                }

                case 9 -> cadastro.listarCarrinhos();

                case 10 -> {

                }

                case 0 -> System.out.println("Até mais!");
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);

        scanner.close();
    }
}


