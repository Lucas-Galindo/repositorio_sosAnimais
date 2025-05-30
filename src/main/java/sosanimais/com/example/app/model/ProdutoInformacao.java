package sosanimais.com.example.app.model;

public class ProdutoInformacao {
    private String nome;
    private String descricao;
    private double preco;
    private String validade;

    public ProdutoInformacao(String nome, String descricao, double preco, String validade) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.validade = validade;
    }

    public ProdutoInformacao() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getValidade() {
        return validade;
    }

    public void setValidade(String validade) {
        this.validade = validade;
    }
}