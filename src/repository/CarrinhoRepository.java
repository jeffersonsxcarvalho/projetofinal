package repository;

import model.Carrinho;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CarrinhoRepository {
    private final String arquivo = "src/repository/carrinhos.csv";

    public void salvarCarrinho(String linhaCsv) throws IOException {
        try (FileWriter fw = new FileWriter(arquivo, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(linhaCsv);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Erro ao salvar carrinho: " + e.getMessage());
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

