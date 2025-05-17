package sosanimais.com.example.app.model.DAL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import sosanimais.com.example.app.controller.service.PessoaService;
import sosanimais.com.example.app.model.PessoaInformacao;
import sosanimais.com.example.app.model.db.IDAL;
import sosanimais.com.example.app.model.db.SingletonDB;
import sosanimais.com.example.app.model.entity.Pessoa;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PessoaDAL implements IDAL<Pessoa> {


    @Override
    public boolean save(Pessoa entidade) {

        /*
        *
        *  Pess_ID INTEGER PRIMARY KEY,
        Pess_Nome VARCHAR(4000) NOT NULL,
        Pess_CPF VARCHAR(4000) NOT NULL,
        Pess_Telefone VARCHAR(4000) NOT NULL,
        Pess_Email VARCHAR(4000) NOT NULL*/
        String sql= """ 
                INSERT INTO pessoa (pess_id,pess_nome, pess_cpf, pess_telefone,pess_email) VALUES ('#1','#2','#3','#4','#5'); 
                """;
        sql=sql.replace("#1","" +entidade.getId());
        sql=sql.replace("#2",entidade.getPessoa().getNome());
        sql=sql.replace("#3",entidade.getPessoa().getCpf());
        sql=sql.replace("#4",entidade.getPessoa().getTelefone());
        sql=sql.replace("#5",entidade.getPessoa().getEmail());

        return SingletonDB.getConexao().manipular(sql);
    }

    @Override
    public boolean update(Pessoa entidade) {
        String sql= """
            UPDATE pessoa SET pess_nome = #2, pess_cpf = #3, pess_telefone = #4,pess_email = #5 WHERE pess_id=#1;
            """;
        sql=sql.replace("#1","" +entidade.getId());
        sql=sql.replace("#2",entidade.getPessoa().getNome());
        sql=sql.replace("#3",entidade.getPessoa().getCpf());
        sql=sql.replace("#4",entidade.getPessoa().getTelefone());
        sql=sql.replace("#5",entidade.getPessoa().getEmail());

        return SingletonDB.getConexao().manipular(sql);

    }

    @Override
    public boolean delete(Pessoa entidade) {
        return SingletonDB.getConexao().manipular("DELETE FROM pessoa WHERE pess_id="+entidade.getId());

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
    public Pessoa get(Long id) {
        Pessoa pessoa=null;

        PessoaInformacao pessoaInfo = null;
        String sql = "SELECT * FROM pessoa WHERE pess_id="+id;
        ResultSet resultSet=SingletonDB.getConexao().consultar(sql);
        try{
            if(resultSet.next()){

                pessoa =new Pessoa(
                        resultSet.getLong("pess_id"),
                        pessoaInfo(resultSet)
                );
            }

            return pessoa;

        }catch(Exception e){
            return null;
        }


    }


    @Override
    public List<Pessoa> get(String filtro) {

        List<Pessoa> lista = new ArrayList<>();
        PessoaInformacao pessoaInfo =null;
        Pessoa pessoa = null;

        String sql="SELECT * FROM pessoa";
        if(!filtro.isEmpty())
            sql+=" WHERE "+filtro;
        try {
            ResultSet resultSet = SingletonDB.getConexao().consultar(sql);
            while (resultSet.next()) {

                 pessoa = new Pessoa(
                        resultSet.getLong("pess_id"),
                         pessoaInfo(resultSet)
                 );
                lista.add(pessoa);
            }
        }
        catch (Exception e){ e.printStackTrace();}


        return lista;
    }
}
