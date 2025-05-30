package sosanimais.com.example.app.model.DAL;

import org.springframework.stereotype.Repository;
import sosanimais.com.example.app.model.Compra;
import sosanimais.com.example.app.model.db.IDAL;
import sosanimais.com.example.app.model.db.SingletonDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Repository
public class CompraDAL implements IDAL<Compra> {
    
    public CompraDAL() {
        super();
    }

    @Override
    public boolean save(Compra entidade) {
        System.out.println("=== INÍCIO DO PROCESSO DE SALVAR COMPRA ===");
        System.out.println("Dados recebidos:");
        System.out.println("Produto: " + entidade.getProdutoNome());
        System.out.println("Quantidade: " + entidade.getQuantidade());
        System.out.println("Valor Unitário: " + entidade.getValorUnitario());
        System.out.println("ID do funcionário: " + entidade.getFuncCod());
        System.out.println("Data: " + entidade.getDataCompra());

        // Buscar o último ID da tabela
        String getLastId = "SELECT MAX(compra_cod) as ultimo_id FROM compra";
        System.out.println("Buscando último ID...");
        ResultSet rs = SingletonDB.getConexao().consultar(getLastId);
        try {
            if (rs != null && rs.next()) {
                Long ultimoId = rs.getLong("ultimo_id");
                entidade.setCompraCod(ultimoId + 1);
                System.out.println("Último ID obtido: " + ultimoId + ", próximo será: " + (ultimoId + 1));
            } else {
                entidade.setCompraCod(1L);
                System.out.println("Primeiro registro, ID será: 1");
            }
        } catch (SQLException e) {
            System.out.println("ERRO ao obter último ID: " + e.getMessage());
            e.printStackTrace();
            return false;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
        String dataCompra = sdf.format(entidade.getDataCompra());

        String sql = """
                    INSERT INTO compra(compra_cod, produto_nome, quantidade, valor_unitario, funcionario_func_cod, data_compra)
                    VALUES (#1, '#2', #3, #4, #5, '#6')
                """;

        sql = sql.replace("#1", String.valueOf(entidade.getCompraCod()));
        sql = sql.replace("#2", entidade.getProdutoNome());
        sql = sql.replace("#3", String.valueOf(entidade.getQuantidade()));
        sql = sql.replace("#4", String.valueOf(entidade.getValorUnitario()));
        sql = sql.replace("#5", String.valueOf(entidade.getFuncCod()));
        sql = sql.replace("#6", dataCompra);

        System.out.println("Enviando o SQL: " + sql);
        return SingletonDB.getConexao().manipular(sql);
    }

    @Override
    public boolean update(Compra entidade) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
        String dataCompra = sdf.format(entidade.getDataCompra());

        String sql = """
                UPDATE compra 
                SET produto_nome='#2', quantidade=#3, valor_unitario=#4, 
                    funcionario_func_cod=#5, data_compra='#6'
                WHERE compra_cod=#1
                """;

        sql = sql.replace("#1", String.valueOf(entidade.getCompraCod()));
        sql = sql.replace("#2", entidade.getProdutoNome());
        sql = sql.replace("#3", String.valueOf(entidade.getQuantidade()));
        sql = sql.replace("#4", String.valueOf(entidade.getValorUnitario()));
        sql = sql.replace("#5", String.valueOf(entidade.getFuncCod()));
        sql = sql.replace("#6", dataCompra);

        System.out.println("Enviando o SQL de atualização: " + sql);
        return SingletonDB.getConexao().manipular(sql);
    }

    @Override
    public boolean delete(Compra entidade) {
        String sql = "DELETE FROM compra WHERE compra_cod = " + entidade.getCompraCod();
        System.out.println("Enviando o SQL de exclusão: " + sql);
        return SingletonDB.getConexao().manipular(sql);
    }

    @Override
    public Compra get(Long id) {
        String sql = "SELECT * FROM compra WHERE compra_cod = " + id;
        System.out.println("Buscando compra por ID: " + sql);
        ResultSet rs = SingletonDB.getConexao().consultar(sql);
        try {
            if (rs != null && rs.next()) {
                return new Compra(
                    rs.getLong("compra_cod"),
                    rs.getString("produto_nome"),
                    rs.getInt("quantidade"),
                    rs.getDouble("valor_unitario"),
                    rs.getLong("funcionario_func_cod"),
                    rs.getDate("data_compra")
                );
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar compra: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Compra> get(String filtro) {
        List<Compra> compras = new ArrayList<>();
        String sql = "SELECT * FROM compra";
        if (filtro != null && !filtro.isEmpty()) {
            sql += " WHERE " + filtro;
        }
        sql += " ORDER BY compra_cod ASC";

        System.out.println("Buscando compras: " + sql);
        ResultSet rs = SingletonDB.getConexao().consultar(sql);
        try {
            while (rs != null && rs.next()) {
                Compra compra = new Compra(
                    rs.getLong("compra_cod"),
                    rs.getString("produto_nome"),
                    rs.getInt("quantidade"),
                    rs.getDouble("valor_unitario"),
                    rs.getLong("funcionario_func_cod"),
                    rs.getDate("data_compra")
                );
                compras.add(compra);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar compras: " + e.getMessage());
            e.printStackTrace();
        }
        return compras;
    }
} 