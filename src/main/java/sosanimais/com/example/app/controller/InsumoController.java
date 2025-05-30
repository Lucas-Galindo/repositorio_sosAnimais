package sosanimais.com.example.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sosanimais.com.example.app.model.entity.Insumos;
import sosanimais.com.example.app.controller.service.InsumoService;
import sosanimais.com.example.app.model.util.Erro;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/apis/insumo")
public class InsumoController {
    private final InsumoService insumoService;
    public InsumoController(InsumoService insumoService) {
        this.insumoService = insumoService;
    }

    @GetMapping
    public ResponseEntity<Object> getAll() {
        List<Insumos> list = insumoService.buscarTodos();
        if (!list.isEmpty()) return ResponseEntity.ok(list);
        return ResponseEntity.badRequest().body(new Erro("Nenhum insumo encontrado."));
    }

    @GetMapping("/ids/{categoria1Id}/{categoria2Id}")
    public ResponseEntity<Object> getByCategoriaId(@PathVariable Long categoria1Id, @PathVariable Long categoria2Id) {
        List<Insumos>list=insumoService.buscaByCategoriaIds(categoria1Id,categoria2Id);
        if (!list.isEmpty()) return ResponseEntity.ok(list);
        return ResponseEntity.badRequest().body(new Erro("Nenhum insumo encontrado."));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        Insumos inst = insumoService.buscarPorId(id);
        if (inst != null) return ResponseEntity.ok(inst);
        return ResponseEntity.badRequest().body(new Erro("Insumo não encontrado, id=" + id));
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<Object> getByName(@PathVariable String nome) {
        List<Insumos> list = insumoService.buscarComFiltro(
                "insu_nome = '" + nome.replace("'","''") + "'"
        );
        if (!list.isEmpty()) return ResponseEntity.ok(list);
        return ResponseEntity.badRequest().body(new Erro("Nenhum insumo com nome " + nome));
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Insumos insumo) {
        Insumos saved = insumoService.salvar(insumo);
        if (saved != null) return ResponseEntity.ok(saved);
        return ResponseEntity.badRequest().body(new Erro("Erro ao salvar insumo."));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(
            @PathVariable Long id,
            @RequestBody Insumos insumo
    ) {
        insumo.setId(id);
        Insumos upd = insumoService.atualizar(insumo);
        if (upd != null) return ResponseEntity.ok(upd);
        return ResponseEntity.badRequest().body(new Erro("Erro ao atualizar insumo."));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        Insumos inst = insumoService.buscarPorId(id);
        if (inst == null)
            return ResponseEntity.badRequest().body(new Erro("Insumo não encontrado."));
        boolean removed = insumoService.deletar(inst);
        if (removed) return ResponseEntity.ok(inst);
        return ResponseEntity.badRequest().body(new Erro("Erro ao deletar insumo."));
    }
}