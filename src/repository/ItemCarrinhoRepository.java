package repository;

import model.Carrinho;
import model.ItemCarrinho;
import model.Produto;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ItemCarrinhoRepository {
    private final String arquivo = "src/repository/itenscarrinho.csv";

    public void salvar(ItemCarrinho itemCarrinho) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo, true))) {
            writer.write(itemCarrinho.toString());
            writer.newLine();
        }
    }

    // Buscar Produto por
    public ItemCarrinho buscarPorCpfENome(String cpfCliente, String nomeProduto) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                ItemCarrinho itemCarrinho = model.ItemCarrinho.fromCSV(linha);
                if (itemCarrinho.getNomeProduto().equals(nomeProduto) && itemCarrinho.getCpfCliente().equals(cpfCliente)) {
                    return itemCarrinho;
                }
            }
        }
        return null;
    }

    // Atualizar Produto existente
    public void atualizar(ItemCarrinho atualizado) throws IOException {
        File inputFile = new File(arquivo);
        File tempFile = new File("src/repository/tempI.csv");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String linha;
            while ((linha = reader.readLine()) != null) {
                ItemCarrinho itemCarrinho = ItemCarrinho.fromCSV(linha);
                if (itemCarrinho.getNomeProduto().equals(atualizado.getNomeProduto())) {
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

    public List<ItemCarrinho> listarTodos() {
        List<ItemCarrinho> itensCarrinho = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                itensCarrinho.add(ItemCarrinho.fromCSV(linha));
            }
        } catch (IOException e) {
            System.out.println("Nenhum item encontrado ainda.");
        }
        return itensCarrinho;
    }
}
