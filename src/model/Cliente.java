package model;

public class Cliente {
    private String nome;
    private String cpf;
    private String email;

    public Cliente(String nome, String cpf, String email) {
        if (cpf == null || cpf.isEmpty()) throw new IllegalArgumentException("CPF é obrigatório.");
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
    }

    public String getCpf() { return cpf; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }

    public void setNome(String nome) { this.nome = nome; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return nome + "," + cpf + "," + email;
    }

    public static Cliente fromCSV(String linha) {
        String[] campos = linha.split(",");
        return new Cliente(campos[0].trim(), campos[1].trim(), campos[2].trim());
    }
}
