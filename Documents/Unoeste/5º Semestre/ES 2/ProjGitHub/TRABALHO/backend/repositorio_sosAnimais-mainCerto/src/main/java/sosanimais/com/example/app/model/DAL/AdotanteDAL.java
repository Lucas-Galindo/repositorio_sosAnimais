package sosanimais.com.example.app.model.DAL;

import org.springframework.stereotype.Repository;
import sosanimais.com.example.app.model.PessoaInformacao;
import sosanimais.com.example.app.model.db.IDAL;
import sosanimais.com.example.app.model.db.SingletonDB;
import sosanimais.com.example.app.model.entity.Adotante;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AdotanteDAL implements IDAL<Adotante>{
    public AdotanteDAL() {
        super();
    }

    @Override
    public boolean save(Adotante entidade) {
        String sql = "";
        ResultSet pessSet;
        pessSet = SingletonDB.getConexao().consultar(" SELECT * FROM pessoa WHERE pess_id =" + entidade.getId());
        try{
            if(!pessSet.wasNull()){
                sql = """
                    INSERT INTO adotante(usu_id, adocao_animal_adota_cod) VALUES ('#1', #2);
                    """;

                sql = sql.replace("#1", "" + entidade.getId());
                sql = sql.replace("#2", entidade.getAdocaoAnimalAdotaCod() != null ? 
                    entidade.getAdocaoAnimalAdotaCod().toString() : "NULL");

                return SingletonDB.getConexao().manipular(sql);
            }
            return false;

        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Adotante entidade) {
        String sql = """
                UPDATE adotante SET adocao_animal_adota_cod = #2 WHERE adotante_matricula = #1;
                """;
        sql = sql.replace("#1", "" + entidade.getMatricula());
        sql = sql.replace("#2", entidade.getAdocaoAnimalAdotaCod() != null ? 
            entidade.getAdocaoAnimalAdotaCod().toString() : "NULL");

        return SingletonDB.getConexao().manipular(sql);
    }

    @Override
    public boolean delete(Adotante entidade) {
        // Primeiro, atualiza o adocao_animal_adota_cod para NULL
        String updateSql = "UPDATE adotante SET adocao_animal_adota_cod = NULL WHERE adotante_matricula = " + entidade.getMatricula();
        SingletonDB.getConexao().manipular(updateSql);
        
        // Depois exclui o registro
        return SingletonDB.getConexao().manipular("DELETE FROM adotante WHERE adotante_matricula = " + entidade.getMatricula());
    }

    public PessoaInformacao pessoaInfo(ResultSet set) {
        try {
            PessoaInformacao info = null;
            if (set.next()) {
                info = new PessoaInformacao(
                        set.getString("pess_nome"),
                        set.getString("pess_cpf"),
                        set.getString("pess_telefone"),
                        set.getString("pess_email")
                );
            }
            return info;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Adotante get(Long mat) {
        String sql = "SELECT a.*, p.pess_nome, p.pess_cpf, p.pess_telefone, p.pess_email " +
                    "FROM adotante a " +
                    "JOIN pessoa p ON a.usu_id = p.pess_id " +
                    "WHERE a.adotante_matricula = " + Math.toIntExact(mat);
        
        ResultSet resultSet = SingletonDB.getConexao().consultar(sql);
        try {
            if (resultSet.next()) {
                PessoaInformacao pessoaInfo = new PessoaInformacao(
                    resultSet.getString("pess_nome"),
                    resultSet.getString("pess_cpf"),
                    resultSet.getString("pess_telefone"),
                    resultSet.getString("pess_email")
                );

                return new Adotante(
                    resultSet.getLong("usu_id"),
                    pessoaInfo,
                    resultSet.getInt("adotante_matricula"),
                    resultSet.getObject("adocao_animal_adota_cod") != null ? 
                        resultSet.getLong("adocao_animal_adota_cod") : null
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Adotante> get(String filtro) {
        List<Adotante> listaAdotantes = new ArrayList<>();
        String sql = "SELECT a.adotante_matricula, a.usu_id, a.adocao_animal_adota_cod, " +
                "p.pess_nome, p.pess_cpf, p.pess_telefone, p.pess_email " +
                "FROM adotante a " +
                "JOIN pessoa p ON a.usu_id = p.pess_id";

        if (filtro != null && !filtro.trim().isEmpty()) {
            String termoFiltrado = filtro.trim().replace("'", "''");
            sql += " WHERE (LOWER(p.pess_nome) LIKE LOWER('%" + termoFiltrado + "%') " +
                    "OR p.pess_cpf LIKE '%" + termoFiltrado + "%' " +
                    "OR CAST(a.adotante_matricula AS VARCHAR) LIKE '%" + termoFiltrado + "%')";
        }

        ResultSet rs = null;
        try {
            rs = SingletonDB.getConexao().consultar(sql);
            while (rs != null && rs.next()) {
                PessoaInformacao pessoaInfo = new PessoaInformacao(
                    rs.getString("pess_nome"),
                    rs.getString("pess_cpf"),
                    rs.getString("pess_telefone"),
                    rs.getString("pess_email")
                );

                Adotante adotante = new Adotante(
                    rs.getLong("usu_id"),
                    pessoaInfo,
                    rs.getInt("adotante_matricula"),
                    rs.getObject("adocao_animal_adota_cod") != null ? 
                        rs.getLong("adocao_animal_adota_cod") : null
                );
                listaAdotantes.add(adotante);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar adotantes: " + e.getMessage());
            e.printStackTrace();
        }
        return listaAdotantes;
    }
}
