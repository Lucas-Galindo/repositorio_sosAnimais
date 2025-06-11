package sosanimais.com.example.app.model.DAL;

import org.springframework.stereotype.Repository;
import sosanimais.com.example.app.model.ProdutoInformacao;
import sosanimais.com.example.app.model.db.SingletonDB;
import sosanimais.com.example.app.model.entity.Produto;
import sosanimais.com.example.app.model.db.IDAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProdutoDAL implements IDAL<Produto> {

    @Override
    public boolean save(Produto produto) {
        ProdutoInformacao info = produto.getProduto();
        String sql = "INSERT INTO produto (prod_nome, prod_descricao, prod_preco, prod_validade) VALUES (?, ?, ?, ?)";

        try (Connection conn = SingletonDB.getConexao().getConnect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, info.getNome());
            pstmt.setString(2, info.getDescricao());
            pstmt.setDouble(3, info.getPreco());
            pstmt.setString(4, info.getValidade());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            // Adicionar tratamento de erro mais específico ou log, se necessário
            return false;
        } catch (NullPointerException e) {
            // Pode ocorrer se SingletonDB.getConexao() ou info for null
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Produto entidade) {
        // Implementar o método update
        return false;
    }

    @Override
    public boolean delete(Produto entidade) {
        try {
            String sql = "DELETE FROM produto WHERE prod_id = ?";
            try (Connection conn = SingletonDB.getConexao().getConnect();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setLong(1, entidade.getId());
                return pstmt.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Produto get(Long id) {
        String sql = "SELECT * FROM produto WHERE prod_id = ?";
        try (Connection conn = SingletonDB.getConexao().getConnect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                ProdutoInformacao info = new ProdutoInformacao(
                    rs.getString("prod_nome"),
                    rs.getString("prod_descricao"),
                    rs.getDouble("prod_preco"),
                    rs.getString("prod_validade")
                );
                return new Produto(rs.getLong("prod_id"), info);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Produto> get(String filtro) {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM produto";
        if (filtro != null && !filtro.isEmpty()) {
            sql += " WHERE " + filtro;
        }
        sql += " ORDER BY prod_id ASC";

        ResultSet rs = SingletonDB.getConexao().consultar(sql);
        try {
            while (rs != null && rs.next()) {
                ProdutoInformacao info = new ProdutoInformacao(
                    rs.getString("prod_nome"),
                    rs.getString("prod_descricao"),
                    rs.getDouble("prod_preco"),
                    rs.getString("prod_validade")
                );
                produtos.add(new Produto(rs.getLong("prod_id"), info));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produtos;
    }
}