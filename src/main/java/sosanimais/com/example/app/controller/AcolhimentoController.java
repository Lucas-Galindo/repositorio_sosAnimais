package sosanimais.com.example.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sosanimais.com.example.app.controller.service.AcolhimentoService;
import sosanimais.com.example.app.controller.service.AnimalService;
import sosanimais.com.example.app.model.entity.Acolhimento;
import sosanimais.com.example.app.model.entity.Animal;
import sosanimais.com.example.app.model.util.Erro;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/apis/acolhimento")
public class AcolhimentoController {
    @Autowired
    private AcolhimentoService acolhimentoService;
    @Autowired
    private AnimalService animalService;

    @GetMapping
    public ResponseEntity<Object> getAllAcolhimentos(){//ok
        List<Acolhimento> acolhimentos=new ArrayList<>();
        try {
            acolhimentos=acolhimentoService.buscarTodos();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(!acolhimentos.isEmpty()){
            return ResponseEntity.ok(acolhimentos);
        }
        else{
            return ResponseEntity.badRequest().body(new Erro("Nenhum Acolhimento encontrado!"));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getAcById(@PathVariable Long id){//ok
        Acolhimento aux=acolhimentoService.buscarPorId(id);
        if(aux!=null)
            return ResponseEntity.ok(aux);
        else
            return ResponseEntity.badRequest().body(new Erro("Não foi possível encontrar o acolhimento de id: "+id));
    }

    @PostMapping
    public ResponseEntity<Object> addAcolhimento(@RequestBody Acolhimento ac){//ok
        try{
            Acolhimento aux=acolhimentoService.salvarAcolhimento(ac);
            Animal ani=animalService.buscarPorId(aux.getIdAnimal());
            if(ani==null)
                return ResponseEntity.badRequest().body(new Erro("Animal com id " + aux.getIdAnimal() + " não encontrado."));
            if(aux!=null){
                Animal animal=animalService.buscarPorId(aux.getIdAnimal());
                System.out.println("id Animal: "+aux.getIdAnimal());
                animal.setIdAcolhimento(aux.getId());
                System.out.println("id Acolhimento para animal: "+aux.getId());
                animalService.atualizarAnimal(animal);
                return ResponseEntity.ok(aux);
            }
            else
                return ResponseEntity.badRequest().body(new Erro("Erro ao salvar o acolhimento"));
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body(new Erro(e.getMessage()));
        }
    }

    @PutMapping("/{id}")//ok
    public ResponseEntity<Object> updateAcolhimento(@PathVariable Long id, @RequestBody Acolhimento ac){
        try{
            ac.setId(id);
            Acolhimento aux=acolhimentoService.atualizarAcolhimento(ac);
            if(aux!=null)
                return ResponseEntity.ok(aux);
            else
                return ResponseEntity.badRequest().body(new Erro("Erro ao atualizar o acolhimento"));
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body(new Erro(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAcolhimento(@PathVariable Long id) {//ok
        Acolhimento aux = acolhimentoService.buscarPorId(id);
        if (aux != null) {
            boolean status = acolhimentoService.deletarAcolhimento(aux);
            if (status)
                return ResponseEntity.ok(aux);
        }
        return ResponseEntity.badRequest().body(new Erro("Erro ao deletar o acolhimento!"));
    }

}
