package sosanimais.com.example.app.model.db;

import sosanimais.com.example.app.model.entity.Funcionario;
import sosanimais.com.example.app.model.entity.Pessoa;

import java.sql.SQLException;
import java.util.List;

public interface IDAL<T>{

     boolean save(T entidade);
     public boolean update(T entidade);
     public boolean delete(T entidade);
     public T get(Long id);
     public List<T> get(String filtro) throws SQLException;

}
