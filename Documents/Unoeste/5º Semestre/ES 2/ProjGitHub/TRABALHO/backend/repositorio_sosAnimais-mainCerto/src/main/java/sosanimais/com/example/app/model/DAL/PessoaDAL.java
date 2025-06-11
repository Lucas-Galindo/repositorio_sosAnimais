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
public class PessoaDAL{

    public PessoaDAL(){
        super();
    }


    public boolean save(Pessoa entidade) {

        /*
        *
        *  Pess_ID INTEGER PRIMARY KEY,
        Pess_Nome VARCHAR(4000) NOT NULL,
        Pess_CPF VARCHAR(4000) NOT NULL,
        Pess_Telefone VARCHAR(4000) NOT NULL,
        Pess_Email VARCHAR(4000) NOT NULL*/
        String sql= """ 
                INSERT INTO pessoa (pess_nome, pess_cpf, pess_telefone, pess_email) VALUES ('#2','#3','#4','#5'); 
                """;
        //sql=sql.replace("#1","" +entidade.getId());
        sql=sql.replace("#2",entidade.getPessoa().getNome());
        sql=sql.replace("#3",entidade.getPessoa().getCpf());
        sql=sql.replace("#4",entidade.getPessoa().getTelefone());
        sql=sql.replace("#5",entidade.getPessoa().getEmail());


        return SingletonDB.getConexao().manipular(sql);
    }


    public boolean update(Pessoa entidade) {
//        System.out.println("***"+entidade.getId()+" "+entidade.getPessoa().getNome());
        String sql= """
            UPDATE pessoa SET pess_nome = '#2', pess_cpf = '#3', pess_email = '#5', pess_telefone = #4 WHERE pess_id = '#1';
            """;
        sql=sql.replace("'#2'",entidade.getPessoa().getNome());
        sql=sql.replace("'#3'",entidade.getPessoa().getCpf());
        sql=sql.replace("'#4'",entidade.getPessoa().getTelefone());
        sql=sql.replace("'#5'",entidade.getPessoa().getEmail());
        sql=sql.replace("'#1'","" +entidade.getId());
//        System.out.println("***"+sql);

//        System.out.println(SingletonDB.getConexao().manipular(sql));
        return SingletonDB.getConexao().manipular(sql);
    }


    public boolean delete(Pessoa entidade) {
        return SingletonDB.getConexao().manipular("DELETE FROM pessoa WHERE pess_id="+entidade.getId());
    }

    public PessoaInformacao pessoaInfo(ResultSet set){
        try{
            PessoaInformacao info = new PessoaInformacao(
                        set.getString("pess_nome"),
                        set.getString("pess_cpf"),
                        set.getString("pess_telefone"),
                        set.getString("pess_email")
            );
            return info;

        }catch(Exception e){
            return null;
        }
    }



    public Pessoa get(Long id) {
        Pessoa pessoa=null;

        String sql = "SELECT * FROM pessoa WHERE pess_id=" + Math.toIntExact(id);
        ResultSet resultSet=SingletonDB.getConexao().consultar(sql);
        try{
            if(resultSet.next()){
                PessoaInformacao aux = pessoaInfo(resultSet);
                pessoa =new Pessoa(
                        resultSet.getLong("pess_id"),
                        aux
                );
                return pessoa;
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }



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
                PessoaInformacao aux = pessoaInfo(resultSet);
                 pessoa = new Pessoa(
                        resultSet.getLong("pess_id"),
                        aux
                 );
                lista.add(pessoa);
            }
        }
        catch (Exception e){ e.printStackTrace();}

        return lista;
    }

    public Pessoa findByCPF(String cpf){
        ResultSet pessSet;
        String sql = "SELECT * FROM pessoa WHERE pess_cpf = '"+cpf+"'";
        pessSet = SingletonDB.getConexao().consultar(sql);
        try{
            if(pessSet.next() && !pessSet.wasNull()){
                PessoaInformacao aux = pessoaInfo(pessSet);
                return new Pessoa(
                        pessSet.getLong("pess_id"),
                        aux
                );
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
