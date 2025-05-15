package sosanimais.com.example.app.controller.services;


<<<<<<< HEAD
import sosanimais.com.example.app.model.PessoaInformacao;
import sosanimais.com.example.app.model.db.IDAL;
import sosanimais.com.example.app.model.db.SingletonDB;
import sosanimais.com.example.app.model.entity.Funcionario;
import sosanimais.com.example.app.model.entity.Pessoa;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioService implements IDAL<Funcionario> {

    PessoaService pessoaSerivce;


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

            /*Usu_id INTENGER PRIMARY KEY,
            Func_Login VARCHAR(4000) NOT NULL,
            Func_Matricula INTEGER UNIQUE
            Func_Senha
            */

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
    public Funcionario get( int mat) {
        Funcionario func=null;
        String sql = "", sql2 = "";
        ResultSet resultSet;

        int matAux = 0;
        String loginAux= "";
        String senhaAux="";

        //sql = "SELECT * FROM funcionario INNER JOIN pessoa ON funcionario.usu_id = pessoa.pess_id";
        sql = "SELECT * FROM funcionario WHERE func_matricula = "+ mat;
        resultSet =SingletonDB.getConexao().consultar(sql);
        try{
            if (resultSet.next()) {
                matAux = resultSet.getInt("func_matricula");
                loginAux = resultSet.getString("func_login");
                senhaAux = resultSet.getString("func_senha");
            }

            sql2 = "SELECT * FROM pessoa WHERE pess_id = "+ resultSet.getLong("pess_id");
            resultSet =SingletonDB.getConexao().consultar(sql2);
            try{

                if (resultSet.next()) {
                    PessoaInformacao pessoaAux = new PessoaInformacao();
                    pessoaAux.setNome(resultSet.getString("pess_nome"));
                    pessoaAux.setCpf(resultSet.getString("pess_cpf"));
                    pessoaAux.setEmail(resultSet.getString("pess_email"));
                    pessoaAux.setTelefone(resultSet.getString("pess_telefone"));


                    /*Endereco enderecoAux = new Endereco();
                    enderecoAux*/

                    func = new Funcionario(
                            resultSet.getLong("pess_id"),
                            pessoaAux,
                            matAux,
                            loginAux,
                            senhaAux
                            );
                }

            }catch(Exception e){
                return null;
            }

        }catch(Exception e){
            return null;
        }

        return func;
    }

    @Override
    public List<Funcionario> get(String filtro) {

        PessoaInformacao pessoaAux = null;
        List<Funcionario> listaFunc = new ArrayList<>();
        Funcionario func = null;
        ResultSet aux, resultSet;

        String sqlFunc = "SELECT * FROM funcionario ";
        String sqlPess = "SELECT * FROM pessoas";
        aux = SingletonDB.getConexao().consultar(sqlPess);
        try {

            if (!filtro.isEmpty())
                sqlFunc+=" WHERE "+filtro;

                while (aux.next()) {

                    resultSet = SingletonDB.getConexao().consultar(sqlFunc);
                    if (resultSet.next()) {

                        if (resultSet.getLong("usu_id") == aux.getLong("pess_id")) {

                            //int idPess = aux.getLong("pess_id");
                            pessoaAux = new PessoaInformacao(
                                    aux.getString("pess_nome"),
                                    aux.getString("pess_cpf"),
                                    aux.getString("pess_telefone"),
                                    aux.getString("pess_email")
                            );

                            func = new Funcionario(
                                    resultSet.getLong("usu_id"),
                                    pessoaAux,
                                    resultSet.getInt("func_matricula"),
                                    resultSet.getString("func_login"),
                                    resultSet.getString("func_senha")
                            );

                            listaFunc.add(func);

                        }
                    }

                }



        } catch (Exception e) {
            return null;
        }

        return listaFunc;
=======
import org.springframework.http.ResponseEntity;
import sosanimais.com.example.app.model.db.IDAO;
import sosanimais.com.example.app.model.entity.Funcionario;

import java.util.List;

public class FuncionarioService implements IDAO{


    @Override
    public boolean save(Object entidade) {
        //metodo
        return false;
    }

    @Override
    public boolean update(Object entidade) {
        return false;
    }

    @Override
    public boolean delete(Object entidade) {
        return false;
    }

    @Override
    public List<Object> getAll() {
        return List.of();
    }

    @Override
    public boolean get(int id) {
        return false;
    }

    @Override
    public List get(String filtro) {
        return List.of();
>>>>>>> c79675591135cd43030da201dac6a938754b568b
    }
}
