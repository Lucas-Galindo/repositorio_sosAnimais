package sosanimais.com.example.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sosanimais.com.example.app.model.entity.Animal;
import sosanimais.com.example.app.controller.service.AnimalService;
import sosanimais.com.example.app.model.entity.Baias;
import sosanimais.com.example.app.model.util.Erro;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(("/apis/animal"))
public class AnimalController {

    private AnimalService animalService;
    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @GetMapping
    public ResponseEntity<Object>getAllAnimals(){//ok
        List<Animal> animalList=new ArrayList<>();
        animalList=animalService.buscarTodos();
        if(!animalList.isEmpty()){
            return ResponseEntity.ok(animalList);
        }
        else{
            return ResponseEntity.badRequest().body(new Erro("Erro ao buscar os animais"));
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<Object> getAnimalById(@PathVariable Long id){//ok
        Animal aux=animalService.buscarPorId(id);
        if(aux!=null)
            return ResponseEntity.ok(aux);
        else
            return ResponseEntity.badRequest().body(new Erro("Erro ao buscar o animal de id: "+id));
    }


    @PostMapping
    public ResponseEntity<Object> addAnimal(@RequestBody Animal animal){//ok
        Animal aux=animalService.salvarAnimal(animal);
        if(aux!=null)
            return ResponseEntity.ok(aux);
        else
            return ResponseEntity.badRequest().body(new Erro("Erro ao salvar o animal"));
    }

    @PutMapping("/{id}")//ok
    public ResponseEntity<Object> updateAnimal(@PathVariable Long id, @RequestBody Animal animal){
        animal.setId(id);
        Animal aux=animalService.atualizarAnimal(animal);
        if(aux!=null)
            return ResponseEntity.ok(aux);
        else
            return ResponseEntity.badRequest().body(new Erro("Erro ao atualizar o animal"));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAnimal(@PathVariable Long id) {
        Animal aux = animalService.buscarPorId(id);
        if (aux != null) {
            boolean status = animalService.deletarAnimal(aux);
            if (status)
                return ResponseEntity.ok(aux);
            return ResponseEntity.badRequest().body(new Erro("Animal não pode ser deletado, pois está presente na baia e/ou pertence a um acolhimento!"));
        }
        return ResponseEntity.badRequest().body(new Erro("Animal não encontrado!"));
    }
}
