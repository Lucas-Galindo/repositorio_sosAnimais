package sosanimais.com.example.app.model.entity;

<<<<<<< HEAD
import sosanimais.com.example.app.model.ProdutoInformacao;

public class Produto {
    private int id;
    private ProdutoInformacao produto;

    public Produto(int id, ProdutoInformacao produto) {
=======
import sosanimais.com.example.app.model.Produto_Informacao;

public class Produto {
    private int id;
    private Produto_Informacao produto;

    public Produto(int id, Produto_Informacao produto) {
>>>>>>> c79675591135cd43030da201dac6a938754b568b
        this.id = id;
        this.produto = produto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

<<<<<<< HEAD
    public ProdutoInformacao getProduto() {
        return produto;
    }

    public void setProduto(ProdutoInformacao produto) {
=======
    public Produto_Informacao getProduto() {
        return produto;
    }

    public void setProduto(Produto_Informacao produto) {
>>>>>>> c79675591135cd43030da201dac6a938754b568b
        this.produto = produto;
    }
}
