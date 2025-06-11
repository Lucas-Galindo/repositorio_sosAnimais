package sosanimais.com.example.app.controller.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sosanimais.com.example.app.model.DAL.AnimalDAL;
import sosanimais.com.example.app.model.DAL.BaiasDAL;
import sosanimais.com.example.app.model.entity.Animal;
import sosanimais.com.example.app.model.entity.Baias;

import java.util.List;
@Service
public class AnimalService {
    @Autowired
    private AnimalDAL animalDAL;

    @Autowired
    private BaiasService baiasService;


    public Animal salvarAnimal(Animal animal) {
        boolean save = animalDAL.save(animal);
        boolean atualizaBaia;
        if(animal.getIdBaia()!=null) {
            Baias baia=baiasService.getId(animal.getIdBaia());
            baia.setQuantidadeAnimais(baia.getQuantidadeAnimais()+1);
            atualizaBaia=baiasService.atualizar(baia);
        }
        if(save==true)
            return animal;
        return null;
    }

    public Animal atualizarAnimal(Animal animal) {
        Animal existente = animalDAL.get(animal.getId());

        if(animal.getIdBaia()!=null) {
            Baias baia1=baiasService.getId(existente.getIdBaia());
            baia1.setQuantidadeAnimais(baia1.getQuantidadeAnimais()-1);
            baiasService.atualizar(baia1);
            Baias baia2=baiasService.getId(animal.getIdBaia());
            baia2.setQuantidadeAnimais(baia2.getQuantidadeAnimais()+1);
            baiasService.atualizar(baia2);
        }

        if (animal.getIdAcolhimento() == null || animal.getIdAcolhimento() == 0) {
            animal.setIdAcolhimento(existente.getIdAcolhimento());
        }

        if (animal.getIdBaia() == null || animal.getIdBaia() == 0) {
            animal.setIdBaia(existente.getIdBaia());
        }

        // Atualiza o animal
        boolean att = animalDAL.update(animal);
        if (att) {
            return animal;
        }
        return null;
    }

    public boolean deletarAnimal(Animal animal) {
        return animalDAL.delete(animal);
    }

    public Animal buscarPorId(Long id) {
        return animalDAL.get(id);
    }

    public List<Animal> buscarTodos() {
        return animalDAL.get("");
    }


    public List<Animal> buscarComFiltro(String filtroSQL) {
        return animalDAL.get(filtroSQL);
    }
}
