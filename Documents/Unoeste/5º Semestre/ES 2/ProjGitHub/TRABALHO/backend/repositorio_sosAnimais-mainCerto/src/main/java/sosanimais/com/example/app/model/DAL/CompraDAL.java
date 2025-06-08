package sosanimais.com.example.app.model.DAL;

import org.springframework.stereotype.Repository;
import sosanimais.com.example.app.model.entity.Compra;
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
        if (entidade.getFuncCod() == null) {
            return false;
        }

        String getLastId = "SELECT MAX(comp_id) as ultimo_id FROM compra";
        ResultSet rs = SingletonDB.getConexao().consultar(getLastId);
        try {
            if (rs != null && rs.next()) {
                Long ultimoId = rs.getLong("ultimo_id");
                entidade.setCompraCod(ultimoId + 1);
            } else {
                entidade.setCompraCod(1L);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
        String dataCompra = sdf.format(entidade.getDataCompra());

        String sql = """
                    INSERT INTO compra(comp_id, produto_prod_nome, comp_qtd, comp_valorfinal, funcionario_func_cod, comp_data)
                    VALUES (#1, '#2', #3, #4, #5, '#6')
                """;

        sql = sql.replace("#1", String.valueOf(entidade.getCompraCod()));
        sql = sql.replace("#2", entidade.getProdutoNome());
        sql = sql.replace("#3", String.valueOf(entidade.getQuantidade()));
        sql = sql.replace("#4", String.valueOf(entidade.getValorUnitario()));
        sql = sql.replace("#5", String.valueOf(entidade.getFuncCod()));
        sql = sql.replace("#6", dataCompra);

        return SingletonDB.getConexao().manipular(sql);
    }

    @Override
    public boolean update(Compra entidade) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
        String dataCompra = sdf.format(entidade.getDataCompra());

        String sql = """
                UPDATE compra 
                SET produto_prod_nome='#2', comp_qtd=#3, comp_valorfinal=#4, 
                    funcionario_func_cod=#5, comp_data='#6'
                WHERE comp_id=#1
                """;

        sql = sql.replace("#1", String.valueOf(entidade.getCompraCod()));
        sql = sql.replace("#2", entidade.getProdutoNome());
        sql = sql.replace("#3", String.valueOf(entidade.getQuantidade()));
        sql = sql.replace("#4", String.valueOf(entidade.getValorUnitario()));
        sql = sql.replace("#5", String.valueOf(entidade.getFuncCod()));
        sql = sql.replace("#6", dataCompra);

        return SingletonDB.getConexao().manipular(sql);
    }

    @Override
    public boolean delete(Compra entidade) {
        try {
            String sqlVerifica = "SELECT * FROM compra WHERE comp_id = " + entidade.getCompraCod();
            ResultSet rs = SingletonDB.getConexao().consultar(sqlVerifica);
            
            if (rs == null || !rs.next()) {
                return false;
            }

            String sqlDelete = "DELETE FROM compra WHERE comp_id = " + entidade.getCompraCod();
            return SingletonDB.getConexao().manipular(sqlDelete);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Compra get(Long id) {
        String sql = "SELECT * FROM compra WHERE comp_id = " + id;
        ResultSet rs = SingletonDB.getConexao().consultar(sql);
        try {
            if (rs != null && rs.next()) {
                return new Compra(
                    rs.getLong("comp_id"),
                    rs.getString("produto_prod_nome"),
                    rs.getInt("comp_qtd"),
                    rs.getDouble("comp_valorfinal"),
                    rs.getLong("funcionario_func_cod"),
                    rs.getDate("comp_data")
                );
            }
        } catch (SQLException e) {
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
        sql += " ORDER BY comp_id ASC";

        ResultSet rs = SingletonDB.getConexao().consultar(sql);
        try {
            while (rs != null && rs.next()) {
                Compra compra = new Compra(
                    rs.getLong("comp_id"),
                    rs.getString("produto_prod_nome"),
                    rs.getInt("comp_qtd"),
                    rs.getDouble("comp_valorfinal"),
                    rs.getLong("funcionario_func_cod"),
                    rs.getDate("comp_data")
                );
                compras.add(compra);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return compras;
    }
}