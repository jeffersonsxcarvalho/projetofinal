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

    // Buscar Item por
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

    // Buscar Item por
    public List<ItemCarrinho> buscarPorCpf(String cpfCliente) {
        List<ItemCarrinho> itensCarrinho = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                ItemCarrinho itemCarrinho = model.ItemCarrinho.fromCSV(linha);
                if (itemCarrinho.getCpfCliente().equals(cpfCliente)) {
                    itensCarrinho.add(ItemCarrinho.fromCSV(linha));
                }

            }
        } catch (IOException e) {
            System.out.println("Nenhum item encontrado ainda.");
        }
        return itensCarrinho;
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
                if (itemCarrinho.getNomeProduto().equals(atualizado.getNomeProduto())
                        && itemCarrinho.getCpfCliente().equals(atualizado.getCpfCliente())) {
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

    public void removerItem(String cpfCliente, String nomeProduto) {
        List<String> linhas = new ArrayList<>();
        boolean removido = false;

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(",");
                if (partes.length < 4) continue;

                String cpf = partes[0];
                String produto = partes[1];

                // Se for o item que queremos remover, não adiciona à lista
                if (cpf.equals(cpfCliente) && produto.equalsIgnoreCase(nomeProduto)) {
                    removido = true;
                    continue;
                }

                linhas.add(linha);
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
            return;
        }

        // Reescreve o arquivo sem a linha removida
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo))) {
            for (String l : linhas) {
                bw.write(l);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar o arquivo: " + e.getMessage());
            return;
        }

        if (removido) {
            System.out.println("Item removido com sucesso!");
        } else {
            System.out.println("Item não encontrado para o cliente informado.");
        }
    }
}
