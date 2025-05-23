package sosanimais.com.example.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sosanimais.com.example.app.model.entity.Doacao;
import sosanimais.com.example.app.controller.service.DoacaoService;
import sosanimais.com.example.app.model.util.Erro;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/doacoes")
public class DoacaoController {

    private final DoacaoService doacaoService = new DoacaoService();

    @PostMapping
    public ResponseEntity<Object> cadastrar(@RequestBody Doacao doacao) {
        boolean sucesso = doacaoService.cadastrar(doacao);
        if (sucesso) {
            return ResponseEntity.ok(doacao);
        }
        return ResponseEntity.badRequest().body(new Erro("Erro ao cadastrar doação"));
    }

    @PutMapping
    public ResponseEntity<Object> atualizar(@RequestBody Doacao doacao) {
        boolean sucesso = doacaoService.atualizar(doacao);
        if (sucesso) {
            return ResponseEntity.ok("Doação atualizada com sucesso");
        }
        return ResponseEntity.badRequest().body(new Erro("Erro ao atualizar doação"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletar(@PathVariable Long id) {
        boolean sucesso = doacaoService.deletar(id);
        if (sucesso) {
            return ResponseEntity.ok("Doação deletada com sucesso");
        }
        return ResponseEntity.badRequest().body(new Erro("Doação não encontrada ou erro ao deletar"));
    }

    @GetMapping("/lista")
    public ResponseEntity<Object> listarTodas() {
        List<Doacao> lista = doacaoService.listarTodas();
        if (lista != null && !lista.isEmpty()) {
            return ResponseEntity.ok(lista);
        }
        return ResponseEntity.badRequest().body(new Erro("Nenhuma doação encontrada"));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Object> buscarPorEmail(@PathVariable String email) {
        List<Doacao> lista = doacaoService.buscarPorEmail(email);
        if (lista != null && !lista.isEmpty()) {
            return ResponseEntity.ok(lista);
        }
        return ResponseEntity.badRequest().body(new Erro("Nenhuma doação encontrada para o e-mail informado"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarPorId(@PathVariable Long id) {
        Doacao doacao = doacaoService.buscarPorId(id);
        if (doacao != null) {
            return ResponseEntity.ok(doacao);
        }
        return ResponseEntity.badRequest().body(new Erro("Doação não encontrada"));
    }
}
