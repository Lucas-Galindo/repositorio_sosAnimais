package sosanimais.com.example.app.model.DAL;

import org.springframework.stereotype.Repository;
import sosanimais.com.example.app.controller.service.EmpresaService;
import sosanimais.com.example.app.model.Endereco;
import sosanimais.com.example.app.model.InfoEmpresa;
import sosanimais.com.example.app.model.PessoaInformacao;
import sosanimais.com.example.app.model.db.IDAL;
import sosanimais.com.example.app.model.db.SingletonDB;
import sosanimais.com.example.app.model.entity.Funcionario;
import sosanimais.com.example.app.model.entity.informacao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EmpresaDAL implements IDAL<EmpresaDAL> {

    @Override
    public boolean save(InfoEmpresa entidade){
        String sql = "";
        InfoEmpresa info = new InfoEmpresa();
        ResultSet resultSet;

        String sqlAux = """
                    SELECT * FROM informacao WHERE entidade.info_id = info_id
                """;
        resultSet =SingletonDB.getConexao().consultar(sqlAux);
        try{
            if(resultSet.next()){
                info.(resultSet.getLong("info_id"));
            }

            sql= """ 
                INSERT INTO informacao(info_id, bairro, numero, cep, telefone) VALUES ('#1','#2','#3','#4','#5'); """;

            sql=sql.replace("#1","" +entidade.getId());
            sql=sql.replace("#3",""+entidade.getEndereco().getRua());
            sql=sql.replace("#2",entidade.getEndereco().getNumero());
            sql=sql.replace("#4",entidade.getEndereco().getCep());
            sql=sql.replace("#5",entidade.getTelefone());

            return SingletonDB.getConexao().manipular(sql);
        }catch(Exception e){
            return false;
        }


    }

    @Override
    public boolean update(InfoEmpresa entidade) {

        String sql= """
            UPDATE informacao SET info_bairro='#2',info_numero='#3',info_cep='#4' WHERE info_id=#5;
            """;
        //sql = sql.replace("#1",""+ entidade.getMatricula());
        sql = sql.replace("#2","" + entidade.getDados().getRua());
        sql = sql.replace("#3",""+ entidade.getEndereco().getNumero());
        sql = sql.replace("#4", "" + entidade.getDados().getCep());
        sql = sql.replace("#5", ""+ entidade.getId());


        return SingletonDB.getConexao().manipular(sql);
    }

    @Override
    public boolean delete(InfoEmpresa entidade) {
        //SingletonDB.getConexao().manipular("DELETE FROM pessoa WHERE pess_id="+entidade.getId());
        return SingletonDB.getConexao().manipular("DELETE FROM informacao WHERE info_id="+entidade.getId());
    }

    @Override
    public EmpresaDAL get(Long mat) {
        informacao id = null;
        Endereco end = null;
        String sql = "";
        ResultSet resultSet;
        informacao infoAux;
        Endereco endAux;

        sql = "SELECT * FROM informacao WHERE info_id=" + mat;
        resultSet = SingletonDB.getConexao().consultar(sql);
        try {
            if (resultSet.next()) {

                infoAux = new informacao(
                        resultSet.getInt("info_id"),
                        resultSet.getString("info_bairro"),
                        resultSet.getInt("info_cep"),
                        resultSet.getString("info_telefone")
                );
            }
        } catch (Exception e) {
            return null;
        }
        return infoAux.getInfo_id();
    }
//    } catch (
//    SQLException e) {
//        throw new RuntimeException(e);
//    }

    @Override
    public List<EmpresaDAL> get(String filtro) {

        PessoaInformacao pessoaAux = null;
        List<Endereco> lista = new ArrayList<>();
        InfoEmpresa info = null;
        ResultSet aux, resultSet;

        //pessoaService = null;
        PessoaInformacao info2 = null;

        String sql = "SELECT * FROM informacao";
        if(!filtro.isEmpty())
            sql+="WHERE " + filtro;
        try{
            resultSet = SingletonDB.getConexao().consultar(sql);
            while(resultSet.next()){
                info2 = EmpresaService.getId(resultSet.getLong("info_id"));
                Endereco endereco = new Endereco(
                        resultSet.getString("info_rua"),
                        resultSet.getInt("info_numero"),
                        resultSet.getString("info_cep"),
                        resultSet.getString("info_complemento")
                );
                lista.add(endereco);
            }

        }catch(Exception e){
            return null;
        }

        return lista;
    }

}
