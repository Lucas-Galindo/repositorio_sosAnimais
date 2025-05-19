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
@RequestMapping(value=("/apis/doador"))
public class DoadorController {

    DoadorService funcService = new DoadorService();

    @PostMapping
    public ResponseEntity<Object> cadastro(@RequestBody Doador elemento){ // correto
        boolean aux = funcService.cadastro(elemento);
        if(aux)
            return ResponseEntity.ok(elemento);
        return ResponseEntity.badRequest().body( new Erro("Erro salvar Doador"));
    }

    @GetMapping("/{mat}") // correto
    public ResponseEntity<Object> getFuncId(@PathVariable Long mat){
        Doador aux = funcService.getId(mat);
        if(aux!=null)
            return ResponseEntity.ok(aux);
        return ResponseEntity.badRequest().body(new Erro("Erro ao achar Doador"));
    }

    @GetMapping("/lista") // correto
    public ResponseEntity<Object> getFuncLista() { //coreto
        List<Doador> lista = funcService.getAll("");
        if(lista!=null)
            return ResponseEntity.ok(lista);
        return ResponseEntity.badRequest().body(new Erro("Erro ao listar Doador"));
    }

    @GetMapping("/lista/{filtro}")
    public ResponseEntity<Object> getFuncLista(@PathVariable String filtro) {
        List<Doador> lista = funcService.getAll(filtro);
        if(lista!=null)
            return ResponseEntity.ok(lista);
        return ResponseEntity.badRequest().body(new Erro("Erro ao listar Doador"));
    }


    @DeleteMapping("/{mat}")
    public ResponseEntity<Object> deletar(@PathVariable Long mat) { //correto
        boolean aux = funcService.deletar(funcService.getId(mat));
        if(aux)
            return ResponseEntity.ok(aux);
        return ResponseEntity.badRequest().body(new Erro("Erro ao deletar Doador"));

    }

    //Acho que aqui nao faz sentido atualização, nao tem porque sendo que so tem matricula
    @PutMapping
    public ResponseEntity<Object> atualizar(@RequestBody Doador entidade){
        boolean aux = funcService.atualizar(entidade);
        if(aux)
            return ResponseEntity.ok(aux);
        return ResponseEntity.badRequest().body(new Erro("Erro ao atualizar Doador"));
    }

}
