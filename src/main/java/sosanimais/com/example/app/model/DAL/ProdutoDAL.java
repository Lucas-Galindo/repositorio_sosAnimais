package sosanimais.com.example.app.model.DAL;

import sosanimais.com.example.app.model.ProdutoInformacao;
import sosanimais.com.example.app.model.db.SingletonDB;
import sosanimais.com.example.app.model.entity.Produto;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAL {

    public boolean save(Produto produto) {
        ProdutoInformacao info = produto.getProduto();

        String sql = String.format("""
            INSERT INTO produto (produto_nome, produto_descricao, produto_preco)
            VALUES ('%s', '%s', %.2f);
        """, info.getNome(), info.getDescricao(), info.getPreco());

        return SingletonDB.getConexao().manipular(sql);
    }

    public Produto get(int id) {
        String sql = "SELECT * FROM produto WHERE produto_id = " + id;
        ResultSet rs = SingletonDB.getConexao().consultar(sql);

        try {
            if (rs.next()) {
                ProdutoInformacao info = new ProdutoInformacao(
                        rs.getString("produto_nome"),
                        rs.getString("produto_descricao"),
                        rs.getDouble("produto_preco")
                );
                return new Produto(rs.getInt("produto_id"), info);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Produto> getAll() {
        List<Produto> lista = new ArrayList<>();
        String sql = "SELECT * FROM produto";
        ResultSet rs = SingletonDB.getConexao().consultar(sql);

        try {
            while (rs.next()) {
                ProdutoInformacao info = new ProdutoInformacao(
                        rs.getString("produto_nome"),
                        rs.getString("produto_descricao"),
                        rs.getDouble("produto_preco")
                );
                lista.add(new Produto(rs.getInt("produto_id"), info));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM produto WHERE produto_id = " + id;
        return SingletonDB.getConexao().manipular(sql);
    }
}
