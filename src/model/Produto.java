package model;

public class Produto {
    private String nome;
    private int quantidadeEstoque;
    private double valorCompra;
    private double valorVenda;

    public Produto(String nome, int quantidadeEstoque, double valorCompra) {
        this.nome = nome;
        this.quantidadeEstoque = quantidadeEstoque;
        this.valorCompra = valorCompra;
        this.valorVenda = calcularValorVenda(valorCompra);
    }

    private double calcularValorVenda(double valorCompra) {
        return valorCompra * 1.20; // margem de lucro de 20%
    }

    public String getNome() { return nome; }
    public int getQuantidadeEstoque() { return quantidadeEstoque; }
    public double getValorCompra() { return valorCompra; }
    public double getValorVenda() { return valorVenda; }

    public double getValorTotalCompraEstoque() {
        return quantidadeEstoque * valorCompra;
    }
    public double getValorTotalVendaEstoque() {
        return quantidadeEstoque * valorVenda;
    }

    public void setQuantidadeEstoque(int quantidade) {
        this.quantidadeEstoque = quantidade;
    }

    public void setValorCompra(double novoValorCompra) {
        this.valorCompra = novoValorCompra;
        this.valorVenda = calcularValorVenda(novoValorCompra);
    }

    @Override
    public String toString() {
        return nome + "," + quantidadeEstoque + "," + valorCompra + "," + valorVenda;
    }

    public static Produto fromCSV(String linha) {
        String[] campos = linha.split(",");
        return new Produto(campos[0].trim(), Integer.parseInt(campos[1].trim()), Double.parseDouble(campos[2].trim()));
    }
}
