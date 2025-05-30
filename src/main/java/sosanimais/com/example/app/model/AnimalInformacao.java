package sosanimais.com.example.app.model;

public class AnimalInformacao {

    private String raca;
    private String nome;
    private String descricao;
    private int idade;
    private char status;
    private char statusVida;

    public AnimalInformacao(String raca, String nome, String descricao, int idade, char status, char statusVida) {
        this.raca = raca;
        this.nome = nome;
        this.descricao = descricao;
        this.idade = idade;
        this.status = status;
        this.statusVida=statusVida;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
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

    public char getStatusVida() {
        return statusVida;
    }
    public void setStatusVida(char statusVida) {
        this.statusVida = statusVida;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }
}
