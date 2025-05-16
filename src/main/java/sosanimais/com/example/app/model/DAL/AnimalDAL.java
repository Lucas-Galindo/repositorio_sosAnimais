package sosanimais.com.example.app.model.DAL;

import org.springframework.stereotype.Repository;
import sosanimais.com.example.app.model.AnimalInformacao;
import sosanimais.com.example.app.model.db.IDAL;
import sosanimais.com.example.app.model.db.SingletonDB;
import sosanimais.com.example.app.model.entity.Animal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Repository
public class AnimalDAL implements IDAL<Animal>{
    public AnimalDAL() {
        super();
    }

    @Override
    public List<Animal> get(String filtro) {
        List<Animal> animals = new ArrayList<Animal>();
        String sql = "SELECT * FROM animals";
        if(!filtro.isEmpty())
            sql += " WHERE " + filtro;
        ResultSet rs = SingletonDB.getConexao().consultar(sql);
        try{
            while(rs.next()){
                Animal animal=new Animal(rs.getLong("animal_id"),
                        rs.getInt("animal_baia"),
                        rs.getInt("animal_acolhimento"),
                        (AnimalInformacao)rs.getObject("animal_informacao"));
                animals.add(animal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return animals;
    }

    @Override
    public boolean update(Animal entidade) {
        String sql= """
                UPDATE animal SET animal_baia='#2', animal_acolhimento='#3', animal_informacao='#4'
                WHERE animal_id='#1';
                """;
        sql=sql.replace("#1",""+entidade.getId());
        sql=sql.replace("#2",""+entidade.getIdBaia());
        sql=sql.replace("#3",""+entidade.getIdAcolhimento());
        sql=sql.replace("#4",""+entidade.getInformacao());
        return SingletonDB.getConexao().manipular(sql);
    }

    @Override
    public boolean delete(Animal entidade) {
        String sql="DELETE FROM animal WHERE id="+entidade.getId();
        return SingletonDB.getConexao().manipular(sql);
    }
/*
    @Override
    public List<Animal> get(String) {
        List<Animal> animals = new ArrayList<>();
        String sql = "SELECT * FROM animal";
        ResultSet rs = SingletonDB.getConexao().consultar(sql);
        try {
            while (rs.next()) {
                Animal animal = new Animal(
                        rs.getLong("animal_id"),
                        rs.getInt("animal_baia"),
                        rs.getInt("animal_acolhimento"),
                        (AnimalInformacao) rs.getObject("animal_informacao")
                );
                animals.add(animal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return animals;
    }
*/
    @Override
    public Animal get(int id) {
        Animal animal=null;
        String sql="select * from animal where id="+id;
        ResultSet rs=SingletonDB.getConexao().consultar(sql);
        try{
            if(rs.next()){
                animal=new Animal(rs.getLong("animal_id"),
                        rs.getInt("animal_baia"),
                        rs.getInt("animal_acolhimento"),
                        (AnimalInformacao)rs.getObject("animal_informacao"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return animal;
    }

    @Override
    public boolean save(Animal entidade) {
        String sql= """
                INSERT INTO animal(animal_id, animal_baia, animal_acolhimento, animal_informacao) VALUES ('#1','#2','#3','#4')
                """;
        sql=sql.replace("#1",""+entidade.getId());
        sql=sql.replace("#2",""+entidade.getIdBaia());
        sql=sql.replace("#3",""+entidade.getIdAcolhimento());
        sql=sql.replace("#4",""+entidade.getInformacao());
        return SingletonDB.getConexao().manipular(sql);
    }
}
