package sosanimais.com.example.app.controller.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sosanimais.com.example.app.model.DAL.ProdutoDAL;
import sosanimais.com.example.app.model.entity.Produto;
import sosanimais.com.example.app.model.ProdutoInformacao;
import sosanimais.com.example.app.model.db.SingletonDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

@Service
public class ProdutoService {

    @Autowired
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

    public Produto buscarProdutoPorId(Long id) {
        return produtoDAL.get(id);
    }

    public boolean deletarProduto(Produto produto) {
        try {
            boolean result = produtoDAL.delete(produto);
            System.out.println("Resultado da exclusão no service: " + result);
            return result;
        } catch (Exception e) {
            System.out.println("ERRO no service durante exclusão: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public List<Produto> listarProdutos() {
        return produtoDAL.get("");
    }

    public List<Produto> getByName(String nome) {
        String filtro = "LOWER(prod_nome) LIKE LOWER('%" + nome + "%')";
        return produtoDAL.get(filtro);
    }

    public List<Produto> getByValidade(String validade) {
        String filtro = "prod_validade = '" + validade + "'";
        return produtoDAL.get(filtro);
    }
}