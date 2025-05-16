package sosanimais.com.example.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import sosanimais.com.example.app.controller.service.FuncionarioService;
import sosanimais.com.example.app.model.entity.Funcionario;
import sosanimais.com.example.app.model.util.Erro;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value=("/apis/funcionario"))
public class FuncionarioController {
    
    FuncionarioService funcService = new FuncionarioService();

    @PostMapping
    public ResponseEntity<Object> cadastro(@PathVariable Funcionario elemento){
        boolean aux = funcService.cadastro(elemento);
        if(aux)
            return ResponseEntity.ok(elemento);
        return ResponseEntity.badRequest().body( new Erro("Erro salvar funcionario"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getFuncId(@PathVariable Long id){
        Funcionario aux = funcService.getId(id);
        if(aux!=null)
            return ResponseEntity.ok(aux);
        return ResponseEntity.badRequest().body(new Erro("Erro ao achar funcionario"));
    }

    @GetMapping
    public ResponseEntity<Object> getFuncLista(){
        List<Funcionario> lista = funcService.getAll("");
        if(lista!=null)
            return ResponseEntity.ok(lista);
        return ResponseEntity.badRequest().body(new Erro("Erro ao listar funcionario"));
    }

    @GetMapping("/{filtro}")
    public ResponseEntity<Object> getFuncLista(@PathVariable String filtro){
        List<Funcionario> lista = funcService.getAll(filtro);
        if(lista!=null)
            return ResponseEntity.ok(lista);
        return ResponseEntity.badRequest().body(new Erro("Erro ao listar funcionario"));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletar(@PathVariable Long id){
        boolean aux = funcService.deletar(funcService.getId(id));
        if(aux==true)
            return ResponseEntity.ok(aux);
        return ResponseEntity.badRequest().body(new Erro("Erro ao deletar funcionario"));
    }


    @PutMapping
    public ResponseEntity<Object> atualizar(@RequestBody Funcionario entidade){
        boolean aux = funcService.atualizar(entidade);
        if(aux == true)
            return ResponseEntity.ok(aux);
        return ResponseEntity.badRequest().body(new Erro("Erro ao atualizar funcionario"));
    }





}
