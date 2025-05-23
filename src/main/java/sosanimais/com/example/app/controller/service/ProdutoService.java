package sosanimais.com.example.app.controller.service;

import sosanimais.com.example.app.model.DAL.ProdutoDAL;
import sosanimais.com.example.app.model.entity.Produto;

import java.util.List;

public class ProdutoService {

    private ProdutoDAL produtoDAL;

    public ProdutoService() {
        this.produtoDAL = new ProdutoDAL();
    }

    public boolean cadastrarProduto(Produto produto) {
        return produtoDAL.save(produto);
    }

    public Produto buscarProdutoPorId(int id) {
        return produtoDAL.get(id);
    }

    public List<Produto> listarProdutos() {
        return produtoDAL.getAll();
    }

    public boolean deletarProduto(int id) {
        return produtoDAL.delete(id);
    }
}
