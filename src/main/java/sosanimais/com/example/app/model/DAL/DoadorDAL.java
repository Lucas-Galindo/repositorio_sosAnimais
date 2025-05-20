package sosanimais.com.example.app.model.DAL;

import sosanimais.com.example.app.model.PessoaInformacao;
import sosanimais.com.example.app.model.db.IDAL;
import sosanimais.com.example.app.model.db.SingletonDB;
import sosanimais.com.example.app.model.entity.Doador;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DoadorDAL implements IDAL<Doador>{
    public DoadorDAL() {
        super();
    }

    @Override
    public boolean save(Doador entidade) {

        String sql = "";

        ResultSet pessSet;
        pessSet = SingletonDB.getConexao().consultar(" SELECT * FROM pessoa WHERE pess_id =" + entidade.getId());
        try{
            if(!pessSet.wasNull()){
                sql = """
                    INSERT INTO doador(usu_id) VALUES ('#1');
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
    public boolean update(Doador entidade) {

//        String sql = """
//                UPDATE doador SET doador_matricula = '#2' WHERE func_matricula=#1;
//                """;
//        sql = sql.replace("#1",""+ entidade.getMatricula());
//        sql = sql.replace("#w", "" + entidade.getMatricula());

//        return SingletonDB.getConexao().manipular(sql);
        return false;
    }

    @Override
    public boolean delete(Doador entidade) {

        return SingletonDB.getConexao().manipular("DELETE FROM doador WHERE doador_matricula=" + entidade.getMatricula());
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
    public Doador get(Long mat) {

        String sql;
        ResultSet resultSet;
//        int aux;
//        aux = Math.toIntExact(mat);

        sql = "SELECT * FROM doador WHERE doador_matricula =" + Math.toIntExact(mat);
        resultSet = SingletonDB.getConexao().consultar(sql);
        try {
            if (resultSet.next()) {

                return new Doador(
                        resultSet.getLong("usu_id"),
                        null,
                        resultSet.getInt("doador_matricula")
                );

            }


        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

    @Override
    public List<Doador> get(String filtro) {

        List<Doador> listaFunc = new ArrayList<>();
        ResultSet funcSet;

        try {
            String sql = "SELECT * FROM doador";

            if(!filtro.isEmpty())
                sql+=" WHILE"+filtro;

            funcSet = SingletonDB.getConexao().consultar(sql);

            while (funcSet.next()) {
                listaFunc.add(
                        new Doador(
                                funcSet.getLong("usu_id"),
                                null,
                                funcSet.getInt("doador_matricula")
                        )
                );

            }

            return listaFunc;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

    }
}
