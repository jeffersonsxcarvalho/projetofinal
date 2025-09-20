package repository;

import model.Produto;
import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class ProdutoRepository {
    private final String arquivo = "src/repository/produtos.csv";

    // Salvar novo Produto
    public void salvar(Produto produto) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo, true))) {
            writer.write(produto.toString());
            writer.newLine();
        }
    }

    // Buscar Produto por 
    public Produto buscarPorNome(String nome) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                Produto produto = Produto.fromCSV(linha);
                if (produto.getNome().equals(nome)) {
                    return produto;
                }
            }
        }
        return null;
    }

    // Atualizar Produto existente
    public void atualizar(Produto atualizado) throws IOException {
        File inputFile = new File(arquivo);
        File tempFile = new File("src/repository/tempP.csv");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String linha;
            while ((linha = reader.readLine()) != null) {
                Produto produto = Produto.fromCSV(linha);
                if (produto.getNome().equals(atualizado.getNome())) {
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

    // Listar todos os Produtos
    public List<Produto> listarTodos() throws IOException {
        List<Produto> produtos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                produtos.add(Produto.fromCSV(linha));
            }
        }
        return produtos;
    }
}
