package service;

import model.Cliente;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class CriarClienteCsv {

    static Path path = Paths.get("repository/clientes.csv");

    public static void main(String[] args) throws IOException {
        ClienteProcessadorCsv.addToCsv(path, Cadastro.getClientes());
        System.out.println("Arquivo gravado em:" + path.toAbsolutePath());

        //readToCsv
        List<Cliente> resultado = ClienteProcessadorCsv.readFromCsv(path);

        if (resultado.isEmpty()) {
            System.out.println("Nenhum cliente encontrado!");
            try (Stream<String> lines = Files.lines(path)) {
                lines.forEach(System.out::println);
            }
        } else {
            resultado.forEach(System.out::println);

            /*resultado.stream().filter(c -> c.getCpf()).
                    forEach(c -> System.out.println("Lista de Clientes: " + c));*/
        }

        System.out.println();
        editarClienteCsv("Reunião");
    }


    private static Cliente buscarClientePorCpf(String cpf) throws IOException {
        List<Cliente> listaCliente = ClienteProcessadorCsv.readFromCsv(path);
        for (Cliente cliente : listaCliente) {
            if (cpf.equals(cliente.getCpf())) {
                return cliente;
            }
        }
        return null;
    }

    private static Optional<Cliente> buscarCliente(Path path, String cpf) throws IOException {
        List<Cliente> listaCliente = ClienteProcessadorCsv.readFromCsv(path);
        return listaCliente.stream()
                .filter(c -> c.getCpf().contains(cpf))
                .findFirst();
    }

    public static void editarClienteCsv(String cpf) throws IOException {
        List<Cliente> listaClientes = ClienteProcessadorCsv.readFromCsv(path);
        Optional<Cliente> ClienteOptional = buscarCliente(path, cpf);
        if (ClienteOptional.isPresent()) {
            Cliente ClienteEncontrado = ClienteOptional.get();

            List<Cliente> novaListaSemClienteBuscado = new ArrayList<>();
            for (Cliente Cliente : listaClientes) {
                if (!Cliente.equals(ClienteEncontrado)) {
                    novaListaSemClienteBuscado.add(Cliente);
                }
            }

            Cliente ClienteAtualizado = atualizarCliente(ClienteEncontrado, null, null, null);
            novaListaSemClienteBuscado.add(ClienteAtualizado);
            ClienteProcessadorCsv.writeToCsv(path, novaListaSemClienteBuscado);
            System.err.println("Cliente atualizado com sucesso!");
            System.out.println(ClienteProcessadorCsv.readFromCsv(path));
        }
    }

    private static Cliente atualizarCliente(Cliente ClienteAtual,
                                                    String novoNome,
                                                    String novoCpf,
                                                    String novoEmail
                                                    ) {

        return new Cliente(
                novoCpf != null ? novoCpf : ClienteAtual.getCpf(),
                novoNome != null ? novoNome : ClienteAtual.getNome(),
                novoEmail != null ? novoEmail : ClienteAtual.getEmail()
        );
    }
}
