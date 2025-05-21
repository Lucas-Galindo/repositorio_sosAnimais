package sosanimais.com.example.app.model.DAL;

import sosanimais.com.example.app.model.PessoaInformacao;
import sosanimais.com.example.app.model.db.IDAL;
import sosanimais.com.example.app.model.db.SingletonDB;
import sosanimais.com.example.app.model.entity.Adotante;
import sosanimais.com.example.app.model.entity.Adotante;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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
                    INSERT INTO adotante(usu_id) VALUES ('#1');
                    """;

                sql = sql.replace("#1", "" + entidade.getId());
                //sql = sql.replace("#2", "" + entidade.getMatricula());

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

//        String sql = """
//                UPDATE adotante SET adotante_matricula = '#2' WHERE func_matricula=#1;
//                """;
//        sql = sql.replace("#1",""+ entidade.getMatricula());
//        sql = sql.replace("#w", "" + entidade.getMatricula());

//        return SingletonDB.getConexao().manipular(sql);
        return false;
    }

    @Override
    public boolean delete(Adotante entidade) {
        return SingletonDB.getConexao().manipular("DELETE FROM adotante WHERE adotante_matricula=" + entidade.getMatricula());
    }

    public boolean deletePessoa(Long id) {
        return SingletonDB.getConexao().manipular("DELETE FROM adotante WHERE usu_id=" + id);
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

        String sql;
        ResultSet resultSet;
//        int aux;
//        aux = Math.toIntExact(mat);

        sql = "SELECT * FROM adotante WHERE adotante_matricula =" + Math.toIntExact(mat);
        resultSet = SingletonDB.getConexao().consultar(sql);
        try {
            if (resultSet.next()) {

                return new Adotante(
                        resultSet.getLong("usu_id"),
                        null,
                        resultSet.getInt("adotante_matricula")
                );

            }


        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

    @Override
    public List<Adotante> get(String filtro) {

        List<Adotante> listaFunc = new ArrayList<>();
        ResultSet funcSet;

        try {
            String sql = "SELECT * FROM adotante";

            if(!filtro.isEmpty())
                sql+=" WHILE"+filtro;

            funcSet = SingletonDB.getConexao().consultar(sql);

            while (funcSet.next()) {
                listaFunc.add(
                        new Adotante(
                                funcSet.getLong("usu_id"),
                                null,
                                funcSet.getInt("adotante_matricula")
                        )
                );

            }

            return listaFunc;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

    }


    public Adotante findByPessoaId(Long id){
        String sql = "SELECT * FROM adotante WHERE usu_id = "+id;
        ResultSet adoSet = SingletonDB.getConexao().consultar(sql);

        try{
            if(adoSet.next() && !adoSet.wasNull()){
                return new Adotante(
                        adoSet.getLong("usu_id"),
                        null,
                        adoSet.getInt("adotante_matricula")
                );
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
