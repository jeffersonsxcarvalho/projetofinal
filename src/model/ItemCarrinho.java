package model;

public class ItemCarrinho {
    private final String produtoNome;
    private int quantidade;
    private final double preco;

    public ItemCarrinho(String produtoNome, int quantidade, double preco) {
        this.produtoNome = produtoNome;
        this.quantidade = quantidade;
        this.preco = preco;
    }

    public String getProdutoNome() {
        return produtoNome;
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
}
