package sosanimais.com.example.app.model.DAL;

import sosanimais.com.example.app.model.db.SingletonDB;
import sosanimais.com.example.app.model.entity.Baias;
import sosanimais.com.example.app.model.entity.Servico;

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

        
    }


}
