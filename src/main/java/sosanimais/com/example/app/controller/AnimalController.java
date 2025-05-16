package sosanimais.com.example.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sosanimais.com.example.app.model.entity.Animal;
import sosanimais.com.example.app.controller.AnimalService;
import sosanimais.com.example.app.model.util.Erro;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(("/apis/animal"))
public class AnimalController {
    @Autowired
    AnimalService animalService;


    @GetMapping
    public ResponseEntity<Object>getAllAnimals(){
        List<Animal> animalList=new ArrayList<>();
        animalList=animalService.buscarTodos();
        if(!animalList.isEmpty()){
            return ResponseEntity.ok(animalList);
        }
        else{
            return ResponseEntity.badRequest().body(new Erro("Erro ao buscar os animals"));
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<Object> getAnimalById(@PathVariable int id){
        Animal aux=animalService.buscarPorId(id);
        if(aux!=null)
            return ResponseEntity.ok(aux);
        else
            return ResponseEntity.badRequest().body(new Erro("Erro ao buscar o animal de id: "+id));
    }
    @PostMapping
    public ResponseEntity<Object> addAnimal(@RequestBody Animal animal){
        Animal aux=animalService.salvarAnimal(animal);
        if(aux!=null)
            return ResponseEntity.ok(aux);
        else
            return ResponseEntity.badRequest().body(new Erro("Erro ao salvar o animal"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateAnimal(@PathVariable Long id, @RequestBody Animal animal){
        animal.setId(id);
        Animal aux=animalService.atualizarAnimal(animal);
        if(aux!=null)
            return ResponseEntity.ok(aux);
        else
            return ResponseEntity.badRequest().body(new Erro("Erro ao atualizar o animal"));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAnimal(@PathVariable int id){
        Animal aux=animalService.buscarPorId(id);
        if(aux!=null){
            boolean status=animalService.deletarAnimal(aux);
            if(status)
                return ResponseEntity.ok(aux);
            return ResponseEntity.badRequest().body(new Erro("Erro ao deletar o animal"));
        }
        return ResponseEntity.badRequest().body(new Erro("Erro ao deletar o animal"));
    }
}
