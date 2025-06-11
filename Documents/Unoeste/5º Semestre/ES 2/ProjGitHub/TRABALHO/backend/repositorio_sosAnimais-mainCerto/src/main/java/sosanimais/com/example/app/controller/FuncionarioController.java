package sosanimais.com.example.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import sosanimais.com.example.app.controller.service.FuncionarioService;
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


    @DeleteMapping("/{mat}")
    public ResponseEntity<Object> deletar(@PathVariable Long mat) { //correto
        Funcionario aux = funcService.getId(mat);

        if(aux==null) {
            return ResponseEntity.badRequest().body(new Erro("Funcionário não encontrado"));
        }

        // Aqui detectamos se o motivo do false foi ser o último ADMIN
        if ("ADMIN".equals(aux.getRole()) && funcService.contarAdmins() <= 1) {
            return ResponseEntity.badRequest().body(new Erro("Não é possível excluir o único administrador do sistema"));
        }

        boolean deletado = funcService.deletar(aux);
        if(deletado) {
            return ResponseEntity.ok("Funcionário deletado com sucesso");
        }

        return ResponseEntity.badRequest().body(new Erro("Erro ao deletar funcionário"));
    }

    @PutMapping
    public ResponseEntity<Object> atualizar(@RequestBody Funcionario entidade) { //correto
        boolean aux = funcService.atualizar(entidade);
        if (aux)
            return ResponseEntity.ok(aux);
        return ResponseEntity.badRequest().body(new Erro("Erro ao atualizar funcionario"));
    }

}

