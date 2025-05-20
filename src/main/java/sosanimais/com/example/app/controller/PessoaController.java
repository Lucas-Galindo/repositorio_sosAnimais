package sosanimais.com.example.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import sosanimais.com.example.app.controller.service.PessoaService;
import sosanimais.com.example.app.model.entity.Pessoa;
import sosanimais.com.example.app.model.util.Erro;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/apis/pessoa")
public class PessoaController {

    PessoaService pessoaService = new PessoaService();

    @PostMapping
    public ResponseEntity<Object> cadastro(@RequestBody Pessoa elemento){
        boolean aux = pessoaService.cadastro(elemento);
        if(aux)
            return ResponseEntity.ok(elemento);
        return ResponseEntity.badRequest().body( new Erro("Erro salvar Pessoa"));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletar(@PathVariable Long id){
        boolean aux = pessoaService.deletar(pessoaService.getId(id));
        if(aux==true)
            return ResponseEntity.ok(aux);
        return ResponseEntity.badRequest().body(new Erro("Erro ao deletar Pessoa"));
    }

    @PutMapping
    public ResponseEntity<Object> atualizar(@RequestBody Pessoa entidade){
        boolean aux = pessoaService.atualizar(entidade);
        if(aux)
            return ResponseEntity.ok(aux);
        return ResponseEntity.badRequest().body(new Erro("Erro ao atualizar Pessoa"));
    }


    @GetMapping("/lista")
    public ResponseEntity<Object> getPessoaLista(){
        List<Pessoa> lista = pessoaService.getAll("");
        if(lista!=null)
            return ResponseEntity.ok(lista);
        return ResponseEntity.badRequest().body(new Erro("Problema ao listar Pessoa"));
    }

    @GetMapping("/lista/{filtro}")
    public ResponseEntity<Object> getPessoaLista(@PathVariable String filtro){
        List<Pessoa> lista = pessoaService.getAll(filtro);
        if(lista!=null)
            return ResponseEntity.ok(lista);
        return ResponseEntity.badRequest().body(new Erro("Problema ao listar Pessoa"));

    }


//    @GetMapping("/buscaid/{id}")
//    public ResponseEntity<Object> getPessoaId(@PathVariable Long id){
//        Pessoa aux = pessoaService.getId(id);
//        if(aux!=null)
//            return ResponseEntity.ok(aux);
//        return ResponseEntity.badRequest().body(new Erro("Erro ao achar Pessoa"));
//    }

    @GetMapping("/buscacpf/{cpf}")
    public ResponseEntity<Object> getPessoaCPF(@PathVariable String cpf){
        Pessoa aux = pessoaService.getCpf(cpf);
        if(aux!=null)
            return ResponseEntity.ok(aux);
        return ResponseEntity.badRequest().body(new Erro("Erro ao achar Pessoa com cpf"));
    }

}
