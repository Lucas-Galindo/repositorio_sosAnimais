package sosanimais.com.example.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import sosanimais.com.example.app.controller.service.FuncionarioService;
import sosanimais.com.example.app.model.entity.Adotante;
import sosanimais.com.example.app.model.entity.Funcionario;
import sosanimais.com.example.app.model.util.Erro;

import java.sql.SQLException;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/apis/funcionario")
public class FuncionarioController {


    FuncionarioService funcService = new FuncionarioService();

    @PostMapping
    public ResponseEntity<Object> cadastro(@RequestBody Funcionario elemento) { // correto
        boolean aux = funcService.cadastro(elemento);
        if (aux)
            return ResponseEntity.ok(elemento);
        return ResponseEntity.badRequest().body(new Erro("Erro salvar funcionario"));
    }

    @GetMapping("/{mat}") // correto
    public ResponseEntity<Object> getFuncId(@PathVariable Long mat) {
        Funcionario aux = funcService.getId(mat);
        if (aux != null)
            return ResponseEntity.ok(aux);
        return ResponseEntity.badRequest().body(new Erro("Erro ao achar funcionario"));
    }

    @GetMapping("/lista") // correto
    public ResponseEntity<Object> getFuncLista() { //coreto
        List<Funcionario> lista = funcService.getAll("");
        if (lista != null)
            return ResponseEntity.ok(lista);
        return ResponseEntity.badRequest().body(new Erro("Erro ao listar funcionario"));
    }

    @GetMapping("/lista/{filtro}")
    public ResponseEntity<Object> getFuncLista(@PathVariable String filtro) {
        List<Funcionario> lista = funcService.getAll(filtro);
        if (lista != null)
            return ResponseEntity.ok(lista);
        return ResponseEntity.badRequest().body(new Erro("Erro ao listar funcionario"));
    }


    @DeleteMapping("/exclusao-mat/{mat}")
    public ResponseEntity<Object> deletar(@PathVariable Long mat) { //correto
        boolean aux = funcService.deletar(funcService.getId(mat));
        if (aux)
            return ResponseEntity.ok(aux);
        return ResponseEntity.badRequest().body(new Erro("Erro ao deletar funcionario"));

    }

    @DeleteMapping("/exclusao-pessoa/{id}")
    public ResponseEntity<Object> deletarPess(@PathVariable Long id) { //correto
        boolean aux = funcService.deletarPess(id);
        if (aux)
            return ResponseEntity.ok(aux);
        return ResponseEntity.badRequest().body(new Erro("Erro ao deletar Pessoa - funcionario"));

    }

    @PutMapping
    public ResponseEntity<Object> atualizar(@RequestBody Funcionario entidade) { //correto
        boolean aux = funcService.atualizar(entidade);
        if (aux)
            return ResponseEntity.ok(aux);
        return ResponseEntity.badRequest().body(new Erro("Erro ao atualizar funcionario"));
    }

    @GetMapping("/busca-pessoa/{idPessoa}")
    public ResponseEntity<Object> getPessoaId(@PathVariable Long idPessoa){
        Funcionario aux = funcService.getPessoaId(idPessoa);
        if(aux!=null)
            return ResponseEntity.ok(aux);
        return ResponseEntity.badRequest().body(new Erro("Não é adotante"));
    }

}

