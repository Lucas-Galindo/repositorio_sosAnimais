package sosanimais.com.example.app.model.entity;


import sosanimais.com.example.app.model.ProdutoInformacao;

public class Produto {
    private int id;
    private ProdutoInformacao produto;

    public Produto(int id, ProdutoInformacao produto) {

        this.id = id;
        this.produto = produto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public ProdutoInformacao getProduto() {
        return produto;
    }

    public void setProduto(ProdutoInformacao produto) {
        this.produto = produto;
    }
}
