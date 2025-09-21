package model;

public class ItemCarrinho {
    private final String cpfCliente;
    private final String nomeProduto;
    private int quantidade;
    private double preco;

    public ItemCarrinho(String cpfCliente, String nomeProduto, int quantidade, double preco) {
        this.cpfCliente = cpfCliente;
        this.nomeProduto = nomeProduto;
        this.quantidade = quantidade;
        this.preco = preco;
    }

    public String getCpfCliente() {
        return cpfCliente;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    @Override
    public String toString() {
        return cpfCliente + "," + nomeProduto + "," + quantidade + "," + preco;
    }

    public static ItemCarrinho fromCSV(String linha) {
        String[] campos = linha.split(",");
        return new ItemCarrinho(campos[0].trim(), campos[1].trim(), Integer.parseInt(campos[2].trim()), Double.parseDouble(campos[3].trim()));
    }
}
