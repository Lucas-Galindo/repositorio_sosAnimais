package sosanimais.com.example.app.model;

public class Produto_Informacao {
    private String nome;
    private String Descricao;
    private double preco;

    public Produto_Informacao(String nome, String Descricao, double preco){
        this.nome = nome;
        this.Descricao = Descricao;
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String descricao) {
        Descricao = descricao;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
}
