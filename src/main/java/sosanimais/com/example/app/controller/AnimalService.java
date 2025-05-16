package sosanimais.com.example.app.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sosanimais.com.example.app.model.DAL.AnimalDAL;
import sosanimais.com.example.app.model.entity.Animal;

import java.util.List;
@Service
public class AnimalService {
    @Autowired
    private AnimalDAL animalDAL;

    public Animal salvarAnimal(Animal animal) {
        boolean save = animalDAL.save(animal);
        if(save==true)
            return animal;
        return null;
    }

    public Animal atualizarAnimal(Animal animal) {
        boolean att=animalDAL.update(animal);
        if(att==true)
            return animal;
        return null;
    }

    public boolean deletarAnimal(Animal animal) {
        return animalDAL.delete(animal);
    }

    public Animal buscarPorId(int id) {
        return animalDAL.get(id);
    }

    public List<Animal> buscarTodos() {
        // Faz um cast da lista de Object para Animal
        return animalDAL.get("");
    }

    public List<Animal> buscarComFiltro(String filtroSQL) {
        return animalDAL.get(filtroSQL);
    }
}
