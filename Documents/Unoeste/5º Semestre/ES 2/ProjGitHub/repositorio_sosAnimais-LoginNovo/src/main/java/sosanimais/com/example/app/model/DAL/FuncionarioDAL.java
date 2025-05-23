package sosanimais.com.example.app.model.DAL;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import sosanimais.com.example.app.controller.service.PessoaService;
import sosanimais.com.example.app.model.PessoaInformacao;
import sosanimais.com.example.app.model.db.IDAL;
import sosanimais.com.example.app.model.db.SingletonDB;
import sosanimais.com.example.app.model.entity.Funcionario;
import sosanimais.com.example.app.model.entity.Pessoa;


import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FuncionarioDAL implements IDAL<Funcionario> {


    public FuncionarioDAL() {
        super();
    }

    @Override
    public boolean save(Funcionario entidade) {

        /*
          {
            "id": 20,
            "pessoa": null,
            "matricula": 20011,
            "login": "jaoSilva",
            "senha": "12345",
            "role": "ADMIN"
           }
        */

        String sql = "";
        //Pessoa pessoa = new Pessoa();
        ResultSet pessSet;

        pessSet = SingletonDB.getConexao().consultar(" SELECT * FROM pessoa WHERE pess_id =" + entidade.getId());
        try{
            if(!pessSet.wasNull()){
                sql = """
                    INSERT INTO funcionario(usu_id,func_login, func_senha, func_role) VALUES ('#1','#2','#4', '#5');
                    """;

                sql = sql.replace("#1", "" + entidade.getId());
                sql = sql.replace("#2", entidade.getLogin());
                //sql = sql.replace("#3", "" + entidade.getMatricula());
                sql = sql.replace("#4", entidade.getSenha());
                sql = sql.replace("#5", entidade.getRole());
                return SingletonDB.getConexao().manipular(sql);
            }
            return false;

        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Funcionario entidade) {

        String sql = """
                UPDATE funcionario SET func_login='#2',func_senha='#3' WHERE func_matricula=#4;
                """;
        //sql = sql.replace("#1",""+ entidade.getMatricula());
        sql = sql.replace("#2", entidade.getLogin());
        sql = sql.replace("#3", entidade.getSenha());
        sql = sql.replace("#4", "" + entidade.getMatricula());

        return SingletonDB.getConexao().manipular(sql);
    }

    @Override
    public boolean delete(Funcionario entidade) {

        return SingletonDB.getConexao().manipular("DELETE FROM funcionario WHERE func_matricula=" + entidade.getMatricula());
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
    public Funcionario get(Long mat) {
        Funcionario func = null;
        String sql;
        ResultSet resultSet;
//        int aux;
//        aux = Math.toIntExact(mat);

        sql = "SELECT * FROM funcionario WHERE func_matricula =" + Math.toIntExact(mat);
        resultSet = SingletonDB.getConexao().consultar(sql);
        try {
            if (resultSet.next()) {

                func = new Funcionario(
                        resultSet.getLong("usu_id"),
                        null,
                        resultSet.getInt("func_matricula"),
                        resultSet.getString("func_login"),
                        resultSet.getString("func_senha"),
                        resultSet.getString("func_role")
                );
                return func;
            }


        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

    @Override
    public List<Funcionario> get(String filtro) {

        List<Funcionario> listaFunc = new ArrayList<>();
        ResultSet funcSet;
        //Funcionario func;
        try {
            String sql = "SELECT * FROM funcionario";

            if(!filtro.isEmpty())
                sql+=" WHILE"+filtro;

            funcSet = SingletonDB.getConexao().consultar(sql);

            while (funcSet.next()) {
                listaFunc.add(
                        new Funcionario(
                                funcSet.getLong("usu_id"),
                                null,
                                funcSet.getInt("func_matricula"),
                                funcSet.getString("func_login"),
                                funcSet.getString("func_senha"),
                                funcSet.getString("func_role")
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