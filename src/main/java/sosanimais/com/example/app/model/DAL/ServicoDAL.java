package sosanimais.com.example.app.model.DAL;

import sosanimais.com.example.app.model.db.SingletonDB;
import sosanimais.com.example.app.model.entity.Baias;
import sosanimais.com.example.app.model.entity.Servico;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ServicoDAL {


    public boolean save(Servico elemento){

        String sql = """
                INSERT INTO servico(serv_nome,serv_desc) VALUES ('#1','#2');
                """;

        sql = sql.replace("#1",elemento.getNome());
        sql = sql.replace("#2",elemento.getDescricao());
        return SingletonDB.getConexao().manipular(sql);
    }

    public boolean update(Servico elemento){
        String sql = "UPDATE servico SET serv_nome = '#1', serv_desc = '#2' WHERE serv_cod = "+elemento.getCod();

        sql = sql.replace("#1",elemento.getNome());
        sql = sql.replace("#2", elemento.getDescricao());
        return SingletonDB.getConexao().manipular(sql);
    }

    public boolean delete(Servico entidade) {
        return SingletonDB.getConexao().manipular("DELETE FROM servico WHERE serv_cod=" + entidade.getCod());
    }

    public Servico get(Long cod){

        String sql = "SELECT * FROM servico WHERE serv_cod = "+cod;
        ResultSet resultSet = SingletonDB.getConexao().consultar(sql);
        try{
            if(resultSet.next()){
                return new Servico(
                        resultSet.getLong("serv_cod"),
                        resultSet.getString("serv_nome"),
                        resultSet.getString("serv_desc")
                );
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;

    }

    public List<Servico> get(String filtro){
        List<Servico> lista = new ArrayList<>();
        ResultSet resultSet;
        String sql;
        try{
            sql = "SELECT * FROM servico";
            if(!filtro.isEmpty())
                sql += "WHERE"+filtro;

            resultSet = SingletonDB.getConexao().consultar(sql);
            while(resultSet.next()){
                lista.add(
                  new Servico(
                          resultSet.getLong("serv_cod"),
                          resultSet.getString("serv_nome"),
                          resultSet.getString("serv_desc")
                  )
                );
            }
            return lista;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public Servico findByNome(String nome){
        String sql = "SELECT * FROM servico WHERE serv_nome = "+nome+"';";
        ResultSet resultSet = SingletonDB.getConexao().consultar(sql);

        try{
            if(resultSet.next()){
                return new Servico(
                        resultSet.getLong("serv_cod"),
                        resultSet.getString("serv_nome"),
                        resultSet.getString("serv_desc")
                );
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }


}
