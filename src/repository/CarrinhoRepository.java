package repository;

import model.Carrinho;
import model.Produto;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CarrinhoRepository {
    private final String arquivo = "src/repository/carrinhos.csv";

    public void salvar(Carrinho carrinho) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo, true))) {
            writer.write(carrinho.toString());
            writer.newLine();
        }
    }

    // Buscar Produto por
    public Carrinho buscarPorCpf(String cpf) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                Carrinho carrinho = Carrinho.fromCSV(linha);
                if (carrinho.getCpfCliente().equals(cpf)) {
                    return carrinho;
                }
            }
        }
        return null;
    }

    // Atualizar Produto existente
    public void atualizar(Carrinho atualizado) throws IOException {
        File inputFile = new File(arquivo);
        File tempFile = new File("src/repository/tempCar.csv");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String linha;
            while ((linha = reader.readLine()) != null) {
                Carrinho carrinho = Carrinho.fromCSV(linha);
                if (carrinho.getCpfCliente().equals(atualizado.getCpfCliente())) {
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

    public List<Carrinho> listarTodos() throws IOException {
        List<Carrinho> carrinhos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                carrinhos.add(Carrinho.fromCSV(linha));
            }
        }
        return carrinhos;
    }
}

