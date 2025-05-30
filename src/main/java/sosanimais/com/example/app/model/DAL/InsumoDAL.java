package sosanimais.com.example.app.model.DAL;

import org.springframework.stereotype.Repository;
import sosanimais.com.example.app.model.entity.Insumos;
import sosanimais.com.example.app.model.db.IDAL;
import sosanimais.com.example.app.model.db.SingletonDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class InsumoDAL implements IDAL<Insumos> {
    @Override
    public List<Insumos> get(String filtro) {
        List<Insumos> lista = new ArrayList<>();
        String sql = "SELECT * FROM insumo";
        if (!filtro.isEmpty()) {
            sql += " WHERE " + filtro;
        }
        System.out.println("SQL consulta get Insumo: " + sql);
        ResultSet rs = SingletonDB.getConexao().consultar(sql);
        try {
            while (rs.next()) {
                Insumos insumo = new Insumos(
                        rs.getLong("insu_id"),
                        rs.getString("insu_nome"),
                        rs.getString("insu_categoria"),
                        rs.getInt("insu_qtd_estoque"),
                        rs.getString("insu_descricao"),
                        rs.getLong("categoria_cat_id")
                );
                lista.add(insumo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public Insumos get(Long id) {
        Insumos insumo = null;
        String sql = "SELECT * FROM insumo WHERE insu_id = " + id;
        System.out.println("SQL consulta get by id Insumo: " + sql);
        ResultSet rs = SingletonDB.getConexao().consultar(sql);
        try {
            if (rs.next()) {
                insumo = new Insumos(
                        rs.getLong("insu_id"),
                        rs.getString("insu_nome"),
                        rs.getString("insu_categoria"),
                        rs.getInt("insu_qtd_estoque"),
                        rs.getString("insu_descricao"),
                        rs.getLong("categoria_cat_id")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return insumo;
    }

    @Override
    public boolean save(Insumos entidade) {
        // atualiza sequência
        SingletonDB.getConexao().manipular(
                "SELECT setval('insumo_id_seq', (SELECT MAX(insu_id) FROM insumo))"
        );
        String sql = String.format(
                "INSERT INTO insumo(insu_nome, insu_categoria, insu_qtd_estoque, insu_descricao, categoria_cat_id) VALUES ('%s','%s',%d,'%s',%d)",
                entidade.getNome().replace("'", "''"),
                entidade.getNomeCategoria().replace("'", "''"),
                entidade.getQuantidade(),
                entidade.getDescricao().replace("'", "''"),
                entidade.getIdCat()
        );
        System.out.println("Enviando SQL save Insumo: " + sql);
        return SingletonDB.getConexao().manipular(sql);
    }


    @Override
    public boolean update(Insumos entidade) {
        String sql = String.format(
                "UPDATE insumo SET insu_nome='%s', insu_categoria='%s', insu_qtd_estoque=%d, insu_descricao='%s', categoria_cat_id=%d WHERE insu_id=%d",
                entidade.getNome().replace("'", "''"),
                entidade.getNomeCategoria().replace("'", "''"),
                entidade.getQuantidade(),
                entidade.getDescricao().replace("'", "''"),
                entidade.getIdCat(),
                entidade.getId()
        );
        System.out.println("Enviando SQL update Insumo: " + sql);
        return SingletonDB.getConexao().manipular(sql);
    }

    @Override
    public boolean delete(Insumos entidade) {
        String sql = "DELETE FROM insumo WHERE insu_id=" + entidade.getId();
        System.out.println("Enviando SQL delete Insumo: " + sql);
        boolean ok = SingletonDB.getConexao().manipular(sql);
        // re-alinha sequência
        SingletonDB.getConexao().manipular(
                "SELECT setval('insumo_id_seq', (SELECT MAX(insu_id) FROM insumo))"
        );
        return ok;
    }

    public List<Insumos> getByCategoriaIds(Long id1, Long id2) {
        String sql = "SELECT insu_id, insu_nome, insu_descricao, insu_qtd_estoque, categoria_cat_id, insu_categoria "
                + "FROM insumo "
                + "WHERE categoria_cat_id = "+id1+" or categoria_cat_id = "+id2 ;
        List<Insumos> lista = new ArrayList<>();
        System.out.println("SQL consulta getByCategoriaIds: " + sql);
        ResultSet rs = SingletonDB.getConexao().consultar(sql);
        if (rs == null) {
            return lista;
        }

        try {
            while (rs.next()) {
                Insumos ins = new Insumos(
                        rs.getLong("insu_id"),
                        rs.getString("insu_nome"),
                        rs.getString("insu_categoria"),
                        rs.getInt("insu_qtd_estoque"),
                        rs.getString("insu_descricao"),
                        rs.getLong("categoria_cat_id")

                );
                lista.add(ins);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro lendo ResultSet de insumos", e);
        }
        return lista;
    }

}