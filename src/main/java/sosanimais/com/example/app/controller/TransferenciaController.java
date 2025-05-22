package sosanimais.com.example.app.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sosanimais.com.example.app.controller.service.TransferenciaService;
import sosanimais.com.example.app.model.Transferencia;
import sosanimais.com.example.app.model.util.Erro;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/apis/transfere")
public class TransferenciaController {



    TransferenciaService transfereService = new TransferenciaService();

    @PostMapping
    public ResponseEntity<Object> cadastro(@RequestBody Transferencia elemento) { // correto
        boolean aux = transfereService.cadastro(elemento);
        if (aux)
            return ResponseEntity.ok(elemento);
        return ResponseEntity.badRequest().body(new Erro("Erro salvar Transferencia"));
    }

    @GetMapping("/{id}") // correto
    public ResponseEntity<Object> getFuncId(@PathVariable Long id) {
        Transferencia aux = transfereService.getId(id);
        if (aux != null)
            return ResponseEntity.ok(aux);
        return ResponseEntity.badRequest().body(new Erro("Erro ao achar Transferencia"));
    }

    @GetMapping("/lista") // correto
    public ResponseEntity<Object> getFuncLista() { //coreto
        List<Transferencia> lista = transfereService.getAll("");
        if (lista != null)
            return ResponseEntity.ok(lista);
        return ResponseEntity.badRequest().body(new Erro("Erro ao listar Transferencia"));
    }

    @GetMapping("/lista/{filtro}")
    public ResponseEntity<Object> getFuncLista(@PathVariable String filtro) {
        List<Transferencia> lista = transfereService.getAll(filtro);
        if (lista != null)
            return ResponseEntity.ok(lista);
        return ResponseEntity.badRequest().body(new Erro("Erro ao listar Transferencia"));
    }



    @DeleteMapping("/exclusao-transfere/{id}")
    public ResponseEntity<Object> deletarPess(@PathVariable Long id) { //correto
        boolean aux = transfereService.deletarPess(id);
        if (aux)
            return ResponseEntity.ok(aux);
        return ResponseEntity.badRequest().body(new Erro("Erro ao deletar Transferencia"));

    }

    @PutMapping
    public ResponseEntity<Object> atualizar(@RequestBody Transferencia entidade) { //correto
        boolean aux = transfereService.atualizar(entidade);
        if (aux)
            return ResponseEntity.ok(aux);
        return ResponseEntity.badRequest().body(new Erro("Erro ao atualizar Transferencia"));
    }

  

}
