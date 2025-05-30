package sosanimais.com.example.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sosanimais.com.example.app.controller.service.CategoriaService;
import sosanimais.com.example.app.model.entity.Categoria;
import sosanimais.com.example.app.model.util.Erro;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/apis/categoria")
public class CategoriaController {

    private final CategoriaService service;

    public CategoriaController(CategoriaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Object> getAll() {
        List<Categoria> list = service.buscarTodos();
        if (!list.isEmpty()) return ResponseEntity.ok(list);
        return ResponseEntity.badRequest().body(new Erro("Nenhuma categoria encontrada."));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        Categoria c = service.buscarPorId(id);
        if (c != null) return ResponseEntity.ok(c);
        return ResponseEntity.badRequest().body(new Erro("Categoria não encontrada, id=" + id));
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<Object> getByName(@PathVariable String nome) {
        List<Categoria> list = service.buscarComFiltro(
                "cat_nome = '" + nome.replace("'", "''") + "'"
        );
        if (!list.isEmpty()) return ResponseEntity.ok(list);
        return ResponseEntity.badRequest().body(new Erro("Nenhuma categoria com nome " + nome));
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Categoria c) {
        Categoria saved = service.salvar(c);
        if (saved != null) return ResponseEntity.ok(saved);
        return ResponseEntity.badRequest().body(new Erro("Erro ao salvar categoria."));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody Categoria c) {
        c.setId(id);
        Categoria upd = service.atualizar(c);
        if (upd != null) return ResponseEntity.ok(upd);
        return ResponseEntity.badRequest().body(new Erro("Erro ao atualizar categoria."));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        Categoria c = service.buscarPorId(id);
        if (c == null) return ResponseEntity.badRequest().body(new Erro("Categoria não encontrada."));
        if (service.deletar(c)) return ResponseEntity.ok(c);
        return ResponseEntity.badRequest().body(new Erro("Erro ao deletar categoria."));
    }
}
