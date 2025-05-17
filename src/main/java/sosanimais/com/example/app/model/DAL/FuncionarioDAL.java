package sosanimais.com.example.app.model.DAL;



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

    public FuncionarioDAL() {
        super();
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



    public PessoaInformacao pessoaInfo(ResultSet set){
        try{
            PessoaInformacao info = null;
            if(set.next()){
                info = new PessoaInformacao(
                        set.getString("pess_nome"),
                        set.getString("pess_cpf"),
                        set.getString("pess_telefone"),
                        set.getString("pess_email")
                );
            }

            return info;


        }catch(Exception e){
            return null;
        }
    }
    @Override
    public Funcionario get(Long mat) {

        Funcionario func=null;
        String sql = "";
        ResultSet resultSet;
        PessoaInformacao pessoaAux;
        sql = "SELECT * FROM funcionario WHERE func_matricula="+mat;
        resultSet = SingletonDB.getConexao().consultar(sql);
        try{
            if(resultSet.next()){
                //pessoa = pessoaService.getId(resultSet.getLong("usu_id"));

//                pessoaAux = new PessoaInformacao(
//                        resultSet.getString("pess_nome"),
//                        resultSet.getString("pess_cpf"),
//                        resultSet.getString("pess_email"),
//                        resultSet.getString("pess_telefone")
//                );
//

//                func = new Funcionario(
//                            resultSet.getLong("pess_id"),
//                            pessoaAux,
//                            resultSet.getInt("func_mat"),
//                            resultSet.getString("func_login"),
//                            resultSet.getString("func_senha")
//                            );

                func = new Funcionario(
                        resultSet.getLong("pess_id"),
                        pessoaInfo(resultSet),
                        resultSet.getInt("func_matricula"),
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
    public List<EmpresaDAL> get(String filtro) {
//
//        PessoaInformacao pessoaAux = null;
//        List<Funcionario> listaFunc = new ArrayList<>();
//        Funcionario func = null;
//        ResultSet PessSet, FuncSet;
//        String sql2;
//
//
//        //pessoaService = null;
//        PessoaInformacao infoPessoa = null;

//        String sql = "SELECT * FROM funcionario";
//        if(!filtro.isEmpty())
//            sql+="WHERE " + filtro;
//        FuncSet = SingletonDB.getConexao().consultar(sql);
//        try{
//            sql2 = "SELECT * FROM pessoa";
//            PessSet= SingletonDB.getConexao().consultar(sql2);
//            try{
//                while(PessSet.next()){
//                    while(FuncSet.next()){
//                        if(PessSet.getLong("pess_id") == FuncSet.getLong("usu_id")){
//                            func = new Funcionario(
//                                    FuncSet.getLong("usu_id"),
//                                    pessoaInfo(PessSet),
//                                    FuncSet.getInt("func_matricula"),
//                                    FuncSet.getString("func_login"),
//                                    FuncSet.getString("func_senha")
//                            );
//
//                            listaFunc.add(func);
//                        }
//
//
//                    }
//                }
//            }catch(Exception e){
//                return null;
//            }
//
//        }catch(Exception e){
//            return null;
//        }


       // String sql = "SELECT * FROM funcionario INNER JOIN pessoa ON pessoa.pess_id = usu_id;";

        ResultSet funcSet;
        List<Funcionario> listaFunc = new ArrayList<>();

        Funcionario func;
        try {
            String sql = "SELECT * FROM funcionario";
            funcSet = SingletonDB.getConexao().consultar(sql);

            while (funcSet.next()) {
                func = new Funcionario(
                        funcSet.getLong("usu_id"),
                        pessoaInfo(funcSet),
                        funcSet.getInt("func_matricula"),
                        funcSet.getString("func_login"),
                        funcSet.getString("func_senha")
                );
                listaFunc.add(func);
            }

            return listaFunc;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("****************");
            return null;
        }





    }




//    public Funcionario buscarLoginPorSenha(String login, String senha) {
//        Funcionario func = null;
//        String sql = "SELECT * FROM funcionario f INNER JOIN pessoa p ON f.pess_id = p.pess_id " +
//                "WHERE f.func_login = ? AND f.func_senha = ?";
//
//        try (PreparedStatement stmt = SingletonDB.getConexao().prepareStatement(sql)) {
//            stmt.setString(1, login);
//            stmt.setString(2, senha);
//            try (ResultSet rs = stmt.executeQuery()) {
//                if (rs.next()) {
//                    PessoaInformacao info = new PessoaInformacao(
//                            rs.getString("pess_nome"),
//                            rs.getString("pess_cpf"),
//                            rs.getString("pess_email"),
//                            rs.getString("pess_telefone")
//                    );
//                    func = new Funcionario(
//                            rs.getLong("pess_id"),
//                            info,
//                            rs.getInt("func_matricula"),
//                            rs.getString("func_login"),
//                            rs.getString("func_senha")
//                    );
//                }
//            }
//        } catch (SQLException e) {
//            System.err.println("Erro ao validar login: " + e.getMessage());
//        }
//        return func;
//    }

}
