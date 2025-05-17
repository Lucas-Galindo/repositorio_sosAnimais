package sosanimais.com.example.app.model.db;

import sosanimais.com.example.app.model.DAL.EmpresaDAL;
import sosanimais.com.example.app.model.entity.Empresa;

import java.util.List;

public interface IDAL<T>{
    

     boolean save(T entidade);
     public boolean update(T entidade);
     public boolean delete(T entidade);

    boolean save(Empresa entidade);

    boolean update(Empresa entidade);

    boolean delete(Empresa entidade);

    public T get(Long id);
     public List<EmpresaDAL> get(String filtro);

}
