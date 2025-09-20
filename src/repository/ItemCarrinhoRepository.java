package repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ItemCarrinhoRepository {
    private final String arquivo = "src/repository/itenscarrinho.csv";

    public void salvarItemCarrinho(String linhaCsv) throws IOException {
        try (FileWriter fw = new FileWriter(arquivo, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(linhaCsv);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Erro ao salvar item: " + e.getMessage());
        }
    }

    public List<String> listarItensCarrinho() {
        List<String> itensCarrinho = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                itensCarrinho.add(linha);
            }
        } catch (IOException e) {
            System.out.println("Nenhum carrinho encontrado ainda.");
        }
        return itensCarrinho;
    }
}
