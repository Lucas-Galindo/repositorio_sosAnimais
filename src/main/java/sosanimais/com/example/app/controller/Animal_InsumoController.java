package sosanimais.com.example.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import sosanimais.com.example.app.controller.service.Animal_InsumoService;
import sosanimais.com.example.app.model.Animal_Insumo;
import sosanimais.com.example.app.model.util.Erro;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/apis/animal_insumo")
public class Animal_InsumoController {

    private final Animal_InsumoService service;

    public Animal_InsumoController(Animal_InsumoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Object> getAll() {
        List<Animal_Insumo> list = service.buscarTodos();
        if (!list.isEmpty()) return ResponseEntity.ok(list);
        return ResponseEntity.badRequest().body(new Erro("Nenhum registro de Animal_Insumo encontrado."));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        Animal_Insumo a = service.buscarPorId(id);
        if (a != null) return ResponseEntity.ok(a);
        return ResponseEntity.badRequest().body(new Erro("Registro não encontrado, ai_id=" + id));
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Animal_Insumo a) {
        Animal_Insumo saved = service.salvar(a);
        if (saved != null) return ResponseEntity.ok(saved);
        return ResponseEntity.badRequest().body(new Erro("Erro ao salvar Animal_Insumo."));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id,
                                         @RequestBody Animal_Insumo a) {
        a.setAiId(id);
        Animal_Insumo upd = service.atualizar(a);
        if (upd != null) return ResponseEntity.ok(upd);
        return ResponseEntity.badRequest().body(new Erro("Erro ao atualizar Animal_Insumo."));
    }

    @GetMapping("/animal/{termo}")
    public ResponseEntity<?> getByAnimal(@PathVariable String termo) {
        List<Animal_Insumo> lista = service.buscarPorAnimal(termo);
        if (lista.isEmpty()) {
            return ResponseEntity.badRequest().body(new Erro("Nenhuma aplicação encontrada para: " + termo));
        }
        return ResponseEntity.ok(lista);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        Animal_Insumo a = service.buscarPorId(id);
        if (a == null) return ResponseEntity.badRequest().body(new Erro("Registro não encontrado."));
        if (service.deletar(a)) return ResponseEntity.ok(a);
        return ResponseEntity.badRequest().body(new Erro("Erro ao deletar Animal_Insumo."));
    }
}