package sosanimais.com.example.app.model.entity;

public class Baias {

    private Long id;
    private int quantidadeAnimais;
    private String nome;
    private String categoria;

    public Baias(){
        this(0L,0,"","");
    }
    public Baias(Long id, int quantidadeAnimais, String nome, String categoria) {
        this.id = id;
        this.quantidadeAnimais = quantidadeAnimais;
        this.nome = nome;
       // this.categoria = categoria;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantidadeAnimais() {
        return quantidadeAnimais;
    }

    public void setQuantidadeAnimais(int quantidadeAnimais) {
        this.quantidadeAnimais = quantidadeAnimais;
    }
}
