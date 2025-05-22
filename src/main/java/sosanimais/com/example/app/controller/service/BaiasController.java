package sosanimais.com.example.app.controller.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sosanimais.com.example.app.model.entity.Baias;
import sosanimais.com.example.app.model.entity.Baias;
import sosanimais.com.example.app.model.util.Erro;

import java.util.List;


@RestController
@CrossOrigin("*")
@RequestMapping("/apis/baias")
public class BaiasController {

    BaiasService baiaService = new BaiasService();
    

    @PostMapping
    public ResponseEntity<Object> cadastro(@RequestBody Baias elemento) { // correto
        boolean aux = baiaService.cadastro(elemento);
        if (aux)
            return ResponseEntity.ok(elemento);
        return ResponseEntity.badRequest().body(new Erro("Erro salvar Baias"));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletar(@PathVariable Long id) { //correto
        boolean aux = baiaService.deletar(baiaService.getId(id));
        if (aux)
            return ResponseEntity.ok(aux);
        return ResponseEntity.badRequest().body(new Erro("Erro ao deletar Baias"));

    }


    
    @PutMapping
    public ResponseEntity<Object> atualizar(@RequestBody Baias entidade) {
        boolean aux = baiaService.atualizar(entidade);
        if (aux)
            return ResponseEntity.ok(aux);
        return ResponseEntity.badRequest().body(new Erro("Erro ao atualizar Baias"));
    }



    @GetMapping("/lista") // correto
    public ResponseEntity<Object> getBaiaLista() { //coreto
        List<Baias> lista = baiaService.getAll("");
        if (lista != null)
            return ResponseEntity.ok(lista);
        return ResponseEntity.badRequest().body(new Erro("Erro ao listar Baias"));
    }

    @GetMapping("/lista/{filtro}")
    public ResponseEntity<Object> getBaiaLista(@PathVariable String filtro) {
        List<Baias> lista = baiaService.getAll(filtro);
        if (lista != null)
            return ResponseEntity.ok(lista);
        return ResponseEntity.badRequest().body(new Erro("Erro ao listar Baias"));
    }

    @GetMapping("/busca-nome/{nome}") // correto
    public ResponseEntity<Object> getBaia(@PathVariable String nome) { //coreto
        Baias aux = baiaService.getNomeBaia(nome);
        if (aux != null)
            return ResponseEntity.ok(aux);
        return ResponseEntity.badRequest().body(new Erro("Erro ao listar Baias"));
    }
    
}