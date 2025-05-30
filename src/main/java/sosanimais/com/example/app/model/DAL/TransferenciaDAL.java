package sosanimais.com.example.app.model.DAL;

import org.springframework.http.ResponseEntity;
import sosanimais.com.example.app.model.Transfere_to_Baia;
import sosanimais.com.example.app.model.Transferencia;
import sosanimais.com.example.app.model.db.SingletonDB;
import sosanimais.com.example.app.model.objetosAux.FiltrosTransferencia;

import java.text.SimpleDateFormat;


import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransferenciaDAL {
    /*
    Nome da tabela: Transferencia

    tb_id intenger primary key,
    tb_date date,
    func_mat integer,
     -- ani_cod intenger,
    CONSTRAINT Transferir_Baia_funcionario_FK FOREIGN KEY (func_mat) REFERENCES funcionario(Func_Matricula),
     -- CONSTRAINT Transferir_Baia_animal_FK FOREIGN KEY (ani_cod) REFERENCES funcionario(ani_cod)

     //Associativa -baia e transferencia
    tb_id intenger primary key,
    func_mat integer,
    ani_cod intenger,
    baia_id intenger,
    CONSTRAINT Transferir_Baia_funcionario_FK FOREIGN KEY (func_mat) REFERENCES funcionario(Func_Matricula),
    CONSTRAINT Transferir_Baia_animal_FK FOREIGN KEY (ani_cod) REFERENCES animal(ani_cod),
    CONSTRAINT Transferir_Baia_Baia_FK FOREIGN KEY (baia_id) REFERENCES baia(baia_id)
     */

    public TransferenciaDAL(){
        super();
    }

    public Transferencia saveTransfere(Transferencia elemento){
        String sql = """
                INSERT INTO transferencia(tb_date, func_mat) VALUES ('#1',#2);
                """;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String data = elemento.getData() != null ? sdf.format(elemento.getData()) : null;
        sql = sql.replace("#1", data);
        sql = sql.replace("#2", ""+elemento.getMatFunc());

        if(SingletonDB.getConexao().manipular(sql)){
            return findByDate(elemento.getData());
        }
        return null;
    }

    public boolean saveAssociativa(Transfere_to_Baia elemento){
        String sql = """
                INSERT INTO transferir_to_baia(ttb_destino,ttb_origem,tb_id,ani_id) VALUES (#1,#2,#3,#4);
                """;

        sql = sql.replace("#1", ""+elemento.getBaiaDestino());
        sql = sql.replace("#2", ""+elemento.getBaiaOrigem());
        sql = sql.replace("#3", ""+elemento.getTransfId());
        sql = sql.replace("#4", ""+elemento.getAniId());
        return SingletonDB.getConexao().manipular(sql);
    }


    public boolean update(Transferencia entidade) {

        String sql = """
                UPDATE transferencia SET tb_date = #2, func_mat = '#3'  WHERE tb_id = #1;
                """;
        sql = sql.replace("#2",""+ entidade.getData());
        sql = sql.replace("#3", ""+entidade.getMatFunc());
        sql = sql.replace("#1",""+ entidade.getId());

        return SingletonDB.getConexao().manipular(sql);

    }



    public boolean delete(Transferencia entidade) {
        return SingletonDB.getConexao().manipular("DELETE FROM transferencia WHERE tb_id =" + entidade.getId());
    }



    public Transferencia get(Long id) {

        String sql;
        ResultSet resultSet;

        sql = "SELECT * FROM transferencia WHERE tb_id =" + Math.toIntExact(id);
        resultSet = SingletonDB.getConexao().consultar(sql);
        try {
            if (resultSet.next()) {

                return new Transferencia(
                        resultSet.getLong("tb_id"),
                        resultSet.getDate("tb_date"),
                        resultSet.getInt("func_mat")
                );

            }


        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }


    public List<Transferencia> get(String filtro) {

        List<Transferencia> lista = new ArrayList<>();
        ResultSet resultSet;

        try {
            String sql = "SELECT * FROM transferencia";

            if(!filtro.isEmpty())
                sql+=" WHILE"+filtro;

            resultSet = SingletonDB.getConexao().consultar(sql);

            while (resultSet.next()) {
                lista.add(
                    new Transferencia(
                            resultSet.getLong("tb_id"),
                            resultSet.getDate("tb_date"),
                            resultSet.getInt("func_mat")
                    )
                );

            }

            return lista;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

    }


    public Transferencia findByDate(Date data){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dataFormatada = sdf.format(data);

            String sql = "SELECT * FROM transferencia WHERE tb_date = '" + dataFormatada + "'";

            ResultSet resultSet = SingletonDB.getConexao().consultar(sql);

            if(resultSet.next()){
                return new Transferencia(
                        resultSet.getLong("tb_id"),
                        resultSet.getDate("tb_date"),
                        resultSet.getInt("func_mat")
                );
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public Transferencia findByMat(int mat){
        String sql = "SELECT * FROM transferencia WHERE func_mat = " + mat;


        ResultSet resultSet = SingletonDB.getConexao().consultar(sql);
        try{
            if(resultSet.next()){
                return new Transferencia(
                        resultSet.getLong("tb_id"),
                        resultSet.getDate("tb_date"),
                        resultSet.getInt("func_mat")
                );
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }


    public List<Transferencia> searchTransferencia(Long id){
        String sql = """
            SELECT ttb.ttb_id AS Identificador,
                           ttb.tb_id AS Transferencia,
                           ttb.ani_id as Animal,
                           ttb.ttb_origem AS Origem,
                           ttb.ttb_destino AS Destino
                    FROM transferir_to_baia ttb
                    LEFT JOIN animal a ON ttb.ani_id = a.ani_cod
                    LEFT JOIN baia o ON ttb.ttb_origem = o.baia_id    -- Mesmo tabela, alias 'origem'
                    LEFT JOIN baia d ON ttb.ttb_destino = d.baia_id -- Mesmo tabela, alias 'destino' 
                    WHERE ttb.ttb_id = #1
        """;

        sql = sql.replace("#1",""+id);
        return null;
    }



    /* JSON DE COMO VAI CHEGAR NO CONTROLLER PROS DETALHES
    {
      "transferencia": {
        "id": 1,
        "data": "2025-05-27T10:30:00.000Z",
        "matFunc": 15
      },
      "detalhes": [
        {
          "ttb_id": 1,
          "ani_id": 5,
          "nomeAnimal": "Rex",
          "ttb_Origem": 2,
          "nomeOrigemBaia": "Baia Comum 02",
          "categoriaOrigem": "comum",
          "ttb_Destino": 8,
          "nomeDestinoBaia": "Baia Médica 01",
          "categoriaDestino": "medica"
        }
      ]
    }
     */

    // Pesquisa por id de transferencia - acesso a associativa
    public Transferencia searchDetailsTransfere(FiltrosTransferencia filtros) {

        StringBuilder sql = new StringBuilder("SELECT DISTINCT t.* FROM transferencia t ");

        if (filtros.getNomeFunc() != null) {
            sql.append("LEFT JOIN funcionario f ON t.func_mat = f.func_matricula ");
            sql.append("LEFT JOIN pessoa p ON f.usu_id = p.pess_id ");
        }

        if (filtros.getCategoriaBaias() != null) {
            sql.append("LEFT JOIN transferir_to_baia ttb ON t.tb_id = ttb.tb_id ");
            sql.append("LEFT JOIN baia b ON ttb.ttb_destino = b.baia_id OR ttb.ttb_origem = b.baia_id ");
        }

        sql.append("WHERE 1=1 ");
        return null;
    }

}
