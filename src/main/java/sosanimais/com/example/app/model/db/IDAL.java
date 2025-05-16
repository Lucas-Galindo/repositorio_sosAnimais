package sosanimais.com.example.app.model.db;

import sosanimais.com.example.app.model.entity.Funcionario;
import sosanimais.com.example.app.model.entity.Pessoa;

import java.sql.SQLException;
import java.util.List;

public interface IDAL<T>{

     boolean save(T entidade);

    //Salva um funcionário junto com o vínculo à pessoa
    boolean save(Pessoa pessoa, Funcionario entidade) throws SQLException;

    public boolean update(T entidade);
     public boolean delete(T entidade);
     public T get(int id);

    //Deleta um funcionário
    boolean delete(Pessoa pessoa, Funcionario entidade);

    //Retorna um funcionário específico
    Funcionario get(Pessoa pessoa, int mat);

    public List<T> get(String filtro);

}
