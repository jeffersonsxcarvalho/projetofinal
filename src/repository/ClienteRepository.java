package repository;

import model.Cliente;
import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class ClienteRepository {

    private final String arquivo = "src/repository/clientes.csv";

    // Salvar novo Cliente
    public void salvar(Cliente Cliente) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo, true))) {
            writer.write(Cliente.toString());
            writer.newLine();
        }
    }

    // Buscar Cliente por 
    public Cliente buscarPorCpf(String cpf) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                Cliente cliente = Cliente.fromCSV(linha);
                if (cliente.getCpf().equals(cpf)) {
                    return cliente;
                }
            }
        }
        return null;
    }

    // Atualizar Cliente existente
    public void atualizar(Cliente atualizado) throws IOException {
        File inputFile = new File(arquivo);
        File tempFile = new File("src/repository/tempC.csv");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String linha;
            while ((linha = reader.readLine()) != null) {
                Cliente cliente = Cliente.fromCSV(linha);
                if (cliente.getCpf().equals(atualizado.getCpf())) {
                    writer.write(atualizado.toString());
                } else {
                    writer.write(linha);
                }
                writer.newLine();
            }
        }

        if (inputFile.delete()) {
            tempFile.renameTo(inputFile);
        }
    }

    // Listar todos os Clientes
    public List<Cliente> listarTodos() throws IOException {
        List<Cliente> clientes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                clientes.add(Cliente.fromCSV(linha));
            }
        }
        return clientes;
    }
}
