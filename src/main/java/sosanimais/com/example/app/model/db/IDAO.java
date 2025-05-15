package sosanimais.com.example.app.model.db;
import java.util.List;
public interface IDAO <T>{
    
     public boolean save(T entidade);
     public boolean update(T entidade);
     public boolean  delete(T entidade);
     public List<Object> getAll();
     public boolean get(int id);
     public List<T> get(String filtro);

}
