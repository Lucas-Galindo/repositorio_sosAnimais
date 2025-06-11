package sosanimais.com.example.app.model.DAL;

import org.springframework.stereotype.Repository;
import sosanimais.com.example.app.model.Animal_Insumo;
import sosanimais.com.example.app.model.db.IDAL;
import sosanimais.com.example.app.model.db.SingletonDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AnimalInsumoDAL implements IDAL<Animal_Insumo> {

    @Override
    public List<Animal_Insumo> get(String filtro) {
        List<Animal_Insumo> lista = new ArrayList<>();
        String sql = "SELECT ai_id, animal_ani_cod, insumo_insu_id, ai_dataexecucao, func_matricula FROM animal_insumo" + (filtro.isEmpty() ? "" : " WHERE " + filtro);
        System.out.println("SQL get AnimalInsumo: " + sql);
        ResultSet rs = SingletonDB.getConexao().consultar(sql);
        try {
            while (rs.next()) {
                lista.add(new Animal_Insumo(
                        rs.getLong("ai_id"),
                        rs.getLong("animal_ani_cod"),
                        rs.getLong("insumo_insu_id"),
                        rs.getDate("ai_dataexecucao"),
                        rs.getLong("func_matricula")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public Animal_Insumo get(Long id) {
        String sql = "SELECT ai_id, animal_ani_cod, insumo_insu_id, ai_dataexecucao, func_matricula FROM animal_insumo WHERE ai_id=" + id;
        System.out.println("SQL getById AnimalInsumo: " + sql);
        ResultSet rs = SingletonDB.getConexao().consultar(sql);
        try {
            if (rs.next()) {
                return new Animal_Insumo(
                        rs.getLong("ai_id"),
                        rs.getLong("animal_ani_cod"),
                        rs.getLong("insumo_insu_id"),
                        rs.getDate("ai_dataexecucao"),
                        rs.getLong("func_matricula")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean save(Animal_Insumo a) {
        // realinha sequência
        SingletonDB.getConexao().manipular(
                "SELECT setval('animal_insumo_ai_id_seq', (SELECT MAX(ai_id) FROM animal_insumo))"
        );
        String sql = String.format(
                "INSERT INTO animal_insumo(animal_ani_cod, insumo_insu_id, ai_dataexecucao, func_matricula) VALUES(%d,%d,'%s',%d)", // ADICIONADO AQUI
                a.getAnimalAniCod(),
                a.getInsumoIsuId(),
                a.getAiDataExecucao(),
                a.getFuncionarioFuncCod() // ADICIONADO AQUI
        );
        System.out.println("SQL save AnimalInsumo: " + sql);
        return SingletonDB.getConexao().manipular(sql);
    }

    @Override
    public boolean update(Animal_Insumo a) {
        String sql = String.format(
                "UPDATE animal_insumo SET animal_ani_cod=%d, insumo_insu_id=%d, ai_dataexecucao='%s', func_matricula=%d WHERE ai_id=%d", // ADICIONADO AQUI
                a.getAnimalAniCod(),
                a.getInsumoIsuId(),
                a.getAiDataExecucao(),
                a.getFuncionarioFuncCod(), // ADICIONADO AQUI
                a.getAiId()
        );
        System.out.println("SQL update AnimalInsumo: " + sql);
        return SingletonDB.getConexao().manipular(sql);
    }

    @Override
    public boolean delete(Animal_Insumo a) {
        String sql = "DELETE FROM animal_insumo WHERE ai_id=" + a.getAiId();
        System.out.println("SQL delete AnimalInsumo: " + sql);
        boolean ok = SingletonDB.getConexao().manipular(sql);
        // realinha seq
        SingletonDB.getConexao().manipular(
                "SELECT setval('animal_insumo_ai_id_seq', (SELECT MAX(ai_id) FROM animal_insumo))"
        );
        return ok;
    }

    public List<Animal_Insumo> getByAnimal(String termo) {
        String filtro;
        if (termo.matches("\\d+")) {
            // pesquisa por ID do animal
            filtro = "animal_ani_cod = " + termo;
        } else {
            filtro = "animal_ani_cod IN (" +
                    "SELECT ani_cod FROM animal " +
                    "WHERE LOWER(informacao->>'nome') LIKE '%" + termo.toLowerCase().replace("'", "''") + "%'" +
                    ")";
        }
        return get(filtro);
    }
}