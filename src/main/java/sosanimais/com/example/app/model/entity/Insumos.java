package sosanimais.com.example.app.model.entity;

public class Insumos {
    private Long id;
    private String nome;
    private String nomeCategoria;
    private int quantidade;
    private String descricao;
    private Long idCat;

    public Insumos() {
        super();
    }

    public Insumos(Long id, String nome, String nomeCategoria, int quantidade, String descricao, Long idCat) {
        this.id = id;
        this.nome = nome;
        this.nomeCategoria = nomeCategoria;
        this.quantidade = quantidade;
        this.descricao = descricao;
        this.idCat = idCat;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getIdCat() {
        return idCat;
    }

    public void setIdCat(Long idCat) {
        this.idCat = idCat;
    }
}
