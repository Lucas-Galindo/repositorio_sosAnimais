package sosanimais.com.example.app.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sosanimais.com.example.app.controller.service.AdotanteService;
import sosanimais.com.example.app.controller.service.FuncionarioService;
import sosanimais.com.example.app.model.entity.Adotante;
import sosanimais.com.example.app.model.entity.Funcionario;
import sosanimais.com.example.app.model.util.Erro;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value=("/apis/adotante"))
public class AdotanteController {

    AdotanteService funcService = new AdotanteService();

    @PostMapping
    public ResponseEntity<Object> cadastro(@RequestBody Adotante elemento){ // correto
        boolean aux = funcService.cadastro(elemento);
        if(aux)
            return ResponseEntity.ok(elemento);
        return ResponseEntity.badRequest().body( new Erro("Erro salvar Adotante"));
    }

    @GetMapping("/{mat}") // correto
    public ResponseEntity<Object> getFuncId(@PathVariable Long mat){
        Adotante aux = funcService.getId(mat);
        if(aux!=null)
            return ResponseEntity.ok(aux);
        return ResponseEntity.badRequest().body(new Erro("Erro ao achar Adotante"));
    }

    @GetMapping("/lista") // correto
    public ResponseEntity<Object> getFuncLista() { //coreto
        List<Adotante> lista = funcService.getAll("");
        if(lista!=null)
            return ResponseEntity.ok(lista);
        return ResponseEntity.badRequest().body(new Erro("Erro ao listar Adotante"));
    }

    @GetMapping("/lista/{filtro}")
    public ResponseEntity<Object> getFuncLista(@PathVariable String filtro) {
        List<Adotante> lista = funcService.getAll(filtro);
        if(lista!=null)
            return ResponseEntity.ok(lista);
        return ResponseEntity.badRequest().body(new Erro("Erro ao listar Adotante"));
    }


    @DeleteMapping("/{mat}")
    public ResponseEntity<Object> deletar(@PathVariable Long mat) { //correto
        boolean aux = funcService.deletar(funcService.getId(mat));
        if(aux)
            return ResponseEntity.ok(aux);
        return ResponseEntity.badRequest().body(new Erro("Erro ao deletar Adotante"));

    }

    //Acho que aqui nao faz sentido atualização, nao tem porque sendo que so tem matricula
    @PutMapping
    public ResponseEntity<Object> atualizar(@RequestBody Adotante entidade){
        boolean aux = funcService.atualizar(entidade);
        if(aux)
            return ResponseEntity.ok(aux);
        return ResponseEntity.badRequest().body(new Erro("Erro ao atualizar Adotante"));
    }





}
