package sosanimais.com.example.app.model.DAL;

import sosanimais.com.example.app.model.Transferencia;
import sosanimais.com.example.app.model.db.SingletonDB;
import sosanimais.com.example.app.model.entity.Baias;

import java.sql.ResultSet;
import java.util.ArrayList;
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

    public boolean save(Transferencia elemento){
        String sql = """
                INSERT INTO transferencia(tb_date, func_matricula) values ('#1','#2');
                """;

        sql = sql.replace("#1",elemento.getData());
        sql = sql.replace("#2", ""+elemento.getMatFunc());

        return SingletonDB.getConexao().manipular(sql);
    }

    public boolean update(Transferencia entidade) {

        String sql = """
                UPDATE transferencia SET tb_date = #2', func_mat = '#3'  WHERE tb_id = #1;
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
//        int aux;
//        aux = Math.toIntExact(mat);

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




}
