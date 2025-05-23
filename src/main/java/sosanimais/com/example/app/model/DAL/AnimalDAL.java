package sosanimais.com.example.app.model.DAL;

import org.springframework.stereotype.Repository;
import sosanimais.com.example.app.model.AnimalInformacao;
import sosanimais.com.example.app.model.db.IDAL;
import sosanimais.com.example.app.model.db.SingletonDB;
import sosanimais.com.example.app.model.entity.Animal;
import sosanimais.com.example.app.model.entity.Baias;

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
        List<Animal> animals = new ArrayList<>();
        String sql = "SELECT * FROM animal";
        if (!filtro.isEmpty())
            sql += " WHERE " + filtro;

        ResultSet rs = SingletonDB.getConexao().consultar(sql);
        try {
            while (rs.next()) {
                AnimalInformacao info = new AnimalInformacao(
                        rs.getString("ani_raca"),
                        rs.getString("ani_nome"),
                        rs.getString("ani_desc"),
                        rs.getInt("ani_idade"),
                        rs.getString("ani_status").charAt(0),
                        rs.getString("ani_statusvida").charAt(0)
                );

                Animal animal = new Animal(
                        rs.getLong("ani_cod"),
                        rs.getInt("baia_baia_cod"),
                        rs.getInt("acolhimento_aco_cod"),
                        info
                );

                animals.add(animal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return animals;
    }

    @Override
    public boolean update(Animal entidade) {
        String sql = """
                UPDATE animal SET ani_nome='#2', ani_raca='#3', ani_desc='#4', ani_status='#5', ani_idade='#6',
                ani_statusvida='#7', acolhimento_aco_cod=#8, baia_baia_cod='#9'
                WHERE ani_cod='#1'
                """;

        sql = sql.replace("#1", String.valueOf(entidade.getId()));
        sql = sql.replace("#2", entidade.getInformacao().getNome());
        sql = sql.replace("#3", entidade.getInformacao().getRaca());
        sql = sql.replace("#4", entidade.getInformacao().getDescricao());
        sql = sql.replace("#5", String.valueOf(entidade.getInformacao().getStatus()));
        sql = sql.replace("#6", String.valueOf(entidade.getInformacao().getIdade()));
        sql = sql.replace("#7", String.valueOf(entidade.getInformacao().getStatusVida()));
        if(entidade.getIdAcolhimento()==0){
            sql=sql.replace("#8","null");
        }
        else
            sql=sql.replace("#8",""+entidade.getIdAcolhimento());
        sql = sql.replace("#9", String.valueOf(entidade.getIdBaia()));
        return SingletonDB.getConexao().manipular(sql);
    }

    @Override
    public boolean delete(Animal entidade) {
        boolean sucesso = true;

        // Deleta da tabela adotante se houver relacionamento
        String sqlAdotante = "DELETE FROM adotante WHERE adocao_animal_adota_cod = (SELECT adocao_animal_adota_cod FROM adocao_animal WHERE animal_ani_cod = " + entidade.getId() + ")";
        SingletonDB.getConexao().manipular(sqlAdotante);
        System.out.println("Adotante: " + sqlAdotante);
        // Deleta da tabela adocao_animal (pode não existir e tudo bem)
        String sqlAdocao = "DELETE FROM adocao_animal WHERE animal_ani_cod = " + entidade.getId();
        System.out.println("Adocao: " + sqlAdocao);
        SingletonDB.getConexao().manipular(sqlAdocao);

        // Por fim, deleta o animal
        String sqlAnimal = "DELETE FROM animal WHERE ani_cod = " + entidade.getId();
        System.out.println("Animal: " + sqlAnimal);
        sucesso = SingletonDB.getConexao().manipular(sqlAnimal);
        String liberaId="SELECT setval('animal_id_seq', (SELECT MAX(ani_cod) FROM animal))";
        SingletonDB.getConexao().manipular(liberaId);
        return sucesso;
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
    public Animal get(Long id) {
        Animal animal = null;
        String sql = "SELECT * FROM animal WHERE ani_cod=" + id;
        ResultSet rs = SingletonDB.getConexao().consultar(sql);
        try {
            if (rs!=null && rs.next()) {
                AnimalInformacao info = new AnimalInformacao(
                        rs.getString("ani_raca"),
                        rs.getString("ani_nome"),
                        rs.getString("ani_desc"),
                        rs.getInt("ani_idade"),
                        rs.getString("ani_status").charAt(0),
                        rs.getString("ani_statusvida").charAt(0)
                );

                animal = new Animal(
                        rs.getLong("ani_cod"),
                        rs.getInt("baia_baia_cod"),
                        rs.getInt("acolhimento_aco_cod"),
                        info
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return animal;
    }

    @Override
    public boolean save(Animal entidade) {
        String sql= """
                INSERT INTO animal(ani_nome, ani_raca, ani_desc, ani_status, ani_idade, ani_statusVida, Acolhimento_aco_cod, Baia_baia_cod) VALUES
                 ('#2','#3','#4','#5',#6,'#7',#8,#9)
                """;
        sql=sql.replace("#2",entidade.getInformacao().getNome());
        sql=sql.replace("#3",entidade.getInformacao().getRaca());
        sql=sql.replace("#4",entidade.getInformacao().getDescricao());
        sql=sql.replace("#5",""+entidade.getInformacao().getStatus());
        sql=sql.replace("#6",""+entidade.getInformacao().getIdade());
        sql=sql.replace("#7",""+entidade.getInformacao().getStatusVida());
        if(entidade.getIdAcolhimento()==0){
            sql=sql.replace("#8","null");
        }
        else
            sql=sql.replace("#8",""+entidade.getIdAcolhimento());
        if(entidade.getIdBaia()==0)
            sql=sql.replace("#9","null");
        else
            sql=sql.replace("#9",""+entidade.getIdBaia());
        System.out.println("Enviando o SQL: " + sql);
        return SingletonDB.getConexao().manipular(sql);
    }



}
