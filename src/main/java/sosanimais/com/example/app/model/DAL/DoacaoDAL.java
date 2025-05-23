package sosanimais.com.example.app.model.DAL;

import sosanimais.com.example.app.model.db.SingletonDB;
import sosanimais.com.example.app.model.entity.Doacao;


import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DoacaoDAL {

    public boolean salvar(Doacao doacao) {
        String sql = """
        INSERT INTO doacao (
            doacao_nome_doador, doacao_email, doacao_tipo,
            doacao_valor, doacao_mensagem, doacao_data,
            produto_nome, produto_validade, produto_descricao
        ) VALUES (
            '#1', '#2', '#3', #4, '#5', '#6', '#7', '#8', '#9'
        );
        """;

        Doacao.Produto produto = doacao.getProduto();

        sql = sql.replace("#1", doacao.getNomeDoador())
                .replace("#2", doacao.getEmail())
                .replace("#3", doacao.getTipo())
                .replace("#4", doacao.getValor() == null ? "null" : doacao.getValor().toString())
                .replace("#5", doacao.getMensagem() == null ? "" : doacao.getMensagem())
                .replace("#6", doacao.getDataDoacao())
                .replace("#7", produto != null ? produto.getNome() : "")
                .replace("#8", produto != null ? produto.getValidade() : "")
                .replace("#9", produto != null ? produto.getDescricao() : "");

        return SingletonDB.getConexao().manipular(sql);
    }

    public boolean atualizar(Doacao doacao) {
        String sql = """
        UPDATE doacao SET 
            doacao_nome_doador = '#1',
            doacao_email = '#2',
            doacao_tipo = '#3',
            doacao_valor = #4,
            doacao_mensagem = '#5',
            doacao_data = '#6',
            produto_nome = '#7',
            produto_validade = '#8',
            produto_descricao = '#9'
        WHERE doacao_id = #10;
        """;

        Doacao.Produto produto = doacao.getProduto();

        sql = sql.replace("#1", doacao.getNomeDoador())
                .replace("#2", doacao.getEmail())
                .replace("#3", doacao.getTipo())
                .replace("#4", doacao.getValor() == null ? "null" : doacao.getValor().toString())
                .replace("#5", doacao.getMensagem())
                .replace("#6", doacao.getDataDoacao())
                .replace("#7", produto != null ? produto.getNome() : "")
                .replace("#8", produto != null ? produto.getValidade() : "")
                .replace("#9", produto != null ? produto.getDescricao() : "")
                .replace("#10", doacao.getId().toString());

        return SingletonDB.getConexao().manipular(sql);
    }

    public boolean deletar(Doacao doacao) {
        return SingletonDB.getConexao().manipular("DELETE FROM doacao WHERE doacao_id = " + doacao.getId());
    }

    public Doacao buscarPorId(Long id) {
        String sql = "SELECT * FROM doacao WHERE doacao_id = " + id;
        try (ResultSet rs = SingletonDB.getConexao().consultar(sql)) {
            if (rs.next()) {
                return construirDoacao(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Doacao> listarTodas() {
        return buscarPorFiltro("");
    }

    public List<Doacao> buscarPorEmail(String email) {
        return buscarPorFiltro("doacao_email = '" + email + "'");
    }

    private List<Doacao> buscarPorFiltro(String filtro) {
        List<Doacao> lista = new ArrayList<>();
        String sql = "SELECT * FROM doacao";
        if (!filtro.isEmpty()) {
            sql += " WHERE " + filtro;
        }


        try (ResultSet rs = SingletonDB.getConexao().consultar(sql)) {
            while (rs.next()) {
                lista.add(construirDoacao(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    private Doacao construirDoacao(ResultSet rs) throws Exception {
        return new Doacao(
                rs.getLong("doacao_id"),
                rs.getString("doacao_nome_doador"),
                rs.getString("doacao_email"),
                rs.getString("doacao_tipo"),
                rs.getDouble("doacao_valor"),
                new Doacao.Produto(
                        rs.getString("produto_nome"),
                        rs.getString("produto_validade"),
                        rs.getString("produto_descricao")
                ),
                rs.getString("doacao_mensagem"),
                rs.getString("doacao_data")
        );
    }
}
