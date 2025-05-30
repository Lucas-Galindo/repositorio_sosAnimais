package sosanimais.com.example.app.model.entity;

public class Baias {

    private int id;
    private int quantidadeAnimais;

    public Baias(int id, int quantidadeAnimais) {
        this.id = id;
        this.quantidadeAnimais = quantidadeAnimais;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantidadeAnimais() {
        return quantidadeAnimais;
    }

    public void setQuantidadeAnimais(int quantidadeAnimais) {
        this.quantidadeAnimais = quantidadeAnimais;
    }
}
