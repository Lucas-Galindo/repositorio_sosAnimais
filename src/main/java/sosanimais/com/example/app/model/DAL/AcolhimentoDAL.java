package sosanimais.com.example.app.model.DAL;

import org.springframework.stereotype.Repository;
import sosanimais.com.example.app.model.AnimalInformacao;
import sosanimais.com.example.app.model.db.IDAL;
import sosanimais.com.example.app.model.db.SingletonDB;
import sosanimais.com.example.app.model.entity.Acolhimento;
import sosanimais.com.example.app.model.entity.Animal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Repository
public class AcolhimentoDAL implements IDAL<Acolhimento> {
    public AcolhimentoDAL() {
        super();
    }

    @Override
    public boolean save(Acolhimento entidade) {//ok, ver pq cadastrou com um dia a menos
        String alinhaId="SELECT setval('acolhimento_id_seq', (SELECT MAX(aco_cod) FROM acolhimento))";
        SingletonDB.getConexao().consultar(alinhaId);
        String sql= """
                INSERT INTO acolhimento(aco_data, funcionario_func_mat, animal_ani_cod) VALUES
                 ('#3',#4,#5)
                """;
        sql=sql.replace("#3",entidade.getData().toString());
        sql=sql.replace("#4",""+entidade.getIdFunc());
        sql=sql.replace("#5",""+entidade.getIdAnimal());
        System.out.println("Enviando o SQL: " + sql);

        return SingletonDB.getConexao().manipular(sql);
    }

    @Override
    public boolean update(Acolhimento entidade) {
        String sql= """
                UPDATE acolhimento SET aco_data='#3', funcionario_func_mat=#4, animal_ani_cod=#5
                WHERE aco_cod=#1
                """;
        sql=sql.replace("#1",""+entidade.getId());
        sql=sql.replace("#3",entidade.getData().toString());
        sql=sql.replace("#4",""+entidade.getIdFunc());
        sql=sql.replace("#5",""+entidade.getIdAnimal());
        System.out.println("Enviando o SQL: " + sql);
        return SingletonDB.getConexao().manipular(sql);
    }

    @Override
    public boolean delete(Acolhimento entidade) {
        String desvincularAnimal ="UPDATE animal" +
                "   SET acolhimento_aco_cod = NULL" +
                " WHERE acolhimento_aco_cod ="+entidade.getId();
        SingletonDB.getConexao().manipular(desvincularAnimal);
        String sql = "DELETE FROM acolhimento WHERE aco_cod ="+entidade.getId();

        return SingletonDB.getConexao().manipular(sql);
    }

    @Override
    public Acolhimento get(Long id) {//ok
        Acolhimento acolhimento = null;
        String sql = "SELECT * FROM acolhimento WHERE aco_cod=" + id;
        System.out.println("SQL: " + sql);
        ResultSet rs = SingletonDB.getConexao().consultar(sql);
        try {
            if (rs!=null && rs.next()) {
                acolhimento = new Acolhimento(
                        rs.getLong("aco_cod"),
                        rs.getDate("aco_data"),
                        rs.getLong("funcionario_func_mat"),
                        rs.getLong("animal_ani_cod")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return acolhimento;
    }

    @Override
    public List<Acolhimento> get(String filtro) throws SQLException {
        List<Acolhimento> acolhimento = new ArrayList<>();
        String sql = "SELECT * FROM acolhimento";
        if (!filtro.isEmpty())
            sql += " WHERE " + filtro;

        ResultSet rs = SingletonDB.getConexao().consultar(sql);
        try {
            while (rs.next()) {
                Acolhimento ac = new Acolhimento(
                        rs.getLong("aco_cod"),
                        rs.getDate("aco_data"),
                        rs.getLong("funcionario_func_mat"),
                        rs.getLong("animal_ani_cod")
                );

                acolhimento.add(ac);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return acolhimento;
    }

    public Long obterUltimoIdPorAnimal(Long idAnimal) {
        String sql = "SELECT MAX(aco_cod) AS max_id FROM acolhimento WHERE animal_ani_cod = " + idAnimal;
        Long maxId = null;
        ResultSet rs = SingletonDB.getConexao().consultar(sql);
        try {
            if (rs.next()) {
                maxId = rs.getLong("max_id");
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maxId;
    }

    public List<Acolhimento> buscarPorNomeAnimal(String nome) {
        // escapa aspas simples e normaliza para minúsculas
        String nomeEscaped = nome.replace("'", "''").toLowerCase();
        String sql =
                "SELECT a.aco_cod, a.aco_data, a.funcionario_func_mat, a.animal_ani_cod " +
                        "FROM Acolhimento a " +
                        "JOIN Animal ani ON a.animal_ani_cod = ani.ani_cod " +
                        "WHERE LOWER(ani.ani_nome) LIKE '%" + nomeEscaped + "%'";

        List<Acolhimento> resultados = new ArrayList<>();
        try (
                ResultSet rs = SingletonDB.getConexao().consultar(sql);
        ) {
            while (rs.next()) {
                Acolhimento ac = new Acolhimento(
                        rs.getLong("aco_cod"),
                        rs.getDate("aco_data"),
                        rs.getLong("funcionario_func_mat"),
                        rs.getLong("animal_ani_cod")
                );
                resultados.add(ac);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar acolhimentos por nome de animal (case-insensitive)", e);
        }
        return resultados;
    }


    public Acolhimento buscarPorAnimal(Long idAnimal){
        String sql = "SELECT aco_cod, aco_data, funcionario_func_mat, animal_ani_cod "
                + "FROM Acolhimento WHERE animal_ani_cod = " + idAnimal;
        ResultSet rs = SingletonDB.getConexao().consultar(sql);
        try {
            if (rs.next()) {
                Acolhimento ac = new Acolhimento(
                        rs.getLong("aco_cod"),
                        rs.getDate("aco_data"),
                        rs.getLong("funcionario_func_mat"),
                        rs.getLong("animal_ani_cod")
                );
                return ac;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
