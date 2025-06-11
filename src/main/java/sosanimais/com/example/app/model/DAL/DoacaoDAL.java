package sosanimais.com.example.app.model.DAL;

import sosanimais.com.example.app.model.db.SingletonDB;
import sosanimais.com.example.app.model.entity.Doacao;
import sosanimais.com.example.app.model.ProdutoInformacao; // Usando sua classe existente

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList; // Import para ArrayList
import java.util.List;    // Import para List

// Se DoacaoDAL for gerenciado pelo Spring, adicione @Repository
// import org.springframework.stereotype.Repository;
// @Repository
public class DoacaoDAL {

    // Método auxiliar para montar o objeto Doacao a partir do ResultSet
    private Doacao montarDoacao(ResultSet rs) throws SQLException {
        ProdutoInformacao produtoInfo = null;
        String produtoNome = rs.getString("produto_nome");
        if (produtoNome != null) { // Só cria ProdutoInformacao se houver nome de produto
            produtoInfo = new ProdutoInformacao();
            produtoInfo.setNome(produtoNome);
            produtoInfo.setValidade(rs.getString("produto_validade")); // Pode ser null
            produtoInfo.setDescricao(rs.getString("produto_descricao")); // Pode ser null
            // O campo preco não é relevante para doação de produto, então não o setamos aqui
        }

        return new Doacao(
                rs.getString("doacao_nome_doador"),
                rs.getString("doacao_email_doador"),
                rs.getString("doacao_tipo"),
                rs.getString("doacao_mensagem"),
                rs.getDate("doacao_data") != null ? rs.getDate("doacao_data").toLocalDate() : null,
                rs.getObject("doacao_valor") != null ? rs.getDouble("doacao_valor") : null,
                produtoInfo
        );
    }


    public Doacao save(Doacao doacao) {
        String sql = "INSERT INTO DoacoesGerais (doacao_nome_doador, doacao_email_doador, doacao_tipo, " +
                "doacao_data, doacao_valor, doacao_mensagem, " +
                "produto_nome, produto_validade, produto_descricao) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = SingletonDB.getConexao().getConnect();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, doacao.getNomeDoador());
            pstmt.setString(2, doacao.getEmail());
            pstmt.setString(3, doacao.getTipo());
            pstmt.setDate(4, java.sql.Date.valueOf(doacao.getDataDoacao()));

            if (doacao.getValor() != null) {
                pstmt.setDouble(5, doacao.getValor());
            } else {
                pstmt.setNull(5, Types.DECIMAL);
            }
            pstmt.setString(6, doacao.getMensagem());

            ProdutoInformacao produtoInfo = doacao.getProduto();
            if (produtoInfo != null) {
                pstmt.setString(7, produtoInfo.getNome());
                if (produtoInfo.getValidade() != null && !produtoInfo.getValidade().isEmpty()) {
                    pstmt.setDate(8, java.sql.Date.valueOf(produtoInfo.getValidade()));
                } else {
                    pstmt.setNull(8, Types.DATE);
                }
                pstmt.setString(9, produtoInfo.getDescricao());
            } else {
                pstmt.setNull(7, Types.VARCHAR);
                pstmt.setNull(8, Types.DATE);
                pstmt.setNull(9, Types.VARCHAR);
            }

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        doacao.setId(generatedKeys.getLong("doacao_id")); // Nome da coluna PK em DoacoesGerais
                        return doacao;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao salvar doação no DAL: " + e.getMessage());
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.err.println("Erro de NullPointer ao obter conexão ou dados da doação no DAL: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    // NOVO MÉTODO: Buscar todas as doações
    public List<Doacao> getAll() {
        List<Doacao> doacoes = new ArrayList<>();
        String sql = "SELECT * FROM DoacoesGerais ORDER BY doacao_data DESC, doacao_id DESC"; // Ordena pelas mais recentes

        try (Connection conn = SingletonDB.getConexao().getConnect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Doacao doacao = montarDoacao(rs);
                doacao.setId(rs.getLong("doacao_id")); // Pega o ID da doação
                doacoes.add(doacao);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar todas as doações no DAL: " + e.getMessage());
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.err.println("Erro de NullPointer ao obter conexão para listar doações no DAL: " + e.getMessage());
            e.printStackTrace();
        }
        return doacoes;
    }

    // NOVO MÉTODO: Buscar doação por ID
    public Doacao getById(Long id) {
        String sql = "SELECT * FROM DoacoesGerais WHERE doacao_id = ?";
        try (Connection conn = SingletonDB.getConexao().getConnect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Doacao doacao = montarDoacao(rs);
                    doacao.setId(rs.getLong("doacao_id"));
                    return doacao;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar doação por ID " + id + " no DAL: " + e.getMessage());
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.err.println("Erro de NullPointer ao obter conexão para buscar doação por ID no DAL: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    // MÉTODO EXISTENTE: Deletar doação
    public boolean delete(Long id) {
        String sql = "DELETE FROM DoacoesGerais WHERE doacao_id = ?";
        try (Connection conn = SingletonDB.getConexao().getConnect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao deletar doação com ID " + id + " no DAL: " + e.getMessage());
            e.printStackTrace();
            return false;
        } catch (NullPointerException e) {
            System.err.println("Erro de NullPointer ao obter conexão para deletar doação no DAL: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
