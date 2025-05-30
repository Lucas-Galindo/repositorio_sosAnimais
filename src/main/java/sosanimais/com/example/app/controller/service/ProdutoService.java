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
        if (produto == null || produto.getProduto() == null || produto.getProduto().getNome() == null || produto.getProduto().getNome().trim().isEmpty()) {
            return false;
        }
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

    public List<Produto> buscarProdutosPorNome(String nome) {
        return produtoDAL.getByName(nome);
    }

    public List<Produto> buscarProdutosPorValidade(String validade) {
        return produtoDAL.getByValidade(validade);
    }
}