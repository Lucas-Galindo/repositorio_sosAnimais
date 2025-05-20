package sosanimais.com.example.app.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sosanimais.com.example.app.controller.service.AdotanteService;
import sosanimais.com.example.app.model.entity.Adotante;
import sosanimais.com.example.app.model.util.Erro;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/apis/adotante")
public class AdotanteController {

    AdotanteService adotaService = new AdotanteService();

    @PostMapping
    public ResponseEntity<Object> cadastro(@RequestBody Adotante elemento){ // correto
        boolean aux = adotaService.cadastro(elemento);
        if(aux)
            return ResponseEntity.ok(elemento);
        return ResponseEntity.badRequest().body( new Erro("Erro salvar Adotante"));
    }


    @DeleteMapping("/{mat}")
    public ResponseEntity<Object> deletar(@PathVariable Long mat) { //correto
        boolean aux = adotaService.deletar(adotaService.getId(mat));
        if(aux)
            return ResponseEntity.ok(aux);
        return ResponseEntity.badRequest().body(new Erro("Erro ao deletar Adotante"));

    }

    //Acho que aqui nao faz sentido atualização, nao tem porque sendo que so tem matricula
    @PutMapping
    public ResponseEntity<Object> atualizar(@RequestBody Adotante entidade){
        boolean aux = adotaService.atualizar(entidade);
        if(aux)
            return ResponseEntity.ok(aux);
        return ResponseEntity.badRequest().body(new Erro("Erro ao atualizar Adotante"));
    }


    @GetMapping("/{mat}") // correto
    public ResponseEntity<Object> getAdotaId(@PathVariable Long mat){
        Adotante aux = adotaService.getId(mat);
        if(aux!=null)
            return ResponseEntity.ok(aux);
        return ResponseEntity.badRequest().body(new Erro("Erro ao achar Adotante"));
    }

    @GetMapping("/lista") // correto
    public ResponseEntity<Object> getAdotaLista() { //coreto
        List<Adotante> lista = adotaService.getAll("");
        if(lista!=null)
            return ResponseEntity.ok(lista);
        return ResponseEntity.badRequest().body(new Erro("Erro ao listar Adotante"));
    }

    @GetMapping("/lista/{filtro}")
    public ResponseEntity<Object> getAdotaLista(@PathVariable String filtro) {
        List<Adotante> lista = adotaService.getAll(filtro);
        if(lista!=null)
            return ResponseEntity.ok(lista);
        return ResponseEntity.badRequest().body(new Erro("Erro ao listar Adotante"));
    }

    @GetMapping("/busca-pessoa/{idPessoa}")
    public ResponseEntity<Object> getPessoaId(@PathVariable Long idPessoa){
        Adotante aux = adotaService.getPessoaId(idPessoa);
        if(aux!=null)
            return ResponseEntity.ok(aux);
        return ResponseEntity.badRequest().body(new Erro("Não é adotante"));
    }









}
