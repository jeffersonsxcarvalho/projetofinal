package model;

import repository.ProdutoRepository;
import java.io.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Carrinho {
    private final String cpfCliente;
    private String dataCriacao;
    private String dataAtualizacao;
    private String status;
    private double valorTotal;

    public Carrinho(String cpfCliente) {
        this.cpfCliente = cpfCliente;
        LocalDateTime agora = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.dataCriacao = agora.format(formatter);
        this.dataAtualizacao = this.dataCriacao;
        this.status = "Aberto";

    }

    public Carrinho(String cpfCliente, String status, String dataCriacao, String dataAtualizacao, double valorTotal) {
        this.cpfCliente = cpfCliente;
        this.status = status;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
        this.valorTotal = valorTotal;

    }

    public String getCpfCliente() {
        return cpfCliente;
    }

    public String getDataCriacao() {
        return dataCriacao;
    }

    /*public int getQuantidadeDiferentes() {
        return itens.size();
    }

    public int getQuantidadeTotal() {
        return itens.stream().mapToInt(ItemCarrinho::getQuantidade).sum();
    }*/

    public String getStatus() {
        return status;
    }


    /*public double getValorTotal() {
        return itens.stream().mapToDouble(i -> i.getQuantidade() * i.getPreco()).sum();
    }*/

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) { this.valorTotal = valorTotal; }

    public void atualizarDatas() {
        LocalDateTime agora = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.dataAtualizacao = agora.format(formatter);
    }

    @Override
    public String toString() {
        return cpfCliente + "," + status + "," + dataCriacao + "," + dataAtualizacao + "," + valorTotal;
    }

    public static Carrinho fromCSV(String linha) {
        String[] campos = linha.split(",");
        return new Carrinho(campos[0].trim(), campos[1].trim(), campos[2].trim(), campos[3].trim(), Double.parseDouble(campos[4].trim()));

    }
}
