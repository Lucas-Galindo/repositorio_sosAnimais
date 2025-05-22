package sosanimais.com.example.app.model.DAL;

import sosanimais.com.example.app.model.Transferencia;
import sosanimais.com.example.app.model.db.SingletonDB;

public class TransferenciaDAL {
    /*
    Nome da tabela: Transferencia
    tb_id INTEGER PRIMARY KEY,
    tb_date DATE,
    funcionario_Func_Login VARCHAR(4000) NOT NULL,
    Adocao_Animal_adota_cod INTEGER,
    Func_Matricula INTEGER,
    CONSTRAINT Transferir_Baia_funcionario_FK FOREIGN KEY (Func_Matricula) REFERENCES funcionario(Func_Matricula),
    CONSTRAINT Transferir_Baia_Ado

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


    public boolean save(Transferencia elemento){
        String sql = """
                INSERT INTO transferencia(tb_date, func_matricula) values ('#1','#2');
                """;
        
        sql = sql.replace("#1",elemento.getData());
        sql = sql.replace("#2", ""+elemento.getMatFunc());

        return SingletonDB.getConexao().manipular(sql);
    }


}
