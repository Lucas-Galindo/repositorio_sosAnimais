package sosanimais.com.example.app.model.entity;

import sosanimais.com.example.app.model.ProdutoInformacao;

public class Produto {
    private Long id;
    private ProdutoInformacao produto;

    public Produto() {
    }

    public Produto(Long id, ProdutoInformacao produto) {
        this.id = id;
        this.produto = produto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProdutoInformacao getProduto() {
        return produto;
    }

    public void setProduto(ProdutoInformacao produto) {
        this.produto = produto;
    }
}
