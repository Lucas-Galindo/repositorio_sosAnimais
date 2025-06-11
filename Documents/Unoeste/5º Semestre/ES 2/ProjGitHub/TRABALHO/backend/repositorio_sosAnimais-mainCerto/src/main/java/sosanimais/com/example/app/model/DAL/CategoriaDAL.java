package sosanimais.com.example.app.model.DAL;


import org.springframework.stereotype.Repository;
import sosanimais.com.example.app.model.db.IDAL;
import sosanimais.com.example.app.model.db.SingletonDB;
import sosanimais.com.example.app.model.entity.Categoria;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CategoriaDAL implements IDAL<Categoria> {

    @Override
    public List<Categoria> get(String filtro) {
        List<Categoria> lista = new ArrayList<>();
        String sql = "SELECT * FROM categoria" + (filtro.isEmpty() ? "" : " WHERE " + filtro);
        System.out.println("SQL consulta get Categoria: " + sql);
        ResultSet rs = SingletonDB.getConexao().consultar(sql);
        try {
            while (rs.next()) {
                Categoria c = new Categoria(
                        rs.getLong("cat_id"),
                        rs.getString("cat_nome"),
                        rs.getString("cat_descricao")
                );
                lista.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public Categoria get(Long id) {
        String sql = "SELECT * FROM categoria WHERE cat_id = " + id;
        System.out.println("SQL consulta getById Categoria: " + sql);
        ResultSet rs = SingletonDB.getConexao().consultar(sql);
        try {
            if (rs.next()) {
                return new Categoria(
                        rs.getLong("cat_id"),
                        rs.getString("cat_nome"),
                        rs.getString("cat_descricao")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean save(Categoria entidade) {
        // alinhar sequência
        SingletonDB.getConexao().manipular(
                "SELECT setval('categoria_id_seq', (SELECT MAX(cat_id) FROM categoria))"
        );
        String sql = String.format(
                "INSERT INTO categoria(cat_nome, cat_descricao) VALUES('%s','%s')",
                entidade.getNome().replace("'", "''"),
                entidade.getDescricao().replace("'", "''")
        );
        System.out.println("SQL save Categoria: " + sql);
        return SingletonDB.getConexao().manipular(sql);
    }

    @Override
    public boolean update(Categoria entidade) {
        String sql = String.format(
                "UPDATE categoria SET cat_nome='%s', cat_descricao='%s' WHERE cat_id=%d",
                entidade.getNome().replace("'", "''"),
                entidade.getDescricao().replace("'", "''"),
                entidade.getId()
        );
        System.out.println("SQL update Categoria: " + sql);
        return SingletonDB.getConexao().manipular(sql);
    }

    @Override
    public boolean delete(Categoria entidade) {
        String sql = "DELETE FROM categoria WHERE cat_id = " + entidade.getId();
        System.out.println("SQL delete Categoria: " + sql);
        boolean ok = SingletonDB.getConexao().manipular(sql);
        // realinha sequência
        SingletonDB.getConexao().manipular(
                "SELECT setval('categoria_id_seq', (SELECT MAX(cat_id) FROM categoria))"
        );
        return ok;
    }
}