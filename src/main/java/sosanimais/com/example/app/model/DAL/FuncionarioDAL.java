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


    private PessoaService pessoaService;
    public FuncionarioDAL(PessoaService pessoaService){
        this.pessoaService = pessoaService;
   }
    @Override
    public boolean save(Funcionario entidade){
        String sql = "";
        Pessoa pessoa = new Pessoa();
        ResultSet resultSet;

        String sqlAux = """
                    SELECT * FROM pessoa WHERE entidade.usu_id = pess_id
                """;
        resultSet =SingletonDB.getConexao().consultar(sqlAux);
        try{
            if(resultSet.next()){
                pessoa.setId(resultSet.getLong("pess_id"));
            }

            sql= """ 
                INSERT INTO funcionario(usu_id,func_login, func_matricula, func_senha) VALUES ('#1','#2','#3','#4'); """;

            sql=sql.replace("#1","" +pessoa.getId());
            sql=sql.replace("#3",""+entidade.getMatricula());
            sql=sql.replace("#2", entidade.getLogin());
            sql=sql.replace("#4",entidade.getSenha());

            return SingletonDB.getConexao().manipular(sql);
        }catch(Exception e){
           return false;
        }


    }

    @Override
    public boolean update(Funcionario entidade) {

        String sql= """
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
        //SingletonDB.getConexao().manipular("DELETE FROM pessoa WHERE pess_id="+entidade.getId());
        return SingletonDB.getConexao().manipular("DELETE FROM funcionario WHERE func_matricula="+entidade.getMatricula());
    }

    @Override
    public Funcionario get(Long mat) {

        Funcionario func=null;
        String sql = "";
        ResultSet resultSet;
//        Pessoa pessoa = null;
        PessoaInformacao pessoaAux;


//        int matAux = 0;
//        String loginAux= "";
//        String senhaAux="";

        sql = "SELECT * FROM funcionario WHERE func_matricula="+mat;
        resultSet = SingletonDB.getConexao().consultar(sql);
        try{
            if(resultSet.next()){
                //pessoa = pessoaService.getId(resultSet.getLong("usu_id"));

                pessoaAux = new PessoaInformacao(
                        resultSet.getString("pess_nome"),
                        resultSet.getString("pess_cpf"),
                        resultSet.getString("pess_email"),
                        resultSet.getString("pess_telefone")
                );
//

                func = new Funcionario(
                            resultSet.getLong("pess_id"),
                            pessoaAux,
                            resultSet.getInt("func_mat"),
                            resultSet.getString("func_login"),
                            resultSet.getString("func_senha")
                            );
            }
            return func;

        }catch(Exception e) {
            return null;
        }

    }

    @Override
    public List<Funcionario> get(String filtro) {

        PessoaInformacao pessoaAux = null;
        List<Funcionario> listaFunc = new ArrayList<>();
        Funcionario func = null;
        ResultSet aux, resultSet;

        //pessoaService = null;
        PessoaInformacao infoPessoa = null;

        String sql = "SELECT * FROM funcionario";
        if(!filtro.isEmpty())
            sql+="WHERE " + filtro;
        try{
            resultSet = SingletonDB.getConexao().consultar(sql);
            while(resultSet.next()){
                infoPessoa = pessoaService.getId(resultSet.getLong("usu_id")).getPessoa();
                func = new Funcionario(
                        resultSet.getLong("usu_id"),
                        infoPessoa,
                        resultSet.getInt("func_matricula"),
                        resultSet.getString("func_login"),
                        resultSet.getString("func_senha")
                );
                listaFunc.add(func);
            }

        }catch(Exception e){
            return null;
        }

        return listaFunc;
    }

}
